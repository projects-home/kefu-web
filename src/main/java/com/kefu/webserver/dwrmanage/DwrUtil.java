package com.kefu.webserver.dwrmanage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.kefu.constant.Constants;
import com.kefu.server.model.MessageWrapper;
import com.kefu.server.model.proto.MessageBodyProto;
import com.kefu.server.model.proto.MessageProto;

public class DwrUtil {
    /**
     * 发送给全部
     */
    public static void sedMessageToAll(MessageProto.Model  msgModel){
      
    	 
        try{
			  MessageBodyProto.MessageBody  content = MessageBodyProto.MessageBody.parseFrom(msgModel.getContent());    
			  Map<String,Object> map = new HashMap<String,Object>();
			  map.put("user", Constants.DWRConfig.JSONFORMAT.printToString(msgModel));
			  map.put("content", Constants.DWRConfig.JSONFORMAT.printToString(content));
			  final Object msg = JSONArray.toJSON(map);
			  Browser.withAllSessions(new Runnable(){ 
		            private ScriptBuffer script = new ScriptBuffer(); 
		            public void run(){ 
		            	script.appendCall(Constants.DWRConfig.DWR_SCRIPT_FUNCTIONNAME, msg);  
		                Collection<ScriptSession> sessions = Browser.getTargetSessions(); 
		                for (ScriptSession scriptSession : sessions){ 
		                    scriptSession.addScript(script); 
		                } 
		            } 
		       });
		  }catch(Exception e){
			  
		  } 
    } 
    /**
     * 发送给个人
     */
    public static void sendMessageToUser(String userid, String message) {  
        final String sessionid = userid;  
        final String msg = message;  
        final String attributeName = Constants.SessionConfig.SESSION_KEY;  
        Browser.withAllSessionsFiltered(new ScriptSessionFilter() {  
            public boolean match(ScriptSession session) {  
                if (session.getAttribute(attributeName) == null)  
                    return false;  
                else {  
                    boolean f = session.getAttribute(attributeName).equals(sessionid);  
                    return f;  
                }  
            }  
        }, new Runnable() {  
            private ScriptBuffer script = new ScriptBuffer();  
            public void run() {  
                script.appendCall(Constants.DWRConfig.DWR_SCRIPT_FUNCTIONNAME, msg);  
                Collection<ScriptSession> sessions = Browser.getTargetSessions();  
                for (ScriptSession scriptSession : sessions) {  
                    scriptSession.addScript(script);  
                }   
            }   
        });  
  
    }  
    
}