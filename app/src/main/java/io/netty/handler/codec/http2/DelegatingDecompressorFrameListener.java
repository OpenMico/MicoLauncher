package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.util.AsciiString;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DelegatingDecompressorFrameListener extends Http2FrameListenerDecorator {
    final Http2Connection.PropertyKey a;
    private final Http2Connection b;
    private final boolean c;
    private boolean d;

    public DelegatingDecompressorFrameListener(Http2Connection http2Connection, Http2FrameListener http2FrameListener) {
        this(http2Connection, http2FrameListener, true);
    }

    public DelegatingDecompressorFrameListener(Http2Connection http2Connection, Http2FrameListener http2FrameListener, boolean z) {
        super(http2FrameListener);
        this.b = http2Connection;
        this.c = z;
        this.a = http2Connection.newKey();
        http2Connection.addListener(new Http2ConnectionAdapter() { // from class: io.netty.handler.codec.http2.DelegatingDecompressorFrameListener.1
            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamRemoved(Http2Stream http2Stream) {
                b a2 = DelegatingDecompressorFrameListener.this.a(http2Stream);
                if (a2 != null) {
                    DelegatingDecompressorFrameListener.this.a(http2Stream, a2);
                }
            }
        });
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListenerDecorator, io.netty.handler.codec.http2.Http2FrameListener
    public int onDataRead(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, boolean z) throws Http2Exception {
        ByteBuf byteBuf2;
        Throwable th;
        int i3;
        ByteBuf byteBuf3;
        boolean z2;
        Http2Stream stream = this.b.stream(i);
        b a2 = a(stream);
        if (a2 == null) {
            return this.listener.onDataRead(channelHandlerContext, i, byteBuf, i2, z);
        }
        EmbeddedChannel a3 = a2.a();
        int readableBytes = byteBuf.readableBytes() + i2;
        a2.b(readableBytes);
        try {
            a3.writeInbound(byteBuf.retain());
            ByteBuf a4 = a(a3);
            if (a4 == null && z && a3.finish()) {
                a4 = a(a3);
            }
            if (a4 == null) {
                if (z) {
                    this.listener.onDataRead(channelHandlerContext, i, Unpooled.EMPTY_BUFFER, i2, true);
                }
                a2.c(readableBytes);
                i3 = readableBytes;
            } else {
                try {
                    a2.c(i2);
                    int i4 = i2;
                    byteBuf2 = a4;
                    i3 = 0;
                    while (true) {
                        try {
                            ByteBuf a5 = a(a3);
                            boolean z3 = a5 == null && z;
                            if (!z3 || !a3.finish()) {
                                byteBuf3 = a5;
                                z2 = z3;
                            } else {
                                ByteBuf a6 = a(a3);
                                z2 = a6 == null;
                                byteBuf3 = a6;
                            }
                            a2.c(byteBuf2.readableBytes());
                            i3 += this.listener.onDataRead(channelHandlerContext, i, byteBuf2, i4, z2);
                            if (byteBuf3 == null) {
                                break;
                            }
                            byteBuf2.release();
                            i4 = 0;
                            byteBuf2 = byteBuf3;
                        } catch (Throwable th2) {
                            th = th2;
                            byteBuf2.release();
                            throw th;
                        }
                    }
                    byteBuf2.release();
                } catch (Throwable th3) {
                    th = th3;
                    byteBuf2 = a4;
                }
            }
            a2.a(i3);
            return i3;
        } catch (Http2Exception e) {
            a2.a(readableBytes);
            throw e;
        } catch (Throwable th4) {
            a2.a(readableBytes);
            throw Http2Exception.streamError(stream.id(), Http2Error.INTERNAL_ERROR, th4, "Decompressor error detected while delegating data read on streamId %d", Integer.valueOf(stream.id()));
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListenerDecorator, io.netty.handler.codec.http2.Http2FrameListener
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z) throws Http2Exception {
        a(channelHandlerContext, i, http2Headers, z);
        this.listener.onHeadersRead(channelHandlerContext, i, http2Headers, i2, z);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListenerDecorator, io.netty.handler.codec.http2.Http2FrameListener
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2) throws Http2Exception {
        a(channelHandlerContext, i, http2Headers, z2);
        this.listener.onHeadersRead(channelHandlerContext, i, http2Headers, i2, s, z, i3, z2);
    }

    protected EmbeddedChannel newContentDecompressor(ChannelHandlerContext channelHandlerContext, CharSequence charSequence) throws Http2Exception {
        if (HttpHeaderValues.GZIP.contentEqualsIgnoreCase(charSequence) || HttpHeaderValues.X_GZIP.contentEqualsIgnoreCase(charSequence)) {
            return new EmbeddedChannel(channelHandlerContext.channel().id(), channelHandlerContext.channel().metadata().hasDisconnect(), channelHandlerContext.channel().config(), ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
        }
        if (!HttpHeaderValues.DEFLATE.contentEqualsIgnoreCase(charSequence) && !HttpHeaderValues.X_DEFLATE.contentEqualsIgnoreCase(charSequence)) {
            return null;
        }
        return new EmbeddedChannel(channelHandlerContext.channel().id(), channelHandlerContext.channel().metadata().hasDisconnect(), channelHandlerContext.channel().config(), ZlibCodecFactory.newZlibDecoder(this.c ? ZlibWrapper.ZLIB : ZlibWrapper.ZLIB_OR_NONE));
    }

    protected CharSequence getTargetContentEncoding(CharSequence charSequence) throws Http2Exception {
        return HttpHeaderValues.IDENTITY;
    }

    private void a(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, boolean z) throws Http2Exception {
        Http2Stream stream = this.b.stream(i);
        if (stream != null) {
            b a2 = a(stream);
            if (a2 == null && !z) {
                CharSequence charSequence = http2Headers.get(HttpHeaderNames.CONTENT_ENCODING);
                if (charSequence == null) {
                    charSequence = HttpHeaderValues.IDENTITY;
                }
                EmbeddedChannel newContentDecompressor = newContentDecompressor(channelHandlerContext, charSequence);
                if (newContentDecompressor != null) {
                    a2 = new b(newContentDecompressor);
                    stream.setProperty(this.a, a2);
                    CharSequence targetContentEncoding = getTargetContentEncoding(charSequence);
                    if (HttpHeaderValues.IDENTITY.contentEqualsIgnoreCase(targetContentEncoding)) {
                        http2Headers.remove(HttpHeaderNames.CONTENT_ENCODING);
                    } else {
                        http2Headers.set((Http2Headers) HttpHeaderNames.CONTENT_ENCODING, (AsciiString) targetContentEncoding);
                    }
                }
            }
            if (a2 != null) {
                http2Headers.remove(HttpHeaderNames.CONTENT_LENGTH);
                if (!this.d) {
                    this.d = true;
                    this.b.local().flowController(new a(this.b.local().flowController()));
                }
            }
        }
    }

    b a(Http2Stream http2Stream) {
        if (http2Stream == null) {
            return null;
        }
        return (b) http2Stream.getProperty(this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Http2Stream http2Stream, b bVar) {
        EmbeddedChannel a2 = bVar.a();
        if (a2.finish()) {
            while (true) {
                ByteBuf byteBuf = (ByteBuf) a2.readInbound();
                if (byteBuf == null) {
                    break;
                }
                byteBuf.release();
            }
        }
        b bVar2 = (b) http2Stream.removeProperty(this.a);
    }

    private static ByteBuf a(EmbeddedChannel embeddedChannel) {
        while (true) {
            ByteBuf byteBuf = (ByteBuf) embeddedChannel.readInbound();
            if (byteBuf == null) {
                return null;
            }
            if (byteBuf.isReadable()) {
                return byteBuf;
            }
            byteBuf.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a implements Http2LocalFlowController {
        private final Http2LocalFlowController b;

        a(Http2LocalFlowController http2LocalFlowController) {
            this.b = (Http2LocalFlowController) ObjectUtil.checkNotNull(http2LocalFlowController, "flowController");
        }

        @Override // io.netty.handler.codec.http2.Http2LocalFlowController
        public Http2LocalFlowController frameWriter(Http2FrameWriter http2FrameWriter) {
            return this.b.frameWriter(http2FrameWriter);
        }

        @Override // io.netty.handler.codec.http2.Http2FlowController
        public void channelHandlerContext(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
            this.b.channelHandlerContext(channelHandlerContext);
        }

        @Override // io.netty.handler.codec.http2.Http2FlowController
        public void initialWindowSize(int i) throws Http2Exception {
            this.b.initialWindowSize(i);
        }

        @Override // io.netty.handler.codec.http2.Http2FlowController
        public int initialWindowSize() {
            return this.b.initialWindowSize();
        }

        @Override // io.netty.handler.codec.http2.Http2FlowController
        public int windowSize(Http2Stream http2Stream) {
            return this.b.windowSize(http2Stream);
        }

        @Override // io.netty.handler.codec.http2.Http2FlowController
        public void incrementWindowSize(Http2Stream http2Stream, int i) throws Http2Exception {
            this.b.incrementWindowSize(http2Stream, i);
        }

        @Override // io.netty.handler.codec.http2.Http2LocalFlowController
        public void receiveFlowControlledFrame(Http2Stream http2Stream, ByteBuf byteBuf, int i, boolean z) throws Http2Exception {
            this.b.receiveFlowControlledFrame(http2Stream, byteBuf, i, z);
        }

        @Override // io.netty.handler.codec.http2.Http2LocalFlowController
        public boolean consumeBytes(Http2Stream http2Stream, int i) throws Http2Exception {
            b bVar;
            b a = DelegatingDecompressorFrameListener.this.a(http2Stream);
            b bVar2 = null;
            if (a != null) {
                try {
                    bVar = new b(a);
                } catch (Http2Exception e) {
                    e = e;
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    i = a.d(i);
                    bVar2 = bVar;
                } catch (Http2Exception e2) {
                    e = e2;
                    bVar2 = bVar;
                    if (bVar2 != null) {
                        http2Stream.setProperty(DelegatingDecompressorFrameListener.this.a, bVar2);
                    }
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    bVar2 = bVar;
                    if (bVar2 != null) {
                        http2Stream.setProperty(DelegatingDecompressorFrameListener.this.a, bVar2);
                    }
                    throw new Http2Exception(Http2Error.INTERNAL_ERROR, "Error while returning bytes to flow control window", th);
                }
            }
            return this.b.consumeBytes(http2Stream, i);
        }

        @Override // io.netty.handler.codec.http2.Http2LocalFlowController
        public int unconsumedBytes(Http2Stream http2Stream) {
            return this.b.unconsumedBytes(http2Stream);
        }

        @Override // io.netty.handler.codec.http2.Http2LocalFlowController
        public int initialWindowSize(Http2Stream http2Stream) {
            return this.b.initialWindowSize(http2Stream);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b {
        private final EmbeddedChannel a;
        private int b;
        private int c;
        private int d;

        b(b bVar) {
            this(bVar.a);
            this.b = bVar.b;
            this.c = bVar.c;
            this.d = bVar.d;
        }

        b(EmbeddedChannel embeddedChannel) {
            this.a = embeddedChannel;
        }

        EmbeddedChannel a() {
            return this.a;
        }

        void a(int i) {
            int i2 = this.b;
            if (i2 + i >= 0) {
                this.b = i2 + i;
                return;
            }
            throw new IllegalArgumentException("processed bytes cannot be negative");
        }

        void b(int i) {
            int i2 = this.c;
            if (i2 + i >= 0) {
                this.c = i2 + i;
                return;
            }
            throw new IllegalArgumentException("compressed bytes cannot be negative");
        }

        void c(int i) {
            int i2 = this.d;
            if (i2 + i >= 0) {
                this.d = i2 + i;
                return;
            }
            throw new IllegalArgumentException("decompressed bytes cannot be negative");
        }

        int d(int i) {
            a(-i);
            double d = i / this.d;
            int i2 = this.c;
            int min = Math.min(i2, (int) Math.ceil(i2 * d));
            int i3 = this.d;
            c(-Math.min(i3, (int) Math.ceil(i3 * d)));
            b(-min);
            return min;
        }
    }
}
