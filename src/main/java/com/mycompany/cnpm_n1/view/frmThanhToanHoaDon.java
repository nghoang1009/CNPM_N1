package com.mycompany.cnpm_n1.view;

import com.mycompany.cnpm_n1.dao.HoaDonDAO;
import com.mycompany.cnpm_n1.dao.SinhVienDAO;
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
import java.math.BigDecimal;
import java.util.List;

public class frmThanhToanHoaDon extends JFrame {

    private JTable tblHoaDon;
    private DefaultTableModel tableModel;
    private JLabel lblSinhVienName, lblTongCong;
    private SinhVien currentSinhVien;

    public frmThanhToanHoaDon() {
        super("Thanh toán Hóa Đơn KTX");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Thêm menu bar
        setJMenuBar(MenuBarFactory.createMenuBar());

        // Tiêu đề
        JLabel lbTitle = new JLabel("THANH TOÁN HÓA ĐƠN KTX", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        // ════════════════════════════════════════
        // PANEL THÔNG TIN SINH VIÊN
        // ════════════════════════════════════════
        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        infoPanel.setBorder(new TitledBorder(border, "Thông tin sinh viên"));
        infoPanel.setPreferredSize(new Dimension(950, 50));

        infoPanel.add(new JLabel("Tên sinh viên:"));
        lblSinhVienName = new JLabel();
        lblSinhVienName.setFont(new Font("Arial", Font.BOLD, 12));
        infoPanel.add(lblSinhVienName);

        // ════════════════════════════════════════
        // PANEL BẢNG DANH SÁCH HÓA ĐƠN CHƯA TRẢ
        // ════════════════════════════════════════
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder(border, "Danh sách hóa đơn chưa thanh toán"));

        String[] columns = {"ID", "Tháng", "Phòng", "Tiền phòng", "Tiền điện", "Tiền nước", "Tổng tiền", "Trạng thái"};
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
        tableScrollPane.setPreferredSize(new Dimension(950, 250));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // ════════════════════════════════════════
        // PANEL TỔNG CỘNG VÀ CÁC NÚT BẤTM
        // ════════════════════════════════════════
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        totalPanel.add(new JLabel("Tổng số tiền phải thanh toán:"));
        lblTongCong = new JLabel("0 VNĐ");
        lblTongCong.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongCong.setForeground(Color.RED);
        totalPanel.add(lblTongCong);

        JPanel buttonPanel = new JPanel();
        JButton btnThanhToan = new JButton("Thanh toán hóa đơn");
        JButton btnRefresh = new JButton("Làm mới");
        JButton btnClose = new JButton("Đóng");

        buttonPanel.add(btnThanhToan);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClose);

        btnThanhToan.addActionListener(e -> handleThanhToan());
        btnRefresh.addActionListener(e -> loadDataFromDatabase());
        btnClose.addActionListener(e -> dispose());

        bottomPanel.add(totalPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        // ════════════════════════════════════════
        // Layout chính
        // ════════════════════════════════════════
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(infoPanel, BorderLayout.PAGE_START);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);

        setSize(1000, 600);
        setLocationRelativeTo(null);
        loadSinhVienInfo();
        loadDataFromDatabase();
    }

    private void loadSinhVienInfo() {
        try {
            currentSinhVien = SinhVienDAO.getSinhVienByTaiKhoanId(PermissionManager.getCurrentUserId());
            if (currentSinhVien != null) {
                lblSinhVienName.setText(currentSinhVien.getHoTen() + " (" + currentSinhVien.getMaSinhVien() + ")");
            } else {
                lblSinhVienName.setText("Không tìm thấy thông tin sinh viên");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromDatabase() {
        tableModel.setRowCount(0);
        if (currentSinhVien == null) return;

        List<HoaDon> list = HoaDonDAO.getHoaDonChuaTraBySinhVienId(currentSinhVien.getId());
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (HoaDon hd : list) {
            Phong p = PhongDAO.getPhongById(hd.getPhongId());

            tableModel.addRow(new Object[]{
                hd.getId(),
                hd.getThang(),
                p != null ? p.getSoPhong() : "N/A",
                formatMoney(hd.getTienPhong()),
                formatMoney(hd.getTienDien()),
                formatMoney(hd.getTienNuoc()),
                formatMoney(hd.getTongTien()),
                hd.getTrangThai()
            });

            if (hd.getTongTien() != null) {
                totalAmount = totalAmount.add(hd.getTongTien());
            }
        }

        lblTongCong.setText(String.format("%,.0f VNĐ", totalAmount));
    }

    private void handleThanhToan() {
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để thanh toán!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int hoaDonId = (int) tableModel.getValueAt(selectedRow, 0);
            HoaDon hd = HoaDonDAO.getHoaDonById(hoaDonId);

            if (hd == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra xem hóa đơn này có thuộc về sinh viên hiện tại không
            if (hd.getSinhVienId() != currentSinhVien.getId()) {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền thanh toán hóa đơn này!", 
                    "Lỗi phân quyền", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Hiển thị chi tiết hóa đơn
            String message = String.format(
                "Bạn muốn thanh toán hóa đơn này?\n\n" +
                "Tháng: %s\n" +
                "Tiền phòng: %,.0f VNĐ\n" +
                "Tiền điện: %,.0f VNĐ\n" +
                "Tiền nước: %,.0f VNĐ\n" +
                "Tổng cộng: %,.0f VNĐ",
                hd.getThang(),
                hd.getTienPhong() != null ? hd.getTienPhong().doubleValue() : 0,
                hd.getTienDien() != null ? hd.getTienDien().doubleValue() : 0,
                hd.getTienNuoc() != null ? hd.getTienNuoc().doubleValue() : 0,
                hd.getTongTien() != null ? hd.getTongTien().doubleValue() : 0
            );

            int confirm = JOptionPane.showConfirmDialog(this, message, "Xác nhận thanh toán", 
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                if (HoaDonDAO.capNhatTrangThaiHoaDon(hoaDonId, "da_tra")) {
                    JOptionPane.showMessageDialog(this, 
                        "Thanh toán thành công!\nHóa đơn đã được cập nhật.", 
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật trạng thái hóa đơn thất bại!", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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