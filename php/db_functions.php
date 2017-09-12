<?php

class DB_Functions {

	private $conn;

	function __construct() {
		require_once 'DB_connect.php';
		$db = new DB_connect();
		$this->conn = $db->connect();
	}

	public function storeUser($name,$number, $password,$barcode,$barcode_url, $money,$point) {
	 $uuid = uniqid('',true);
	 $hash = $this->hashSSHA($password);
	 $encrypted_password = $hash["encrypted"];
	 $salt = $hash["salt"];

	 $stmt = $this->conn->prepare("INSERT INTO users(unique_id,name,number,barcode,barcode_url,money,point,encrypted_password, salt, created_at) VALUES (?,?,?,?,?,?,?,?,?,NOW())");
	 $stmt->bind_param("sssssssss", $uuid,$name,$number,$barcode,$barcode_url,$money,$point,$encrypted_password, $salt);
	 $result = $stmt->execute();
	 $stmt->close();

	 if($result) {
		$stmt= $this->conn->prepare("SELECT * FROM users WHERE number = ?");
		$stmt->bind_param("s",$number);
		$stmt->execute();
		$user= $stmt->get_result()->fetch_assoc();
		$stmt->close();
		
		return $user;
	  } else {
		return false;
	  }

	}
	public function getUserByEmailAndPassword($number,$password) {
	$stmt = $this->conn->prepare("SELECT * FROM users WHERE number = ?");
	$stmt->bind_param("s",$number);

	if($stmt->execute()) {
		$user = $stmt->get_result()->fetch_assoc();
		$stmt->close();

		return $user;
	} else {
		return NULL;
	}
	}

	public function isUserExisted($number) {
	 $stmt = $this->conn->prepare("SELECT number from users WHERE number = ?");

	 $stmt->bind_param("s", $number);
	 $stmt->execute();
	 $stmt->store_result();

	 if($stmt->num_rows >0) {
		$stmt->close();
		return true;
	 } else {
		$stmt->close();
		return false;
	 }
	}

	public function isbarcodeExisted($barcode) {
	 $stmt = $this->conn->prepare("SELECT barcode from users WHERE barcode = ?");

	 $stmt->bind_param("s", $barcode);
	 $stmt->execute();
	 $stmt->store_result();

	 if($stmt->num_rows >0) {
		$stmt->close();
		return true;
	 } else {
		$stmt->close();
		return false;
	 }
	}

	public function hashSSHA($password) {
		$salt = sha1(rand());
		$salt = substr($salt, 0 , 10);
		$encrypted = base64_encode(sha1($password . $salt, true) . $salt);
		$hash = array("salt" => $salt, "encrypted" => $encrypted);
		return $hash;
	}

	public function checkhashSSHA($salt, $password) {
		$hash = base64_encode(sha1($password . $salt, true) . $salt);
		return $hash;
	}

	public function storeproduct($number,$barcode,$fprice,$fname,$food_image,$barcode_image) {

	$stmt = $this->conn->prepare("INSERT INTO barcode(number, barcode_number, food_price, food_name, food_image, barcode_image, created_at) VALUES (?,?,?,?,?,?,NOW())");
	 $stmt->bind_param("ssssss", $number,$barcode,$fprice,$fname,$food_image,$barcode_image);
	 $result = $stmt->execute();
	 $stmt->close();

	 if($result) {
		$stmt= $this->conn->prepare("SELECT * FROM barcode WHERE barcode_number = ?");
		$stmt->bind_param("s",$barcode);
		$stmt->execute();
		$user= $stmt->get_result()->fetch_assoc();
		$stmt->close();
		
		return $user;
	  } else {
		return false;
	  }

	}
	
	public function showmystatus($number) {
$stmt= $this->conn->prepare("SELECT money, point, number FROM users WHERE number= ?");
		$stmt->bind_param("s",$number);
		$stmt->execute();
		$user= $stmt->get_result()->fetch_assoc();
		$stmt->close();
		
		return $user;
	  } 

	public function Insertfoodimage($fname) {
	$stmt= $this->conn->prepare("SELECT food_image FROM products WHERE food_name=?");
		$stmt->bind_param("s",$fname);
		$stmt->execute();
		$foodimage=$stmt->get_result()->fetch_assoc();
		$stmt->close();

		return $foodimage;
	}
	
	public function Updatemystatus($num,$mon,$po) {

	$stmt = $this->conn->prepare("UPDATE users SET money=?,point=? WHERE number=?");
	$stmt->bind_param("sss",$mon,$po,$num);
	$stmt->execute();
	$stmt->close();
	}

