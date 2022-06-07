package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Predicate;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/* loaded from: classes2.dex */
public class DefaultHttpDataSource extends BaseDataSource implements HttpDataSource {
    public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 8000;
    public static final int DEFAULT_READ_TIMEOUT_MILLIS = 8000;
    private final boolean a;
    private final int b;
    private final int c;
    @Nullable
    private final String d;
    @Nullable
    private final HttpDataSource.RequestProperties e;
    private final HttpDataSource.RequestProperties f;
    private final boolean g;
    @Nullable
    private Predicate<String> h;
    @Nullable
    private DataSpec i;
    @Nullable
    private HttpURLConnection j;
    @Nullable
    private InputStream k;
    private boolean l;
    private int m;
    private long n;
    private long o;

    /* loaded from: classes2.dex */
    public static final class Factory implements HttpDataSource.Factory {
        @Nullable
        private TransferListener b;
        @Nullable
        private Predicate<String> c;
        @Nullable
        private String d;
        private boolean g;
        private boolean h;
        private final HttpDataSource.RequestProperties a = new HttpDataSource.RequestProperties();
        private int e = 8000;
        private int f = 8000;

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        @Deprecated
        public final HttpDataSource.RequestProperties getDefaultRequestProperties() {
            return this.a;
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        public final Factory setDefaultRequestProperties(Map<String, String> map) {
            this.a.clearAndSet(map);
            return this;
        }

        public Factory setUserAgent(@Nullable String str) {
            this.d = str;
            return this;
        }

        public Factory setConnectTimeoutMs(int i) {
            this.e = i;
            return this;
        }

        public Factory setReadTimeoutMs(int i) {
            this.f = i;
            return this;
        }

        public Factory setAllowCrossProtocolRedirects(boolean z) {
            this.g = z;
            return this;
        }

        public Factory setContentTypePredicate(@Nullable Predicate<String> predicate) {
            this.c = predicate;
            return this;
        }

        public Factory setTransferListener(@Nullable TransferListener transferListener) {
            this.b = transferListener;
            return this;
        }

        public Factory setKeepPostFor302Redirects(boolean z) {
            this.h = z;
            return this;
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory, com.google.android.exoplayer2.upstream.DataSource.Factory
        public DefaultHttpDataSource createDataSource() {
            DefaultHttpDataSource defaultHttpDataSource = new DefaultHttpDataSource(this.d, this.e, this.f, this.g, this.a, this.c, this.h);
            TransferListener transferListener = this.b;
            if (transferListener != null) {
                defaultHttpDataSource.addTransferListener(transferListener);
            }
            return defaultHttpDataSource;
        }
    }

    @Deprecated
    public DefaultHttpDataSource() {
        this(null, 8000, 8000);
    }

    @Deprecated
    public DefaultHttpDataSource(@Nullable String str) {
        this(str, 8000, 8000);
    }

    @Deprecated
    public DefaultHttpDataSource(@Nullable String str, int i, int i2) {
        this(str, i, i2, false, null);
    }

    @Deprecated
    public DefaultHttpDataSource(@Nullable String str, int i, int i2, boolean z, @Nullable HttpDataSource.RequestProperties requestProperties) {
        this(str, i, i2, z, requestProperties, null, false);
    }

    private DefaultHttpDataSource(@Nullable String str, int i, int i2, boolean z, @Nullable HttpDataSource.RequestProperties requestProperties, @Nullable Predicate<String> predicate, boolean z2) {
        super(true);
        this.d = str;
        this.b = i;
        this.c = i2;
        this.a = z;
        this.e = requestProperties;
        this.h = predicate;
        this.f = new HttpDataSource.RequestProperties();
        this.g = z2;
    }

    @Deprecated
    public void setContentTypePredicate(@Nullable Predicate<String> predicate) {
        this.h = predicate;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        HttpURLConnection httpURLConnection = this.j;
        if (httpURLConnection == null) {
            return null;
        }
        return Uri.parse(httpURLConnection.getURL().toString());
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public int getResponseCode() {
        int i;
        if (this.j == null || (i = this.m) <= 0) {
            return -1;
        }
        return i;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Map<String, List<String>> getResponseHeaders() {
        HttpURLConnection httpURLConnection = this.j;
        return httpURLConnection == null ? Collections.emptyMap() : httpURLConnection.getHeaderFields();
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void setRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        this.f.set(str, str2);
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void clearRequestProperty(String str) {
        Assertions.checkNotNull(str);
        this.f.remove(str);
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void clearAllRequestProperties() {
        this.f.clear();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws HttpDataSource.HttpDataSourceException {
        byte[] bArr;
        this.i = dataSpec;
        long j = 0;
        this.o = 0L;
        this.n = 0L;
        transferInitializing(dataSpec);
        try {
            this.j = a(dataSpec);
            HttpURLConnection httpURLConnection = this.j;
            this.m = httpURLConnection.getResponseCode();
            String responseMessage = httpURLConnection.getResponseMessage();
            int i = this.m;
            long j2 = -1;
            if (i < 200 || i > 299) {
                Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
                if (this.m == 416) {
                    if (dataSpec.position == HttpUtil.getDocumentSize(httpURLConnection.getHeaderField("Content-Range"))) {
                        this.l = true;
                        transferStarted(dataSpec);
                        if (dataSpec.length != -1) {
                            return dataSpec.length;
                        }
                        return 0L;
                    }
                }
                InputStream errorStream = httpURLConnection.getErrorStream();
                try {
                    bArr = errorStream != null ? Util.toByteArray(errorStream) : Util.EMPTY_BYTE_ARRAY;
                } catch (IOException unused) {
                    bArr = Util.EMPTY_BYTE_ARRAY;
                }
                a();
                throw new HttpDataSource.InvalidResponseCodeException(this.m, responseMessage, this.m == 416 ? new DataSourceException(2008) : null, headerFields, dataSpec, bArr);
            }
            String contentType = httpURLConnection.getContentType();
            Predicate<String> predicate = this.h;
            if (predicate == null || predicate.apply(contentType)) {
                if (this.m == 200 && dataSpec.position != 0) {
                    j = dataSpec.position;
                }
                boolean a = a(httpURLConnection);
                if (a) {
                    this.n = dataSpec.length;
                } else if (dataSpec.length != -1) {
                    this.n = dataSpec.length;
                } else {
                    long contentLength = HttpUtil.getContentLength(httpURLConnection.getHeaderField("Content-Length"), httpURLConnection.getHeaderField("Content-Range"));
                    if (contentLength != -1) {
                        j2 = contentLength - j;
                    }
                    this.n = j2;
                }
                try {
                    this.k = httpURLConnection.getInputStream();
                    if (a) {
                        this.k = new GZIPInputStream(this.k);
                    }
                    this.l = true;
                    transferStarted(dataSpec);
                    try {
                        a(j, dataSpec);
                        return this.n;
                    } catch (IOException e) {
                        a();
                        if (e instanceof HttpDataSource.HttpDataSourceException) {
                            throw ((HttpDataSource.HttpDataSourceException) e);
                        }
                        throw new HttpDataSource.HttpDataSourceException(e, dataSpec, 2000, 1);
                    }
                } catch (IOException e2) {
                    a();
                    throw new HttpDataSource.HttpDataSourceException(e2, dataSpec, 2000, 1);
                }
            } else {
                a();
                throw new HttpDataSource.InvalidContentTypeException(contentType, dataSpec);
            }
        } catch (IOException e3) {
            a();
            throw HttpDataSource.HttpDataSourceException.createForIOException(e3, dataSpec, 1);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws HttpDataSource.HttpDataSourceException {
        try {
            return a(bArr, i, i2);
        } catch (IOException e) {
            throw HttpDataSource.HttpDataSourceException.createForIOException(e, (DataSpec) Util.castNonNull(this.i), 2);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws HttpDataSource.HttpDataSourceException {
        try {
            InputStream inputStream = this.k;
            if (inputStream != null) {
                long j = -1;
                if (this.n != -1) {
                    j = this.n - this.o;
                }
                a(this.j, j);
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new HttpDataSource.HttpDataSourceException(e, (DataSpec) Util.castNonNull(this.i), 2000, 3);
                }
            }
        } finally {
            this.k = null;
            a();
            if (this.l) {
                this.l = false;
                transferEnded();
            }
        }
    }

    private HttpURLConnection a(DataSpec dataSpec) throws IOException {
        HttpURLConnection a;
        URL url = new URL(dataSpec.uri.toString());
        int i = dataSpec.httpMethod;
        byte[] bArr = dataSpec.httpBody;
        long j = dataSpec.position;
        long j2 = dataSpec.length;
        boolean isFlagSet = dataSpec.isFlagSet(1);
        if (!this.a && !this.g) {
            return a(url, i, bArr, j, j2, isFlagSet, true, dataSpec.httpRequestHeaders);
        }
        URL url2 = url;
        int i2 = i;
        byte[] bArr2 = bArr;
        int i3 = 0;
        while (true) {
            int i4 = i3 + 1;
            if (i3 <= 20) {
                a = a(url2, i2, bArr2, j, j2, isFlagSet, false, dataSpec.httpRequestHeaders);
                int responseCode = a.getResponseCode();
                String headerField = a.getHeaderField("Location");
                if ((i2 == 1 || i2 == 3) && (responseCode == 300 || responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == 307 || responseCode == 308)) {
                    a.disconnect();
                    url2 = a(url2, headerField, dataSpec);
                    i2 = i2;
                } else if (i2 != 2 || (responseCode != 300 && responseCode != 301 && responseCode != 302 && responseCode != 303)) {
                    break;
                } else {
                    a.disconnect();
                    if (!(this.g && responseCode == 302)) {
                        bArr2 = null;
                        i2 = 1;
                    } else {
                        i2 = i2;
                    }
                    url2 = a(url2, headerField, dataSpec);
                }
                i3 = i4;
                j = j;
                j2 = j2;
            } else {
                StringBuilder sb = new StringBuilder(31);
                sb.append("Too many redirects: ");
                sb.append(i4);
                throw new HttpDataSource.HttpDataSourceException(new NoRouteToHostException(sb.toString()), dataSpec, 2001, 1);
            }
        }
        return a;
    }

    private HttpURLConnection a(URL url, int i, @Nullable byte[] bArr, long j, long j2, boolean z, boolean z2, Map<String, String> map) throws IOException {
        HttpURLConnection a = a(url);
        a.setConnectTimeout(this.b);
        a.setReadTimeout(this.c);
        HashMap hashMap = new HashMap();
        HttpDataSource.RequestProperties requestProperties = this.e;
        if (requestProperties != null) {
            hashMap.putAll(requestProperties.getSnapshot());
        }
        hashMap.putAll(this.f.getSnapshot());
        hashMap.putAll(map);
        for (Map.Entry entry : hashMap.entrySet()) {
            a.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
        }
        String buildRangeRequestHeader = HttpUtil.buildRangeRequestHeader(j, j2);
        if (buildRangeRequestHeader != null) {
            a.setRequestProperty("Range", buildRangeRequestHeader);
        }
        String str = this.d;
        if (str != null) {
            a.setRequestProperty("User-Agent", str);
        }
        a.setRequestProperty("Accept-Encoding", z ? "gzip" : "identity");
        a.setInstanceFollowRedirects(z2);
        a.setDoOutput(bArr != null);
        a.setRequestMethod(DataSpec.getStringForHttpMethod(i));
        if (bArr != null) {
            a.setFixedLengthStreamingMode(bArr.length);
            a.connect();
            OutputStream outputStream = a.getOutputStream();
            outputStream.write(bArr);
            outputStream.close();
        } else {
            a.connect();
        }
        return a;
    }

    @VisibleForTesting
    HttpURLConnection a(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    private URL a(URL url, @Nullable String str, DataSpec dataSpec) throws HttpDataSource.HttpDataSourceException {
        if (str != null) {
            try {
                URL url2 = new URL(url, str);
                String protocol = url2.getProtocol();
                if (!"https".equals(protocol) && !"http".equals(protocol)) {
                    String valueOf = String.valueOf(protocol);
                    throw new HttpDataSource.HttpDataSourceException(valueOf.length() != 0 ? "Unsupported protocol redirect: ".concat(valueOf) : new String("Unsupported protocol redirect: "), dataSpec, 2001, 1);
                } else if (this.a || protocol.equals(url.getProtocol())) {
                    return url2;
                } else {
                    String protocol2 = url.getProtocol();
                    StringBuilder sb = new StringBuilder(String.valueOf(protocol2).length() + 41 + String.valueOf(protocol).length());
                    sb.append("Disallowed cross-protocol redirect (");
                    sb.append(protocol2);
                    sb.append(" to ");
                    sb.append(protocol);
                    sb.append(")");
                    throw new HttpDataSource.HttpDataSourceException(sb.toString(), dataSpec, 2001, 1);
                }
            } catch (MalformedURLException e) {
                throw new HttpDataSource.HttpDataSourceException(e, dataSpec, 2001, 1);
            }
        } else {
            throw new HttpDataSource.HttpDataSourceException("Null location redirect", dataSpec, 2001, 1);
        }
    }

    private void a(long j, DataSpec dataSpec) throws IOException {
        if (j != 0) {
            byte[] bArr = new byte[4096];
            while (j > 0) {
                int read = ((InputStream) Util.castNonNull(this.k)).read(bArr, 0, (int) Math.min(j, bArr.length));
                if (Thread.currentThread().isInterrupted()) {
                    throw new HttpDataSource.HttpDataSourceException(new InterruptedIOException(), dataSpec, 2000, 1);
                } else if (read != -1) {
                    j -= read;
                    bytesTransferred(read);
                } else {
                    throw new HttpDataSource.HttpDataSourceException(dataSpec, 2008, 1);
                }
            }
        }
    }

    private int a(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        long j = this.n;
        if (j != -1) {
            long j2 = j - this.o;
            if (j2 == 0) {
                return -1;
            }
            i2 = (int) Math.min(i2, j2);
        }
        int read = ((InputStream) Util.castNonNull(this.k)).read(bArr, i, i2);
        if (read == -1) {
            return -1;
        }
        this.o += read;
        bytesTransferred(read);
        return read;
    }

    private static void a(@Nullable HttpURLConnection httpURLConnection, long j) {
        if (httpURLConnection != null && Util.SDK_INT >= 19 && Util.SDK_INT <= 20) {
            try {
                InputStream inputStream = httpURLConnection.getInputStream();
                if (j == -1) {
                    if (inputStream.read() == -1) {
                        return;
                    }
                } else if (j <= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) {
                    return;
                }
                String name = inputStream.getClass().getName();
                if ("com.android.okhttp.internal.http.HttpTransport$ChunkedInputStream".equals(name) || "com.android.okhttp.internal.http.HttpTransport$FixedLengthInputStream".equals(name)) {
                    Method declaredMethod = ((Class) Assertions.checkNotNull(inputStream.getClass().getSuperclass())).getDeclaredMethod("unexpectedEndOfInput", new Class[0]);
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(inputStream, new Object[0]);
                }
            } catch (Exception unused) {
            }
        }
    }

    private void a() {
        HttpURLConnection httpURLConnection = this.j;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (Exception e) {
                Log.e("DefaultHttpDataSource", "Unexpected error while disconnecting", e);
            }
            this.j = null;
        }
    }

    private static boolean a(HttpURLConnection httpURLConnection) {
        return "gzip".equalsIgnoreCase(httpURLConnection.getHeaderField("Content-Encoding"));
    }
}
