package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.AbstractConcatenatedTimeline;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public final class ConcatenatingMediaSource extends CompositeMediaSource<d> {
    private static final MediaItem a = new MediaItem.Builder().setUri(Uri.EMPTY).build();
    @GuardedBy("this")
    private final List<d> b;
    @GuardedBy("this")
    private final Set<c> c;
    @Nullable
    @GuardedBy("this")
    private Handler d;
    private final List<d> e;
    private final IdentityHashMap<MediaPeriod, d> f;
    private final Map<Object, d> g;
    private final Set<d> h;
    private final boolean i;
    private final boolean j;
    private boolean k;
    private Set<c> l;
    private ShuffleOrder m;

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    protected void enableInternal() {
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public boolean isSingleWindow() {
        return false;
    }

    public ConcatenatingMediaSource(MediaSource... mediaSourceArr) {
        this(false, mediaSourceArr);
    }

    public ConcatenatingMediaSource(boolean z, MediaSource... mediaSourceArr) {
        this(z, new ShuffleOrder.DefaultShuffleOrder(0), mediaSourceArr);
    }

    public ConcatenatingMediaSource(boolean z, ShuffleOrder shuffleOrder, MediaSource... mediaSourceArr) {
        this(z, false, shuffleOrder, mediaSourceArr);
    }

    public ConcatenatingMediaSource(boolean z, boolean z2, ShuffleOrder shuffleOrder, MediaSource... mediaSourceArr) {
        for (MediaSource mediaSource : mediaSourceArr) {
            Assertions.checkNotNull(mediaSource);
        }
        this.m = shuffleOrder.getLength() > 0 ? shuffleOrder.cloneAndClear() : shuffleOrder;
        this.f = new IdentityHashMap<>();
        this.g = new HashMap();
        this.b = new ArrayList();
        this.e = new ArrayList();
        this.l = new HashSet();
        this.c = new HashSet();
        this.h = new HashSet();
        this.i = z;
        this.j = z2;
        addMediaSources(Arrays.asList(mediaSourceArr));
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public synchronized Timeline getInitialTimeline() {
        ShuffleOrder shuffleOrder;
        if (this.m.getLength() != this.b.size()) {
            shuffleOrder = this.m.cloneAndClear().cloneAndInsert(0, this.b.size());
        } else {
            shuffleOrder = this.m;
        }
        return new a(this.b, shuffleOrder, this.i);
    }

    public synchronized void addMediaSource(MediaSource mediaSource) {
        addMediaSource(this.b.size(), mediaSource);
    }

    public synchronized void addMediaSource(MediaSource mediaSource, Handler handler, Runnable runnable) {
        addMediaSource(this.b.size(), mediaSource, handler, runnable);
    }

    public synchronized void addMediaSource(int i, MediaSource mediaSource) {
        a(i, Collections.singletonList(mediaSource), (Handler) null, (Runnable) null);
    }

    public synchronized void addMediaSource(int i, MediaSource mediaSource, Handler handler, Runnable runnable) {
        a(i, Collections.singletonList(mediaSource), handler, runnable);
    }

    public synchronized void addMediaSources(Collection<MediaSource> collection) {
        a(this.b.size(), collection, (Handler) null, (Runnable) null);
    }

    public synchronized void addMediaSources(Collection<MediaSource> collection, Handler handler, Runnable runnable) {
        a(this.b.size(), collection, handler, runnable);
    }

    public synchronized void addMediaSources(int i, Collection<MediaSource> collection) {
        a(i, collection, (Handler) null, (Runnable) null);
    }

    public synchronized void addMediaSources(int i, Collection<MediaSource> collection, Handler handler, Runnable runnable) {
        a(i, collection, handler, runnable);
    }

    public synchronized MediaSource removeMediaSource(int i) {
        MediaSource mediaSource;
        mediaSource = getMediaSource(i);
        a(i, i + 1, (Handler) null, (Runnable) null);
        return mediaSource;
    }

    public synchronized MediaSource removeMediaSource(int i, Handler handler, Runnable runnable) {
        MediaSource mediaSource;
        mediaSource = getMediaSource(i);
        a(i, i + 1, handler, runnable);
        return mediaSource;
    }

    public synchronized void removeMediaSourceRange(int i, int i2) {
        a(i, i2, (Handler) null, (Runnable) null);
    }

    public synchronized void removeMediaSourceRange(int i, int i2, Handler handler, Runnable runnable) {
        a(i, i2, handler, runnable);
    }

    public synchronized void moveMediaSource(int i, int i2) {
        b(i, i2, null, null);
    }

    public synchronized void moveMediaSource(int i, int i2, Handler handler, Runnable runnable) {
        b(i, i2, handler, runnable);
    }

    public synchronized void clear() {
        removeMediaSourceRange(0, getSize());
    }

    public synchronized void clear(Handler handler, Runnable runnable) {
        removeMediaSourceRange(0, getSize(), handler, runnable);
    }

    public synchronized int getSize() {
        return this.b.size();
    }

    public synchronized MediaSource getMediaSource(int i) {
        return this.b.get(i).a;
    }

    public synchronized void setShuffleOrder(ShuffleOrder shuffleOrder) {
        a(shuffleOrder, (Handler) null, (Runnable) null);
    }

    public synchronized void setShuffleOrder(ShuffleOrder shuffleOrder, Handler handler, Runnable runnable) {
        a(shuffleOrder, handler, runnable);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public synchronized void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        this.d = new Handler(new Handler.Callback() { // from class: com.google.android.exoplayer2.source.-$$Lambda$ConcatenatingMediaSource$0FfvkEv6fl784HtO7mUJLM-PzGw
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a2;
                a2 = ConcatenatingMediaSource.this.a(message);
                return a2;
            }
        });
        if (this.b.isEmpty()) {
            c();
        } else {
            this.m = this.m.cloneAndInsert(0, this.b.size());
            a(0, this.b);
            b();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        Object a2 = a(mediaPeriodId.periodUid);
        MediaSource.MediaPeriodId copyWithPeriodUid = mediaPeriodId.copyWithPeriodUid(b(mediaPeriodId.periodUid));
        d dVar = this.g.get(a2);
        if (dVar == null) {
            dVar = new d(new b(), this.j);
            dVar.f = true;
            prepareChildSource(dVar, dVar.a);
        }
        b(dVar);
        dVar.c.add(copyWithPeriodUid);
        MaskingMediaPeriod createPeriod = dVar.a.createPeriod(copyWithPeriodUid, allocator, j);
        this.f.put(createPeriod, dVar);
        e();
        return createPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        d dVar = (d) Assertions.checkNotNull(this.f.remove(mediaPeriod));
        dVar.a.releasePeriod(mediaPeriod);
        dVar.c.remove(((MaskingMediaPeriod) mediaPeriod).id);
        if (!this.f.isEmpty()) {
            e();
        }
        a(dVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public void disableInternal() {
        super.disableInternal();
        this.h.clear();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public synchronized void releaseSourceInternal() {
        super.releaseSourceInternal();
        this.e.clear();
        this.h.clear();
        this.g.clear();
        this.m = this.m.cloneAndClear();
        if (this.d != null) {
            this.d.removeCallbacksAndMessages(null);
            this.d = null;
        }
        this.k = false;
        this.l.clear();
        a(this.c);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChildSourceInfoRefreshed(d dVar, MediaSource mediaSource, Timeline timeline) {
        a(dVar, timeline);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(d dVar, MediaSource.MediaPeriodId mediaPeriodId) {
        for (int i = 0; i < dVar.c.size(); i++) {
            if (dVar.c.get(i).windowSequenceNumber == mediaPeriodId.windowSequenceNumber) {
                return mediaPeriodId.copyWithPeriodUid(a(dVar, mediaPeriodId.periodUid));
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindowIndexForChildWindowIndex(d dVar, int i) {
        return i + dVar.e;
    }

    @GuardedBy("this")
    private void a(int i, Collection<MediaSource> collection, @Nullable Handler handler, @Nullable Runnable runnable) {
        boolean z = true;
        if ((handler == null) != (runnable == null)) {
            z = false;
        }
        Assertions.checkArgument(z);
        Handler handler2 = this.d;
        for (MediaSource mediaSource : collection) {
            Assertions.checkNotNull(mediaSource);
        }
        ArrayList arrayList = new ArrayList(collection.size());
        for (MediaSource mediaSource2 : collection) {
            arrayList.add(new d(mediaSource2, this.j));
        }
        this.b.addAll(i, arrayList);
        if (handler2 != null && !collection.isEmpty()) {
            handler2.obtainMessage(0, new e(i, arrayList, a(handler, runnable))).sendToTarget();
        } else if (runnable != null && handler != null) {
            handler.post(runnable);
        }
    }

    @GuardedBy("this")
    private void a(int i, int i2, @Nullable Handler handler, @Nullable Runnable runnable) {
        boolean z = false;
        if ((handler == null) == (runnable == null)) {
            z = true;
        }
        Assertions.checkArgument(z);
        Handler handler2 = this.d;
        Util.removeRange(this.b, i, i2);
        if (handler2 != null) {
            handler2.obtainMessage(1, new e(i, Integer.valueOf(i2), a(handler, runnable))).sendToTarget();
        } else if (runnable != null && handler != null) {
            handler.post(runnable);
        }
    }

    @GuardedBy("this")
    private void b(int i, int i2, @Nullable Handler handler, @Nullable Runnable runnable) {
        boolean z = true;
        if ((handler == null) != (runnable == null)) {
            z = false;
        }
        Assertions.checkArgument(z);
        Handler handler2 = this.d;
        List<d> list = this.b;
        list.add(i2, list.remove(i));
        if (handler2 != null) {
            handler2.obtainMessage(2, new e(i, Integer.valueOf(i2), a(handler, runnable))).sendToTarget();
        } else if (runnable != null && handler != null) {
            handler.post(runnable);
        }
    }

    @GuardedBy("this")
    private void a(ShuffleOrder shuffleOrder, @Nullable Handler handler, @Nullable Runnable runnable) {
        boolean z = true;
        if ((handler == null) != (runnable == null)) {
            z = false;
        }
        Assertions.checkArgument(z);
        Handler handler2 = this.d;
        if (handler2 != null) {
            int size = getSize();
            if (shuffleOrder.getLength() != size) {
                shuffleOrder = shuffleOrder.cloneAndClear().cloneAndInsert(0, size);
            }
            handler2.obtainMessage(3, new e(0, shuffleOrder, a(handler, runnable))).sendToTarget();
            return;
        }
        if (shuffleOrder.getLength() > 0) {
            shuffleOrder = shuffleOrder.cloneAndClear();
        }
        this.m = shuffleOrder;
        if (runnable != null && handler != null) {
            handler.post(runnable);
        }
    }

    @Nullable
    @GuardedBy("this")
    private c a(@Nullable Handler handler, @Nullable Runnable runnable) {
        if (handler == null || runnable == null) {
            return null;
        }
        c cVar = new c(handler, runnable);
        this.c.add(cVar);
        return cVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Message message) {
        switch (message.what) {
            case 0:
                e eVar = (e) Util.castNonNull(message.obj);
                this.m = this.m.cloneAndInsert(eVar.a, ((Collection) eVar.b).size());
                a(eVar.a, (Collection) eVar.b);
                a(eVar.c);
                break;
            case 1:
                e eVar2 = (e) Util.castNonNull(message.obj);
                int i = eVar2.a;
                int intValue = ((Integer) eVar2.b).intValue();
                if (i == 0 && intValue == this.m.getLength()) {
                    this.m = this.m.cloneAndClear();
                } else {
                    this.m = this.m.cloneAndRemove(i, intValue);
                }
                for (int i2 = intValue - 1; i2 >= i; i2--) {
                    a(i2);
                }
                a(eVar2.c);
                break;
            case 2:
                e eVar3 = (e) Util.castNonNull(message.obj);
                this.m = this.m.cloneAndRemove(eVar3.a, eVar3.a + 1);
                this.m = this.m.cloneAndInsert(((Integer) eVar3.b).intValue(), 1);
                a(eVar3.a, ((Integer) eVar3.b).intValue());
                a(eVar3.c);
                break;
            case 3:
                e eVar4 = (e) Util.castNonNull(message.obj);
                this.m = (ShuffleOrder) eVar4.b;
                a(eVar4.c);
                break;
            case 4:
                c();
                break;
            case 5:
                a((Set) Util.castNonNull(message.obj));
                break;
            default:
                throw new IllegalStateException();
        }
        return true;
    }

    private void b() {
        a((c) null);
    }

    private void a(@Nullable c cVar) {
        if (!this.k) {
            d().obtainMessage(4).sendToTarget();
            this.k = true;
        }
        if (cVar != null) {
            this.l.add(cVar);
        }
    }

    private void c() {
        this.k = false;
        Set<c> set = this.l;
        this.l = new HashSet();
        refreshSourceInfo(new a(this.e, this.m, this.i));
        d().obtainMessage(5, set).sendToTarget();
    }

    private Handler d() {
        return (Handler) Assertions.checkNotNull(this.d);
    }

    private synchronized void a(Set<c> set) {
        for (c cVar : set) {
            cVar.a();
        }
        this.c.removeAll(set);
    }

    private void a(int i, Collection<d> collection) {
        for (d dVar : collection) {
            i++;
            a(i, dVar);
        }
    }

    private void a(int i, d dVar) {
        if (i > 0) {
            d dVar2 = this.e.get(i - 1);
            dVar.a(i, dVar2.e + dVar2.a.getTimeline().getWindowCount());
        } else {
            dVar.a(i, 0);
        }
        a(i, 1, dVar.a.getTimeline().getWindowCount());
        this.e.add(i, dVar);
        this.g.put(dVar.b, dVar);
        prepareChildSource(dVar, dVar.a);
        if (!isEnabled() || !this.f.isEmpty()) {
            disableChildSource(dVar);
        } else {
            this.h.add(dVar);
        }
    }

    private void a(d dVar, Timeline timeline) {
        int windowCount;
        if (dVar.d + 1 < this.e.size() && (windowCount = timeline.getWindowCount() - (this.e.get(dVar.d + 1).e - dVar.e)) != 0) {
            a(dVar.d + 1, 0, windowCount);
        }
        b();
    }

    private void a(int i) {
        d remove = this.e.remove(i);
        this.g.remove(remove.b);
        a(i, -1, -remove.a.getTimeline().getWindowCount());
        remove.f = true;
        a(remove);
    }

    private void a(int i, int i2) {
        int min = Math.min(i, i2);
        int max = Math.max(i, i2);
        int i3 = this.e.get(min).e;
        List<d> list = this.e;
        list.add(i2, list.remove(i));
        while (min <= max) {
            d dVar = this.e.get(min);
            dVar.d = min;
            dVar.e = i3;
            i3 += dVar.a.getTimeline().getWindowCount();
            min++;
        }
    }

    private void a(int i, int i2, int i3) {
        while (i < this.e.size()) {
            d dVar = this.e.get(i);
            dVar.d += i2;
            dVar.e += i3;
            i++;
        }
    }

    private void a(d dVar) {
        if (dVar.f && dVar.c.isEmpty()) {
            this.h.remove(dVar);
            releaseChildSource(dVar);
        }
    }

    private void b(d dVar) {
        this.h.add(dVar);
        enableChildSource(dVar);
    }

    private void e() {
        Iterator<d> it = this.h.iterator();
        while (it.hasNext()) {
            d next = it.next();
            if (next.c.isEmpty()) {
                disableChildSource(next);
                it.remove();
            }
        }
    }

    private static Object a(Object obj) {
        return a.getChildTimelineUidFromConcatenatedUid(obj);
    }

    private static Object b(Object obj) {
        return a.getChildPeriodUidFromConcatenatedUid(obj);
    }

    private static Object a(d dVar, Object obj) {
        return a.getConcatenatedUid(dVar.b, obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class d {
        public final MaskingMediaSource a;
        public int d;
        public int e;
        public boolean f;
        public final List<MediaSource.MediaPeriodId> c = new ArrayList();
        public final Object b = new Object();

        public d(MediaSource mediaSource, boolean z) {
            this.a = new MaskingMediaSource(mediaSource, z);
        }

        public void a(int i, int i2) {
            this.d = i;
            this.e = i2;
            this.f = false;
            this.c.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class e<T> {
        public final int a;
        public final T b;
        @Nullable
        public final c c;

        public e(int i, T t, @Nullable c cVar) {
            this.a = i;
            this.b = t;
            this.c = cVar;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a extends AbstractConcatenatedTimeline {
        private final int a;
        private final int b;
        private final int[] c;
        private final int[] d;
        private final Timeline[] e;
        private final Object[] f;
        private final HashMap<Object, Integer> g = new HashMap<>();

        public a(Collection<d> collection, ShuffleOrder shuffleOrder, boolean z) {
            super(z, shuffleOrder);
            int size = collection.size();
            this.c = new int[size];
            this.d = new int[size];
            this.e = new Timeline[size];
            this.f = new Object[size];
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            for (d dVar : collection) {
                this.e[i3] = dVar.a.getTimeline();
                this.d[i3] = i;
                this.c[i3] = i2;
                i += this.e[i3].getWindowCount();
                i2 += this.e[i3].getPeriodCount();
                this.f[i3] = dVar.b;
                HashMap<Object, Integer> hashMap = this.g;
                Object obj = this.f[i3];
                i3++;
                hashMap.put(obj, Integer.valueOf(i3));
            }
            this.a = i;
            this.b = i2;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getChildIndexByPeriodIndex(int i) {
            return Util.binarySearchFloor(this.c, i + 1, false, false);
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getChildIndexByWindowIndex(int i) {
            return Util.binarySearchFloor(this.d, i + 1, false, false);
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getChildIndexByChildUid(Object obj) {
            Integer num = this.g.get(obj);
            if (num == null) {
                return -1;
            }
            return num.intValue();
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected Timeline getTimelineByChildIndex(int i) {
            return this.e[i];
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getFirstPeriodIndexByChildIndex(int i) {
            return this.c[i];
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getFirstWindowIndexByChildIndex(int i) {
            return this.d[i];
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected Object getChildUidByChildIndex(int i) {
            return this.f[i];
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return this.a;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.b;
        }
    }

    /* loaded from: classes2.dex */
    private static final class b extends BaseMediaSource {
        @Override // com.google.android.exoplayer2.source.MediaSource
        public void maybeThrowSourceInfoRefreshError() {
        }

        @Override // com.google.android.exoplayer2.source.BaseMediaSource
        protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        }

        @Override // com.google.android.exoplayer2.source.MediaSource
        public void releasePeriod(MediaPeriod mediaPeriod) {
        }

        @Override // com.google.android.exoplayer2.source.BaseMediaSource
        protected void releaseSourceInternal() {
        }

        private b() {
        }

        @Override // com.google.android.exoplayer2.source.MediaSource
        public MediaItem getMediaItem() {
            return ConcatenatingMediaSource.a;
        }

        @Override // com.google.android.exoplayer2.source.MediaSource
        public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c {
        private final Handler a;
        private final Runnable b;

        public c(Handler handler, Runnable runnable) {
            this.a = handler;
            this.b = runnable;
        }

        public void a() {
            this.a.post(this.b);
        }
    }
}
