package com.nhasach.controller;

import com.nhasach.model.InvoiceModels.ChiTietHoaDon;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class KhachHangController {
    
    @FXML private TableView<ChiTietHoaDon> gioHangTableKhach;
    @FXML private TableColumn<ChiTietHoaDon, String> TenSachCol;
    @FXML private TableColumn<ChiTietHoaDon, Number> soLuongCol;
    @FXML private TableColumn<ChiTietHoaDon, Number> donGiaCol;
    @FXML private TableColumn<ChiTietHoaDon, Number> thanhTienCol;
    @FXML private Label lblTongTienKhach;

    // Hàm này được gọi từ BanHangController để truyền dữ liệu qua
    public void initData(ObservableList<ChiTietHoaDon> gioHang, SimpleDoubleProperty tongTien) {
        // Cấu hình các cột
        TenSachCol.setCellValueFactory(cell -> cell.getValue().tenSachProperty());
        soLuongCol.setCellValueFactory(cell -> cell.getValue().soLuongProperty());
        donGiaCol.setCellValueFactory(cell -> cell.getValue().donGiaProperty());
        thanhTienCol.setCellValueFactory(cell -> cell.getValue().thanhTienProperty());

        // **Quan trọng: Dùng chung giỏ hàng và tổng tiền với màn hình nhân viên**
        gioHangTableKhach.setItems(gioHang);
        lblTongTienKhach.textProperty().bind(tongTien.asString("%,.0f VNĐ"));
    }
}
