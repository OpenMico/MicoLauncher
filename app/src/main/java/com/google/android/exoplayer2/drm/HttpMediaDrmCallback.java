package com.google.android.exoplayer2.drm;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.upstream.DataSourceInputStream;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.eclipse.jetty.http.MimeTypes;

/* loaded from: classes2.dex */
public final class HttpMediaDrmCallback implements MediaDrmCallback {
    private final HttpDataSource.Factory a;
    @Nullable
    private final String b;
    private final boolean c;
    private final Map<String, String> d;

    public HttpMediaDrmCallback(@Nullable String str, HttpDataSource.Factory factory) {
        this(str, false, factory);
    }

    public HttpMediaDrmCallback(@Nullable String str, boolean z, HttpDataSource.Factory factory) {
        Assertions.checkArgument(!z || !TextUtils.isEmpty(str));
        this.a = factory;
        this.b = str;
        this.c = z;
        this.d = new HashMap();
    }

    public void setKeyRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        synchronized (this.d) {
            this.d.put(str, str2);
        }
    }

    public void clearKeyRequestProperty(String str) {
        Assertions.checkNotNull(str);
        synchronized (this.d) {
            this.d.remove(str);
        }
    }

    public void clearAllKeyRequestProperties() {
        synchronized (this.d) {
            this.d.clear();
        }
    }

    @Override // com.google.android.exoplayer2.drm.MediaDrmCallback
    public byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest provisionRequest) throws MediaDrmCallbackException {
        String defaultUrl = provisionRequest.getDefaultUrl();
        String fromUtf8Bytes = Util.fromUtf8Bytes(provisionRequest.getData());
        StringBuilder sb = new StringBuilder(String.valueOf(defaultUrl).length() + 15 + String.valueOf(fromUtf8Bytes).length());
        sb.append(defaultUrl);
        sb.append("&signedRequest=");
        sb.append(fromUtf8Bytes);
        return a(this.a, sb.toString(), null, Collections.emptyMap());
    }

    @Override // com.google.android.exoplayer2.drm.MediaDrmCallback
    public byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest keyRequest) throws MediaDrmCallbackException {
        String str;
        String licenseServerUrl = keyRequest.getLicenseServerUrl();
        if (this.c || TextUtils.isEmpty(licenseServerUrl)) {
            licenseServerUrl = this.b;
        }
        if (!TextUtils.isEmpty(licenseServerUrl)) {
            HashMap hashMap = new HashMap();
            if (C.PLAYREADY_UUID.equals(uuid)) {
                str = MimeTypes.TEXT_XML;
            } else {
                str = C.CLEARKEY_UUID.equals(uuid) ? "application/json" : "application/octet-stream";
            }
            hashMap.put("Content-Type", str);
            if (C.PLAYREADY_UUID.equals(uuid)) {
                hashMap.put("SOAPAction", "http://schemas.microsoft.com/DRM/2007/03/protocols/AcquireLicense");
            }
            synchronized (this.d) {
                hashMap.putAll(this.d);
            }
            return a(this.a, licenseServerUrl, keyRequest.getData(), hashMap);
        }
        throw new MediaDrmCallbackException(new DataSpec.Builder().setUri(Uri.EMPTY).build(), Uri.EMPTY, ImmutableMap.of(), 0L, new IllegalStateException("No license URL"));
    }

    private static byte[] a(HttpDataSource.Factory factory, String str, @Nullable byte[] bArr, Map<String, String> map) throws MediaDrmCallbackException {
        StatsDataSource statsDataSource = new StatsDataSource(factory.createDataSource());
        DataSpec build = new DataSpec.Builder().setUri(str).setHttpRequestHeaders(map).setHttpMethod(2).setHttpBody(bArr).setFlags(1).build();
        int i = 0;
        DataSpec dataSpec = build;
        while (true) {
            try {
                DataSourceInputStream dataSourceInputStream = new DataSourceInputStream(statsDataSource, dataSpec);
                try {
                    byte[] byteArray = Util.toByteArray(dataSourceInputStream);
                    Util.closeQuietly(dataSourceInputStream);
                    return byteArray;
                } catch (HttpDataSource.InvalidResponseCodeException e) {
                    String a = a(e, i);
                    if (a != null) {
                        i++;
                        dataSpec = dataSpec.buildUpon().setUri(a).build();
                        Util.closeQuietly(dataSourceInputStream);
                    } else {
                        throw e;
                    }
                }
            } catch (Exception e2) {
                throw new MediaDrmCallbackException(build, (Uri) Assertions.checkNotNull(statsDataSource.getLastOpenedUri()), statsDataSource.getResponseHeaders(), statsDataSource.getBytesRead(), e2);
            }
        }
    }

    @Nullable
    private static String a(HttpDataSource.InvalidResponseCodeException invalidResponseCodeException, int i) {
        Map<String, List<String>> map;
        List<String> list;
        if (((invalidResponseCodeException.responseCode == 307 || invalidResponseCodeException.responseCode == 308) && i < 5) && (map = invalidResponseCodeException.headerFields) != null && (list = map.get("Location")) != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
