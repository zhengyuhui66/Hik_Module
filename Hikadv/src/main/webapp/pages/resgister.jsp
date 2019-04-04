<%@ page language="java" contentType="text/html;charset=utf-8"  
    pageEncoding="UTF-8"%> 
<%@ include file="/pages/base/base.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>欢迎</title>  
</head>  
<body>  
<h2>Hello World!</h2>  
 
<form action="login">  
    用户名：<input id="username" name="username" type="text"></input><br>  
    密  码：<input id="password" name="password" type="password"></input><br>  
    <input type="submit">  
</form>  
<span>当前IP：<%=request.getRemoteAddr() %></span>
<input type=button name="check" id="check" value="check">
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
			url : rootUrl+"/deleteId",
			dataType : "json",
			data : {
    			username:username,
    			password:password,
			},
			success : function(data) {
				console.info(data);
			},
			error : function(request, textStatus, errorThrown) {
			}
		});
	 
//  	$("#userName").html($("#username").val());
//  	$("#passWord").html($("#password").val());		
 }
</script> 

</html>  