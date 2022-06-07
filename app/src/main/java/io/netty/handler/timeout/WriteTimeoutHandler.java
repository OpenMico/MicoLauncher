package io.netty.handler.timeout;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.OneTimeTask;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class WriteTimeoutHandler extends ChannelOutboundHandlerAdapter {
    static final /* synthetic */ boolean a = !WriteTimeoutHandler.class.desiredAssertionStatus();
    private static final long b = TimeUnit.MILLISECONDS.toNanos(1);
    private final long c;
    private a d;
    private boolean e;

    public WriteTimeoutHandler(int i) {
        this(i, TimeUnit.SECONDS);
    }

    public WriteTimeoutHandler(long j, TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        } else if (j <= 0) {
            this.c = 0L;
        } else {
            this.c = Math.max(timeUnit.toNanos(j), b);
        }
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (this.c > 0) {
            channelPromise = channelPromise.unvoid();
            a(channelHandlerContext, channelPromise);
        }
        channelHandlerContext.write(obj, channelPromise);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        a aVar = this.d;
        this.d = null;
        while (aVar != null) {
            aVar.c.cancel(false);
            a aVar2 = aVar.a;
            aVar.a = null;
            aVar.b = null;
            aVar = aVar2;
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        a aVar = new a(channelHandlerContext, channelPromise);
        aVar.c = channelHandlerContext.executor().schedule((Runnable) aVar, this.c, TimeUnit.NANOSECONDS);
        if (!aVar.c.isDone()) {
            a(aVar);
            channelPromise.addListener((GenericFutureListener<? extends Future<? super Void>>) aVar);
        }
    }

    private void a(a aVar) {
        a aVar2 = this.d;
        if (aVar2 == null) {
            this.d = aVar;
            return;
        }
        aVar2.b = aVar;
        aVar.a = aVar2;
        this.d = aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(a aVar) {
        if (aVar == this.d) {
            if (a || aVar.b == null) {
                this.d = this.d.a;
                a aVar2 = this.d;
                if (aVar2 != null) {
                    aVar2.b = null;
                }
            } else {
                throw new AssertionError();
            }
        } else if (aVar.a != null || aVar.b != null) {
            if (aVar.a == null) {
                aVar.b.a = null;
            } else {
                aVar.a.b = aVar.b;
                aVar.b.a = aVar.a;
            }
        } else {
            return;
        }
        aVar.a = null;
        aVar.b = null;
    }

    protected void writeTimedOut(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.e) {
            channelHandlerContext.fireExceptionCaught((Throwable) WriteTimeoutException.INSTANCE);
            channelHandlerContext.close();
            this.e = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a extends OneTimeTask implements ChannelFutureListener {
        a a;
        a b;
        ScheduledFuture<?> c;
        private final ChannelHandlerContext e;
        private final ChannelPromise f;

        a(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
            this.e = channelHandlerContext;
            this.f = channelPromise;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.f.isDone()) {
                try {
                    WriteTimeoutHandler.this.writeTimedOut(this.e);
                } catch (Throwable th) {
                    this.e.fireExceptionCaught(th);
                }
            }
            WriteTimeoutHandler.this.b(this);
        }

        /* renamed from: a */
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            this.c.cancel(false);
            WriteTimeoutHandler.this.b(this);
        }
    }
}
