package io.netty.channel;

import io.netty.util.concurrent.EventExecutorGroup;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public interface ChannelPipeline extends ChannelInboundInvoker, ChannelOutboundInvoker, Iterable<Map.Entry<String, ChannelHandler>> {
    ChannelPipeline addAfter(EventExecutorGroup eventExecutorGroup, String str, String str2, ChannelHandler channelHandler);

    ChannelPipeline addAfter(String str, String str2, ChannelHandler channelHandler);

    ChannelPipeline addBefore(EventExecutorGroup eventExecutorGroup, String str, String str2, ChannelHandler channelHandler);

    ChannelPipeline addBefore(String str, String str2, ChannelHandler channelHandler);

    ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, String str, ChannelHandler channelHandler);

    ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, ChannelHandler... channelHandlerArr);

    ChannelPipeline addFirst(String str, ChannelHandler channelHandler);

    ChannelPipeline addFirst(ChannelHandler... channelHandlerArr);

    ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, String str, ChannelHandler channelHandler);

    ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, ChannelHandler... channelHandlerArr);

    ChannelPipeline addLast(String str, ChannelHandler channelHandler);

    ChannelPipeline addLast(ChannelHandler... channelHandlerArr);

    Channel channel();

    ChannelHandlerContext context(ChannelHandler channelHandler);

    ChannelHandlerContext context(Class<? extends ChannelHandler> cls);

    ChannelHandlerContext context(String str);

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelPipeline fireChannelActive();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelPipeline fireChannelInactive();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelPipeline fireChannelRead(Object obj);

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelPipeline fireChannelReadComplete();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelPipeline fireChannelRegistered();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelPipeline fireChannelUnregistered();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelPipeline fireChannelWritabilityChanged();

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelPipeline fireExceptionCaught(Throwable th);

    @Override // io.netty.channel.ChannelInboundInvoker
    ChannelPipeline fireUserEventTriggered(Object obj);

    ChannelHandler first();

    ChannelHandlerContext firstContext();

    @Override // io.netty.channel.ChannelOutboundInvoker
    ChannelPipeline flush();

    <T extends ChannelHandler> T get(Class<T> cls);

    ChannelHandler get(String str);

    ChannelHandler last();

    ChannelHandlerContext lastContext();

    List<String> names();

    <T extends ChannelHandler> T remove(Class<T> cls);

    ChannelHandler remove(String str);

    ChannelPipeline remove(ChannelHandler channelHandler);

    ChannelHandler removeFirst();

    ChannelHandler removeLast();

    <T extends ChannelHandler> T replace(Class<T> cls, String str, ChannelHandler channelHandler);

    ChannelHandler replace(String str, String str2, ChannelHandler channelHandler);

    ChannelPipeline replace(ChannelHandler channelHandler, String str, ChannelHandler channelHandler2);

    Map<String, ChannelHandler> toMap();
}
