/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.kefu.server;

import static io.netty.buffer.Unpooled.wrappedBuffer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.kefu.constant.Constants;
import com.kefu.server.connertor.impl.ImConnertorImpl;
import com.kefu.server.model.proto.MessageProto;
import com.kefu.server.proxy.MessageProxy;


public class ImWebsocketServer  {

    private final static Logger log = LoggerFactory.getLogger(ImWebsocketServer.class);
    
    private ProtobufDecoder decoder = new ProtobufDecoder(MessageProto.Model.getDefaultInstance());
    
    private MessageProxy proxy = null;
    private ImConnertorImpl connertor; 
    private int port;
 
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;
    
    public void init() throws Exception {
        log.info("start qiqiim websocketserver ...");

        // Server 服务启动
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
	    		ChannelPipeline pipeline = ch.pipeline();
	    		
	    		 // HTTP请求的解码和编码
	            pipeline.addLast(new HttpServerCodec());
	            // 把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse，
	            // 原因是HTTP解码器会在每个HTTP消息中生成多个消息对象HttpRequest/HttpResponse,HttpContent,LastHttpContent
	            pipeline.addLast(new HttpObjectAggregator(Constants.ImserverConfig.MAX_AGGREGATED_CONTENT_LENGTH));
	            // 主要用于处理大数据流，比如一个1G大小的文件如果你直接传输肯定会撑暴jvm内存的; 增加之后就不用考虑这个问题了
	            pipeline.addLast(new ChunkedWriteHandler());
	            // WebSocket数据压缩
	            pipeline.addLast(new WebSocketServerCompressionHandler());
	            // 协议包长度限制
	            pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, Constants.ImserverConfig.MAX_FRAME_LENGTH));
	            // 协议包解码
	            pipeline.addLast(new MessageToMessageDecoder<WebSocketFrame>() {
	                @Override
	                protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> objs) throws Exception {
	                    ByteBuf buf = ((BinaryWebSocketFrame) frame).content();
	                    objs.add(buf);
	                    buf.retain();
	                }
	            });
	            // 协议包编码
	            pipeline.addLast(new MessageToMessageEncoder<MessageLiteOrBuilder>() {
	                @Override
	                protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
	                    ByteBuf result = null;
	                    if (msg instanceof MessageLite) {
	                        result = wrappedBuffer(((MessageLite) msg).toByteArray());
	                    }
	                    if (msg instanceof MessageLite.Builder) {
	                        result = wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray());
	                    }
	                    // 然后下面再转成websocket二进制流，因为客户端不能直接解析protobuf编码生成的
	                    WebSocketFrame frame = new BinaryWebSocketFrame(result);
	                    out.add(frame);
	                }
	            });
	            // 协议包解码时指定Protobuf字节数实例化为CommonProtocol类型
	            pipeline.addLast(decoder);
	            pipeline.addLast(new IdleStateHandler(Constants.ImserverConfig.READ_IDLE_TIME,Constants.ImserverConfig.WRITE_IDLE_TIME,0));
	            // 业务处理器
	            pipeline.addLast(new ImWebSocketServerHandler(proxy,connertor));
	    		 
            }
        });
        
        // 可选参数
    	bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        // 绑定接口，同步等待成功
        log.info("start qiqiim websocketserver at port[" + port + "].");
        ChannelFuture future = bootstrap.bind(port).sync();
    	channel = future.channel();
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    log.info("websocketserver have success bind to " + port);
                } else {
                    log.error("websocketserver fail bind to " + port);
                }
            }
        });
       // future.channel().closeFuture().syncUninterruptibly();
    }

    public void destroy() {
        log.info("destroy qiqiim websocketserver ...");
        // 释放线程池资源
        if (channel != null) {
			channel.close();
		}
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log.info("destroy qiqiim webscoketserver complate.");
    }
    
 
    

    public void setPort(int port) {
        this.port = port;
    }

	public void setProxy(MessageProxy proxy) {
		this.proxy = proxy;
	}
 

    public void setConnertor(ImConnertorImpl connertor) {
		this.connertor = connertor;
	}
    
    
}
