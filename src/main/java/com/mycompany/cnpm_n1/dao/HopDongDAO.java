package com.mycompany.cnpm_n1.dao;

import com.mycompany.cnpm_n1.DatabaseConnection;
import com.mycompany.cnpm_n1.model.HopDong;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HopDongDAO {

    // Lấy tất cả hợp đồng
    public static List<HopDong> getAllHopDong() {
        List<HopDong> list = new ArrayList<>();
        String sql = "SELECT id, sinh_vien_id, phong_id, ngay_bat_dau, ngay_ket_thuc, trang_thai " +
                     "FROM hop_dong ORDER BY ngay_bat_dau DESC";

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

    // Lấy hợp đồng theo id
    public static HopDong getHopDongById(int id) {
        String sql = "SELECT id, sinh_vien_id, phong_id, ngay_bat_dau, ngay_ket_thuc, trang_thai " +
                     "FROM hop_dong WHERE id = ?";

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

    // Lấy hợp đồng của sinh viên
    public static List<HopDong> getHopDongBySinhVienId(int sinhVienId) {
        List<HopDong> list = new ArrayList<>();
        String sql = "SELECT id, sinh_vien_id, phong_id, ngay_bat_dau, ngay_ket_thuc, trang_thai " +
                     "FROM hop_dong WHERE sinh_vien_id = ? ORDER BY ngay_bat_dau DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sinhVienId);
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

    // Thêm hợp đồng
    public static boolean themHopDong(HopDong hd) {
        String sql = "INSERT INTO hop_dong (sinh_vien_id, phong_id, ngay_bat_dau, " +
                     "ngay_ket_thuc, trang_thai) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hd.getSinhVienId());
            pstmt.setInt(2, hd.getPhongId());
            pstmt.setDate(3, Date.valueOf(hd.getNgayBatDau()));
            pstmt.setDate(4, Date.valueOf(hd.getNgayKetThuc()));
            pstmt.setString(5, hd.getTrangThai());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa hợp đồng
    public static boolean suaHopDong(HopDong hd) {
        String sql = "UPDATE hop_dong SET sinh_vien_id=?, phong_id=?, ngay_bat_dau=?, " +
                     "ngay_ket_thuc=?, trang_thai=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hd.getSinhVienId());
            pstmt.setInt(2, hd.getPhongId());
            pstmt.setDate(3, Date.valueOf(hd.getNgayBatDau()));
            pstmt.setDate(4, Date.valueOf(hd.getNgayKetThuc()));
            pstmt.setString(5, hd.getTrangThai());
            pstmt.setInt(6, hd.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa hợp đồng
    public static boolean xoaHopDong(int id) {
        String sql = "DELETE FROM hop_dong WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper: Map ResultSet to HopDong object
    private static HopDong mapRow(ResultSet rs) throws SQLException {
        return new HopDong(
            rs.getInt("id"),
            rs.getInt("sinh_vien_id"),
            rs.getInt("phong_id"),
            rs.getDate("ngay_bat_dau").toLocalDate(),
            rs.getDate("ngay_ket_thuc").toLocalDate(),
            rs.getString("trang_thai")
        );
    }
}
