package io.netty.handler.codec.http;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.AsciiString;

/* loaded from: classes4.dex */
public final class HttpHeaderValues {
    public static final AsciiString APPLICATION_X_WWW_FORM_URLENCODED = new AsciiString("application/x-www-form-urlencoded");
    public static final AsciiString APPLICATION_OCTET_STREAM = new AsciiString("application/octet-stream");
    public static final AsciiString ATTACHMENT = new AsciiString("attachment");
    public static final AsciiString BASE64 = new AsciiString(HttpHeaders.Values.BASE64);
    public static final AsciiString BINARY = new AsciiString(HttpHeaders.Values.BINARY);
    public static final AsciiString BOUNDARY = new AsciiString(HttpHeaders.Values.BOUNDARY);
    public static final AsciiString BYTES = new AsciiString("bytes");
    public static final AsciiString CHARSET = new AsciiString("charset");
    public static final AsciiString CHUNKED = new AsciiString("chunked");
    public static final AsciiString CLOSE = new AsciiString("close");
    public static final AsciiString COMPRESS = new AsciiString("compress");
    public static final AsciiString CONTINUE = new AsciiString("100-continue");
    public static final AsciiString DEFLATE = new AsciiString("deflate");
    public static final AsciiString X_DEFLATE = new AsciiString("x-deflate");
    public static final AsciiString FILE = new AsciiString("file");
    public static final AsciiString FILENAME = new AsciiString("filename");
    public static final AsciiString FORM_DATA = new AsciiString("form-data");
    public static final AsciiString GZIP = new AsciiString("gzip");
    public static final AsciiString X_GZIP = new AsciiString("x-gzip");
    public static final AsciiString IDENTITY = new AsciiString("identity");
    public static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");
    public static final AsciiString MAX_AGE = new AsciiString("max-age");
    public static final AsciiString MAX_STALE = new AsciiString("max-stale");
    public static final AsciiString MIN_FRESH = new AsciiString("min-fresh");
    public static final AsciiString MULTIPART_FORM_DATA = new AsciiString(HttpHeaders.Values.MULTIPART_FORM_DATA);
    public static final AsciiString MULTIPART_MIXED = new AsciiString("multipart/mixed");
    public static final AsciiString MUST_REVALIDATE = new AsciiString("must-revalidate");
    public static final AsciiString NAME = new AsciiString("name");
    public static final AsciiString NO_CACHE = new AsciiString("no-cache");
    public static final AsciiString NO_STORE = new AsciiString(HttpHeaders.Values.NO_STORE);
    public static final AsciiString NO_TRANSFORM = new AsciiString("no-transform");
    public static final AsciiString NONE = new AsciiString("none");
    public static final AsciiString ZERO = new AsciiString("0");
    public static final AsciiString ONLY_IF_CACHED = new AsciiString("only-if-cached");
    public static final AsciiString PRIVATE = new AsciiString("private");
    public static final AsciiString PROXY_REVALIDATE = new AsciiString("proxy-revalidate");
    public static final AsciiString PUBLIC = new AsciiString("public");
    public static final AsciiString QUOTED_PRINTABLE = new AsciiString(HttpHeaders.Values.QUOTED_PRINTABLE);
    public static final AsciiString S_MAXAGE = new AsciiString(HttpHeaders.Values.S_MAXAGE);
    public static final AsciiString TEXT_PLAIN = new AsciiString("text/plain");
    public static final AsciiString TRAILERS = new AsciiString(HttpHeaders.Values.TRAILERS);
    public static final AsciiString UPGRADE = new AsciiString("upgrade");
    public static final AsciiString WEBSOCKET = new AsciiString("websocket");

    private HttpHeaderValues() {
    }
}
