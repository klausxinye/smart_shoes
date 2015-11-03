window.onload = init;
var name="container";
var container1="体重"
var container2="步行时间"
var scale2="步行时间/分"
var url = "getrtdata.php"
function init () {
    getinfo();
    container2="步行时间";
	  scale2="步行时间/分";
    setTimeout(getwtdata(),500);
}
function init1 () {
	  container2="跑步时间";
	  scale2="跑步时间/分";
    setTimeout(getrtdata(),500);
}
function init2 () {
	  container2="步行步数";
	  scale2="步行步数/步";
    setTimeout(getwsdata(),500);
}
function init3 () {
	  container2="跑步步数";
	  scale2="跑步步数/步";
    setTimeout(getrsdata(),500);
}

function getwtdata () {
	//请求服务器
	var xhr = false;
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();	//for IE7+,Firefox,Chrome
	} else{
		xhr = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
	}

    //var url = "getrtdata.php";
    //var url = "http://192.168.102.152/scale_online_arduino/getdata.php";
    //var url = "http://127.0.0.1/scale_online_arduino/getdata.php";
	xhr.open("GET",url, true);
	xhr.onreadystatechange = function () {
	    if (xhr.readyState == 4 && xhr.status == 200) {
	        var response = eval("(" + xhr.responseText + ")");
	        var serial_weight = new Array();
            var serial_exercise = new Array();
	       // var height = parseInt(document.getElementById('add').height.value);
          //  height/=100;
             //var class = "data"
            for(var i = 0; i<response.length; i++) {
                //var time = Date.parse(response[i].time);
                //serial[i] = [parseInt(time), parseFloat(response[i].data
                serial_weight[i] = [parseInt(response[i].date), parseFloat(response[i].weight)];
                serial_exercise[i] = [parseInt(response[i].date), parseFloat(response[i].walktime)];
            }

	        //document.getElementById('records').innerHTML = height;

            $(function () { 
                // Create the chart
                window.chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'weightscale'
                    },

                    rangeSelector: {
                        selected: 0
                    },

                    title: {
                        text: container1
                    },

                    xAxis: {
                    tickInterval:1,
                    title:{
	                    		text:'日期'
	                    		}
                    }, 
                    
                    yAxis: {
                        title:{
	                    		text:container1
	                    		}
	                    	
                    }, 

                    scrollbar: {
                        barBackgroundColor: 'gray',
                        barBorderRadius: 7,
                        barBorderWidth: 0,
                        buttonBackgroundColor: 'gray',
                        buttonBorderWidth: 0,
                        buttonBorderRadius: 7,
                        trackBackgroundColor: 'none',
                        trackBorderWidth: 1,
                        trackBorderRadius: 8,
                        trackBorderColor: '#CCC'
                    },

                    series: [{
                        name: '体重值',
                        data: serial_weight,
                       // marker: {
                       //     enabled: true,
                       //     radius: 3
                       // },
                        shadow: true,
                       // tooltip: {
                       //     valueDecimals: 1
                       // }
                    }]
                });
        });
        
        $(function () { 
                // Create the chart
                window.chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'exercisescale'
                    },

                    rangeSelector: {
                        selected: 0
                    },

                    title: {
                        text: container2
                    },

                    xAxis: {
                        tickInterval:1,
                        title: {
                        text: '日期'
                    }
                    }, 

                    yAxis: {
                        title:{
	                    		text:scale2
	                    		}
                       
                    }, 

                    scrollbar: {
                        barBackgroundColor: 'gray',
                        barBorderRadius: 7,
                        barBorderWidth: 0,
                        buttonBackgroundColor: 'gray',
                        buttonBorderWidth: 0,
                        buttonBorderRadius: 7,
                        trackBackgroundColor: 'none',
                        trackBorderWidth: 1,
                        trackBorderRadius: 8,
                        trackBorderColor: '#CCC'
                    },

                    series: [{
                        name: scale2,
                        data: serial_exercise,
                        marker: {
                            enabled: true,
                            radius: 3
                        },
                        shadow: true,
                        tooltip: {
                            valueDecimals: 1
                        }
                    }]
                });
        });

	    }
	}
	xhr.send();
	return false;
}
function getrtdata () {
	//请求服务器
	var xhr = false;
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();	//for IE7+,Firefox,Chrome
	} else{
		xhr = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
	}

    //var url = "getrtdata.php";
    //var url = "http://192.168.102.152/scale_online_arduino/getdata.php";
    //var url = "http://127.0.0.1/scale_online_arduino/getdata.php";
	xhr.open("GET",url, true);
	xhr.onreadystatechange = function () {
	    if (xhr.readyState == 4 && xhr.status == 200) {
	        var response = eval("(" + xhr.responseText + ")");
	        var serial_weight = new Array();
            var serial_exercise = new Array();
	       // var height = parseInt(document.getElementById('add').height.value);
          //  height/=100;
             //var class = "data"
            for(var i = 0; i<response.length; i++) {
                //var time = Date.parse(response[i].time);
                //serial[i] = [parseInt(time), parseFloat(response[i].data
                serial_weight[i] = [parseInt(response[i].date), parseFloat(response[i].weight)];
                serial_exercise[i] = [parseInt(response[i].date), parseFloat(response[i].runtime)];
            }

	        //document.getElementById('records').innerHTML = height;

            $(function () { 
                // Create the chart
                window.chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'weightscale'
                    },

                    rangeSelector: {
                        selected: 0
                    },

                    title: {
                        text: container1
                    },

                    xAxis: {
                    tickInterval:1,
                    title:{
	                    		text:'日期'
	                    		}
                    }, 
                    
                    yAxis: {
                        title:{
	                    		text:container1
	                    		}
	                    	
                    }, 

                    scrollbar: {
                        barBackgroundColor: 'gray',
                        barBorderRadius: 7,
                        barBorderWidth: 0,
                        buttonBackgroundColor: 'gray',
                        buttonBorderWidth: 0,
                        buttonBorderRadius: 7,
                        trackBackgroundColor: 'none',
                        trackBorderWidth: 1,
                        trackBorderRadius: 8,
                        trackBorderColor: '#CCC'
                    },

                    series: [{
                        name: '体重值',
                        data: serial_weight,
                       // marker: {
                       //     enabled: true,
                       //     radius: 3
                       // },
                        shadow: true,
                       // tooltip: {
                       //     valueDecimals: 1
                       // }
                    }]
                });
        });
        
        $(function () { 
                // Create the chart
                window.chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'exercisescale'
                    },

                    rangeSelector: {
                        selected: 0
                    },

                    title: {
                        text: container2
                    },

                    xAxis: {
                        tickInterval:1,
                        title: {
                        text: '日期'
                    }
                    }, 

                    yAxis: {
                        title:{
	                    		text:scale2
	                    		}
                       
                    }, 

                    scrollbar: {
                        barBackgroundColor: 'gray',
                        barBorderRadius: 7,
                        barBorderWidth: 0,
                        buttonBackgroundColor: 'gray',
                        buttonBorderWidth: 0,
                        buttonBorderRadius: 7,
                        trackBackgroundColor: 'none',
                        trackBorderWidth: 1,
                        trackBorderRadius: 8,
                        trackBorderColor: '#CCC'
                    },

                    series: [{
                        name: scale2,
                        data: serial_exercise,
                        marker: {
                            enabled: true,
                            radius: 3
                        },
                        shadow: true,
                        tooltip: {
                            valueDecimals: 1
                        }
                    }]
                });
        });

	    }
	}
	xhr.send();
	return false;
}
function getwsdata () {
	//请求服务器
	var xhr = false;
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();	//for IE7+,Firefox,Chrome
	} else{
		xhr = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
	}

    //var url = "getrtdata.php";
    //var url = "http://192.168.102.152/scale_online_arduino/getdata.php";
    //var url = "http://127.0.0.1/scale_online_arduino/getdata.php";
	xhr.open("GET",url, true);
	xhr.onreadystatechange = function () {
	    if (xhr.readyState == 4 && xhr.status == 200) {
	        var response = eval("(" + xhr.responseText + ")");
	        var serial_weight = new Array();
            var serial_exercise = new Array();
	       // var height = parseInt(document.getElementById('add').height.value);
          //  height/=100;
             //var class = "data"
            for(var i = 0; i<response.length; i++) {
                //var time = Date.parse(response[i].time);
                //serial[i] = [parseInt(time), parseFloat(response[i].data
                serial_weight[i] = [parseInt(response[i].date), parseFloat(response[i].weight)];
                serial_exercise[i] = [parseInt(response[i].date), parseFloat(response[i].walkstep)];
            }

	        //document.getElementById('records').innerHTML = height;

            $(function () { 
                // Create the chart
                window.chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'weightscale'
                    },

                    rangeSelector: {
                        selected: 0
                    },

                    title: {
                        text: container1
                    },

                    xAxis: {
                    tickInterval:1,
                    title:{
	                    		text:'日期'
	                    		}
                    }, 
                    
                    yAxis: {
                        title:{
	                    		text:container1
	                    		}
	                    	
                    }, 

                    scrollbar: {
                        barBackgroundColor: 'gray',
                        barBorderRadius: 7,
                        barBorderWidth: 0,
                        buttonBackgroundColor: 'gray',
                        buttonBorderWidth: 0,
                        buttonBorderRadius: 7,
                        trackBackgroundColor: 'none',
                        trackBorderWidth: 1,
                        trackBorderRadius: 8,
                        trackBorderColor: '#CCC'
                    },

                    series: [{
                        name: '体重值',
                        data: serial_weight,
                       // marker: {
                       //     enabled: true,
                       //     radius: 3
                       // },
                        shadow: true,
                       // tooltip: {
                       //     valueDecimals: 1
                       // }
                    }]
                });
        });
        
        $(function () { 
                // Create the chart
                window.chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'exercisescale'
                    },

                    rangeSelector: {
                        selected: 0
                    },

                    title: {
                        text: container2
                    },

                    xAxis: {
                        tickInterval:1,
                        title: {
                        text: '日期'
                    }
                    }, 

                    yAxis: {
                        title:{
	                    		text:scale2
	                    		}
                       
                    }, 

                    scrollbar: {
                        barBackgroundColor: 'gray',
                        barBorderRadius: 7,
                        barBorderWidth: 0,
                        buttonBackgroundColor: 'gray',
                        buttonBorderWidth: 0,
                        buttonBorderRadius: 7,
                        trackBackgroundColor: 'none',
                        trackBorderWidth: 1,
                        trackBorderRadius: 8,
                        trackBorderColor: '#CCC'
                    },

                    series: [{
                        name: scale2,
                        data: serial_exercise,
                        marker: {
                            enabled: true,
                            radius: 3
                        },
                        shadow: true,
                        tooltip: {
                            valueDecimals: 1
                        }
                    }]
                });
        });

	    }
	}
	xhr.send();
	return false;
}
function getrsdata () {
	//请求服务器
	var xhr = false;
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();	//for IE7+,Firefox,Chrome
	} else{
		xhr = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
	}

    //var url = "getrtdata.php";
    //var url = "http://192.168.102.152/scale_online_arduino/getdata.php";
    //var url = "http://127.0.0.1/scale_online_arduino/getdata.php";
	xhr.open("GET",url, true);
	xhr.onreadystatechange = function () {
	    if (xhr.readyState == 4 && xhr.status == 200) {
	        var response = eval("(" + xhr.responseText + ")");
	        var serial_weight = new Array();
            var serial_exercise = new Array();
	       // var height = parseInt(document.getElementById('add').height.value);
          //  height/=100;
             //var class = "data"
            for(var i = 0; i<response.length; i++) {
                //var time = Date.parse(response[i].time);
                //serial[i] = [parseInt(time), parseFloat(response[i].data
                serial_weight[i] = [parseInt(response[i].date), parseFloat(response[i].weight)];
                serial_exercise[i] = [parseInt(response[i].date), parseFloat(response[i].runstep)];
            }

	        //document.getElementById('records').innerHTML = height;

            $(function () { 
                // Create the chart
                window.chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'weightscale'
                    },

                    rangeSelector: {
                        selected: 0
                    },

                    title: {
                        text: container1
                    },

                    xAxis: {
                    tickInterval:1,
                    title:{
	                    		text:'日期'
	                    		}
                    }, 
                    
                    yAxis: {
                        title:{
	                    		text:container1
	                    		}
	                    	
                    }, 

                    scrollbar: {
                        barBackgroundColor: 'gray',
                        barBorderRadius: 7,
                        barBorderWidth: 0,
                        buttonBackgroundColor: 'gray',
                        buttonBorderWidth: 0,
                        buttonBorderRadius: 7,
                        trackBackgroundColor: 'none',
                        trackBorderWidth: 1,
                        trackBorderRadius: 8,
                        trackBorderColor: '#CCC'
                    },

                    series: [{
                        name: '体重值',
                        data: serial_weight,
                       // marker: {
                       //     enabled: true,
                       //     radius: 3
                       // },
                        shadow: true,
                       // tooltip: {
                       //     valueDecimals: 1
                       // }
                    }]
                });
        });
        
        $(function () { 
                // Create the chart
                window.chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'exercisescale'
                    },

                    rangeSelector: {
                        selected: 0
                    },

                    title: {
                        text: container2
                    },

                    xAxis: {
                        tickInterval:1,
                        title: {
                        text: '日期'
                    }
                    }, 

                    yAxis: {
                        title:{
	                    		text:scale2
	                    		}
                       
                    }, 

                    scrollbar: {
                        barBackgroundColor: 'gray',
                        barBorderRadius: 7,
                        barBorderWidth: 0,
                        buttonBackgroundColor: 'gray',
                        buttonBorderWidth: 0,
                        buttonBorderRadius: 7,
                        trackBackgroundColor: 'none',
                        trackBorderWidth: 1,
                        trackBorderRadius: 8,
                        trackBorderColor: '#CCC'
                    },

                    series: [{
                        name: scale2,
                        data: serial_exercise,
                        marker: {
                            enabled: true,
                            radius: 3
                        },
                        shadow: true,
                        tooltip: {
                            valueDecimals: 1
                        }
                    }]
                });
        });

	    }
	}
	xhr.send();
	return false;
}
function getinfo () {
	//请求服务器
	var xhr = false;
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();	//for IE7+,Firefox,Chrome
	} else{
		xhr = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
	}

    //var url = "getinfo.php";
    //var url = "http://192.168.102.152/scale_online_arduino/getinfo.php";
    //var url = "http://127.0.0.1/scale_online_arduino/getinfo.php";
	xhr.open("GET",url, true);
	xhr.onreadystatechange = function () {
	    if (xhr.readyState == 4 && xhr.status == 200) {
	        var response = eval("(" + xhr.responseText + ")");
	        document.getElementById('username').innerHTML = response[0].name;
          document.getElementById('userweight').innerHTML = response[response.length-1].weight;
           //document.getElementById('add').height.value = parseInt(response[0].data);
        }
    }
	xhr.send();
	return false;
}