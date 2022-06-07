package io.netty.channel.group;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* compiled from: VoidChannelGroupFuture.java */
/* loaded from: classes4.dex */
final class c implements ChannelGroupFuture {
    private static final Iterator<ChannelFuture> a = Collections.emptyList().iterator();
    private final ChannelGroup b;

    /* renamed from: a */
    public Void getNow() {
        return null;
    }

    @Override // io.netty.util.concurrent.Future, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return false;
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, io.netty.util.concurrent.Future
    public ChannelGroupException cause() {
        return null;
    }

    @Override // io.netty.channel.group.ChannelGroupFuture
    public ChannelFuture find(Channel channel) {
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

    @Override // io.netty.channel.group.ChannelGroupFuture
    public boolean isPartialFailure() {
        return false;
    }

    @Override // io.netty.channel.group.ChannelGroupFuture
    public boolean isPartialSuccess() {
        return false;
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, io.netty.util.concurrent.Future
    public boolean isSuccess() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(ChannelGroup channelGroup) {
        this.b = channelGroup;
    }

    @Override // io.netty.channel.group.ChannelGroupFuture
    public ChannelGroup group() {
        return this.b;
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, io.netty.util.concurrent.Future
    /* renamed from: addListener */
    public Future<Void> addListener2(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        throw c();
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, io.netty.util.concurrent.Future
    /* renamed from: addListeners */
    public Future<Void> addListeners2(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        throw c();
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, io.netty.util.concurrent.Future
    /* renamed from: removeListener */
    public Future<Void> removeListener2(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        throw c();
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, io.netty.util.concurrent.Future
    /* renamed from: removeListeners */
    public Future<Void> removeListeners2(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        throw c();
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, io.netty.util.concurrent.Future
    /* renamed from: await */
    public Future<Void> await2() {
        throw c();
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, io.netty.util.concurrent.Future
    /* renamed from: awaitUninterruptibly */
    public Future<Void> awaitUninterruptibly2() {
        throw c();
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, io.netty.util.concurrent.Future
    /* renamed from: syncUninterruptibly */
    public Future<Void> syncUninterruptibly2() {
        throw c();
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, io.netty.util.concurrent.Future
    /* renamed from: sync */
    public Future<Void> sync2() {
        throw c();
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, java.lang.Iterable
    public Iterator<ChannelFuture> iterator() {
        return a;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean await(long j, TimeUnit timeUnit) {
        throw c();
    }

    @Override // io.netty.util.concurrent.Future
    public boolean await(long j) {
        throw c();
    }

    @Override // io.netty.util.concurrent.Future
    public boolean awaitUninterruptibly(long j, TimeUnit timeUnit) {
        throw c();
    }

    @Override // io.netty.util.concurrent.Future
    public boolean awaitUninterruptibly(long j) {
        throw c();
    }

    /* renamed from: b */
    public Void get() {
        throw c();
    }

    /* renamed from: a */
    public Void get(long j, TimeUnit timeUnit) {
        throw c();
    }

    private static RuntimeException c() {
        return new IllegalStateException("void future");
    }
}
