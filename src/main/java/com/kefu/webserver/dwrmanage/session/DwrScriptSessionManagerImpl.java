package com.kefu.webserver.dwrmanage.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.impl.DefaultScriptSession;
import org.directwebremoting.impl.DefaultScriptSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kefu.constant.Constants;
import com.kefu.webserver.user.model.UserAccountEntity;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
//@Service("dwrScriptSessionManagerImpl")
public class DwrScriptSessionManagerImpl extends DefaultScriptSessionManager{   
    
	  
    private final Logger logger = LoggerFactory.getLogger(this.getClass());  
 
   
 
 

	public DwrScriptSessionManagerImpl(){  
		
        addScriptSessionListener(  new ScriptSessionListener() {  
        	
              public void sessionCreated(ScriptSessionEvent ev) {  
                    logger.info(" script session in.....");  
                    WebContext ctx = WebContextFactory.get();  
                    HttpSession httpSession = ctx.getSession();  
                    ScriptSession scriptSession = ev.getSession();  
                    HttpServletRequest req = ctx.getHttpServletRequest();  
                    
                    //获取浏览器信息
            		Browser browser = UserAgent.parseUserAgentString(req.getHeader("User-Agent")).getBrowser();
            		//获取浏览器版本号
            		Version version = browser.getVersion(req.getHeader("User-Agent"));
            		String info = browser.getName() + "/" + version.getVersion(); 
                    
                    UserAccountEntity user =  (UserAccountEntity)req.getSession().getAttribute("user");
                    //String userId = (String)httpSession.getAttribute("userId");  
                    String userId =    (String)req.getSession().getId(); //scriptSession.getHttpSessionId();//
                    if(user!=null){
                    	userId = user.getId().toString();
                    }
                    if (userId == null) {  
                        logger.info(" script session invalidate");  
                        scriptSession.invalidate();  
                        httpSession.invalidate();  
                        return;  
                    }  
                    //已经存在旧的scriptSession 注销这个旧的scriptSession  
                    DefaultScriptSession old = sessionMap.get(userId);  
                    if (old != null){  
                        DwrScriptSessionManagerImpl.this.invalidate(old);  
                    }  
                      
                    httpSession.setAttribute(Constants.DWRConfig.SS_KEY, scriptSession.getId());  
                    scriptSession.setAttribute(Constants.SessionConfig.SESSION_KEY, userId);  
                    scriptSession.setAttribute("SESSIONID", httpSession.getId());  
                    scriptSession.setAttribute(Constants.DWRConfig.BROWSER, browser.getName()); 
                    scriptSession.setAttribute(Constants.DWRConfig.BROWSER_VERSION, version.getVersion());
                    //scriptSession.setAttribute("ip", value);
                    logger.info(" script session created: " + userId);  
                 }  
                
                 public void sessionDestroyed(ScriptSessionEvent ev) {  
                    ScriptSession ss = ev.getSession();  
                    if (ss.getAttribute(Constants.SessionConfig.SESSION_KEY) != null){  
                        logger.info(" script session destroyed: " + ss.getAttribute(Constants.SessionConfig.SESSION_KEY));  
                    }  
                 }  
          } );  
          
         //this.setScriptSessionTimeout(150000L);  
    }  
}