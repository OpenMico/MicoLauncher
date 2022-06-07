package io.netty.handler.codec.http;

/* loaded from: classes4.dex */
public class HttpResponseDecoder extends HttpObjectDecoder {
    private static final HttpResponseStatus a = new HttpResponseStatus(999, "Unknown");

    @Override // io.netty.handler.codec.http.HttpObjectDecoder
    protected boolean isDecodingRequest() {
        return false;
    }

    public HttpResponseDecoder() {
    }

    public HttpResponseDecoder(int i, int i2, int i3) {
        super(i, i2, i3, true);
    }

    public HttpResponseDecoder(int i, int i2, int i3, boolean z) {
        super(i, i2, i3, true, z);
    }

    public HttpResponseDecoder(int i, int i2, int i3, boolean z, int i4) {
        super(i, i2, i3, true, z, i4);
    }

    @Override // io.netty.handler.codec.http.HttpObjectDecoder
    protected HttpMessage createMessage(String[] strArr) {
        return new DefaultHttpResponse(HttpVersion.valueOf(strArr[0]), new HttpResponseStatus(Integer.parseInt(strArr[1]), strArr[2]), this.validateHeaders);
    }

    @Override // io.netty.handler.codec.http.HttpObjectDecoder
    protected HttpMessage createInvalidMessage() {
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, a, this.validateHeaders);
    }
}
