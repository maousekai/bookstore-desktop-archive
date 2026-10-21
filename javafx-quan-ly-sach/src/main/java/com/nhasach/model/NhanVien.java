package com.nhasach.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;


public class NhanVien {
    private final StringProperty maNV = new SimpleStringProperty();
    private final StringProperty TenNV = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> ngaySinh = new SimpleObjectProperty<>();
    private final StringProperty diaChi = new SimpleStringProperty();
    private final StringProperty dienThoai = new SimpleStringProperty();
    private final StringProperty taiKhoan = new SimpleStringProperty();
    private final StringProperty matKhau = new SimpleStringProperty(); // Chỉ dùng để truyền dữ liệu, không lưu trữ
    private final IntegerProperty maQuyen = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDate> ngayVaoLam = new SimpleObjectProperty<>();
    private final StringProperty viTri = new SimpleStringProperty();
    private final DoubleProperty luongCoBan = new SimpleDoubleProperty();
    // Getters and Setters
    public String getMaNV() { return maNV.get(); }
    public void setMaNV(String value) { maNV.set(value); }
    public StringProperty maNVProperty() { return maNV; }

    public String getTenNV() { return TenNV.get(); }
    public void setTenNV(String value) { TenNV.set(value); }
    public StringProperty TenNVProperty() { return TenNV; }
    
    public String getTaiKhoan() { return taiKhoan.get(); }
    public void setTaiKhoan(String value) { taiKhoan.set(value); }
    public StringProperty taiKhoanProperty() { return taiKhoan; }
    
	public String getMatKhau() { return matKhau.get(); }
	public void setMatKhau(String value) { matKhau.set(value); }
	public StringProperty matKhauProperty() { return matKhau; }
	
	public LocalDate getNgaySinh() { return ngaySinh.get(); }
	public void setNgaySinh(LocalDate value) { ngaySinh.set(value); }
	public ObjectProperty<LocalDate> ngaySinhProperty() { return ngaySinh; }

	public String getDiaChi() { return diaChi.get(); }
	public void setDiaChi(String value) { diaChi.set(value); }
	public StringProperty diaChiProperty() { return diaChi; }

	public String getDienThoai() { return dienThoai.get(); }
	public void setDienThoai(String value) { dienThoai.set(value); }
	public StringProperty dienThoaiProperty() { return dienThoai; }

	public int getMaQuyen() { return maQuyen.get(); }
	public void setMaQuyen(int value) { maQuyen.set(value); }
	public IntegerProperty maQuyenProperty() { return maQuyen; }

	public LocalDate getNgayVaoLam() { return ngayVaoLam.get(); }
	public void setNgayVaoLam(LocalDate string) { ngayVaoLam.set(string); }
	public ObjectProperty<LocalDate> ngayVaoLamProperty() { return ngayVaoLam; }

	public String getViTri() { return viTri.get(); }
	public void setViTri(String value) { viTri.set(value); }
	public StringProperty viTriProperty() { return viTri; }

	public double getLuongCoBan() { return luongCoBan.get(); }
	public void setLuongCoBan(double value) { luongCoBan.set(value); }
	public DoubleProperty luongCoBanProperty() { return luongCoBan; }
	
	


    
}