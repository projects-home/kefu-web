<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/public/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户帐号</title>
<%@ include file="/WEB-INF/page/public/meta.jsp"%>
</head>
<body>
  
 <div style="margin: 20px;">     
    <div class="layui-btn-group listTable">
	  <button class="layui-btn" data-type="getCheckData">获取多选内容</button> 
	</div>
	<table class="layui-table" lay-data="{ url:'/useraccount/list', method:'post',  page:true, id:'listTable'}" lay-filter="list">
	  <thead>
	    <tr>
	      <th lay-data="{type:'checkbox', fixed: 'left'}"></th>
	      							   <th lay-data="{field:'id'}"></th>
		    			 			   <th lay-data="{field:'account'}">帐号</th>
		    			 			   <th lay-data="{field:'password'}">密码</th>
		    			 			   <th lay-data="{field:'disablestate'}">禁用状态（0 启用  1 禁用）</th>
		    			 			   <th lay-data="{field:'isdel'}">是否删除（0未删除1已删除）</th>
		    			 			   <th lay-data="{field:'createdate'}">创建日期</th>
		    			 			   <th lay-data="{field:'updatedate'}">修改日期</th>
		    			 			   <th lay-data="{field:'updateuser'}">修改人</th>
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
	 
 <script src="/js/generator/useraccount.js?<%=System.currentTimeMillis()%>"></script>  
</body>
</html>

 