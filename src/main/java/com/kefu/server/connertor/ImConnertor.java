/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.kefu.server.connertor;

import com.kefu.server.model.MessageWrapper;

import io.netty.channel.ChannelHandlerContext;

public interface ImConnertor {
	 /**
	  * 发送心跳检测 到客户端
	  * @param hander
	  * @param wrapper
	  */
	void heartbeatToClient(ChannelHandlerContext hander,MessageWrapper wrapper); 
	/**
	  * 发送消息
	  * @param wrapper
	  * @throws RuntimeException
	  */
	 void pushMessage(MessageWrapper wrapper) throws RuntimeException;
	 /**
	  * 发送组消息
	  * @param wrapper
	  * @throws RuntimeException
	  */
	 void pushGroupMessage(MessageWrapper wrapper) throws RuntimeException;
	 /**
	  * 验证session
	  * @param wrapper
	  * @return
	  * @throws RuntimeException
	  */
	 boolean validateSession(MessageWrapper wrapper) throws RuntimeException;
	 
	 void close(ChannelHandlerContext hander,MessageWrapper wrapper);
	 
	 void close(String sessionId);
	 
	 void close(ChannelHandlerContext hander);
	  
	 void connect(ChannelHandlerContext ctx, MessageWrapper wrapper) ;

	 boolean exist(String sessionId) throws Exception;
	 /**
	  * 发送消息
	  * @param sessionId  发送人
	  * @param wrapper   发送内容
	  * @throws RuntimeException
	  */
	 void pushMessage(String sessionId,MessageWrapper wrapper) throws RuntimeException;
	 /**
	  * 获取用户唯一标识符
	  * @param ctx
	  * @return
	  */
	 String getChannelSessionId(ChannelHandlerContext ctx); 
}
