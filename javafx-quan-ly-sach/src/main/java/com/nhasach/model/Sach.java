package com.nhasach.model;

import javafx.beans.property.*;

public class Sach {

    private final StringProperty maSach = new SimpleStringProperty();
    private final StringProperty tenSach = new SimpleStringProperty();
    private final DoubleProperty donGia = new SimpleDoubleProperty();
    private final IntegerProperty soLuong = new SimpleIntegerProperty();
    private final IntegerProperty namXuatBan = new SimpleIntegerProperty();
    private final StringProperty maNXB = new SimpleStringProperty();
    private final StringProperty Khuyenmai = new SimpleStringProperty();
    

    private final StringProperty viTriKe = new SimpleStringProperty();
    private final StringProperty viTriHang = new SimpleStringProperty();
    private final StringProperty viTriNgan = new SimpleStringProperty();

    // --- Các trường mới bổ sung ---
    private final StringProperty tacGia = new SimpleStringProperty();
    private final StringProperty theLoai = new SimpleStringProperty();
    private final StringProperty nhaXuatBan = new SimpleStringProperty();
    private final StringProperty moTa = new SimpleStringProperty();
    private final StringProperty anh = new SimpleStringProperty(); // tên file ảnh (VD: "sach1.png")

    // --- Getter / Setter / Property ---
    public String getMaSach() { return maSach.get(); }
    public void setMaSach(String value) { maSach.set(value); }
    public StringProperty maSachProperty() { return maSach; }

    public String getTenSach() { return tenSach.get(); }
    public void setTenSach(String value) { tenSach.set(value); }
    public StringProperty tenSachProperty() { return tenSach; }

    public double getDonGia() { return donGia.get(); }
    public void setDonGia(double value) { donGia.set(value); }
    public DoubleProperty donGiaProperty() { return donGia; }

    public int getSoLuong() { return soLuong.get(); }
    public void setSoLuong(int value) { soLuong.set(value); }
    public IntegerProperty soLuongProperty() { return soLuong; }

    public int getNamXuatBan() { return namXuatBan.get(); }
    public void setNamXuatBan(int value) { namXuatBan.set(value); }
    public IntegerProperty namXuatBanProperty() { return namXuatBan; }

    public String getMaNXB() { return maNXB.get(); }
    public void setMaNXB(String value) { maNXB.set(value); }
    public StringProperty maNXBProperty() { return maNXB; }

    public String getViTriKe() { return viTriKe.get(); }
    public void setViTriKe(String value) { viTriKe.set(value); }
    public StringProperty viTriKeProperty() { return viTriKe; }

    public String getViTriHang() { return viTriHang.get(); }
    public void setViTriHang(String value) { viTriHang.set(value); }
    public StringProperty viTriHangProperty() { return viTriHang; }

    public String getViTriNgan() { return viTriNgan.get(); }
    public void setViTriNgan(String value) { viTriNgan.set(value); }
    public StringProperty viTriNganProperty() { return viTriNgan; }

    // --- Thuộc tính thêm mới ---
    public String getTacGia() { return tacGia.get(); }
    public void setTacGia(String value) { tacGia.set(value); }
    public StringProperty tacGiaProperty() { return tacGia; }

    public String getTheLoai() { return theLoai.get(); }
    public void setTheLoai(String value) { theLoai.set(value); }
    public StringProperty theLoaiProperty() { return theLoai; }

    public String getNhaXuatBan() { return nhaXuatBan.get(); }
    public void setNhaXuatBan(String value) { nhaXuatBan.set(value); }
    public StringProperty nhaXuatBanProperty() { return nhaXuatBan; }

    public String getMoTa() { return moTa.get(); }
    public void setMoTa(String value) { moTa.set(value); }
    public StringProperty moTaProperty() { return moTa; }

    public String getAnh() { return anh.get(); }
    public void setAnh(String value) { anh.set(value); }
    public StringProperty anhBiaProperty() { return anh; }
    
    public String getKhuyenmai() { return Khuyenmai.get(); }
    public void setKhuyenmai(String value) { Khuyenmai.set(value); }
    public StringProperty khuyenmaiProperty() { return Khuyenmai; }

    @Override
    public String toString() {
        return tenSach.get();
    }
}
