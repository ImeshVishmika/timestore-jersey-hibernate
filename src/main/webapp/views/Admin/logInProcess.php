<?php

include "connection.php";

$email=$_POST["email"];
$password=$_POST["password"];

$user_rs=Database::search("SELECT * FROM `admin` WHERE `email`='".$email."' AND `password`='".$password."' ");

if($user_rs->num_rows==1){

    session_start();
    $user_data=$user_rs->fetch_assoc(); 
    $_SESSION["u"]=[
        "email"=>$user_data["email"],
        "role"=>"admin",
    ];

    if($_POST["rm"]==1){
        setcookie("email",$email,time()+60*60*24*7);
        setcookie("pw",$password,time()+60*60*24*7);
    };
       
    echo("success");

}



?>