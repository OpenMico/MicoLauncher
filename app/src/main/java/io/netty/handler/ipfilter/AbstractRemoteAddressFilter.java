package io.netty.handler.ipfilter;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public abstract class AbstractRemoteAddressFilter<T extends SocketAddress> extends ChannelInboundHandlerAdapter {
    protected abstract boolean accept(ChannelHandlerContext channelHandlerContext, T t) throws Exception;

    protected void channelAccepted(ChannelHandlerContext channelHandlerContext, T t) {
    }

    protected ChannelFuture channelRejected(ChannelHandlerContext channelHandlerContext, T t) {
        return null;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        a(channelHandlerContext);
        channelHandlerContext.fireChannelRegistered();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (a(channelHandlerContext)) {
            channelHandlerContext.fireChannelActive();
            return;
        }
        throw new IllegalStateException("cannot determine to accept or reject a channel: " + channelHandlerContext.channel());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean a(ChannelHandlerContext channelHandlerContext) throws Exception {
        SocketAddress remoteAddress = channelHandlerContext.channel().remoteAddress();
        if (remoteAddress == null) {
            return false;
        }
        channelHandlerContext.pipeline().remove(this);
        if (accept(channelHandlerContext, remoteAddress)) {
            channelAccepted(channelHandlerContext, remoteAddress);
            return true;
        }
        ChannelFuture channelRejected = channelRejected(channelHandlerContext, remoteAddress);
        if (channelRejected != null) {
            channelRejected.addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE);
            return true;
        }
        channelHandlerContext.close();
        return true;
    }
}
