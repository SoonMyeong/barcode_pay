<?php
class DB_connect {
	private $conn;

public function connect() {
	$this->conn = new mysqli("localhost", "root", "root", "android_api");
	
	return $this->conn;
	}

}	

?>
