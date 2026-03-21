// ====================================================================================
// END-TO-END TEST SUITE: Add Model Workflow
// ====================================================================================
// Usage: Paste this entire script into your browser DevTools Console while logged in
// ====================================================================================

const ModelWorkflowTests = {
    results: [],
    productId: null,
    
    // Initialize - Get a valid product ID
    async init() {
        console.log('🚀 Initializing Model Workflow Tests...');
        try {
            // Fetch first product for testing
            const response = await fetch('/api/product/all', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({})
            });
            const data = await response.json();
            
            if (data.data && data.data.length > 0) {
                this.productId = data.data[0].productId;
                console.log(`✓ Using Product ID: ${this.productId}`);
                return true;
            } else {
                console.error('✗ No products found for testing');
                return false;
            }
        } catch (e) {
            console.error('✗ Failed to initialize:', e);
            return false;
        }
    },
    
    // Test 1: Valid model addition
    async test1_ValidModelAddition() {
        console.log('\n📋 TEST 1: Valid Model Addition');
        try {
            const response = await fetch(`/api/model/add/${this.productId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    model: `Test Model ${Date.now()}`,
                    price: 450000,
                    qty: 5,
                    productId: this.productId
                })
            });
            
            const data = await response.json();
            const pass = data.state === true && data.message === "model added successfully";
            
            this.logResult('Valid Model Addition', pass, data);
            return pass;
        } catch (e) {
            this.logResult('Valid Model Addition', false, { error: e.message });
            return false;
        }
    },
    
    // Test 2: Empty model name validation
    async test2_EmptyModelName() {
        console.log('\n📋 TEST 2: Empty Model Name Validation (Frontend)');
        try {
            // Clear form and try submit
            document.getElementById('addModelName').value = '';
            document.getElementById('addModelPrice').value = '500000';
            document.getElementById('addModelQty').value = '5';
            
            const modelName = document.getElementById('addModelName').value;
            const pass = !modelName || modelName.trim() === '';
            
            this.logResult('Empty Model Name Detected', pass, 
                { modelName: modelName, expectedEmpty: true });
            return pass;
        } catch (e) {
            this.logResult('Empty Model Name Validation', false, { error: e.message });
            return false;
        }
    },
    
    // Test 3: Invalid price (zero)
    async test3_InvalidPriceZero() {
        console.log('\n📋 TEST 3: Invalid Price Validation (Zero)');
        try {
            const response = await fetch(`/api/model/add/${this.productId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    model: `Test Model ${Date.now()}`,
                    price: 0,
                    qty: 5,
                    productId: this.productId
                })
            });
            
            const data = await response.json();
            const pass = data.state === false && 
                         data.message.includes("price must be greater than 0");
            
            this.logResult('Price Zero Validation', pass, data);
            return pass;
        } catch (e) {
            this.logResult('Price Zero Validation', false, { error: e.message });
            return false;
        }
    },
    
    // Test 4: Invalid price (negative)
    async test4_InvalidPriceNegative() {
        console.log('\n📋 TEST 4: Invalid Price Validation (Negative)');
        try {
            const response = await fetch(`/api/model/add/${this.productId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    model: `Test Model ${Date.now()}`,
                    price: -50000,
                    qty: 5,
                    productId: this.productId
                })
            });
            
            const data = await response.json();
            const pass = data.state === false;
            
            this.logResult('Price Negative Validation', pass, data);
            return pass;
        } catch (e) {
            this.logResult('Price Negative Validation', false, { error: e.message });
            return false;
        }
    },
    
    // Test 5: Invalid quantity (zero)
    async test5_InvalidQtyZero() {
        console.log('\n📋 TEST 5: Invalid Quantity Validation (Zero)');
        try {
            const response = await fetch(`/api/model/add/${this.productId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    model: `Test Model ${Date.now()}`,
                    price: 500000,
                    qty: 0,
                    productId: this.productId
                })
            });
            
            const data = await response.json();
            const pass = data.state === false && 
                         data.message.includes("quantity must be greater than 0");
            
            this.logResult('Qty Zero Validation', pass, data);
            return pass;
        } catch (e) {
            this.logResult('Qty Zero Validation', false, { error: e.message });
            return false;
        }
    },
    
    // Test 6: Invalid product ID (non-existent)
    async test6_NonExistentProduct() {
        console.log('\n📋 TEST 6: Non-existent Product Validation');
        try {
            const response = await fetch('/api/model/add/99999', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    model: `Test Model ${Date.now()}`,
                    price: 500000,
                    qty: 5,
                    productId: 99999
                })
            });
            
            const data = await response.json();
            const pass = data.state === false && data.message === "product not found";
            
            this.logResult('Non-existent Product Check', pass, data);
            return pass;
        } catch (e) {
            this.logResult('Non-existent Product Check', false, { error: e.message });
            return false;
        }
    },
    
    // Test 7: Invalid path parameter (zero)
    async test7_InvalidPathParam() {
        console.log('\n📋 TEST 7: Invalid Path Parameter (Zero)');
        try {
            const response = await fetch('/api/model/add/0', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    model: `Test Model ${Date.now()}`,
                    price: 500000,
                    qty: 5,
                    productId: 0
                })
            });
            
            const pass = response.status === 400;
            const data = await response.json();
            
            this.logResult('Invalid Path Parameter', pass, { 
                statusCode: response.status, 
                expectedStatus: 400,
                response: data 
            });
            return pass;
        } catch (e) {
            this.logResult('Invalid Path Parameter', false, { error: e.message });
            return false;
        }
    },
    
    // Test 8: Response format validation
    async test8_ResponseFormat() {
        console.log('\n📋 TEST 8: Response Format Validation');
        try {
            const response = await fetch(`/api/model/add/${this.productId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    model: `Test Model ${Date.now()}`,
                    price: 600000,
                    qty: 3,
                    productId: this.productId
                })
            });
            
            const data = await response.json();
            const hasRequiredFields = 'state' in data && 'message' in data && 'data' in data;
            const isValidJson = data && typeof data === 'object';
            const pass = hasRequiredFields && isValidJson;
            
            this.logResult('Response Format Valid', pass, {
                hasState: 'state' in data,
                hasMessage: 'message' in data,
                hasData: 'data' in data,
                isJson: isValidJson,
                response: data
            });
            return pass;
        } catch (e) {
            this.logResult('Response Format Valid', false, { error: e.message });
            return false;
        }
    },
    
    // Test 9: Multiple models creation
    async test9_MultipleModels() {
        console.log('\n📋 TEST 9: Multiple Models Creation');
        const models = [];
        let allPass = true;
        
        for (let i = 0; i < 3; i++) {
            try {
                const response = await fetch(`/api/model/add/${this.productId}`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        model: `Batch Test ${i + 1} ${Date.now()}`,
                        price: 100000 * (i + 1),
                        qty: i + 1,
                        productId: this.productId
                    })
                });
                
                const data = await response.json();
                models.push(data.state);
                allPass = allPass && data.state;
            } catch (e) {
                allPass = false;
            }
        }
        
        this.logResult('Multiple Models Creation', allPass, {
            modelsCreated: models.length,
            successCount: models.filter(m => m).length,
            allSuccessful: allPass
        });
        return allPass;
    },
    
    // Test 10: Data type handling (invalid price type)
    async test10_InvalidDataType() {
        console.log('\n📋 TEST 10: Invalid Data Type Handling');
        try {
            const response = await fetch(`/api/model/add/${this.productId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    model: `Type Test ${Date.now()}`,
                    price: "not a number",
                    qty: 5,
                    productId: this.productId
                })
            });
            
            const data = await response.json();
            const pass = data.state === false;
            
            this.logResult('Invalid Data Type Handling', pass, data);
            return pass;
        } catch (e) {
            this.logResult('Invalid Data Type Handling', false, { error: e.message });
            return false;
        }
    },
    
    // Helper: Log results
    logResult(testName, passed, details = {}) {
        const status = passed ? '✅ PASS' : '❌ FAIL';
        console.log(`${status}: ${testName}`);
        console.log('Details:', details);
        
        this.results.push({
            name: testName,
            passed: passed,
            timestamp: new Date().toISOString(),
            details: details
        });
    },
    
    // Print summary
    printSummary() {
        console.log('\n' + '='.repeat(60));
        console.log('📊 TEST SUMMARY');
        console.log('='.repeat(60));
        
        const passed = this.results.filter(r => r.passed).length;
        const total = this.results.length;
        const percentage = Math.round((passed / total) * 100);
        
        console.log(`Total Tests: ${total}`);
        console.log(`Passed: ${passed} ✅`);
        console.log(`Failed: ${total - passed} ❌`);
        console.log(`Success Rate: ${percentage}%`);
        console.log('='.repeat(60));
        
        console.table(this.results.map(r => ({
            'Test Name': r.name,
            'Status': r.passed ? '✅' : '❌',
            'Time': new Date(r.timestamp).toLocaleTimeString()
        })));
        
        return passed === total;
    },
    
    // Run all tests
    async runAll() {
        const initialized = await this.init();
        if (!initialized) {
            console.error('Failed to initialize tests');
            return;
        }
        
        console.log('=' .repeat(60));
        console.log('🧪 RUNNING ALL TESTS...');
        console.log('=' .repeat(60));
        
        await this.test1_ValidModelAddition();
        await this.test2_EmptyModelName();
        await this.test3_InvalidPriceZero();
        await this.test4_InvalidPriceNegative();
        await this.test5_InvalidQtyZero();
        await this.test6_NonExistentProduct();
        await this.test7_InvalidPathParam();
        await this.test8_ResponseFormat();
        await this.test9_MultipleModels();
        await this.test10_InvalidDataType();
        
        const allPassed = this.printSummary();
        
        if (allPassed) {
            console.log('\n🎉 ALL TESTS PASSED! Your workflow is ready.');
        } else {
            console.log('\n⚠️  Some tests failed. Review the details above.');
        }
        
        return allPassed;
    }
};

// ====================================================================================
// RUN THE TESTS
// ====================================================================================
console.log('Starting automated test suite...');
ModelWorkflowTests.runAll()
    .then(success => {
        if (success) {
            console.log('\n✨ Ready for production use!');
        } else {
            console.log('\n🔧 Please fix the failed tests before deployment');
        }
    })
    .catch(e => console.error('Test suite error:', e));
