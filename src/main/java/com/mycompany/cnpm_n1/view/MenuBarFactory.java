package com.mycompany.cnpm_n1.view;

import javax.swing.*;

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

        // Menu Hệ thống
        JMenu menuHeThong = new JMenu("Hệ thống");
        JMenuItem mDangXuat = new JMenuItem("Đăng xuất");
        JMenuItem mThoat = new JMenuItem("Thoát");
        menuHeThong.add(mDangXuat);
        menuHeThong.add(mThoat);

        // Thêm vào menuBar
        menuBar.add(menuTrangChu);
        menuBar.add(menuSinhVien);
        menuBar.add(menuPhong);
        menuBar.add(menuHoaDon);
        menuBar.add(menuHeThong);

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
            new frmSinhVien().setVisible(true);
        });

        mPhong.addActionListener(e -> {
            new frmPhong().setVisible(true);
        });

        mHoaDon.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Chức năng Quản lý Hóa đơn chưa được xây dựng!");
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
