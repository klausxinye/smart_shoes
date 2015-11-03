<?php
require_once 'database.ini.php';
//var_dump($_POST);
$name = $_POST["name"];
echo $name;
$date = $_POST["date"];
$status = $_POST["status"];
$runtime = $_POST["runtime"];
$walktime = $_POST["walktime"];
$runstep = $_POST["runstep"];
$walkstep = $_POST["walkstep"];
$weight = $_POST["weight"];
echo $walkstep;
//if(existdate($name,$date) == 0)
//{
	$sql="insert into user_history (name, date,status,runtime,walktime,runstep,walkstep,weight) values('".$name."',".$date.",'".$status."',".$runtime.",".$walktime.",".$runstep.",".$walkstep.",".$weight.");";
	$query = $db->query($sql);
//}
//else
//{
//	$sql = "update user_history set //name='".$name."',walktime=".$walktime.",runtime=".$runtime.",walkstep=".$runtime.",runstep=".$runstep.",status='".$status."');";
//}

//if($db->query($sql) == false)
//   echo "yes";
//$sql="insert into user_history (name, date,status,runtime,walktime,runstep,walkstep) //values('".$name."',".$date.",'".$status."',".$runtime.",".$walktime.",".$runstep.",".$walkstep.");";
$query = $db->query($sql);
if($query==false)
{
	echo "FAIL!";
}
else
{
	echo "SUCCESS!";
}
?>
