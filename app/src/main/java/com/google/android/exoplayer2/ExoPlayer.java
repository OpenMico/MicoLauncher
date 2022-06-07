package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.DefaultLivePlaybackSpeedControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.device.DeviceListener;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import java.util.List;

/* loaded from: classes.dex */
public interface ExoPlayer extends Player {
    public static final long DEFAULT_RELEASE_TIMEOUT_MS = 500;

    /* loaded from: classes.dex */
    public interface AudioComponent {
        @Deprecated
        void addAudioListener(AudioListener audioListener);

        void clearAuxEffectInfo();

        AudioAttributes getAudioAttributes();

        int getAudioSessionId();

        boolean getSkipSilenceEnabled();

        float getVolume();

        @Deprecated
        void removeAudioListener(AudioListener audioListener);

        void setAudioAttributes(AudioAttributes audioAttributes, boolean z);

        void setAudioSessionId(int i);

        void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

        void setSkipSilenceEnabled(boolean z);

        void setVolume(float f);
    }

    /* loaded from: classes.dex */
    public interface AudioOffloadListener {
        default void onExperimentalOffloadSchedulingEnabledChanged(boolean z) {
        }

        default void onExperimentalSleepingForOffloadChanged(boolean z) {
        }
    }

    /* loaded from: classes.dex */
    public interface DeviceComponent {
        @Deprecated
        void addDeviceListener(DeviceListener deviceListener);

        void decreaseDeviceVolume();

        DeviceInfo getDeviceInfo();

        int getDeviceVolume();

        void increaseDeviceVolume();

        boolean isDeviceMuted();

        @Deprecated
        void removeDeviceListener(DeviceListener deviceListener);

        void setDeviceMuted(boolean z);

        void setDeviceVolume(int i);
    }

    /* loaded from: classes.dex */
    public interface MetadataComponent {
        @Deprecated
        void addMetadataOutput(MetadataOutput metadataOutput);

        @Deprecated
        void removeMetadataOutput(MetadataOutput metadataOutput);
    }

    /* loaded from: classes.dex */
    public interface TextComponent {
        @Deprecated
        void addTextOutput(TextOutput textOutput);

        List<Cue> getCurrentCues();

        @Deprecated
        void removeTextOutput(TextOutput textOutput);
    }

    /* loaded from: classes.dex */
    public interface VideoComponent {
        @Deprecated
        void addVideoListener(VideoListener videoListener);

        void clearCameraMotionListener(CameraMotionListener cameraMotionListener);

        void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        void clearVideoSurface();

        void clearVideoSurface(@Nullable Surface surface);

        void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

        void clearVideoSurfaceView(@Nullable SurfaceView surfaceView);

        void clearVideoTextureView(@Nullable TextureView textureView);

        int getVideoScalingMode();

        VideoSize getVideoSize();

        @Deprecated
        void removeVideoListener(VideoListener videoListener);

        void setCameraMotionListener(CameraMotionListener cameraMotionListener);

        void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        void setVideoScalingMode(int i);

        void setVideoSurface(@Nullable Surface surface);

        void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

        void setVideoSurfaceView(@Nullable SurfaceView surfaceView);

        void setVideoTextureView(@Nullable TextureView textureView);
    }

    void addAudioOffloadListener(AudioOffloadListener audioOffloadListener);

    void addMediaSource(int i, MediaSource mediaSource);

    void addMediaSource(MediaSource mediaSource);

    void addMediaSources(int i, List<MediaSource> list);

    void addMediaSources(List<MediaSource> list);

    PlayerMessage createMessage(PlayerMessage.Target target);

    boolean experimentalIsSleepingForOffload();

    void experimentalSetOffloadSchedulingEnabled(boolean z);

    @Nullable
    AudioComponent getAudioComponent();

    Clock getClock();

    @Nullable
    DeviceComponent getDeviceComponent();

    @Nullable
    MetadataComponent getMetadataComponent();

    boolean getPauseAtEndOfMediaItems();

    Looper getPlaybackLooper();

    @Override // com.google.android.exoplayer2.Player
    ExoPlaybackException getPlayerError();

    int getRendererCount();

    int getRendererType(int i);

    SeekParameters getSeekParameters();

    @Nullable
    TextComponent getTextComponent();

    @Nullable
    TrackSelector getTrackSelector();

    @Nullable
    VideoComponent getVideoComponent();

    @Deprecated
    void prepare(MediaSource mediaSource);

    @Deprecated
    void prepare(MediaSource mediaSource, boolean z, boolean z2);

