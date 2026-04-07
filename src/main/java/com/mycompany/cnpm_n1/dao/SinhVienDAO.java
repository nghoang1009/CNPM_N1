package com.mycompany.cnpm_n1.dao;

import com.mycompany.cnpm_n1.DatabaseConnection;
import com.mycompany.cnpm_n1.model.SinhVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class SinhVienDAO {

    // Lấy tất cả sinh viên
    public static List<SinhVien> getAllSinhVien() {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT id, tai_khoan_id, ma_sinh_vien, ho_ten, gioi_tinh, " +
                     "ngay_sinh, so_dien_thoai, truong FROM sinh_vien ORDER BY ma_sinh_vien";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy sinh viên theo id
    public static SinhVien getSinhVienById(int id) {
        String sql = "SELECT id, tai_khoan_id, ma_sinh_vien, ho_ten, gioi_tinh, " +
                     "ngay_sinh, so_dien_thoai, truong FROM sinh_vien WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm sinh viên
    public static boolean themSinhVien(SinhVien sv) {
        String sql = "INSERT INTO sinh_vien (tai_khoan_id, ma_sinh_vien, ho_ten, gioi_tinh, " +
                     "ngay_sinh, so_dien_thoai, truong) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, sv.getTaiKhoanId());
            pstmt.setString(2, sv.getMaSinhVien());
            pstmt.setString(3, sv.getHoTen());
            pstmt.setString(4, sv.getGioiTinh());
            pstmt.setDate(5, sv.getNgaySinh() != null ? Date.valueOf(sv.getNgaySinh()) : null);
            pstmt.setString(6, sv.getSoDienThoai());
            pstmt.setString(7, sv.getTruong());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa sinh viên
    public static boolean suaSinhVien(SinhVien sv) {
        String sql = "UPDATE sinh_vien SET tai_khoan_id=?, ma_sinh_vien=?, ho_ten=?, gioi_tinh=?, " +
                     "ngay_sinh=?, so_dien_thoai=?, truong=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, sv.getTaiKhoanId());
            pstmt.setString(2, sv.getMaSinhVien());
            pstmt.setString(3, sv.getHoTen());
            pstmt.setString(4, sv.getGioiTinh());
            pstmt.setDate(5, sv.getNgaySinh() != null ? Date.valueOf(sv.getNgaySinh()) : null);
            pstmt.setString(6, sv.getSoDienThoai());
            pstmt.setString(7, sv.getTruong());
            pstmt.setInt(8, sv.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa sinh viên
    public static boolean xoaSinhVien(int id) {
        String sql = "DELETE FROM sinh_vien WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm sinh viên theo tên / mã SV / SĐT
    public static List<SinhVien> timKiemSinhVien(String keyword) {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT id, tai_khoan_id, ma_sinh_vien, ho_ten, gioi_tinh, " +
                     "ngay_sinh, so_dien_thoai, truong FROM sinh_vien " +
                     "WHERE ma_sinh_vien LIKE ? OR ho_ten LIKE ? OR so_dien_thoai LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 1; i <= 3; i++)
                pstmt.setString(i, "%" + keyword + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Kiểm tra mã sinh viên đã tồn tại chưa (excludeId = 0 khi thêm mới)
    public static boolean isMaTonTai(String maSinhVien, int excludeId) {
        String sql = "SELECT COUNT(*) FROM sinh_vien WHERE ma_sinh_vien = ? AND id != ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maSinhVien);
            pstmt.setInt(2, excludeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ============ LOOKUP: Map Sinh Viên ============
    public static Map<String, Integer> getSinhVienMap() {
        Map<String, Integer> map = new LinkedHashMap<>();
        List<SinhVien> list = getAllSinhVien();
        for (SinhVien sv : list) {
            String displayText = sv.getMaSinhVien() + " - " + sv.getHoTen();
            map.put(displayText, sv.getId());
        }
        return map;
    }

    // Helper: map ResultSet -> SinhVien
    private static SinhVien mapRow(ResultSet rs) throws SQLException {
        SinhVien sv = new SinhVien();
        sv.setId(rs.getInt("id"));

        int tkId = rs.getInt("tai_khoan_id");
        sv.setTaiKhoanId(rs.wasNull() ? null : tkId);

        sv.setMaSinhVien(rs.getString("ma_sinh_vien"));
        sv.setHoTen(rs.getString("ho_ten"));
        sv.setGioiTinh(rs.getString("gioi_tinh"));

        Date d = rs.getDate("ngay_sinh");
        sv.setNgaySinh(d != null ? d.toLocalDate() : null);

        sv.setSoDienThoai(rs.getString("so_dien_thoai"));
        sv.setTruong(rs.getString("truong"));
        return sv;
    }
}