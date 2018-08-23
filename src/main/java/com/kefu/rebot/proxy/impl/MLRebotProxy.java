/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.kefu.rebot.proxy.impl;
/**
 * 茉莉机器人回复
 */
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.kefu.constant.Constants;
import com.kefu.rebot.model.RobotMessage;
import com.kefu.rebot.model.RobotMessageArticle;
import com.kefu.rebot.proxy.RebotProxy;
import com.kefu.server.model.MessageWrapper;
import com.kefu.server.model.proto.MessageBodyProto;
import com.kefu.server.model.proto.MessageProto;

public class MLRebotProxy implements RebotProxy {
	  private final static Logger log = LoggerFactory.getLogger(MLRebotProxy.class);
	private String apiUrl;//机器人apiurl
	private String key="";//秘钥    请自行申请机器人KEY
	private String secret="";

	@Override
	public MessageWrapper botMessageReply(String user, String content) {
		log.info("MLRebot reply user -->"+user +"--mes:"+content);
		String message = "";
		 try{
			    //只实现了基础的  具体的请自行修改
			    Document doc = Jsoup.connect(apiUrl).timeout(62000).data("api_key",key).data("api_secret",secret).data("limit","5").data("question",content).post();
				message = doc.select("body").html(); 
			}catch(Exception e){
				e.printStackTrace();
			}
		 MessageProto.Model.Builder  result = MessageProto.Model.newBuilder();
		 result.setCmd(Constants.CmdType.MESSAGE);
		 result.setMsgtype(Constants.ProtobufType.REPLY);
		 result.setSender(Constants.ImserverConfig.REBOT_SESSIONID);//机器人ID
		 result.setReceiver(user);//回复人
		 result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		 MessageBodyProto.MessageBody.Builder  msgbody =  MessageBodyProto.MessageBody.newBuilder();
		 msgbody.setContent(message); 
	     result.setContent(msgbody.build().toByteString());
		 return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, Constants.ImserverConfig.REBOT_SESSIONID, user,result.build());
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	  
}
