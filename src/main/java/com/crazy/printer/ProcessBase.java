package com.crazy.printer;

import com.crazy.proto.IMMsg;

import java.util.HashMap;
import java.util.Map;

public abstract class ProcessBase {

    public static Map<Integer, ProcessBase> headTypeMapProcessBase = new HashMap<>();

    static {
        headTypeMapProcessBase.put(IMMsg.HeadType.LOGIN_RESPONSE_VALUE, new LoginResponseProcess());
        headTypeMapProcessBase.put(IMMsg.HeadType.LOGOUT_RESPONSE_VALUE, new LogoutResponseProcess());
        headTypeMapProcessBase.put(IMMsg.HeadType.MESSAGE_RESPONSE_VALUE, new MessageResponseProcess());
        headTypeMapProcessBase.put(IMMsg.HeadType.MESSAGE_NOTIFICATION_VALUE,new NotificationResponseProcess());
    }

    public static void execute(IMMsg.IMMessage imMessage) {
        Integer headTypeNumber = imMessage.getHeadType().getNumber();
        if (!headTypeMapProcessBase.containsKey(headTypeNumber)) {
            System.out.println("未找到相应的ProcessBase子类");
        }
        ProcessBase processBase = headTypeMapProcessBase.get(headTypeNumber);
        processBase.process(imMessage);
    }

    public abstract void process(IMMsg.IMMessage imMessage);

}
