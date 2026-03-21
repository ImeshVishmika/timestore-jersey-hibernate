# Discount System - Complete Documentation

## 📋 Overview

The Discount System is a comprehensive solution for managing promotional discounts that can be applied at two levels:
- **PRODUCT Level**: Discounts applied to specific products
- **ORDER Level**: Discounts applied to entire orders

#### Key Features:
✅ Expiry dates with automatic validation  
✅ Usage limits with tracking  
✅ Flexible value types (percentage or fixed amount)  
✅ Minimum order value requirements  
✅ Product quantity minimums  
✅ Status management (ACTIVE/INACTIVE/ARCHIVED)  
✅ Comprehensive audit trail (created/updated dates)  

---

## 🗄️ Database Schema

### Discount Table
```sql
CREATE TABLE discount (
    discount_id INT PRIMARY KEY AUTO_INCREMENT,
    discount_code VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255),
    discount_type ENUM('PRODUCT', 'ORDER') NOT NULL,
    value_type ENUM('PERCENTAGE', 'FIXED_AMOUNT') NOT NULL,
    discount_value DECIMAL(10,2) NOT NULL,
    usage_limit INT NOT NULL,
    times_used INT NOT NULL DEFAULT 0,
    expiry_date DATETIME NOT NULL,
    minimum_order_value DECIMAL(10,2),
    minimum_product_quantity INT,
    status ENUM('ACTIVE', 'INACTIVE', 'ARCHIVED') DEFAULT 'ACTIVE',
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_date DATETIME
);
```

### ProductDiscount Junction Table
```sql
CREATE TABLE product_discount (
    product_discount_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    discount_id INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (discount_id) REFERENCES discount(discount_id),
    UNIQUE(product_id, discount_id)
);
```

---

## 🏗️ Entity Structure

### Discount.java
```java
// Main discount entity with:
- discountId: Unique identifier
- discountCode: Unique code (e.g., "SUMMER20")
- discountType: PRODUCT or ORDER
- valueType: PERCENTAGE or FIXED_AMOUNT
- discountValue: Discount amount
- usageLimit: Maximum uses
- timesUsed: Current usage count
- expiryDate: When discount expires
- minimumOrderValue: Min order for eligibility (nullable)
- minimumProductQuantity: Min qty for product discounts (nullable)
- status: ACTIVE/INACTIVE/ARCHIVED
- createdDate: Auto-set on creation
- updatedDate: Auto-updated on changes

// Utility Methods:
- isExpired(): Checks if expiry date has passed
- isUsageLimitReached(): Checks if usage limit exceeded
- isActive(): Comprehensive check (not expired, within limit, active status)
- incrementUsageCount(): Increment usage on apply
```

### ProductDiscount.java
```java
// Junction table for product-discount mapping:
- productDiscountId: Auto-increment ID
- productId: Reference to Product
- discountId: Reference to Discount
- Ensures unique product-discount pairs
```

### DiscountDTO.java
```java
// Data Transfer Object with same fields as entity
// Includes helper methods for client-side validation:
- isExpired()
- isUsageLimitReached()
- isActive()
- getRemainingUsage()
```

---

## 📡 API Endpoints

### 1. Create Discount
```http
POST /api/discount/create
Content-Type: application/json

{
  "discountCode": "SUMMER20",
  "description": "Summer sale - 20% off",
  "discountType": "ORDER",
  "valueType": "PERCENTAGE",
  "discountValue": 20,
  "usageLimit": 1000,
  "expiryDate": "2026-06-21T23:59:59",
  "minimumOrderValue": 5000,
  "minimumProductQuantity": null
}

Response:
{
  "state": true,
  "message": "Discount created successfully",
  "data": { ...DiscountDTO... }
}
```

### 2. Get Discount by ID
```http
GET /api/discount/{discountId}

Response:
{
  "state": true,
  "message": "success",
  "data": { ...DiscountDTO... }
}
```

### 3. Get Active Order Discounts
```http
GET /api/discount/order/active

Response:
{
  "state": true,
  "message": "success",
  "data": [
    { ...DiscountDTO... },
    { ...DiscountDTO... }
  ]
}
```

### 4. Get Active Product Discounts
```http
GET /api/discount/product/{productId}/active

Response:
{
  "state": true,
  "message": "success",
  "data": [
    { ...DiscountDTO... }
  ]
}
```

### 5. Apply Discount to Product
```http
POST /api/discount/apply/{productId}/{discountId}

Response:
{
  "state": true,
  "message": "Discount applied to product successfully",
  "data": { ...ProductDiscount object... }
}
```

### 6. Remove Discount from Product
```http
DELETE /api/discount/remove/{productId}/{discountId}

Response:
{
  "state": true,
  "message": "Discount removed from product successfully",
  "data": null
}
```

### 7. Record Discount Usage
```http
POST /api/discount/use/{discountId}

Response:
{
  "state": true,
  "message": "Discount usage recorded",
  "data": { ...DiscountDTO with updated timesUsed... }
}
```

