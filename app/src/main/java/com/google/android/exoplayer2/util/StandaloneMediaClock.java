package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.PlaybackParameters;

/* loaded from: classes2.dex */
public final class StandaloneMediaClock implements MediaClock {
    private final Clock a;
    private boolean b;
    private long c;
    private long d;
    private PlaybackParameters e = PlaybackParameters.DEFAULT;

    public StandaloneMediaClock(Clock clock) {
        this.a = clock;
    }

    public void start() {
        if (!this.b) {
            this.d = this.a.elapsedRealtime();
            this.b = true;
        }
    }

    public void stop() {
        if (this.b) {
            resetPosition(getPositionUs());
            this.b = false;
        }
    }

    public void resetPosition(long j) {
        this.c = j;
        if (this.b) {
            this.d = this.a.elapsedRealtime();
        }
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public long getPositionUs() {
        long j = this.c;
        if (!this.b) {
            return j;
        }
        long elapsedRealtime = this.a.elapsedRealtime() - this.d;
        if (this.e.speed == 1.0f) {
            return j + C.msToUs(elapsedRealtime);
        }
        return j + this.e.getMediaTimeUsForPlayoutTimeMs(elapsedRealtime);
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (this.b) {
            resetPosition(getPositionUs());
        }
        this.e = playbackParameters;
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public PlaybackParameters getPlaybackParameters() {
        return this.e;
    }
}
