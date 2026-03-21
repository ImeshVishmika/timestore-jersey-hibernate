# Quick Reference: Add Model Workflow Testing

## 🔄 Complete Workflow Flow

```
┌─────────────────────────────────────────────────────────────┐
│  FRONTEND: Dashboard                                        │
│  ├─ Click Product → Open productDetailsModal               │
│  └─ Click "Add Model" → Open addModelModal                 │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│  FORM INPUT: addModelModal                                  │
│  ├─ Input: addModelName (e.g., "Daytona White")            │
│  ├─ Input: addModelPrice (e.g., 450000)                    │
│  ├─ Input: addModelQty (e.g., 5)                           │
│  └─ Button: submitAddModel (click)                         │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│  JAVASCRIPT VALIDATION (product.js)                         │
│  ├─ [ ] Model name not empty?                              │
│  ├─ [ ] Price > 0?                                         │
│  ├─ [ ] Qty > 0?                                           │
│  └─ [ ] All validated? → Proceed to API Call              │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│  API REQUEST                                                │
│  POST /api/model/add/{productId}                           │
│  ├─ URL Path: productId from updateProductBtn.dataset      │
│  ├─ Body: {                                                 │
│  │    model: "string",                                      │
│  │    price: number,                                        │
│  │    qty: number,                                          │
│  │    productId: number                                     │
│  │  }                                                       │
│  └─ Headers: Content-Type: application/json                │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│  BACKEND: ModelController.addModel()                        │
│  ├─ [ ] Validate @PathParam productId > 0?                 │
│  ├─ [ ] Deserialize JSON to ModelDTO                       │
│  ├─ [ ] Set ModelDTO.productId from path parameter         │
│  └─ [ ] Call ModelService.addModel(modelDTO)              │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│  BACKEND: ModelService.addModel()                           │
│  ├─ [ ] Validate productId > 0?                            │
│  ├─ [ ] Validate model name not empty?                     │
│  ├─ [ ] Validate price > 0?                                │
│  ├─ [ ] Validate qty > 0?                                  │
│  ├─ [ ] Verify Product exists in DB?                      │
│  ├─ [ ] Create Model entity                               │
│  ├─ [ ] Set addedTime = LocalDateTime.now()               │
│  └─ [ ] Persist to DB & commit transaction                │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│  API RESPONSE (JSON)                                        │
│  Success: {                                                 │
│    "state": true,                                           │
│    "message": "model added successfully",                   │
│    "data": { ... ModelDTO ... }                            │
│  }                                                          │
│                                                             │
│  Failure: {                                                 │
│    "state": false,                                          │
│    "message": "error description",                          │
│    "data": null                                            │
│  }                                                          │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│  JAVASCRIPT RESPONSE HANDLING (product.js)                  │
│  ├─ [ ] Check response.ok                                  │
│  ├─ [ ] Parse JSON                                         │
│  ├─ [ ] Check response.state === true?                    │
│  │       ├─ YES: Proceed to success actions               │
│  │       └─ NO: Show error notification                   │
│  └─ On Success:                                            │
│       ├─ Show Notiflix.success()                           │
│       ├─ Clear form inputs                                 │
│       ├─ Close modal (bootstrap.Modal.hide)               │
│       └─ Reload models list (loadModels)                  │
└─────────────────────────────────────────────────────────────┘
```

---

## ✅ Validation Layers

### Layer 1: Frontend JavaScript Validation
```javascript
// File: product.js (line ~660)
✓ modelName cannot be empty
✓ modelPrice must be > 0
✓ modelQty must be > 0
✓ Fails BEFORE calling API (fail-fast)
```

### Layer 2: Backend Controller Validation
```java
// File: ModelController.java (line ~59-62)
✓ productId from @PathParam must be > 0
✓ Returns HTTP 400 if invalid
```

### Layer 3: Backend Service Validation
```java
// File: ModelService.java (line ~212-245)
✓ productId must be > 0
✓ model (name) cannot be null/empty
✓ price must be > 0
✓ qty must be > 0
✓ Product must exist in database
✓ All fail-fast with descriptive error messages
```

