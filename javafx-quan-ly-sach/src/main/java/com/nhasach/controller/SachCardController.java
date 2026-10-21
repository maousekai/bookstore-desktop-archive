package com.nhasach.controller;

import com.nhasach.model.Sach;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.File;

import java.io.IOException;

public class SachCardController {
    @FXML private AnchorPane cardPane;
    @FXML private ImageView anhSach;
    @FXML private Label lblTenSach;
    @FXML private Label lblGia;

    private Sach sach;

    public void setData(Sach sach) {
        this.sach = sach;
        lblTenSach.setText(sach.getTenSach());
        lblGia.setText(String.format("%,.0f đ", sach.getDonGia()));
        try {
        	if (sach.getAnh() != null && !sach.getAnh().isEmpty()) {
        	    File file = new File("Anh/" + sach.getAnh());
        	    if (file.exists()) {
        	        anhSach.setImage(new Image(file.toURI().toString()));
        	    }
        	}
        } catch (Exception e) {
            System.out.println("Không tải được ảnh: " + e.getMessage());
        }

        cardPane.setOnMouseClicked(this::openDetail);
    }

    private void openDetail(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nhasach/view/ChiTietSach.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            ChiTietSachController controller = loader.getController();
            controller.setSach(sach);
            stage.setTitle(sach.getTenSach());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
