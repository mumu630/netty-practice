package com.crazy.printer;

import com.crazy.proto.IMMsg;

public class MessageResponseProcess extends ProcessBase {

    @Override
    public void process(IMMsg.IMMessage imMessage) {
        IMMsg.MessageResponse messageResponse = imMessage.getMessageResponse();

        String logoutReturnMsg = String.format("[Message]收到消息发送回复， %s", messageResponse.getMsg());
        System.out.println(logoutReturnMsg);
    }

}
