package com.google.android.exoplayer2.upstream;

import android.content.Context;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

/* loaded from: classes2.dex */
public final class DefaultDataSourceFactory implements DataSource.Factory {
    private final Context a;
    @Nullable
    private final TransferListener b;
    private final DataSource.Factory c;

    public DefaultDataSourceFactory(Context context) {
        this(context, (String) null, (TransferListener) null);
    }

    public DefaultDataSourceFactory(Context context, @Nullable String str) {
        this(context, str, (TransferListener) null);
    }

    public DefaultDataSourceFactory(Context context, @Nullable String str, @Nullable TransferListener transferListener) {
        this(context, transferListener, new DefaultHttpDataSource.Factory().setUserAgent(str));
    }

    public DefaultDataSourceFactory(Context context, DataSource.Factory factory) {
        this(context, (TransferListener) null, factory);
    }

    public DefaultDataSourceFactory(Context context, @Nullable TransferListener transferListener, DataSource.Factory factory) {
        this.a = context.getApplicationContext();
        this.b = transferListener;
        this.c = factory;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
    public DefaultDataSource createDataSource() {
        DefaultDataSource defaultDataSource = new DefaultDataSource(this.a, this.c.createDataSource());
        TransferListener transferListener = this.b;
        if (transferListener != null) {
            defaultDataSource.addTransferListener(transferListener);
        }
        return defaultDataSource;
    }
}
