package retrofit2;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RequestBuilder.java */
/* loaded from: classes6.dex */
public final class i {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final Pattern b = Pattern.compile("(.*/)?(\\.|%2e|%2E){1,2}(/.*)?");
    private final String c;
    private final HttpUrl d;
    @Nullable
    private String e;
    @Nullable
    private HttpUrl.Builder f;
    private final Request.Builder g = new Request.Builder();
    private final Headers.Builder h;
    @Nullable
    private MediaType i;
    private final boolean j;
    @Nullable
    private MultipartBody.Builder k;
    @Nullable
    private FormBody.Builder l;
    @Nullable
    private RequestBody m;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(String str, HttpUrl httpUrl, @Nullable String str2, @Nullable Headers headers, @Nullable MediaType mediaType, boolean z, boolean z2, boolean z3) {
        this.c = str;
        this.d = httpUrl;
        this.e = str2;
        this.i = mediaType;
        this.j = z;
        if (headers != null) {
            this.h = headers.newBuilder();
        } else {
            this.h = new Headers.Builder();
        }
        if (z2) {
            this.l = new FormBody.Builder();
        } else if (z3) {
            this.k = new MultipartBody.Builder();
            this.k.setType(MultipartBody.FORM);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Object obj) {
        this.e = obj.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(String str, String str2) {
        if ("Content-Type".equalsIgnoreCase(str)) {
            try {
                this.i = MediaType.get(str2);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Malformed content type: " + str2, e);
            }
        } else {
            this.h.add(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Headers headers) {
        this.h.addAll(headers);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(String str, String str2, boolean z) {
        if (this.e != null) {
            String a2 = a(str2, z);
            String str3 = this.e;
            String replace = str3.replace("{" + str + "}", a2);
            if (!b.matcher(replace).matches()) {
                this.e = replace;
                return;
            }
            throw new IllegalArgumentException("@Path parameters shouldn't perform path traversal ('.' or '..'): " + str2);
        }
        throw new AssertionError();
    }

    private static String a(String str, boolean z) {
        int length = str.length();
        int i = 0;
        while (i < length) {
            int codePointAt = str.codePointAt(i);
            if (codePointAt < 32 || codePointAt >= 127 || " \"<>^`{}|\\?#".indexOf(codePointAt) != -1 || (!z && (codePointAt == 47 || codePointAt == 37))) {
                Buffer buffer = new Buffer();
                buffer.writeUtf8(str, 0, i);
                a(buffer, str, i, length, z);
                return buffer.readUtf8();
            }
            i += Character.charCount(codePointAt);
        }
        return str;
    }

    private static void a(Buffer buffer, String str, int i, int i2, boolean z) {
        Buffer buffer2 = null;
        while (i < i2) {
            int codePointAt = str.codePointAt(i);
            if (!z || !(codePointAt == 9 || codePointAt == 10 || codePointAt == 12 || codePointAt == 13)) {
                if (codePointAt < 32 || codePointAt >= 127 || " \"<>^`{}|\\?#".indexOf(codePointAt) != -1 || (!z && (codePointAt == 47 || codePointAt == 37))) {
                    if (buffer2 == null) {
                        buffer2 = new Buffer();
                    }
                    buffer2.writeUtf8CodePoint(codePointAt);
                    while (!buffer2.exhausted()) {
                        int readByte = buffer2.readByte() & 255;
                        buffer.writeByte(37);
                        buffer.writeByte((int) a[(readByte >> 4) & 15]);
                        buffer.writeByte((int) a[readByte & 15]);
                    }
                } else {
                    buffer.writeUtf8CodePoint(codePointAt);
                }
            }
            i += Character.charCount(codePointAt);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(String str, @Nullable String str2, boolean z) {
        String str3 = this.e;
        if (str3 != null) {
            this.f = this.d.newBuilder(str3);
            if (this.f != null) {
                this.e = null;
            } else {
                throw new IllegalArgumentException("Malformed URL. Base: " + this.d + ", Relative: " + this.e);
            }
        }
        if (z) {
            this.f.addEncodedQueryParameter(str, str2);
        } else {
            this.f.addQueryParameter(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(String str, String str2, boolean z) {
        if (z) {
            this.l.addEncoded(str, str2);
        } else {
            this.l.add(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Headers headers, RequestBody requestBody) {
        this.k.addPart(headers, requestBody);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(MultipartBody.Part part) {
        this.k.addPart(part);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(RequestBody requestBody) {
        this.m = requestBody;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public <T> void a(Class<T> cls, @Nullable T t) {
        this.g.tag(cls, t);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Request.Builder a() {
        HttpUrl httpUrl;
        HttpUrl.Builder builder = this.f;
        if (builder != null) {
            httpUrl = builder.build();
        } else {
            httpUrl = this.d.resolve(this.e);
            if (httpUrl == null) {
                throw new IllegalArgumentException("Malformed URL. Base: " + this.d + ", Relative: " + this.e);
            }
        }
        a aVar = this.m;
        if (aVar == null) {
            FormBody.Builder builder2 = this.l;
            if (builder2 != null) {
                aVar = builder2.build();
            } else {
                MultipartBody.Builder builder3 = this.k;
                if (builder3 != null) {
                    aVar = builder3.build();
                } else if (this.j) {
                    aVar = RequestBody.create((MediaType) null, new byte[0]);
                }
            }
        }
        MediaType mediaType = this.i;
        if (mediaType != null) {
            if (aVar != null) {
                aVar = new a(aVar, mediaType);
            } else {
                this.h.add("Content-Type", mediaType.toString());
            }
        }
        return this.g.url(httpUrl).headers(this.h.build()).method(this.c, aVar);
    }

    /* compiled from: RequestBuilder.java */
    /* loaded from: classes6.dex */
    private static class a extends RequestBody {
        private final RequestBody a;
        private final MediaType b;

        a(RequestBody requestBody, MediaType mediaType) {
            this.a = requestBody;
            this.b = mediaType;
        }

        @Override // okhttp3.RequestBody
        public MediaType contentType() {
            return this.b;
        }

        @Override // okhttp3.RequestBody
        public long contentLength() throws IOException {
            return this.a.contentLength();
        }

        @Override // okhttp3.RequestBody
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            this.a.writeTo(bufferedSink);
        }
    }
}
