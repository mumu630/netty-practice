package com.crazy.sender;

import com.crazy.proto.IMMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class LogoutSender implements SenderBase {

    @Override
    public void send(Channel channel,IMMsg.IMMessage imMessage) {
        ChannelFuture channelFuture = channel.writeAndFlush(imMessage);
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                System.out.println("登出信息发送成功");
            }
        });
    }
    
}
