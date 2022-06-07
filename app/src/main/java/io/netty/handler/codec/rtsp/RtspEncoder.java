package io.netty.handler.codec.rtsp;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectEncoder;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class RtspEncoder extends HttpObjectEncoder<HttpMessage> {
    private static final byte[] a = {13, 10};

    @Override // io.netty.handler.codec.http.HttpObjectEncoder, io.netty.handler.codec.MessageToMessageEncoder
    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return super.acceptOutboundMessage(obj) && ((obj instanceof HttpRequest) || (obj instanceof HttpResponse));
    }

    @Override // io.netty.handler.codec.http.HttpObjectEncoder
    protected void encodeInitialLine(ByteBuf byteBuf, HttpMessage httpMessage) throws Exception {
        if (httpMessage instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) httpMessage;
            HttpHeaders.encodeAscii(httpRequest.method().toString(), byteBuf);
            byteBuf.writeByte(32);
            byteBuf.writeBytes(httpRequest.uri().getBytes(CharsetUtil.UTF_8));
            byteBuf.writeByte(32);
            HttpHeaders.encodeAscii(httpRequest.protocolVersion().toString(), byteBuf);
            byteBuf.writeBytes(a);
        } else if (httpMessage instanceof HttpResponse) {
            HttpResponse httpResponse = (HttpResponse) httpMessage;
            HttpHeaders.encodeAscii(httpResponse.protocolVersion().toString(), byteBuf);
            byteBuf.writeByte(32);
            byteBuf.writeBytes(String.valueOf(httpResponse.status().code()).getBytes(CharsetUtil.US_ASCII));
            byteBuf.writeByte(32);
            HttpHeaders.encodeAscii(String.valueOf(httpResponse.status().reasonPhrase()), byteBuf);
            byteBuf.writeBytes(a);
        } else {
            throw new UnsupportedMessageTypeException("Unsupported type " + StringUtil.simpleClassName(httpMessage));
        }
    }
}
