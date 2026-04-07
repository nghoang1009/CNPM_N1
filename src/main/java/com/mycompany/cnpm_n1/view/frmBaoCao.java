package com.mycompany.cnpm_n1.view;

import com.mycompany.cnpm_n1.dao.BaoCaoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class frmBaoCao extends JFrame {

    public frmBaoCao() {
        super("Báo Cáo Quản Lý KTX");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Thêm menu bar
        setJMenuBar(MenuBarFactory.createMenuBar());

        // Tiêu đề
        JLabel lbTitle = new JLabel("BÁO CÁO VÀ THỐNG KÊ KTX", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        // ════════════════════════════════════════
        // Tạo tab panel
        // ════════════════════════════════════════
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Thống kê tổng quát
        tabbedPane.addTab("Thống Kê Tổng Quát", createThongKeTongQuat());

        // Tab 2: Báo cáo doanh thu
        tabbedPane.addTab("Báo Cáo Doanh Thu", createBaoCaoDoanhuTheoThang());

        // Tab 3: Báo cáo chiếm phòng
        tabbedPane.addTab("Báo Cáo Chiếm Phòng", createBaoCaoChiemPhong());

        // Tab 4: Hợp đồng sắp hết hạn
        tabbedPane.addTab("Hợp Đồng Sắp Hết Hạn", createBaoCaoHopDongSapHetHan());

        add(tabbedPane, BorderLayout.CENTER);

        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    // ════════════════════════════════════════
    // Tab 1: Thống kê tổng quát
    // ════════════════════════════════════════
    private JPanel createThongKeTongQuat() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Map<String, Object> stats = BaoCaoDAO.getThongKeTongQuat();

        // Thẻ thống kê
        panel.add(createStatCard("Tổng số phòng", stats.get("tongPhong")));
        panel.add(createStatCard("Phòng đang có người", stats.get("phongCoDayNguoi")));
        panel.add(createStatCard("Tổng sinh viên", stats.get("tongSinhVien")));
        panel.add(createStatCard("Hợp đồng hoạt động", stats.get("hopDongHoatDong")));
        panel.add(createStatCard("Tiền chưa trả", formatMoney(stats.get("tongTienChuaTra"))));

        return panel;
    }

    // ════════════════════════════════════════
    // Tab 2: Báo cáo doanh thu
    // ════════════════════════════════════════
    private JPanel createBaoCaoDoanhuTheoThang() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"Tháng", "Tiền Phòng", "Tiền Điện", "Tiền Nước", "Tổng Tiền", "Đã Trả", "Chưa Trả"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Map<String, Object> report = BaoCaoDAO.getBaoCaoDoanhuTheoThang();
        int soThang = report.get("soThang") != null ? (Integer) report.get("soThang") : 0;

        for (int i = 0; i < soThang; i++) {
            Map<String, Object> row = (Map<String, Object>) report.get("thang_" + i);
            if (row != null) {
                tableModel.addRow(new Object[]{
                    row.get("thang"),
                    formatMoney(row.get("tienPhong")),
                    formatMoney(row.get("tienDien")),
                    formatMoney(row.get("tienNuoc")),
                    formatMoney(row.get("tongTien")),
                    formatMoney(row.get("tienDaTra")),
                    formatMoney(row.get("tienChuaTra"))
                });
            }
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // ════════════════════════════════════════
    // Tab 3: Báo cáo chiếm phòng
    // ════════════════════════════════════════
    private JPanel createBaoCaoChiemPhong() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"Phòng", "Loại Phòng", "Người Hiện Tại", "Sức Chứa", "Sinh viên"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Map<String, Object> report = BaoCaoDAO.getBaoCaoChiemPhong();
        int soPhong = report.get("soPhong") != null ? (Integer) report.get("soPhong") : 0;

        for (int i = 0; i < soPhong; i++) {
            Map<String, Object> row = (Map<String, Object>) report.get("phong_" + i);
            if (row != null) {
                tableModel.addRow(new Object[]{
                    row.get("soPhong"),
                    row.get("loaiPhong"),
                    row.get("soNguoiHienTai"),
                    row.get("sucChua"),
                    row.get("sinhVien")
                });
            }
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // ════════════════════════════════════════
    // Tab 4: Hợp đồng sắp hết hạn
    // ════════════════════════════════════════
    private JPanel createBaoCaoHopDongSapHetHan() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"Mã SV", "Tên SV", "Phòng", "Ngày Kết Thúc", "Ngày Còn Lại"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Map<String, Object> report = BaoCaoDAO.getBaoCaoHopDongSapHetHan(90);
        int soHopDong = report.get("soHopDong") != null ? (Integer) report.get("soHopDong") : 0;

        for (int i = 0; i < soHopDong; i++) {
            Map<String, Object> row = (Map<String, Object>) report.get("hopdong_" + i);
            if (row != null) {
                tableModel.addRow(new Object[]{
                    row.get("maSinhVien"),
                    row.get("hoTen"),
                    row.get("soPhong"),
                    row.get("ngayKetThuc"),
                    row.get("soNgayConLai") + " ngày"
                });
            }
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setBackground(new Color(255, 255, 200)); // Màu vàng nhẹ để cảnh báo
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // ════════════════════════════════════════
    // Helper methods
    // ════════════════════════════════════════
    private JPanel createStatCard(String title, Object value) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setForeground(new Color(0, 102, 204));

        card.add(titleLabel, BorderLayout.PAGE_START);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
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
