package io.netty.handler.ipfilter;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.ConcurrentSet;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class UniqueIpFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {
    private final Set<InetAddress> a = new ConcurrentSet();

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean accept(ChannelHandlerContext channelHandlerContext, InetSocketAddress inetSocketAddress) throws Exception {
        final InetAddress address = inetSocketAddress.getAddress();
        if (this.a.contains(address)) {
            return false;
        }
        this.a.add(address);
        channelHandlerContext.channel().closeFuture().addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.ipfilter.UniqueIpFilter.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                UniqueIpFilter.this.a.remove(address);
            }
        });
        return true;
    }
}
