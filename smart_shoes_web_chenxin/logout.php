<?php
$_SESSION["name"]="";
$_SESSION["login"]="false";
session_destroy();
header("location:login.php");
?>
