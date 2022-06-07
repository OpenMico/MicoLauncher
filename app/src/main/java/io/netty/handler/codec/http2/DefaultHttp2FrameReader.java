package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2FrameReader;
import io.netty.util.internal.PlatformDependent;

/* loaded from: classes4.dex */
public class DefaultHttp2FrameReader implements Http2FrameReader, Http2FrameReader.Configuration, Http2FrameSizePolicy {
    private final Http2HeadersDecoder a;
    private boolean b;
    private boolean c;
    private byte d;
    private int e;
    private Http2Flags f;
    private int g;
    private a h;
    private int i;

    @Override // io.netty.handler.codec.http2.Http2FrameReader
    public Http2FrameReader.Configuration configuration() {
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2FrameReader.Configuration
    public Http2FrameSizePolicy frameSizePolicy() {
        return this;
    }

    public DefaultHttp2FrameReader() {
        this(true);
    }

    public DefaultHttp2FrameReader(boolean z) {
        this(new DefaultHttp2HeadersDecoder(z));
    }

    public DefaultHttp2FrameReader(Http2HeadersDecoder http2HeadersDecoder) {
        this.b = true;
        this.a = http2HeadersDecoder;
        this.i = 16384;
    }

    @Override // io.netty.handler.codec.http2.Http2FrameReader.Configuration
    public Http2HeaderTable headerTable() {
        return this.a.configuration().headerTable();
    }

    @Override // io.netty.handler.codec.http2.Http2FrameSizePolicy
    public void maxFrameSize(int i) throws Http2Exception {
        if (Http2CodecUtil.isMaxFrameSizeValid(i)) {
            this.i = i;
            return;
        }
        throw Http2Exception.streamError(this.e, Http2Error.FRAME_SIZE_ERROR, "Invalid MAX_FRAME_SIZE specified in sent settings: %d", Integer.valueOf(i));
    }

    @Override // io.netty.handler.codec.http2.Http2FrameSizePolicy
    public int maxFrameSize() {
        return this.i;
    }

    @Override // io.netty.handler.codec.http2.Http2FrameReader, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        a aVar = this.h;
        if (aVar != null) {
            aVar.c();
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameReader
    public void readFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        if (this.c) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        do {
            try {
                if (this.b) {
                    a(byteBuf);
                    if (this.b) {
                        return;
                    }
                }
                a(channelHandlerContext, byteBuf, http2FrameListener);
                if (!this.b) {
                    return;
                }
            } catch (Http2Exception e) {
                this.c = !Http2Exception.isStreamError(e);
                throw e;
            } catch (RuntimeException e2) {
                this.c = true;
                throw e2;
            } catch (Throwable th) {
                this.c = true;
                PlatformDependent.throwException(th);
                return;
            }
        } while (byteBuf.isReadable());
    }

    private void a(ByteBuf byteBuf) throws Http2Exception {
        if (byteBuf.readableBytes() >= 9) {
            this.g = byteBuf.readUnsignedMedium();
            if (this.g <= this.i) {
                this.d = byteBuf.readByte();
                this.f = new Http2Flags(byteBuf.readUnsignedByte());
                this.e = Http2CodecUtil.readUnsignedInt(byteBuf);
                this.b = false;
                switch (this.d) {
                    case 0:
                        a();
                        return;
                    case 1:
                        b();
                        return;
                    case 2:
                        c();
                        return;
                    case 3:
                        d();
                        return;
                    case 4:
                        e();
                        return;
                    case 5:
                        f();
                        return;
                    case 6:
                        g();
                        return;
                    case 7:
                        h();
                        return;
                    case 8:
                        i();
                        return;
                    case 9:
                        j();
                        return;
                    default:
                        return;
                }
            } else {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Frame length: %d exceeds maximum: %d", Integer.valueOf(this.g), Integer.valueOf(this.i));
            }
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        int readableBytes = byteBuf.readableBytes();
        int i = this.g;
        if (readableBytes >= i) {
            ByteBuf readSlice = byteBuf.readSlice(i);
            this.b = true;
            switch (this.d) {
                case 0:
                    b(channelHandlerContext, readSlice, http2FrameListener);
                    return;
                case 1:
                    c(channelHandlerContext, readSlice, http2FrameListener);
                    return;
                case 2:
                    d(channelHandlerContext, readSlice, http2FrameListener);
                    return;
                case 3:
                    e(channelHandlerContext, readSlice, http2FrameListener);
                    return;
                case 4:
                    f(channelHandlerContext, readSlice, http2FrameListener);
                    return;
                case 5:
                    g(channelHandlerContext, readSlice, http2FrameListener);
                    return;
                case 6:
                    h(channelHandlerContext, readSlice, http2FrameListener);
                    return;
                case 7:
                    i(channelHandlerContext, readSlice, http2FrameListener);
                    return;
                case 8:
                    j(channelHandlerContext, readSlice, http2FrameListener);
                    return;
                case 9:
                    a(readSlice, http2FrameListener);
                    return;
                default:
                    k(channelHandlerContext, readSlice, http2FrameListener);
                    return;
            }
        }
    }

    private void a() throws Http2Exception {
        k();
        a(this.g);
        if (this.g < this.f.getPaddingPresenceFieldLength()) {
            throw Http2Exception.streamError(this.e, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", Integer.valueOf(this.g));
        }
    }

    private void b() throws Http2Exception {
        k();
        a(this.g);
        if (this.g < this.f.getPaddingPresenceFieldLength() + this.f.getNumPriorityBytes()) {
            int i = this.e;
            Http2Error http2Error = Http2Error.FRAME_SIZE_ERROR;
            throw Http2Exception.streamError(i, http2Error, "Frame length too small." + this.g, new Object[0]);
        }
    }

    private void c() throws Http2Exception {
        k();
        if (this.g != 5) {
            throw Http2Exception.streamError(this.e, Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", Integer.valueOf(this.g));
        }
    }

    private void d() throws Http2Exception {
        k();
        if (this.g != 4) {
            throw Http2Exception.streamError(this.e, Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", Integer.valueOf(this.g));
        }
    }

    private void e() throws Http2Exception {
        k();
        a(this.g);
        if (this.e != 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.", new Object[0]);
        } else if (this.f.ack() && this.g > 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Ack settings frame must have an empty payload.", new Object[0]);
        } else if (this.g % 6 > 0) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d invalid.", Integer.valueOf(this.g));
        }
    }

    private void f() throws Http2Exception {
        k();
        a(this.g);
        if (this.g < this.f.getPaddingPresenceFieldLength() + 4) {
            throw Http2Exception.streamError(this.e, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", Integer.valueOf(this.g));
        }
    }

    private void g() throws Http2Exception {
        k();
        if (this.e != 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.", new Object[0]);
        } else if (this.g != 8) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d incorrect size for ping.", Integer.valueOf(this.g));
        }
    }

    private void h() throws Http2Exception {
        k();
        a(this.g);
        if (this.e != 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.", new Object[0]);
        } else if (this.g < 8) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", Integer.valueOf(this.g));
        }
    }

    private void i() throws Http2Exception {
        k();
        a(this.e, "Stream ID");
        if (this.g != 4) {
            throw Http2Exception.streamError(this.e, Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", Integer.valueOf(this.g));
        }
    }

    private void j() throws Http2Exception {
        a(this.g);
        a aVar = this.h;
        if (aVar == null) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Received %s frame but not currently processing headers.", Byte.valueOf(this.d));
        } else if (this.e != aVar.a()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Continuation stream ID does not match pending headers. Expected %d, but received %d.", Integer.valueOf(this.h.a()), Integer.valueOf(this.e));
        } else if (this.g < this.f.getPaddingPresenceFieldLength()) {
            throw Http2Exception.streamError(this.e, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small for padding.", Integer.valueOf(this.g));
        }
    }

    private void b(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        short b = b(byteBuf);
        int readableBytes = byteBuf.readableBytes() - b;
        if (readableBytes >= 0) {
            http2FrameListener.onDataRead(channelHandlerContext, this.e, byteBuf.readSlice(readableBytes), b, this.f.endOfStream());
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        throw Http2Exception.streamError(this.e, Http2Error.FRAME_SIZE_ERROR, "Frame payload too small for padding.", new Object[0]);
    }

    private void c(final ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        final int i = this.e;
        final Http2Flags http2Flags = this.f;
        final short b = b(byteBuf);
        if (this.f.priorityPresent()) {
            long readUnsignedInt = byteBuf.readUnsignedInt();
            final boolean z = (2147483648L & readUnsignedInt) != 0;
            final int i2 = (int) (readUnsignedInt & 2147483647L);
            final short readUnsignedByte = (short) (byteBuf.readUnsignedByte() + 1);
            ByteBuf readSlice = byteBuf.readSlice(byteBuf.readableBytes() - b);
            this.h = new a() { // from class: io.netty.handler.codec.http2.DefaultHttp2FrameReader.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // io.netty.handler.codec.http2.DefaultHttp2FrameReader.a
                public int a() {
                    return i;
                }

                @Override // io.netty.handler.codec.http2.DefaultHttp2FrameReader.a
                public void a(boolean z2, ByteBuf byteBuf2, Http2FrameListener http2FrameListener2) throws Http2Exception {
                    HeadersBlockBuilder b2 = b();
                    b2.a(byteBuf2, channelHandlerContext.alloc(), z2);
                    if (z2) {
                        http2FrameListener2.onHeadersRead(channelHandlerContext, i, b2.a(), i2, readUnsignedByte, z, b, http2Flags.endOfStream());
                    }
                }
            };
            this.h.a(this.f.endOfHeaders(), readSlice, http2FrameListener);
            return;
        }
        this.h = new a() { // from class: io.netty.handler.codec.http2.DefaultHttp2FrameReader.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // io.netty.handler.codec.http2.DefaultHttp2FrameReader.a
            public int a() {
                return i;
            }

            @Override // io.netty.handler.codec.http2.DefaultHttp2FrameReader.a
            public void a(boolean z2, ByteBuf byteBuf2, Http2FrameListener http2FrameListener2) throws Http2Exception {
                HeadersBlockBuilder b2 = b();
                b2.a(byteBuf2, channelHandlerContext.alloc(), z2);
                if (z2) {
                    http2FrameListener2.onHeadersRead(channelHandlerContext, i, b2.a(), b, http2Flags.endOfStream());
                }
            }
        };
        this.h.a(this.f.endOfHeaders(), byteBuf.readSlice(byteBuf.readableBytes() - b), http2FrameListener);
    }

    private void d(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        long readUnsignedInt = byteBuf.readUnsignedInt();
        http2FrameListener.onPriorityRead(channelHandlerContext, this.e, (int) (readUnsignedInt & 2147483647L), (short) (byteBuf.readUnsignedByte() + 1), (2147483648L & readUnsignedInt) != 0);
    }

    private void e(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        http2FrameListener.onRstStreamRead(channelHandlerContext, this.e, byteBuf.readUnsignedInt());
    }

    private void f(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        if (this.f.ack()) {
            http2FrameListener.onSettingsAckRead(channelHandlerContext);
            return;
        }
        int i = this.g / 6;
        Http2Settings http2Settings = new Http2Settings();
        for (int i2 = 0; i2 < i; i2++) {
            char readUnsignedShort = (char) byteBuf.readUnsignedShort();
            try {
                http2Settings.put(readUnsignedShort, Long.valueOf(byteBuf.readUnsignedInt()));
            } catch (IllegalArgumentException e) {
                switch (readUnsignedShort) {
                    case 4:
                        throw Http2Exception.connectionError(Http2Error.FLOW_CONTROL_ERROR, e, e.getMessage(), new Object[0]);
                    case 5:
                        throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, e, e.getMessage(), new Object[0]);
                    default:
                        throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, e, e.getMessage(), new Object[0]);
                }
            }
        }
        http2FrameListener.onSettingsRead(channelHandlerContext, http2Settings);
    }

    private void g(final ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        final int i = this.e;
        final short b = b(byteBuf);
        final int readUnsignedInt = Http2CodecUtil.readUnsignedInt(byteBuf);
        this.h = new a() { // from class: io.netty.handler.codec.http2.DefaultHttp2FrameReader.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // io.netty.handler.codec.http2.DefaultHttp2FrameReader.a
            public int a() {
                return i;
            }

            @Override // io.netty.handler.codec.http2.DefaultHttp2FrameReader.a
            public void a(boolean z, ByteBuf byteBuf2, Http2FrameListener http2FrameListener2) throws Http2Exception {
                b().a(byteBuf2, channelHandlerContext.alloc(), z);
                if (z) {
                    http2FrameListener2.onPushPromiseRead(channelHandlerContext, i, readUnsignedInt, b().a(), b);
                }
            }
        };
        this.h.a(this.f.endOfHeaders(), byteBuf.readSlice(byteBuf.readableBytes() - b), http2FrameListener);
    }

    private void h(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        ByteBuf readSlice = byteBuf.readSlice(byteBuf.readableBytes());
        if (this.f.ack()) {
            http2FrameListener.onPingAckRead(channelHandlerContext, readSlice);
        } else {
            http2FrameListener.onPingRead(channelHandlerContext, readSlice);
        }
    }

    private static void i(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        http2FrameListener.onGoAwayRead(channelHandlerContext, Http2CodecUtil.readUnsignedInt(byteBuf), byteBuf.readUnsignedInt(), byteBuf.readSlice(byteBuf.readableBytes()));
    }

    private void j(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        int readUnsignedInt = Http2CodecUtil.readUnsignedInt(byteBuf);
        if (readUnsignedInt != 0) {
            http2FrameListener.onWindowUpdateRead(channelHandlerContext, this.e, readUnsignedInt);
            return;
        }
        throw Http2Exception.streamError(this.e, Http2Error.PROTOCOL_ERROR, "Received WINDOW_UPDATE with delta 0 for stream: %d", Integer.valueOf(this.e));
    }

    private void a(ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        this.h.a(this.f.endOfHeaders(), byteBuf.readSlice(byteBuf.readableBytes()), http2FrameListener);
    }

    private void k(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        http2FrameListener.onUnknownFrame(channelHandlerContext, this.d, this.e, this.f, byteBuf.readSlice(byteBuf.readableBytes()));
    }

    private short b(ByteBuf byteBuf) {
        if (!this.f.paddingPresent()) {
            return (short) 0;
        }
        return byteBuf.readUnsignedByte();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public abstract class a {
        private final HeadersBlockBuilder a;

        abstract int a();

        abstract void a(boolean z, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception;

        private a() {
            this.a = new HeadersBlockBuilder();
        }

        final HeadersBlockBuilder b() {
            return this.a;
        }

        final void c() {
            this.a.b();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes4.dex */
    public class HeadersBlockBuilder {
        private ByteBuf b;

        protected HeadersBlockBuilder() {
        }

        private void c() throws Http2Exception {
            b();
            throw Http2Exception.connectionError(Http2Error.ENHANCE_YOUR_CALM, "Header size exceeded max allowed size (%d)", Integer.valueOf(DefaultHttp2FrameReader.this.a.configuration().maxHeaderSize()));
        }

        final void a(ByteBuf byteBuf, ByteBufAllocator byteBufAllocator, boolean z) throws Http2Exception {
            if (this.b == null) {
                if (byteBuf.readableBytes() > DefaultHttp2FrameReader.this.a.configuration().maxHeaderSize()) {
                    c();
                }
                if (z) {
                    this.b = byteBuf.retain();
                    return;
                }
                this.b = byteBufAllocator.buffer(byteBuf.readableBytes());
                this.b.writeBytes(byteBuf);
                return;
            }
            if (DefaultHttp2FrameReader.this.a.configuration().maxHeaderSize() - byteBuf.readableBytes() < this.b.readableBytes()) {
                c();
            }
            if (this.b.isWritable(byteBuf.readableBytes())) {
                this.b.writeBytes(byteBuf);
                return;
            }
            ByteBuf buffer = byteBufAllocator.buffer(this.b.readableBytes() + byteBuf.readableBytes());
            buffer.writeBytes(this.b);
            buffer.writeBytes(byteBuf);
            this.b.release();
            this.b = buffer;
        }

        Http2Headers a() throws Http2Exception {
            try {
                return DefaultHttp2FrameReader.this.a.decodeHeaders(this.b);
            } finally {
                b();
            }
        }

        void b() {
            ByteBuf byteBuf = this.b;
            if (byteBuf != null) {
                byteBuf.release();
                this.b = null;
            }
            DefaultHttp2FrameReader.this.h = null;
        }
    }

    private void k() throws Http2Exception {
        if (this.h != null) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Received frame of type %s while processing headers.", Byte.valueOf(this.d));
        }
    }

    private void a(int i) throws Http2Exception {
        if (i > this.i) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Total payload length %d exceeds max frame length.", Integer.valueOf(i));
        }
    }

    private static void a(int i, String str) throws Http2Exception {
        if (i < 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "%s must be >= 0", str);
        }
    }
}
