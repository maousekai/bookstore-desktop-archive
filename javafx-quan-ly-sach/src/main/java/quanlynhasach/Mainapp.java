package quanlynhasach;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mainapp extends Application {

    private final Label status = new Label("Status: not tested");

    public void start(Stage stage) {
        Button btn = new Button("Test DB Connection");
        btn.setOnAction(e -> testDb());

        VBox root = new VBox(10, status, btn);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root, 420, 120);

        stage.setTitle("JavaFX + SQL Server Test");
        stage.setScene(scene);
        stage.show();
    }

    private void testDb() {
        status.setText("Status: connecting...");
        new Thread(() -> {
            //THAY THÔNG SỐ KẾT NỐI Ở ĐÂY
        	String url = "jdbc:sqlserver://DESKTOP-JBDM2OH\\SQLEXPRESS;databaseName=QL_NhaSach;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String password = System.getenv("BOOKSTORE_DB_PASSWORD");
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                Platform.runLater(() -> status.setText("Status: Connected"));
            } catch (SQLException ex) {
                ex.printStackTrace();
                Platform.runLater(() -> status.setText("Status: Error: " + ex.getMessage()));
            }
        }).start();
    }

    public static void main(String[] args) {
        launch();
    }
}
