package com.mycompany.cnpm_n1.dao;

import com.mycompany.cnpm_n1.DatabaseConnection;
import com.mycompany.cnpm_n1.model.HoaDon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class HoaDonDAO {

    // Lấy tất cả hóa đơn
    public static List<HoaDon> getAllHoaDon() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT id, hop_dong_id, sinh_vien_id, phong_id, thang, " +
                     "tien_phong, tien_dien, tien_nuoc, tong_tien, trang_thai " +
                     "FROM hoa_don ORDER BY thang DESC";

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

    // Lấy hóa đơn theo id
    public static HoaDon getHoaDonById(int id) {
        String sql = "SELECT id, hop_dong_id, sinh_vien_id, phong_id, thang, " +
                     "tien_phong, tien_dien, tien_nuoc, tong_tien, trang_thai " +
                     "FROM hoa_don WHERE id = ?";

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

    // Lấy hóa đơn của sinh viên
    public static List<HoaDon> getHoaDonBySinhVienId(int sinhVienId) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT id, hop_dong_id, sinh_vien_id, phong_id, thang, " +
                     "tien_phong, tien_dien, tien_nuoc, tong_tien, trang_thai " +
                     "FROM hoa_don WHERE sinh_vien_id = ? ORDER BY thang DESC";

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

    // Lấy hóa đơn chưa trả của sinh viên
    public static List<HoaDon> getHoaDonChuaTraBySinhVienId(int sinhVienId) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT id, hop_dong_id, sinh_vien_id, phong_id, thang, " +
                     "tien_phong, tien_dien, tien_nuoc, tong_tien, trang_thai " +
                     "FROM hoa_don WHERE sinh_vien_id = ? AND trang_thai = 'chua_tra' " +
                     "ORDER BY thang DESC";

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

    // Lấy hóa đơn chưa trả
    public static List<HoaDon> getHoaDonChuaTra() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT id, hop_dong_id, sinh_vien_id, phong_id, thang, " +
                     "tien_phong, tien_dien, tien_nuoc, tong_tien, trang_thai " +
                     "FROM hoa_don WHERE trang_thai = 'chua_tra' ORDER BY thang DESC";

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

    // Lấy hóa đơn đã trả
    public static List<HoaDon> getHoaDonDaTra() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT id, hop_dong_id, sinh_vien_id, phong_id, thang, " +
                     "tien_phong, tien_dien, tien_nuoc, tong_tien, trang_thai " +
                     "FROM hoa_don WHERE trang_thai = 'da_tra' ORDER BY thang DESC";

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

    // Thêm hóa đơn
    public static boolean themHoaDon(HoaDon hd) {
        String sql = "INSERT INTO hoa_don (hop_dong_id, sinh_vien_id, phong_id, thang, " +
                     "tien_phong, tien_dien, tien_nuoc, tong_tien, trang_thai) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hd.getHopDongId());
            pstmt.setInt(2, hd.getSinhVienId());
            pstmt.setInt(3, hd.getPhongId());
            pstmt.setDate(4, Date.valueOf(hd.getThang()));
            pstmt.setBigDecimal(5, hd.getTienPhong());
            pstmt.setBigDecimal(6, hd.getTienDien());
            pstmt.setBigDecimal(7, hd.getTienNuoc());
            pstmt.setBigDecimal(8, hd.getTongTien());
            pstmt.setString(9, hd.getTrangThai());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa hóa đơn
    public static boolean suaHoaDon(HoaDon hd) {
        String sql = "UPDATE hoa_don SET hop_dong_id=?, sinh_vien_id=?, phong_id=?, thang=?, " +
                     "tien_phong=?, tien_dien=?, tien_nuoc=?, tong_tien=?, trang_thai=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hd.getHopDongId());
            pstmt.setInt(2, hd.getSinhVienId());
            pstmt.setInt(3, hd.getPhongId());
            pstmt.setDate(4, Date.valueOf(hd.getThang()));
            pstmt.setBigDecimal(5, hd.getTienPhong());
            pstmt.setBigDecimal(6, hd.getTienDien());
            pstmt.setBigDecimal(7, hd.getTienNuoc());
            pstmt.setBigDecimal(8, hd.getTongTien());
            pstmt.setString(9, hd.getTrangThai());
            pstmt.setInt(10, hd.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa hóa đơn
    public static boolean xoaHoaDon(int id) {
        String sql = "DELETE FROM hoa_don WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật trạng thái hóa đơn
    public static boolean capNhatTrangThaiHoaDon(int id, String trangThai) {
        String sql = "UPDATE hoa_don SET trang_thai = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, trangThai);
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper: Map ResultSet to HoaDon object
    private static HoaDon mapRow(ResultSet rs) throws SQLException {
        return new HoaDon(
            rs.getInt("id"),
            rs.getInt("hop_dong_id"),
            rs.getInt("sinh_vien_id"),
            rs.getInt("phong_id"),
            rs.getDate("thang").toLocalDate(),
            rs.getBigDecimal("tien_phong"),
            rs.getBigDecimal("tien_dien"),
            rs.getBigDecimal("tien_nuoc"),
            rs.getBigDecimal("tong_tien"),
            rs.getString("trang_thai")
        );
    }
}

