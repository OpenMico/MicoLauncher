package com.google.android.exoplayer2.upstream.crypto;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class AesCipherDataSink implements DataSink {
    private final DataSink a;
    private final byte[] b;
    @Nullable
    private final byte[] c;
    @Nullable
    private AesFlushingCipher d;

    public AesCipherDataSink(byte[] bArr, DataSink dataSink) {
        this(bArr, dataSink, null);
    }

    public AesCipherDataSink(byte[] bArr, DataSink dataSink, @Nullable byte[] bArr2) {
        this.a = dataSink;
        this.b = bArr;
        this.c = bArr2;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void open(DataSpec dataSpec) throws IOException {
        this.a.open(dataSpec);
        long a = a.a(dataSpec.key);
        this.d = new AesFlushingCipher(1, this.b, a, dataSpec.position + dataSpec.uriPositionOffset);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (this.c == null) {
            ((AesFlushingCipher) Util.castNonNull(this.d)).updateInPlace(bArr, i, i2);
            this.a.write(bArr, i, i2);
            return;
        }
        int i3 = 0;
        while (i3 < i2) {
            int min = Math.min(i2 - i3, this.c.length);
            ((AesFlushingCipher) Util.castNonNull(this.d)).update(bArr, i + i3, min, this.c, 0);
            this.a.write(this.c, 0, min);
            i3 += min;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void close() throws IOException {
        this.d = null;
        this.a.close();
    }
}
