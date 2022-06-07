package io.netty.handler.codec.http;

import com.fasterxml.jackson.core.JsonPointer;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jetty.http.HttpVersions;

/* loaded from: classes4.dex */
public class HttpVersion implements Comparable<HttpVersion> {
    private final String b;
    private final int c;
    private final int d;
    private final String e;
    private final boolean f;
    private final byte[] g;
    private static final Pattern a = Pattern.compile("(\\S+)/(\\d+)\\.(\\d+)");
    public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP", 1, 0, false, true);
    public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP", 1, 1, true, true);

    public static HttpVersion valueOf(String str) {
        if (str != null) {
            String trim = str.trim();
            if (!trim.isEmpty()) {
                HttpVersion a2 = a(trim);
                return a2 == null ? new HttpVersion(trim, true) : a2;
            }
            throw new IllegalArgumentException("text is empty");
        }
        throw new NullPointerException("text");
    }

    private static HttpVersion a(String str) {
        if (HttpVersions.HTTP_1_1.equals(str)) {
            return HTTP_1_1;
        }
        if (HttpVersions.HTTP_1_0.equals(str)) {
            return HTTP_1_0;
        }
        return null;
    }

    public HttpVersion(String str, boolean z) {
        if (str != null) {
            String upperCase = str.trim().toUpperCase();
            if (!upperCase.isEmpty()) {
                Matcher matcher = a.matcher(upperCase);
                if (matcher.matches()) {
                    this.b = matcher.group(1);
                    this.c = Integer.parseInt(matcher.group(2));
                    this.d = Integer.parseInt(matcher.group(3));
                    this.e = this.b + JsonPointer.SEPARATOR + this.c + '.' + this.d;
                    this.f = z;
                    this.g = null;
                    return;
                }
                throw new IllegalArgumentException("invalid version format: " + upperCase);
            }
            throw new IllegalArgumentException("empty text");
        }
        throw new NullPointerException("text");
    }

    public HttpVersion(String str, int i, int i2, boolean z) {
        this(str, i, i2, z, false);
    }

    private HttpVersion(String str, int i, int i2, boolean z, boolean z2) {
        if (str != null) {
            String upperCase = str.trim().toUpperCase();
            if (!upperCase.isEmpty()) {
                for (int i3 = 0; i3 < upperCase.length(); i3++) {
                    if (Character.isISOControl(upperCase.charAt(i3)) || Character.isWhitespace(upperCase.charAt(i3))) {
                        throw new IllegalArgumentException("invalid character in protocolName");
                    }
                }
                if (i < 0) {
                    throw new IllegalArgumentException("negative majorVersion");
                } else if (i2 >= 0) {
                    this.b = upperCase;
                    this.c = i;
                    this.d = i2;
                    this.e = upperCase + JsonPointer.SEPARATOR + i + '.' + i2;
                    this.f = z;
                    if (z2) {
                        this.g = this.e.getBytes(CharsetUtil.US_ASCII);
                    } else {
                        this.g = null;
                    }
                } else {
                    throw new IllegalArgumentException("negative minorVersion");
                }
            } else {
                throw new IllegalArgumentException("empty protocolName");
            }
        } else {
            throw new NullPointerException("protocolName");
        }
    }

    public String protocolName() {
        return this.b;
    }

    public int majorVersion() {
        return this.c;
    }

    public int minorVersion() {
        return this.d;
    }

    public String text() {
        return this.e;
    }

    public boolean isKeepAliveDefault() {
        return this.f;
    }

    public String toString() {
        return text();
    }

    public int hashCode() {
        return (((protocolName().hashCode() * 31) + majorVersion()) * 31) + minorVersion();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof HttpVersion)) {
            return false;
        }
        HttpVersion httpVersion = (HttpVersion) obj;
        return minorVersion() == httpVersion.minorVersion() && majorVersion() == httpVersion.majorVersion() && protocolName().equals(httpVersion.protocolName());
    }

    public int compareTo(HttpVersion httpVersion) {
        int compareTo = protocolName().compareTo(httpVersion.protocolName());
        if (compareTo != 0) {
            return compareTo;
        }
        int majorVersion = majorVersion() - httpVersion.majorVersion();
        return majorVersion != 0 ? majorVersion : minorVersion() - httpVersion.minorVersion();
    }

    public void a(ByteBuf byteBuf) {
        byte[] bArr = this.g;
        if (bArr == null) {
            HttpUtil.a(this.e, byteBuf);
        } else {
            byteBuf.writeBytes(bArr);
        }
    }
}
