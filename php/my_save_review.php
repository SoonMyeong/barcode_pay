<?php

require_once 'DB_Functions.php';

$db = new DB_Functions();

$num = $_POST["number"];

$status = $db->Callmyreview($num);

echo json_encode($status);

?>
