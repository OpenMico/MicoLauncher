package io.netty.handler.codec.http;

import com.zhy.http.okhttp.OkHttpUtils;
import io.netty.util.AsciiString;
import io.netty.util.internal.ObjectUtil;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.http.HttpMethods;

/* loaded from: classes4.dex */
public class HttpMethod implements Comparable<HttpMethod> {
    private final AsciiString b;
    public static final HttpMethod OPTIONS = new HttpMethod(HttpMethods.OPTIONS);
    public static final HttpMethod GET = new HttpMethod("GET");
    public static final HttpMethod HEAD = new HttpMethod("HEAD");
    public static final HttpMethod POST = new HttpMethod("POST");
    public static final HttpMethod PUT = new HttpMethod("PUT");
    public static final HttpMethod PATCH = new HttpMethod(OkHttpUtils.METHOD.PATCH);
    public static final HttpMethod DELETE = new HttpMethod("DELETE");
    public static final HttpMethod TRACE = new HttpMethod(HttpMethods.TRACE);
    public static final HttpMethod CONNECT = new HttpMethod(HttpMethods.CONNECT);
    private static final Map<String, HttpMethod> a = new HashMap();

    static {
        a.put(OPTIONS.toString(), OPTIONS);
        a.put(GET.toString(), GET);
        a.put(HEAD.toString(), HEAD);
        a.put(POST.toString(), POST);
        a.put(PUT.toString(), PUT);
        a.put(PATCH.toString(), PATCH);
        a.put(DELETE.toString(), DELETE);
        a.put(TRACE.toString(), TRACE);
        a.put(CONNECT.toString(), CONNECT);
    }

    public static HttpMethod valueOf(String str) {
        HttpMethod httpMethod = a.get(str);
        return httpMethod != null ? httpMethod : new HttpMethod(str);
    }

    public HttpMethod(String str) {
        String trim = ((String) ObjectUtil.checkNotNull(str, "name")).trim();
        if (!trim.isEmpty()) {
            for (int i = 0; i < trim.length(); i++) {
                char charAt = trim.charAt(i);
                if (Character.isISOControl(charAt) || Character.isWhitespace(charAt)) {
                    throw new IllegalArgumentException("invalid character in name");
                }
            }
            this.b = new AsciiString(trim);
            return;
        }
        throw new IllegalArgumentException("empty name");
    }

    public String name() {
        return this.b.toString();
    }

    public AsciiString asciiName() {
        return this.b;
    }

    public int hashCode() {
        return name().hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof HttpMethod)) {
            return false;
        }
        return name().equals(((HttpMethod) obj).name());
    }

    public String toString() {
        return this.b.toString();
    }

    public int compareTo(HttpMethod httpMethod) {
        return name().compareTo(httpMethod.name());
    }
}
