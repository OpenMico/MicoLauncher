package com.google.android.exoplayer2.source;

import android.os.Handler;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public interface MediaSourceEventListener {
    default void onDownstreamFormatChanged(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
    }

    default void onLoadCanceled(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    default void onLoadCompleted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    default void onLoadError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
    }

    default void onLoadStarted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    default void onUpstreamDiscarded(int i, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
    }

    /* loaded from: classes2.dex */
    public static class EventDispatcher {
        private final CopyOnWriteArrayList<a> a;
        private final long b;
        @Nullable
        public final MediaSource.MediaPeriodId mediaPeriodId;
        public final int windowIndex;

        public EventDispatcher() {
            this(new CopyOnWriteArrayList(), 0, null, 0L);
        }

        private EventDispatcher(CopyOnWriteArrayList<a> copyOnWriteArrayList, int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, long j) {
            this.a = copyOnWriteArrayList;
            this.windowIndex = i;
            this.mediaPeriodId = mediaPeriodId;
            this.b = j;
        }

        @CheckResult
        public EventDispatcher withParameters(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, long j) {
            return new EventDispatcher(this.a, i, mediaPeriodId, j);
        }

        public void addEventListener(Handler handler, MediaSourceEventListener mediaSourceEventListener) {
            Assertions.checkNotNull(handler);
            Assertions.checkNotNull(mediaSourceEventListener);
            this.a.add(new a(handler, mediaSourceEventListener));
        }

        public void removeEventListener(MediaSourceEventListener mediaSourceEventListener) {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (next.b == mediaSourceEventListener) {
                    this.a.remove(next);
                }
            }
        }

        public void loadStarted(LoadEventInfo loadEventInfo, int i) {
            loadStarted(loadEventInfo, i, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET);
        }

        public void loadStarted(LoadEventInfo loadEventInfo, int i, int i2, @Nullable Format format, int i3, @Nullable Object obj, long j, long j2) {
            loadStarted(loadEventInfo, new MediaLoadData(i, i2, format, i3, obj, a(j), a(j2)));
        }

        public void loadStarted(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.source.-$$Lambda$MediaSourceEventListener$EventDispatcher$AtM-tjlWBcJR4ZqWvPicrTs47iI
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaSourceEventListener.EventDispatcher.this.c(mediaSourceEventListener, loadEventInfo, mediaLoadData);
                    }
                });
            }
        }

        public /* synthetic */ void c(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onLoadStarted(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public void loadCompleted(LoadEventInfo loadEventInfo, int i) {
            loadCompleted(loadEventInfo, i, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET);
        }

        public void loadCompleted(LoadEventInfo loadEventInfo, int i, int i2, @Nullable Format format, int i3, @Nullable Object obj, long j, long j2) {
            loadCompleted(loadEventInfo, new MediaLoadData(i, i2, format, i3, obj, a(j), a(j2)));
        }

        public void loadCompleted(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.source.-$$Lambda$MediaSourceEventListener$EventDispatcher$P_rc-4oMIbv5irJ3TnuhgiIITSE
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaSourceEventListener.EventDispatcher.this.b(mediaSourceEventListener, loadEventInfo, mediaLoadData);
                    }
                });
            }
        }

        public /* synthetic */ void b(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onLoadCompleted(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public void loadCanceled(LoadEventInfo loadEventInfo, int i) {
            loadCanceled(loadEventInfo, i, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET);
        }

        public void loadCanceled(LoadEventInfo loadEventInfo, int i, int i2, @Nullable Format format, int i3, @Nullable Object obj, long j, long j2) {
            loadCanceled(loadEventInfo, new MediaLoadData(i, i2, format, i3, obj, a(j), a(j2)));
        }

        public void loadCanceled(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.source.-$$Lambda$MediaSourceEventListener$EventDispatcher$zXkYDgN-jCcZqHyF8RfyBl0xCK4
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaSourceEventListener.EventDispatcher.this.a(mediaSourceEventListener, loadEventInfo, mediaLoadData);
                    }
                });
            }
        }

        public /* synthetic */ void a(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onLoadCanceled(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public void loadError(LoadEventInfo loadEventInfo, int i, IOException iOException, boolean z) {
            loadError(loadEventInfo, i, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET, iOException, z);
        }

        public void loadError(LoadEventInfo loadEventInfo, int i, int i2, @Nullable Format format, int i3, @Nullable Object obj, long j, long j2, IOException iOException, boolean z) {
            loadError(loadEventInfo, new MediaLoadData(i, i2, format, i3, obj, a(j), a(j2)), iOException, z);
        }

        public void loadError(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData, final IOException iOException, final boolean z) {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.source.-$$Lambda$MediaSourceEventListener$EventDispatcher$XN3t-R0Ha4HA2gxLH0ldh9WV6tI
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaSourceEventListener.EventDispatcher.this.a(mediaSourceEventListener, loadEventInfo, mediaLoadData, iOException, z);
                    }
                });
            }
        }

        public /* synthetic */ void a(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
            mediaSourceEventListener.onLoadError(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData, iOException, z);
        }

        public void upstreamDiscarded(int i, long j, long j2) {
            upstreamDiscarded(new MediaLoadData(1, i, null, 3, null, a(j), a(j2)));
        }

        public void upstreamDiscarded(final MediaLoadData mediaLoadData) {
            final MediaSource.MediaPeriodId mediaPeriodId = (MediaSource.MediaPeriodId) Assertions.checkNotNull(this.mediaPeriodId);
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.source.-$$Lambda$MediaSourceEventListener$EventDispatcher$vdxzlFNKpmKIpdunPe3S_Tgb81w
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaSourceEventListener.EventDispatcher.this.a(mediaSourceEventListener, mediaPeriodId, mediaLoadData);
                    }
                });
            }
        }

        public /* synthetic */ void a(MediaSourceEventListener mediaSourceEventListener, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onUpstreamDiscarded(this.windowIndex, mediaPeriodId, mediaLoadData);
        }

        public void downstreamFormatChanged(int i, @Nullable Format format, int i2, @Nullable Object obj, long j) {
            downstreamFormatChanged(new MediaLoadData(1, i, format, i2, obj, a(j), C.TIME_UNSET));
        }

        public void downstreamFormatChanged(final MediaLoadData mediaLoadData) {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.source.-$$Lambda$MediaSourceEventListener$EventDispatcher$n-FqC2qWlisyEv23UB30oOdDCwc
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaSourceEventListener.EventDispatcher.this.a(mediaSourceEventListener, mediaLoadData);
                    }
                });
            }
        }

        public /* synthetic */ void a(MediaSourceEventListener mediaSourceEventListener, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onDownstreamFormatChanged(this.windowIndex, this.mediaPeriodId, mediaLoadData);
        }

        private long a(long j) {
            long usToMs = C.usToMs(j);
            return usToMs == C.TIME_UNSET ? C.TIME_UNSET : this.b + usToMs;
        }

        /* loaded from: classes2.dex */
        public static final class a {
            public Handler a;
            public MediaSourceEventListener b;

            public a(Handler handler, MediaSourceEventListener mediaSourceEventListener) {
                this.a = handler;
                this.b = mediaSourceEventListener;
            }
        }
    }
}
