<?php

require_once 'DB_Functions.php';

$db = new DB_Functions();

$response = array("error" => FALSE);

$food_name = $_POST["fname"];
$review = $_POST["write"];
$user_name = $_POST["user_name"];
$barcode = $_POST["barcode"];
$user_number = $_POST["user_number"];
$user_score = $_POST["user_score"];

$db->Sendreview($user_name, $food_name, $review,$barcode, $user_number,$user_score);

$db->deletereview($barcode);




?>
