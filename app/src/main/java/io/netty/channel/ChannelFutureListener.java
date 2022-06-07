package io.netty.channel;

import io.netty.util.concurrent.GenericFutureListener;

/* loaded from: classes4.dex */
public interface ChannelFutureListener extends GenericFutureListener<ChannelFuture> {
    public static final ChannelFutureListener CLOSE = new ChannelFutureListener() { // from class: io.netty.channel.ChannelFutureListener.1
        /* renamed from: a */
        public void operationComplete(ChannelFuture channelFuture) {
            channelFuture.channel().close();
        }
    };
    public static final ChannelFutureListener CLOSE_ON_FAILURE = new ChannelFutureListener() { // from class: io.netty.channel.ChannelFutureListener.2
        /* renamed from: a */
        public void operationComplete(ChannelFuture channelFuture) {
            if (!channelFuture.isSuccess()) {
                channelFuture.channel().close();
            }
        }
    };
    public static final ChannelFutureListener FIRE_EXCEPTION_ON_FAILURE = new ChannelFutureListener() { // from class: io.netty.channel.ChannelFutureListener.3
        /* renamed from: a */
        public void operationComplete(ChannelFuture channelFuture) {
            if (!channelFuture.isSuccess()) {
                channelFuture.channel().pipeline().fireExceptionCaught(channelFuture.cause());
            }
        }
    };
}
