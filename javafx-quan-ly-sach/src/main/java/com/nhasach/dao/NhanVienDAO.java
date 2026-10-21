package com.nhasach.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.nhasach.model.NhanVien;
import com.nhasach.util.NhaSachUtils.*;
import com.nhasach.util.NhaSachUtils;

public class NhanVienDAO {
    
    public NhanVien findByTaiKhoan(String taiKhoan) throws Exception {
        String sql = "SELECT * FROM NhanVien WHERE TaiKhoan = ?";
        try (Connection conn = NhaSachUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, taiKhoan);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setTaiKhoan(rs.getString("TaiKhoan"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setMaQuyen(rs.getInt("MaQuyen"));
                nv.setDienThoai(rs.getString("DienThoai"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setLuongCoBan(rs.getDouble("LuongCoBan"));
                nv.setNgayVaoLam(rs.getDate("NgayVaoLam").toLocalDate());
                nv.setNgaySinh(rs.getDate("NgaySinh").toLocalDate());
                nv.setViTri(rs.getString("ViTri"));
                

               
                return nv;
            }
        }
        return null;
    }
    
    public List<NhanVien> getAll() throws Exception {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";  // lấy đủ cột
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setTaiKhoan(rs.getString("TaiKhoan"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setMaQuyen(rs.getInt("MaQuyen"));
                nv.setDienThoai(rs.getString("DienThoai"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setLuongCoBan(rs.getDouble("LuongCoBan"));
                nv.setNgayVaoLam(rs.getDate("NgayVaoLam").toLocalDate());
                nv.setNgaySinh(rs.getDate("NgaySinh").toLocalDate());
                nv.setViTri(rs.getString("ViTri"));

                

                list.add(nv);
            }
        }
        return list;
    }
    
    public boolean add(NhanVien nv) throws Exception {
        String sql = """
            INSERT INTO NhanVien (
                MaNV, TenNV, TaiKhoan, MatKhau, MaQuyen,
                DienThoai, DiaChi, LuongCoBan, NgayVaoLam, NgaySinh, ViTri
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?)
        """;

        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nv.getMaNV());
            pstmt.setString(2, nv.getTenNV());
            pstmt.setString(3, nv.getTaiKhoan());
            pstmt.setString(4, PasswordUtil.hashPassword(nv.getMatKhau())); // mã hóa mật khẩu
            pstmt.setInt(5, nv.getMaQuyen());
            pstmt.setString(6, nv.getDienThoai());
            pstmt.setString(7, nv.getDiaChi());
            pstmt.setDouble(8, nv.getLuongCoBan());
            pstmt.setDate(9, nv.getNgayVaoLam() != null ? Date.valueOf(nv.getNgayVaoLam()) : null);
            pstmt.setDate(10, nv.getNgaySinh() != null ? Date.valueOf(nv.getNgaySinh()) : null);
            pstmt.setString(11, nv.getViTri());

            return pstmt.executeUpdate() > 0;
        }
    }

    public NhanVien login(String taiKhoan, String matKhau) throws Exception {
        NhanVien nv = findByTaiKhoan(taiKhoan);
        if (nv != null && PasswordUtil.verifyPassword(matKhau, nv.getMatKhau())) {
            return nv; // đúng mật khẩu
        }
        return null; // sai
    }
    public boolean update(NhanVien nv) throws SQLException, ClassNotFoundException {
        // Nếu mật khẩu được cung cấp, câu lệnh SQL sẽ bao gồm cả việc cập nhật mật khẩu
        boolean isPasswordProvided = nv.getMatKhau() != null && !nv.getMatKhau().isEmpty();
        String sql = "UPDATE NhanVien SET HoTen = ?, TaiKhoan = ?, MaQuyen = ?" 
                   + (isPasswordProvided ? ", MatKhau = ? " : " ") 
                   + "WHERE MaNV = ?";
        
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nv.getTenNV());
            pstmt.setString(2, nv.getTaiKhoan());
            pstmt.setInt(3, nv.getMaQuyen());
            
            
            if (isPasswordProvided) {
                pstmt.setString(4, PasswordUtil.hashPassword(nv.getMatKhau()));
                pstmt.setString(5, nv.getMaNV());
            } else {
                pstmt.setString(4, nv.getMaNV());
            }
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean delete(String maNV) throws SQLException, ClassNotFoundException {
        // Lưu ý: Trong hệ thống thực tế, bạn nên kiểm tra các ràng buộc khóa ngoại
        // (ví dụ: nhân viên đã lập hóa đơn nào chưa) trước khi xóa.
        String sql = "DELETE FROM NhanVien WHERE MaNV = ?";
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maNV);
            return pstmt.executeUpdate() > 0;
        }
    }
}

    

