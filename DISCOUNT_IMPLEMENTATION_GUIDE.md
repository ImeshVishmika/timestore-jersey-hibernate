# Discount System - Implementation Guide

## 📋 Quick Implementation Checklist

### Phase 1: Database Setup
- [ ] Run `discount_migration.sql` to create tables
- [ ] Verify tables exist:
  ```sql
  SHOW TABLES LIKE 'discount%';
  SHOW COLUMNS FROM discount;
  SHOW COLUMNS FROM product_discount;
  ```

### Phase 2: Entity Registration
- [ ] Add to `hibernate.cfg.xml`:
  ```xml
  <mapping class="com.org.entity.Discount" />
  <mapping class="com.org.entity.ProductDiscount" />
  ```
- [ ] Verify no Hibernate errors on startup

### Phase 3: Service & Controller Deployment
- [ ] Compile Java files
- [ ] Deploy to Tomcat
- [ ] Check server logs for errors

### Phase 4: API Testing
- [ ] Test each endpoint with curl or Postman
- [ ] Verify response formats

### Phase 5: Integration
- [ ] Add to checkout flow
- [ ] Add to admin panel UI
- [ ] Test end-to-end

---

## 🔧 Step-by-Step Implementation

### Step 1: Database Tables

**Location**: `src/main/resources/discount_migration.sql`

**Action**:
```bash
# Connect to database
mysql -u root -p

# Use TimeStore database
use timestore;

# Execute the migration
source discount_migration.sql;

# Verify
SELECT * FROM discount LIMIT 1;
SELECT * FROM product_discount LIMIT 1;
```

---

### Step 2: Register Entities in Hibernate

**Location**: `src/main/resources/hibernate.cfg.xml`

**Action**: Add these lines in `<sessionFactory>` section:
```xml
<mapping class="com.org.entity.Discount" />
<mapping class="com.org.entity.ProductDiscount" />
```

---

### Step 3: Java Classes

**All files already created**:
- ✅ `com.org.entity.Discount`
- ✅ `com.org.entity.ProductDiscount`
- ✅ `com.org.dto.DiscountDTO`
- ✅ `com.org.service.DiscountService`
- ✅ `com.org.controller.admin.DiscountController`

**Action**: Compile and verify no errors:
```bash
mvn clean compile
# or use IDE compile
```

---

### Step 4: Tomcat Deployment

**Action**:
1. Build: `mvn clean package`
2. Deploy to Tomcat
3. Start Tomcat
4. Check logs for errors
5. Access: `http://localhost:8080/TimeStore/`

---

### Step 5: API Testing

**Test Endpoint 1: Create Discount**
```bash
curl -X POST http://localhost:8080/TimeStore/api/discount/create \
  -H "Content-Type: application/json" \
  -d '{
    "discountCode": "TEST10",
    "description": "Test discount - 10% off",
    "discountType": "ORDER",
    "valueType": "PERCENTAGE",
    "discountValue": 10,
    "usageLimit": 100,
    "expiryDate": "2026-12-31T23:59:59",
    "minimumOrderValue": 1000
  }'
```

**Expected Response**:
```json
{
  "state": true,
  "message": "Discount created successfully",
  "data": {
    "discountId": 1,
    "discountCode": "TEST10",
    ...
  }
}
```

**Test Endpoint 2: Get Active Discounts**
```bash
curl http://localhost:8080/TimeStore/api/discount/order/active
```

**Test Endpoint 3: Validate**
```bash
curl -X POST http://localhost:8080/TimeStore/api/discount/validate/order \
  -H "Content-Type: application/json" \
  -d '{
    "discountId": 1,
    "orderTotal": 5000
  }'
```

---

## 🎯 Integration Points

### 1. **Checkout Process**

In your checkout service/controller:

```java
// Get active order discounts
String discountsJson = discountService.getActiveOrderDiscounts();

// User selects discount
Integer discountId = getUserSelectedDiscount();

// Validate discount
String validationError = discountService.validateOrderDiscount(discountId, orderTotal);

if (validationError == null) {
    // Apply discount
    Discount discount = getDiscount(discountId);
    Double discountAmount = discountService.calculateDiscountAmount(discount, orderTotal);
    Double finalAmount = orderTotal - discountAmount;
    
    // Record usage
    discountService.useDiscount(discountId);
    
    // Save with order (add field to Order entity)
    order.setDiscount(discount);
    order.setDiscountAmount(discountAmount);
}
```

### 2. **Admin Dashboard**

Add to admin panel:

```html
<!-- Create Discount Form -->
<form id="createDiscountForm">
    <input type="text" name="discountCode" placeholder="CODE20" />
    <input type="text" name="description" />
    <select name="discountType">
        <option>PRODUCT</option>
        <option>ORDER</option>
    </select>
    <select name="valueType">
        <option>PERCENTAGE</option>
        <option>FIXED_AMOUNT</option>
    </select>
    <input type="number" name="discountValue" placeholder="20" />
    <input type="number" name="usageLimit" placeholder="100" />
    <input type="datetime-local" name="expiryDate" />
    <input type="number" name="minimumOrderValue" placeholder="Optional" />
    <button type="submit">Create Discount</button>
</form>

<script>
document.getElementById("createDiscountForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData);
    
    const response = await fetch('/api/discount/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    
    const result = await response.json();
    if (result.state) {
        alert('Discount created successfully!');
        loadDiscounts();
    } else {
        alert('Error: ' + result.message);
    }
});
</script>
```

### 3. **Product Listing**

Show discounts on products:

```java
// In ProductService or ProductController
public String getProductWithDiscounts(Integer productId) {
    // Get product
    Product product = getProduct(productId);
    
    // Get active discounts for this product
    String discountsJson = discountService.getActiveProductDiscounts(productId);
    
    // Return combined response
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("product", product);
    response.put("discounts", discountsJson);
    return gson.toJson(response);
}
```

