package com.jason.nettydemo.server;

import com.jason.nettydemo.Exception.CIMException;
import com.jason.nettydemo.constant.Constants;
import com.jason.nettydemo.protocol.WUProto;
import com.jason.nettydemo.util.ControlWindow;
import com.jason.nettydemo.util.NettyUtil;
import com.jason.nettydemo.util.SocketHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 17/05/2018 18:52
 * @since JDK 1.8
 */
@ChannelHandler.Sharable
public class CIMServerHandle extends SimpleChannelInboundHandler<WUProto.WUProtocol> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CIMServerHandle.class);

    ControlWindow controlWindow = new ControlWindow();

    /**
     * 取消绑定
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //可能出现业务判断离线后再次触发 channelInactive
        /*CIMUserInfo userInfo = SocketHandler.getUserId((NioSocketChannel) ctx.channel());
        if (userInfo != null){
            LOGGER.warn("[{}]触发 channelInactive 掉线!",userInfo.getUserName());
            userOffLine(userInfo, (NioSocketChannel) ctx.channel());
            ctx.channel().close();
        }*/
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        /*if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {

                LOGGER.info("定时检测客户端是否存活");

                HeartBeatHandler heartBeatHandler = SpringBeanFactory.getBean(ServerHeartBeatHandlerImpl.class) ;
                heartBeatHandler.process(ctx) ;
            }
        }
        super.userEventTriggered(ctx, evt);*/
    }

    /**
     * 用户下线
     * @param userInfo
     * @param channel
     * @throws IOException
     */
    /*private void userOffLine(CIMUserInfo userInfo, NioSocketChannel channel) throws IOException {
        LOGGER.info("用户[{}]下线", userInfo.getUserName());
        SocketHandler.remove(channel);
        SocketHandler.removeSession(userInfo.getUserId());

        //清除路由关系
        clearRouteInfo(userInfo);
    }*/

    /**
     * 下线，清除路由关系
     * @throws IOException
     */
    /*private void clearRouteInfo(CIMUserInfo userInfo) throws IOException {
        OkHttpClient okHttpClient = SpringBeanFactory.getBean(OkHttpClient.class);
        AppConfiguration configuration = SpringBeanFactory.getBean(AppConfiguration.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userInfo.getUserId());
        jsonObject.put("msg", "offLine");
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        Request request = new Request.Builder()
                .url(configuration.getClearRouteUrl())
                .post(requestBody)
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } finally {
            response.body().close();
        }
    }*/


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WUProto.WUProtocol msg) throws Exception {
        LOGGER.info("收到msg={}", msg.toString());

        if (msg.getMsgType() == Constants.CommandType.LOGIN) {
            //保存客户端与 Channel 之间的关系
            SocketHandler.putUse(msg.getSendUserId(), (NioSocketChannel) ctx.channel());
            //SocketHandler.saveSession(msg.getRequestId(), msg.getReqMsg());
            LOGGER.info("客户端[{}]上线成功", msg.toString());

            NettyUtil.sendGoogleProtocolMsg(Constants.CommandType.MSG_CONTROL, msg.getReceiveUserId(), msg.getSendUserId(), null, null);
        }

        //心跳更新时间
        if (msg.getMsgType() == Constants.CommandType.PING){
            //NettyUtil.updateReaderTime(ctx.channel(),System.currentTimeMillis());
            //向客户端响应 pong 消息
            NettyUtil.sendGoogleProtocolMsg(Constants.CommandType.PONG, 0, 1, null, null);
        }

        if (msg.getMsgType() == Constants.CommandType.MSG_IMG){
            //NettyUtil.sendGoogleProtocolMsg(Constants.CommandType.PONG, 0, 1, null, null, ctx);
            controlWindow.repainImage(msg.getScreenImg().toByteArray());
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (CIMException.isResetByPeer(cause.getMessage())) {
            return;
        }

        LOGGER.error(cause.getMessage(), cause);

    }

}
