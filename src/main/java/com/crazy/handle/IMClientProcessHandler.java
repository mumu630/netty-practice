package com.crazy.handle;

import com.crazy.printer.ProcessBase;
import com.crazy.proto.IMMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class IMClientProcessHandler extends MessageToMessageDecoder<IMMsg.IMMessage> {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, IMMsg.IMMessage imMessage, List<Object> list)
            throws Exception {
        dispath(imMessage);
    }

    void dispath(IMMsg.IMMessage imMessage) {
        ProcessBase.execute(imMessage);
    }
}
