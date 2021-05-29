package com.crazy.command;

import com.crazy.common.IMClientContext;
import com.crazy.proto.IMMsg;

import java.util.Scanner;

public class MessageCommand implements CommandBase {

    @Override
    public IMMsg.IMMessage execute(Scanner in) {
        System.out.println("请输入发送消息(用户名&内容)");
        while (in.hasNextLine()) {
            String msg = in.nextLine();
            String[] msgArray = msg.split("&");
            if (msgArray.length != 2) {
                System.out.println("数据格式错误，请输入发送消息(用户名&内容)");
            }
            String receiveUsername = msgArray[0];
            String content = msgArray[1];
            String senderSid = IMClientContext.clientSession.getSid();
            IMMsg.MessageRequest messageRequest = IMMsg.MessageRequest.newBuilder()
                    .setReceiveUsername(receiveUsername).setSendSid(senderSid).setContent(content).build();
            IMMsg.IMMessage imMessage = IMMsg.IMMessage.newBuilder()
                    .setHeadType(IMMsg.HeadType.MESSAGE_REQUEST).setMessageRequest(messageRequest).build();
            return imMessage;
        }
        return null;
    }
}
