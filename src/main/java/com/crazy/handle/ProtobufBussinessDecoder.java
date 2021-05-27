package com.crazy.handle;

import com.crazy.proto.Practice;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtobufBussinessDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Practice.Person person = (Practice.Person)msg;
        String printMsg = String.format("收到数据，姓名:%s,年龄:%d,邮箱:%s",person.getName(),person.getAge(),person.getEmail());
        System.out.println(printMsg);
    }
}
