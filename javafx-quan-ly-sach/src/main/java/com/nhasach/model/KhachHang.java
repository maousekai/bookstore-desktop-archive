package com.nhasach.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KhachHang {
    private StringProperty maKH;
    private StringProperty hoTen;
    private StringProperty dienThoai;
    private StringProperty email;
    private StringProperty diaChi;

    public KhachHang(String maKH, String hoTen, String dienThoai, String email, String diaChi) {
        this.maKH = new SimpleStringProperty(maKH);
        this.hoTen = new SimpleStringProperty(hoTen);
        this.dienThoai = new SimpleStringProperty(dienThoai);
        this.email = new SimpleStringProperty(email);
        this.diaChi = new SimpleStringProperty(diaChi);
    }

    public String getMaKH() { return maKH.get(); }
    public void setMaKH(String value) { maKH.set(value); }
    public StringProperty maKHProperty() { return maKH; }

    public String getHoTen() { return hoTen.get(); }
    public void setHoTen(String value) { hoTen.set(value); }
    public StringProperty hoTenProperty() { return hoTen; }

    public String getDienThoai() { return dienThoai.get(); }
    public void setDienThoai(String value) { dienThoai.set(value); }
    public StringProperty dienThoaiProperty() { return dienThoai; }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }
    public StringProperty emailProperty() { return email; }

    public String getDiaChi() { return diaChi.get(); }
    public void setDiaChi(String value) { diaChi.set(value); }
    public StringProperty diaChiProperty() { return diaChi; }
}
