package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.base64.Base64Dialect;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientUpgradeHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.collection.CharObjectMap;
import io.netty.util.internal.ObjectUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class Http2ClientUpgradeCodec implements HttpClientUpgradeHandler.UpgradeCodec {
    private static final List<CharSequence> a = Collections.singletonList(Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER);
    private final String b;
    private final Http2ConnectionHandler c;

    public Http2ClientUpgradeCodec(Http2ConnectionHandler http2ConnectionHandler) {
        this(null, http2ConnectionHandler);
    }

    public Http2ClientUpgradeCodec(String str, Http2ConnectionHandler http2ConnectionHandler) {
        this.b = str;
        this.c = (Http2ConnectionHandler) ObjectUtil.checkNotNull(http2ConnectionHandler, "connectionHandler");
    }

    @Override // io.netty.handler.codec.http.HttpClientUpgradeHandler.UpgradeCodec
    public CharSequence protocol() {
        return Http2CodecUtil.HTTP_UPGRADE_PROTOCOL_NAME;
    }

    @Override // io.netty.handler.codec.http.HttpClientUpgradeHandler.UpgradeCodec
    public Collection<CharSequence> setUpgradeHeaders(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) {
        httpRequest.headers().set(Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER, a(channelHandlerContext));
        return a;
    }

    @Override // io.netty.handler.codec.http.HttpClientUpgradeHandler.UpgradeCodec
    public void upgradeTo(ChannelHandlerContext channelHandlerContext, FullHttpResponse fullHttpResponse) throws Exception {
        this.c.onHttpClientUpgrade();
        channelHandlerContext.pipeline().addAfter(channelHandlerContext.name(), this.b, this.c);
    }

    private CharSequence a(ChannelHandlerContext channelHandlerContext) {
        ByteBuf byteBuf;
        Throwable th;
        ByteBuf byteBuf2 = null;
        try {
            Http2Settings localSettings = this.c.decoder().localSettings();
            byteBuf = channelHandlerContext.alloc().buffer(localSettings.size() * 6);
            try {
                for (CharObjectMap.PrimitiveEntry<Long> primitiveEntry : localSettings.entries()) {
                    Http2CodecUtil.writeUnsignedShort(primitiveEntry.key(), byteBuf);
                    Http2CodecUtil.writeUnsignedInt(primitiveEntry.value().longValue(), byteBuf);
                }
                byteBuf2 = Base64.encode(byteBuf, Base64Dialect.URL_SAFE);
                String byteBuf3 = byteBuf2.toString(CharsetUtil.UTF_8);
                ReferenceCountUtil.release(byteBuf);
                ReferenceCountUtil.release(byteBuf2);
                return byteBuf3;
            } catch (Throwable th2) {
                th = th2;
                ReferenceCountUtil.release(byteBuf);
                ReferenceCountUtil.release(byteBuf2);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            byteBuf = null;
        }
    }
}
