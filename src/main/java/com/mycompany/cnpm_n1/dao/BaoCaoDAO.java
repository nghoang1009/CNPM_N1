package com.mycompany.cnpm_n1.dao;

import com.mycompany.cnpm_n1.DatabaseConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BaoCaoDAO {

    /**
     * Lấy thống kê tổng quát KTX
     */
    public static Map<String, Object> getThongKeTongQuat() {
        Map<String, Object> stats = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Tổng số phòng
            Statement stmt = conn.createStatement();        
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM phong");
            if (rs.next()) stats.put("tongPhong", rs.getInt("total"));
            rs.close();

            // Phòng đang có người
            rs = stmt.executeQuery("SELECT COUNT(*) as total FROM phong WHERE so_nguoi > 0");
            if (rs.next()) stats.put("phongCoDayNguoi", rs.getInt("total"));
            rs.close();

            // Tổng số sinh viên
            rs = stmt.executeQuery("SELECT COUNT(*) as total FROM sinh_vien");
            if (rs.next()) stats.put("tongSinhVien", rs.getInt("total"));
            rs.close();

            // Hợp đồng hoạt động
            rs = stmt.executeQuery("SELECT COUNT(*) as total FROM hop_dong WHERE trang_thai = 'hieu_luc'");
            if (rs.next()) stats.put("hopDongHoatDong", rs.getInt("total"));
            rs.close();

            // Tổng tiền hóa đơn chưa trả
            rs = stmt.executeQuery("SELECT COALESCE(SUM(tong_tien), 0) as total FROM hoa_don WHERE trang_thai = 'chua_tra'");
            if (rs.next()) stats.put("tongTienChuaTra", rs.getBigDecimal("total"));
            rs.close();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return stats;
    }

    /**
     * Lấy báo cáo doanh thu theo tháng
     */
    public static Map<String, Object> getBaoCaoDoanhuTheoThang() {
        Map<String, Object> report = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT DATE_FORMAT(thang, '%Y-%m') as thang_key, " +
                         "DATE_FORMAT(thang, '%m/%Y') as thang_display, " +
                         "SUM(tien_phong) as tien_phong, " +
                         "SUM(tien_dien) as tien_dien, " +
                         "SUM(tien_nuoc) as tien_nuoc, " +
                         "SUM(tong_tien) as tong_tien, " +
                         "SUM(CASE WHEN trang_thai='da_tra' THEN tong_tien ELSE 0 END) as tien_da_tra, " +
                         "SUM(CASE WHEN trang_thai='chua_tra' THEN tong_tien ELSE 0 END) as tien_chua_tra " +
                         "FROM hoa_don " +
                         "GROUP BY DATE_FORMAT(thang, '%Y-%m'), DATE_FORMAT(thang, '%m/%Y') " +
                         "ORDER BY thang_key DESC";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            int count = 0;
            while (rs.next() && count < 12) {
                Map<String, Object> row = new HashMap<>();
                row.put("thang", rs.getString("thang_display"));
                row.put("tienPhong", rs.getBigDecimal("tien_phong"));
                row.put("tienDien", rs.getBigDecimal("tien_dien"));
                row.put("tienNuoc", rs.getBigDecimal("tien_nuoc"));
                row.put("tongTien", rs.getBigDecimal("tong_tien"));
                row.put("tienDaTra", rs.getBigDecimal("tien_da_tra"));
                row.put("tienChuaTra", rs.getBigDecimal("tien_chua_tra"));
                report.put("thang_" + count, row);
                count++;
            }
            report.put("soThang", count);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return report;
    }

    /**
     * Lấy báo cáo chiếm phòng
     */
    public static Map<String, Object> getBaoCaoChiemPhong() {
        Map<String, Object> report = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT p.id, p.so_phong, ln.ten_loai, p.so_nguoi, ln.suc_chua, " +
                         "GROUP_CONCAT(sv.ho_ten SEPARATOR ', ') as sinh_vien " +
                         "FROM phong p " +
                         "LEFT JOIN loai_phong ln ON p.loai_phong_id = ln.id " +
                         "LEFT JOIN hop_dong hd ON p.id = hd.phong_id AND hd.trang_thai = 'hieu_luc' " +
                         "LEFT JOIN sinh_vien sv ON hd.sinh_vien_id = sv.id " +
                         "GROUP BY p.id, p.so_phong, ln.ten_loai, p.so_nguoi, ln.suc_chua " +
                         "ORDER BY p.so_phong";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            int count = 0;
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("phongId", rs.getInt("id"));
                row.put("soPhong", rs.getString("so_phong"));
                row.put("loaiPhong", rs.getString("ten_loai"));
                row.put("soNguoiHienTai", rs.getInt("so_nguoi"));
                row.put("sucChua", rs.getInt("suc_chua"));
                row.put("sinhVien", rs.getString("sinh_vien"));
                report.put("phong_" + count, row);
                count++;
            }
            report.put("soPhong", count);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return report;
    }

    /**
     * Lấy danh sách hợp đồng sắp hết hạn
     */
    public static Map<String, Object> getBaoCaoHopDongSapHetHan(int soNgay) {
        Map<String, Object> report = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT hd.id, sv.ma_sinh_vien, sv.ho_ten, p.so_phong, " +
                         "hd.ngay_bat_dau, hd.ngay_ket_thuc, " +
                         "DATEDIFF(hd.ngay_ket_thuc, CURDATE()) as so_ngay_con_lai " +
                         "FROM hop_dong hd " +
                         "JOIN sinh_vien sv ON hd.sinh_vien_id = sv.id " +
                         "JOIN phong p ON hd.phong_id = p.id " +
                         "WHERE hd.trang_thai = 'hieu_luc' " +
                         "AND DATEDIFF(hd.ngay_ket_thuc, CURDATE()) BETWEEN 1 AND ? " +
                         "ORDER BY hd.ngay_ket_thuc ASC";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, soNgay);
            ResultSet rs = pstmt.executeQuery();
            
            int count = 0;
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("hopDongId", rs.getInt("id"));
                row.put("maSinhVien", rs.getString("ma_sinh_vien"));
                row.put("hoTen", rs.getString("ho_ten"));
                row.put("soPhong", rs.getString("so_phong"));
                row.put("ngayBatDau", rs.getDate("ngay_bat_dau"));
                row.put("ngayKetThuc", rs.getDate("ngay_ket_thuc"));
                row.put("soNgayConLai", rs.getInt("so_ngay_con_lai"));
                report.put("hopdong_" + count, row);
                count++;
            }
            report.put("soHopDong", count);
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return report;
    }
}
