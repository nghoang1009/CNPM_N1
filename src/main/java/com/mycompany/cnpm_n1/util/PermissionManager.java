package com.mycompany.cnpm_n1.util;

/**
 * Lớp quản lý quyền hạn dựa trên vai trò của user
 */
public class PermissionManager {
    
    // Định nghĩa các vai trò
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_NHAN_VIEN = 2;
    public static final int ROLE_SINH_VIEN = 3;
    
    // Lấy vai trò hiện tại từ frmLogin
    public static int getCurrentRole() {
        try {
            String role = com.mycompany.cnpm_n1.view.frmLogin.chucVu;
            if (role == null) return -1;
            return Integer.parseInt(role);
        } catch (Exception e) {
            return -1;
        }
    }
    
    // Lấy tên user hiện tại
    public static String getCurrentUser() {
        return com.mycompany.cnpm_n1.view.frmLogin.tenTK;
    }
    
    // Lấy ID tài khoản hiện tại
    public static int getCurrentUserId() {
        return com.mycompany.cnpm_n1.view.frmLogin.maTK;
    }
    
    // Kiểm tra user là Admin
    public static boolean isAdmin() {
        return getCurrentRole() == ROLE_ADMIN;
    }
    
    // Kiểm tra user là Nhân viên
    public static boolean isNhanVien() {
        return getCurrentRole() == ROLE_NHAN_VIEN;
    }
    
    // Kiểm tra user là Sinh viên
    public static boolean isSinhVien() {
        return getCurrentRole() == ROLE_SINH_VIEN;
    }
    
    // Kiểm tra user là Admin hoặc Nhân viên
    public static boolean isAdminOrNhanVien() {
        return getCurrentRole() == ROLE_ADMIN || getCurrentRole() == ROLE_NHAN_VIEN;
    }
    
    // Lấy tên vai trò
    public static String getRoleName() {
        int role = getCurrentRole();
        switch (role) {
            case ROLE_ADMIN: return "Admin";
            case ROLE_NHAN_VIEN: return "Nhân viên";
            case ROLE_SINH_VIEN: return "Sinh viên";
            default: return "Unknown";
        }
    }
    
    // ════════════════════════════════════════
    // SINH VIÊN PERMISSIONS
    // ════════════════════════════════════════
    
    // Kiểm tra quyền xem sinh viên
    public static boolean canViewSinhVien() {
        return isAdminOrNhanVien() || isSinhVien();
    }
    
    // Kiểm tra quyền thêm/sửa/xóa sinh viên
    public static boolean canEditSinhVien() {
        return isAdmin() || isNhanVien();
    }
    
    // ════════════════════════════════════════
    // PHÒNG PERMISSIONS
    // ════════════════════════════════════════
    
    // Kiểm tra quyền xem phòng
    public static boolean canViewPhong() {
        return isAdminOrNhanVien() || isSinhVien();
    }
    
    // Kiểm tra quyền thêm/sửa/xóa phòng
    public static boolean canEditPhong() {
        return isAdmin() || isNhanVien();
    }
    
    // ════════════════════════════════════════
    // HỢP ĐỒNG PERMISSIONS
    // ════════════════════════════════════════
    
    // Kiểm tra quyền xem hợp đồng
    public static boolean canViewHopDong() {
        return true; // Ai cũng xem được
    }
    
    // Kiểm tra quyền thêm/sửa/xóa hợp đồng
    public static boolean canEditHopDong() {
        return isAdmin() || isNhanVien();
    }
    
    // ════════════════════════════════════════
    // HÓA ĐƠN PERMISSIONS
    // ════════════════════════════════════════
    
    // Kiểm tra quyền xem hóa đơn
    public static boolean canViewHoaDon() {
        return true; // Ai cũng xem được
    }
    
    // Kiểm tra quyền thêm/sửa/xóa hóa đơn
    public static boolean canEditHoaDon() {
        return isAdmin() || isNhanVien();
    }
    
    // Kiểm tra quyền thanh toán/cập nhật trạng thái hóa đơn (sinh viên)
    public static boolean canPayHoaDon() {
        return isAdminOrNhanVien() || isSinhVien();
    }
    
    // ════════════════════════════════════════
    // NỘI QUY PERMISSIONS
    // ════════════════════════════════════════
    
    // Kiểm tra quyền xem nội quy
    public static boolean canViewNoiQuy() {
        return true; // Ai cũng xem được
    }
    
    // Kiểm tra quyền thêm/sửa/xóa nội quy
    public static boolean canEditNoiQuy() {
        return isAdmin() || isNhanVien();
    }
    
    // ════════════════════════════════════════
    // BÁO CÁO PERMISSIONS
    // ════════════════════════════════════════
    
    // Kiểm tra quyền xem báo cáo
    public static boolean canViewBaoCao() {
        return isAdmin() || isNhanVien();
    }
}
