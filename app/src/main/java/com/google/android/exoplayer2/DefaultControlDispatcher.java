package com.google.android.exoplayer2;

@Deprecated
/* loaded from: classes.dex */
public class DefaultControlDispatcher implements ControlDispatcher {
    private final long a;
    private final long b;
    private final boolean c;

    public DefaultControlDispatcher() {
        this.b = C.TIME_UNSET;
        this.a = C.TIME_UNSET;
        this.c = false;
    }

    public DefaultControlDispatcher(long j, long j2) {
        this.b = j;
        this.a = j2;
        this.c = true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchPrepare(Player player) {
        player.prepare();
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchSetPlayWhenReady(Player player, boolean z) {
        player.setPlayWhenReady(z);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchSeekTo(Player player, int i, long j) {
        player.seekTo(i, j);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchPrevious(Player player) {
        player.seekToPrevious();
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchNext(Player player) {
        player.seekToNext();
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchRewind(Player player) {
        if (!this.c) {
            player.seekBack();
            return true;
        } else if (!isRewindEnabled() || !player.isCurrentWindowSeekable()) {
            return true;
        } else {
            a(player, -this.a);
            return true;
        }
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchFastForward(Player player) {
        if (!this.c) {
            player.seekForward();
            return true;
        } else if (!isFastForwardEnabled() || !player.isCurrentWindowSeekable()) {
            return true;
        } else {
            a(player, this.b);
            return true;
        }
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchSetRepeatMode(Player player, int i) {
        player.setRepeatMode(i);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchSetShuffleModeEnabled(Player player, boolean z) {
        player.setShuffleModeEnabled(z);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchStop(Player player, boolean z) {
        player.stop(z);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean dispatchSetPlaybackParameters(Player player, PlaybackParameters playbackParameters) {
        player.setPlaybackParameters(playbackParameters);
        return true;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean isRewindEnabled() {
        return !this.c || this.a > 0;
    }

    @Override // com.google.android.exoplayer2.ControlDispatcher
    public boolean isFastForwardEnabled() {
        return !this.c || this.b > 0;
    }

    public long getRewindIncrementMs(Player player) {
        return this.c ? this.a : player.getSeekBackIncrement();
    }

    public long getFastForwardIncrementMs(Player player) {
        if (this.c) {
            return this.b;
        }
        return player.getSeekForwardIncrement();
    }

    private static void a(Player player, long j) {
        long currentPosition = player.getCurrentPosition() + j;
        long duration = player.getDuration();
        if (duration != C.TIME_UNSET) {
            currentPosition = Math.min(currentPosition, duration);
        }
        player.seekTo(Math.max(currentPosition, 0L));
    }
}
