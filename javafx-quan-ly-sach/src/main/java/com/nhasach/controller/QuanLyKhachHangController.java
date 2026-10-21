package com.nhasach.controller;

import com.nhasach.dao.KhachHangDAO;
import com.nhasach.model.KhachHang;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

public class QuanLyKhachHangController {

    @FXML private TableView<KhachHang> khachHangTable;
    @FXML private TableColumn<KhachHang, String> MaKHCol;
    @FXML private TableColumn<KhachHang, String> HoTenCol;
    @FXML private TableColumn<KhachHang, String> DienThoaiCol;
    @FXML private TableColumn<KhachHang, String> EmailCol;
    @FXML private TableColumn<KhachHang, String> DiaChiCol;

    @FXML private TextField txtMaKH, txtHoTen, txtDienThoai, txtEmail, txtDiaChi, txtTimKiem;
    @FXML private Button btnThem, btnSua, btnXoa, btnLamMoi;

    private final KhachHangDAO khachHangDAO = new KhachHangDAO();
    private final ObservableList<KhachHang> list = FXCollections.observableArrayList();
    private FilteredList<KhachHang> filteredList;

    @FXML
    public void initialize() {
        // Liên kết cột
        MaKHCol.setCellValueFactory(cell -> cell.getValue().maKHProperty());
        HoTenCol.setCellValueFactory(cell -> cell.getValue().hoTenProperty());
        DienThoaiCol.setCellValueFactory(cell -> cell.getValue().dienThoaiProperty());
        EmailCol.setCellValueFactory(cell -> cell.getValue().emailProperty());
        DiaChiCol.setCellValueFactory(cell -> cell.getValue().diaChiProperty());

        // Cho phép sửa trực tiếp trên bảng
        khachHangTable.setEditable(true);
        HoTenCol.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        DienThoaiCol.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        EmailCol.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        DiaChiCol.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        // Lưu thay đổi sau khi sửa trực tiếp
        HoTenCol.setOnEditCommit(e -> updateCell(e, "HoTen"));
        DienThoaiCol.setOnEditCommit(e -> updateCell(e, "DienThoai"));
        EmailCol.setOnEditCommit(e -> updateCell(e, "Email"));
        DiaChiCol.setOnEditCommit(e -> updateCell(e, "DiaChi"));

        loadData();

        // Lọc theo tìm kiếm
        txtTimKiem.textProperty().addListener((obs, oldVal, newVal) -> {
            String keyword = newVal.toLowerCase().trim();
            filteredList.setPredicate(kh -> {
                if (keyword.isEmpty()) return true;
                return kh.getHoTen().toLowerCase().contains(keyword)
                        || kh.getDienThoai().toLowerCase().contains(keyword);
            });
        });

        // Khi chọn hàng, hiển thị dữ liệu lên form
        khachHangTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtMaKH.setText(newSel.getMaKH());
                txtHoTen.setText(newSel.getHoTen());
                txtDienThoai.setText(newSel.getDienThoai());
                txtEmail.setText(newSel.getEmail());
                txtDiaChi.setText(newSel.getDiaChi());
            }
        });
    }

    private void updateCell(TableColumn.CellEditEvent<KhachHang, String> event, String field) {
        KhachHang kh = event.getRowValue();
        String newValue = event.getNewValue();

        switch (field) {
            case "HoTen" -> kh.setHoTen(newValue);
            case "DienThoai" -> kh.setDienThoai(newValue);
            case "Email" -> kh.setEmail(newValue);
            case "DiaChi" -> kh.setDiaChi(newValue);
        }

        if (khachHangDAO.updateKhachHang(kh)) {
            showInfo("Cập nhật dữ liệu thành công!");
        } else {
            showAlert("Cập nhật thất bại!");
        }
    }

    private void loadData() {
        list.setAll(khachHangDAO.getAllKhachHang());
        filteredList = new FilteredList<>(list, p -> true);
        khachHangTable.setItems(filteredList);
    }

    @FXML
    private void themAction() {
        if (txtMaKH.getText().isEmpty() || txtHoTen.getText().isEmpty()) {
            showAlert("Vui lòng nhập Mã KH và Họ Tên!");
            return;
        }

        KhachHang kh = new KhachHang(
                txtMaKH.getText(),
                txtHoTen.getText(),
                txtDienThoai.getText(),
                txtEmail.getText(),
                txtDiaChi.getText()
        );

        if (khachHangDAO.addKhachHang(kh)) {
            loadData();
            clearForm();
            showInfo("Thêm khách hàng thành công!");
        } else {
            showAlert("Thêm thất bại! Có thể mã khách hàng đã tồn tại.");
        }
    }

    @FXML
    private void suaAction() {
        KhachHang kh = khachHangTable.getSelectionModel().getSelectedItem();
        if (kh == null) {
            showAlert("Chọn khách hàng cần sửa!");
            return;
        }

        kh.setHoTen(txtHoTen.getText());
        kh.setDienThoai(txtDienThoai.getText());
        kh.setEmail(txtEmail.getText());
        kh.setDiaChi(txtDiaChi.getText());

        if (khachHangDAO.updateKhachHang(kh)) {
            loadData();
            showInfo("Cập nhật thành công!");
        } else {
            showAlert("Sửa thất bại!");
        }
    }

    @FXML
    private void xoaAction() {
        KhachHang kh = khachHangTable.getSelectionModel().getSelectedItem();
        if (kh == null) {
            showAlert("Chọn khách hàng cần xóa!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Bạn có chắc muốn xóa khách hàng " + kh.getHoTen() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            if (khachHangDAO.deleteKhachHang(kh.getMaKH())) {
                loadData();
                clearForm();
                showInfo("Đã xóa khách hàng!");
            } else {
                showAlert("Xóa thất bại!");
            }
        }
    }

    @FXML
    private void lamMoiAction(ActionEvent event) {
        clearForm();
        loadData();
    }

    private void clearForm() {
        txtMaKH.clear();
        txtHoTen.clear();
        txtDienThoai.clear();
        txtEmail.clear();
        txtDiaChi.clear();
        txtTimKiem.clear();
        khachHangTable.getSelectionModel().clearSelection();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Cảnh báo");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Thành công");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
