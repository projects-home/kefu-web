<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/page/public/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>群聊</title>
  <link type="text/css" rel="stylesheet" href="css/im.css"/> 
  <link rel="stylesheet" href="layui/css/layui.css">
    <script type="text/javascript" src="js/util.js"></script>  
  <script type="text/javascript" src="js/message.js?v=1"></script>  
  <script type="text/javascript" src="js/messagebody.js"></script>   
  <script type='text/javascript' src='dwr/engine.js'></script>
  <script type='text/javascript' src='dwr/interface/Imwebserver.js'></script>
  <style>
	.flow-default{width: auto; height: 460px; overflow: auto; font-size: 0;}
	.flow-default li{display: inline-block; margin: 0 5px; font-size: 14px; width: 96%;  margin-bottom: 10px; height: 60px; line-height: 25px; text-align: left; background-color: #eee;}
	.flow-default img{width: 100%; height: 100%;}
   </style>
</head>
<body>
<div id="container" class="wrap" style="left: 464px; height: 566px; right: auto;"><!--990宽度时请计算margin-top=（屏幕高度-990px）/2-->
    <!--蓝色背景头部-->
    <div class="header" onselectstart="return false">
        <div class="logo fixPng"></div>
       
       <div class="weixin-code">
            <span class="weixin-img-s" id="jimiWeixinCode">
                <i class="weixin-icon fixPng"></i>
                扫码关注
            </span>
                        	<span class="weixin-img-b fixPng">微信咨询</span> 
                    </div>
       
        <div class="service-online">
            <a id="skipToOnlineService" href="#">
                <i class="service-icon fixPng"></i>
                在线客服
            </a>
        </div>

        <i id="winmax" class="full-screen"  ></i>
        <i id="winclose_top" class="close-screen" ></i>
    </div>
    <!--内容区域-->
    <div class="container clearfix">
        <!--左侧聊天区域-->
        <div class="l-area fl">
            <div class="l-area-content">
                <!--全屏时请计算chat-block的高度height=屏幕高度-184px-->
					<div class="nano has-scrollbar">
						<div id="master"></div>
						<div id="chatcontent" class="content chat-block" tabindex="0"
							style="height: 371px;">
							<div class="jimi-noLogin-blank" style="height: 30px;"></div>
							<div class="jimi_lists clearfix">
								<div class="header_img jimi3 fl"></div>
								<table class="msg" cellspacing="0" cellpadding="0">
									<tbody>
										<tr>
											<td class="lt"></td>
											<td class="tt"></td>
											<td class="rt"></td>
										</tr>
										<tr>
											<td class="lm"><span></span></td>
											<td class="mm"><span><p>
														<span style="white-space: normal; line-height: 17.1429px;">Hi~</span>${pageContext.session.id}<span
															style="white-space: normal; line-height: 17.1429px;">，</span><span
															style="white-space: normal; line-height: 17.1429px;"><span
															style="line-height: 17.1429px;">有什么问题尽管问我哦~</span></span>
													</p></span></td>
											<td class="rm"></td>
										</tr>
										<tr>
											<td class="lb"></td>
											<td class="bm"></td>
											<td class="rb"></td>
										</tr>
										<tr>
											<td></td>
										</tr>
									</tbody>
								</table>
							</div>  
						</div>
						<div id="pop-alert"></div>
						<div class="pane"
							style="height: 100%; display: none; opacity: 1; visibility: visible;">
							<div class="slider" style="height: 396px;"></div>
						</div>
						<div class="jimi-noLogin-msgTips">
							<p>
								欢迎 ${pageContext.session.id} 
							</p>
						</div>
					</div>
					<div class="edit-block">
                    <div class="clearfix">
                        <div class="inputL"></div>
                        <!--
                          全屏时计算inputM宽度=屏幕宽度-980px
                          注：ie6下多减去3px
                        -->
                        <!--<div class="inputM" style="width: 10px;_width:7px;">-->
                            <div id="sugguestions" class="suggestion-area" style="display: none;">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tbody>
                                    <tr>
                                        <td class="tl"></td>
                                        <td class="tm"></td>
                                        <td class="tr"></td>
                                    </tr>
                                    <tr>
                                        <td class="ml"></td>
                                        <td class="mm">
                                            <ul>
                                            </ul>
                                        </td>
                                        <td class="mr"></td>
                                    </tr>
                                    <tr>
                                        <td class="bl"></td>
                                        <td class="bm"></td>
                                        <td class="br"></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        <!--</div>-->
                        <!--<div class="inputR"></div>-->
                        <div class="input-block">
                            <textarea id="text-in" name="text-in" class="text-type grayInput" autocomplete="off" style="width: 577px; color: rgb(153, 153, 153);"></textarea>
                        </div>
                    </div>
                    <a id="j_satisfy" href="javascript:void(0);" class="satisfaction-percent" ></a>
                    <!-- <a id="j_teach" href="javascript:void(0);" class="teach" ></a> -->
                    <!--点击关闭时请加class:close-pressed-->
                    <i id="winclose" class="close"  ></i>
                    <!--点击发送时请加class:send-pressed-->
                    <i id="winsend" class="send"></i>
                </div>
            </div>
        </div>
        
        <!--右侧导航信息-->
	        	        	
		        <div class="r-area fr" id="jRightArea">
	       
			            <ul id="sidenav" class="side-nav clearfix">
			                    <li id="productInfo" class="side-nav-item fl current" data-type="45">
			                        <a href="javascript:void(0);">用户列表</a>
			                    </li>
			                    <li style="_display: none;"><div class="tab_arrow" style="left: 0;"><b></b></div></li>
			            </ul>
			            <!--快捷查询信息显示区域-->
			            <div id="navcontent" style="height: 462px;">
							<ul class="flow-default" id="LAY_demo1">
								<c:forEach  items="${allsession}"  var="user">
						    		<li>用户：${user.account}  <br>客户端：${user.platform} -${user.platformVersion}  </li> 
						    	</c:forEach> 
							</ul>
						</div>
                    </div>
    
    </div>

</div>

<div style="display:none"  class="mymsg">
		<div class="customer_lists clearfix  "  >
			<div class="header_img jimi3"
				style="background: url(img/defaultImgLarge.png) no-repeat center;">
				<div class="header_img_hover"></div>
			</div>
			<table class="msg" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td class="lt"></td>
						<td class="tt"></td>
						<td class="rt"></td>
					</tr>
					<tr>
						<td class="lm"></td>
						<td class="mm">{sender}<br>{content}</td>
						<td class="rm"><span></span></td>
					</tr>
					<tr>
						<td class="lb"></td>
						<td class="bm"></td>
						<td class="rb"></td>
					</tr>
					<tr>
						<td></td>
						<td class="time"></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
</div>
<div style="display:none"  class="remsg">							
	<div class="jimi_lists clearfix ">
		<div class="header_img jimi3 fl"></div>
		<table class="msg" cellspacing="0" cellpadding="0">
			<tbody>
				<tr>
					<td class="lt"></td>
					<td class="tt"></td>
					<td class="rt"></td>
				</tr>
				<tr>
					<td class="lm"><span></span></td>
					<td class="mm">{sender}   {time}<br><span>{content}</span></td>
					<td class="rm"></td>
				</tr>
				<tr>
					<td class="lb"></td>
					<td class="bm"></td>
					<td class="rb"></td>
				</tr>
				<tr>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
 
<script src="layui/layui.js"></script>
<script src="js/websocketconfig.js"></script>
<script>
  var currentsession= "${pageContext.session.id}";
  var showmsg;
	//一般直接写在一个js文件中
	layui.use(['layer',  'jquery'], function(){
	  var layer = layui.layer 
	  ,$ = layui.jquery;  
	  //回复消息
	  var reMsg=function(sender,time,msg){
		  var content = $(".remsg").html();
	       content  =content.replace("{content}", HtmlUtil.htmlEncodeByRegExp(msg)).replace("{time}",time).replace("{sender}",sender);
	       $("#chatcontent").append(content);
   	       $("#chatcontent").scrollTop( $("#chatcontent")[0].scrollHeight); 
	  }
	  //发送消息
      var sendMsg=function(msg){ 
    	  var message = new proto.Model(); 
      	  var content = new proto.MessageBody();
         
           message.setMsgtype(4);
           message.setCmd(5);
           message.setGroupid("0");//系统用户组
           message.setToken(currentsession);  
           message.setSender(currentsession);
           content.setContent(msg);
           content.setType(0)
           message.setContent(content.serializeBinary())
           socket.send(message.serializeBinary());
	  }
	  
      showmsg = function(data){
    	  var msg = eval("("+data.user+")");
	   	   var content = eval("("+data.content+")"); 
	   	   if(msg.cmd==3){
	   	    	  if(msg.sender!=currentsession){
	   	    		layer.msg("用户"+msg.sender+"上线了");  
	   	    	  } 
	   	   }else if(msg.cmd==4){
	    	       if(msg.sender!=currentsession){
	     	    		layer.msg("用户"+msg.sender+"下线了");  
	     	       }    
	       }else if(msg.cmd==5){
	   	    	   //显示非自身消息    
	   	    	   if(msg.sender!=currentsession){
	   	    		   //不显示用户组消息
	   	    		   if(msg.groupId==null||msg.groupId.length<1){
	       	    	     reMsg(msg.sender,msg.timeStamp,content.content);
	   	    		   } 
	   	    	   } 
	   	   } 
	   	   /* 
			以下代码只适合ie10以上浏览器  无法兼容低版本浏览器
			var  msgmodel =  proto.Model.deserializeBinary(data);  
			var  msgbody = proto.MessageBody.deserializeBinary(msgmodel.getContent()); 
			alert(msgbody.getContent())
			*/
    	  
      }
	  
  var initEventHandle = function () {   
	  
          //收到消息后
          socket.onmessage = function(event) {
          	  if (event.data instanceof ArrayBuffer){
          	       var msg =  proto.Model.deserializeBinary(event.data);      //如果后端发送的是二进制帧（protobuf）会收到前面定义的类型
          	       //心跳消息
          	       if(msg.getCmd()==2){
          	    	   //发送心跳回应
          	    	   var message1 = new proto.Model();
                       message1.setCmd(1);
                       message1.setMsgtype(4);
                       socket.send(message1.serializeBinary());
          	       }else if(msg.getCmd()==3){
           	    	  if(msg.getSender()!=currentsession){
            	    		layer.msg("用户"+msg.getSender()+"上线了");  
            	    		 
            	       } 
            	   }else if(msg.getCmd()==4){
            	       if(msg.getSender()!=currentsession){
              	    		layer.msg("用户"+msg.getSender()+"下线了");  
              	       }    
              	   }else if(msg.getCmd()==5){
          	    	   //显示非自身消息    
          	    	   if(msg.getSender()!=currentsession){
          	    		   
          	    		 var msgCon =  proto.MessageBody.deserializeBinary(msg.getContent()); 
            	    	   reMsg(msg.getSender(),msg.getTimestamp(),msgCon.getContent());
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
        	  layer.confirm('您已下线，重新上线?', function(index){
        		  reconnect(websocketurl,initEventHandle); 
        		  layer.close(index);
        	  }); 
	      };
	      socket.onerror = function () {
	    	  layer.msg("服务器连接出错，请检查websocketconfig.js里面的IP地址");  
	          reconnect(websocketurl,initEventHandle);
	      };
	 }  	  

    createWebSocket(websocketurl,initEventHandle);  
  
	 $("#winsend").on("click",function(){
		 
		  var groupid = "0";
		  var msg = $("#text-in").val();
	   	 
    	  if(msg.length>0){
    		  if (!window.WebSocket) {
    			  Imwebserver.sendGroupMsg(groupid,msg);
    	      }else{
    	    	  if (socket.readyState == WebSocket.OPEN) {
	    	    	  sendMsg(msg)
	    	      } else {
	    	          layer.msg("连接没有开启！");
	    	      }	 
    	      }
    		  var ct = HtmlUtil.htmlEncodeByRegExp(msg);
              var content = $(".mymsg").html();
   	    	  content  =content.replace("{content}",ct).replace("{sender}",currentsession)
   	          $("#chatcontent").append(content);
   	    	  $("#chatcontent").scrollTop( $("#chatcontent")[0].scrollHeight); 
    	      $("#text-in").val("");
    	  }else{
    		  layer.msg("请输入要发送的消息!");
    	  } 
	 }) 
	  
	});

	 //dwr推送消息方法
	 function showMessage(data) {  
		    showmsg(data); 
	 }
</script> 

 
</body>
</html>