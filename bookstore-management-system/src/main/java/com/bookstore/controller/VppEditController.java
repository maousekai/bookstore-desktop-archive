package com.bookstore.controller;

import com.bookstore.dao.VppDAO;
import com.bookstore.model.VanPhongPham;
import com.bookstore.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class VppEditController {

    @FXML private Text titleText;
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private TextField imageField;
    @FXML private ComboBox<String> supplierComboBox;
    @FXML private TextArea notesArea;
    @FXML private Button saveButton;
    @FXML private TextField promotionField;

    private VppDAO vppDAO = new VppDAO();
    private VanPhongPham currentVpp;
    private boolean dataChanged = false;

    @FXML
    private void initialize() {
        // Tải các NCC (giả) giống như ThemsanphamController
        supplierComboBox.setItems(FXCollections.observableArrayList(
            "Thiên Long", "Campus", "Hồng Hà", "Deli", "Pentel"
        ));
    }

    public void setVpp(VanPhongPham vpp) {
        this.currentVpp = vpp;
        populateData();
    }
    
    private void populateData() {
        titleText.setText("Sửa: " + currentVpp.getTenVPP());
        nameField.setText(currentVpp.getTenVPP());
        priceField.setText(String.valueOf(currentVpp.getDonGia()));
        stockField.setText(String.valueOf(currentVpp.getSoLuong()));
        imageField.setText(currentVpp.getAnh());
        notesArea.setText(currentVpp.getGhiChu());
        promotionField.setText(currentVpp.getKhuyenMai());
        supplierComboBox.setValue(currentVpp.getNhaCungCap()); // Đặt NCC
    }
    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh VPP");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter(
                "Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"
            )
        );

        Stage stage = (Stage) saveButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile == null) return;

        try {
            Path projectRoot = Paths.get(System.getProperty("user.dir"));

            Path destDir = projectRoot
                    .resolve("userdata")
                    .resolve("images")
                    .resolve("vpp");

            Files.createDirectories(destDir);

            Path destPath = destDir.resolve(selectedFile.getName());

            Files.copy(
                selectedFile.toPath(),
                destPath,
                StandardCopyOption.REPLACE_EXISTING
            );

            imageField.setText(selectedFile.getName());
            AlertUtil.showInfo("Đã lưu ảnh VPP thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Lỗi khi lưu ảnh: " + e.getMessage());
        }
    }

    @FXML
    private void handleSave() {
        try {
            // Cập nhật dữ liệu vào đối tượng
            currentVpp.setTenVPP(nameField.getText());
            currentVpp.setDonGia(Double.parseDouble(priceField.getText()));
            currentVpp.setAnh(imageField.getText());
            currentVpp.setNhaCungCap(supplierComboBox.getValue()); // Lấy NCC từ ComboBox
            currentVpp.setGhiChu(notesArea.getText());
            currentVpp.setKhuyenMai(promotionField.getText());
            
            // Gọi DAO để update
            vppDAO.update(currentVpp);
            
            dataChanged = true;
            AlertUtil.showInfo("Cập nhật VPP thành công!");
            handleCancel(); // Đóng cửa sổ

        } catch (NumberFormatException e) {
            AlertUtil.showError("Giá phải là SỐ.");
        } catch (SQLException e) {
            AlertUtil.showError("Lỗi CSDL khi cập nhật VPP: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
    
    public boolean isDataChanged() {
        return dataChanged;
    }
}