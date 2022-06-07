package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import okio.Utf8;

/* loaded from: classes4.dex */
public final class HttpUtil {
    @Deprecated
    static final EmptyHttpHeaders a = new EmptyHttpHeaders();
    private static final AsciiString b = AsciiString.of(((Object) HttpHeaderValues.CHARSET) + "=");
    private static final AsciiString c = AsciiString.of(";");

    private static byte a(char c2) {
        return c2 > 255 ? Utf8.REPLACEMENT_BYTE : (byte) c2;
    }

    private HttpUtil() {
    }

    public static boolean isOriginForm(URI uri) {
        return uri.getScheme() == null && uri.getSchemeSpecificPart() == null && uri.getHost() == null && uri.getAuthority() == null;
    }

    public static boolean isAsteriskForm(URI uri) {
        return "*".equals(uri.getPath()) && uri.getScheme() == null && uri.getSchemeSpecificPart() == null && uri.getHost() == null && uri.getAuthority() == null && uri.getQuery() == null && uri.getFragment() == null;
    }

    public static boolean isKeepAlive(HttpMessage httpMessage) {
        String str = httpMessage.headers().get(HttpHeaderNames.CONNECTION);
        if (str != null && HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(str)) {
            return false;
        }
        if (httpMessage.protocolVersion().isKeepAliveDefault()) {
            return !HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(str);
        }
        return HttpHeaderValues.KEEP_ALIVE.contentEqualsIgnoreCase(str);
    }

    public static void setKeepAlive(HttpMessage httpMessage, boolean z) {
        setKeepAlive(httpMessage.headers(), httpMessage.protocolVersion(), z);
    }

    public static void setKeepAlive(HttpHeaders httpHeaders, HttpVersion httpVersion, boolean z) {
        if (httpVersion.isKeepAliveDefault()) {
            if (z) {
                httpHeaders.remove(HttpHeaderNames.CONNECTION);
            } else {
                httpHeaders.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            }
        } else if (z) {
            httpHeaders.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        } else {
            httpHeaders.remove(HttpHeaderNames.CONNECTION);
        }
    }

    public static long getContentLength(HttpMessage httpMessage) {
        String str = httpMessage.headers().get(HttpHeaderNames.CONTENT_LENGTH);
        if (str != null) {
            return Long.parseLong(str);
        }
        long a2 = a(httpMessage);
        if (a2 >= 0) {
            return a2;
        }
        throw new NumberFormatException("header not found: " + ((Object) HttpHeaderNames.CONTENT_LENGTH));
    }

    public static long getContentLength(HttpMessage httpMessage, long j) {
        String str = httpMessage.headers().get(HttpHeaderNames.CONTENT_LENGTH);
        if (str != null) {
            return Long.parseLong(str);
        }
        long a2 = a(httpMessage);
        return a2 >= 0 ? a2 : j;
    }

    public static int getContentLength(HttpMessage httpMessage, int i) {
        return (int) Math.min(2147483647L, getContentLength(httpMessage, i));
    }

    private static int a(HttpMessage httpMessage) {
        HttpHeaders headers = httpMessage.headers();
        return httpMessage instanceof HttpRequest ? (!HttpMethod.GET.equals(((HttpRequest) httpMessage).method()) || !headers.contains(HttpHeaderNames.SEC_WEBSOCKET_KEY1) || !headers.contains(HttpHeaderNames.SEC_WEBSOCKET_KEY2)) ? -1 : 8 : (!(httpMessage instanceof HttpResponse) || ((HttpResponse) httpMessage).status().code() != 101 || !headers.contains(HttpHeaderNames.SEC_WEBSOCKET_ORIGIN) || !headers.contains(HttpHeaderNames.SEC_WEBSOCKET_LOCATION)) ? -1 : 16;
    }

    public static void setContentLength(HttpMessage httpMessage, long j) {
        httpMessage.headers().set(HttpHeaderNames.CONTENT_LENGTH, Long.valueOf(j));
    }

