package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.PriorityTaskManager;

/* loaded from: classes2.dex */
public final class PriorityDataSourceFactory implements DataSource.Factory {
    private final DataSource.Factory a;
    private final PriorityTaskManager b;
    private final int c;

    public PriorityDataSourceFactory(DataSource.Factory factory, PriorityTaskManager priorityTaskManager, int i) {
        this.a = factory;
        this.b = priorityTaskManager;
        this.c = i;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
    public PriorityDataSource createDataSource() {
        return new PriorityDataSource(this.a.createDataSource(), this.b, this.c);
    }
}
