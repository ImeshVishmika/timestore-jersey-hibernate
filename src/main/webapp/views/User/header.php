<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/timestore/public/assets/style/bootstrap.css">
    <link rel="stylesheet" href="/timestore/public/assets/style/style.css">
</head>

<body

    <nav>
    <div class="">
        <ul class="vh-100 sidenav py-2 d-none " id="sidenav">
            <div class="d-flex justify-content-between m-2">
                <li class="py-3">
                    <a href="/timestore/Home" class="text-decoration-none mt-4">
                        <h3 class="text-light ">TimeStore</h3>
                    </a>
                </li>
                <li class="mt-2">
                    <button class="hideSideNav btn " onclick="hideSideNav();"><img src="icons/forward.png" height="20" width="20" alt=""></button>
                </li>
            </div>

            <li class="input-group bg-light rounded-3 mb-3 ">
                <input type="text" class="form-control" placeholder="Search">
                <button class="input-group-text bg-black rounded-5 border-0 m-1" id="basic-addon1"><img src="icons/search.png" height="19" width="19" alt="" onclick="search();"></button>
            </li>

            <?php

            if (!isset($_SESSION["u"])) {
            ?>
                <li class=" mt-2 mb-2 d-grid  me-3">
                    <a href="/timestore/logIn" class="btn bg-black text-light rounded-4 fw-bold ">Sign in</a>
                </li>
                <li class="d-flex justify-content-center">
                    <span><a class="text-decoration-none text-secondary" href="#">Register</a></span>
                </li>

            <?php
            } else {
            ?> <li class=" mt-2 mb-2 d-grid  me-3">
                    <a class="btn btn-dark rounded-4 fw-bold ">Welcome</a>
                </li><?php
                    }

                        ?>

            <hr class="me-4">

            <li><a class="dropdown-item d-flex justify-content-center" href="watchlist.php">Watchlist</a></li>

            <?php
            if (isset($_SESSION["u"])) {
            ?>
                <li><a class="dropdown-item d-flex justify-content-center" href="cart.php">Cart</a></li>
                <li><a class="dropdown-item d-flex justify-content-center" href="history.php">History</a></li>
            <?php
            }
            ?>
            <li><a class="dropdown-item d-flex justify-content-center" href="watchlist.php">Watchlist</a></li>
            <hr class="me-4">

            <li><a class="dropdown-item text-secondary d-flex justify-content-center" href="#">Setting</a></li>
            <li><a class="dropdown-item text-secondary d-flex justify-content-center" href="#">Seller Log in</a></li>
            <li><a class="dropdown-item text-secondary d-flex justify-content-center" href="#">Buyer Protection</a></li>
            <li><a class="dropdown-item text-secondary d-flex justify-content-center" href="#">Help Center</a></li>
            <li><a class="dropdown-item text-secondary d-flex justify-content-center" href="#">Accessibility</a></li>




        </ul>
    </div>
    </nav>

    <nav class="navbar navbar-expand-lg navbar-dark bg-black py-4">
        <div class="container">
            <a class="navbar-brand fw-bold fs-4" href="/timestore/Home">TimeStore</a>

            <button class="navbar-toggler" onclick="hideSideNav();" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-between py-1" id="navbarNav">

                <div class="mx-auto w-50 d-none d-lg-block">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search">
                        <button class="btn btn-light" type="button">
                            <i class="bi bi-search"></i>
                        </button>
                    </div>
                </div>

                <div class="d-flex align-items-center mt-3 mt-lg-0">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                        <li class="nav-item mt-2 px-3">
                            <div class="position-relative">
                                <a href="#" class="text-white fs-5"><i class="bi bi-cart"></i></a>
                                <span class="position-absolute  badge rounded-pill bg-danger" style="font-size: 0.6rem;">
                                    2
                                </span>
                            </div>
                        </li>


                        <li class="nav-item dropdown mx-3">
                            <a class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="bi bi-person fs-5 h-25"></i>
                            </a>
                            <ul class="dropdown-menu px-3">
                                <?php
                                if (!isset($_SESSION["u"])) {
                                ?>
                                    <li class=" mt-2 mb-2 d-grid ">
                                        <a href="/timestore/logIn" class="btn bg-black rounded-4 fw-bold ">Sign in</a>
                                    </li>
                                    <hr class="">
                                    <li class="d-flex justify-content-center">
                                        <span><a class="text-decoration-none text-secondary" href="#">Register</a></span>
                                    </li>
                                <?php
                                } else {
                                ?> <li class=" mt-2 mb-2 d-grid  me-3">
                                        <a class="btn btn-dark rounded-4 fw-bold ms-3 ">Welcome</a>
                                    </li>
                                    <li><a class="dropdown-item d-flex justify-content-center" href="cart.php">Cart</a></li>
                                    <li><a class="dropdown-item d-flex justify-content-center" href="history.php">History</a></li>
                                    <li><a class="dropdown-item d-flex justify-content-center" href="/timestore/profile">Profile</a></li>
                                    <li><a class="dropdown-item d-flex justify-content-center" href="watchlist.php">Watchlist</a></li>
                                    <hr class="">
                                    <li><a class="dropdown-item text-danger my-1 d-flex justify-content-center" onclick="logOut();">Log Out</a></li>

                                <?php
                                }
                                ?>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>

    <script src="/timestore/public/assets/Script/bootstrap.bundle.js"></script>

    <?php
    session_write_close()
    ?>

</body>

</html>