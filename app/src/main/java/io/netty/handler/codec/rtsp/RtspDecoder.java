package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectDecoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class RtspDecoder extends HttpObjectDecoder {
    public static final int DEFAULT_MAX_CONTENT_LENGTH = 8192;
    public static final int DEFAULT_MAX_HEADER_SIZE = 8192;
    public static final int DEFAULT_MAX_INITIAL_LINE_LENGTH = 4096;
    private static final HttpResponseStatus a = new HttpResponseStatus(999, "Unknown");
    private static final Pattern e = Pattern.compile("RTSP/\\d\\.\\d");
    private boolean d;

    public RtspDecoder() {
        this(4096, 8192, 8192);
    }

    public RtspDecoder(int i, int i2, int i3) {
        super(i, i2, i3 * 2, false);
    }

    public RtspDecoder(int i, int i2, int i3, boolean z) {
        super(i, i2, i3 * 2, false, z);
    }

    @Override // io.netty.handler.codec.http.HttpObjectDecoder
    protected HttpMessage createMessage(String[] strArr) throws Exception {
        if (e.matcher(strArr[0]).matches()) {
            this.d = false;
            return new DefaultHttpResponse(RtspVersions.valueOf(strArr[0]), new HttpResponseStatus(Integer.parseInt(strArr[1]), strArr[2]), this.validateHeaders);
        }
        this.d = true;
        return new DefaultHttpRequest(RtspVersions.valueOf(strArr[2]), RtspMethods.valueOf(strArr[0]), strArr[1], this.validateHeaders);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.http.HttpObjectDecoder
    public boolean isContentAlwaysEmpty(HttpMessage httpMessage) {
        return super.isContentAlwaysEmpty(httpMessage) || !httpMessage.headers().contains(RtspHeaderNames.CONTENT_LENGTH);
    }

    @Override // io.netty.handler.codec.http.HttpObjectDecoder
    protected HttpMessage createInvalidMessage() {
        if (this.d) {
            return new DefaultFullHttpRequest(RtspVersions.RTSP_1_0, RtspMethods.OPTIONS, "/bad-request", this.validateHeaders);
        }
        return new DefaultFullHttpResponse(RtspVersions.RTSP_1_0, a, this.validateHeaders);
    }

    @Override // io.netty.handler.codec.http.HttpObjectDecoder
    protected boolean isDecodingRequest() {
        return this.d;
    }
}
