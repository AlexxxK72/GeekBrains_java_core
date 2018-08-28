package com.flamexander.netty.servers.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

public class BroadcastHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channels;

    public BroadcastHandler(ChannelGroup channels) {
        this.channels = channels;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

}
