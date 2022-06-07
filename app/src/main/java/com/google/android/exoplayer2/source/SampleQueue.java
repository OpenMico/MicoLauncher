package com.google.android.exoplayer2.source;

import android.os.Looper;
import androidx.annotation.CallSuper;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes2.dex */
public class SampleQueue implements TrackOutput {
    private boolean A;
    @Nullable
    private Format B;
    @Nullable
    private Format C;
    private int D;
    private boolean E;
    private boolean F;
    private long G;
    private boolean H;
    private final c a;
    @Nullable
    private final DrmSessionManager d;
    @Nullable
    private final DrmSessionEventListener.EventDispatcher e;
    @Nullable
    private final Looper f;
    @Nullable
    private UpstreamFormatChangedListener g;
    @Nullable
    private Format h;
    @Nullable
    private DrmSession i;
    private int[] k;
    private long[] l;
    private int[] m;
    private int[] n;
    private long[] o;
    private TrackOutput.CryptoData[] p;
    private int q;
    private int r;
    private int s;
    private int t;
    private boolean x;
    private final a b = new a();
    private int j = 1000;
    private final e<b> c = new e<>($$Lambda$SampleQueue$1Ob8N7s0SMsrsscatj81bjfF3Mw.INSTANCE);
    private long u = Long.MIN_VALUE;
    private long v = Long.MIN_VALUE;
    private long w = Long.MIN_VALUE;
    private boolean z = true;
    private boolean y = true;

    /* loaded from: classes2.dex */
    public interface UpstreamFormatChangedListener {
        void onUpstreamFormatChanged(Format format);
    }

    public static SampleQueue createWithoutDrm(Allocator allocator) {
        return new SampleQueue(allocator, null, null, null);
    }

    public static SampleQueue createWithDrm(Allocator allocator, Looper looper, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return new SampleQueue(allocator, (Looper) Assertions.checkNotNull(looper), (DrmSessionManager) Assertions.checkNotNull(drmSessionManager), (DrmSessionEventListener.EventDispatcher) Assertions.checkNotNull(eventDispatcher));
    }

    public SampleQueue(Allocator allocator, @Nullable Looper looper, @Nullable DrmSessionManager drmSessionManager, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
        this.f = looper;
        this.d = drmSessionManager;
        this.e = eventDispatcher;
        this.a = new c(allocator);
        int i = this.j;
        this.k = new int[i];
        this.l = new long[i];
        this.o = new long[i];
        this.n = new int[i];
        this.m = new int[i];
        this.p = new TrackOutput.CryptoData[i];
    }

    public static /* synthetic */ void a(b bVar) {
        bVar.b.release();
    }

    @CallSuper
    public void release() {
        reset(true);
        c();
    }

    public final void reset() {
        reset(false);
    }

    @CallSuper
    public void reset(boolean z) {
        this.a.a();
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.y = true;
        this.u = Long.MIN_VALUE;
        this.v = Long.MIN_VALUE;
        this.w = Long.MIN_VALUE;
        this.x = false;
        this.c.b();
        if (z) {
            this.B = null;
            this.C = null;
            this.z = true;
        }
    }

    public final void setStartTimeUs(long j) {
        this.u = j;
    }

    public final void sourceId(int i) {
        this.D = i;
    }

    public final void splice() {
        this.H = true;
    }

    public final int getWriteIndex() {
        return this.r + this.q;
    }

    public final void discardUpstreamSamples(int i) {
        this.a.a(a(i));
    }

    public final void discardUpstreamFrom(long j) {
        if (this.q != 0) {
            Assertions.checkArgument(j > getLargestReadTimestampUs());
            discardUpstreamSamples(this.r + b(j));
        }
    }

    @CallSuper
    public void preRelease() {
        discardToEnd();
        c();
    }

    @CallSuper
    public void maybeThrowError() throws IOException {
        DrmSession drmSession = this.i;
        if (drmSession != null && drmSession.getState() == 1) {
            throw ((DrmSession.DrmSessionException) Assertions.checkNotNull(this.i.getError()));
        }
    }

    public final int getFirstIndex() {
        return this.r;
    }

    public final int getReadIndex() {
        return this.r + this.t;
    }

    public final synchronized int peekSourceId() {
        return d() ? this.k[e(this.t)] : this.D;
    }

