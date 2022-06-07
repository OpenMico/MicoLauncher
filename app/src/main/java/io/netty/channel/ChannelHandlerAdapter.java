package io.netty.channel;

import io.netty.channel.ChannelHandler;
import io.netty.util.internal.InternalThreadLocalMap;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class ChannelHandlerAdapter implements ChannelHandler {
    boolean added;

    @Override // io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    @Override // io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    public boolean isSharable() {
        Class<?> cls = getClass();
        Map<Class<?>, Boolean> handlerSharableCache = InternalThreadLocalMap.get().handlerSharableCache();
        Boolean bool = handlerSharableCache.get(cls);
        if (bool == null) {
            bool = Boolean.valueOf(cls.isAnnotationPresent(ChannelHandler.Sharable.class));
            handlerSharableCache.put(cls, bool);
        }
        return bool.booleanValue();
    }

    @Override // io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        channelHandlerContext.fireExceptionCaught(th);
    }
}
