/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.kefu.server.model;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kefu.constant.Constants;
import com.kefu.server.model.proto.MessageBodyProto;
import com.kefu.server.model.proto.MessageProto;

public class Session   implements Serializable{

 
 
 
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 8269505210699191257L;
	private transient Channel session;
	private ScriptSession dwrsession;
	 
	private String nid;//session在本台服务器上的ID
	private int source;//来源 用于区分是websocket\socket\dwr
	private String deviceId;//客户端ID  (设备号码+应用包名),ios为devicetoken
	private String host;//session绑定的服务器IP
	private String account;//session绑定的账号
	private String platform;//终端类型  
	private String platformVersion;//终端版本号
	private String appKey;//客户端key
	private Long bindTime;//登录时间
	private Long updateTime;//更新时间
	private String sign;//签名
	private Double longitude;//经度
	private Double latitude;//维度
	private String location;//位置
	private int status;// 状态
	private Map<String,Session> sessions = new HashMap<String,Session>(); //用于dwr websocket存储多开页面创建的session

	public void addSessions(Session session){
		sessions.put(session.getNid(), session);
	}
	 
	
	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
		setAttribute("updateTime", updateTime);
	}


	public Session(ScriptSession session) {
		this.dwrsession = session;
		this.nid = session.getId();
	}
	 
	public Session(Channel session) {
		this.session = session;
		this.nid = session.id().asShortText();
	}
 
	public Session()
	{
		
	}
	
	 
	public ScriptSession getDwrsession() {
		return dwrsession;
	}


	public void setDwrsession(ScriptSession dwrsession) {
		this.dwrsession = dwrsession;
	}


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
		setAttribute(Constants.SessionConfig.SESSION_KEY, account);
	}
 

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		setAttribute("longitude", longitude);
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		setAttribute("latitude", latitude);
		this.latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		setAttribute("location", location);
		this.location = location;
	}

 

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getDeviceId() {
		return deviceId;
	}
 
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
		
		setAttribute("deviceId", deviceId);
	}


   

	public String getHost() {
		return host;
	}



	public Long getBindTime() {
		return bindTime;
	}

	public void setBindTime(Long bindTime) {
		this.bindTime = bindTime;
	    setAttribute("bindTime", bindTime);
	}
 

	public void setHost(String host) {
		this.host = host;
		 
		setAttribute("host", host);
	}

	public void setChannel(Channel session) {
		this.session = session;
	}
 
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		setAttribute("status", status);
	}


	
	
	public Channel getSession() {
		return session;
	}
	
	public List<Channel> getSessionAll(){
		  List<Channel>  list= new ArrayList<Channel>();
		  list.add(getSession());
		  for (String key : sessions.keySet()) {
				Session  session = sessions.get(key);
				if(session!=null){
					list.add(session.getSession()); 
				}
		  }
		  return list;
	}

	public void setSession(Channel session) {
		this.session = session;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
		setAttribute("platform", platform);
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
		setAttribute("platformVersion", platformVersion);
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
		setAttribute("appKey", appKey);
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
		setAttribute("sign", sign);
	}
	
	

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
		setAttribute("source", source);
	}

	public void setAttribute(String key, Object value) {
		if(session!=null)
		session.attr(AttributeKey.valueOf(key)).set(value);
	}


	public boolean containsAttribute(String key) {
		if(session!=null)
		return session.hasAttr(AttributeKey.valueOf(key));
		return false;
	}
	
	public Object getAttribute(String key) {
		if(session!=null)
		return session.attr(AttributeKey.valueOf(key)).get();
		return null;
	}

	public void removeAttribute(String key) {
		if(session!=null)
		session.attr(AttributeKey.valueOf(key)).set(null);;
	}

	public SocketAddress getRemoteAddress() {
		if(session!=null)
		return session.remoteAddress();
		return null;
	}

	public  boolean write(Object msg) {
		if(sessions.size()>0){
			for (String key : sessions.keySet()) {
				Session  session = sessions.get(key);
				if(session!=null){
					session.write(msg);
				}
			}
		} 
		if(session!=null&&isConnected())
		{
			return session.writeAndFlush(msg).awaitUninterruptibly(5000);
		}else if(dwrsession!=null&&isConnected()){
			try{
				MessageProto.Model  msgModel = (MessageProto.Model)msg;
				MessageBodyProto.MessageBody  content = MessageBodyProto.MessageBody.parseFrom(msgModel.getContent());    
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("user", Constants.DWRConfig.JSONFORMAT.printToString(msgModel));
				map.put("content", Constants.DWRConfig.JSONFORMAT.printToString(content));
				ScriptBuffer script = new ScriptBuffer();  
				script.appendCall(Constants.DWRConfig.DWR_SCRIPT_FUNCTIONNAME, JSONArray.toJSON(map));  
				dwrsession.addScript(script);
				//protobuf 不支持低版本IE 没法使用protobuf  支持可以使用以下方法
				/*MessageProto.Model  msgModel = (MessageProto.Model)msg;
				ScriptBuffer script = new ScriptBuffer();  
				Object json = JSONArray.toJSON(msgModel.toByteArray());
				script.appendCall(Constants.DWRConfig.DWR_SCRIPT_FUNCTIONNAME, json);  
				dwrsession.addScript(script); */
				return  true; 
			}catch(Exception e){
				
			}
		} 
		return false;
	}

	
	public boolean isConnected() {
		//判断是否有多个session
		if(sessions!=null&&sessions.size()>0){
			for (String key : sessions.keySet()) {
				Session  session = sessions.get(key);
				if(session!=null && session.isConnected()){
					return true;
				}
			}
		}else if(session != null){
			return session.isActive();
		}else if(dwrsession!=null){
			return !dwrsession.isInvalidated();
		}
		return false;
	}

	public boolean  isLocalhost()
	{
		
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			return ip.equals(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return false;
		 
	}
	
	
	public int  otherSessionSize()
	{
		return sessions.size();
	}
	
 
	public void close() {
		if(session!=null){
			session.close();
		}else if(dwrsession!=null){
			dwrsession.invalidate();
		}
	}
	
	public void closeAll() {
		close();
		for (String key : sessions.keySet()) {
			Session  session = sessions.get(key);
			if(session!=null){
				session.close();
				sessions.remove(key);
			}
		}
	}

	public void close(String nid) {
		if(getNid().equals(nid)){
			close();
		}else{
			Session  session = sessions.get(nid);
			sessions.remove(nid);
			session.close();
		} 
	}
	
	
	public int hashCode(){
		
		return (deviceId + nid + host).hashCode();
	}
	
	public boolean equals(Object o) {
        
		if(o instanceof Session){
			return hashCode() == o.hashCode();
		}
		return false;
	}

    public boolean fromOtherDevice(Object o) {
        
		if (o instanceof Session) {
			
			Session t = (Session) o;
			if(t.deviceId!=null && deviceId!=null)
			{
				return !t.deviceId.equals(deviceId);
			} 
		}  
		return false;
	}

    public boolean fromCurrentDevice(Object o) {
        
		return !fromOtherDevice(o);
	}
 
	
	public String  toString(){
		return  JSON.toJSONString(Session.this);
	}


	
}