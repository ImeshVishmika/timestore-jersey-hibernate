<div class="tab-pane" id="orders" role="tabpanel" aria-labelledby="orders-tab" tabindex="0">

    <main class=" ms-sm-auto  py-4">

        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold text-dark display-6">Orders</h2>
                <p class="text-muted small mb-0">Track and manage customer orders</p>
            </div>
            <div class="d-flex gap-2">
                <button class="btn btn-outline-dark rounded-1 fw-bold shadow-sm">
                    <i class="bi bi-download me-2"></i> Export CSV
                </button>
            </div>
        </div>

        <div class="row g-3 mb-4">
            <div class="col-md-3">
                <div class="card border-0 shadow-sm rounded-4 p-3 d-flex flex-row align-items-center justify-content-between">
                    <div>
                        <h6 class="text-secondary fw-bold mb-1">New Orders</h6>
                        <h4 class="fw-bold mb-0">12</h4>
                    </div>
                    <div class="bg-warning bg-opacity-10 text-warning p-3 rounded-circle"><i class="bi bi-bell-fill"></i></div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card border-0 shadow-sm rounded-4 p-3 d-flex flex-row align-items-center justify-content-between">
                    <div>
                        <h6 class="text-secondary fw-bold mb-1">To Ship</h6>
                        <h4 class="fw-bold mb-0">05</h4>
                    </div>
                    <div class="bg-primary bg-opacity-10 text-primary p-3 rounded-circle"><i class="bi bi-box-seam"></i></div>
                </div>
            </div>
        </div>

        <div class="card border-0 shadow-sm rounded-4 mb-4">
            <div class="card-body p-3">
                <div class="row g-3">
                    <div class="col-md-4">
                        <div class="input-group">
                            <span class="input-group-text bg-white border-end-0"><i class="bi bi-search text-secondary"></i></span>
                            <input type="text" class="form-control border-start-0 ps-0" placeholder="Search Order ID or Customer...">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <select class="form-select text-secondary fw-bold">
                            <option selected>Status: All</option>
                            <option value="pending">Pending</option>
                            <option value="shipped">Shipped</option>
                            <option value="delivered">Delivered</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <input type="date" class="form-control text-secondary fw-bold">
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
                                <th scope="col" class="py-3 ps-4">Order ID</th>
                                <th scope="col" class="py-3">Customer</th>
                                <th scope="col" class="py-3">Date</th>
                                <th scope="col" class="py-3">Total</th>
                                <th scope="col" class="py-3">Payment</th>
                                <th scope="col" class="py-3">Status</th>
                                <th scope="col" class="py-3 text-end pe-4">Action</th>
                            </tr>
                        </thead>
                        <tbody id="orderTableBody">

                            <tr>
                                <td class="ps-4 fw-bold text-danger">#ORD-</td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <div class="bg-dark text-white rounded-circle d-flex align-items-center justify-content-center me-2" style="width: 35px; height: 35px; font-size: 0.8rem;">JS</div>
                                        <div>
                                            <h6 class="mb-0 text-dark small fw-bold">John Smith</h6>
                                            <small class="text-muted" style="font-size: 0.75rem;">john@gmail.com</small>
                                        </div>
                                    </div>
                                </td>
                                <td class="text-secondary small fw-bold">Jan 20, 2026</td>
                                <td class="fw-bold">Rs. 45,000</td>
                                <td><span class="badge bg-light text-secondary border">Card</span></td>
                                <td><span class="status-badge"></span></td>
                                <td class="text-end pe-4">
                                    <div class="dropdown">
                                        <button class="btn btn-sm btn-light border" type="button" data-bs-toggle="dropdown">
                                            <i class="bi bi-three-dots-vertical"></i>
                                        </button>
                                        <ul class="dropdown-menu dropdown-menu-end shadow border-0 rounded-3">
                                            <li><a class="dropdown-item small py-2" data-order_id="" data-bs-toggle="modal" data-bs-target="#orderModal"><i class="bi bi-eye me-2"></i>View Details</a></li>
                                            <li><a class="dropdown-item small py-2" href="#"><i class="bi bi-truck me-2"></i>Mark as Shipped</a></li>
                                            <li>
                                                <hr class="dropdown-divider">
                                            </li>
                                            <li><a class="dropdown-item small py-2 text-danger" href="#"><i class="bi bi-x-circle me-2"></i>Cancel Order</a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
            </div>
            <div class="card-footer bg-white border-0 py-3 rounded-bottom-4 text-center">
                <button class="btn btn-sm btn-outline-secondary rounded-pill px-4">Load More Orders</button>
            </div>
        </div>
    </main>

    <div class="modal fade" id="orderModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
            <div class="modal-content rounded-4 border-0 shadow-lg">

                <div class="modal-header border-bottom-0 p-4 pb-0">
                    <div>
                        <h5 class="modal-title fw-bold">Order #<span id="viewOrderId"></span></h5>
                        <p class="text-muted small mb-0">Date: <span id="viewOrderDate"></span></p>
                    </div>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <!-- <div class="modal-header border-bottom p-4">
                                         <div class=""> 
                                            <span class="badge bg-light text-dark border px-3 py-2 rounded-pill" id="viewOrderStatus">Status</span> 
                                            <button type="button" class="btn-close ms-2" data-bs-dismiss="modal" aria-label="Close"></button>
                                         </div> 
                                    </div> -->

                <div class="modal-body p-4">

                    <div class="row mb-4">
                        <div class="col-md-6">
                            <h6 class="fw-bold text-dark text-uppercase small mb-3">Customer Details</h6>
                            <div class="p-3 bg-light rounded-3 ">
                                <h6 class="fw-bold mb-1" id="viewCustomerName">...</h6>
                                <p class="small text-secondary mb-1"><i class="bi bi-envelope me-2"></i><span id="viewCustomerEmail">...</span></p>
                                <p class="small text-secondary mb-0"><i class="bi bi-telephone me-2"></i><span id="viewCustomerPhone">...</span></p>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <h6 class="fw-bold text-dark text-uppercase small mb-3">Shipping Address</h6>
                            <div class="p-3 bg-light rounded-3 ">
                                <p class="small text-dark mb-0 lh-lg" id="viewAddress">
                                <p class="small text-secondary mb-1"></i><span id="viewCustomerAddressLine1">...</span></p>
                                <p class="small text-secondary mb-1"></i><span id="viewCustomerAddressLine2">...</span></p>
                                <p class="small text-secondary mb-1"></i><span id="viewCustomerCity">...</span></p>
                                <p class="small text-secondary mb-1"></i><span id="viewCustomerDistrict">...</span></p>
                                <p class="small text-secondary "></i><span id="viewCustomerProvince">...</span></p>
                                </p>
                            </div>
                        </div>
                    </div>

                    <h6 class="fw-bold text-dark text-uppercase small mb-3">Order Items</h6>
                    <div class="table-responsive border rounded-3 mb-4">
                        <table class="table table-borderless align-middle mb-0">
                            <thead class="bg-light border-bottom">
                                <tr class="small text-secondary text-uppercase">
                                    <th class="ps-4 py-3">Product</th>
                                    <th class="text-center">Qty</th>
                                    <th class="text-end">Unit Price</th>
                                    <th class="text-end pe-4">Total</th>
                                </tr>
                            </thead>
                            <tbody id="orderItemsTable">
                                <tr class="border-bottom">
                                    <td class="ps-4 py-3">
                                        <div class="d-flex align-items-center">
                                            <div class="bg-white border rounded-3 d-flex align-items-center justify-content-center me-3" style="width: 50px; height: 50px;">
                                                <img src="orderItemsImg" class="img-fluid p-1" style="max-height: 100%; object-fit: contain;" alt="Product">
                                            </div>

                                            <div>
                                                <h6 class="fw-bold text-dark mb-0 small">Casio G-Shock GA-2100</h6>
                                                <small class="text-muted" style="font-size: 11px;">Carbon Core Guard (Black)</small>
                                            </div>
                                        </div>
                                    </td>

                                    <td class="text-center align-middle">
                                        <span class="fw-bold text-secondary">2</span>
                                    </td>

                                    <td class="text-end align-middle">
                                        <span class="small fw-bold text-dark">Rs. 45,000</span>
                                    </td>

                                    <td class="text-end align-middle pe-4">
                                        <span class="fw-bold text-dark">Rs. 90,000</span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="row justify-content-end">
                        <div class="col-md-5">
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-secondary small fw-bold">Subtotal</span>
                                <span class="fw-bold text-dark" id="viewSubTotal">Rs. 0</span>
                            </div>
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-secondary small fw-bold">Delivery Fee</span>
                                <span class="fw-bold text-dark" id="viewDelivery"></span>
                            </div>
                            <div class="border-top pt-3 mt-2 d-flex justify-content-between align-items-center">
                                <span class="fw-bold text-dark fs-5">Grand Total</span>
                                <span class="fw-bold text-danger fs-4" id="viewGrandTotal">Rs. 0</span>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="modal-footer border-top-0 p-3 bg-light rounded-bottom-4 justify-content-between">
                    <button type="button" class="btn btn-outline-secondary fw-bold rounded-1 px-4" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-dark fw-bold rounded-1 px-4" ><i class="bi bi-printer-fill me-2"></i>Print Invoice</button>
                </div>

            </div>
        </div>
    </div>
</div>

<script src="/timestore/public/assets/Script/Admin/orders.js"></script>