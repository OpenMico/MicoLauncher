package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BasePlayer implements Player {
    protected final Timeline.Window window = new Timeline.Window();

    @Override // com.google.android.exoplayer2.Player
    public final void setMediaItem(MediaItem mediaItem) {
        setMediaItems(Collections.singletonList(mediaItem));
    }

    @Override // com.google.android.exoplayer2.Player
    public final void setMediaItem(MediaItem mediaItem, long j) {
        setMediaItems(Collections.singletonList(mediaItem), 0, j);
    }

    @Override // com.google.android.exoplayer2.Player
    public final void setMediaItem(MediaItem mediaItem, boolean z) {
        setMediaItems(Collections.singletonList(mediaItem), z);
    }

    @Override // com.google.android.exoplayer2.Player
    public final void setMediaItems(List<MediaItem> list) {
        setMediaItems(list, true);
    }

    @Override // com.google.android.exoplayer2.Player
    public final void addMediaItem(int i, MediaItem mediaItem) {
        addMediaItems(i, Collections.singletonList(mediaItem));
    }

    @Override // com.google.android.exoplayer2.Player
    public final void addMediaItem(MediaItem mediaItem) {
        addMediaItems(Collections.singletonList(mediaItem));
    }

    @Override // com.google.android.exoplayer2.Player
    public final void addMediaItems(List<MediaItem> list) {
        addMediaItems(Integer.MAX_VALUE, list);
    }

    @Override // com.google.android.exoplayer2.Player
    public final void moveMediaItem(int i, int i2) {
        if (i != i2) {
            moveMediaItems(i, i + 1, i2);
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public final void removeMediaItem(int i) {
        removeMediaItems(i, i + 1);
    }

    @Override // com.google.android.exoplayer2.Player
    public final void clearMediaItems() {
        removeMediaItems(0, Integer.MAX_VALUE);
    }

    @Override // com.google.android.exoplayer2.Player
    public final boolean isCommandAvailable(int i) {
        return getAvailableCommands().contains(i);
    }

    @Override // com.google.android.exoplayer2.Player
    public final void play() {
        setPlayWhenReady(true);
    }

    @Override // com.google.android.exoplayer2.Player
    public final void pause() {
        setPlayWhenReady(false);
    }

    @Override // com.google.android.exoplayer2.Player
    public final boolean isPlaying() {
        return getPlaybackState() == 3 && getPlayWhenReady() && getPlaybackSuppressionReason() == 0;
    }

    @Override // com.google.android.exoplayer2.Player
    public final void seekToDefaultPosition() {
        seekToDefaultPosition(getCurrentWindowIndex());
    }

    @Override // com.google.android.exoplayer2.Player
    public final void seekToDefaultPosition(int i) {
        seekTo(i, C.TIME_UNSET);
    }

    @Override // com.google.android.exoplayer2.Player
    public final void seekTo(long j) {
        seekTo(getCurrentWindowIndex(), j);
    }

    @Override // com.google.android.exoplayer2.Player
    public final void seekBack() {
        a(-getSeekBackIncrement());
    }

    @Override // com.google.android.exoplayer2.Player
    public final void seekForward() {
        a(getSeekForwardIncrement());
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public final boolean hasPrevious() {
        return hasPreviousWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    public final boolean hasPreviousWindow() {
        return getPreviousWindowIndex() != -1;
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public final void previous() {
        seekToPreviousWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    public final void seekToPreviousWindow() {
        int previousWindowIndex = getPreviousWindowIndex();
        if (previousWindowIndex != -1) {
            seekToDefaultPosition(previousWindowIndex);
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public final void seekToPrevious() {
        if (!getCurrentTimeline().isEmpty() && !isPlayingAd()) {
            boolean hasPreviousWindow = hasPreviousWindow();
            if (!isCurrentWindowLive() || isCurrentWindowSeekable()) {
                if (!hasPreviousWindow || getCurrentPosition() > getMaxSeekToPreviousPosition()) {
                    seekTo(0L);
                } else {
                    seekToPreviousWindow();
                }
            } else if (hasPreviousWindow) {
                seekToPreviousWindow();
            }
        }
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public final boolean hasNext() {
        return hasNextWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    public final boolean hasNextWindow() {
        return getNextWindowIndex() != -1;
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public final void next() {
        seekToNextWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    public final void seekToNextWindow() {
        int nextWindowIndex = getNextWindowIndex();
        if (nextWindowIndex != -1) {
            seekToDefaultPosition(nextWindowIndex);
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public final void seekToNext() {
        if (!getCurrentTimeline().isEmpty() && !isPlayingAd()) {
            if (hasNextWindow()) {
                seekToNextWindow();
            } else if (isCurrentWindowLive() && isCurrentWindowDynamic()) {
                seekToDefaultPosition();
            }
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public final void setPlaybackSpeed(float f) {
        setPlaybackParameters(getPlaybackParameters().withSpeed(f));
    }

    @Override // com.google.android.exoplayer2.Player
    public final void stop() {
        stop(false);
    }

    @Override // com.google.android.exoplayer2.Player
    public final int getNextWindowIndex() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return -1;
        }
        return currentTimeline.getNextWindowIndex(getCurrentWindowIndex(), a(), getShuffleModeEnabled());
    }

    @Override // com.google.android.exoplayer2.Player
    public final int getPreviousWindowIndex() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return -1;
        }
        return currentTimeline.getPreviousWindowIndex(getCurrentWindowIndex(), a(), getShuffleModeEnabled());
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public final MediaItem getCurrentMediaItem() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return null;
        }
        return currentTimeline.getWindow(getCurrentWindowIndex(), this.window).mediaItem;
    }

    @Override // com.google.android.exoplayer2.Player
    public final int getMediaItemCount() {
        return getCurrentTimeline().getWindowCount();
    }

    @Override // com.google.android.exoplayer2.Player
    public final MediaItem getMediaItemAt(int i) {
        return getCurrentTimeline().getWindow(i, this.window).mediaItem;
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public final Object getCurrentManifest() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return null;
        }
        return currentTimeline.getWindow(getCurrentWindowIndex(), this.window).manifest;
    }

    @Override // com.google.android.exoplayer2.Player
    public final int getBufferedPercentage() {
        long bufferedPosition = getBufferedPosition();
        long duration = getDuration();
        if (bufferedPosition == C.TIME_UNSET || duration == C.TIME_UNSET) {
            return 0;
        }
        if (duration == 0) {
            return 100;
        }
        return Util.constrainValue((int) ((bufferedPosition * 100) / duration), 0, 100);
    }

    @Override // com.google.android.exoplayer2.Player
    public final boolean isCurrentWindowDynamic() {
        Timeline currentTimeline = getCurrentTimeline();
        return !currentTimeline.isEmpty() && currentTimeline.getWindow(getCurrentWindowIndex(), this.window).isDynamic;
    }

    @Override // com.google.android.exoplayer2.Player
    public final boolean isCurrentWindowLive() {
        Timeline currentTimeline = getCurrentTimeline();
        return !currentTimeline.isEmpty() && currentTimeline.getWindow(getCurrentWindowIndex(), this.window).isLive();
    }

    @Override // com.google.android.exoplayer2.Player
    public final long getCurrentLiveOffset() {
        Timeline currentTimeline = getCurrentTimeline();
        return (!currentTimeline.isEmpty() && currentTimeline.getWindow(getCurrentWindowIndex(), this.window).windowStartTimeMs != C.TIME_UNSET) ? (this.window.getCurrentUnixTimeMs() - this.window.windowStartTimeMs) - getContentPosition() : C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.Player
    public final boolean isCurrentWindowSeekable() {
        Timeline currentTimeline = getCurrentTimeline();
        return !currentTimeline.isEmpty() && currentTimeline.getWindow(getCurrentWindowIndex(), this.window).isSeekable;
    }

    @Override // com.google.android.exoplayer2.Player
    public final long getContentDuration() {
        Timeline currentTimeline = getCurrentTimeline();
        return currentTimeline.isEmpty() ? C.TIME_UNSET : currentTimeline.getWindow(getCurrentWindowIndex(), this.window).getDurationMs();
    }

    protected Player.Commands getAvailableCommands(Player.Commands commands) {
        boolean z = true;
        Player.Commands.Builder addIf = new Player.Commands.Builder().addAll(commands).addIf(3, !isPlayingAd()).addIf(4, isCurrentWindowSeekable() && !isPlayingAd()).addIf(5, hasPreviousWindow() && !isPlayingAd()).addIf(6, !getCurrentTimeline().isEmpty() && (hasPreviousWindow() || !isCurrentWindowLive() || isCurrentWindowSeekable()) && !isPlayingAd()).addIf(7, hasNextWindow() && !isPlayingAd()).addIf(8, !getCurrentTimeline().isEmpty() && (hasNextWindow() || (isCurrentWindowLive() && isCurrentWindowDynamic())) && !isPlayingAd()).addIf(9, !isPlayingAd()).addIf(10, isCurrentWindowSeekable() && !isPlayingAd());
        if (!isCurrentWindowSeekable() || isPlayingAd()) {
            z = false;
        }
        return addIf.addIf(11, z).build();
    }

    private int a() {
        int repeatMode = getRepeatMode();
        if (repeatMode == 1) {
            return 0;
        }
        return repeatMode;
    }

    private void a(long j) {
        long currentPosition = getCurrentPosition() + j;
        long duration = getDuration();
        if (duration != C.TIME_UNSET) {
            currentPosition = Math.min(currentPosition, duration);
        }
        seekTo(Math.max(currentPosition, 0L));
    }
}
