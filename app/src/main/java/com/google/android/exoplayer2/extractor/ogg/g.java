package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* compiled from: StreamReader.java */
/* loaded from: classes2.dex */
public abstract class g {
    private TrackOutput b;
    private ExtractorOutput c;
    private e d;
    private long e;
    private long f;
    private long g;
    private int h;
    private int i;
    private long k;
    private boolean l;
    private boolean m;
    private final c a = new c();
    private a j = new a();

    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    protected abstract boolean a(ParsableByteArray parsableByteArray, long j, a aVar) throws IOException;

    protected abstract long b(ParsableByteArray parsableByteArray);

    /* compiled from: StreamReader.java */
    /* loaded from: classes2.dex */
    public static class a {
        Format a;
        e b;

        a() {
        }
    }

    public void a(ExtractorOutput extractorOutput, TrackOutput trackOutput) {
        this.c = extractorOutput;
        this.b = trackOutput;
        a(true);
    }

    public void a(boolean z) {
        if (z) {
            this.j = new a();
            this.f = 0L;
            this.h = 0;
        } else {
            this.h = 1;
        }
        this.e = -1L;
        this.g = 0L;
    }

    public final void a(long j, long j2) {
        this.a.a();
        if (j == 0) {
            a(!this.l);
        } else if (this.h != 0) {
            this.e = b(j2);
            ((e) Util.castNonNull(this.d)).a(this.e);
            this.h = 2;
        }
    }

    public final int a(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        a();
        switch (this.h) {
            case 0:
                return b(extractorInput);
            case 1:
                extractorInput.skipFully((int) this.f);
                this.h = 2;
                return 0;
            case 2:
                Util.castNonNull(this.d);
                return b(extractorInput, positionHolder);
            case 3:
                return -1;
            default:
                throw new IllegalStateException();
        }
    }

    @EnsuresNonNull({"trackOutput", "extractorOutput"})
    private void a() {
        Assertions.checkStateNotNull(this.b);
        Util.castNonNull(this.c);
    }

    @EnsuresNonNullIf(expression = {"setupData.format"}, result = true)
    private boolean a(ExtractorInput extractorInput) throws IOException {
        while (this.a.a(extractorInput)) {
            this.k = extractorInput.getPosition() - this.f;
            if (!a(this.a.c(), this.f, this.j)) {
                return true;
            }
            this.f = extractorInput.getPosition();
        }
        this.h = 3;
        return false;
    }

    @RequiresNonNull({"trackOutput"})
    private int b(ExtractorInput extractorInput) throws IOException {
        if (!a(extractorInput)) {
            return -1;
        }
        this.i = this.j.a.sampleRate;
        if (!this.m) {
            this.b.format(this.j.a);
            this.m = true;
        }
        if (this.j.b != null) {
            this.d = this.j.b;
        } else if (extractorInput.getLength() == -1) {
            this.d = new b();
        } else {
            d b2 = this.a.b();
            this.d = new a(this, this.f, extractorInput.getLength(), b2.h + b2.i, b2.c, (b2.b & 4) != 0);
        }
        this.h = 2;
        this.a.d();
        return 0;
    }

    @RequiresNonNull({"trackOutput", "oggSeeker", "extractorOutput"})
    private int b(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        long a2 = this.d.a(extractorInput);
        if (a2 >= 0) {
            positionHolder.position = a2;
            return 1;
        }
        if (a2 < -1) {
            c(-(a2 + 2));
        }
        if (!this.l) {
            this.c.seekMap((SeekMap) Assertions.checkStateNotNull(this.d.b()));
            this.l = true;
        }
        if (this.k > 0 || this.a.a(extractorInput)) {
            this.k = 0L;
            ParsableByteArray c = this.a.c();
            long b2 = b(c);
            if (b2 >= 0) {
                long j = this.g;
                if (j + b2 >= this.e) {
                    long a3 = a(j);
                    this.b.sampleData(c, c.limit());
                    this.b.sampleMetadata(a3, 1, c.limit(), 0, null);
                    this.e = -1L;
                }
            }
            this.g += b2;
            return 0;
        }
        this.h = 3;
        return -1;
    }

    public long a(long j) {
        return (j * 1000000) / this.i;
    }

    public long b(long j) {
        return (this.i * j) / 1000000;
    }

    public void c(long j) {
        this.g = j;
    }

    /* compiled from: StreamReader.java */
    /* loaded from: classes2.dex */
    public static final class b implements e {
        @Override // com.google.android.exoplayer2.extractor.ogg.e
        public long a(ExtractorInput extractorInput) {
            return -1L;
        }

        @Override // com.google.android.exoplayer2.extractor.ogg.e
        public void a(long j) {
        }

        private b() {
        }

        @Override // com.google.android.exoplayer2.extractor.ogg.e
        public SeekMap b() {
            return new SeekMap.Unseekable(C.TIME_UNSET);
        }
    }
}
