<%@page import="java.net.InetAddress"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Song Player</title>
<script type="application/javascript">
var time=0;
var length=306;
var addr;
var ws;
function init()
{
addr=""+document.getElementById("localAddr").value.split("/")[1];
//alert("ws://"+addr+":8080/SecondServlet2/Radio");
ws=new WebSocket("ws://"+addr+":8080/SecondServlet2/Radio");
//alert("SENDING");
	ws.onopen = function(evt) 
	{
		//alert("OPEN");
	}; 
	ws.onmessage = function(evt) 
	{
//		alert(evt.data);

		if(evt.data.substring(0,5)=="TIME:")
		{
			document.getElementById("player").currentTime=parseFloat(evt.data.split(":")[1]);
		}
		if(evt.data=="TIME?")
		{
			ws.send("TIME:"+document.getElementById("player").currentTime);
		}
		if(evt.data.substring(0,5)=="PLAY:")
		{
			play(evt.data.split(":")[1]);
		}
		if(evt.data.substring(0,5)=="LIST:")
		{
//			alert(evt.data);
			document.getElementById("list").innerHTML="";
			var list=evt.data.split(":")[1].split("|");
			for(var i=0;i<list.length;i++)
				{
				document.getElementById("list").innerHTML+="<dt><div class=\"details\"><a href=\"javascript:void(0);\" onclick=\"change(this);\">"+list[i]+"</a></div><div class=\"division\" /></dt>";
				}
		}
		if(evt.data.substring(0,5)=="SONG:")
		{
			document.getElementById("list").innerHTML+="<dt><div class=\"details\"><a href=\"javascript:void(0);\" onclick=\"change(this);\">"+evt.data.split(":")[1]+"</a></div><div class=\"division\" /></dt>";
		}
		if(evt.data.substring(0,5)=="INFO:")
		{
			var infoArr=evt.data.split(":")[1].split("|");
			var ll=document.getElementById("currSongDetails");
			ll.innerHTML="<dl>";
			ll.innerHTML+="<dt><div class=\"details\"><a href=\"#\"><span class=\"option\"> Name: </span> <span class=\"value\">"+infoArr[0]+"</span>	</a> <div class=\"division\" />	</div></dt>"
			ll.innerHTML+="<dt><div class=\"details\"><a href=\"#\"><span class=\"option\"> Album: </span> <span class=\"value\">"+infoArr[1]+"</span>	</a> <div class=\"division\" />	</div></dt>"
			ll.innerHTML+="<dt><div class=\"details\"><a href=\"#\"><span class=\"option\"> Artist: </span> <span class=\"value\">"+infoArr[2]+"</span>	</a> <div class=\"division\" />	</div></dt>"
			ll.innerHTML+="</dl>";
			
		}
		if(evt.data.substring(0,6)=="Cancel")
		{
			var songList=evt.data;
			var songArr=songList.split("|");
			for(var i=0;i<songArr.length;i++)
			{
				song=songArr[i];
				document.getElementById("addList").innerHTML+="<dt><div class=\"details\"><a href=\"javascript:void(0);\" onclick=\"addToList(this);\">"+song+"</a></div><div class=\"division\" /></dt>";
			}
//			document.getElementById("addOption").setAttribute("style","display: none; ");
		}
	}
	ws.onclose = function(evt) 
	{ 
	};
	//alert(ws.readyState);
}
function addToList(val)
{
	song=val.innerHTML;
		if(song!="Cancel")
		{
			ws.send("SONG:"+song);
		}
	document.getElementById("add").setAttribute("style","display: visible; ");
	document.getElementById("addOption").setAttribute("style","display: none; ");
	document.getElementById("addList").innerHTML="";
}
function next()
{
	ws.send("NEXT");
}
function add(){
	ws.send("ADD");
	document.getElementById("add").setAttribute("style","display: none; ");
	document.getElementById("addOption").setAttribute("style","display: visible; ");
}
function change(elem){
	
	ws.send("PLAY:"+elem.innerHTML);
}

