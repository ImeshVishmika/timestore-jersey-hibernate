<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style/bootstrap.css">
    <link rel="stylesheet" href="style/style.css">
    <title>History</title>
</head>

<body>
    <?php
    include "connection.php";
    ?>
    <div class="fixed-top">
        <?php include "header.php"; ?>
    </div>

    <div class="container mt-5">
        <div class="row  justify-content-center">
            <div class="row row-cols-1 row-cols-md-2 g-4">
                <?php
                $history_rs = Database::search("SELECT *,model.model_id as pid,user_history.id as hid FROM `user_history` JOIN `model` ON `user_history`.`product_id`=`model`.`model_id` WHERE `user_id`='" . $_SESSION["u"]["email"] . "' ");
                $history_num = $history_rs->num_rows;

                for ($i = 0; $i < $history_num; $i++) {
                    $history_data=$history_rs->fetch_assoc();
                    $prodcut_id=$history_data["product_id"];
                    $img_rs=Database::search("SELECT `img_path` FROM `product_img` WHERE `product_id`='".$prodcut_id."' ");
                    $img_data=$img_rs->fetch_assoc();
                ?>
                    <div class="col">
                        <div class="card mb-3 px-2">
                            <div class="row g-0">
                                <div class="col-4 py-5 ">
                                    <img src=<?php echo $img_data["img_path"]  ?> class="img-fluid rounded-start" alt="...">
                                </div>
                                <div class="col-8">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between">
                                            <h5 class="card-title"><?php echo($history_data["model"]); ?></h5>

                                        </div>
                                        <div class="d-flex justify-content-between">
                                            <h4 class="card-title fw-bold">Rs.<?php echo $history_data["price"] ?></h4>
                                            <h2 class="fs-5 "><?php echo($history_data["amount"]) ?>*</h2>
                                        </div>
                                        <div class="">
                                            <span class="card-title  fw-bold">Total:660,000</span>
                                        </div>
                                        <div class="">
                                            <span class="card-title  fw-bold">Order Date:<?php echo $history_data["buy_datetime"] ?></span>
                                        </div>
                                        <div class="fs-7 my-1">
                                            <li class="fa fa-star checked" id="s1"></li>
                                            <li class="fa fa-star checked" id="s2"></li>
                                            <li class="fa fa-star " id="s3"></li>
                                            <li class="fa fa-star" id="s4"></li>
                                            <li class="fa fa-star" id="s5"></li>
                                        </div>
                                        <hr>
                                    </div>
                                    <div class="row mb-3 mx-2">
                                        <div class="col-12 col-md-6 d-grid mb-1">
                                            <button class="btn text-light fw-bold rounded-4" style="background-color: #dc3545;">Add To Cart</button>
                                        </div>
                                        <div class="col-12 col-md-6 d-grid mb-1">
                                            <button class="btn btn-secondary fw-bold rounded-4" onclick="removeFromHistory(<?php echo $history_data['hid'] ?>)">Remove</button>
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
    </div>
    
</body>

</html>