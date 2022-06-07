package com.google.android.exoplayer2.upstream;

import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public interface BandwidthMeter {
    void addEventListener(Handler handler, EventListener eventListener);

    long getBitrateEstimate();

    default long getTimeToFirstByteEstimateUs() {
        return C.TIME_UNSET;
    }

    @Nullable
    TransferListener getTransferListener();

    void removeEventListener(EventListener eventListener);

    /* loaded from: classes2.dex */
    public interface EventListener {
        void onBandwidthSample(int i, long j, long j2);

        /* loaded from: classes2.dex */
        public static final class EventDispatcher {
            private final CopyOnWriteArrayList<a> a = new CopyOnWriteArrayList<>();

            public void addListener(Handler handler, EventListener eventListener) {
                Assertions.checkNotNull(handler);
                Assertions.checkNotNull(eventListener);
                removeListener(eventListener);
                this.a.add(new a(handler, eventListener));
            }

            public void removeListener(EventListener eventListener) {
                Iterator<a> it = this.a.iterator();
                while (it.hasNext()) {
                    a next = it.next();
                    if (next.b == eventListener) {
                        next.a();
                        this.a.remove(next);
                    }
                }
            }

            public void bandwidthSample(final int i, final long j, final long j2) {
                Iterator<a> it = this.a.iterator();
                while (it.hasNext()) {
                    final a next = it.next();
                    if (!next.c) {
                        next.a.post(new Runnable() { // from class: com.google.android.exoplayer2.upstream.-$$Lambda$BandwidthMeter$EventListener$EventDispatcher$lEVac-ZKbud-cl_5aFhmjv_qRQw
                            @Override // java.lang.Runnable
                            public final void run() {
                                BandwidthMeter.EventListener.EventDispatcher.a(BandwidthMeter.EventListener.EventDispatcher.a.this, i, j, j2);
                            }
                        });
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static /* synthetic */ void a(a aVar, int i, long j, long j2) {
                aVar.b.onBandwidthSample(i, j, j2);
            }

            /* JADX INFO: Access modifiers changed from: private */
            /* loaded from: classes2.dex */
            public static final class a {
                private final Handler a;
                private final EventListener b;
                private boolean c;

                public a(Handler handler, EventListener eventListener) {
                    this.a = handler;
                    this.b = eventListener;
                }

                public void a() {
                    this.c = true;
                }
            }
        }
    }
}
