package com.google.android.exoplayer2.analytics;

import android.util.Base64;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.PlaybackSessionManager;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Supplier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public final class DefaultPlaybackSessionManager implements PlaybackSessionManager {
    public static final Supplier<String> DEFAULT_SESSION_ID_GENERATOR = $$Lambda$DefaultPlaybackSessionManager$aTHOsO_2SGfSNzHls9YLMktslTs.INSTANCE;
    private static final Random a = new Random();
    private final Timeline.Window b;
    private final Timeline.Period c;
    private final HashMap<String, a> d;
    private final Supplier<String> e;
    private PlaybackSessionManager.Listener f;
    private Timeline g;
    @Nullable
    private String h;

    public DefaultPlaybackSessionManager() {
        this(DEFAULT_SESSION_ID_GENERATOR);
    }

    public DefaultPlaybackSessionManager(Supplier<String> supplier) {
        this.e = supplier;
        this.b = new Timeline.Window();
        this.c = new Timeline.Period();
        this.d = new HashMap<>();
        this.g = Timeline.EMPTY;
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager
    public void setListener(PlaybackSessionManager.Listener listener) {
        this.f = listener;
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager
    public synchronized String getSessionForMediaPeriodId(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId) {
        return a(timeline.getPeriodByUid(mediaPeriodId.periodUid, this.c).windowIndex, mediaPeriodId).b;
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager
    public synchronized boolean belongsToSession(AnalyticsListener.EventTime eventTime, String str) {
        a aVar = this.d.get(str);
        if (aVar == null) {
            return false;
        }
        aVar.b(eventTime.windowIndex, eventTime.mediaPeriodId);
        return aVar.a(eventTime.windowIndex, eventTime.mediaPeriodId);
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager
    public synchronized void updateSessions(AnalyticsListener.EventTime eventTime) {
        a aVar;
        AnalyticsListener.EventTime eventTime2;
        a aVar2;
        Assertions.checkNotNull(this.f);
        if (!eventTime.timeline.isEmpty()) {
            a aVar3 = this.d.get(this.h);
            if (!(eventTime.mediaPeriodId == null || aVar3 == null)) {
                boolean z = false;
                if (aVar3.d == -1) {
                    if (aVar3.c != eventTime.windowIndex) {
                        z = true;
                    }
                } else if (eventTime.mediaPeriodId.windowSequenceNumber < aVar3.d) {
                    z = true;
                }
                if (z) {
                    return;
                }
            }
            a a2 = a(eventTime.windowIndex, eventTime.mediaPeriodId);
            if (this.h == null) {
                this.h = a2.b;
            }
            if (eventTime.mediaPeriodId == null || !eventTime.mediaPeriodId.isAd()) {
                aVar = a2;
            } else {
                MediaSource.MediaPeriodId mediaPeriodId = new MediaSource.MediaPeriodId(eventTime.mediaPeriodId.periodUid, eventTime.mediaPeriodId.windowSequenceNumber, eventTime.mediaPeriodId.adGroupIndex);
                a a3 = a(eventTime.windowIndex, mediaPeriodId);
                if (!a3.f) {
                    a3.f = true;
                    eventTime.timeline.getPeriodByUid(eventTime.mediaPeriodId.periodUid, this.c);
                    aVar = a2;
                    this.f.onSessionCreated(new AnalyticsListener.EventTime(eventTime.realtimeMs, eventTime.timeline, eventTime.windowIndex, mediaPeriodId, Math.max(0L, C.usToMs(this.c.getAdGroupTimeUs(eventTime.mediaPeriodId.adGroupIndex)) + this.c.getPositionInWindowMs()), eventTime.currentTimeline, eventTime.currentWindowIndex, eventTime.currentMediaPeriodId, eventTime.currentPlaybackPositionMs, eventTime.totalBufferedDurationMs), a3.b);
                } else {
                    aVar = a2;
                }
            }
            if (!aVar.f) {
                aVar2 = aVar;
                aVar2.f = true;
                eventTime2 = eventTime;
                this.f.onSessionCreated(eventTime2, aVar2.b);
            } else {
                aVar2 = aVar;
                eventTime2 = eventTime;
            }
            if (aVar2.b.equals(this.h) && !aVar2.g) {
                aVar2.g = true;
                this.f.onSessionActive(eventTime2, aVar2.b);
            }
        }
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager
    public synchronized void updateSessionsWithTimelineChange(AnalyticsListener.EventTime eventTime) {
        Assertions.checkNotNull(this.f);
        Timeline timeline = this.g;
        this.g = eventTime.timeline;
        Iterator<a> it = this.d.values().iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!next.a(timeline, this.g)) {
                it.remove();
                if (next.f) {
                    if (next.b.equals(this.h)) {
                        this.h = null;
                    }
                    this.f.onSessionFinished(eventTime, next.b, false);
                }
            }
        }
        a(eventTime);
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager
    public synchronized void updateSessionsWithDiscontinuity(AnalyticsListener.EventTime eventTime, int i) {
        Assertions.checkNotNull(this.f);
        boolean z = i == 0;
        Iterator<a> it = this.d.values().iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next.a(eventTime)) {
                it.remove();
                if (next.f) {
                    boolean equals = next.b.equals(this.h);
                    boolean z2 = z && equals && next.g;
                    if (equals) {
                        this.h = null;
                    }
                    this.f.onSessionFinished(eventTime, next.b, z2);
                }
            }
        }
        a(eventTime);
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager
    @Nullable
    public synchronized String getActiveSessionId() {
        return this.h;
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager
    public synchronized void finishAllSessions(AnalyticsListener.EventTime eventTime) {
        this.h = null;
        Iterator<a> it = this.d.values().iterator();
        while (it.hasNext()) {
            a next = it.next();
            it.remove();
            if (next.f && this.f != null) {
                this.f.onSessionFinished(eventTime, next.b, false);
            }
        }
    }

    @RequiresNonNull({"listener"})
    private void a(AnalyticsListener.EventTime eventTime) {
        if (eventTime.timeline.isEmpty()) {
            this.h = null;
            return;
        }
        a aVar = this.d.get(this.h);
        a a2 = a(eventTime.windowIndex, eventTime.mediaPeriodId);
        this.h = a2.b;
        updateSessions(eventTime);
        if (eventTime.mediaPeriodId != null && eventTime.mediaPeriodId.isAd()) {
            if (aVar == null || aVar.d != eventTime.mediaPeriodId.windowSequenceNumber || aVar.e == null || aVar.e.adGroupIndex != eventTime.mediaPeriodId.adGroupIndex || aVar.e.adIndexInAdGroup != eventTime.mediaPeriodId.adIndexInAdGroup) {
                this.f.onAdPlaybackStarted(eventTime, a(eventTime.windowIndex, new MediaSource.MediaPeriodId(eventTime.mediaPeriodId.periodUid, eventTime.mediaPeriodId.windowSequenceNumber)).b, a2.b);
            }
        }
    }

    private a a(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        int i2;
        a aVar = null;
        long j = Long.MAX_VALUE;
        for (a aVar2 : this.d.values()) {
            aVar2.b(i, mediaPeriodId);
            if (aVar2.a(i, mediaPeriodId)) {
                long j2 = aVar2.d;
                if (j2 == -1 || j2 < j) {
                    aVar = aVar2;
                    j = j2;
                } else if (!(i2 != 0 || ((a) Util.castNonNull(aVar)).e == null || aVar2.e == null)) {
                    aVar = aVar2;
                }
            }
        }
        if (aVar != null) {
            return aVar;
        }
        String str = this.e.get();
        a aVar3 = new a(str, i, mediaPeriodId);
        this.d.put(str, aVar3);
        return aVar3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String a() {
        byte[] bArr = new byte[12];
        a.nextBytes(bArr);
        return Base64.encodeToString(bArr, 10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class a {
        private final String b;
        private int c;
        private long d;
        private MediaSource.MediaPeriodId e;
        private boolean f;
        private boolean g;

        public a(String str, int i, MediaSource.MediaPeriodId mediaPeriodId) {
            this.b = str;
            this.c = i;
            this.d = mediaPeriodId == null ? -1L : mediaPeriodId.windowSequenceNumber;
            if (mediaPeriodId != null && mediaPeriodId.isAd()) {
                this.e = mediaPeriodId;
            }
        }

        public boolean a(Timeline timeline, Timeline timeline2) {
            this.c = a(timeline, timeline2, this.c);
            if (this.c == -1) {
                return false;
            }
            MediaSource.MediaPeriodId mediaPeriodId = this.e;
            return mediaPeriodId == null || timeline2.getIndexOfPeriod(mediaPeriodId.periodUid) != -1;
        }

        public boolean a(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            return mediaPeriodId == null ? i == this.c : this.e == null ? !mediaPeriodId.isAd() && mediaPeriodId.windowSequenceNumber == this.d : mediaPeriodId.windowSequenceNumber == this.e.windowSequenceNumber && mediaPeriodId.adGroupIndex == this.e.adGroupIndex && mediaPeriodId.adIndexInAdGroup == this.e.adIndexInAdGroup;
        }

        public void b(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (this.d == -1 && i == this.c && mediaPeriodId != null) {
                this.d = mediaPeriodId.windowSequenceNumber;
            }
        }

        public boolean a(AnalyticsListener.EventTime eventTime) {
            if (this.d == -1) {
                return false;
            }
            if (eventTime.mediaPeriodId == null) {
                return this.c != eventTime.windowIndex;
            }
            if (eventTime.mediaPeriodId.windowSequenceNumber > this.d) {
                return true;
            }
            if (this.e == null) {
                return false;
            }
            int indexOfPeriod = eventTime.timeline.getIndexOfPeriod(eventTime.mediaPeriodId.periodUid);
            int indexOfPeriod2 = eventTime.timeline.getIndexOfPeriod(this.e.periodUid);
            if (eventTime.mediaPeriodId.windowSequenceNumber < this.e.windowSequenceNumber || indexOfPeriod < indexOfPeriod2) {
                return false;
            }
            if (indexOfPeriod > indexOfPeriod2) {
                return true;
            }
            if (!eventTime.mediaPeriodId.isAd()) {
                return eventTime.mediaPeriodId.nextAdGroupIndex == -1 || eventTime.mediaPeriodId.nextAdGroupIndex > this.e.adGroupIndex;
            }
            int i = eventTime.mediaPeriodId.adGroupIndex;
            return i > this.e.adGroupIndex || (i == this.e.adGroupIndex && eventTime.mediaPeriodId.adIndexInAdGroup > this.e.adIndexInAdGroup);
        }

        private int a(Timeline timeline, Timeline timeline2, int i) {
            if (i < timeline.getWindowCount()) {
                timeline.getWindow(i, DefaultPlaybackSessionManager.this.b);
                for (int i2 = DefaultPlaybackSessionManager.this.b.firstPeriodIndex; i2 <= DefaultPlaybackSessionManager.this.b.lastPeriodIndex; i2++) {
                    int indexOfPeriod = timeline2.getIndexOfPeriod(timeline.getUidOfPeriod(i2));
                    if (indexOfPeriod != -1) {
                        return timeline2.getPeriod(indexOfPeriod, DefaultPlaybackSessionManager.this.c).windowIndex;
                    }
                }
                return -1;
            } else if (i < timeline2.getWindowCount()) {
                return i;
            } else {
                return -1;
            }
        }
    }
}
