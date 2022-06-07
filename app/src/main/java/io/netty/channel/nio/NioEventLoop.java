package io.netty.channel.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.EventLoopException;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.util.IntSupplier;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class NioEventLoop extends SingleThreadEventLoop {
    private static final InternalLogger e = InternalLoggerFactory.getInstance(NioEventLoop.class);
    private static final boolean f = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
    private static final int g;
    Selector a;
    private a j;
    private final SelectorProvider k;
    private final SelectStrategy m;
    private int o;
    private boolean p;
    private final IntSupplier h = new IntSupplier() { // from class: io.netty.channel.nio.NioEventLoop.1
        @Override // io.netty.util.IntSupplier
        public int get() throws Exception {
            return NioEventLoop.this.a();
        }
    };
    private final Callable<Integer> i = new Callable<Integer>() { // from class: io.netty.channel.nio.NioEventLoop.2
        /* renamed from: a */
        public Integer call() throws Exception {
            return Integer.valueOf(NioEventLoop.super.pendingTasks());
        }
    };
    private final AtomicBoolean l = new AtomicBoolean();
    private volatile int n = 50;

    static {
        try {
            if (SystemPropertyUtil.get("sun.nio.ch.bugLevel") == null) {
                System.setProperty("sun.nio.ch.bugLevel", "");
            }
        } catch (SecurityException e2) {
            if (e.isDebugEnabled()) {
                e.debug("Unable to get/set System Property: {}", "sun.nio.ch.bugLevel", e2);
            }
        }
        int i = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
        if (i < 3) {
            i = 0;
        }
        g = i;
        if (e.isDebugEnabled()) {
            e.debug("-Dio.netty.noKeySetOptimization: {}", Boolean.valueOf(f));
            e.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", Integer.valueOf(g));
        }
    }

    public NioEventLoop(NioEventLoopGroup nioEventLoopGroup, Executor executor, SelectorProvider selectorProvider, SelectStrategy selectStrategy) {
        super((EventLoopGroup) nioEventLoopGroup, executor, false);
        if (selectorProvider == null) {
            throw new NullPointerException("selectorProvider");
        } else if (selectStrategy != null) {
            this.k = selectorProvider;
            this.a = f();
            this.m = selectStrategy;
        } else {
            throw new NullPointerException("selectStrategy");
        }
    }

    private Selector f() {
        a aVar;
        Class<?> cls;
        try {
            AbstractSelector openSelector = this.k.openSelector();
            if (f) {
                return openSelector;
            }
            try {
                aVar = new a();
                cls = Class.forName("sun.nio.ch.SelectorImpl", false, PlatformDependent.getSystemClassLoader());
            } catch (Throwable th) {
                this.j = null;
                e.trace("Failed to instrument an optimized java.util.Set into: {}", openSelector, th);
            }
            if (!cls.isAssignableFrom(openSelector.getClass())) {
                return openSelector;
            }
            Field declaredField = cls.getDeclaredField("selectedKeys");
            Field declaredField2 = cls.getDeclaredField("publicSelectedKeys");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            declaredField.set(openSelector, aVar);
            declaredField2.set(openSelector, aVar);
            this.j = aVar;
            e.trace("Instrumented an optimized java.util.Set into: {}", openSelector);
            return openSelector;
        } catch (IOException e2) {
            throw new ChannelException("failed to open a new selector", e2);
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
        return ((Integer) submit((Callable) this.i).syncUninterruptibly().getNow()).intValue();
    }

    public void register(SelectableChannel selectableChannel, int i, NioTask<?> nioTask) {
        if (selectableChannel == null) {
            throw new NullPointerException("ch");
        } else if (i == 0) {
            throw new IllegalArgumentException("interestOps must be non-zero.");
        } else if (((~selectableChannel.validOps()) & i) != 0) {
            throw new IllegalArgumentException("invalid interestOps: " + i + "(validOps: " + selectableChannel.validOps() + ')');
        } else if (nioTask == null) {
            throw new NullPointerException("task");
        } else if (!isShutdown()) {
            try {
                selectableChannel.register(this.a, i, nioTask);
            } catch (Exception e2) {
                throw new EventLoopException("failed to register a channel", e2);
            }
        } else {
            throw new IllegalStateException("event loop shut down");
        }
    }

    public int getIoRatio() {
        return this.n;
    }

    public void setIoRatio(int i) {
        if (i <= 0 || i > 100) {
            throw new IllegalArgumentException("ioRatio: " + i + " (expected: 0 < ioRatio <= 100)");
        }
        this.n = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:?, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void rebuildSelector() {
        /*
            r9 = this;
            boolean r0 = r9.inEventLoop()
            if (r0 != 0) goto L_0x000f
            io.netty.channel.nio.NioEventLoop$3 r0 = new io.netty.channel.nio.NioEventLoop$3
            r0.<init>()
            r9.execute(r0)
            return
        L_0x000f:
            java.nio.channels.Selector r0 = r9.a
            if (r0 != 0) goto L_0x0014
            return
        L_0x0014:
            java.nio.channels.Selector r1 = r9.f()     // Catch: Exception -> 0x00b3
            r2 = 0
        L_0x0019:
            java.util.Set r3 = r0.keys()     // Catch: ConcurrentModificationException -> 0x0019
            java.util.Iterator r3 = r3.iterator()     // Catch: ConcurrentModificationException -> 0x0019
        L_0x0021:
            boolean r4 = r3.hasNext()     // Catch: ConcurrentModificationException -> 0x0019
            if (r4 == 0) goto L_0x0081
            java.lang.Object r4 = r3.next()     // Catch: ConcurrentModificationException -> 0x0019
            java.nio.channels.SelectionKey r4 = (java.nio.channels.SelectionKey) r4     // Catch: ConcurrentModificationException -> 0x0019
            java.lang.Object r5 = r4.attachment()     // Catch: ConcurrentModificationException -> 0x0019
            boolean r6 = r4.isValid()     // Catch: Exception -> 0x005d, ConcurrentModificationException -> 0x0019
            if (r6 == 0) goto L_0x0021
            java.nio.channels.SelectableChannel r6 = r4.channel()     // Catch: Exception -> 0x005d, ConcurrentModificationException -> 0x0019
            java.nio.channels.SelectionKey r6 = r6.keyFor(r1)     // Catch: Exception -> 0x005d, ConcurrentModificationException -> 0x0019
            if (r6 == 0) goto L_0x0042
            goto L_0x0021
        L_0x0042:
            int r6 = r4.interestOps()     // Catch: Exception -> 0x005d, ConcurrentModificationException -> 0x0019
            r4.cancel()     // Catch: Exception -> 0x005d, ConcurrentModificationException -> 0x0019
            java.nio.channels.SelectableChannel r7 = r4.channel()     // Catch: Exception -> 0x005d, ConcurrentModificationException -> 0x0019
            java.nio.channels.SelectionKey r6 = r7.register(r1, r6, r5)     // Catch: Exception -> 0x005d, ConcurrentModificationException -> 0x0019
            boolean r7 = r5 instanceof io.netty.channel.nio.AbstractNioChannel     // Catch: Exception -> 0x005d, ConcurrentModificationException -> 0x0019
            if (r7 == 0) goto L_0x005a
            r7 = r5
            io.netty.channel.nio.AbstractNioChannel r7 = (io.netty.channel.nio.AbstractNioChannel) r7     // Catch: Exception -> 0x005d, ConcurrentModificationException -> 0x0019
            r7.c = r6     // Catch: Exception -> 0x005d, ConcurrentModificationException -> 0x0019
        L_0x005a:
            int r2 = r2 + 1
            goto L_0x0021
        L_0x005d:
            r6 = move-exception
            io.netty.util.internal.logging.InternalLogger r7 = io.netty.channel.nio.NioEventLoop.e     // Catch: ConcurrentModificationException -> 0x0019
            java.lang.String r8 = "Failed to re-register a Channel to the new Selector."
            r7.warn(r8, r6)     // Catch: ConcurrentModificationException -> 0x0019
            boolean r7 = r5 instanceof io.netty.channel.nio.AbstractNioChannel     // Catch: ConcurrentModificationException -> 0x0019
            if (r7 == 0) goto L_0x007b
            io.netty.channel.nio.AbstractNioChannel r5 = (io.netty.channel.nio.AbstractNioChannel) r5     // Catch: ConcurrentModificationException -> 0x0019
            io.netty.channel.nio.AbstractNioChannel$NioUnsafe r4 = r5.unsafe()     // Catch: ConcurrentModificationException -> 0x0019
            io.netty.channel.nio.AbstractNioChannel$NioUnsafe r5 = r5.unsafe()     // Catch: ConcurrentModificationException -> 0x0019
            io.netty.channel.ChannelPromise r5 = r5.voidPromise()     // Catch: ConcurrentModificationException -> 0x0019
            r4.close(r5)     // Catch: ConcurrentModificationException -> 0x0019
            goto L_0x0021
        L_0x007b:
            io.netty.channel.nio.NioTask r5 = (io.netty.channel.nio.NioTask) r5     // Catch: ConcurrentModificationException -> 0x0019
            a(r5, r4, r6)     // Catch: ConcurrentModificationException -> 0x0019
            goto L_0x0021
        L_0x0081:
            r9.a = r1
            r0.close()     // Catch: Throwable -> 0x0087
            goto L_0x0097
        L_0x0087:
            r0 = move-exception
            io.netty.util.internal.logging.InternalLogger r1 = io.netty.channel.nio.NioEventLoop.e
            boolean r1 = r1.isWarnEnabled()
            if (r1 == 0) goto L_0x0097
            io.netty.util.internal.logging.InternalLogger r1 = io.netty.channel.nio.NioEventLoop.e
            java.lang.String r3 = "Failed to close the old Selector."
            r1.warn(r3, r0)
        L_0x0097:
            io.netty.util.internal.logging.InternalLogger r0 = io.netty.channel.nio.NioEventLoop.e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "Migrated "
            r1.append(r3)
            r1.append(r2)
            java.lang.String r2 = " channel(s) to the new Selector."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.info(r1)
            return
        L_0x00b3:
            r0 = move-exception
            io.netty.util.internal.logging.InternalLogger r1 = io.netty.channel.nio.NioEventLoop.e
            java.lang.String r2 = "Failed to create a new Selector."
            r1.warn(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.nio.NioEventLoop.rebuildSelector():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0031 A[Catch: Throwable -> 0x005c, TryCatch #0 {Throwable -> 0x005c, blocks: (B:2:0x0000, B:3:0x000d, B:5:0x0011, B:7:0x0022, B:8:0x0027, B:10:0x0031, B:11:0x0038, B:12:0x004c, B:14:0x0052), top: B:21:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0038 A[Catch: Throwable -> 0x005c, TryCatch #0 {Throwable -> 0x005c, blocks: (B:2:0x0000, B:3:0x000d, B:5:0x0011, B:7:0x0022, B:8:0x0027, B:10:0x0031, B:11:0x0038, B:12:0x004c, B:14:0x0052), top: B:21:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0052 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0000 A[SYNTHETIC] */
    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void run() {
        /*
            r6 = this;
        L_0x0000:
            io.netty.channel.SelectStrategy r0 = r6.m     // Catch: Throwable -> 0x005c
            io.netty.util.IntSupplier r1 = r6.h     // Catch: Throwable -> 0x005c
            boolean r2 = r6.hasTasks()     // Catch: Throwable -> 0x005c
            int r0 = r0.calculateStrategy(r1, r2)     // Catch: Throwable -> 0x005c
            r1 = 0
            switch(r0) {
                case -2: goto L_0x0000;
                case -1: goto L_0x0011;
                default: goto L_0x0010;
            }     // Catch: Throwable -> 0x005c
        L_0x0010:
            goto L_0x0027
        L_0x0011:
            java.util.concurrent.atomic.AtomicBoolean r0 = r6.l     // Catch: Throwable -> 0x005c
            boolean r0 = r0.getAndSet(r1)     // Catch: Throwable -> 0x005c
            r6.a(r0)     // Catch: Throwable -> 0x005c
            java.util.concurrent.atomic.AtomicBoolean r0 = r6.l     // Catch: Throwable -> 0x005c
            boolean r0 = r0.get()     // Catch: Throwable -> 0x005c
            if (r0 == 0) goto L_0x0027
            java.nio.channels.Selector r0 = r6.a     // Catch: Throwable -> 0x005c
            r0.wakeup()     // Catch: Throwable -> 0x005c
        L_0x0027:
            r6.o = r1     // Catch: Throwable -> 0x005c
            r6.p = r1     // Catch: Throwable -> 0x005c
            int r0 = r6.n     // Catch: Throwable -> 0x005c
            r1 = 100
            if (r0 != r1) goto L_0x0038
            r6.g()     // Catch: Throwable -> 0x005c
            r6.runAllTasks()     // Catch: Throwable -> 0x005c
            goto L_0x004c
        L_0x0038:
            long r2 = java.lang.System.nanoTime()     // Catch: Throwable -> 0x005c
            r6.g()     // Catch: Throwable -> 0x005c
            long r4 = java.lang.System.nanoTime()     // Catch: Throwable -> 0x005c
            long r4 = r4 - r2
            int r1 = r1 - r0
            long r1 = (long) r1     // Catch: Throwable -> 0x005c
            long r4 = r4 * r1
            long r0 = (long) r0     // Catch: Throwable -> 0x005c
            long r4 = r4 / r0
            r6.runAllTasks(r4)     // Catch: Throwable -> 0x005c
        L_0x004c:
            boolean r0 = r6.isShuttingDown()     // Catch: Throwable -> 0x005c
            if (r0 == 0) goto L_0x0000
            r6.h()     // Catch: Throwable -> 0x005c
            boolean r0 = r6.confirmShutdown()     // Catch: Throwable -> 0x005c
            if (r0 == 0) goto L_0x0000
            return
        L_0x005c:
            r0 = move-exception
            io.netty.util.internal.logging.InternalLogger r1 = io.netty.channel.nio.NioEventLoop.e
            java.lang.String r2 = "Unexpected exception in the selector loop."
            r1.warn(r2, r0)
            r0 = 1000(0x3e8, double:4.94E-321)
            java.lang.Thread.sleep(r0)     // Catch: InterruptedException -> 0x0000
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.nio.NioEventLoop.run():void");
    }

    private void g() {
        a aVar = this.j;
        if (aVar != null) {
            a(aVar.a());
        } else {
            a(this.a.selectedKeys());
        }
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected void cleanup() {
        try {
            this.a.close();
        } catch (IOException e2) {
            e.warn("Failed to close a selector.", (Throwable) e2);
        }
    }

    public void a(SelectionKey selectionKey) {
        selectionKey.cancel();
        this.o++;
        if (this.o >= 256) {
            this.o = 0;
            this.p = true;
        }
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected Runnable pollTask() {
        Runnable pollTask = super.pollTask();
        if (this.p) {
            i();
        }
        return pollTask;
    }

    private void a(Set<SelectionKey> set) {
        if (!set.isEmpty()) {
            Iterator<SelectionKey> it = set.iterator();
            while (true) {
                SelectionKey next = it.next();
                Object attachment = next.attachment();
                it.remove();
                if (attachment instanceof AbstractNioChannel) {
                    a(next, (AbstractNioChannel) attachment);
                } else {
                    a(next, (NioTask) attachment);
                }
                if (it.hasNext()) {
                    if (this.p) {
                        i();
                        Set<SelectionKey> selectedKeys = this.a.selectedKeys();
                        if (!selectedKeys.isEmpty()) {
                            it = selectedKeys.iterator();
                        } else {
                            return;
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    private void a(SelectionKey[] selectionKeyArr) {
        int i = 0;
        while (true) {
            SelectionKey selectionKey = selectionKeyArr[i];
            if (selectionKey != null) {
                selectionKeyArr[i] = null;
                Object attachment = selectionKey.attachment();
                if (attachment instanceof AbstractNioChannel) {
                    a(selectionKey, (AbstractNioChannel) attachment);
                } else {
                    a(selectionKey, (NioTask) attachment);
                }
                if (this.p) {
                    while (true) {
                        i++;
                        if (selectionKeyArr[i] == null) {
                            break;
                        }
                        selectionKeyArr[i] = null;
                    }
                    i();
                    selectionKeyArr = this.j.a();
                    i = -1;
                }
                i++;
            } else {
                return;
            }
        }
    }

    private void a(SelectionKey selectionKey, AbstractNioChannel abstractNioChannel) {
        AbstractNioChannel.NioUnsafe unsafe = abstractNioChannel.unsafe();
        if (!selectionKey.isValid()) {
            try {
                NioEventLoop eventLoop = abstractNioChannel.eventLoop();
                if (eventLoop == this && eventLoop != null) {
                    unsafe.close(unsafe.voidPromise());
                }
            } catch (Throwable unused) {
            }
        } else {
            try {
                int readyOps = selectionKey.readyOps();
                if ((readyOps & 17) != 0 || readyOps == 0) {
                    unsafe.read();
                    if (!abstractNioChannel.isOpen()) {
                        return;
                    }
                }
                if ((readyOps & 4) != 0) {
                    abstractNioChannel.unsafe().forceFlush();
                }
                if ((readyOps & 8) != 0) {
                    selectionKey.interestOps(selectionKey.interestOps() & (-9));
                    unsafe.finishConnect();
                }
            } catch (CancelledKeyException unused2) {
                unsafe.close(unsafe.voidPromise());
            }
        }
    }

    private static void a(SelectionKey selectionKey, NioTask<SelectableChannel> nioTask) {
        try {
            try {
                nioTask.channelReady(selectionKey.channel(), selectionKey);
            } catch (Exception e2) {
                selectionKey.cancel();
                a(nioTask, selectionKey, e2);
                switch (2) {
                    case 0:
                        break;
                    case 1:
                        if (selectionKey.isValid()) {
                            return;
                        }
                        break;
                    default:
                        return;
                }
            }
            switch (1) {
                case 0:
                    break;
                case 1:
                    if (selectionKey.isValid()) {
                        return;
                    }
                    a(nioTask, selectionKey, null);
                    return;
                default:
                    return;
            }
            selectionKey.cancel();
            a(nioTask, selectionKey, null);
        } catch (Throwable th) {
            boolean z = false;
            switch (z) {
                case false:
                    selectionKey.cancel();
                    a(nioTask, selectionKey, null);
                    break;
                case true:
                    if (!selectionKey.isValid()) {
                        a(nioTask, selectionKey, null);
                        break;
                    }
                    break;
            }
            throw th;
        }
    }

    private void h() {
        i();
        Set<SelectionKey> keys = this.a.keys();
        ArrayList<AbstractNioChannel> arrayList = new ArrayList(keys.size());
        for (SelectionKey selectionKey : keys) {
            Object attachment = selectionKey.attachment();
            if (attachment instanceof AbstractNioChannel) {
                arrayList.add((AbstractNioChannel) attachment);
            } else {
                selectionKey.cancel();
                a((NioTask) attachment, selectionKey, null);
            }
        }
        for (AbstractNioChannel abstractNioChannel : arrayList) {
            abstractNioChannel.unsafe().close(abstractNioChannel.unsafe().voidPromise());
        }
    }

    private static void a(NioTask<SelectableChannel> nioTask, SelectionKey selectionKey, Throwable th) {
        try {
            nioTask.channelUnregistered(selectionKey.channel(), th);
        } catch (Exception e2) {
            e.warn("Unexpected exception while running NioTask.channelUnregistered()", (Throwable) e2);
        }
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected void wakeup(boolean z) {
        if (!z && this.l.compareAndSet(false, true)) {
            this.a.wakeup();
        }
    }

    public int a() throws IOException {
        try {
            return this.a.selectNow();
        } finally {
            if (this.l.get()) {
                this.a.wakeup();
            }
        }
    }

    private void a(boolean z) throws IOException {
        Selector selector = this.a;
        try {
            long nanoTime = System.nanoTime();
            long delayNanos = delayNanos(nanoTime) + nanoTime;
            int i = 0;
            while (true) {
                long j = ((delayNanos - nanoTime) + 500000) / 1000000;
                if (j > 0) {
                    if (hasTasks() && this.l.compareAndSet(false, true)) {
                        selector.selectNow();
                        i = 1;
                        break;
                    }
                    i++;
                    if (selector.select(j) != 0 || z || this.l.get() || hasTasks() || hasScheduledTasks()) {
                        break;
                    } else if (Thread.interrupted()) {
                        if (e.isDebugEnabled()) {
                            e.debug("Selector.select() returned prematurely because Thread.currentThread().interrupt() was called. Use NioEventLoop.shutdownGracefully() to shutdown the NioEventLoop.");
                        }
                        i = 1;
                    } else {
                        long nanoTime2 = System.nanoTime();
                        if (nanoTime2 - TimeUnit.MILLISECONDS.toNanos(j) < nanoTime) {
                            if (g > 0 && i >= g) {
                                e.warn("Selector.select() returned prematurely {} times in a row; rebuilding selector.", Integer.valueOf(i));
                                rebuildSelector();
                                this.a.selectNow();
                                i = 1;
                                break;
                            }
                        } else {
                            i = 1;
                        }
                        nanoTime = nanoTime2;
                    }
                } else if (i == 0) {
                    selector.selectNow();
                    i = 1;
                }
            }
            if (i > 3 && e.isDebugEnabled()) {
                e.debug("Selector.select() returned prematurely {} times in a row.", Integer.valueOf(i - 1));
            }
        } catch (CancelledKeyException e2) {
            if (e.isDebugEnabled()) {
                e.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector - JDK bug?", (Throwable) e2);
            }
        }
    }

    private void i() {
        this.p = false;
        try {
            this.a.selectNow();
        } catch (Throwable th) {
            e.warn("Failed to update SelectionKeys.", th);
        }
    }
}
