package com.nhasach.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nhasach/view/LoginView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/nhasach/view/style.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Đăng nhập hệ thống quản lý nhà sách");

            // 🌤 Giao diện sáng hiện đại - icon + kích thước cố định
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/nhasach/view/images/logo.png")));
            primaryStage.setResizable(false); // không cho resize để layout không vỡ
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
