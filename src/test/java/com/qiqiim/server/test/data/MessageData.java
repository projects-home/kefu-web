package com.qiqiim.server.test.data;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.kefu.constant.Constants;
import com.kefu.server.model.proto.MessageBodyProto;
import com.kefu.server.model.proto.MessageProto;
import com.kefu.util.SystemInfo;

public class MessageData {

    public  MessageProto.Model.Builder generateConnect(String sessionid) {
    	SystemInfo syso = SystemInfo.getInstance();
        MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
        builder.setVersion("1.0");
        builder.setDeviceId(syso.getMac());
        builder.setCmd(Constants.CmdType.BIND);
        builder.setSender(sessionid);
        builder.setReceiver(sessionid);
        builder.setMsgtype(Constants.ProtobufType.SEND);
        builder.setFlag(1);
        builder.setPlatform(syso.getSystem());
        builder.setPlatformVersion(syso. getSystemName());
        builder.setToken(sessionid);
        builder.setAppKey("123");
        builder.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        builder.setSign("123");
        return builder;
    }

    public  MessageProto.Model.Builder generateHeartbeat() {
        MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
        builder.setCmd(Constants.CmdType.HEARTBEAT);
        builder.setMsgtype(Constants.ProtobufType.REPLY);
        return builder;
    }
    
    public  MessageProto.Model.Builder generateSend(String sessionid,String ressionId,String sendcontent) {
        MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
        builder.setCmd(Constants.CmdType.MESSAGE);
        builder.setSender(sessionid);
        builder.setReceiver(ressionId);
        builder.setMsgtype(Constants.ProtobufType.REPLY);
        builder.setToken(sessionid);
        MessageBodyProto.MessageBody.Builder  msg =  MessageBodyProto.MessageBody.newBuilder();
        msg.setContent(sendcontent); 
        builder.setContent(msg.build().toByteString());
        return builder;
    }
}
