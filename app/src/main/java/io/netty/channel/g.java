package io.netty.channel;

import io.netty.util.concurrent.AbstractFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.concurrent.TimeUnit;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: VoidChannelPromise.java */
/* loaded from: classes4.dex */
public final class g extends AbstractFuture<Void> implements ChannelPromise {
    private final Channel a;
    private final boolean b;

    /* renamed from: a */
    public g setSuccess(Void r1) {
        return this;
    }

    /* renamed from: b */
    public g removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        return this;
    }

    /* renamed from: b */
    public g removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        return this;
    }

    /* renamed from: b */
    public boolean trySuccess(Void r1) {
        return false;
    }

    @Override // io.netty.util.concurrent.Future, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return false;
    }

    @Override // io.netty.util.concurrent.Future
    public Throwable cause() {
        return null;
    }

    /* renamed from: e */
    public g setSuccess() {
        return this;
    }

    /* renamed from: f */
    public Void getNow() {
        return null;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean isCancellable() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return false;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean isSuccess() {
        return false;
    }

    @Override // io.netty.channel.ChannelFuture
    public boolean isVoid() {
        return true;
    }

    @Override // io.netty.util.concurrent.Promise
    public boolean setUncancellable() {
        return true;
    }

    @Override // io.netty.channel.ChannelPromise
    public boolean trySuccess() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(Channel channel, boolean z) {
        if (channel != null) {
            this.a = channel;
            this.b = z;
            return;
        }
        throw new NullPointerException("channel");
    }

    /* renamed from: a */
    public g addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        g();
        return this;
    }

    /* renamed from: a */
    public g addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        g();
        return this;
    }

    /* renamed from: a */
    public g await() throws InterruptedException {
        if (!Thread.interrupted()) {
            return this;
        }
        throw new InterruptedException();
    }

    @Override // io.netty.util.concurrent.Future
    public boolean await(long j, TimeUnit timeUnit) {
        g();
        return false;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean await(long j) {
        g();
        return false;
    }

    /* renamed from: b */
    public g awaitUninterruptibly() {
        g();
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean awaitUninterruptibly(long j, TimeUnit timeUnit) {
        g();
        return false;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean awaitUninterruptibly(long j) {
        g();
        return false;
    }

    @Override // io.netty.channel.ChannelPromise, io.netty.channel.ChannelFuture
    public Channel channel() {
        return this.a;
    }

    /* renamed from: c */
    public g sync() {
        g();
        return this;
    }

    /* renamed from: d */
    public g syncUninterruptibly() {
        g();
        return this;
    }

    /* renamed from: a */
    public g setFailure(Throwable th) {
        b(th);
        return this;
    }

    @Override // io.netty.util.concurrent.Promise
    public boolean tryFailure(Throwable th) {
        b(th);
        return false;
    }

    private static void g() {
        throw new IllegalStateException("void future");
    }

    @Override // io.netty.channel.ChannelPromise
    public ChannelPromise unvoid() {
        DefaultChannelPromise defaultChannelPromise = new DefaultChannelPromise(this.a);
        if (this.b) {
            defaultChannelPromise.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.channel.g.1
                /* renamed from: a */
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()) {
                        g.this.b(channelFuture.cause());
                    }
                }
            });
        }
        return defaultChannelPromise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Throwable th) {
        if (this.b && this.a.isRegistered()) {
            this.a.pipeline().fireExceptionCaught(th);
        }
    }
}
