package io.netty.channel;

import io.netty.util.concurrent.PromiseNotifier;

/* loaded from: classes4.dex */
public final class ChannelPromiseNotifier extends PromiseNotifier<Void, ChannelFuture> implements ChannelFutureListener {
    public ChannelPromiseNotifier(ChannelPromise... channelPromiseArr) {
        super(channelPromiseArr);
    }
}
