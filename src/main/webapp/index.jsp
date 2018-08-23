<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
  <link rel="stylesheet" href="layui/css/layui.css"> 
  <style type="text/css">
  	.index-button{margin-bottom: 30px;}
	.index-button div{margin: 20px 30px 10px;}
	.index-button .layui-btn+.layui-btn{margin-left: 0;}
	.index-button .layui-btn{margin: 0 7px 10px 0; }
  </style>
</head>
<body>
  <div style="margin: 30px;">
    <fieldset class="layui-elem-field index-button" style="margin-top: 30px;">
	  <legend>选择</legend>
	  <div>
	    <a class="layui-btn layui-btn-primary"  href="chat"><i class="layui-icon">&#xe612;</i>单聊</a>
	    <a class="layui-btn layui-btn-primary" href="groupchat"><i class="layui-icon">&#xe613;</i>群聊</a> 
	    <a class="layui-btn layui-btn-primary" href="bot"><i class="layui-icon">&#xe63a;</i>机器人</a>
	     <div class="layui-text" style="margin: 0; color: #FF5722;">
		      注意：自行测试请打开两个不同的浏览器
		 </div>
		 <div class="layui-text" style="margin-top:10px; color: #FF5722;">
		       <a href="helpdoc.doc" target="_blank" class="layui-btn  layui-btn-sm layui-bg-blue"><i class="layui-icon">&#xe705;</i>查看文档</a>
		 </div>
	  </div>
	</fieldset>
	
	 <fieldset class="layui-elem-field index-button" style="margin-top: 30px;">
	  <legend>LAYIM</legend>
	  <div>
	    <a class="layui-btn layui-btn-primary"  href="login.jsp" target="_blank"><i class="layui-icon">&#xe613;</i>登录去聊天</a>
	 	<div class="layui-text" style="margin: 0; color: #FF5722;">
		      结合了LayIm,实现了PC端及手机端访问
		  <p> 单聊，群聊，文件上传显示，聊天记录查询，上下线提醒
		  <p>注意：layim需要授权所以不便放入项目， 有layim的用户直接把layim放进layui文件夹下就可以愉快的使用了！
		</div>
	  </div>
	</fieldset>
	
	 <fieldset class="layui-elem-field index-button" style="margin-top: 30px;">
	  <legend>管理</legend>
	  <div>
	    <a class="layui-btn layui-btn-primary"  href="user/imuser/list" target="_blank"><i class="layui-icon">&#xe613;</i>在线用户管理</a>
	    <a class="layui-btn layui-btn-primary" href="usermessage/page" target="_blank"><i class="layui-icon">&#xe63a;</i>消息管理</a> 
	    <a class="layui-btn layui-btn-primary" href="sys/generator/page" target="_blank"><i class="layui-icon">&#xe635;</i>代码生成</a> 
	  </div>
	</fieldset>
  </div>
</body>
</html>