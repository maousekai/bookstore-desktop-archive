package com.nhasach.controller;

import com.nhasach.model.Sach;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class SachItemController {
    @FXML private ImageView anhSach;
    @FXML private Label tenSach;
    @FXML private Label giaSach;

    public void setData(Sach sach) {
        tenSach.setText(sach.getTenSach());
        giaSach.setText(String.format("%.0f đ", sach.getDonGia()));
        if (sach.getAnh() != null && !sach.getAnh().isEmpty()) {
            File file = new File("Anh/" + sach.getAnh());
            if (file.exists()) {
                anhSach.setImage(new Image(file.toURI().toString()));
            }
        }
    }
}

