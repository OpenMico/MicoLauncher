package com.google.android.exoplayer2.analytics;

import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.PlaybackSessionManager;
import com.google.android.exoplayer2.analytics.PlaybackStats;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class PlaybackStatsListener implements AnalyticsListener, PlaybackSessionManager.Listener {
    @Nullable
    private final Callback d;
    private final boolean e;
    @Nullable
    private String h;
    private long i;
    private int j;
    private int k;
    @Nullable
    private Exception l;
    private long m;
    private long n;
    @Nullable
    private Format o;
    @Nullable
    private Format p;
    private final PlaybackSessionManager a = new DefaultPlaybackSessionManager();
    private final Map<String, a> b = new HashMap();
    private final Map<String, AnalyticsListener.EventTime> c = new HashMap();
    private PlaybackStats g = PlaybackStats.EMPTY;
    private final Timeline.Period f = new Timeline.Period();
    private VideoSize q = VideoSize.UNKNOWN;

    /* loaded from: classes.dex */
    public interface Callback {
        void onPlaybackStatsReady(AnalyticsListener.EventTime eventTime, PlaybackStats playbackStats);
    }

    public PlaybackStatsListener(boolean z, @Nullable Callback callback) {
        this.d = callback;
        this.e = z;
        this.a.setListener(this);
    }

    public PlaybackStats getCombinedPlaybackStats() {
        int i = 1;
        PlaybackStats[] playbackStatsArr = new PlaybackStats[this.b.size() + 1];
        playbackStatsArr[0] = this.g;
        for (a aVar : this.b.values()) {
            i++;
            playbackStatsArr[i] = aVar.a(false);
        }
        return PlaybackStats.merge(playbackStatsArr);
    }

    @Nullable
    public PlaybackStats getPlaybackStats() {
        String activeSessionId = this.a.getActiveSessionId();
        a aVar = activeSessionId == null ? null : this.b.get(activeSessionId);
        if (aVar == null) {
            return null;
        }
        return aVar.a(false);
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager.Listener
    public void onSessionCreated(AnalyticsListener.EventTime eventTime, String str) {
        this.b.put(str, new a(this.e, eventTime));
        this.c.put(str, eventTime);
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager.Listener
    public void onSessionActive(AnalyticsListener.EventTime eventTime, String str) {
        ((a) Assertions.checkNotNull(this.b.get(str))).a();
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager.Listener
    public void onAdPlaybackStarted(AnalyticsListener.EventTime eventTime, String str, String str2) {
        ((a) Assertions.checkNotNull(this.b.get(str))).b();
    }

    @Override // com.google.android.exoplayer2.analytics.PlaybackSessionManager.Listener
    public void onSessionFinished(AnalyticsListener.EventTime eventTime, String str, boolean z) {
        a aVar = (a) Assertions.checkNotNull(this.b.remove(str));
        AnalyticsListener.EventTime eventTime2 = (AnalyticsListener.EventTime) Assertions.checkNotNull(this.c.remove(str));
        aVar.a(eventTime, z, str.equals(this.h) ? this.i : C.TIME_UNSET);
        PlaybackStats a2 = aVar.a(true);
        this.g = PlaybackStats.merge(this.g, a2);
        Callback callback = this.d;
        if (callback != null) {
            callback.onPlaybackStatsReady(eventTime2, a2);
        }
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
        if (this.h == null) {
            this.h = this.a.getActiveSessionId();
            this.i = positionInfo.positionMs;
        }
        this.j = i;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int i, long j) {
        this.k = i;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadError(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        this.l = iOException;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmSessionManagerError(AnalyticsListener.EventTime eventTime, Exception exc) {
        this.l = exc;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int i, long j, long j2) {
        this.m = i;
        this.n = j;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDownstreamFormatChanged(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        if (mediaLoadData.trackType == 2 || mediaLoadData.trackType == 0) {
            this.o = mediaLoadData.trackFormat;
        } else if (mediaLoadData.trackType == 1) {
            this.p = mediaLoadData.trackFormat;
        }
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, VideoSize videoSize) {
        this.q = videoSize;
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onEvents(Player player, AnalyticsListener.Events events) {
        if (events.size() != 0) {
            a(events);
            for (String str : this.b.keySet()) {
                Pair<AnalyticsListener.EventTime, Boolean> a2 = a(events, str);
                a aVar = this.b.get(str);
                boolean a3 = a(events, str, 12);
                boolean a4 = a(events, str, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES);
                boolean a5 = a(events, str, 1012);
                boolean a6 = a(events, str, 1000);
                boolean a7 = a(events, str, 11);
                boolean z = a(events, str, 1003) || a(events, str, AnalyticsListener.EVENT_DRM_SESSION_MANAGER_ERROR);
                boolean a8 = a(events, str, 1006);
                boolean a9 = a(events, str, 1004);
                aVar.a(player, (AnalyticsListener.EventTime) a2.first, ((Boolean) a2.second).booleanValue(), str.equals(this.h) ? this.i : C.TIME_UNSET, a3, a4 ? this.k : 0, a5, a6, a7 ? player.getPlayerError() : null, z ? this.l : null, a8 ? this.m : 0L, a8 ? this.n : 0L, a9 ? this.o : null, a9 ? this.p : null, a(events, str, AnalyticsListener.EVENT_VIDEO_SIZE_CHANGED) ? this.q : null);
            }
            this.o = null;
            this.p = null;
            this.h = null;
            if (events.contains(AnalyticsListener.EVENT_PLAYER_RELEASED)) {
                this.a.finishAllSessions(events.getEventTime(AnalyticsListener.EVENT_PLAYER_RELEASED));
            }
        }
    }

    private void a(AnalyticsListener.Events events) {
        for (int i = 0; i < events.size(); i++) {
            int i2 = events.get(i);
            AnalyticsListener.EventTime eventTime = events.getEventTime(i2);
            if (i2 == 0) {
                this.a.updateSessionsWithTimelineChange(eventTime);
            } else if (i2 == 12) {
                this.a.updateSessionsWithDiscontinuity(eventTime, this.j);
            } else {
                this.a.updateSessions(eventTime);
            }
        }
    }

    private Pair<AnalyticsListener.EventTime, Boolean> a(AnalyticsListener.Events events, String str) {
        AnalyticsListener.EventTime eventTime;
        AnalyticsListener.EventTime eventTime2 = null;
        boolean z = false;
        for (int i = 0; i < events.size(); i++) {
            AnalyticsListener.EventTime eventTime3 = events.getEventTime(events.get(i));
            boolean belongsToSession = this.a.belongsToSession(eventTime3, str);
            if (eventTime2 == null || ((belongsToSession && !z) || (belongsToSession == z && eventTime3.realtimeMs > eventTime2.realtimeMs))) {
                eventTime2 = eventTime3;
                z = belongsToSession;
            }
        }
        Assertions.checkNotNull(eventTime2);
        if (z || eventTime2.mediaPeriodId == null || !eventTime2.mediaPeriodId.isAd()) {
            eventTime = eventTime2;
        } else {
            long adGroupTimeUs = eventTime2.timeline.getPeriodByUid(eventTime2.mediaPeriodId.periodUid, this.f).getAdGroupTimeUs(eventTime2.mediaPeriodId.adGroupIndex);
            if (adGroupTimeUs == Long.MIN_VALUE) {
                adGroupTimeUs = this.f.durationUs;
            }
            eventTime = new AnalyticsListener.EventTime(eventTime2.realtimeMs, eventTime2.timeline, eventTime2.windowIndex, new MediaSource.MediaPeriodId(eventTime2.mediaPeriodId.periodUid, eventTime2.mediaPeriodId.windowSequenceNumber, eventTime2.mediaPeriodId.adGroupIndex), C.usToMs(adGroupTimeUs + this.f.getPositionInWindowUs()), eventTime2.timeline, eventTime2.currentWindowIndex, eventTime2.currentMediaPeriodId, eventTime2.currentPlaybackPositionMs, eventTime2.totalBufferedDurationMs);
            z = this.a.belongsToSession(eventTime, str);
        }
        return Pair.create(eventTime, Boolean.valueOf(z));
    }

    private boolean a(AnalyticsListener.Events events, String str, int i) {
        return events.contains(i) && this.a.belongsToSession(events.getEventTime(i), str);
    }

    /* loaded from: classes.dex */
    private static final class a {
        private long A;
        private long B;
        private long C;
        private long D;
        private long E;
        private int F;
        private int G;
        private int H;
        private long I;
        private boolean J;
        private boolean K;
        private boolean L;
        private boolean M;
        private boolean N;
        private long O;
        @Nullable
        private Format P;
        @Nullable
        private Format Q;
        private long R;
        private long S;
        private float T;
        private final boolean a;
        private final long[] b = new long[16];
        private final List<PlaybackStats.EventTimeAndPlaybackState> c;
        private final List<long[]> d;
        private final List<PlaybackStats.EventTimeAndFormat> e;
        private final List<PlaybackStats.EventTimeAndFormat> f;
        private final List<PlaybackStats.EventTimeAndException> g;
        private final List<PlaybackStats.EventTimeAndException> h;
        private final boolean i;
        private long j;
        private boolean k;
        private boolean l;
        private boolean m;
        private int n;
        private int o;
        private int p;
        private int q;
        private long r;
        private int s;
        private long t;
        private long u;
        private long v;
        private long w;
        private long x;
        private long y;
        private long z;

        private static boolean a(int i) {
            return i == 3 || i == 4 || i == 9;
        }

        private static boolean a(int i, int i2) {
            return ((i != 1 && i != 2 && i != 14) || i2 == 1 || i2 == 2 || i2 == 14 || i2 == 3 || i2 == 4 || i2 == 9 || i2 == 11) ? false : true;
        }

        private static boolean b(int i) {
            return i == 4 || i == 7;
        }

        private static boolean c(int i) {
            return i == 6 || i == 7 || i == 10;
        }

        public a(boolean z, AnalyticsListener.EventTime eventTime) {
            this.a = z;
            this.c = z ? new ArrayList<>() : Collections.emptyList();
            this.d = z ? new ArrayList<>() : Collections.emptyList();
            this.e = z ? new ArrayList<>() : Collections.emptyList();
            this.f = z ? new ArrayList<>() : Collections.emptyList();
            this.g = z ? new ArrayList<>() : Collections.emptyList();
            this.h = z ? new ArrayList<>() : Collections.emptyList();
            boolean z2 = false;
            this.H = 0;
            this.I = eventTime.realtimeMs;
            this.j = C.TIME_UNSET;
            this.r = C.TIME_UNSET;
            if (eventTime.mediaPeriodId != null && eventTime.mediaPeriodId.isAd()) {
                z2 = true;
            }
            this.i = z2;
            this.u = -1L;
            this.t = -1L;
            this.s = -1;
            this.T = 1.0f;
        }

        public void a() {
            this.K = true;
        }

        public void b() {
            this.L = true;
            this.J = false;
        }

        public void a(AnalyticsListener.EventTime eventTime, boolean z, long j) {
            int i = 11;
            if (this.H != 11 && !z) {
                i = 15;
            }
            a(eventTime.realtimeMs, j);
            c(eventTime.realtimeMs);
            d(eventTime.realtimeMs);
            a(i, eventTime);
        }

        public void a(Player player, AnalyticsListener.EventTime eventTime, boolean z, long j, boolean z2, int i, boolean z3, boolean z4, @Nullable PlaybackException playbackException, @Nullable Exception exc, long j2, long j3, @Nullable Format format, @Nullable Format format2, @Nullable VideoSize videoSize) {
            if (j != C.TIME_UNSET) {
                a(eventTime.realtimeMs, j);
                this.J = true;
            }
            if (player.getPlaybackState() != 2) {
                this.J = false;
            }
            int playbackState = player.getPlaybackState();
            if (playbackState == 1 || playbackState == 4 || z2) {
                this.L = false;
            }
            if (playbackException != null) {
                this.M = true;
                this.F++;
                if (this.a) {
                    this.g.add(new PlaybackStats.EventTimeAndException(eventTime, playbackException));
                }
            } else if (player.getPlayerError() == null) {
                this.M = false;
            }
            if (this.K && !this.L) {
                TrackSelection[] all = player.getCurrentTrackSelections().getAll();
                boolean z5 = false;
                boolean z6 = false;
                for (TrackSelection trackSelection : all) {
                    if (trackSelection != null && trackSelection.length() > 0) {
                        int trackType = MimeTypes.getTrackType(trackSelection.getFormat(0).sampleMimeType);
                        if (trackType == 2) {
                            z5 = true;
                        } else if (trackType == 1) {
                            z6 = true;
                        }
                    }
                }
                if (!z5) {
                    a(eventTime, (Format) null);
                }
                if (!z6) {
                    b(eventTime, null);
                }
            }
            if (format != null) {
                a(eventTime, format);
            }
            if (format2 != null) {
                b(eventTime, format2);
            }
            Format format3 = this.P;
            if (!(format3 == null || format3.height != -1 || videoSize == null)) {
                a(eventTime, this.P.buildUpon().setWidth(videoSize.width).setHeight(videoSize.height).build());
            }
            if (z4) {
                this.N = true;
            }
            if (z3) {
                this.E++;
            }
            this.D += i;
            this.B += j2;
            this.C += j3;
            if (exc != null) {
                this.G++;
                if (this.a) {
                    this.h.add(new PlaybackStats.EventTimeAndException(eventTime, exc));
                }
            }
            int a = a(player);
            float f = player.getPlaybackParameters().speed;
            if (!(this.H == a && this.T == f)) {
                a(eventTime.realtimeMs, z ? eventTime.eventPlaybackPositionMs : C.TIME_UNSET);
                c(eventTime.realtimeMs);
                d(eventTime.realtimeMs);
            }
            this.T = f;
            if (this.H != a) {
                a(a, eventTime);
            }
        }

        public PlaybackStats a(boolean z) {
            ArrayList arrayList;
            long[] jArr;
            List list;
            List list2;
            List list3;
            long[] jArr2 = this.b;
            List<long[]> list4 = this.d;
            if (!z) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                long[] copyOf = Arrays.copyOf(this.b, 16);
                long max = Math.max(0L, elapsedRealtime - this.I);
                int i = this.H;
                copyOf[i] = copyOf[i] + max;
                a(elapsedRealtime);
                c(elapsedRealtime);
                d(elapsedRealtime);
                ArrayList arrayList2 = new ArrayList(this.d);
                if (this.a && this.H == 3) {
                    arrayList2.add(b(elapsedRealtime));
                }
                jArr = copyOf;
                arrayList = arrayList2;
            } else {
                jArr = jArr2;
                arrayList = list4;
            }
            int i2 = (this.m || !this.k) ? 1 : 0;
            long j = i2 != 0 ? C.TIME_UNSET : jArr[2];
            int i3 = jArr[1] > 0 ? 1 : 0;
            if (z) {
                list = this.e;
            } else {
                list = new ArrayList(this.e);
            }
            if (z) {
                list2 = this.f;
            } else {
                list2 = new ArrayList(this.f);
            }
            if (z) {
                list3 = this.c;
            } else {
                list3 = new ArrayList(this.c);
            }
            long j2 = this.j;
            boolean z2 = this.K;
            int i4 = !this.k ? 1 : 0;
            boolean z3 = this.l;
            return new PlaybackStats(1, jArr, list3, arrayList, j2, z2 ? 1 : 0, i4, z3 ? 1 : 0, i3, j, i2 ^ 1, this.n, this.o, this.p, this.q, this.r, this.i ? 1 : 0, list, list2, this.v, this.w, this.x, this.y, this.z, this.A, this.s == -1 ? 0 : 1, this.t == -1 ? 0 : 1, this.s, this.t, this.u == -1 ? 0 : 1, this.u, this.B, this.C, this.D, this.E, this.F > 0 ? 1 : 0, this.F, this.G, this.g, this.h);
        }

        private void a(int i, AnalyticsListener.EventTime eventTime) {
            boolean z = false;
            Assertions.checkArgument(eventTime.realtimeMs >= this.I);
            long j = eventTime.realtimeMs - this.I;
            long[] jArr = this.b;
            int i2 = this.H;
            jArr[i2] = jArr[i2] + j;
            if (this.j == C.TIME_UNSET) {
                this.j = eventTime.realtimeMs;
            }
            this.m |= a(this.H, i);
            this.k |= a(i);
            boolean z2 = this.l;
            if (i == 11) {
                z = true;
            }
            this.l = z2 | z;
            if (!b(this.H) && b(i)) {
                this.n++;
            }
            if (i == 5) {
                this.p++;
            }
            if (!c(this.H) && c(i)) {
                this.q++;
                this.O = eventTime.realtimeMs;
            }
            if (c(this.H) && this.H != 7 && i == 7) {
                this.o++;
            }
            a(eventTime.realtimeMs);
            this.H = i;
            this.I = eventTime.realtimeMs;
            if (this.a) {
                this.c.add(new PlaybackStats.EventTimeAndPlaybackState(eventTime, this.H));
            }
        }

        private int a(Player player) {
            int playbackState = player.getPlaybackState();
            if (this.J && this.K) {
                return 5;
            }
            if (this.M) {
                return 13;
            }
            if (!this.K) {
                return this.N ? 1 : 0;
            }
            if (this.L) {
                return 14;
            }
            if (playbackState == 4) {
                return 11;
            }
            if (playbackState == 2) {
                int i = this.H;
                if (i == 0 || i == 1 || i == 2 || i == 14) {
                    return 2;
                }
                if (!player.getPlayWhenReady()) {
                    return 7;
                }
                return player.getPlaybackSuppressionReason() != 0 ? 10 : 6;
            } else if (playbackState == 3) {
                if (!player.getPlayWhenReady()) {
                    return 4;
                }
                return player.getPlaybackSuppressionReason() != 0 ? 9 : 3;
            } else if (playbackState != 1 || this.H == 0) {
                return this.H;
            } else {
                return 12;
            }
        }

        private void a(long j) {
            if (c(this.H)) {
                long j2 = j - this.O;
                long j3 = this.r;
                if (j3 == C.TIME_UNSET || j2 > j3) {
                    this.r = j2;
                }
            }
        }

        private void a(long j, long j2) {
            if (this.a) {
                if (this.H != 3) {
                    if (j2 != C.TIME_UNSET) {
                        if (!this.d.isEmpty()) {
                            List<long[]> list = this.d;
                            long j3 = list.get(list.size() - 1)[1];
                            if (j3 != j2) {
                                this.d.add(new long[]{j, j3});
                            }
                        }
                    } else {
                        return;
                    }
                }
                if (j2 != C.TIME_UNSET) {
                    this.d.add(new long[]{j, j2});
                } else if (!this.d.isEmpty()) {
                    this.d.add(b(j));
                }
            }
        }

        private long[] b(long j) {
            List<long[]> list = this.d;
            long[] jArr = list.get(list.size() - 1);
            return new long[]{j, jArr[1] + (((float) (j - jArr[0])) * this.T)};
        }

        private void a(AnalyticsListener.EventTime eventTime, @Nullable Format format) {
            if (!Util.areEqual(this.P, format)) {
                c(eventTime.realtimeMs);
                if (format != null) {
                    if (this.s == -1 && format.height != -1) {
                        this.s = format.height;
                    }
                    if (this.t == -1 && format.bitrate != -1) {
                        this.t = format.bitrate;
                    }
                }
                this.P = format;
                if (this.a) {
                    this.e.add(new PlaybackStats.EventTimeAndFormat(eventTime, this.P));
                }
            }
        }

        private void b(AnalyticsListener.EventTime eventTime, @Nullable Format format) {
            if (!Util.areEqual(this.Q, format)) {
                d(eventTime.realtimeMs);
                if (!(format == null || this.u != -1 || format.bitrate == -1)) {
                    this.u = format.bitrate;
                }
                this.Q = format;
                if (this.a) {
                    this.f.add(new PlaybackStats.EventTimeAndFormat(eventTime, this.Q));
                }
            }
        }

        private void c(long j) {
            Format format;
            if (this.H == 3 && (format = this.P) != null) {
                long j2 = ((float) (j - this.R)) * this.T;
                if (format.height != -1) {
                    this.v += j2;
                    this.w += this.P.height * j2;
                }
                if (this.P.bitrate != -1) {
                    this.x += j2;
                    this.y += j2 * this.P.bitrate;
                }
            }
            this.R = j;
        }

        private void d(long j) {
            Format format;
            if (!(this.H != 3 || (format = this.Q) == null || format.bitrate == -1)) {
                long j2 = ((float) (j - this.S)) * this.T;
                this.z += j2;
                this.A += j2 * this.Q.bitrate;
            }
            this.S = j;
        }
    }
}