### 8. Update Discount Status
```http
PUT /api/discount/{discountId}/status/{status}

Path Variables:
- discountId: The discount to update
- status: ACTIVE | INACTIVE | ARCHIVED

Response:
{
  "state": true,
  "message": "Discount status updated successfully",
  "data": { ...DiscountDTO... }
}
```

### 9. Delete Discount
```http
DELETE /api/discount/{discountId}

Response:
{
  "state": true,
  "message": "Discount deleted successfully",
  "data": null
}
```

### 10. Validate Order Discount
```http
POST /api/discount/validate/order
Content-Type: application/json

{
  "discountId": 1,
  "orderTotal": 10000
}

Response:
{
  "state": true,
  "message": "Discount is valid"
}

Or on validation failure:
{
  "state": false,
  "message": "Order total must be at least Rs. 5000"
}
```

---

## 💡 Usage Examples

### Example 1: Create a Summer Percentage Discount
```java
DiscountDTO summerDiscount = new DiscountDTO();
summerDiscount.setDiscountCode("SUMMER20");
summerDiscount.setDescription("Summer sale - 20% off on orders above Rs. 5000");
summerDiscount.setDiscountType("ORDER");
summerDiscount.setValueType("PERCENTAGE");
summerDiscount.setDiscountValue(20);
summerDiscount.setUsageLimit(1000);
summerDiscount.setExpiryDate(LocalDateTime.of(2026, 6, 21, 23, 59, 59));
summerDiscount.setMinimumOrderValue(5000.0);

discountService.createDiscount(summerDiscount);
```

### Example 2: Create a Fixed Amount Product Discount
```java
DiscountDTO productDiscount = new DiscountDTO();
productDiscount.setDiscountCode("FLAT500");
productDiscount.setDescription("Rs. 500 off on selected watches");
productDiscount.setDiscountType("PRODUCT");
productDiscount.setValueType("FIXED_AMOUNT");
productDiscount.setDiscountValue(500);
productDiscount.setUsageLimit(100);
productDiscount.setExpiryDate(LocalDateTime.of(2026, 4, 30, 23, 59, 59));
productDiscount.setMinimumProductQuantity(2);  // Min 2 items

discountService.createDiscount(productDiscount);
```

### Example 3: Apply Discount to a Product
```java
// Apply discount ID 3 to product ID 1
discountService.applyDiscountToProduct(1, 3);
```

### Example 4: Get Active Discounts for a Product
```java
String result = discountService.getActiveProductDiscounts(1);
// Returns list of all active discounts for product ID 1
```

### Example 5: Record When Discount is Used
```java
// When customer applies discount at checkout
discountService.useDiscount(1);
// Increments times_used count and updates timestamp
```

### Example 6: Calculate Discount Amount
```java
Discount discount = session.get(Discount.class, 1);

// For percentage discount:
Double baseAmount = 10000; // Rs. 10,000
Double discountAmount = discountService.calculateDiscountAmount(discount, baseAmount);
// Returns: 2000 (20% of 10000)
Double finalAmount = baseAmount - discountAmount;  // 8000

// For fixed amount discount:
// Returns: 500 (or whatever fixed amount is set)
```

### Example 7: Validate Before Applying
```java
String validationError = discountService.validateOrderDiscount(1, 10000);

if (validationError == null) {
    // Discount is valid, can be applied
    discountService.useDiscount(1);
} else {
    // Show error to customer
    System.out.println(validationError);
}
```

---

## 🔍 Database Queries

### Get all active discounts
```sql
SELECT * FROM discount 
WHERE status = 'ACTIVE' 
  AND expiry_date > NOW() 
  AND times_used < usage_limit 
ORDER BY created_date DESC;
```

### Get active order discounts only
```sql
SELECT * FROM discount 
WHERE discount_type = 'ORDER' 
  AND status = 'ACTIVE' 
  AND expiry_date > NOW() 
  AND times_used < usage_limit;
```

### Get discounts applied to a product
```sql
SELECT d.* FROM discount d
JOIN product_discount pd ON d.discount_id = pd.discount_id
WHERE pd.product_id = 1
  AND d.status = 'ACTIVE'
  AND d.expiry_date > NOW();
```

### Check discount usage statistics
```sql
SELECT 
    discount_code, 
    usage_limit, 
    times_used, 
    (times_used / usage_limit * 100) as usage_percentage,
    status, 
    expiry_date
FROM discount
ORDER BY created_date DESC;
```

### Find discounts expiring soon
```sql
SELECT * FROM discount
WHERE expiry_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)
  AND status = 'ACTIVE';
```

---

## 🎯 Discount Types & Scenarios

### Scenario 1: ORDER-Level Percentage Discount
**Use Case**: "20% off on orders above Rs. 5,000"
```java
• discountType: ORDER
• valueType: PERCENTAGE
• discountValue: 20
• minimumOrderValue: 5000
• Applied to: Entire checkout total
• Calculation: Final Amount = BaseAmount × (1 - 20/100)
```

