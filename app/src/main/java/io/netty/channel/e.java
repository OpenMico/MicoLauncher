package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.PlatformDependent;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FailedChannelFuture.java */
/* loaded from: classes4.dex */
public final class e extends b {
    private final Throwable a;

    @Override // io.netty.util.concurrent.Future
    public boolean isSuccess() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Channel channel, EventExecutor eventExecutor, Throwable th) {
        super(channel, eventExecutor);
        if (th != null) {
            this.a = th;
            return;
        }
        throw new NullPointerException("cause");
    }

    @Override // io.netty.util.concurrent.Future
    public Throwable cause() {
        return this.a;
    }

    @Override // io.netty.channel.b, io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    /* renamed from: sync */
    public Future<Void> sync2() {
        PlatformDependent.throwException(this.a);
        return this;
    }

    @Override // io.netty.channel.b, io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    /* renamed from: syncUninterruptibly */
    public Future<Void> syncUninterruptibly2() {
        PlatformDependent.throwException(this.a);
        return this;
    }
}
