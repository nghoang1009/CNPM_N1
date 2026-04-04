package com.mycompany.cnpm_n1.view;

import javax.swing.*;
import java.awt.*;

public class frmTrangChu extends JFrame{
    public frmTrangChu() 
    {
        super("Hệ thống quản lý sinh viên KTX");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
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
        setJMenuBar(MenuBarFactory.createMenuBar());
    }

    public static void main(String[] args) {
        frmTrangChu frmTrangChu = new frmTrangChu();
    }
}
