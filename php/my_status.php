<?php

require_once 'DB_Functions.php';

$db = new DB_Functions();

$response = array("error" => FALSE);

$number = $_POST["number"];

$user = $db->showmystatus($number);

if($user) {
		$response["error"] = FALSE;
		$response["number"]= $user["number"];
		$response["money"] = $user["money"];
		$response["point"] = $user["point"];
		echo json_encode($response);
	} else {
	
		$response["error"] = TRUE;
		$response["error_msg"] = "Unknown error";
		echo json_encode($response);
	}

?>
