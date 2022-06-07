package com.bumptech.glide.load.model;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.util.Preconditions;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Map;

/* loaded from: classes.dex */
public class GlideUrl implements Key {
    private final Headers a;
    @Nullable
    private final URL b;
    @Nullable
    private final String c;
    @Nullable
    private String d;
    @Nullable
    private URL e;
    @Nullable
    private volatile byte[] f;
    private int g;

    public GlideUrl(URL url) {
        this(url, Headers.DEFAULT);
    }

    public GlideUrl(String str) {
        this(str, Headers.DEFAULT);
    }

    public GlideUrl(URL url, Headers headers) {
        this.b = (URL) Preconditions.checkNotNull(url);
        this.c = null;
        this.a = (Headers) Preconditions.checkNotNull(headers);
    }

    public GlideUrl(String str, Headers headers) {
        this.b = null;
        this.c = Preconditions.checkNotEmpty(str);
        this.a = (Headers) Preconditions.checkNotNull(headers);
    }

    public URL toURL() throws MalformedURLException {
        return a();
    }

    private URL a() throws MalformedURLException {
        if (this.e == null) {
            this.e = new URL(b());
        }
        return this.e;
    }

    public String toStringUrl() {
        return b();
    }

    private String b() {
        if (TextUtils.isEmpty(this.d)) {
            String str = this.c;
            if (TextUtils.isEmpty(str)) {
                str = ((URL) Preconditions.checkNotNull(this.b)).toString();
            }
            this.d = Uri.encode(str, "@#&=*+-_.,:!?()/~'%;$");
        }
        return this.d;
    }

    public Map<String, String> getHeaders() {
        return this.a.getHeaders();
    }

    public String getCacheKey() {
        String str = this.c;
        return str != null ? str : ((URL) Preconditions.checkNotNull(this.b)).toString();
    }

    public String toString() {
        return getCacheKey();
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(c());
    }

    private byte[] c() {
        if (this.f == null) {
            this.f = getCacheKey().getBytes(CHARSET);
        }
        return this.f;
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (!(obj instanceof GlideUrl)) {
            return false;
        }
        GlideUrl glideUrl = (GlideUrl) obj;
        return getCacheKey().equals(glideUrl.getCacheKey()) && this.a.equals(glideUrl.a);
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        if (this.g == 0) {
            this.g = getCacheKey().hashCode();
            this.g = (this.g * 31) + this.a.hashCode();
        }
        return this.g;
    }
}
