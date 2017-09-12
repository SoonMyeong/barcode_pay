<?php

require_once 'DB_Functions.php';

$db = new DB_Functions();

$barcode_number = $_POST["barcode_number"];

$db->delete_comment($barcode_number);

?>
