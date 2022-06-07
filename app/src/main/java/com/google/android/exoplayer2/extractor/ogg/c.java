package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import java.util.Arrays;

/* compiled from: OggPacket.java */
/* loaded from: classes2.dex */
final class c {
    private final d a = new d();
    private final ParsableByteArray b = new ParsableByteArray(new byte[65025], 0);
    private int c = -1;
    private int d;
    private boolean e;

    public void a() {
        this.a.a();
        this.b.reset(0);
        this.c = -1;
        this.e = false;
    }

    public boolean a(ExtractorInput extractorInput) throws IOException {
        int i;
        Assertions.checkState(extractorInput != null);
        if (this.e) {
            this.e = false;
            this.b.reset(0);
        }
        while (!this.e) {
            if (this.c < 0) {
                if (!this.a.a(extractorInput) || !this.a.a(extractorInput, true)) {
                    return false;
                }
                int i2 = this.a.h;
                if ((this.a.b & 1) == 1 && this.b.limit() == 0) {
                    i2 += a(0);
                    i = this.d + 0;
                } else {
                    i = 0;
                }
                if (!ExtractorUtil.skipFullyQuietly(extractorInput, i2)) {
                    return false;
                }
                this.c = i;
            }
            int a = a(this.c);
            int i3 = this.c + this.d;
            if (a > 0) {
                ParsableByteArray parsableByteArray = this.b;
                parsableByteArray.ensureCapacity(parsableByteArray.limit() + a);
                if (!ExtractorUtil.readFullyQuietly(extractorInput, this.b.getData(), this.b.limit(), a)) {
                    return false;
                }
                ParsableByteArray parsableByteArray2 = this.b;
                parsableByteArray2.setLimit(parsableByteArray2.limit() + a);
                this.e = this.a.j[i3 + (-1)] != 255;
            }
            if (i3 == this.a.g) {
                i3 = -1;
            }
            this.c = i3;
        }
        return true;
    }

    public d b() {
        return this.a;
    }

    public ParsableByteArray c() {
        return this.b;
    }

    public void d() {
        if (this.b.getData().length != 65025) {
            ParsableByteArray parsableByteArray = this.b;
            parsableByteArray.reset(Arrays.copyOf(parsableByteArray.getData(), Math.max(65025, this.b.limit())), this.b.limit());
        }
    }

    private int a(int i) {
        int i2 = 0;
        this.d = 0;
        while (this.d + i < this.a.g) {
            int[] iArr = this.a.j;
            int i3 = this.d;
            this.d = i3 + 1;
            int i4 = iArr[i3 + i];
            i2 += i4;
            if (i4 != 255) {
                break;
            }
        }
        return i2;
    }
}