function play(elem){
	song=elem+".mp3";
	var play=document.getElementById("player");
	var str=document.getElementById("pathname").value;
	str=str+song;
	//alert(str);
	play.setAttribute('src',str);
	$("player")[0].play();
}
</script>
<style type="text/css">

a:LINK {
	text-decoration: none;
}

a:HOVER {
	text-decoration: none;
}

a:VISITED {
	font-family: tempus sans itc;
	font-size: 20px;
	font-style: italic;
}

#songImage {
	width: 20%;
	height: 50%;
	position: absolute;
	left: 5px;
	top: 5px;
	border-radius: 2px;
	border: 1px black dashed;
	box-shadow: 0px 10px 20px -2px black;
	background-color: white;
	text-align: center;
}

#currSongDetails {
	width: 45%;
	height: 150px;
	position: absolute;
	top: 5px;
	left: 22%;
	border: 1px black dashed;
	box-shadow: 0px 10px 20px -2px black;
	border-radius: 2px;
}

#playList {
	width: 30%;
	height: 90%;
	position: absolute;
	top: 5px;
	left: 69%;
	border: 1px black dashed;
	box-shadow: 0px 10px 20px -2px black;
	border-radius: 2px;
	overflow: auto
}

#add {
	position: absolute;
	top: 90.9%;
	left: 93%;
	width: 5%;
	height: 5%;
	border: 1px black dashed;
	box-shadow: 0px 10px 20px -2px black;
	text-align: center;
	border-bottom-left-radius: 10px;
	border-bottom-right-radius: 10px;
	border-top-left-radius: 0px;
	border-top-right-radius: 0px;
}

.option {
	padding-bottom: 20px;
}

* {
	margin: 0;
	padding: 0;
	font: CENTURY GOTHIC;
	color: #000
}

body {
	font: 100% normal Arial, Helvetica, sans-serif;
	background: aqua;/*#161712;*/
}

.canvas {
	min-width: 640px;
	min-height: 575px;
	width: 95%;
	height: 95%;
	position: absolute;
	top: 2%;
	left: 2%;
	background-color: white;/*#39343C;*/
	box-shadow: rgba(1, 1, 1, 1) 0px 0px 20px;
	-moz-box-shadow: rgba(1, 1, 1, 1) 0px 0px 20px;
	-webkit-box-shadow: rgba(1, 1, 1, 1) 0px 0px 20px;
	border-radius: 2px;
}

.details {
	width: 95%;
	height: 33%;
	position: relative;
	left: 2%;
	padding: 5px;
	border-radius: 10px;
}

.details:HOVER {
	text-decoration: none;
}

.division {
	border: thin;
	border-top-color: #3D2A17;
	border-bottom-color: #A05B5B;
	border-style: solid;
	width: 95%;
	position: relative;
	left: 2%;
}

#seekbar{
	position: absolute;
	top: 85%;
	left: 1%;
	width: 65%;
}

#seek{
	width: 100%;
}

#temp{
	position: absolute;
	top: 90%;
	left: 1%;
	width: 10%;
}

#addOption{
	position: absolute;
	top: 20%;
	left: 20%;
	width: 60%;
	height: 60%;
	background: white;
	border: 1px black dashed;
	box-shadow: 0px 10px 20px -2px black;
	border-radius: 2px;
	z-index: 2;
	overflow: auto
}

</style>
</head>
<body onload="init()">
<input type="hidden" value="<%=InetAddress.getLocalHost() %>" id="localAddr"/>
<input type="hidden" name="path" value="<%= request.getContextPath() %>/songs/" id="pathname"/>
	<div class="canvas">
		<div id="songImage">An Image Should Be Here</div>
		<div id="currSongDetails">
			<dl>
			</dl>
		</div>
		<div id="playList">
			<dl id="list">
			</dl>
		</div>
		<div id="add" onclick="add()">
			<a href="#"> add </a>
		</div>
		<div id="audioPlayerContainer">
			<audio id="player" src="" autoplay onended="next()">
			</audio>
		</div>		
		<div id="addOption" style="display: none;">
			<dl id="addList">
			</dl>
		</div>
			
	</div>
</body>
<script>
</html>
