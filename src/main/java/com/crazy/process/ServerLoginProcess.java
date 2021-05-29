package com.crazy.process;

import com.crazy.common.IMServerContext;
import com.crazy.proto.IMMsg;
import io.netty.channel.Channel;

import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.Callable;

public class ServerLoginProcess implements Callable {

    private Channel channel;
    private IMMsg.LoginRequest loginRequest;

    public ServerLoginProcess(Channel channel, IMMsg.IMMessage imMessage) {
        this.channel = channel;
        this.loginRequest = IMMsg.LoginRequest.newBuilder(imMessage.getLoginRequest()).build();
    }

    @Override
    public Object call() throws Exception {
        String sid = UUID.randomUUID().toString();
        String username = loginRequest.getUsername();

        IMServerContext.sidMapUsername.put(sid, username);
        if (!IMServerContext.usernameMapSidSet.containsKey(username)) {
            IMServerContext.usernameMapSidSet.put(username, new HashSet<>());
        }
        IMServerContext.usernameMapSidSet.get(username).add(sid);
        IMServerContext.sidMapChannel.put(sid, channel);

        IMMsg.LoginResponse loginResponse = IMMsg.LoginResponse.newBuilder()
                .setCode(0)
                .setSid(sid)
                .setUsername(username)
                .setMsg("welcome," + username)
                .build();
        IMMsg.IMMessage imMessage = IMMsg.IMMessage.newBuilder()
                .setHeadType(IMMsg.HeadType.LOGIN_RESPONSE)
                .setLoginResponse(loginResponse).build();
        return channel.writeAndFlush(imMessage);
    }
}
