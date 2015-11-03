<?php
require_once 'database.ini.php';

session_start();
$name = $_SESSION["name"];

$sql = "select * from height where name = '".$name."'";
$query = $db->query($sql);

$arr = array();
$row = mysqli_fetch_assoc($query);
while($row)
{
	$arr[] = $row;
	$row = mysqli_fetch_assoc($query);
}

echo json_encode($arr);
?>
