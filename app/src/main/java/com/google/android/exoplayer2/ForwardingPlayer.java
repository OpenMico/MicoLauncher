package com.google.android.exoplayer2;

import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.video.VideoSize;
import java.util.List;

/* loaded from: classes.dex */
public class ForwardingPlayer implements Player {
    private final Player a;

    public ForwardingPlayer(Player player) {
        this.a = player;
    }

    @Override // com.google.android.exoplayer2.Player
    public Looper getApplicationLooper() {
        return this.a.getApplicationLooper();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void addListener(Player.EventListener eventListener) {
        this.a.addListener(new a(eventListener));
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.Listener listener) {
        this.a.addListener((Player.Listener) new b(this, listener));
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void removeListener(Player.EventListener eventListener) {
        this.a.removeListener(new a(eventListener));
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.Listener listener) {
        this.a.removeListener((Player.Listener) new b(this, listener));
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list) {
        this.a.setMediaItems(list);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, boolean z) {
        this.a.setMediaItems(list, z);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, int i, long j) {
        this.a.setMediaItems(list, i, j);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItem(MediaItem mediaItem) {
        this.a.setMediaItem(mediaItem);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItem(MediaItem mediaItem, long j) {
        this.a.setMediaItem(mediaItem, j);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItem(MediaItem mediaItem, boolean z) {
        this.a.setMediaItem(mediaItem, z);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItem(MediaItem mediaItem) {
        this.a.addMediaItem(mediaItem);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItem(int i, MediaItem mediaItem) {
        this.a.addMediaItem(i, mediaItem);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(List<MediaItem> list) {
        this.a.addMediaItems(list);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(int i, List<MediaItem> list) {
        this.a.addMediaItems(i, list);
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItem(int i, int i2) {
        this.a.moveMediaItem(i, i2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItems(int i, int i2, int i3) {
        this.a.moveMediaItems(i, i2, i3);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItem(int i) {
        this.a.removeMediaItem(i);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItems(int i, int i2) {
        this.a.removeMediaItems(i, i2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearMediaItems() {
        this.a.clearMediaItems();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCommandAvailable(int i) {
        return this.a.isCommandAvailable(i);
    }

    @Override // com.google.android.exoplayer2.Player
    public Player.Commands getAvailableCommands() {
        return this.a.getAvailableCommands();
    }

    @Override // com.google.android.exoplayer2.Player
    public void prepare() {
        this.a.prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackState() {
        return this.a.getPlaybackState();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackSuppressionReason() {
        return this.a.getPlaybackSuppressionReason();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlaying() {
        return this.a.isPlaying();
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public PlaybackException getPlayerError() {
        return this.a.getPlayerError();
    }

    @Override // com.google.android.exoplayer2.Player
    public void play() {
        this.a.play();
    }

    @Override // com.google.android.exoplayer2.Player
    public void pause() {
        this.a.pause();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlayWhenReady(boolean z) {
        this.a.setPlayWhenReady(z);
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getPlayWhenReady() {
        return this.a.getPlayWhenReady();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setRepeatMode(int i) {
        this.a.setRepeatMode(i);
    }

    @Override // com.google.android.exoplayer2.Player
    public int getRepeatMode() {
        return this.a.getRepeatMode();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setShuffleModeEnabled(boolean z) {
        this.a.setShuffleModeEnabled(z);
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getShuffleModeEnabled() {
        return this.a.getShuffleModeEnabled();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isLoading() {
        return this.a.isLoading();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToDefaultPosition() {
        this.a.seekToDefaultPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToDefaultPosition(int i) {
        this.a.seekToDefaultPosition(i);
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(long j) {
        this.a.seekTo(j);
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(int i, long j) {
        this.a.seekTo(i, j);
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekBackIncrement() {
        return this.a.getSeekBackIncrement();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekBack() {
        this.a.seekBack();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekForwardIncrement() {
        return this.a.getSeekForwardIncrement();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekForward() {
        this.a.seekForward();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public boolean hasPrevious() {
        return this.a.hasPrevious();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean hasPreviousWindow() {
        return this.a.hasPreviousWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void previous() {
        this.a.previous();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToPreviousWindow() {
        this.a.seekToPreviousWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToPrevious() {
        this.a.seekToPrevious();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getMaxSeekToPreviousPosition() {
        return this.a.getMaxSeekToPreviousPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean hasNextWindow() {
        return this.a.hasNextWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void next() {
        this.a.next();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToNextWindow() {
        this.a.seekToNextWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToNext() {
        this.a.seekToNext();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.a.setPlaybackParameters(playbackParameters);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackSpeed(float f) {
        this.a.setPlaybackSpeed(f);
    }

    @Override // com.google.android.exoplayer2.Player
    public PlaybackParameters getPlaybackParameters() {
        return this.a.getPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.Player
    public void stop() {
        this.a.stop();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void stop(boolean z) {
        this.a.stop(z);
    }

    @Override // com.google.android.exoplayer2.Player
    public void release() {
        this.a.release();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackGroupArray getCurrentTrackGroups() {
        return this.a.getCurrentTrackGroups();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionArray getCurrentTrackSelections() {
        return this.a.getCurrentTrackSelections();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public List<Metadata> getCurrentStaticMetadata() {
        return this.a.getCurrentStaticMetadata();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getMediaMetadata() {
        return this.a.getMediaMetadata();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getPlaylistMetadata() {
        return this.a.getPlaylistMetadata();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaylistMetadata(MediaMetadata mediaMetadata) {
        this.a.setPlaylistMetadata(mediaMetadata);
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public Object getCurrentManifest() {
        return this.a.getCurrentManifest();
    }

    @Override // com.google.android.exoplayer2.Player
    public Timeline getCurrentTimeline() {
        return this.a.getCurrentTimeline();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentPeriodIndex() {
        return this.a.getCurrentPeriodIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentWindowIndex() {
        return this.a.getCurrentWindowIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getNextWindowIndex() {
        return this.a.getNextWindowIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPreviousWindowIndex() {
        return this.a.getPreviousWindowIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public MediaItem getCurrentMediaItem() {
        return this.a.getCurrentMediaItem();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getMediaItemCount() {
        return this.a.getMediaItemCount();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaItem getMediaItemAt(int i) {
        return this.a.getMediaItemAt(i);
    }

    @Override // com.google.android.exoplayer2.Player
    public long getDuration() {
        return this.a.getDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentPosition() {
        return this.a.getCurrentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getBufferedPosition() {
        return this.a.getBufferedPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getBufferedPercentage() {
        return this.a.getBufferedPercentage();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getTotalBufferedDuration() {
        return this.a.getTotalBufferedDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCurrentWindowDynamic() {
        return this.a.isCurrentWindowDynamic();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCurrentWindowLive() {
        return this.a.isCurrentWindowLive();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentLiveOffset() {
        return this.a.getCurrentLiveOffset();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCurrentWindowSeekable() {
        return this.a.isCurrentWindowSeekable();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlayingAd() {
        return this.a.isPlayingAd();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdGroupIndex() {
        return this.a.getCurrentAdGroupIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdIndexInAdGroup() {
        return this.a.getCurrentAdIndexInAdGroup();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentDuration() {
        return this.a.getContentDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentPosition() {
        return this.a.getContentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentBufferedPosition() {
        return this.a.getContentBufferedPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public AudioAttributes getAudioAttributes() {
        return this.a.getAudioAttributes();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVolume(float f) {
        this.a.setVolume(f);
    }

    @Override // com.google.android.exoplayer2.Player
    public float getVolume() {
        return this.a.getVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public VideoSize getVideoSize() {
        return this.a.getVideoSize();
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface() {
        this.a.clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface(@Nullable Surface surface) {
        this.a.clearVideoSurface(surface);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurface(@Nullable Surface surface) {
        this.a.setVideoSurface(surface);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        this.a.setVideoSurfaceHolder(surfaceHolder);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        this.a.clearVideoSurfaceHolder(surfaceHolder);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        this.a.setVideoSurfaceView(surfaceView);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        this.a.clearVideoSurfaceView(surfaceView);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoTextureView(@Nullable TextureView textureView) {
        this.a.setVideoTextureView(textureView);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoTextureView(@Nullable TextureView textureView) {
        this.a.clearVideoTextureView(textureView);
    }

    @Override // com.google.android.exoplayer2.Player
    public List<Cue> getCurrentCues() {
        return this.a.getCurrentCues();
    }

    @Override // com.google.android.exoplayer2.Player
    public DeviceInfo getDeviceInfo() {
        return this.a.getDeviceInfo();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getDeviceVolume() {
        return this.a.getDeviceVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isDeviceMuted() {
        return this.a.isDeviceMuted();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceVolume(int i) {
        this.a.setDeviceVolume(i);
    }

    @Override // com.google.android.exoplayer2.Player
    public void increaseDeviceVolume() {
        this.a.increaseDeviceVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public void decreaseDeviceVolume() {
        this.a.decreaseDeviceVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceMuted(boolean z) {
        this.a.setDeviceMuted(z);
    }

    public Player getWrappedPlayer() {
        return this.a;
    }

    /* loaded from: classes.dex */
    private static class a implements Player.EventListener {
        private final ForwardingPlayer a;
        private final Player.EventListener b;

        private a(ForwardingPlayer forwardingPlayer, Player.EventListener eventListener) {
            this.a = forwardingPlayer;
            this.b = eventListener;
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTimelineChanged(Timeline timeline, int i) {
            this.b.onTimelineChanged(timeline, i);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onMediaItemTransition(@Nullable MediaItem mediaItem, int i) {
            this.b.onMediaItemTransition(mediaItem, i);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            this.b.onTracksChanged(trackGroupArray, trackSelectionArray);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        @Deprecated
        public void onStaticMetadataChanged(List<Metadata> list) {
            this.b.onStaticMetadataChanged(list);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
            this.b.onMediaMetadataChanged(mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
            this.b.onPlaylistMetadataChanged(mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onIsLoadingChanged(boolean z) {
            this.b.onIsLoadingChanged(z);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onLoadingChanged(boolean z) {
            this.b.onIsLoadingChanged(z);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onAvailableCommandsChanged(Player.Commands commands) {
            this.b.onAvailableCommandsChanged(commands);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerStateChanged(boolean z, int i) {
            this.b.onPlayerStateChanged(z, i);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackStateChanged(int i) {
            this.b.onPlaybackStateChanged(i);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayWhenReadyChanged(boolean z, int i) {
            this.b.onPlayWhenReadyChanged(z, i);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackSuppressionReasonChanged(int i) {
            this.b.onPlaybackSuppressionReasonChanged(i);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onIsPlayingChanged(boolean z) {
            this.b.onIsPlayingChanged(z);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onRepeatModeChanged(int i) {
            this.b.onRepeatModeChanged(i);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onShuffleModeEnabledChanged(boolean z) {
            this.b.onShuffleModeEnabledChanged(z);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerError(PlaybackException playbackException) {
            this.b.onPlayerError(playbackException);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerErrorChanged(@Nullable PlaybackException playbackException) {
            this.b.onPlayerErrorChanged(playbackException);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPositionDiscontinuity(int i) {
            this.b.onPositionDiscontinuity(i);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
            this.b.onPositionDiscontinuity(positionInfo, positionInfo2, i);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            this.b.onPlaybackParametersChanged(playbackParameters);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekBackIncrementChanged(long j) {
            this.b.onSeekBackIncrementChanged(j);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekForwardIncrementChanged(long j) {
            this.b.onSeekForwardIncrementChanged(j);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onMaxSeekToPreviousPositionChanged(int i) {
            this.b.onMaxSeekToPreviousPositionChanged(i);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekProcessed() {
            this.b.onSeekProcessed();
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onEvents(Player player, Player.Events events) {
            this.b.onEvents(this.a, events);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            if (!this.a.equals(aVar.a)) {
                return false;
            }
            return this.b.equals(aVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }
    }

    /* loaded from: classes.dex */
    private static final class b extends a implements Player.Listener {
        private final Player.Listener a;

        public b(ForwardingPlayer forwardingPlayer, Player.Listener listener) {
            super(listener);
            this.a = listener;
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
        public void onVideoSizeChanged(VideoSize videoSize) {
            this.a.onVideoSizeChanged(videoSize);
        }

        @Override // com.google.android.exoplayer2.video.VideoListener
        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
            this.a.onVideoSizeChanged(i, i2, i3, f);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
        public void onSurfaceSizeChanged(int i, int i2) {
            this.a.onSurfaceSizeChanged(i, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
        public void onRenderedFirstFrame() {
            this.a.onRenderedFirstFrame();
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
        public void onAudioSessionIdChanged(int i) {
            this.a.onAudioSessionIdChanged(i);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
        public void onAudioAttributesChanged(AudioAttributes audioAttributes) {
            this.a.onAudioAttributesChanged(audioAttributes);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
        public void onVolumeChanged(float f) {
            this.a.onVolumeChanged(f);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
        public void onSkipSilenceEnabledChanged(boolean z) {
            this.a.onSkipSilenceEnabledChanged(z);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.text.TextOutput
        public void onCues(List<Cue> list) {
            this.a.onCues(list);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.metadata.MetadataOutput
        public void onMetadata(Metadata metadata) {
            this.a.onMetadata(metadata);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.device.DeviceListener
        public void onDeviceInfoChanged(DeviceInfo deviceInfo) {
            this.a.onDeviceInfoChanged(deviceInfo);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.device.DeviceListener
        public void onDeviceVolumeChanged(int i, boolean z) {
            this.a.onDeviceVolumeChanged(i, z);
        }
    }
}
