package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.netty.util.collection.CharObjectMap;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DefaultHttp2FrameWriter implements Http2FrameSizePolicy, Http2FrameWriter, Http2FrameWriter.Configuration {
    private static final ByteBuf a = Unpooled.unreleasableBuffer(Unpooled.directBuffer(255).writeZero(255)).asReadOnly();
    private final Http2HeadersEncoder b;
    private int c;

    @Override // io.netty.handler.codec.http2.Http2FrameWriter, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public Http2FrameWriter.Configuration configuration() {
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter.Configuration
    public Http2FrameSizePolicy frameSizePolicy() {
        return this;
    }

    public DefaultHttp2FrameWriter() {
        this(new DefaultHttp2HeadersEncoder());
    }

    public DefaultHttp2FrameWriter(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        this(new DefaultHttp2HeadersEncoder(4096, sensitivityDetector));
    }

    public DefaultHttp2FrameWriter(Http2HeadersEncoder http2HeadersEncoder) {
        this.b = http2HeadersEncoder;
        this.c = 16384;
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter.Configuration
    public Http2HeaderTable headerTable() {
        return this.b.configuration().headerTable();
    }

    @Override // io.netty.handler.codec.http2.Http2FrameSizePolicy
    public void maxFrameSize(int i) throws Http2Exception {
        if (Http2CodecUtil.isMaxFrameSizeValid(i)) {
            this.c = i;
            return;
        }
        throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Invalid MAX_FRAME_SIZE specified in sent settings: %d", Integer.valueOf(i));
    }

    @Override // io.netty.handler.codec.http2.Http2FrameSizePolicy
    public int maxFrameSize() {
        return this.c;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a2  */
    @Override // io.netty.handler.codec.http2.Http2DataWriter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public io.netty.channel.ChannelFuture writeData(io.netty.channel.ChannelHandlerContext r16, int r17, io.netty.buffer.ByteBuf r18, int r19, boolean r20, io.netty.channel.ChannelPromise r21) {
        /*
            r15 = this;
            r1 = r15
            r0 = r16
            r2 = r17
            io.netty.handler.codec.http2.Http2CodecUtil$a r3 = new io.netty.handler.codec.http2.Http2CodecUtil$a
            io.netty.channel.Channel r4 = r16.channel()
            io.netty.util.concurrent.EventExecutor r5 = r16.executor()
            r6 = r21
            r3.<init>(r6, r4, r5)
            io.netty.handler.codec.http2.DefaultHttp2FrameWriter$a r4 = new io.netty.handler.codec.http2.DefaultHttp2FrameWriter$a
            r4.<init>(r0, r2)
            r5 = 1
            java.lang.String r6 = "Stream ID"
            a(r2, r6)     // Catch: Throwable -> 0x0096
            a(r19)     // Catch: Throwable -> 0x0096
            int r2 = r18.readableBytes()     // Catch: Throwable -> 0x0096
            r6 = r19
            r7 = r5
            r8 = r7
        L_0x002a:
            int r9 = r1.c     // Catch: Throwable -> 0x0091
            int r9 = java.lang.Math.min(r2, r9)     // Catch: Throwable -> 0x0091
            int r10 = r1.c     // Catch: Throwable -> 0x0091
            int r10 = r10 - r5
            int r10 = r10 - r9
            r11 = 0
            int r10 = java.lang.Math.max(r11, r10)     // Catch: Throwable -> 0x0091
            int r10 = java.lang.Math.min(r6, r10)     // Catch: Throwable -> 0x0091
            int r6 = r6 - r10
            int r2 = r2 - r9
            if (r2 != 0) goto L_0x0045
            if (r6 != 0) goto L_0x0045
            r12 = r5
            goto L_0x0046
        L_0x0045:
            r12 = r11
        L_0x0046:
            if (r12 == 0) goto L_0x004c
            if (r20 == 0) goto L_0x004c
            r13 = r5
            goto L_0x004d
        L_0x004c:
            r13 = r11
        L_0x004d:
            io.netty.buffer.ByteBuf r7 = r4.a(r9, r10, r13)     // Catch: Throwable -> 0x0091
            r13 = r12 ^ 1
            if (r12 == 0) goto L_0x0056
            goto L_0x005a
        L_0x0056:
            io.netty.buffer.ByteBuf r7 = r7.retain()     // Catch: Throwable -> 0x008d
        L_0x005a:
            io.netty.channel.ChannelPromise r14 = r3.a()     // Catch: Throwable -> 0x008d
            r0.write(r7, r14)     // Catch: Throwable -> 0x008d
            r14 = r18
            io.netty.buffer.ByteBuf r7 = r14.readSlice(r9)     // Catch: Throwable -> 0x008b
            r8 = r12 ^ 1
            if (r12 == 0) goto L_0x006c
            goto L_0x0070
        L_0x006c:
            io.netty.buffer.ByteBuf r7 = r7.retain()     // Catch: Throwable -> 0x008b
        L_0x0070:
            io.netty.channel.ChannelPromise r9 = r3.a()     // Catch: Throwable -> 0x008b
            r0.write(r7, r9)     // Catch: Throwable -> 0x008b
            if (r10 <= 0) goto L_0x0086
            io.netty.buffer.ByteBuf r7 = io.netty.handler.codec.http2.DefaultHttp2FrameWriter.a     // Catch: Throwable -> 0x008b
            io.netty.buffer.ByteBuf r7 = r7.slice(r11, r10)     // Catch: Throwable -> 0x008b
            io.netty.channel.ChannelPromise r9 = r3.a()     // Catch: Throwable -> 0x008b
            r0.write(r7, r9)     // Catch: Throwable -> 0x008b
        L_0x0086:
            if (r12 == 0) goto L_0x0089
            goto L_0x00a8
        L_0x0089:
            r7 = r13
            goto L_0x002a
        L_0x008b:
            r0 = move-exception
            goto L_0x009b
        L_0x008d:
            r0 = move-exception
            r14 = r18
            goto L_0x009b
        L_0x0091:
            r0 = move-exception
            r14 = r18
            r13 = r7
            goto L_0x009b
        L_0x0096:
            r0 = move-exception
            r14 = r18
            r8 = r5
            r13 = r8
        L_0x009b:
            if (r13 == 0) goto L_0x00a0
            r4.a()
        L_0x00a0:
            if (r8 == 0) goto L_0x00a5
            r18.release()
        L_0x00a5:
            r3.setFailure(r0)
        L_0x00a8:
            io.netty.channel.ChannelPromise r0 = r3.b()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http2.DefaultHttp2FrameWriter.writeData(io.netty.channel.ChannelHandlerContext, int, io.netty.buffer.ByteBuf, int, boolean, io.netty.channel.ChannelPromise):io.netty.channel.ChannelFuture");
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z, ChannelPromise channelPromise) {
        return a(channelHandlerContext, i, http2Headers, i2, z, false, 0, (short) 0, false, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2, ChannelPromise channelPromise) {
        return a(channelHandlerContext, i, http2Headers, i3, z2, true, i2, s, z, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writePriority(ChannelHandlerContext channelHandlerContext, int i, int i2, short s, boolean z, ChannelPromise channelPromise) {
        try {
            a(i, "Stream ID");
            a(i2, "Stream Dependency");
            a(s);
            ByteBuf buffer = channelHandlerContext.alloc().buffer(14);
            Http2CodecUtil.a(buffer, 5, (byte) 2, new Http2Flags(), i);
            Http2CodecUtil.writeUnsignedInt(z ? i2 | 2147483648L : i2, buffer);
            buffer.writeByte(s - 1);
            return channelHandlerContext.write(buffer, channelPromise);
        } catch (Throwable th) {
            return channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeRstStream(ChannelHandlerContext channelHandlerContext, int i, long j, ChannelPromise channelPromise) {
        try {
            a(i, "Stream ID");
            a(j);
            ByteBuf buffer = channelHandlerContext.alloc().buffer(13);
            Http2CodecUtil.a(buffer, 4, (byte) 3, new Http2Flags(), i);
            Http2CodecUtil.writeUnsignedInt(j, buffer);
            return channelHandlerContext.write(buffer, channelPromise);
        } catch (Throwable th) {
            return channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeSettings(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings, ChannelPromise channelPromise) {
        try {
            ObjectUtil.checkNotNull(http2Settings, "settings");
            ByteBuf buffer = channelHandlerContext.alloc().buffer((http2Settings.size() * 6) + 9);
            Http2CodecUtil.a(buffer, http2Settings.size() * 6, (byte) 4, new Http2Flags(), 0);
            for (CharObjectMap.PrimitiveEntry<Long> primitiveEntry : http2Settings.entries()) {
                Http2CodecUtil.writeUnsignedShort(primitiveEntry.key(), buffer);
                Http2CodecUtil.writeUnsignedInt(primitiveEntry.value().longValue(), buffer);
            }
            return channelHandlerContext.write(buffer, channelPromise);
        } catch (Throwable th) {
            return channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeSettingsAck(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        try {
            ByteBuf buffer = channelHandlerContext.alloc().buffer(9);
            Http2CodecUtil.a(buffer, 0, (byte) 4, new Http2Flags().ack(true), 0);
            return channelHandlerContext.write(buffer, channelPromise);
        } catch (Throwable th) {
            return channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writePing(ChannelHandlerContext channelHandlerContext, boolean z, ByteBuf byteBuf, ChannelPromise channelPromise) {
        Throwable th;
        Http2CodecUtil.a aVar = new Http2CodecUtil.a(channelPromise, channelHandlerContext.channel(), channelHandlerContext.executor());
        boolean z2 = true;
        try {
            a(byteBuf);
            Http2Flags ack = z ? new Http2Flags().ack(true) : new Http2Flags();
            ByteBuf buffer = channelHandlerContext.alloc().buffer(9);
            Http2CodecUtil.a(buffer, byteBuf.readableBytes(), (byte) 6, ack, 0);
            channelHandlerContext.write(buffer, aVar.a());
            try {
                channelHandlerContext.write(byteBuf, aVar.a());
            } catch (Throwable th2) {
                th = th2;
                z2 = false;
                if (z2) {
                    byteBuf.release();
                }
                aVar.setFailure(th);
                return aVar.b();
            }
        } catch (Throwable th3) {
            th = th3;
        }
        return aVar.b();
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writePushPromise(ChannelHandlerContext channelHandlerContext, int i, int i2, Http2Headers http2Headers, int i3, ChannelPromise channelPromise) {
        Http2CodecUtil.a aVar = new Http2CodecUtil.a(channelPromise, channelHandlerContext.channel(), channelHandlerContext.executor());
        ByteBuf byteBuf = null;
        try {
            a(i, "Stream ID");
            a(i2, "Promised Stream ID");
            a(i3);
            byteBuf = channelHandlerContext.alloc().buffer();
            this.b.encodeHeaders(http2Headers, byteBuf);
            boolean z = true;
            Http2Flags paddingPresent = new Http2Flags().paddingPresent(i3 > 0);
            int paddingPresenceFieldLength = i3 + 4 + paddingPresent.getPaddingPresenceFieldLength();
            ByteBuf readRetainedSlice = byteBuf.readRetainedSlice(Math.min(byteBuf.readableBytes(), this.c - paddingPresenceFieldLength));
            if (byteBuf.isReadable()) {
                z = false;
            }
            paddingPresent.endOfHeaders(z);
            int readableBytes = readRetainedSlice.readableBytes() + paddingPresenceFieldLength;
            ByteBuf buffer = channelHandlerContext.alloc().buffer(14);
            Http2CodecUtil.a(buffer, readableBytes, (byte) 5, paddingPresent, i);
            b(buffer, i3);
            buffer.writeInt(i2);
            channelHandlerContext.write(buffer, aVar.a());
            channelHandlerContext.write(readRetainedSlice, aVar.a());
            if (i3 > 0) {
                channelHandlerContext.write(a.slice(0, i3), aVar.a());
            }
            if (!paddingPresent.endOfHeaders()) {
                a(channelHandlerContext, i, byteBuf, i3, aVar);
            }
            if (byteBuf != null) {
                byteBuf.release();
            }
            return aVar.b();
        } catch (Throwable th) {
            if (byteBuf != null) {
                byteBuf.release();
            }
            throw th;
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeGoAway(ChannelHandlerContext channelHandlerContext, int i, long j, ByteBuf byteBuf, ChannelPromise channelPromise) {
        Throwable th;
        Http2CodecUtil.a aVar = new Http2CodecUtil.a(channelPromise, channelHandlerContext.channel(), channelHandlerContext.executor());
        boolean z = true;
        try {
            b(i, "Last Stream ID");
            a(j);
            ByteBuf buffer = channelHandlerContext.alloc().buffer(17);
            Http2CodecUtil.a(buffer, byteBuf.readableBytes() + 8, (byte) 7, new Http2Flags(), 0);
            buffer.writeInt(i);
            Http2CodecUtil.writeUnsignedInt(j, buffer);
            channelHandlerContext.write(buffer, aVar.a());
            try {
                channelHandlerContext.write(byteBuf, aVar.a());
            } catch (Throwable th2) {
                th = th2;
                z = false;
                if (z) {
                    byteBuf.release();
                }
                aVar.setFailure(th);
                return aVar.b();
            }
        } catch (Throwable th3) {
            th = th3;
        }
        return aVar.b();
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeWindowUpdate(ChannelHandlerContext channelHandlerContext, int i, int i2, ChannelPromise channelPromise) {
        try {
            b(i, "Stream ID");
            b(i2);
            ByteBuf buffer = channelHandlerContext.alloc().buffer(13);
            Http2CodecUtil.a(buffer, 4, (byte) 8, new Http2Flags(), i);
            buffer.writeInt(i2);
            return channelHandlerContext.write(buffer, channelPromise);
        } catch (Throwable th) {
            return channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeFrame(ChannelHandlerContext channelHandlerContext, byte b, int i, Http2Flags http2Flags, ByteBuf byteBuf, ChannelPromise channelPromise) {
        Http2CodecUtil.a aVar = new Http2CodecUtil.a(channelPromise, channelHandlerContext.channel(), channelHandlerContext.executor());
        boolean z = true;
        try {
            b(i, "Stream ID");
            ByteBuf buffer = channelHandlerContext.alloc().buffer(9);
            Http2CodecUtil.a(buffer, byteBuf.readableBytes(), b, http2Flags, i);
            channelHandlerContext.write(buffer, aVar.a());
            z = false;
            channelHandlerContext.write(byteBuf, aVar.a());
        } catch (Throwable th) {
            if (z) {
                byteBuf.release();
            }
            aVar.setFailure(th);
        }
        return aVar.b();
    }

    private ChannelFuture a(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z, boolean z2, int i3, short s, boolean z3, ChannelPromise channelPromise) {
        Http2CodecUtil.a aVar = new Http2CodecUtil.a(channelPromise, channelHandlerContext.channel(), channelHandlerContext.executor());
        ByteBuf byteBuf = null;
        try {
            a(i, "Stream ID");
            if (z2) {
                b(i3, "Stream Dependency");
                a(i2);
                a(s);
            }
            byteBuf = channelHandlerContext.alloc().buffer();
            this.b.encodeHeaders(http2Headers, byteBuf);
            Http2Flags paddingPresent = new Http2Flags().endOfStream(z).priorityPresent(z2).paddingPresent(i2 > 0);
            int numPriorityBytes = paddingPresent.getNumPriorityBytes() + i2 + paddingPresent.getPaddingPresenceFieldLength();
            ByteBuf readRetainedSlice = byteBuf.readRetainedSlice(Math.min(byteBuf.readableBytes(), this.c - numPriorityBytes));
            paddingPresent.endOfHeaders(!byteBuf.isReadable());
            int readableBytes = readRetainedSlice.readableBytes() + numPriorityBytes;
            ByteBuf buffer = channelHandlerContext.alloc().buffer(15);
            Http2CodecUtil.a(buffer, readableBytes, (byte) 1, paddingPresent, i);
            b(buffer, i2);
            if (z2) {
                Http2CodecUtil.writeUnsignedInt(z3 ? i3 | 2147483648L : i3, buffer);
                buffer.writeByte(s - 1);
            }
            channelHandlerContext.write(buffer, aVar.a());
            channelHandlerContext.write(readRetainedSlice, aVar.a());
            if (i2 > 0) {
                channelHandlerContext.write(a.slice(0, i2), aVar.a());
            }
            if (!paddingPresent.endOfHeaders()) {
                a(channelHandlerContext, i, byteBuf, i2, aVar);
            }
            if (byteBuf != null) {
                byteBuf.release();
            }
            return aVar.b();
        } catch (Throwable th) {
            if (byteBuf != null) {
                byteBuf.release();
            }
            throw th;
        }
    }

    private ChannelFuture a(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, Http2CodecUtil.a aVar) {
        Http2Flags paddingPresent = new Http2Flags().paddingPresent(i2 > 0);
        int paddingPresenceFieldLength = paddingPresent.getPaddingPresenceFieldLength() + i2;
        int i3 = this.c - paddingPresenceFieldLength;
        if (i3 <= 0) {
            return aVar.setFailure((Throwable) new IllegalArgumentException("Padding [" + i2 + "] is too large for max frame size [" + this.c + "]"));
        } else if (!byteBuf.isReadable()) {
            return aVar;
        } else {
            ByteBuf buffer = channelHandlerContext.alloc().buffer(10);
            Http2CodecUtil.a(buffer, Math.min(byteBuf.readableBytes(), i3) + paddingPresenceFieldLength, (byte) 9, paddingPresent, i);
            b(buffer, i2);
            do {
                int min = Math.min(byteBuf.readableBytes(), i3);
                Object readRetainedSlice = byteBuf.readRetainedSlice(min);
                int i4 = min + paddingPresenceFieldLength;
                if (byteBuf.isReadable()) {
                    channelHandlerContext.write(buffer.retain(), aVar.a());
                } else {
                    paddingPresent = paddingPresent.endOfHeaders(true);
                    buffer.release();
                    ByteBuf buffer2 = channelHandlerContext.alloc().buffer(10);
                    Http2CodecUtil.a(buffer2, i4, (byte) 9, paddingPresent, i);
                    b(buffer2, i2);
                    channelHandlerContext.write(buffer2, aVar.a());
                    buffer = buffer2;
                }
                channelHandlerContext.write(readRetainedSlice, aVar.a());
                if (i2 > 0) {
                    channelHandlerContext.write(a.slice(0, i2), aVar.a());
                }
            } while (byteBuf.isReadable());
            return aVar;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(ByteBuf byteBuf, int i) {
        if (i > 0) {
            byteBuf.writeByte(i);
        }
    }

    private static void a(int i, String str) {
        if (i <= 0) {
            throw new IllegalArgumentException(str + " must be > 0");
        }
    }

    private static void b(int i, String str) {
        if (i < 0) {
            throw new IllegalArgumentException(str + " must be >= 0");
        }
    }

    private static void a(int i) {
        if (i < 0 || i > 255) {
            throw new IllegalArgumentException("Invalid padding value: " + i);
        }
    }

    private static void a(short s) {
        if (s < 1 || s > 256) {
            throw new IllegalArgumentException("Invalid weight: " + ((int) s));
        }
    }

    private static void a(long j) {
        if (j < 0 || j > 4294967295L) {
            throw new IllegalArgumentException("Invalid errorCode: " + j);
        }
    }

    private static void b(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("WindowSizeIncrement must be >= 0");
        }
    }

    private static void a(ByteBuf byteBuf) {
        if (byteBuf == null || byteBuf.readableBytes() != 8) {
            throw new IllegalArgumentException("Opaque data must be 8 bytes");
        }
    }

    /* loaded from: classes4.dex */
    private static final class a {
        private final int a;
        private final ByteBuf b;
        private final Http2Flags c = new Http2Flags();
        private int d;
        private int e;
        private ByteBuf f;

        a(ChannelHandlerContext channelHandlerContext, int i) {
            this.b = channelHandlerContext.alloc().buffer(30);
            this.a = i;
        }

        ByteBuf a(int i, int i2, boolean z) {
            if (!(i == this.d && i2 == this.e && z == this.c.endOfStream() && this.f != null)) {
                this.d = i;
                this.e = i2;
                this.c.paddingPresent(i2 > 0);
                this.c.endOfStream(z);
                this.f = this.b.readSlice(10).writerIndex(0);
                Http2CodecUtil.a(this.f, i + i2 + this.c.getPaddingPresenceFieldLength(), (byte) 0, this.c, this.a);
                DefaultHttp2FrameWriter.b(this.f, i2);
            }
            return this.f.slice();
        }

        void a() {
            this.b.release();
        }
    }
}
