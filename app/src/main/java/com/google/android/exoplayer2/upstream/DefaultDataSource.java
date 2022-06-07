package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class DefaultDataSource implements DataSource {
    private final Context a;
    private final List<TransferListener> b;
    private final DataSource c;
    @Nullable
    private DataSource d;
    @Nullable
    private DataSource e;
    @Nullable
    private DataSource f;
    @Nullable
    private DataSource g;
    @Nullable
    private DataSource h;
    @Nullable
    private DataSource i;
    @Nullable
    private DataSource j;
    @Nullable
    private DataSource k;

    public DefaultDataSource(Context context, boolean z) {
        this(context, null, 8000, 8000, z);
    }

    public DefaultDataSource(Context context, @Nullable String str, boolean z) {
        this(context, str, 8000, 8000, z);
    }

    public DefaultDataSource(Context context, @Nullable String str, int i, int i2, boolean z) {
        this(context, new DefaultHttpDataSource.Factory().setUserAgent(str).setConnectTimeoutMs(i).setReadTimeoutMs(i2).setAllowCrossProtocolRedirects(z).createDataSource());
    }

    public DefaultDataSource(Context context, DataSource dataSource) {
        this.a = context.getApplicationContext();
        this.c = (DataSource) Assertions.checkNotNull(dataSource);
        this.b = new ArrayList();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.c.addTransferListener(transferListener);
        this.b.add(transferListener);
        a(this.d, transferListener);
        a(this.e, transferListener);
        a(this.f, transferListener);
        a(this.g, transferListener);
        a(this.h, transferListener);
        a(this.i, transferListener);
        a(this.j, transferListener);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        Assertions.checkState(this.k == null);
        String scheme = dataSpec.uri.getScheme();
        if (Util.isLocalFileUri(dataSpec.uri)) {
            String path = dataSpec.uri.getPath();
            if (path == null || !path.startsWith("/android_asset/")) {
                this.k = b();
            } else {
                this.k = c();
            }
        } else if ("asset".equals(scheme)) {
            this.k = c();
        } else if ("content".equals(scheme)) {
            this.k = d();
        } else if ("rtmp".equals(scheme)) {
            this.k = e();
        } else if ("udp".equals(scheme)) {
            this.k = a();
        } else if ("data".equals(scheme)) {
            this.k = f();
        } else if (RawResourceDataSource.RAW_RESOURCE_SCHEME.equals(scheme) || "android.resource".equals(scheme)) {
            this.k = g();
        } else {
            this.k = this.c;
        }
        return this.k.open(dataSpec);
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws IOException {
        return ((DataSource) Assertions.checkNotNull(this.k)).read(bArr, i, i2);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        DataSource dataSource = this.k;
        if (dataSource == null) {
            return null;
        }
        return dataSource.getUri();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Map<String, List<String>> getResponseHeaders() {
        DataSource dataSource = this.k;
        return dataSource == null ? Collections.emptyMap() : dataSource.getResponseHeaders();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws IOException {
        DataSource dataSource = this.k;
        if (dataSource != null) {
            try {
                dataSource.close();
            } finally {
                this.k = null;
            }
        }
    }

    private DataSource a() {
        if (this.h == null) {
            this.h = new UdpDataSource();
            a(this.h);
        }
        return this.h;
    }

    private DataSource b() {
        if (this.d == null) {
            this.d = new FileDataSource();
            a(this.d);
        }
        return this.d;
    }

    private DataSource c() {
        if (this.e == null) {
            this.e = new AssetDataSource(this.a);
            a(this.e);
        }
        return this.e;
    }

    private DataSource d() {
        if (this.f == null) {
            this.f = new ContentDataSource(this.a);
            a(this.f);
        }
        return this.f;
    }

    private DataSource e() {
        if (this.g == null) {
            try {
                this.g = (DataSource) Class.forName("com.google.android.exoplayer2.ext.rtmp.RtmpDataSource").getConstructor(new Class[0]).newInstance(new Object[0]);
                a(this.g);
            } catch (ClassNotFoundException unused) {
                Log.w("DefaultDataSource", "Attempting to play RTMP stream without depending on the RTMP extension");
            } catch (Exception e) {
                throw new RuntimeException("Error instantiating RTMP extension", e);
            }
            if (this.g == null) {
                this.g = this.c;
            }
        }
        return this.g;
    }

    private DataSource f() {
        if (this.i == null) {
            this.i = new DataSchemeDataSource();
            a(this.i);
        }
        return this.i;
    }

    private DataSource g() {
        if (this.j == null) {
            this.j = new RawResourceDataSource(this.a);
            a(this.j);
        }
        return this.j;
    }

    private void a(DataSource dataSource) {
        for (int i = 0; i < this.b.size(); i++) {
            dataSource.addTransferListener(this.b.get(i));
        }
    }

    private void a(@Nullable DataSource dataSource, TransferListener transferListener) {
        if (dataSource != null) {
            dataSource.addTransferListener(transferListener);
        }
    }
}
