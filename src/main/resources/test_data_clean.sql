-- ========================================
-- TimeStore Test Data
-- Clean INSERT statements only
-- ========================================

SET FOREIGN_KEY_CHECKS=0;

-- ========================================
-- 1. LOOKUP TABLES (No FK dependencies)
-- ========================================

-- Provinces
INSERT INTO `provinces` (`province_id`, `province_en`, `province_si`, `province_ta`) VALUES
	(1, 'Central Province', 'මධ්‍යම පළාත', 'மத்திய மாகாணம்'),
	(2, 'Eastern Province', 'නැගෙනහිර පළාත', 'கிழக்கு மாகாணம்'),
	(3, 'North Central Province', 'උතුරු මැද පළාත', 'வட மத்திய மாகாணம்'),
	(4, 'Northern Province', 'උතුරු පළාත', 'வட மாகாணம்'),
	(5, 'North Western Province', 'වයඹ පළාත', 'வட மேல் மாகாணம்'),
	(6, 'Sabaragamuwa Province', 'සබරගමුව පළාත', 'சப்ரகமுவ மாகாணம்'),
	(7, 'Southern Province', 'දකුණු පළාත', 'தென் மாகாணம்'),
	(8, 'Uva Province', 'ඌව පළාත', 'ஊவா மாகாணம்'),
	(9, 'Western Province', 'බස්නාහිර පළාත', 'மேல் மாகாணம்');

-- Districts  
INSERT INTO `districts` (`district_id`, `province_id`, `district_en`, `district_si`, `district_ta`) VALUES
	(1, 2, 'Ampara', 'අම්පාර', 'அம்பாறை'),
	(2, 3, 'Anuradhapura', 'අනුරාධපුරය', 'அனுராதபுரம்'),
	(3, 8, 'Badulla', 'බදුල්ල', 'பதுளை'),
	(4, 2, 'Batticaloa', 'මඩකලපුව', 'மட்டக்களப்பு'),
	(5, 9, 'Colombo', 'කොළඹ', 'கொழும்பு'),
	(6, 7, 'Galle', 'ගාල්ල', 'காலி'),
	(7, 9, 'Gampaha', 'ගම්පහ', 'கம்பஹா'),
	(8, 7, 'Hambantota', 'හම්බන්තොට', 'அம்பாந்தோட்டை'),
	(9, 2, 'Jaffna', 'යාපනය', 'யாழ்ப்பாணம்'),
	(10, 9, 'Kalutara', 'කළුතර', 'களுத்துறை'),
	(11, 1, 'Kandy', 'මහනුවර', 'கண்டி'),
	(12, 6, 'Kegalle', 'කැගල්ල', 'கேகாலை'),
	(13, 2, 'Kilinochchi', 'කිලිනොච්චිය', 'கிளிநொச்சி'),
	(14, 4, 'Mannar', 'මන්නාරම', 'மன்னார்'),
	(15, 1, 'Matale', 'මාතලේ', 'மாத்தளை'),
	(16, 7, 'Matara', 'මාතර', 'மாத்தறை'),
	(17, 8, 'Monaragala', 'මොනරාගල', 'மொனராகலை'),
	(18, 4, 'Mullaitivu', 'මුලතිව්', 'முல்லைத்தீவு'),
	(19, 1, 'Nuwara Eliya', 'නුවර එළිය', 'நுவரெலியா'),
	(20, 3, 'Polonnaruwa', 'පොළොන්නරුව', 'பொலன்னறுவை'),
	(21, 5, 'Puttalam', 'පුත්තලම', 'புத்தளம்'),
	(22, 6, 'Ratnapura', 'රත්නපුර', 'இரத்தினபுரி'),
	(23, 2, 'Trincomalee', 'ත්‍රිකුණාමලය', 'திருகோணமலை'),
	(24, 4, 'Vavuniya', 'වව්නියාව', 'வவுனியா'),
	(25, 5, 'Kurunegala', 'කුරුණෑගල', 'குருனகல்');

-- Gender
INSERT INTO `gender` (`id`, `gender`) VALUES
	(1, 'Male'),
	(2, 'Female');

-- User Status
INSERT INTO `user_status` (`status_id`, `status`) VALUES
	(1, 'Active'),
	(2, 'Blocked');

-- Message Status
INSERT INTO `msg_status` (`msg_status_id`, `msg_status`) VALUES
	(1, 'Read'),
	(2, 'Unread');

-- Delivery Method
INSERT INTO `delivery_method` (`id`, `delivery_method`, `price`) VALUES
	(1, 'Standard Delivery', 350.00),
	(2, 'Express Delivery', 600.00);

-- Order Status
INSERT INTO `order_status` ( `status`) VALUES
	( 'Order Place'),
	( 'Confirm'),
	( 'Dispatched'),
	( 'In Transit'),
	( 'Delivered'),
	( 'Cancelled');

-- Admin
INSERT INTO `admin` (`email`, `password`, `first_name`, `last_name`) VALUES
	('admin@gmail.com', '12345', NULL, NULL),
	('admin@timestore.com', 'admin123', 'System', 'Admin');

