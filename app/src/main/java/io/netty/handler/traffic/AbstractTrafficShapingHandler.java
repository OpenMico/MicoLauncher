package io.netty.handler.traffic;

import android.support.v4.media.session.PlaybackStateCompat;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class AbstractTrafficShapingHandler extends ChannelDuplexHandler {
    public static final long DEFAULT_CHECK_INTERVAL = 1000;
    public static final long DEFAULT_MAX_TIME = 15000;
    volatile long c;
    protected volatile long checkInterval;
    volatile long d;
    final int e;
    private volatile long g;
    private volatile long h;
    protected volatile long maxTime;
    protected TrafficCounter trafficCounter;
    private static final InternalLogger f = InternalLoggerFactory.getInstance(AbstractTrafficShapingHandler.class);
    static final AttributeKey<Boolean> a = AttributeKey.valueOf(AbstractTrafficShapingHandler.class.getName() + ".READ_SUSPENDED");
    static final AttributeKey<Runnable> b = AttributeKey.valueOf(AbstractTrafficShapingHandler.class.getName() + ".REOPEN_TASK");

    long checkWaitReadTime(ChannelHandlerContext channelHandlerContext, long j, long j2) {
        return j;
    }

    public void doAccounting(TrafficCounter trafficCounter) {
    }

    void informReadOperation(ChannelHandlerContext channelHandlerContext, long j) {
    }

    abstract void submitWrite(ChannelHandlerContext channelHandlerContext, Object obj, long j, long j2, long j3, ChannelPromise channelPromise);

    void a(TrafficCounter trafficCounter) {
        this.trafficCounter = trafficCounter;
    }

    int a() {
        if (this instanceof GlobalChannelTrafficShapingHandler) {
            return 3;
        }
        return this instanceof GlobalTrafficShapingHandler ? 2 : 1;
    }

    public AbstractTrafficShapingHandler(long j, long j2, long j3, long j4) {
        this.maxTime = 15000L;
        this.checkInterval = 1000L;
        this.c = 4000L;
        this.d = PlaybackStateCompat.ACTION_SET_PLAYBACK_SPEED;
        if (j4 > 0) {
            this.e = a();
            this.g = j;
            this.h = j2;
            this.checkInterval = j3;
            this.maxTime = j4;
            return;
        }
        throw new IllegalArgumentException("maxTime must be positive");
    }

    public AbstractTrafficShapingHandler(long j, long j2, long j3) {
        this(j, j2, j3, 15000L);
    }

    public AbstractTrafficShapingHandler(long j, long j2) {
        this(j, j2, 1000L, 15000L);
    }

    public AbstractTrafficShapingHandler() {
        this(0L, 0L, 1000L, 15000L);
    }

    public AbstractTrafficShapingHandler(long j) {
        this(0L, 0L, j, 15000L);
    }

    public void configure(long j, long j2, long j3) {
        configure(j, j2);
        configure(j3);
    }

    public void configure(long j, long j2) {
        this.g = j;
        this.h = j2;
        TrafficCounter trafficCounter = this.trafficCounter;
        if (trafficCounter != null) {
            trafficCounter.a(TrafficCounter.milliSecondFromNano());
        }
    }

    public void configure(long j) {
        this.checkInterval = j;
        TrafficCounter trafficCounter = this.trafficCounter;
        if (trafficCounter != null) {
            trafficCounter.configure(this.checkInterval);
        }
    }

    public long getWriteLimit() {
        return this.g;
    }

    public void setWriteLimit(long j) {
        this.g = j;
        TrafficCounter trafficCounter = this.trafficCounter;
        if (trafficCounter != null) {
            trafficCounter.a(TrafficCounter.milliSecondFromNano());
        }
    }

    public long getReadLimit() {
        return this.h;
    }

    public void setReadLimit(long j) {
        this.h = j;
        TrafficCounter trafficCounter = this.trafficCounter;
        if (trafficCounter != null) {
            trafficCounter.a(TrafficCounter.milliSecondFromNano());
        }
    }

    public long getCheckInterval() {
        return this.checkInterval;
    }

    public void setCheckInterval(long j) {
        this.checkInterval = j;
        TrafficCounter trafficCounter = this.trafficCounter;
        if (trafficCounter != null) {
            trafficCounter.configure(j);
        }
    }

    public void setMaxTimeWait(long j) {
        if (j > 0) {
            this.maxTime = j;
            return;
        }
        throw new IllegalArgumentException("maxTime must be positive");
    }

    public long getMaxTimeWait() {
        return this.maxTime;
    }

    public long getMaxWriteDelay() {
        return this.c;
    }

    public void setMaxWriteDelay(long j) {
        if (j > 0) {
            this.c = j;
            return;
        }
        throw new IllegalArgumentException("maxWriteDelay must be positive");
    }

    public long getMaxWriteSize() {
        return this.d;
    }

    public void setMaxWriteSize(long j) {
        this.d = j;
    }

    /* loaded from: classes4.dex */
    static final class a implements Runnable {
        final ChannelHandlerContext a;

        public a(ChannelHandlerContext channelHandlerContext) {
            this.a = channelHandlerContext;
        }

        @Override // java.lang.Runnable
        public void run() {
            ChannelConfig config = this.a.channel().config();
            if (config.isAutoRead() || !AbstractTrafficShapingHandler.isHandlerActive(this.a)) {
                if (AbstractTrafficShapingHandler.f.isDebugEnabled()) {
                    if (!config.isAutoRead() || AbstractTrafficShapingHandler.isHandlerActive(this.a)) {
                        InternalLogger internalLogger = AbstractTrafficShapingHandler.f;
                        internalLogger.debug("Normal unsuspend: " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.a));
                    } else {
                        InternalLogger internalLogger2 = AbstractTrafficShapingHandler.f;
                        internalLogger2.debug("Unsuspend: " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.a));
                    }
                }
                this.a.attr(AbstractTrafficShapingHandler.a).set(false);
                config.setAutoRead(true);
                this.a.channel().read();
            } else {
                if (AbstractTrafficShapingHandler.f.isDebugEnabled()) {
                    InternalLogger internalLogger3 = AbstractTrafficShapingHandler.f;
                    internalLogger3.debug("Not unsuspend: " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.a));
                }
                this.a.attr(AbstractTrafficShapingHandler.a).set(false);
            }
            if (AbstractTrafficShapingHandler.f.isDebugEnabled()) {
                InternalLogger internalLogger4 = AbstractTrafficShapingHandler.f;
                internalLogger4.debug("Unsupsend final status => " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.a));
            }
        }
    }

    void a(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.attr(a).set(false);
        channelHandlerContext.channel().config().setAutoRead(true);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        long calculateSize = calculateSize(obj);
        long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
        if (calculateSize > 0) {
            long checkWaitReadTime = checkWaitReadTime(channelHandlerContext, this.trafficCounter.readTimeToWait(calculateSize, this.h, this.maxTime, milliSecondFromNano), milliSecondFromNano);
            if (checkWaitReadTime >= 10) {
                ChannelConfig config = channelHandlerContext.channel().config();
                if (f.isDebugEnabled()) {
                    InternalLogger internalLogger = f;
                    internalLogger.debug("Read suspend: " + checkWaitReadTime + ':' + config.isAutoRead() + ':' + isHandlerActive(channelHandlerContext));
                }
                if (config.isAutoRead() && isHandlerActive(channelHandlerContext)) {
                    config.setAutoRead(false);
                    channelHandlerContext.attr(a).set(true);
                    Attribute attr = channelHandlerContext.attr(b);
                    Runnable runnable = (Runnable) attr.get();
                    if (runnable == null) {
                        runnable = new a(channelHandlerContext);
                        attr.set(runnable);
                    }
                    channelHandlerContext.executor().schedule(runnable, checkWaitReadTime, TimeUnit.MILLISECONDS);
                    if (f.isDebugEnabled()) {
                        InternalLogger internalLogger2 = f;
                        internalLogger2.debug("Suspend final status => " + config.isAutoRead() + ':' + isHandlerActive(channelHandlerContext) + " will reopened at: " + checkWaitReadTime);
                    }
                }
            }
        }
        informReadOperation(channelHandlerContext, milliSecondFromNano);
        channelHandlerContext.fireChannelRead(obj);
    }

    protected static boolean isHandlerActive(ChannelHandlerContext channelHandlerContext) {
        Boolean bool = (Boolean) channelHandlerContext.attr(a).get();
        return bool == null || Boolean.FALSE.equals(bool);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void read(ChannelHandlerContext channelHandlerContext) {
        if (isHandlerActive(channelHandlerContext)) {
            channelHandlerContext.read();
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        long calculateSize = calculateSize(obj);
        long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
        if (calculateSize > 0) {
            long writeTimeToWait = this.trafficCounter.writeTimeToWait(calculateSize, this.g, this.maxTime, milliSecondFromNano);
            if (writeTimeToWait >= 10) {
                if (f.isDebugEnabled()) {
                    InternalLogger internalLogger = f;
                    internalLogger.debug("Write suspend: " + writeTimeToWait + ':' + channelHandlerContext.channel().config().isAutoRead() + ':' + isHandlerActive(channelHandlerContext));
                }
                submitWrite(channelHandlerContext, obj, calculateSize, writeTimeToWait, milliSecondFromNano, channelPromise);
                return;
            }
        }
        submitWrite(channelHandlerContext, obj, calculateSize, 0L, milliSecondFromNano, channelPromise);
    }

    @Deprecated
    protected void submitWrite(ChannelHandlerContext channelHandlerContext, Object obj, long j, ChannelPromise channelPromise) {
        submitWrite(channelHandlerContext, obj, calculateSize(obj), j, TrafficCounter.milliSecondFromNano(), channelPromise);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        a(channelHandlerContext, true);
        super.channelRegistered(channelHandlerContext);
    }

    void a(ChannelHandlerContext channelHandlerContext, boolean z) {
        ChannelOutboundBuffer outboundBuffer = channelHandlerContext.channel().unsafe().outboundBuffer();
        if (outboundBuffer != null) {
            outboundBuffer.setUserDefinedWritability(this.e, z);
        }
    }

    void a(ChannelHandlerContext channelHandlerContext, long j, long j2) {
        if (j2 > this.d || j > this.c) {
            a(channelHandlerContext, false);
        }
    }

    void b(ChannelHandlerContext channelHandlerContext) {
        a(channelHandlerContext, true);
    }

    public TrafficCounter trafficCounter() {
        return this.trafficCounter;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(290);
        sb.append("TrafficShaping with Write Limit: ");
        sb.append(this.g);
        sb.append(" Read Limit: ");
        sb.append(this.h);
        sb.append(" CheckInterval: ");
        sb.append(this.checkInterval);
        sb.append(" maxDelay: ");
        sb.append(this.c);
        sb.append(" maxSize: ");
        sb.append(this.d);
        sb.append(" and Counter: ");
        TrafficCounter trafficCounter = this.trafficCounter;
        if (trafficCounter != null) {
            sb.append(trafficCounter);
        } else {
            sb.append("none");
        }
        return sb.toString();
    }

    protected long calculateSize(Object obj) {
        if (obj instanceof ByteBuf) {
            return ((ByteBuf) obj).readableBytes();
        }
        if (obj instanceof ByteBufHolder) {
            return ((ByteBufHolder) obj).content().readableBytes();
        }
        return -1L;
    }
}
