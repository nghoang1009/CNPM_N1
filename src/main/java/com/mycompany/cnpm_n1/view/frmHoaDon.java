package com.mycompany.cnpm_n1.view;

import com.mycompany.cnpm_n1.dao.HoaDonDAO;
import com.mycompany.cnpm_n1.dao.SinhVienDAO;
import com.mycompany.cnpm_n1.dao.HopDongDAO;
import com.mycompany.cnpm_n1.dao.PhongDAO;
import com.mycompany.cnpm_n1.model.HoaDon;
import com.mycompany.cnpm_n1.model.SinhVien;
import com.mycompany.cnpm_n1.model.Phong;
import com.mycompany.cnpm_n1.util.PermissionManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class frmHoaDon extends JFrame {

    private JTable tblHoaDon;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtTienPhong, txtTienDien, txtTienNuoc;
    private JComboBox<String> cboSinhVien, cboHopDong, cboTrangThai;
    private JSpinner spinThang;
    private Map<String, Integer> sinhVienMap, hopDongMap;

    private static final DateTimeFormatter MONTH_FMT = DateTimeFormatter.ofPattern("MM/yyyy");

    public frmHoaDon() {
        super("Quản lý Hóa Đơn KTX");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Thêm menu bar
        setJMenuBar(MenuBarFactory.createMenuBar());

        // Tiêu đề
        JLabel lbTitle = new JLabel("QUẢN LÝ HÓA ĐƠN KTX", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        // ── Load lookup maps ──
        sinhVienMap = SinhVienDAO.getSinhVienMap();
        hopDongMap = HopDongDAO.getHopDongMap();

        // ════════════════════════════════════════
        // PANEL GIỮA: Bảng danh sách
        // ════════════════════════════════════════
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder(border, "Danh sách hóa đơn"));

        String[] columns = {"ID", "Sinh viên", "Phòng", "Tháng", "Phòng", "Điện", "Nước", "Tổng tiền", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblHoaDon = new JTable(tableModel);
        tblHoaDon.setRowHeight(24);
        tblHoaDon.getColumnModel().getColumn(0).setMinWidth(0);
        tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(0);
        tblHoaDon.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane tableScrollPane = new JScrollPane(tblHoaDon);
        tableScrollPane.setPreferredSize(new Dimension(950, 200));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblHoaDon.getSelectedRow();
                if (row >= 0) {
                    loadFormFromTable(row);
                }
            }
        });

        // ════════════════════════════════════════
        // PANEL DƯỚI: Form nhập liệu
        // ════════════════════════════════════════
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        formPanel.setBorder(new TitledBorder(border, "Thông tin hóa đơn"));
        formPanel.setPreferredSize(new Dimension(950, 150));

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

        // Hợp đồng
        formPanel.add(new JLabel("Hợp đồng:"));
        cboHopDong = new JComboBox<>();
        hopDongMap.keySet().forEach(cboHopDong::addItem);
        formPanel.add(cboHopDong);

        // Tháng
        formPanel.add(new JLabel("Tháng (MM/YYYY):"));
        spinThang = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinThang, "MM/yyyy");
        spinThang.setEditor(editor);
        formPanel.add(spinThang);

        // Tiền phòng
        formPanel.add(new JLabel("Tiền phòng (VNĐ):"));
        txtTienPhong = new JTextField();
        formPanel.add(txtTienPhong);

        // Tiền điện
        formPanel.add(new JLabel("Tiền điện (VNĐ):"));
        txtTienDien = new JTextField();
        formPanel.add(txtTienDien);

        // Tiền nước
        formPanel.add(new JLabel("Tiền nước (VNĐ):"));
        txtTienNuoc = new JTextField();
        formPanel.add(txtTienNuoc);

        // Trạng thái
        formPanel.add(new JLabel("Trạng thái:"));
        cboTrangThai = new JComboBox<>(new String[]{"chua_tra", "da_tra"});
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
            if (PermissionManager.canEditHoaDon()) {
                handleThemHoaDon();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền thêm hóa đơn", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnEdit.addActionListener(e -> {
            if (PermissionManager.canEditHoaDon()) {
                handleSuaHoaDon();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền sửa hóa đơn", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnDelete.addActionListener(e -> {
            if (PermissionManager.canEditHoaDon()) {
                handleXoaHoaDon();
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa hóa đơn", 
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

        setSize(1000, 600);
        setLocationRelativeTo(null);
        loadDataFromDatabase();
        
        // Disable Add/Delete buttons nếu sinh viên (chỉ cho thanh toán)
        if (PermissionManager.isSinhVien()) {
            for (java.awt.Component comp : btnPanel.getComponents()) {
                if (comp instanceof JButton) {
                    String text = ((JButton) comp).getText();
                    if (text.equals("Thêm mới") || text.equals("Xóa")) {
                        comp.setEnabled(false);
                    }
                }
            }
        }
    }

    private void loadDataFromDatabase() {
        tableModel.setRowCount(0);
        List<HoaDon> list = HoaDonDAO.getAllHoaDon();
        for (HoaDon hd : list) {
            SinhVien sv = SinhVienDAO.getSinhVienById(hd.getSinhVienId());
            Phong p = PhongDAO.getPhongById(hd.getPhongId());
            
            tableModel.addRow(new Object[]{
                hd.getId(),
                sv != null ? sv.getHoTen() : "N/A",
                p != null ? p.getSoPhong() : "N/A",
                hd.getThang(),
                formatMoney(hd.getTienPhong()),
                formatMoney(hd.getTienDien()),
                formatMoney(hd.getTienNuoc()),
                formatMoney(hd.getTongTien()),
                hd.getTrangThai()
            });
        }
    }

    private void loadFormFromTable(int row) {
        try {
            int id = (int) tableModel.getValueAt(row, 0);
            HoaDon hd = HoaDonDAO.getHoaDonById(id);
            if (hd != null) {
                txtId.setText(String.valueOf(hd.getId()));
                
                for (Map.Entry<String, Integer> entry : sinhVienMap.entrySet()) {
                    if (entry.getValue() == hd.getSinhVienId()) {
                        cboSinhVien.setSelectedItem(entry.getKey());
                        break;
                    }
                }
                
                for (Map.Entry<String, Integer> entry : hopDongMap.entrySet()) {
                    if (entry.getValue() == hd.getHopDongId()) {
                        cboHopDong.setSelectedItem(entry.getKey());
                        break;
                    }
                }
                
                spinThang.setValue(java.sql.Date.valueOf(hd.getThang()));
                txtTienPhong.setText(hd.getTienPhong() != null ? hd.getTienPhong().toString() : "0");
                txtTienDien.setText(hd.getTienDien() != null ? hd.getTienDien().toString() : "0");
                txtTienNuoc.setText(hd.getTienNuoc() != null ? hd.getTienNuoc().toString() : "0");
                cboTrangThai.setSelectedItem(hd.getTrangThai());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleThemHoaDon() {
        try {
            Integer sinhVienId = sinhVienMap.get((String) cboSinhVien.getSelectedItem());
            Integer hopDongId = hopDongMap.get((String) cboHopDong.getSelectedItem());
            
            java.util.Date selectedDate = (java.util.Date) spinThang.getValue();
            LocalDate thang = new java.sql.Date(selectedDate.getTime()).toLocalDate();
            
            BigDecimal tienPhong = new BigDecimal(txtTienPhong.getText().isEmpty() ? "0" : txtTienPhong.getText());
            BigDecimal tienDien = new BigDecimal(txtTienDien.getText().isEmpty() ? "0" : txtTienDien.getText());
            BigDecimal tienNuoc = new BigDecimal(txtTienNuoc.getText().isEmpty() ? "0" : txtTienNuoc.getText());
            BigDecimal tongTien = tienPhong.add(tienDien).add(tienNuoc);
            String trangThai = (String) cboTrangThai.getSelectedItem();

            HoaDon hd = new HoaDon(hopDongId, sinhVienId, 0, thang, tienPhong, tienDien, tienNuoc, tongTien, trangThai);
            if (HoaDonDAO.themHoaDon(hd)) {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!");
                clearForm();
                loadDataFromDatabase();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: Số tiền phải là số!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleSuaHoaDon() {
        try {
            int id = Integer.parseInt(txtId.getText());
            if (id <= 0) {
                JOptionPane.showMessageDialog(this, "Lỗi: Chọn hóa đơn để cập nhật!");
                return;
            }

            Integer sinhVienId = sinhVienMap.get((String) cboSinhVien.getSelectedItem());
            Integer hopDongId = hopDongMap.get((String) cboHopDong.getSelectedItem());
            
            java.util.Date selectedDate = (java.util.Date) spinThang.getValue();
            LocalDate thang = new java.sql.Date(selectedDate.getTime()).toLocalDate();
            
            BigDecimal tienPhong = new BigDecimal(txtTienPhong.getText().isEmpty() ? "0" : txtTienPhong.getText());
            BigDecimal tienDien = new BigDecimal(txtTienDien.getText().isEmpty() ? "0" : txtTienDien.getText());
            BigDecimal tienNuoc = new BigDecimal(txtTienNuoc.getText().isEmpty() ? "0" : txtTienNuoc.getText());
            BigDecimal tongTien = tienPhong.add(tienDien).add(tienNuoc);
            String trangThai = (String) cboTrangThai.getSelectedItem();

            HoaDon hd = new HoaDon(id, hopDongId, sinhVienId, 0, thang, tienPhong, tienDien, tienNuoc, tongTien, trangThai);
            if (HoaDonDAO.suaHoaDon(hd)) {
                JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công!");
                clearForm();
                loadDataFromDatabase();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: Số tiền phải là số!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleXoaHoaDon() {
        int row = tblHoaDon.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            if (HoaDonDAO.xoaHoaDon(id)) {
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!");
                clearForm();
                loadDataFromDatabase();
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtTienPhong.setText("");
        txtTienDien.setText("");
        txtTienNuoc.setText("");
        if (cboSinhVien.getItemCount() > 0) cboSinhVien.setSelectedIndex(0);
        if (cboHopDong.getItemCount() > 0) cboHopDong.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
    }

    private String formatMoney(Object value) {
        if (value == null) return "0";
        try {
            double num = Double.parseDouble(value.toString());
            return String.format("%,.0f", num);
        } catch (Exception e) {
            return value.toString();
        }
    }
}
