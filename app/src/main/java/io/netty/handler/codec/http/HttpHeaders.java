package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public abstract class HttpHeaders implements Iterable<Map.Entry<String, String>> {
    @Deprecated
    public static final HttpHeaders EMPTY_HEADERS = EmptyHttpHeaders.a();

    public abstract HttpHeaders add(String str, Iterable<?> iterable);

    public abstract HttpHeaders add(String str, Object obj);

    public abstract HttpHeaders addInt(CharSequence charSequence, int i);

    public abstract HttpHeaders addShort(CharSequence charSequence, short s);

    public abstract HttpHeaders clear();

    public abstract boolean contains(String str);

    public abstract List<Map.Entry<String, String>> entries();

    public abstract String get(String str);

    public abstract List<String> getAll(String str);

    public abstract int getInt(CharSequence charSequence, int i);

    public abstract Integer getInt(CharSequence charSequence);

    public abstract Short getShort(CharSequence charSequence);

    public abstract short getShort(CharSequence charSequence, short s);

    public abstract long getTimeMillis(CharSequence charSequence, long j);

    public abstract Long getTimeMillis(CharSequence charSequence);

    public abstract boolean isEmpty();

    @Override // java.lang.Iterable
    @Deprecated
    public abstract Iterator<Map.Entry<String, String>> iterator();

    public abstract Iterator<Map.Entry<CharSequence, CharSequence>> iteratorCharSequence();

    public abstract Set<String> names();

    public abstract HttpHeaders remove(String str);

    public abstract HttpHeaders set(String str, Iterable<?> iterable);

    public abstract HttpHeaders set(String str, Object obj);

    public abstract HttpHeaders setInt(CharSequence charSequence, int i);

    public abstract HttpHeaders setShort(CharSequence charSequence, short s);

    public abstract int size();

    @Deprecated
    /* loaded from: classes4.dex */
    public static final class Names {
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String ACCEPT_PATCH = "Accept-Patch";
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
        public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
        public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
        public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
        public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
        public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
        public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
        public static final String AGE = "Age";
        public static final String ALLOW = "Allow";
        public static final String AUTHORIZATION = "Authorization";
        public static final String CACHE_CONTROL = "Cache-Control";
        public static final String CONNECTION = "Connection";
        public static final String CONTENT_BASE = "Content-Base";
        public static final String CONTENT_ENCODING = "Content-Encoding";
        public static final String CONTENT_LANGUAGE = "Content-Language";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String CONTENT_LOCATION = "Content-Location";
        public static final String CONTENT_MD5 = "Content-MD5";
        public static final String CONTENT_RANGE = "Content-Range";
        public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String COOKIE = "Cookie";
        public static final String DATE = "Date";
        public static final String ETAG = "ETag";
        public static final String EXPECT = "Expect";
        public static final String EXPIRES = "Expires";
        public static final String FROM = "From";
        public static final String HOST = "Host";
        public static final String IF_MATCH = "If-Match";
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        public static final String IF_NONE_MATCH = "If-None-Match";
        public static final String IF_RANGE = "If-Range";
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        public static final String LAST_MODIFIED = "Last-Modified";
        public static final String LOCATION = "Location";
        public static final String MAX_FORWARDS = "Max-Forwards";
        public static final String ORIGIN = "Origin";
        public static final String PRAGMA = "Pragma";
        public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
        public static final String RANGE = "Range";
        public static final String REFERER = "Referer";
        public static final String RETRY_AFTER = "Retry-After";
        public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
        public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
        public static final String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";
        public static final String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";
        public static final String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";
        public static final String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";
        public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
        public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
        public static final String SERVER = "Server";
        public static final String SET_COOKIE = "Set-Cookie";
        public static final String SET_COOKIE2 = "Set-Cookie2";
        public static final String TE = "TE";
        public static final String TRAILER = "Trailer";
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";
        public static final String UPGRADE = "Upgrade";
        public static final String USER_AGENT = "User-Agent";
        public static final String VARY = "Vary";
        public static final String VIA = "Via";
        public static final String WARNING = "Warning";
        public static final String WEBSOCKET_LOCATION = "WebSocket-Location";
        public static final String WEBSOCKET_ORIGIN = "WebSocket-Origin";
        public static final String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

        private Names() {
        }
    }

    @Deprecated
    /* loaded from: classes4.dex */
    public static final class Values {
        public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
        public static final String BASE64 = "base64";
        public static final String BINARY = "binary";
        public static final String BOUNDARY = "boundary";
        public static final String BYTES = "bytes";
        public static final String CHARSET = "charset";
        public static final String CHUNKED = "chunked";
        public static final String CLOSE = "close";
        public static final String COMPRESS = "compress";
        public static final String CONTINUE = "100-continue";
        public static final String DEFLATE = "deflate";
        public static final String GZIP = "gzip";
        public static final String IDENTITY = "identity";
        public static final String KEEP_ALIVE = "keep-alive";
        public static final String MAX_AGE = "max-age";
        public static final String MAX_STALE = "max-stale";
        public static final String MIN_FRESH = "min-fresh";
        public static final String MULTIPART_FORM_DATA = "multipart/form-data";
        public static final String MUST_REVALIDATE = "must-revalidate";
        public static final String NONE = "none";
        public static final String NO_CACHE = "no-cache";
        public static final String NO_STORE = "no-store";
        public static final String NO_TRANSFORM = "no-transform";
        public static final String ONLY_IF_CACHED = "only-if-cached";
        public static final String PRIVATE = "private";
        public static final String PROXY_REVALIDATE = "proxy-revalidate";
        public static final String PUBLIC = "public";
        public static final String QUOTED_PRINTABLE = "quoted-printable";
        public static final String S_MAXAGE = "s-maxage";
        public static final String TRAILERS = "trailers";
        public static final String UPGRADE = "Upgrade";
        public static final String WEBSOCKET = "WebSocket";

        private Values() {
        }
    }

    @Deprecated
    public static boolean isKeepAlive(HttpMessage httpMessage) {
        return HttpUtil.isKeepAlive(httpMessage);
    }

    @Deprecated
    public static void setKeepAlive(HttpMessage httpMessage, boolean z) {
        HttpUtil.setKeepAlive(httpMessage, z);
    }

    @Deprecated
    public static String getHeader(HttpMessage httpMessage, String str) {
        return httpMessage.headers().get(str);
    }

    @Deprecated
    public static String getHeader(HttpMessage httpMessage, CharSequence charSequence) {
        return httpMessage.headers().get(charSequence);
    }

    @Deprecated
    public static String getHeader(HttpMessage httpMessage, String str, String str2) {
        return httpMessage.headers().get(str, str2);
    }

    @Deprecated
    public static String getHeader(HttpMessage httpMessage, CharSequence charSequence, String str) {
        return httpMessage.headers().get(charSequence, str);
    }

    @Deprecated
    public static void setHeader(HttpMessage httpMessage, String str, Object obj) {
        httpMessage.headers().set(str, obj);
    }

    @Deprecated
    public static void setHeader(HttpMessage httpMessage, CharSequence charSequence, Object obj) {
        httpMessage.headers().set(charSequence, obj);
    }

    @Deprecated
    public static void setHeader(HttpMessage httpMessage, String str, Iterable<?> iterable) {
        httpMessage.headers().set(str, iterable);
    }

    @Deprecated
    public static void setHeader(HttpMessage httpMessage, CharSequence charSequence, Iterable<?> iterable) {
        httpMessage.headers().set(charSequence, iterable);
    }

    @Deprecated
    public static void addHeader(HttpMessage httpMessage, String str, Object obj) {
        httpMessage.headers().add(str, obj);
    }

    @Deprecated
    public static void addHeader(HttpMessage httpMessage, CharSequence charSequence, Object obj) {
        httpMessage.headers().add(charSequence, obj);
    }

    @Deprecated
    public static void removeHeader(HttpMessage httpMessage, String str) {
        httpMessage.headers().remove(str);
    }

    @Deprecated
    public static void removeHeader(HttpMessage httpMessage, CharSequence charSequence) {
        httpMessage.headers().remove(charSequence);
    }

    @Deprecated
    public static void clearHeaders(HttpMessage httpMessage) {
        httpMessage.headers().clear();
    }

    @Deprecated
    public static int getIntHeader(HttpMessage httpMessage, String str) {
        return getIntHeader(httpMessage, (CharSequence) str);
    }

    @Deprecated
    public static int getIntHeader(HttpMessage httpMessage, CharSequence charSequence) {
        String str = httpMessage.headers().get(charSequence);
        if (str != null) {
            return Integer.parseInt(str);
        }
        throw new NumberFormatException("header not found: " + ((Object) charSequence));
    }

    @Deprecated
    public static int getIntHeader(HttpMessage httpMessage, String str, int i) {
        return httpMessage.headers().getInt(str, i);
    }

    @Deprecated
    public static int getIntHeader(HttpMessage httpMessage, CharSequence charSequence, int i) {
        return httpMessage.headers().getInt(charSequence, i);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage httpMessage, String str, int i) {
        httpMessage.headers().setInt(str, i);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage httpMessage, CharSequence charSequence, int i) {
        httpMessage.headers().setInt(charSequence, i);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage httpMessage, String str, Iterable<Integer> iterable) {
        httpMessage.headers().set(str, (Iterable<?>) iterable);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage httpMessage, CharSequence charSequence, Iterable<Integer> iterable) {
        httpMessage.headers().set(charSequence, (Iterable<?>) iterable);
    }

    @Deprecated
    public static void addIntHeader(HttpMessage httpMessage, String str, int i) {
        httpMessage.headers().add(str, (Object) Integer.valueOf(i));
    }

    @Deprecated
    public static void addIntHeader(HttpMessage httpMessage, CharSequence charSequence, int i) {
        httpMessage.headers().addInt(charSequence, i);
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage httpMessage, String str) throws ParseException {
        return getDateHeader(httpMessage, (CharSequence) str);
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage httpMessage, CharSequence charSequence) throws ParseException {
        String str = httpMessage.headers().get(charSequence);
        if (str != null) {
            return HttpHeaderDateFormat.get().parse(str);
        }
        throw new ParseException("header not found: " + ((Object) charSequence), 0);
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage httpMessage, String str, Date date) {
        return getDateHeader(httpMessage, (CharSequence) str, date);
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage httpMessage, CharSequence charSequence, Date date) {
        String header = getHeader(httpMessage, charSequence);
        if (header == null) {
            return date;
        }
        try {
            return HttpHeaderDateFormat.get().parse(header);
        } catch (ParseException unused) {
            return date;
        }
    }

    @Deprecated
    public static void setDateHeader(HttpMessage httpMessage, String str, Date date) {
        setDateHeader(httpMessage, (CharSequence) str, date);
    }

    @Deprecated
    public static void setDateHeader(HttpMessage httpMessage, CharSequence charSequence, Date date) {
        if (date != null) {
            httpMessage.headers().set(charSequence, HttpHeaderDateFormat.get().format(date));
        } else {
            httpMessage.headers().set(charSequence, (Iterable<?>) null);
        }
    }

    @Deprecated
    public static void setDateHeader(HttpMessage httpMessage, String str, Iterable<Date> iterable) {
        httpMessage.headers().set(str, (Iterable<?>) iterable);
    }

    @Deprecated
    public static void setDateHeader(HttpMessage httpMessage, CharSequence charSequence, Iterable<Date> iterable) {
        httpMessage.headers().set(charSequence, (Iterable<?>) iterable);
    }

    @Deprecated
    public static void addDateHeader(HttpMessage httpMessage, String str, Date date) {
        httpMessage.headers().add(str, (Object) date);
    }

    @Deprecated
    public static void addDateHeader(HttpMessage httpMessage, CharSequence charSequence, Date date) {
        httpMessage.headers().add(charSequence, date);
    }

    @Deprecated
    public static long getContentLength(HttpMessage httpMessage) {
        return HttpUtil.getContentLength(httpMessage);
    }

    @Deprecated
    public static long getContentLength(HttpMessage httpMessage, long j) {
        return HttpUtil.getContentLength(httpMessage, j);
    }

    @Deprecated
    public static void setContentLength(HttpMessage httpMessage, long j) {
        HttpUtil.setContentLength(httpMessage, j);
    }

    @Deprecated
    public static String getHost(HttpMessage httpMessage) {
        return httpMessage.headers().get(HttpHeaderNames.HOST);
    }

    @Deprecated
    public static String getHost(HttpMessage httpMessage, String str) {
        return httpMessage.headers().get(HttpHeaderNames.HOST, str);
    }

    @Deprecated
    public static void setHost(HttpMessage httpMessage, String str) {
        httpMessage.headers().set(HttpHeaderNames.HOST, str);
    }

    @Deprecated
    public static void setHost(HttpMessage httpMessage, CharSequence charSequence) {
        httpMessage.headers().set(HttpHeaderNames.HOST, charSequence);
    }

    @Deprecated
    public static Date getDate(HttpMessage httpMessage) throws ParseException {
        return getDateHeader(httpMessage, HttpHeaderNames.DATE);
    }

    @Deprecated
    public static Date getDate(HttpMessage httpMessage, Date date) {
        return getDateHeader(httpMessage, HttpHeaderNames.DATE, date);
    }

    @Deprecated
    public static void setDate(HttpMessage httpMessage, Date date) {
        httpMessage.headers().set(HttpHeaderNames.DATE, date);
    }

    @Deprecated
    public static boolean is100ContinueExpected(HttpMessage httpMessage) {
        return HttpUtil.is100ContinueExpected(httpMessage);
    }

    @Deprecated
    public static void set100ContinueExpected(HttpMessage httpMessage) {
        HttpUtil.set100ContinueExpected(httpMessage, true);
    }

    @Deprecated
    public static void set100ContinueExpected(HttpMessage httpMessage, boolean z) {
        HttpUtil.set100ContinueExpected(httpMessage, z);
    }

    @Deprecated
    public static boolean isTransferEncodingChunked(HttpMessage httpMessage) {
        return HttpUtil.isTransferEncodingChunked(httpMessage);
    }

    @Deprecated
    public static void removeTransferEncodingChunked(HttpMessage httpMessage) {
        HttpUtil.setTransferEncodingChunked(httpMessage, false);
    }

    @Deprecated
    public static void setTransferEncodingChunked(HttpMessage httpMessage) {
        HttpUtil.setTransferEncodingChunked(httpMessage, true);
    }

    @Deprecated
    public static boolean isContentLengthSet(HttpMessage httpMessage) {
        return HttpUtil.isContentLengthSet(httpMessage);
    }

    @Deprecated
    public static boolean equalsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return AsciiString.contentEqualsIgnoreCase(charSequence, charSequence2);
    }

    public static void encodeAscii(CharSequence charSequence, ByteBuf byteBuf) {
        if (charSequence instanceof AsciiString) {
            ByteBufUtil.copy((AsciiString) charSequence, 0, byteBuf, charSequence.length());
        } else {
            HttpUtil.a(charSequence, byteBuf);
        }
    }

    @Deprecated
    public static CharSequence newEntity(String str) {
        return new AsciiString(str);
    }

    public String get(CharSequence charSequence) {
        return get(charSequence.toString());
    }

    public String get(CharSequence charSequence, String str) {
        String str2 = get(charSequence);
        return str2 == null ? str : str2;
    }

    public List<String> getAll(CharSequence charSequence) {
        return getAll(charSequence.toString());
    }

    public boolean contains(CharSequence charSequence) {
        return contains(charSequence.toString());
    }

    public HttpHeaders add(CharSequence charSequence, Object obj) {
        return add(charSequence.toString(), obj);
    }

    public HttpHeaders add(CharSequence charSequence, Iterable<?> iterable) {
        return add(charSequence.toString(), iterable);
    }

    public HttpHeaders add(HttpHeaders httpHeaders) {
        if (httpHeaders != null) {
            Iterator<Map.Entry<String, String>> it = httpHeaders.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> next = it.next();
                add(next.getKey(), (Object) next.getValue());
            }
            return this;
        }
        throw new NullPointerException("headers");
    }

    public HttpHeaders set(CharSequence charSequence, Object obj) {
        return set(charSequence.toString(), obj);
    }

    public HttpHeaders set(CharSequence charSequence, Iterable<?> iterable) {
        return set(charSequence.toString(), iterable);
    }

    public HttpHeaders set(HttpHeaders httpHeaders) {
        ObjectUtil.checkNotNull(httpHeaders, "headers");
        clear();
        if (httpHeaders.isEmpty()) {
            return this;
        }
        Iterator<Map.Entry<String, String>> it = httpHeaders.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            add(next.getKey(), (Object) next.getValue());
        }
        return this;
    }

    public HttpHeaders setAll(HttpHeaders httpHeaders) {
        ObjectUtil.checkNotNull(httpHeaders, "headers");
        if (httpHeaders.isEmpty()) {
            return this;
        }
        Iterator<Map.Entry<String, String>> it = httpHeaders.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            set(next.getKey(), (Object) next.getValue());
        }
        return this;
    }

    public HttpHeaders remove(CharSequence charSequence) {
        return remove(charSequence.toString());
    }

    public boolean contains(String str, String str2, boolean z) {
        List<String> all = getAll(str);
        if (all.isEmpty()) {
            return false;
        }
        for (String str3 : all) {
            if (z) {
                if (str3.equalsIgnoreCase(str2)) {
                    return true;
                }
            } else if (str3.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        List<String> all = getAll(charSequence);
        if (all.isEmpty()) {
            return false;
        }
        for (String str : all) {
            if (a(str, charSequence2, z)) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(String str, CharSequence charSequence, boolean z) {
        String[] split = StringUtil.split(str, StringUtil.COMMA);
        if (z) {
            for (String str2 : split) {
                if (AsciiString.contentEqualsIgnoreCase(charSequence, str2.trim())) {
                    return true;
                }
            }
        } else {
            for (String str3 : split) {
                if (AsciiString.contentEquals(charSequence, str3.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    public final String getAsString(CharSequence charSequence) {
        return get(charSequence);
    }

    public final List<String> getAllAsString(CharSequence charSequence) {
        return getAll(charSequence);
    }

    public final Iterator<Map.Entry<String, String>> iteratorAsString() {
        return iterator();
    }

    public boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        return contains(charSequence.toString(), charSequence2.toString(), z);
    }
}
