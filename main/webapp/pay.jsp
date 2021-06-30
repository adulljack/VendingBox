<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<title>微信Native支付测试</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
	<script type="text/javascript">
var interval;//计时器

//生成微信支付二维码
// function wxPayTest(){
$(function() {
	$.ajax({
        url: '${ctx}/wx/pay/wxPayQrcode',
        type: 'post',
        cache: false,
        data: {
        },
        dataType: 'json',
        success: function(result) {
       	 if (result.code == 200) {
       		 //生成二维码
	       	 $("#code").qrcode({
	       		 render: "canvas", //table方式
	       	        width: 240, //宽度
	       	        height:240, //高度
	       	        text: result.object.code_url //任意内容
	       	 });
       		$("#orderNo").val(result.object.orderNo);
	       	clearInterval(interval);
	       	interval = setInterval(function(){
			    selectOrderState(result.object.orderNo);
			},2000);

       	 }else{
       		 alert("发生异常");
		 }
        },error: function() {
        	alert("请求服务器超时");
        }
	 });
})


//查询订单状态
function selectOrderState(orderNo){
	$.ajax({
        url: '${ctx}/wx/pay/wxPaySelectOrder',
        type: 'post',
        cache: false,
        data: {
        	"orderNo" :orderNo
        },
        dataType: 'json',
        success: function(result) {
	       	 if (result.code == 200) {
		       	clearInterval(interval);
		       	//alert("支付成功，跳转成功页面");
		        window.location = "${ctx}/success.jsp"
	       	 }else{
	       		console.log(result.msg);
	       	 }
        },error: function() {
        	console.log("请求服务器超时");
        }
	 });
}

</script>
</head>
<body>
<%--<button onclick="wxPayTest();">微信支付测试点击</button>--%>

<input type="text" id="orderNo" readonly="readonly" />
<span>温馨提示:支付完成后，稍等片刻</span>
<!-- <button onclick="seletOrderState();">查询订单接口测试点击</button> -->

<div align="center" style="margin-top: 100px;">
	<div id="code" style="width: 240px;height: 240px;margin: 0px auto;"></div>
</div>

</body>
</html>
