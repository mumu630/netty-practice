package com.crazy.command;

import com.crazy.proto.IMMsg;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandClient {

    static Map<Integer, CommandBase> kindMapCommandBase = new HashMap<>();

    static {
        kindMapCommandBase.put(IMMsg.HeadType.LOGIN_REQUEST_VALUE, new LoginCommand());
        kindMapCommandBase.put(IMMsg.HeadType.LOGOUT_REQUEST_VALUE, new LogoutCommand());
        kindMapCommandBase.put(IMMsg.HeadType.MESSAGE_REQUEST_VALUE, new MessageCommand());
    }

    public IMMsg.IMMessage input() {
        String nav = String.format("请选择操作类型，登录：%d， 登出：%d， 消息:%d", IMMsg.HeadType.LOGIN_REQUEST_VALUE
                , IMMsg.HeadType.LOGOUT_REQUEST_VALUE, IMMsg.HeadType.MESSAGE_REQUEST_VALUE);

        System.out.println(nav);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int kind = Integer.parseInt(scanner.nextLine());
            return kindMapCommandBase.get(kind).execute(scanner);
        }
        return null;
    }
}
