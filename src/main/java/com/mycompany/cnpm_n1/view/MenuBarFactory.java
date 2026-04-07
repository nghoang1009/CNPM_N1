package com.mycompany.cnpm_n1.view;

import javax.swing.*;
import com.mycompany.cnpm_n1.util.PermissionManager;

public class MenuBarFactory {
    public static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuTrangChu = new JMenu("Trang chủ");
        JMenuItem mTrangChu = new JMenuItem("Trang chủ");
        menuTrangChu.add(mTrangChu);

        // Menu Sinh viên
        JMenu menuSinhVien = new JMenu("Sinh viên");
        JMenuItem mSinhVien = new JMenuItem("QL Sinh viên");
        menuSinhVien.add(mSinhVien);

        // Menu Phòng
        JMenu menuPhong = new JMenu("Phòng");
        JMenuItem mPhong = new JMenuItem("QL Phòng");
        menuPhong.add(mPhong);

        // Menu Hóa đơn
        JMenu menuHoaDon = new JMenu("Hóa đơn");
        JMenuItem mHoaDon = new JMenuItem("QL Hóa đơn");
        menuHoaDon.add(mHoaDon);

        // Menu Hợp Đồng
        JMenu menuHopDong = new JMenu("Hợp Đồng");
        JMenuItem mHopDong = new JMenuItem("QL Hợp Đồng");
        menuHopDong.add(mHopDong);

        // Menu Nội Quy
        JMenu menuNoiQuy = new JMenu("Nội Quy");
        JMenuItem mNoiQuy = new JMenuItem("QL Nội Quy");
        menuNoiQuy.add(mNoiQuy);

        // Menu Báo Cáo
        JMenu menuBaoCao = new JMenu("Báo Cáo");
        JMenuItem mBaoCao = new JMenuItem("Báo Cáo & Thống Kê");
        menuBaoCao.add(mBaoCao);

        // Menu Hệ thống
        JMenu menuHeThong = new JMenu("Hệ thống");
        JMenuItem mDangXuat = new JMenuItem("Đăng xuất");
        JMenuItem mThoat = new JMenuItem("Thoát");
        menuHeThong.add(mDangXuat);
        menuHeThong.add(mThoat);

        // Thêm vào menuBar
        menuBar.add(menuTrangChu);
        
        // Chỉ Quản lý (Admin & Nhân viên) mới quản lý Sinh viên
        if (PermissionManager.isAdminOrNhanVien()) {
            menuBar.add(menuSinhVien);
        }
        
        // Chỉ Quản lý mới quản lý Phòng (Sinh viên chỉ xem)
        if (PermissionManager.isAdminOrNhanVien()) {
            menuBar.add(menuPhong);
        }
        
        // Ai cũng xem được Hóa đơn (Sinh viên thanh toán)
        menuBar.add(menuHoaDon);
        
        // Ai cũng xem được Hợp đồng
        menuBar.add(menuHopDong);
        
        // Ai cũng xem được Nội quy
        menuBar.add(menuNoiQuy);
        
        // Chỉ Quản lý xem báo cáo thống kê
        if (PermissionManager.isAdminOrNhanVien()) {
            menuBar.add(menuBaoCao);
        }
        
        menuBar.add(menuHeThong);
        
        // Thêm label hiển thị user đang đăng nhập
        menuBar.add(Box.createHorizontalGlue());
        JLabel lblUser = new JLabel("Người dùng: " + PermissionManager.getCurrentUser() + 
                                    " (" + PermissionManager.getRoleName() + ")");
        lblUser.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        menuBar.add(lblUser);

        // ── Events ──
        mTrangChu.addActionListener(e -> {
            // Đóng tất cả các form khác, chỉ giữ lại frmTrangChu
            for (java.awt.Window window : java.awt.Window.getWindows()) {
                if (window instanceof JFrame) {
                    JFrame frame = (JFrame) window;
                    if (!frame.getTitle().equals("Hệ thống quản lý sinh viên KTX")) {
                        frame.dispose();
                    }
                }
            }
            // Nếu trang chủ chưa mở, mở nó
            boolean trangChuOpened = false;
            for (java.awt.Window window : java.awt.Window.getWindows()) {
                if (window instanceof JFrame) {
                    JFrame frame = (JFrame) window;
                    if (frame.getTitle().equals("Hệ thống quản lý sinh viên KTX")) {
                        frame.setVisible(true);
                        frame.toFront();
                        trangChuOpened = true;
                        break;
                    }
                }
            }
            if (!trangChuOpened) {
                new frmTrangChu().setVisible(true);
            }
        });

        mSinhVien.addActionListener(e -> {
            if (PermissionManager.canViewSinhVien()) {
                new frmSinhVien().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Bạn không có quyền truy cập mục này!", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });

        mPhong.addActionListener(e -> {
            if (PermissionManager.canViewPhong()) {
                new frmPhong().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Bạn không có quyền truy cập mục này!", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });

        mHoaDon.addActionListener(e -> {
            if (PermissionManager.canViewHoaDon()) {
                new frmHoaDon().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Bạn không có quyền truy cập mục này!", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });

        mHopDong.addActionListener(e -> {
            if (PermissionManager.canViewHopDong()) {
                new frmHopDong().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Bạn không có quyền truy cập mục này!", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });

        mNoiQuy.addActionListener(e -> {
            new frmNoiQuy().setVisible(true);
        });

        mBaoCao.addActionListener(e -> {
            if (PermissionManager.canViewBaoCao()) {
                new frmBaoCao().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Bạn không có quyền truy cập mục này!", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });

        mDangXuat.addActionListener(e -> {
            // Tìm frame hiện tại và đóng nó
            for (java.awt.Window window : java.awt.Window.getWindows()) {
                if (window instanceof JFrame) {
                    JFrame frame = (JFrame) window;
                    if (!frame.getTitle().equals("Hệ thống quản lý sinh viên KTX")) {
                        frame.dispose();
                    }
                }
            }
            new frmLogin().setVisible(true);
        });

        mThoat.addActionListener(e -> {
            System.exit(0);
        });

        return menuBar;
    }
}
