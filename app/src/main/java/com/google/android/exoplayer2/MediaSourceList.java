package com.google.android.exoplayer2;

import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MaskingMediaPeriod;
import com.google.android.exoplayer2.source.MaskingMediaSource;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class MediaSourceList {
    private final MediaSourceListInfoRefreshListener d;
    private boolean j;
    @Nullable
    private TransferListener k;
    private ShuffleOrder i = new ShuffleOrder.DefaultShuffleOrder(0);
    private final IdentityHashMap<MediaPeriod, c> b = new IdentityHashMap<>();
    private final Map<Object, c> c = new HashMap();
    private final List<c> a = new ArrayList();
    private final MediaSourceEventListener.EventDispatcher e = new MediaSourceEventListener.EventDispatcher();
    private final DrmSessionEventListener.EventDispatcher f = new DrmSessionEventListener.EventDispatcher();
    private final HashMap<c, b> g = new HashMap<>();
    private final Set<c> h = new HashSet();

    /* loaded from: classes.dex */
    public interface MediaSourceListInfoRefreshListener {
        void onPlaylistUpdateRequested();
    }

    public MediaSourceList(MediaSourceListInfoRefreshListener mediaSourceListInfoRefreshListener, @Nullable AnalyticsCollector analyticsCollector, Handler handler) {
        this.d = mediaSourceListInfoRefreshListener;
        if (analyticsCollector != null) {
            this.e.addEventListener(handler, analyticsCollector);
            this.f.addEventListener(handler, analyticsCollector);
        }
    }

    public Timeline a(List<c> list, ShuffleOrder shuffleOrder) {
        a(0, this.a.size());
        return a(this.a.size(), list, shuffleOrder);
    }

    public Timeline a(int i, List<c> list, ShuffleOrder shuffleOrder) {
        if (!list.isEmpty()) {
            this.i = shuffleOrder;
            for (int i2 = i; i2 < list.size() + i; i2++) {
                c cVar = list.get(i2 - i);
                if (i2 > 0) {
                    c cVar2 = this.a.get(i2 - 1);
                    cVar.a(cVar2.d + cVar2.a.getTimeline().getWindowCount());
                } else {
                    cVar.a(0);
                }
                b(i2, cVar.a.getTimeline().getWindowCount());
                this.a.add(i2, cVar);
                this.c.put(cVar.b, cVar);
                if (this.j) {
                    c(cVar);
                    if (this.b.isEmpty()) {
                        this.h.add(cVar);
                    } else {
                        b(cVar);
                    }
                }
            }
        }
        return d();
    }

    public Timeline a(int i, int i2, ShuffleOrder shuffleOrder) {
        Assertions.checkArgument(i >= 0 && i <= i2 && i2 <= b());
        this.i = shuffleOrder;
        a(i, i2);
        return d();
    }

    public Timeline a(int i, int i2, int i3, ShuffleOrder shuffleOrder) {
        Assertions.checkArgument(i >= 0 && i <= i2 && i2 <= b() && i3 >= 0);
        this.i = shuffleOrder;
        if (i == i2 || i == i3) {
            return d();
        }
        int min = Math.min(i, i3);
        int max = Math.max(((i2 - i) + i3) - 1, i2 - 1);
        int i4 = this.a.get(min).d;
        Util.moveItems(this.a, i, i2, i3);
        while (min <= max) {
            c cVar = this.a.get(min);
            cVar.d = i4;
            i4 += cVar.a.getTimeline().getWindowCount();
            min++;
        }
        return d();
    }

    public boolean a() {
        return this.j;
    }

    public int b() {
        return this.a.size();
    }

    public Timeline a(ShuffleOrder shuffleOrder) {
        int b2 = b();
        if (shuffleOrder.getLength() != b2) {
            shuffleOrder = shuffleOrder.cloneAndClear().cloneAndInsert(0, b2);
        }
        this.i = shuffleOrder;
        return d();
    }

    public void a(@Nullable TransferListener transferListener) {
        Assertions.checkState(!this.j);
        this.k = transferListener;
        for (int i = 0; i < this.a.size(); i++) {
            c cVar = this.a.get(i);
            c(cVar);
            this.h.add(cVar);
        }
        this.j = true;
    }

    public MediaPeriod a(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        Object a2 = a(mediaPeriodId.periodUid);
        MediaSource.MediaPeriodId copyWithPeriodUid = mediaPeriodId.copyWithPeriodUid(b(mediaPeriodId.periodUid));
        c cVar = (c) Assertions.checkNotNull(this.c.get(a2));
        a(cVar);
        cVar.c.add(copyWithPeriodUid);
        MaskingMediaPeriod createPeriod = cVar.a.createPeriod(copyWithPeriodUid, allocator, j);
        this.b.put(createPeriod, cVar);
        e();
        return createPeriod;
    }

    public void a(MediaPeriod mediaPeriod) {
        c cVar = (c) Assertions.checkNotNull(this.b.remove(mediaPeriod));
        cVar.a.releasePeriod(mediaPeriod);
        cVar.c.remove(((MaskingMediaPeriod) mediaPeriod).id);
        if (!this.b.isEmpty()) {
            e();
        }
        d(cVar);
    }

    public void c() {
        for (b bVar : this.g.values()) {
            try {
                bVar.a.releaseSource(bVar.b);
            } catch (RuntimeException e) {
                Log.e("MediaSourceList", "Failed to release child source.", e);
            }
            bVar.a.removeEventListener(bVar.c);
            bVar.a.removeDrmEventListener(bVar.c);
        }
        this.g.clear();
        this.h.clear();
        this.j = false;
    }

    public Timeline d() {
        if (this.a.isEmpty()) {
            return Timeline.EMPTY;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.a.size(); i2++) {
            c cVar = this.a.get(i2);
            cVar.d = i;
            i += cVar.a.getTimeline().getWindowCount();
        }
        return new h(this.a, this.i);
    }

    private void a(c cVar) {
        this.h.add(cVar);
        b bVar = this.g.get(cVar);
        if (bVar != null) {
            bVar.a.enable(bVar.b);
        }
    }

    private void e() {
        Iterator<c> it = this.h.iterator();
        while (it.hasNext()) {
            c next = it.next();
            if (next.c.isEmpty()) {
                b(next);
                it.remove();
            }
        }
    }

    private void b(c cVar) {
        b bVar = this.g.get(cVar);
        if (bVar != null) {
            bVar.a.disable(bVar.b);
        }
    }

    private void a(int i, int i2) {
        for (int i3 = i2 - 1; i3 >= i; i3--) {
            c remove = this.a.remove(i3);
            this.c.remove(remove.b);
            b(i3, -remove.a.getTimeline().getWindowCount());
            remove.e = true;
            if (this.j) {
                d(remove);
            }
        }
    }

    private void b(int i, int i2) {
        while (i < this.a.size()) {
            this.a.get(i).d += i2;
            i++;
        }
    }

    @Nullable
    public static MediaSource.MediaPeriodId b(c cVar, MediaSource.MediaPeriodId mediaPeriodId) {
        for (int i = 0; i < cVar.c.size(); i++) {
            if (cVar.c.get(i).windowSequenceNumber == mediaPeriodId.windowSequenceNumber) {
                return mediaPeriodId.copyWithPeriodUid(a(cVar, mediaPeriodId.periodUid));
            }
        }
        return null;
    }

    public static int b(c cVar, int i) {
        return i + cVar.d;
    }

    private void c(c cVar) {
        MaskingMediaSource maskingMediaSource = cVar.a;
        MediaSource.MediaSourceCaller mediaSourceCaller = new MediaSource.MediaSourceCaller() { // from class: com.google.android.exoplayer2.-$$Lambda$MediaSourceList$RLQ9woNbS_5mPD0R4sQXRYUU4cA
            @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
            public final void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
                MediaSourceList.this.a(mediaSource, timeline);
            }
        };
        a aVar = new a(cVar);
        this.g.put(cVar, new b(maskingMediaSource, mediaSourceCaller, aVar));
        maskingMediaSource.addEventListener(Util.createHandlerForCurrentOrMainLooper(), aVar);
        maskingMediaSource.addDrmEventListener(Util.createHandlerForCurrentOrMainLooper(), aVar);
        maskingMediaSource.prepareSource(mediaSourceCaller, this.k);
    }

    public /* synthetic */ void a(MediaSource mediaSource, Timeline timeline) {
        this.d.onPlaylistUpdateRequested();
    }

    private void d(c cVar) {
        if (cVar.e && cVar.c.isEmpty()) {
            b bVar = (b) Assertions.checkNotNull(this.g.remove(cVar));
            bVar.a.releaseSource(bVar.b);
            bVar.a.removeEventListener(bVar.c);
            bVar.a.removeDrmEventListener(bVar.c);
            this.h.remove(cVar);
        }
    }

    private static Object a(Object obj) {
        return h.getChildTimelineUidFromConcatenatedUid(obj);
    }

    private static Object b(Object obj) {
        return h.getChildPeriodUidFromConcatenatedUid(obj);
    }

    private static Object a(c cVar, Object obj) {
        return h.getConcatenatedUid(cVar.b, obj);
    }

    /* loaded from: classes.dex */
    public static final class c implements f {
        public final MaskingMediaSource a;
        public int d;
        public boolean e;
        public final List<MediaSource.MediaPeriodId> c = new ArrayList();
        public final Object b = new Object();

        public c(MediaSource mediaSource, boolean z) {
            this.a = new MaskingMediaSource(mediaSource, z);
        }

        public void a(int i) {
            this.d = i;
            this.e = false;
            this.c.clear();
        }

        @Override // com.google.android.exoplayer2.f
        public Object a() {
            return this.b;
        }

        @Override // com.google.android.exoplayer2.f
        public Timeline b() {
            return this.a.getTimeline();
        }
    }

    /* loaded from: classes.dex */
    public static final class b {
        public final MediaSource a;
        public final MediaSource.MediaSourceCaller b;
        public final a c;

        public b(MediaSource mediaSource, MediaSource.MediaSourceCaller mediaSourceCaller, a aVar) {
            this.a = mediaSource;
            this.b = mediaSourceCaller;
            this.c = aVar;
        }
    }

    /* loaded from: classes.dex */
    public final class a implements DrmSessionEventListener, MediaSourceEventListener {
        private final c b;
        private MediaSourceEventListener.EventDispatcher c;
        private DrmSessionEventListener.EventDispatcher d;

        public a(c cVar) {
            MediaSourceList.this = r2;
            this.c = r2.e;
            this.d = r2.f;
            this.b = cVar;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadStarted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.c.loadStarted(loadEventInfo, mediaLoadData);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadCompleted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.c.loadCompleted(loadEventInfo, mediaLoadData);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadCanceled(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.c.loadCanceled(loadEventInfo, mediaLoadData);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
            if (a(i, mediaPeriodId)) {
                this.c.loadError(loadEventInfo, mediaLoadData, iOException, z);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onUpstreamDiscarded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.c.upstreamDiscarded(mediaLoadData);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onDownstreamFormatChanged(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.c.downstreamFormatChanged(mediaLoadData);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, int i2) {
            if (a(i, mediaPeriodId)) {
                this.d.drmSessionAcquired(i2);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysLoaded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.d.drmKeysLoaded();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionManagerError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc) {
            if (a(i, mediaPeriodId)) {
                this.d.drmSessionManagerError(exc);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysRestored(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.d.drmKeysRestored();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysRemoved(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.d.drmKeysRemoved();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionReleased(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.d.drmSessionReleased();
            }
        }

        private boolean a(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            MediaSource.MediaPeriodId mediaPeriodId2;
            if (mediaPeriodId != null) {
                mediaPeriodId2 = MediaSourceList.b(this.b, mediaPeriodId);
                if (mediaPeriodId2 == null) {
                    return false;
                }
            } else {
                mediaPeriodId2 = null;
            }
            int b = MediaSourceList.b(this.b, i);
            if (this.c.windowIndex != b || !Util.areEqual(this.c.mediaPeriodId, mediaPeriodId2)) {
                this.c = MediaSourceList.this.e.withParameters(b, mediaPeriodId2, 0L);
            }
            if (this.d.windowIndex == b && Util.areEqual(this.d.mediaPeriodId, mediaPeriodId2)) {
                return true;
            }
            this.d = MediaSourceList.this.f.withParameters(b, mediaPeriodId2);
            return true;
        }
    }
}
