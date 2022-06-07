package com.google.android.exoplayer2;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.AudioTrack;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.AudioBecomingNoisyManager;
import com.google.android.exoplayer2.AudioFocusManager;
import com.google.android.exoplayer2.DefaultLivePlaybackSpeedControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.StreamVolumeManager;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.device.DeviceListener;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoDecoderOutputBufferRenderer;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public class SimpleExoPlayer extends BasePlayer implements ExoPlayer, ExoPlayer.AudioComponent, ExoPlayer.DeviceComponent, ExoPlayer.MetadataComponent, ExoPlayer.TextComponent, ExoPlayer.VideoComponent {
    public static final long DEFAULT_DETACH_SURFACE_TIMEOUT_MS = 2000;
    private int A;
    private int B;
    private int C;
    @Nullable
    private DecoderCounters D;
    @Nullable
    private DecoderCounters E;
    private int F;
    private AudioAttributes G;
    private float H;
    private boolean I;
    private List<Cue> J;
    @Nullable
    private VideoFrameMetadataListener K;
    @Nullable
    private CameraMotionListener L;
    private boolean M;
    private boolean N;
    @Nullable
    private PriorityTaskManager O;
    private boolean P;
    private boolean Q;
    private DeviceInfo R;
    private VideoSize S;
    private final ConditionVariable a;
    private final Context b;
    private final a c;
    private final a d;
    private final b e;
    private final CopyOnWriteArraySet<VideoListener> f;
    private final CopyOnWriteArraySet<AudioListener> g;
    private final CopyOnWriteArraySet<TextOutput> h;
    private final CopyOnWriteArraySet<MetadataOutput> i;
    private final CopyOnWriteArraySet<DeviceListener> j;
    private final AnalyticsCollector k;
    private final AudioBecomingNoisyManager l;
    private final AudioFocusManager m;
    private final StreamVolumeManager n;
    private final i o;
    private final j p;
    private final long q;
    @Nullable
    private Format r;
    protected final Renderer[] renderers;
    @Nullable
    private Format s;
    @Nullable
    private AudioTrack t;
    @Nullable
    private Object u;
    @Nullable
    private Surface v;
    @Nullable
    private SurfaceHolder w;
    @Nullable
    private SphericalGLSurfaceView x;
    private boolean y;
    @Nullable
    private TextureView z;

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(boolean z, int i) {
        return (!z || i == 1) ? 1 : 2;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.AudioComponent getAudioComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.DeviceComponent getDeviceComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.MetadataComponent getMetadataComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.TextComponent getTextComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.VideoComponent getVideoComponent() {
        return this;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private final Context a;
        private final RenderersFactory b;
        private Clock c;
        private long d;
        private TrackSelector e;
        private MediaSourceFactory f;
        private LoadControl g;
        private BandwidthMeter h;
        private AnalyticsCollector i;
        private Looper j;
        @Nullable
        private PriorityTaskManager k;
        private AudioAttributes l;
        private boolean m;
        private int n;
        private boolean o;
        private boolean p;
        private int q;
        private boolean r;
        private SeekParameters s;
        private long t;
        private long u;
        private LivePlaybackSpeedControl v;
        private long w;
        private long x;
        private boolean y;
        private boolean z;

        public Builder(Context context) {
            this(context, new DefaultRenderersFactory(context), new DefaultExtractorsFactory());
        }

        public Builder(Context context, RenderersFactory renderersFactory) {
            this(context, renderersFactory, new DefaultExtractorsFactory());
        }

        public Builder(Context context, ExtractorsFactory extractorsFactory) {
            this(context, new DefaultRenderersFactory(context), extractorsFactory);
        }

        public Builder(Context context, RenderersFactory renderersFactory, ExtractorsFactory extractorsFactory) {
            this(context, renderersFactory, new DefaultTrackSelector(context), new DefaultMediaSourceFactory(context, extractorsFactory), new DefaultLoadControl(), DefaultBandwidthMeter.getSingletonInstance(context), new AnalyticsCollector(Clock.DEFAULT));
        }

        public Builder(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter, AnalyticsCollector analyticsCollector) {
            this.a = context;
            this.b = renderersFactory;
            this.e = trackSelector;
            this.f = mediaSourceFactory;
            this.g = loadControl;
            this.h = bandwidthMeter;
            this.i = analyticsCollector;
            this.j = Util.getCurrentOrMainLooper();
            this.l = AudioAttributes.DEFAULT;
            this.n = 0;
            this.q = 1;
            this.r = true;
            this.s = SeekParameters.DEFAULT;
            this.t = 5000L;
            this.u = 15000L;
            this.v = new DefaultLivePlaybackSpeedControl.Builder().build();
            this.c = Clock.DEFAULT;
            this.w = 500L;
            this.x = SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS;
        }

        public Builder experimentalSetForegroundModeTimeoutMs(long j) {
            Assertions.checkState(!this.z);
            this.d = j;
            return this;
        }

        public Builder setTrackSelector(TrackSelector trackSelector) {
            Assertions.checkState(!this.z);
            this.e = trackSelector;
            return this;
        }

        public Builder setMediaSourceFactory(MediaSourceFactory mediaSourceFactory) {
            Assertions.checkState(!this.z);
            this.f = mediaSourceFactory;
            return this;
        }

        public Builder setLoadControl(LoadControl loadControl) {
            Assertions.checkState(!this.z);
            this.g = loadControl;
            return this;
        }

        public Builder setBandwidthMeter(BandwidthMeter bandwidthMeter) {
            Assertions.checkState(!this.z);
            this.h = bandwidthMeter;
            return this;
        }

        public Builder setLooper(Looper looper) {
            Assertions.checkState(!this.z);
            this.j = looper;
            return this;
        }

        public Builder setAnalyticsCollector(AnalyticsCollector analyticsCollector) {
            Assertions.checkState(!this.z);
            this.i = analyticsCollector;
            return this;
        }

        public Builder setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
            Assertions.checkState(!this.z);
            this.k = priorityTaskManager;
            return this;
        }

        public Builder setAudioAttributes(AudioAttributes audioAttributes, boolean z) {
            Assertions.checkState(!this.z);
            this.l = audioAttributes;
            this.m = z;
            return this;
        }

        public Builder setWakeMode(int i) {
            Assertions.checkState(!this.z);
            this.n = i;
            return this;
        }

        public Builder setHandleAudioBecomingNoisy(boolean z) {
            Assertions.checkState(!this.z);
            this.o = z;
            return this;
        }

        public Builder setSkipSilenceEnabled(boolean z) {
            Assertions.checkState(!this.z);
            this.p = z;
            return this;
        }

        public Builder setVideoScalingMode(int i) {
            Assertions.checkState(!this.z);
            this.q = i;
            return this;
        }

        public Builder setUseLazyPreparation(boolean z) {
            Assertions.checkState(!this.z);
            this.r = z;
            return this;
        }

        public Builder setSeekParameters(SeekParameters seekParameters) {
            Assertions.checkState(!this.z);
            this.s = seekParameters;
            return this;
        }

        public Builder setSeekBackIncrementMs(@IntRange(from = 1) long j) {
            Assertions.checkArgument(j > 0);
            Assertions.checkState(!this.z);
            this.t = j;
            return this;
        }

        public Builder setSeekForwardIncrementMs(@IntRange(from = 1) long j) {
            Assertions.checkArgument(j > 0);
            Assertions.checkState(!this.z);
            this.u = j;
            return this;
        }

        public Builder setReleaseTimeoutMs(long j) {
            Assertions.checkState(!this.z);
            this.w = j;
            return this;
        }

        public Builder setDetachSurfaceTimeoutMs(long j) {
            Assertions.checkState(!this.z);
            this.x = j;
            return this;
        }

        public Builder setPauseAtEndOfMediaItems(boolean z) {
            Assertions.checkState(!this.z);
            this.y = z;
            return this;
        }

        public Builder setLivePlaybackSpeedControl(LivePlaybackSpeedControl livePlaybackSpeedControl) {
            Assertions.checkState(!this.z);
            this.v = livePlaybackSpeedControl;
            return this;
        }

        @VisibleForTesting
        public Builder setClock(Clock clock) {
            Assertions.checkState(!this.z);
            this.c = clock;
            return this;
        }

        public SimpleExoPlayer build() {
            Assertions.checkState(!this.z);
            this.z = true;
            return new SimpleExoPlayer(this);
        }
    }

    @Deprecated
    protected SimpleExoPlayer(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter, AnalyticsCollector analyticsCollector, boolean z, Clock clock, Looper looper) {
        this(new Builder(context, renderersFactory).setTrackSelector(trackSelector).setMediaSourceFactory(mediaSourceFactory).setLoadControl(loadControl).setBandwidthMeter(bandwidthMeter).setAnalyticsCollector(analyticsCollector).setUseLazyPreparation(z).setClock(clock).setLooper(looper));
    }

    protected SimpleExoPlayer(Builder builder) {
        SimpleExoPlayer simpleExoPlayer;
        Throwable th;
        this.a = new ConditionVariable();
        try {
            this.b = builder.a.getApplicationContext();
            this.k = builder.i;
            this.O = builder.k;
            this.G = builder.l;
            this.A = builder.q;
            this.I = builder.p;
            this.q = builder.x;
            this.d = new a();
            this.e = new b();
            this.f = new CopyOnWriteArraySet<>();
            this.g = new CopyOnWriteArraySet<>();
            this.h = new CopyOnWriteArraySet<>();
            this.i = new CopyOnWriteArraySet<>();
            this.j = new CopyOnWriteArraySet<>();
            Handler handler = new Handler(builder.j);
            this.renderers = builder.b.createRenderers(handler, this.d, this.d, this.d, this.d);
            this.H = 1.0f;
            if (Util.SDK_INT < 21) {
                this.F = a(0);
            } else {
                this.F = C.generateAudioSessionIdV21(this.b);
            }
            this.J = Collections.emptyList();
            this.M = true;
            try {
                simpleExoPlayer = this;
                try {
                    simpleExoPlayer.c = new a(this.renderers, builder.e, builder.f, builder.g, builder.h, this.k, builder.r, builder.s, builder.t, builder.u, builder.v, builder.w, builder.y, builder.c, builder.j, this, new Player.Commands.Builder().addAll(20, 21, 22, 23, 24, 25, 26, 27).build());
                    simpleExoPlayer.c.addListener(simpleExoPlayer.d);
                    simpleExoPlayer.c.addAudioOffloadListener(simpleExoPlayer.d);
                    if (builder.d > 0) {
                        simpleExoPlayer.c.a(builder.d);
                    }
                    simpleExoPlayer.l = new AudioBecomingNoisyManager(builder.a, handler, simpleExoPlayer.d);
                    simpleExoPlayer.l.a(builder.o);
                    simpleExoPlayer.m = new AudioFocusManager(builder.a, handler, simpleExoPlayer.d);
                    simpleExoPlayer.m.a(builder.m ? simpleExoPlayer.G : null);
                    simpleExoPlayer.n = new StreamVolumeManager(builder.a, handler, simpleExoPlayer.d);
                    simpleExoPlayer.n.a(Util.getStreamTypeForAudioUsage(simpleExoPlayer.G.usage));
                    simpleExoPlayer.o = new i(builder.a);
                    simpleExoPlayer.o.a(builder.n != 0);
                    simpleExoPlayer.p = new j(builder.a);
                    simpleExoPlayer.p.a(builder.n == 2);
                    simpleExoPlayer.R = b(simpleExoPlayer.n);
                    simpleExoPlayer.S = VideoSize.UNKNOWN;
                    simpleExoPlayer.a(1, 102, Integer.valueOf(simpleExoPlayer.F));
                    simpleExoPlayer.a(2, 102, Integer.valueOf(simpleExoPlayer.F));
                    simpleExoPlayer.a(1, 3, simpleExoPlayer.G);
                    simpleExoPlayer.a(2, 4, Integer.valueOf(simpleExoPlayer.A));
                    simpleExoPlayer.a(1, 101, Boolean.valueOf(simpleExoPlayer.I));
                    simpleExoPlayer.a(2, 6, simpleExoPlayer.e);
                    simpleExoPlayer.a(6, 7, simpleExoPlayer.e);
                    simpleExoPlayer.a.open();
                } catch (Throwable th2) {
                    th = th2;
                    simpleExoPlayer.a.open();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                simpleExoPlayer = this;
            }
        } catch (Throwable th4) {
            th = th4;
            simpleExoPlayer = this;
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void experimentalSetOffloadSchedulingEnabled(boolean z) {
        e();
        this.c.experimentalSetOffloadSchedulingEnabled(z);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean experimentalIsSleepingForOffload() {
        e();
        return this.c.experimentalIsSleepingForOffload();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoScalingMode(int i) {
        e();
        this.A = i;
        a(2, 4, Integer.valueOf(i));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public int getVideoScalingMode() {
        return this.A;
    }

    @Override // com.google.android.exoplayer2.Player
    public VideoSize getVideoSize() {
        return this.S;
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface() {
        e();
        a();
        a((Object) null);
        a(0, 0);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface(@Nullable Surface surface) {
        e();
        if (surface != null && surface == this.u) {
            clearVideoSurface();
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurface(@Nullable Surface surface) {
        e();
        a();
        a(surface);
        int i = surface == null ? 0 : -1;
        a(i, i);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        e();
        if (surfaceHolder == null) {
            clearVideoSurface();
            return;
        }
        a();
        this.y = true;
        this.w = surfaceHolder;
        surfaceHolder.addCallback(this.d);
        Surface surface = surfaceHolder.getSurface();
        if (surface == null || !surface.isValid()) {
            a((Object) null);
            a(0, 0);
            return;
        }
        a(surface);
        Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
        a(surfaceFrame.width(), surfaceFrame.height());
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        e();
        if (surfaceHolder != null && surfaceHolder == this.w) {
            clearVideoSurface();
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        e();
        if (surfaceView instanceof VideoDecoderOutputBufferRenderer) {
            a();
            a(surfaceView);
            a(surfaceView.getHolder());
        } else if (surfaceView instanceof SphericalGLSurfaceView) {
            a();
            this.x = (SphericalGLSurfaceView) surfaceView;
            this.c.createMessage(this.e).setType(10000).setPayload(this.x).send();
            this.x.addVideoSurfaceListener(this.d);
            a(this.x.getVideoSurface());
            a(surfaceView.getHolder());
        } else {
            setVideoSurfaceHolder(surfaceView == null ? null : surfaceView.getHolder());
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        e();
        clearVideoSurfaceHolder(surfaceView == null ? null : surfaceView.getHolder());
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoTextureView(@Nullable TextureView textureView) {
        e();
        if (textureView == null) {
            clearVideoSurface();
            return;
        }
        a();
        this.z = textureView;
        if (textureView.getSurfaceTextureListener() != null) {
            Log.w("SimpleExoPlayer", "Replacing existing SurfaceTextureListener.");
        }
        textureView.setSurfaceTextureListener(this.d);
        SurfaceTexture surfaceTexture = textureView.isAvailable() ? textureView.getSurfaceTexture() : null;
        if (surfaceTexture == null) {
            a((Object) null);
            a(0, 0);
            return;
        }
        a(surfaceTexture);
        a(textureView.getWidth(), textureView.getHeight());
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoTextureView(@Nullable TextureView textureView) {
        e();
        if (textureView != null && textureView == this.z) {
            clearVideoSurface();
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.c.addAudioOffloadListener(audioOffloadListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void removeAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.c.removeAudioOffloadListener(audioOffloadListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    @Deprecated
    public void addAudioListener(AudioListener audioListener) {
        Assertions.checkNotNull(audioListener);
        this.g.add(audioListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    @Deprecated
    public void removeAudioListener(AudioListener audioListener) {
        this.g.remove(audioListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAudioAttributes(AudioAttributes audioAttributes, boolean z) {
        e();
        if (!this.Q) {
            if (!Util.areEqual(this.G, audioAttributes)) {
                this.G = audioAttributes;
                a(1, 3, audioAttributes);
                this.n.a(Util.getStreamTypeForAudioUsage(audioAttributes.usage));
                this.k.onAudioAttributesChanged(audioAttributes);
                Iterator<AudioListener> it = this.g.iterator();
                while (it.hasNext()) {
                    it.next().onAudioAttributesChanged(audioAttributes);
                }
            }
            AudioFocusManager audioFocusManager = this.m;
            if (!z) {
                audioAttributes = null;
            }
            audioFocusManager.a(audioAttributes);
            boolean playWhenReady = getPlayWhenReady();
            int a2 = this.m.a(playWhenReady, getPlaybackState());
            a(playWhenReady, a2, b(playWhenReady, a2));
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public AudioAttributes getAudioAttributes() {
        return this.G;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAudioSessionId(int i) {
        e();
        if (this.F != i) {
            if (i == 0) {
                if (Util.SDK_INT < 21) {
                    i = a(0);
                } else {
                    i = C.generateAudioSessionIdV21(this.b);
                }
            } else if (Util.SDK_INT < 21) {
                a(i);
            }
            this.F = i;
            a(1, 102, Integer.valueOf(i));
            a(2, 102, Integer.valueOf(i));
            this.k.onAudioSessionIdChanged(i);
            Iterator<AudioListener> it = this.g.iterator();
            while (it.hasNext()) {
                it.next().onAudioSessionIdChanged(i);
            }
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public int getAudioSessionId() {
        return this.F;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        e();
        a(1, 5, auxEffectInfo);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void clearAuxEffectInfo() {
        setAuxEffectInfo(new AuxEffectInfo(0, 0.0f));
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVolume(float f) {
        e();
        float constrainValue = Util.constrainValue(f, 0.0f, 1.0f);
        if (this.H != constrainValue) {
            this.H = constrainValue;
            b();
            this.k.onVolumeChanged(constrainValue);
            Iterator<AudioListener> it = this.g.iterator();
            while (it.hasNext()) {
                it.next().onVolumeChanged(constrainValue);
            }
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public float getVolume() {
        return this.H;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public boolean getSkipSilenceEnabled() {
        return this.I;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setSkipSilenceEnabled(boolean z) {
        e();
        if (this.I != z) {
            this.I = z;
            a(1, 101, Boolean.valueOf(z));
            c();
        }
    }

    public AnalyticsCollector getAnalyticsCollector() {
        return this.k;
    }

    public void addAnalyticsListener(AnalyticsListener analyticsListener) {
        Assertions.checkNotNull(analyticsListener);
        this.k.addListener(analyticsListener);
    }

    public void removeAnalyticsListener(AnalyticsListener analyticsListener) {
        this.k.removeListener(analyticsListener);
    }

    public void setHandleAudioBecomingNoisy(boolean z) {
        e();
        if (!this.Q) {
            this.l.a(z);
        }
    }

    public void setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
        e();
        if (!Util.areEqual(this.O, priorityTaskManager)) {
            if (this.P) {
                ((PriorityTaskManager) Assertions.checkNotNull(this.O)).remove(0);
            }
            if (priorityTaskManager == null || !isLoading()) {
                this.P = false;
            } else {
                priorityTaskManager.add(0);
                this.P = true;
            }
            this.O = priorityTaskManager;
        }
    }

    @Nullable
    public Format getVideoFormat() {
        return this.r;
    }

    @Nullable
    public Format getAudioFormat() {
        return this.s;
    }

    @Nullable
    public DecoderCounters getVideoDecoderCounters() {
        return this.D;
    }

    @Nullable
    public DecoderCounters getAudioDecoderCounters() {
        return this.E;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    @Deprecated
    public void addVideoListener(VideoListener videoListener) {
        Assertions.checkNotNull(videoListener);
        this.f.add(videoListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    @Deprecated
    public void removeVideoListener(VideoListener videoListener) {
        this.f.remove(videoListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        e();
        this.K = videoFrameMetadataListener;
        this.c.createMessage(this.e).setType(6).setPayload(videoFrameMetadataListener).send();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        e();
        if (this.K == videoFrameMetadataListener) {
            this.c.createMessage(this.e).setType(6).setPayload(null).send();
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setCameraMotionListener(CameraMotionListener cameraMotionListener) {
        e();
        this.L = cameraMotionListener;
        this.c.createMessage(this.e).setType(7).setPayload(cameraMotionListener).send();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearCameraMotionListener(CameraMotionListener cameraMotionListener) {
        e();
        if (this.L == cameraMotionListener) {
            this.c.createMessage(this.e).setType(7).setPayload(null).send();
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.TextComponent
    @Deprecated
    public void addTextOutput(TextOutput textOutput) {
        Assertions.checkNotNull(textOutput);
        this.h.add(textOutput);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.TextComponent
    @Deprecated
    public void removeTextOutput(TextOutput textOutput) {
        this.h.remove(textOutput);
    }

    @Override // com.google.android.exoplayer2.Player
    public List<Cue> getCurrentCues() {
        e();
        return this.J;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.MetadataComponent
    @Deprecated
    public void addMetadataOutput(MetadataOutput metadataOutput) {
        Assertions.checkNotNull(metadataOutput);
        this.i.add(metadataOutput);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.MetadataComponent
    @Deprecated
    public void removeMetadataOutput(MetadataOutput metadataOutput) {
        this.i.remove(metadataOutput);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Looper getPlaybackLooper() {
        return this.c.getPlaybackLooper();
    }

    @Override // com.google.android.exoplayer2.Player
    public Looper getApplicationLooper() {
        return this.c.getApplicationLooper();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Clock getClock() {
        return this.c.getClock();
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.Listener listener) {
        Assertions.checkNotNull(listener);
        addAudioListener(listener);
        addVideoListener(listener);
        addTextOutput(listener);
        addMetadataOutput(listener);
        addDeviceListener(listener);
        addListener((Player.EventListener) listener);
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void addListener(Player.EventListener eventListener) {
        Assertions.checkNotNull(eventListener);
        this.c.addListener(eventListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.Listener listener) {
        Assertions.checkNotNull(listener);
        removeAudioListener(listener);
        removeVideoListener(listener);
        removeTextOutput(listener);
        removeMetadataOutput(listener);
        removeDeviceListener(listener);
        removeListener((Player.EventListener) listener);
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void removeListener(Player.EventListener eventListener) {
        this.c.removeListener(eventListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackState() {
        e();
        return this.c.getPlaybackState();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackSuppressionReason() {
        e();
        return this.c.getPlaybackSuppressionReason();
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public ExoPlaybackException getPlayerError() {
        e();
        return this.c.getPlayerError();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void retry() {
        e();
        prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public Player.Commands getAvailableCommands() {
        e();
        return this.c.getAvailableCommands();
    }

    @Override // com.google.android.exoplayer2.Player
    public void prepare() {
        e();
        boolean playWhenReady = getPlayWhenReady();
        int a2 = this.m.a(playWhenReady, 2);
        a(playWhenReady, a2, b(playWhenReady, a2));
        this.c.prepare();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource) {
        prepare(mediaSource, true, true);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource, boolean z, boolean z2) {
        e();
        setMediaSources(Collections.singletonList(mediaSource), z);
        prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, boolean z) {
        e();
        this.c.setMediaItems(list, z);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, int i, long j) {
        e();
        this.c.setMediaItems(list, i, j);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list) {
        e();
        this.c.setMediaSources(list);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list, boolean z) {
        e();
        this.c.setMediaSources(list, z);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list, int i, long j) {
        e();
        this.c.setMediaSources(list, i, j);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource) {
        e();
        this.c.setMediaSource(mediaSource);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, boolean z) {
        e();
        this.c.setMediaSource(mediaSource, z);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, long j) {
        e();
        this.c.setMediaSource(mediaSource, j);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(int i, List<MediaItem> list) {
        e();
        this.c.addMediaItems(i, list);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(MediaSource mediaSource) {
        e();
        this.c.addMediaSource(mediaSource);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(int i, MediaSource mediaSource) {
        e();
        this.c.addMediaSource(i, mediaSource);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(List<MediaSource> list) {
        e();
        this.c.addMediaSources(list);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(int i, List<MediaSource> list) {
        e();
        this.c.addMediaSources(i, list);
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItems(int i, int i2, int i3) {
        e();
        this.c.moveMediaItems(i, i2, i3);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItems(int i, int i2) {
        e();
        this.c.removeMediaItems(i, i2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        e();
        this.c.setShuffleOrder(shuffleOrder);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlayWhenReady(boolean z) {
        e();
        int a2 = this.m.a(z, getPlaybackState());
        a(z, a2, b(z, a2));
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getPlayWhenReady() {
        e();
        return this.c.getPlayWhenReady();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setPauseAtEndOfMediaItems(boolean z) {
        e();
        this.c.setPauseAtEndOfMediaItems(z);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean getPauseAtEndOfMediaItems() {
        e();
        return this.c.getPauseAtEndOfMediaItems();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getRepeatMode() {
        e();
        return this.c.getRepeatMode();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setRepeatMode(int i) {
        e();
        this.c.setRepeatMode(i);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setShuffleModeEnabled(boolean z) {
        e();
        this.c.setShuffleModeEnabled(z);
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getShuffleModeEnabled() {
        e();
        return this.c.getShuffleModeEnabled();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isLoading() {
        e();
        return this.c.isLoading();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(int i, long j) {
        e();
        this.k.notifySeekStarted();
        this.c.seekTo(i, j);
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekBackIncrement() {
        e();
        return this.c.getSeekBackIncrement();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekForwardIncrement() {
        e();
        return this.c.getSeekForwardIncrement();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getMaxSeekToPreviousPosition() {
        e();
        return this.c.getMaxSeekToPreviousPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        e();
        this.c.setPlaybackParameters(playbackParameters);
    }

    @Override // com.google.android.exoplayer2.Player
    public PlaybackParameters getPlaybackParameters() {
        e();
        return this.c.getPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setSeekParameters(@Nullable SeekParameters seekParameters) {
        e();
        this.c.setSeekParameters(seekParameters);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public SeekParameters getSeekParameters() {
        e();
        return this.c.getSeekParameters();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setForegroundMode(boolean z) {
        e();
        this.c.setForegroundMode(z);
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void stop(boolean z) {
        e();
        this.m.a(getPlayWhenReady(), 1);
        this.c.stop(z);
        this.J = Collections.emptyList();
    }

    @Override // com.google.android.exoplayer2.Player
    public void release() {
        AudioTrack audioTrack;
        e();
        if (Util.SDK_INT < 21 && (audioTrack = this.t) != null) {
            audioTrack.release();
            this.t = null;
        }
        this.l.a(false);
        this.n.g();
        this.o.b(false);
        this.p.b(false);
        this.m.b();
        this.c.release();
        this.k.release();
        a();
        Surface surface = this.v;
        if (surface != null) {
            surface.release();
            this.v = null;
        }
        if (this.P) {
            ((PriorityTaskManager) Assertions.checkNotNull(this.O)).remove(0);
            this.P = false;
        }
        this.J = Collections.emptyList();
        this.Q = true;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public PlayerMessage createMessage(PlayerMessage.Target target) {
        e();
        return this.c.createMessage(target);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererCount() {
        e();
        return this.c.getRendererCount();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererType(int i) {
        e();
        return this.c.getRendererType(i);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public TrackSelector getTrackSelector() {
        e();
        return this.c.getTrackSelector();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackGroupArray getCurrentTrackGroups() {
        e();
        return this.c.getCurrentTrackGroups();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionArray getCurrentTrackSelections() {
        e();
        return this.c.getCurrentTrackSelections();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public List<Metadata> getCurrentStaticMetadata() {
        e();
        return this.c.getCurrentStaticMetadata();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getMediaMetadata() {
        return this.c.getMediaMetadata();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getPlaylistMetadata() {
        return this.c.getPlaylistMetadata();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaylistMetadata(MediaMetadata mediaMetadata) {
        this.c.setPlaylistMetadata(mediaMetadata);
    }

    @Override // com.google.android.exoplayer2.Player
    public Timeline getCurrentTimeline() {
        e();
        return this.c.getCurrentTimeline();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentPeriodIndex() {
        e();
        return this.c.getCurrentPeriodIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentWindowIndex() {
        e();
        return this.c.getCurrentWindowIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getDuration() {
        e();
        return this.c.getDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentPosition() {
        e();
        return this.c.getCurrentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getBufferedPosition() {
        e();
        return this.c.getBufferedPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getTotalBufferedDuration() {
        e();
        return this.c.getTotalBufferedDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlayingAd() {
        e();
        return this.c.isPlayingAd();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdGroupIndex() {
        e();
        return this.c.getCurrentAdGroupIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdIndexInAdGroup() {
        e();
        return this.c.getCurrentAdIndexInAdGroup();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentPosition() {
        e();
        return this.c.getContentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentBufferedPosition() {
        e();
        return this.c.getContentBufferedPosition();
    }

    @Deprecated
    public void setHandleWakeLock(boolean z) {
        setWakeMode(z ? 1 : 0);
    }

    public void setWakeMode(int i) {
        e();
        switch (i) {
            case 0:
                this.o.a(false);
                this.p.a(false);
                return;
            case 1:
                this.o.a(true);
                this.p.a(false);
                return;
            case 2:
                this.o.a(true);
                this.p.a(true);
                return;
            default:
                return;
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.DeviceComponent
    @Deprecated
    public void addDeviceListener(DeviceListener deviceListener) {
        Assertions.checkNotNull(deviceListener);
        this.j.add(deviceListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.DeviceComponent
    @Deprecated
    public void removeDeviceListener(DeviceListener deviceListener) {
        this.j.remove(deviceListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public DeviceInfo getDeviceInfo() {
        e();
        return this.R;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getDeviceVolume() {
        e();
        return this.n.c();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isDeviceMuted() {
        e();
        return this.n.d();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceVolume(int i) {
        e();
        this.n.b(i);
    }

    @Override // com.google.android.exoplayer2.Player
    public void increaseDeviceVolume() {
        e();
        this.n.e();
    }

    @Override // com.google.android.exoplayer2.Player
    public void decreaseDeviceVolume() {
        e();
        this.n.f();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceMuted(boolean z) {
        e();
        this.n.a(z);
    }

    @Deprecated
    public void setThrowsWhenUsingWrongThread(boolean z) {
        this.M = z;
    }

    private void a() {
        if (this.x != null) {
            this.c.createMessage(this.e).setType(10000).setPayload(null).send();
            this.x.removeVideoSurfaceListener(this.d);
            this.x = null;
        }
        TextureView textureView = this.z;
        if (textureView != null) {
            if (textureView.getSurfaceTextureListener() != this.d) {
                Log.w("SimpleExoPlayer", "SurfaceTextureListener already unset or replaced.");
            } else {
                this.z.setSurfaceTextureListener(null);
            }
            this.z = null;
        }
        SurfaceHolder surfaceHolder = this.w;
        if (surfaceHolder != null) {
            surfaceHolder.removeCallback(this.d);
            this.w = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(SurfaceTexture surfaceTexture) {
        Surface surface = new Surface(surfaceTexture);
        a(surface);
        this.v = surface;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@Nullable Object obj) {
        boolean z;
        ArrayList<PlayerMessage> arrayList = new ArrayList();
        Renderer[] rendererArr = this.renderers;
        int length = rendererArr.length;
        int i = 0;
        while (true) {
            z = true;
            if (i >= length) {
                break;
            }
            Renderer renderer = rendererArr[i];
            if (renderer.getTrackType() == 2) {
                arrayList.add(this.c.createMessage(renderer).setType(1).setPayload(obj).send());
            }
            i++;
        }
        Object obj2 = this.u;
        if (obj2 == null || obj2 == obj) {
            z = false;
        } else {
            try {
                for (PlayerMessage playerMessage : arrayList) {
                    playerMessage.blockUntilDelivered(this.q);
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (TimeoutException unused2) {
            }
            z = false;
            Object obj3 = this.u;
            Surface surface = this.v;
            if (obj3 == surface) {
                surface.release();
                this.v = null;
            }
        }
        this.u = obj;
        if (z) {
            this.c.a(false, ExoPlaybackException.createForUnexpected(new ExoTimeoutException(3), 1003));
        }
    }

    private void a(SurfaceHolder surfaceHolder) {
        this.y = false;
        this.w = surfaceHolder;
        this.w.addCallback(this.d);
        Surface surface = this.w.getSurface();
        if (surface == null || !surface.isValid()) {
            a(0, 0);
            return;
        }
        Rect surfaceFrame = this.w.getSurfaceFrame();
        a(surfaceFrame.width(), surfaceFrame.height());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, int i2) {
        if (i != this.B || i2 != this.C) {
            this.B = i;
            this.C = i2;
            this.k.onSurfaceSizeChanged(i, i2);
            Iterator<VideoListener> it = this.f.iterator();
            while (it.hasNext()) {
                it.next().onSurfaceSizeChanged(i, i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        a(1, 2, Float.valueOf(this.H * this.m.a()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        this.k.onSkipSilenceEnabledChanged(this.I);
        Iterator<AudioListener> it = this.g.iterator();
        while (it.hasNext()) {
            it.next().onSkipSilenceEnabledChanged(this.I);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z, int i, int i2) {
        int i3 = 0;
        boolean z2 = z && i != -1;
        if (z2 && i != 1) {
            i3 = 1;
        }
        this.c.a(z2, i3, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        boolean z = false;
        switch (getPlaybackState()) {
            case 1:
            case 4:
                this.o.b(false);
                this.p.b(false);
                return;
            case 2:
            case 3:
                boolean experimentalIsSleepingForOffload = experimentalIsSleepingForOffload();
                i iVar = this.o;
                if (getPlayWhenReady() && !experimentalIsSleepingForOffload) {
                    z = true;
                }
                iVar.b(z);
                this.p.b(getPlayWhenReady());
                return;
            default:
                throw new IllegalStateException();
        }
    }

    private void e() {
        this.a.blockUninterruptible();
        if (Thread.currentThread() != getApplicationLooper().getThread()) {
            String formatInvariant = Util.formatInvariant("Player is accessed on the wrong thread.\nCurrent thread: '%s'\nExpected thread: '%s'\nSee https://exoplayer.dev/issues/player-accessed-on-wrong-thread", Thread.currentThread().getName(), getApplicationLooper().getThread().getName());
            if (!this.M) {
                Log.w("SimpleExoPlayer", formatInvariant, this.N ? null : new IllegalStateException());
                this.N = true;
                return;
            }
            throw new IllegalStateException(formatInvariant);
        }
    }

    private void a(int i, int i2, @Nullable Object obj) {
        Renderer[] rendererArr = this.renderers;
        for (Renderer renderer : rendererArr) {
            if (renderer.getTrackType() == i) {
                this.c.createMessage(renderer).setType(i2).setPayload(obj).send();
            }
        }
    }

    private int a(int i) {
        AudioTrack audioTrack = this.t;
        if (!(audioTrack == null || audioTrack.getAudioSessionId() == i)) {
            this.t.release();
            this.t = null;
        }
        if (this.t == null) {
            this.t = new AudioTrack(3, 4000, 4, 2, 2, 0, i);
        }
        return this.t.getAudioSessionId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DeviceInfo b(StreamVolumeManager streamVolumeManager) {
        return new DeviceInfo(0, streamVolumeManager.a(), streamVolumeManager.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class a implements SurfaceHolder.Callback, TextureView.SurfaceTextureListener, AudioBecomingNoisyManager.EventListener, AudioFocusManager.PlayerControl, ExoPlayer.AudioOffloadListener, Player.EventListener, StreamVolumeManager.Listener, AudioRendererEventListener, MetadataOutput, TextOutput, VideoRendererEventListener, SphericalGLSurfaceView.VideoSurfaceListener {
        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        private a() {
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoEnabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer.this.D = decoderCounters;
            SimpleExoPlayer.this.k.onVideoEnabled(decoderCounters);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDecoderInitialized(String str, long j, long j2) {
            SimpleExoPlayer.this.k.onVideoDecoderInitialized(str, j, j2);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            SimpleExoPlayer.this.r = format;
            SimpleExoPlayer.this.k.onVideoInputFormatChanged(format, decoderReuseEvaluation);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onDroppedFrames(int i, long j) {
            SimpleExoPlayer.this.k.onDroppedFrames(i, j);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoSizeChanged(VideoSize videoSize) {
            SimpleExoPlayer.this.S = videoSize;
            SimpleExoPlayer.this.k.onVideoSizeChanged(videoSize);
            Iterator it = SimpleExoPlayer.this.f.iterator();
            while (it.hasNext()) {
                VideoListener videoListener = (VideoListener) it.next();
                videoListener.onVideoSizeChanged(videoSize);
                videoListener.onVideoSizeChanged(videoSize.width, videoSize.height, videoSize.unappliedRotationDegrees, videoSize.pixelWidthHeightRatio);
            }
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onRenderedFirstFrame(Object obj, long j) {
            SimpleExoPlayer.this.k.onRenderedFirstFrame(obj, j);
            if (SimpleExoPlayer.this.u == obj) {
                Iterator it = SimpleExoPlayer.this.f.iterator();
                while (it.hasNext()) {
                    ((VideoListener) it.next()).onRenderedFirstFrame();
                }
            }
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDecoderReleased(String str) {
            SimpleExoPlayer.this.k.onVideoDecoderReleased(str);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDisabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer.this.k.onVideoDisabled(decoderCounters);
            SimpleExoPlayer.this.r = null;
            SimpleExoPlayer.this.D = null;
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoFrameProcessingOffset(long j, int i) {
            SimpleExoPlayer.this.k.onVideoFrameProcessingOffset(j, i);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoCodecError(Exception exc) {
            SimpleExoPlayer.this.k.onVideoCodecError(exc);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioEnabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer.this.E = decoderCounters;
            SimpleExoPlayer.this.k.onAudioEnabled(decoderCounters);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDecoderInitialized(String str, long j, long j2) {
            SimpleExoPlayer.this.k.onAudioDecoderInitialized(str, j, j2);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            SimpleExoPlayer.this.s = format;
            SimpleExoPlayer.this.k.onAudioInputFormatChanged(format, decoderReuseEvaluation);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioPositionAdvancing(long j) {
            SimpleExoPlayer.this.k.onAudioPositionAdvancing(j);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioUnderrun(int i, long j, long j2) {
            SimpleExoPlayer.this.k.onAudioUnderrun(i, j, j2);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDecoderReleased(String str) {
            SimpleExoPlayer.this.k.onAudioDecoderReleased(str);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDisabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer.this.k.onAudioDisabled(decoderCounters);
            SimpleExoPlayer.this.s = null;
            SimpleExoPlayer.this.E = null;
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onSkipSilenceEnabledChanged(boolean z) {
            if (SimpleExoPlayer.this.I != z) {
                SimpleExoPlayer.this.I = z;
                SimpleExoPlayer.this.c();
            }
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioSinkError(Exception exc) {
            SimpleExoPlayer.this.k.onAudioSinkError(exc);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioCodecError(Exception exc) {
            SimpleExoPlayer.this.k.onAudioCodecError(exc);
        }

        @Override // com.google.android.exoplayer2.text.TextOutput
        public void onCues(List<Cue> list) {
            SimpleExoPlayer.this.J = list;
            Iterator it = SimpleExoPlayer.this.h.iterator();
            while (it.hasNext()) {
                ((TextOutput) it.next()).onCues(list);
            }
        }

        @Override // com.google.android.exoplayer2.metadata.MetadataOutput
        public void onMetadata(Metadata metadata) {
            SimpleExoPlayer.this.k.onMetadata(metadata);
            SimpleExoPlayer.this.c.a(metadata);
            Iterator it = SimpleExoPlayer.this.i.iterator();
            while (it.hasNext()) {
                ((MetadataOutput) it.next()).onMetadata(metadata);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (SimpleExoPlayer.this.y) {
                SimpleExoPlayer.this.a(surfaceHolder.getSurface());
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            SimpleExoPlayer.this.a(i2, i3);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (SimpleExoPlayer.this.y) {
                SimpleExoPlayer.this.a((Object) null);
            }
            SimpleExoPlayer.this.a(0, 0);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            SimpleExoPlayer.this.a(surfaceTexture);
            SimpleExoPlayer.this.a(i, i2);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            SimpleExoPlayer.this.a(i, i2);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            SimpleExoPlayer.this.a((Object) null);
            SimpleExoPlayer.this.a(0, 0);
            return true;
        }

        @Override // com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView.VideoSurfaceListener
        public void onVideoSurfaceCreated(Surface surface) {
            SimpleExoPlayer.this.a(surface);
        }

        @Override // com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView.VideoSurfaceListener
        public void onVideoSurfaceDestroyed(Surface surface) {
            SimpleExoPlayer.this.a((Object) null);
        }

        @Override // com.google.android.exoplayer2.AudioFocusManager.PlayerControl
        public void setVolumeMultiplier(float f) {
            SimpleExoPlayer.this.b();
        }

        @Override // com.google.android.exoplayer2.AudioFocusManager.PlayerControl
        public void executePlayerCommand(int i) {
            boolean playWhenReady = SimpleExoPlayer.this.getPlayWhenReady();
            SimpleExoPlayer.this.a(playWhenReady, i, SimpleExoPlayer.b(playWhenReady, i));
        }

        @Override // com.google.android.exoplayer2.AudioBecomingNoisyManager.EventListener
        public void onAudioBecomingNoisy() {
            SimpleExoPlayer.this.a(false, -1, 3);
        }

        @Override // com.google.android.exoplayer2.StreamVolumeManager.Listener
        public void onStreamTypeChanged(int i) {
            DeviceInfo b = SimpleExoPlayer.b(SimpleExoPlayer.this.n);
            if (!b.equals(SimpleExoPlayer.this.R)) {
                SimpleExoPlayer.this.R = b;
                Iterator it = SimpleExoPlayer.this.j.iterator();
                while (it.hasNext()) {
                    ((DeviceListener) it.next()).onDeviceInfoChanged(b);
                }
            }
        }

        @Override // com.google.android.exoplayer2.StreamVolumeManager.Listener
        public void onStreamVolumeChanged(int i, boolean z) {
            Iterator it = SimpleExoPlayer.this.j.iterator();
            while (it.hasNext()) {
                ((DeviceListener) it.next()).onDeviceVolumeChanged(i, z);
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onIsLoadingChanged(boolean z) {
            if (SimpleExoPlayer.this.O == null) {
                return;
            }
            if (z && !SimpleExoPlayer.this.P) {
                SimpleExoPlayer.this.O.add(0);
                SimpleExoPlayer.this.P = true;
            } else if (!z && SimpleExoPlayer.this.P) {
                SimpleExoPlayer.this.O.remove(0);
                SimpleExoPlayer.this.P = false;
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackStateChanged(int i) {
            SimpleExoPlayer.this.d();
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayWhenReadyChanged(boolean z, int i) {
            SimpleExoPlayer.this.d();
        }

        @Override // com.google.android.exoplayer2.ExoPlayer.AudioOffloadListener
        public void onExperimentalSleepingForOffloadChanged(boolean z) {
            SimpleExoPlayer.this.d();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b implements PlayerMessage.Target, VideoFrameMetadataListener, CameraMotionListener {
        @Nullable
        private VideoFrameMetadataListener a;
        @Nullable
        private CameraMotionListener b;
        @Nullable
        private VideoFrameMetadataListener c;
        @Nullable
        private CameraMotionListener d;

        private b() {
        }

        @Override // com.google.android.exoplayer2.PlayerMessage.Target
        public void handleMessage(int i, @Nullable Object obj) {
            if (i != 10000) {
                switch (i) {
                    case 6:
                        this.a = (VideoFrameMetadataListener) obj;
                        return;
                    case 7:
                        this.b = (CameraMotionListener) obj;
                        return;
                    default:
                        return;
                }
            } else {
                SphericalGLSurfaceView sphericalGLSurfaceView = (SphericalGLSurfaceView) obj;
                if (sphericalGLSurfaceView == null) {
                    this.c = null;
                    this.d = null;
                    return;
                }
                this.c = sphericalGLSurfaceView.getVideoFrameMetadataListener();
                this.d = sphericalGLSurfaceView.getCameraMotionListener();
            }
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameMetadataListener
        public void onVideoFrameAboutToBeRendered(long j, long j2, Format format, @Nullable MediaFormat mediaFormat) {
            VideoFrameMetadataListener videoFrameMetadataListener = this.c;
            if (videoFrameMetadataListener != null) {
                videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j, j2, format, mediaFormat);
            }
            VideoFrameMetadataListener videoFrameMetadataListener2 = this.a;
            if (videoFrameMetadataListener2 != null) {
                videoFrameMetadataListener2.onVideoFrameAboutToBeRendered(j, j2, format, mediaFormat);
            }
        }

        @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
        public void onCameraMotion(long j, float[] fArr) {
            CameraMotionListener cameraMotionListener = this.d;
            if (cameraMotionListener != null) {
                cameraMotionListener.onCameraMotion(j, fArr);
            }
            CameraMotionListener cameraMotionListener2 = this.b;
            if (cameraMotionListener2 != null) {
                cameraMotionListener2.onCameraMotion(j, fArr);
            }
        }

        @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
        public void onCameraMotionReset() {
            CameraMotionListener cameraMotionListener = this.d;
            if (cameraMotionListener != null) {
                cameraMotionListener.onCameraMotionReset();
            }
            CameraMotionListener cameraMotionListener2 = this.b;
            if (cameraMotionListener2 != null) {
                cameraMotionListener2.onCameraMotionReset();
            }
        }
    }
}
