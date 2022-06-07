package io.netty.handler.timeout;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ReadTimeoutHandler extends ChannelInboundHandlerAdapter {
    private static final long a = TimeUnit.MILLISECONDS.toNanos(1);
    private final long b;
    private long c;
    private volatile ScheduledFuture<?> d;
    private volatile int e;
    private volatile boolean f;
    private boolean g;

    public ReadTimeoutHandler(int i) {
        this(i, TimeUnit.SECONDS);
    }

    public ReadTimeoutHandler(long j, TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        } else if (j <= 0) {
            this.b = 0L;
        } else {
            this.b = Math.max(timeUnit.toNanos(j), a);
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isActive() && channelHandlerContext.channel().isRegistered()) {
            a(channelHandlerContext);
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        a();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isActive()) {
            a(channelHandlerContext);
        }
        super.channelRegistered(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        a(channelHandlerContext);
        super.channelActive(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        a();
        super.channelInactive(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        this.f = true;
        channelHandlerContext.fireChannelRead(obj);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.c = System.nanoTime();
        this.f = false;
        channelHandlerContext.fireChannelReadComplete();
    }

    private void a(ChannelHandlerContext channelHandlerContext) {
        switch (this.e) {
            case 1:
            case 2:
                return;
            default:
                this.e = 1;
                this.c = System.nanoTime();
                if (this.b > 0) {
                    this.d = channelHandlerContext.executor().schedule((Runnable) new a(channelHandlerContext), this.b, TimeUnit.NANOSECONDS);
                    return;
                }
                return;
        }
    }

    private void a() {
        this.e = 2;
        if (this.d != null) {
            this.d.cancel(false);
            this.d = null;
        }
    }

    protected void readTimedOut(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.g) {
            channelHandlerContext.fireExceptionCaught((Throwable) ReadTimeoutException.INSTANCE);
            channelHandlerContext.close();
            this.g = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a implements Runnable {
        private final ChannelHandlerContext b;

        a(ChannelHandlerContext channelHandlerContext) {
            this.b = channelHandlerContext;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.b.channel().isOpen()) {
                long j = ReadTimeoutHandler.this.b;
                if (!ReadTimeoutHandler.this.f) {
                    j -= System.nanoTime() - ReadTimeoutHandler.this.c;
                }
                if (j <= 0) {
                    ReadTimeoutHandler.this.d = this.b.executor().schedule((Runnable) this, ReadTimeoutHandler.this.b, TimeUnit.NANOSECONDS);
                    try {
                        ReadTimeoutHandler.this.readTimedOut(this.b);
                    } catch (Throwable th) {
                        this.b.fireExceptionCaught(th);
                    }
                } else {
                    ReadTimeoutHandler.this.d = this.b.executor().schedule((Runnable) this, j, TimeUnit.NANOSECONDS);
                }
            }
        }
    }
}
