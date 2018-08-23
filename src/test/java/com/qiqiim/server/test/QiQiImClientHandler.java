/*
 * Copyright (c) 2016, LinkedKeeper
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of LinkedKeeper nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.qiqiim.server.test;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kefu.constant.Constants;
import com.kefu.server.model.proto.MessageBodyProto;
import com.kefu.server.model.proto.MessageProto;
import com.kefu.server.model.proto.MessageBodyProto.MessageBody;
import com.qiqiim.server.test.data.MessageData;

@Sharable
public class QiQiImClientHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(QiQiImClientHandler.class);

    
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
        MessageProto.Model message = (MessageProto.Model) o;
        
        if(message.getCmd()==Constants.CmdType.HEARTBEAT){
        	ctx.channel().writeAndFlush(new MessageData().generateHeartbeat());
        	System.out.println("------------心跳检测--------------"+message);
        }else if(message.getCmd()==Constants.CmdType.ONLINE){
        	System.out.println(message.getSender()+"------------上线了--------------");
        }else if(message.getCmd()==Constants.CmdType.RECON){
        	System.out.println(message.getSender()+"------------重新连接--------------");
        }else if(message.getCmd()==Constants.CmdType.OFFLINE){
        	System.out.println(message.getSender()+"------------下线了--------------");
        }else if(message.getCmd()==Constants.CmdType.MESSAGE){
        	 MessageBodyProto.MessageBody  content =  MessageBody.parseFrom(message.getContent()) ;
             logger.info(message.getSender()+" 回复我 :" + content.getContent());
             System.out.println(message.getSender()+" 回复我 :" + content.getContent());
        }
       
    }
    
    
    public void userEventTriggered(ChannelHandlerContext ctx, Object o) throws Exception {
	     //断线重连
	}
}
