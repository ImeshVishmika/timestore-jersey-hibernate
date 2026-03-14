<div class="tab-pane" id="customers" role="tabpanel" aria-labelledby="customers-tab" tabindex="0">

    <main class="ms-sm-auto  py-4">

        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold text-dark display-6">Customers</h2>
                <p class="text-muted small mb-0">Manage user accounts and view history</p>
            </div>
            <div class="d-flex gap-2">
                <button class="btn btn-dark rounded-1 fw-bold shadow-sm">
                    <i class="bi bi-envelope me-2"></i> Email All
                </button>
            </div>
        </div>

        <div class="row g-3 mb-4">
            <div class="col-md-4">
                <div class="card border-0 shadow-sm rounded-4 p-3 d-flex flex-row align-items-center justify-content-between">
                    <div>
                        <h6 class="text-secondary fw-bold mb-1">Total Users</h6>
                        <h4 class="fw-bold mb-0" id="totalUserCount">842</h4>
                    </div>
                    <div class="bg-primary bg-opacity-10 text-primary p-3 rounded-circle"><i class="bi bi-people-fill"></i></div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card border-0 shadow-sm rounded-4 p-3 d-flex flex-row align-items-center justify-content-between">
                    <div>
                        <h6 class="text-secondary fw-bold mb-1">Active Users</h6>
                        <h4 class="fw-bold mb-0" id="activeUserCount">820</h4>
                    </div>
                    <div class="bg-success bg-opacity-10 text-success p-3 rounded-circle"><i class="bi bi-person-check-fill"></i></div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card border-0 shadow-sm rounded-4 p-3 d-flex flex-row align-items-center justify-content-between">
                    <div>
                        <h6 class="text-secondary fw-bold mb-1">Blocked</h6>
                        <h4 class="fw-bold mb-0" id="blockedUserCount">22</h4>
                    </div>
                    <div class="bg-danger bg-opacity-10 text-danger p-3 rounded-circle"><i class="bi bi-person-x-fill"></i></div>
                </div>
            </div>
        </div>

        <div class="card border-0 shadow-sm rounded-4 mb-4">
            <div class="card-body p-3">
                <div class="row g-3">
                    <div class="col-md-5">
                        <div class="input-group">
                            <span class="input-group-text bg-white border-end-0"><i class="bi bi-search text-secondary"></i></span>
                            <input type="text" class="form-control border-start-0 ps-0" placeholder="Search by name, email or mobile...">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <select class="form-select text-secondary fw-bold" id="userStatusFilter">
                            <option value="0" selected>Status: All</option>
                            <option value="1">Active</option>
                            <option value="2">Blocked</option>
                        </select>
                    </div>
                    <div class="col-md-4 text-end">
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
                                <th scope="col" class="py-3 ps-4">User Profile</th>
                                <th scope="col" class="py-3">Contact Info</th>
                                <th scope="col" class="py-3">Stats</th>
                                <th scope="col" class="py-3">Joined Date</th>
                                <th scope="col" class="py-3 text-center">Status</th>
                                <th scope="col" class="py-3 text-end pe-4">Action</th>
                            </tr>
                        </thead>
                        <tbody id="customerTableBody">


                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </main>


    <div class="modal fade" id="userModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content rounded-4 border-0 shadow-lg">
                <div class="modal-header border-bottom-0 p-4 pb-0 bg-light rounded-top-4">
                    <h5 class="modal-title fw-bold">Customer Profile</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-0">
                    <div class="bg-light p-4 pt-2 text-center border-bottom">
                        <div class="avatar-circle mx-auto mb-3" style="width: 80px; height: 80px; font-size: 2rem; background-color: #000; color: #fff;">J</div>
                        <h4 class="fw-bold mb-0" id="userName">John Smith</h4>
                        <p class="text-muted" id="userEmail">john@gmail.com</p>
                        <div class="d-flex justify-content-center gap-3 mt-3">
                            <div class="border rounded-3 px-4 py-2 bg-white">
                                <h5 class="fw-bold mb-0 text-success" id="orderCount">12</h5>
                                <small class="text-secondary">Orders</small>
                            </div>
                            <div class="border rounded-3 px-4 py-2 bg-white">
                                <h5 class="fw-bold mb-0 text-dark" id="totalSpend">Rs. 125k</h5>
                                <small class="text-secondary">Spent</small>
                            </div>
                        </div>
                    </div>

                    <div class="p-4">
                        <div class="row g-4">
                            <div class="col-md-6 border-end">
                                <h6 class="fw-bold text-dark mb-3">Personal Information</h6>
                                <div class="mb-3">
                                    <label class="small text-secondary fw-bold">Full Name</label>
                                    <p class="mb-0 fw-bold">John Smith</p>
                                </div>
                                <div class="mb-3">
                                    <label class="small text-secondary fw-bold">Mobile</label>
                                    <p class="mb-0 fw-bold" id="mobile">077 123 4567</p>
                                </div>
                                <div class="mb-3">
                                    <label class="small text-secondary fw-bold">Shipping Address</label>

                                    <p id="shippingAddress" class="mb-0 text-muted small">
                                        <span id="line_one"></span> <span id="line_two"></span><br>
                                        <span id="city"></span><br>
                                        <span id="district"></span><br>
                                        <span id="province"></span><br>
                                        <span id="postalCode"></span>
                                    </p>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <h6 class="fw-bold text-dark mb-0">Recent Orders</h6>
                                    <a href="#" class="small text-danger text-decoration-none fw-bold">View All</a>
                                </div>

                                <div id="recenOtrders" class="list-group list-group-flush">
                                    <div class="list-group-item px-0 d-flex justify-content-between align-items-center border-bottom">
                                        <div>
                                            <p class="mb-0 fw-bold small">#ORD-9985</p>
                                            <small class="text-muted" style="font-size: 11px;">Jan 12, 2026</small>
                                        </div>
                                        <span class="badge bg-success bg-opacity-10 text-success rounded-pill">Delivered</span>
                                        <span class="fw-bold small">Rs. 45,000</span>
                                    </div>
                                    <div class="list-group-item px-0 d-flex justify-content-between align-items-center border-bottom">
                                        <div>
                                            <p class="mb-0 fw-bold small">#ORD-8854</p>
                                            <small class="text-muted" style="font-size: 11px;">Dec 24, 2025</small>
                                        </div>
                                        <span class="badge bg-primary bg-opacity-10 text-primary rounded-pill">Shipped</span>
                                        <span class="fw-bold small">Rs. 22,000</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer border-top-0 p-3 bg-light rounded-bottom-4">
                    <button type="button" class="btn btn-outline-dark fw-bold rounded-1" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger fw-bold rounded-1"><i class="bi bi-slash-circle me-2"></i>Block Account</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/timestore/public/assets/Script/Admin/customers.js"></script>