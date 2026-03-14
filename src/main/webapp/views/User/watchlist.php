<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style/bootstrap.css">
    <link rel="stylesheet" href="style/style.css">
    <title>Watchlist</title>


</head>

<body>

    <div class="fixed-top">
        <?php include "header.php"; ?>
    </div>

    <?php include "connection.php";
    ?>

    <?php

    $email = $_SESSION["u"]["email"];

    $watchlist_rs = Database::search("SELECT * FROM `watchlist` JOIN `model` ON `watchlist`.`product_id`=`model`.`model_id` WHERE `users_email`='" . $email . "' ");
    $watchlist_num = $watchlist_rs->num_rows;

    if ($watchlist_num > 0) {
    ?>
        <div class="container min-vh-100 mt-6 mb-3">
            <div class="row d-flex justify-content-center ">
                <div class="row row-cols-1 row-cols-lg-2 g-4 flex-row  ">

                    <?php

                    for ($x = 0; $x < $watchlist_num; $x++) {
                        $watchlist_data = $watchlist_rs->fetch_assoc();
                        $id = $watchlist_data["product_id"];

                        $img_rs = Database::search("SELECT * FROM `product_img` WHERE `product_id`='" . $id . "' ");
                        $img_data = $img_rs->fetch_assoc();

                    ?>
                        <div class="col">
                            <div class=" card mb-3 mx-3" style="max-width: 540px;">
                                <div class="row g-0">
                                    <div class="col-4 py-3">
                                        <img src="<?php echo ($img_data["img_path"]) ?>" class="img-fluid rounded-start" alt="...">
                                    </div>
                                    <div class="col-8">
                                        <div class="card-body">
                                            <div class="d-flex justify-content-between">
                                                <h5 class="card-title product-title px-2"><?php echo $watchlist_data["model"] ?></h5>
                                                <div class="col-2 d-grid closebtn">
                                                    <button class="btn btn-light fw-bold rounded-5 " onclick="removeFromWatchlist(<?php echo ($watchlist_data['watchlist_id']); ?>);"><i><img src="icons/close.png" alt="" width="15" height="15"></i></button>
                                                </div>
                                            </div>
                                            <h2 class="card-title fw-bold">LKR:25,000</h2>
                                            <div class=" fs-5">
                                                <li class="fa fa-star checked " id="s1"></li>
                                                <li class="fa fa-star checked " id="s2"></li>
                                                <li class="fa fa-star checked" id="s3"></li>
                                                <li class="fa fa-star checked" id="s4"></li>
                                                <li class="fa fa-star" id="s5"></li>
                                            </div>
                                            <hr>
                                        </div>
                                        <?php
                                        if (isset($_SESSION["u"])) {
                                        ?>
                                            <div class="row mb-4">
                                                <div class="col-12 d-grid pb-1 px-4">
                                                    <a href="viewProduct.php?id=<?php echo ($watchlist_data["product_id"]); ?>" class="btn btn-dark fw-bold rounded-4">View Product</a>
                                                </div>
                                            </div>
                                        <?php
                                        }
                                        ?>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <?php
                    }
                    ?>
                </div>
            </div>
        </div>
        <?php include "footer.php"; ?>

    <?php
    } else {
    ?>
        <div class="vh-100 align-content-center">
            <div class=" container d-flex flex-column justify-content-center h-75">
                <h1>Your watchlist is empty</h1>
                <a href="index.php" class="btn btn-dark col-6 col-md-2 fw-bold">Start Shopping</a>
            </div>
        </div>
        <div class="fixed-bottom">
            <?php include "footer.php"; ?>
        </div>
    <?php
    }
    ?>

    <script src="script/script.js"></script>

</body>

</html>