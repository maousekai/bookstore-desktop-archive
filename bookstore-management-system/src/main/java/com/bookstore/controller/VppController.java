package com.bookstore.controller;

import com.bookstore.model.VanPhongPham;
import com.bookstore.model.GioHang;
import com.bookstore.util.AlertUtil;
import com.bookstore.util.ImageHelper; 
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.text.DecimalFormat;

public class VppController {
    @FXML private ImageView vppImageView;
    @FXML private Text titleLabel;
    @FXML private Label priceLabel;
    @FXML private Label supplierLabel;
    @FXML private Label stockLabel;
    @FXML private TextArea descriptionArea;

    private VanPhongPham vpp;
    private GioHang cart;
    private DecimalFormat df = new DecimalFormat("#,##0 VND");

    public void setVppAndCart(VanPhongPham vpp, GioHang cart) {
        this.vpp = vpp;
        this.cart = cart;
        loadVppDetails();
    }

    private void loadVppDetails() {
        if (vpp == null) return;

        titleLabel.setText(vpp.getTenVPP());
        priceLabel.setText(df.format(vpp.getDonGia()));
        supplierLabel.setText(vpp.getNhaCungCap());
        stockLabel.setText(String.valueOf(vpp.getSoLuong()));
        descriptionArea.setText(vpp.getGhiChu());
        Image img = ImageHelper.loadImage(vpp.getAnh(), "VPP");
        if (img != null) {
            vppImageView.setImage(img);
        }
    }

    @FXML
    private void handleAddToCart() {
        if (cart != null && vpp != null) {
            cart.addVPP(vpp, 1);
            AlertUtil.showInfo("Đã thêm \"" + vpp.getTenVPP() + "\" vào giỏ hàng!");
            handleClose(); 
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}