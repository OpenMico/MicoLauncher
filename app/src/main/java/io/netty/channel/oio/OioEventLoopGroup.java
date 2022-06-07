package io.netty.channel.oio;

import io.netty.channel.ThreadPerChannelEventLoopGroup;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes4.dex */
public class OioEventLoopGroup extends ThreadPerChannelEventLoopGroup {
    public OioEventLoopGroup() {
        this(0);
    }

    public OioEventLoopGroup(int i) {
        this(i, Executors.defaultThreadFactory());
    }

    public OioEventLoopGroup(int i, Executor executor) {
        super(i, executor, new Object[0]);
    }

    public OioEventLoopGroup(int i, ThreadFactory threadFactory) {
        super(i, threadFactory, new Object[0]);
    }
}
