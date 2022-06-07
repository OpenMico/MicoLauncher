package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;

/* loaded from: classes4.dex */
public interface EventLoop extends EventLoopGroup, EventExecutor {
    EventLoopGroup parent();
}