---

## 📊 Database Maintenance

### Daily Tasks
```sql
-- Archive expired discounts
UPDATE discount SET status = 'ARCHIVED' 
WHERE expiry_date <= NOW() AND status = 'ACTIVE';

-- Check usage stats
SELECT discount_code, times_used, usage_limit, 
       (times_used/usage_limit*100) as percentage
FROM discount WHERE status = 'ACTIVE';
```

### Weekly Tasks
```sql
-- Find expiring soon
SELECT * FROM discount
WHERE expiry_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)
AND status = 'ACTIVE';

-- Check unused discounts
SELECT * FROM discount
WHERE times_used = 0 AND status = 'ACTIVE'
AND created_date < DATE_SUB(NOW(), INTERVAL 7 DAY);
```

---

## 🐛 Troubleshooting

### Issue: Discount code already exists
**Solution**: Use unique codes, check database for duplicates
```sql
SELECT * FROM discount WHERE discount_code = 'CODE20';
```

### Issue: Product discount not applying
**Solution**: Verify product_discount mapping exists
```sql
SELECT * FROM product_discount 
WHERE product_id = 1 AND discount_id = 1;
```

### Issue: Usage limit not enforced
**Solution**: Check that `times_used < usage_limit` in queries
```sql
SELECT * FROM discount 
WHERE discount_id = 1 
AND times_used >= usage_limit;
```

### Issue: Expiry date validation failing
**Solution**: Set future dates in expiryDate field
```java
LocalDateTime futureDate = LocalDateTime.now().plusDays(30);
discountDTO.setExpiryDate(futureDate);
```

### Issue: API returning 500 error
**Solution**: Check server logs for exceptions
```bash
tail -f tomcat/logs/catalina.out
```

---

## ✨ Advanced Features (Optional Enhancements)

### 1. Discount Categories
```java
@Column(name = "category")
private String category;  // E.g., "SEASONAL", "LOYALTY", "CLEARANCE"
```

### 2. Discount Combinations
```java
public boolean canCombineWith(Discount other) {
    // Implement logic for stackable discounts
}
```

### 3. Tiered Discounts
```java
// Multiple discount levels based on order amount
// Handle in DiscountService
```

### 4. Referral Bonuses
```java
// Link to User table for referral tracking
@ManyToOne
@JoinColumn(name = "referred_by_user_id")
private User referredByUser;
```

### 5. Analytics/Reports
```sql
SELECT 
    discount_code,
    usage_limit,
    times_used,
    (times_used / usage_limit * 100) as effectiveness,
    SUM(discount_value * times_used) as total_value_given
FROM discount
WHERE created_date >= DATE_SUB(NOW(), INTERVAL 30 DAY)
GROUP BY discount_code;
```

---

## 📝 Sample Implementation Timeline

| Time | Task | Status |
|------|------|--------|
| Day 1 | Database setup + entity creation | ✅ Complete |
| Day 2 | Service layer implementation | ✅ Complete |
| Day 3 | REST API endpoints | ✅ Complete |
| Day 4 | Admin UI for discount management | ⏳ TODO |
| Day 5 | Integration with checkout | ⏳ TODO |
| Day 6 | Product discount display | ⏳ TODO |
| Day 7 | Testing & QA | ⏳ TODO |

---

## 🎓 Usage Examples for Developers

### Example 1: Apply Discount to Order
```java
// In CheckoutService
public Order applyDiscount(Integer orderId, Integer discountId) {
    DiscountService discountService = new DiscountService();
    
    // Validate
    String error = discountService.validateOrderDiscount(discountId, order.total);
    if (error != null) throw new IllegalArgumentException(error);
    
    // Get discount
    Discount discount = getDiscount(discountId);
    
    // Calculate
    Double discountAmount = discountService.calculateDiscountAmount(discount, order.total);
    
    // Update order
    order.setDiscount(discount);
    order.setDiscountAmount(discountAmount);
    order.setFinalAmount(order.total - discountAmount);
    
    // Record usage
    discountService.useDiscount(discountId);
    
    return order;
}
```

### Example 2: Get Available Discounts
```java
// In CartService
public void loadAvailableDiscounts(Cart cart) {
    DiscountService discountService = new DiscountService();
    
    // Get active order discounts
    String orderDiscountsJson = discountService.getActiveOrderDiscounts();
    List<DiscountDTO> orderDiscounts = parseDiscounts(orderDiscountsJson);
    
    // Get product discounts
    List<DiscountDTO> productDiscounts = new ArrayList<>();
    for (CartItem item : cart.getItems()) {
        String productDiscountsJson = discountService.getActiveProductDiscounts(item.productId);
        productDiscounts.addAll(parseDiscounts(productDiscountsJson));
    }
    
    cart.setAvailableOrderDiscounts(orderDiscounts);
    cart.setAvailableProductDiscounts(productDiscounts);
}
```

---

## 🚀 Production Checklist

Before going live:
- [ ] Database backed up
- [ ] All tests passing
- [ ] Error handling verified
- [ ] API documentation updated
- [ ] Admin UI tested
- [ ] Discount codes documented
- [ ] Monitoring setup
- [ ] Rollback plan ready
- [ ] Staff trained
- [ ] Launch announcement prepared

---

## 📞 Support & Resources

**Documentation**: See `DISCOUNT_SYSTEM_DOCUMENTATION.md`  
**SQL Scripts**: See `discount_migration.sql`  
**API Reference**: See controller JavaDoc  
**Test Cases**: Available in DiscountService  

---

**Version**: 1.0  
**Status**: Ready for Implementation  
**Date**: March 21, 2026
