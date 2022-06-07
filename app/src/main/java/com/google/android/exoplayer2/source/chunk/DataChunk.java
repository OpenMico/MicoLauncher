package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes2.dex */
public abstract class DataChunk extends Chunk {
    private byte[] a;
    private volatile boolean b;

    protected abstract void consume(byte[] bArr, int i) throws IOException;

    public DataChunk(DataSource dataSource, DataSpec dataSpec, int i, Format format, int i2, @Nullable Object obj, @Nullable byte[] bArr) {
        super(dataSource, dataSpec, i, format, i2, obj, C.TIME_UNSET, C.TIME_UNSET);
        byte[] bArr2;
        DataChunk dataChunk;
        if (bArr == null) {
            bArr2 = Util.EMPTY_BYTE_ARRAY;
            dataChunk = this;
        } else {
            dataChunk = this;
            bArr2 = bArr;
        }
        dataChunk.a = bArr2;
    }

    public byte[] getDataHolder() {
        return this.a;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public final void cancelLoad() {
        this.b = true;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public final void load() throws IOException {
        try {
            this.dataSource.open(this.dataSpec);
            int i = 0;
            int i2 = 0;
            while (i != -1 && !this.b) {
                a(i2);
                i = this.dataSource.read(this.a, i2, 16384);
                if (i != -1) {
                    i2 += i;
                }
            }
            if (!this.b) {
                consume(this.a, i2);
            }
        } finally {
            Util.closeQuietly(this.dataSource);
        }
    }

    private void a(int i) {
        byte[] bArr = this.a;
        if (bArr.length < i + 16384) {
            this.a = Arrays.copyOf(bArr, bArr.length + 16384);
        }
    }
}
