<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/public/taglib.jsp"%>
<!doctype html>
<html>
<head>
<title>用户管理</title>
<%@ include file="/WEB-INF/page/public/meta.jsp"%>
</head>
<body>
 <div style="margin: 20px;">     
    <div class="demoTable">
	  <button class="layui-btn" id="broadcast"  >广播消息</button>
	  <button class="layui-btn offline"   data="">全部下线</button>
	</div> 
    <table class="layui-table" lay-size="lg">
	  <colgroup>
	    <col width="150">
	    <col width="200">
	    <col>
	  </colgroup>
	  <thead>
	    <tr>
	      <th>唯一标识符</th>
	      <th>加入时间</th>
	      <th>签名</th>
	      <th>客户端类型</th>
	      <th>客户端版本</th>
	      <th>操作</th>
	    </tr> 
	  </thead>
	  <tbody>
	    <c:forEach  items="${allsession}"  var="user">
    		 <tr>
		      <td>${user.account}</td>
		      <td>${user.bindTime}</td>
		      <td>${user.sign}</td>
		      <td>${user.platform}</td>
		      <td>${user.platformVersion}</td>
		      <td> 
		      	<a class="layui-btn layui-btn-primary layui-btn-xs sendUserMsg"   data="${user.account}">消息</a>
  				<a class="layui-btn layui-btn-primary layui-btn-xs offline"    data="${user.account}">下线</a>
  			  </td>
		    </tr>
    
    	</c:forEach>
	  </tbody>
	</table>
</div>	
	
  
	
<script>
	//一般直接写在一个js文件中
	layui.use(['layer', 'jquery'], function(){
	  var layer = layui.layer
	  ,$ = layui.jquery;  
	  
	  //广播消息
	  $("#broadcast").on("click",function(){
		  layer.prompt({title: '广播消息', formType: 2}, function(text, index){
			    layer.close(index); 
				$.ajax({
					type : "post",
					url : "<%=basePath%>user/imuser/msgbroadcast",
					data : {msgContent:text},
					dataType : "json",
					async : false,
					success : function(data){
						layer.msg("广播已推送");
					}
				}); 
			    
		   }); 
	  })
	  
	  //单用户发送消息
	  $(".sendUserMsg").on("click",function(){
		  var usersession =$(this).attr("data");
		  layer.prompt({title: '发送消息', formType: 2}, function(text, index){
			  layer.close(index);
			   $.ajax({
					type : "post",
					url : "<%=basePath%>user/imuser/msgbroadcast",
					data : {msgContent:text,session:usersession},
					dataType : "json",
					async : false,
					success : function(data){
						layer.msg("消息已推送");
					}
			   }); 
		  });
	  })
	  
	  $(".offline").on("click",function(){
		  var usersession =$(this).attr("data");
		  layer.prompt({title: '下线', formType: 2}, function(text, index){
			  layer.close(index);
			   $.ajax({
					type : "post",
					url : "<%=basePath%>user/imuser/offline",
					data : {msgContent:text,session:usersession},
					dataType : "json",
					async : false,
					success : function(data){
						layer.msg("用户已下线");
					}
			   }); 
		  });
	  })
	  
	})
 
</script>	
</body>
</html>