package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class DefaultExtractorInput implements ExtractorInput {
    private final DataReader b;
    private final long c;
    private long d;
    private int f;
    private int g;
    private byte[] e = new byte[65536];
    private final byte[] a = new byte[4096];

    public DefaultExtractorInput(DataReader dataReader, long j, long j2) {
        this.b = dataReader;
        this.d = j;
        this.c = j2;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int a = a(bArr, i, i2);
        if (a == 0) {
            a = a(bArr, i, i2, 0, true);
        }
        d(a);
        return a;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean readFully(byte[] bArr, int i, int i2, boolean z) throws IOException {
        int a = a(bArr, i, i2);
        while (a < i2 && a != -1) {
            a = a(bArr, i, i2, a, z);
        }
        d(a);
        return a != -1;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        readFully(bArr, i, i2, false);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public int skip(int i) throws IOException {
        int b = b(i);
        if (b == 0) {
            byte[] bArr = this.a;
            b = a(bArr, 0, Math.min(i, bArr.length), 0, true);
        }
        d(b);
        return b;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean skipFully(int i, boolean z) throws IOException {
        int b = b(i);
        while (b < i && b != -1) {
            b = a(this.a, -b, Math.min(i, this.a.length + b), b, z);
        }
        d(b);
        return b != -1;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void skipFully(int i) throws IOException {
        skipFully(i, false);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public int peek(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        a(i2);
        int i4 = this.g;
        int i5 = this.f;
        int i6 = i4 - i5;
        if (i6 == 0) {
            i3 = a(this.e, i5, i2, 0, true);
            if (i3 == -1) {
                return -1;
            }
            this.g += i3;
        } else {
            i3 = Math.min(i2, i6);
        }
        System.arraycopy(this.e, this.f, bArr, i, i3);
        this.f += i3;
        return i3;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean peekFully(byte[] bArr, int i, int i2, boolean z) throws IOException {
        if (!advancePeekPosition(i2, z)) {
            return false;
        }
        System.arraycopy(this.e, this.f - i2, bArr, i, i2);
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void peekFully(byte[] bArr, int i, int i2) throws IOException {
        peekFully(bArr, i, i2, false);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public boolean advancePeekPosition(int i, boolean z) throws IOException {
        a(i);
        int i2 = this.g - this.f;
        while (i2 < i) {
            i2 = a(this.e, this.f, i, i2, z);
            if (i2 == -1) {
                return false;
            }
            this.g = this.f + i2;
        }
        this.f += i;
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void advancePeekPosition(int i) throws IOException {
        advancePeekPosition(i, false);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public void resetPeekPosition() {
        this.f = 0;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public long getPeekPosition() {
        return this.d + this.f;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public long getPosition() {
        return this.d;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public long getLength() {
        return this.c;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorInput
    public <E extends Throwable> void setRetryPosition(long j, E e) throws Throwable {
        Assertions.checkArgument(j >= 0);
        this.d = j;
        throw e;
    }

    private void a(int i) {
        int i2 = this.f + i;
        byte[] bArr = this.e;
        if (i2 > bArr.length) {
            this.e = Arrays.copyOf(this.e, Util.constrainValue(bArr.length * 2, 65536 + i2, i2 + 524288));
        }
    }

    private int b(int i) {
        int min = Math.min(this.g, i);
        c(min);
        return min;
    }

    private int a(byte[] bArr, int i, int i2) {
        int i3 = this.g;
        if (i3 == 0) {
            return 0;
        }
        int min = Math.min(i3, i2);
        System.arraycopy(this.e, 0, bArr, i, min);
        c(min);
        return min;
    }

    private void c(int i) {
        this.g -= i;
        this.f = 0;
        byte[] bArr = this.e;
        int i2 = this.g;
        if (i2 < bArr.length - 524288) {
            bArr = new byte[i2 + 65536];
        }
        System.arraycopy(this.e, i, bArr, 0, this.g);
        this.e = bArr;
    }

    private int a(byte[] bArr, int i, int i2, int i3, boolean z) throws IOException {
        if (!Thread.interrupted()) {
            int read = this.b.read(bArr, i + i3, i2 - i3);
            if (read != -1) {
                return i3 + read;
            }
            if (i3 == 0 && z) {
                return -1;
            }
            throw new EOFException();
        }
        throw new InterruptedIOException();
    }

    private void d(int i) {
        if (i != -1) {
            this.d += i;
        }
    }
}
