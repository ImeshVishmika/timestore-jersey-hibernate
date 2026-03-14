<?php 

include "connection.php";
session_start();

if( isset($_SESSION["u"]["email"]) && isset($_POST["orderId"]) && isset($_POST["total"])){

    $email=$_SESSION["u"]["email"];
    $orderId=$_POST["orderId"];
    $total=$_POST["total"];

    Database::iud("INSERT INTO `invoice`(`order_id`,`email`,`total`) VALUES($orderId , '".$email."',$total) ");

    echo("nice");
}else{
    echo("not nice");
}


?>