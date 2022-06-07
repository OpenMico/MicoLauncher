package com.xiaomi.miot.host.lan.impl.client;

import android.util.Log;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
/* loaded from: classes2.dex */
public class MiotClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final String TAG = "MiotClientHandler";
    private MiotClientListener listener;

    public MiotClientHandler(MiotClientListener miotClientListener) {
        this.listener = miotClientListener;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelActive(channelHandlerContext);
        Log.e(TAG, "channelActive");
        this.listener.onConnected();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelInactive(channelHandlerContext);
        Log.e(TAG, "channelInactive");
        this.listener.onDisconnected();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        Log.d(TAG, "channelRead0");
        byte[] bArr = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bArr);
        this.listener.onReceiveMessage(channelHandlerContext, bArr);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelReadComplete(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        String message = th != null ? th.getMessage() : "";
        Log.d(TAG, "exceptionCaught: " + message);
        channelHandlerContext.close();
    }
}
