package com.bumptech.glide.load.data;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.bumptech.glide.util.LogTime;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/* loaded from: classes.dex */
public class HttpUrlFetcher implements DataFetcher<InputStream> {
    @VisibleForTesting
    static final b a = new a();
    private final GlideUrl b;
    private final int c;
    private final b d;
    private HttpURLConnection e;
    private InputStream f;
    private volatile boolean g;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface b {
        HttpURLConnection a(URL url) throws IOException;
    }

    public HttpUrlFetcher(GlideUrl glideUrl, int i) {
        this(glideUrl, i, a);
    }

    @VisibleForTesting
    HttpUrlFetcher(GlideUrl glideUrl, int i, b bVar) {
        this.b = glideUrl;
        this.c = i;
        this.d = bVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v5 */
    @Override // com.bumptech.glide.load.data.DataFetcher
    public void loadData(@NonNull Priority priority, @NonNull DataFetcher.DataCallback<? super InputStream> dataCallback) {
        long logTime;
        StringBuilder sb;
        String str;
        try {
            logTime = LogTime.getLogTime();
            priority = 2;
            priority = 2;
            try {
                dataCallback.onDataReady(a(this.b.toURL(), 0, null, this.b.getHeaders()));
            } catch (IOException e) {
                if (Log.isLoggable("HttpUrlFetcher", 3)) {
                    Log.d("HttpUrlFetcher", "Failed to load data for url", e);
                }
                dataCallback.onLoadFailed(e);
                if (Log.isLoggable("HttpUrlFetcher", 2)) {
                    str = "HttpUrlFetcher";
                    sb = new StringBuilder();
                } else {
                    return;
                }
            }
            if (Log.isLoggable("HttpUrlFetcher", 2)) {
                str = "HttpUrlFetcher";
                sb = new StringBuilder();
                sb.append("Finished http url fetcher fetch in ");
                sb.append(LogTime.getElapsedMillis(logTime));
                Log.v(str, sb.toString());
            }
        } catch (Throwable th) {
            if (Log.isLoggable("HttpUrlFetcher", priority == true ? 1 : 0)) {
                Log.v("HttpUrlFetcher", "Finished http url fetcher fetch in " + LogTime.getElapsedMillis(logTime));
            }
            throw th;
        }
    }

    private InputStream a(URL url, int i, URL url2, Map<String, String> map) throws HttpException {
        if (i < 5) {
            if (url2 != null) {
                try {
                    if (url.toURI().equals(url2.toURI())) {
                        throw new HttpException("In re-direct loop", -1);
                    }
                } catch (URISyntaxException unused) {
                }
            }
            this.e = a(url, map);
            try {
                this.e.connect();
                this.f = this.e.getInputStream();
                if (this.g) {
                    return null;
                }
                int a2 = a(this.e);
                if (a(a2)) {
                    return b(this.e);
                }
                if (b(a2)) {
                    String headerField = this.e.getHeaderField("Location");
                    if (!TextUtils.isEmpty(headerField)) {
                        try {
                            URL url3 = new URL(url, headerField);
                            cleanup();
                            return a(url3, i + 1, url, map);
                        } catch (MalformedURLException e) {
                            throw new HttpException("Bad redirect url: " + headerField, a2, e);
                        }
                    } else {
                        throw new HttpException("Received empty or null redirect url", a2);
                    }
                } else if (a2 == -1) {
                    throw new HttpException(a2);
                } else {
                    try {
                        throw new HttpException(this.e.getResponseMessage(), a2);
                    } catch (IOException e2) {
                        throw new HttpException("Failed to get a response message", a2, e2);
                    }
                }
            } catch (IOException e3) {
                throw new HttpException("Failed to connect or obtain data", a(this.e), e3);
            }
        } else {
            throw new HttpException("Too many (> 5) redirects!", -1);
        }
    }

    private static int a(HttpURLConnection httpURLConnection) {
        try {
            return httpURLConnection.getResponseCode();
        } catch (IOException e) {
            if (!Log.isLoggable("HttpUrlFetcher", 3)) {
                return -1;
            }
            Log.d("HttpUrlFetcher", "Failed to get a response code", e);
            return -1;
        }
    }

    private HttpURLConnection a(URL url, Map<String, String> map) throws HttpException {
        try {
            HttpURLConnection a2 = this.d.a(url);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                a2.addRequestProperty(entry.getKey(), entry.getValue());
            }
            a2.setConnectTimeout(this.c);
            a2.setReadTimeout(this.c);
            a2.setUseCaches(false);
            a2.setDoInput(true);
            a2.setInstanceFollowRedirects(false);
            return a2;
        } catch (IOException e) {
            throw new HttpException("URL.openConnection threw", 0, e);
        }
    }

    private static boolean a(int i) {
        return i / 100 == 2;
    }

    private static boolean b(int i) {
        return i / 100 == 3;
    }

    private InputStream b(HttpURLConnection httpURLConnection) throws HttpException {
        try {
            if (TextUtils.isEmpty(httpURLConnection.getContentEncoding())) {
                this.f = ContentLengthInputStream.obtain(httpURLConnection.getInputStream(), httpURLConnection.getContentLength());
            } else {
                if (Log.isLoggable("HttpUrlFetcher", 3)) {
                    Log.d("HttpUrlFetcher", "Got non empty content encoding: " + httpURLConnection.getContentEncoding());
                }
                this.f = httpURLConnection.getInputStream();
            }
            return this.f;
        } catch (IOException e) {
            throw new HttpException("Failed to obtain InputStream", a(httpURLConnection), e);
        }
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cleanup() {
        InputStream inputStream = this.f;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
        HttpURLConnection httpURLConnection = this.e;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        this.e = null;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cancel() {
        this.g = true;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    @NonNull
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    @NonNull
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }

    /* loaded from: classes.dex */
    private static class a implements b {
        a() {
        }

        @Override // com.bumptech.glide.load.data.HttpUrlFetcher.b
        public HttpURLConnection a(URL url) throws IOException {
            return (HttpURLConnection) url.openConnection();
        }
    }
}
