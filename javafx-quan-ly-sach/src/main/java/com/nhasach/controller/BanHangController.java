package com.nhasach.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import com.nhasach.dao.HoaDonDAO;
import com.nhasach.dao.SachDAO;
import com.nhasach.model.InvoiceModels;
import com.nhasach.model.Sach;
import com.nhasach.util.NhaSachUtils.AlertUtil;
import com.nhasach.model.InvoiceModels.ChiTietHoaDon;
import com.nhasach.model.InvoiceModels;
import com.nhasach.util.NhaSachUtils.LoggedInUser;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class BanHangController {

    // Vùng tìm kiếm sách
    @FXML private TextField searchField;
    @FXML private TableView<Sach> sachSearchResultTable;
    @FXML private TableColumn<Sach, String> searchMaSachCol;
    @FXML private TableColumn<Sach, String> searchTenSachCol;
    @FXML private TableColumn<Sach, Number> searchDonGiaCol;
    @FXML private TableColumn<Sach, Number> searchSoLuongCol;

    // Vùng giỏ hàng
    @FXML private TableView<ChiTietHoaDon> gioHangTable;
    @FXML private TableColumn<ChiTietHoaDon, String> cartTenSachCol;
    @FXML private TableColumn<ChiTietHoaDon, Number> cartSoLuongCol;
    @FXML private TableColumn<ChiTietHoaDon, Number> cartDonGiaCol;
    @FXML private TableColumn<ChiTietHoaDon, Number> cartThanhTienCol;
    @FXML private Label lblTongTien;

    private SachDAO sachDAO = new SachDAO();
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    
    // Đây là cái chính của module: 1 giỏ hàng được chia sẻ giữa 2 màn hình
    private ObservableList<ChiTietHoaDon> gioHangList = FXCollections.observableArrayList();
    private SimpleDoubleProperty tongTien = new SimpleDoubleProperty(0);
    
    private Stage customerStage;
    private KhachHangController khachHangController;
    
    @FXML
    void initialize() {
        // Cấu hình bảng kết quả tìm kiếm
        searchMaSachCol.setCellValueFactory(cell -> cell.getValue().maSachProperty());
        searchTenSachCol.setCellValueFactory(cell -> cell.getValue().tenSachProperty());
        searchDonGiaCol.setCellValueFactory(cell -> cell.getValue().donGiaProperty());
        searchSoLuongCol.setCellValueFactory(cell -> cell.getValue().soLuongProperty());

        // Thêm sách vào giỏ hàng khi double-click
        sachSearchResultTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Sach selectedSach = sachSearchResultTable.getSelectionModel().getSelectedItem();
                if (selectedSach != null) {
                    themVaoGioHang(selectedSach);
                }
            }
        });

        // Cấu hình bảng giỏ hàng
        cartTenSachCol.setCellValueFactory(cell -> cell.getValue().tenSachProperty());
        cartSoLuongCol.setCellValueFactory(cell -> cell.getValue().soLuongProperty());
        cartDonGiaCol.setCellValueFactory(cell -> cell.getValue().donGiaProperty());
        cartThanhTienCol.setCellValueFactory(cell -> cell.getValue().thanhTienProperty());
        
        gioHangTable.setItems(gioHangList);

        // Binding tổng tiền vào label
        lblTongTien.textProperty().bind(tongTien.asString("%,.0f VNĐ"));

        // Listener cho thanh tìm kiếm
        searchField.textProperty().addListener((obs, oldV, newV) -> timKiemSach(newV));
        
        timKiemSach(""); // Tải tất cả sách lúc đầu
    }

    private void timKiemSach(String query) {
        try {
            sachSearchResultTable.setItems(FXCollections.observableArrayList(sachDAO.searchByName(query)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
 // Trong file BanHangController.java

    private void themVaoGioHang(Sach sach) {
        // Kiểm tra tồn kho trước khi thêm
        if (sach.getSoLuong() <= 0) {
            AlertUtil.showError("Hết hàng", "Sản phẩm đã hết hàng", "Không thể thêm sách '" + sach.getTenSach() + "' vào giỏ.");
            return;
        }

        // Kiểm tra sách đã có trong giỏ hàng chưa
        for (ChiTietHoaDon item : gioHangList) {
            if (item.getMaSach().equals(sach.getMaSach())) {
                // Nếu thêm nữa sẽ vượt quá tồn kho
                if (item.getSoLuong() + 1 > sach.getSoLuong()) {
                    AlertUtil.showError("Không đủ hàng", "Số lượng tồn kho không đủ.", "Bạn chỉ có thể mua tối đa " + sach.getSoLuong() + " cuốn '" + sach.getTenSach() + "'.");
                    return;
                }
                item.setSoLuong(item.getSoLuong() + 1); // Tăng số lượng
                capNhatTongTien();
                gioHangTable.refresh(); // Cập nhật lại bảng
                return;
            }
        }


        gioHangList.add(new ChiTietHoaDon(sach, 1));
        capNhatTongTien();
    }

    @FXML
    void xoaKhoiGioHang(ActionEvent event) {
        ChiTietHoaDon selectedItem = gioHangTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            gioHangList.remove(selectedItem);
            capNhatTongTien();
        }
    }

    private void capNhatTongTien() {
        double total = 0;
        for (ChiTietHoaDon item : gioHangList) {
            total += item.getThanhTien();
        }
        tongTien.set(total);
    }

    @FXML
    void openCustomerDisplay(ActionEvent event) {
        if (customerStage == null || !customerStage.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nhasach/view/KhachHangView.fxml"));
                Parent root = loader.load();
                
                khachHangController = loader.getController();
                // **Chia sẻ giỏ hàng cho màn hình khách**
                khachHangController.initData(gioHangList, tongTien);

                customerStage = new Stage();
                customerStage.setTitle("Màn hình Khách hàng");
                customerStage.setScene(new Scene(root));
                customerStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void huyHoaDon(ActionEvent event) {
        gioHangList.clear();
        capNhatTongTien();
    }

    @FXML
    void thanhToan(ActionEvent event) {
        if (gioHangList.isEmpty()) {
            AlertUtil.showError("Lỗi", "Giỏ hàng trống", "Vui lòng thêm sách vào giỏ hàng trước khi thanh toán.");
            return;
        }

        try {
            String maHD = "HD-" + UUID.randomUUID().toString().substring(0, 8);
            String maNV = LoggedInUser.getInstance().getNhanVien().getMaNV();
            
            boolean success = hoaDonDAO.createInvoice(maHD, maNV, tongTien.get(), gioHangList);
            
            if (success) {
                AlertUtil.showInfo("Thành công", "Thanh toán thành công!", "Hóa đơn " + maHD + " đã được lưu.");
                huyHoaDon(null);
                timKiemSach(""); 
            } else {
                AlertUtil.showError("Thất bại", "Không thể lưu hóa đơn", "Đã có lỗi xảy ra trong quá trình lưu. Vui lòng thử lại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
