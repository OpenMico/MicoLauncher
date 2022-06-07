package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class ByteArrayDataSource extends BaseDataSource {
    private final byte[] a;
    @Nullable
    private Uri b;
    private int c;
    private int d;
    private boolean e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ByteArrayDataSource(byte[] bArr) {
        super(false);
        boolean z = false;
        Assertions.checkNotNull(bArr);
        Assertions.checkArgument(bArr.length > 0 ? true : z);
        this.a = bArr;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        this.b = dataSpec.uri;
        transferInitializing(dataSpec);
        if (dataSpec.position <= this.a.length) {
            this.c = (int) dataSpec.position;
            this.d = this.a.length - ((int) dataSpec.position);
            if (dataSpec.length != -1) {
                this.d = (int) Math.min(this.d, dataSpec.length);
            }
            this.e = true;
            transferStarted(dataSpec);
            return dataSpec.length != -1 ? dataSpec.length : this.d;
        }
        throw new DataSourceException(2008);
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return 0;
        }
        int i3 = this.d;
        if (i3 == 0) {
            return -1;
        }
        int min = Math.min(i2, i3);
        System.arraycopy(this.a, this.c, bArr, i, min);
        this.c += min;
        this.d -= min;
        bytesTransferred(min);
        return min;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return this.b;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
        if (this.e) {
            this.e = false;
            transferEnded();
        }
        this.b = null;
    }
}
