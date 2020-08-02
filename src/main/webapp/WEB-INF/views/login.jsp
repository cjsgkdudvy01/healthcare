<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="sweetpotato">
		<title>로그인</title>
		<!-- Bootstrap core CSS -->
		<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
		<!-- Custom -->
		<link href="css/login.css" rel="stylesheet"> 
	</head>
	<body class="text-center">
		<form class="form-signin" name="loginform" id="loginform" action="<c:url value='/myPage'/>" method="post">
		<img class="mb-4" src="img/dolphin-logo.svg" alt="" width="72" height="72">
		<h1 class="h3 mb-3 font-weight-normal">로그인</h1>
		<input type="text" id="loginInputId" class="form-control" placeholder="아이디" autofocus>
		<input type="password" id="loginInputPassword" class="form-control" placeholder="비밀번호" >
		<div class="checkbox mb-3">
			<label>
				<input type="checkbox" value="remember-me"> 아이디 기억하기
			</label>
		</div>
		<button class="btn btn-lg btn-primary btn-block" id="login_btn">로그인</button>
		<p class="mt-5 mb-3 text-muted">&copy; 2020-08-01</p>
		</form>
	</body>
	<!-- costum JavaScript -->
	<script src="js/login.js"></script>
</html>
