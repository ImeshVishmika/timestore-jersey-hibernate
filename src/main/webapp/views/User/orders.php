<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NeuroShop | Global Transaction Ledger</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --primary-bg: #0f172a;
            --card-bg: #1e293b;
            --accent: #38bdf8;
            --text-main: #e2e8f0;
            --border-color: rgba(255, 255, 255, 0.1);
        }

        body {
            background-color: var(--primary-bg);
            color: var(--text-main);
            font-family: 'Inter', sans-serif;
        }

        .sidebar {
            min-height: 100vh;
            border-right: 1px solid var(--border-color);
            background: var(--primary-bg);
        }

        .nav-link {
            color: #94a3b8;
            margin-bottom: 5px;
            border-radius: 5px;
        }

        .nav-link:hover,
        .nav-link.active {
            background: rgba(56, 189, 248, 0.1);
            color: var(--accent);
        }

        /* The Advanced Data Grid */
        .data-grid-container {
            background-color: var(--card-bg);
            border: 1px solid var(--border-color);
            border-radius: 8px;
            overflow: hidden;
        }

        .table-dark {
            --bs-table-bg: transparent;
        }

        th {
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.75rem;
            color: #64748b;
            letter-spacing: 1px;
        }

        td {
            vertical-align: middle;
            border-bottom: 1px solid var(--border-color);
        }

        .user-avatar {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            background: #475569;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            font-size: 0.8rem;
            margin-right: 10px;
        }

        .search-input {
            background: #0f172a;
            border: 1px solid var(--border-color);
            color: white;
        }

        .search-input:focus {
            background: #0f172a;
            color: white;
            border-color: var(--accent);
            box-shadow: none;
        }
    </style>
</head>

