package com.google.android.exoplayer2.source.mediaparser;

import android.annotation.SuppressLint;
import android.media.MediaParser;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

@RequiresApi(30)
@SuppressLint({"Override"})
/* loaded from: classes2.dex */
public final class InputReaderAdapterV30 implements MediaParser.SeekableInputReader {
    @Nullable
    private DataReader a;
    private long b;
    private long c;
    private long d;

    public void setDataReader(DataReader dataReader, long j) {
        this.a = dataReader;
        this.b = j;
        this.d = -1L;
    }

    public void setCurrentPosition(long j) {
        this.c = j;
    }

    public long getAndResetSeekPosition() {
        long j = this.d;
        this.d = -1L;
        return j;
    }

    @Override // android.media.MediaParser.SeekableInputReader
    public void seekToPosition(long j) {
        this.d = j;
    }

    @Override // android.media.MediaParser.InputReader
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = ((DataReader) Util.castNonNull(this.a)).read(bArr, i, i2);
        this.c += read;
        return read;
    }

    @Override // android.media.MediaParser.InputReader
    public long getPosition() {
        return this.c;
    }

    @Override // android.media.MediaParser.InputReader
    public long getLength() {
        return this.b;
    }
}
