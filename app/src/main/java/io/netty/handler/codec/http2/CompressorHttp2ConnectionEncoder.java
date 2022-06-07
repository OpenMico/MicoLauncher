package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.util.AsciiString;
import io.netty.util.concurrent.PromiseCombiner;

/* loaded from: classes4.dex */
public class CompressorHttp2ConnectionEncoder extends DecoratingHttp2ConnectionEncoder {
    public static final int DEFAULT_COMPRESSION_LEVEL = 6;
    public static final int DEFAULT_MEM_LEVEL = 8;
    public static final int DEFAULT_WINDOW_BITS = 15;
    private final int a;
    private final int b;
    private final int c;
    private final Http2Connection.PropertyKey d;

    protected CharSequence getTargetContentEncoding(CharSequence charSequence) throws Http2Exception {
        return charSequence;
    }

    public CompressorHttp2ConnectionEncoder(Http2ConnectionEncoder http2ConnectionEncoder) {
        this(http2ConnectionEncoder, 6, 15, 8);
    }

    public CompressorHttp2ConnectionEncoder(Http2ConnectionEncoder http2ConnectionEncoder, int i, int i2, int i3) {
        super(http2ConnectionEncoder);
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("compressionLevel: " + i + " (expected: 0-9)");
        } else if (i2 < 9 || i2 > 15) {
            throw new IllegalArgumentException("windowBits: " + i2 + " (expected: 9-15)");
        } else if (i3 < 1 || i3 > 9) {
            throw new IllegalArgumentException("memLevel: " + i3 + " (expected: 1-9)");
        } else {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = connection().newKey();
            connection().addListener(new Http2ConnectionAdapter() { // from class: io.netty.handler.codec.http2.CompressorHttp2ConnectionEncoder.1
                @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
                public void onStreamRemoved(Http2Stream http2Stream) {
                    EmbeddedChannel embeddedChannel = (EmbeddedChannel) http2Stream.getProperty(CompressorHttp2ConnectionEncoder.this.d);
                    if (embeddedChannel != null) {
                        CompressorHttp2ConnectionEncoder.this.a(http2Stream, embeddedChannel);
                    }
                }
            });
        }
    }

    @Override // io.netty.handler.codec.http2.DecoratingHttp2FrameWriter, io.netty.handler.codec.http2.Http2DataWriter
    public ChannelFuture writeData(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, boolean z, ChannelPromise channelPromise) {
        ByteBuf byteBuf2;
        boolean z2;
        Http2Stream stream = connection().stream(i);
        EmbeddedChannel embeddedChannel = stream == null ? null : (EmbeddedChannel) stream.getProperty(this.d);
        if (embeddedChannel == null) {
            return super.writeData(channelHandlerContext, i, byteBuf, i2, z, channelPromise);
        }
        try {
            embeddedChannel.writeOutbound(byteBuf);
            ByteBuf a = a(embeddedChannel);
            if (a != null) {
                PromiseCombiner promiseCombiner = new PromiseCombiner();
                int i3 = i2;
                ByteBuf byteBuf3 = a;
                while (true) {
                    ByteBuf a2 = a(embeddedChannel);
                    boolean z3 = a2 == null && z;
                    if (!z3 || !embeddedChannel.finish()) {
                        byteBuf2 = a2;
                        z2 = z3;
                    } else {
                        ByteBuf a3 = a(embeddedChannel);
                        z2 = a3 == null;
                        byteBuf2 = a3;
                    }
                    ChannelPromise newPromise = channelHandlerContext.newPromise();
                    promiseCombiner.add(newPromise);
                    super.writeData(channelHandlerContext, i, byteBuf3, i3, z2, newPromise);
                    if (byteBuf2 == null) {
                        break;
                    }
                    i3 = 0;
                    byteBuf3 = byteBuf2;
                }
                promiseCombiner.finish(channelPromise);
                if (z) {
                    a(stream, embeddedChannel);
                }
                return channelPromise;
            } else if (z) {
                if (embeddedChannel.finish()) {
                    a = a(embeddedChannel);
                }
                return super.writeData(channelHandlerContext, i, a == null ? Unpooled.EMPTY_BUFFER : a, i2, true, channelPromise);
            } else {
                channelPromise.setSuccess();
                if (z) {
                    a(stream, embeddedChannel);
                }
                return channelPromise;
            }
        } finally {
            if (z) {
                a(stream, embeddedChannel);
            }
        }
    }

    @Override // io.netty.handler.codec.http2.DecoratingHttp2FrameWriter, io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z, ChannelPromise channelPromise) {
        try {
            EmbeddedChannel a = a(channelHandlerContext, http2Headers, z);
            ChannelFuture writeHeaders = super.writeHeaders(channelHandlerContext, i, http2Headers, i2, z, channelPromise);
            a(a, i);
            return writeHeaders;
        } catch (Throwable th) {
            channelPromise.tryFailure(th);
            return channelPromise;
        }
    }

    @Override // io.netty.handler.codec.http2.DecoratingHttp2FrameWriter, io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2, ChannelPromise channelPromise) {
        try {
            EmbeddedChannel a = a(channelHandlerContext, http2Headers, z2);
            ChannelFuture writeHeaders = super.writeHeaders(channelHandlerContext, i, http2Headers, i2, s, z, i3, z2, channelPromise);
            a(a, i);
            return writeHeaders;
        } catch (Throwable th) {
            channelPromise.tryFailure(th);
            return channelPromise;
        }
    }

    protected EmbeddedChannel newContentCompressor(ChannelHandlerContext channelHandlerContext, CharSequence charSequence) throws Http2Exception {
        if (HttpHeaderValues.GZIP.contentEqualsIgnoreCase(charSequence) || HttpHeaderValues.X_GZIP.contentEqualsIgnoreCase(charSequence)) {
            return a(channelHandlerContext, ZlibWrapper.GZIP);
        }
        if (HttpHeaderValues.DEFLATE.contentEqualsIgnoreCase(charSequence) || HttpHeaderValues.X_DEFLATE.contentEqualsIgnoreCase(charSequence)) {
            return a(channelHandlerContext, ZlibWrapper.ZLIB);
        }
        return null;
    }

    private EmbeddedChannel a(ChannelHandlerContext channelHandlerContext, ZlibWrapper zlibWrapper) {
        return new EmbeddedChannel(channelHandlerContext.channel().id(), channelHandlerContext.channel().metadata().hasDisconnect(), channelHandlerContext.channel().config(), ZlibCodecFactory.newZlibEncoder(zlibWrapper, this.a, this.b, this.c));
    }

    private EmbeddedChannel a(ChannelHandlerContext channelHandlerContext, Http2Headers http2Headers, boolean z) throws Http2Exception {
        if (z) {
            return null;
        }
        CharSequence charSequence = http2Headers.get(HttpHeaderNames.CONTENT_ENCODING);
        if (charSequence == null) {
            charSequence = HttpHeaderValues.IDENTITY;
        }
        EmbeddedChannel newContentCompressor = newContentCompressor(channelHandlerContext, charSequence);
        if (newContentCompressor != null) {
            CharSequence targetContentEncoding = getTargetContentEncoding(charSequence);
            if (HttpHeaderValues.IDENTITY.contentEqualsIgnoreCase(targetContentEncoding)) {
                http2Headers.remove(HttpHeaderNames.CONTENT_ENCODING);
            } else {
                http2Headers.set((Http2Headers) HttpHeaderNames.CONTENT_ENCODING, (AsciiString) targetContentEncoding);
            }
            http2Headers.remove(HttpHeaderNames.CONTENT_LENGTH);
        }
        return newContentCompressor;
    }

    private void a(EmbeddedChannel embeddedChannel, int i) {
        Http2Stream stream;
        if (embeddedChannel != null && (stream = connection().stream(i)) != null) {
            stream.setProperty(this.d, embeddedChannel);
        }
    }

    void a(Http2Stream http2Stream, EmbeddedChannel embeddedChannel) {
        if (embeddedChannel.finish()) {
            while (true) {
                ByteBuf byteBuf = (ByteBuf) embeddedChannel.readOutbound();
                if (byteBuf == null) {
                    break;
                }
                byteBuf.release();
            }
        }
        http2Stream.removeProperty(this.d);
    }

    private static ByteBuf a(EmbeddedChannel embeddedChannel) {
        while (true) {
            ByteBuf byteBuf = (ByteBuf) embeddedChannel.readOutbound();
            if (byteBuf == null) {
                return null;
            }
            if (byteBuf.isReadable()) {
                return byteBuf;
            }
            byteBuf.release();
        }
    }
}
