package com.google.android.exoplayer2.transformer;

import android.util.SparseIntArray;
import android.util.SparseLongArray;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MuxerWrapper.java */
@RequiresApi(18)
/* loaded from: classes2.dex */
public final class b {
    private static final long a = C.msToUs(500);
    private final Muxer b;
    private int e;
    private int f;
    private boolean g;
    private long i;
    private final SparseIntArray c = new SparseIntArray();
    private final SparseLongArray d = new SparseLongArray();
    private int h = 7;

    public b(Muxer muxer) {
        this.b = muxer;
    }

    public void a() {
        Assertions.checkState(this.f == 0, "Tracks cannot be registered after track formats have been added.");
        this.e++;
    }

    public boolean a(@Nullable String str) {
        return this.b.a(str);
    }

    public void a(Format format) {
        boolean z = false;
        Assertions.checkState(this.e > 0, "All tracks should be registered before the formats are added.");
        Assertions.checkState(this.f < this.e, "All track formats have already been added.");
        String str = format.sampleMimeType;
        boolean z2 = MimeTypes.isAudio(str) || MimeTypes.isVideo(str);
        String valueOf = String.valueOf(str);
        Assertions.checkState(z2, valueOf.length() != 0 ? "Unsupported track format: ".concat(valueOf) : new String("Unsupported track format: "));
        int trackType = MimeTypes.getTrackType(str);
        if (this.c.get(trackType, -1) == -1) {
            z = true;
        }
        StringBuilder sb = new StringBuilder(44);
        sb.append("There is already a track of type ");
        sb.append(trackType);
        Assertions.checkState(z, sb.toString());
        this.c.put(trackType, this.b.a(format));
        this.d.put(trackType, 0L);
        this.f++;
        if (this.f == this.e) {
            this.g = true;
        }
    }

    public boolean a(int i, @Nullable ByteBuffer byteBuffer, boolean z, long j) {
        int i2 = this.c.get(i, -1);
        boolean z2 = i2 != -1;
        StringBuilder sb = new StringBuilder(68);
        sb.append("Could not write sample because there is no track of type ");
        sb.append(i);
        Assertions.checkState(z2, sb.toString());
        if (!b(i)) {
            return false;
        }
        if (byteBuffer == null) {
            return true;
        }
        this.b.a(i2, byteBuffer, z, j);
        this.d.put(i, j);
        this.h = i;
        return true;
    }

    public void a(int i) {
        this.c.delete(i);
        this.d.delete(i);
    }

    public void a(boolean z) {
        this.g = false;
        this.b.a(z);
    }

    public int b() {
        return this.e;
    }

    private boolean b(int i) {
        long j = this.d.get(i, C.TIME_UNSET);
        Assertions.checkState(j != C.TIME_UNSET);
        if (!this.g) {
            return false;
        }
        if (this.d.size() == 1) {
            return true;
        }
        if (i != this.h) {
            this.i = Util.minValue(this.d);
        }
        return j - this.i <= a;
    }
}
