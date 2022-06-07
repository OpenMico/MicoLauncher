package io.netty.channel.local;

import io.netty.channel.DefaultEventLoopGroup;
import java.util.concurrent.ThreadFactory;

@Deprecated
/* loaded from: classes4.dex */
public class LocalEventLoopGroup extends DefaultEventLoopGroup {
    public LocalEventLoopGroup() {
    }

    public LocalEventLoopGroup(int i) {
        super(i);
    }

    public LocalEventLoopGroup(int i, ThreadFactory threadFactory) {
        super(i, threadFactory);
    }
}
