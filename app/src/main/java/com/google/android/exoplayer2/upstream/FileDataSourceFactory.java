package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.FileDataSource;

@Deprecated
/* loaded from: classes2.dex */
public final class FileDataSourceFactory implements DataSource.Factory {
    private final FileDataSource.Factory a;

    public FileDataSourceFactory() {
        this(null);
    }

    public FileDataSourceFactory(@Nullable TransferListener transferListener) {
        this.a = new FileDataSource.Factory().setListener(transferListener);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
    public FileDataSource createDataSource() {
        return this.a.createDataSource();
    }
}
