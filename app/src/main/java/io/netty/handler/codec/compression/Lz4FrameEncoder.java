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
import java.util.zip.Checksum;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Exception;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.xxhash.XXHashFactory;

/* loaded from: classes4.dex */
public class Lz4FrameEncoder extends MessageToByteEncoder<ByteBuf> {
    private LZ4Compressor a;
    private Checksum b;
    private final int c;
    private byte[] d;
    private int e;
    private final int f;
    private volatile boolean g;
    private volatile ChannelHandlerContext h;

    public Lz4FrameEncoder() {
        this(false);
    }

    public Lz4FrameEncoder(boolean z) {
        this(LZ4Factory.fastestInstance(), z, 65536, XXHashFactory.fastestInstance().newStreamingHash32(-1756908916).asChecksum());
    }

    public Lz4FrameEncoder(LZ4Factory lZ4Factory, boolean z, int i, Checksum checksum) {
        super(false);
        if (lZ4Factory == null) {
            throw new NullPointerException("factory");
        } else if (checksum != null) {
            this.a = z ? lZ4Factory.highCompressor() : lZ4Factory.fastCompressor();
            this.b = checksum;
            this.c = a(i);
            this.d = new byte[i];
            this.e = 0;
            this.f = this.a.maxCompressedLength(i) + 21;
            this.g = false;
        } else {
            throw new NullPointerException("checksum");
        }
    }

    private static int a(int i) {
        if (i >= 64 && i <= 33554432) {
            return Math.max(0, (32 - Integer.numberOfLeadingZeros(i - 1)) - 10);
        }
        throw new IllegalArgumentException(String.format("blockSize: %d (expected: %d-%d)", Integer.valueOf(i), 64, 33554432));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        if (this.g) {
            byteBuf2.writeBytes(byteBuf);
            return;
        }
        int readableBytes = byteBuf.readableBytes();
        byte[] bArr = this.d;
        int length = bArr.length;
        while (true) {
            int i = this.e;
            if (i + readableBytes >= length) {
                int i2 = length - i;
                byteBuf.getBytes(byteBuf.readerIndex(), bArr, this.e, i2);
                this.e = length;
                a(byteBuf2);
                byteBuf.skipBytes(i2);
                readableBytes -= i2;
            } else {
                byteBuf.readBytes(bArr, i, readableBytes);
                this.e += readableBytes;
                return;
            }
        }
    }

    private void a(ByteBuf byteBuf) {
        int i;
        int i2;
        int i3 = this.e;
        if (i3 != 0) {
            this.b.reset();
            this.b.update(this.d, 0, i3);
            int value = (int) this.b.getValue();
            byteBuf.ensureWritable(this.f);
            int writerIndex = byteBuf.writerIndex();
            byte[] array = byteBuf.array();
            int arrayOffset = byteBuf.arrayOffset() + writerIndex;
            try {
                int i4 = arrayOffset + 21;
                int compress = this.a.compress(this.d, 0, i3, array, i4);
                if (compress >= i3) {
                    i2 = 16;
                    System.arraycopy(this.d, 0, array, i4, i3);
                    i = i3;
                } else {
                    i2 = 32;
                    i = compress;
                }
                byteBuf.setLong(writerIndex, 5501767354678207339L);
                array[arrayOffset + 8] = (byte) (i2 | this.c);
                a(i, array, arrayOffset + 9);
                a(i3, array, arrayOffset + 13);
                a(value, array, arrayOffset + 17);
                byteBuf.writerIndex(writerIndex + 21 + i);
                this.e = 0;
            } catch (LZ4Exception e) {
                throw new CompressionException((Throwable) e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ChannelFuture a(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        if (this.g) {
            channelPromise.setSuccess();
            return channelPromise;
        }
        this.g = true;
        ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer(this.a.maxCompressedLength(this.e) + 21);
        a(heapBuffer);
        int writerIndex = heapBuffer.writerIndex();
        byte[] array = heapBuffer.array();
        int arrayOffset = heapBuffer.arrayOffset() + writerIndex;
        heapBuffer.setLong(writerIndex, 5501767354678207339L);
        array[arrayOffset + 8] = (byte) (this.c | 16);
        a(0, array, arrayOffset + 9);
        a(0, array, arrayOffset + 13);
        a(0, array, arrayOffset + 17);
        heapBuffer.writerIndex(writerIndex + 21);
        this.a = null;
        this.b = null;
        this.d = null;
        return channelHandlerContext.writeAndFlush(heapBuffer, channelPromise);
    }

    private static void a(int i, byte[] bArr, int i2) {
        int i3 = i2 + 1;
        bArr[i2] = (byte) i;
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i >>> 8);
        bArr[i4] = (byte) (i >>> 16);
        bArr[i4 + 1] = (byte) (i >>> 24);
    }

    public boolean isClosed() {
        return this.g;
    }

    public ChannelFuture close() {
        return close(a().newPromise());
    }

    public ChannelFuture close(final ChannelPromise channelPromise) {
        ChannelHandlerContext a = a();
        EventExecutor executor = a.executor();
        if (executor.inEventLoop()) {
            return a(a, channelPromise);
        }
        executor.execute(new Runnable() { // from class: io.netty.handler.codec.compression.Lz4FrameEncoder.1
            @Override // java.lang.Runnable
            public void run() {
                Lz4FrameEncoder lz4FrameEncoder = Lz4FrameEncoder.this;
                lz4FrameEncoder.a(lz4FrameEncoder.a(), channelPromise).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelPromiseNotifier(channelPromise));
            }
        });
        return channelPromise;
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        ChannelFuture a = a(channelHandlerContext, channelHandlerContext.newPromise());
        a.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.compression.Lz4FrameEncoder.2
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                channelHandlerContext.close(channelPromise);
            }
        });
        if (!a.isDone()) {
            channelHandlerContext.executor().schedule(new Runnable() { // from class: io.netty.handler.codec.compression.Lz4FrameEncoder.3
                @Override // java.lang.Runnable
                public void run() {
                    channelHandlerContext.close(channelPromise);
                }
            }, 10L, TimeUnit.SECONDS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ChannelHandlerContext a() {
        ChannelHandlerContext channelHandlerContext = this.h;
        if (channelHandlerContext != null) {
            return channelHandlerContext;
        }
        throw new IllegalStateException("not added to a pipeline");
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.h = channelHandlerContext;
    }
}
