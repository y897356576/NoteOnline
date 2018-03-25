package com.stone.demo.nettyCase;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by admin on 2018/3/25.
 */
public class NettyServer {

    private int port = 8080;

    private NettyServerHandler serverHandler = new NettyServerHandler();

    public NettyServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.localAddress(new InetSocketAddress(port));
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(serverHandler);
                    /*socketChannel.pipeline().addLast(new ChannelHandlerAdapter());
                    socketChannel.pipeline().addLast(new ChannelHandlerAdapter());*/
                }
            });
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println(NettyServer.class.getName() + " started and listening for " + future.channel().localAddress());
            future.channel().closeFuture().sync();
            System.out.println(NettyServer.class.getName() + " closed");
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) {
        try {
            new NettyServer(8888).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
