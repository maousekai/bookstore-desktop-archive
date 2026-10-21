package com.nhasach.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;
import java.io.IOException;

import com.nhasach.dao.*;
import com.nhasach.model.*;
import com.nhasach.util.NhaSachUtils.AlertUtil;
import com.nhasach.model.BookMetadata.*;
import com.nhasach.dao.BookMetadataDAO.NhaXuatBanDAO;
import com.nhasach.dao.BookMetadataDAO.TacGiaDAO;
import com.nhasach.dao.BookMetadataDAO.TheLoaiDAO;
import com.nhasach.dao.BookMetadataDAO;


import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

public class QuanLySachController {

    // --- FXML Fields ---
    @FXML private TextField maSachField, tenSachField, donGiaField, soLuongField, namXBField;
    @FXML private TextField viTriKeField, viTriHangField, viTriNganField;
    @FXML private ComboBox<NhaXuatBan> nxbComboBox;
    @FXML private ComboBox<TheLoai> theLoaiComboBox;
    @FXML private ComboBox<TacGia> tacGiaComboBox;
    @FXML private ImageView anhSachImageView;
    @FXML private FlowPane flowPaneSach;   // hiển thị danh sách sách dạng thẻ
    @FXML private TextField txtTimKiem;



    // --- Dữ liệu & DAO ---
    private ObservableList<Sach> sachList = FXCollections.observableArrayList();
    private final SachDAO sachDAO = new SachDAO();
    private final TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
    private final TacGiaDAO tacGiaDAO = new TacGiaDAO();
    private final NhaXuatBanDAO nxbDAO = new NhaXuatBanDAO();

    private File selectedImageFile;

    // --- Khởi tạo ---
    @FXML
    void initialize() {
        loadComboBoxData();
        loadSachData();
        txtTimKiem.setOnAction(this::timKiemAction);

    }

