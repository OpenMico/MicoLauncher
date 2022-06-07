package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.HttpDataSource;

@Deprecated
/* loaded from: classes2.dex */
public final class DefaultHttpDataSourceFactory extends HttpDataSource.BaseFactory {
    @Nullable
    private final String a;
    @Nullable
    private final TransferListener b;
    private final int c;
    private final int d;
    private final boolean e;

    public DefaultHttpDataSourceFactory() {
        this(null);
    }

    public DefaultHttpDataSourceFactory(@Nullable String str) {
        this(str, null);
    }

    public DefaultHttpDataSourceFactory(@Nullable String str, @Nullable TransferListener transferListener) {
        this(str, transferListener, 8000, 8000, false);
    }

    public DefaultHttpDataSourceFactory(@Nullable String str, int i, int i2, boolean z) {
        this(str, null, i, i2, z);
    }

    public DefaultHttpDataSourceFactory(@Nullable String str, @Nullable TransferListener transferListener, int i, int i2, boolean z) {
        this.a = str;
        this.b = transferListener;
        this.c = i;
        this.d = i2;
        this.e = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.upstream.HttpDataSource.BaseFactory
    public DefaultHttpDataSource createDataSourceInternal(HttpDataSource.RequestProperties requestProperties) {
        DefaultHttpDataSource defaultHttpDataSource = new DefaultHttpDataSource(this.a, this.c, this.d, this.e, requestProperties);
        TransferListener transferListener = this.b;
        if (transferListener != null) {
            defaultHttpDataSource.addTransferListener(transferListener);
        }
        return defaultHttpDataSource;
    }
}
