package com.crazy.bootstrap;

import com.crazy.client.IMServer;

public class IMServerBootstrap {

    public static void main(String[] args) throws InterruptedException {
        new IMServer().startServer();
    }
}
