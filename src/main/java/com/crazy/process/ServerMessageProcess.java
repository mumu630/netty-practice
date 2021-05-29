package com.crazy.process;

import com.crazy.common.IMServerContext;
import com.crazy.proto.IMMsg;
import io.netty.channel.Channel;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.Callable;

public class ServerMessageProcess implements Callable {

    private Channel channel;
    private IMMsg.MessageRequest messageRequest;

    public ServerMessageProcess(Channel channel, IMMsg.IMMessage imMessage) {
        this.channel = channel;
        this.messageRequest = IMMsg.MessageRequest.newBuilder(imMessage.getMessageRequest()).build();
    }

    @Override
    public Object call() throws Exception {
        String sendSid = messageRequest.getSendSid();
        String receiveUsername = messageRequest.getReceiveUsername();
        String content = messageRequest.getContent();
        if (IMServerContext.sidMapUsername.containsKey(sendSid)) {
            String sendUsername = IMServerContext.sidMapUsername.get(sendSid);
            Set<String> sidSet = IMServerContext.usernameMapSidSet.get(receiveUsername);
            if (!CollectionUtils.isEmpty(sidSet)) {
                IMMsg.MessageNotification notification = IMMsg.MessageNotification.newBuilder()
                        .setSenderUsername(sendUsername).setContent(content).setSenderUsername(sendUsername).build();
                IMMsg.IMMessage imMessage = IMMsg.IMMessage.newBuilder()
                        .setHeadType(IMMsg.HeadType.MESSAGE_NOTIFICATION)
                        .setMessageNotification(notification).build();

                sidSet.forEach(peerSid -> {
                    Channel ch = IMServerContext.sidMapChannel.get(peerSid);
                    System.out.println("transfer sid=" + peerSid);
                    ch.writeAndFlush(imMessage);
                });
            }
        }
        IMMsg.MessageResponse messageResponse = IMMsg.MessageResponse.newBuilder()
                .setCode(0).setMsg("already received").build();
        IMMsg.IMMessage imMessage = IMMsg.IMMessage.newBuilder().setMessageResponse(messageResponse)
                .setHeadType(IMMsg.HeadType.MESSAGE_RESPONSE).build();
        return channel.writeAndFlush(imMessage);
    }
}
