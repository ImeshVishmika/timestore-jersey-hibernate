-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.37 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for timestore
CREATE DATABASE IF NOT EXISTS `timestore` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `timestore`;

-- Dumping structure for table timestore.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.brand
CREATE TABLE IF NOT EXISTS `brand` (
  `brand_id` int NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.buy_now_cart
CREATE TABLE IF NOT EXISTS `buy_now_cart` (
  `user_email` varchar(50) DEFAULT NULL,
  `model_id` int DEFAULT NULL,
  `qty` int DEFAULT NULL,
  KEY `fk_buy_now_product` (`model_id`),
  KEY `fk_buy_now_email` (`user_email`),
  CONSTRAINT `fk_buy_now_email` FOREIGN KEY (`user_email`) REFERENCES `users` (`email`),
  CONSTRAINT `fk_buy_now_product` FOREIGN KEY (`model_id`) REFERENCES `model` (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.cart
CREATE TABLE IF NOT EXISTS `cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `cart_qty` int DEFAULT NULL,
  `users_email` varchar(50) NOT NULL,
  PRIMARY KEY (`cart_id`),
  KEY `fk_cart_product1_idx` (`product_id`),
  KEY `fk_cart_users1_idx` (`users_email`),
  CONSTRAINT `fk_cart_product1` FOREIGN KEY (`product_id`) REFERENCES `model` (`model_id`),
  CONSTRAINT `fk_cart_users1` FOREIGN KEY (`users_email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.category
CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(45) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.cities
CREATE TABLE IF NOT EXISTS `cities` (
  `city_id` int NOT NULL AUTO_INCREMENT,
  `district_id` int NOT NULL,
  `city_en` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `city_si` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `city_ta` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `sub_name_en` varchar(45) DEFAULT NULL,
  `sub_name_si` varchar(45) DEFAULT NULL,
  `sub_name_ta` varchar(45) DEFAULT NULL,
  `postcode` varchar(15) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`city_id`) USING BTREE,
  KEY `fk_cities_districts1_idx` (`district_id`) USING BTREE,
  CONSTRAINT `fk_cities_districts1` FOREIGN KEY (`district_id`) REFERENCES `districts` (`district_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1847 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.delivery_method
CREATE TABLE IF NOT EXISTS `delivery_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `delivery_method` varchar(50) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `delivery_days` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.districts
CREATE TABLE IF NOT EXISTS `districts` (
  `district_id` int NOT NULL AUTO_INCREMENT,
  `province_id` int NOT NULL,
  `district_en` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `district_si` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `district_ta` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`district_id`) USING BTREE,
  KEY `provinces_id` (`province_id`) USING BTREE,
  CONSTRAINT `fk_districts_provinces1` FOREIGN KEY (`province_id`) REFERENCES `provinces` (`province_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.gender
CREATE TABLE IF NOT EXISTS `gender` (
  `id` int NOT NULL,
  `gender` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `invoice_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `invoice_date` datetime NOT NULL DEFAULT (now()),
  `email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `delivery_fee` double DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `invoice_ibfk_1` (`email`),
  CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`email`) REFERENCES `users` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=997185141 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for view timestore.invoice_data
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `invoice_data` (
	`invoice_id` INT NOT NULL,
	`order_id` INT NOT NULL,
	`invoice_date` DATETIME NOT NULL,
	`email` VARCHAR(1) NOT NULL COLLATE 'utf8mb3_general_ci',
	`delivery_fee` DOUBLE NULL,
	`product_id` INT NOT NULL,
	`product_name` VARCHAR(1) NOT NULL COLLATE 'utf8mb3_general_ci',
	`product_price` DOUBLE NOT NULL,
	`qty` INT NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for table timestore.invoice_items
CREATE TABLE IF NOT EXISTS `invoice_items` (
  `order_id` int NOT NULL,
  `invoice_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL DEFAULT '0',
  `product_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '',
  `product_price` double NOT NULL DEFAULT (0),
  `qty` int NOT NULL,
  PRIMARY KEY (`invoice_id`,`product_id`) USING BTREE,
  KEY `invoice_items_ibfk_1` (`order_id`),
  CONSTRAINT `invoice_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `invoice_items_ibfk_2` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=997185141 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.messages
CREATE TABLE IF NOT EXISTS `messages` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `status` int DEFAULT NULL,
  `message` text,
  `sender` varchar(50) DEFAULT NULL,
  `subject` text,
  `date_time` timestamp NULL DEFAULT (now()),
  PRIMARY KEY (`message_id`),
  KEY `fk_users_message` (`sender`),
  CONSTRAINT `fk_users_message` FOREIGN KEY (`sender`) REFERENCES `users` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for view timestore.model_data
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `model_data` (
	`brand_id` INT NOT NULL,
	`brand_name` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`product_id` INT NOT NULL,
	`product_name` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`model_id` INT NOT NULL,
	`model` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`price` DOUBLE NULL,
	`qty` INT NULL,
	`added_time` DATETIME NULL,
	`img_path` VARCHAR(1) NOT NULL COLLATE 'utf8mb3_general_ci'
) ENGINE=MyISAM;

-- Dumping structure for table timestore.msg_status
CREATE TABLE IF NOT EXISTS `msg_status` (
  `msg_status_id` int NOT NULL,
  `msg_status` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`msg_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.order
CREATE TABLE IF NOT EXISTS `order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL DEFAULT '',
  `ordered_date` datetime DEFAULT (now()),
  `delivery_method` int DEFAULT NULL,
  `order_status` int DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `email` (`email`),
  KEY `delivery_method` (`delivery_method`),
  KEY `order_status` (`order_status`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`email`) REFERENCES `users` (`email`),
  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`delivery_method`) REFERENCES `delivery_method` (`id`),
  CONSTRAINT `order_ibfk_3` FOREIGN KEY (`order_status`) REFERENCES `order_status` (`order_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=993320419 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for view timestore.order_data
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `order_data` (
	`email` VARCHAR(1) NOT NULL COLLATE 'utf8mb3_general_ci',
	`fname` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`lname` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`order_id` INT NOT NULL,
	`ordered_date` DATETIME NULL,
	`order_qty` INT NULL,
	`delivery_method` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`delivery_fee` DOUBLE NULL,
	`model_id` INT NOT NULL,
	`model` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`price` DOUBLE NULL,
	`img_path` VARCHAR(1) NOT NULL COLLATE 'utf8mb3_general_ci',
	`product_id` INT NOT NULL,
	`product_name` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`brand_id` INT NOT NULL,
	`brand_name` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`status` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci'
) ENGINE=MyISAM;

-- Dumping structure for table timestore.order_has_model
CREATE TABLE IF NOT EXISTS `order_has_model` (
  `order_id` int NOT NULL,
  `model_id` int NOT NULL,
  `model_price` double DEFAULT NULL,
  `qty` int DEFAULT NULL,
  PRIMARY KEY (`order_id`,`model_id`),
  KEY `order_has_model_ibfk_1` (`model_id`),
  CONSTRAINT `order_has_model_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `model` (`model_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_has_model_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.order_status
CREATE TABLE IF NOT EXISTS `order_status` (
  `order_status_id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`order_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.product
CREATE TABLE IF NOT EXISTS `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) DEFAULT NULL,
  `brand_id` int DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `fk_product_brand` (`brand_id`),
  CONSTRAINT `fk_product_brand` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`brand_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.product_has_category
CREATE TABLE IF NOT EXISTS `product_has_category` (
  `product_id` int NOT NULL,
  `category_category_id` int NOT NULL,
  PRIMARY KEY (`product_id`,`category_category_id`),
  KEY `fk_product_has_category_category1_idx` (`category_category_id`),
  KEY `fk_product_has_category_product1_idx` (`product_id`),
  CONSTRAINT `fk_product_has_category_category1` FOREIGN KEY (`category_category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `fk_product_has_category_product1` FOREIGN KEY (`product_id`) REFERENCES `model` (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.model
CREATE TABLE IF NOT EXISTS `model` (
  `product_id` int DEFAULT NULL,
  `model_id` int NOT NULL AUTO_INCREMENT,
  `model` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  `qty` int DEFAULT NULL,
  `added_time` datetime DEFAULT (now()),
  PRIMARY KEY (`model_id`) USING BTREE,
  KEY `fk_product_model` (`product_id`),
  CONSTRAINT `fk_product_model` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.product_img
CREATE TABLE IF NOT EXISTS `product_img` (
  `img_path` varchar(150) NOT NULL,
  `model_id` int NOT NULL,
  PRIMARY KEY (`img_path`),
  KEY `fk_product_img_product1_idx` (`model_id`) USING BTREE,
  CONSTRAINT `fk_product_img_product1` FOREIGN KEY (`model_id`) REFERENCES `model` (`model_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.provinces
CREATE TABLE IF NOT EXISTS `provinces` (
  `province_id` int NOT NULL AUTO_INCREMENT,
  `province_en` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `province_si` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `province_ta` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`province_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.ratings
CREATE TABLE IF NOT EXISTS `ratings` (
  `user_email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `product_id` int NOT NULL,
  `ratings` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `comment` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_email`,`product_id`),
  KEY `fk_product_rating` (`product_id`),
  CONSTRAINT `fk_product_rating` FOREIGN KEY (`product_id`) REFERENCES `model` (`model_id`),
  CONSTRAINT `fk_user_rating` FOREIGN KEY (`user_email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.users
CREATE TABLE IF NOT EXISTS `users` (
  `fname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `gender_id` int NOT NULL,
  `status` int DEFAULT '1',
  `joined_date` date DEFAULT NULL,
  PRIMARY KEY (`email`),
  KEY `fk_users_gender_idx` (`gender_id`),
  KEY `fk_users_status` (`status`),
  CONSTRAINT `fk_users_gender` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`),
  CONSTRAINT `fk_users_status` FOREIGN KEY (`status`) REFERENCES `user_status` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.user_address
CREATE TABLE IF NOT EXISTS `user_address` (
  `address_line1` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `address_line2` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `address_city_id` int DEFAULT NULL,
  `users_email` varchar(50) NOT NULL,
  PRIMARY KEY (`users_email`),
  KEY `fk_user_address_users1_idx` (`users_email`),
  KEY `fk_user_address_cities1_idx` (`address_city_id`) USING BTREE,
  CONSTRAINT `fk_user_address_cities1` FOREIGN KEY (`address_city_id`) REFERENCES `cities` (`city_id`),
  CONSTRAINT `fk_user_address_users1` FOREIGN KEY (`users_email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for view timestore.user_address_data
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `user_address_data` (
	`email` VARCHAR(1) NOT NULL COLLATE 'utf8mb3_general_ci',
	`fname` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`lname` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`password` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`mobile` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`gender_id` INT NOT NULL,
	`address_line1` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`address_line2` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`address_city_id` INT NULL,
	`city_id` INT NOT NULL,
	`city_en` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`city_si` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`postcode` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`latitude` DOUBLE NULL,
	`longitude` DOUBLE NULL,
	`district_id` INT NOT NULL,
	`district_en` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`district_si` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`district_ta` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`province_id` INT NOT NULL,
	`province_en` VARCHAR(1) NOT NULL COLLATE 'utf8mb3_general_ci',
	`province_si` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`province_ta` VARCHAR(1) NULL COLLATE 'utf8mb3_general_ci',
	`img_path` TEXT NULL COLLATE 'utf8mb3_general_ci'
) ENGINE=MyISAM;

-- Dumping structure for table timestore.user_history
CREATE TABLE IF NOT EXISTS `user_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `buy_datetime` datetime DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP,
  `amount` int DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_user_history` (`user_id`),
  KEY `user_product` (`product_id`),
  CONSTRAINT `fk_user_history` FOREIGN KEY (`user_id`) REFERENCES `users` (`email`),
  CONSTRAINT `user_product` FOREIGN KEY (`product_id`) REFERENCES `model` (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.user_img
CREATE TABLE IF NOT EXISTS `user_img` (
  `email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `path` text,
  PRIMARY KEY (`email`) USING BTREE,
  CONSTRAINT `user_img_ibfk_1` FOREIGN KEY (`email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.user_status
CREATE TABLE IF NOT EXISTS `user_status` (
  `status_id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table timestore.watchlist
CREATE TABLE IF NOT EXISTS `watchlist` (
  `watchlist_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `users_email` varchar(50) NOT NULL,
  PRIMARY KEY (`watchlist_id`),
  KEY `fk_wishlist_product1_idx` (`product_id`),
  KEY `fk_wishlist_users1_idx` (`users_email`),
  CONSTRAINT `fk_wishlist_product1` FOREIGN KEY (`product_id`) REFERENCES `model` (`model_id`),
  CONSTRAINT `fk_wishlist_users1` FOREIGN KEY (`users_email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `invoice_data`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `invoice_data` AS select `invoice`.`invoice_id` AS `invoice_id`,`invoice`.`order_id` AS `order_id`,`invoice`.`invoice_date` AS `invoice_date`,`invoice`.`email` AS `email`,`invoice`.`delivery_fee` AS `delivery_fee`,`invoice_items`.`product_id` AS `product_id`,`invoice_items`.`product_name` AS `product_name`,`invoice_items`.`product_price` AS `product_price`,`invoice_items`.`qty` AS `qty` from (`invoice` join `invoice_items` on((`invoice`.`invoice_id` = `invoice_items`.`invoice_id`)));

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `model_data`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `model_data` AS select `brand`.`brand_id` AS `brand_id`,`brand`.`brand_name` AS `brand_name`,`product`.`product_id` AS `product_id`,`product`.`product_name` AS `product_name`,`model`.`model_id` AS `model_id`,`model`.`model` AS `model`,`model`.`price` AS `price`,`model`.`qty` AS `qty`,`model`.`added_time` AS `added_time`,`product_img`.`img_path` AS `img_path` from (((`brand` join `product` on((`brand`.`brand_id` = `product`.`brand_id`))) join `model` on((`product`.`product_id` = `model`.`product_id`))) join `product_img` on((`model`.`model_id` = `product_img`.`model_id`))) WITH CASCADED CHECK OPTION;

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `order_data`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `order_data` AS select `users`.`email` AS `email`,`users`.`fname` AS `fname`,`users`.`lname` AS `lname`,`order`.`order_id` AS `order_id`,`order`.`ordered_date` AS `ordered_date`,`order_has_model`.`qty` AS `order_qty`,`delivery_method`.`delivery_method` AS `delivery_method`,`delivery_method`.`price` AS `delivery_fee`,`order_has_model`.`model_id` AS `model_id`,`model`.`model` AS `model`,`model`.`price` AS `price`,`product_img`.`img_path` AS `img_path`,`product`.`product_id` AS `product_id`,`product`.`product_name` AS `product_name`,`brand`.`brand_id` AS `brand_id`,`brand`.`brand_name` AS `brand_name`,`order_status`.`status` AS `status` from ((((((((`order` join `users` on((`order`.`email` = `users`.`email`))) join `order_has_model` on((`order`.`order_id` = `order_has_model`.`order_id`))) join `model` on((`order_has_model`.`model_id` = `model`.`model_id`))) join `delivery_method` on((`order`.`delivery_method` = `delivery_method`.`id`))) join `product_img` on((`model`.`model_id` = `product_img`.`model_id`))) join `product` on((`model`.`product_id` = `product`.`product_id`))) join `brand` on((`product`.`brand_id` = `brand`.`brand_id`))) join `order_status` on((`order`.`order_status` = `order_status`.`order_status_id`))) WITH CASCADED CHECK OPTION;

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `user_address_data`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `user_address_data` AS select `users`.`email` AS `email`,`users`.`fname` AS `fname`,`users`.`lname` AS `lname`,`users`.`password` AS `password`,`users`.`mobile` AS `mobile`,`users`.`gender_id` AS `gender_id`,`user_address`.`address_line1` AS `address_line1`,`user_address`.`address_line2` AS `address_line2`,`user_address`.`address_city_id` AS `address_city_id`,`cities`.`city_id` AS `city_id`,`cities`.`city_en` AS `city_en`,`cities`.`city_si` AS `city_si`,`cities`.`postcode` AS `postcode`,`cities`.`latitude` AS `latitude`,`cities`.`longitude` AS `longitude`,`districts`.`district_id` AS `district_id`,`districts`.`district_en` AS `district_en`,`districts`.`district_si` AS `district_si`,`districts`.`district_ta` AS `district_ta`,`provinces`.`province_id` AS `province_id`,`provinces`.`province_en` AS `province_en`,`provinces`.`province_si` AS `province_si`,`provinces`.`province_ta` AS `province_ta`,`user_img`.`path` AS `img_path` from (((((`users` left join `user_address` on((`users`.`email` = `user_address`.`users_email`))) join `cities` on((`user_address`.`address_city_id` = `cities`.`city_id`))) join `districts` on((`cities`.`district_id` = `districts`.`district_id`))) join `provinces` on((`districts`.`province_id` = `provinces`.`province_id`))) join `user_img` on((`users`.`email` = `user_img`.`email`)));

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
