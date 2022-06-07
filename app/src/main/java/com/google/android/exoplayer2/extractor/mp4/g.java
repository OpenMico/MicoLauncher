package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* compiled from: TrackFragment.java */
/* loaded from: classes2.dex */
final class g {
    public c a;
    public long b;
    public long c;
    public long d;
    public int e;
    public int f;
    public boolean m;
    @Nullable
    public TrackEncryptionBox o;
    public boolean q;
    public long r;
    public boolean s;
    public long[] g = new long[0];
    public int[] h = new int[0];
    public int[] i = new int[0];
    public int[] j = new int[0];
    public long[] k = new long[0];
    public boolean[] l = new boolean[0];
    public boolean[] n = new boolean[0];
    public final ParsableByteArray p = new ParsableByteArray();

    public void a() {
        this.e = 0;
        this.r = 0L;
        this.s = false;
        this.m = false;
        this.q = false;
        this.o = null;
    }

    public void a(int i, int i2) {
        this.e = i;
        this.f = i2;
        if (this.h.length < i) {
            this.g = new long[i];
            this.h = new int[i];
        }
        if (this.i.length < i2) {
            int i3 = (i2 * 125) / 100;
            this.i = new int[i3];
            this.j = new int[i3];
            this.k = new long[i3];
            this.l = new boolean[i3];
            this.n = new boolean[i3];
        }
    }

    public void a(int i) {
        this.p.reset(i);
        this.m = true;
        this.q = true;
    }

    public void a(ExtractorInput extractorInput) throws IOException {
        extractorInput.readFully(this.p.getData(), 0, this.p.limit());
        this.p.setPosition(0);
        this.q = false;
    }

    public void a(ParsableByteArray parsableByteArray) {
        parsableByteArray.readBytes(this.p.getData(), 0, this.p.limit());
        this.p.setPosition(0);
        this.q = false;
    }

    public long b(int i) {
        return this.k[i] + this.j[i];
    }

    public boolean c(int i) {
        return this.m && this.n[i];
    }
}
