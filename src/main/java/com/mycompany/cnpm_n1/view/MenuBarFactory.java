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
        menuBar.add(menuSinhVien);
        menuBar.add(menuPhong);
        menuBar.add(menuHoaDon);
        menuBar.add(menuHopDong);
        menuBar.add(menuNoiQuy);
        menuBar.add(menuBaoCao);
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
            new frmHoaDon().setVisible(true);
        });

        mHopDong.addActionListener(e -> {
            new frmHopDong().setVisible(true);
        });

        mNoiQuy.addActionListener(e -> {
            new frmNoiQuy().setVisible(true);
        });

        mBaoCao.addActionListener(e -> {
            new frmBaoCao().setVisible(true);
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
