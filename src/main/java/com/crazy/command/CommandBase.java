package com.crazy.command;

import com.crazy.proto.IMMsg;

import java.util.Scanner;

public interface CommandBase {

    IMMsg.IMMessage execute(Scanner in);
}
