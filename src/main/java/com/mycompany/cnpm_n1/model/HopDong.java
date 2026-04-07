package com.mycompany.cnpm_n1.model;

import java.time.LocalDate;

public class HopDong {
    private int id;
    private int sinhVienId;
    private int phongId;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String trangThai; // hieu_luc, het_han, huy

    // Constructor mặc định
    public HopDong() {
    }

    // Constructor đầy đủ (với id)
    public HopDong(int id, int sinhVienId, int phongId, LocalDate ngayBatDau,
                   LocalDate ngayKetThuc, String trangThai) {
        this.id = id;
        this.sinhVienId = sinhVienId;
        this.phongId = phongId;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    // Constructor không có id
    public HopDong(int sinhVienId, int phongId, LocalDate ngayBatDau,
                   LocalDate ngayKetThuc, String trangThai) {
        this.sinhVienId = sinhVienId;
        this.phongId = phongId;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSinhVienId() {
        return sinhVienId;
    }

    public void setSinhVienId(int sinhVienId) {
        this.sinhVienId = sinhVienId;
    }

    public int getPhongId() {
        return phongId;
    }

    public void setPhongId(int phongId) {
        this.phongId = phongId;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "HopDong{" +
                "id=" + id +
                ", sinhVienId=" + sinhVienId +
                ", phongId=" + phongId +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
