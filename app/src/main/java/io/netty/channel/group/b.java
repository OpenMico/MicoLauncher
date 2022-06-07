package io.netty.channel.group;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.BlockingOperationException;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ImmediateEventExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DefaultChannelGroupFuture.java */
/* loaded from: classes4.dex */
public final class b extends DefaultPromise<Void> implements ChannelGroupFuture {
    private final ChannelGroup a;
    private final Map<Channel, ChannelFuture> b;
    private int c;
    private int d;
    private final ChannelFutureListener e = new ChannelFutureListener() { // from class: io.netty.channel.group.b.1
        static final /* synthetic */ boolean a = !b.class.desiredAssertionStatus();

        /* renamed from: a */
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            boolean z;
            boolean isSuccess = channelFuture.isSuccess();
            synchronized (b.this) {
                if (isSuccess) {
                    b.a(b.this);
                } else {
                    b.b(b.this);
                }
                z = b.this.c + b.this.d == b.this.b.size();
                if (!a && b.this.c + b.this.d > b.this.b.size()) {
                    throw new AssertionError();
                }
            }
            if (!z) {
                return;
            }
            if (b.this.d > 0) {
                ArrayList arrayList = new ArrayList(b.this.d);
                for (ChannelFuture channelFuture2 : b.this.b.values()) {
                    if (!channelFuture2.isSuccess()) {
                        arrayList.add(new a(channelFuture2.channel(), channelFuture2.cause()));
                    }
                }
                b.this.a(new ChannelGroupException(arrayList));
                return;
            }
            b.this.e();
        }
    };

    static /* synthetic */ int a(b bVar) {
        int i = bVar.c;
        bVar.c = i + 1;
        return i;
    }

    static /* synthetic */ int b(b bVar) {
        int i = bVar.d;
        bVar.d = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(ChannelGroup channelGroup, Map<Channel, ChannelFuture> map, EventExecutor eventExecutor) {
        super(eventExecutor);
        this.a = channelGroup;
        this.b = Collections.unmodifiableMap(map);
        for (ChannelFuture channelFuture : this.b.values()) {
            channelFuture.addListener((GenericFutureListener<? extends Future<? super Void>>) this.e);
        }
        if (this.b.isEmpty()) {
            e();
        }
    }

    @Override // io.netty.channel.group.ChannelGroupFuture
    public ChannelGroup group() {
        return this.a;
    }

    @Override // io.netty.channel.group.ChannelGroupFuture
    public ChannelFuture find(Channel channel) {
        return this.b.get(channel);
    }

    @Override // io.netty.channel.group.ChannelGroupFuture, java.lang.Iterable
    public Iterator<ChannelFuture> iterator() {
        return this.b.values().iterator();
    }

    @Override // io.netty.channel.group.ChannelGroupFuture
    public synchronized boolean isPartialSuccess() {
        boolean z;
        if (this.c != 0) {
            if (this.c != this.b.size()) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    @Override // io.netty.channel.group.ChannelGroupFuture
    public synchronized boolean isPartialFailure() {
        boolean z;
        if (this.d != 0) {
            if (this.d != this.b.size()) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    /* renamed from: a */
    public b addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.addListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    /* renamed from: a */
    public b addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.addListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    /* renamed from: b */
    public b removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.removeListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    /* renamed from: b */
    public b removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.removeListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    /* renamed from: a */
    public b await() throws InterruptedException {
        super.await();
        return this;
    }

    /* renamed from: b */
    public b awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    /* renamed from: c */
    public b syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    /* renamed from: d */
    public b sync() throws InterruptedException {
        super.sync();
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public ChannelGroupException cause() {
        return (ChannelGroupException) super.cause();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        super.setSuccess(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ChannelGroupException channelGroupException) {
        super.setFailure(channelGroupException);
    }

    /* renamed from: a */
    public b setSuccess(Void r1) {
        throw new IllegalStateException();
    }

    /* renamed from: b */
    public boolean trySuccess(Void r1) {
        throw new IllegalStateException();
    }

    /* renamed from: a */
    public b setFailure(Throwable th) {
        throw new IllegalStateException();
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise
    public boolean tryFailure(Throwable th) {
        throw new IllegalStateException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.util.concurrent.DefaultPromise
    public void checkDeadLock() {
        EventExecutor executor = executor();
        if (executor != null && executor != ImmediateEventExecutor.INSTANCE && executor.inEventLoop()) {
            throw new BlockingOperationException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DefaultChannelGroupFuture.java */
    /* loaded from: classes4.dex */
    public static final class a<K, V> implements Map.Entry<K, V> {
        private final K a;
        private final V b;

        a(K k, V v) {
            this.a = k;
            this.b = v;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.a;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.b;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            throw new UnsupportedOperationException("read-only");
        }
    }
}
