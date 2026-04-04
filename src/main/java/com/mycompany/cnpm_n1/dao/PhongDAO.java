package com.mycompany.cnpm_n1.dao;

import com.mycompany.cnpm_n1.DatabaseConnection;
import com.mycompany.cnpm_n1.model.Phong;
import com.mycompany.cnpm_n1.model.SinhVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongDAO {

    // ============ PHONG CRUD ============

    // Lấy tất cả phòng
    public static List<Phong> getAllPhong() {
        List<Phong> list = new ArrayList<>();
        String sql = "SELECT id, toa_nha_id, loai_phong_id, so_phong, tang, so_nguoi, trang_thai " +
                     "FROM phong ORDER BY toa_nha_id, so_phong";

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

    // Lấy phòng theo id
    public static Phong getPhongById(int id) {
        String sql = "SELECT id, toa_nha_id, loai_phong_id, so_phong, tang, so_nguoi, trang_thai " +
                     "FROM phong WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return mapRow(rs);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy phòng theo tòa nhà
    public static List<Phong> getPhongByToaNha(int toaNhaId) {
        List<Phong> list = new ArrayList<>();
        String sql = "SELECT id, toa_nha_id, loai_phong_id, so_phong, tang, so_nguoi, trang_thai " +
                     "FROM phong WHERE toa_nha_id = ? ORDER BY so_phong";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, toaNhaId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tìm kiếm phòng theo số phòng hoặc trạng thái
    public static List<Phong> timKiemPhong(String keyword) {
        List<Phong> list = new ArrayList<>();
        String sql = "SELECT id, toa_nha_id, loai_phong_id, so_phong, tang, so_nguoi, trang_thai " +
                     "FROM phong WHERE so_phong LIKE ? OR trang_thai LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 1; i <= 2; i++)
                pstmt.setString(i, "%" + keyword + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm phòng
    public static boolean themPhong(Phong p) {
        String sql = "INSERT INTO phong (toa_nha_id, loai_phong_id, so_phong, tang, so_nguoi, trang_thai) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, p.getToaNhaId());
            pstmt.setInt(2, p.getLoaiPhongId());
            pstmt.setString(3, p.getSoPhong());
            pstmt.setInt(4, p.getTang());
            pstmt.setInt(5, p.getSoNguoi());
            pstmt.setString(6, p.getTrangThai());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa phòng
    public static boolean suaPhong(Phong p) {
        String sql = "UPDATE phong SET toa_nha_id=?, loai_phong_id=?, so_phong=?, " +
                     "tang=?, so_nguoi=?, trang_thai=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, p.getToaNhaId());
            pstmt.setInt(2, p.getLoaiPhongId());
            pstmt.setString(3, p.getSoPhong());
            pstmt.setInt(4, p.getTang());
            pstmt.setInt(5, p.getSoNguoi());
            pstmt.setString(6, p.getTrangThai());
            pstmt.setInt(7, p.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa phòng
    public static boolean xoaPhong(int id) {
        String sql = "DELETE FROM phong WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============ LIÊN KẾT: Lấy sinh viên đang ở trong 1 phòng ============
    // JOIN: phong → hop_dong → sinh_vien (hop_dong.trang_thai = 'hieu_luc')
    public static List<SinhVien> getSinhVienTrongPhong(int phongId) {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT sv.id, sv.tai_khoan_id, sv.ma_sinh_vien, sv.ho_ten, sv.gioi_tinh, " +
                     "       sv.ngay_sinh, sv.so_dien_thoai, sv.truong " +
                     "FROM sinh_vien sv " +
                     "JOIN hop_dong hd ON hd.sinh_vien_id = sv.id " +
                     "WHERE hd.phong_id = ? AND hd.trang_thai = 'hieu_luc' " +
                     "ORDER BY sv.ho_ten";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, phongId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
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
                list.add(sv);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ============ LOOKUP: Tòa nhà ============
    // Trả về Map<tenToa, id> để dùng trong ComboBox
    public static java.util.Map<String, Integer> getToaNhaMap() {
        java.util.Map<String, Integer> map = new java.util.LinkedHashMap<>();
        String sql = "SELECT id, ten_toa FROM toa_nha ORDER BY ma_toa";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) map.put(rs.getString("ten_toa"), rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    // ============ LOOKUP: Loại phòng ============
    // Trả về Map<tenLoai, id> để dùng trong ComboBox
    public static java.util.Map<String, Integer> getLoaiPhongMap() {
        java.util.Map<String, Integer> map = new java.util.LinkedHashMap<>();
        String sql = "SELECT id, ten_loai FROM loai_phong ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) map.put(rs.getString("ten_loai"), rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    // Lấy tên tòa nhà theo id
    public static String getTenToaNha(int toaNhaId) {
        String sql = "SELECT ten_toa FROM toa_nha WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, toaNhaId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("ten_toa");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Lấy tên loại phòng theo id
    public static String getTenLoaiPhong(int loaiPhongId) {
        String sql = "SELECT ten_loai FROM loai_phong WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loaiPhongId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("ten_loai");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // ============ LOOKUP: Map Phòng ============
    // Trả về Map<soPhong (tenToa - tenLoai), id> để dùng trong JList
    public static java.util.Map<String, Integer> getPhongMap() {
        java.util.Map<String, Integer> map = new java.util.LinkedHashMap<>();
        List<Phong> listPhong = getAllPhong();
        for (Phong p : listPhong) {
            String tenToa = getTenToaNha(p.getToaNhaId());
            String tenLoai = getTenLoaiPhong(p.getLoaiPhongId());
            String displayText = p.getSoPhong() + " (" + tenToa + " - " + tenLoai + ")";
            map.put(displayText, p.getId());
        }
        return map;
    }

    // ============ LOOKUP: Tòa nhà (List) ============
    // Lấy danh sách tòa nhà cho JList
    public static java.util.List<Object> getAllToaNha() {
        java.util.List<Object> list = new ArrayList<>();
        String sql = "SELECT id, ten_toa FROM toa_nha ORDER BY ma_toa";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Lưu dưới dạng cặp key-value
                list.add(new java.util.AbstractMap.SimpleEntry<>(rs.getString("ten_toa"), rs.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Helper: map ResultSet -> Phong
    private static Phong mapRow(ResultSet rs) throws SQLException {
        Phong p = new Phong();
        p.setId(rs.getInt("id"));
        p.setToaNhaId(rs.getInt("toa_nha_id"));
        p.setLoaiPhongId(rs.getInt("loai_phong_id"));
        p.setSoPhong(rs.getString("so_phong"));
        p.setTang(rs.getInt("tang"));
        p.setSoNguoi(rs.getInt("so_nguoi"));
        p.setTrangThai(rs.getString("trang_thai"));
        return p;
    }
}