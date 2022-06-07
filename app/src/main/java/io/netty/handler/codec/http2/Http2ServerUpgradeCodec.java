package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.base64.Base64Dialect;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpServerUpgradeHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class Http2ServerUpgradeCodec implements HttpServerUpgradeHandler.UpgradeCodec {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(Http2ServerUpgradeCodec.class);
    private static final List<CharSequence> b = Collections.singletonList(Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER);
    private final String c;
    private final Http2ConnectionHandler d;
    private final ChannelHandler e;
    private final Http2FrameReader f;

    public Http2ServerUpgradeCodec(Http2ConnectionHandler http2ConnectionHandler) {
        this((String) null, http2ConnectionHandler);
    }

    public Http2ServerUpgradeCodec(Http2MultiplexCodec http2MultiplexCodec) {
        this((String) null, http2MultiplexCodec);
    }

    public Http2ServerUpgradeCodec(String str, Http2ConnectionHandler http2ConnectionHandler) {
        this(str, http2ConnectionHandler, http2ConnectionHandler);
    }

    public Http2ServerUpgradeCodec(String str, Http2MultiplexCodec http2MultiplexCodec) {
        this(str, http2MultiplexCodec.a(), http2MultiplexCodec);
    }

    Http2ServerUpgradeCodec(String str, Http2ConnectionHandler http2ConnectionHandler, ChannelHandler channelHandler) {
        this.c = str;
        this.d = (Http2ConnectionHandler) ObjectUtil.checkNotNull(http2ConnectionHandler, "connectionHandler");
        this.e = (ChannelHandler) ObjectUtil.checkNotNull(channelHandler, "upgradeToHandler");
        this.f = new DefaultHttp2FrameReader();
    }

    @Override // io.netty.handler.codec.http.HttpServerUpgradeHandler.UpgradeCodec
    public Collection<CharSequence> requiredUpgradeHeaders() {
        return b;
    }

    @Override // io.netty.handler.codec.http.HttpServerUpgradeHandler.UpgradeCodec
    public boolean prepareUpgradeResponse(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest, HttpHeaders httpHeaders) {
        try {
            List<String> all = fullHttpRequest.headers().getAll(Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER);
            if (all.isEmpty() || all.size() > 1) {
                throw new IllegalArgumentException("There must be 1 and only 1 " + ((Object) Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER) + " header.");
            }
            this.d.onHttpServerUpgrade(a(channelHandlerContext, all.get(0)));
            return true;
        } catch (Throwable th) {
            a.info("Error during upgrade to HTTP/2", th);
            return false;
        }
    }

    @Override // io.netty.handler.codec.http.HttpServerUpgradeHandler.UpgradeCodec
    public void upgradeTo(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) {
        channelHandlerContext.pipeline().addAfter(channelHandlerContext.name(), this.c, this.e);
    }

    private Http2Settings a(ChannelHandlerContext channelHandlerContext, CharSequence charSequence) throws Http2Exception {
        ByteBuf encodeString = ByteBufUtil.encodeString(channelHandlerContext.alloc(), CharBuffer.wrap(charSequence), CharsetUtil.UTF_8);
        try {
            return a(channelHandlerContext, b(channelHandlerContext, Base64.decode(encodeString, Base64Dialect.URL_SAFE)));
        } finally {
            encodeString.release();
        }
    }

    private Http2Settings a(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Http2Exception {
        try {
            final Http2Settings http2Settings = new Http2Settings();
            this.f.readFrame(channelHandlerContext, byteBuf, new Http2FrameAdapter() { // from class: io.netty.handler.codec.http2.Http2ServerUpgradeCodec.1
                @Override // io.netty.handler.codec.http2.Http2FrameAdapter, io.netty.handler.codec.http2.Http2FrameListener
                public void onSettingsRead(ChannelHandlerContext channelHandlerContext2, Http2Settings http2Settings2) {
                    http2Settings.copyFrom(http2Settings2);
                }
            });
            return http2Settings;
        } finally {
            byteBuf.release();
        }
    }

    private static ByteBuf b(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        ByteBuf buffer = channelHandlerContext.alloc().buffer(byteBuf.readableBytes() + 9);
        Http2CodecUtil.writeFrameHeader(buffer, byteBuf.readableBytes(), (byte) 4, new Http2Flags(), 0);
        buffer.writeBytes(byteBuf);
        byteBuf.release();
        return buffer;
    }
}
