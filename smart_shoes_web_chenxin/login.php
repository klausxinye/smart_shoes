<!DOCTYPE html>
<head>
	<meta charset="utf-8">
	<title>智能鞋用户登录</title>
	<style type="text/css">
		body {
			background-image:url(./bg.png);	
		}
		#login {
			
			width:210px;
			margin-left: auto;
			margin-right: auto;
			position: relative;
			top: 200px;
			color:yellow;
		}
		#title {
			text-align:center;
			width:210px;
			margin-left: 505px;
			margin-right: auto;
			position: relative;
			top: 100px;
			font-size:30px;
			color:yellow;
		}
		.logininfo {
			text-align:left;
			color:yellow;
		}
		.submit {
			text-align:relative;
			color:yellow;
		}
	</style>
</head>
<body>
<div id="title">智能鞋用户登录</div>
<div id="login">
<form action="loginsubmit.php" method="post">
<div class="logininfo">账户：<input type="text" name="name" /></div>
<div class="logininfo">密码：<input type="password" name="passwd" /></div>
<div class="submit"><input color="red" type="submit" value="登录"/><div>
</form>
</div>

<?php
if(isset($_GET["error"]))
{
?>
<p style="color:#FF0000">用户名/密码错误！</p>
<?php
}
?>
</body>