### Layer 4: Database Constraints
```sql
-- Implicit via Hibernate/FK constraints:
✓ product_id must reference existing product.product_id
✓ All numeric fields validated at SQL level
✓ Timestamp automatically set by LocalDateTime.now()
```

---

## 🧪 How to Run Automated Tests

### Option 1: Run in Browser Console (Recommended)
```javascript
1. Open TimeStore Admin (http://localhost:8080/TimeStore/)
2. Navigate to Dashboard
3. Open DevTools: F12 or Ctrl+Shift+I
4. Go to Console tab
5. Copy & paste content from: test-add-model-automated.js
6. Press Enter - Tests will run automatically
```

### Option 2: From File
```javascript
// In browser console:
1. Open test file: cat webapp/TEST_ADD_MODEL_WORKFLOW.md
2. Copy the JavaScript section
3. Paste in console
4. Run: ModelWorkflowTests.runAll()
```

### Expected Console Output:
```
🚀 Initializing Model Workflow Tests...
✓ Using Product ID: 1
============================================================
🧪 RUNNING ALL TESTS...
============================================================

📋 TEST 1: Valid Model Addition
✅ PASS: Valid Model Addition
Details: {state: true, message: "model added successfully", data: {...}}

📋 TEST 2: Empty Model Name Validation (Frontend)
✅ PASS: Empty Model Name Detected
...

📊 TEST SUMMARY
============================================================
Total Tests: 10
Passed: 10 ✅
Failed: 0 ❌
Success Rate: 100%
============================================================
[Table showing all test results]

🎉 ALL TESTS PASSED! Your workflow is ready.
✨ Ready for production use!
```

---

## 🔍 Manual Test Workflow (Step-by-Step)

### Test Setup (Once)
1. Start Tomcat: `catalina.bat run` or use IDE
2. Deploy TimeStore application
3. Login to Admin Dashboard
4. Keep DevTools open (F12 → Network & Console tabs)

### Test 1: Successful Addition
```
Steps:
1. Click any product row
2. Click "Add Model" button in productDetailsModal
3. Fill form:
   - Name: "Daytona Test"
   - Price: 450000
   - Qty: 5
4. Click Submit

Verify:
✅ Success notification appears
✅ Modal closes
✅ Form clears
✅ Model appears in list
✅ Network tab shows: 
   - POST /api/model/add/1 → 200 OK
   - Response: {"state": true, ...}
```

### Test 2: Input Validation
```
Steps:
1. Open Add Model modal
2. Try these scenarios:

Scenario A: Empty Name
  - Leave name blank
  - Enter price: 100000, qty: 5
  - Click Submit
  - Verify: Error notification "Model name is required"

Scenario B: Invalid Price
  - Enter name: "Test"
  - Enter price: -5000 or 0
  - Enter qty: 5
  - Click Submit
  - Verify: Error "price must be greater than 0"

Scenario C: Invalid Qty
  - Enter name: "Test"
  - Enter price: 100000
  - Enter qty: -1 or 0
  - Click Submit
  - Verify: Error "quantity must be greater than 0"
```

### Test 3: Verify Database
```sql
-- On local database:
1. Open SQL client (MySQL Workbench, DBeaver, etc.)
2. Connect to TimeStore database
3. Run this query:

SELECT m.model_id, m.model, m.price, m.qty, 
       m.added_time, p.product_name
FROM model m
JOIN product p ON m.product_id = p.product_id
WHERE m.product_id = 1
ORDER BY m.added_time DESC
LIMIT 5;

Expected columns:
- model_id: AUTO_INCREMENT value (e.g., 42)
- model: Your entered name (e.g., "Daytona Test")
- price: Your entered price (e.g., 450000)
- qty: Your entered quantity (e.g., 5)
- added_time: Current date/time (within last minute)
- product_name: Your selected product
```

---

## 🐛 Troubleshooting

### Issue: Modal doesn't open
```
Cause: Product not selected
Fix: Click a product row first, then try "Add Model"
```

