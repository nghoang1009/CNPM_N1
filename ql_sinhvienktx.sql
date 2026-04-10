-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 10, 2026 lúc 11:23 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ql_sinhvienktx`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `dien_nuoc`
--

CREATE TABLE `dien_nuoc` (
  `id` int(10) UNSIGNED NOT NULL,
  `phong_id` int(10) UNSIGNED NOT NULL,
  `thang` date NOT NULL,
  `dien_cu` decimal(10,2) DEFAULT 0.00,
  `dien_moi` decimal(10,2) DEFAULT 0.00,
  `nuoc_cu` decimal(10,2) DEFAULT 0.00,
  `nuoc_moi` decimal(10,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `dien_nuoc`
--

INSERT INTO `dien_nuoc` (`id`, `phong_id`, `thang`, `dien_cu`, `dien_moi`, `nuoc_cu`, `nuoc_moi`) VALUES
(1, 1, '2026-02-01', 120.00, 200.00, 30.00, 50.00),
(2, 1, '2026-03-01', 200.00, 300.00, 50.00, 75.00),
(3, 2, '2026-02-01', 110.00, 190.00, 28.00, 48.00),
(4, 2, '2026-03-01', 190.00, 280.00, 48.00, 70.00),
(5, 3, '2026-02-01', 115.00, 195.00, 29.00, 49.00),
(6, 3, '2026-03-01', 195.00, 290.00, 49.00, 73.00),
(7, 4, '2026-02-01', 85.00, 155.00, 20.00, 35.00),
(8, 4, '2026-03-01', 155.00, 225.00, 35.00, 52.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoa_don`
--

