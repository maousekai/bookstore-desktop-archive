package com.nhasach.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.nhasach.dao.HoaDonDAO;
import com.nhasach.model.InvoiceModels.HoaDon;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

public class QuanLyHoaDonController {

    @FXML private TableView<HoaDon> tablehoaDon;
    @FXML private TableColumn<HoaDon, String> MaHDCol;
    @FXML private TableColumn<HoaDon, String> NgayLapCol;
    @FXML private TableColumn<HoaDon, Double> TongTienCol;
    @FXML private TableColumn<HoaDon, String> MaKHCol;
    @FXML private TableColumn<HoaDon, String> MaNVCol;
    
    

    private HoaDonDAO hoaDonDAO = new HoaDonDAO();

    @FXML
    public void initialize() {
    	MaHDCol.setCellValueFactory(new PropertyValueFactory<>("maHD"));
    	TongTienCol.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
    	MaKHCol.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        NgayLapCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().ngayLapStringProperty().get()));
        MaNVCol.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        
        

        loadData();
    }

    private void loadData() {
        List<HoaDon> list = hoaDonDAO.getAllInvoices();
        tablehoaDon.getItems().setAll(list);
    }
}
