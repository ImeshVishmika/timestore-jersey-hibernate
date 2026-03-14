<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TimeStore - Admin Overview</title>
    <link rel="stylesheet" href="/timestore/public/assets/style/bootstrap.css">
    <link rel="stylesheet" href="/timestore/public/assets/style/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<body class="bg-light">


    <div class="container-fluid">
        <div class="row">

            <div class="col-md-2 d-none d-md-block bg-black min-vh-100 p-4">
                <div class="mb-5 px-2">
                    <h3 class="fw-bold text-white">TimeStore <span class="text-danger fs-6">ADMIN</span></h3>
                </div>
                <p class="text-uppercase text-secondary small fw-bold mb-2 px-2 ls-1">Main Menu</p>
                <nav class="nav flex-column gap-1" id="myTab" role="tablist">
                    <li class="nav-item  mb-2" role="presentation">
                        <button class="w-100 text-start sidebar-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home" type="button" role="tab" aria-controls="home" aria-selected="true"><i class="bi bi-grid-fill me-3"></i>Overview</button>
                    </li>
                    <li class="nav-item  mb-2" role="presentation">
                        <button class="w-100 text-start sidebar-link" id="product-tab" data-bs-toggle="tab" data-bs-target="#product" type="button" role="tab" aria-controls="product" aria-selected="false"><i class="bi bi-box-seam-fill me-3"></i>Products</button>
                    </li>
                    <li class="nav-item  mb-2" role="presentation">
                        <button class="w-100 text-start sidebar-link" id="orders-tab" data-bs-toggle="tab" data-bs-target="#orders" type="button" role="tab" aria-controls="orders" aria-selected="false"><i class="bi bi-receipt me-3"></i>Orders</button>
                    </li>
                    <li class="nav-item  mb-2" role="presentation">
                        <button class="w-100 text-start sidebar-link" id="customers-tab" data-bs-toggle="tab" data-bs-target="#customers" type="button" role="tab" aria-controls="customers" aria-selected="false"><i class="bi bi-people-fill me-3"></i>Customers</button>
                    </li>
                    <li class="nav-item  mb-2" role="presentation">
                        <button class="w-100 text-start sidebar-link" id="messages-tab" data-bs-toggle="tab" data-bs-target="#msg" type="button" role="tab" aria-controls="msg" aria-selected="false"><i class="bi bi-chat-dots-fill me-3"></i>Messages</button>
                    </li>
                    <li class="nav-item  mb-2" role="presentation">
                        <button class="w-100 text-start sidebar-link" id="settings-tab" data-bs-toggle="tab" data-bs-target="#settings" type="button" role="tab" aria-controls="settings" aria-selected="false"><i class="bi bi-gear-fill me-3"></i>Settings</button>
                    </li>
                    <hr>
                    <a href="adminSignout.php" class="btn btn-danger w-100 fw-bold rounded-2 mb-3 shadow-sm d-flex align-items-center justify-content-center">
                        <i class="bi bi-box-arrow-right me-2"></i> Sign Out
                    </a>
                </nav>
            </div>

            <div class="col-md-10 ms-sm-auto px-md-5 py-4">
                <div class="tab-content">
                    <?php include 'home-tab.php'; ?>

                    <?php include 'product-tab.php'; ?>

                    <?php include 'orders-tab.php'; ?>

                    <?php include 'customers-tab.php'; ?>

                    <?php include 'messages-tab.php'; ?>

                   <?php include 'settings-tab.php'; ?>

                </div>
                </main>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

</body>

</html>