package com.nhasach.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.nhasach.model.Sach;
import com.nhasach.util.NhaSachUtils.*;
import com.nhasach.model.BookMetadata.*;
import com.nhasach.model.Sach;
import com.nhasach.util.NhaSachUtils;


public class SachDAO {
	public boolean update(Sach sach, List<String> maTacGiaList, List<String> maTheLoaiList) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            conn = NhaSachUtils.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Cập nhật bảng Sach
            String sqlSach = "UPDATE Sach SET MaSach =? ,TenSach=?, DonGia=?, SoLuong=?, Anh=?, MaNXB=?, NamXuatBan=?, ViTri_Ke=?, ViTri_Hang=?, ViTri_Ngan=? WHERE MaSach=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlSach)) {
            	pstmt.setString(1, sach.getMaSach());
                pstmt.setString(2, sach.getTenSach());
                pstmt.setDouble(3, sach.getDonGia());
                pstmt.setInt(4, sach.getSoLuong());
                pstmt.setString(5, sach.getAnh());
                pstmt.setString(6, sach.getMaNXB());
                pstmt.setInt(7, sach.getNamXuatBan());
                pstmt.setString(8, sach.getViTriKe());
                pstmt.setString(9, sach.getViTriHang());
                pstmt.setString(10, sach.getViTriNgan());
                pstmt.setString(11, sach.getMaSach());
                
                pstmt.executeUpdate();
            }

            // 2. Xóa các liên kết cũ trong bảng trung gian
            try (PreparedStatement pstmtDelTG = conn.prepareStatement("DELETE FROM Sach_TacGia WHERE MaSach = ?");
                 PreparedStatement pstmtDelTL = conn.prepareStatement("DELETE FROM Sach_TheLoai WHERE MaSach = ?")) {
                pstmtDelTG.setString(1, sach.getMaSach());
                pstmtDelTG.executeUpdate();
                pstmtDelTL.setString(1, sach.getMaSach());
                pstmtDelTL.executeUpdate();
                
            }

            String sqlTacGia = "INSERT INTO Sach_TacGia(MaSach, MaTG) VALUES(?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlTacGia)) {
                if (maTacGiaList != null) { // ✅ tránh NullPointer
                    for (String maTG : maTacGiaList) {
                        pstmt.setString(1, sach.getMaSach());
                        pstmt.setString(2, maTG);
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
            }

            String sqlTheLoai = "INSERT INTO Sach_TheLoai(MaSach, MaTL) VALUES(?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlTheLoai)) {
                if (maTheLoaiList != null) {
                    for (String maTL : maTheLoaiList) {
                        pstmt.setString(1, sach.getMaSach());
                        pstmt.setString(2, maTL);
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
            }

            conn.commit(); // Hoàn thành transaction
            return true;
        } catch (SQLException e) {
            if (conn != null) conn.rollback(); // Rollback nếu có lỗi
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    public boolean delete(String maSach) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            conn = NhaSachUtils.getConnection();
            conn.setAutoCommit(false);

            // Xóa các bản ghi tham chiếu trước để tránh lỗi khóa ngoại
            String[] deleteSQLs = {
                "DELETE FROM ChiTietHoaDon WHERE MaSach = ?",
                "DELETE FROM Sach_TacGia WHERE MaSach = ?",
                "DELETE FROM Sach_TheLoai WHERE MaSach = ?",
                "DELETE FROM Sach WHERE MaSach = ?"
            };

            for (String sql : deleteSQLs) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, maSach);
                    pstmt.executeUpdate();
                }
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    public List<Sach> getAll() throws Exception {
        List<Sach> list = new ArrayList<>();
        String sql = "SELECT * FROM Sach";
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Sach s = new Sach();
                s.setMaSach(rs.getString("MaSach"));
                s.setTenSach(rs.getString("TenSach"));
                s.setDonGia(rs.getDouble("DonGia"));
                s.setSoLuong(rs.getInt("SoLuong"));
                s.setAnh(rs.getString("Anh"));
                s.setMaNXB(rs.getString("MaNXB"));
                
                list.add(s);
            }
        }
        return list;
    }
    public List<Sach> searchByName(String keyword) {
        List<Sach> list = new ArrayList<>();
        String sql = "SELECT * FROM Sach WHERE TenSach LIKE ?";
        
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Sach s = new Sach();
                s.setMaSach(rs.getString("MaSach"));
                s.setTenSach(rs.getString("TenSach"));
                s.setDonGia(rs.getDouble("DonGia"));
                s.setSoLuong(rs.getInt("SoLuong"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public Sach findById(String maSach) throws SQLException, ClassNotFoundException {
        Sach sach = null;
        String sql = "SELECT * FROM Sach WHERE MaSach = ?";
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maSach);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                sach = new Sach();
                sach.setMaSach(rs.getString("MaSach"));
                sach.setTenSach(rs.getString("TenSach"));
                sach.setDonGia(rs.getDouble("DonGia"));
                sach.setSoLuong(rs.getInt("SoLuong"));
                sach.setNamXuatBan(rs.getInt("NamXuatBan"));
                sach.setAnh(rs.getString("Anh"));
                sach.setKhuyenmai(rs.getString("KhuyenMai"));
                sach.setViTriKe(rs.getString("ViTri_Ke"));
                sach.setViTriHang(rs.getString("ViTri_Hang"));
                sach.setViTriNgan(rs.getString("ViTri_Ngan"));
                sach.setMaNXB(rs.getString("MaNXB"));
            }
        }
        return sach;
    }

    
    public boolean add(Sach sach) throws SQLException, ClassNotFoundException {
        // Hàm này giờ chỉ thêm vào bảng Sach, việc liên kết tác giả/thể loại sẽ làm riêng
        String sql = "INSERT INTO Sach(MaSach, TenSach, DonGia, SoLuong, NamXuatBan, Anh, KhuyenMai, ViTri_Ke, ViTri_Hang, ViTri_Ngan, MaNXB) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, sach.getMaSach());
            pstmt.setString(2, sach.getTenSach());
            pstmt.setDouble(3, sach.getDonGia());
            pstmt.setInt(4, sach.getSoLuong());
            pstmt.setInt(5, sach.getNamXuatBan());
            pstmt.setString(6, sach.getAnh());
            pstmt.setString(7, sach.getKhuyenmai());
            pstmt.setString(8, sach.getViTriKe());
            pstmt.setString(9, sach.getViTriHang());
            pstmt.setString(10, sach.getViTriNgan());
            pstmt.setString(11, sach.getMaNXB());
            
            
            return pstmt.executeUpdate() > 0;
        }
    }
    // TODO: Viết các hàm update và delete với logic transaction tương tự
}