    @Nullable
    public final synchronized Format getUpstreamFormat() {
        return this.z ? null : this.C;
    }

    public final synchronized long getLargestQueuedTimestampUs() {
        return this.w;
    }

    public final synchronized long getLargestReadTimestampUs() {
        return Math.max(this.v, d(this.t));
    }

    public final synchronized boolean isLastSampleQueued() {
        return this.x;
    }

    public final synchronized long getFirstTimestampUs() {
        return this.q == 0 ? Long.MIN_VALUE : this.o[this.s];
    }

    @CallSuper
    public synchronized boolean isReady(boolean z) {
        boolean z2 = true;
        if (!d()) {
            if (!z && !this.x && (this.C == null || this.C == this.h)) {
                z2 = false;
            }
            return z2;
        } else if (this.c.a(getReadIndex()).a != this.h) {
            return true;
        } else {
            return b(e(this.t));
        }
    }

    @CallSuper
    public int read(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i, boolean z) {
        boolean z2 = false;
        int a2 = a(formatHolder, decoderInputBuffer, (i & 2) != 0, z, this.b);
        if (a2 == -4 && !decoderInputBuffer.isEndOfStream()) {
            if ((i & 1) != 0) {
                z2 = true;
            }
            if ((i & 4) == 0) {
                if (z2) {
                    this.a.b(decoderInputBuffer, this.b);
                } else {
                    this.a.a(decoderInputBuffer, this.b);
                }
            }
            if (!z2) {
                this.t++;
            }
        }
        return a2;
    }

    public final synchronized boolean seekTo(int i) {
        a();
        if (i >= this.r && i <= this.r + this.q) {
            this.u = Long.MIN_VALUE;
            this.t = i - this.r;
            return true;
        }
        return false;
    }

    public final synchronized boolean seekTo(long j, boolean z) {
        a();
        int e = e(this.t);
        if (d() && j >= this.o[e] && (j <= this.w || z)) {
            int a2 = a(e, this.q - this.t, j, true);
            if (a2 == -1) {
                return false;
            }
            this.u = j;
            this.t += a2;
            return true;
        }
        return false;
    }

    public final synchronized int getSkipCount(long j, boolean z) {
        int e = e(this.t);
        if (d() && j >= this.o[e]) {
            if (j <= this.w || !z) {
                int a2 = a(e, this.q - this.t, j, true);
                if (a2 == -1) {
                    return 0;
                }
                return a2;
            }
            return this.q - this.t;
        }
        return 0;
    }

