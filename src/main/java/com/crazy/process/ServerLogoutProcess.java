package com.crazy.process;

import com.crazy.common.IMServerContext;
import com.crazy.proto.IMMsg;
import io.netty.channel.Channel;

import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.Callable;

public class ServerLogoutProcess implements Callable {

    private Channel channel;
    private IMMsg.LogoutRequest logoutRequest;

    public ServerLogoutProcess(Channel channel, IMMsg.IMMessage imMessage) {
        this.channel = channel;
        this.logoutRequest = IMMsg.LogoutRequest.newBuilder(imMessage.getLogoutRequest()).build();
    }

    @Override
    public Object call() throws Exception {
        String sid = logoutRequest.getSid();
        String usename = null;
        if (IMServerContext.sidMapUsername.containsKey(sid)) {
            usename = IMServerContext.sidMapUsername.get(sid);

            IMServerContext.sidMapUsername.remove(sid);
            if (IMServerContext.usernameMapSidSet.containsKey(usename)) {
                IMServerContext.usernameMapSidSet.get(usename).remove(sid);
            }

            if (IMServerContext.sidMapChannel.containsKey(sid)) {
                IMServerContext.sidMapChannel.remove(sid);
            }
        }
        IMMsg.LogoutResponse logoutResponse = IMMsg.LogoutResponse.newBuilder()
                .setCode(0).setMsg("ByeBye," + usename).build();
        IMMsg.IMMessage imMessage = IMMsg.IMMessage.newBuilder()
                .setHeadType(IMMsg.HeadType.LOGOUT_RESPONSE)
                .setLogoutResponse(logoutResponse).build();
        return channel.writeAndFlush(imMessage);
    }
}
