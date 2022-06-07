package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class ByteArrayDataSink implements DataSink {
    private ByteArrayOutputStream a;

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void open(DataSpec dataSpec) {
        if (dataSpec.length == -1) {
            this.a = new ByteArrayOutputStream();
            return;
        }
        Assertions.checkArgument(dataSpec.length <= 2147483647L);
        this.a = new ByteArrayOutputStream((int) dataSpec.length);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void close() throws IOException {
        ((ByteArrayOutputStream) Util.castNonNull(this.a)).close();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void write(byte[] bArr, int i, int i2) {
        ((ByteArrayOutputStream) Util.castNonNull(this.a)).write(bArr, i, i2);
    }

    @Nullable
    public byte[] getData() {
        ByteArrayOutputStream byteArrayOutputStream = this.a;
        if (byteArrayOutputStream == null) {
            return null;
        }
        return byteArrayOutputStream.toByteArray();
    }
}
