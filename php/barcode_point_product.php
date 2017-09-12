<?php

require_once 'DB_Functions.php';

$db = new DB_Functions();

$response = array("error" => FALSE);

$barcode = mt_rand(100000000,999999999);
$food_image= "http:111111111";
$url1 = "http://server_url/barcode/save_barcode/code_";
$barcode_image="$url1$barcode.png";
(int)$number = $_POST['number'];
(int)$point = $_POST['point'];
(int)$money= $_POST['money'];
(int)$fprice= substr($_POST['fprice'],0,strlen($_POST['fprice'])-1);
$fname = $_POST['fname'];
(int)$use_money=$_POST['use_money'];
(int)$use_point=$_POST['use_point'];
(int)$use_total = (int)$use_money + (int)$use_point;

if((int)$money >= (int)$use_money && (int)$point >= (int)$use_point) {

if((int)$use_total == (int)$fprice) {

	if($use_money >0){
		(int)$money = (int)$money-(int)$use_money;
	}
	if($use_point >0) {
		(int)$point = (int)$point-(int)$use_point+((int)$fprice*0.1);
	}

	/*(int)$money = (int)$money-(int)$use_money;
	(int)$point = (int)$point-(int)$use_point+((int)$fprice*0.1);*/
	
	$status= $db->Updatemystatus($number,$money,$point);

	$foodimage_parsor=$db->Insertfoodimage($fname);
	$foodimage_parsor_2 = array();
	$foodimage_parsor_2 = $foodimage_parsor;
	$food_image = $foodimage_parsor_2["food_image"];

	$user = $db->storeproduct($number,$barcode,$fprice,$fname,$food_image,$barcode_image);
	$review = $db->storereview($number,$barcode,$fprice,$fname,$food_image,$barcode_image);

	if($user) {
		$response["error"] = FALSE;
		$response["money"] = $money;
		$response["point"] = $point;
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
		$response["error_msg"] = "현금과 포인트를 금액에 맞게 입력해주세요";
		echo json_encode($response);
}

} else {
		$response["error"] = TRUE;
		$response["error_msg"] = "보유하신 현금 또는 포인트 보다 높게 입력 하셨습니다";
		echo json_encode($response);
}


?>
