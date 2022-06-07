package io.netty.handler.codec.http;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;

/* loaded from: classes4.dex */
public class HttpContentDecompressor extends HttpContentDecoder {
    private final boolean b;

    public HttpContentDecompressor() {
        this(false);
    }

    public HttpContentDecompressor(boolean z) {
        this.b = z;
    }

    @Override // io.netty.handler.codec.http.HttpContentDecoder
    protected EmbeddedChannel newContentDecoder(String str) throws Exception {
        if (HttpHeaderValues.GZIP.contentEqualsIgnoreCase(str) || HttpHeaderValues.X_GZIP.contentEqualsIgnoreCase(str)) {
            return new EmbeddedChannel(this.ctx.channel().id(), this.ctx.channel().metadata().hasDisconnect(), this.ctx.channel().config(), ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
        }
        if (!HttpHeaderValues.DEFLATE.contentEqualsIgnoreCase(str) && !HttpHeaderValues.X_DEFLATE.contentEqualsIgnoreCase(str)) {
            return null;
        }
        return new EmbeddedChannel(this.ctx.channel().id(), this.ctx.channel().metadata().hasDisconnect(), this.ctx.channel().config(), ZlibCodecFactory.newZlibDecoder(this.b ? ZlibWrapper.ZLIB : ZlibWrapper.ZLIB_OR_NONE));
    }
}