    public static boolean isContentLengthSet(HttpMessage httpMessage) {
        return httpMessage.headers().contains(HttpHeaderNames.CONTENT_LENGTH);
    }

    public static boolean is100ContinueExpected(HttpMessage httpMessage) {
        String str;
        if (!(httpMessage instanceof HttpRequest) || httpMessage.protocolVersion().compareTo(HttpVersion.HTTP_1_1) < 0 || (str = httpMessage.headers().get(HttpHeaderNames.EXPECT)) == null) {
            return false;
        }
        if (HttpHeaderValues.CONTINUE.contentEqualsIgnoreCase(str)) {
            return true;
        }
        return httpMessage.headers().contains((CharSequence) HttpHeaderNames.EXPECT, (CharSequence) HttpHeaderValues.CONTINUE, true);
    }

    public static void set100ContinueExpected(HttpMessage httpMessage, boolean z) {
        if (z) {
            httpMessage.headers().set(HttpHeaderNames.EXPECT, HttpHeaderValues.CONTINUE);
        } else {
            httpMessage.headers().remove(HttpHeaderNames.EXPECT);
        }
    }

    public static boolean isTransferEncodingChunked(HttpMessage httpMessage) {
        return httpMessage.headers().contains((CharSequence) HttpHeaderNames.TRANSFER_ENCODING, (CharSequence) HttpHeaderValues.CHUNKED, true);
    }

    public static void setTransferEncodingChunked(HttpMessage httpMessage, boolean z) {
        if (z) {
            httpMessage.headers().add(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
            httpMessage.headers().remove(HttpHeaderNames.CONTENT_LENGTH);
            return;
        }
        List<String> all = httpMessage.headers().getAll(HttpHeaderNames.TRANSFER_ENCODING);
        if (!all.isEmpty()) {
            ArrayList arrayList = new ArrayList(all);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (HttpHeaderValues.CHUNKED.contentEqualsIgnoreCase((CharSequence) it.next())) {
                    it.remove();
                }
            }
            if (arrayList.isEmpty()) {
                httpMessage.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
            } else {
                httpMessage.headers().set((CharSequence) HttpHeaderNames.TRANSFER_ENCODING, (Iterable<?>) arrayList);
            }
        }
    }

    public static Charset getCharset(HttpMessage httpMessage) {
        return getCharset(httpMessage, CharsetUtil.ISO_8859_1);
    }

    public static Charset getCharset(HttpMessage httpMessage, Charset charset) {
        CharSequence charsetAsString = getCharsetAsString(httpMessage);
        if (charsetAsString == null) {
            return charset;
        }
        try {
            return Charset.forName(charsetAsString.toString());
        } catch (UnsupportedCharsetException unused) {
            return charset;
        }
    }

    public static CharSequence getCharsetAsString(HttpMessage httpMessage) {
        int indexOfIgnoreCaseAscii;
        int length;
        String str = httpMessage.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (str == null || (indexOfIgnoreCaseAscii = AsciiString.indexOfIgnoreCaseAscii(str, b, 0)) == -1 || (length = indexOfIgnoreCaseAscii + b.length()) >= str.length()) {
            return null;
        }
        return str.subSequence(length, str.length());
    }

    public static CharSequence getMimeType(HttpMessage httpMessage) {
        String str = httpMessage.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (str == null) {
            return null;
        }
        int indexOfIgnoreCaseAscii = AsciiString.indexOfIgnoreCaseAscii(str, c, 0);
        if (indexOfIgnoreCaseAscii != -1) {
            return str.subSequence(0, indexOfIgnoreCaseAscii);
        }
        if (str.length() > 0) {
            return str;
        }
        return null;
    }

    public static void a(CharSequence charSequence, ByteBuf byteBuf) {
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            byteBuf.writeByte(a(charSequence.charAt(i)));
        }
    }
}
