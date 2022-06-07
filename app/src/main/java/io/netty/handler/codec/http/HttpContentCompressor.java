package io.netty.handler.codec.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpContentEncoder;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class HttpContentCompressor extends HttpContentEncoder {
    private final int b;
    private final int c;
    private final int d;
    private ChannelHandlerContext e;

    public HttpContentCompressor() {
        this(6);
    }

    public HttpContentCompressor(int i) {
        this(i, 15, 8);
    }

    public HttpContentCompressor(int i, int i2, int i3) {
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("compressionLevel: " + i + " (expected: 0-9)");
        } else if (i2 < 9 || i2 > 15) {
            throw new IllegalArgumentException("windowBits: " + i2 + " (expected: 9-15)");
        } else if (i3 < 1 || i3 > 9) {
            throw new IllegalArgumentException("memLevel: " + i3 + " (expected: 1-9)");
        } else {
            this.b = i;
            this.c = i2;
            this.d = i3;
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.e = channelHandlerContext;
    }

    @Override // io.netty.handler.codec.http.HttpContentEncoder
    protected HttpContentEncoder.Result beginEncode(HttpResponse httpResponse, String str) throws Exception {
        ZlibWrapper determineWrapper;
        String str2;
        String str3 = httpResponse.headers().get(HttpHeaderNames.CONTENT_ENCODING);
        if ((str3 != null && !HttpHeaderValues.IDENTITY.contentEqualsIgnoreCase(str3)) || (determineWrapper = determineWrapper(str)) == null) {
            return null;
        }
        switch (determineWrapper) {
            case GZIP:
                str2 = "gzip";
                break;
            case ZLIB:
                str2 = "deflate";
                break;
            default:
                throw new Error();
        }
        return new HttpContentEncoder.Result(str2, new EmbeddedChannel(this.e.channel().id(), this.e.channel().metadata().hasDisconnect(), this.e.channel().config(), ZlibCodecFactory.newZlibEncoder(determineWrapper, this.b, this.c, this.d)));
    }

    protected ZlibWrapper determineWrapper(String str) {
        String[] split = StringUtil.split(str, StringUtil.COMMA);
        int length = split.length;
        int i = 0;
        float f = -1.0f;
        float f2 = -1.0f;
        float f3 = -1.0f;
        while (true) {
            float f4 = 0.0f;
            if (i >= length) {
                break;
            }
            String str2 = split[i];
            f4 = 1.0f;
            int indexOf = str2.indexOf(61);
            if (indexOf != -1) {
                try {
                    f4 = Float.valueOf(str2.substring(indexOf + 1)).floatValue();
                } catch (NumberFormatException unused) {
                }
            }
            if (str2.contains("*")) {
                f3 = f4;
            } else if (str2.contains("gzip") && f4 > f) {
                f = f4;
            } else if (str2.contains("deflate") && f4 > f2) {
                f2 = f4;
            }
            i++;
        }
        if (f > 0.0f || f2 > 0.0f) {
            if (f >= f2) {
                return ZlibWrapper.GZIP;
            }
            return ZlibWrapper.ZLIB;
        } else if (f3 <= 0.0f) {
            return null;
        } else {
            if (f == -1.0f) {
                return ZlibWrapper.GZIP;
            }
            if (f2 == -1.0f) {
                return ZlibWrapper.ZLIB;
            }
            return null;
        }
    }
}
