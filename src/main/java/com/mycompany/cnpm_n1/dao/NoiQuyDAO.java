package com.mycompany.cnpm_n1.dao;

import com.mycompany.cnpm_n1.DatabaseConnection;
import com.mycompany.cnpm_n1.model.NoiQuy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class NoiQuyDAO {

    // Lấy tất cả nội quy
    public static List<NoiQuy> getAllNoiQuy() {
        List<NoiQuy> list = new ArrayList<>();
        String sql = "SELECT id, tieu_de, noi_dung, muc_phat, trang_thai, ngay_tao, ngay_cap_nhat " +
                     "FROM noi_quy ORDER BY ngay_tao DESC";

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

    // Lấy nội quy theo id
    public static NoiQuy getNoiQuyById(int id) {
        String sql = "SELECT id, tieu_de, noi_dung, muc_phat, trang_thai, ngay_tao, ngay_cap_nhat " +
                     "FROM noi_quy WHERE id = ?";

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

    // Lấy nội quy đang hoạt động
    public static List<NoiQuy> getNoiQuyHoatDong() {
        List<NoiQuy> list = new ArrayList<>();
        String sql = "SELECT id, tieu_de, noi_dung, muc_phat, trang_thai, ngay_tao, ngay_cap_nhat " +
                     "FROM noi_quy WHERE trang_thai = 1 ORDER BY ngay_tao DESC";

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

    // Thêm nội quy
    public static boolean themNoiQuy(NoiQuy nq) {
        String sql = "INSERT INTO noi_quy (tieu_de, noi_dung, muc_phat, trang_thai) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nq.getTieuDe());
            pstmt.setString(2, nq.getNoiDung());
            pstmt.setBigDecimal(3, nq.getMucPhat());
            pstmt.setInt(4, nq.getTrangThai());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa nội quy
    public static boolean suaNoiQuy(NoiQuy nq) {
        String sql = "UPDATE noi_quy SET tieu_de=?, noi_dung=?, muc_phat=?, " +
                     "trang_thai=?, ngay_cap_nhat=NOW() WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nq.getTieuDe());
            pstmt.setString(2, nq.getNoiDung());
            pstmt.setBigDecimal(3, nq.getMucPhat());
            pstmt.setInt(4, nq.getTrangThai());
            pstmt.setInt(5, nq.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa nội quy
    public static boolean xoaNoiQuy(int id) {
        String sql = "DELETE FROM noi_quy WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper: Map ResultSet to NoiQuy object
    private static NoiQuy mapRow(ResultSet rs) throws SQLException {
        return new NoiQuy(
            rs.getInt("id"),
            rs.getString("tieu_de"),
            rs.getString("noi_dung"),
            rs.getBigDecimal("muc_phat"),
            rs.getInt("trang_thai"),
            rs.getTimestamp("ngay_tao") != null ? rs.getTimestamp("ngay_tao").toLocalDateTime() : null,
            rs.getTimestamp("ngay_cap_nhat") != null ? rs.getTimestamp("ngay_cap_nhat").toLocalDateTime() : null
        );
    }
}
