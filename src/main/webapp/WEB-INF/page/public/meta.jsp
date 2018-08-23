<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
			+ path+"/";
%>

<link rel="stylesheet" href="<%=basePath%>layui/css/layui.css" media="all" />
<script src="<%=basePath%>layui/layui.js"></script>