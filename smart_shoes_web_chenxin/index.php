﻿<?php
  require_once 'include/session.inc.php';
?>
<!doctype html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Exercise management</title>
  <script type="text/javascript" src="jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="script.js"></script>
    <style type="text/css">
      .navigation {
      width: 120px;
      height: 35px;
      background-color:gray;
      margin:0px 0px 10px 0px
      }
      .header {
      margin-left: auto;
      margin-right: auto;
      width: 100px;
      }
      h2 {
      color:white;
      text-align: center;
      padding: 4px 0 4px 0
      }
      h3 {
      color:gray;
      text-aligh:center;
      }
      .float {
      float:right;
      color:red;
      }
      .float1 {
      color:yellow;
      float:left;
      width: 120px;
      height: 35px;
      margin:0px 0px 10px 0px
      //background-color:gray;
      }
      .userinfo {
      font-size:1.2em;
      font-weight:bold;
      color:gray;
      /*#8FBC8B*/
      }
      a {
      text-decoration:none;
      }
      body {
      background-image:url(./bg.png);
      }
      .user {
      width: 120px;
      height: 35px;
      background-color:gray;
      margin-left: 50;
      margin-right: auto;
      }
      .button {
      width: 120px;
      height: 35px;
      }
    </style>
</head>
<body>
  <div class="navigation">
    <h2>用户信息</h2>
  </div>
  
  <a href="logout.php" class="float">&nbsp;注销&nbsp;</a>
  
  <div class="userinfo">
    <label>姓名：</label>
    <span id="username"></span>
  </div>


  
  <div class="userinfo">
    <label>体重：</label>
    <span id="userweight"></span>
    <label>kg</label>
  </div>  


  <hr />

  <div >
    <a href="#" onclick="init();return false" class="float1">&nbsp;&nbsp;</a>
  </div>
  <div style="position:absolute; left:50px; top:145px; width:600px;height:104px;">
  	<a href="#" onclick="init();return false" class="float1" style="position:absolute; left:50px; top:0px; width:100px;height:104px;">&nbsp;体重&nbsp;</a>
    <a href="#" onclick="init();return false" class="float1" style="position:absolute; left:700px; top:0px; width:100px;height:104px;">&nbsp;步行时间&nbsp;</a>
    <a href="#" onclick="init1();return false" class="float1" style="position:absolute; left:800px; top:0px; width:100px;height:104px;">&nbsp;跑步时间&nbsp;</a>
    <a href="#" onclick="init2();return false" class="float1" style="position:absolute; left:900px; top:0px; width:100px;height:104px;">&nbsp;步行步数&nbsp;</a>
    <a href="#" onclick="init3();return false" class="float1" style="position:absolute; left:1000px; top:0px; width:100px;height:104px;">&nbsp;跑步步数&nbsp;</a>
    </div>
	<div id="records"></div>
  
    <script src="../highstock/js/highstock.js"></script>
    <script type="text/javascript" src="../highstock/js/themes/gray.js"></script>
    <script src="../highstock/js/modules/exporting.js"></script>


  <table width="100%">
    <tr>
      <td style="width:50px;">
      </td>
      <td>
        <div id="weightscale" style="height: 500px; width: 600px;" ></div>
      </td>
      <td style="width:50px;">
      </td>
      <td>
        <div id="exercisescale" style="height: 500px; width: 600px;"></div>
      </td>
    </tr>
  </table>

  <!--
    <div class="header"><h3>体重趋势图</h3></div>
    <div id="container" style="width:100%; height: 500px; width: 600px; float:left;" ></div>
    
    <div class="header"><h3>BMI趋势图</h3></div>
    <div id="bmi" style="height: 500px; width: 600px; float:left;"></div>
  -->

</body>
</html>