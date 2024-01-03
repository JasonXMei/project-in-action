package com.jason.nettydemo.util;

import com.google.protobuf.ByteString;
import com.jason.nettydemo.constant.Constants;
import com.jason.nettydemo.protocol.WUProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2019/1/9 00:57
 * @since JDK 1.8
 */
public class NettyUtil {

    private static final AttributeKey<String> ATTR_KEY_READER_TIME = AttributeKey.valueOf("readerTime");
    private final static Logger LOGGER = LoggerFactory.getLogger(NettyUtil.class);

    public static void updateReaderTime(Channel channel, Long time) {
        channel.attr(ATTR_KEY_READER_TIME).set(time.toString());
    }

    public static Long getReaderTime(Channel channel) {
        String value = getAttribute(channel, ATTR_KEY_READER_TIME);

        if (value != null) {
            return Long.valueOf(value);
        }
        return null;
    }


    private static String getAttribute(Channel channel, AttributeKey<String> key) {
        Attribute<String> attr = channel.attr(key);
        return attr.get();
    }

    /**
     * 发送 Google Protocol 编解码字符串
     */
    public static void sendGoogleProtocolMsg(int msgType, int sendUserId, int receiveUserId, byte[] screenImg, byte[] userEvent, NioSocketChannel nioSocketChannel) {
        WUProto.WUProtocol protocol = null;
        switch (msgType){
            case Constants.CommandType.PING:
            case Constants.CommandType.PONG:
            case Constants.CommandType.LOGIN:
            default:
                protocol = WUProto.WUProtocol.newBuilder()
                        .setMsgType(msgType)
                        .setSendUserId(sendUserId)
                        .build();
                break;
            case Constants.CommandType.MSG_IMG:
                protocol = WUProto.WUProtocol.newBuilder()
                        .setMsgType(msgType)
                        .setSendUserId(sendUserId)
                        .setReceiveUserId(receiveUserId)
                        .setScreenImg(ByteString.copyFrom(screenImg))
                        .build();
                break;
            case Constants.CommandType.MSG_EVENT:
                protocol = WUProto.WUProtocol.newBuilder()
                        .setMsgType(msgType)
                        .setSendUserId(sendUserId)
                        .setReceiveUserId(receiveUserId)
                        .setScreenImg(ByteString.copyFrom(userEvent))
                        .build();
        }

        nioSocketChannel.writeAndFlush(protocol).addListeners((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                LOGGER.error("IO error,close Channel");
                future.channel().close();
            }else{
                LOGGER.info("发送 Google Protocol Msg成功!");
            }
        });
    }

    /**
     * 发送 Google Protocol 编解码字符串
     */
    public static void sendGoogleProtocolMsg(int msgType, int sendUserId, int receiveUserId, byte[] screenImg, byte[] userEvent) {
        WUProto.WUProtocol protocol = null;
        switch (msgType){
            case Constants.CommandType.PING:
            case Constants.CommandType.PONG:
            case Constants.CommandType.LOGIN:
            case Constants.CommandType.MSG_CONTROL:
            default:
                protocol = WUProto.WUProtocol.newBuilder()
                        .setMsgType(msgType)
                        .setSendUserId(sendUserId)
                        .build();
                break;
            case Constants.CommandType.MSG_IMG:
                protocol = WUProto.WUProtocol.newBuilder()
                        .setMsgType(msgType)
                        .setSendUserId(sendUserId)
                        .setReceiveUserId(receiveUserId)
                        .setScreenImg(ByteString.copyFrom(screenImg))
                        .build();
                break;
            case Constants.CommandType.MSG_EVENT:
                protocol = WUProto.WUProtocol.newBuilder()
                        .setMsgType(msgType)
                        .setSendUserId(sendUserId)
                        .setReceiveUserId(receiveUserId)
                        .setScreenImg(ByteString.copyFrom(userEvent))
                        .build();
        }

        NioSocketChannel nioSocketChannel = SocketHandler.getUse(receiveUserId);
        nioSocketChannel.writeAndFlush(protocol).addListeners((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                LOGGER.error("IO error,close Channel");
                future.channel().close();
            }else{
                LOGGER.info("发送 Google Protocol Msg成功!");
            }
        });
    }
}
