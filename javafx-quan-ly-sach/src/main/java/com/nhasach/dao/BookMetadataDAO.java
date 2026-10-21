package com.nhasach.dao;

import com.nhasach.model.BookMetadata.NhaXuatBan;
import com.nhasach.model.BookMetadata;
import com.nhasach.model.BookMetadata.TacGia;
import com.nhasach.model.BookMetadata.TheLoai;
import com.nhasach.util.NhaSachUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BookMetadataDAO {
    // Để file này là public class chính


public static class NhaXuatBanDAO {
    public List<NhaXuatBan> getAll() {
        List<NhaXuatBan> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaXuatBan";
        try (Connection conn = NhaSachUtils.getConnection(); // Đã sửa
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                NhaXuatBan nxb = new NhaXuatBan(sql, sql, sql, sql);
                nxb.setMaNXB(rs.getString("MaNXB"));
                nxb.setTenNXB(rs.getString("TenNXB"));
                nxb.setDiaChi(rs.getString("DiaChi"));
                nxb.setDienThoai(rs.getString("DienThoai"));
                list.add(nxb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

/**
 * DAO cho Tác Giả
 * (Nguyên bản từ TacGiaDAO.java)
 */
public static class TacGiaDAO {
    public List<TacGia> getAll() throws Exception {
        List<TacGia> list = new ArrayList<>();
        String sql = "SELECT * FROM TacGia";
        try (Connection conn = NhaSachUtils.getConnection(); // Đã sửa
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new TacGia(rs.getString("MaTG"), rs.getString("TenTG")));
            }
        }
        return list;
    }
}

/**
 * DAO cho Thể Loại
 * (Nguyên bản từ TheLoaiDAO.java)
 */
public static class TheLoaiDAO {
    public List<TheLoai> getAll() throws Exception {
        List<TheLoai> list = new ArrayList<>();
        String sql = "SELECT * FROM TheLoai";
        try (Connection conn = NhaSachUtils.getConnection(); // Đã sửa
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new TheLoai(rs.getString("MaTL"), rs.getString("TenTL")));
            }
        }
        return list;
    }
}}