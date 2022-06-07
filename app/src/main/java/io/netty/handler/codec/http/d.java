package io.netty.handler.codec.http;

import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.Map;

/* compiled from: HttpMessageUtil.java */
/* loaded from: classes4.dex */
final class d {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static StringBuilder a(StringBuilder sb, HttpRequest httpRequest) {
        a(sb, (HttpMessage) httpRequest);
        b(sb, httpRequest);
        a(sb, httpRequest.headers());
        a(sb);
        return sb;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static StringBuilder a(StringBuilder sb, HttpResponse httpResponse) {
        a(sb, (HttpMessage) httpResponse);
        b(sb, httpResponse);
        a(sb, httpResponse.headers());
        a(sb);
        return sb;
    }

    private static void a(StringBuilder sb, HttpMessage httpMessage) {
        sb.append(StringUtil.simpleClassName(httpMessage));
        sb.append("(decodeResult: ");
        sb.append(httpMessage.decoderResult());
        sb.append(", version: ");
        sb.append(httpMessage.protocolVersion());
        sb.append(')');
        sb.append(StringUtil.NEWLINE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static StringBuilder a(StringBuilder sb, FullHttpRequest fullHttpRequest) {
        a(sb, (FullHttpMessage) fullHttpRequest);
        b(sb, fullHttpRequest);
        a(sb, fullHttpRequest.headers());
        a(sb, fullHttpRequest.trailingHeaders());
        a(sb);
        return sb;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static StringBuilder a(StringBuilder sb, FullHttpResponse fullHttpResponse) {
        a(sb, (FullHttpMessage) fullHttpResponse);
        b(sb, fullHttpResponse);
        a(sb, fullHttpResponse.headers());
        a(sb, fullHttpResponse.trailingHeaders());
        a(sb);
        return sb;
    }

    private static void a(StringBuilder sb, FullHttpMessage fullHttpMessage) {
        sb.append(StringUtil.simpleClassName(fullHttpMessage));
        sb.append("(decodeResult: ");
        sb.append(fullHttpMessage.decoderResult());
        sb.append(", version: ");
        sb.append(fullHttpMessage.protocolVersion());
        sb.append(", content: ");
        sb.append(fullHttpMessage.content());
        sb.append(')');
        sb.append(StringUtil.NEWLINE);
    }

    private static void b(StringBuilder sb, HttpRequest httpRequest) {
        sb.append(httpRequest.method());
        sb.append(' ');
        sb.append(httpRequest.uri());
        sb.append(' ');
        sb.append(httpRequest.protocolVersion());
        sb.append(StringUtil.NEWLINE);
    }

    private static void b(StringBuilder sb, HttpResponse httpResponse) {
        sb.append(httpResponse.protocolVersion());
        sb.append(' ');
        sb.append(httpResponse.status());
        sb.append(StringUtil.NEWLINE);
    }

    private static void a(StringBuilder sb, HttpHeaders httpHeaders) {
        Iterator<Map.Entry<String, String>> it = httpHeaders.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            sb.append(next.getKey());
            sb.append(": ");
            sb.append(next.getValue());
            sb.append(StringUtil.NEWLINE);
        }
    }

    private static void a(StringBuilder sb) {
        sb.setLength(sb.length() - StringUtil.NEWLINE.length());
    }
}
