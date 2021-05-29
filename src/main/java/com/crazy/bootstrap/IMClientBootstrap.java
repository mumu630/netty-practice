package com.crazy.bootstrap;

import com.crazy.client.IMClient;

public class IMClientBootstrap {

    public static void main(String []args) throws InterruptedException {
        new IMClient().start();
    }
}
