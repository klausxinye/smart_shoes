<?php
session_start();

if(!isset($_SESSION["name"]) || !isset($_SESSION["login"]) || $_SESSION["login"]!="true")
{
	header("location:login.php?error=no_login");
}
?>
