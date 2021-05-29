package com.crazy.handle;

import com.crazy.proto.Practice;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtobufEncoder extends MessageToByteEncoder<Practice.Person> {

    final short MAGIC_NUMBER = 6;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Practice.Person person, ByteBuf byteBuf)
            throws Exception {
        byte[] contents = person.toByteArray();
        int length = contents.length;
        byteBuf.writeInt(length);
        byteBuf.writeShort(MAGIC_NUMBER);
        byteBuf.writeBytes(contents);
    }
}
