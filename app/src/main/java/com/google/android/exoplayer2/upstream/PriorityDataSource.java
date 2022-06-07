package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class PriorityDataSource implements DataSource {
    private final DataSource a;
    private final PriorityTaskManager b;
    private final int c;

    public PriorityDataSource(DataSource dataSource, PriorityTaskManager priorityTaskManager, int i) {
        this.a = (DataSource) Assertions.checkNotNull(dataSource);
        this.b = (PriorityTaskManager) Assertions.checkNotNull(priorityTaskManager);
        this.c = i;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.a.addTransferListener(transferListener);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        this.b.proceedOrThrow(this.c);
        return this.a.open(dataSpec);
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws IOException {
        this.b.proceedOrThrow(this.c);
        return this.a.read(bArr, i, i2);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return this.a.getUri();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Map<String, List<String>> getResponseHeaders() {
        return this.a.getResponseHeaders();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws IOException {
        this.a.close();
    }
}
