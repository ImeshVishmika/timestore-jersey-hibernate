<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TimeStore | Home</title>
    <link rel="stylesheet" href="/timestore/public/assets/style/bootstrap.css">
    <link rel="stylesheet" href="/timestore/public/assets/style/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    
<style>
    .transition-hover {
        transition: transform 0.3s ease, box-shadow 0.3s ease;
    }
    .transition-hover:hover {
        transform: translateY(-5px);
        box-shadow: 0 10px 20px rgba(0,0,0,0.1) !important;
    }
    .ls-1 { letter-spacing: 1px; }
</style>
</head>

<body class="bg-light">

    
    <?php include "header.php"; ?>

    <div class="container my-4">
        <div class="bg-black text-white rounded-4 p-5 overflow-hidden position-relative">
            <div class="row align-items-center">
                <div class="col-md-6 py-5 px-md-5 z-1">
                    <h1 class="display-5 fw-bold mb-4">Elegance Defined.<br>The New Collection</h1>
                    <p class="lead text-white-50 mb-4">Discover precision engineering meets timeless design</p>
                    <a href="/timestore/search" class="btn btn-light fw-bold px-4 py-2 rounded-2">Shop Now</a>
                </div>
                <div class="col-md-6 text-center">
                    <div id="PoseterCarousel" class="carousel slide" data-bs-ride="carousel" data-bs-interval="3000">
                        <div class="carousel-inner rounded-3">
                            <div class="carousel-item active">
                                <img src="/timestore/poster/1" class="d-block w-100" alt="Watch 1">
                            </div>
                            <div class="carousel-item">
                                <img src="/timestore/poster/2" class="d-block w-100" alt="Watch 2">
                            </div>
                            <div class="carousel-item">
                                <img src="/timestore/poster/3" class="d-block w-100" alt="Watch 3">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container my-5">
        <h4 class="fw-bold mb-4">Shop by Category</h4>
        <div class="row g-3">
            <div class="col-md-4">
                <div class="card border-0 rounded-4 overflow-hidden text-white h-100 shadow-sm category-card position-relative" style="min-height: 250px;">
                    <img src="https://images.unsplash.com/photo-1617043786394-f977fa12eddf?q=80&w=600&auto=format&fit=crop" class="card-img h-100 object-fit-cover w-100" alt="Mens">
                    <div class="card-img-overlay overlay-gradient d-flex flex-column justify-content-end p-4">
                        <h4 class="fw-bold">Men's</h4>
                        <a href="#" class="text-white text-decoration-none fw-bold small stretched-link">Explore <i class="bi bi-arrow-right"></i></a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card border-0 rounded-4 overflow-hidden text-white h-100 shadow-sm category-card position-relative" style="min-height: 250px;">
                    <img src="https://images.unsplash.com/photo-1590736969955-71cc94801759?q=80&w=600&auto=format&fit=crop" class="card-img h-100 object-fit-cover w-100" alt="Womens">
                    <div class="card-img-overlay overlay-gradient d-flex flex-column justify-content-end p-4">
                        <h4 class="fw-bold">Women's</h4>
                        <a href="#" class="text-white text-decoration-none fw-bold small stretched-link">Explore <i class="bi bi-arrow-right"></i></a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card border-0 rounded-4 overflow-hidden text-white h-100 shadow-sm category-card position-relative" style="min-height: 250px;">
                    <img src="https://images.unsplash.com/photo-1579586337278-3befd40fd17a?q=80&w=600&auto=format&fit=crop" class="card-img h-100 object-fit-cover w-100" alt="Smart">
                    <div class="card-img-overlay overlay-gradient d-flex flex-column justify-content-end p-4">
                        <h4 class="fw-bold">Smart Tech</h4>
                        <a href="#" class="text-white text-decoration-none fw-bold small stretched-link">Explore <i class="bi bi-arrow-right"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

      <div class="container my-5">
        <h3 class="fw-bold mb-4">New Arrivals</h3>
        <div id="popularItemesBody" class="row g-4"></div>
    </div>

    <div class="container my-5">
        <div class="bg-white rounded-4 p-5 shadow-sm border">
            <div class="row g-4 text-center">
                <div class="col-6 col-md-3">
                    <div class="mb-3 text-danger"><i class="bi bi-box-seam fs-1"></i></div>
                    <h6 class="fw-bold mb-1">Free Shipping</h6>
                    <p class="text-muted small mb-0">On orders over Rs. 5000</p>
                </div>
                <div class="col-6 col-md-3">
                    <div class="mb-3 text-danger"><i class="bi bi-shield-check fs-1"></i></div>
                    <h6 class="fw-bold mb-1">Secure Payment</h6>
                    <p class="text-muted small mb-0">100% protected payments</p>
                </div>
                <div class="col-6 col-md-3">
                    <div class="mb-3 text-danger"><i class="bi bi-arrow-counterclockwise fs-1"></i></div>
                    <h6 class="fw-bold mb-1">Easy Returns</h6>
                    <p class="text-muted small mb-0">30-day money back policy</p>
                </div>
                <div class="col-6 col-md-3">
                    <div class="mb-3 text-danger"><i class="bi bi-award fs-1"></i></div>
                    <h6 class="fw-bold mb-1">Authentic Products</h6>
                    <p class="text-muted small mb-0">Authorized dealer warranty</p>
                </div>
            </div>
        </div>
    </div>

    <div class="container my-5">
        <h3 class="fw-bold mb-4">New Arrivals</h3>
        <div id="newItemesBody" class="row g-4"></div>
    </div>

    <div class="container-fluid p-0 my-5">
        <div class="position-relative bg-dark text-center text-white" 
             style="background: url('https://images.unsplash.com/photo-1495856458515-0637185db551?q=80&w=2000&auto=format&fit=crop') no-repeat center center; background-size: cover; height: 400px;">
            
            <div class="position-absolute top-0 start-0 w-100 h-100 d-flex flex-column justify-content-center align-items-center" 
                 style="background-color: rgba(0,0,0,0.5);">
                <span class="badge bg-danger rounded-pill mb-3 px-3 py-2">LIMITED TIME OFFER</span>
                <h2 class="display-4 fw-bold mb-3">Summer Clearance</h2>
                <p class="lead mb-4 text-white-50">Up to 40% off on selected G-Shock models.</p>
                <a href="#" class="btn btn-light fw-bold px-5 py-3 rounded-pill">View Offers</a>
            </div>
        </div>
    </div>

    <div class="container my-5">
        <div class="bg-dark text-white rounded-4 p-5 text-center position-relative overflow-hidden">
            <div class="position-absolute top-0 start-0 translate-middle bg-secondary opacity-25 rounded-circle" style="width: 300px; height: 300px;"></div>
            
            <div class="position-relative z-1">
                <h3 class="fw-bold mb-2">Join the Inner Circle</h3>
                <p class="text-white-50 mb-4">Subscribe to receive updates, access to exclusive deals, and more.</p>
                <div class="row justify-content-center">
                    <div class="col-md-6">
                        <div class="input-group">
                            <input type="email" class="form-control border-0 p-3" placeholder="Enter your email address">
                            <button class="btn btn-danger fw-bold px-4" type="button">Subscribe</button>
                        </div>
                        <small class="text-white-50 mt-2 d-block">No spam, unsubscribe anytime.</small>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <?php include "footer.php"; ?>

    <script src="/timestore/public/assets/Script/User/index.js"></script>
    
</body>

</html>