<?php

require_once 'DB_connect.php';
require_once 'DB_Functions.php';

$db = new DB_Functions();

$response = array("error"=> FALSE);

if(isset($_POST['number']) && isset($_POST['password'])) {

$number = $_POST['number'];
$password = $_POST['password'];

$user = $db->getUserByEmailAndPassword($number, $password);

if($user != false) {
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
	$response["error_msg"] = "학번 혹은 패스워드 오류입니다. ";
	echo json_encode($response);
}

} else {

	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters error";
	echo json_encode($response);
}


?>
