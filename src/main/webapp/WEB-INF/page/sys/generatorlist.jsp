<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/public/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据表管理</title>
<%@ include file="/WEB-INF/page/public/meta.jsp"%>
</head>
<body>

 <div style="margin: 20px;">     
    <div class="layui-btn-group listTable">
	  <button class="layui-btn" data-type="getCheckData">多选生成代码</button> 
	</div>
	<table class="layui-table" lay-data="{ url:'<%=basePath%>sys/generator/list', method:'post',  page:true, id:'listTable'}" lay-filter="list">
	  <thead>
	    <tr>
	      <th lay-data="{type:'checkbox', fixed: 'left'}"></th>
	      <th lay-data="{field:'tableName'}">表名</th>
	      <th lay-data="{field:'engine'  }">引擎</th>
	      <th lay-data="{field:'tableComment'}">描述</th>
	      <th lay-data="{field:'createTime'}">创建时间</th>
	      <th lay-data="{fixed: 'right', align:'center', toolbar: '#bar'}">操作</th>
	    </tr>
	  </thead>
	</table>
	<script type="text/html" id="bar">
 	 <a class="layui-btn layui-btn-xs" lay-event="buildFile" >生成代码</a>
	</script> 
</div>	
	
 <script>
layui.use('table', function(){
  var table = layui.table;
  //监听表格复选框选择
  table.on('checkbox(list)', function(obj){
    console.log(obj)
  });
  //监听工具条
  table.on('tool(list)', function(obj){
    var data = obj.data;
    if(obj.event === 'buildFile'){
    	window.open("<%=basePath%>sys/generator/code?tables="+data.tableName);
       //layer.msg('ID：'+ data.tableName + ' 的查看操作');
    } /* else if(obj.event === 'del'){
      layer.confirm('真的删除行么', function(index){
        obj.del();
        layer.close(index);
      });
    } else if(obj.event === 'edit'){
      layer.alert('编辑行：<br>'+ JSON.stringify(data))
    } */
  });
  
  var $ = layui.$, active = {
    getCheckData: function(){ //获取选中数据
      var checkStatus = table.checkStatus('listTable')
      ,data = checkStatus.data;
	   if(data.length<1){
	    	 layer.alert('请选择数据')
	   }else{
		   var tables ="";
		   for(var i=0;i<data.length;i++){
			   tables+="tables="+data[i].tableName+"&";
		   } 
		   window.open("<%=basePath%>sys/generator/code?"+tables);
	   } 
    } 
  };
  
  $('.listTable .layui-btn').on('click', function(){
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });
});
</script>
   
</body>
</html>