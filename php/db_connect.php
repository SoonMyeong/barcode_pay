<?php
class DB_connect {
	private $conn;

public function connect() {
	$this->conn = new mysqli("localhost", "root", "root", "barcode_pay");
	
	return $this->conn;
	}

}	

?>
