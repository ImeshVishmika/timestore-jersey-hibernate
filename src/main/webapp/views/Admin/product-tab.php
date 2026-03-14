<div class="tab-pane" id="product" role="tabpanel" aria-labelledby="product-tab" tabindex="0">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="fw-bold text-dark display-6">Products</h2>
            <p class="text-muted small mb-0">Manage your inventory catalog</p>
        </div>
        <div>
            <button class="btn btn-danger rounded-1 px-4 py-2 fw-bold shadow-sm" data-bs-toggle="modal" data-bs-target="#addProductModal">
                <i class="bi bi-plus-lg me-2"></i> Add Product
            </button>
            <button class="btn btn-dark rounded-1 px-4 py-2 fw-bold shadow-sm" data-bs-toggle="modal" data-bs-target="#addCouponModal">
                <i class="bi bi-ticket-perforated-fill me-2"></i> Add Coupon
            </button>
        </div>
    </div>

    <div class="card border-0 shadow-sm rounded-4 mb-4">
        <div class="card-body p-3">
            <div class="row g-3">
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text bg-white border-end-0"><i class="bi bi-search text-secondary"></i></span>
                        <input type="text" class="form-control border-start-0 ps-0" placeholder="Search by model">
                    </div>
                </div>
                <div class="col-md-3">
                    <select id="filerByBrand" class="form-select text-secondary fw-bold">
                        <option selected >Filter by Brand</option>

                    </select>
                </div>
                <div class="col-md-3">
                    <select id="sortByPrice" class="form-select text-secondary fw-bold">
                        <option disabled>Sort by Price</option>
                        <option value="3" >Revenue</option>
                        <option value="4" selected>Newest</option>
                        <option value="1">Price:Low to High</option>
                        <option value="2">Price:High to Low</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
    <div class="card border-0 shadow rounded-4">
        <div class="card-body p-0">
            <div class="table-responsive rounded-4">
                <table class="table table-hover align-middle mb-0">
                    <thead class="bg-light">
                        <tr>
                            <th scope="col" class="py-3 ps-4">Image</th>
                            <th scope="col" class="py-3">Model Info</th>
                            <th scope="col" class="py-3">Brand</th>
                            <th scope="col" class="py-3">Price</th>
                            <th scope="col" class="py-3">Total Sales</th>
                            <th scope="col" class="py-3">Stock</th>
                            <th scope="col" class="py-3">Status</th>
                            <th scope="col" class="py-3 text-end pe-4">Action</th>
                        </tr>
                    </thead>
                    <tbody id="modelTable">
                        <tr>
                            <td class="ps-4">
                                <div class="bg-white border rounded-3 d-flex align-items-center justify-content-center" style="width: 50px; height: 50px;">
                                    <img src="resources/img/placeholder.png" class="product-thumb" style="max-width: 100%; max-height: 100%;" alt="Thumb">
                                </div>
                            </td>
                            <td>
                                <h6 class="fw-bold text-dark mb-0">Model Name</h6>
                                <small class="text-muted">Product Name</small>
                            </td>
                            <td><span class="badge bg-light text-dark border">Brand Name</span></td>
                            <td class="fw-bold">Rs. 10,000</td>
                            <td>
                                <h6 class="fw-bold text-success mb-0">Rs. 1000</h6>
                                <small class="text-secondary fw-bold" style="font-size: 11px;">5 Sold</small>
                            </td>
                            <td>
                                <span class="fw-bold text-dark">
                                    25
                                </span>
                            </td>

                            <td>
                                <span class="badge bg-success bg-opacity-10 text-success rounded-pill px-3">In Stock</span>
                            </td>
                            <td class="text-end pe-4">
                                <div class="btn-group">
                                    <button id="loadProductDetailsModal" class="btn btn-sm btn-outline-dark border-0" data-bs-toggle="modal" data-bs-target="#productDetailsModal"
                                        data-test="resources/img/placeholder.png"
                                        data-price="10000"
                                        data-qty="25"
                                        data-product_id="1">
                                        <i class="bi bi-eye-fill"></i>
                                    </button>

                                    <button id="loadUpdateProductModal" class="btn btn-sm btn-outline-secondary border-0" data-bs-toggle="modal" data-bs-target="#updateProductModal"
                                        data-test="resources/img/placeholder.png"
                                        data-id="1"
                                        data-brand="1"

                                        data-product="Product Name"
                                        data-model="Model Name"
                                        data-price="10000"
                                        data-qty="25">
                                        <i class="bi bi-pencil-fill"></i></button>
                                    <button class="btn btn-sm btn-outline-danger border-0"><i class="bi bi-trash-fill"></i></button>
                                </div>
                            </td>
                        </tr>

                    </tbody>

                </table>
            </div>
        </div>

        <div class="card-footer bg-white border-0 py-3 d-flex justify-content-end rounded-bottom-4">
            <nav>
                <ul class="pagination pagination-sm mb-0 gap-1">
                    <li class="page-item disabled"><a class="page-link border-0 text-secondary" href="#"><i class="bi bi-chevron-left"></i></a></li>
                    <li class="page-item"><a class="page-link border-0 rounded bg-danger text-white fw-bold" href="#">1</a></li>
                    <li class="page-item"><a class="page-link border-0 rounded text-secondary fw-bold" href="#">2</a></li>
                    <li class="page-item"><a class="page-link border-0 rounded text-secondary fw-bold" href="#">3</a></li>
                    <li class="page-item"><a class="page-link border-0 text-secondary" href="#"><i class="bi bi-chevron-right"></i></a></li>
                </ul>
            </nav>
        </div>
    </div>

