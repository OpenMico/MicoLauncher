package io.netty.handler.codec.http;

import io.netty.handler.codec.http.cookie.Cookie;
import java.util.Collection;
import java.util.List;

@Deprecated
/* loaded from: classes4.dex */
public final class ServerCookieEncoder {
    @Deprecated
    public static String encode(String str, String str2) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(str, str2);
    }

    @Deprecated
    public static String encode(Cookie cookie) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookie);
    }

    @Deprecated
    public static List<String> encode(Cookie... cookieArr) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookieArr);
    }

    @Deprecated
    public static List<String> encode(Collection<Cookie> collection) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode((Collection<? extends Cookie>) collection);
    }

    @Deprecated
    public static List<String> encode(Iterable<Cookie> iterable) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(iterable);
    }

    private ServerCookieEncoder() {
    }
}