    public final synchronized void skip(int i) {
        boolean z;
        if (i >= 0) {
            try {
                if (this.t + i <= this.q) {
                    z = true;
                    Assertions.checkArgument(z);
                    this.t += i;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        z = false;
        Assertions.checkArgument(z);
        this.t += i;
    }

    public final void discardTo(long j, boolean z, boolean z2) {
        this.a.b(a(j, z, z2));
    }

    public final void discardToRead() {
        this.a.b(discardSampleMetadataToRead());
    }

    public final void discardToEnd() {
        this.a.b(b());
    }

    public final void setSampleOffsetUs(long j) {
        if (this.G != j) {
            this.G = j;
            invalidateUpstreamFormatAdjustment();
        }
    }

    public final void setUpstreamFormatChangeListener(@Nullable UpstreamFormatChangedListener upstreamFormatChangedListener) {
        this.g = upstreamFormatChangedListener;
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public final void format(Format format) {
        Format adjustedUpstreamFormat = getAdjustedUpstreamFormat(format);
        this.A = false;
        this.B = format;
        boolean a2 = a(adjustedUpstreamFormat);
        UpstreamFormatChangedListener upstreamFormatChangedListener = this.g;
        if (upstreamFormatChangedListener != null && a2) {
            upstreamFormatChangedListener.onUpstreamFormatChanged(adjustedUpstreamFormat);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public final int sampleData(DataReader dataReader, int i, boolean z, int i2) throws IOException {
        return this.a.a(dataReader, i, z);
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public final void sampleData(ParsableByteArray parsableByteArray, int i, int i2) {
        this.a.a(parsableByteArray, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0067  */
    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void sampleMetadata(long r12, int r14, int r15, int r16, @androidx.annotation.Nullable com.google.android.exoplayer2.extractor.TrackOutput.CryptoData r17) {
        /*
            r11 = this;
            r8 = r11
            boolean r0 = r8.A
            if (r0 == 0) goto L_0x0010
            com.google.android.exoplayer2.Format r0 = r8.B
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r0)
            com.google.android.exoplayer2.Format r0 = (com.google.android.exoplayer2.Format) r0
            r11.format(r0)
        L_0x0010:
            r0 = r14 & 1
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0018
            r3 = r2
            goto L_0x0019
        L_0x0018:
            r3 = r1
        L_0x0019:
            boolean r4 = r8.y
            if (r4 == 0) goto L_0x0022
            if (r3 != 0) goto L_0x0020
            return
        L_0x0020:
            r8.y = r1
        L_0x0022:
            long r4 = r8.G
            long r4 = r4 + r12
            boolean r6 = r8.E
            if (r6 == 0) goto L_0x0062
            long r6 = r8.u
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 >= 0) goto L_0x0030
            return
        L_0x0030:
            if (r0 != 0) goto L_0x0062
            boolean r0 = r8.F
            if (r0 != 0) goto L_0x005e
            java.lang.String r0 = "SampleQueue"
            com.google.android.exoplayer2.Format r6 = r8.C
            java.lang.String r6 = java.lang.String.valueOf(r6)
            java.lang.String r7 = java.lang.String.valueOf(r6)
            int r7 = r7.length()
            int r7 = r7 + 50
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>(r7)
            java.lang.String r7 = "Overriding unexpected non-sync sample for format: "
            r9.append(r7)
            r9.append(r6)
            java.lang.String r6 = r9.toString()
            com.google.android.exoplayer2.util.Log.w(r0, r6)
            r8.F = r2
        L_0x005e:
            r0 = r14 | 1
            r6 = r0
            goto L_0x0063
        L_0x0062:
            r6 = r14
        L_0x0063:
            boolean r0 = r8.H
            if (r0 == 0) goto L_0x0074
            if (r3 == 0) goto L_0x0073
            boolean r0 = r11.a(r4)
            if (r0 != 0) goto L_0x0070
            goto L_0x0073
        L_0x0070:
            r8.H = r1
            goto L_0x0074
        L_0x0073:
            return
        L_0x0074:
            com.google.android.exoplayer2.source.c r0 = r8.a
            long r0 = r0.c()
            r7 = r15
            long r2 = (long) r7
            long r0 = r0 - r2
            r2 = r16
            long r2 = (long) r2
            long r9 = r0 - r2
            r0 = r11
            r1 = r4
            r3 = r6
            r4 = r9
            r6 = r15
            r7 = r17
            r0.a(r1, r3, r4, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SampleQueue.sampleMetadata(long, int, int, int, com.google.android.exoplayer2.extractor.TrackOutput$CryptoData):void");
    }

    protected final void invalidateUpstreamFormatAdjustment() {
        this.A = true;
    }

    @CallSuper
    public Format getAdjustedUpstreamFormat(Format format) {
        return (this.G == 0 || format.subsampleOffsetUs == Long.MAX_VALUE) ? format : format.buildUpon().setSubsampleOffsetUs(format.subsampleOffsetUs + this.G).build();
    }

    private synchronized void a() {
        this.t = 0;
        this.a.b();
    }

    private synchronized int a(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z, boolean z2, a aVar) {
        decoderInputBuffer.waitingForKeys = false;
        if (!d()) {
            if (!z2 && !this.x) {
                if (this.C == null || (!z && this.C == this.h)) {
                    return -3;
                }
                a((Format) Assertions.checkNotNull(this.C), formatHolder);
                return -5;
            }
            decoderInputBuffer.setFlags(4);
            return -4;
        }
        Format format = this.c.a(getReadIndex()).a;
        if (!z && format == this.h) {
            int e = e(this.t);
            if (!b(e)) {
                decoderInputBuffer.waitingForKeys = true;
                return -3;
            }
            decoderInputBuffer.setFlags(this.n[e]);
            decoderInputBuffer.timeUs = this.o[e];
            if (decoderInputBuffer.timeUs < this.u) {
                decoderInputBuffer.addFlag(Integer.MIN_VALUE);
            }
            aVar.a = this.m[e];
            aVar.b = this.l[e];
            aVar.c = this.p[e];
            return -4;
        }
        a(format, formatHolder);
        return -5;
    }

    private synchronized boolean a(Format format) {
        this.z = false;
        if (Util.areEqual(format, this.C)) {
            return false;
        }
        if (this.c.c() || !this.c.a().a.equals(format)) {
            this.C = format;
        } else {
            this.C = this.c.a().a;
        }
        this.E = MimeTypes.allSamplesAreSyncSamples(this.C.sampleMimeType, this.C.codecs);
        this.F = false;
        return true;
    }

    private synchronized long a(long j, boolean z, boolean z2) {
        if (this.q != 0 && j >= this.o[this.s]) {
            int a2 = a(this.s, (!z2 || this.t == this.q) ? this.q : this.t + 1, j, z);
            if (a2 == -1) {
                return -1L;
            }
            return c(a2);
        }
        return -1L;
    }

    public synchronized long discardSampleMetadataToRead() {
        if (this.t == 0) {
            return -1L;
        }
        return c(this.t);
    }

    private synchronized long b() {
        if (this.q == 0) {
            return -1L;
        }
        return c(this.q);
    }

    private void c() {
        DrmSession drmSession = this.i;
        if (drmSession != null) {
            drmSession.release(this.e);
            this.i = null;
            this.h = null;
        }
    }

    private synchronized void a(long j, int i, long j2, int i2, @Nullable TrackOutput.CryptoData cryptoData) {
        DrmSessionManager.DrmSessionReference drmSessionReference;
        if (this.q > 0) {
            int e = e(this.q - 1);
            Assertions.checkArgument(this.l[e] + ((long) this.m[e]) <= j2);
        }
        this.x = (536870912 & i) != 0;
        this.w = Math.max(this.w, j);
        int e2 = e(this.q);
        this.o[e2] = j;
        this.l[e2] = j2;
        this.m[e2] = i2;
        this.n[e2] = i;
        this.p[e2] = cryptoData;
        this.k[e2] = this.D;
        if (this.c.c() || !this.c.a().a.equals(this.C)) {
            if (this.d != null) {
                drmSessionReference = this.d.preacquireSession((Looper) Assertions.checkNotNull(this.f), this.e, this.C);
            } else {
                drmSessionReference = DrmSessionManager.DrmSessionReference.EMPTY;
            }
            this.c.a(getWriteIndex(), new b((Format) Assertions.checkNotNull(this.C), drmSessionReference));
        }
        this.q++;
        if (this.q == this.j) {
            int i3 = this.j + 1000;
            int[] iArr = new int[i3];
            long[] jArr = new long[i3];
            long[] jArr2 = new long[i3];
            int[] iArr2 = new int[i3];
            int[] iArr3 = new int[i3];
            TrackOutput.CryptoData[] cryptoDataArr = new TrackOutput.CryptoData[i3];
            int i4 = this.j - this.s;
            System.arraycopy(this.l, this.s, jArr, 0, i4);
            System.arraycopy(this.o, this.s, jArr2, 0, i4);
            System.arraycopy(this.n, this.s, iArr2, 0, i4);
            System.arraycopy(this.m, this.s, iArr3, 0, i4);
            System.arraycopy(this.p, this.s, cryptoDataArr, 0, i4);
            System.arraycopy(this.k, this.s, iArr, 0, i4);
            int i5 = this.s;
            System.arraycopy(this.l, 0, jArr, i4, i5);
            System.arraycopy(this.o, 0, jArr2, i4, i5);
            System.arraycopy(this.n, 0, iArr2, i4, i5);
            System.arraycopy(this.m, 0, iArr3, i4, i5);
            System.arraycopy(this.p, 0, cryptoDataArr, i4, i5);
            System.arraycopy(this.k, 0, iArr, i4, i5);
            this.l = jArr;
            this.o = jArr2;
            this.n = iArr2;
            this.m = iArr3;
            this.p = cryptoDataArr;
            this.k = iArr;
            this.s = 0;
            this.j = i3;
        }
    }

    private synchronized boolean a(long j) {
        boolean z = true;
        if (this.q == 0) {
            if (j <= this.v) {
                z = false;
            }
            return z;
        } else if (getLargestReadTimestampUs() >= j) {
            return false;
        } else {
            a(this.r + b(j));
            return true;
        }
    }

    private long a(int i) {
        int writeIndex = getWriteIndex() - i;
        boolean z = false;
        Assertions.checkArgument(writeIndex >= 0 && writeIndex <= this.q - this.t);
        this.q -= writeIndex;
        this.w = Math.max(this.v, d(this.q));
        if (writeIndex == 0 && this.x) {
            z = true;
        }
        this.x = z;
        this.c.c(i);
        int i2 = this.q;
        if (i2 == 0) {
            return 0L;
        }
        int e = e(i2 - 1);
        return this.l[e] + this.m[e];
    }

    private boolean d() {
        return this.t != this.q;
    }

    private void a(Format format, FormatHolder formatHolder) {
        boolean z = this.h == null;
        DrmInitData drmInitData = z ? null : this.h.drmInitData;
        this.h = format;
        DrmInitData drmInitData2 = format.drmInitData;
        DrmSessionManager drmSessionManager = this.d;
        formatHolder.format = drmSessionManager != null ? format.copyWithExoMediaCryptoType(drmSessionManager.getExoMediaCryptoType(format)) : format;
        formatHolder.drmSession = this.i;
        if (this.d != null) {
            if (z || !Util.areEqual(drmInitData, drmInitData2)) {
                DrmSession drmSession = this.i;
                this.i = this.d.acquireSession((Looper) Assertions.checkNotNull(this.f), this.e, format);
                formatHolder.drmSession = this.i;
                if (drmSession != null) {
                    drmSession.release(this.e);
                }
            }
        }
    }

    private boolean b(int i) {
        DrmSession drmSession = this.i;
        return drmSession == null || drmSession.getState() == 4 || ((this.n[i] & 1073741824) == 0 && this.i.playClearSamplesWithoutKeys());
    }

    private int a(int i, int i2, long j, boolean z) {
        int i3 = -1;
        int i4 = i;
        for (int i5 = 0; i5 < i2 && this.o[i4] <= j; i5++) {
            if (!z || (this.n[i4] & 1) != 0) {
                if (this.o[i4] == j) {
                    return i5;
                }
                i3 = i5;
            }
            i4++;
            if (i4 == this.j) {
                i4 = 0;
            }
        }
        return i3;
    }

    private int b(long j) {
        int i = this.q;
        int e = e(i - 1);
        while (i > this.t && this.o[e] >= j) {
            i--;
            e--;
            if (e == -1) {
                e = this.j - 1;
            }
        }
        return i;
    }

    @GuardedBy("this")
    private long c(int i) {
        this.v = Math.max(this.v, d(i));
        this.q -= i;
        this.r += i;
        this.s += i;
        int i2 = this.s;
        int i3 = this.j;
        if (i2 >= i3) {
            this.s = i2 - i3;
        }
        this.t -= i;
        if (this.t < 0) {
            this.t = 0;
        }
        this.c.b(this.r);
        if (this.q != 0) {
            return this.l[this.s];
        }
        int i4 = this.s;
        if (i4 == 0) {
            i4 = this.j;
        }
        int i5 = i4 - 1;
        return this.l[i5] + this.m[i5];
    }

    private long d(int i) {
        long j = Long.MIN_VALUE;
        if (i == 0) {
            return Long.MIN_VALUE;
        }
        int e = e(i - 1);
        for (int i2 = 0; i2 < i; i2++) {
            j = Math.max(j, this.o[e]);
            if ((this.n[e] & 1) != 0) {
                break;
            }
            e--;
            if (e == -1) {
                e = this.j - 1;
            }
        }
        return j;
    }

    private int e(int i) {
        int i2 = this.s + i;
        int i3 = this.j;
        return i2 < i3 ? i2 : i2 - i3;
    }

    /* loaded from: classes2.dex */
    public static final class a {
        public int a;
        public long b;
        @Nullable
        public TrackOutput.CryptoData c;

        a() {
        }
    }

    /* loaded from: classes2.dex */
    public static final class b {
        public final Format a;
        public final DrmSessionManager.DrmSessionReference b;

        private b(Format format, DrmSessionManager.DrmSessionReference drmSessionReference) {
            this.a = format;
            this.b = drmSessionReference;
        }
    }
}
