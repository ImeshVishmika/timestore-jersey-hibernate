-- ============================================================================
-- Migration: Add model_price to order_has_model
-- Target DB: MySQL
-- Safe for repeated execution (idempotent)
-- ============================================================================

-- 1) Add model_price column only if it does not already exist
SET @column_exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'order_has_model'
      AND COLUMN_NAME = 'model_price'
);

SET @add_column_sql := IF(
    @column_exists = 0,
    'ALTER TABLE order_has_model ADD COLUMN model_price DOUBLE NULL AFTER model_id',
    'SELECT ''Column model_price already exists in order_has_model'' AS message'
);

PREPARE stmt FROM @add_column_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2) Backfill model_price from current model.price where missing
UPDATE order_has_model ohm
JOIN model m ON m.model_id = ohm.model_id
SET ohm.model_price = m.price
WHERE ohm.model_price IS NULL;

-- Optional verification
-- SELECT order_id, model_id, model_price, qty FROM order_has_model LIMIT 20;