### Scenario 2: PRODUCT-Level Fixed Amount
**Use Case**: "Rs. 500 off on selected watches"
```java
• discountType: PRODUCT
• valueType: FIXED_AMOUNT
• discountValue: 500
• Applied to: Specific products (via product_discount table)
• Calculation: ItemPrice - 500 per item
```

### Scenario 3: Tiered Discount
**Use Case**: "Buy 3+ items, get 15% off"
```java
• discountType: PRODUCT
• valueType: PERCENTAGE
• minimumProductQuantity: 3
• discountValue: 15
• Check: Only apply if qty >= 3
```

### Scenario 4: Minimum Order Value
**Use Case**: "Free shipping + Rs. 1000 off for orders > Rs. 50,000"
```java
• discountType: ORDER
• valueType: FIXED_AMOUNT
• discountValue: 1000
• minimumOrderValue: 50000
• Validation: Check order total before applying
```

---

## 🛡️ Validation Rules

### On Create:
- ✅ Discount code cannot be empty or duplicate
- ✅ Discount value must be > 0
- ✅ Usage limit must be > 0
- ✅ Expiry date must be in the future
- ✅ Percentage discounts must be ≤ 100%
- ✅ Value type must be PERCENTAGE or FIXED_AMOUNT
- ✅ Discount type must be PRODUCT or ORDER

### On Usage:
- ✅ Discount must be ACTIVE status
- ✅ Usage limit must not be reached
- ✅ Must not be expired
- ✅ For ORDER type: minimum order value must be met

### On Application:
- ✅ Product must exist
- ✅ Discount must exist
- ✅ No duplicate product-discount pairs

---

## 🔧 Maintenance Tasks

### Archive Expired Discounts (Run Daily)
```sql
UPDATE discount SET status = 'ARCHIVED' 
WHERE expiry_date <= NOW() AND status = 'ACTIVE';
```

### Reset Usage Count (For Renewal)
```sql
UPDATE discount SET times_used = 0, updated_date = NOW() 
WHERE discount_id = 1;
```

### Check Discounts About to Expire
```sql
SELECT * FROM discount
WHERE expiry_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)
  AND status = 'ACTIVE';
```

---

## 📊 Sample Discounts

### 1. Summer Sale (ORDER - Percentage)
```json
{
  "discountCode": "SUMMER20",
  "description": "Summer Sale - 20% off on orders above Rs. 5000",
  "discountType": "ORDER",
  "valueType": "PERCENTAGE",
  "discountValue": 20,
  "usageLimit": 1000,
  "expiryDate": "2026-06-21T23:59:59",
  "minimumOrderValue": 5000
}
```

### 2. New Year Bonus (ORDER - Fixed Amount)
```json
{
  "discountCode": "NEWYEAR500",
  "description": "Flat Rs. 500 off on orders above Rs. 2000",
  "discountType": "ORDER",
  "valueType": "FIXED_AMOUNT",
  "discountValue": 500,
  "usageLimit": 500,
  "expiryDate": "2026-01-31T23:59:59",
  "minimumOrderValue": 2000
}
```

### 3. Product Clearance (PRODUCT - Percentage)
```json
{
  "discountCode": "CLEARANCE50",
  "description": "Clearance: 50% off on selected items",
  "discountType": "PRODUCT",
  "valueType": "PERCENTAGE",
  "discountValue": 50,
  "usageLimit": 100,
  "expiryDate": "2026-03-31T23:59:59"
}
```

---

## ⚠️ Important Notes

1. **Discount Code Format**: Use UPPERCASE, no spaces (e.g., "SUMMER20", "NEWYEAR500")
2. **Expiry Date**: Should be set in the future at creation time
3. **Usage Limit**: Set high for popular discounts, low for limited promotions
4. **Minimum Order Value**: Leave NULL if no minimum required (only for ORDER type)
5. **Status Management**: Use ARCHIVED for historical records, not DELETE (maintains audit trail)
6. **Backward Compatibility**: OrderDiscount can be applied to specific products via product_discount table

---

## 🚀 Setup Instructions

1. **Create Database Tables**:
   ```bash
   mysql -u root -p timestore < src/main/resources/discount_migration.sql
   ```

2. **Add to Hibernate Configuration**:
   - Add `Discount.java` to `hibernate.cfg.xml` mapping
   - Add `ProductDiscount.java` to mapping

3. **Register Controller**:
   - Ensure `DiscountController.java` is in classpath
   - Register in application context

4. **Test Endpoints**:
   ```bash
   curl -X POST http://localhost:8080/TimeStore/api/discount/create \
     -H "Content-Type: application/json" \
     -d '{"discountCode":"TEST","discountType":"ORDER",...}'
   ```

---

## 📞 Support

For questions or issues:
1. Check database tables are created correctly
2. Verify API endpoints are registered
3. Check Hibernate mappings are configured
4. Review error messages in server logs
5. Test with sample data

---

**Status**: ✅ Complete and Ready for Use  
**Date Created**: March 21, 2026  
**Version**: 1.0
