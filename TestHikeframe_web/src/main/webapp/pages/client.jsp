<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/base/pbase.jsp"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"/> 
<link href="${rootUrl}/components/bootstrap-3.3.5-dist/css/bootstrap.min.css"rel="stylesheet"></link>
<link href="${rootUrl}/components/bootstrap-3.3.5-dist/css/style.css"rel="stylesheet"></link>
<link href="${rootUrl}/components/font-awesome-4.5.0/css/font-awesome.min.css"rel="stylesheet"></link>
<link href="${rootUrl}/components/layer-v2.3/layer/skin/layer.css"rel="stylesheet"></link>
<title>绍兴公交客户端</title>
</head>

<body style="background-color:#177ebc;">
	<div style="height:60px;margin:15px 15px 15px 15px;background-image:url(../images/logo.png);background-repeat:no-repeat;border-bottom:2px solid #288FD4;"></div>
	<div style="margin:50px 10px 0px 20px">
	<div>
		<span style="color:white">安卓客户端 :</span><a href="" id="androidid" style="color:white">点击此处下载</a>
	</div>
	
	<div  style="margin-top:50px">
		<span style="color:white">IOS客户端 :</span><a href="" id="iosid" style="color:white" onclick="javascript:alert('暂无开发');">点击此处下载</a>
	</div>
	</div>
	<script src="${rootUrl}/components/jquery/jquery-2.2.3.min.js"></script>
	<script
		src="${rootUrl}/components/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	<script src="${rootUrl}/components/layer-v2.3/layer/layer.js"></script>
	</script>
		<script type="text/javascript">
		$.ajax({
			type : "GET", //提交方式  
			url : rootUrl + '/mobile/getupdateinfo',//路径  
			async : false,
			data:{
				clienttype : 'android'
			},
			success : function(result) {//返回数据根据结果进行相应的处理  
				var obj = eval('(' + result + ')');
				$("#androidid").attr("href",obj.data[0].URL);
			}
		});
		</script>
	</script>
</body>
</html>
