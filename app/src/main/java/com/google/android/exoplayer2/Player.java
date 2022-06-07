package com.google.android.exoplayer2;

import android.os.Bundle;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.device.DeviceListener;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.base.Objects;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface Player {
    public static final int COMMAND_ADJUST_DEVICE_VOLUME = 25;
    public static final int COMMAND_CHANGE_MEDIA_ITEMS = 19;
    public static final int COMMAND_GET_AUDIO_ATTRIBUTES = 20;
    public static final int COMMAND_GET_CURRENT_MEDIA_ITEM = 15;
    public static final int COMMAND_GET_DEVICE_VOLUME = 22;
    public static final int COMMAND_GET_MEDIA_ITEMS_METADATA = 17;
    public static final int COMMAND_GET_TEXT = 27;
    public static final int COMMAND_GET_TIMELINE = 16;
    public static final int COMMAND_GET_VOLUME = 21;
    public static final int COMMAND_INVALID = -1;
    public static final int COMMAND_PLAY_PAUSE = 1;
    public static final int COMMAND_PREPARE_STOP = 2;
    public static final int COMMAND_SEEK_BACK = 10;
    public static final int COMMAND_SEEK_FORWARD = 11;
    public static final int COMMAND_SEEK_IN_CURRENT_WINDOW = 4;
    public static final int COMMAND_SEEK_TO_DEFAULT_POSITION = 3;
    public static final int COMMAND_SEEK_TO_NEXT = 8;
    public static final int COMMAND_SEEK_TO_NEXT_WINDOW = 7;
    public static final int COMMAND_SEEK_TO_PREVIOUS = 6;
    public static final int COMMAND_SEEK_TO_PREVIOUS_WINDOW = 5;
    public static final int COMMAND_SEEK_TO_WINDOW = 9;
    public static final int COMMAND_SET_DEVICE_VOLUME = 24;
    public static final int COMMAND_SET_MEDIA_ITEMS_METADATA = 18;
    public static final int COMMAND_SET_REPEAT_MODE = 14;
    public static final int COMMAND_SET_SHUFFLE_MODE = 13;
    public static final int COMMAND_SET_SPEED_AND_PITCH = 12;
    public static final int COMMAND_SET_VIDEO_SURFACE = 26;
    public static final int COMMAND_SET_VOLUME = 23;
    public static final int DISCONTINUITY_REASON_AUTO_TRANSITION = 0;
    public static final int DISCONTINUITY_REASON_INTERNAL = 5;
    public static final int DISCONTINUITY_REASON_REMOVE = 4;
    public static final int DISCONTINUITY_REASON_SEEK = 1;
    public static final int DISCONTINUITY_REASON_SEEK_ADJUSTMENT = 2;
    public static final int DISCONTINUITY_REASON_SKIP = 3;
    public static final int EVENT_AVAILABLE_COMMANDS_CHANGED = 14;
    public static final int EVENT_IS_LOADING_CHANGED = 4;
    public static final int EVENT_IS_PLAYING_CHANGED = 8;
    public static final int EVENT_MAX_SEEK_TO_PREVIOUS_POSITION_CHANGED = 19;
    public static final int EVENT_MEDIA_ITEM_TRANSITION = 1;
    public static final int EVENT_MEDIA_METADATA_CHANGED = 15;
    public static final int EVENT_PLAYBACK_PARAMETERS_CHANGED = 13;
    public static final int EVENT_PLAYBACK_STATE_CHANGED = 5;
    public static final int EVENT_PLAYBACK_SUPPRESSION_REASON_CHANGED = 7;
    public static final int EVENT_PLAYER_ERROR = 11;
    public static final int EVENT_PLAYLIST_METADATA_CHANGED = 16;
    public static final int EVENT_PLAY_WHEN_READY_CHANGED = 6;
    public static final int EVENT_POSITION_DISCONTINUITY = 12;
    public static final int EVENT_REPEAT_MODE_CHANGED = 9;
    public static final int EVENT_SEEK_BACK_INCREMENT_CHANGED = 17;
    public static final int EVENT_SEEK_FORWARD_INCREMENT_CHANGED = 18;
    public static final int EVENT_SHUFFLE_MODE_ENABLED_CHANGED = 10;
    @Deprecated
    public static final int EVENT_STATIC_METADATA_CHANGED = 3;
    public static final int EVENT_TIMELINE_CHANGED = 0;
    public static final int EVENT_TRACKS_CHANGED = 2;
    public static final int MEDIA_ITEM_TRANSITION_REASON_AUTO = 1;
    public static final int MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED = 3;
    public static final int MEDIA_ITEM_TRANSITION_REASON_REPEAT = 0;
    public static final int MEDIA_ITEM_TRANSITION_REASON_SEEK = 2;
    public static final int PLAYBACK_SUPPRESSION_REASON_NONE = 0;
    public static final int PLAYBACK_SUPPRESSION_REASON_TRANSIENT_AUDIO_FOCUS_LOSS = 1;
    public static final int PLAY_WHEN_READY_CHANGE_REASON_AUDIO_BECOMING_NOISY = 3;
    public static final int PLAY_WHEN_READY_CHANGE_REASON_AUDIO_FOCUS_LOSS = 2;
    public static final int PLAY_WHEN_READY_CHANGE_REASON_END_OF_MEDIA_ITEM = 5;
    public static final int PLAY_WHEN_READY_CHANGE_REASON_REMOTE = 4;
    public static final int PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST = 1;
    public static final int REPEAT_MODE_ALL = 2;
    public static final int REPEAT_MODE_OFF = 0;
    public static final int REPEAT_MODE_ONE = 1;
    public static final int STATE_BUFFERING = 2;
    public static final int STATE_ENDED = 4;
    public static final int STATE_IDLE = 1;
    public static final int STATE_READY = 3;
    public static final int TIMELINE_CHANGE_REASON_PLAYLIST_CHANGED = 0;
    public static final int TIMELINE_CHANGE_REASON_SOURCE_UPDATE = 1;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Command {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface DiscontinuityReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Event {
    }

    @Deprecated
    /* loaded from: classes.dex */
    public interface EventListener {
        default void onAvailableCommandsChanged(Commands commands) {
        }

        default void onEvents(Player player, Events events) {
        }

        default void onIsLoadingChanged(boolean z) {
        }

        default void onIsPlayingChanged(boolean z) {
        }

        @Deprecated
        default void onLoadingChanged(boolean z) {
        }

        default void onMaxSeekToPreviousPositionChanged(int i) {
        }

        default void onMediaItemTransition(@Nullable MediaItem mediaItem, int i) {
        }

        default void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
        }

        default void onPlayWhenReadyChanged(boolean z, int i) {
        }

        default void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }

        default void onPlaybackStateChanged(int i) {
        }

        default void onPlaybackSuppressionReasonChanged(int i) {
        }

        default void onPlayerError(PlaybackException playbackException) {
        }

        default void onPlayerErrorChanged(@Nullable PlaybackException playbackException) {
        }

        @Deprecated
        default void onPlayerStateChanged(boolean z, int i) {
        }

        default void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
        }

        @Deprecated
        default void onPositionDiscontinuity(int i) {
        }

        default void onPositionDiscontinuity(PositionInfo positionInfo, PositionInfo positionInfo2, int i) {
        }

        default void onRepeatModeChanged(int i) {
        }

        default void onSeekBackIncrementChanged(long j) {
        }

        default void onSeekForwardIncrementChanged(long j) {
        }

        @Deprecated
        default void onSeekProcessed() {
        }

        default void onShuffleModeEnabledChanged(boolean z) {
        }

        @Deprecated
        default void onStaticMetadataChanged(List<Metadata> list) {
        }

        default void onTimelineChanged(Timeline timeline, int i) {
        }

        default void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        }
    }

    /* loaded from: classes.dex */
    public interface Listener extends EventListener, AudioListener, DeviceListener, MetadataOutput, TextOutput, VideoListener {
        default void onAudioAttributesChanged(AudioAttributes audioAttributes) {
        }

        default void onAudioSessionIdChanged(int i) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onAvailableCommandsChanged(Commands commands) {
        }

        default void onCues(List<Cue> list) {
        }

        default void onDeviceInfoChanged(DeviceInfo deviceInfo) {
        }

        default void onDeviceVolumeChanged(int i, boolean z) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onEvents(Player player, Events events) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onIsLoadingChanged(boolean z) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onIsPlayingChanged(boolean z) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onMediaItemTransition(@Nullable MediaItem mediaItem, int i) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
        }

        default void onMetadata(Metadata metadata) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlayWhenReadyChanged(boolean z, int i) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlaybackStateChanged(int i) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlaybackSuppressionReasonChanged(int i) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlayerError(PlaybackException playbackException) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlayerErrorChanged(@Nullable PlaybackException playbackException) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPositionDiscontinuity(PositionInfo positionInfo, PositionInfo positionInfo2, int i) {
        }

        default void onRenderedFirstFrame() {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onRepeatModeChanged(int i) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onSeekBackIncrementChanged(long j) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onSeekForwardIncrementChanged(long j) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onShuffleModeEnabledChanged(boolean z) {
        }

        default void onSkipSilenceEnabledChanged(boolean z) {
        }

        default void onSurfaceSizeChanged(int i, int i2) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onTimelineChanged(Timeline timeline, int i) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        }

        default void onVideoSizeChanged(VideoSize videoSize) {
        }

        default void onVolumeChanged(float f) {
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface MediaItemTransitionReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface PlayWhenReadyChangeReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface PlaybackSuppressionReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface RepeatMode {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface State {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface TimelineChangeReason {
    }

    @Deprecated
    void addListener(EventListener eventListener);

    void addListener(Listener listener);

    void addMediaItem(int i, MediaItem mediaItem);

    void addMediaItem(MediaItem mediaItem);

    void addMediaItems(int i, List<MediaItem> list);

    void addMediaItems(List<MediaItem> list);

    void clearMediaItems();

    void clearVideoSurface();

    void clearVideoSurface(@Nullable Surface surface);

    void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

    void clearVideoSurfaceView(@Nullable SurfaceView surfaceView);

    void clearVideoTextureView(@Nullable TextureView textureView);

    void decreaseDeviceVolume();

    Looper getApplicationLooper();

    AudioAttributes getAudioAttributes();

    Commands getAvailableCommands();

    int getBufferedPercentage();

    long getBufferedPosition();

    long getContentBufferedPosition();

    long getContentDuration();

    long getContentPosition();

    int getCurrentAdGroupIndex();

    int getCurrentAdIndexInAdGroup();

    List<Cue> getCurrentCues();

    long getCurrentLiveOffset();

    @Nullable
    Object getCurrentManifest();

    @Nullable
    MediaItem getCurrentMediaItem();

    int getCurrentPeriodIndex();

    long getCurrentPosition();

    @Deprecated
    List<Metadata> getCurrentStaticMetadata();

    Timeline getCurrentTimeline();

    TrackGroupArray getCurrentTrackGroups();

    TrackSelectionArray getCurrentTrackSelections();

    int getCurrentWindowIndex();

    DeviceInfo getDeviceInfo();

    int getDeviceVolume();

    long getDuration();

    int getMaxSeekToPreviousPosition();

    MediaItem getMediaItemAt(int i);

    int getMediaItemCount();

    MediaMetadata getMediaMetadata();

    int getNextWindowIndex();

    boolean getPlayWhenReady();

    PlaybackParameters getPlaybackParameters();

    int getPlaybackState();

    int getPlaybackSuppressionReason();

    @Nullable
    PlaybackException getPlayerError();

    MediaMetadata getPlaylistMetadata();

    int getPreviousWindowIndex();

    int getRepeatMode();

    long getSeekBackIncrement();

    long getSeekForwardIncrement();

    boolean getShuffleModeEnabled();

    long getTotalBufferedDuration();

    VideoSize getVideoSize();

    float getVolume();

    @Deprecated
    boolean hasNext();

    boolean hasNextWindow();

    @Deprecated
    boolean hasPrevious();

    boolean hasPreviousWindow();

    void increaseDeviceVolume();

    boolean isCommandAvailable(int i);

    boolean isCurrentWindowDynamic();

    boolean isCurrentWindowLive();

    boolean isCurrentWindowSeekable();

    boolean isDeviceMuted();

    boolean isLoading();

    boolean isPlaying();

    boolean isPlayingAd();

    void moveMediaItem(int i, int i2);

    void moveMediaItems(int i, int i2, int i3);

    @Deprecated
    void next();

    void pause();

    void play();

    void prepare();

    @Deprecated
    void previous();

    void release();

    @Deprecated
    void removeListener(EventListener eventListener);

    void removeListener(Listener listener);

    void removeMediaItem(int i);

    void removeMediaItems(int i, int i2);

    void seekBack();

    void seekForward();

    void seekTo(int i, long j);

    void seekTo(long j);

    void seekToDefaultPosition();

    void seekToDefaultPosition(int i);

    void seekToNext();

    void seekToNextWindow();

    void seekToPrevious();

    void seekToPreviousWindow();

    void setDeviceMuted(boolean z);

    void setDeviceVolume(int i);

    void setMediaItem(MediaItem mediaItem);

    void setMediaItem(MediaItem mediaItem, long j);

    void setMediaItem(MediaItem mediaItem, boolean z);

    void setMediaItems(List<MediaItem> list);

    void setMediaItems(List<MediaItem> list, int i, long j);

    void setMediaItems(List<MediaItem> list, boolean z);

    void setPlayWhenReady(boolean z);

    void setPlaybackParameters(PlaybackParameters playbackParameters);

    void setPlaybackSpeed(float f);

    void setPlaylistMetadata(MediaMetadata mediaMetadata);

    void setRepeatMode(int i);

    void setShuffleModeEnabled(boolean z);

    void setVideoSurface(@Nullable Surface surface);

    void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

    void setVideoSurfaceView(@Nullable SurfaceView surfaceView);

    void setVideoTextureView(@Nullable TextureView textureView);

    void setVolume(float f);

    void stop();

    @Deprecated
    void stop(boolean z);

    /* loaded from: classes.dex */
    public static final class Events {
        private final FlagSet a;

        public Events(FlagSet flagSet) {
            this.a = flagSet;
        }

        public boolean contains(int i) {
            return this.a.contains(i);
        }

        public boolean containsAny(int... iArr) {
            return this.a.containsAny(iArr);
        }

        public int size() {
            return this.a.size();
        }

        public int get(int i) {
            return this.a.get(i);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Events)) {
                return false;
            }
            return this.a.equals(((Events) obj).a);
        }
    }

    /* loaded from: classes.dex */
    public static final class PositionInfo implements Bundleable {
        public static final Bundleable.Creator<PositionInfo> CREATOR = $$Lambda$Player$PositionInfo$UeaKh_RfV8f1mx_F6lcaAK3_On8.INSTANCE;
        public final int adGroupIndex;
        public final int adIndexInAdGroup;
        public final long contentPositionMs;
        public final int periodIndex;
        @Nullable
        public final Object periodUid;
        public final long positionMs;
        public final int windowIndex;
        @Nullable
        public final Object windowUid;

        public PositionInfo(@Nullable Object obj, int i, @Nullable Object obj2, int i2, long j, long j2, int i3, int i4) {
            this.windowUid = obj;
            this.windowIndex = i;
            this.periodUid = obj2;
            this.periodIndex = i2;
            this.positionMs = j;
            this.contentPositionMs = j2;
            this.adGroupIndex = i3;
            this.adIndexInAdGroup = i4;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            PositionInfo positionInfo = (PositionInfo) obj;
            return this.windowIndex == positionInfo.windowIndex && this.periodIndex == positionInfo.periodIndex && this.positionMs == positionInfo.positionMs && this.contentPositionMs == positionInfo.contentPositionMs && this.adGroupIndex == positionInfo.adGroupIndex && this.adIndexInAdGroup == positionInfo.adIndexInAdGroup && Objects.equal(this.windowUid, positionInfo.windowUid) && Objects.equal(this.periodUid, positionInfo.periodUid);
        }

        public int hashCode() {
            return Objects.hashCode(this.windowUid, Integer.valueOf(this.windowIndex), this.periodUid, Integer.valueOf(this.periodIndex), Integer.valueOf(this.windowIndex), Long.valueOf(this.positionMs), Long.valueOf(this.contentPositionMs), Integer.valueOf(this.adGroupIndex), Integer.valueOf(this.adIndexInAdGroup));
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putInt(a(0), this.windowIndex);
            bundle.putInt(a(1), this.periodIndex);
            bundle.putLong(a(2), this.positionMs);
            bundle.putLong(a(3), this.contentPositionMs);
            bundle.putInt(a(4), this.adGroupIndex);
            bundle.putInt(a(5), this.adIndexInAdGroup);
            return bundle;
        }

        public static PositionInfo a(Bundle bundle) {
            return new PositionInfo(null, bundle.getInt(a(0), -1), null, bundle.getInt(a(1), -1), bundle.getLong(a(2), C.TIME_UNSET), bundle.getLong(a(3), C.TIME_UNSET), bundle.getInt(a(4), -1), bundle.getInt(a(5), -1));
        }

        private static String a(int i) {
            return Integer.toString(i, 36);
        }
    }

    /* loaded from: classes.dex */
    public static final class Commands implements Bundleable {
        private final FlagSet a;
        public static final Commands EMPTY = new Builder().build();
        public static final Bundleable.Creator<Commands> CREATOR = $$Lambda$Player$Commands$wF45SkvPMsUjs6MeCmjk5UlGlAY.INSTANCE;

        /* loaded from: classes.dex */
        public static final class Builder {
            private static final int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
            private final FlagSet.Builder b;

            public Builder() {
                this.b = new FlagSet.Builder();
            }

            private Builder(Commands commands) {
                this.b = new FlagSet.Builder();
                this.b.addAll(commands.a);
            }

            public Builder add(int i) {
                this.b.add(i);
                return this;
            }

            public Builder addIf(int i, boolean z) {
                this.b.addIf(i, z);
                return this;
            }

            public Builder addAll(int... iArr) {
                this.b.addAll(iArr);
                return this;
            }

            public Builder addAll(Commands commands) {
                this.b.addAll(commands.a);
                return this;
            }

            public Builder addAllCommands() {
                this.b.addAll(a);
                return this;
            }

            public Builder remove(int i) {
                this.b.remove(i);
                return this;
            }

            public Builder removeIf(int i, boolean z) {
                this.b.removeIf(i, z);
                return this;
            }

            public Builder removeAll(int... iArr) {
                this.b.removeAll(iArr);
                return this;
            }

            public Commands build() {
                return new Commands(this.b.build());
            }
        }

        private Commands(FlagSet flagSet) {
            this.a = flagSet;
        }

        public Builder buildUpon() {
            return new Builder();
        }

        public boolean contains(int i) {
            return this.a.contains(i);
        }

        public int size() {
            return this.a.size();
        }

        public int get(int i) {
            return this.a.get(i);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Commands)) {
                return false;
            }
            return this.a.equals(((Commands) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int i = 0; i < this.a.size(); i++) {
                arrayList.add(Integer.valueOf(this.a.get(i)));
            }
            bundle.putIntegerArrayList(a(0), arrayList);
            return bundle;
        }

        public static Commands a(Bundle bundle) {
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList(a(0));
            if (integerArrayList == null) {
                return EMPTY;
            }
            Builder builder = new Builder();
            for (int i = 0; i < integerArrayList.size(); i++) {
                builder.add(integerArrayList.get(i).intValue());
            }
            return builder.build();
        }

        private static String a(int i) {
            return Integer.toString(i, 36);
        }
    }
}
