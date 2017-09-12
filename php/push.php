<?php

require_once 'DB_Functions.php';

$db = new DB_Functions();

$id = $_POST["id"];

$db->push_id($id);

?>

