<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>货柜管理系统 | Log in</title>

<meta
	content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no"
	name="viewport">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/ionicons/css/ionicons.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/adminLTE/css/AdminLTE.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/iCheck/square/blue.css">
</head>

<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<a href="all-admin-index.html">7货柜管理系统</a>
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<p class="login-box-msg">登录系统</p>

			<form action="${pageContext.request.contextPath}/user/login" method="post" onsubmit="return checkForm(this)">
				<div class="form-group has-feedback">
					<input type="text" name="username" class="form-control"
						placeholder="用户名"> <span
						class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" name="password" class="form-control"
						placeholder="密码">
						<span style="color: red">${info} </span>
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<div class="checkbox icheck">
							<label><input type="checkbox"> 记住 下次自动登录</label>
						</div>
					</div>
					<!-- /.col -->
					<div class="col-xs-4">
						<button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
					</div>
					<!-- /.col -->
				</div>
			</form>

			<form class="form-signin"  action="/login/jumpGetface">
				<!--			<img class="mb-4" src="img/bootstrap-solid.svg" alt="" width="72" height="72">-->
				<!--			<h1 class="h3 mb-3 font-weight-normal">系统登录</h1>-->
				<!--			<label class="sr-only">Username</label>-->
				<!--			<input type="text" name="userID" id="uid" class="form-control" placeholder="userid" required="" autofocus="">-->
				<!--			<label class="sr-only">Password</label>-->
				<!--			<input type="password" name="password" id="password" class="form-control" placeholder="Password" required="">-->
				<!--			<span  class="form-control"  onclick="login()">登录</span>-->
				<!--			<div class="checkbox mb-3">-->
				<!--				<label><input type="checkbox" value="remember-me"> Remember me</label>-->
				<!--			</div>-->
				<button class="btn btn-lg btn-primary btn-block" id="faceloginpage" type="submit">刷脸登录</button>
			</form>
		</div>
		<!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->

	<!-- jQuery 2.2.3 -->
	<!-- Bootstrap 3.3.6 -->
	<!-- iCheck -->
	<script
		src="${pageContext.request.contextPath}/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/iCheck/icheck.min.js"></script>
	<script>
		$(function() {
			$('input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' // optional
			});
		});
	</script>

	<script type="text/javascript">
		// 验证输入不为空的脚本代码
		function checkForm(form) {
			if(form.username.value == "") {
				alert("用户名不能为空!");
				form.username.focus();
				return false;
			}
			if(form.password.value == "") {
				alert("密码不能为空!");
				form.password.focus();
				return false;
			}
			return true;
		}
	</script>
</body>

</html>





