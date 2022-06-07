package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class Bzip2Encoder extends MessageToByteEncoder<ByteBuf> {
    private a a;
    private final b b;
    private final int c;
    private int d;
    private c e;
    private volatile boolean f;
    private volatile ChannelHandlerContext g;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public enum a {
        INIT,
        INIT_BLOCK,
        WRITE_DATA,
        CLOSE_BLOCK
    }

    public Bzip2Encoder() {
        this(9);
    }

    public Bzip2Encoder(int i) {
        this.a = a.INIT;
        this.b = new b();
        if (i < 1 || i > 9) {
            throw new IllegalArgumentException("blockSizeMultiplier: " + i + " (expected: 1-9)");
        }
        this.c = i * 100000;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int i;
        byte[] bArr;
        if (this.f) {
            byteBuf2.writeBytes(byteBuf);
            return;
        }
        while (true) {
            switch (this.a) {
                case INIT:
                    byteBuf2.ensureWritable(4);
                    byteBuf2.writeMedium(4348520);
                    byteBuf2.writeByte((this.c / 100000) + 48);
                    this.a = a.INIT_BLOCK;
                case INIT_BLOCK:
                    this.e = new c(this.b, this.c);
                    this.a = a.WRITE_DATA;
                case WRITE_DATA:
                    if (byteBuf.isReadable()) {
                        c cVar = this.e;
                        int readableBytes = byteBuf.readableBytes() < cVar.a() ? byteBuf.readableBytes() : cVar.a();
                        if (byteBuf.hasArray()) {
                            bArr = byteBuf.array();
                            i = byteBuf.arrayOffset() + byteBuf.readerIndex();
                        } else {
                            bArr = new byte[readableBytes];
                            byteBuf.getBytes(byteBuf.readerIndex(), bArr);
                            i = 0;
                        }
                        byteBuf.skipBytes(cVar.a(bArr, i, readableBytes));
                        if (cVar.b()) {
                            this.a = a.CLOSE_BLOCK;
                            a(byteBuf2);
                            this.a = a.INIT_BLOCK;
                            break;
                        } else if (byteBuf.isReadable()) {
                            break;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                case CLOSE_BLOCK:
                    a(byteBuf2);
                    this.a = a.INIT_BLOCK;
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    private void a(ByteBuf byteBuf) {
        c cVar = this.e;
        if (!cVar.c()) {
            cVar.a(byteBuf);
            int d = cVar.d();
            int i = this.d;
            this.d = d ^ ((i >>> 31) | (i << 1));
        }
    }

    public boolean isClosed() {
        return this.f;
    }

    public ChannelFuture close() {
        return close(a().newPromise());
    }

    public ChannelFuture close(final ChannelPromise channelPromise) {
        ChannelHandlerContext a2 = a();
        EventExecutor executor = a2.executor();
        if (executor.inEventLoop()) {
            return a(a2, channelPromise);
        }
        executor.execute(new Runnable() { // from class: io.netty.handler.codec.compression.Bzip2Encoder.1
            @Override // java.lang.Runnable
            public void run() {
                Bzip2Encoder bzip2Encoder = Bzip2Encoder.this;
                bzip2Encoder.a(bzip2Encoder.a(), channelPromise).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelPromiseNotifier(channelPromise));
            }
        });
        return channelPromise;
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        ChannelFuture a2 = a(channelHandlerContext, channelHandlerContext.newPromise());
        a2.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.compression.Bzip2Encoder.2
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                channelHandlerContext.close(channelPromise);
            }
        });
        if (!a2.isDone()) {
            channelHandlerContext.executor().schedule(new Runnable() { // from class: io.netty.handler.codec.compression.Bzip2Encoder.3
                @Override // java.lang.Runnable
                public void run() {
                    channelHandlerContext.close(channelPromise);
                }
            }, 10L, TimeUnit.SECONDS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Finally extract failed */
    public ChannelFuture a(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        if (this.f) {
            channelPromise.setSuccess();
            return channelPromise;
        }
        this.f = true;
        ByteBuf buffer = channelHandlerContext.alloc().buffer();
        a(buffer);
        int i = this.d;
        b bVar = this.b;
        try {
            bVar.a(buffer, 24, 1536581L);
            bVar.a(buffer, 24, 3690640L);
            bVar.b(buffer, i);
            bVar.a(buffer);
            this.e = null;
            return channelHandlerContext.writeAndFlush(buffer, channelPromise);
        } catch (Throwable th) {
            this.e = null;
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ChannelHandlerContext a() {
        ChannelHandlerContext channelHandlerContext = this.g;
        if (channelHandlerContext != null) {
            return channelHandlerContext;
        }
        throw new IllegalStateException("not added to a pipeline");
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.g = channelHandlerContext;
    }
}
