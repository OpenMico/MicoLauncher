package com.google.android.exoplayer2.source.dash;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.metadata.emsg.EventMessageEncoder;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.dash.manifest.EventStream;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* compiled from: EventSampleStream.java */
/* loaded from: classes2.dex */
final class a implements SampleStream {
    private final Format a;
    private long[] c;
    private boolean d;
    private EventStream e;
    private boolean f;
    private int g;
    private final EventMessageEncoder b = new EventMessageEncoder();
    private long h = C.TIME_UNSET;

    @Override // com.google.android.exoplayer2.source.SampleStream
    public boolean isReady() {
        return true;
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public void maybeThrowError() throws IOException {
    }

    public a(EventStream eventStream, Format format, boolean z) {
        this.a = format;
        this.e = eventStream;
        this.c = eventStream.presentationTimesUs;
        a(eventStream, z);
    }

    public String a() {
        return this.e.id();
    }

    public void a(EventStream eventStream, boolean z) {
        int i = this.g;
        long j = i == 0 ? -9223372036854775807L : this.c[i - 1];
        this.d = z;
        this.e = eventStream;
        this.c = eventStream.presentationTimesUs;
        long j2 = this.h;
        if (j2 != C.TIME_UNSET) {
            a(j2);
        } else if (j != C.TIME_UNSET) {
            this.g = Util.binarySearchCeil(this.c, j, false, false);
        }
    }

    public void a(long j) {
        boolean z = false;
        this.g = Util.binarySearchCeil(this.c, j, true, false);
        if (this.d && this.g == this.c.length) {
            z = true;
        }
        if (!z) {
            j = C.TIME_UNSET;
        }
        this.h = j;
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
        boolean z = this.g == this.c.length;
        if (z && !this.d) {
            decoderInputBuffer.setFlags(4);
            return -4;
        } else if ((i & 2) != 0 || !this.f) {
            formatHolder.format = this.a;
            this.f = true;
            return -5;
        } else if (z) {
            return -3;
        } else {
            int i2 = this.g;
            this.g = i2 + 1;
            byte[] encode = this.b.encode(this.e.events[i2]);
            decoderInputBuffer.ensureSpaceForWrite(encode.length);
            decoderInputBuffer.data.put(encode);
            decoderInputBuffer.timeUs = this.c[i2];
            decoderInputBuffer.setFlags(1);
            return -4;
        }
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public int skipData(long j) {
        int max = Math.max(this.g, Util.binarySearchCeil(this.c, j, true, false));
        int i = max - this.g;
        this.g = max;
        return i;
    }
}
