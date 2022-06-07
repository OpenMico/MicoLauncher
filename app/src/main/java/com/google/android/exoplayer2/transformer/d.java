package com.google.android.exoplayer2.transformer;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.SlowMotionData;
import com.google.android.exoplayer2.metadata.mp4.SmtaMetadataEntry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* compiled from: SefSlowMotionVideoSampleTransformer.java */
/* loaded from: classes2.dex */
final class d implements c {
    private static final int a = NalUnitUtil.NAL_START_CODE.length;
    private final byte[] b = new byte[a];
    @Nullable
    private final SlowMotionData c;
    private final Iterator<SlowMotionData.Segment> d;
    private final float e;
    private final int f;
    private final int g;
    @Nullable
    private b h;
    @Nullable
    private b i;
    private long j;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SefSlowMotionVideoSampleTransformer.java */
    /* loaded from: classes2.dex */
    public static final class a {
        public float a = -3.4028235E38f;
        public int b = -1;
        public int c = -1;
        @Nullable
        public SlowMotionData d;
    }

    public d(Format format) {
        a a2 = a(format.metadata);
        this.c = a2.d;
        SlowMotionData slowMotionData = this.c;
        this.d = (slowMotionData != null ? slowMotionData.segments : ImmutableList.of()).iterator();
        this.e = a2.a;
        this.f = a2.b;
        this.g = a2.c;
        this.i = this.d.hasNext() ? new b(this.d.next(), this.f, this.g) : null;
        if (this.c != null) {
            boolean equals = MimeTypes.VIDEO_H264.equals(format.sampleMimeType);
            String valueOf = String.valueOf(format.sampleMimeType);
            Assertions.checkArgument(equals, valueOf.length() != 0 ? "Unsupported MIME type for SEF slow motion video track: ".concat(valueOf) : new String("Unsupported MIME type for SEF slow motion video track: "));
        }
    }

    @Override // com.google.android.exoplayer2.transformer.c
    public void a(DecoderInputBuffer decoderInputBuffer) {
        if (this.c != null) {
            ByteBuffer byteBuffer = (ByteBuffer) Util.castNonNull(decoderInputBuffer.data);
            byteBuffer.position(byteBuffer.position() + a);
            boolean z = false;
            byteBuffer.get(this.b, 0, 4);
            byte[] bArr = this.b;
            int i = bArr[0] & Ascii.US;
            boolean z2 = ((bArr[1] & 255) >> 7) == 1;
            if (i == 14 && z2) {
                z = true;
            }
            Assertions.checkState(z, "Missing SVC extension prefix NAL unit.");
            if (a((this.b[3] & 255) >> 5, decoderInputBuffer.timeUs)) {
                decoderInputBuffer.timeUs = a(decoderInputBuffer.timeUs);
                a(byteBuffer);
                return;
            }
            decoderInputBuffer.data = null;
        }
    }

    @VisibleForTesting
    boolean a(int i, long j) {
        while (true) {
            b bVar = this.i;
            if (bVar == null || j < bVar.b) {
                break;
            }
            a();
        }
        b bVar2 = this.i;
        if (bVar2 == null || j < bVar2.a) {
            b bVar3 = this.h;
            if (bVar3 != null && j >= bVar3.b) {
                b();
            }
        } else {
            a();
        }
        b bVar4 = this.h;
        return i <= (bVar4 != null ? bVar4.d : this.g) || b(i, j);
    }

    private void a() {
        if (this.h != null) {
            b();
        }
        this.h = this.i;
        this.i = this.d.hasNext() ? new b(this.d.next(), this.f, this.g) : null;
    }

    @RequiresNonNull({"currentSegmentInfo"})
    private void b() {
        this.j += (this.h.b - this.h.a) * (this.h.c - 1);
        this.h = null;
    }

