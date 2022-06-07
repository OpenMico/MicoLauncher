package io.netty.channel.epoll;

import com.umeng.analytics.pro.ai;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.util.IntSupplier;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: EpollEventLoop.java */
/* loaded from: classes4.dex */
public final class b extends SingleThreadEventLoop {
    static final /* synthetic */ boolean a = !b.class.desiredAssertionStatus();
    private static final InternalLogger e = InternalLoggerFactory.getInstance(b.class);
    private static final AtomicIntegerFieldUpdater<b> f;
    private final FileDescriptor g;
    private final FileDescriptor h;
    private final boolean j;
    private final a k;
    private final SelectStrategy m;
    private volatile int p;
    private final IntObjectMap<AbstractEpollChannel> i = new IntObjectHashMap(4096);
    private final e l = new e();
    private final IntSupplier n = new IntSupplier() { // from class: io.netty.channel.epoll.b.1
        @Override // io.netty.util.IntSupplier
        public int get() throws Exception {
            return Native.epollWait(b.this.g.intValue(), b.this.k, 0);
        }
    };
    private final Callable<Integer> o = new Callable<Integer>() { // from class: io.netty.channel.epoll.b.2
        /* renamed from: a */
        public Integer call() throws Exception {
            return Integer.valueOf(b.super.pendingTasks());
        }
    };
    private volatile int q = 50;

