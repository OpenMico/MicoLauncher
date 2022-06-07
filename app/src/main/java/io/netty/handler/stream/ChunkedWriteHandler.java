package io.netty.handler.stream;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

/* loaded from: classes4.dex */
public class ChunkedWriteHandler extends ChannelDuplexHandler {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);
    private final Queue<a> b = new ArrayDeque();
    private volatile ChannelHandlerContext c;
    private a d;

    public ChunkedWriteHandler() {
    }

    @Deprecated
    public ChunkedWriteHandler(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxPendingWrites: " + i + " (expected: > 0)");
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.c = channelHandlerContext;
    }

    public void resumeTransfer() {
        final ChannelHandlerContext channelHandlerContext = this.c;
        if (channelHandlerContext != null) {
            if (channelHandlerContext.executor().inEventLoop()) {
                try {
                    a(channelHandlerContext);
                } catch (Exception e) {
                    if (a.isWarnEnabled()) {
                        a.warn("Unexpected exception while sending chunks.", (Throwable) e);
                    }
                }
            } else {
                channelHandlerContext.executor().execute(new OneTimeTask() { // from class: io.netty.handler.stream.ChunkedWriteHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            ChunkedWriteHandler.this.a(channelHandlerContext);
                        } catch (Exception e2) {
                            if (ChunkedWriteHandler.a.isWarnEnabled()) {
                                ChunkedWriteHandler.a.warn("Unexpected exception while sending chunks.", (Throwable) e2);
                            }
                        }
                    }
                });
            }
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        this.b.add(new a(obj, channelPromise));
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!a(channelHandlerContext)) {
            channelHandlerContext.flush();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        a(channelHandlerContext);
        channelHandlerContext.fireChannelInactive();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isWritable()) {
            a(channelHandlerContext);
        }
        channelHandlerContext.fireChannelWritabilityChanged();
    }

    private void a(Throwable th) {
        while (true) {
            a aVar = this.d;
            if (aVar == null) {
                aVar = this.b.poll();
            } else {
                this.d = null;
            }
            if (aVar != null) {
                Object obj = aVar.a;
                if (obj instanceof ChunkedInput) {
                    ChunkedInput chunkedInput = (ChunkedInput) obj;
                    try {
                        if (!chunkedInput.isEndOfInput()) {
                            if (th == null) {
                                th = new ClosedChannelException();
                            }
                            aVar.a(th);
                        } else {
                            aVar.a(chunkedInput.length());
                        }
                        a(chunkedInput);
                    } catch (Exception e) {
                        aVar.a(e);
                        InternalLogger internalLogger = a;
                        internalLogger.warn(ChunkedInput.class.getSimpleName() + ".isEndOfInput() failed", (Throwable) e);
                        a(chunkedInput);
                    }
                } else {
                    if (th == null) {
                        th = new ClosedChannelException();
                    }
                    aVar.a(th);
                }
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(ChannelHandlerContext channelHandlerContext) throws Exception {
        Throwable th;
        Object obj;
        final Channel channel = channelHandlerContext.channel();
        if (!channel.isActive()) {
            a((Throwable) null);
            return false;
        }
        ByteBufAllocator alloc = channelHandlerContext.alloc();
        boolean z = false;
        while (channel.isWritable()) {
            if (this.d == null) {
                this.d = this.b.poll();
            }
            final a aVar = this.d;
            if (aVar == null) {
                return z;
            }
            final Object obj2 = aVar.a;
            if (obj2 instanceof ChunkedInput) {
                final ChunkedInput chunkedInput = (ChunkedInput) obj2;
                try {
                    obj = chunkedInput.readChunk(alloc);
                    try {
                        boolean isEndOfInput = chunkedInput.isEndOfInput();
                        if (obj == null ? !isEndOfInput : false) {
                            return z;
                        }
                        if (obj == null) {
                            obj = Unpooled.EMPTY_BUFFER;
                        }
                        ChannelFuture write = channelHandlerContext.write(obj);
                        if (isEndOfInput) {
                            this.d = null;
                            write.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.stream.ChunkedWriteHandler.2
                                /* renamed from: a */
                                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                    aVar.a(chunkedInput.progress(), chunkedInput.length());
                                    aVar.a(chunkedInput.length());
                                    ChunkedWriteHandler.a(chunkedInput);
                                }
                            });
                        } else if (channel.isWritable()) {
                            write.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.stream.ChunkedWriteHandler.3
                                /* renamed from: a */
                                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                    if (!channelFuture.isSuccess()) {
                                        ChunkedWriteHandler.a((ChunkedInput) obj2);
                                        aVar.a(channelFuture.cause());
                                        return;
                                    }
                                    aVar.a(chunkedInput.progress(), chunkedInput.length());
                                }
                            });
                        } else {
                            write.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.stream.ChunkedWriteHandler.4
                                /* renamed from: a */
                                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                    if (!channelFuture.isSuccess()) {
                                        ChunkedWriteHandler.a((ChunkedInput) obj2);
                                        aVar.a(channelFuture.cause());
                                        return;
                                    }
                                    aVar.a(chunkedInput.progress(), chunkedInput.length());
                                    if (channel.isWritable()) {
                                        ChunkedWriteHandler.this.resumeTransfer();
                                    }
                                }
                            });
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        this.d = null;
                        if (obj != null) {
                            ReferenceCountUtil.release(obj);
                        }
                        aVar.a(th);
                        a(chunkedInput);
                        return z;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    obj = null;
                }
            } else {
                channelHandlerContext.write(obj2, aVar.b);
                this.d = null;
            }
            channelHandlerContext.flush();
            if (!channel.isActive()) {
                a(new ClosedChannelException());
                return true;
            }
            z = true;
        }
        return z;
    }

    static void a(ChunkedInput<?> chunkedInput) {
        try {
            chunkedInput.close();
        } catch (Throwable th) {
            if (a.isWarnEnabled()) {
                a.warn("Failed to close a chunked input.", th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a {
        final Object a;
        final ChannelPromise b;

        a(Object obj, ChannelPromise channelPromise) {
            this.a = obj;
            this.b = channelPromise;
        }

        void a(Throwable th) {
            ReferenceCountUtil.release(this.a);
            this.b.tryFailure(th);
        }

        void a(long j) {
            if (!this.b.isDone()) {
                ChannelPromise channelPromise = this.b;
                if (channelPromise instanceof ChannelProgressivePromise) {
                    ((ChannelProgressivePromise) channelPromise).tryProgress(j, j);
                }
                this.b.trySuccess();
            }
        }

        void a(long j, long j2) {
            ChannelPromise channelPromise = this.b;
            if (channelPromise instanceof ChannelProgressivePromise) {
                ((ChannelProgressivePromise) channelPromise).tryProgress(j, j2);
            }
        }
    }
}
