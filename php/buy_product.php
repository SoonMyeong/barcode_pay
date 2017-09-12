<?php

require_once 'DB_Functions.php';

$db = new DB_Functions();

$response = array("error" => FALSE);

$barcode = mt_rand(100000000,999999999);
$url1 = "http://server_url/barcode/save_barcode/code_";
$barcode_image="$url1$barcode.png";
(int)$number = $_POST['number'];
(int)$point = $_POST['point'];
(int)$money= $_POST['money'];
(int)$fprice= substr($_POST['fprice'],0,strlen($_POST['fprice'])-1);
$fname = $_POST['fname'];


if((int)$money >= (int)$fprice) {

	(string)$money = (int)$money-(int)$fprice;
	(string)$point = (int)$point+((int)$fprice*0.1);

	$status= $db->Updatemystatus($number,$money,$point);	

	$foodimage_parsor=$db->Insertfoodimage($fname);
	$foodimage_parsor_2 = array();
	$foodimage_parsor_2 = $foodimage_parsor;
	$food_image = $foodimage_parsor_2["food_image"];


	$user = $db->storeproduct($number,$barcode,$fprice,$fname,$food_image,$barcode_image);
	$review = $db->storereview($number,$barcode,$fprice,$fname,$food_image,$barcode_image);


	if($user) {
		$response["error"] = FALSE;
		$response["money"] = (string)$money;
		$response["point"] = (string)$point;
		$response["number"] = $user["number"];
		$response["user"]["barcode"] = $user["barcode_number"];
		$response["user"]["fprice"] = $user["food_price"];
		$response["user"]["fname"] = $user["food_name"];
		$response["user"]["food_image"] = $user["food_image"];
		$response["user"]["barcode_image"] = $user["barcode_image"];
		$response["user"]["created_at"] = $user["created_at"];
		echo json_encode($response);
	} else {
	
		$response["error"] = TRUE;
		$response["error_msg"] = "Unknown error";
		echo json_encode($response);
	}

} else {
		$response["error"] = TRUE;
		$response["error_msg"] = "돈이 부족합니다!";
		echo json_encode($response);

}

?>


