package com.bookstore.controller;

import com.bookstore.dao.NhaXuatBanDAO;
import com.bookstore.dao.SachDAO;
import com.bookstore.model.Sach;
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
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class SachController {
    @FXML private ImageView bookImageView;
    @FXML private Text titleLabel;
    @FXML private Label authorLabel;
    @FXML private Label priceLabel;
    @FXML private Label genreLabel;
    @FXML private Label publisherLabel;
    @FXML private Label yearLabel;
    @FXML private Label locationLabel;
    @FXML private Label saleOffLabel;
    @FXML private TextArea descriptionArea;

    private Sach book;
    private GioHang cart;
    private DecimalFormat df = new DecimalFormat("#,##0 VND");
    
    private SachDAO sachDAO = new SachDAO();
    private NhaXuatBanDAO nxbDAO = new NhaXuatBanDAO();

    public void setBookAndCart(Sach book, GioHang cart) {
        this.book = book;
        this.cart = cart;
        loadBookDetails();
    }

    private void loadBookDetails() {
        if (book == null) return;

        titleLabel.setText(book.getTenSach());
        priceLabel.setText(df.format(book.getDonGia()));
        yearLabel.setText(String.valueOf(book.getNamXuatBan()));
        locationLabel.setText(book.getViTriKe() + " - " + book.getViTriNgan() + " - " + book.getViTriHang());
        descriptionArea.setText(book.getMoTa());
        saleOffLabel.setText(book.getKhuyenMai());

        Image img = ImageHelper.loadImage(book.getAnh(), "BOOK");
        if (img != null) {
            bookImageView.setImage(img);
        } else {

            System.out.println("Khong tim thay anh cho sach: " + book.getTenSach());
        }


        try {
            List<String> authors = sachDAO.getAuthorsForBook(book.getMaSach());
            authorLabel.setText("Tác giả: " + String.join(", ", authors));

            List<String> genres = sachDAO.getGenresForBook(book.getMaSach());
            genreLabel.setText(String.join(", ", genres));

            if (nxbDAO.getById(book.getMaNXB()) != null) {
                publisherLabel.setText(nxbDAO.getById(book.getMaNXB()).getTenNXB());
            }

        } catch (SQLException e) {
            AlertUtil.showError("Lỗi load chi tiết: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddToCart() {
        if (cart != null && book != null) {
            cart.addBook(book, 1);
            AlertUtil.showInfo("Đã thêm \"" + book.getTenSach() + "\" vào giỏ hàng!");
            handleClose();
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}