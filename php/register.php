<?php

require_once 'DB_Functions.php';

$db = new DB_Functions();

$response = array("error" => FALSE);

if(isset($_POST['name']) && isset($_POST['number']) && isset($_POST['password'])) {

$name = $_POST['name'];
$number = $_POST['number'];
$password = $_POST['password'];
$barcode = mt_rand(100000000,999999999);
$url1 = "http://server_url/barcode/personal_barcode/code_";
$barcode_url = "$url1$barcode.png";
$money = 200;
$point =0;



if($db->isUserExisted($number)) {
	$response["error"] =  TRUE;
	$response["error_msg"] = "유저가 이미 존재합니다." . $number;
	echo json_encode($response);
} if($db->isbarcodeExisted($barcode)) {
	$response["error"] = TRUE;
	$response["error_msg"] = "barcode error";
	echo json_encode($response);
}
else {
	
	$user = $db->storeUser($name, $number, $password,$barcode,$barcode_url,$money,$point);

	if($user) {
		$response["error"] = FALSE;
		$response["uid"] = $user["unique_id"];
		$response["user"]["name"] = $user["name"];
		$response["user"]["number"] = $user["number"];
		$response["user"]["created_at"] = $user["created_at"];
		$response["user"]["updated_at"] = $user["updated_at"];
		$response["user"]["barcode"] = $user["barcode"];
		$response["user"]["barcode_url"] = $user["barcode_url"];
		$response["user"]["money"] = $user["money"];
		$response["user"]["point"] = $user["point"];
		echo json_encode($response);
	} else {
	
		$response["error"] = TRUE;
		$response["error_msg"] = "Unknown error";
		echo json_encode($response);
	}
    } 
}else {
	$response["error"] = TRUE;
	$response["error_msg"] = "parameters error";
	echo json_encode($response);
}
?>
