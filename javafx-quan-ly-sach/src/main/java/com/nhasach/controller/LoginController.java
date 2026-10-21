package com.nhasach.controller;

import java.io.IOException;
import com.nhasach.application.Main;
import com.nhasach.dao.BangChamCongDAO;
import com.nhasach.dao.NhanVienDAO;
import com.nhasach.model.NhanVien;
import com.nhasach.util.NhaSachUtils.LoggedInUser;
import com.nhasach.util.NhaSachUtils.PasswordUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {

    @FXML private PasswordField txtPassword;
    @FXML private TextField txtUsername;
    @FXML private Label lblStatus;

    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private BangChamCongDAO chamCongDAO = new BangChamCongDAO();

    @FXML
    void loginButtonAction(ActionEvent event) {
        try {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            if (username.isEmpty() || password.isEmpty()) {
                lblStatus.setText("Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            NhanVien nv = nhanVienDAO.findByTaiKhoan(username);

            if (nv == null) {
                lblStatus.setText("Tài khoản không tồn tại!");
            } else {
            	if (PasswordUtil.verifyPassword(password, nv.getMatKhau())) {
            	    lblStatus.setText("Đăng nhập thành công!");
            	    lblStatus.setTextFill(Color.GREEN);

            	    LoggedInUser.getInstance().setNhanVien(nv);
            	    chamCongDAO.ghiNhanDangNhap(nv.getMaNV());
            	    openMainView();
            	} else {
            	    lblStatus.setText("Mật khẩu không chính xác!");
            	}
            }
        } catch (Exception e) {
            lblStatus.setText("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void openMainView() throws IOException {
        // Đóng cửa sổ đăng nhập hiện tại
        Stage currentStage = (Stage) lblStatus.getScene().getWindow();
        currentStage.close();

        // Mở cửa sổ chính
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nhasach/view/MainView.fxml"));
        Parent root = loader.load();
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("Hệ thống Quản lý Nhà sách");
        mainStage.setMaximized(true);
        mainStage.show();
    }
}
