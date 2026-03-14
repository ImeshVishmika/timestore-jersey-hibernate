<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/timestore/public/assets/style/bootstrap.css">
    <link rel="stylesheet" href="/timestore/public/assets/style/style.css">
    <title>Profile</title>
</head>

<body class="bg-light">

    <div class="fixed-top">
        <?php include "header.php";
        ?>
    </div>

    <?php

    include "connection.php";
    $email = $_SESSION["u"]["email"];

    ?>

    <div class="container align-content-center  pt-3 pb-5 min-vh-100">
        <div class="row g-0 rounded-3 overflow-hidden shadow-lg bg-white">

            <div class="col-lg-3 col-md-4 p-4 border-end">
                <div class="text-center mb-4">
                    <div class="d-inline-block justify-content-center d-block  border border-3 border-dark rounded-circle shadow p-1 mb-3" style="width: 120px; height: 120px;">
                        <img src="/timestore/app/media/icons/profile.png" data-default-src="/timestore/app/media/icons/profile.png"
                            class="img-fluid  rounded-circle w-100 h-100 object-fit-cover"
                            id="profileImage"
                            alt="Profile Picture">
                    </div>
                    <button class="btn btn-sm btn-outline-dark mb-2" onclick="document.getElementById('profileImageUpload').click()">
                        Change Photo <i class="bi bi-camera"></i>
                    </button>
                    <input type="file" id="profileImageUpload" class="d-none" accept="image/*" onchange="previewProfileImage(event)">
                    <h5 class="fw-bold text-dark mt-2 mb-1" id="profileName"></h5>
                    <p class="text-muted small" id="profileEmail"></p>
                </div>

                <hr>
                

                <ul class="nav nav-pills flex-column mb-auto" id="profileSidebarTabs" role="tablist">
                    <li class="nav-item mb-2" role="presentation">
                        <button class="nav-link w-100 text-start active" id="Overview-tab" data-bs-toggle="pill" data-bs-target="#overview" type="button" role="tab" aria-controls="overview" aria-selected="true">
                            <i class="bi bi-card-list"></i> Overview
                        </button>
                    </li>
                    <li class="nav-item mb-2" role="presentation">
                        <button class="nav-link w-100 text-start " id="account-details-tab" data-bs-toggle="pill" data-bs-target="#account-details" type="button" role="tab" aria-controls="account-details" aria-selected="false">
                            <i class="bi bi-person-fill me-2"></i> Account Details
                        </button>
                    </li>
                    <li class="nav-item mb-2" role="presentation">
                        <button class="nav-link w-100 text-start" id="shipping-address-tab" data-bs-toggle="pill" data-bs-target="#shipping-address" type="button" role="tab" aria-controls="shipping-address" aria-selected="false">
                            <i class="bi bi-geo-alt-fill me-2"></i> Shipping Address
                        </button>
                    </li>
                    <li class="nav-item mb-2" role="presentation">
                        <button class="nav-link w-100 text-start" id="order-history-tab" data-bs-toggle="pill" data-bs-target="#order-history" type="button" role="tab" aria-controls="order-history" aria-selected="false">
                            <i class="bi bi-box-seam-fill me-2"></i> Order History
                        </button>
                    </li>
                    <li class="nav-item mb-2" role="presentation">
                        <button class="nav-link w-100 text-start" id="wishlist-tab" data-bs-toggle="pill" data-bs-target="#wishlist" type="button" role="tab" aria-controls="wishlist" aria-selected="false">
                            <i class="bi bi-heart-fill me-2"></i> Wishlist
                            <span class="badge bg-danger float-end">3</span> </button>
                    </li>
                    <li>
                        <hr>
                    </li>
                    <li class="nav-item">
                        <button class="btn btn-outline-danger w-100 text-start" onclick="logoutprofile()">
                            <i class="bi bi-box-arrow-right me-2"></i> Logout
                        </button>
                    </li>
                </ul>
            </div>

            <div class="col-lg-9 col-md-8 p-4">

                <div class="tab-content" id="profileTabContent">

                    <div class="tab-pane row row-cols-2 g-4 flex-row fade show  " id="overview" role="tabpanel" aria-labelledby="Overview-tab">
                        <h3 class="fw-bold mb-4">Overview</h3>

                        <!-- Overview profile card -->
                        <div class="col">
                            <div class="card  shadow border-0 m-1">
                                <div class="card-body">
                                    <div class=" d-flex justify-content-between pt-2 pb-3">
                                        <h6 class="fw-bold  m-0">Profile</h6>
                                        <!-- Load Profile tab -->
                                        <button type="button" class="btn" id="account-details-tab" data-bs-toggle="pill" data-bs-target="#account-details" type="button" role="tab" aria-controls="account-details" aria-selected="false">
                                            <h6 class="fw-bold text-primary m-0"><i class="bi bi-pencil me-1"></i>Edit</h6>
                                        </button>

                                    </div>
                                    <figure>
                                        <blockquote class="blockquote">
                                            <h6 class="card-text col-12 col-lg-5 fw-bold " id="overviewName"></h6>
                                            <h6 class="text-secondary col-12 col-lg-6" id="overviewMobile"></h6>
                                            <h6 class="text-secondary col-12 col-lg-6" id="overviewEmail"></h6>
                                        </blockquote>
                                    </figure>
                                </div>
                            </div>
                        </div>

                        <!-- Overview address card -->
                        <div class="col">
                            <div class="card  shadow border-0 m-1">
                                <div class="card-body">
                                    <div class=" d-flex justify-content-between pt-2 pb-3">
                                        <h6 class="fw-bold  m-0">Address</h6>
                                        <!-- Button trigger modal -->
                                        <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                                            <h6 class="fw-bold text-primary m-0"><i class="bi bi-pencil me-1"></i>Edit</h6>
                                        </button>

                                    </div>
                                    <figure>
                                        <blockquote class="blockquote">
                                            <h6 class="text-secondary col-12 col-lg-6" id="overviewAddress"></h6>
                                        </blockquote>
                                    </figure>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="tab-pane fade show " id="account-details" role="tabpanel" aria-labelledby="account-details-tab">
                        <h3 class="fw-bold mb-4">Personal Information</h3>
                        <form id="updateDetailsForm">
                            <div class="row g-3">

                                <div class="col-md-6">
                                    <label for="fname" class="form-label text-secondary">First Name</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-person"></i></span>
                                        <input type="text" class="form-control" id="fname" value="" placeholder="First Name" required>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="lname" class="form-label text-secondary">Last Name</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-person-vcard"></i></span>
                                        <input type="text" class="form-control" id="lname" value="" placeholder="Last Name" required>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="email" class="form-label text-secondary">Email Address</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                        <input type="email" class="form-control" id="email" value="" placeholder="Email Address" disabled>
                                        <button class="btn btn-outline-secondary" type="button" id="button-addon2">Confirm</button>
                                    </div>
                                    <small class="text-muted">Email cannot be changed directly.</small>
                                </div>

                                <div class="col-md-6">
                                    <label for="mobile" class="form-label text-secondary">Mobile Number</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-phone"></i></span>
                                        <input type="tel" class="form-control" id="mobile" value="" placeholder="Mobile Number">
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="password" class="form-label text-secondary">Password</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-lock"></i></span>
                                        <input type="password" class="form-control" id="password" placeholder="Password" disabled>
                                    </div>
                                    <small class="text-muted">Contact support to change password.</small>
                                </div>

                                <div class="col-12 mt-4">
                                    <button type="submit" class="btn btn-dark btn-lg">Save Changes</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="tab-pane fade" id="shipping-address" role="tabpanel" aria-labelledby="shipping-address-tab">
                        <h3 class="fw-bold mb-4">Shipping Address</h3>
                        <form id="updateAddressForm">
                            <div class="row g-3">

                                <div class="col-md-12">
                                    <label for="line1" class="form-label text-secondary">Address Line 1</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-house"></i></span>
                                        <input type="text" class="form-control" id="line1" value="" placeholder="Address Line 1" required>
                                    </div>
                                </div>

                                <div class="col-md-12">
                                    <label for="line2" class="form-label text-secondary">Address Line 2 (Optional)</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-signpost-2"></i></span>
                                        <input type="text" class="form-control" id="line2" value="" placeholder="Address Line 2 (Optional)">
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="city" class="form-label text-secondary">City</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-building"></i></span>
                                        <input type="text" class="form-control" id="city" value="" placeholder="City" required>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="district" class="form-label text-secondary">District</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-pin-map"></i></i></span>
                                        <input type="text" class="form-control" id="district" value="" placeholder="District" required>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="province" class="form-label text-secondary">Province</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-map"></i></span>
                                        <input type="text" class="form-control" id="province" value="" placeholder="Province" required>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="postal_code" class="form-label text-secondary">Postal Code</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-mailbox"></i></span>
                                        <input type="text" class="form-control" id="postal_code" value="" placeholder="Postal Code" required>
                                    </div>
                                </div>

                                <div class="col-12 mt-4">
                                    <button type="submit" class="btn btn-dark btn-lg">Update Address</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="tab-pane fade" id="order-history" role="tabpanel" aria-labelledby="order-history-tab">
                        <h3 class="fw-bold mb-4">Your Recent Orders</h3>
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th>Order ID</th>
                                        <th>Date</th>
                                        <th>Total (Rs.)</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody id="ordersTable">
                                    <!-- Orders loaded via API -->
                                </tbody>
                            </table>
                        </div>
                    </div>
                                        <td>2,100.00</td>
                                        <td><span class="badge bg-secondary">Cancelled</span></td>
                                        <td><button class="btn btn-sm btn-outline-dark">View Details</button></td>
                                    </tr> -->
                                </tbody>
                            </table>
                        </div>
                        <div class="alert alert-info mt-4">
                            <i class="bi bi-info-circle-fill me-2"></i> Showing your last 3 orders.
                        </div>
                    </div>

                    <div class="tab-pane fade" id="wishlist" role="tabpanel" aria-labelledby="wishlist-tab">
                        <h3 class="fw-bold mb-4">Your Wishlist (<span id="wishlistCount">0</span> items)</h3>
                        <div id="wishlistContainer" class="row g-4">
                            <!-- Wishlist items loaded via API -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <?php

            if (isset($_SESSION["u"])) {

            ?>
                <div class="modal-content">
                    <div class="row justify-content-center">
                        <div class="modal-body card col-10 shadow-lg border-0 rounded-4">
                            <div class="card-body p-4 p-md-5 bg-white rounded-4">
                                <h1 class="fw-bold mb-1">Order # <span id="ordereDetailsId"></span> </h1>
                                <p class="text-muted mb-4"> Placed on: <span id="orderedDate">2023-11-15</span> | Total: Rs.<span id="total">15000</span>.00</p>
                                <div class="row g-4">

                                    <div class="col-lg-8 align-content-between">

                                        <div class="card shadow-sm mb-4">
                                            <div class="card-body">
                                                <div class="border-start border-3 ps-3">

                                                    <div class="mb-3">
                                                        <span class="text-success me-2"><i class="bi bi-check-circle-fill"></i></span>
                                                        <h6 class="fw-bold mb-0 d-inline-block">Order Placed</h6>
                                                        <p class="text-muted small mb-0 ms-4">15 Nov, 09:30 AM</p>
                                                    </div>

                                                    <div class="mb-3">
                                                        <span class="text-success me-2"><i class="bi bi-check-circle-fill"></i></span>
                                                        <h6 class="fw-bold mb-0 d-inline-block">Processing & Confirmed</h6>
                                                        <p class="text-muted small mb-0 ms-4">15 Nov, 11:00 AM</p>
                                                    </div>

                                                    <div class="mb-3 p-2 bg-light rounded border border-danger">
                                                        <span class="text-danger me-2"><i class="bi bi-truck-flatbed"></i></span>
                                                        <h6 class="fw-bold mb-0 d-inline-block text-danger">Shipped</h6>
                                                        <p class="text-muted small mb-0 ms-4">16 Nov, 04:15 PM</p>
                                                    </div>

                                                    <div class="mb-3">
                                                        <span class="text-secondary me-2"><i class="bi bi-circle"></i></span>
                                                        <h6 class="fw-bold mb-0 d-inline-block">Out for Delivery</h6>
                                                    </div>

                                                    <div class="mb-0">
                                                        <span class="text-secondary me-2"><i class="bi bi-circle"></i></span>
                                                        <h6 class="fw-bold mb-0 d-inline-block">Delivered</h6>
                                                    </div>

                                                </div>
                                                <a href="#" class="btn btn-sm btn-outline-dark mt-3"><i class="bi bi-box-arrow-up-right me-2"></i> View Tracking Portal</a>
                                            </div>
                                        </div>

                                        <div class="card shadow-sm">
                                            <div class="card-header fw-bold bg-white">Items in Order </div>
                                            <ul id="orderList" class="list-group list-group-flush">




                                                <!-- <li class="list-group-item">
                                                    <div class="row align-items-center">
                                                        <div class="col-auto">
                                                            <img src="https://via.placeholder.com/60x60/F8F9FA/343A40?text=W1" class="rounded" alt="Watch 1">
                                                        </div>
                                                        <div class="col">
                                                            <h6 class="fw-bold mb-1">G-Shock MRG-B2000B-1A4</h6>
                                                            <p class="text-muted small mb-0">Quantity: 1 | Model ID: MRGB2000</p>
                                                        </div>
                                                        <div class="col-auto text-end">
                                                            <p class="fw-bold mb-0">Rs. 10,000.00</p>
                                                        </div>
                                                    </div>
                                                </li>

                                                <li class="list-group-item">
                                                    <div class="row align-items-center">
                                                        <div class="col-auto">
                                                            <img src="https://via.placeholder.com/60x60/F8F9FA/343A40?text=W2" class="rounded" alt="Watch 2">
                                                        </div>
                                                        <div class="col">
                                                            <h6 class="fw-bold mb-1">Casio Vintage A168WA-1YES</h6>
                                                            <p class="text-muted small mb-0">Quantity: 2 | Model ID: A168</p>
                                                        </div>
                                                        <div class="col-auto text-end">
                                                            <p class="fw-bold mb-0">Rs. 2,000.00</p>
                                                        </div>
                                                    </div>
                                                </li> -->

                                            </ul>
                                            <div class="card-footer bg-white text-end">
                                                <button class="btn btn-sm btn-outline-dark">Write a Review</button>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="col-lg-4 align-content-between">

                                        <div class="card shadow-sm mb-4">
                                            <div class="card-header fw-bold bg-white"><i class="bi bi-geo-alt-fill me-2"></i> Shipping Details</div>
                                            <div class="card-body">
                                                <h6 class="fw-bold mb-1" id="shippingName"></h6>
                                                <blockquote class="blockquote">
                                                    <h6 class="text-secondary col-12 col-lg-6" id="shippingAddress"></h6>
                                                </blockquote>
                                                <p class="mb-0 text-muted" id="shippingAddressLine"></p></br>
                                                <p class="mb-0 text-muted" id="shippingMobile"></p>

                                                <hr>
                                                <p class="fw-bold mb-1">Tracking ID:</p>
                                                <p class="text-primary fw-bold mb-0">TSL-774589932</p>
                                                <p class="small text-success">Shipped via Standard Courier</p>
                                            </div>
                                        </div>

                                        <div class="card shadow-sm">
                                            <div class="card-header fw-bold bg-white"><i class="bi bi-credit-card-2-front me-2"></i> Payment Summary</div>
                                            <div class="card-body">
                                                <ul class="list-group list-group-flush mb-3">
                                                    <li class="list-group-item d-flex justify-content-between px-0 py-1 bg-transparent">
                                                        <span>Item Subtotal (3 items)</span>
                                                        <span>Rs. 12,000.00</span>
                                                    </li>
                                                    <li class="list-group-item d-flex justify-content-between px-0 py-1 bg-transparent">
                                                        <span>Shipping Fee</span>
                                                        <span class="text-danger">Rs. 500.00</span>
                                                    </li>
                                                    <li class="list-group-item d-flex justify-content-between px-0 py-1 bg-transparent">
                                                        <span>Tax (VAT/GST)</span>
                                                        <span>Rs. 0.00</span>
                                                    </li>
                                                    <li class="list-group-item d-flex justify-content-between px-0 py-2 bg-transparent border-top">
                                                        <span class="fs-5 fw-bold">Order Total</span>
                                                        <span class="fs-5 fw-bold text-dark">Rs. 12,500.00</span>
                                                    </li>
                                                </ul>
                                                <p class="small mb-0">Paid by: Visa ending in 4242</p>
                                            </div>
                                            <div class="card-footer bg-light text-center">
                                                <button class="btn btn-sm btn-outline-dark me-2">Download Invoice</button>
                                                <button class="btn btn-sm btn-outline-secondary">Return Item</button>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            <?php
            }
            ?>

        </div>
    </div>

    <?php include "footer.php" ?>
    <script src="/timestore/public/assets/Script/User/profile.js"></script>
    <script src="script/script.js"></script>

</body>

</html>