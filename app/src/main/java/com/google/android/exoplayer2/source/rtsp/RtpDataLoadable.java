package com.google.android.exoplayer2.source.rtsp;

import android.os.Handler;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.source.rtsp.RtpDataChannel;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class RtpDataLoadable implements Loader.Loadable {
    public final int a;
    public final f b;
    private final EventListener c;
    private final ExtractorOutput d;
    private final RtpDataChannel.Factory f;
    private a g;
    private volatile boolean h;
    private volatile long j;
    private final Handler e = Util.createHandlerForCurrentLooper();
    private volatile long i = C.TIME_UNSET;

    /* loaded from: classes2.dex */
    public interface EventListener {
        void onTransportReady(String str, RtpDataChannel rtpDataChannel);
    }

    public RtpDataLoadable(int i, f fVar, EventListener eventListener, ExtractorOutput extractorOutput, RtpDataChannel.Factory factory) {
        this.a = i;
        this.b = fVar;
        this.c = eventListener;
        this.d = extractorOutput;
        this.f = factory;
    }

    public void a(long j) {
        if (j != C.TIME_UNSET && !((a) Assertions.checkNotNull(this.g)).a()) {
            this.g.a(j);
        }
    }

    public void a(int i) {
        if (!((a) Assertions.checkNotNull(this.g)).a()) {
            this.g.a(i);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void cancelLoad() {
        this.h = true;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void load() throws IOException {
        final RtpDataChannel rtpDataChannel = null;
        try {
            rtpDataChannel = this.f.createAndOpenDataChannel(this.a);
            final String a = rtpDataChannel.a();
            this.e.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.-$$Lambda$RtpDataLoadable$TDeptjpi-h0dB5W7SnqtTUakRZs
                @Override // java.lang.Runnable
                public final void run() {
                    RtpDataLoadable.this.a(a, rtpDataChannel);
                }
            });
            DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput((DataReader) Assertions.checkNotNull(rtpDataChannel), 0L, -1L);
            this.g = new a(this.b.a, this.a);
            this.g.init(this.d);
            while (!this.h) {
                if (this.i != C.TIME_UNSET) {
                    this.g.seek(this.j, this.i);
                    this.i = C.TIME_UNSET;
                }
                if (this.g.read(defaultExtractorInput, new PositionHolder()) == -1) {
                    break;
                }
            }
        } finally {
            Util.closeQuietly(rtpDataChannel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, RtpDataChannel rtpDataChannel) {
        this.c.onTransportReady(str, rtpDataChannel);
    }

    public void a() {
        ((a) Assertions.checkNotNull(this.g)).b();
    }

    public void a(long j, long j2) {
        this.i = j;
        this.j = j2;
    }
}
