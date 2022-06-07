package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.ClippingMediaPeriod;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MediaPeriodHolder.java */
/* loaded from: classes.dex */
public final class b {
    public final MediaPeriod a;
    public final Object b;
    public final SampleStream[] c;
    public boolean d;
    public boolean e;
    public d f;
    public boolean g;
    private final boolean[] h;
    private final RendererCapabilities[] i;
    private final TrackSelector j;
    private final MediaSourceList k;
    @Nullable
    private b l;
    private TrackGroupArray m = TrackGroupArray.EMPTY;
    private TrackSelectorResult n;
    private long o;

    public b(RendererCapabilities[] rendererCapabilitiesArr, long j, TrackSelector trackSelector, Allocator allocator, MediaSourceList mediaSourceList, d dVar, TrackSelectorResult trackSelectorResult) {
        this.i = rendererCapabilitiesArr;
        this.o = j;
        this.j = trackSelector;
        this.k = mediaSourceList;
        this.b = dVar.a.periodUid;
        this.f = dVar;
        this.n = trackSelectorResult;
        this.c = new SampleStream[rendererCapabilitiesArr.length];
        this.h = new boolean[rendererCapabilitiesArr.length];
        this.a = a(dVar.a, mediaSourceList, allocator, dVar.b, dVar.d);
    }

    public long a(long j) {
        return j + a();
    }

    public long b(long j) {
        return j - a();
    }

    public long a() {
        return this.o;
    }

    public void c(long j) {
        this.o = j;
    }

    public long b() {
        return this.f.b + this.o;
    }

    public boolean c() {
        return this.d && (!this.e || this.a.getBufferedPositionUs() == Long.MIN_VALUE);
    }

    public long d() {
        if (!this.d) {
            return this.f.b;
        }
        long bufferedPositionUs = this.e ? this.a.getBufferedPositionUs() : Long.MIN_VALUE;
        return bufferedPositionUs == Long.MIN_VALUE ? this.f.e : bufferedPositionUs;
    }

    public long e() {
        if (!this.d) {
            return 0L;
        }
        return this.a.getNextLoadPositionUs();
    }

    public void a(float f, Timeline timeline) throws ExoPlaybackException {
        this.d = true;
        this.m = this.a.getTrackGroups();
        TrackSelectorResult b = b(f, timeline);
        long j = this.f.b;
        if (this.f.e != C.TIME_UNSET && j >= this.f.e) {
            j = Math.max(0L, this.f.e - 1);
        }
        long a = a(b, j, false);
        this.o += this.f.b - a;
        this.f = this.f.a(a);
    }

    public void d(long j) {
        Assertions.checkState(m());
        if (this.d) {
            this.a.reevaluateBuffer(b(j));
        }
    }

    public void e(long j) {
        Assertions.checkState(m());
        this.a.continueLoading(b(j));
    }

    public TrackSelectorResult b(float f, Timeline timeline) throws ExoPlaybackException {
        TrackSelectorResult selectTracks = this.j.selectTracks(this.i, h(), this.f.a, timeline);
        ExoTrackSelection[] exoTrackSelectionArr = selectTracks.selections;
        for (ExoTrackSelection exoTrackSelection : exoTrackSelectionArr) {
            if (exoTrackSelection != null) {
                exoTrackSelection.onPlaybackSpeed(f);
            }
        }
        return selectTracks;
    }

    public long a(TrackSelectorResult trackSelectorResult, long j, boolean z) {
        return a(trackSelectorResult, j, z, new boolean[this.i.length]);
    }

