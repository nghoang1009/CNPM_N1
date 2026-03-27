-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 27, 2026 lúc 05:52 PM
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
(1, 1, '2026-02-01', 120.00, 180.00, 30.00, 40.00),
(2, 1, '2026-03-01', 180.00, 250.00, 40.00, 55.00),
(3, 4, '2026-02-01', 80.00, 140.00, 20.00, 30.00),
(4, 4, '2026-03-01', 140.00, 200.00, 30.00, 45.00);

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
(1, 1, 1, 1, '2026-02-01', 600000.00, 210000.00, 150000.00, 960000.00, 'da_tra'),
(2, 1, 1, 1, '2026-03-01', 600000.00, 245000.00, 200000.00, 1045000.00, 'chua_tra'),
(3, 2, 2, 4, '2026-02-01', 400000.00, 200000.00, 120000.00, 720000.00, 'da_tra'),
(4, 2, 2, 4, '2026-03-01', 400000.00, 210000.00, 180000.00, 790000.00, 'chua_tra');

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
(2, 2, 4, '2025-09-01', '2026-06-30', 'hieu_luc'),
(3, 3, 1, '2025-09-01', '2026-06-30', 'hieu_luc');

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
(1, 1, 1, '101', 1, 3, 'con_trong'),
(2, 1, 1, '102', 1, 6, 'day'),
(3, 1, 2, '201', 2, 2, 'con_trong'),
(4, 2, 1, '101', 1, 2, 'con_trong'),
(5, 2, 3, '301', 3, 1, 'con_trong'),
(6, 3, 2, '202', 2, 0, 'bao_tri');

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
(1, 3, 'SV001', 'Lê Văn An', 'nam', '2003-05-15', '0911111111', 'Bách Khoa'),
(2, 4, 'SV002', 'Phạm Thị Bình', 'nu', '2003-08-22', '0922222222', 'Kinh Tế'),
(3, 5, 'SV003', 'Hoàng Văn C', 'nam', '2004-01-10', '0933333333', 'Xây Dựng');

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
(3, 'sv01', '123456', 3, 'Lê Văn An', 'an@gmail.com', '0911111111', 1, NULL),
(4, 'sv02', '123456', 3, 'Phạm Thị Bình', 'binh@gmail.com', '0922222222', 1, NULL),
(5, 'sv03', '123456', 3, 'Hoàng Văn C', 'c@gmail.com', '0933333333', 1, NULL);

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
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `hoa_don`
--
ALTER TABLE `hoa_don`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `hop_dong`
--
ALTER TABLE `hop_dong`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `loai_phong`
--
ALTER TABLE `loai_phong`
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `phong`
--
ALTER TABLE `phong`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `sinh_vien`
--
ALTER TABLE `sinh_vien`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `tai_khoan`
--
ALTER TABLE `tai_khoan`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

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