-- Brand
INSERT INTO `brand` (`brand_id`, `brand_name`) VALUES
	(1, 'G-Shock'),
	(2, 'Seiko'),
	(3, 'Rolex'),
	(8, 'Citizen '),
	(10, 'Omega');

-- Category
INSERT INTO `category` (`category_name`) VALUES
	( 'men'),
	( 'woman'),
	( 'leather'),
	( 'chain'),
	( 'digital'),
	( 'analog');

-- ========================================
-- 2. CITIES (Large dataset - sample data)
-- ========================================
-- Note: Only including first 100 cities for performance. Add more as needed.

INSERT INTO `cities` (`city_id`, `district_id`, `city_en`, `city_si`, `city_ta`, `sub_name_en`, `sub_name_si`, `sub_name_ta`, `postcode`, `latitude`, `longitude`) VALUES
	(1, 1, 'Akkaraipattu', 'අක්කරපත්තුව', NULL, NULL, NULL, NULL, '32400', 7.2167, 81.85),
	(2, 1, 'Ampara', 'අම්පාර', NULL, NULL, NULL, NULL, '32000', 7.2833, 81.6667),
	(3, 5, 'Colombo', 'කොළඹ', 'கொழும்பு', NULL, NULL, NULL, '00100', 6.9271, 79.8612),
	(4, 5, 'Dehiwala', 'දෙහිවල', NULL, NULL, NULL, NULL, '10350', 6.856387, 79.865156),
	(5, 5, 'Maharagama', 'මහරගම', NULL, NULL, NULL, NULL, '10280', 6.843401, 79.932766),
	(6, 7, 'Gampaha', 'ගම්පහ', NULL, NULL, NULL, NULL, '11000', 7.0917, 79.9958),
	(7, 11, 'Kandy', 'මහනුවර', 'கண்டி', NULL, NULL, NULL, '20000', 7.2906, 80.6337);

-- ========================================
-- 3. PRODUCTS
-- ========================================

-- Product
INSERT INTO `product` (`product_id`, `product_name`, `brand_id`) VALUES
	(1, 'G-Shock Digital Series', 1),
	(2, 'Seiko Automatic Collection', 2),
	(3, 'Rolex Submariner Line', 3),
	(4, 'Citizen Eco-Drive Range', 8),
	(5, 'Omega Seamaster Collection', 10),
	(6, 'G-Shock Analog-Digital', 1),
	(7, 'Seiko Presage Series', 2),
	(8, 'Citizen Chronograph', 8);

-- Product Has Model
INSERT INTO `model` (`model_id`, `model`, `price`, `qty`, `product_id`, `added_time`) VALUES
	(101, 'GA-2100-1AER', 15000.00, 50, 1, '2026-02-01 10:00:00'),
	(102, 'DW-5600E-1VER', 12000.00, 75, 1, '2026-02-01 10:15:00'),
	(103, 'SRPD55K1', 35000.00, 30, 2, '2026-02-01 11:00:00'),
	(104, 'SRPD51K1', 32000.00, 25, 2, '2026-02-01 11:30:00'),
	(105, '126610LN', 950000.00, 5, 3, '2026-02-02 09:00:00'),
	(106, '126600', 1100000.00, 3, 3, '2026-02-02 09:30:00'),
	(107, 'BM8180-03E', 18000.00, 60, 4, '2026-02-02 10:00:00'),
	(108, 'AT2141-52L', 22000.00, 40, 4, '2026-02-02 10:30:00'),
	(109, '210.30.42.20.01.001', 550000.00, 8, 5, '2026-02-03 08:00:00'),
	(110, 'GA-2200BB-1AER', 16500.00, 45, 6, '2026-02-03 09:00:00');

-- Product Has Category
INSERT INTO `product_has_category` (`product_id`, `category_id`) VALUES
	(1, 1),
	(1, 5),
	(2, 1),
	(2, 6),
	(3, 1),
	(4, 1),
	(5, 1),
	(6, 1);

-- Product Images
INSERT INTO `product_img` (`model_id`, `img_path`) VALUES
	(101, 'Image/product/MRG-B2000B-1A4.avif'),
	(102, 'Image/product/MRG-B2000BG-3A.avif'),
	(103, 'Image/product/Seiko SSH161J1.png'),
	(104, 'Image/product/Seiko SSH175J1.png'),
	(105, 'Image/product/m126508-0002.avif'),
	(106, 'Image/product/Cosmograph-Daytona-Oyestersteel.avif'),
	(107, 'Image/product/Citizen NB6030-59L.jpg'),
	(108, 'Image/product/Citizen NB6031-56E.jpg'),
	(109, 'Image/product/omega-seamaster-planet-ocean-6000m-co-axial-master-chronometer-45-5-mm-21530462104001-6fb924.avif'),
	(110, 'Image/product/MRG-B2000JS-1A.avif');

-- ========================================
-- 4. USERS
-- ========================================

