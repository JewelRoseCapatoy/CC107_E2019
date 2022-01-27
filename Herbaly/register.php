<?php

$name = $_POST["name"];
$email = $_POST["email"];
$password = $_POST["password"];

	$con=mysqli_connect("localhost", "id18199676_herbalylogin", "<Klqn>9I3x/<r(Gc", "id18199676_herbalyregis");
	
$sql = "INSERT INTO register (name,email,password) VALUES ('$name', '$email', '$password')";

$result = mysqli_query($con, $sql);
if($result){
	$response=array("success"=>"1","message"=>"Registration Successful");
/* echo("Registration Successful");*/
}else{
	$response=array("success"=>"0","message"=>"Registration Failed");
	/*echo("Registration Failed");*/
}
header('Content-type: application/json');
echo json_encode($response);
?>
	