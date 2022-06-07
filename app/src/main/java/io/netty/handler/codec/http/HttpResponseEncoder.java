package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public class HttpResponseEncoder extends HttpObjectEncoder<HttpResponse> {
    private static final byte[] a = {13, 10};

    @Override // io.netty.handler.codec.http.HttpObjectEncoder, io.netty.handler.codec.MessageToMessageEncoder
    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return super.acceptOutboundMessage(obj) && !(obj instanceof HttpRequest);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encodeInitialLine(ByteBuf byteBuf, HttpResponse httpResponse) throws Exception {
        httpResponse.protocolVersion().a(byteBuf);
        byteBuf.writeByte(32);
        httpResponse.status().a(byteBuf);
        byteBuf.writeBytes(a);
    }
}
