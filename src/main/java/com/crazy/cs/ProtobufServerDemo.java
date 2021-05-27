package com.crazy.cs;

import com.crazy.handle.ProtobufBussinessDecoder;
import com.crazy.proto.Practice;import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

import java.net.InetSocketAddress;

public class ProtobufServerDemo {

    public static void main(String[] args)  throws InterruptedException  {
        new ProtobufServerDemo().startServer();
    }

    public void startServer() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class)
                    .group(bossGroup, workGroup)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(Practice.Person.newBuilder().build()))
                                    .addLast(new ProtobufBussinessDecoder());
                        }
                    });

            ChannelFuture channelFuture =  serverBootstrap.bind(new InetSocketAddress(9999)).sync();
            System.out.println("server started, ready to accept connect, ip:127.0.0.1, port:9999");

            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
