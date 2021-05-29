package com.crazy.printer;

import com.crazy.common.IMClientContext;
import com.crazy.proto.IMMsg;

public class LoginResponseProcess extends ProcessBase {

    @Override
    public void process(IMMsg.IMMessage imMessage) {
        IMMsg.LoginResponse loginResponse = imMessage.getLoginResponse();

        String loginReturnMsg;
        if (loginResponse.getCode() == 0) {
            loginReturnMsg = String.format("登录成功, %s", loginResponse.getMsg());
            String sid = loginResponse.getSid();
            String username = loginResponse.getUsername();
            IMClientContext.clientSession.setSid(sid);
            IMClientContext.clientSession.setLogined(true);
            IMClientContext.clientSession.setUsername(username);
        } else {
            loginReturnMsg = String.format("登录失败, %s", loginResponse.getMsg());
        }
        System.out.println(loginReturnMsg);
    }

}
