<?php
require_once 'database.ini.php';
$number = 10;
$sameminuteThreshold = 1;
function getbehavior($name)
{
		//$name = "Klaus";//$_POST["name"];
		$sql = "select * from user_history where name = '".$name."'";
		$query =  $GLOBALS["db"]->query($sql);
		$array = array();
		$avgarray = array();
		$row = mysqli_fetch_assoc($query);
		$count = 0;
		while($row)
		{
			$intarray = array();
			$status = $row['status'];
			$statusarray = str_split($status);
			foreach($statusarray as $v)
				{
				  $intarray[] = (int)$v;
				}
				$array[] = $intarray;
				$count++;
				$row = mysqli_fetch_assoc($query);
		}
		for($j = 0; $j < $GLOBALS["number"]; $j++)
			{
				$max = array(0,0,0,0,0);
				$maxnum = 0;
				$maxstatus = 5;
				for($i = 0; $i < $count; $i++)
					{
						$max[$array[$i][$j]]++;
					}
				for($k = 0; $k < 5; $k++)
				 {
				 	if($max[$k] > $maxnum)
				 		{
				 			$maxnum = $max[$k];
				 			$maxstatus = $k;
				 		}
				 }
				 $avgarray[] = $maxstatus;
			}
	  return $avgarray;
}
function test($name,$db)
{
	$array = array(1,2,3,4);
	for ($i = 0; $i < 4; $i++) {
	 	  // code to execute
	 	  echo $array[1]; 
	}
	}
function comparebehavior($name)
{
	  $samebehaviorlist = array();
	  $namelist = array();
		$sql = "select distinct name from user_history where name != '".$name."'";
		$query = $GLOBALS["db"]->query($sql);
		$row = mysqli_fetch_assoc($query);
		$userbehavior = array();
		$userbehavior = getbehavior($name,$GLOBALS["db"]);
		$currentbehavior = array();
		$sameminute = 0;
		$sarray = getbehavior($name);
    $currentname;
    while($row)
		{
			$namelist[] = $row['name'];
			$row = mysqli_fetch_assoc($query);
	  }
	 	foreach($namelist as $currentname)
	 	{
	 		$sameminute = 0;
	 		$currentbehavior = getbehavior($currentname,$GLOBALS["db"]);
			for($i = 0; $i < $GLOBALS["number"]; $i++)
			if($userbehavior[$i] == $currentbehavior[$i] && $userbehavior[$i] >= 3)
				$sameminute++;
			if($sameminute >= $GLOBALS["sameminuteThreshold"])
		  $samebehaviorlist[] = $currentname;
		  //echo $currentname;
  	}
  	return $samebehaviorlist;
}
?>

<?php
//comparebehavior("Klaus");
//print_r(comparebehavior("Klaus"));
?>
