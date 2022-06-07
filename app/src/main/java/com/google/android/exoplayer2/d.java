package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* compiled from: MediaPeriodInfo.java */
/* loaded from: classes.dex */
final class d {
    public final MediaSource.MediaPeriodId a;
    public final long b;
    public final long c;
    public final long d;
    public final long e;
    public final boolean f;
    public final boolean g;
    public final boolean h;
    public final boolean i;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(MediaSource.MediaPeriodId mediaPeriodId, long j, long j2, long j3, long j4, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5 = false;
        Assertions.checkArgument(!z4 || z2);
        Assertions.checkArgument(!z3 || z2);
        if (!z || (!z2 && !z3 && !z4)) {
            z5 = true;
        }
        Assertions.checkArgument(z5);
        this.a = mediaPeriodId;
        this.b = j;
        this.c = j2;
        this.d = j3;
        this.e = j4;
        this.f = z;
        this.g = z2;
        this.h = z3;
        this.i = z4;
    }

    public d a(long j) {
        if (j == this.b) {
            return this;
        }
        return new d(this.a, j, this.c, this.d, this.e, this.f, this.g, this.h, this.i);
    }

    public d b(long j) {
        if (j == this.c) {
            return this;
        }
        return new d(this.a, this.b, j, this.d, this.e, this.f, this.g, this.h, this.i);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        d dVar = (d) obj;
        return this.b == dVar.b && this.c == dVar.c && this.d == dVar.d && this.e == dVar.e && this.f == dVar.f && this.g == dVar.g && this.h == dVar.h && this.i == dVar.i && Util.areEqual(this.a, dVar.a);
    }

    public int hashCode() {
        return ((((((((((((((((527 + this.a.hashCode()) * 31) + ((int) this.b)) * 31) + ((int) this.c)) * 31) + ((int) this.d)) * 31) + ((int) this.e)) * 31) + (this.f ? 1 : 0)) * 31) + (this.g ? 1 : 0)) * 31) + (this.h ? 1 : 0)) * 31) + (this.i ? 1 : 0);
    }
}
