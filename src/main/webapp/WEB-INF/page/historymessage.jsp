<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/public/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>聊天记录</title>

<%@ include file="/WEB-INF/page/public/meta.jsp"%>
<style>
body .layim-chat-main{height: auto;}
</style>

</head>
<body>


<div class="layim-chat-main">
  <ul id="LAY_view">
     
  </ul>
</div>

<div id="LAY_page" style="margin: 0 10px;"></div>

<!-- parent.layui.layim.cache().mine.id -->
 
<textarea title="消息模版" id="LAY_tpl" style="display:none;">
{{# layui.each(d.data, function(index, item){
  if(item.senduser == ${sessionScope.user.id}){ }}
    <li class="layim-chat-mine"><div class="layim-chat-user"><img src="{{ item.avatar }}"><cite><i>{{ layui.data.date(item.createdate) }}</i>{{ item.sendusername }}</cite></div><div class="layim-chat-text">{{ layui.layim.content(item.content) }}</div></li>
  {{# } else { }}
    <li><div class="layim-chat-user"><img src="{{ item.avatar }}"><cite>{{ item.sendusername }}<i>{{ layui.data.date(item.createdate) }}</i></cite></div><div class="layim-chat-text">{{ layui.layim.content(item.content) }}</div></li>
  {{# }
}); }}
</textarea>
 
<script>
layui.use(['layim', 'laypage'], function(){
  var layim = layui.layim
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,$ = layui.jquery
  ,laypage = layui.laypage
  ,param =  location.search ;
 
  $.ajax({
		type : "post",
		url : "historymessageajax"+param+"&skipToPage=1",
		async:false,
		dataType : "json",
		success : function(data){
			result = data;
			var html = laytpl(LAY_tpl.value).render({
				    data: result.data
			});
			$('#LAY_view').html(html);
		}
	});  
 
//分页按钮
  	laypage.render({
  	  elem: 'LAY_page',
      curr:  '${pager.pageNo}'
      ,count:'${pager.totalSize}' //数据总数
      ,
      groups: 3 //连续显示分页数
      ,
      skip: true,
      jump: function(obj, first) {
          //得到了当前页，用于向服务端请求对应数据
          var curr = obj.curr;
          if(!first) {
        	  var  vl = param+"&skipToPage="+curr;
        	  $.ajax({
        			type : "post",
        			url : "historymessageajax"+vl,
        			async:false,
        			dataType : "json",
        			success : function(data){
        				result = data; 
        				 var html = laytpl(LAY_tpl.value).render({
        					    data: result.data
        				 });
        				 $('#LAY_view').html(html);
        			}
        		});  
        	   
          }
      }
  });  
  
  
  
});
</script>

  
</body>
</html>
