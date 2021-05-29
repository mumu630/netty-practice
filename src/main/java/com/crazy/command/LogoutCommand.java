package com.crazy.command;

import com.crazy.common.IMClientContext;
import com.crazy.proto.IMMsg;

import java.util.Scanner;

public class LogoutCommand implements CommandBase{

    @Override
    public IMMsg.IMMessage execute(Scanner in) {
        System.out.println("请输入登出信息(用户名)");
        while (in.hasNextLine()) {
            String username = in.nextLine();
            if(IMClientContext.clientSession.isConnected()
                    && !IMClientContext.clientSession.getUsername().equals(username)){
                System.out.println("数据信息错误，请输入登出信息用户名)");
            }
            String sid = IMClientContext.clientSession.getSid();
            IMMsg.LogoutRequest logoutRequest = IMMsg.LogoutRequest.newBuilder().setSid(sid).build();
            IMMsg.IMMessage imMessage = IMMsg.IMMessage.newBuilder()
                    .setHeadType(IMMsg.HeadType.LOGOUT_REQUEST).setLogoutRequest(logoutRequest).build();
            return imMessage;
        }
        return null;
    }
}
