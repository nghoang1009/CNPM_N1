package com.mycompany.cnpm_n1.view;

import com.mycompany.cnpm_n1.dao.PhongDAO;
import com.mycompany.cnpm_n1.model.Phong;
import com.mycompany.cnpm_n1.model.SinhVien;
import com.mycompany.cnpm_n1.util.PermissionManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class frmPhong extends JFrame {

    // ── JList danh sách tòa nhà ──
    private JList<String> lstToaNha;
    private DefaultListModel<String> toaNhaListModel;

    // ── JTable danh sách phòng ──
    private JTable tblPhong;
    private DefaultTableModel tableModel;

    // ── JTable danh sách sinh viên ──
    private JTable tblSinhVien;
    private DefaultTableModel svTableModel;

    // ── Form nhập liệu phòng ──
    private JTextField txtId, txtSoPhong, txtTang, txtSoNguoi;
    private JComboBox<String> cboLoaiPhong, cboTrangThai;

    // ── Map lookup ──
    private Map<String, Integer> toaNhaMap;
    private Map<String, Integer> loaiPhongMap;

    public frmPhong() {
        super("Quản lý phòng KTX");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Thêm menu bar
        setJMenuBar(MenuBarFactory.createMenuBar());

        // Tiêu đề
        JLabel lbTitle = new JLabel("QUẢN LÝ PHÒNG KTX", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        // ── Load lookup maps ──
        toaNhaMap    = PhongDAO.getToaNhaMap();
        loaiPhongMap = PhongDAO.getLoaiPhongMap();

        // ════════════════════════════════════════
        // PANEL TRÁI: danh sách tòa nhà
        // ════════════════════════════════════════
        JPanel pnLeft = new JPanel(new BorderLayout());
        toaNhaListModel = new DefaultListModel<>();

        lstToaNha = new JList<>(toaNhaListModel);
        JScrollPane scrollPane = new JScrollPane(lstToaNha);
        scrollPane.setBorder(new TitledBorder(border, "Danh sách tòa nhà"));

        JPanel pnBtn1 = new JPanel();
        JButton btnAddToaNha = new JButton("Thêm");
        JButton btnEditToaNha = new JButton("Sửa");
        JButton btnDeleteToaNha = new JButton("Xóa");

        pnBtn1.add(btnAddToaNha);
        pnBtn1.add(btnEditToaNha);
        pnBtn1.add(btnDeleteToaNha);

        pnLeft.add(scrollPane, BorderLayout.CENTER);
        pnLeft.add(pnBtn1, BorderLayout.PAGE_END);

        // ════════════════════════════════════════
        // PANEL PHẢI: danh sách và form phòng + sinh viên
        // ════════════════════════════════════════
        JPanel pnRight = new JPanel(new BorderLayout());

        // ── Bảng danh sách phòng ──
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder(border, "Danh sách phòng"));

        String[] colsPhong = {"ID", "Số phòng", "Loại phòng", "Tầng", "Số người", "Trạng thái"};
        tableModel = new DefaultTableModel(colsPhong, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblPhong = new JTable(tableModel);
        tblPhong.setRowHeight(24);
        // Ẩn cột ID
        tblPhong.getColumnModel().getColumn(0).setMinWidth(0);
        tblPhong.getColumnModel().getColumn(0).setMaxWidth(0);
        tblPhong.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane spPhong = new JScrollPane(tblPhong);
        spPhong.setPreferredSize(new Dimension(500, 150));
        tablePanel.add(spPhong, BorderLayout.CENTER);

        pnRight.add(tablePanel, BorderLayout.PAGE_START);

        // ── Panel thông tin phòng ──
        JPanel pnTT = new JPanel(new GridLayout(6, 2, 5, 5));
        pnTT.setBorder(new TitledBorder(border, "Thông tin phòng"));

        txtId      = new JTextField(); txtId.setEditable(false);
        txtSoPhong = new JTextField(30);
        txtTang    = new JTextField(30);
        txtSoNguoi = new JTextField(30);

        cboLoaiPhong = new JComboBox<>(loaiPhongMap.keySet().toArray(new String[0]));
        cboTrangThai = new JComboBox<>(new String[]{"con_trong", "day", "bao_tri"});

        pnTT.add(new JLabel("ID:"));              pnTT.add(txtId);
        pnTT.add(new JLabel("Số phòng:"));        pnTT.add(txtSoPhong);
        pnTT.add(new JLabel("Loại phòng:"));      pnTT.add(cboLoaiPhong);
        pnTT.add(new JLabel("Tầng:"));            pnTT.add(txtTang);
        pnTT.add(new JLabel("Số người hiện tại:")); pnTT.add(txtSoNguoi);
        pnTT.add(new JLabel("Trạng thái:"));      pnTT.add(cboTrangThai);

        // ── Panel nút phòng ──
        JPanel pnBtn2 = new JPanel();
        JButton btThem = new JButton("Thêm");
        JButton btSua = new JButton("Sửa");
        JButton btXoa = new JButton("Xóa");
        JButton btXoaTrang = new JButton("Xóa trắng");

        pnBtn2.add(btThem);
        pnBtn2.add(btSua);
        pnBtn2.add(btXoa);
        pnBtn2.add(btXoaTrang);

        // ── Bảng sinh viên trong phòng ──
        String[] colsSV = {"Mã SV", "Họ và tên", "Giới tính", "SĐT", "Trường"};
        svTableModel = new DefaultTableModel(colsSV, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblSinhVien = new JTable(svTableModel);
        tblSinhVien.setRowHeight(24);

        JScrollPane spSV = new JScrollPane(tblSinhVien);
        spSV.setPreferredSize(new Dimension(500, 120));
        JPanel pnSV = new JPanel(new BorderLayout());
        pnSV.setBorder(new TitledBorder(border, "Sinh viên đang ở phòng này"));
        pnSV.add(spSV, BorderLayout.CENTER);

        // ── Ghép form + nút + sinh viên ──
        JPanel pnCenter = new JPanel(new BorderLayout());
        pnCenter.add(pnTT, BorderLayout.PAGE_START);
        pnCenter.add(pnBtn2, BorderLayout.CENTER);
        pnCenter.add(pnSV, BorderLayout.PAGE_END);

        pnRight.add(pnCenter, BorderLayout.CENTER);

        // ════════════════════════════════════════
        // JSplitPane: trái + phải
        // ════════════════════════════════════════
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight);
        jSplitPane.setDividerLocation(280);
        add(jSplitPane, BorderLayout.CENTER);

        setSize(1000, 650);
        setLocationRelativeTo(null);

        // ── Thêm menu bar chung ──
        setJMenuBar(MenuBarFactory.createMenuBar());

        // Load dữ liệu ban đầu
        loadToaNha();
        loadAllPhong();
        
        // Disable buttons nếu không có quyền edit phòng
        if (!PermissionManager.canEditPhong()) {
            for (java.awt.Component comp : pnBtn2.getComponents()) {
                if (comp instanceof JButton) {
                    String text = ((JButton) comp).getText();
                    // Disable Thêm, Sửa, Xóa; giữ lại "Xóa trắng" cho user xóa form
                    if (text.equals("Thêm") || text.equals("Sửa") || text.equals("Xóa")) {
                        comp.setEnabled(false);
                    }
                }
            }
        }

        // ════════════════════════════════════════
        // EVENTS
        // ════════════════════════════════════════

        // Click vào tòa nhà → load phòng của tòa nhà đó
        lstToaNha.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedToaNha = lstToaNha.getSelectedValue();
                if (selectedToaNha != null) {
                    Integer toaNhaId = toaNhaMap.get(selectedToaNha);
                    if (toaNhaId != null) {
                        loadPhongByToaNha(toaNhaId);
                    }
                }
            }
        });

        // Click vào dòng trong bảng phòng → điền form + load sinh viên
        tblPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblPhong.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(tableModel.getValueAt(row, 0).toString());
                    txtSoPhong.setText(tableModel.getValueAt(row, 1).toString());
                    String tenLoai = tableModel.getValueAt(row, 2).toString();
                    cboLoaiPhong.setSelectedItem(tenLoai);
                    txtTang.setText(tableModel.getValueAt(row, 3).toString());
                    txtSoNguoi.setText(tableModel.getValueAt(row, 4).toString());
                    String trangThai = tableModel.getValueAt(row, 5).toString();
                    cboTrangThai.setSelectedItem(trangThai);

                    // Load sinh viên trong phòng này
                    int phongId = Integer.parseInt(txtId.getText().trim());
                    loadSinhVienTrongPhong(phongId);
                }
            }
        });

        // ── Events TÒANHÀ ──
        btnAddToaNha.addActionListener(e -> ThemToaNha());
        btnEditToaNha.addActionListener(e -> SuaToaNha());
        btnDeleteToaNha.addActionListener(e -> XoaToaNha());

        // ── Events PHÒNG ──
        btThem.addActionListener(e -> {
            if (PermissionManager.canEditPhong()) {
                ThemPhong();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền thêm phòng", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });
        btSua.addActionListener(e -> {
            if (PermissionManager.canEditPhong()) {
                SuaPhong();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền sửa phòng", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });
        btXoa.addActionListener(e -> {
            if (PermissionManager.canEditPhong()) {
                XoaPhong();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa phòng", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });
        btXoaTrang.addActionListener(e -> XoaTrang());
    }

    // ════════════════════════════════════════
    // LOAD TÒA NHÀ
    // ════════════════════════════════════════

    private void loadToaNha() {
        toaNhaListModel.clear();
        toaNhaMap.clear();

        List<Object> listToaNha = PhongDAO.getAllToaNha();
        for (Object item : listToaNha) {
            if (item instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) item;
                String tenToa = entry.getKey().toString();
                Integer toaNhaId = (Integer) entry.getValue();
                toaNhaListModel.addElement(tenToa);
                toaNhaMap.put(tenToa, toaNhaId);
            }
        }
    }

    // ════════════════════════════════════════
    // LOAD PHÒNG
    // ════════════════════════════════════════

    private void loadAllPhong() {
        loadPhong(PhongDAO.getAllPhong());
    }

    private void loadPhongByToaNha(int toaNhaId) {
        tableModel.setRowCount(0);
        List<Phong> list = PhongDAO.getAllPhong();
        if (list != null) {
            for (Phong p : list) {
                if (p.getToaNhaId() == toaNhaId) {
                    String tenLoai = PhongDAO.getTenLoaiPhong(p.getLoaiPhongId());
                    tableModel.addRow(new Object[]{
                        p.getId(),
                        p.getSoPhong(),
                        tenLoai,
                        p.getTang(),
                        p.getSoNguoi(),
                        p.getTrangThai()
                    });
                }
            }
        }
        XoaTrang();
    }

    private void loadPhong(List<Phong> list) {
        tableModel.setRowCount(0);
        for (Phong p : list) {
            String tenLoai = PhongDAO.getTenLoaiPhong(p.getLoaiPhongId());
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getSoPhong(),
                tenLoai,
                p.getTang(),
                p.getSoNguoi(),
                p.getTrangThai()
            });
        }
    }

    private void loadSinhVienTrongPhong(int phongId) {
        svTableModel.setRowCount(0);
        List<SinhVien> listSV = PhongDAO.getSinhVienTrongPhong(phongId);
        for (SinhVien sv : listSV) {
            svTableModel.addRow(new Object[]{
                sv.getMaSinhVien(),
                sv.getHoTen(),
                sv.getGioiTinh(),
                sv.getSoDienThoai(),
                sv.getTruong()
            });
        }

        // Cập nhật tiêu đề panel với số sinh viên
        String tieuDe = "Sinh viên đang ở phòng này (" + listSV.size() + " người)";
        ((TitledBorder) ((JPanel) tblSinhVien.getParent().getParent()).getBorder()).setTitle(tieuDe);
        ((JPanel) tblSinhVien.getParent().getParent()).repaint();
    }

    // ════════════════════════════════════════
    // CRUD TÒA NHÀ
    // ════════════════════════════════════════

    private void ThemToaNha() {
        JOptionPane.showMessageDialog(this, "Chức năng quản lý tòa nhà sẽ được cập nhật sau!");
        loadToaNha();
    }

    private void SuaToaNha() {
        String selectedToaNha = lstToaNha.getSelectedValue();
        if (selectedToaNha == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tòa nhà cần sửa!");
            return;
        }
        JOptionPane.showMessageDialog(this, "Chức năng quản lý tòa nhà sẽ được cập nhật sau!");
        loadToaNha();
    }

    private void XoaToaNha() {
        String selectedToaNha = lstToaNha.getSelectedValue();
        if (selectedToaNha == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tòa nhà cần xóa!");
            return;
        }

        JOptionPane.showMessageDialog(this, "Chức năng quản lý tòa nhà sẽ được cập nhật sau!");
        loadToaNha();
    }

    // ════════════════════════════════════════
    // CRUD PHÒNG
    // ════════════════════════════════════════

    private void ThemPhong() {
        if (lstToaNha.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tòa nhà trước!");
            return;
        }

        if (txtSoPhong.getText().trim().isEmpty() ||
            txtTang.getText().trim().isEmpty()    ||
            txtSoNguoi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try {
            String selectedToaNha = lstToaNha.getSelectedValue();
            int toaNhaId = toaNhaMap.get(selectedToaNha);
            String tenLoai = (String) cboLoaiPhong.getSelectedItem();
            int loaiPhongId = loaiPhongMap.get(tenLoai);
            String soPhong = txtSoPhong.getText().trim();
            int tang = Integer.parseInt(txtTang.getText().trim());
            int soNguoi = Integer.parseInt(txtSoNguoi.getText().trim());
            String trangThai = (String) cboTrangThai.getSelectedItem();

            Phong p = new Phong(toaNhaId, loaiPhongId, soPhong, tang, soNguoi, trangThai);

            if (PhongDAO.themPhong(p)) {
                JOptionPane.showMessageDialog(this, "Thêm phòng thành công!");
                String toaNha = lstToaNha.getSelectedValue();
                if (toaNha != null) {
                    int tid = toaNhaMap.get(toaNha);
                    loadPhongByToaNha(tid);
                } else {
                    loadAllPhong();
                }
                XoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tầng và số người phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void SuaPhong() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần sửa!");
            return;
        }
        if (txtSoPhong.getText().trim().isEmpty() ||
            txtTang.getText().trim().isEmpty()    ||
            txtSoNguoi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String selectedToaNha = lstToaNha.getSelectedValue();
            int toaNhaId = toaNhaMap.get(selectedToaNha);
            String tenLoai = (String) cboLoaiPhong.getSelectedItem();
            int loaiPhongId = loaiPhongMap.get(tenLoai);
            String soPhong = txtSoPhong.getText().trim();
            int tang = Integer.parseInt(txtTang.getText().trim());
            int soNguoi = Integer.parseInt(txtSoNguoi.getText().trim());
            String trangThai = (String) cboTrangThai.getSelectedItem();

            Phong p = new Phong(id, toaNhaId, loaiPhongId, soPhong, tang, soNguoi, trangThai);

            if (PhongDAO.suaPhong(p)) {
                JOptionPane.showMessageDialog(this, "Sửa phòng thành công!");
                String toaNha = lstToaNha.getSelectedValue();
                if (toaNha != null) {
                    int tid = toaNhaMap.get(toaNha);
                    loadPhongByToaNha(tid);
                } else {
                    loadAllPhong();
                }
                XoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi sửa phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void XoaPhong() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa phòng " + txtSoPhong.getText() + "?\n" +
            "(Không thể xóa nếu phòng đang có sinh viên ở)",
            "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                if (PhongDAO.xoaPhong(id)) {
                    JOptionPane.showMessageDialog(this, "Xóa phòng thành công!");
                    String toaNha = lstToaNha.getSelectedValue();
                    if (toaNha != null) {
                        int tid = toaNhaMap.get(toaNha);
                        loadPhongByToaNha(tid);
                    } else {
                        loadAllPhong();
                    }
                    XoaTrang();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Lỗi xóa phòng!\nPhòng có thể đang có hợp đồng hoặc sinh viên liên quan.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void XoaTrang() {
        txtId.setText("");
        txtSoPhong.setText("");
        txtTang.setText("");
        txtSoNguoi.setText("");
        cboLoaiPhong.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        tblPhong.clearSelection();
        svTableModel.setRowCount(0);
        txtSoPhong.requestFocus();
    }

    // Test chạy form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmPhong frm = new frmPhong();
            frm.setVisible(true);
        });
    }
}