package com.crazy.printer;

import com.crazy.proto.IMMsg;

public class NotificationResponseProcess extends ProcessBase {

    @Override
    public void process(IMMsg.IMMessage imMessage) {
        IMMsg.MessageNotification messageNotification = imMessage.getMessageNotification();
        String notifacationReturnMsg = String.format("[Message]收到%s的消息，内容:%s", messageNotification.getSenderUsername()
                , messageNotification.getContent());
        System.out.println(notifacationReturnMsg);
    }

}
