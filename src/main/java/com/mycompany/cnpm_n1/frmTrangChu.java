package com.mycompany.cnpm_n1;

import javax.swing.*;
import java.awt.*;

public class frmTrangChu extends JFrame{
    public frmTrangChu() 
    {
        super("Hệ thống quản lý sinh viên KTX");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Giao dien
        initComponent();

        setSize(900, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponent()
    {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ SINH VIÊN KÍ TÚC XÁ");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));

        headerPanel.add(lblTitle, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        add(mainPanel);

        //Tạo menuBar
        createMenuBar();
    }

    private void createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        //Menu Sinh viên
        JMenu menuSinhVien = new JMenu("Sinh viên");

        JMenuItem mSinhVien = new JMenuItem("QL Sinh viên");

        menuSinhVien.add(mSinhVien);

        //Menu Phòng
        JMenu menuPhong = new JMenu("Phòng");
        
        JMenuItem mPhong = new JMenuItem("QL Phòng");

        menuPhong.add(mPhong);

        //Menu Hóa đơn
        JMenu menuHoaDon = new JMenu("Hóa đơn");
        
        JMenuItem mHoaDon = new JMenuItem("QL Hóa đơn");

        menuHoaDon.add(mHoaDon);

        //Menu Hệ thống
        JMenu menuHeThong = new JMenu("Hệ thống");

        JMenuItem mDangXuat = new JMenuItem("Đăng xuất");
        JMenuItem mThoat = new JMenuItem("Thoát");

        menuHeThong.add(mDangXuat);
        menuHeThong.add(mThoat);

        //Thêm vào menuBar
        menuBar.add(menuSinhVien);
        menuBar.add(menuPhong);
        menuBar.add(menuHoaDon);
        menuBar.add(menuHeThong);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        frmTrangChu frmTrangChu = new frmTrangChu();
    }
}
