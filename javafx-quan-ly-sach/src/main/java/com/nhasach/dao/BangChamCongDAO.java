package com.nhasach.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.nhasach.util.NhaSachUtils;
import com.nhasach.util.NhaSachUtils.AlertUtil;

public class BangChamCongDAO {
    
    public void ghiNhanDangNhap(String maNV) {
        String sql = "INSERT INTO BangChamCong(MaNV, MaCa, NgayChamCong, GioVao) VALUES (?, 1, CAST(GETDATE() AS DATE), ?)";
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, maNV);
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
            System.out.println("Đã chấm công cho nhân viên: " + maNV);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
