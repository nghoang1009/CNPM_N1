package com.mycompany.cnpm_n1.model;

import java.time.LocalDate;
import java.math.BigDecimal;

public class HoaDon {
    private int id;
    private int hopDongId;
    private int sinhVienId;
    private int phongId;
    private LocalDate thang;
    private BigDecimal tienPhong;
    private BigDecimal tienDien;
    private BigDecimal tienNuoc;
    private BigDecimal tongTien;
    private String trangThai; // chua_tra, da_tra

    // Constructor mặc định
    public HoaDon() {
    }

    // Constructor đầy đủ (với id)
    public HoaDon(int id, int hopDongId, int sinhVienId, int phongId, LocalDate thang,
                  BigDecimal tienPhong, BigDecimal tienDien, BigDecimal tienNuoc,
                  BigDecimal tongTien, String trangThai) {
        this.id = id;
        this.hopDongId = hopDongId;
        this.sinhVienId = sinhVienId;
        this.phongId = phongId;
        this.thang = thang;
        this.tienPhong = tienPhong;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    // Constructor không có id
    public HoaDon(int hopDongId, int sinhVienId, int phongId, LocalDate thang,
                  BigDecimal tienPhong, BigDecimal tienDien, BigDecimal tienNuoc,
                  BigDecimal tongTien, String trangThai) {
        this.hopDongId = hopDongId;
        this.sinhVienId = sinhVienId;
        this.phongId = phongId;
        this.thang = thang;
        this.tienPhong = tienPhong;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHopDongId() {
        return hopDongId;
    }

    public void setHopDongId(int hopDongId) {
        this.hopDongId = hopDongId;
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

    public LocalDate getThang() {
        return thang;
    }

    public void setThang(LocalDate thang) {
        this.thang = thang;
    }

    public BigDecimal getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(BigDecimal tienPhong) {
        this.tienPhong = tienPhong;
    }

    public BigDecimal getTienDien() {
        return tienDien;
    }

    public void setTienDien(BigDecimal tienDien) {
        this.tienDien = tienDien;
    }

    public BigDecimal getTienNuoc() {
        return tienNuoc;
    }

    public void setTienNuoc(BigDecimal tienNuoc) {
        this.tienNuoc = tienNuoc;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "id=" + id +
                ", hopDongId=" + hopDongId +
                ", sinhVienId=" + sinhVienId +
                ", phongId=" + phongId +
                ", thang=" + thang +
                ", tongTien=" + tongTien +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}

