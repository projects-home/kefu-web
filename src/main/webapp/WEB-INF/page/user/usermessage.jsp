<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/public/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/page/public/meta.jsp"%>
</head>
<body>
  
 <div style="margin: 20px;">     
    <div class="layui-btn-group listTable">
	  <button class="layui-btn" data-type="getCheckData">获取多选内容</button> 
	</div>
	<table class="layui-table" lay-data="{ url:'<%=basePath%>usermessage/list', method:'post',  page:true, id:'listTable'}" lay-filter="list">
	  <thead>
	    <tr>
	      <th lay-data="{type:'checkbox', fixed: 'left'}"></th>
	      							   <th lay-data="{field:'id'}"></th>
		    			 			   <th lay-data="{field:'senduser'}">发送人</th>
		    			 			   <th lay-data="{field:'receiveuser'}">接收人</th>
		    			 			   <th lay-data="{field:'groupid'}">群ID</th>
		    			 			   <th lay-data="{field:'isread'}">是否已读 0 未读  1 已读</th>
		    			 			   <th lay-data="{field:'type'}">类型 0 单聊消息  1 群消息</th>
		    			 			   <th lay-data="{field:'content'}">消息内容</th>
		    			 			   <th lay-data="{field:'createuser'}"></th>
		    			 			   <th lay-data="{field:'createdate'}"></th>
		    			 			   <th lay-data="{field:'updatedate'}"></th>
		    	      <th lay-data="{fixed: 'right', align:'center', toolbar: '#bar'}">操作</th>
	    </tr>
	  </thead>
	</table>
	<script type="text/html" id="bar">
 	  <a class="layui-btn layui-btn-xs" lay-event="show" >查看</a>
 	  <a class="layui-btn layui-btn-xs" lay-event="edit" >修改</a>
 	  <a class="layui-btn layui-btn-xs" lay-event="del" >删除</a>
	</script> 
</div>	
	 
 <script src="<%=basePath%>js/generator/usermessage.js?<%=System.currentTimeMillis()%>"></script>  
</body>
</html>

 