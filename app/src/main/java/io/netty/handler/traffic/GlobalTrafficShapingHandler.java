package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.PlatformDependent;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class GlobalTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private final ConcurrentMap<Integer, a> g = PlatformDependent.newConcurrentHashMap();
    private final AtomicLong h = new AtomicLong();
    long f = 419430400;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a {
        ArrayDeque<b> a;
        long b;
        long c;
        long d;

        private a() {
        }
    }

    void a(ScheduledExecutorService scheduledExecutorService) {
        if (scheduledExecutorService != null) {
            TrafficCounter trafficCounter = new TrafficCounter(this, scheduledExecutorService, "GlobalTC", this.checkInterval);
            a(trafficCounter);
            trafficCounter.start();
            return;
        }
        throw new NullPointerException("executor");
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j, long j2, long j3, long j4) {
        super(j, j2, j3, j4);
        a(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j, long j2, long j3) {
        super(j, j2, j3);
        a(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j, long j2) {
        super(j, j2);
        a(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j) {
        super(j);
        a(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(EventExecutor eventExecutor) {
        a(eventExecutor);
    }

    public long getMaxGlobalWriteSize() {
        return this.f;
    }

    public void setMaxGlobalWriteSize(long j) {
        this.f = j;
    }

    public long queuesSize() {
        return this.h.get();
    }

    public final void release() {
        this.trafficCounter.stop();
    }

    private a c(ChannelHandlerContext channelHandlerContext) {
        Integer valueOf = Integer.valueOf(channelHandlerContext.channel().hashCode());
        a aVar = this.g.get(valueOf);
        if (aVar != null) {
            return aVar;
        }
        a aVar2 = new a();
        aVar2.a = new ArrayDeque<>();
        aVar2.b = 0L;
        aVar2.d = TrafficCounter.milliSecondFromNano();
        aVar2.c = aVar2.d;
        this.g.put(valueOf, aVar2);
        return aVar2;
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        c(channelHandlerContext);
        super.handlerAdded(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        Channel channel = channelHandlerContext.channel();
        a remove = this.g.remove(Integer.valueOf(channel.hashCode()));
        if (remove != null) {
            synchronized (remove) {
                if (channel.isActive()) {
                    Iterator<b> it = remove.a.iterator();
                    while (it.hasNext()) {
                        b next = it.next();
                        long calculateSize = calculateSize(next.b);
                        this.trafficCounter.d(calculateSize);
                        remove.b -= calculateSize;
                        this.h.addAndGet(-calculateSize);
                        channelHandlerContext.write(next.b, next.d);
                    }
                } else {
                    this.h.addAndGet(-remove.b);
                    Iterator<b> it2 = remove.a.iterator();
                    while (it2.hasNext()) {
                        b next2 = it2.next();
                        if (next2.b instanceof ByteBuf) {
                            ((ByteBuf) next2.b).release();
                        }
                    }
                }
                remove.a.clear();
            }
        }
        b(channelHandlerContext);
        a(channelHandlerContext);
        super.handlerRemoved(channelHandlerContext);
    }

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler
    long checkWaitReadTime(ChannelHandlerContext channelHandlerContext, long j, long j2) {
        a aVar = this.g.get(Integer.valueOf(channelHandlerContext.channel().hashCode()));
        return (aVar == null || j <= this.maxTime || (j2 + j) - aVar.d <= this.maxTime) ? j : this.maxTime;
    }

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler
    void informReadOperation(ChannelHandlerContext channelHandlerContext, long j) {
        a aVar = this.g.get(Integer.valueOf(channelHandlerContext.channel().hashCode()));
        if (aVar != null) {
            aVar.d = j;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b {
        final long a;
        final Object b;
        final long c;
        final ChannelPromise d;

        private b(long j, Object obj, long j2, ChannelPromise channelPromise) {
            this.a = j;
            this.b = obj;
            this.c = j2;
            this.d = channelPromise;
        }
    }

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler
    void submitWrite(final ChannelHandlerContext channelHandlerContext, Object obj, long j, long j2, long j3, ChannelPromise channelPromise) {
        a aVar = this.g.get(Integer.valueOf(channelHandlerContext.channel().hashCode()));
        final a c = aVar == null ? c(channelHandlerContext) : aVar;
        synchronized (c) {
            if (j2 == 0) {
                if (c.a.isEmpty()) {
                    this.trafficCounter.d(j);
                    channelHandlerContext.write(obj, channelPromise);
                    c.c = j3;
                    return;
                }
            }
            long j4 = (j2 <= this.maxTime || (j3 + j2) - c.c <= this.maxTime) ? j2 : this.maxTime;
            b bVar = new b(j4 + j3, obj, j, channelPromise);
            c.a.addLast(bVar);
            c.b += j;
            this.h.addAndGet(j);
            a(channelHandlerContext, j4, c.b);
            boolean z = this.h.get() > this.f;
            if (z) {
                a(channelHandlerContext, false);
            }
            final long j5 = bVar.a;
            channelHandlerContext.executor().schedule((Runnable) new OneTimeTask() { // from class: io.netty.handler.traffic.GlobalTrafficShapingHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    GlobalTrafficShapingHandler.this.a(channelHandlerContext, c, j5);
                }
            }, j4, TimeUnit.MILLISECONDS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ChannelHandlerContext channelHandlerContext, a aVar, long j) {
        synchronized (aVar) {
            b pollFirst = aVar.a.pollFirst();
            while (true) {
                if (pollFirst != null) {
                    if (pollFirst.a > j) {
                        aVar.a.addFirst(pollFirst);
                        break;
                    }
                    long j2 = pollFirst.c;
                    this.trafficCounter.d(j2);
                    aVar.b -= j2;
                    this.h.addAndGet(-j2);
                    channelHandlerContext.write(pollFirst.b, pollFirst.d);
                    aVar.c = j;
                    pollFirst = aVar.a.pollFirst();
                } else {
                    break;
                }
            }
            if (aVar.a.isEmpty()) {
                b(channelHandlerContext);
            }
        }
        channelHandlerContext.flush();
    }
}