    // --- Tải dữ liệu sách ---
    private void loadSachData() {
        try {
            sachList.setAll(sachDAO.getAll());
            loadSachCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Tạo thẻ sách (card view) ---
    private void loadSachCards() {
        flowPaneSach.getChildren().clear();

        for (Sach sach : sachList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nhasach/view/SachCard.fxml"));
                AnchorPane card = loader.load();

                // Lấy controller của thẻ và truyền dữ liệu sách
                SachCardController controller = loader.getController();
                controller.setData(sach);

                // Khi click vào thẻ => hiện chi tiết ở phần dưới
                card.setOnMouseClicked(e -> showSachDetails(sach));

                flowPaneSach.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // --- Hiển thị chi tiết sách ở phần dưới ---
    private void showSachDetails(Sach sach) {
        if (sach != null) {
            maSachField.setText(sach.getMaSach());
            tenSachField.setText(sach.getTenSach());
            donGiaField.setText(String.valueOf(sach.getDonGia()));
            soLuongField.setText(String.valueOf(sach.getSoLuong()));
            namXBField.setText(String.valueOf(sach.getNamXuatBan()));
            viTriKeField.setText(sach.getViTriKe());
            viTriHangField.setText(sach.getViTriHang());
            viTriNganField.setText(sach.getViTriNgan());

            nxbComboBox.getSelectionModel().select(
            	    new NhaXuatBan(sach.getMaNXB(), "", "", "")
            	);

            if (sach.getAnh() != null && !sach.getAnh().isEmpty()) {
                File imageFile = new File("Anh/" + sach.getAnh());
                if (imageFile.exists()) {
                    anhSachImageView.setImage(new Image(imageFile.toURI().toString()));
                } else {
                    anhSachImageView.setImage(null);
                }
            } else {
                anhSachImageView.setImage(null);
            }
        } else {
            lamMoiAction(null);
        }
    }

    // --- Tải dữ liệu ComboBox ---
    private void loadComboBoxData() {
        try {
            theLoaiComboBox.setItems(FXCollections.observableArrayList(theLoaiDAO.getAll()));
            tacGiaComboBox.setItems(FXCollections.observableArrayList(tacGiaDAO.getAll()));
            nxbComboBox.setItems(FXCollections.observableArrayList(nxbDAO.getAll()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Action: Làm mới ---
    @FXML
    void lamMoiAction(ActionEvent event) {
        maSachField.clear();
        tenSachField.clear();
        donGiaField.clear();
        soLuongField.clear();
        namXBField.clear();
        viTriKeField.clear();
        viTriHangField.clear();
        viTriNganField.clear();

        nxbComboBox.getSelectionModel().clearSelection();
        theLoaiComboBox.getSelectionModel().clearSelection();
        tacGiaComboBox.getSelectionModel().clearSelection();

        anhSachImageView.setImage(null);
        selectedImageFile = null;
    }

    // --- Action: Chọn ảnh ---
    @FXML
    void chonAnhAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            try {
                anhSachImageView.setImage(new Image(file.toURI().toURL().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    // --- Action: Thêm sách ---
    @FXML
    void themSachAction(ActionEvent event) {
        try {
            Sach newSach = new Sach();
            newSach.setMaSach(maSachField.getText());
            newSach.setTenSach(tenSachField.getText());
            newSach.setDonGia(Double.parseDouble(donGiaField.getText()));
            newSach.setSoLuong(Integer.parseInt(soLuongField.getText()));
            newSach.setNamXuatBan(Integer.parseInt(namXBField.getText()));
            newSach.setViTriKe(viTriKeField.getText());
            newSach.setViTriHang(viTriHangField.getText());
            newSach.setViTriNgan(viTriNganField.getText());

            if (nxbComboBox.getValue() != null)
                newSach.setMaNXB(nxbComboBox.getValue().getMaNXB());

            // Copy ảnh
            if (selectedImageFile != null) {
                Path destDir = Paths.get("Anh");
                if (!Files.exists(destDir)) Files.createDirectories(destDir);
                String newFileName = newSach.getMaSach() + "_" + selectedImageFile.getName();
                Path targetPath = destDir.resolve(newFileName);
                Files.copy(selectedImageFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                newSach.setAnh(newFileName);
            }

            if (sachDAO.add(newSach)) {
                AlertUtil.showInfo("Thành công", "Thêm sách thành công!", "");
                loadSachData();
                lamMoiAction(null);
            } else {
                AlertUtil.showError("Lỗi", "Thêm sách thất bại!", "Vui lòng kiểm tra lại dữ liệu.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Lỗi", "Không thể thêm sách", e.getMessage());
        }
    }

    // --- Action: Xóa sách ---
    @FXML
    void xoaSachAction(ActionEvent event) {
        String maSach = maSachField.getText();
        if (maSach == null || maSach.isEmpty()) {
            AlertUtil.showError("Chưa chọn sách", "Vui lòng chọn sách cần xóa.", "");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc muốn xóa sách có mã: " + maSach + "?");
        alert.setContentText("Hành động này sẽ xóa vĩnh viễn dữ liệu.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (sachDAO.delete(maSach)) {
                    AlertUtil.showInfo("Thành công", "Đã xóa sách khỏi hệ thống.", "");
                    loadSachData();
                    lamMoiAction(null);
                }
            } catch (Exception e) {
                AlertUtil.showError("Lỗi CSDL", "Không thể xóa sách", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // --- Action: Sửa sách ---
    @FXML
    void suaSachAction(ActionEvent event) {
        String maSach = maSachField.getText();
        if (maSach == null || maSach.isEmpty()) {
            AlertUtil.showError("Chưa chọn sách", "Vui lòng chọn sách cần sửa.", "");
            return;
        }

        try {
            Sach updated = sachDAO.findById(maSach);
            if (updated == null) {
                AlertUtil.showError("Lỗi", "Không tìm thấy sách trong CSDL!", "");
                return;
            }

            updated.setTenSach(tenSachField.getText());
            updated.setDonGia(Double.parseDouble(donGiaField.getText()));
            updated.setSoLuong(Integer.parseInt(soLuongField.getText()));
            updated.setNamXuatBan(Integer.parseInt(namXBField.getText()));
            updated.setViTriKe(viTriKeField.getText());
            updated.setViTriHang(viTriHangField.getText());
            updated.setViTriNgan(viTriNganField.getText());

            // ảnh mới (nếu có)
            if (selectedImageFile != null) {
                Path destDir = Paths.get("Anh");
                if (!Files.exists(destDir)) Files.createDirectories(destDir);
                String newFileName = updated.getMaSach() + "_" + selectedImageFile.getName();
                Path targetPath = destDir.resolve(newFileName);
                Files.copy(selectedImageFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                updated.setAnh(newFileName);
            }

            if (sachDAO.update(updated, null, null)) {
                AlertUtil.showInfo("Thành công", "Cập nhật sách thành công!", "");
                loadSachData();
                lamMoiAction(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Lỗi", "Không thể sửa sách", e.getMessage());
        }
    }

    // --- Action: Tìm kiếm 
    @FXML
    private void timKiemAction(ActionEvent event) {
        String tuKhoa = txtTimKiem.getText().trim().toLowerCase();

        if (tuKhoa.isEmpty()) {
            // Nếu không nhập gì -> hiển thị lại toàn bộ
            loadSachCards();
            return;
        }

        // Lọc danh sách theo tên hoặc mã sách
        List<Sach> ketQua = sachList.stream()
                .filter(sach -> sach.getTenSach().toLowerCase().contains(tuKhoa)
                             || sach.getMaSach().toLowerCase().contains(tuKhoa))
                .toList();

        // Hiển thị kết quả
        hienThiDanhSachTimKiem(ketQua);
    }

    private void hienThiDanhSachTimKiem(List<Sach> ketQua) {
        flowPaneSach.getChildren().clear();

        if (ketQua.isEmpty()) {
            AlertUtil.showInfo("Kết quả tìm kiếm", "Không tìm thấy sách nào phù hợp!", "");
            return;
        }

        for (Sach sach : ketQua) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nhasach/view/SachCard.fxml"));
                AnchorPane card = loader.load();
                SachCardController controller = loader.getController();
                controller.setData(sach);
                card.setOnMouseClicked(e -> showSachDetails(sach));
                flowPaneSach.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
