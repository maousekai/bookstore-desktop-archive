package com.nhasach.dao;

import com.nhasach.model.KhachHang;
import com.nhasach.util.NhaSachUtils.*;
import com.nhasach.util.NhaSachUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> list = new ArrayList<>();

        String sql = "SELECT MaKH, HoTen, DienThoai, Email, DiaChi FROM KhachHang";

        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new KhachHang(
                        rs.getString("MaKH"),
                        rs.getString("HoTen"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi")
                ));
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang (MaKH, HoTen, DienThoai, Email, DiaChi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getHoTen());
            stmt.setString(3, kh.getDienThoai());
            stmt.setString(4, kh.getEmail());
            stmt.setString(5, kh.getDiaChi());
            return stmt.executeUpdate() > 0;

        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET HoTen = ?, DienThoai = ?, Email = ?, DiaChi = ? WHERE MaKH = ?";
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kh.getHoTen());
            stmt.setString(2, kh.getDienThoai());
            stmt.setString(3, kh.getEmail());
            stmt.setString(4, kh.getDiaChi());
            stmt.setString(5, kh.getMaKH());
            return stmt.executeUpdate() > 0;

        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteKhachHang(String maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maKH);
            return stmt.executeUpdate() > 0;

        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return false;
    }
}
