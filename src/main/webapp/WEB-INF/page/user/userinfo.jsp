<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/public/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息表</title>
<%@ include file="/WEB-INF/page/public/meta.jsp"%>
</head>
<body>
  
 <div style="margin: 20px;">     
    <div class="layui-btn-group listTable">
	  <button class="layui-btn" data-type="getCheckData">获取多选内容</button> 
	</div>
	<table class="layui-table" lay-data="{ url:'/userinfo/list', method:'post',  page:true, id:'listTable'}" lay-filter="list">
	  <thead>
	    <tr>
	      <th lay-data="{type:'checkbox', fixed: 'left'}"></th>
	      							   <th lay-data="{field:'id'}"></th>
		    			 			   <th lay-data="{field:'uid'}">用户id</th>
		    			 			   <th lay-data="{field:'deptid'}">部门</th>
		    			 			   <th lay-data="{field:'name'}">姓名</th>
		    			 			   <th lay-data="{field:'nickname'}">昵称</th>
		    			 			   <th lay-data="{field:'sex'}">性别（0女 1男）</th>
		    			 			   <th lay-data="{field:'birthday'}">生日</th>
		    			 			   <th lay-data="{field:'cardid'}">身份证</th>
		    			 			   <th lay-data="{field:'signature'}">签名</th>
		    			 			   <th lay-data="{field:'school'}">毕业院校</th>
		    			 			   <th lay-data="{field:'education'}">学历</th>
		    			 			   <th lay-data="{field:'address'}">现居住地址</th>
		    			 			   <th lay-data="{field:'phone'}">联系电话</th>
		    			 			   <th lay-data="{field:'email'}">邮箱</th>
		    			 			   <th lay-data="{field:'remark'}">备注</th>
		    			 			   <th lay-data="{field:'profilephoto'}">个人头像</th>
		    			 			   <th lay-data="{field:'createdate'}">创建时间</th>
		    			 			   <th lay-data="{field:'createuser'}">创建人</th>
		    			 			   <th lay-data="{field:'updatedate'}">修改时间</th>
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
	 
 <script src="/js/generator/userinfo.js?<%=System.currentTimeMillis()%>"></script>  
</body>
</html>

 