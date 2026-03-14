<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TimeStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <?php include "header.php" ?>

    <div id="loader" class="d-none justify-content-center align-items-center w-100 py-5" style="height: 80vh;">
        <div class="text-center">
            <div class="spinner-border text-dark mb-3" role="status" style="width: 3rem; height: 3rem;">
                <span class="visually-hidden">Loading...</span>
            </div>
            <p class="text-muted fw-bold">Loading Collection...</p>
        </div>
    </div>

    <div id="shop-view" class="">
        <div class="bg-light py-4 mb-4">
            <div class="container">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb mb-2">
                        <li class="breadcrumb-item"><a href="#" onclick="showHome()" class="text-decoration-none text-muted">Home</a></li>
                        <li class="breadcrumb-item active text-dark fw-bold" aria-current="page">Shop All</li>
                    </ol>
                </nav>
                <h2 class="fw-bold">All Timepieces</h2>
            </div>
        </div>

        <div class="container mb-5">
            <div class="row">
                
                <div class="col-lg-3 mb-4">
                    <div class="border rounded-3 p-4 sticky-top" style="top: 90px; z-index: 1;">
                        <div class="mb-4">
                            <h6 class="fw-bold mb-3">Categories</h6>
                            <div class="form-check mb-2">
                                <input class="form-check-input" type="checkbox" id="cat1" checked>
                                <label class="form-check-label" for="cat1">Men's Watches</label>
                            </div>
                            <div class="form-check mb-2">
                                <input class="form-check-input" type="checkbox" id="cat2">
                                <label class="form-check-label" for="cat2">Women's Watches</label>
                            </div>
                            <div class="form-check mb-2">
                                <input class="form-check-input" type="checkbox" id="cat3">
                                <label class="form-check-label" for="cat3">Smart Watches</label>
                            </div>
                        </div>

                        <div class="mb-4">
                            <h6 class="fw-bold mb-3">Brands</h6>
                            
                            <div id="brands">

                            </div>
                               
                        </div>
                        
                        <div>
                            <h6 class="fw-bold mb-3">Price Range</h6>
                            <input type="range" class="form-range" id="priceRange">
                            <div class="d-flex justify-content-between text-muted small">
                                <span>$0</span>
                                <span>$5,000+</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-9">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <span class="text-muted">Showing 1-9 of 24 results</span>
                        <select id="filter" class=" form-select w-auto border-0 bg-light fw-bold">
                            <option class="dropdown-item" selected>Sort by: Newest</option>
                            <option class="dropdown-item" value="1">Price: Low to High</option>
                            <option class="dropdown-item" value="2">Price: High to Low</option>
                        </select>
                    </div>

                    <div id="modelsTable" class="row g-4">
                        <div class="col-md-4">
                            <div class="card border-0 h-100">
                                <div class="bg-light rounded-3 p-4 text-center mb-3 position-relative">
                                    <span class="badge bg-black position-absolute top-0 start-0 m-3">New</span>
                                    <img src="https://purepng.com/public/uploads/large/purepng.com-silver-watchwatchclocktimepiecesilver-1421526963701j2x4p.png" class="img-fluid" style="height: 180px; object-fit: contain;">
                                </div>
                                <div class="card-body px-0 pt-0">
                                    <small class="text-muted">Patek Philippe</small>
                                    <h6 class="card-title fw-bold">Nautilus Silver</h6>
                                    <p class="fw-bold">$340.00</p>
                                    <button class="btn btn-outline-dark btn-sm w-100 mt-2">Add to Cart</button>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="card border-0 h-100">
                                <div class="bg-light rounded-3 p-4 text-center mb-3">
                                    <img src="https://purepng.com/public/uploads/large/purepng.com-mens-wrist-watchwatchclocktimepiecewrist-watchmens-wrist-watch-1421526966671eclul.png" class="img-fluid" style="height: 180px; object-fit: contain;">
                                </div>
                                <div class="card-body px-0 pt-0">
                                    <small class="text-muted">Cartier</small>
                                    <h6 class="card-title fw-bold">Santos Dumont</h6>
                                    <p class="fw-bold">$235.00</p>
                                    <button class="btn btn-outline-dark btn-sm w-100 mt-2">Add to Cart</button>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="card border-0 h-100">
                                <div class="bg-light rounded-3 p-4 text-center mb-3">
                                    <img src="https://purepng.com/public/uploads/large/purepng.com-black-watchwatchclocktimewrist-watchblack-1421526965688v5l2x.png" class="img-fluid" style="height: 180px; object-fit: contain;">
                                </div>
                                <div class="card-body px-0 pt-0">
                                    <small class="text-muted">IWC</small>
                                    <h6 class="card-title fw-bold">Pilot's Watch</h6>
                                    <p class="fw-bold">$289.00</p>
                                    <button class="btn btn-outline-dark btn-sm w-100 mt-2">Add to Cart</button>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="card border-0 h-100">
                                <div class="bg-light rounded-3 p-4 text-center mb-3">
                                    <img src="https://purepng.com/public/uploads/large/purepng.com-rolex-watchrolex-watchrolexwatchtime-1421526964324i4e6r.png" class="img-fluid" style="height: 180px; object-fit: contain;">
                                </div>
                                <div class="card-body px-0 pt-0">
                                    <small class="text-muted">Rolex</small>
                                    <h6 class="card-title fw-bold">Daytona Gold</h6>
                                    <p class="fw-bold">$520.00</p>
                                    <button class="btn btn-outline-dark btn-sm w-100 mt-2">Add to Cart</button>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="card border-0 h-100">
                                <div class="bg-light rounded-3 p-4 text-center mb-3 position-relative">
                                    <span class="badge bg-danger position-absolute top-0 start-0 m-3">-20%</span>
                                    <img src="https://purepng.com/public/uploads/large/purepng.com-blue-watchwatchclocktimeblue-14215269653453q41k.png" class="img-fluid" style="height: 180px; object-fit: contain;">
                                </div>
                                <div class="card-body px-0 pt-0">
                                    <small class="text-muted">Tag Heuer</small>
                                    <h6 class="card-title fw-bold">Carrera Blue</h6>
                                    <p class="fw-bold"><span class="text-muted text-decoration-line-through fw-normal me-2">$350</span> $280.00</p>
                                    <button class="btn btn-outline-dark btn-sm w-100 mt-2">Add to Cart</button>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="card border-0 h-100">
                                <div class="bg-light rounded-3 p-4 text-center mb-3">
                                    <img src="https://purepng.com/public/uploads/large/purepng.com-mens-wrist-watchwatchclocktimepiecewrist-watchmens-wrist-watch-1421526966671eclul.png" class="img-fluid" style="height: 180px; object-fit: contain;">
                                </div>
                                <div class="card-body px-0 pt-0">
                                    <small class="text-muted">Omega</small>
                                    <h6 class="card-title fw-bold">Seamaster</h6>
                                    <p class="fw-bold">$410.00</p>
                                    <button class="btn btn-outline-dark btn-sm w-100 mt-2">Add to Cart</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <nav class="mt-5">
                        <ul class="pagination justify-content-center">
                            <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
                            <li class="page-item active bg-black border-black"><a class="page-link bg-black border-black text-white" href="#">1</a></li>
                            <li class="page-item"><a class="page-link text-dark" href="#">2</a></li>
                            <li class="page-item"><a class="page-link text-dark" href="#">3</a></li>
                            <li class="page-item"><a class="page-link text-dark" href="#">Next</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>

    <footer class="bg-light py-5 mt-auto">
        <div class="container">
            <div class="row">
                <div class="col-md-4 mb-3">
                    <h6 class="fw-bold mb-3">Follow Us</h6>
                    <div class="d-flex gap-3">
                        <i class="bi bi-facebook fs-5"></i>
                        <i class="bi bi-instagram fs-5"></i>
                        <i class="bi bi-twitter fs-5"></i>
                        <i class="bi bi-youtube fs-5"></i>
                    </div>
                </div>
                <div class="col-md-4 mb-3">
                    <h6 class="fw-bold mb-3">About</h6>
                    <ul class="list-unstyled text-muted">
                        <li><a href="#" class="text-decoration-none text-muted">Home</a></li>
                        <li><a href="#" class="text-decoration-none text-muted">Careers</a></li>
                        <li><a href="#" class="text-decoration-none text-muted">Legal</a></li>
                    </ul>
                </div>
                <div class="col-md-4 mb-3">
                    <h6 class="fw-bold mb-3">Support</h6>
                    <ul class="list-unstyled text-muted">
                        <li><a href="#" class="text-decoration-none text-muted">Contact</a></li>
                        <li><a href="#" class="text-decoration-none text-muted">Shipping</a></li>
                        <li><a href="#" class="text-decoration-none text-muted">Returns</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </footer>

    <script src="/timestore/public/assets/Script/bootstrap.bundle.js"></script>
    <script src="/timestore/public/assets/Script/User/search.js"></script>

</body>
</html>