    private boolean b(int i, long j) {
        b bVar = this.i;
        if (bVar == null || i >= bVar.d) {
            return false;
        }
        long j2 = ((this.i.a - j) * 30) / 1000000;
        float f = (-(1 << (this.f - this.i.d))) + 0.45f;
        for (int i2 = 1; i2 < this.i.d && ((float) j2) < (1 << (this.f - i2)) + f; i2++) {
            if (i <= i2) {
                return true;
            }
        }
        return false;
    }

    @VisibleForTesting
    long a(long j) {
        long j2 = this.j + j;
        b bVar = this.h;
        if (bVar != null) {
            j2 += (j - bVar.a) * (this.h.c - 1);
        }
        return Math.round(((float) (j2 * 30)) / this.e);
    }

    private void a(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        while (true) {
            int remaining = byteBuffer.remaining();
            int i = a;
            if (remaining >= i) {
                byteBuffer.get(this.b, 0, i);
                if (Arrays.equals(this.b, NalUnitUtil.NAL_START_CODE)) {
                    byteBuffer.position(position);
                    return;
                } else {
                    position++;
                    byteBuffer.position(position);
                }
            } else {
                throw new IllegalStateException("Could not find NAL unit start code.");
            }
        }
    }

    private static a a(@Nullable Metadata metadata) {
        a aVar = new a();
        if (metadata == null) {
            return aVar;
        }
        boolean z = false;
        for (int i = 0; i < metadata.length(); i++) {
            Metadata.Entry entry = metadata.get(i);
            if (entry instanceof SmtaMetadataEntry) {
                SmtaMetadataEntry smtaMetadataEntry = (SmtaMetadataEntry) entry;
                aVar.a = smtaMetadataEntry.captureFrameRate;
                aVar.b = smtaMetadataEntry.svcTemporalLayerCount - 1;
            } else if (entry instanceof SlowMotionData) {
                aVar.d = (SlowMotionData) entry;
            }
        }
        if (aVar.d == null) {
            return aVar;
        }
        Assertions.checkState(aVar.b != -1, "SVC temporal layer count not found.");
        Assertions.checkState(aVar.a != -3.4028235E38f, "Capture frame rate not found.");
        boolean z2 = aVar.a % 1.0f == 0.0f && aVar.a % 30.0f == 0.0f;
        float f = aVar.a;
        StringBuilder sb = new StringBuilder(43);
        sb.append("Invalid capture frame rate: ");
        sb.append(f);
        Assertions.checkState(z2, sb.toString());
        int i2 = ((int) aVar.a) / 30;
        int i3 = aVar.b;
        while (true) {
            if (i3 < 0) {
                break;
            } else if ((i2 & 1) == 1) {
                if ((i2 >> 1) == 0) {
                    z = true;
                }
                float f2 = aVar.a;
                StringBuilder sb2 = new StringBuilder(84);
                sb2.append("Could not compute normal speed max SVC layer for capture frame rate  ");
                sb2.append(f2);
                Assertions.checkState(z, sb2.toString());
                aVar.c = i3;
            } else {
                i2 >>= 1;
                i3--;
            }
        }
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SefSlowMotionVideoSampleTransformer.java */
    /* loaded from: classes2.dex */
    public static final class b {
        public final long a;
        public final long b;
        public final int c;
        public final int d;

        public b(SlowMotionData.Segment segment, int i, int i2) {
            this.a = C.msToUs(segment.startTimeMs);
            this.b = C.msToUs(segment.endTimeMs);
            this.c = segment.speedDivisor;
            this.d = a(this.c, i, i2);
        }

        private static int a(int i, int i2, int i3) {
            int i4 = i3;
            int i5 = i;
            while (true) {
                if (i5 <= 0) {
                    break;
                }
                boolean z = true;
                if ((i5 & 1) == 1) {
                    if ((i5 >> 1) != 0) {
                        z = false;
                    }
                    StringBuilder sb = new StringBuilder(34);
                    sb.append("Invalid speed divisor: ");
                    sb.append(i);
                    Assertions.checkState(z, sb.toString());
                } else {
                    i4++;
                    i5 >>= 1;
                }
            }
            return Math.min(i4, i2);
        }
    }
}
