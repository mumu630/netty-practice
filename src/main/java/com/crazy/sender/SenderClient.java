package com.crazy.sender;

import com.crazy.proto.IMMsg;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class SenderClient {
    private static Map<Integer,SenderBase> kindMapSenderBase = new HashMap<>();

    static{
        kindMapSenderBase.put(IMMsg.HeadType.LOGIN_REQUEST_VALUE,new LoginSender());
        kindMapSenderBase.put(IMMsg.HeadType.LOGOUT_REQUEST_VALUE,new LogoutSender());
        kindMapSenderBase.put(IMMsg.HeadType.MESSAGE_REQUEST_VALUE,new MessageSender());
    }

    public void send(Channel channel, IMMsg.IMMessage imMessage){
        if(!kindMapSenderBase.containsKey(imMessage.getHeadTypeValue())){
            System.out.println("未找到对应的Sender");
            return;
        }
        kindMapSenderBase.get(imMessage.getHeadTypeValue()).send(channel, imMessage);
    }
}
