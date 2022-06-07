package io.netty.handler.codec.http;

@Deprecated
/* loaded from: classes4.dex */
public final class ClientCookieEncoder {
    @Deprecated
    public static String encode(String str, String str2) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(str, str2);
    }

    @Deprecated
    public static String encode(Cookie cookie) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(cookie);
    }

    @Deprecated
    public static String encode(Cookie... cookieArr) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(cookieArr);
    }

    @Deprecated
    public static String encode(Iterable<Cookie> iterable) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(iterable);
    }

    private ClientCookieEncoder() {
    }
}
