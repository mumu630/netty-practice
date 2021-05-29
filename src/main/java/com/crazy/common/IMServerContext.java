package com.crazy.common;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class IMServerContext {

    public static Map<String,String> sidMapUsername = new ConcurrentHashMap<>();

    public static Map<String, Set<String>> usernameMapSidSet= new ConcurrentHashMap<>();

    public static Map<String, Channel> sidMapChannel = new ConcurrentHashMap<>();

}
