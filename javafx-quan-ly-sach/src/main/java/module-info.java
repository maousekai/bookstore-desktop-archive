module com.example.JavaFXquanlysach {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;

    // xuất các package chính để bên ngoài (nếu cần import)
    exports com.nhasach.application;
    exports com.nhasach.controller;
    exports com.nhasach.dao;
    exports com.nhasach.model;
    exports quanlynhasach;

    // mở cho JavaFX truy cập reflection
    opens com.nhasach.controller to javafx.fxml;
    opens com.nhasach.model to javafx.base; // cho TableView đọc property trong model
}
