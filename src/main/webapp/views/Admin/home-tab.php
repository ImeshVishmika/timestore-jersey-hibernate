<div class="tab-pane active" id="home" role="tabpanel" aria-labelledby="home-tab" tabindex="0">
    <div class="d-flex justify-content-between align-items-end mb-5 border-bottom pb-3">
        <div>
            <h6 class="text-danger fw-bold text-uppercase mb-1">Welcome back, Imesh</h6>
            <h2 class="fw-bold text-dark display-6">Overview Dashboard</h2>
        </div>
    </div>

    <div class="row g-4 mb-5">
        <div class="col-md-3">
            <div class="card border-0 shadow rounded-4 h-100">
                <div class="card-body p-4">
                    <div class="d-flex justify-content-between mb-4">
                        <div class="bg-light p-3 rounded-4 text-danger">
                            <i class="bi bi-wallet-fill fs-4"></i>
                        </div>
                        <span id="revenueGrowthBadge" class="badge bg-success bg-opacity-10 text-success h-50 align-self-center">
                            <i class="bi bi-arrow-up-short"></i> <span id="revenueGrowthPercent">12</span>%
                        </span>
                    </div>
                    <h3 id="totalRevenueValue" class="fw-bold mb-1">Rs. 0.00</h3>
                    <div class="text-muted small fw-bold">Total Revenue</div>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card border-0 shadow rounded-4 h-100">
                <div class="card-body p-4">
                    <div class="d-flex justify-content-between mb-4">
                        <div class="bg-light p-3 rounded-4 text-dark">
                            <i class="bi bi-bag-fill fs-4"></i>
                        </div>
                    </div>
                    <h3 id="totalOrdersValue" class="fw-bold mb-1">0</h3>
                    <div class="text-muted small fw-bold">Total Orders</div>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card border-0 shadow rounded-4 h-100 bg-dark text-white overflow-hidden position-relative">
                <div class="position-absolute top-0 end-0 bg-secondary opacity-25 rounded-circle" style="width: 150px; height: 150px; margin-top: -50px; margin-right: -50px;"></div>

                <div class="card-body p-4 d-flex flex-column justify-content-center position-relative z-1">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h5 class="fw-bold mb-2"><i class="bi bi-cpu me-2"></i>Inventory Alert</h5>
                            <p class="text-secondary mb-0 small">low stock for <br> <span class="text-white fw-bold">G-Shock MRG-B2000</span> </p>
                        </div>
                        <button class="btn btn-light text-dark fw-bold px-4 py-2 rounded-1">Auto-Restock</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="card border-0 shadow rounded-4 mb-5">
        <div class="card-body p-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h5 class="fw-bold m-0">Revenue Flow</h5>
                    <p class="text-muted small mb-0">Sales performance over time</p>
                </div>
                <select id="revenuePeriod" class="form-select form-select-sm w-auto border-secondary fw-bold text-secondary">
                    <option value=7 selected>This Week</option>
                    <option value=30 >This Month</option>
                </select>
            </div>
            <div style="height: 350px;">
                <canvas id="revenueChart"></canvas>
            </div>
        </div>
    </div>
</div>

<script src="/timestore/public/assets/Script/bootstrap.bundle.js"></script>
<script src="/timestore/public/assets/Script/Admin/dashboard.js"></script>