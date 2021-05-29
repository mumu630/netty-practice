package com.crazy.command;

import com.crazy.proto.IMMsg;

import java.util.Scanner;

public class LoginCommand implements CommandBase {

    @Override
    public IMMsg.IMMessage execute(Scanner in) {
        System.out.println("请输入登录信息(用户名&密码)");
        while (in.hasNextLine()) {
            String msg = in.nextLine();
            String[] msgArray = msg.split("&");
            if (msgArray.length != 2) {
                System.out.println("数据格式错误，请输入登录信息(用户名&密码)");
                continue;
            }
            String username = msgArray[0];
            String passwd = msgArray[1];
            IMMsg.LoginRequest loginRequest = IMMsg.LoginRequest.newBuilder().setUsername(username)
                    .setPassword(passwd).build();
            IMMsg.IMMessage imMessage = IMMsg.IMMessage.newBuilder()
                    .setHeadType(IMMsg.HeadType.LOGIN_REQUEST).setLoginRequest(loginRequest).build();
            return imMessage;
        }
        return null;
    }

}