	public function Callmyproduct($num) {

	$product = array();
	$stmt = $this->conn->prepare("SELECT * FROM barcode WHERE number=?");
	$stmt->bind_param("s",$num);
	$stmt->execute();
	$result= $stmt->get_result();
	
	while($row = $result->fetch_assoc()) {
		$product[] = $row;
	}
	$stmt->close();
	return $product;
	}

	public function Sendreview($user_name,$food_name,$review,$barcode,$user_number,$user_score) {

	$stmt = $this->conn->prepare("INSERT INTO review(name, food_name, food_review, barcode_number,number,score,created_at) VALUES (?,?,?,?,?,?,NOW())");
	 $stmt->bind_param("ssssss", $user_name,$food_name,$review,$barcode,$user_number,$user_score);
	 $result = $stmt->execute();
	 $stmt->close();
	}

	public function deletereview($barcode) {
	$stmt = $this->conn->prepare("DELETE FROM review_index WHERE barcode_number=?");
	 $stmt->bind_param("s",$barcode);
	 $result = $stmt->execute();
	 $stmt->close();
	}

	public function storereview($number,$barcode,$fprice,$fname,$food_image,$barcode_image) {

	$stmt = $this->conn->prepare("INSERT INTO review_index(number, barcode_number, food_price, food_name, food_image, barcode_image, created_at) VALUES (?,?,?,?,?,?,NOW())");
	 $stmt->bind_param("ssssss", $number,$barcode,$fprice,$fname,$food_image,$barcode_image);
	 $result = $stmt->execute();
	 $stmt->close();
	}

	public function Callmyreview($num) {

	$product = array();
	$stmt = $this->conn->prepare("SELECT * FROM review_index WHERE number=?");
	$stmt->bind_param("s",$num);
	$stmt->execute();
	$result= $stmt->get_result();
	
	while($row = $result->fetch_assoc()) {
		$product[] = $row;
	}
	$stmt->close();
	return $product;
	}


	public function Callreviewlist($fname) {
	
	$product = array();
	$stmt = $this->conn->prepare("SELECT * FROM review WHERE food_name=? Limit 2");
	$stmt->bind_param("s",$fname);
	$stmt->execute();
	$result= $stmt->get_result();
	
	while($row = $result->fetch_assoc()) {
		$product[] = $row;
	}
	
	
	$stmt->close();
	return $product;
	}

	public function delete_comment($barcode_number) {
	
	$stmt=$this->conn->prepare("DELETE FROM review WHERE barcode_number=?");
	$stmt->bind_param("s",$barcode_number);
	$stmt->execute();
	$stmt->close();		
	}

	public function Call_notice($call) {
	
	$notice_menu = array();
	$stmt=$this->conn->prepare("SELECT * FROM notice WHERE number=?");
	$stmt->bind_param("s",$call);
	$stmt->execute();
	$result= $stmt->get_result();
	
	while($row = $result->fetch_assoc()) {
		$notice_menu[] = $row;
	}
	
	$stmt->close();
	return $notice_menu;
	}

	public function count_add($fname) {

	$stmt=$this->conn->prepare("SELECT COUNT(*) FROM review WHERE food_name=?");
	$stmt->bind_param("s",$fname);
	$stmt->execute();
	$result= $stmt->get_result()->fetch_assoc();
	$stmt->close();
	
	return $result;
	}

	public function Callreviewlist_all($fname) {
	
	$product = array();
	$stmt = $this->conn->prepare("SELECT * FROM review WHERE food_name=?");
	$stmt->bind_param("s",$fname);
	$stmt->execute();
	$result= $stmt->get_result();
	
	while($row = $result->fetch_assoc()) {
		$product[] = $row;
	}
	
	
	$stmt->close();
	return $product;
	}

	public function Charge_money($number) {

	$stmt = $this->conn->prepare("SELECT number,money, point FROM users WHERE number=?");
	$stmt->bind_param("s",$number);
	$stmt->execute();
	$result=$stmt->get_result()->fetch_assoc();
	$stmt->close();

	return $result;
	}

	public function push_id($id) {

	$stmt=$this->conn->prepare("SELECT regId FROM push WHERE regId=?");
	$stmt->bind_param("s",$id);
	$stmt->execute();
	$stmt->store_result();
	
	if($stmt->num_rows >0) {
		$stmt->close();
	 } else {
		$stmt->close();
	$stmt= $this->conn->prepare("INSERT INTO push(regId) VALUES(?)");
	$stmt->bind_param("s",$id);
	$result =$stmt->execute();
	$stmt->close();
	}

   	}
	



}
?>
