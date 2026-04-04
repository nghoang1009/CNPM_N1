package com.mycompany.cnpm_n1.view;

import com.mycompany.cnpm_n1.dao.PhongDAO;
import com.mycompany.cnpm_n1.dao.SinhVienDAO;
import com.mycompany.cnpm_n1.model.Phong;
import com.mycompany.cnpm_n1.model.SinhVien;

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

public class frmSinhVien extends JFrame {

    // ── JList danh sách phòng ──
    private JList<String> lstPhong;
    private DefaultListModel<String> phongListModel;

    // ── JTable danh sách sinh viên ──
    private JTable tblSinhVien;
    private DefaultTableModel tableModel;

    // ── Form nhập liệu sinh viên ──
    private JTextField txtId, txtMaSV, txtHoTen, txtNgaySinh, txtSDT, txtTruong;
    private JComboBox<String> cboGioiTinh;

    // ── Map lookup phòng ──
    private Map<String, Integer> phongMap;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public frmSinhVien() {
        super("Quản lý sinh viên và phòng KTX");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Tiêu đề
        JLabel lbTitle = new JLabel("QUẢN LÝ SINH VIÊN KTX", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        // ── Load lookup maps ──
        phongMap = PhongDAO.getPhongMap();

        // ════════════════════════════════════════
        // PANEL TRÁI: danh sách phòng
        // ════════════════════════════════════════
        JPanel pnLeft = new JPanel(new BorderLayout());
        phongListModel = new DefaultListModel<>();

        lstPhong = new JList<>(phongListModel);
        JScrollPane scrollPane = new JScrollPane(lstPhong);
        scrollPane.setBorder(new TitledBorder(border, "Danh sách phòng"));

        JPanel pnBtn1 = new JPanel();
        JButton btnAddPhong = new JButton("Thêm");
        JButton btnEditPhong = new JButton("Sửa");
        JButton btnDeletePhong = new JButton("Xóa");

        pnBtn1.add(btnAddPhong);
        pnBtn1.add(btnEditPhong);
        pnBtn1.add(btnDeletePhong);

        pnLeft.add(scrollPane, BorderLayout.CENTER);
        pnLeft.add(pnBtn1, BorderLayout.PAGE_END);

        // ════════════════════════════════════════
        // PANEL PHẢI: danh sách và form sinh viên
        // ════════════════════════════════════════
        JPanel pnRight = new JPanel(new BorderLayout());

        // ── Bảng danh sách sinh viên ──
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder(border, "Danh sách sinh viên trong phòng"));

        String[] columns = {"ID", "Mã SV", "Họ và tên", "Giới tính", "Ngày sinh", "SĐT", "Trường"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblSinhVien = new JTable(tableModel);
        tblSinhVien.setRowHeight(24);
        // Ẩn cột ID
        tblSinhVien.getColumnModel().getColumn(0).setMinWidth(0);
        tblSinhVien.getColumnModel().getColumn(0).setMaxWidth(0);
        tblSinhVien.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane tableScrollPane = new JScrollPane(tblSinhVien);
        tableScrollPane.setPreferredSize(new Dimension(500, 150));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        pnRight.add(tablePanel, BorderLayout.PAGE_START);

        // ── Panel thông tin sinh viên ──
        JPanel pnTT = new JPanel(new GridLayout(7, 2, 5, 5));
        pnTT.setBorder(new TitledBorder(border, "Thông tin sinh viên"));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtMaSV = new JTextField(30);
        txtHoTen = new JTextField(30);
        cboGioiTinh = new JComboBox<>(new String[]{"nam", "nu"});
        txtNgaySinh = new JTextField(30);
        txtSDT = new JTextField(30);
        txtTruong = new JTextField(30);

        pnTT.add(new JLabel("ID:"));
        pnTT.add(txtId);
        pnTT.add(new JLabel("Mã sinh viên:"));
        pnTT.add(txtMaSV);
        pnTT.add(new JLabel("Họ và tên:"));
        pnTT.add(txtHoTen);
        pnTT.add(new JLabel("Giới tính:"));
        pnTT.add(cboGioiTinh);
        pnTT.add(new JLabel("Ngày sinh (dd/MM/yyyy):"));
        pnTT.add(txtNgaySinh);
        pnTT.add(new JLabel("Số điện thoại:"));
        pnTT.add(txtSDT);
        pnTT.add(new JLabel("Trường:"));
        pnTT.add(txtTruong);

        // ── Panel nút sinh viên ──
        JPanel pnBtn2 = new JPanel();
        JButton btThem = new JButton("Thêm");
        JButton btSua = new JButton("Sửa");
        JButton btXoa = new JButton("Xóa");
        JButton btXoaTrang = new JButton("Xóa trắng");

        pnBtn2.add(btThem);
        pnBtn2.add(btSua);
        pnBtn2.add(btXoa);
        pnBtn2.add(btXoaTrang);

        pnRight.add(pnTT, BorderLayout.CENTER);
        pnRight.add(pnBtn2, BorderLayout.PAGE_END);

        // ════════════════════════════════════════
        // JSplitPane: trái + phải
        // ════════════════════════════════════════
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight);
        jSplitPane.setDividerLocation(280);
        add(jSplitPane, BorderLayout.CENTER);

        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Load dữ liệu ban đầu
        loadPhong();
        loadAllSinhVien();

        // ════════════════════════════════════════
        // EVENTS
        // ════════════════════════════════════════

        // Click vào phòng → load sinh viên của phòng đó
        lstPhong.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedPhong = lstPhong.getSelectedValue();
                if (selectedPhong != null) {
                    Integer phongId = phongMap.get(selectedPhong);
                    if (phongId != null) {
                        loadSinhVienByPhong(phongId);
                    }
                }
            }
        });

        // Click vào dòng trong bảng sinh viên → điền vào form
        tblSinhVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblSinhVien.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(tableModel.getValueAt(row, 0).toString());
                    txtMaSV.setText(tableModel.getValueAt(row, 1).toString());
                    txtHoTen.setText(tableModel.getValueAt(row, 2).toString());
                    cboGioiTinh.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                    txtNgaySinh.setText(tableModel.getValueAt(row, 4).toString());
                    txtSDT.setText(tableModel.getValueAt(row, 5) != null
                            ? tableModel.getValueAt(row, 5).toString() : "");
                    txtTruong.setText(tableModel.getValueAt(row, 6).toString());
                }
            }
        });

        // ── Events PHÒNG ──
        btnAddPhong.addActionListener(e -> ThemPhong());
        btnEditPhong.addActionListener(e -> SuaPhong());
        btnDeletePhong.addActionListener(e -> XoaPhong());

        // ── Events SINH VIÊN ──
        btThem.addActionListener(e -> ThemSV());
        btSua.addActionListener(e -> SuaSV());
        btXoa.addActionListener(e -> XoaSV());
        btXoaTrang.addActionListener(e -> XoaTrang());
    }

    // ════════════════════════════════════════
    // LOAD PHÒNG
    // ════════════════════════════════════════

    private void loadPhong() {
        phongListModel.clear();
        phongMap.clear();

        List<Phong> listPhong = PhongDAO.getAllPhong();
        for (Phong p : listPhong) {
            String tenToa = PhongDAO.getTenToaNha(p.getToaNhaId());
            String tenLoai = PhongDAO.getTenLoaiPhong(p.getLoaiPhongId());
            String displayText = p.getSoPhong() + " (" + tenToa + " - " + tenLoai + ")";
            phongListModel.addElement(displayText);
            phongMap.put(displayText, p.getId());
        }
    }

    // ════════════════════════════════════════
    // LOAD SINH VIÊN
    // ════════════════════════════════════════

    private void loadAllSinhVien() {
        loadSinhVien(SinhVienDAO.getAllSinhVien());
    }

    private void loadSinhVienByPhong(int phongId) {
        // Lấy danh sách sinh viên theo phòng từ database
        // Giả sử có method trong SinhVienDAO
        tableModel.setRowCount(0);
        List<SinhVien> list = PhongDAO.getSinhVienTrongPhong(phongId);
        if (list != null) {
            for (SinhVien sv : list) {
                tableModel.addRow(new Object[]{
                    sv.getId(),
                    sv.getMaSinhVien(),
                    sv.getHoTen(),
                    sv.getGioiTinh(),
                    sv.getNgaySinh() != null ? sv.getNgaySinh().format(DATE_FMT) : "",
                    sv.getSoDienThoai(),
                    sv.getTruong()
                });
            }
        }
        XoaTrang();
    }

    private void loadSinhVien(List<SinhVien> list) {
        tableModel.setRowCount(0);
        for (SinhVien sv : list) {
            tableModel.addRow(new Object[]{
                sv.getId(),
                sv.getMaSinhVien(),
                sv.getHoTen(),
                sv.getGioiTinh(),
                sv.getNgaySinh() != null ? sv.getNgaySinh().format(DATE_FMT) : "",
                sv.getSoDienThoai(),
                sv.getTruong()
            });
        }
    }

    // ════════════════════════════════════════
    // CRUD PHÒNG
    // ════════════════════════════════════════

    private void ThemPhong() {
        // Mở form thêm phòng (tạm thời chỉ trigger refresh từ frmPhong)
        JOptionPane.showMessageDialog(this, "Vui lòng sử dụng form Quản lý Phòng để thêm phòng!");
        loadPhong();
    }

    private void SuaPhong() {
        String selectedPhong = lstPhong.getSelectedValue();
        if (selectedPhong == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần sửa!");
            return;
        }
        JOptionPane.showMessageDialog(this, "Vui lòng sử dụng form Quản lý Phòng để sửa phòng!");
        loadPhong();
    }

    private void XoaPhong() {
        String selectedPhong = lstPhong.getSelectedValue();
        if (selectedPhong == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần xóa!");
            return;
        }

        Integer phongId = phongMap.get(selectedPhong);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa phòng " + selectedPhong + "?",
            "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (PhongDAO.xoaPhong(phongId)) {
                JOptionPane.showMessageDialog(this, "Xóa phòng thành công!");
                loadPhong();
                tableModel.setRowCount(0);
                XoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi xóa phòng!\nPhòng có thể đang có hợp đồng hoặc sinh viên liên quan.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ════════════════════════════════════════
    // CRUD SINH VIÊN
    // ════════════════════════════════════════

    private void ThemSV() {
        if (lstPhong.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng trước!");
            return;
        }

        if (txtMaSV.getText().trim().isEmpty() ||
            txtHoTen.getText().trim().isEmpty() ||
            txtNgaySinh.getText().trim().isEmpty() ||
            txtTruong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc!");
            return;
        }

        LocalDate ngaySinh = parseNgaySinh(txtNgaySinh.getText().trim());
        if (ngaySinh == null) return;

        String maSV = txtMaSV.getText().trim();
        if (SinhVienDAO.isMaTonTai(maSV, 0)) {
            JOptionPane.showMessageDialog(this, "Mã sinh viên \"" + maSV + "\" đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SinhVien sv = new SinhVien(
            maSV,
            txtHoTen.getText().trim(),
            (String) cboGioiTinh.getSelectedItem(),
            ngaySinh,
            txtSDT.getText().trim().isEmpty() ? null : txtSDT.getText().trim(),
            txtTruong.getText().trim()
        );

        if (SinhVienDAO.themSinhVien(sv)) {
            JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!");
            String selectedPhong = lstPhong.getSelectedValue();
            if (selectedPhong != null) {
                Integer phongId = phongMap.get(selectedPhong);
                loadSinhVienByPhong(phongId);
            } else {
                loadAllSinhVien();
            }
            XoaTrang();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi thêm sinh viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void SuaSV() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần sửa!");
            return;
        }

        if (txtMaSV.getText().trim().isEmpty() ||
            txtHoTen.getText().trim().isEmpty() ||
            txtNgaySinh.getText().trim().isEmpty() ||
            txtTruong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc!");
            return;
        }

        LocalDate ngaySinh = parseNgaySinh(txtNgaySinh.getText().trim());
        if (ngaySinh == null) return;

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String maSV = txtMaSV.getText().trim();

            if (SinhVienDAO.isMaTonTai(maSV, id)) {
                JOptionPane.showMessageDialog(this, "Mã sinh viên \"" + maSV + "\" đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SinhVien sv = new SinhVien(
                id, null,
                maSV,
                txtHoTen.getText().trim(),
                (String) cboGioiTinh.getSelectedItem(),
                ngaySinh,
                txtSDT.getText().trim().isEmpty() ? null : txtSDT.getText().trim(),
                txtTruong.getText().trim()
            );

            if (SinhVienDAO.suaSinhVien(sv)) {
                JOptionPane.showMessageDialog(this, "Sửa sinh viên thành công!");
                String selectedPhong = lstPhong.getSelectedValue();
                if (selectedPhong != null) {
                    Integer phongId = phongMap.get(selectedPhong);
                    loadSinhVienByPhong(phongId);
                } else {
                    loadAllSinhVien();
                }
                XoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi sửa sinh viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void XoaSV() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa sinh viên:\n" + txtHoTen.getText() + "?",
            "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                if (SinhVienDAO.xoaSinhVien(id)) {
                    JOptionPane.showMessageDialog(this, "Xóa sinh viên thành công!");
                    String selectedPhong = lstPhong.getSelectedValue();
                    if (selectedPhong != null) {
                        Integer phongId = phongMap.get(selectedPhong);
                        loadSinhVienByPhong(phongId);
                    } else {
                        loadAllSinhVien();
                    }
                    XoaTrang();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa sinh viên!\nSinh viên có thể đang có hợp đồng liên quan.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void XoaTrang() {
        txtId.setText("");
        txtMaSV.setText("");
        txtHoTen.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtNgaySinh.setText("");
        txtSDT.setText("");
        txtTruong.setText("");
        tblSinhVien.clearSelection();
        txtMaSV.requestFocus();
    }

    // ============ Helper ============

    private LocalDate parseNgaySinh(String s) {
        try {
            return LocalDate.parse(s, DATE_FMT);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                "Ngày sinh không đúng định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Test chạy form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmSinhVien frm = new frmSinhVien();
            frm.setVisible(true);
        });
    }
}