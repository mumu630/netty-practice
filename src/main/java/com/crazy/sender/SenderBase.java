package com.crazy.sender;

import com.crazy.proto.IMMsg;
import io.netty.channel.Channel;


public interface SenderBase {

    void send(Channel channel, IMMsg.IMMessage imMessage);

}
