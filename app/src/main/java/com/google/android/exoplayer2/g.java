package com.google.android.exoplayer2;

import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.common.collect.ImmutableList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PlaybackInfo.java */
/* loaded from: classes.dex */
public final class g {
    private static final MediaSource.MediaPeriodId t = new MediaSource.MediaPeriodId(new Object());
    public final Timeline a;
    public final MediaSource.MediaPeriodId b;
    public final long c;
    public final long d;
    public final int e;
    @Nullable
    public final ExoPlaybackException f;
    public final boolean g;
    public final TrackGroupArray h;
    public final TrackSelectorResult i;
    public final List<Metadata> j;
    public final MediaSource.MediaPeriodId k;
    public final boolean l;
    public final int m;
    public final PlaybackParameters n;
    public final boolean o;
    public final boolean p;
    public volatile long q;
    public volatile long r;
    public volatile long s;

    public static g a(TrackSelectorResult trackSelectorResult) {
        return new g(Timeline.EMPTY, t, C.TIME_UNSET, 0L, 1, null, false, TrackGroupArray.EMPTY, trackSelectorResult, ImmutableList.of(), t, false, 0, PlaybackParameters.DEFAULT, 0L, 0L, 0L, false, false);
    }

    public g(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, long j, long j2, int i, @Nullable ExoPlaybackException exoPlaybackException, boolean z, TrackGroupArray trackGroupArray, TrackSelectorResult trackSelectorResult, List<Metadata> list, MediaSource.MediaPeriodId mediaPeriodId2, boolean z2, int i2, PlaybackParameters playbackParameters, long j3, long j4, long j5, boolean z3, boolean z4) {
        this.a = timeline;
        this.b = mediaPeriodId;
        this.c = j;
        this.d = j2;
        this.e = i;
        this.f = exoPlaybackException;
        this.g = z;
        this.h = trackGroupArray;
        this.i = trackSelectorResult;
        this.j = list;
        this.k = mediaPeriodId2;
        this.l = z2;
        this.m = i2;
        this.n = playbackParameters;
        this.q = j3;
        this.r = j4;
        this.s = j5;
        this.o = z3;
        this.p = z4;
    }

    public static MediaSource.MediaPeriodId a() {
        return t;
    }

    @CheckResult
    public g a(MediaSource.MediaPeriodId mediaPeriodId, long j, long j2, long j3, long j4, TrackGroupArray trackGroupArray, TrackSelectorResult trackSelectorResult, List<Metadata> list) {
        return new g(this.a, mediaPeriodId, j2, j3, this.e, this.f, this.g, trackGroupArray, trackSelectorResult, list, this.k, this.l, this.m, this.n, this.q, j4, j, this.o, this.p);
    }

    @CheckResult
    public g a(Timeline timeline) {
        return new g(timeline, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.q, this.r, this.s, this.o, this.p);
    }

    @CheckResult
    public g a(int i) {
        return new g(this.a, this.b, this.c, this.d, i, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.q, this.r, this.s, this.o, this.p);
    }

    @CheckResult
    public g a(@Nullable ExoPlaybackException exoPlaybackException) {
        return new g(this.a, this.b, this.c, this.d, this.e, exoPlaybackException, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.q, this.r, this.s, this.o, this.p);
    }

    @CheckResult
    public g a(boolean z) {
        return new g(this.a, this.b, this.c, this.d, this.e, this.f, z, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.q, this.r, this.s, this.o, this.p);
    }

    @CheckResult
    public g a(MediaSource.MediaPeriodId mediaPeriodId) {
        return new g(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, mediaPeriodId, this.l, this.m, this.n, this.q, this.r, this.s, this.o, this.p);
    }

    @CheckResult
    public g a(boolean z, int i) {
        return new g(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, z, i, this.n, this.q, this.r, this.s, this.o, this.p);
    }

    @CheckResult
    public g a(PlaybackParameters playbackParameters) {
        return new g(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, playbackParameters, this.q, this.r, this.s, this.o, this.p);
    }

    @CheckResult
    public g b(boolean z) {
        return new g(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.q, this.r, this.s, z, this.p);
    }

    @CheckResult
    public g c(boolean z) {
        return new g(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.q, this.r, this.s, this.o, z);
    }
}
