package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.netty.util.Attribute;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class GlobalChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private static final InternalLogger h = InternalLoggerFactory.getInstance(GlobalChannelTrafficShapingHandler.class);
    private volatile long l;
    private volatile long m;
    private volatile float n;
    private volatile float o;
    private volatile float p;
    private volatile boolean q;
    private volatile boolean r;
    final ConcurrentMap<Integer, a> f = PlatformDependent.newConcurrentHashMap();
    private final AtomicLong i = new AtomicLong();
    private final AtomicLong j = new AtomicLong();
    private final AtomicLong k = new AtomicLong();
    volatile long g = 419430400;

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler
    int a() {
        return 3;
    }

    /* loaded from: classes4.dex */
    public static final class a {
        ArrayDeque<b> a;
        TrafficCounter b;
        long c;
        long d;
        long e;

        a() {
        }
    }

    void a(ScheduledExecutorService scheduledExecutorService) {
        setMaxDeviation(0.1f, 0.4f, -0.1f);
        if (scheduledExecutorService != null) {
            GlobalChannelTrafficCounter globalChannelTrafficCounter = new GlobalChannelTrafficCounter(this, scheduledExecutorService, "GlobalChannelTC", this.checkInterval);
            a(globalChannelTrafficCounter);
            globalChannelTrafficCounter.start();
            return;
        }
        throw new IllegalArgumentException("Executor must not be null");
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j, long j2, long j3, long j4, long j5, long j6) {
        super(j, j2, j5, j6);
        a(scheduledExecutorService);
        this.l = j3;
        this.m = j4;
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j, long j2, long j3, long j4, long j5) {
        super(j, j2, j5);
        this.l = j3;
        this.m = j4;
        a(scheduledExecutorService);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j, long j2, long j3, long j4) {
        super(j, j2);
        this.l = j3;
        this.m = j4;
        a(scheduledExecutorService);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j) {
        super(j);
        a(scheduledExecutorService);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService) {
        a(scheduledExecutorService);
    }

    public float maxDeviation() {
        return this.n;
    }

    public float accelerationFactor() {
        return this.o;
    }

    public float slowDownFactor() {
        return this.p;
    }

    public void setMaxDeviation(float f, float f2, float f3) {
        if (f > 0.4f) {
            throw new IllegalArgumentException("maxDeviation must be <= 0.4");
        } else if (f2 < 0.0f) {
            throw new IllegalArgumentException("slowDownFactor must be >= 0");
        } else if (f3 <= 0.0f) {
            this.n = f;
            this.o = f3 + 1.0f;
            this.p = f2 + 1.0f;
        } else {
            throw new IllegalArgumentException("accelerationFactor must be <= 0");
        }
    }

    private void c() {
        long j = Long.MAX_VALUE;
        long j2 = 0;
        long j3 = Long.MAX_VALUE;
        long j4 = 0;
        for (a aVar : this.f.values()) {
            long cumulativeWrittenBytes = aVar.b.cumulativeWrittenBytes();
            if (j2 < cumulativeWrittenBytes) {
                j2 = cumulativeWrittenBytes;
            }
            if (j > cumulativeWrittenBytes) {
                j = cumulativeWrittenBytes;
            }
            long cumulativeReadBytes = aVar.b.cumulativeReadBytes();
            if (j4 < cumulativeReadBytes) {
                j4 = cumulativeReadBytes;
            }
            if (j3 > cumulativeReadBytes) {
                j3 = cumulativeReadBytes;
            }
        }
        boolean z = false;
        boolean z2 = this.f.size() > 1;
        this.q = z2 && j3 < j4 / 2;
        if (z2 && j < j2 / 2) {
            z = true;
        }
        this.r = z;
        this.j.set(j2);
        this.k.set(j4);
    }

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler
    public void doAccounting(TrafficCounter trafficCounter) {
        c();
        super.doAccounting(trafficCounter);
    }

    private long a(float f, float f2, long j) {
        float f3;
        if (f2 == 0.0f) {
            return j;
        }
        float f4 = f / f2;
        if (f4 <= this.n) {
            f3 = this.o;
        } else if (f4 < 1.0f - this.n) {
            return j;
        } else {
            f3 = this.p;
            if (j < 10) {
                j = 10;
            }
        }
        return ((float) j) * f3;
    }

    public long getMaxGlobalWriteSize() {
        return this.g;
    }

    public void setMaxGlobalWriteSize(long j) {
        if (j > 0) {
            this.g = j;
            return;
        }
        throw new IllegalArgumentException("maxGlobalWriteSize must be positive");
    }

    public long queuesSize() {
        return this.i.get();
    }

    public void configureChannel(long j, long j2) {
        this.l = j;
        this.m = j2;
        long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
        for (a aVar : this.f.values()) {
            aVar.b.a(milliSecondFromNano);
        }
    }

    public long getWriteChannelLimit() {
        return this.l;
    }

    public void setWriteChannelLimit(long j) {
        this.l = j;
        long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
        for (a aVar : this.f.values()) {
            aVar.b.a(milliSecondFromNano);
        }
    }

    public long getReadChannelLimit() {
        return this.m;
    }

    public void setReadChannelLimit(long j) {
        this.m = j;
        long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
        for (a aVar : this.f.values()) {
            aVar.b.a(milliSecondFromNano);
        }
    }

    public final void release() {
        this.trafficCounter.stop();
    }

    private a c(ChannelHandlerContext channelHandlerContext) {
        Integer valueOf = Integer.valueOf(channelHandlerContext.channel().hashCode());
        a aVar = this.f.get(valueOf);
        if (aVar != null) {
            return aVar;
        }
        a aVar2 = new a();
        aVar2.a = new ArrayDeque<>();
        aVar2.b = new TrafficCounter(this, null, "ChannelTC" + channelHandlerContext.channel().hashCode(), this.checkInterval);
        aVar2.c = 0L;
        aVar2.e = TrafficCounter.milliSecondFromNano();
        aVar2.d = aVar2.e;
        this.f.put(valueOf, aVar2);
        return aVar2;
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        c(channelHandlerContext);
        this.trafficCounter.resetCumulativeTime();
        super.handlerAdded(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.trafficCounter.resetCumulativeTime();
        Channel channel = channelHandlerContext.channel();
        a remove = this.f.remove(Integer.valueOf(channel.hashCode()));
        if (remove != null) {
            synchronized (remove) {
                if (channel.isActive()) {
                    Iterator<b> it = remove.a.iterator();
                    while (it.hasNext()) {
                        b next = it.next();
                        long calculateSize = calculateSize(next.b);
                        this.trafficCounter.d(calculateSize);
                        remove.b.d(calculateSize);
                        remove.c -= calculateSize;
                        this.i.addAndGet(-calculateSize);
                        channelHandlerContext.write(next.b, next.c);
                    }
                } else {
                    this.i.addAndGet(-remove.c);
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

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        long j;
        long calculateSize = calculateSize(obj);
        long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
        long j2 = 0;
        if (calculateSize > 0) {
            long readTimeToWait = this.trafficCounter.readTimeToWait(calculateSize, getReadLimit(), this.maxTime, milliSecondFromNano);
            a aVar = this.f.get(Integer.valueOf(channelHandlerContext.channel().hashCode()));
            if (aVar != null) {
                long readTimeToWait2 = aVar.b.readTimeToWait(calculateSize, this.m, this.maxTime, milliSecondFromNano);
                if (this.q) {
                    long cumulativeReadBytes = aVar.b.cumulativeReadBytes();
                    long j3 = this.k.get();
                    if (cumulativeReadBytes <= 0) {
                        cumulativeReadBytes = 0;
                    }
                    if (j3 < cumulativeReadBytes) {
                        j3 = cumulativeReadBytes;
                    }
                    j2 = a((float) cumulativeReadBytes, (float) j3, readTimeToWait2);
                } else {
                    j2 = readTimeToWait2;
                }
            }
            if (j2 < readTimeToWait) {
                j2 = readTimeToWait;
            }
            j = milliSecondFromNano;
            long checkWaitReadTime = checkWaitReadTime(channelHandlerContext, j2, milliSecondFromNano);
            if (checkWaitReadTime >= 10) {
                ChannelConfig config = channelHandlerContext.channel().config();
                if (h.isDebugEnabled()) {
                    h.debug("Read Suspend: " + checkWaitReadTime + ':' + config.isAutoRead() + ':' + isHandlerActive(channelHandlerContext));
                }
                if (config.isAutoRead() && isHandlerActive(channelHandlerContext)) {
                    config.setAutoRead(false);
                    channelHandlerContext.attr(a).set(true);
                    Attribute attr = channelHandlerContext.attr(b);
                    Runnable runnable = (Runnable) attr.get();
                    if (runnable == null) {
                        runnable = new AbstractTrafficShapingHandler.a(channelHandlerContext);
                        attr.set(runnable);
                    }
                    channelHandlerContext.executor().schedule(runnable, checkWaitReadTime, TimeUnit.MILLISECONDS);
                    if (h.isDebugEnabled()) {
                        h.debug("Suspend final status => " + config.isAutoRead() + ':' + isHandlerActive(channelHandlerContext) + " will reopened at: " + checkWaitReadTime);
                    }
                }
            }
        } else {
            j = milliSecondFromNano;
        }
        informReadOperation(channelHandlerContext, j);
        channelHandlerContext.fireChannelRead(obj);
    }

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler
    protected long checkWaitReadTime(ChannelHandlerContext channelHandlerContext, long j, long j2) {
        a aVar = this.f.get(Integer.valueOf(channelHandlerContext.channel().hashCode()));
        return (aVar == null || j <= this.maxTime || (j2 + j) - aVar.e <= this.maxTime) ? j : this.maxTime;
    }

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler
    protected void informReadOperation(ChannelHandlerContext channelHandlerContext, long j) {
        a aVar = this.f.get(Integer.valueOf(channelHandlerContext.channel().hashCode()));
        if (aVar != null) {
            aVar.e = j;
        }
    }

    /* loaded from: classes4.dex */
    public static final class b {
        final long a;
        final Object b;
        final ChannelPromise c;
        final long d;

        private b(long j, Object obj, long j2, ChannelPromise channelPromise) {
            this.a = j;
            this.b = obj;
            this.d = j2;
            this.c = channelPromise;
        }
    }

    protected long maximumCumulativeWrittenBytes() {
        return this.j.get();
    }

    protected long maximumCumulativeReadBytes() {
        return this.k.get();
    }

    public Collection<TrafficCounter> channelTrafficCounters() {
        return new AbstractCollection<TrafficCounter>() { // from class: io.netty.handler.traffic.GlobalChannelTrafficShapingHandler.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<TrafficCounter> iterator() {
                return new Iterator<TrafficCounter>() { // from class: io.netty.handler.traffic.GlobalChannelTrafficShapingHandler.1.1
                    final Iterator<a> a;

                    {
                        AnonymousClass1.this = this;
                        this.a = GlobalChannelTrafficShapingHandler.this.f.values().iterator();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.a.hasNext();
                    }

                    /* renamed from: a */
                    public TrafficCounter next() {
                        return this.a.next().b;
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return GlobalChannelTrafficShapingHandler.this.f.size();
            }
        };
    }

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler, io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        long calculateSize = calculateSize(obj);
        long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
        long j = 0;
        if (calculateSize > 0) {
            long writeTimeToWait = this.trafficCounter.writeTimeToWait(calculateSize, getWriteLimit(), this.maxTime, milliSecondFromNano);
            a aVar = this.f.get(Integer.valueOf(channelHandlerContext.channel().hashCode()));
            if (aVar != null) {
                long writeTimeToWait2 = aVar.b.writeTimeToWait(calculateSize, this.l, this.maxTime, milliSecondFromNano);
                if (this.r) {
                    long cumulativeWrittenBytes = aVar.b.cumulativeWrittenBytes();
                    long j2 = this.j.get();
                    if (cumulativeWrittenBytes <= 0) {
                        cumulativeWrittenBytes = 0;
                    }
                    j = a((float) cumulativeWrittenBytes, (float) (j2 < cumulativeWrittenBytes ? cumulativeWrittenBytes : j2), writeTimeToWait2);
                } else {
                    j = writeTimeToWait2;
                }
            }
            if (j >= writeTimeToWait) {
                writeTimeToWait = j;
            }
            if (writeTimeToWait >= 10) {
                if (h.isDebugEnabled()) {
                    h.debug("Write suspend: " + writeTimeToWait + ':' + channelHandlerContext.channel().config().isAutoRead() + ':' + isHandlerActive(channelHandlerContext));
                }
                submitWrite(channelHandlerContext, obj, calculateSize, writeTimeToWait, milliSecondFromNano, channelPromise);
                return;
            }
        }
        submitWrite(channelHandlerContext, obj, calculateSize, 0L, milliSecondFromNano, channelPromise);
    }

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler
    protected void submitWrite(final ChannelHandlerContext channelHandlerContext, Object obj, long j, long j2, long j3, ChannelPromise channelPromise) {
        a aVar = this.f.get(Integer.valueOf(channelHandlerContext.channel().hashCode()));
        final a c = aVar == null ? c(channelHandlerContext) : aVar;
        synchronized (c) {
            if (j2 == 0) {
                if (c.a.isEmpty()) {
                    this.trafficCounter.d(j);
                    c.b.d(j);
                    channelHandlerContext.write(obj, channelPromise);
                    c.d = j3;
                    return;
                }
            }
            long j4 = (j2 <= this.maxTime || (j3 + j2) - c.d <= this.maxTime) ? j2 : this.maxTime;
            b bVar = new b(j4 + j3, obj, j, channelPromise);
            c.a.addLast(bVar);
            c.c += j;
            this.i.addAndGet(j);
            a(channelHandlerContext, j4, c.c);
            boolean z = this.i.get() > this.g;
            if (z) {
                a(channelHandlerContext, false);
            }
            final long j5 = bVar.a;
            channelHandlerContext.executor().schedule(new Runnable() { // from class: io.netty.handler.traffic.GlobalChannelTrafficShapingHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    GlobalChannelTrafficShapingHandler.this.a(channelHandlerContext, c, j5);
                }
            }, j4, TimeUnit.MILLISECONDS);
        }
    }

    public void a(ChannelHandlerContext channelHandlerContext, a aVar, long j) {
        synchronized (aVar) {
            b pollFirst = aVar.a.pollFirst();
            while (true) {
                if (pollFirst != null) {
                    if (pollFirst.a > j) {
                        aVar.a.addFirst(pollFirst);
                        break;
                    }
                    long j2 = pollFirst.d;
                    this.trafficCounter.d(j2);
                    aVar.b.d(j2);
                    aVar.c -= j2;
                    this.i.addAndGet(-j2);
                    channelHandlerContext.write(pollFirst.b, pollFirst.c);
                    aVar.d = j;
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

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler
    public String toString() {
        StringBuilder sb = new StringBuilder(340);
        sb.append(super.toString());
        sb.append(" Write Channel Limit: ");
        sb.append(this.l);
        sb.append(" Read Channel Limit: ");
        sb.append(this.m);
        return sb.toString();
    }
}
