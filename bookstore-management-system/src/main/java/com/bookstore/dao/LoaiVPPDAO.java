package com.bookstore.dao;

import com.bookstore.model.LoaiVPP;
import com.bookstore.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiVPPDAO {
    // Lấy tất cả loại VPP
    public List<LoaiVPP> getAll() throws SQLException {
        List<LoaiVPP> list = new ArrayList<>();

        String sql = "SELECT MaLoaiVPP, TenLoai FROM LoaiVPP";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new LoaiVPP(
                    rs.getInt("MaLoaiVPP"),
                    rs.getString("TenLoai")
                ));
            }
        }
        return list;
    }

    public int insertAndGetId(LoaiVPP loai) throws SQLException {
        String sql = "INSERT INTO LoaiVPP (TenLoai) VALUES (?)";
        try (Connection conn = DatabaseManager.getConnection();
             // Thêm tham số Statement.RETURN_GENERATED_KEYS để lấy ID tự tăng
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, loai.getTenLoai());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) { 
                throw new SQLException("Thêm loại VPP thất bại, không có dòng nào thay đổi.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Trả về ID mới
                } else {
                    throw new SQLException("Thêm loại VPP thất bại, không lấy được ID.");
                }
            }
        }
    }
}