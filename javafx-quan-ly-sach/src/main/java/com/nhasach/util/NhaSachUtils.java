package com.nhasach.util;

import com.nhasach.model.NhanVien;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * File này gộp chung 4 lớp tiện ích.
 * 1. DatabaseConnection (lớp gốc)
 * 2. AlertUtil (lớp con tĩnh)
 * 3. LoggedInUser (lớp con tĩnh)
 * 4. PasswordUtil (lớp con tĩnh)
 */
public class NhaSachUtils {

    // --- Logic của DatabaseConnection ---
    private static final String URL = "jdbc:sqlserver://DESKTOP-JBDM2OH\\SQLEXPRESS;databaseName=QL_NhaSach;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = System.getenv("BOOKSTORE_DB_PASSWORD");

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Kết nối database thành công!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Không tìm thấy driver JDBC SQL Server!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi SQL khi kết nối: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lớp tiện ích để hiển thị các hộp thoại thông báo
     * (Nguyên bản từ AlertUtil.java)
     */
    public static class AlertUtil {
        public static void showError(String title, String header, String content) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }

        public static void showInfo(String title, String header, String content) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }
    }

    /**
     * Lớp này dùng kỹ thuật Singleton để lưu thông tin người dùng đã đăng nhập
     * (Nguyên bản từ LoggedInUser.java)
     */
    public static class LoggedInUser {
        private static LoggedInUser instance;
        private NhanVien nhanVien;

        private LoggedInUser() {}

        public static LoggedInUser getInstance() {
            if (instance == null) {
                instance = new LoggedInUser();
            }
            return instance;
        }

        public NhanVien getNhanVien() {
            return nhanVien;
        }

        public void setNhanVien(NhanVien nhanVien) {
            this.nhanVien = nhanVien;
        }

        public void clear() {
            this.nhanVien = null;
        }
    }

    /**
     * Lớp tiện ích để mã hóa và xác thực mật khẩu
     * (Nguyên bản từ PasswordUtil.java)
     */
    public static class PasswordUtil {
        public static String hashPassword(String plainPassword) {
            return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
        }

        public static boolean verifyPassword(String plainPassword, String storedPassword) {
            try {
                if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$")) {
                    return BCrypt.checkpw(plainPassword, storedPassword);
                } else {
                    return plainPassword.equals(storedPassword);
                }
            } catch (Exception e) {
                return false;
            }
        }
    }
}