<?php

require_once 'DB_Functions.php';

$db = new DB_Functions();

$fname =$_POST["fname"];

$add_count = $db->count_add($fname);



echo json_encode($add_count); 


?>
