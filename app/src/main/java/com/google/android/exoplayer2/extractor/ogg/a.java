package com.google.android.exoplayer2.extractor.ogg;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DefaultOggSeeker.java */
/* loaded from: classes2.dex */
public final class a implements e {
    private final d a;
    private final long b;
    private final long c;
    private final g d;
    private int e;
    private long f;
    private long g;
    private long h;
    private long i;
    private long j;
    private long k;
    private long l;

    public a(g gVar, long j, long j2, long j3, long j4, boolean z) {
        Assertions.checkArgument(j >= 0 && j2 > j);
        this.d = gVar;
        this.b = j;
        this.c = j2;
        if (j3 == j2 - j || z) {
            this.f = j4;
            this.e = 4;
        } else {
            this.e = 0;
        }
        this.a = new d();
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.e
    public long a(ExtractorInput extractorInput) throws IOException {
        switch (this.e) {
            case 0:
                this.g = extractorInput.getPosition();
                this.e = 1;
                long j = this.c - 65307;
                if (j > this.g) {
                    return j;
                }
                this.f = b(extractorInput);
                this.e = 4;
                return this.g;
            case 1:
                this.f = b(extractorInput);
                this.e = 4;
                return this.g;
            case 2:
                long c = c(extractorInput);
                if (c != -1) {
                    return c;
                }
                this.e = 3;
                d(extractorInput);
                this.e = 4;
                return -(this.k + 2);
            case 3:
                d(extractorInput);
                this.e = 4;
                return -(this.k + 2);
            case 4:
                return -1L;
            default:
                throw new IllegalStateException();
        }
    }

    @Nullable
    /* renamed from: a */
    public C0062a b() {
        if (this.f != 0) {
            return new C0062a();
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.e
    public void a(long j) {
        this.h = Util.constrainValue(j, 0L, this.f - 1);
        this.e = 2;
        this.i = this.b;
        this.j = this.c;
        this.k = 0L;
        this.l = this.f;
    }

    private long c(ExtractorInput extractorInput) throws IOException {
        if (this.i == this.j) {
            return -1L;
        }
        long position = extractorInput.getPosition();
        if (!this.a.a(extractorInput, this.j)) {
            long j = this.i;
            if (j != position) {
                return j;
            }
            throw new IOException("No ogg page can be found.");
        }
        this.a.a(extractorInput, false);
        extractorInput.resetPeekPosition();
        long j2 = this.h - this.a.c;
        int i = this.a.h + this.a.i;
        if (0 <= j2 && j2 < 72000) {
            return -1L;
        }
        int i2 = (j2 > 0L ? 1 : (j2 == 0L ? 0 : -1));
        if (i2 < 0) {
            this.j = position;
            this.l = this.a.c;
        } else {
            this.i = extractorInput.getPosition() + i;
            this.k = this.a.c;
        }
        long j3 = this.j;
        long j4 = this.i;
        if (j3 - j4 < 100000) {
            this.j = j4;
            return j4;
        }
        long j5 = i;
        long j6 = i2 <= 0 ? 2L : 1L;
        long position2 = extractorInput.getPosition();
        long j7 = this.j;
        long j8 = this.i;
        return Util.constrainValue((position2 - (j5 * j6)) + ((j2 * (j7 - j8)) / (this.l - this.k)), j8, j7 - 1);
    }

    private void d(ExtractorInput extractorInput) throws IOException {
        while (true) {
            this.a.a(extractorInput);
            this.a.a(extractorInput, false);
            if (this.a.c > this.h) {
                extractorInput.resetPeekPosition();
                return;
            }
            extractorInput.skipFully(this.a.h + this.a.i);
            this.i = extractorInput.getPosition();
            this.k = this.a.c;
        }
    }

    @VisibleForTesting
    long b(ExtractorInput extractorInput) throws IOException {
        this.a.a();
        if (this.a.a(extractorInput)) {
            this.a.a(extractorInput, false);
            extractorInput.skipFully(this.a.h + this.a.i);
            long j = this.a.c;
            while ((this.a.b & 4) != 4 && this.a.a(extractorInput) && extractorInput.getPosition() < this.c && this.a.a(extractorInput, true) && ExtractorUtil.skipFullyQuietly(extractorInput, this.a.h + this.a.i)) {
                j = this.a.c;
            }
            return j;
        }
        throw new EOFException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DefaultOggSeeker.java */
    /* renamed from: com.google.android.exoplayer2.extractor.ogg.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public final class C0062a implements SeekMap {
        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return true;
        }

        private C0062a() {
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekMap.SeekPoints getSeekPoints(long j) {
            return new SeekMap.SeekPoints(new SeekPoint(j, Util.constrainValue((a.this.b + ((a.this.d.b(j) * (a.this.c - a.this.b)) / a.this.f)) - 30000, a.this.b, a.this.c - 1)));
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            return a.this.d.a(a.this.f);
        }
    }
}
