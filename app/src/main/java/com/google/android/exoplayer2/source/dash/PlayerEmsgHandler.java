package com.google.android.exoplayer2.source.dash;

import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.emsg.EventMessageDecoder;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public final class PlayerEmsgHandler implements Handler.Callback {
    private final Allocator a;
    private final PlayerEmsgCallback b;
    private DashManifest f;
    private long g;
    private boolean h;
    private boolean i;
    private boolean j;
    private final TreeMap<Long, Long> e = new TreeMap<>();
    private final Handler d = Util.createHandlerForCurrentLooper(this);
    private final EventMessageDecoder c = new EventMessageDecoder();

    /* loaded from: classes2.dex */
    public interface PlayerEmsgCallback {
        void onDashManifestPublishTimeExpired(long j);

        void onDashManifestRefreshRequested();
    }

    public PlayerEmsgHandler(DashManifest dashManifest, PlayerEmsgCallback playerEmsgCallback, Allocator allocator) {
        this.f = dashManifest;
        this.b = playerEmsgCallback;
        this.a = allocator;
    }

    public void updateManifest(DashManifest dashManifest) {
        this.i = false;
        this.g = C.TIME_UNSET;
        this.f = dashManifest;
        a();
    }

    public PlayerTrackEmsgHandler newPlayerTrackEmsgHandler() {
        return new PlayerTrackEmsgHandler(this.a);
    }

    public void release() {
        this.j = true;
        this.d.removeCallbacksAndMessages(null);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (this.j) {
            return true;
        }
        if (message.what != 1) {
            return false;
        }
        a aVar = (a) message.obj;
        a(aVar.a, aVar.b);
        return true;
    }

    boolean a(long j) {
        boolean z = false;
        if (!this.f.dynamic) {
            return false;
        }
        if (this.i) {
            return true;
        }
        Map.Entry<Long, Long> b = b(this.f.publishTimeMs);
        if (b != null && b.getValue().longValue() < j) {
            this.g = b.getKey().longValue();
            b();
            z = true;
        }
        if (z) {
            c();
        }
        return z;
    }

    void a(Chunk chunk) {
        this.h = true;
    }

    boolean a(boolean z) {
        if (!this.f.dynamic) {
            return false;
        }
        if (this.i) {
            return true;
        }
        if (!z) {
            return false;
        }
        c();
        return true;
    }

    private void a(long j, long j2) {
        Long l = this.e.get(Long.valueOf(j2));
        if (l == null) {
            this.e.put(Long.valueOf(j2), Long.valueOf(j));
        } else if (l.longValue() > j) {
            this.e.put(Long.valueOf(j2), Long.valueOf(j));
        }
    }

    @Nullable
    private Map.Entry<Long, Long> b(long j) {
        return this.e.ceilingEntry(Long.valueOf(j));
    }

    private void a() {
        Iterator<Map.Entry<Long, Long>> it = this.e.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getKey().longValue() < this.f.publishTimeMs) {
                it.remove();
            }
        }
    }

    private void b() {
        this.b.onDashManifestPublishTimeExpired(this.g);
    }

    private void c() {
        if (this.h) {
            this.i = true;
            this.h = false;
            this.b.onDashManifestRefreshRequested();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long b(EventMessage eventMessage) {
        try {
            return Util.parseXsDateTime(Util.fromUtf8Bytes(eventMessage.messageData));
        } catch (ParserException unused) {
            return C.TIME_UNSET;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(String str, String str2) {
        return "urn:mpeg:dash:event:2012".equals(str) && ("1".equals(str2) || "2".equals(str2) || "3".equals(str2));
    }

    /* loaded from: classes2.dex */
    public final class PlayerTrackEmsgHandler implements TrackOutput {
        private final SampleQueue b;
        private final FormatHolder c = new FormatHolder();
        private final MetadataInputBuffer d = new MetadataInputBuffer();
        private long e = C.TIME_UNSET;

        PlayerTrackEmsgHandler(Allocator allocator) {
            this.b = SampleQueue.createWithoutDrm(allocator);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void format(Format format) {
            this.b.format(format);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public int sampleData(DataReader dataReader, int i, boolean z, int i2) throws IOException {
            return this.b.sampleData(dataReader, i, z);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleData(ParsableByteArray parsableByteArray, int i, int i2) {
            this.b.sampleData(parsableByteArray, i);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleMetadata(long j, int i, int i2, int i3, @Nullable TrackOutput.CryptoData cryptoData) {
            this.b.sampleMetadata(j, i, i2, i3, cryptoData);
            a();
        }

        public boolean maybeRefreshManifestBeforeLoadingNextChunk(long j) {
            return PlayerEmsgHandler.this.a(j);
        }

        public void onChunkLoadCompleted(Chunk chunk) {
            if (this.e == C.TIME_UNSET || chunk.endTimeUs > this.e) {
                this.e = chunk.endTimeUs;
            }
            PlayerEmsgHandler.this.a(chunk);
        }

        public boolean onChunkLoadError(Chunk chunk) {
            long j = this.e;
            return PlayerEmsgHandler.this.a(j != C.TIME_UNSET && j < chunk.startTimeUs);
        }

        public void release() {
            this.b.release();
        }

        private void a() {
            while (this.b.isReady(false)) {
                MetadataInputBuffer b = b();
                if (b != null) {
                    long j = b.timeUs;
                    Metadata decode = PlayerEmsgHandler.this.c.decode(b);
                    if (decode != null) {
                        EventMessage eventMessage = (EventMessage) decode.get(0);
                        if (PlayerEmsgHandler.b(eventMessage.schemeIdUri, eventMessage.value)) {
                            a(j, eventMessage);
                        }
                    }
                }
            }
            this.b.discardToRead();
        }

        @Nullable
        private MetadataInputBuffer b() {
            this.d.clear();
            if (this.b.read(this.c, this.d, 0, false) != -4) {
                return null;
            }
            this.d.flip();
            return this.d;
        }

        private void a(long j, EventMessage eventMessage) {
            long b = PlayerEmsgHandler.b(eventMessage);
            if (b != C.TIME_UNSET) {
                a(j, b);
            }
        }

        private void a(long j, long j2) {
            PlayerEmsgHandler.this.d.sendMessage(PlayerEmsgHandler.this.d.obtainMessage(1, new a(j, j2)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        public final long a;
        public final long b;

        public a(long j, long j2) {
            this.a = j;
            this.b = j2;
        }
    }
}
