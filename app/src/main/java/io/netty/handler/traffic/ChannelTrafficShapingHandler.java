package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.OneTimeTask;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private final ArrayDeque<a> f = new ArrayDeque<>();
    private long g;

    public ChannelTrafficShapingHandler(long j, long j2, long j3, long j4) {
        super(j, j2, j3, j4);
    }

    public ChannelTrafficShapingHandler(long j, long j2, long j3) {
        super(j, j2, j3);
    }

    public ChannelTrafficShapingHandler(long j, long j2) {
        super(j, j2);
    }

    public ChannelTrafficShapingHandler(long j) {
        super(j);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        EventExecutor executor = channelHandlerContext.executor();
        TrafficCounter trafficCounter = new TrafficCounter(this, executor, "ChannelTC" + channelHandlerContext.channel().hashCode(), this.checkInterval);
        a(trafficCounter);
        trafficCounter.start();
        super.handlerAdded(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.trafficCounter.stop();
        synchronized (this) {
            if (channelHandlerContext.channel().isActive()) {
                Iterator<a> it = this.f.iterator();
                while (it.hasNext()) {
                    a next = it.next();
                    long calculateSize = calculateSize(next.b);
                    this.trafficCounter.d(calculateSize);
                    this.g -= calculateSize;
                    channelHandlerContext.write(next.b, next.c);
                }
            } else {
                Iterator<a> it2 = this.f.iterator();
                while (it2.hasNext()) {
                    a next2 = it2.next();
                    if (next2.b instanceof ByteBuf) {
                        ((ByteBuf) next2.b).release();
                    }
                }
            }
            this.f.clear();
        }
        b(channelHandlerContext);
        a(channelHandlerContext);
        super.handlerRemoved(channelHandlerContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a {
        final long a;
        final Object b;
        final ChannelPromise c;

        private a(long j, Object obj, ChannelPromise channelPromise) {
            this.a = j;
            this.b = obj;
            this.c = channelPromise;
        }
    }

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler
    void submitWrite(final ChannelHandlerContext channelHandlerContext, Object obj, long j, long j2, long j3, ChannelPromise channelPromise) {
        synchronized (this) {
            if (j2 == 0) {
                if (this.f.isEmpty()) {
                    this.trafficCounter.d(j);
                    channelHandlerContext.write(obj, channelPromise);
                    return;
                }
            }
            a aVar = new a(j2 + j3, obj, channelPromise);
            this.f.addLast(aVar);
            this.g += j;
            a(channelHandlerContext, j2, this.g);
            final long j4 = aVar.a;
            channelHandlerContext.executor().schedule((Runnable) new OneTimeTask() { // from class: io.netty.handler.traffic.ChannelTrafficShapingHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    ChannelTrafficShapingHandler.this.a(channelHandlerContext, j4);
                }
            }, j2, TimeUnit.MILLISECONDS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ChannelHandlerContext channelHandlerContext, long j) {
        synchronized (this) {
            a pollFirst = this.f.pollFirst();
            while (true) {
                if (pollFirst != null) {
                    if (pollFirst.a > j) {
                        this.f.addFirst(pollFirst);
                        break;
                    }
                    long calculateSize = calculateSize(pollFirst.b);
                    this.trafficCounter.d(calculateSize);
                    this.g -= calculateSize;
                    channelHandlerContext.write(pollFirst.b, pollFirst.c);
                    pollFirst = this.f.pollFirst();
                } else {
                    break;
                }
            }
            if (this.f.isEmpty()) {
                b(channelHandlerContext);
            }
        }
        channelHandlerContext.flush();
    }

    public long queueSize() {
        return this.g;
    }
}
