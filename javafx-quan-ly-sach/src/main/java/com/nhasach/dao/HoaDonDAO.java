package com.nhasach.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.nhasach.util.NhaSachUtils;
import com.nhasach.model.InvoiceModels.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class HoaDonDAO {
	public List<HoaDon> getAllInvoices() {
	    List<HoaDon> list = new ArrayList<>();
	    String sql = """
	        SELECT hd.MaHD, hd.NgayLap, hd.TongTien, nv.MaNV, nv.TenNV , hd.MaKH
	        FROM HoaDon hd
	        JOIN NhanVien nv ON hd.MaNV = nv.MaNV
	        ORDER BY hd.NgayLap DESC
	    """;

	    try (Connection conn = NhaSachUtils.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            String MaHD = rs.getString("MaHD");
	            Timestamp ts = rs.getTimestamp("NgayLap");
	            LocalDateTime NgayLap = (ts != null) ? ts.toLocalDateTime() : null;
	            double TongTien = rs.getDouble("TongTien");
	            String MaNV = rs.getString("MaNV");
	            String TenNV = rs.getString("TenNV");
	            String MaKH = rs.getString("MaKH");

	            list.add(new HoaDon(MaHD, NgayLap, TongTien, MaNV, TenNV, MaKH));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
    
    // Hàm này sử dụng transaction để đảm bảo tính toàn vẹn dữ liệu
    public boolean createInvoice(String MaHD, String MaNV, double TongTien, List<ChiTietHoaDon> gioHang) throws SQLException, ClassNotFoundException {
        Connection conn = NhaSachUtils.getConnection();
        conn.setAutoCommit(false); // Bắt đầu transaction

        String sqlHoaDon = "INSERT INTO HoaDon (MaHD, NgayLap, TongTien, MaNV ,MaKH) VALUES (?, ?, ?, ?, ?)";
        String sqlChiTiet = "INSERT INTO ChiTietHoaDon (MaHD, MaSach, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdateKho = "UPDATE Sach SET SoLuong = SoLuong - ? WHERE MaSach = ?";

        try (PreparedStatement psHD = conn.prepareStatement(sqlHoaDon);
             PreparedStatement psCT = conn.prepareStatement(sqlChiTiet);
             PreparedStatement psKho = conn.prepareStatement(sqlUpdateKho)) {

            // Thêm vào bảng HoaDon
            psHD.setString(1, MaHD);
            psHD.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            psHD.setDouble(3, TongTien);
            psHD.setString(4, MaNV);
            psHD.executeUpdate();

            // Thêm vào bảng ChiTietHoaDon và cập nhật kho
            for (ChiTietHoaDon item : gioHang) {
                // Thêm chi tiết
                psCT.setString(1, MaHD);
                psCT.setString(2, item.getMaSach());
                psCT.setInt(3, item.getSoLuong());
                psCT.setDouble(4, item.getDonGia());
                psCT.setDouble(5, item.getThanhTien());
                psCT.addBatch();

                // Cập nhật kho
                psKho.setInt(1, item.getSoLuong());
                psKho.setString(2, item.getMaSach());
                psKho.addBatch();
            }
            psCT.executeBatch();
            psKho.executeBatch();

            conn.commit(); // Hoàn tất, lưu tất cả thay đổi
            return true;

        } catch (SQLException e) {
            conn.rollback(); // Nếu có lỗi, hủy bỏ tất cả thay đổi
            e.printStackTrace();
            return false;
        } finally {
            conn.setAutoCommit(true); // Trả lại chế độ tự động commit
        }
    }

    public Map<String, Double> getMonthlyRevenue(int year) {
        Map<String, Double> revenueData = new HashMap<>();
        String sql = "SELECT MONTH(NgayLap) as Thang, SUM(TongTien) as DoanhThu " +
                     "FROM HoaDon WHERE YEAR(NgayLap) = ? " +
                     "GROUP BY MONTH(NgayLap) ORDER BY Thang";
        try (Connection conn = NhaSachUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String month = "Tháng " + rs.getInt("Thang");
                double revenue = rs.getDouble("DoanhThu");
                revenueData.put(month, revenue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return revenueData;
    }
}