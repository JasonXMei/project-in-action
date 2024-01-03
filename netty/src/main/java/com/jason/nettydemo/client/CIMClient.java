package com.jason.nettydemo.client;

import com.jason.nettydemo.constant.Constants;
import com.jason.nettydemo.protocol.WUProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CIMClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(CIMClient.class);

    private EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("cim-work"));

    private static SocketChannel channel;

    /**
     * 重试次数
     */
    private int maxRetryCount = 5;
    private int errorCount;
    private String ip = "127.0.0.1";
    private int port = 8989;

    /**
     * 启动客户端
     */
    private void start() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new CIMClientHandleInitializer());

        ChannelFuture future = null;
        try {
            future = bootstrap.connect(ip, port).sync();

            if (future.isSuccess()) {
                LOGGER.info("启动 cim client 成功");
            }
            channel = (SocketChannel) future.channel();
        } catch (InterruptedException e) {
            errorCount++;

            if (errorCount >= maxRetryCount) {
                LOGGER.error("链接失败次数达到上限[{}]次", errorCount);
                return;
            }
            LOGGER.error("连接失败", e);
            start();
        }
    }

    public void reconnect() throws Exception {
        if (channel != null && channel.isActive()) {
            return;
        }
        LOGGER.info("开始重连。。");
        start();
        LOGGER.info("重连成功！！");
    }

    /**
     * 关闭
     *
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        if (channel != null){
            channel.close();
        }
    }

    public static void main(String[] args) {
        CIMClient cimClient = new CIMClient();
        cimClient.start();

        WUProto.WUProtocol protocol = WUProto.WUProtocol.newBuilder()
                .setMsgType(Constants.CommandType.LOGIN)
                .setSendUserId(1)
                .setReceiveUserId(0)
                .build();
        channel.writeAndFlush(protocol).addListeners((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                LOGGER.error("IO error,close Channel");
                future.channel().close();
            }else{
                LOGGER.info("发送 Google Protocol Msg成功!");
            }
        });
    }
}
