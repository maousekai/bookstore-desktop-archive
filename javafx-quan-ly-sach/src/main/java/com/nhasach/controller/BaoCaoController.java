package com.nhasach.controller;

import com.nhasach.dao.HoaDonDAO;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import java.time.Year;
import java.util.Map;


//Trong file BaoCaoController.java
import com.nhasach.dao.HoaDonDAO;
import java.util.Map;
import java.time.Year;

public class BaoCaoController {
 @FXML private BarChart<String, Number> doanhThuChart;
 private HoaDonDAO hoaDonDAO = new HoaDonDAO();

 @FXML
 void initialize() {
     int currentYear = Year.now().getValue();
     doanhThuChart.setTitle("Doanh thu theo tháng (Năm " + currentYear + ")");

     XYChart.Series<String, Number> series = new XYChart.Series<>();
     series.setName("Doanh thu");

     Map<String, Double> data = hoaDonDAO.getMonthlyRevenue(currentYear);

     for (Map.Entry<String, Double> entry : data.entrySet()) {
         series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
     }

     doanhThuChart.getData().add(series);
 }
}
