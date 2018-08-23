var websocketurl="ws://"+window.location.hostname+":2048/ws";   //ws://{ip}:{端口}/{java后端websocket配置的上下文}
var reconnectflag = false;//避免重复连接
var socket; 

function createWebSocket(url,callbak) {
   try { 
      if (!window.WebSocket) {
  	      window.WebSocket = window.MozWebSocket; 
  	  }  
  	  if (window.WebSocket) {
  		socket = new WebSocket(url);
        socket.binaryType = "arraybuffer"; 
        callbak();
  	  } else {
         // layer.msg("你的浏览器不支持websocket！");
  	     //当浏览器不支持websocket时 降级为http模式	  
  	    var isClose =false;
  		window.onbeforeunload =function() {
  			if(!isClose){
  				return "确定要离开当前聊天吗?";
  			}else{
  				return "";
  			}
  		}
  		window.onunload =function() {
  			if(!isClose){
  				Imwebserver.closeconnect(); 
  			}
  		} 
  	    dwr.engine.setActiveReverseAjax(true);
  	    dwr.engine.setNotifyServerOnPageUnload(true);
  	    dwr.engine.setErrorHandler(function(){  
  	    });
  	    dwr.engine._errorHandler = function(message, ex) {
  	       //alert("服务器出现错误");
  	       //dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);
  	    };
  	    Imwebserver.serverconnect();
  		  
      }  
    } catch (e) { 
       reconnect(url,callbak);
    }     
}
 

function reconnect(url,callbak) {
    if(reconnectflag) return;
    reconnectflag = true;
    //没连接上会一直重连，设置延迟避免请求过多
    setTimeout(function () {
        createWebSocket(url,callbak);
        reconnectflag = false;
    }, 2000);
}

 
 

