package io.netty.handler.codec.http;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import kotlin.text.Typography;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public class QueryStringEncoder {
    private final Charset a;
    private final String b;
    private final List<a> c;

    public QueryStringEncoder(String str) {
        this(str, HttpConstants.DEFAULT_CHARSET);
    }

    public QueryStringEncoder(String str, Charset charset) {
        this.c = new ArrayList();
        if (str == null) {
            throw new NullPointerException("getUri");
        } else if (charset != null) {
            this.b = str;
            this.a = charset;
        } else {
            throw new NullPointerException("charset");
        }
    }

    public void addParam(String str, String str2) {
        if (str != null) {
            this.c.add(new a(str, str2));
            return;
        }
        throw new NullPointerException("name");
    }

    public URI toUri() throws URISyntaxException {
        return new URI(toString());
    }

    public String toString() {
        if (this.c.isEmpty()) {
            return this.b;
        }
        StringBuilder sb = new StringBuilder(this.b);
        sb.append('?');
        for (int i = 0; i < this.c.size(); i++) {
            a aVar = this.c.get(i);
            sb.append(a(aVar.a, this.a));
            if (aVar.b != null) {
                sb.append('=');
                sb.append(a(aVar.b, this.a));
            }
            if (i != this.c.size() - 1) {
                sb.append(Typography.amp);
            }
        }
        return sb.toString();
    }

    private static String a(String str, Charset charset) {
        try {
            return URLEncoder.encode(str, charset.name()).replace(Marker.ANY_NON_NULL_MARKER, "%20");
        } catch (UnsupportedEncodingException unused) {
            throw new UnsupportedCharsetException(charset.name());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a {
        final String a;
        final String b;

        a(String str, String str2) {
            this.b = str2;
            this.a = str;
        }
    }
}
