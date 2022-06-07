package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public abstract class BaseDataSource implements DataSource {
    private final boolean a;
    private final ArrayList<TransferListener> b = new ArrayList<>(1);
    private int c;
    @Nullable
    private DataSpec d;

    public BaseDataSource(boolean z) {
        this.a = z;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public final void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        if (!this.b.contains(transferListener)) {
            this.b.add(transferListener);
            this.c++;
        }
    }

    protected final void transferInitializing(DataSpec dataSpec) {
        for (int i = 0; i < this.c; i++) {
            this.b.get(i).onTransferInitializing(this, dataSpec, this.a);
        }
    }

    protected final void transferStarted(DataSpec dataSpec) {
        this.d = dataSpec;
        for (int i = 0; i < this.c; i++) {
            this.b.get(i).onTransferStart(this, dataSpec, this.a);
        }
    }

    protected final void bytesTransferred(int i) {
        DataSpec dataSpec = (DataSpec) Util.castNonNull(this.d);
        for (int i2 = 0; i2 < this.c; i2++) {
            this.b.get(i2).onBytesTransferred(this, dataSpec, this.a, i);
        }
    }

    protected final void transferEnded() {
        DataSpec dataSpec = (DataSpec) Util.castNonNull(this.d);
        for (int i = 0; i < this.c; i++) {
            this.b.get(i).onTransferEnd(this, dataSpec, this.a);
        }
        this.d = null;
    }
}
