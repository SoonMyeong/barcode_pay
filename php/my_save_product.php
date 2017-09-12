<?php

require_once 'DB_Functions.php';

$db = new DB_Functions();

$num = $_POST["number"];

$status = $db->Callmyproduct($num);

echo json_encode($status);

?>
