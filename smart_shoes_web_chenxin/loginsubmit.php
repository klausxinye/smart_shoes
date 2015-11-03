<?php
require_once "database.ini.php";
session_start();

if(isset($_POST["name"]) && isset($_POST["passwd"]) && $_POST["name"]!="" && $_POST["passwd"]!="")
{
  $sql = "select * from account where name='".$_POST["name"]."'";
  $query = $db->query($sql);
  $row = mysqli_fetch_assoc($query);

	if($_POST["passwd"]==$row["password"])
	{
		$_SESSION["name"]=$_POST["name"];
    $_SESSION["login"]="true";
		header("Location:index.php");
	}
	else
	{
		session_destroy();
		header("Location:login.php?error=passwd_error");
	}
}
elseif (isset($_POST["name"]) && isset($_POST["passwd"]) && $_POST["name"]=="")
{
  session_destroy();
  header("Location:login.php?error=no_name");
}
elseif (isset($_POST["name"]) && isset($_POST["passwd"]) && $_POST["password"]=="")
{
  session_destroy();
  header("Location:login.php?error=no_passwd");
}
else {
  session_destroy();
  header("Location:login.php?error=system_error");
}
?>
