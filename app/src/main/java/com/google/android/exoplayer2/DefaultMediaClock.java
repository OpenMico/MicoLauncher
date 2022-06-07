package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.StandaloneMediaClock;

/* loaded from: classes.dex */
final class DefaultMediaClock implements MediaClock {
    private final StandaloneMediaClock a;
    private final PlaybackParametersListener b;
    @Nullable
    private Renderer c;
    @Nullable
    private MediaClock d;
    private boolean e = true;
    private boolean f;

    /* loaded from: classes.dex */
    public interface PlaybackParametersListener {
        void onPlaybackParametersChanged(PlaybackParameters playbackParameters);
    }

    public DefaultMediaClock(PlaybackParametersListener playbackParametersListener, Clock clock) {
        this.b = playbackParametersListener;
        this.a = new StandaloneMediaClock(clock);
    }

    public void a() {
        this.f = true;
        this.a.start();
    }

    public void b() {
        this.f = false;
        this.a.stop();
    }

    public void a(long j) {
        this.a.resetPosition(j);
    }

    public void a(Renderer renderer) throws ExoPlaybackException {
        MediaClock mediaClock;
        MediaClock mediaClock2 = renderer.getMediaClock();
        if (mediaClock2 != null && mediaClock2 != (mediaClock = this.d)) {
            if (mediaClock == null) {
                this.d = mediaClock2;
                this.c = renderer;
                this.d.setPlaybackParameters(this.a.getPlaybackParameters());
                return;
            }
            throw ExoPlaybackException.createForUnexpected(new IllegalStateException("Multiple renderer media clocks enabled."));
        }
    }

    public void b(Renderer renderer) {
        if (renderer == this.c) {
            this.d = null;
            this.c = null;
            this.e = true;
        }
    }

    public long a(boolean z) {
        b(z);
        return getPositionUs();
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public long getPositionUs() {
        if (this.e) {
            return this.a.getPositionUs();
        }
        return ((MediaClock) Assertions.checkNotNull(this.d)).getPositionUs();
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        MediaClock mediaClock = this.d;
        if (mediaClock != null) {
            mediaClock.setPlaybackParameters(playbackParameters);
            playbackParameters = this.d.getPlaybackParameters();
        }
        this.a.setPlaybackParameters(playbackParameters);
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public PlaybackParameters getPlaybackParameters() {
        MediaClock mediaClock = this.d;
        if (mediaClock != null) {
            return mediaClock.getPlaybackParameters();
        }
        return this.a.getPlaybackParameters();
    }

    private void b(boolean z) {
        if (c(z)) {
            this.e = true;
            if (this.f) {
                this.a.start();
                return;
            }
            return;
        }
        MediaClock mediaClock = (MediaClock) Assertions.checkNotNull(this.d);
        long positionUs = mediaClock.getPositionUs();
        if (this.e) {
            if (positionUs < this.a.getPositionUs()) {
                this.a.stop();
                return;
            }
            this.e = false;
            if (this.f) {
                this.a.start();
            }
        }
        this.a.resetPosition(positionUs);
        PlaybackParameters playbackParameters = mediaClock.getPlaybackParameters();
        if (!playbackParameters.equals(this.a.getPlaybackParameters())) {
            this.a.setPlaybackParameters(playbackParameters);
            this.b.onPlaybackParametersChanged(playbackParameters);
        }
    }

    private boolean c(boolean z) {
        Renderer renderer = this.c;
        return renderer == null || renderer.isEnded() || (!this.c.isReady() && (z || this.c.hasReadStreamToEnd()));
    }
}