    public long a(TrackSelectorResult trackSelectorResult, long j, boolean z, boolean[] zArr) {
        int i = 0;
        while (true) {
            boolean z2 = true;
            if (i >= trackSelectorResult.length) {
                break;
            }
            boolean[] zArr2 = this.h;
            if (z || !trackSelectorResult.isEquivalent(this.n, i)) {
                z2 = false;
            }
            zArr2[i] = z2;
            i++;
        }
        a(this.c);
        l();
        this.n = trackSelectorResult;
        k();
        long selectTracks = this.a.selectTracks(trackSelectorResult.selections, this.h, this.c, zArr, j);
        b(this.c);
        this.e = false;
        int i2 = 0;
        while (true) {
            SampleStream[] sampleStreamArr = this.c;
            if (i2 >= sampleStreamArr.length) {
                return selectTracks;
            }
            if (sampleStreamArr[i2] != null) {
                Assertions.checkState(trackSelectorResult.isRendererEnabled(i2));
                if (this.i[i2].getTrackType() != 7) {
                    this.e = true;
                }
            } else {
                Assertions.checkState(trackSelectorResult.selections[i2] == null);
            }
            i2++;
        }
    }

    public void f() {
        l();
        a(this.k, this.a);
    }

    public void a(@Nullable b bVar) {
        if (bVar != this.l) {
            l();
            this.l = bVar;
            k();
        }
    }

    @Nullable
    public b g() {
        return this.l;
    }

    public TrackGroupArray h() {
        return this.m;
    }

    public TrackSelectorResult i() {
        return this.n;
    }

    public void j() {
        if (this.a instanceof ClippingMediaPeriod) {
            ((ClippingMediaPeriod) this.a).updateClipping(0L, this.f.d == C.TIME_UNSET ? Long.MIN_VALUE : this.f.d);
        }
    }

    private void k() {
        if (m()) {
            for (int i = 0; i < this.n.length; i++) {
                boolean isRendererEnabled = this.n.isRendererEnabled(i);
                ExoTrackSelection exoTrackSelection = this.n.selections[i];
                if (isRendererEnabled && exoTrackSelection != null) {
                    exoTrackSelection.enable();
                }
            }
        }
    }

    private void l() {
        if (m()) {
            for (int i = 0; i < this.n.length; i++) {
                boolean isRendererEnabled = this.n.isRendererEnabled(i);
                ExoTrackSelection exoTrackSelection = this.n.selections[i];
                if (isRendererEnabled && exoTrackSelection != null) {
                    exoTrackSelection.disable();
                }
            }
        }
    }

    private void a(SampleStream[] sampleStreamArr) {
        int i = 0;
        while (true) {
            RendererCapabilities[] rendererCapabilitiesArr = this.i;
            if (i < rendererCapabilitiesArr.length) {
                if (rendererCapabilitiesArr[i].getTrackType() == 7) {
                    sampleStreamArr[i] = null;
                }
                i++;
            } else {
                return;
            }
        }
    }

    private void b(SampleStream[] sampleStreamArr) {
        int i = 0;
        while (true) {
            RendererCapabilities[] rendererCapabilitiesArr = this.i;
            if (i < rendererCapabilitiesArr.length) {
                if (rendererCapabilitiesArr[i].getTrackType() == 7 && this.n.isRendererEnabled(i)) {
                    sampleStreamArr[i] = new EmptySampleStream();
                }
                i++;
            } else {
                return;
            }
        }
    }

    private boolean m() {
        return this.l == null;
    }

    private static MediaPeriod a(MediaSource.MediaPeriodId mediaPeriodId, MediaSourceList mediaSourceList, Allocator allocator, long j, long j2) {
        MediaPeriod a = mediaSourceList.a(mediaPeriodId, allocator, j);
        return j2 != C.TIME_UNSET ? new ClippingMediaPeriod(a, true, 0L, j2) : a;
    }

    private static void a(MediaSourceList mediaSourceList, MediaPeriod mediaPeriod) {
        try {
            if (mediaPeriod instanceof ClippingMediaPeriod) {
                mediaSourceList.a(((ClippingMediaPeriod) mediaPeriod).mediaPeriod);
            } else {
                mediaSourceList.a(mediaPeriod);
            }
        } catch (RuntimeException e) {
            Log.e("MediaPeriodHolder", "Period release failed.", e);
        }
    }
}
