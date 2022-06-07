package io.netty.handler.codec.http;

/* loaded from: classes4.dex */
public interface HttpMessage extends HttpObject {
    @Deprecated
    HttpVersion getProtocolVersion();

    HttpHeaders headers();

    HttpVersion protocolVersion();

    HttpMessage setProtocolVersion(HttpVersion httpVersion);
}
