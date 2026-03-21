# End-to-End Testing: Add Model Workflow

## Architecture Overview
```
Frontend (Dashboard.html) → Modal Form
         ↓
JavaScript (product.js) → Form Validation + API Call
         ↓
ModelController (/api/model/add/{productId}) → Path parameter validation
         ↓
ModelService.addModel(ModelDTO) → Business logic validation + DB persistence
         ↓
Database (Model table) → Data stored with timestamp
```

---

## Test Environment Setup

### Prerequisites
- Tomcat server running on port 8080
- Application deployed at http://localhost:8080/TimeStore/
- At least one product exists in the database
- Admin is logged in
- Browser Developer Console is open (F12)

---

## Test Case 1: Successful Model Addition

### Steps:
1. Navigate to Admin Dashboard
2. Click on any product to open productDetailsModal
3. Click "Add Model" button (should open addModelModal)
4. Fill in form:
   - **Model Name:** "Daytona White Dial"
   - **Price:** 450000
   - **Quantity:** 5
   - **Image:** (leave empty for now)
5. Click "Submit"

### Expected Results:
✅ Success notification shows: "model added successfully"
✅ Modal closes automatically
✅ Form inputs clear
✅ New model appears in models list below
✅ Browser console shows no errors
✅ Network tab shows:
   - POST to `/api/model/add/{productId}` 
   - Status: 200
   - Response: `{"state": true, "message": "model added successfully", "data": {...}}`

### Database Verification:
```sql
-- Verify model was created
SELECT * FROM model WHERE model = 'Daytona White Dial' 
  AND price = 450000 AND qty = 5;

-- Should return 1 row with:
-- - model_id: AUTO_INCREMENT value
-- - product_id: matches selected product
-- - added_time: current timestamp
```

---

## Test Case 2: Frontend Validation - Empty Model Name

### Steps:
1. Open Add Model modal
2. Leave **Model Name** empty
3. Fill other fields:
   - Price: 350000
   - Quantity: 10
4. Click Submit

### Expected Results:
❌ Notiflix failure notification: "Model name is required"
❌ Modal stays open
❌ Form values retained
❌ No API request sent (check Network tab)

---

## Test Case 3: Frontend Validation - Invalid Price

### Steps - Test 3a (Zero Price)
1. Open Add Model modal
2. Fill form:
   - Model Name: "Submariner"
   - Price: 0
   - Quantity: 3
3. Click Submit

### Expected Results:
❌ Notiflix failure: "Model price must be greater than 0"
❌ Modal stays open, no API call

### Steps - Test 3b (Negative Price)
1. Same as above but Price: -50000

### Expected Results:
❌ Same failure notification

