package com.crazy.printer;

import com.crazy.common.IMClientContext;
import com.crazy.proto.IMMsg;

public class LogoutResponseProcess extends ProcessBase {

    @Override
    public void process(IMMsg.IMMessage imMessage) {
        IMMsg.LogoutResponse logoutResponse = imMessage.getLogoutResponse();

        String logoutReturnMsg;
        if (logoutResponse.getCode() == 0) {
            logoutReturnMsg = String.format("登出成功, %s", logoutResponse.getMsg());
            IMClientContext.clientSession.setSid(null);
            IMClientContext.clientSession.setLogined(false);
        } else {
            logoutReturnMsg = String.format("登录失败, %s", logoutResponse.getMsg());
        }
        System.out.println(logoutReturnMsg);
    }

}
