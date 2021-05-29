package com.crazy.handle;

import com.crazy.common.BusinessThreadPool;
import com.crazy.process.ServerLogoutProcess;
import com.crazy.proto.IMMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class IMServerLogoutHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        IMMsg.IMMessage imMessage = (IMMsg.IMMessage)msg;
        if(imMessage.getHeadType().getNumber() == IMMsg.HeadType.LOGOUT_REQUEST_VALUE){
            process(ctx.channel() ,imMessage);
            return;
        }
        super.channelRead(ctx, msg);
    }

    private void process(Channel channel, IMMsg.IMMessage imMessage){
        Callable callable = new ServerLogoutProcess(channel,imMessage);
        BusinessThreadPool.submit(callable);
    }

}
