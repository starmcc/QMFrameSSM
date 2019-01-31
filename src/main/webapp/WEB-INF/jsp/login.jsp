<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>QM_SSMFrame</title>
</head>
<body>
	用户名：<input id="userName" type="text" name="userName" />
	密码：<input id="password" type="password" name="password" />
	<button type="button" id="commit" onclick="login()">提交</button>
</body>
<script src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script>
	function login(){
		console.info("正在进行登录ing")
		var userName = document.getElementById("userName").value
		var password = document.getElementById("password").value
		var body = {
			"value":{
				"userName":userName,
				"password":password
			}
		}

		$.ajax({
			url:"http://localhost:8080/QMFrame/api/login",
			type:"post",
			dataType:"json",
			data:JSON.stringify(body),
			success:function(res){
				console.info(res)
			},
			error:function(res){
				console.info("error！" + res)
			}
		})
	}
</script>

</html>