<?php
require_once 'database.ini.php';
require_once 'userbehavior.php';
$name = $_POST["name"];
//$name = "Klaus";
$samebehaviorlist = array();
$samebehaviorlist = comparebehavior($name);
//print_r($samebehaviorlist);
echo json_encode($samebehaviorlist);
//echo ¡°shabi¡±;
?>