<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>js调用摄像头拍照上传图片</title>
    <meta charset="utf-8">
</head>
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/Getface.js"></script>
<style>
	.getface{
		position: absolute;
		top: 20%;
		left: 35%;
		
	}
	.tishi{
		font-size: 20px;
		
	}
</style>
<body>
<div align="center">
	<p id="flag" class="tishi"></p>
</div>
<div class="getface">
	<video id="video" width="400px" height="400px" autoplay="autoplay"></video>
	<canvas id="canvas" width="400px" height="400px" style="display: none;"></canvas>
	<img id="imgTag" src="" alt="imgTag" style="display: none;">
</div>

</body>