package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public final class HttpConversionUtil {
    public static final String OUT_OF_MESSAGE_SEQUENCE_PATH = "";
    private static final b<AsciiString> a = new b<>();
    public static final HttpMethod OUT_OF_MESSAGE_SEQUENCE_METHOD = HttpMethod.OPTIONS;
    public static final HttpResponseStatus OUT_OF_MESSAGE_SEQUENCE_RETURN_CODE = HttpResponseStatus.OK;
    private static final AsciiString b = new AsciiString("/");

    static {
        a.add((b<AsciiString>) HttpHeaderNames.CONNECTION, AsciiString.EMPTY_STRING);
        a.add((b<AsciiString>) HttpHeaderNames.KEEP_ALIVE, AsciiString.EMPTY_STRING);
        a.add((b<AsciiString>) HttpHeaderNames.PROXY_CONNECTION, AsciiString.EMPTY_STRING);
        a.add((b<AsciiString>) HttpHeaderNames.TRANSFER_ENCODING, AsciiString.EMPTY_STRING);
        a.add((b<AsciiString>) HttpHeaderNames.HOST, AsciiString.EMPTY_STRING);
        a.add((b<AsciiString>) HttpHeaderNames.UPGRADE, AsciiString.EMPTY_STRING);
        a.add((b<AsciiString>) ExtensionHeaderNames.STREAM_ID.text(), AsciiString.EMPTY_STRING);
        a.add((b<AsciiString>) ExtensionHeaderNames.SCHEME.text(), AsciiString.EMPTY_STRING);
        a.add((b<AsciiString>) ExtensionHeaderNames.PATH.text(), AsciiString.EMPTY_STRING);
    }

    private HttpConversionUtil() {
    }

    /* loaded from: classes4.dex */
    public enum ExtensionHeaderNames {
        STREAM_ID("x-http2-stream-id"),
        SCHEME("x-http2-scheme"),
        PATH("x-http2-path"),
        STREAM_PROMISE_ID("x-http2-stream-promise-id"),
        STREAM_DEPENDENCY_ID("x-http2-stream-dependency-id"),
        STREAM_WEIGHT("x-http2-stream-weight");
        
        private final AsciiString text;

        ExtensionHeaderNames(String str) {
            this.text = new AsciiString(str);
        }

        public AsciiString text() {
            return this.text;
        }
    }

    public static HttpResponseStatus parseStatus(CharSequence charSequence) throws Http2Exception {
        try {
            HttpResponseStatus parseLine = HttpResponseStatus.parseLine(charSequence);
            if (parseLine != HttpResponseStatus.SWITCHING_PROTOCOLS) {
                return parseLine;
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Invalid HTTP/2 status code '%d'", Integer.valueOf(parseLine.code()));
        } catch (Http2Exception e) {
            throw e;
        } catch (Throwable th) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, th, "Unrecognized HTTP status code '%s' encountered in translation to HTTP/1.x", charSequence);
        }
    }

    public static FullHttpResponse toHttpResponse(int i, Http2Headers http2Headers, ByteBufAllocator byteBufAllocator, boolean z) throws Http2Exception {
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, parseStatus(http2Headers.status()), byteBufAllocator.buffer(), z);
        try {
            addHttp2ToHttpHeaders(i, http2Headers, defaultFullHttpResponse, false);
            return defaultFullHttpResponse;
        } catch (Http2Exception e) {
            defaultFullHttpResponse.release();
            throw e;
        } catch (Throwable th) {
            defaultFullHttpResponse.release();
            throw Http2Exception.streamError(i, Http2Error.PROTOCOL_ERROR, th, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
    }

    public static FullHttpRequest toFullHttpRequest(int i, Http2Headers http2Headers, ByteBufAllocator byteBufAllocator, boolean z) throws Http2Exception {
        DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(((CharSequence) ObjectUtil.checkNotNull(http2Headers.method(), "method header cannot be null in conversion to HTTP/1.x")).toString()), ((CharSequence) ObjectUtil.checkNotNull(http2Headers.path(), "path header cannot be null in conversion to HTTP/1.x")).toString(), byteBufAllocator.buffer(), z);
        try {
            addHttp2ToHttpHeaders(i, http2Headers, defaultFullHttpRequest, false);
            return defaultFullHttpRequest;
        } catch (Http2Exception e) {
            defaultFullHttpRequest.release();
            throw e;
        } catch (Throwable th) {
            defaultFullHttpRequest.release();
            throw Http2Exception.streamError(i, Http2Error.PROTOCOL_ERROR, th, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
    }

    public static HttpRequest toHttpRequest(int i, Http2Headers http2Headers, boolean z) throws Http2Exception {
        DefaultHttpRequest defaultHttpRequest = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(((CharSequence) ObjectUtil.checkNotNull(http2Headers.method(), "method header cannot be null in conversion to HTTP/1.x")).toString()), ((CharSequence) ObjectUtil.checkNotNull(http2Headers.path(), "path header cannot be null in conversion to HTTP/1.x")).toString(), z);
        try {
            addHttp2ToHttpHeaders(i, http2Headers, defaultHttpRequest.headers(), defaultHttpRequest.protocolVersion(), false, true);
            return defaultHttpRequest;
        } catch (Http2Exception e) {
            throw e;
        } catch (Throwable th) {
            throw Http2Exception.streamError(i, Http2Error.PROTOCOL_ERROR, th, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
    }

    public static void addHttp2ToHttpHeaders(int i, Http2Headers http2Headers, FullHttpMessage fullHttpMessage, boolean z) throws Http2Exception {
        addHttp2ToHttpHeaders(i, http2Headers, z ? fullHttpMessage.trailingHeaders() : fullHttpMessage.headers(), fullHttpMessage.protocolVersion(), z, fullHttpMessage instanceof HttpRequest);
    }

    public static void addHttp2ToHttpHeaders(int i, Http2Headers http2Headers, HttpHeaders httpHeaders, HttpVersion httpVersion, boolean z, boolean z2) throws Http2Exception {
        a aVar = new a(i, httpHeaders, z2);
        try {
            Iterator<Map.Entry<CharSequence, CharSequence>> it = http2Headers.iterator();
            while (it.hasNext()) {
                aVar.a(it.next());
            }
            httpHeaders.remove(HttpHeaderNames.TRANSFER_ENCODING);
            httpHeaders.remove(HttpHeaderNames.TRAILER);
            if (!z) {
                httpHeaders.setInt(ExtensionHeaderNames.STREAM_ID.text(), i);
                HttpUtil.setKeepAlive(httpHeaders, httpVersion, true);
            }
        } catch (Http2Exception e) {
            throw e;
        } catch (Throwable th) {
            throw Http2Exception.streamError(i, Http2Error.PROTOCOL_ERROR, th, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
    }

    public static Http2Headers toHttp2Headers(HttpMessage httpMessage, boolean z) {
        HttpHeaders headers = httpMessage.headers();
        DefaultHttp2Headers defaultHttp2Headers = new DefaultHttp2Headers(z, headers.size());
        if (httpMessage instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) httpMessage;
            URI create = URI.create(httpRequest.uri());
            defaultHttp2Headers.path(a(create));
            defaultHttp2Headers.method(httpRequest.method().asciiName());
            a(headers, create, defaultHttp2Headers);
            if (!HttpUtil.isOriginForm(create) && !HttpUtil.isAsteriskForm(create)) {
                String asString = headers.getAsString(HttpHeaderNames.HOST);
                if (asString == null || asString.isEmpty()) {
                    asString = create.getAuthority();
                }
                a(asString, defaultHttp2Headers);
            }
        } else if (httpMessage instanceof HttpResponse) {
            defaultHttp2Headers.status(new AsciiString(Integer.toString(((HttpResponse) httpMessage).status().code())));
        }
        toHttp2Headers(headers, defaultHttp2Headers);
        return defaultHttp2Headers;
    }

    public static Http2Headers toHttp2Headers(HttpHeaders httpHeaders, boolean z) {
        if (httpHeaders.isEmpty()) {
            return EmptyHttp2Headers.INSTANCE;
        }
        DefaultHttp2Headers defaultHttp2Headers = new DefaultHttp2Headers(z, httpHeaders.size());
        toHttp2Headers(httpHeaders, defaultHttp2Headers);
        return defaultHttp2Headers;
    }

    public static void toHttp2Headers(HttpHeaders httpHeaders, Http2Headers http2Headers) {
        Iterator<Map.Entry<CharSequence, CharSequence>> iteratorCharSequence = httpHeaders.iteratorCharSequence();
        while (iteratorCharSequence.hasNext()) {
            Map.Entry<CharSequence, CharSequence> next = iteratorCharSequence.next();
            AsciiString lowerCase = AsciiString.of(next.getKey()).toLowerCase();
            if (!a.contains(lowerCase)) {
                if (lowerCase.contentEqualsIgnoreCase(HttpHeaderNames.TE) && !AsciiString.contentEqualsIgnoreCase(next.getValue(), HttpHeaderValues.TRAILERS)) {
                    throw new IllegalArgumentException("Invalid value for " + ((Object) HttpHeaderNames.TE) + ": " + ((Object) next.getValue()));
                } else if (lowerCase.contentEqualsIgnoreCase(HttpHeaderNames.COOKIE)) {
                    AsciiString of = AsciiString.of(next.getValue());
                    try {
                        int forEachByte = of.forEachByte(ByteProcessor.FIND_SEMI_COLON);
                        if (forEachByte != -1) {
                            int i = forEachByte;
                            int i2 = 0;
                            do {
                                http2Headers.add((Http2Headers) HttpHeaderNames.COOKIE, of.subSequence(i2, i, false));
                                i2 = i + 2;
                                if (i2 >= of.length()) {
                                    break;
                                }
                                i = of.forEachByte(i2, of.length() - i2, ByteProcessor.FIND_SEMI_COLON);
                            } while (i != -1);
                            if (i2 < of.length()) {
                                http2Headers.add((Http2Headers) HttpHeaderNames.COOKIE, of.subSequence(i2, of.length(), false));
                            } else {
                                throw new IllegalArgumentException("cookie value is of unexpected format: " + ((Object) of));
                            }
                        } else {
                            http2Headers.add((Http2Headers) HttpHeaderNames.COOKIE, of);
                        }
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                } else {
                    http2Headers.add((Http2Headers) lowerCase, (AsciiString) next.getValue());
                }
            }
        }
    }

    private static AsciiString a(URI uri) {
        StringBuilder sb = new StringBuilder(StringUtil.length(uri.getRawPath()) + StringUtil.length(uri.getRawQuery()) + StringUtil.length(uri.getRawFragment()) + 2);
        if (!StringUtil.isNullOrEmpty(uri.getRawPath())) {
            sb.append(uri.getRawPath());
        }
        if (!StringUtil.isNullOrEmpty(uri.getRawQuery())) {
            sb.append('?');
            sb.append(uri.getRawQuery());
        }
        if (!StringUtil.isNullOrEmpty(uri.getRawFragment())) {
            sb.append('#');
            sb.append(uri.getRawFragment());
        }
        String sb2 = sb.toString();
        return sb2.isEmpty() ? b : new AsciiString(sb2);
    }

    private static void a(String str, Http2Headers http2Headers) {
        if (str != null) {
            int indexOf = str.indexOf(64);
            if (indexOf < 0) {
                http2Headers.authority(new AsciiString(str));
                return;
            }
            int i = indexOf + 1;
            if (i < str.length()) {
                http2Headers.authority(new AsciiString(str.substring(i)));
                return;
            }
            throw new IllegalArgumentException("autority: " + str);
        }
    }

    private static void a(HttpHeaders httpHeaders, URI uri, Http2Headers http2Headers) {
        String scheme = uri.getScheme();
        if (scheme != null) {
            http2Headers.scheme(new AsciiString(scheme));
            return;
        }
        String str = httpHeaders.get(ExtensionHeaderNames.SCHEME.text());
        if (str != null) {
            http2Headers.scheme(AsciiString.of(str));
        } else if (uri.getPort() == HttpScheme.HTTPS.port()) {
            http2Headers.scheme(HttpScheme.HTTPS.name());
        } else if (uri.getPort() == HttpScheme.HTTP.port()) {
            http2Headers.scheme(HttpScheme.HTTP.name());
        } else {
            throw new IllegalArgumentException(":scheme must be specified. see https://tools.ietf.org/html/rfc7540#section-8.1.2.3");
        }
    }

    /* loaded from: classes4.dex */
    public static final class a {
        private static final b<AsciiString> a = new b<>();
        private static final b<AsciiString> b = new b<>();
        private final int c;
        private final HttpHeaders d;
        private final b<AsciiString> e;

        static {
            b.add((b<AsciiString>) Http2Headers.PseudoHeaderName.AUTHORITY.value(), HttpHeaderNames.HOST);
            b.add((b<AsciiString>) Http2Headers.PseudoHeaderName.SCHEME.value(), ExtensionHeaderNames.SCHEME.text());
            a.add(b);
            b.add((b<AsciiString>) Http2Headers.PseudoHeaderName.PATH.value(), ExtensionHeaderNames.PATH.text());
        }

        a(int i, HttpHeaders httpHeaders, boolean z) {
            this.c = i;
            this.d = httpHeaders;
            this.e = z ? a : b;
        }

        public void a(Map.Entry<CharSequence, CharSequence> entry) throws Http2Exception {
            CharSequence key = entry.getKey();
            CharSequence value = entry.getValue();
            AsciiString asciiString = this.e.get(key);
            if (asciiString != null) {
                this.d.add(asciiString, AsciiString.of(value));
            } else if (Http2Headers.PseudoHeaderName.isPseudoHeader(key)) {
            } else {
                if (key.length() == 0 || key.charAt(0) == ':') {
                    throw Http2Exception.streamError(this.c, Http2Error.PROTOCOL_ERROR, "Invalid HTTP/2 header '%s' encountered in translation to HTTP/1.x", key);
                } else if (HttpHeaderNames.COOKIE.equals(key)) {
                    String str = this.d.get(HttpHeaderNames.COOKIE);
                    HttpHeaders httpHeaders = this.d;
                    AsciiString asciiString2 = HttpHeaderNames.COOKIE;
                    if (str != null) {
                        value = str + "; " + ((Object) value);
                    }
                    httpHeaders.set(asciiString2, value);
                } else {
                    this.d.add(key, value);
                }
            }
        }
    }
}
