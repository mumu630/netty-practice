package com.crazy.client;

import com.crazy.command.CommandClient;
import com.crazy.common.ClientSession;
import com.crazy.common.IMClientContext;
import com.crazy.handle.IMClientProcessHandler;
import com.crazy.proto.IMMsg;
import com.crazy.sender.SenderClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;

public class IMClient {
    private CommandClient commandClient;
    private SenderClient senderClient;


    public void start() throws InterruptedException {
        EventLoopGroup workGroup = null;
        try {
            Bootstrap bootstrap = new Bootstrap();
            workGroup = new NioEventLoopGroup();

            bootstrap.channel(NioSocketChannel.class).group(workGroup).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline()
                            .addLast(new ProtobufVarint32LengthFieldPrepender())
                            .addLast(new ProtobufEncoder());
                    socketChannel.pipeline()
                            .addLast(new ProtobufVarint32FrameDecoder())
                            .addLast(new ProtobufDecoder(IMMsg.IMMessage.newBuilder().build()))
                            .addLast(new IMClientProcessHandler());
                }
            });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(9999)).sync();
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    ClientSession clientSession = new ClientSession();
                    clientSession.setConnected(true);
                    IMClientContext.clientSession = clientSession;
                }
            });
            System.out.println("already connected to 127.0.0.1:9999");

            commandClient = new CommandClient();
            senderClient = new SenderClient();

            boolean loop = true;
            while(loop){
                IMMsg.IMMessage imMessage = commandClient.input();
                senderClient.send(future.channel(), imMessage);

                Thread.sleep(1000);
            }
            future.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