    static {
        AtomicIntegerFieldUpdater<b> newAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(b.class, "wakenUp");
        if (newAtomicIntegerFieldUpdater == null) {
            newAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(b.class, ai.av);
        }
        f = newAtomicIntegerFieldUpdater;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(EventLoopGroup eventLoopGroup, Executor executor, int i, SelectStrategy selectStrategy) {
        super(eventLoopGroup, executor, false);
        Throwable th;
        FileDescriptor fileDescriptor;
        this.m = (SelectStrategy) ObjectUtil.checkNotNull(selectStrategy, "strategy");
        if (i == 0) {
            this.j = true;
            this.k = new a(4096);
        } else {
            this.j = false;
            this.k = new a(i);
        }
        FileDescriptor fileDescriptor2 = null;
        try {
            fileDescriptor = Native.newEpollCreate();
            try {
                this.g = fileDescriptor;
                fileDescriptor2 = Native.newEventFd();
                this.h = fileDescriptor2;
                try {
                    Native.epollCtlAdd(fileDescriptor.intValue(), fileDescriptor2.intValue(), Native.EPOLLIN);
                } catch (IOException e2) {
                    throw new IllegalStateException("Unable to add eventFd filedescriptor to epoll", e2);
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileDescriptor != null) {
                    try {
                        fileDescriptor.close();
                    } catch (Exception unused) {
                    }
                }
                if (fileDescriptor2 != null) {
                    try {
                        fileDescriptor2.close();
                    } catch (Exception unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileDescriptor = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e a() {
        this.l.a();
        return this.l;
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected void wakeup(boolean z) {
        if (!z && f.compareAndSet(this, 0, 1)) {
            Native.eventFdWrite(this.h.intValue(), 1L);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(AbstractEpollChannel abstractEpollChannel) throws IOException {
        if (a || inEventLoop()) {
            int intValue = abstractEpollChannel.fd().intValue();
            Native.epollCtlAdd(this.g.intValue(), intValue, abstractEpollChannel.flags);
            this.i.put(intValue, (int) abstractEpollChannel);
            return;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(AbstractEpollChannel abstractEpollChannel) throws IOException {
        if (a || inEventLoop()) {
            Native.epollCtlMod(this.g.intValue(), abstractEpollChannel.fd().intValue(), abstractEpollChannel.flags);
            return;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(AbstractEpollChannel abstractEpollChannel) throws IOException {
        if (!a && !inEventLoop()) {
            throw new AssertionError();
        } else if (abstractEpollChannel.isOpen()) {
            if (this.i.remove(abstractEpollChannel.fd().intValue()) != null) {
                Native.epollCtlDel(this.g.intValue(), abstractEpollChannel.fd().intValue());
            }
        }
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected Queue<Runnable> newTaskQueue() {
        return PlatformDependent.newMpscQueue();
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    public int pendingTasks() {
        if (inEventLoop()) {
            return super.pendingTasks();
        }
        return ((Integer) submit((Callable) this.o).syncUninterruptibly().getNow()).intValue();
    }

    public void a(int i) {
        if (i <= 0 || i > 100) {
            throw new IllegalArgumentException("ioRatio: " + i + " (expected: 0 < ioRatio <= 100)");
        }
        this.q = i;
    }

    private int a(boolean z) throws IOException {
        int epollWait;
        int epollWait2;
        long nanoTime = System.nanoTime();
        long delayNanos = delayNanos(nanoTime) + nanoTime;
        int i = 0;
        while (true) {
            long j = ((delayNanos - nanoTime) + 500000) / 1000000;
            if (j <= 0) {
                if (i != 0 || (epollWait = Native.epollWait(this.g.intValue(), this.k, 0)) <= 0) {
                    return 0;
                }
                return epollWait;
            } else if (hasTasks() && f.compareAndSet(this, 0, 1)) {
                return Native.epollWait(this.g.intValue(), this.k, 0);
            } else {
                epollWait2 = Native.epollWait(this.g.intValue(), this.k, (int) j);
                i++;
                if (epollWait2 != 0 || z || this.p == 1 || hasTasks() || hasScheduledTasks()) {
                    break;
                }
                nanoTime = System.nanoTime();
            }
        }
        return epollWait2;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003f A[Catch: Throwable -> 0x0078, TryCatch #1 {Throwable -> 0x0078, blocks: (B:2:0x0000, B:3:0x000c, B:5:0x0010, B:8:0x001b, B:10:0x0023, B:11:0x002e, B:14:0x0036, B:15:0x003b, B:16:0x003f, B:18:0x0045, B:19:0x004a, B:20:0x0057, B:22:0x005b, B:24:0x0063, B:25:0x0068, B:27:0x006e), top: B:36:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x006e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0000 A[SYNTHETIC] */
    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void run() {
        /*
            r7 = this;
        L_0x0000:
            io.netty.channel.SelectStrategy r0 = r7.m     // Catch: Throwable -> 0x0078
            io.netty.util.IntSupplier r1 = r7.n     // Catch: Throwable -> 0x0078
            boolean r2 = r7.hasTasks()     // Catch: Throwable -> 0x0078
            int r0 = r0.calculateStrategy(r1, r2)     // Catch: Throwable -> 0x0078
            switch(r0) {
                case -2: goto L_0x0000;
                case -1: goto L_0x0010;
                default: goto L_0x000f;
            }     // Catch: Throwable -> 0x0078
        L_0x000f:
            goto L_0x002e
        L_0x0010:
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater<io.netty.channel.epoll.b> r0 = io.netty.channel.epoll.b.f     // Catch: Throwable -> 0x0078
            r1 = 0
            int r0 = r0.getAndSet(r7, r1)     // Catch: Throwable -> 0x0078
            r2 = 1
            if (r0 != r2) goto L_0x001b
            r1 = r2
        L_0x001b:
            int r0 = r7.a(r1)     // Catch: Throwable -> 0x0078
            int r1 = r7.p     // Catch: Throwable -> 0x0078
            if (r1 != r2) goto L_0x002e
            io.netty.channel.unix.FileDescriptor r1 = r7.h     // Catch: Throwable -> 0x0078
            int r1 = r1.intValue()     // Catch: Throwable -> 0x0078
            r2 = 1
            io.netty.channel.epoll.Native.eventFdWrite(r1, r2)     // Catch: Throwable -> 0x0078
        L_0x002e:
            int r1 = r7.q     // Catch: Throwable -> 0x0078
            r2 = 100
            if (r1 != r2) goto L_0x003f
            if (r0 <= 0) goto L_0x003b
            io.netty.channel.epoll.a r1 = r7.k     // Catch: Throwable -> 0x0078
            r7.a(r1, r0)     // Catch: Throwable -> 0x0078
        L_0x003b:
            r7.runAllTasks()     // Catch: Throwable -> 0x0078
            goto L_0x0057
        L_0x003f:
            long r3 = java.lang.System.nanoTime()     // Catch: Throwable -> 0x0078
            if (r0 <= 0) goto L_0x004a
            io.netty.channel.epoll.a r5 = r7.k     // Catch: Throwable -> 0x0078
            r7.a(r5, r0)     // Catch: Throwable -> 0x0078
        L_0x004a:
            long r5 = java.lang.System.nanoTime()     // Catch: Throwable -> 0x0078
            long r5 = r5 - r3
            int r2 = r2 - r1
            long r2 = (long) r2     // Catch: Throwable -> 0x0078
            long r5 = r5 * r2
            long r1 = (long) r1     // Catch: Throwable -> 0x0078
            long r5 = r5 / r1
            r7.runAllTasks(r5)     // Catch: Throwable -> 0x0078
        L_0x0057:
            boolean r1 = r7.j     // Catch: Throwable -> 0x0078
            if (r1 == 0) goto L_0x0068
            io.netty.channel.epoll.a r1 = r7.k     // Catch: Throwable -> 0x0078
            int r1 = r1.b()     // Catch: Throwable -> 0x0078
            if (r0 != r1) goto L_0x0068
            io.netty.channel.epoll.a r0 = r7.k     // Catch: Throwable -> 0x0078
            r0.c()     // Catch: Throwable -> 0x0078
        L_0x0068:
            boolean r0 = r7.isShuttingDown()     // Catch: Throwable -> 0x0078
            if (r0 == 0) goto L_0x0000
            r7.f()     // Catch: Throwable -> 0x0078
            boolean r0 = r7.confirmShutdown()     // Catch: Throwable -> 0x0078
            if (r0 == 0) goto L_0x0000
            return
        L_0x0078:
            r0 = move-exception
            io.netty.util.internal.logging.InternalLogger r1 = io.netty.channel.epoll.b.e
            java.lang.String r2 = "Unexpected exception in the selector loop."
            r1.warn(r2, r0)
            r0 = 1000(0x3e8, double:4.94E-321)
            java.lang.Thread.sleep(r0)     // Catch: InterruptedException -> 0x0000
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.epoll.b.run():void");
    }

    private void f() {
        try {
            Native.epollWait(this.g.intValue(), this.k, 0);
        } catch (IOException unused) {
        }
        ArrayList<AbstractEpollChannel> arrayList = new ArrayList(this.i.size());
        for (AbstractEpollChannel abstractEpollChannel : this.i.values()) {
            arrayList.add(abstractEpollChannel);
        }
        for (AbstractEpollChannel abstractEpollChannel2 : arrayList) {
            abstractEpollChannel2.unsafe().close(abstractEpollChannel2.unsafe().voidPromise());
        }
    }

    private void a(a aVar, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            int b = aVar.b(i2);
            if (b == this.h.intValue()) {
                Native.eventFdRead(this.h.intValue());
            } else {
                long a2 = aVar.a(i2);
                AbstractEpollChannel abstractEpollChannel = this.i.get(b);
                if (abstractEpollChannel != null) {
                    AbstractEpollChannel.AbstractEpollUnsafe abstractEpollUnsafe = (AbstractEpollChannel.AbstractEpollUnsafe) abstractEpollChannel.unsafe();
                    if (((Native.EPOLLERR | Native.EPOLLOUT) & a2) != 0) {
                        abstractEpollUnsafe.f();
                    }
                    if (((Native.EPOLLERR | Native.EPOLLIN) & a2) != 0) {
                        abstractEpollUnsafe.a();
                    }
                    if ((a2 & Native.EPOLLRDHUP) != 0) {
                        abstractEpollUnsafe.d();
                    }
                } else {
                    try {
                        Native.epollCtlDel(this.g.intValue(), b);
                    } catch (IOException unused) {
                    }
                }
            }
        }
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected void cleanup() {
        try {
            try {
                this.g.close();
            } catch (IOException e2) {
                e.warn("Failed to close the epoll fd.", (Throwable) e2);
            }
            try {
                this.h.close();
            } catch (IOException e3) {
                e.warn("Failed to close the event fd.", (Throwable) e3);
            }
        } finally {
            this.l.d();
            this.k.d();
        }
    }
}
