package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
final class IcyDataSource implements DataSource {
    private final DataSource a;
    private final int b;
    private final Listener c;
    private final byte[] d;
    private int e;

    /* loaded from: classes2.dex */
    public interface Listener {
        void onIcyMetadata(ParsableByteArray parsableByteArray);
    }

    public IcyDataSource(DataSource dataSource, int i, Listener listener) {
        Assertions.checkArgument(i > 0);
        this.a = dataSource;
        this.b = i;
        this.c = listener;
        this.d = new byte[1];
        this.e = i;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.a.addTransferListener(transferListener);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.e == 0) {
            if (!a()) {
                return -1;
            }
            this.e = this.b;
        }
        int read = this.a.read(bArr, i, Math.min(this.e, i2));
        if (read != -1) {
            this.e -= read;
        }
        return read;
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
    public void close() {
        throw new UnsupportedOperationException();
    }

    private boolean a() throws IOException {
        if (this.a.read(this.d, 0, 1) == -1) {
            return false;
        }
        int i = (this.d[0] & 255) << 4;
        if (i == 0) {
            return true;
        }
        byte[] bArr = new byte[i];
        int i2 = i;
        int i3 = 0;
        while (i2 > 0) {
            int read = this.a.read(bArr, i3, i2);
            if (read == -1) {
                return false;
            }
            i3 += read;
            i2 -= read;
        }
        while (i > 0 && bArr[i - 1] == 0) {
            i--;
        }
        if (i > 0) {
            this.c.onIcyMetadata(new ParsableByteArray(bArr, i));
        }
        return true;
    }
}