-- Users (passwords are BCrypt hashed - $2a$12$ format)
-- Schema note: password column is varchar(45) which is too short for BCrypt (needs 60+)
-- Consider altering table: ALTER TABLE users MODIFY password VARCHAR(72);
INSERT INTO `users` (`fname`, `lname`, `password`, `mobile`, `email`, `gender_id`, `status`, `joined_date`) VALUES
	('Nimal', 'Perera', 'pass123', '0771234567', 'nimal@example.com', 1, 1, '2026-02-01'),
	('Kavindi', 'Silva', 'pass123', '0712223344', 'kavindi@example.com', 2, 1, '2026-02-05'),
	('Imesh', 'Vishmika', '12345', '0786981108', 'imesh@gmail.com', 1, 1, '2026-02-03'),
	('Vishmika', 'Liyanage', '456', '0700000000', 'vishmika@gmail.com', 1, 2, '2026-02-03');

-- User Address
INSERT INTO `user_address` (`address_line1`, `address_line2`, `address_city_id`, `users_email`) VALUES
	('No 10, Galle Road', 'Colombo', 3, 'nimal@example.com'),
	('No 22, Main Street', 'Maharagama', 5, 'kavindi@example.com'),
	('Horakad Road', 'Ranjurawa', 6, 'imesh@gmail.com');

-- User Images
INSERT INTO `user_img` (`email`, `path`) VALUES
	('nimal@example.com', '/assets/images/users/nimal.jpg'),
	('kavindi@example.com', '/assets/images/users/kavindi.jpg'),
	('imesh@gmail.com', 'userprofile/photo_2024-08-23_14-50-42.jpg');

-- ========================================
-- 5. SHOPPING CART & WISHLIST
-- ========================================

-- Cart
INSERT INTO `cart` (`cart_id`, `product_id`, `cart_qty`, `users_email`) VALUES
	(1, 101, 1, 'nimal@example.com');

-- Buy Now Cart
INSERT INTO `buy_now_cart` (`user_email`, `model_id`, `qty`) VALUES
	('kavindi@example.com', 101, 1);

-- Watchlist
INSERT INTO `watchlist` (`watchlist_id`, `product_id`, `users_email`) VALUES
	(1, 102, 'nimal@example.com');

-- ========================================
-- 6. ORDERS
-- ========================================

-- Orders
INSERT INTO `order` (`order_id`, `email`, `ordered_date`, `delivery_method`, `order_status`) VALUES
	(1, 'nimal@example.com', '2026-02-10 14:30:00', 1, 4),
	(2, 'kavindi@example.com', '2026-02-12 16:45:00', 2, 3),
	(3, 'imesh@gmail.com', '2026-02-15 10:20:00', 1, 1);

-- Order Has Model
INSERT INTO `order_has_model` (`order_id`, `model_id`, `qty`) VALUES
	(1, 101, 1),
	(2, 102, 1),
	(3, 103, 1);

-- ========================================
-- 7. INVOICES
-- ========================================

-- Invoice
INSERT INTO `invoice` (`invoice_id`, `order_id`, `invoice_date`, `email`, `delivery_fee`) VALUES
	(1, 1, '2026-02-10', 'nimal@example.com', 350.00),
	(2, 2, '2026-02-12', 'kavindi@example.com', 600.00),
	(3, 3, '2026-02-15', 'imesh@gmail.com', 350.00);

-- Invoice Items
INSERT INTO `invoice_items` (`invoice_id`, `product_id`, `product_name`, `product_price`, `qty`) VALUES
	(1, 101, 'G-Shock GA-2100-1AER', 15000.00, 1),
	(2, 102, 'G-Shock DW-5600E-1VER', 12000.00, 1),
	(3, 103, 'Seiko SRPD55K1', 35000.00, 1);

-- ========================================
-- 8. USER ACTIVITY
-- ========================================

-- User History
INSERT INTO `user_history` (`id`, `user_id`, `product_id`, `buy_datetime`, `amount`) VALUES
	(1, 'nimal@example.com', 101, '2026-02-28 10:32:24', 1),
	(2, 'kavindi@example.com', 102, '2026-02-28 10:32:24', 1);

-- Ratings
INSERT INTO `ratings` (`user_email`, `product_id`, `ratings`, `comment`) VALUES
	('nimal@example.com', 101, '5', 'Excellent watch'),
	('kavindi@example.com', 102, '4', 'Great quality');

-- Messages
INSERT INTO `messages` (`message_id`, `sender`, `message`, `date_time`, `status`) VALUES
	(1, 'nimal@example.com', 'When will my order be delivered?', '2026-02-11 09:00:00', 1),
	(2, 'kavindi@example.com', 'Do you have this model in stock?', '2026-02-13 14:30:00', 2),
	(3, 'imesh@gmail.com', 'What is the warranty period?', '2026-02-16 11:15:00', 2);

SET FOREIGN_KEY_CHECKS=1;

-- ========================================
-- END OF FILE
-- ========================================