### Issue: Form submits but nothing happens
```
Cause: Network error or API endpoint not found
Fix: 
1. Check Network tab in DevTools
2. Verify URL is: POST /api/model/add/{productId}
3. Check server logs for errors
4. Verify Tomcat is running
```

### Issue: Error "Invalid product id"
```
Cause: productId in path is invalid
Fix:
1. Ensure you selected an existing product
2. Check that product exists in database
3. Verify productId > 0
```

### Issue: Error "product not found"
```
Cause: Product ID doesn't exist in database
Fix:
1. Create/find an existing product
2. Use that product's ID for testing
3. Run: SELECT MAX(product_id) FROM product;
```

### Issue: Error "model name is required"
```
Cause: Model name field is empty on submission
Fix: Enter a non-empty model name (min 1 character)
```

### Issue: Form clears but modal doesn't close
```
Cause: Bootstrap modal instance issue
Fix:
1. Refresh page: Ctrl+R
2. Try closing modal manually with X button
3. Check console for JavaScript errors
```

### Issue: Localhost:8080 connection refused
```
Cause: Tomcat not running
Fix: 
1. Start Tomcat: catalina.bat run
2. Wait for "Server startup" message
3. Retry http://localhost:8080/TimeStore/
```

---

## 📊 Success Checklist

Mark these off as you complete each test:

```
Frontend Tests:
[ ] Modal opens when "Add Model" clicked
[ ] Form has all required fields
[ ] Form clears after successful submit
[ ] Modal closes after successful submit

Validation Tests:
[ ] Empty name shows error notification
[ ] Zero price shows error notification
[ ] Negative price shows error notification
[ ] Zero qty shows error notification
[ ] Negative qty shows error notification
[ ] Non-existent product returns error

API Tests:
[ ] POST request goes to /api/model/add/{productId}
[ ] Request includes JSON body with model, price, qty
[ ] Response has state, message, data fields
[ ] Success response has state: true
[ ] Failure response has state: false

Database Tests:
[ ] Model appears in model table
[ ] All fields match what was entered
[ ] added_time is current date/time
[ ] product_id matches selected product

Integration Tests:
[ ] Can add multiple models to same product
[ ] Can add models after deleting one
[ ] Models list updates after addition
[ ] Success notification appears
```

---

## 📝 Test Log Template

Keep this handy for testing purposes:

```
Date: _______________
Tester: _______________
Environment: Local / Dev / Production
Browser: Chrome / Firefox / Safari / Edge

Test Results:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Test Case 1 - Valid Addition
  Product ID: ___________
  Model Name: ___________
  Price: ___________
  Quantity: ___________
  Result: PASS / FAIL
  Notes: ___________

Test Case 2 - Empty Name
  Result: PASS / FAIL
  Error Message: ___________
  
Test Case 3 - Invalid Price
  Result: PASS / FAIL
  Error Message: ___________

Test Case 4 - Invalid Qty
  Result: PASS / FAIL
  Error Message: ___________

Test Case 5 - Non-existent Product
  Result: PASS / FAIL
  Error Message: ___________

Database Verification:
  [SQL Query Results Here]
  
Overall Status: ALL PASS / SOME FAILED / BLOCKED

Issues Found:
  [ ] Issue 1: ___________
  [ ] Issue 2: ___________
  
Sign-off: ___________
```

---

## 🚀 Next Steps After Testing

If all tests pass ✅:
1. ✅ Workflow is complete and production-ready
2. ✅ Ready to test image upload (next feature)
3. ✅ Ready to move to other features

If any tests fail ❌:
1. ❌ Don't deploy yet
2. ❌ Check troubleshooting section
3. ❌ Review error messages in console/logs
4. ❌ Fix identified issues
5. ❌ Re-run tests

---

## 📞 Support Information

Files referenced:
- [Automated Test Script](test-add-model-automated.js)
- [Detailed Test Cases](TEST_ADD_MODEL_WORKFLOW.md)
- Frontend Code: `webapp/views/Admin/assets/script/product.js`
- Backend Code: `src/main/java/com/org/controller/user/ModelController.java`
- Service Code: `src/main/java/com/org/service/ModelService.java`
- HTML Form: `webapp/views/Admin/dashboard.html`
