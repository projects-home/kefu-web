/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.kefu.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kefu.constant.Constants;
import com.kefu.server.connertor.impl.ImConnertorImpl;
import com.kefu.server.model.MessageWrapper;
import com.kefu.server.model.proto.MessageProto;
import com.kefu.server.proxy.MessageProxy;
import com.kefu.util.ImUtils;
@Sharable
public class ImWebSocketServerHandler   extends SimpleChannelInboundHandler<MessageProto.Model>{

	private final static Logger log = LoggerFactory.getLogger(ImWebSocketServerHandler.class);
    private ImConnertorImpl connertor = null;
    private MessageProxy proxy = null;

    public ImWebSocketServerHandler(MessageProxy proxy, ImConnertorImpl connertor) {
        this.connertor = connertor;
        this.proxy = proxy;
    }
	
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object o) throws Exception {
    	 String sessionId = ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_ID).get();
    	//发送心跳包
    	if (o instanceof IdleStateEvent && ((IdleStateEvent) o).state().equals(IdleState.WRITER_IDLE)) {
			  if(StringUtils.isNotEmpty(sessionId)){
				  MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
				  builder.setCmd(Constants.CmdType.HEARTBEAT);
			      builder.setMsgtype(Constants.ProtobufType.SEND);
				  ctx.channel().writeAndFlush(builder);
			  } 
 			 log.debug(IdleState.WRITER_IDLE +"... from "+sessionId+"-->"+ctx.channel().remoteAddress()+" nid:" +ctx.channel().id().asShortText());
 	    } 
	        
	    //如果心跳请求发出70秒内没收到响应，则关闭连接
	    if ( o instanceof IdleStateEvent && ((IdleStateEvent) o).state().equals(IdleState.READER_IDLE)){
			log.debug(IdleState.READER_IDLE +"... from "+sessionId+" nid:" +ctx.channel().id().asShortText());
			Long lastTime = (Long) ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_HEARBEAT).get();
	     	if(lastTime == null || ((System.currentTimeMillis() - lastTime)/1000>= Constants.ImserverConfig.PING_TIME_OUT))
	     	{
	     		connertor.close(ctx);
	     	}
	     	//ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_HEARBEAT).set(null);
	    }
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageProto.Model message)
			throws Exception {
		  try {
			   String sessionId = connertor.getChannelSessionId(ctx);
                // inbound
                if (message.getMsgtype() == Constants.ProtobufType.SEND) {
                	ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_HEARBEAT).set(System.currentTimeMillis());
                    MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionId, message);
                    if (wrapper != null)
                        receiveMessages(ctx, wrapper);
                }
                // outbound
                if (message.getMsgtype() == Constants.ProtobufType.REPLY) {
                	MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionId, message);
                	if (wrapper != null)
                      receiveMessages(ctx, wrapper);
                }
	        } catch (Exception e) {
	            log.error("ImWebSocketServerHandler channerRead error.", e);
	            throw e;
	        }
		 
	}
	
	
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    	log.info("ImWebSocketServerHandler  join from "+ImUtils.getRemoteAddress(ctx)+" nid:" + ctx.channel().id().asShortText());
    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.debug("ImWebSocketServerHandler Disconnected from {" +ctx.channel().remoteAddress()+"--->"+ ctx.channel().localAddress() + "}");
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.debug("ImWebSocketServerHandler channelActive from (" + ImUtils.getRemoteAddress(ctx) + ")");
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.debug("ImWebSocketServerHandler channelInactive from (" + ImUtils.getRemoteAddress(ctx) + ")");
        String sessionId = connertor.getChannelSessionId(ctx);
        receiveMessages(ctx,new MessageWrapper(MessageWrapper.MessageProtocol.CLOSE, sessionId,null, null));  
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("ImWebSocketServerHandler (" + ImUtils.getRemoteAddress(ctx) + ") -> Unexpected exception from downstream." + cause);
    }





    /**
     * to send message
     *
     * @param hander
     * @param wrapper
     */
    private void receiveMessages(ChannelHandlerContext hander, MessageWrapper wrapper) {
    	//设置消息来源为Websocket
    	wrapper.setSource(Constants.ImserverConfig.WEBSOCKET);
        if (wrapper.isConnect()) {
       	    connertor.connect(hander, wrapper); 
        } else if (wrapper.isClose()) {
        	connertor.close(hander,wrapper);
        } else if (wrapper.isHeartbeat()) {
        	connertor.heartbeatToClient(hander,wrapper);
        }else if (wrapper.isGroup()) {
        	connertor.pushGroupMessage(wrapper);
        }else if (wrapper.isSend()) {
        	connertor.pushMessage(wrapper);
        } else if (wrapper.isReply()) {
        	connertor.pushMessage(wrapper.getSessionId(),wrapper);
        }  
    }
}
