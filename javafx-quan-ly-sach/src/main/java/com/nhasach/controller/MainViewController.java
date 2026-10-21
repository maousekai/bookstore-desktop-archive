package com.nhasach.controller;

import java.io.IOException;
import com.nhasach.model.NhanVien;
import com.nhasach.util.NhaSachUtils.LoggedInUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.nhasach.dao.HoaDonDAO;



public class MainViewController {

    @FXML private BorderPane mainPane;
    @FXML private Label lblTenNhanVien;
    @FXML private Button btnQuanLySach;
    @FXML private Button btnQuanLyNhanVien;
    @FXML private Button btnBaoCao;
    @FXML private Button btnBanHang;
    @FXML private Button btnQuanLyHoaDon;
    @FXML private Button btnDangXuat;
    @FXML private Button btnQuanLyKhachHang;
    @FXML private Button btnDashboard, btnCategories, btnInventory;
   
    @FXML
    void showDashboard(ActionEvent event) {
        loadView("BaoCaoView.fxml");
    }

    @FXML
    void showCategories(ActionEvent event) {
        // Bạn sẽ cần tạo file FXML mới này
        loadView("QuanLyTheLoaiView.fxml");
    }

    @FXML
    void showInventory(ActionEvent event) {
         // Bạn sẽ cần tạo file FXML mới này
        loadView("QuanLyTonKhoView.fxml");
    }
    

    @FXML
    void initialize() {
        NhanVien currentUser = LoggedInUser.getInstance().getNhanVien();
        if (currentUser == null) {
            // Trường hợp không có người dùng đăng nhập, nên đóng lại và về màn hình login
            try {
                dangXuat(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        lblTenNhanVien.setText(currentUser.getTenNV());


        
        // Vai trò Quản lý (Admin)
        if (currentUser.getMaQuyen() == 1) {
            // Ẩn nút bán hàng
            btnBanHang.setVisible(false);
            btnBanHang.setManaged(false); // Xóa luôn không gian của nút
            
            btnQuanLyHoaDon.setVisible(true);
            btnQuanLyHoaDon.setManaged(true);
            btnQuanLyKhachHang.setVisible(true);
            btnQuanLyKhachHang.setManaged(true);

            // Hiển thị các nút quản lý
            btnQuanLySach.setVisible(true);
            btnQuanLyNhanVien.setVisible(true);
            btnBaoCao.setVisible(true);
            btnQuanLyNhanVien.setManaged(true);
            btnBaoCao.setManaged(true);
            

            // Tải giao diện quản lý sách làm mặc định
            loadView("QuanLySachView.fxml");
        } 
        // Vai trò Nhân viên
        else if (currentUser.getMaQuyen() == 2) {
            // Hiển thị nút bán hàng
            btnBanHang.setVisible(true);
            btnQuanLyHoaDon.setVisible(true);
            btnQuanLyHoaDon.setManaged(true);

            // Ẩn các nút quản lý
            btnQuanLySach.setVisible(true);
            btnQuanLySach.setManaged(true);
            btnQuanLyNhanVien.setVisible(false);
            btnQuanLyNhanVien.setManaged(false);
            btnBaoCao.setVisible(false);
            btnBaoCao.setManaged(false);

            // Tải giao diện bán hàng làm mặc định
            loadView("BanHangView.fxml");
        }
    }
    private void loadView(String fxmlFile) {
        try {
            // Luôn đảm bảo đường dẫn chính xác
            Parent view = FXMLLoader.load(getClass().getResource("/com/nhasach/view/" + fxmlFile));
            mainPane.setCenter(view);
        } catch (IOException e) {
            System.err.println("Không thể tải file FXML: " + fxmlFile);
            e.printStackTrace();
        }
    }

    @FXML
    void showBanHang(ActionEvent event) {
        // loadView("BanHangView.fxml");
        System.out.println("Chức năng Bán hàng sẽ được thêm ở đây");
        loadView("BanHangView.fxml");
    }
    @FXML
    private void showQuanLyKhachHang(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nhasach/view/QuanLyKhachHangView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Quản Lý Khách Hàng");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void showQuanLySach(ActionEvent event) {
        loadView("QuanLySachView.fxml");
    }

    @FXML
    void showQuanLyNhanVien(ActionEvent event) {
        loadView("QuanLyNhanVienView.fxml");
    }
    @FXML
    void showQuanLyHoaDon(ActionEvent event) {
        loadView("QuanLyHoaDonView.fxml");
    }
    @FXML
    void showBaoCao(ActionEvent event) {
        System.out.println("Chức năng Báo cáo sẽ được thêm ở đây");
        loadView("BaoCaoView.fxml");
    }
    
    @FXML
    void dangXuat(ActionEvent event) throws IOException {
        LoggedInUser.getInstance().clear();

        Stage currentStage = (Stage) mainPane.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nhasach/view/LoginView.fxml"));
        Parent root = loader.load();
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(root));
        loginStage.setTitle("Đăng nhập");
        loginStage.show();
    }
}
