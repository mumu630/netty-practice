package com.crazy.handle;

import com.crazy.proto.Practice;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ProtobufDecoder extends ByteToMessageDecoder {
    final short MAGIC_NUMBER = 6;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
            throws Exception {
        if (byteBuf.isReadable()) {
            byteBuf.markReaderIndex();
            if (byteBuf.readableBytes() < 4) {
                return;
            }
            int length = byteBuf.readInt();
            if (byteBuf.readableBytes() < (2 + length)) {
                byteBuf.resetReaderIndex();
            }
            short magicNumber = byteBuf.readShort();
            if (magicNumber != MAGIC_NUMBER) {
                System.out.println("Magic number not match, do something, try again");
                byteBuf.resetReaderIndex();
                return;
            }
            byte[] bytes = new byte[length];
            byteBuf.readBytes(bytes, 0, length);
            Practice.Person person = Practice.Person.newBuilder().mergeFrom(bytes).build();
            list.add(person);
        }
    }
}
