package com.nhasach.controller;

import com.nhasach.model.Sach;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class ChiTietSachController {
    @FXML private ImageView imgSach;
    @FXML private Label lblTenSach, lblTacGia, lblNhaXB, lblTheLoai, lblGia, lblMoTa;

    public void setSach(Sach sach) {
        lblTenSach.setText(sach.getTenSach());
        lblTacGia.setText("Tác giả: " + sach.getTacGia());
        lblNhaXB.setText("Nhà XB: " + sach.getNhaXuatBan());
        lblTheLoai.setText("Thể loại: " + sach.getTheLoai());
        lblGia.setText(String.format("%,.0f đ", sach.getDonGia()));
        lblMoTa.setText("Mô tả: " + sach.getMoTa());
        

        if (sach.getAnh() != null && !sach.getAnh().isEmpty()) {
            File file = new File("Anh/" + sach.getAnh());
            if (file.exists()) {
                imgSach.setImage(new Image(file.toURI().toString()));
            }
        }

    }
}
