package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.rtsp.RtspHeaders;
import io.netty.util.AsciiString;

/* loaded from: classes4.dex */
public final class RtspHeaderValues {
    public static final AsciiString APPEND = new AsciiString(RtspHeaders.Values.APPEND);
    public static final AsciiString AVP = new AsciiString(RtspHeaders.Values.AVP);
    public static final AsciiString BYTES = HttpHeaderValues.BYTES;
    public static final AsciiString CHARSET = HttpHeaderValues.CHARSET;
    public static final AsciiString CLIENT_PORT = new AsciiString(RtspHeaders.Values.CLIENT_PORT);
    public static final AsciiString CLOCK = new AsciiString(RtspHeaders.Values.CLOCK);
    public static final AsciiString CLOSE = HttpHeaderValues.CLOSE;
    public static final AsciiString COMPRESS = HttpHeaderValues.COMPRESS;
    public static final AsciiString CONTINUE = HttpHeaderValues.CONTINUE;
    public static final AsciiString DEFLATE = HttpHeaderValues.DEFLATE;
    public static final AsciiString DESTINATION = new AsciiString(RtspHeaders.Values.DESTINATION);
    public static final AsciiString GZIP = HttpHeaderValues.GZIP;
    public static final AsciiString IDENTITY = HttpHeaderValues.IDENTITY;
    public static final AsciiString INTERLEAVED = new AsciiString(RtspHeaders.Values.INTERLEAVED);
    public static final AsciiString KEEP_ALIVE = HttpHeaderValues.KEEP_ALIVE;
    public static final AsciiString LAYERS = new AsciiString(RtspHeaders.Values.LAYERS);
    public static final AsciiString MAX_AGE = HttpHeaderValues.MAX_AGE;
    public static final AsciiString MAX_STALE = HttpHeaderValues.MAX_STALE;
    public static final AsciiString MIN_FRESH = HttpHeaderValues.MIN_FRESH;
    public static final AsciiString MODE = new AsciiString("mode");
    public static final AsciiString MULTICAST = new AsciiString(RtspHeaders.Values.MULTICAST);
    public static final AsciiString MUST_REVALIDATE = HttpHeaderValues.MUST_REVALIDATE;
    public static final AsciiString NONE = HttpHeaderValues.NONE;
    public static final AsciiString NO_CACHE = HttpHeaderValues.NO_CACHE;
    public static final AsciiString NO_TRANSFORM = HttpHeaderValues.NO_TRANSFORM;
    public static final AsciiString ONLY_IF_CACHED = HttpHeaderValues.ONLY_IF_CACHED;
    public static final AsciiString PORT = new AsciiString("port");
    public static final AsciiString PRIVATE = HttpHeaderValues.PRIVATE;
    public static final AsciiString PROXY_REVALIDATE = HttpHeaderValues.PROXY_REVALIDATE;
    public static final AsciiString PUBLIC = HttpHeaderValues.PUBLIC;
    public static final AsciiString RTP = new AsciiString(RtspHeaders.Values.RTP);
    public static final AsciiString RTPTIME = new AsciiString(RtspHeaders.Values.RTPTIME);
    public static final AsciiString SEQ = new AsciiString(RtspHeaders.Values.SEQ);
    public static final AsciiString SERVER_PORT = new AsciiString(RtspHeaders.Values.SERVER_PORT);
    public static final AsciiString SSRC = new AsciiString(RtspHeaders.Values.SSRC);
    public static final AsciiString TCP = new AsciiString(RtspHeaders.Values.TCP);
    public static final AsciiString TIME = new AsciiString("time");
    public static final AsciiString TIMEOUT = new AsciiString(RtspHeaders.Values.TIMEOUT);
    public static final AsciiString TTL = new AsciiString(RtspHeaders.Values.TTL);
    public static final AsciiString UDP = new AsciiString(RtspHeaders.Values.UDP);
    public static final AsciiString UNICAST = new AsciiString(RtspHeaders.Values.UNICAST);
    public static final AsciiString URL = new AsciiString("url");

    private RtspHeaderValues() {
    }
}
