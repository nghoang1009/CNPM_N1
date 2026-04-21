package com.mycompany.cnpm_n1.dao;

import com.mycompany.cnpm_n1.model.SinhVien;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test cho SinhVienDAO
 * Dựa trên schema thực tế:
 *   - gioi_tinh: enum('nam','nu') NOT NULL
 *   - ngay_sinh: date NOT NULL
 *   - truong: varchar(200) NOT NULL
 *   - ma_sinh_vien: UNIQUE
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SinhVienDAOTest {

    private static int testSinhVienId = -1;
    private static final String TEST_MA_SV = "SV_TEST_999";

    @BeforeAll
    static void setupAll() {
        List<SinhVien> existing = SinhVienDAO.timKiemSinhVien(TEST_MA_SV);
        for (SinhVien sv : existing) SinhVienDAO.xoaSinhVien(sv.getId());

        existing = SinhVienDAO.timKiemSinhVien("SV_TEST_NU");
        for (SinhVien sv : existing) SinhVienDAO.xoaSinhVien(sv.getId());
    }

    @AfterAll
    static void teardownAll() {
        if (testSinhVienId > 0) SinhVienDAO.xoaSinhVien(testSinhVienId);
    }

    // ── themSinhVien ──────────────────────────────────────────────

    @Test @Order(1)
    @DisplayName("Thêm sinh viên nam thành công")
    void testThemSinhVien_ThanhCong() {
        SinhVien sv = new SinhVien();
        sv.setMaSinhVien(TEST_MA_SV);
        sv.setHoTen("Nguyen Van Test");
        sv.setGioiTinh("nam");                       // enum: 'nam' hoặc 'nu'
        sv.setNgaySinh(LocalDate.of(2002, 5, 15));   // NOT NULL
        sv.setSoDienThoai("0909999999");
        sv.setTruong("Dai hoc Kiem Thu");             // NOT NULL

        boolean result = SinhVienDAO.themSinhVien(sv);
        assertTrue(result, "Thêm sinh viên phải trả về true");

        List<SinhVien> found = SinhVienDAO.timKiemSinhVien(TEST_MA_SV);
        assertFalse(found.isEmpty());
        testSinhVienId = found.get(0).getId();
    }

    @Test @Order(2)
    @DisplayName("Thêm sinh viên nữ thành công")
    void testThemSinhVien_GioiTinhNu() {
        SinhVien sv = new SinhVien();
        sv.setMaSinhVien("SV_TEST_NU");
        sv.setHoTen("Thi Nu Test");
        sv.setGioiTinh("nu");
        sv.setNgaySinh(LocalDate.of(2003, 8, 20));
        sv.setSoDienThoai("0922222222");
        sv.setTruong("Dai hoc Nu Test");

        boolean result = SinhVienDAO.themSinhVien(sv);
        assertTrue(result, "Thêm sinh viên nữ phải thành công");

        // Cleanup
        List<SinhVien> found = SinhVienDAO.timKiemSinhVien("SV_TEST_NU");
        if (!found.isEmpty()) SinhVienDAO.xoaSinhVien(found.get(0).getId());
    }

    @Test @Order(3)
    @DisplayName("Thêm sinh viên với ngày sinh null - DB từ chối (NOT NULL)")
    void testThemSinhVien_NgaySinhNull() {
        SinhVien sv = new SinhVien();
        sv.setMaSinhVien("SV_TEST_NULL");
        sv.setHoTen("Test Null");
        sv.setGioiTinh("nu");
        sv.setNgaySinh(null);   // vi phạm NOT NULL
        sv.setTruong("Truong Test");

        boolean result = SinhVienDAO.themSinhVien(sv);
        assertFalse(result, "ngay_sinh NOT NULL trong DB, phải trả về false");
    }

    // ── getSinhVienById ───────────────────────────────────────────

    @Test @Order(4)
    @DisplayName("Lấy sinh viên theo ID hợp lệ - kiểm tra dữ liệu đúng")
    void testGetSinhVienById_CoTonTai() {
        assumeTestSinhVienExist();
        SinhVien sv = SinhVienDAO.getSinhVienById(testSinhVienId);

        assertNotNull(sv);
        assertEquals(TEST_MA_SV, sv.getMaSinhVien());
        assertEquals("Nguyen Van Test", sv.getHoTen());
        assertEquals("nam", sv.getGioiTinh());       // DB enum lưu chữ thường
        assertEquals(LocalDate.of(2002, 5, 15), sv.getNgaySinh());
    }

    @Test @Order(5)
    @DisplayName("Lấy sinh viên theo ID không tồn tại trả về null")
    void testGetSinhVienById_KhongTonTai() {
        assertNull(SinhVienDAO.getSinhVienById(999999));
    }

    // ── getAllSinhVien ─────────────────────────────────────────────

    @Test @Order(6)
    @DisplayName("getAllSinhVien trả về danh sách không null")
    void testGetAllSinhVien_KhongNull() {
        assertNotNull(SinhVienDAO.getAllSinhVien());
    }

    @Test @Order(7)
    @DisplayName("getAllSinhVien có ít nhất 30 bản ghi (dữ liệu mẫu)")
    void testGetAllSinhVien_CoItNhat30() {
        assertTrue(SinhVienDAO.getAllSinhVien().size() >= 30);
    }

    // ── suaSinhVien ───────────────────────────────────────────────

    @Test @Order(8)
    @DisplayName("Cập nhật thông tin sinh viên thành công")
    void testSuaSinhVien_ThanhCong() {
        assumeTestSinhVienExist();
        SinhVien sv = SinhVienDAO.getSinhVienById(testSinhVienId);
        assertNotNull(sv);

        sv.setHoTen("Nguyen Van Da Sua");
        sv.setSoDienThoai("0988888888");
        sv.setTruong("Dai hoc Da Cap Nhat");

        assertTrue(SinhVienDAO.suaSinhVien(sv));

        SinhVien updated = SinhVienDAO.getSinhVienById(testSinhVienId);
        assertEquals("Nguyen Van Da Sua", updated.getHoTen());
        assertEquals("0988888888", updated.getSoDienThoai());
    }

    @Test @Order(9)
    @DisplayName("Cập nhật sinh viên ID không tồn tại trả về false")
    void testSuaSinhVien_KhongTonTai() {
        SinhVien sv = new SinhVien();
        sv.setId(999999);
        sv.setMaSinhVien("SV_FAKE");
        sv.setHoTen("Fake");
        sv.setGioiTinh("nam");
        sv.setNgaySinh(LocalDate.of(2000, 1, 1));
        sv.setTruong("Truong Fake");

        assertFalse(SinhVienDAO.suaSinhVien(sv));
    }

    // ── timKiemSinhVien ───────────────────────────────────────────

    @Test @Order(10)
    @DisplayName("Tìm kiếm theo mã sinh viên đúng")
    void testTimKiemSinhVien_TheoMa() {
        List<SinhVien> result = SinhVienDAO.timKiemSinhVien(TEST_MA_SV);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(sv -> sv.getMaSinhVien().equals(TEST_MA_SV)));
    }

    @Test @Order(11)
    @DisplayName("Tìm kiếm theo tên có trong DB mẫu")
    void testTimKiemSinhVien_TheoTen() {
        // DB mẫu có nhiều SV tên "Nguyen"
        assertFalse(SinhVienDAO.timKiemSinhVien("Nguyen").isEmpty());
    }

    @Test @Order(12)
    @DisplayName("Tìm kiếm từ khóa không tồn tại trả về danh sách rỗng")
    void testTimKiemSinhVien_KhongCoKetQua() {
        List<SinhVien> result = SinhVienDAO.timKiemSinhVien("XXXXXXXXNOTEXIST999");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test @Order(13)
    @DisplayName("Tìm kiếm chuỗi rỗng không crash")
    void testTimKiemSinhVien_ChuoiRong() {
        assertDoesNotThrow(() -> assertNotNull(SinhVienDAO.timKiemSinhVien("")));
    }

    // ── isMaTonTai ────────────────────────────────────────────────

    @Test @Order(14)
    @DisplayName("Mã đã tồn tại - trả về true")
    void testIsMaTonTai_DaTonTai() {
        assertTrue(SinhVienDAO.isMaTonTai(TEST_MA_SV, 0));
    }

    @Test @Order(15)
    @DisplayName("Mã chưa tồn tại - trả về false")
    void testIsMaTonTai_ChuaTonTai() {
        assertFalse(SinhVienDAO.isMaTonTai("SV_KHONG_CO_XYZ", 0));
    }

    @Test @Order(16)
    @DisplayName("Mã trùng chính mình khi sửa - trả về false")
    void testIsMaTonTai_ChinhMinhKhiSua() {
        assumeTestSinhVienExist();
        assertFalse(SinhVienDAO.isMaTonTai(TEST_MA_SV, testSinhVienId));
    }

    // ── xoaSinhVien ───────────────────────────────────────────────

    @Test @Order(17)
    @DisplayName("Xóa sinh viên hợp lệ thành công")
    void testXoaSinhVien_ThanhCong() {
        assumeTestSinhVienExist();

        assertTrue(SinhVienDAO.xoaSinhVien(testSinhVienId));
        assertNull(SinhVienDAO.getSinhVienById(testSinhVienId), "Đã xóa thì không tìm được nữa");

        testSinhVienId = -1;
    }

    @Test @Order(18)
    @DisplayName("Xóa sinh viên ID không tồn tại trả về false")
    void testXoaSinhVien_KhongTonTai() {
        assertFalse(SinhVienDAO.xoaSinhVien(999999));
    }

    // ── getSinhVienMap ────────────────────────────────────────────

    @Test @Order(19)
    @DisplayName("getSinhVienMap trả về Map không null, không rỗng, đúng format")
    void testGetSinhVienMap() {
        var map = SinhVienDAO.getSinhVienMap();
        assertNotNull(map);
        assertFalse(map.isEmpty());
        // Format key phải là "MaSV - HoTen"
        String firstKey = map.keySet().iterator().next();
        assertTrue(firstKey.contains(" - "), "Key phải có dạng 'MaSV - HoTen'");
    }

    // ── Helper ────────────────────────────────────────────────────

    private void assumeTestSinhVienExist() {
        org.junit.jupiter.api.Assumptions.assumeTrue(
            testSinhVienId > 0,
            "Bỏ qua: sinh viên test chưa được tạo"
        );
    }
}