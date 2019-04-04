<%@ page language="java" contentType="text/html;charset=utf-8"  
    pageEncoding="UTF-8"%> 
<%@ include file="/pages/base/base.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">  
<title>欢迎</title>  
</head>  
<body>  
<h2>Hello World!</h2>  
 
<form action="pushMessage">  
    时间长：<input id="username" name="username" type="text"></input><br>  
    内容：<input id="password" name="password" type="text"></input><br>  
    <input type="submit">  
</form>
<span>当前IP：<%=request.getRemoteAddr() %></span>
<input type=button name="推送消息" id="check" value="推送消息">
<div>
<span id="userName"></span>
<span id="passWord"></span>
</div>  
</body>  

<script type="text/javascript">
 $("#check").on("click",function(){
 	getValue();
 });	
 var getValue=function(){
	 var username=$("#username").val();
	 var password=$("#password").val()
		$.ajax({
			type : "post",
			async : true, 
			url : rootUrl+"/pushMessage",
			dataType : "json",
			data : {
    			time:username,
    			alert:password,
			},
			success : function(data) {
				if(data){
						alert(data.responseInfo);
				}else{
					alert("消息推送没有回应消息");
				}
			},
			error : function(request, textStatus, errorThrown) {
			}
		});		
 }
</script> 

</html>  