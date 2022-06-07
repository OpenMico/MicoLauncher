package io.netty.handler.codec.http;

import com.fasterxml.jackson.core.JsonPointer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;

/* loaded from: classes4.dex */
public class HttpRequestEncoder extends HttpObjectEncoder<HttpRequest> {
    private static final byte[] a = {13, 10};

    @Override // io.netty.handler.codec.http.HttpObjectEncoder, io.netty.handler.codec.MessageToMessageEncoder
    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return super.acceptOutboundMessage(obj) && !(obj instanceof HttpResponse);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encodeInitialLine(ByteBuf byteBuf, HttpRequest httpRequest) throws Exception {
        AsciiString asciiName = httpRequest.method().asciiName();
        ByteBufUtil.copy(asciiName, asciiName.arrayOffset(), byteBuf, asciiName.length());
        byteBuf.writeByte(32);
        String uri = httpRequest.uri();
        if (uri.length() == 0) {
            uri = uri + JsonPointer.SEPARATOR;
        } else {
            int indexOf = uri.indexOf("://");
            if (!(indexOf == -1 || uri.charAt(0) == '/')) {
                int i = indexOf + 3;
                int indexOf2 = uri.indexOf(63, i);
                if (indexOf2 == -1) {
                    if (uri.lastIndexOf(47) <= i) {
                        uri = uri + JsonPointer.SEPARATOR;
                    }
                } else if (uri.lastIndexOf(47, indexOf2) <= i) {
                    int length = uri.length();
                    StringBuilder sb = new StringBuilder(length + 1);
                    sb.append((CharSequence) uri, 0, indexOf2);
                    sb.append(JsonPointer.SEPARATOR);
                    sb.append((CharSequence) uri, indexOf2, length);
                    uri = sb.toString();
                }
            }
        }
        byteBuf.writeBytes(uri.getBytes(CharsetUtil.UTF_8));
        byteBuf.writeByte(32);
        httpRequest.protocolVersion().a(byteBuf);
        byteBuf.writeBytes(a);
    }
}
