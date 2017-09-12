<?php

require_once 'DB_Functions.php';

$db = new DB_Functions();

$call = $_POST["call"];

$status=$db->Call_notice($call);

echo json_encode($status);
?>