</div>

<div class="modal fade" id="productDetailsModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content rounded-4 border-0 shadow-lg">

            <div class="modal-header border-bottom-0 p-4 pb-0">
                <div>
                    <h5 class="modal-title fw-bold" id="modalProductTitle"></h5>
                    <p class="text-muted small mb-0">Product ID: <span id="modalProductId">---</span></p>
                </div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body p-4">
                <div class="row g-4 mb-4">

                    <div class="col-lg-4">
                        <div class="card border-0 shadow-sm rounded-4 text-center p-3 bg-light h-100 justify-content-center">
                            <img src="" id="modalImg" class="img-fluid rounded-3" style="max-height: 250px; object-fit: contain;">
                        </div>
                    </div>

                    <div class="col-lg-8">
                        <div class="row g-3">
                            <div class="col-md-4">
                                <div class="p-3 border rounded-3 text-center bg-white h-100">
                                    <h5 class="fw-bold mb-0 text-success" id="modalSold">---</h5>
                                    <small class="text-secondary fw-bold" style="font-size: 11px;">ITEMS SOLD</small>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="p-3 border rounded-3 text-center bg-white h-100">
                                    <h5 class="fw-bold mb-0 text-dark" id="modalStock">---</h5>
                                    <small class="text-secondary fw-bold" style="font-size: 11px;">CURRENT STOCK</small>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="p-3 bg-black text-white rounded-3 text-center h-100">
                                    <h5 class="fw-bold mb-0" id="modalTotalSales"></h5>
                                    <small class="text-white-50 fw-bold" style="font-size: 11px;">TOTAL SALES</small>
                                </div>
                            </div>

                            <div class="col-12 mt-3">
                                <div class="card border-0 shadow-sm rounded-4">
                                    <div class="card-body p-3">
                                        <h6 class="fw-bold small text-secondary mb-2">Sales Trend</h6>
                                        <div style="height: 200px; width: 100%;">
                                            <canvas id="productSalesChart"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card border-0 shadow-sm rounded-4 bg-light">
                    <div class="card-body p-4">
                        <h6 class="fw-bold text-dark mb-3"><i class="bi bi-layers-fill me-2 text-danger"></i>Models under this Product Group</h6>

                        <div class="table-responsive bg-white rounded-3 border">
                            <table class="table table-hover align-middle mb-0">
                                <thead class="bg-white border-bottom">
                                    <tr class="small text-secondary">
                                        <th class="ps-4 py-3">Variant Image</th>
                                        <th>Model Name</th>
                                        <th>Sold Items</th>
                                        <th>Price</th>
                                        <th>Stock</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody id="variantsTableBody">

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            </div>

            <div class="modal-footer border-top-0 p-3 bg-white rounded-bottom-4">
                <button type="button" class="btn btn-outline-dark fw-bold rounded-1 px-4" data-bs-dismiss="modal">Close</button>
            </div>

        </div>
    </div>
