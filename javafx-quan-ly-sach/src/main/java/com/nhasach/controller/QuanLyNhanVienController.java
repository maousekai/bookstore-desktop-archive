package com.nhasach.controller;

import java.util.Optional;
import java.time.LocalDate;
import com.nhasach.dao.NhanVienDAO;
import com.nhasach.model.NhanVien;
import com.nhasach.util.NhaSachUtils.AlertUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class QuanLyNhanVienController {
	@FXML private TextField maNVField, TenNVField, taiKhoanField, dienThoaiField, diaChiField, viTriField, luongCoBanField; // Thêm luongCoBanField
    @FXML private PasswordField matKhauField;
    @FXML private ComboBox<String> quyenComboBox;
    @FXML private DatePicker ngaySinhPicker, ngayVaoLamPicker;
    
    @FXML private TableView<NhanVien> nhanVienTableView;
    @FXML private TableColumn<NhanVien, String> maNVCol;
    @FXML private TableColumn<NhanVien, String> TenNVCol;
    @FXML private TableColumn<NhanVien, String> taiKhoanCol;
    @FXML private TableColumn<NhanVien, String> quyenCol; 
    @FXML private TableColumn<NhanVien, String> dienThoaiCol; 
    @FXML private TableColumn<NhanVien, String> viTriCol; 
    @FXML private TableColumn<NhanVien, Double> luongCoBanCol; 
    @FXML private TableColumn<NhanVien, LocalDate> ngayVaoLamCol;
    @FXML private TableColumn<NhanVien, LocalDate> ngaySinhCol;	
    

    private ObservableList<NhanVien> nhanVienList = FXCollections.observableArrayList();
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();

    @FXML
    void initialize() {

        maNVCol.setCellValueFactory(cellData -> cellData.getValue().maNVProperty());
        TenNVCol.setCellValueFactory(cellData -> cellData.getValue().TenNVProperty());
        taiKhoanCol.setCellValueFactory(cellData -> cellData.getValue().taiKhoanProperty());
        dienThoaiCol.setCellValueFactory(cellData -> cellData.getValue().dienThoaiProperty());
        viTriCol.setCellValueFactory(cellData -> cellData.getValue().viTriProperty());
        luongCoBanCol.setCellValueFactory(cellData -> cellData.getValue().luongCoBanProperty().asObject());
        

        quyenCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().getMaQuyen() == 1) {
                return new javafx.beans.property.SimpleStringProperty("Quản lý");
            } else {
                return new javafx.beans.property.SimpleStringProperty("Nhân viên");
            }
        });
        
        quyenComboBox.setItems(FXCollections.observableArrayList("Quản lý", "Nhân viên"));

        nhanVienTableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> showNhanVienDetails(newVal));

        loadNhanVienData();
        
    }

    private void loadNhanVienData() {
        try {
            nhanVienList.setAll(nhanVienDAO.getAll());
            nhanVienTableView.setItems(nhanVienList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNhanVienDetails(NhanVien nv) {
        if (nv != null) {
            maNVField.setText(nv.getMaNV());
            TenNVField.setText(nv.getTenNV());
            taiKhoanField.setText(nv.getTaiKhoan());
            matKhauField.clear();
            quyenComboBox.getSelectionModel().select(nv.getMaQuyen() == 1 ? "Quản lý" : "Nhân viên");
            ngaySinhPicker.setValue(nv.getNgaySinh());
            dienThoaiField.setText(nv.getDienThoai());
            diaChiField.setText(nv.getDiaChi());
            viTriField.setText(nv.getViTri());
            luongCoBanField.setText(String.valueOf(nv.getLuongCoBan()));
            ngayVaoLamPicker.setValue(nv.getNgayVaoLam());
            
        } else {
            lamMoiAction(null);
        }
    }

    @FXML
    void lamMoiAction(ActionEvent event) {
        // Xóa sạch nội dung các ô nhập
        maNVField.clear();
        TenNVField.clear();
        taiKhoanField.clear();
        matKhauField.clear();
        dienThoaiField.clear();
        diaChiField.clear();
        viTriField.clear();
        luongCoBanField.clear();

        // Reset DatePicker về null
        ngaySinhPicker.setValue(null);
        ngayVaoLamPicker.setValue(null);

        // Xóa chọn ComboBox quyền
        quyenComboBox.getSelectionModel().clearSelection();

        // Xóa chọn dòng trong bảng
        nhanVienTableView.getSelectionModel().clearSelection();
    }


    @FXML
    void themAction(ActionEvent event) {
        try {
            String quyen = quyenComboBox.getValue();
            if (quyen == null) {
                AlertUtil.showError("Thiếu thông tin", null, "Vui lòng chọn quyền (Quản lý / Nhân viên) trước khi thêm!");
                return;
            }

            NhanVien nv = new NhanVien();
            nv.setMaNV(maNVField.getText());
            nv.setTenNV(TenNVField.getText());
            nv.setTaiKhoan(taiKhoanField.getText());
            nv.setMatKhau(matKhauField.getText());
            nv.setMaQuyen(quyen.equals("Quản lý") ? 1 : 2);
            nv.setNgaySinh(ngaySinhPicker.getValue());
            nv.setDienThoai(dienThoaiField.getText());
            nv.setDiaChi(diaChiField.getText());
            nv.setViTri(viTriField.getText());
            nv.setLuongCoBan(luongCoBanField.getText().isEmpty() ? 0.0 : Double.parseDouble(luongCoBanField.getText()));
            nv.setNgayVaoLam(ngayVaoLamPicker.getValue());
            

            if (nhanVienDAO.add(nv)) {
                AlertUtil.showInfo("Thành công", null, "Đã thêm nhân viên mới!");
                loadNhanVienData();
                lamMoiAction(null);
            }
        } catch (Exception e) {
            AlertUtil.showError("Lỗi thêm nhân viên", null, e.getMessage());
        }
    }

    

    @FXML
    void suaAction(ActionEvent event) {
        NhanVien selectedNV = nhanVienTableView.getSelectionModel().getSelectedItem();
        if (selectedNV == null) {
            AlertUtil.showError("Chưa chọn nhân viên", null, "Vui lòng chọn một nhân viên từ danh sách để sửa.");
            return;
        }
        
        try {
            NhanVien nvToUpdate = new NhanVien();
            nvToUpdate.setMaNV(maNVField.getText());
            nvToUpdate.setTenNV(TenNVField.getText());
            nvToUpdate.setTaiKhoan(taiKhoanField.getText());
            nvToUpdate.setMatKhau(matKhauField.getText()); // DAO sẽ kiểm tra nếu rỗng thì không đổi
            nvToUpdate.setMaQuyen(quyenComboBox.getValue().equals("Quản lý") ? 1 : 2);
            nvToUpdate.setNgaySinh(ngaySinhPicker.getValue());
            nvToUpdate.setDienThoai(dienThoaiField.getText());
            nvToUpdate.setDiaChi(diaChiField.getText());
            nvToUpdate.setViTri(viTriField.getText());
            nvToUpdate.setLuongCoBan(luongCoBanField.getText().isEmpty() ? 0.0 : Double.parseDouble(luongCoBanField.getText()));
            nvToUpdate.setNgayVaoLam(ngayVaoLamPicker.getValue());
            

            if (nhanVienDAO.update(nvToUpdate)) {
                 AlertUtil.showInfo("Thành công", null, "Đã cập nhật thông tin nhân viên.");
                loadNhanVienData();
                lamMoiAction(null);
            }
        } catch (Exception e) {
            AlertUtil.showError("Lỗi CSDL", "Không thể cập nhật nhân viên", e.getMessage());
        }
    }
    
    @FXML
    void xoaAction(ActionEvent event) {
        NhanVien selectedNV = nhanVienTableView.getSelectionModel().getSelectedItem();
        if (selectedNV == null) {
            AlertUtil.showError("Chưa chọn nhân viên", null, "Vui lòng chọn một nhân viên để xóa.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa nhân viên '" + selectedNV.getTenNV() + "'?");
        alert.setContentText("Hành động này không thể hoàn tác.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (nhanVienDAO.delete(selectedNV.getMaNV())) {
                    AlertUtil.showInfo("Thành công", null, "Đã xóa nhân viên.");
                    loadNhanVienData();
                    lamMoiAction(null);
                }
            } catch (Exception e) {
                AlertUtil.showError("Lỗi CSDL", "Không thể xóa nhân viên", "Lỗi có thể do nhân viên này đã có dữ liệu liên quan (hóa đơn, chấm công).");
            }
        }
    }
}
