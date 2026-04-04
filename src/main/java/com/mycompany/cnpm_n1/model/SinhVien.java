package com.mycompany.cnpm_n1.model;

import java.time.LocalDate;

public class SinhVien {
    private int id;
    private Integer taiKhoanId;
    private String maSinhVien;
    private String hoTen;
    private String gioiTinh;
    private LocalDate ngaySinh;
    private String soDienThoai;
    private String truong;

    // Constructor mặc định
    public SinhVien() {
    }

    // Constructor đầy đủ (với id) — dùng khi load từ DB
    public SinhVien(int id, Integer taiKhoanId, String maSinhVien, String hoTen,
                    String gioiTinh, LocalDate ngaySinh, String soDienThoai, String truong) {
        this.id          = id;
        this.taiKhoanId  = taiKhoanId;
        this.maSinhVien  = maSinhVien;
        this.hoTen       = hoTen;
        this.gioiTinh    = gioiTinh;
        this.ngaySinh    = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.truong      = truong;
    }

    // Constructor không có id — dùng khi thêm mới
    public SinhVien(String maSinhVien, String hoTen, String gioiTinh,
                    LocalDate ngaySinh, String soDienThoai, String truong) {
        this.maSinhVien  = maSinhVien;
        this.hoTen       = hoTen;
        this.gioiTinh    = gioiTinh;
        this.ngaySinh    = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.truong      = truong;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTaiKhoanId() {
        return taiKhoanId;
    }

    public void setTaiKhoanId(Integer taiKhoanId) {
        this.taiKhoanId = taiKhoanId;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getTruong() {
        return truong;
    }

    public void setTruong(String truong) {
        this.truong = truong;
    }

    @Override
    public String toString() {
        return maSinhVien + " - " + hoTen;
    }
}