</div>

<div class="modal fade" id="updateProductModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content rounded-4 border-0 shadow-lg">
            <div class="modal-header border-bottom-0 p-4 pb-0">
                <h5 class="modal-title fw-bold">Update Product Details</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body p-4">


                <div class="row g-3">
                    <div class="col-12 text-center mb-2">
                        <label class="d-block mb-2 small fw-bold text-secondary text-start">Update Image (Optional)</label>
                        <div class="border border-1 border-secondary border-opacity-25 rounded-3 p-3 position-relative bg-light d-flex align-items-center justify-content-center gap-2" style="cursor: pointer;">
                            <i class="bi bi-camera-fill text-secondary"></i>
                            <span class="small text-muted fw-bold">Choose new file</span>
                            <input type="file" id="update_img" class="position-absolute top-0 start-0 w-100 h-100 opacity-0" name="img" accept="image/*">
                        </div>
                    </div>

                    <input type="text" id="update_id" hidden>
                    <input type="text" id="product_id" hidden>



                    <div class="col-12">
                        <label class="form-label fw-bold text-secondary small">Model</label>
                        <input type="text" class="form-control rounded-2" id="update_model" name="title">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-bold text-secondary small">Price (Rs.)</label>
                        <div class="input-group">
                            <span class="input-group-text bg-light">Rs.</span>
                            <input type="number" class="form-control" id="update_price" name="price">
                        </div>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-bold text-secondary small">Quantity</label>
                        <input type="number" class="form-control rounded-2" id="update_qty" name="qty" min="0">
                    </div>

                    <div class="col-12">
                        <label class="form-label fw-bold text-secondary small">Description</label>
                        <textarea class="form-control rounded-2" rows="3" id="update_desc" name="desc"></textarea>
                    </div>

                    <div class="col-12 mt-4">
                        <button id="updateProductBtn" data-bs-dismiss="modal" class="btn btn-dark w-100 py-2 fw-bold rounded-2">Save Changes</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addProductModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content rounded-4 border-0 shadow-lg">
            <div class="modal-header border-bottom-0 p-4 pb-0">
                <h5 class="modal-title fw-bold">Add New Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body p-4">
                    <div class="row g-3">
                        <div class="col-12 text-center mb-2">
                            <div class="border border-2 border-dashed border-secondary rounded-4 p-4 position-relative bg-light" style="cursor: pointer;">
                                <i class="bi bi-cloud-upload fs-1 text-secondary"></i>
                                <p class="small text-muted fw-bold mb-0">Click to upload product image</p>
                                <input id="productImg" type="file" class="position-absolute top-0 start-0 w-100 h-100 opacity-0" name="img" accept="image/*">
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label fw-bold text-secondary small">Brand Name</label>

                            <select class="form-select rounded-2" id="brandSelect" name="brand_id">
                                <option value="0" selected>Select Brand</option>

                            </select>

                            <input type="text" class="form-control rounded-2 d-none" id="brandInput" name="new_brand_name" placeholder="Enter new brand name">

                            <div class="text-end mt-1">
                                <a href="#" class="text-decoration-none small fw-bold text-danger" id="brandToggle" >
                                    <i class="bi bi-plus-circle me-1"></i>Add new brand
                                </a>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label fw-bold text-secondary small">Product</label>
                            <select class="form-select rounded-2 d-none" id="modelSelect" name="model_id">
                               
                            </select>
                            <input type="text" class="form-control rounded-2 " id="modelInput" placeholder="e.g. MRG-B2000">
                            <div class="text-end mt-1">
                                <a href="#" class="text-decoration-none small fw-bold text-danger " id="modelToggle" >
                                    Select Product
                                </a>
                            </div>
                        </div>

                        <div class="col-12">
                            <label class="form-label fw-bold text-secondary small">Model Title</label>
                            <input id="modelName" type="text" class="form-control rounded-2" placeholder="e.g. G-Shock MR-G Titanium Series" name="title">
                        </div>

                        <div class="col-md-6">
                            <label class="form-label fw-bold text-secondary small">Price (Rs.)</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light">Rs.</span>
                                <input id="productPrice" type="number" class="form-control" placeholder="0.00" name="price">
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label fw-bold text-secondary small">Initial Quantity</label>
                            <input id="productQty" type="number" class="form-control rounded-2" value="1" min="1" name="qty">
                        </div>

                        <div class="col-12">
                            <label class="form-label fw-bold text-secondary small">Description</label>
                            <textarea id="productDesc" class="form-control rounded-2" rows="3" placeholder="Product details..." name="desc"></textarea>
                        </div>

                        <div class="col-12 mt-4">
                            <button id="addProduct" class="btn btn-danger w-100 py-2 fw-bold rounded-2">Publish Product</button>
                        </div>
                    </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addCouponModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content rounded-4 border-0 shadow-lg">
            <div class="modal-header border-bottom-0 p-4 pb-0">
                <h5 class="modal-title fw-bold">Create Discount Code</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body p-4">
                <form action="addCouponProcess.php" method="POST">

                    <div class="row g-3 mb-3">
                        <div class="col-8">
                            <label class="form-label fw-bold text-secondary small">Code Name</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light border-end-0"><i class="bi bi-tag-fill text-secondary"></i></span>
                                <input type="text" class="form-control fw-bold text-uppercase border-start-0" placeholder="SUMMER2026" name="code" required>
                            </div>
                        </div>
                        <div class="col-4">
                            <label class="form-label fw-bold text-secondary small">Discount (%)</label>
                            <input type="number" class="form-control fw-bold" placeholder="10" name="percent" max="100" required>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold text-secondary small">Applies To</label>
                        <select class="form-select rounded-2 fw-bold text-dark" id="couponScope" name="scope" onchange="toggleCouponFields()">
                            <option value="all" selected>Entire Order</option>
                            <option value="product">Specific Product</option>
                        </select>
                    </div>

                    <div class="mb-3" id="minSpendDiv">
                        <label class="form-label fw-bold text-secondary small">Minimum Order Value</label>
                        <div class="input-group">
                            <span class="input-group-text bg-light border-end-0">Rs.</span>
                            <input type="number" class="form-control border-start-0" name="min_spend" placeholder="0 (No Minimum)">
                        </div>
                        <div class="form-text small">Coupon only valid if cart total exceeds this amount.</div>
                    </div>

                    <div class="mb-3 d-none bg-light p-3 rounded-3 border" id="productSelectDiv">
                        <label class="form-label fw-bold text-secondary small">Select Target Product</label>
                        <select class="form-select rounded-2" name="target_product_id">
                            <option value="0" selected>Choose Product...</option>

                        </select>
                        <div class="form-text small text-danger"><i class="bi bi-info-circle me-1"></i>Discount applies ONLY to this item.</div>
                    </div>

                    <div class="row g-3 mb-4">
                        <div class="col-6">
                            <label class="form-label fw-bold text-secondary small">Usage Limit</label>
                            <input type="number" class="form-control" placeholder="100" name="limit">
                        </div>
                        <div class="col-6">
                            <label class="form-label fw-bold text-secondary small">Expiry Date</label>
                            <input type="date" class="form-control" name="expiry" required>
                        </div>
                    </div>

                    <div class="d-grid">
                        <button type="submit" class="btn btn-danger fw-bold py-2 rounded-2 shadow-sm">
                            <i class="bi bi-plus-circle me-2"></i>Create Coupon
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="/timestore/public/assets/Script/Admin/product.js"></script>