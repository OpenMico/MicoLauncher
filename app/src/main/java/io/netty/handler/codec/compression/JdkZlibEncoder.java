package io.netty.handler.codec.compression;

import com.google.common.base.Ascii;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.OneTimeTask;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

/* loaded from: classes4.dex */
public class JdkZlibEncoder extends ZlibEncoder {
    private static final byte[] f = {Ascii.US, -117, 8, 0, 0, 0, 0, 0, 0, 0};
    private final ZlibWrapper a;
    private final Deflater b;
    private volatile boolean c;
    private volatile ChannelHandlerContext d;
    private final CRC32 e;
    private boolean g;

    public JdkZlibEncoder() {
        this(6);
    }

    public JdkZlibEncoder(int i) {
        this(ZlibWrapper.ZLIB, i);
    }

    public JdkZlibEncoder(ZlibWrapper zlibWrapper) {
        this(zlibWrapper, 6);
    }

    public JdkZlibEncoder(ZlibWrapper zlibWrapper, int i) {
        this.e = new CRC32();
        boolean z = true;
        this.g = true;
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("compressionLevel: " + i + " (expected: 0-9)");
        } else if (zlibWrapper == null) {
            throw new NullPointerException("wrapper");
        } else if (zlibWrapper != ZlibWrapper.ZLIB_OR_NONE) {
            this.a = zlibWrapper;
            this.b = new Deflater(i, zlibWrapper == ZlibWrapper.ZLIB ? false : z);
        } else {
            throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not allowed for compression.");
        }
    }

    public JdkZlibEncoder(byte[] bArr) {
        this(6, bArr);
    }

    public JdkZlibEncoder(int i, byte[] bArr) {
        this.e = new CRC32();
        this.g = true;
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("compressionLevel: " + i + " (expected: 0-9)");
        } else if (bArr != null) {
            this.a = ZlibWrapper.ZLIB;
            this.b = new Deflater(i);
            this.b.setDictionary(bArr);
        } else {
            throw new NullPointerException("dictionary");
        }
    }

    @Override // io.netty.handler.codec.compression.ZlibEncoder
    public ChannelFuture close() {
        return close(a().newPromise());
    }

    @Override // io.netty.handler.codec.compression.ZlibEncoder
    public ChannelFuture close(final ChannelPromise channelPromise) {
        ChannelHandlerContext a = a();
        EventExecutor executor = a.executor();
        if (executor.inEventLoop()) {
            return a(a, channelPromise);
        }
        final ChannelPromise newPromise = a.newPromise();
        executor.execute(new OneTimeTask() { // from class: io.netty.handler.codec.compression.JdkZlibEncoder.1
            @Override // java.lang.Runnable
            public void run() {
                JdkZlibEncoder jdkZlibEncoder = JdkZlibEncoder.this;
                jdkZlibEncoder.a(jdkZlibEncoder.a(), newPromise).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelPromiseNotifier(channelPromise));
            }
        });
        return newPromise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ChannelHandlerContext a() {
        ChannelHandlerContext channelHandlerContext = this.d;
        if (channelHandlerContext != null) {
            return channelHandlerContext;
        }
        throw new IllegalStateException("not added to a pipeline");
    }

    @Override // io.netty.handler.codec.compression.ZlibEncoder
    public boolean isClosed() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int i;
        byte[] bArr;
        if (this.c) {
            byteBuf2.writeBytes(byteBuf);
            return;
        }
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes != 0) {
            if (byteBuf.hasArray()) {
                bArr = byteBuf.array();
                i = byteBuf.arrayOffset() + byteBuf.readerIndex();
                byteBuf.skipBytes(readableBytes);
            } else {
                bArr = new byte[readableBytes];
                byteBuf.readBytes(bArr);
                i = 0;
            }
            if (this.g) {
                this.g = false;
                if (this.a == ZlibWrapper.GZIP) {
                    byteBuf2.writeBytes(f);
                }
            }
            if (this.a == ZlibWrapper.GZIP) {
                this.e.update(bArr, i, readableBytes);
            }
            this.b.setInput(bArr, i, readableBytes);
            while (!this.b.needsInput()) {
                a(byteBuf2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, boolean z) throws Exception {
        int ceil = ((int) Math.ceil(byteBuf.readableBytes() * 1.001d)) + 12;
        if (this.g) {
            switch (this.a) {
                case GZIP:
                    ceil += f.length;
                    break;
                case ZLIB:
                    ceil += 2;
                    break;
            }
        }
        return channelHandlerContext.alloc().heapBuffer(ceil);
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        ChannelFuture a = a(channelHandlerContext, channelHandlerContext.newPromise());
        a.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.compression.JdkZlibEncoder.2
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                channelHandlerContext.close(channelPromise);
            }
        });
        if (!a.isDone()) {
            channelHandlerContext.executor().schedule((Runnable) new OneTimeTask() { // from class: io.netty.handler.codec.compression.JdkZlibEncoder.3
                @Override // java.lang.Runnable
                public void run() {
                    channelHandlerContext.close(channelPromise);
                }
            }, 10L, TimeUnit.SECONDS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ChannelFuture a(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        if (this.c) {
            channelPromise.setSuccess();
            return channelPromise;
        }
        this.c = true;
        ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer();
        if (this.g && this.a == ZlibWrapper.GZIP) {
            this.g = false;
            heapBuffer.writeBytes(f);
        }
        this.b.finish();
        while (!this.b.finished()) {
            a(heapBuffer);
            if (!heapBuffer.isWritable()) {
                channelHandlerContext.write(heapBuffer);
                heapBuffer = channelHandlerContext.alloc().heapBuffer();
            }
        }
        if (this.a == ZlibWrapper.GZIP) {
            int value = (int) this.e.getValue();
            int totalIn = this.b.getTotalIn();
            heapBuffer.writeByte(value);
            heapBuffer.writeByte(value >>> 8);
            heapBuffer.writeByte(value >>> 16);
            heapBuffer.writeByte(value >>> 24);
            heapBuffer.writeByte(totalIn);
            heapBuffer.writeByte(totalIn >>> 8);
            heapBuffer.writeByte(totalIn >>> 16);
            heapBuffer.writeByte(totalIn >>> 24);
        }
        this.b.end();
        return channelHandlerContext.writeAndFlush(heapBuffer, channelPromise);
    }

    private void a(ByteBuf byteBuf) {
        int deflate;
        do {
            int writerIndex = byteBuf.writerIndex();
            deflate = this.b.deflate(byteBuf.array(), byteBuf.arrayOffset() + writerIndex, byteBuf.writableBytes(), 2);
            byteBuf.writerIndex(writerIndex + deflate);
        } while (deflate > 0);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.d = channelHandlerContext;
    }
}