CREATE TABLE `hoa_don` (
  `id` int(10) UNSIGNED NOT NULL,
  `hop_dong_id` int(10) UNSIGNED NOT NULL,
  `sinh_vien_id` int(10) UNSIGNED NOT NULL,
  `phong_id` int(10) UNSIGNED NOT NULL,
  `thang` date NOT NULL,
  `tien_phong` decimal(12,2) DEFAULT 0.00,
  `tien_dien` decimal(12,2) DEFAULT 0.00,
  `tien_nuoc` decimal(12,2) DEFAULT 0.00,
  `tong_tien` decimal(12,2) DEFAULT 0.00,
  `trang_thai` enum('chua_tra','da_tra') DEFAULT 'chua_tra'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `hoa_don`
--

INSERT INTO `hoa_don` (`id`, `hop_dong_id`, `sinh_vien_id`, `phong_id`, `thang`, `tien_phong`, `tien_dien`, `tien_nuoc`, `tong_tien`, `trang_thai`) VALUES
(1, 1, 1, 1, '2026-02-01', 400000.00, 240000.00, 100000.00, 740000.00, 'da_tra'),
(2, 7, 7, 2, '2026-02-01', 400000.00, 230000.00, 95000.00, 725000.00, 'da_tra'),
(3, 13, 13, 3, '2026-02-01', 400000.00, 235000.00, 98000.00, 733000.00, 'da_tra'),
(4, 19, 19, 4, '2026-02-01', 600000.00, 210000.00, 125000.00, 935000.00, 'da_tra'),
(5, 23, 23, 5, '2026-02-01', 600000.00, 205000.00, 120000.00, 925000.00, 'da_tra'),
(6, 27, 27, 6, '2026-02-01', 1200000.00, 190000.00, 110000.00, 1500000.00, 'da_tra'),
(7, 29, 29, 7, '2026-02-01', 1200000.00, 185000.00, 105000.00, 1490000.00, 'da_tra'),
(8, 1, 1, 1, '2026-03-01', 400000.00, 280000.00, 125000.00, 805000.00, 'chua_tra'),
(9, 7, 7, 2, '2026-03-01', 400000.00, 270000.00, 120000.00, 790000.00, 'chua_tra'),
(10, 13, 13, 3, '2026-03-01', 400000.00, 275000.00, 122000.00, 797000.00, 'chua_tra'),
(11, 19, 19, 4, '2026-03-01', 600000.00, 250000.00, 150000.00, 1000000.00, 'chua_tra'),
(12, 23, 23, 5, '2026-03-01', 600000.00, 245000.00, 145000.00, 990000.00, 'chua_tra'),
(13, 27, 27, 6, '2026-03-01', 1200000.00, 230000.00, 135000.00, 1565000.00, 'chua_tra'),
(14, 29, 29, 7, '2026-03-01', 1200000.00, 225000.00, 130000.00, 1555000.00, 'chua_tra');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hop_dong`
--

CREATE TABLE `hop_dong` (
  `id` int(10) UNSIGNED NOT NULL,
  `sinh_vien_id` int(10) UNSIGNED NOT NULL,
  `phong_id` int(10) UNSIGNED NOT NULL,
  `ngay_bat_dau` date NOT NULL,
  `ngay_ket_thuc` date NOT NULL,
  `trang_thai` enum('hieu_luc','het_han','huy') DEFAULT 'hieu_luc'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `hop_dong`
--

INSERT INTO `hop_dong` (`id`, `sinh_vien_id`, `phong_id`, `ngay_bat_dau`, `ngay_ket_thuc`, `trang_thai`) VALUES
(1, 1, 1, '2025-09-01', '2026-06-30', 'hieu_luc'),
(2, 2, 1, '2025-09-01', '2026-06-30', 'hieu_luc'),
(3, 3, 1, '2025-09-01', '2026-06-30', 'hieu_luc'),
(4, 4, 1, '2025-09-01', '2026-06-30', 'hieu_luc'),
(5, 5, 1, '2025-09-01', '2026-06-30', 'hieu_luc'),
(6, 6, 1, '2025-09-01', '2026-06-30', 'hieu_luc'),
(7, 7, 2, '2025-09-01', '2026-06-30', 'hieu_luc'),
(8, 8, 2, '2025-09-01', '2026-06-30', 'hieu_luc'),
(9, 9, 2, '2025-09-01', '2026-06-30', 'hieu_luc'),
(10, 10, 2, '2025-09-01', '2026-06-30', 'hieu_luc'),
(11, 11, 2, '2025-09-01', '2026-06-30', 'hieu_luc'),
(12, 12, 2, '2025-09-01', '2026-06-30', 'hieu_luc'),
(13, 13, 3, '2025-09-01', '2026-06-30', 'hieu_luc'),
(14, 14, 3, '2025-09-01', '2026-06-30', 'hieu_luc'),
(15, 15, 3, '2025-09-01', '2026-06-30', 'hieu_luc'),
(16, 16, 3, '2025-09-01', '2026-06-30', 'hieu_luc'),
(17, 17, 3, '2025-09-01', '2026-06-30', 'hieu_luc'),
(18, 18, 3, '2025-09-01', '2026-06-30', 'hieu_luc'),
(19, 19, 4, '2025-09-01', '2026-06-30', 'hieu_luc'),
(20, 20, 4, '2025-09-01', '2026-06-30', 'hieu_luc'),
(21, 21, 4, '2025-09-01', '2026-06-30', 'hieu_luc'),
(22, 22, 4, '2025-09-01', '2026-06-30', 'hieu_luc'),
(23, 23, 5, '2025-09-01', '2026-06-30', 'hieu_luc'),
(24, 24, 5, '2025-09-01', '2026-06-30', 'hieu_luc'),
(25, 25, 5, '2025-09-01', '2026-06-30', 'hieu_luc'),
(26, 26, 5, '2025-09-01', '2026-06-30', 'hieu_luc'),
(27, 27, 6, '2025-09-01', '2026-06-30', 'hieu_luc'),
(28, 28, 6, '2025-09-01', '2026-06-30', 'hieu_luc'),
(29, 29, 7, '2025-09-01', '2026-06-30', 'hieu_luc'),
(30, 30, 7, '2025-09-01', '2026-06-30', 'hieu_luc');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loai_phong`
--

CREATE TABLE `loai_phong` (
  `id` smallint(5) UNSIGNED NOT NULL,
  `ten_loai` varchar(100) NOT NULL,
  `suc_chua` tinyint(3) UNSIGNED NOT NULL,
  `gia_thang` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `loai_phong`
--

INSERT INTO `loai_phong` (`id`, `ten_loai`, `suc_chua`, `gia_thang`) VALUES
(1, 'Phòng 6 người', 6, 400000.00),
(2, 'Phòng 4 người', 4, 600000.00),
(3, 'Phòng 2 người VIP', 2, 1200000.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `noi_quy`
--

CREATE TABLE `noi_quy` (
  `id` int(10) UNSIGNED NOT NULL,
  `tieu_de` varchar(255) NOT NULL,
  `noi_dung` longtext NOT NULL,
  `muc_phat` decimal(12,2) DEFAULT 0.00,
  `trang_thai` tinyint(1) DEFAULT 1,
  `ngay_tao` datetime DEFAULT current_timestamp(),
  `ngay_cap_nhat` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `noi_quy`
--

INSERT INTO `noi_quy` (`id`, `tieu_de`, `noi_dung`, `muc_phat`, `trang_thai`, `ngay_tao`, `ngay_cap_nhat`) VALUES
(1, 'Cấm huỷ hoại tài sản', 'Sinh viên không được huỷ hoại hoặc làm hư hỏng tài sản của ký túc xá', 500000.00, 1, '2026-04-10 16:21:57', '2026-04-10 16:21:57'),
(2, 'Cấm gây ồn ào', 'Yêu cầu giảm tiếng ồn sau 22h tối, giờ im lặng từ 22h đến 6h sáng', 200000.00, 1, '2026-04-10 16:21:57', '2026-04-10 16:21:57'),
(3, 'Vệ sinh chung', 'Phải dọn dẹp phòng ở tình trạng sạch sẽ, tổng vệ sinh 2 lần/tuần', 300000.00, 1, '2026-04-10 16:21:57', '2026-04-10 16:21:57'),
(4, 'An toàn điện', 'Cấm sử dụng các thiết bị điện công suất lớn (lò nướng, nồi cơm...)', 150000.00, 1, '2026-04-10 16:21:57', '2026-04-10 16:21:57'),
(5, 'Khách thăm', 'Khách thăm phải đăng ký với quản lý, không được ở lại qua đêm', 100000.00, 1, '2026-04-10 16:21:57', '2026-04-10 16:21:57'),
(6, 'Chất cấm', 'Cấm mang vào KTX các chất gây nghiện, vũ khí hoặc vật phẩm độc hại', 1000000.00, 1, '2026-04-10 16:21:57', '2026-04-10 16:21:57');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phong`
--

CREATE TABLE `phong` (
  `id` int(10) UNSIGNED NOT NULL,
  `toa_nha_id` smallint(5) UNSIGNED NOT NULL,
  `loai_phong_id` smallint(5) UNSIGNED NOT NULL,
  `so_phong` varchar(10) NOT NULL,
  `tang` tinyint(3) UNSIGNED NOT NULL,
  `so_nguoi` tinyint(3) UNSIGNED DEFAULT 0,
  `trang_thai` enum('con_trong','day','bao_tri') DEFAULT 'con_trong'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `phong`
--

INSERT INTO `phong` (`id`, `toa_nha_id`, `loai_phong_id`, `so_phong`, `tang`, `so_nguoi`, `trang_thai`) VALUES
(1, 1, 1, '101', 1, 6, 'day'),
(2, 1, 1, '102', 1, 6, 'day'),
(3, 1, 1, '103', 1, 6, 'day'),
(4, 1, 2, '201', 2, 4, 'day'),
(5, 1, 2, '202', 2, 4, 'day'),
(6, 1, 3, '301', 3, 2, 'day'),
(7, 2, 1, '101', 1, 6, 'day'),
(8, 2, 1, '102', 1, 6, 'day'),
(9, 2, 2, '201', 2, 4, 'day'),
(10, 2, 2, '202', 2, 4, 'day'),
(11, 2, 3, '301', 3, 2, 'day');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sinh_vien`
--

CREATE TABLE `sinh_vien` (
  `id` int(10) UNSIGNED NOT NULL,
  `tai_khoan_id` int(10) UNSIGNED DEFAULT NULL,
  `ma_sinh_vien` varchar(20) NOT NULL,
  `ho_ten` varchar(100) NOT NULL,
  `gioi_tinh` enum('nam','nu') NOT NULL,
  `ngay_sinh` date NOT NULL,
  `so_dien_thoai` varchar(15) DEFAULT NULL,
  `truong` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sinh_vien`
--

INSERT INTO `sinh_vien` (`id`, `tai_khoan_id`, `ma_sinh_vien`, `ho_ten`, `gioi_tinh`, `ngay_sinh`, `so_dien_thoai`, `truong`) VALUES
(1, 3, 'SV001', 'Lê Văn An', 'nam', '2003-05-15', '0911111101', 'Đại học CNGTVT'),
(2, 4, 'SV002', 'Phạm Thị Bình', 'nu', '2003-08-22', '0911111102', 'Đại học CNGTVT'),
(3, 5, 'SV003', 'Hoàng Văn C', 'nam', '2004-01-10', '0911111103', 'Đại học CNGTVT'),
(4, 6, 'SV004', 'Trần Văn D', 'nam', '2004-03-18', '0911111104', 'Đại học CNGTVT'),
(5, 7, 'SV005', 'Nguyễn Thị E', 'nu', '2004-06-20', '0911111105', 'Đại học CNGTVT'),
(6, 8, 'SV006', 'Vũ Văn F', 'nam', '2004-09-05', '0911111106', 'Đại học CNGTVT'),
(7, 9, 'SV007', 'Đặng Thị G', 'nu', '2005-02-14', '0911111107', 'Đại học CNGTVT'),
(8, 10, 'SV008', 'Phan Văn H', 'nam', '2005-07-11', '0911111108', 'Đại học CNGTVT'),
(9, 11, 'SV009', 'Bùi Thị I', 'nu', '2003-11-25', '0911111109', 'Đại học CNGTVT'),
(10, 12, 'SV010', 'Tô Văn K', 'nam', '2004-04-30', '0911111110', 'Đại học CNGTVT'),
(11, 13, 'SV011', 'Võ Thị L', 'nu', '2004-08-12', '0911111111', 'Đại học CNGTVT'),
(12, 14, 'SV012', 'Dương Văn M', 'nam', '2005-01-22', '0911111112', 'Đại học CNGTVT'),
(13, 15, 'SV013', 'Hà Thị N', 'nu', '2005-05-08', '0911111113', 'Đại học CNGTVT'),
(14, 16, 'SV014', 'Giang Văn O', 'nam', '2003-09-19', '0911111114', 'Đại học CNGTVT'),
(15, 17, 'SV015', 'Minh Thị P', 'nu', '2004-02-28', '0911111115', 'Đại học CNGTVT'),
(16, 18, 'SV016', 'Hùng Văn Q', 'nam', '2004-07-15', '0911111116', 'Đại học CNGTVT'),
(17, 19, 'SV017', 'Liên Thị R', 'nu', '2005-03-10', '0911111117', 'Đại học CNGTVT'),
(18, 20, 'SV018', 'Sơn Văn S', 'nam', '2003-10-05', '0911111118', 'Đại học CNGTVT'),
(19, 21, 'SV019', 'Trang Thị T', 'nu', '2004-12-18', '0911111119', 'Đại học CNGTVT'),
(20, 22, 'SV020', 'Tú Văn U', 'nam', '2005-06-23', '0911111120', 'Đại học CNGTVT'),
(21, 23, 'SV021', 'Uyên Thị V', 'nu', '2003-07-09', '0911111121', 'Đại học CNGTVT'),
(22, 24, 'SV022', 'Việt Văn W', 'nam', '2004-05-17', '0911111122', 'Đại học CNGTVT'),
(23, 25, 'SV023', 'Xuyên Thị X', 'nu', '2004-11-26', '0911111123', 'Đại học CNGTVT'),
(24, 26, 'SV024', 'Yên Văn Y', 'nam', '2005-04-12', '0911111124', 'Đại học CNGTVT'),
(25, 27, 'SV025', 'Thuận Thị Z', 'nu', '2003-06-20', '0911111125', 'Đại học CNGTVT'),
(26, 28, 'SV026', 'Anh Văn A1', 'nam', '2004-10-14', '0911111126', 'Đại học CNGTVT'),
(27, 29, 'SV027', 'Bảo Thị B1', 'nu', '2005-02-03', '0911111127', 'Đại học CNGTVT'),
(28, 30, 'SV028', 'Chi Văn C1', 'nam', '2003-08-31', '0911111128', 'Đại học CNGTVT'),
(29, 31, 'SV029', 'Duy Thị D1', 'nu', '2004-09-16', '0911111129', 'Đại học CNGTVT'),
(30, 32, 'SV030', 'Em Văn E1', 'nam', '2005-01-27', '0911111130', 'Đại học CNGTVT');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tai_khoan`
--

CREATE TABLE `tai_khoan` (
  `id` int(10) UNSIGNED NOT NULL,
  `ten_dang_nhap` varchar(50) NOT NULL,
  `mat_khau` varchar(255) NOT NULL,
  `vai_tro_id` tinyint(3) UNSIGNED NOT NULL,
  `ho_ten` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `so_dien_thoai` varchar(15) DEFAULT NULL,
  `trang_thai` tinyint(1) DEFAULT 1,
  `lan_dang_nhap_cuoi` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tai_khoan`
--

INSERT INTO `tai_khoan` (`id`, `ten_dang_nhap`, `mat_khau`, `vai_tro_id`, `ho_ten`, `email`, `so_dien_thoai`, `trang_thai`, `lan_dang_nhap_cuoi`) VALUES
(1, 'admin', '123456', 1, 'Nguyễn Admin', 'admin@ktx.vn', '0900000001', 1, NULL),
(2, 'nv01', '123456', 2, 'Trần Nhân Viên', 'nv01@ktx.vn', '0900000002', 1, NULL),
(3, 'sv001', '123456', 3, 'Lê Văn An', 'sv001@gmail.com', '0911111101', 1, NULL),
(4, 'sv002', '123456', 3, 'Phạm Thị Bình', 'sv002@gmail.com', '0911111102', 1, NULL),
(5, 'sv003', '123456', 3, 'Hoàng Văn C', 'sv003@gmail.com', '0911111103', 1, NULL),
(6, 'sv004', '123456', 3, 'Trần Văn D', 'sv004@gmail.com', '0911111104', 1, NULL),
(7, 'sv005', '123456', 3, 'Nguyễn Thị E', 'sv005@gmail.com', '0911111105', 1, NULL),
(8, 'sv006', '123456', 3, 'Vũ Văn F', 'sv006@gmail.com', '0911111106', 1, NULL),
(9, 'sv007', '123456', 3, 'Đặng Thị G', 'sv007@gmail.com', '0911111107', 1, NULL),
(10, 'sv008', '123456', 3, 'Phan Văn H', 'sv008@gmail.com', '0911111108', 1, NULL),
(11, 'sv009', '123456', 3, 'Bùi Thị I', 'sv009@gmail.com', '0911111109', 1, NULL),
(12, 'sv010', '123456', 3, 'Tô Văn K', 'sv010@gmail.com', '0911111110', 1, NULL),
(13, 'sv011', '123456', 3, 'Võ Thị L', 'sv011@gmail.com', '0911111111', 1, NULL),
(14, 'sv012', '123456', 3, 'Dương Văn M', 'sv012@gmail.com', '0911111112', 1, NULL),
(15, 'sv013', '123456', 3, 'Hà Thị N', 'sv013@gmail.com', '0911111113', 1, NULL),
(16, 'sv014', '123456', 3, 'Giang Văn O', 'sv014@gmail.com', '0911111114', 1, NULL),
(17, 'sv015', '123456', 3, 'Minh Thị P', 'sv015@gmail.com', '0911111115', 1, NULL),
(18, 'sv016', '123456', 3, 'Hùng Văn Q', 'sv016@gmail.com', '0911111116', 1, NULL),
(19, 'sv017', '123456', 3, 'Liên Thị R', 'sv017@gmail.com', '0911111117', 1, NULL),
(20, 'sv018', '123456', 3, 'Sơn Văn S', 'sv018@gmail.com', '0911111118', 1, NULL),
(21, 'sv019', '123456', 3, 'Trang Thị T', 'sv019@gmail.com', '0911111119', 1, NULL),
(22, 'sv020', '123456', 3, 'Tú Văn U', 'sv020@gmail.com', '0911111120', 1, NULL),
(23, 'sv021', '123456', 3, 'Uyên Thị V', 'sv021@gmail.com', '0911111121', 1, NULL),
(24, 'sv022', '123456', 3, 'Việt Văn W', 'sv022@gmail.com', '0911111122', 1, NULL),
(25, 'sv023', '123456', 3, 'Xuyên Thị X', 'sv023@gmail.com', '0911111123', 1, NULL),
(26, 'sv024', '123456', 3, 'Yên Văn Y', 'sv024@gmail.com', '0911111124', 1, NULL),
(27, 'sv025', '123456', 3, 'Thuận Thị Z', 'sv025@gmail.com', '0911111125', 1, NULL),
(28, 'sv026', '123456', 3, 'Anh Văn A1', 'sv026@gmail.com', '0911111126', 1, NULL),
(29, 'sv027', '123456', 3, 'Bảo Thị B1', 'sv027@gmail.com', '0911111127', 1, NULL),
(30, 'sv028', '123456', 3, 'Chi Văn C1', 'sv028@gmail.com', '0911111128', 1, NULL),
(31, 'sv029', '123456', 3, 'Duy Thị D1', 'sv029@gmail.com', '0911111129', 1, NULL),
(32, 'sv030', '123456', 3, 'Em Văn E1', 'sv030@gmail.com', '0911111130', 1, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `toa_nha`
--

CREATE TABLE `toa_nha` (
  `id` smallint(5) UNSIGNED NOT NULL,
  `ma_toa` varchar(10) NOT NULL,
  `ten_toa` varchar(100) NOT NULL,
  `so_tang` tinyint(3) UNSIGNED DEFAULT 1,
  `gioi_tinh` enum('nam','nu','hon_hop') DEFAULT 'hon_hop'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `toa_nha`
--

INSERT INTO `toa_nha` (`id`, `ma_toa`, `ten_toa`, `so_tang`, `gioi_tinh`) VALUES
(1, 'A', 'Tòa A - Nam', 5, 'nam'),
(2, 'B', 'Tòa B - Nữ', 5, 'nu'),
(3, 'C', 'Tòa C - Hỗn hợp', 4, 'hon_hop');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vai_tro`
--

CREATE TABLE `vai_tro` (
  `id` tinyint(3) UNSIGNED NOT NULL,
  `ten_vai_tro` varchar(50) NOT NULL,
  `mo_ta` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `vai_tro`
--

INSERT INTO `vai_tro` (`id`, `ten_vai_tro`, `mo_ta`) VALUES
(1, 'admin', 'Quản trị hệ thống'),
(2, 'nhan_vien', 'Quản lý KTX'),
(3, 'sinh_vien', 'Sinh viên');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `dien_nuoc`
--
ALTER TABLE `dien_nuoc`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `phong_id` (`phong_id`,`thang`);

--
-- Chỉ mục cho bảng `hoa_don`
--
ALTER TABLE `hoa_don`
  ADD PRIMARY KEY (`id`),
  ADD KEY `hop_dong_id` (`hop_dong_id`),
  ADD KEY `sinh_vien_id` (`sinh_vien_id`),
  ADD KEY `phong_id` (`phong_id`);

--
-- Chỉ mục cho bảng `hop_dong`
--
ALTER TABLE `hop_dong`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sinh_vien_id` (`sinh_vien_id`),
  ADD KEY `phong_id` (`phong_id`);

--
-- Chỉ mục cho bảng `loai_phong`
--
ALTER TABLE `loai_phong`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `noi_quy`
--
ALTER TABLE `noi_quy`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `phong`
--
ALTER TABLE `phong`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `toa_nha_id` (`toa_nha_id`,`so_phong`),
  ADD KEY `loai_phong_id` (`loai_phong_id`);

--
-- Chỉ mục cho bảng `sinh_vien`
--
ALTER TABLE `sinh_vien`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ma_sinh_vien` (`ma_sinh_vien`),
  ADD UNIQUE KEY `tai_khoan_id` (`tai_khoan_id`);

--
-- Chỉ mục cho bảng `tai_khoan`
--
ALTER TABLE `tai_khoan`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ten_dang_nhap` (`ten_dang_nhap`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `vai_tro_id` (`vai_tro_id`);

--
-- Chỉ mục cho bảng `toa_nha`
--
ALTER TABLE `toa_nha`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ma_toa` (`ma_toa`);

--
-- Chỉ mục cho bảng `vai_tro`
--
ALTER TABLE `vai_tro`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ten_vai_tro` (`ten_vai_tro`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `dien_nuoc`
--
ALTER TABLE `dien_nuoc`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `hoa_don`
--
ALTER TABLE `hoa_don`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `hop_dong`
--
ALTER TABLE `hop_dong`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT cho bảng `loai_phong`
--
ALTER TABLE `loai_phong`
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `noi_quy`
--
ALTER TABLE `noi_quy`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `phong`
--
ALTER TABLE `phong`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `sinh_vien`
--
ALTER TABLE `sinh_vien`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT cho bảng `tai_khoan`
--
ALTER TABLE `tai_khoan`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT cho bảng `toa_nha`
--
ALTER TABLE `toa_nha`
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `vai_tro`
--
ALTER TABLE `vai_tro`
  MODIFY `id` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `dien_nuoc`
--
ALTER TABLE `dien_nuoc`
  ADD CONSTRAINT `dien_nuoc_ibfk_1` FOREIGN KEY (`phong_id`) REFERENCES `phong` (`id`);

--
-- Các ràng buộc cho bảng `hoa_don`
--
ALTER TABLE `hoa_don`
  ADD CONSTRAINT `hoa_don_ibfk_1` FOREIGN KEY (`hop_dong_id`) REFERENCES `hop_dong` (`id`),
  ADD CONSTRAINT `hoa_don_ibfk_2` FOREIGN KEY (`sinh_vien_id`) REFERENCES `sinh_vien` (`id`),
  ADD CONSTRAINT `hoa_don_ibfk_3` FOREIGN KEY (`phong_id`) REFERENCES `phong` (`id`);

--
-- Các ràng buộc cho bảng `hop_dong`
--
ALTER TABLE `hop_dong`
  ADD CONSTRAINT `hop_dong_ibfk_1` FOREIGN KEY (`sinh_vien_id`) REFERENCES `sinh_vien` (`id`),
  ADD CONSTRAINT `hop_dong_ibfk_2` FOREIGN KEY (`phong_id`) REFERENCES `phong` (`id`);

--
-- Các ràng buộc cho bảng `phong`
--
ALTER TABLE `phong`
  ADD CONSTRAINT `phong_ibfk_1` FOREIGN KEY (`toa_nha_id`) REFERENCES `toa_nha` (`id`),
  ADD CONSTRAINT `phong_ibfk_2` FOREIGN KEY (`loai_phong_id`) REFERENCES `loai_phong` (`id`);

--
-- Các ràng buộc cho bảng `sinh_vien`
--
ALTER TABLE `sinh_vien`
  ADD CONSTRAINT `sinh_vien_ibfk_1` FOREIGN KEY (`tai_khoan_id`) REFERENCES `tai_khoan` (`id`);

--
-- Các ràng buộc cho bảng `tai_khoan`
--
ALTER TABLE `tai_khoan`
  ADD CONSTRAINT `tai_khoan_ibfk_1` FOREIGN KEY (`vai_tro_id`) REFERENCES `vai_tro` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
