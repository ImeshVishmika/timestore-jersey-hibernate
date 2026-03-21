-- ============================================================================
-- Discount System Database Migration
-- Creates tables for managing product and order discounts
-- ============================================================================

-- Create DISCOUNT table
CREATE TABLE IF NOT EXISTS discount (
    discount_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    discount_code VARCHAR(50) NOT NULL UNIQUE COMMENT 'Unique discount code (e.g., SUMMER20, NEWYEAR50)',
    description VARCHAR(255) COMMENT 'Description of the discount',
    discount_type ENUM('PRODUCT', 'ORDER') NOT NULL COMMENT 'Type of discount: PRODUCT or ORDER',
    value_type ENUM('PERCENTAGE', 'FIXED_AMOUNT') NOT NULL COMMENT 'PERCENTAGE (%) or FIXED_AMOUNT (Rs.)',
    discount_value DECIMAL(10, 2) NOT NULL COMMENT 'Discount amount (percentage 0-100 or fixed amount in Rs.)',
    usage_limit INT NOT NULL COMMENT 'Maximum number of times this discount can be used',
    times_used INT NOT NULL DEFAULT 0 COMMENT 'Current number of times used',
    expiry_date DATETIME NOT NULL COMMENT 'When the discount expires',
    minimum_order_value DECIMAL(10, 2) DEFAULT NULL COMMENT 'Minimum order total to apply discount (nullable)',
    minimum_product_quantity INT DEFAULT NULL COMMENT 'Minimum quantity for product discounts (nullable)',
    status ENUM('ACTIVE', 'INACTIVE', 'ARCHIVED') NOT NULL DEFAULT 'ACTIVE' COMMENT 'Discount status',
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'When discount was created',
    updated_date DATETIME DEFAULT NULL COMMENT 'When discount was last updated',
    INDEX idx_discount_code (discount_code),
    INDEX idx_discount_type (discount_type),
    INDEX idx_status (status),
    INDEX idx_expiry_date (expiry_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Table storing all discount codes and configurations';

-- ============================================================================

-- Create PRODUCT_DISCOUNT junction table
CREATE TABLE IF NOT EXISTS product_discount (
    product_discount_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    product_id INT NOT NULL COMMENT 'Reference to product',
    discount_id INT NOT NULL COMMENT 'Reference to discount',
    UNIQUE KEY uk_product_discount (product_id, discount_id),
    CONSTRAINT fk_product_discount_product FOREIGN KEY (product_id) 
        REFERENCES product(product_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_product_discount_discount FOREIGN KEY (discount_id) 
        REFERENCES discount(discount_id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_product_id (product_id),
    INDEX idx_discount_id (discount_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Junction table mapping products to specific discounts';

-- ============================================================================

-- Insert sample discounts
-- Note: Uncomment to add sample data

/*
-- Example 1: Order-level percentage discount (Summer sale)
INSERT INTO discount (discount_code, description, discount_type, value_type, discount_value, usage_limit, expiry_date, minimum_order_value, status)
VALUES ('SUMMER20', 'Summer Sale - 20% off on orders above Rs. 5000', 'ORDER', 'PERCENTAGE', 20.00, 1000, DATE_ADD(NOW(), INTERVAL 60 DAY), 5000.00, 'ACTIVE');

-- Example 2: Order-level fixed amount discount (New Year promotion)
INSERT INTO discount (discount_code, description, discount_type, value_type, discount_value, usage_limit, expiry_date, minimum_order_value, status)
VALUES ('NEWYEAR500', 'New Year Special - Flat Rs. 500 off on orders above Rs. 2000', 'ORDER', 'FIXED_AMOUNT', 500.00, 500, DATE_ADD(NOW(), INTERVAL 30 DAY), 2000.00, 'ACTIVE');

-- Example 3: Product-level discount (Clearance sale on specific products)
INSERT INTO discount (discount_code, description, discount_type, value_type, discount_value, usage_limit, expiry_date, status)
VALUES ('PRODUCT_CLR', 'Product Clearance - 50% off on selected items', 'PRODUCT', 'PERCENTAGE', 50.00, 100, DATE_ADD(NOW(), INTERVAL 15 DAY), 'ACTIVE');

-- Example 4: Expired discount (inactive)
INSERT INTO discount (discount_code, description, discount_type, value_type, discount_value, usage_limit, expiry_date, status)
VALUES ('EXPIRED_SALE', 'Old expired discount', 'ORDER', 'PERCENTAGE', 10.00, 100, DATE_SUB(NOW(), INTERVAL 5 DAY), 'INACTIVE');

-- Apply sample product discount
-- INSERT INTO product_discount (product_id, discount_id) VALUES (1, 3);
-- INSERT INTO product_discount (product_id, discount_id) VALUES (2, 3);
*/

-- ============================================================================

-- Query examples and maintenance procedures:

-- 1. Get all active discounts that haven't expired
-- SELECT * FROM discount 
-- WHERE status = 'ACTIVE' 
-- AND expiry_date > NOW() 
-- AND times_used < usage_limit 
-- ORDER BY created_date DESC;

-- 2. Get active order discounts only
-- SELECT * FROM discount 
-- WHERE discount_type = 'ORDER' 
-- AND status = 'ACTIVE' 
-- AND expiry_date > NOW() 
-- AND times_used < usage_limit;

-- 3. Get discounts applied to a specific product
-- SELECT d.* FROM discount d
-- JOIN product_discount pd ON d.discount_id = pd.discount_id
-- WHERE pd.product_id = 1
-- AND d.status = 'ACTIVE'
-- AND d.expiry_date > NOW();

-- 4. Check discount usage stats
-- SELECT discount_code, usage_limit, times_used, 
--        (times_used / usage_limit * 100) as usage_percentage,
--        status, expiry_date
-- FROM discount
-- ORDER BY created_date DESC;

-- 5. Archive expired discounts (maintenance)
-- UPDATE discount SET status = 'ARCHIVED' 
-- WHERE expiry_date <= NOW() AND status = 'ACTIVE';

-- 6. Check discounts about to expire (next 7 days)
-- SELECT * FROM discount
-- WHERE expiry_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)
-- AND status = 'ACTIVE';

-- 7. Reset usage count for a discount
-- UPDATE discount SET times_used = 0, updated_date = NOW() 
-- WHERE discount_id = 1;

-- ============================================================================
-- End of Migration
-- ============================================================================
