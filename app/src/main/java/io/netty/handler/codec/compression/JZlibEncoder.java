package io.netty.handler.codec.compression;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.JZlib;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.OneTimeTask;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class JZlibEncoder extends ZlibEncoder {
    private final int a;
    private final Deflater b;
    private volatile boolean c;
    private volatile ChannelHandlerContext d;

    public JZlibEncoder() {
        this(6);
    }

    public JZlibEncoder(int i) {
        this(ZlibWrapper.ZLIB, i);
    }

    public JZlibEncoder(ZlibWrapper zlibWrapper) {
        this(zlibWrapper, 6);
    }

    public JZlibEncoder(ZlibWrapper zlibWrapper, int i) {
        this(zlibWrapper, i, 15, 8);
    }

    public JZlibEncoder(ZlibWrapper zlibWrapper, int i, int i2, int i3) {
        this.b = new Deflater();
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("compressionLevel: " + i + " (expected: 0-9)");
        } else if (i2 < 9 || i2 > 15) {
            throw new IllegalArgumentException("windowBits: " + i2 + " (expected: 9-15)");
        } else if (i3 < 1 || i3 > 9) {
            throw new IllegalArgumentException("memLevel: " + i3 + " (expected: 1-9)");
        } else if (zlibWrapper == null) {
            throw new NullPointerException("wrapper");
        } else if (zlibWrapper != ZlibWrapper.ZLIB_OR_NONE) {
            int init = this.b.init(i, i2, i3, o.a(zlibWrapper));
            if (init != 0) {
                o.a(this.b, "initialization failure", init);
            }
            this.a = o.b(zlibWrapper);
        } else {
            throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not allowed for compression.");
        }
    }

    public JZlibEncoder(byte[] bArr) {
        this(6, bArr);
    }

    public JZlibEncoder(int i, byte[] bArr) {
        this(i, 15, 8, bArr);
    }

    public JZlibEncoder(int i, int i2, int i3, byte[] bArr) {
        this.b = new Deflater();
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("compressionLevel: " + i + " (expected: 0-9)");
        } else if (i2 < 9 || i2 > 15) {
            throw new IllegalArgumentException("windowBits: " + i2 + " (expected: 9-15)");
        } else if (i3 < 1 || i3 > 9) {
            throw new IllegalArgumentException("memLevel: " + i3 + " (expected: 1-9)");
        } else if (bArr != null) {
            int deflateInit = this.b.deflateInit(i, i2, i3, JZlib.W_ZLIB);
            if (deflateInit != 0) {
                o.a(this.b, "initialization failure", deflateInit);
            } else {
                int deflateSetDictionary = this.b.deflateSetDictionary(bArr, bArr.length);
                if (deflateSetDictionary != 0) {
                    o.a(this.b, "failed to set the dictionary", deflateSetDictionary);
                }
            }
            this.a = o.b(ZlibWrapper.ZLIB);
        } else {
            throw new NullPointerException("dictionary");
        }
    }

    @Override // io.netty.handler.codec.compression.ZlibEncoder
    public ChannelFuture close() {
        return close(a().channel().newPromise());
    }

    @Override // io.netty.handler.codec.compression.ZlibEncoder
    public ChannelFuture close(final ChannelPromise channelPromise) {
        ChannelHandlerContext a = a();
        EventExecutor executor = a.executor();
        if (executor.inEventLoop()) {
            return a(a, channelPromise);
        }
        final ChannelPromise newPromise = a.newPromise();
        executor.execute(new OneTimeTask() { // from class: io.netty.handler.codec.compression.JZlibEncoder.1
            @Override // java.lang.Runnable
            public void run() {
                JZlibEncoder jZlibEncoder = JZlibEncoder.this;
                jZlibEncoder.a(jZlibEncoder.a(), newPromise).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelPromiseNotifier(channelPromise));
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
        if (this.c) {
            byteBuf2.writeBytes(byteBuf);
            return;
        }
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes != 0) {
            try {
                boolean hasArray = byteBuf.hasArray();
                this.b.avail_in = readableBytes;
                if (hasArray) {
                    this.b.next_in = byteBuf.array();
                    this.b.next_in_index = byteBuf.arrayOffset() + byteBuf.readerIndex();
                } else {
                    byte[] bArr = new byte[readableBytes];
                    byteBuf.getBytes(byteBuf.readerIndex(), bArr);
                    this.b.next_in = bArr;
                    this.b.next_in_index = 0;
                }
                int i = this.b.next_in_index;
                int ceil = ((int) Math.ceil(readableBytes * 1.001d)) + 12 + this.a;
                byteBuf2.ensureWritable(ceil);
                this.b.avail_out = ceil;
                this.b.next_out = byteBuf2.array();
                this.b.next_out_index = byteBuf2.arrayOffset() + byteBuf2.writerIndex();
                int i2 = this.b.next_out_index;
                int deflate = this.b.deflate(2);
                byteBuf.skipBytes(this.b.next_in_index - i);
                if (deflate != 0) {
                    o.a(this.b, "compression failure", deflate);
                }
                int i3 = this.b.next_out_index - i2;
                if (i3 > 0) {
                    byteBuf2.writerIndex(byteBuf2.writerIndex() + i3);
                }
            } finally {
                Deflater deflater = this.b;
                deflater.next_in = null;
                deflater.next_out = null;
            }
        }
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) {
        ChannelFuture a = a(channelHandlerContext, channelHandlerContext.newPromise());
        a.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.compression.JZlibEncoder.2
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                channelHandlerContext.close(channelPromise);
            }
        });
        if (!a.isDone()) {
            channelHandlerContext.executor().schedule((Runnable) new OneTimeTask() { // from class: io.netty.handler.codec.compression.JZlibEncoder.3
                @Override // java.lang.Runnable
                public void run() {
                    channelHandlerContext.close(channelPromise);
                }
            }, 10L, TimeUnit.SECONDS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ChannelFuture a(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        ByteBuf byteBuf;
        if (this.c) {
            channelPromise.setSuccess();
            return channelPromise;
        }
        this.c = true;
        try {
            this.b.next_in = EmptyArrays.EMPTY_BYTES;
            this.b.next_in_index = 0;
            this.b.avail_in = 0;
            byte[] bArr = new byte[32];
            this.b.next_out = bArr;
            this.b.next_out_index = 0;
            this.b.avail_out = bArr.length;
            int deflate = this.b.deflate(4);
            if (deflate == 0 || deflate == 1) {
                if (this.b.next_out_index != 0) {
                    byteBuf = Unpooled.wrappedBuffer(bArr, 0, this.b.next_out_index);
                } else {
                    byteBuf = Unpooled.EMPTY_BUFFER;
                }
                this.b.deflateEnd();
                Deflater deflater = this.b;
                deflater.next_in = null;
                deflater.next_out = null;
                return channelHandlerContext.writeAndFlush(byteBuf, channelPromise);
            }
            channelPromise.setFailure((Throwable) o.b(this.b, "compression failure", deflate));
            return channelPromise;
        } finally {
            this.b.deflateEnd();
            Deflater deflater2 = this.b;
            deflater2.next_in = null;
            deflater2.next_out = null;
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.d = channelHandlerContext;
    }
}
