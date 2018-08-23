<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, height=device-height, user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=no">
<title>聊天</title>
<link rel="stylesheet" href="layui/css/layui.mobile.css" media="all" />
<script type="text/javascript" src="js/util.js"></script>  
<script type="text/javascript" src="js/message.js"></script>  
<script type="text/javascript" src="js/messagebody.js"></script>  
 
</head>
<body>




<script src="layui/layui.js"></script>
<script src="js/websocketconfig.js"></script>
<script>
var msgs =null;
var lm = null;
 
layui.config({
	  version: true
	}).use(['jquery','mobile' ], function(){
	    var mobile = layui.mobile
         ,layim = mobile.layim
         ,$ = layui.jquery 
         ,laytpl = layui.laytpl
         ,layer = mobile.layer;
		 var currentsession= "${sessionScope.user.id}";
	     data = null; 
	      
		  //发送消息
	      var sendMsg=function(msg,receiver,group){ 
	    	  var message = new proto.Model(); 
	      	  var content = new proto.MessageBody();
	           message.setMsgtype(4);
	           message.setCmd(5);
	           message.setGroupid(group);//系统用户组
	           message.setToken(currentsession);  
	           message.setSender(currentsession);
	           message.setReceiver(receiver);//好友ID
	           content.setContent(msg);
	           content.setType(0)
	           message.setContent(content.serializeBinary())
	           socket.send(message.serializeBinary()); 
		  }
		  
	 	 //拉取离线消息
	      var showOfflineMsg = function (layim){
	     	 $.ajax({
	 			  type : "post",
	 			  url : "getofflinemsg",
	 			  async : true,
	 			  success : function(data){ 
	 				  var dataObj=eval("("+data+")");
	 			      if(dataObj!=null&&dataObj.length>0){
	 			    	  for(var i =0;i<dataObj.length;i++){
	 			    		  layim.getMessage({
	 					 	        username: dataObj[i].sendusername
	 					 	        ,avatar: dataObj[i].avatar+"?"+new Date().getTime()
	 					 	        ,id: dataObj[i].senduser
	 					 	        ,type: "friend"
	 					 	        ,content: dataObj[i].content
	 					 	        ,timestamp: dataObj[i].createdate
	 				 	       }); 
	 			    	  }   
	 				  } 
	 			  }
	 		  }); 
	      }

	     
	     
	     $.get('getusers',  function(str){
	    	var json =eval("("+str+")")
		    data = json.data;
	    	  //基础配置
	  	  layim.config({
	  		  init: data, 
	  	    title:"我的IM"
	  	    ,notice:true
	  	    //获取群员接口（返回的数据格式见下文）
	  	    ,members: {
	  	      url: '' //接口地址（返回的数据格式见下文）
	  	      ,type: 'get' //默认get，一般可不填
	  	      ,data: {} //额外参数
	  	    }, 
	  	    uploadImage: {
		      url: 'imgupload' //接口地址
		      ,type: 'post' //默认post
		    }, 
		    //上传文件接口（返回的数据格式见下文）
		    uploadFile: {
		      url: 'fileupload' //接口地址
		      ,type: 'post' //默认post
		    },  
	  	    brief:false,
	  		min:true,
	  		isNewFriend:false,
	  		isgroup:true,
	  		voice:true,
	  		copyright:true
	  	   ,msgbox: 'message' //消息盒子页面地址，若不开启，剔除该项即可
	  	    //,find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
	  	    ,chatLog: 'historymessage' //聊天记录页面地址，若不开启，剔除该项即可
	  	  });
		});
	   
	    //查看聊天信息
	    layim.on('detail', function(data){
	      //console.log(data); //获取当前会话对象
	      layim.panel({
	        title: data.name + ' 聊天信息' //标题
	        ,tpl: '<div style="padding: 10px;">自定义模版，<a href="http://www.layui.com/doc/modules/layim_mobile.html#ondetail" target="_blank">参考文档</a></div>' //模版
	        ,data: { //数据
	          test: '么么哒'
	        }
	      });
	    });
	    
	    
	  
	  //监听查看更多记录
	    layim.on('chatlog', function(data, ul){
	      $.post('historymessageajax',{id:data.id,pageSize:2000},  function(str){
		    	var json =eval("("+str+")")
			     result = json.data;
		    	var insertData =   { list: result,  id:data.id }
		    	var template = '<div class="layui-unselect layim-content"><div class="layim-chat layim-chat-friend"><div class="layim-chat-main" style="height:98%"><ul> '
			    	+'{{#  layui.each(d.list, function(index, item){ }}<li class="layim-chat-li {{#  if(d.id === item.senduser){ }}layim-chat-mine {{#  } }} "><div class="layim-chat-user"><img src="{{ item.avatar}}"><cite>{{ item.sendusername}}</cite></div>'
			    	+'<div class="layim-chat-text">{{item.content}}</div></li>{{#  }); }}</ul></div></div></div>'
			    	
		    	var temphtml = "";
		    	 laytpl(template).render(insertData, function(html){
		    		 temphtml =html;
		    	 }); 
			     layim.panel({
			        title: '与 '+ data.name +' 的聊天记录' //标题
			        ,tpl: temphtml//模版
			        ,data: { //数据
			          list: result,
			          id:data.id
			        }
			     });
	      })
	  
	    });
	  
	    layim.on('moreList', function(obj){
	    	 /*  switch(obj.alias){ //alias即为上述配置对应的alias
	    	    case 'find': //发现
	    	      layer.msg('自定义发现动作');
	    	    break;
	    	    case 'share':
	    	      layim.panel({
	    	        title: 'share' //分享
	    	        ,tpl: '<div style="padding: 10px;">自定义模版，{{d.data.test}}</div>' //模版
	    	        ,data: { //数据
	    	          test: '123'
	    	        }
	    	      });
	    	    break;
	    	  } */
	    	});   
	   
	  layim.on('ready', function(res){
		  lm =  mobile.layim; 
		  //添加客服
		  lm.addList({
			  type: 'friend' //列表类型，只支持friend和group两种
				  ,avatar: "layui/images/0.jpg" //好友头像
				  ,username: '琪琪IM智能客服' //好友昵称
				  ,groupid: 1 //所在的分组id
				  ,id: "0" //好友id
				  ,sign: "有什么问题尽管问我把" //好友签名
		  }); 
		  
		  //取得离线消息
		  showOfflineMsg(layim)
		  layim.setFriendStatus(currentsession, 'oline');
	   }); 
	  //监听发送消息
	  layim.on('sendMessage', function(data){
		     var To = data.to; 
	    	 var my = data.mine;
	    	 var message = my.content;
		     var receiver =To.id+"";
		     if($.trim(currentsession)=='' ){
		       return;
		     } 
		     if($.trim(message)==''){
		       layer.msg("请输入要发送的消息!");
		       return;
		     }   
		     if (!window.WebSocket) {
		          return;
		     }
		     if (socket.readyState == WebSocket.OPEN) {
		    	 //判断是发送好友消息还是群消息
		    	 if(To.type=="friend"){
		    		 sendMsg(message,receiver,null)
		    	 }else{
		    		 sendMsg(message,null,receiver)
		    	 }   
		     }  
	  }); 
	   
	  layim.on('online', function(status){
		  //console.log(status); //获得online或者hide
		  /*  var stateVal = "";
		  if(status=="hide"){
			  stateVal ="hide";
		  }
		  $.get("/user/onlinestate",{ state: stateVal }); */
	   });  
	  
	  var initEventHandle = function () {
          //收到消息后
          socket.onmessage = function(event) {
          	  if (event.data instanceof ArrayBuffer){
          	        var msg =  proto.Model.deserializeBinary(event.data);      //如果后端发送的是二进制帧（protobuf）会收到前面定义的类型
	          	 	var msgCon =  proto.MessageBody.deserializeBinary(msg.getContent());  
  	    			var cache = layim.cache();
  	    			var username = "",avatar="",friend=false;
  			        layui.each(cache.friend, function(index1, item1){
			            layui.each(item1.list, function(index, item){
			              if(item.id == msg.getSender()){ 
			                username = item.username;
			                avatar = item.avatar;
			                return friend = true;
			              }
			            });
			            if(friend) return true;
		            }); 
          	       
          	       //心跳消息
          	       if(msg.getCmd()==2){
          	    	   //发送心跳回应
          	    	   var message1 = new proto.Model();
                       message1.setCmd(2);
                       message1.setMsgtype(4);
                       socket.send(message1.serializeBinary());
          	       }else if(msg.getCmd()==3){
          	    	   //上线
          	    	   if(msg.getSender()!=currentsession){
          	    	      layer.msg(username+"上线了！");
  	          	    	  layim.setFriendStatus(msg.getSender(), 'online');  
          	    	   } 
          	       }else if(msg.getCmd()==4){
          	    	   //下线
          	    	   if(msg.getSender()!=currentsession){
          	    		   layer.msg(username+"已下线！");
	          	    	   layim.setFriendStatus(msg.getSender(), 'offline');
          	    	   } 
          	       } else{
          	    	   //显示非自身消息    
          	    	   if(msg.getSender()!=currentsession){
          	    		  var time = (new Date(msg.getTimestamp())).getTime(); 
          	    		   //不显示用户组消息
          	    		   if(msg.getGroupid()==null||msg.getGroupid().length<1){
      	    				    lm.getMessage({
						 	        username: username
						 	        ,avatar: avatar+"?"+new Date().getTime()
						 	        ,id: msg.getSender()
						 	        ,type: "friend"
						 	        ,content: msgCon.getContent()
						 	        ,timestamp:time
					 	     	});   
          	    		   }else{
          	    			    lm.getMessage({
						 	        username: username
						 	        ,avatar: avatar+"?"+new Date().getTime()
						 	        ,id: msg.getGroupid()
						 	        ,type: "group"
						 	        ,content: msgCon.getContent()
						 	        ,timestamp: time
					 	     	});  
          	    		   } 
          	    	   }  
          	       }
          	  }else {
                    var data = event.data;                //后端返回的是文本帧时触发
              } 
          };
          //连接后
          socket.onopen = function(event) {
        	   var message = new proto.Model();
        	   var browser=BrowserUtil.info();
	   	       message.setVersion("1.0");
	   	       message.setDeviceid("")
	   	       message.setCmd(1);
	   	       message.setSender(currentsession);
	   	       message.setMsgtype(1); 
	   	       message.setFlag(1);
	   	       message.setPlatform(browser.name);
	   	       message.setPlatformversion(browser.version);
	   	       message.setToken(currentsession);
	   	       var bytes = message.serializeBinary();  
               socket.send(bytes);  
          };
          //连接关闭
          socket.onclose = function(event) {
        	  layim.setFriendStatus(currentsession, 'offline');
        	  layer.open({
        		    content: '您已下线，重新上线?'
        		    ,btn: ['确定', '取消']
        		    ,yes: function(index){
        		      reconnect(websocketurl,initEventHandle); 
        		      layer.close(index);
        		      showOfflineMsg(layim)
        		    }
        	  });
        	 
	      }; 
	      socket.onerror = function () {
	          reconnect(websocketurl,initEventHandle);
	          showOfflineMsg(layim)
	      };
      }  
	  createWebSocket(websocketurl,initEventHandle);
	  
 }); 

 
  
</script>
</body>
</html>