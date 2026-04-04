package com.mycompany.cnpm_n1.model;

public class Phong {
    private int id;
    private int toaNhaId;
    private int loaiPhongId;
    private String soPhong;
    private int tang;
    private int soNguoi;
    private String trangThai;

    // Constructor mặc định
    public Phong() {
    }

    // Constructor đầy đủ (với id) — dùng khi load từ DB
    public Phong(int id, int toaNhaId, int loaiPhongId, String soPhong,
                 int tang, int soNguoi, String trangThai) {
        this.id          = id;
        this.toaNhaId    = toaNhaId;
        this.loaiPhongId = loaiPhongId;
        this.soPhong     = soPhong;
        this.tang        = tang;
        this.soNguoi     = soNguoi;
        this.trangThai   = trangThai;
    }

    // Constructor không có id — dùng khi thêm mới
    public Phong(int toaNhaId, int loaiPhongId, String soPhong,
                 int tang, int soNguoi, String trangThai) {
        this.toaNhaId    = toaNhaId;
        this.loaiPhongId = loaiPhongId;
        this.soPhong     = soPhong;
        this.tang        = tang;
        this.soNguoi     = soNguoi;
        this.trangThai   = trangThai;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToaNhaId() {
        return toaNhaId;
    }

    public void setToaNhaId(int toaNhaId) {
        this.toaNhaId = toaNhaId;
    }

    public int getLoaiPhongId() {
        return loaiPhongId;
    }

    public void setLoaiPhongId(int loaiPhongId) {
        this.loaiPhongId = loaiPhongId;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    public int getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "Phòng " + soPhong;
    }
}