package com.xiaomi.micolauncher.api.interceptor;

import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.utils.ByteArrayPool;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class LoggingInterceptor implements Interceptor {
    private static final Charset a = Charset.forName("UTF-8");
    private static int b = 1;
    private final Logger d;
    private ByteArrayPool c = new ByteArrayPool(65536);
    private final boolean e = false;

    public LoggingInterceptor(Logger logger) {
        this.d = logger;
    }

    private static synchronized int a() {
        int i;
        synchronized (LoggingInterceptor.class) {
            b++;
            if (b == Integer.MAX_VALUE) {
                b = 1;
            }
            i = b;
        }
        return i;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        String str;
        Charset charset;
        Request request = chain.request();
        String str2 = "#" + a() + StringUtils.SPACE;
        RequestBody body = request.body();
        this.d.i(str2 + request.method() + ' ' + request.url());
        if (TextUtils.isEmpty(request.header("Not-Log-Request-Body")) && body != null && !a(request.headers())) {
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            Charset charset2 = a;
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset2 = contentType.charset(a);
            }
            if (a(buffer)) {
                this.d.i(str2 + buffer.readString(charset2));
            }
        }
        long nanoTime = System.nanoTime();
        try {
            Response proceed = chain.proceed(request);
            long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime);
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(proceed.code());
            if (!proceed.isSuccessful()) {
                str = ' ' + proceed.message();
            } else {
                str = "";
            }
            sb.append(str);
            sb.append(" (");
            sb.append(millis);
            sb.append("ms)");
            String sb2 = sb.toString();
            DebugHelper.isDebugVersion();
            if (TextUtils.isEmpty(request.header("Not-Log-Response-Body")) && HttpHeaders.hasBody(proceed) && !a(proceed.headers())) {
                ResponseBody body2 = proceed.body();
                BufferedSource source = body2.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer2 = source.buffer();
                MediaType contentType2 = body2.contentType();
                if (contentType2 != null) {
                    charset = contentType2.charset(a);
                } else {
                    charset = a;
                }
                if (a(buffer2) && body2.contentLength() != 0) {
                    Buffer clone = buffer2.clone();
                    int min = (int) Math.min(clone.size(), (long) PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM);
                    byte[] buf = this.c.getBuf(min);
                    clone.read(buf);
                    String str3 = sb2 + StringUtils.SPACE;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str3);
                    sb3.append(charset == null ? new String(buf, 0, min) : new String(buf, 0, min, charset));
                    sb2 = sb3.toString();
                    this.c.returnBuf(buf);
                }
            }
            this.d.i(sb2);
            return proceed;
        } catch (Exception e) {
            this.d.e(str2 + "HTTP FAILED: " + e, e);
            throw e;
        }
    }

    private static boolean a(Buffer buffer) {
        try {
            Buffer buffer2 = new Buffer();
            buffer.copyTo(buffer2, 0L, buffer.size() < 64 ? buffer.size() : 64L);
            for (int i = 0; i < 16; i++) {
                if (buffer2.exhausted()) {
                    return true;
                }
                int readUtf8CodePoint = buffer2.readUtf8CodePoint();
                if (Character.isISOControl(readUtf8CodePoint) && !Character.isWhitespace(readUtf8CodePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException unused) {
            return false;
        }
    }

    private static boolean a(Headers headers) {
        String str = headers.get("Content-Encoding");
        return str != null && !str.equalsIgnoreCase("identity");
    }
}
