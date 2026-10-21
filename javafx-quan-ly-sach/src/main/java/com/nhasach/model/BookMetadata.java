package com.nhasach.model;

// File này gộp 3 file: NhaXuatBan.java, TacGia.java, TheLoai.java

/**
 * Model cho Nhà Xuất Bản
 * (Nguyên bản từ NhaXuatBan.java)
 */
public class BookMetadata {
    // Để file này là public class chính


public static class NhaXuatBan {
    private String MaNXB;
    private String tenNXB;
    private String DiaChi;
    private String DienThoai;

    public NhaXuatBan(String MaNXB, String tenNXB, String DiaChi, String DienThoai) {
        this.MaNXB = MaNXB;
        this.tenNXB = tenNXB;
        this.DiaChi = DiaChi;
        this.DienThoai = DienThoai;
    }
    
    // Getters and Setters
    public String getMaNXB() { return MaNXB; }
    public void setMaNXB(String maNXB) { this.MaNXB = maNXB; }
    public String getTenNXB() { return tenNXB; }
    public void setTenNXB(String tenNXB) { this.tenNXB = tenNXB; }
    public String getDiaChi() { return DiaChi; }
    public void setDiaChi(String diaChi) { DiaChi = diaChi; }
    public String getDienThoai() { return DienThoai; }
    public void setDienThoai(String dienThoai) { DienThoai = dienThoai; }
    
    @Override
    public String toString() {
        return this.tenNXB;
    }
}

/**
 * Model cho Tác Giả
 * (Nguyên bản từ TacGia.java)
 */
public static class TacGia {
    private String maTG;
    private String tenTG;

    public TacGia(String maTG, String tenTG) {
        this.maTG = maTG;
        this.tenTG = tenTG;
    }

    // Getters and Setters
    public String getMaTG() { return maTG; }
    public void setMaTG(String maTG) { this.maTG = maTG; }
    public String getTenTG() { return tenTG; }
    public void setTenTG(String tenTG) { this.tenTG = tenTG; }

    @Override
    public String toString() {
        return this.tenTG; 
    }
}

/**
 * Model cho Thể Loại
 * (Nguyên bản từ TheLoai.java)
 */
public static class TheLoai {
    private String maTL;
    private String tenTL;

    public TheLoai(String maTL, String tenTL) {
        this.maTL = maTL;
        this.tenTL = tenTL;
    }

    // Getters and Setters
    public String getMaTL() { return maTL; }
    public void setMaTL(String maTL) { this.maTL = maTL; }
    public String getTenTL() { return tenTL; }
    public void setTenTL(String tenTL) { this.tenTL = tenTL; }

    @Override
    public String toString() {
        return this.tenTL; // Quan trọng để ComboBox hiển thị tên
    }
}}