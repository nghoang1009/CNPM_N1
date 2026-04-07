package com.mycompany.cnpm_n1.view;

import com.mycompany.cnpm_n1.dao.NoiQuyDAO;
import com.mycompany.cnpm_n1.model.NoiQuy;
import com.mycompany.cnpm_n1.util.PermissionManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class frmNoiQuy extends JFrame {

    private JTable tblNoiQuy;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtTieuDe, txtMucPhat;
    private JTextArea txtNoiDung;
    private JCheckBox chkTrangThai;

    public frmNoiQuy() {
        super("Quản lý Nội Quy KTX");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Thêm menu bar
        setJMenuBar(MenuBarFactory.createMenuBar());

        // Tiêu đề
        JLabel lbTitle = new JLabel("QUẢN LÝ NỘI QUY KTX", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        // ════════════════════════════════════════
        // PANEL GIỮA: Bảng danh sách
        // ════════════════════════════════════════
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder(border, "Danh sách nội quy"));

        String[] columns = {"ID", "Tiêu đề", "Mức phạt", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblNoiQuy = new JTable(tableModel);
        tblNoiQuy.setRowHeight(24);
        tblNoiQuy.getColumnModel().getColumn(0).setMinWidth(0);
        tblNoiQuy.getColumnModel().getColumn(0).setMaxWidth(0);
        tblNoiQuy.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane tableScrollPane = new JScrollPane(tblNoiQuy);
        tableScrollPane.setPreferredSize(new Dimension(800, 200));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        tblNoiQuy.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblNoiQuy.getSelectedRow();
                if (row >= 0) {
                    loadFormFromTable(row);
                }
            }
        });

        // ════════════════════════════════════════
        // PANEL DƯỚI: Form nhập liệu
        // ════════════════════════════════════════
        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel formTop = new JPanel(new GridLayout(4, 2, 10, 10));
        formTop.setBorder(new TitledBorder(border, "Thông tin nội quy"));

        // ID
        formTop.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        formTop.add(txtId);

        // Tiêu đề
        formTop.add(new JLabel("Tiêu đề:"));
        txtTieuDe = new JTextField();
        formTop.add(txtTieuDe);

        // Mức phạt
        formTop.add(new JLabel("Mức phạt (VNĐ):"));
        txtMucPhat = new JTextField();
        formTop.add(txtMucPhat);

        // Trạng thái
        formTop.add(new JLabel("Trạng thái:"));
        chkTrangThai = new JCheckBox("Hoạt động");
        formTop.add(chkTrangThai);

        // Nội dung
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new JLabel("Nội dung chi tiết:"), BorderLayout.PAGE_START);
        txtNoiDung = new JTextArea(5, 50);
        txtNoiDung.setLineWrap(true);
        txtNoiDung.setWrapStyleWord(true);
        JScrollPane contentScroll = new JScrollPane(txtNoiDung);
        contentPanel.add(contentScroll, BorderLayout.CENTER);

        formPanel.add(formTop, BorderLayout.PAGE_START);
        formPanel.add(contentPanel, BorderLayout.CENTER);

        // ════════════════════════════════════════
        // Nút bấm
        // ════════════════════════════════════════
        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm mới");
        JButton btnEdit = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");
        JButton btnRefresh = new JButton("Refresh");

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);
        btnPanel.add(btnRefresh);

        btnAdd.addActionListener(e -> {
            if (PermissionManager.canEditNoiQuy()) {
                handleThemNoiQuy();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền thêm nội quy", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnEdit.addActionListener(e -> {
            if (PermissionManager.canEditNoiQuy()) {
                handleSuaNoiQuy();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền sửa nội quy", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnDelete.addActionListener(e -> {
            if (PermissionManager.canEditNoiQuy()) {
                handleXoaNoiQuy();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa nội quy", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadDataFromDatabase());

        // ════════════════════════════════════════
        // Layout chính
        // ════════════════════════════════════════
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(formPanel, BorderLayout.PAGE_END);

        add(centerPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);

        setSize(900, 600);
        setLocationRelativeTo(null);
        loadDataFromDatabase();
        
        // Disable edit buttons nếu sinh viên (chỉ cho xem)
        if (PermissionManager.isSinhVien()) {
            for (java.awt.Component comp : btnPanel.getComponents()) {
                if (comp instanceof JButton) {
                    String text = ((JButton) comp).getText();
                    if (!text.equals("Làm mới") && !text.equals("Refresh")) {
                        comp.setEnabled(false);
                    }
                }
            }
        }
    }

    private void loadDataFromDatabase() {
        tableModel.setRowCount(0);
        List<NoiQuy> list = NoiQuyDAO.getAllNoiQuy();
        System.out.println("Tổng nội quy: " + list.size());
        for (NoiQuy nq : list) {
            System.out.println("ID: " + nq.getId() + ", Tiêu đề: " + nq.getTieuDe());
            tableModel.addRow(new Object[]{
                nq.getId(),
                nq.getTieuDe(),
                nq.getMucPhat() != null ? String.format("%,.0f", nq.getMucPhat()) : "0",
                nq.getTrangThai() == 1 ? "Hoạt động" : "Không hoạt động"
            });
        }
    }

    private void loadFormFromTable(int row) {
        try {
            int id = (int) tableModel.getValueAt(row, 0);
            System.out.println("Loading form for ID: " + id);
            NoiQuy nq = NoiQuyDAO.getNoiQuyById(id);
            if (nq != null) {
                txtId.setText(String.valueOf(nq.getId()));
                txtTieuDe.setText(nq.getTieuDe() != null ? nq.getTieuDe() : "");
                txtMucPhat.setText(nq.getMucPhat() != null ? nq.getMucPhat().toString() : "0");
                txtNoiDung.setText(nq.getNoiDung() != null ? nq.getNoiDung() : "");
                chkTrangThai.setSelected(nq.getTrangThai() == 1);
                System.out.println("Loaded successfully!");
            } else {
                System.out.println("NoiQuy not found!");
            }
        } catch (Exception e) {
            System.err.println("Error loading form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleThemNoiQuy() {
        try {
            if (txtTieuDe.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tiêu đề!");
                return;
            }

            String tieuDe = txtTieuDe.getText().trim();
            String noiDung = txtNoiDung.getText().trim();
            BigDecimal mucPhat = new BigDecimal(txtMucPhat.getText().isEmpty() ? "0" : txtMucPhat.getText());
            int trangThai = chkTrangThai.isSelected() ? 1 : 0;

            NoiQuy nq = new NoiQuy(tieuDe, noiDung, mucPhat, trangThai);
            boolean result = NoiQuyDAO.themNoiQuy(nq);
            System.out.println("Insert result: " + result);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Thêm nội quy thành công!");
                clearForm();
                loadDataFromDatabase();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi: Thêm nội quy thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: Mức phạt phải là số! " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleSuaNoiQuy() {
        try {
            String idText = txtId.getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lỗi: Chọn nội quy để cập nhật!");
                return;
            }
            
            int id = Integer.parseInt(idText);
            if (id <= 0) {
                JOptionPane.showMessageDialog(this, "Lỗi: ID không hợp lệ!");
                return;
            }

            if (txtTieuDe.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tiêu đề!");
                return;
            }

            String tieuDe = txtTieuDe.getText().trim();
            String noiDung = txtNoiDung.getText().trim();
            BigDecimal mucPhat = new BigDecimal(txtMucPhat.getText().isEmpty() ? "0" : txtMucPhat.getText());
            int trangThai = chkTrangThai.isSelected() ? 1 : 0;

            NoiQuy nq = new NoiQuy(id, tieuDe, noiDung, mucPhat, trangThai, null, null);
            boolean result = NoiQuyDAO.suaNoiQuy(nq);
            System.out.println("Update result: " + result);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Cập nhật nội quy thành công!");
                clearForm();
                loadDataFromDatabase();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi: Cập nhật nội quy thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: Mức phạt phải là số!");
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleXoaNoiQuy() {
        int row = tblNoiQuy.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nội quy để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            System.out.println("Deleting ID: " + id);
            boolean result = NoiQuyDAO.xoaNoiQuy(id);
            System.out.println("Delete result: " + result);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Xóa nội quy thành công!");
                clearForm();
                loadDataFromDatabase();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi: Xóa nội quy thất bại!");
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtTieuDe.setText("");
        txtMucPhat.setText("");
        txtNoiDung.setText("");
        chkTrangThai.setSelected(true);
    }
}
