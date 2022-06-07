package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;

/* compiled from: SucceededChannelFuture.java */
/* loaded from: classes4.dex */
final class f extends b {
    @Override // io.netty.util.concurrent.Future
    public Throwable cause() {
        return null;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean isSuccess() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(Channel channel, EventExecutor eventExecutor) {
        super(channel, eventExecutor);
    }
}
