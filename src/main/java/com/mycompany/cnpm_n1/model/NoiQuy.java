package com.mycompany.cnpm_n1.model;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class NoiQuy {
    private int id;
    private String tieuDe;
    private String noiDung;
    private BigDecimal mucPhat;
    private int trangThai; // 1: hoạt động, 0: không hoạt động
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;

    // Constructor mặc định
    public NoiQuy() {
    }

    // Constructor đầy đủ (với id)
    public NoiQuy(int id, String tieuDe, String noiDung, BigDecimal mucPhat,
                  int trangThai, LocalDateTime ngayTao, LocalDateTime ngayCapNhat) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.mucPhat = mucPhat;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
    }

    // Constructor không có id
    public NoiQuy(String tieuDe, String noiDung, BigDecimal mucPhat, int trangThai) {
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.mucPhat = mucPhat;
        this.trangThai = trangThai;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public BigDecimal getMucPhat() {
        return mucPhat;
    }

    public void setMucPhat(BigDecimal mucPhat) {
        this.mucPhat = mucPhat;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public LocalDateTime getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(LocalDateTime ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    @Override
    public String toString() {
        return "NoiQuy{" +
                "id=" + id +
                ", tieuDe='" + tieuDe + '\'' +
                ", mucPhat=" + mucPhat +
                ", trangThai=" + trangThai +
                '}';
    }
}