### Steps - Test 3c (Empty Price)
1. Leave Price field blank (or don't enter anything)
2. Fill other fields

### Expected Results:
❌ "Model price must be greater than 0"

---

## Test Case 4: Frontend Validation - Invalid Quantity

### Steps - Test 4a (Zero Quantity)
1. Open Add Model modal
2. Fill form:
   - Model Name: "GMT-Master II"
   - Price: 650000
   - Quantity: 0
3. Click Submit

### Expected Results:
❌ "Model quantity must be greater than 0"

### Steps - Test 4b (Negative Quantity)
1. Same but Quantity: -5

### Expected Results:
❌ Same error message

---

## Test Case 5: Backend Validation - Non-existent Product

### Steps:
1. Open browser Developer Console (F12)
2. Run this command:
```javascript
fetch('/api/model/add/99999', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
        model: 'Test Model',
        price: 500000,
        qty: 5,
        productId: 99999
    })
})
.then(r => r.json())
.then(d => console.log(d))
```

### Expected Results:
✅ Response: `{"state": false, "message": "product not found"}`
✅ Status: 200 (success response format, but with state: false)
✅ No data inserted into database

---

## Test Case 6: Backend Validation - Invalid ProductId in Path

### Steps:
1. In Developer Console, run:
```javascript
fetch('/api/model/add/0', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
        model: 'Test',
        price: 500000,
        qty: 5,
        productId: 0
    })
})
.then(r => r.json())
.then(d => console.log(d))
```

### Expected Results:
✅ Response: `{"state": false, "message": "Invalid product id"}`
✅ HTTP Status: 400 (Bad Request)

---

## Test Case 7: Data Type Validation

### Steps (Run in Developer Console):
1. Test with string price:
```javascript
fetch('/api/model/add/1', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
        model: 'Test Model',
        price: "not a number",
        qty: 5,
        productId: 1
    })
})
.then(r => r.json())
.then(d => console.log(d))
```

### Expected Results:
✅ Response: `{"state": false, "message": "model price must be greater than 0"}`
(Gson deserializes string "not a number" to null, which fails validation)

---

## Test Case 8: Timestamp Verification

### Steps:
1. Add a model successfully via UI
2. Run SQL:
```sql
SELECT model, price, qty, added_time, DATE(added_time) as add_date 
FROM model 
ORDER BY model_id DESC LIMIT 1;
```

### Expected Results:
✅ `added_time` should be current date/time (today's date)
✅ Timestamp should be within ±1 minute of current time
✅ Format: YYYY-MM-DD HH:mm:ss

---

## Test Case 9: Multiple Models for Same Product

### Steps:
1. Select a product
2. Add Model 1: "Dial A" / 100000 / 2
3. Add Model 2: "Dial B" / 200000 / 3
4. Add Model 3: "Dial C" / 300000 / 1

### Expected Results:
✅ All three models appear in the models list
✅ Each has unique model_id
✅ All have same product_id
✅ All show correct prices and quantities

---

## Test Case 10: Modal UI Behavior

### Steps:
1. Open Add Model modal
2. Fill form completely and submit successfully
3. Immediately try to open Add Model modal again
4. Fill and submit different model

### Expected Results:
✅ Form clears between uses
✅ Each submission creates independent model
✅ Both models visible in list
✅ No form state carries over between modal opens

---

## Test Case 11: Integration - Product and Model Together

### Steps:
1. Create a new product with models
2. Then add additional models to that product
3. Delete one model
4. Verify product still has correct models

### Expected Results:
✅ Models list updates correctly
✅ Delete doesn't affect other models
✅ Can add more models after deletion

---

## Test Case 12: Error Response Handling

### Steps:
1. Start with DevTools Network tab open
2. Add a model successfully
3. Check Network tab response details

### Expected Results:
✅ Content-Type: application/json
✅ Response body is valid JSON
✅ Always contains: `state`, `message`, `data` fields
✅ On success: `state: true`, data contains ModelDTO
✅ On failure: `state: false`, data: null

---

## Quick Test Checklist

| Test Case | Scenario | Pass/Fail | Notes |
|-----------|----------|-----------|-------|
| 1 | Valid model addition | [ ] | Check DB + notification |
| 2 | Empty model name | [ ] | Error notification |
| 3a | Zero price | [ ] | Error notification |
| 3b | Negative price | [ ] | Error notification |
| 3c | Empty price | [ ] | Error notification |
| 4a | Zero quantity | [ ] | Error notification |
| 4b | Negative quantity | [ ] | Error notification |
| 5 | Non-existent product ID | [ ] | Returns "product not found" |
| 6 | Invalid path parameter | [ ] | HTTP 400 response |
| 7 | Invalid data types | [ ] | Validation catches |
| 8 | Timestamp format | [ ] | Current datetime stored |
| 9 | Multiple models | [ ] | All created correctly |
| 10 | Modal reuse | [ ] | Form clears between uses |
| 11 | Product-model integration | [ ] | All operations work |
| 12 | Response format | [ ] | Valid JSON with state field |

---

## Performance Considerations

### Expected Performance:
- Form submission: < 500ms
- Model appears in list: < 1 second
- Database query for model list: < 100ms

### Stress Test:
1. Add 10 models in rapid succession
2. Each should complete successfully
3. All should appear in list

---

## Console Output to Monitor

### Expected Console Messages:
```
[INFO] POST /api/model/add/1
[INFO] Model added: model_id=XXX, product_id=1
[SUCCESS] Notiflix notification shown
[INFO] loadModels(1) called
[INFO] Models list rendered
```

### Watch for Errors:
```
[ERROR] The method addModel(ModelDTO) is not applicable for (Integer, ModelDTO) ← FIXED ✓
[ERROR] Cannot read property 'productId' of undefined ← Check product selection
[ERROR] Unexpected token in JSON ← Check API response format
[ERROR] TypeError: Cannot set property 'productId' of null ← Check ModelDTO initialization
```

---

## Debugging Tips

### If Test Fails:

1. **Check Network Tab:**
   - Request URL: `/api/model/add/{productId}`
   - Method: POST
   - Payload: JSON with model, price, qty
   - Response: Check state field (not HTTP status)

2. **Check Console:**
   - Look for JavaScript errors
   - Verify productId is being captured correctly
   - Check response parsing

3. **Check Server Logs:**
   - Look for validation error messages
   - Check for database connection issues
   - Verify transaction commit

4. **Check Database:**
   ```sql
   SELECT * FROM model ORDER BY model_id DESC LIMIT 5;
   ```
   - Verify record exists
   - Check timestamp
   - Confirm product_id matches selected product

5. **Restart & Retry:**
   - Clear browser cache
   - Restart Tomcat
   - Redeploy application

---

## SQL Verification Queries

```sql
-- Check all models for a product
SELECT m.model_id, m.model, m.price, m.qty, m.added_time, p.product_name
FROM model m
JOIN product p ON m.product_id = p.product_id
WHERE m.product_id = 1
ORDER BY m.added_time DESC;

-- Check total models in system
SELECT COUNT(*) as total_models FROM model;

-- Check models added today
SELECT model, price, qty, added_time
FROM model
WHERE DATE(added_time) = DATE(NOW())
ORDER BY added_time DESC;

-- Check for duplicate models (by name on same product)
SELECT product_id, model, COUNT(*) as count
FROM model
GROUP BY product_id, model
HAVING count > 1;
```

---

## Success Criteria

✅ All 12 test cases pass
✅ No JavaScript errors in console
✅ No Java compilation errors
✅ All models persist in database with correct data
✅ Modal UX is smooth (closes, clears, notifies)
✅ Frontend and backend validation work in tandem
✅ Timestamps are accurate
✅ Response format is consistent JSON

---

## Next Steps (After Validation)

- [ ] Implement image upload handling for models
- [ ] Add model duplicate prevention
- [ ] Add bulk model import
- [ ] Add model search/filter
- [ ] Create integration tests with JUnit
