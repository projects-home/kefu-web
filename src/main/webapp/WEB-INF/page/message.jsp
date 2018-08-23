<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/public/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>消息盒子</title>
<%@ include file="/WEB-INF/page/public/meta.jsp"%>
<style>
.layim-msgbox{margin: 15px;}
.layim-msgbox li{position: relative; margin-bottom: 10px; padding: 0 130px 10px 60px; padding-bottom: 10px; line-height: 22px; border-bottom: 1px dotted #e2e2e2;}
.layim-msgbox .layim-msgbox-tips{margin: 0; padding: 10px 0; border: none; text-align: center; color: #999;}
.layim-msgbox .layim-msgbox-system{padding: 0 10px 10px 10px;}
.layim-msgbox li p span{padding-left: 5px; color: #999;}
.layim-msgbox li p em{font-style: normal; color: #FF5722;}

.layim-msgbox-avatar{position: absolute; left: 0; top: 0; width: 50px; height: 50px;}
.layim-msgbox-user{padding-top: 5px;}
.layim-msgbox-content{margin-top: 3px;}
.layim-msgbox .layui-btn-small{padding: 0 15px; margin-left: 5px;}
.layim-msgbox-btn{position: absolute; right: 0; top: 12px; color: #999;}
</style>
</head>
<body>

<ul class="layim-msgbox" id="LAY_view">
  <c:forEach items="${msgList}" var="msg" >
  		<li class="layim-msgbox-system">
	      <p><em>系统：</em>${msg.content}<span>${msg.createdate.substring(0,10)}</span></p>
	    </li>
  </c:forEach> 
<li class="layim-msgbox-tips">暂无更多新消息</li> 
</ul> 
</body>
</html>