<body>

    <div class="container-fluid">
        <div class="row">
            <nav class="col-md-2 d-none d-md-block sidebar p-3">
                <h4 class="mb-4 text-white"><i class="fas fa-bolt text-warning"></i> Nexus</h4>
                <ul class="nav flex-column">
                    <li class="nav-item"><a href="index.html" class="nav-link"><i class="fas fa-chart-line me-2"></i> Dashboard</a></li>
                    <li class="nav-item"><a href="orders.html" class="nav-link active"><i class="fas fa-receipt me-2"></i> Transactions</a></li>
                    <li class="nav-item"><a href="#" class="nav-link"><i class="fas fa-box me-2"></i> Inventory</a></li>
                    <li class="nav-item"><a href="#" class="nav-link"><i class="fas fa-users me-2"></i> Customers</a></li>
                </ul>
            </nav>

            <main class="col-md-10 ms-sm-auto px-md-4 py-4">

                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="h4">Global Transaction Ledger</h2>
                    <div class="d-flex gap-2">
                        <button class="btn btn-outline-secondary btn-sm"><i class="fas fa-download"></i> Export CSV</button>
                        <button class="btn btn-primary btn-sm"><i class="fas fa-plus"></i> Manual Order</button>
                    </div>
                </div>

                <div class="row mb-3 g-2">
                    <div class="col-md-4">
                        <div class="input-group">
                            <span class="input-group-text bg-dark border-secondary text-secondary"><i class="fas fa-search"></i></span>
                            <input type="text" class="form-control search-input" placeholder="Search Order ID, Customer, or Product...">
                        </div>
                    </div>
                    <div class="col-md-2">
                        <select id="orderdStatus" class="form-select search-input">
                            <option selected>Status: All</option>
                            <option value="completed">Completed</option>
                            <option value="pending">Pending</option>
                            <option value="fraud">Potential Fraud</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <input type="date" class="form-control search-input">
                    </div>
                </div>

                <div class="data-grid-container">
                    <div class="table-responsive">
                        <table class="table table-dark table-hover mb-0">
                            <thead>
                                <tr class="bg-dark">
                                    <th class="ps-4">Order ID</th>
                                    <th>Customer</th>
                                    <th>Product(s)</th>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="ps-4 text-accent font-monospace">#ORD-9921</td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <div class="user-avatar bg-primary">JD</div>
                                            <div>John Doe<br><small class="text-muted">john@example.com</small></div>
                                        </div>
                                    </td>
                                    <td>RTX 4090 Gaming OC <span class="badge bg-secondary">+1</span></td>
                                    <td>Oct 24, 2025</td>
                                    <td class="fw-bold">$1,599.00</td>
                                    <td><span class="badge bg-success bg-opacity-10 text-success border border-success">Paid</span></td>
                                    <td><button data-bs-toggle="modal" data-bs-target="#orderModal" class="btn btn-sm btn-link text-muted"><i class="fas fa-ellipsis-v"></i></button></td>
                                </tr>

                                <tr>
                                    <td class="ps-4 text-accent font-monospace">#ORD-9920</td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <div class="user-avatar bg-warning text-dark">A</div>
                                            <div>Anonymous<br><small class="text-muted">Guest Checkout</small></div>
                                        </div>
                                    </td>
                                    <td>Mechanical Keycaps (Blue)</td>
                                    <td>Oct 24, 2025</td>
                                    <td class="fw-bold">$45.00</td>
                                    <td><span class="badge bg-warning bg-opacity-10 text-warning border border-warning">Pending</span></td>
                                    <td><button data-bs-toggle="modal" data-bs-target="#orderModal" class="btn btn-sm btn-link text-muted"><i class="fas fa-ellipsis-v"></i></button></td>
                                </tr>

                                <tr>
                                    <td class="ps-4 text-accent font-monospace">#ORD-9919</td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <div class="user-avatar bg-danger">X</div>
                                            <div>Xavier Bot<br><small class="text-muted">xav@bot.net</small></div>
                                        </div>
                                    </td>
                                    <td>Gift Card $500 (x10)</td>
                                    <td>Oct 23, 2025</td>
                                    <td class="fw-bold text-danger">$5,000.00</td>
                                    <td><span class="badge bg-danger bg-opacity-10 text-danger border border-danger">Flagged</span></td>
                                    <td><button data-bs-toggle="modal" data-bs-target="#orderModal" class="btn btn-sm btn-link text-muted"><i class="fas fa-ellipsis-v"></i></button></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="d-flex justify-content-between align-items-center p-3 border-top border-secondary">
                        <small class="text-muted">Showing 1-10 of 2,450 orders</small>
                        <nav>
                            <ul class="pagination pagination-sm mb-0">
                                <li class="page-item disabled"><a class="page-link bg-dark border-secondary text-muted" href="#">Prev</a></li>
                                <li class="page-item active"><a class="page-link bg-primary border-primary" href="#">1</a></li>
                                <li class="page-item"><a class="page-link bg-dark border-secondary text-muted" href="#">2</a></li>
                                <li class="page-item"><a class="page-link bg-dark border-secondary text-muted" href="#">3</a></li>
                                <li class="page-item"><a class="page-link bg-dark border-secondary text-muted" href="#">Next</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>

            </main>
        </div>
    </div>

    <div class="modal fade" id="orderModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content" style="background-color: #1e293b; color: #e2e8f0; border: 1px solid rgba(255,255,255,0.1);">

                <div class="modal-header border-bottom border-secondary">
                    <h5 class="modal-title font-monospace"><i class="fas fa-receipt me-2 text-accent"></i>Order #ORD-9921</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="modal-body">
                    <div class="row g-4">

                        <div class="col-md-5 border-end border-secondary">
                            <h6 class="text-uppercase text-muted mb-3" style="font-size: 0.75rem;">Customer Details</h6>
                            <div class="d-flex align-items-center mb-3">
                                <div class="bg-primary rounded-circle d-flex align-items-center justify-content-center me-3" style="width: 40px; height: 40px;">JD</div>
                                <div>
                                    <div class="fw-bold">John Doe</div>
                                    <div class="small text-muted">john.doe@example.com</div>
                                </div>
                            </div>

                            <h6 class="text-uppercase text-muted mb-2 mt-4" style="font-size: 0.75rem;">Shipping Address</h6>
                            <p class="small text-light mb-0">
                                123 Cyberpunk Avenue,<br>
                                Sector 7, Neo-Colombo,<br>
                                Sri Lanka, 10100
                            </p>

                            <h6 class="text-uppercase text-muted mb-2 mt-4" style="font-size: 0.75rem;">Payment Method</h6>
                            <div class="d-flex align-items-center gap-2">
                                <i class="fab fa-cc-visa fa-lg text-white"></i>
                                <span class="small">**** 4242</span>
                            </div>
                        </div>

                        <div class="col-md-7">
                            <h6 class="text-uppercase text-muted mb-3" style="font-size: 0.75rem;">Order Manifest</h6>

                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <div class="d-flex align-items-center gap-2">
                                    <div class="bg-dark rounded p-1" style="width: 40px; height: 40px; border: 1px solid rgba(255,255,255,0.1);">
                                        <img src="https://via.placeholder.com/40" class="img-fluid opacity-75">
                                    </div>
                                    <div>
                                        <div class="small fw-bold">RTX 4090 Gaming OC</div>
                                        <div class="text-muted" style="font-size: 0.7rem;">Qt: 1</div>
                                    </div>
                                </div>
                                <div class="fw-bold">$1,599.00</div>
                            </div>

                            <div class="border-top border-secondary pt-3 mt-3">
                                <div class="d-flex justify-content-between small mb-1">
                                    <span class="text-muted">Subtotal</span>
                                    <span>$1,599.00</span>
                                </div>
                                <div class="d-flex justify-content-between small mb-1">
                                    <span class="text-muted">Shipping (Express)</span>
                                    <span>$25.00</span>
                                </div>
                                <div class="d-flex justify-content-between fw-bold fs-5 mt-2 text-accent">
                                    <span>Total</span>
                                    <span>$1,624.00</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="mt-4 p-3 rounded bg-dark border border-secondary">
                        <h6 class="text-uppercase text-muted mb-3" style="font-size: 0.7rem;">Processing Timeline</h6>
                        <div class="d-flex justify-content-between text-center position-relative">
                            <div class="position-absolute top-50 start-0 w-100 translate-middle-y bg-secondary" style="height: 2px; z-index: 0;"></div>

                            <div class="position-relative bg-dark px-2" style="z-index: 1;">
                                <i class="fas fa-check-circle text-success"></i>
                                <div class="small mt-1">Placed</div>
                            </div>
                            <div class="position-relative bg-dark px-2" style="z-index: 1;">
                                <i class="fas fa-check-circle text-success"></i>
                                <div class="small mt-1">Paid</div>
                            </div>
                            <div class="position-relative bg-dark px-2" style="z-index: 1;">
                                <i class="fas fa-box text-primary"></i>
                                <div class="small mt-1 text-primary">Packing</div>
                            </div>
                            <div class="position-relative bg-dark px-2" style="z-index: 1;">
                                <i class="far fa-circle text-muted"></i>
                                <div class="small mt-1 text-muted">Shipped</div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer border-top border-secondary">
                    <button type="button" class="btn btn-outline-danger btn-sm"><i class="fas fa-undo me-2"></i>Refund</button>
                    <button type="button" class="btn btn-outline-light btn-sm"><i class="fas fa-print me-2"></i>Print Invoice</button>
                    <button type="button" class="btn btn-primary btn-sm">Update Status</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>