    void removeAudioOffloadListener(AudioOffloadListener audioOffloadListener);

    @Deprecated
    void retry();

    void setForegroundMode(boolean z);

    void setMediaSource(MediaSource mediaSource);

    void setMediaSource(MediaSource mediaSource, long j);

    void setMediaSource(MediaSource mediaSource, boolean z);

    void setMediaSources(List<MediaSource> list);

    void setMediaSources(List<MediaSource> list, int i, long j);

    void setMediaSources(List<MediaSource> list, boolean z);

    void setPauseAtEndOfMediaItems(boolean z);

    void setSeekParameters(@Nullable SeekParameters seekParameters);

    void setShuffleOrder(ShuffleOrder shuffleOrder);

    @Deprecated
    /* loaded from: classes.dex */
    public static final class Builder {
        private final Renderer[] a;
        private Clock b;
        private TrackSelector c;
        private MediaSourceFactory d;
        private LoadControl e;
        private BandwidthMeter f;
        private Looper g;
        @Nullable
        private AnalyticsCollector h;
        private boolean i;
        private SeekParameters j;
        private boolean k;
        private long l;
        private LivePlaybackSpeedControl m;
        private boolean n;
        private long o;

        public Builder(Context context, Renderer... rendererArr) {
            this(rendererArr, new DefaultTrackSelector(context), new DefaultMediaSourceFactory(context), new DefaultLoadControl(), DefaultBandwidthMeter.getSingletonInstance(context));
        }

        public Builder(Renderer[] rendererArr, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter) {
            Assertions.checkArgument(rendererArr.length > 0);
            this.a = rendererArr;
            this.c = trackSelector;
            this.d = mediaSourceFactory;
            this.e = loadControl;
            this.f = bandwidthMeter;
            this.g = Util.getCurrentOrMainLooper();
            this.i = true;
            this.j = SeekParameters.DEFAULT;
            this.m = new DefaultLivePlaybackSpeedControl.Builder().build();
            this.b = Clock.DEFAULT;
            this.l = 500L;
        }

        public Builder experimentalSetForegroundModeTimeoutMs(long j) {
            Assertions.checkState(!this.n);
            this.o = j;
            return this;
        }

        public Builder setTrackSelector(TrackSelector trackSelector) {
            Assertions.checkState(!this.n);
            this.c = trackSelector;
            return this;
        }

        public Builder setMediaSourceFactory(MediaSourceFactory mediaSourceFactory) {
            Assertions.checkState(!this.n);
            this.d = mediaSourceFactory;
            return this;
        }

        public Builder setLoadControl(LoadControl loadControl) {
            Assertions.checkState(!this.n);
            this.e = loadControl;
            return this;
        }

        public Builder setBandwidthMeter(BandwidthMeter bandwidthMeter) {
            Assertions.checkState(!this.n);
            this.f = bandwidthMeter;
            return this;
        }

        public Builder setLooper(Looper looper) {
            Assertions.checkState(!this.n);
            this.g = looper;
            return this;
        }

        public Builder setAnalyticsCollector(AnalyticsCollector analyticsCollector) {
            Assertions.checkState(!this.n);
            this.h = analyticsCollector;
            return this;
        }

        public Builder setUseLazyPreparation(boolean z) {
            Assertions.checkState(!this.n);
            this.i = z;
            return this;
        }

        public Builder setSeekParameters(SeekParameters seekParameters) {
            Assertions.checkState(!this.n);
            this.j = seekParameters;
            return this;
        }

        public Builder setReleaseTimeoutMs(long j) {
            Assertions.checkState(!this.n);
            this.l = j;
            return this;
        }

        public Builder setPauseAtEndOfMediaItems(boolean z) {
            Assertions.checkState(!this.n);
            this.k = z;
            return this;
        }

        public Builder setLivePlaybackSpeedControl(LivePlaybackSpeedControl livePlaybackSpeedControl) {
            Assertions.checkState(!this.n);
            this.m = livePlaybackSpeedControl;
            return this;
        }

        @VisibleForTesting
        public Builder setClock(Clock clock) {
            Assertions.checkState(!this.n);
            this.b = clock;
            return this;
        }

        public ExoPlayer build() {
            Assertions.checkState(!this.n);
            this.n = true;
            a aVar = new a(this.a, this.c, this.d, this.e, this.f, this.h, this.i, this.j, 5000L, 15000L, this.m, this.l, this.k, this.b, this.g, null, Player.Commands.EMPTY);
            long j = this.o;
            if (j > 0) {
                aVar.a(j);
            }
            return aVar;
        }
    }
}
