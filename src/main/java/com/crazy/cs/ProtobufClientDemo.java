package com.crazy.cs;

import com.crazy.proto.Practice;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ProtobufClientDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        new ProtobufClientDemo().startClient();
    }

    public void startClient() throws InterruptedException, IOException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        bootstrap.channel(NioSocketChannel.class).group(workGroup).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender())
                        .addLast(new ProtobufEncoder());
            }
        });
        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(9999)).sync();
        System.out.println("connected to 127.0.0.1:9999");

        Practice.Person person = Practice.Person.newBuilder().setAge(30).setName("张三").setEmail("zhangsan@gmail.com").build();
        for (int i = 0; i < 100; i++) {
            channelFuture.channel().writeAndFlush(person);
        }

        channelFuture.channel().closeFuture().sync();
    }
}
