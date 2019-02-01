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
	用户名：<input id="userName" type="text" name="userName" value="admin" />
	密码：<input id="password" type="password" name="password" value="123" />
	<button type="button" id="commit" onclick="login()">提交</button>
<br/>
<p id="msg" style="color: red;"></p>
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
			url:"http://localhost:8080/jsp/doLogin",
			type:"post",
			dataType:"json",
			data:JSON.stringify(body),
			success:function(res){
				if (res.value.code === 1){
					window.location.href = "http://localhost:8080/jsp/index"
				} else {
					$("#msg").text(res.value.msg)
				}
			},
			error:function(res){
				console.info("error！" + res)
			}
		})
	}
</script>

</html>