package com.nhasach.model;

import javafx.beans.property.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// File này gộp 2 file: HoaDon.java, ChiTietHoaDon.java

/**
 * Model cho Hóa Đơn
 * (Nguyên bản từ HoaDon.java)
 */
public class InvoiceModels {
    // Để file này là public class chính


public static class HoaDon {
    private StringProperty maHD;
    private ObjectProperty<LocalDateTime> ngayLap;
    private DoubleProperty tongTien;
    private StringProperty maNV;
    private StringProperty tenNV;
    private StringProperty maKH;

    public HoaDon(String maHD, LocalDateTime ngayLap, double tongTien, String maNV, String tenNV , String maKH) {
        this.maHD = new SimpleStringProperty(maHD);
        this.ngayLap = new SimpleObjectProperty<>(ngayLap);
        this.tongTien = new SimpleDoubleProperty(tongTien);
        this.maNV = new SimpleStringProperty(maNV);
        this.tenNV = new SimpleStringProperty(tenNV);
        this.maKH = new SimpleStringProperty();
    }

    public String getMaHD() { return maHD.get(); }
    public void setMaHD(String value) { maHD.set(value); }
    public StringProperty maHDProperty() { return maHD; }

    public LocalDateTime getNgayLap() { return ngayLap.get(); }
    public void setNgayLap(LocalDateTime value) { ngayLap.set(value); }
    public ObjectProperty<LocalDateTime> ngayLapProperty() { return ngayLap; }

    public double getTongTien() { return tongTien.get(); }
    public void setTongTien(double value) { tongTien.set(value); }
    public DoubleProperty tongTienProperty() { return tongTien; }

    public String getMaNV() { return maNV.get(); }
    public void setMaNV(String value) { maNV.set(value); }
    public StringProperty maNVProperty() { return maNV; }

    public String getTenNV() { return tenNV.get(); }
    public void setTenNV(String value) { tenNV.set(value); }
    public StringProperty tenNVProperty() { return tenNV; }
    
    public String getMaKH() { return maKH.get(); }
    public void setMaKH(String value) { maKH.set(value); }
    public StringProperty maKHProperty() { return maKH; }
    

    public StringProperty ngayLapStringProperty() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formatted = ngayLap.get() != null ? ngayLap.get().format(formatter) : "";
        return new SimpleStringProperty(formatted);
        
    }
}

/**
 * Model cho Chi Tiết Hóa Đơn (dùng cho giỏ hàng)
 * (Nguyên bản từ ChiTietHoaDon.java)
 */
public static class ChiTietHoaDon {
    private final StringProperty maSach = new SimpleStringProperty();
    private final StringProperty tenSach = new SimpleStringProperty();
    private final IntegerProperty soLuong = new SimpleIntegerProperty();
    private final DoubleProperty donGia = new SimpleDoubleProperty();
    private final DoubleProperty thanhTien = new SimpleDoubleProperty();

    public ChiTietHoaDon(Sach sach, int soLuong) {
        setMaSach(sach.getMaSach());
        setTenSach(sach.getTenSach());
        setDonGia(sach.getDonGia());
        setSoLuong(soLuong);
    }

    // Getters, Setters, và Properties
    public String getMaSach() { return maSach.get(); }
    public void setMaSach(String value) { maSach.set(value); }
    public StringProperty maSachProperty() { return maSach; }
    
    public String getTenSach() { return tenSach.get(); }
    public void setTenSach(String value) { tenSach.set(value); }
    public StringProperty tenSachProperty() { return tenSach; }

    public int getSoLuong() { return soLuong.get(); }
    public void setSoLuong(int value) {
        soLuong.set(value);
        thanhTien.set(value * getDonGia()); // Tự động cập nhật thành tiền
    }
    public IntegerProperty soLuongProperty() { return soLuong; }

    public double getDonGia() { return donGia.get(); }
    public void setDonGia(double value) { donGia.set(value); }
    public DoubleProperty donGiaProperty() { return donGia; }

    public double getThanhTien() { return thanhTien.get(); }
    public DoubleProperty thanhTienProperty() { return thanhTien; }
}}