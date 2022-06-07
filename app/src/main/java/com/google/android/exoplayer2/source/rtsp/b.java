package com.google.android.exoplayer2.source.rtsp;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.WavUtil;
import java.util.TreeSet;

/* compiled from: RtpPacketReorderingQueue.java */
/* loaded from: classes2.dex */
final class b {
    @GuardedBy("this")
    private final TreeSet<a> a = new TreeSet<>($$Lambda$b$y03smHdsidiKR5JUJXaIv_5JhIY.INSTANCE);
    @GuardedBy("this")
    private int b;
    @GuardedBy("this")
    private int c;
    @GuardedBy("this")
    private boolean d;

    public b() {
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(a aVar, a aVar2) {
        return a(aVar.a.sequenceNumber, aVar2.a.sequenceNumber);
    }

    public synchronized void a() {
        this.a.clear();
        this.d = false;
        this.c = -1;
        this.b = -1;
    }

    public synchronized boolean a(RtpPacket rtpPacket, long j) {
        if (this.a.size() < 5000) {
            int i = rtpPacket.sequenceNumber;
            if (!this.d) {
                a();
                this.c = b(i);
                this.d = true;
                a(new a(rtpPacket, j));
                return true;
            } else if (Math.abs(a(i, a(this.b))) >= 1000) {
                this.c = b(i);
                this.a.clear();
                a(new a(rtpPacket, j));
                return true;
            } else if (a(i, this.c) <= 0) {
                return false;
            } else {
                a(new a(rtpPacket, j));
                return true;
            }
        } else {
            throw new IllegalStateException("Queue size limit of 5000 reached.");
        }
    }

    @Nullable
    public synchronized RtpPacket a(long j) {
        if (this.a.isEmpty()) {
            return null;
        }
        a first = this.a.first();
        int i = first.a.sequenceNumber;
        if (i != a(this.c) && j < first.b) {
            return null;
        }
        this.a.pollFirst();
        this.c = i;
        return first.a;
    }

    private synchronized void a(a aVar) {
        this.b = aVar.a.sequenceNumber;
        this.a.add(aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RtpPacketReorderingQueue.java */
    /* loaded from: classes2.dex */
    public static final class a {
        public final RtpPacket a;
        public final long b;

        public a(RtpPacket rtpPacket, long j) {
            this.a = rtpPacket;
            this.b = j;
        }
    }

    private static int a(int i) {
        return (i + 1) % 65535;
    }

    private static int b(int i) {
        return i == 0 ? WavUtil.TYPE_WAVE_FORMAT_EXTENSIBLE : (i - 1) % 65535;
    }

    private static int a(int i, int i2) {
        int min;
        int i3 = i - i2;
        return (Math.abs(i3) <= 1000 || (min = (Math.min(i, i2) - Math.max(i, i2)) + 65535) >= 1000) ? i3 : i < i2 ? min : -min;
    }
}
