package com.mycompany.cnpm_n1.view;

import com.mycompany.cnpm_n1.dao.HopDongDAO;
import com.mycompany.cnpm_n1.dao.SinhVienDAO;
import com.mycompany.cnpm_n1.dao.PhongDAO;
import com.mycompany.cnpm_n1.model.HopDong;
import com.mycompany.cnpm_n1.model.SinhVien;
import com.mycompany.cnpm_n1.model.Phong;
import com.mycompany.cnpm_n1.util.PermissionManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

public class frmHopDong extends JFrame {

    private JTable tblHopDong;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtNgayBatDau, txtNgayKetThuc;
    private JComboBox<String> cboSinhVien, cboPhong, cboTrangThai;
    private Map<String, Integer> sinhVienMap, phongMap;
    private SinhVien currentSinhVien;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public frmHopDong() {
        super("Quản lý Hợp Đồng KTX");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Thêm menu bar
        setJMenuBar(MenuBarFactory.createMenuBar());

        // Tiêu đề
        JLabel lbTitle = new JLabel("QUẢN LÝ HỢP ĐỒNG KTX", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        // ── Load lookup maps ──
        sinhVienMap = SinhVienDAO.getSinhVienMap();
        phongMap = PhongDAO.getPhongMap();

        // Lấy thông tin sinh viên hiện tại nếu là sinh viên
        if (PermissionManager.isSinhVien()) {
            currentSinhVien = SinhVienDAO.getSinhVienByTaiKhoanId(PermissionManager.getCurrentUserId());
        }

        // ════════════════════════════════════════
        // PANEL GIỮA: Bảng danh sách
        // ════════════════════════════════════════
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder(border, "Danh sách hợp đồng"));

        String[] columns = {"ID", "Mã SV", "Sinh viên", "Phòng", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblHopDong = new JTable(tableModel);
        tblHopDong.setRowHeight(24);
        tblHopDong.getColumnModel().getColumn(0).setMinWidth(0);
        tblHopDong.getColumnModel().getColumn(0).setMaxWidth(0);
        tblHopDong.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane tableScrollPane = new JScrollPane(tblHopDong);
        tableScrollPane.setPreferredSize(new Dimension(800, 200));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        tblHopDong.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblHopDong.getSelectedRow();
                if (row >= 0) {
                    loadFormFromTable(row);
                }
            }
        });

        // ════════════════════════════════════════
        // PANEL DƯỚI: Form nhập liệu
        // ════════════════════════════════════════
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(new TitledBorder(border, "Thông tin hợp đồng"));
        formPanel.setPreferredSize(new Dimension(800, 200));

        // ID
        formPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        formPanel.add(txtId);

        // Sinh viên
        formPanel.add(new JLabel("Sinh viên:"));
        cboSinhVien = new JComboBox<>();
        sinhVienMap.keySet().forEach(cboSinhVien::addItem);
        formPanel.add(cboSinhVien);

        // Phòng
        formPanel.add(new JLabel("Phòng:"));
        cboPhong = new JComboBox<>();
        phongMap.keySet().forEach(cboPhong::addItem);
        formPanel.add(cboPhong);

        // Ngày bắt đầu
        formPanel.add(new JLabel("Ngày bắt đầu (dd/MM/yyyy):"));
        txtNgayBatDau = new JTextField();
        formPanel.add(txtNgayBatDau);

        // Ngày kết thúc
        formPanel.add(new JLabel("Ngày kết thúc (dd/MM/yyyy):"));
        txtNgayKetThuc = new JTextField();
        formPanel.add(txtNgayKetThuc);

        // Trạng thái
        formPanel.add(new JLabel("Trạng thái:"));
        cboTrangThai = new JComboBox<>(new String[]{"hieu_luc", "het_han", "huy"});
        formPanel.add(cboTrangThai);

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
            if (PermissionManager.canEditHopDong()) {
                handleThemHopDong();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền thêm hợp đồng", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEdit.addActionListener(e -> {
            if (PermissionManager.canEditHopDong()) {
                handleSuaHopDong();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền sửa hợp đồng", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            if (PermissionManager.canEditHopDong()) {
                handleXoaHopDong();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa hợp đồng", 
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

        // Xử lý giao diện cho sinh viên
        if (PermissionManager.isSinhVien()) {
            // Ẩn các nút thêm/sửa/xóa
            btnAdd.setVisible(false);
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);

            // Vô hiệu hóa form edit
            for (java.awt.Component comp : formPanel.getComponents()) {
                if (comp != null && !(comp instanceof JLabel)) {
                    comp.setEnabled(false);
                }
            }
        }
    }

    private void loadDataFromDatabase() {
        tableModel.setRowCount(0);
        List<HopDong> list;

        // Sinh viên chỉ xem hợp đồng của mình
        if (PermissionManager.isSinhVien() && currentSinhVien != null) {
            list = HopDongDAO.getHopDongBySinhVienId(currentSinhVien.getId());
        } else {
            // Admin/Nhân viên xem tất cả hợp đồng
            list = HopDongDAO.getAllHopDong();
        }

        for (HopDong hd : list) {
            SinhVien sv = SinhVienDAO.getSinhVienById(hd.getSinhVienId());
            Phong p = PhongDAO.getPhongById(hd.getPhongId());
            
            tableModel.addRow(new Object[]{
                hd.getId(),
                sv != null ? sv.getMaSinhVien() : "N/A",
                sv != null ? sv.getHoTen() : "N/A",
                p != null ? p.getSoPhong() : "N/A",
                hd.getNgayBatDau().format(DATE_FMT),
                hd.getNgayKetThuc().format(DATE_FMT),
                hd.getTrangThai()
            });
        }
    }

    private void loadFormFromTable(int row) {
        int id = (int) tableModel.getValueAt(row, 0);
        HopDong hd = HopDongDAO.getHopDongById(id);
        if (hd != null) {
            txtId.setText(String.valueOf(hd.getId()));
            
            for (Map.Entry<String, Integer> entry : sinhVienMap.entrySet()) {
                if (entry.getValue() == hd.getSinhVienId()) {
                    cboSinhVien.setSelectedItem(entry.getKey());
                    break;
                }
            }
            
            for (Map.Entry<String, Integer> entry : phongMap.entrySet()) {
                if (entry.getValue() == hd.getPhongId()) {
                    cboPhong.setSelectedItem(entry.getKey());
                    break;
                }
            }
            
            txtNgayBatDau.setText(hd.getNgayBatDau().format(DATE_FMT));
            txtNgayKetThuc.setText(hd.getNgayKetThuc().format(DATE_FMT));
            cboTrangThai.setSelectedItem(hd.getTrangThai());
        }
    }

    private void handleThemHopDong() {
        try {
            Integer sinhVienId = sinhVienMap.get((String) cboSinhVien.getSelectedItem());
            Integer phongId = phongMap.get((String) cboPhong.getSelectedItem());
            LocalDate ngayBatDau = LocalDate.parse(txtNgayBatDau.getText(), DATE_FMT);
            LocalDate ngayKetThuc = LocalDate.parse(txtNgayKetThuc.getText(), DATE_FMT);
            String trangThai = (String) cboTrangThai.getSelectedItem();

            HopDong hd = new HopDong(sinhVienId, phongId, ngayBatDau, ngayKetThuc, trangThai);
            if (HopDongDAO.themHopDong(hd)) {
                JOptionPane.showMessageDialog(this, "Thêm hợp đồng thành công!");
                clearForm();
                loadDataFromDatabase();
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: Định dạng ngày không hợp lệ!");
        }
    }

    private void handleSuaHopDong() {
        try {
            int id = Integer.parseInt(txtId.getText());
            if (id <= 0) {
                JOptionPane.showMessageDialog(this, "Lỗi: Chọn hợp đồng để cập nhật!");
                return;
            }

            Integer sinhVienId = sinhVienMap.get((String) cboSinhVien.getSelectedItem());
            Integer phongId = phongMap.get((String) cboPhong.getSelectedItem());
            LocalDate ngayBatDau = LocalDate.parse(txtNgayBatDau.getText(), DATE_FMT);
            LocalDate ngayKetThuc = LocalDate.parse(txtNgayKetThuc.getText(), DATE_FMT);
            String trangThai = (String) cboTrangThai.getSelectedItem();

            HopDong hd = new HopDong(id, sinhVienId, phongId, ngayBatDau, ngayKetThuc, trangThai);
            if (HopDongDAO.suaHopDong(hd)) {
                JOptionPane.showMessageDialog(this, "Cập nhật hợp đồng thành công!");
                clearForm();
                loadDataFromDatabase();
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: Định dạng ngày không hợp lệ!");
        }
    }

    private void handleXoaHopDong() {
        int row = tblHopDong.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hợp đồng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            if (HopDongDAO.xoaHopDong(id)) {
                JOptionPane.showMessageDialog(this, "Xóa hợp đồng thành công!");
                clearForm();
                loadDataFromDatabase();
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        if (cboSinhVien.getItemCount() > 0) cboSinhVien.setSelectedIndex(0);
        if (cboPhong.getItemCount() > 0) cboPhong.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
    }
}
