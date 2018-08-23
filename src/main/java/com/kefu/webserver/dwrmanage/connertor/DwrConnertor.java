/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.kefu.webserver.dwrmanage.connertor;

import org.directwebremoting.ScriptSession;

import com.kefu.server.model.MessageWrapper;

public interface DwrConnertor {
	 
	 
	 void close(ScriptSession scriptSession);
 
	 void connect(ScriptSession scriptSession,String sessionid) ;
	 /**
	  * 发送消息
	  * @param sessionId  发送人
	  * @param wrapper   发送内容
	  * @throws RuntimeException
	  */
	 void pushMessage(String sessionId,MessageWrapper wrapper) throws RuntimeException;
	 
}
