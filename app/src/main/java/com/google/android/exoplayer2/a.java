package com.google.android.exoplayer2;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerImplInternal;
import com.google.android.exoplayer2.MediaSourceList;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ExoPlayerImpl.java */
/* loaded from: classes.dex */
public final class a extends BasePlayer implements ExoPlayer {
    private SeekParameters A;
    private ShuffleOrder B;
    private boolean C;
    private Player.Commands D;
    private MediaMetadata E;
    private MediaMetadata F;
    private g G;
    private int H;
    private int I;
    private long J;
    final TrackSelectorResult a;
    final Player.Commands b;
    private final Renderer[] c;
    private final TrackSelector d;
    private final HandlerWrapper e;
    private final ExoPlayerImplInternal.PlaybackInfoUpdateListener f;
    private final ExoPlayerImplInternal g;
    private final ListenerSet<Player.EventListener> h;
    private final CopyOnWriteArraySet<ExoPlayer.AudioOffloadListener> i;
    private final Timeline.Period j;
    private final List<C0056a> k;
    private final boolean l;
    private final MediaSourceFactory m;
    @Nullable
    private final AnalyticsCollector n;
    private final Looper o;
    private final BandwidthMeter p;
    private final long q;
    private final long r;
    private final Clock s;
    private int t;
    private boolean u;
    private int v;
    private int w;
    private boolean x;
    private int y;
    private boolean z;

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface() {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface(@Nullable Surface surface) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceView(@Nullable SurfaceView surfaceView) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoTextureView(@Nullable TextureView textureView) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void decreaseDeviceVolume() {
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.AudioComponent getAudioComponent() {
        return null;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.DeviceComponent getDeviceComponent() {
        return null;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getDeviceVolume() {
        return 0;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getMaxSeekToPreviousPosition() {
        return 3000;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.MetadataComponent getMetadataComponent() {
        return null;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.TextComponent getTextComponent() {
        return null;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.VideoComponent getVideoComponent() {
        return null;
    }

    @Override // com.google.android.exoplayer2.Player
    public float getVolume() {
        return 1.0f;
    }

    @Override // com.google.android.exoplayer2.Player
    public void increaseDeviceVolume() {
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isDeviceMuted() {
        return false;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceMuted(boolean z) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceVolume(int i) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurface(@Nullable Surface surface) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceView(@Nullable SurfaceView surfaceView) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoTextureView(@Nullable TextureView textureView) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVolume(float f) {
    }

    @SuppressLint({"HandlerLeak"})
    public a(Renderer[] rendererArr, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter, @Nullable AnalyticsCollector analyticsCollector, boolean z, SeekParameters seekParameters, long j, long j2, LivePlaybackSpeedControl livePlaybackSpeedControl, long j3, boolean z2, Clock clock, Looper looper, @Nullable Player player, Player.Commands commands) {
        String hexString = Integer.toHexString(System.identityHashCode(this));
        String str = Util.DEVICE_DEBUG_INFO;
        StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 30 + String.valueOf(str).length());
        sb.append("Init ");
        sb.append(hexString);
        sb.append(" [");
        sb.append(ExoPlayerLibraryInfo.VERSION_SLASHY);
        sb.append("] [");
        sb.append(str);
        sb.append("]");
        Log.i("ExoPlayerImpl", sb.toString());
        Assertions.checkState(rendererArr.length > 0);
        this.c = (Renderer[]) Assertions.checkNotNull(rendererArr);
        this.d = (TrackSelector) Assertions.checkNotNull(trackSelector);
        this.m = mediaSourceFactory;
        this.p = bandwidthMeter;
        this.n = analyticsCollector;
        this.l = z;
        this.A = seekParameters;
        this.q = j;
        this.r = j2;
        this.C = z2;
        this.o = looper;
        this.s = clock;
        this.t = 0;
        final Player player2 = player != null ? player : this;
        this.h = new ListenerSet<>(looper, clock, new ListenerSet.IterationFinishedEvent() { // from class: com.google.android.exoplayer2.-$$Lambda$a$Jtj325rKv9GNi4_IbcbbfiIBhfs
            @Override // com.google.android.exoplayer2.util.ListenerSet.IterationFinishedEvent
            public final void invoke(Object obj, FlagSet flagSet) {
                a.a(Player.this, (Player.EventListener) obj, flagSet);
            }
        });
        this.i = new CopyOnWriteArraySet<>();
        this.k = new ArrayList();
        this.B = new ShuffleOrder.DefaultShuffleOrder(0);
        this.a = new TrackSelectorResult(new RendererConfiguration[rendererArr.length], new ExoTrackSelection[rendererArr.length], null);
        this.j = new Timeline.Period();
        this.b = new Player.Commands.Builder().addAll(1, 2, 12, 13, 14, 15, 16, 17, 18, 19).addAll(commands).build();
        this.D = new Player.Commands.Builder().addAll(this.b).add(3).add(9).build();
        this.E = MediaMetadata.EMPTY;
        this.F = MediaMetadata.EMPTY;
        this.H = -1;
        this.e = clock.createHandler(looper, null);
        this.f = new ExoPlayerImplInternal.PlaybackInfoUpdateListener() { // from class: com.google.android.exoplayer2.-$$Lambda$a$nuygeE-gtvG52o-dfi2MICsK2H4
            @Override // com.google.android.exoplayer2.ExoPlayerImplInternal.PlaybackInfoUpdateListener
            public final void onPlaybackInfoUpdate(ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
                a.this.b(playbackInfoUpdate);
            }
        };
        this.G = g.a(this.a);
        if (analyticsCollector != null) {
            analyticsCollector.setPlayer(player2, looper);
            addListener((Player.Listener) analyticsCollector);
            bandwidthMeter.addEventListener(new Handler(looper), analyticsCollector);
        }
        this.g = new ExoPlayerImplInternal(rendererArr, trackSelector, this.a, loadControl, bandwidthMeter, this.t, this.u, analyticsCollector, seekParameters, livePlaybackSpeedControl, j3, z2, looper, clock, this.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Player player, Player.EventListener eventListener, FlagSet flagSet) {
        eventListener.onEvents(player, new Player.Events(flagSet));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(final ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
        this.e.post(new Runnable() { // from class: com.google.android.exoplayer2.-$$Lambda$a$Z0uyVP4-VirKzQ1nw7Y_GJIx2Mw
            @Override // java.lang.Runnable
            public final void run() {
                a.this.c(playbackInfoUpdate);
            }
        });
    }

    public void a(long j) {
        this.g.a(j);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void experimentalSetOffloadSchedulingEnabled(boolean z) {
        this.g.a(z);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean experimentalIsSleepingForOffload() {
        return this.G.p;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Looper getPlaybackLooper() {
        return this.g.d();
    }

    @Override // com.google.android.exoplayer2.Player
    public Looper getApplicationLooper() {
        return this.o;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Clock getClock() {
        return this.s;
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.Listener listener) {
        addListener((Player.EventListener) listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.EventListener eventListener) {
        this.h.add(eventListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.Listener listener) {
        removeListener((Player.EventListener) listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.EventListener eventListener) {
        this.h.remove(eventListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.i.add(audioOffloadListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void removeAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.i.remove(audioOffloadListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public Player.Commands getAvailableCommands() {
        return this.D;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackState() {
        return this.G.e;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackSuppressionReason() {
        return this.G.m;
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public ExoPlaybackException getPlayerError() {
        return this.G.f;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void retry() {
        prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public void prepare() {
        if (this.G.e == 1) {
            g a = this.G.a((ExoPlaybackException) null);
            g a2 = a.a(a.a.isEmpty() ? 4 : 2);
            this.v++;
            this.g.a();
            a(a2, 1, 1, false, false, 5, C.TIME_UNSET, -1);
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource) {
        setMediaSource(mediaSource);
        prepare();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource, boolean z, boolean z2) {
        setMediaSource(mediaSource, z);
        prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, boolean z) {
        setMediaSources(a(list), z);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, int i, long j) {
        setMediaSources(a(list), i, j);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource) {
        setMediaSources(Collections.singletonList(mediaSource));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, long j) {
        setMediaSources(Collections.singletonList(mediaSource), 0, j);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, boolean z) {
        setMediaSources(Collections.singletonList(mediaSource), z);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list) {
        setMediaSources(list, true);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list, boolean z) {
        a(list, -1, C.TIME_UNSET, z);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list, int i, long j) {
        a(list, i, j, false);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(int i, List<MediaItem> list) {
        addMediaSources(Math.min(i, this.k.size()), a(list));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(MediaSource mediaSource) {
        addMediaSources(Collections.singletonList(mediaSource));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(int i, MediaSource mediaSource) {
        addMediaSources(i, Collections.singletonList(mediaSource));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(List<MediaSource> list) {
        addMediaSources(this.k.size(), list);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(int i, List<MediaSource> list) {
        Assertions.checkArgument(i >= 0);
        Timeline currentTimeline = getCurrentTimeline();
        this.v++;
        List<MediaSourceList.c> a = a(i, list);
        Timeline d = d();
        g a2 = a(this.G, d, a(currentTimeline, d));
        this.g.a(i, a, this.B);
        a(a2, 0, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItems(int i, int i2) {
        g a = a(i, Math.min(i2, this.k.size()));
        a(a, 0, 1, false, !a.b.periodUid.equals(this.G.b.periodUid), 4, a(a), -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItems(int i, int i2, int i3) {
        Assertions.checkArgument(i >= 0 && i <= i2 && i2 <= this.k.size() && i3 >= 0);
        Timeline currentTimeline = getCurrentTimeline();
        this.v++;
        int min = Math.min(i3, this.k.size() - (i2 - i));
        Util.moveItems(this.k, i, i2, min);
        Timeline d = d();
        g a = a(this.G, d, a(currentTimeline, d));
        this.g.a(i, i2, min, this.B);
        a(a, 0, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        Timeline d = d();
        g a = a(this.G, d, a(d, getCurrentWindowIndex(), getCurrentPosition()));
        this.v++;
        this.B = shuffleOrder;
        this.g.a(shuffleOrder);
        a(a, 0, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlayWhenReady(boolean z) {
        a(z, 0, 1);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setPauseAtEndOfMediaItems(boolean z) {
        if (this.C != z) {
            this.C = z;
            this.g.b(z);
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean getPauseAtEndOfMediaItems() {
        return this.C;
    }

    public void a(boolean z, int i, int i2) {
        if (this.G.l != z || this.G.m != i) {
            this.v++;
            g a = this.G.a(z, i);
            this.g.a(z, i);
            a(a, 0, i2, false, false, 5, C.TIME_UNSET, -1);
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getPlayWhenReady() {
        return this.G.l;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setRepeatMode(final int i) {
        if (this.t != i) {
            this.t = i;
            this.g.a(i);
            this.h.queueEvent(9, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$BnsExqZvWROcw3Waej1hFySKI_U
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onRepeatModeChanged(i);
                }
            });
            c();
            this.h.flushEvents();
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public int getRepeatMode() {
        return this.t;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setShuffleModeEnabled(final boolean z) {
        if (this.u != z) {
            this.u = z;
            this.g.c(z);
            this.h.queueEvent(10, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$A24BAvbpZwLbfK6yqTWrVWGkIfo
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onShuffleModeEnabledChanged(z);
                }
            });
            c();
            this.h.flushEvents();
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getShuffleModeEnabled() {
        return this.u;
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isLoading() {
        return this.G.g;
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(int i, long j) {
        Timeline timeline = this.G.a;
        if (i < 0 || (!timeline.isEmpty() && i >= timeline.getWindowCount())) {
            throw new IllegalSeekPositionException(timeline, i, j);
        }
        int i2 = 1;
        this.v++;
        if (isPlayingAd()) {
            Log.w("ExoPlayerImpl", "seekTo ignored because an ad is playing");
            ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate = new ExoPlayerImplInternal.PlaybackInfoUpdate(this.G);
            playbackInfoUpdate.incrementPendingOperationAcks(1);
            this.f.onPlaybackInfoUpdate(playbackInfoUpdate);
            return;
        }
        if (getPlaybackState() != 1) {
            i2 = 2;
        }
        int currentWindowIndex = getCurrentWindowIndex();
        g a = a(this.G.a(i2), timeline, a(timeline, i, j));
        this.g.a(timeline, i, C.msToUs(j));
        a(a, 0, 1, true, true, 1, a(a), currentWindowIndex);
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekBackIncrement() {
        return this.q;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekForwardIncrement() {
        return this.r;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (playbackParameters == null) {
            playbackParameters = PlaybackParameters.DEFAULT;
        }
        if (!this.G.n.equals(playbackParameters)) {
            g a = this.G.a(playbackParameters);
            this.v++;
            this.g.a(playbackParameters);
            a(a, 0, 1, false, false, 5, C.TIME_UNSET, -1);
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public PlaybackParameters getPlaybackParameters() {
        return this.G.n;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setSeekParameters(@Nullable SeekParameters seekParameters) {
        if (seekParameters == null) {
            seekParameters = SeekParameters.DEFAULT;
        }
        if (!this.A.equals(seekParameters)) {
            this.A = seekParameters;
            this.g.a(seekParameters);
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public SeekParameters getSeekParameters() {
        return this.A;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setForegroundMode(boolean z) {
        if (this.z != z) {
            this.z = z;
            if (!this.g.d(z)) {
                a(false, ExoPlaybackException.createForUnexpected(new ExoTimeoutException(2), 1003));
            }
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void stop(boolean z) {
        a(z, (ExoPlaybackException) null);
    }

    public void a(boolean z, @Nullable ExoPlaybackException exoPlaybackException) {
        g gVar;
        if (z) {
            gVar = a(0, this.k.size()).a((ExoPlaybackException) null);
        } else {
            g gVar2 = this.G;
            gVar = gVar2.a(gVar2.b);
            gVar.q = gVar.s;
            gVar.r = 0L;
        }
        g a = gVar.a(1);
        g a2 = exoPlaybackException != null ? a.a(exoPlaybackException) : a;
        this.v++;
        this.g.b();
        a(a2, 0, 1, false, a2.a.isEmpty() && !this.G.a.isEmpty(), 4, a(a2), -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void release() {
        String hexString = Integer.toHexString(System.identityHashCode(this));
        String str = Util.DEVICE_DEBUG_INFO;
        String registeredModules = ExoPlayerLibraryInfo.registeredModules();
        StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 36 + String.valueOf(str).length() + String.valueOf(registeredModules).length());
        sb.append("Release ");
        sb.append(hexString);
        sb.append(" [");
        sb.append(ExoPlayerLibraryInfo.VERSION_SLASHY);
        sb.append("] [");
        sb.append(str);
        sb.append("] [");
        sb.append(registeredModules);
        sb.append("]");
        Log.i("ExoPlayerImpl", sb.toString());
        if (!this.g.c()) {
            this.h.sendEvent(11, $$Lambda$a$DSGIvZbxzlMhjQMWJHsWAug06C0.INSTANCE);
        }
        this.h.release();
        this.e.removeCallbacksAndMessages(null);
        AnalyticsCollector analyticsCollector = this.n;
        if (analyticsCollector != null) {
            this.p.removeEventListener(analyticsCollector);
        }
        this.G = this.G.a(1);
        g gVar = this.G;
        this.G = gVar.a(gVar.b);
        g gVar2 = this.G;
        gVar2.q = gVar2.s;
        this.G.r = 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(Player.EventListener eventListener) {
        eventListener.onPlayerError(ExoPlaybackException.createForUnexpected(new ExoTimeoutException(1), 1003));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public PlayerMessage createMessage(PlayerMessage.Target target) {
        return new PlayerMessage(this.g, target, this.G.a, getCurrentWindowIndex(), this.s, this.g.d());
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentPeriodIndex() {
        if (this.G.a.isEmpty()) {
            return this.I;
        }
        return this.G.a.getIndexOfPeriod(this.G.b.periodUid);
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentWindowIndex() {
        int b = b();
        if (b == -1) {
            return 0;
        }
        return b;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getDuration() {
        if (!isPlayingAd()) {
            return getContentDuration();
        }
        MediaSource.MediaPeriodId mediaPeriodId = this.G.b;
        this.G.a.getPeriodByUid(mediaPeriodId.periodUid, this.j);
        return C.usToMs(this.j.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup));
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentPosition() {
        return C.usToMs(a(this.G));
    }

    @Override // com.google.android.exoplayer2.Player
    public long getBufferedPosition() {
        if (!isPlayingAd()) {
            return getContentBufferedPosition();
        }
        if (this.G.k.equals(this.G.b)) {
            return C.usToMs(this.G.q);
        }
        return getDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getTotalBufferedDuration() {
        return C.usToMs(this.G.r);
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlayingAd() {
        return this.G.b.isAd();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdGroupIndex() {
        if (isPlayingAd()) {
            return this.G.b.adGroupIndex;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdIndexInAdGroup() {
        if (isPlayingAd()) {
            return this.G.b.adIndexInAdGroup;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentPosition() {
        if (!isPlayingAd()) {
            return getCurrentPosition();
        }
        this.G.a.getPeriodByUid(this.G.b.periodUid, this.j);
        if (this.G.c == C.TIME_UNSET) {
            return this.G.a.getWindow(getCurrentWindowIndex(), this.window).getDefaultPositionMs();
        }
        return this.j.getPositionInWindowMs() + C.usToMs(this.G.c);
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentBufferedPosition() {
        if (this.G.a.isEmpty()) {
            return this.J;
        }
        if (this.G.k.windowSequenceNumber != this.G.b.windowSequenceNumber) {
            return this.G.a.getWindow(getCurrentWindowIndex(), this.window).getDurationMs();
        }
        long j = this.G.q;
        if (this.G.k.isAd()) {
            Timeline.Period periodByUid = this.G.a.getPeriodByUid(this.G.k.periodUid, this.j);
            long adGroupTimeUs = periodByUid.getAdGroupTimeUs(this.G.k.adGroupIndex);
            j = adGroupTimeUs == Long.MIN_VALUE ? periodByUid.durationUs : adGroupTimeUs;
        }
        return C.usToMs(a(this.G.a, this.G.k, j));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererCount() {
        return this.c.length;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererType(int i) {
        return this.c[i].getTrackType();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public TrackSelector getTrackSelector() {
        return this.d;
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackGroupArray getCurrentTrackGroups() {
        return this.G.h;
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionArray getCurrentTrackSelections() {
        return new TrackSelectionArray(this.G.i.selections);
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public List<Metadata> getCurrentStaticMetadata() {
        return this.G.j;
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getMediaMetadata() {
        return this.E;
    }

    public void a(Metadata metadata) {
        MediaMetadata build = this.E.buildUpon().populateFromMetadata(metadata).build();
        if (!build.equals(this.E)) {
            this.E = build;
            this.h.sendEvent(15, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$-Hd1Vtzu-P8Sa-l4SL8MOKunej4
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.this.c((Player.EventListener) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Player.EventListener eventListener) {
        eventListener.onMediaMetadataChanged(this.E);
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getPlaylistMetadata() {
        return this.F;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaylistMetadata(MediaMetadata mediaMetadata) {
        Assertions.checkNotNull(mediaMetadata);
        if (!mediaMetadata.equals(this.F)) {
            this.F = mediaMetadata;
            this.h.sendEvent(16, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$SstYNjkrGUdgnYwxZ_XGqM1ncx0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.this.b((Player.EventListener) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Player.EventListener eventListener) {
        eventListener.onPlaylistMetadataChanged(this.F);
    }

    @Override // com.google.android.exoplayer2.Player
    public Timeline getCurrentTimeline() {
        return this.G.a;
    }

    @Override // com.google.android.exoplayer2.Player
    public AudioAttributes getAudioAttributes() {
        return AudioAttributes.DEFAULT;
    }

    @Override // com.google.android.exoplayer2.Player
    public VideoSize getVideoSize() {
        return VideoSize.UNKNOWN;
    }

    /* renamed from: a */
    public ImmutableList<Cue> getCurrentCues() {
        return ImmutableList.of();
    }

    @Override // com.google.android.exoplayer2.Player
    public DeviceInfo getDeviceInfo() {
        return DeviceInfo.UNKNOWN;
    }

    private int b() {
        if (this.G.a.isEmpty()) {
            return this.H;
        }
        return this.G.a.getPeriodByUid(this.G.b.periodUid, this.j).windowIndex;
    }

    private long a(g gVar) {
        if (gVar.a.isEmpty()) {
            return C.msToUs(this.J);
        }
        if (gVar.b.isAd()) {
            return gVar.s;
        }
        return a(gVar.a, gVar.b, gVar.s);
    }

    private List<MediaSource> a(List<MediaItem> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(this.m.createMediaSource(list.get(i)));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void c(ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
        long j;
        boolean z;
        this.v -= playbackInfoUpdate.operationAcks;
        boolean z2 = true;
        if (playbackInfoUpdate.positionDiscontinuity) {
            this.w = playbackInfoUpdate.discontinuityReason;
            this.x = true;
        }
        if (playbackInfoUpdate.hasPlayWhenReadyChangeReason) {
            this.y = playbackInfoUpdate.playWhenReadyChangeReason;
        }
        if (this.v == 0) {
            Timeline timeline = playbackInfoUpdate.playbackInfo.a;
            if (!this.G.a.isEmpty() && timeline.isEmpty()) {
                this.H = -1;
                this.J = 0L;
                this.I = 0;
            }
            if (!timeline.isEmpty()) {
                List<Timeline> a = ((h) timeline).a();
                Assertions.checkState(a.size() == this.k.size());
                for (int i = 0; i < a.size(); i++) {
                    this.k.get(i).b = a.get(i);
                }
            }
            if (this.x) {
                if (playbackInfoUpdate.playbackInfo.b.equals(this.G.b) && playbackInfoUpdate.playbackInfo.d == this.G.s) {
                    z2 = false;
                }
                if (z2) {
                    if (timeline.isEmpty() || playbackInfoUpdate.playbackInfo.b.isAd()) {
                        j = playbackInfoUpdate.playbackInfo.d;
                    } else {
                        j = a(timeline, playbackInfoUpdate.playbackInfo.b, playbackInfoUpdate.playbackInfo.d);
                    }
                    z = z2;
                } else {
                    j = -9223372036854775807L;
                    z = z2;
                }
            } else {
                j = -9223372036854775807L;
                z = false;
            }
            this.x = false;
            a(playbackInfoUpdate.playbackInfo, 1, this.y, false, z, this.w, j, -1);
        }
    }

    private void a(final g gVar, final int i, final int i2, boolean z, boolean z2, final int i3, long j, int i4) {
        g gVar2 = this.G;
        this.G = gVar;
        Pair<Boolean, Integer> a = a(gVar, gVar2, z2, i3, !gVar2.a.equals(gVar.a));
        boolean booleanValue = ((Boolean) a.first).booleanValue();
        final int intValue = ((Integer) a.second).intValue();
        MediaMetadata mediaMetadata = this.E;
        final MediaItem mediaItem = null;
        if (booleanValue) {
            if (!gVar.a.isEmpty()) {
                mediaItem = gVar.a.getWindow(gVar.a.getPeriodByUid(gVar.b.periodUid, this.j).windowIndex, this.window).mediaItem;
            }
            mediaMetadata = mediaItem != null ? mediaItem.mediaMetadata : MediaMetadata.EMPTY;
        }
        if (!gVar2.j.equals(gVar.j)) {
            mediaMetadata = mediaMetadata.buildUpon().populateFromMetadata(gVar.j).build();
        }
        boolean z3 = !mediaMetadata.equals(this.E);
        this.E = mediaMetadata;
        if (!gVar2.a.equals(gVar.a)) {
            this.h.queueEvent(0, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$q0sgx01LTM0kH56OG6KksWxPYJw
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.b(g.this, i, (Player.EventListener) obj);
                }
            });
        }
        if (z2) {
            final Player.PositionInfo a2 = a(i3, gVar2, i4);
            final Player.PositionInfo b = b(j);
            this.h.queueEvent(12, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$YaaQvtUTdBeZv6011z8zzND6ACU
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.a(i3, a2, b, (Player.EventListener) obj);
                }
            });
        }
        if (booleanValue) {
            this.h.queueEvent(1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$y-_uEhsvwf1q2Eo6zvtq7vEARZ0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onMediaItemTransition(MediaItem.this, intValue);
                }
            });
        }
        if (gVar2.f != gVar.f) {
            this.h.queueEvent(11, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$Ra3pCpQBTCABlgp-rI6s95bMDA4
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.i(g.this, (Player.EventListener) obj);
                }
            });
            if (gVar.f != null) {
                this.h.queueEvent(11, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$OWcUb0h1W229Sa1ovGu8z8DgL00
                    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                    public final void invoke(Object obj) {
                        a.h(g.this, (Player.EventListener) obj);
                    }
                });
            }
        }
        if (gVar2.i != gVar.i) {
            this.d.onSelectionActivated(gVar.i.info);
            final TrackSelectionArray trackSelectionArray = new TrackSelectionArray(gVar.i.selections);
            this.h.queueEvent(2, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$Rb6PYgoZ2BBXdUKCrMgbcX9B6go
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.a(g.this, trackSelectionArray, (Player.EventListener) obj);
                }
            });
        }
        if (!gVar2.j.equals(gVar.j)) {
            this.h.queueEvent(3, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$zqEZ59ul4yjiEM1fIQ6TTpU0onE
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.g(g.this, (Player.EventListener) obj);
                }
            });
        }
        if (z3) {
            final MediaMetadata mediaMetadata2 = this.E;
            this.h.queueEvent(15, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$KyN-WKdW6PLKibIjarc1YEf39dA
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onMediaMetadataChanged(MediaMetadata.this);
                }
            });
        }
        if (gVar2.g != gVar.g) {
            this.h.queueEvent(4, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$oChHm4Mms4iniGlmOn9BB0m0dH8
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.f(g.this, (Player.EventListener) obj);
                }
            });
        }
        if (!(gVar2.e == gVar.e && gVar2.l == gVar.l)) {
            this.h.queueEvent(-1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$phHKhhQefhFy_yp01IdgnZ-X7xw
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.e(g.this, (Player.EventListener) obj);
                }
            });
        }
        if (gVar2.e != gVar.e) {
            this.h.queueEvent(5, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$U39xe-ck-53WnuYAbaIwUkhBALg
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.d(g.this, (Player.EventListener) obj);
                }
            });
        }
        if (gVar2.l != gVar.l) {
            this.h.queueEvent(6, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$LxetP5l4jY8CTke6iB65RUgtv24
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.a(g.this, i2, (Player.EventListener) obj);
                }
            });
        }
        if (gVar2.m != gVar.m) {
            this.h.queueEvent(7, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$diyvxvMK55xKcu6Ryf0QsaE-ht0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.c(g.this, (Player.EventListener) obj);
                }
            });
        }
        if (c(gVar2) != c(gVar)) {
            this.h.queueEvent(8, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$zfNtohpzdFIFYxN__tYHti53nis
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.b(g.this, (Player.EventListener) obj);
                }
            });
        }
        if (!gVar2.n.equals(gVar.n)) {
            this.h.queueEvent(13, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$7beDvH3iekJd-ef2AaM6kjt50o8
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.a(g.this, (Player.EventListener) obj);
                }
            });
        }
        if (z) {
            this.h.queueEvent(-1, $$Lambda$IvHsGgCxoxdxsyZUtt2N2KRx2jA.INSTANCE);
        }
        c();
        this.h.flushEvents();
        if (gVar2.o != gVar.o) {
            Iterator<ExoPlayer.AudioOffloadListener> it = this.i.iterator();
            while (it.hasNext()) {
                it.next().onExperimentalOffloadSchedulingEnabledChanged(gVar.o);
            }
        }
        if (gVar2.p != gVar.p) {
            Iterator<ExoPlayer.AudioOffloadListener> it2 = this.i.iterator();
            while (it2.hasNext()) {
                it2.next().onExperimentalSleepingForOffloadChanged(gVar.p);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(g gVar, int i, Player.EventListener eventListener) {
        eventListener.onTimelineChanged(gVar.a, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(int i, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, Player.EventListener eventListener) {
        eventListener.onPositionDiscontinuity(i);
        eventListener.onPositionDiscontinuity(positionInfo, positionInfo2, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void i(g gVar, Player.EventListener eventListener) {
        eventListener.onPlayerErrorChanged(gVar.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void h(g gVar, Player.EventListener eventListener) {
        eventListener.onPlayerError(gVar.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(g gVar, TrackSelectionArray trackSelectionArray, Player.EventListener eventListener) {
        eventListener.onTracksChanged(gVar.h, trackSelectionArray);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void g(g gVar, Player.EventListener eventListener) {
        eventListener.onStaticMetadataChanged(gVar.j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void f(g gVar, Player.EventListener eventListener) {
        eventListener.onLoadingChanged(gVar.g);
        eventListener.onIsLoadingChanged(gVar.g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(g gVar, Player.EventListener eventListener) {
        eventListener.onPlayerStateChanged(gVar.l, gVar.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(g gVar, Player.EventListener eventListener) {
        eventListener.onPlaybackStateChanged(gVar.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(g gVar, int i, Player.EventListener eventListener) {
        eventListener.onPlayWhenReadyChanged(gVar.l, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(g gVar, Player.EventListener eventListener) {
        eventListener.onPlaybackSuppressionReasonChanged(gVar.m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(g gVar, Player.EventListener eventListener) {
        eventListener.onIsPlayingChanged(c(gVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(g gVar, Player.EventListener eventListener) {
        eventListener.onPlaybackParametersChanged(gVar.n);
    }

    private Player.PositionInfo a(int i, g gVar, int i2) {
        int i3;
        Object obj;
        int i4;
        long j;
        long j2;
        Timeline.Period period = new Timeline.Period();
        Object obj2 = null;
        if (!gVar.a.isEmpty()) {
            Object obj3 = gVar.b.periodUid;
            gVar.a.getPeriodByUid(obj3, period);
            int i5 = period.windowIndex;
            i3 = gVar.a.getIndexOfPeriod(obj3);
            obj2 = gVar.a.getWindow(i5, this.window).uid;
            obj = obj3;
            i4 = i5;
        } else {
            i4 = i2;
            i3 = -1;
            obj = null;
        }
        if (i == 0) {
            j = period.positionInWindowUs + period.durationUs;
            if (gVar.b.isAd()) {
                j = period.getAdDurationUs(gVar.b.adGroupIndex, gVar.b.adIndexInAdGroup);
                j2 = b(gVar);
            } else if (gVar.b.nextAdGroupIndex == -1 || !this.G.b.isAd()) {
                j2 = j;
            } else {
                j = b(this.G);
                j2 = j;
            }
        } else if (gVar.b.isAd()) {
            j = gVar.s;
            j2 = b(gVar);
        } else {
            j = period.positionInWindowUs + gVar.s;
            j2 = j;
        }
        return new Player.PositionInfo(obj2, i4, obj, i3, C.usToMs(j), C.usToMs(j2), gVar.b.adGroupIndex, gVar.b.adIndexInAdGroup);
    }

    private Player.PositionInfo b(long j) {
        int i;
        Object obj;
        int currentWindowIndex = getCurrentWindowIndex();
        Object obj2 = null;
        if (!this.G.a.isEmpty()) {
            Object obj3 = this.G.b.periodUid;
            this.G.a.getPeriodByUid(obj3, this.j);
            i = this.G.a.getIndexOfPeriod(obj3);
            obj2 = this.G.a.getWindow(currentWindowIndex, this.window).uid;
            obj = obj3;
        } else {
            i = -1;
            obj = null;
        }
        long usToMs = C.usToMs(j);
        return new Player.PositionInfo(obj2, currentWindowIndex, obj, i, usToMs, this.G.b.isAd() ? C.usToMs(b(this.G)) : usToMs, this.G.b.adGroupIndex, this.G.b.adIndexInAdGroup);
    }

    private static long b(g gVar) {
        Timeline.Window window = new Timeline.Window();
        Timeline.Period period = new Timeline.Period();
        gVar.a.getPeriodByUid(gVar.b.periodUid, period);
        if (gVar.c == C.TIME_UNSET) {
            return gVar.a.getWindow(period.windowIndex, window).getDefaultPositionUs();
        }
        return period.getPositionInWindowUs() + gVar.c;
    }

    private Pair<Boolean, Integer> a(g gVar, g gVar2, boolean z, int i, boolean z2) {
        Timeline timeline = gVar2.a;
        Timeline timeline2 = gVar.a;
        if (timeline2.isEmpty() && timeline.isEmpty()) {
            return new Pair<>(false, -1);
        }
        int i2 = 3;
        if (timeline2.isEmpty() != timeline.isEmpty()) {
            return new Pair<>(true, 3);
        }
        if (!timeline.getWindow(timeline.getPeriodByUid(gVar2.b.periodUid, this.j).windowIndex, this.window).uid.equals(timeline2.getWindow(timeline2.getPeriodByUid(gVar.b.periodUid, this.j).windowIndex, this.window).uid)) {
            if (z && i == 0) {
                i2 = 1;
            } else if (z && i == 1) {
                i2 = 2;
            } else if (!z2) {
                throw new IllegalStateException();
            }
            return new Pair<>(true, Integer.valueOf(i2));
        } else if (!z || i != 0 || gVar2.b.windowSequenceNumber >= gVar.b.windowSequenceNumber) {
            return new Pair<>(false, -1);
        } else {
            return new Pair<>(true, 0);
        }
    }

    private void c() {
        Player.Commands commands = this.D;
        this.D = getAvailableCommands(this.b);
        if (!this.D.equals(commands)) {
            this.h.queueEvent(14, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.-$$Lambda$a$lhltmUVyjkUQ6ADPE22zUDLZEpU
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    a.this.a((Player.EventListener) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Player.EventListener eventListener) {
        eventListener.onAvailableCommandsChanged(this.D);
    }

    private void a(List<MediaSource> list, int i, long j, boolean z) {
        List<MediaSource> list2;
        int i2;
        long j2;
        int b = b();
        long currentPosition = getCurrentPosition();
        boolean z2 = true;
        this.v++;
        if (!this.k.isEmpty()) {
            b(0, this.k.size());
            list2 = list;
        } else {
            list2 = list;
        }
        List<MediaSourceList.c> a = a(0, list2);
        Timeline d = d();
        if (d.isEmpty() || i < d.getWindowCount()) {
            if (z) {
                i2 = d.getFirstWindowIndex(this.u);
                j2 = C.TIME_UNSET;
            } else if (i == -1) {
                i2 = b;
                j2 = currentPosition;
            } else {
                i2 = i;
                j2 = j;
            }
            g a2 = a(this.G, d, a(d, i2, j2));
            int i3 = a2.e;
            if (!(i2 == -1 || a2.e == 1)) {
                i3 = (d.isEmpty() || i2 >= d.getWindowCount()) ? 4 : 2;
            }
            g a3 = a2.a(i3);
            this.g.a(a, i2, C.msToUs(j2), this.B);
            if (this.G.b.periodUid.equals(a3.b.periodUid) || this.G.a.isEmpty()) {
                z2 = false;
            }
            a(a3, 0, 1, false, z2, 4, a(a3), -1);
            return;
        }
        throw new IllegalSeekPositionException(d, i, j);
    }

    private List<MediaSourceList.c> a(int i, List<MediaSource> list) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            MediaSourceList.c cVar = new MediaSourceList.c(list.get(i2), this.l);
            arrayList.add(cVar);
            this.k.add(i2 + i, new C0056a(cVar.b, cVar.a.getTimeline()));
        }
        this.B = this.B.cloneAndInsert(i, arrayList.size());
        return arrayList;
    }

    private g a(int i, int i2) {
        boolean z = false;
        Assertions.checkArgument(i >= 0 && i2 >= i && i2 <= this.k.size());
        int currentWindowIndex = getCurrentWindowIndex();
        Timeline currentTimeline = getCurrentTimeline();
        int size = this.k.size();
        this.v++;
        b(i, i2);
        Timeline d = d();
        g a = a(this.G, d, a(currentTimeline, d));
        if (a.e != 1 && a.e != 4 && i < i2 && i2 == size && currentWindowIndex >= a.a.getWindowCount()) {
            z = true;
        }
        if (z) {
            a = a.a(4);
        }
        this.g.a(i, i2, this.B);
        return a;
    }

    private void b(int i, int i2) {
        for (int i3 = i2 - 1; i3 >= i; i3--) {
            this.k.remove(i3);
        }
        this.B = this.B.cloneAndRemove(i, i2);
    }

    private Timeline d() {
        return new h(this.k, this.B);
    }

    private g a(g gVar, Timeline timeline, @Nullable Pair<Object, Long> pair) {
        TrackSelectorResult trackSelectorResult;
        MediaSource.MediaPeriodId mediaPeriodId;
        int i;
        long j;
        Assertions.checkArgument(timeline.isEmpty() || pair != null);
        Timeline timeline2 = gVar.a;
        g a = gVar.a(timeline);
        if (timeline.isEmpty()) {
            MediaSource.MediaPeriodId a2 = g.a();
            long msToUs = C.msToUs(this.J);
            g a3 = a.a(a2, msToUs, msToUs, msToUs, 0L, TrackGroupArray.EMPTY, this.a, ImmutableList.of()).a(a2);
            a3.q = a3.s;
            return a3;
        }
        Object obj = a.b.periodUid;
        boolean z = !obj.equals(((Pair) Util.castNonNull(pair)).first);
        MediaSource.MediaPeriodId mediaPeriodId2 = z ? new MediaSource.MediaPeriodId(pair.first) : a.b;
        long longValue = ((Long) pair.second).longValue();
        long msToUs2 = C.msToUs(getContentPosition());
        if (!timeline2.isEmpty()) {
            msToUs2 -= timeline2.getPeriodByUid(obj, this.j).getPositionInWindowUs();
        }
        if (z || longValue < msToUs2) {
            Assertions.checkState(!mediaPeriodId2.isAd());
            TrackGroupArray trackGroupArray = z ? TrackGroupArray.EMPTY : a.h;
            if (z) {
                mediaPeriodId = mediaPeriodId2;
                trackSelectorResult = this.a;
            } else {
                mediaPeriodId = mediaPeriodId2;
                trackSelectorResult = a.i;
            }
            g a4 = a.a(mediaPeriodId, longValue, longValue, longValue, 0L, trackGroupArray, trackSelectorResult, z ? ImmutableList.of() : a.j).a(mediaPeriodId);
            a4.q = longValue;
            return a4;
        } else if (i == 0) {
            int indexOfPeriod = timeline.getIndexOfPeriod(a.k.periodUid);
            if (indexOfPeriod == -1 || timeline.getPeriod(indexOfPeriod, this.j).windowIndex != timeline.getPeriodByUid(mediaPeriodId2.periodUid, this.j).windowIndex) {
                timeline.getPeriodByUid(mediaPeriodId2.periodUid, this.j);
                if (mediaPeriodId2.isAd()) {
                    j = this.j.getAdDurationUs(mediaPeriodId2.adGroupIndex, mediaPeriodId2.adIndexInAdGroup);
                } else {
                    j = this.j.durationUs;
                }
                a = a.a(mediaPeriodId2, a.s, a.s, a.d, j - a.s, a.h, a.i, a.j).a(mediaPeriodId2);
                a.q = j;
            }
            return a;
        } else {
            Assertions.checkState(!mediaPeriodId2.isAd());
            long max = Math.max(0L, a.r - (longValue - msToUs2));
            long j2 = a.q;
            if (a.k.equals(a.b)) {
                j2 = longValue + max;
            }
            g a5 = a.a(mediaPeriodId2, longValue, longValue, longValue, max, a.h, a.i, a.j);
            a5.q = j2;
            return a5;
        }
    }

    @Nullable
    private Pair<Object, Long> a(Timeline timeline, Timeline timeline2) {
        long contentPosition = getContentPosition();
        int i = -1;
        if (timeline.isEmpty() || timeline2.isEmpty()) {
            boolean z = !timeline.isEmpty() && timeline2.isEmpty();
            if (!z) {
                i = b();
            }
            if (z) {
                contentPosition = -9223372036854775807L;
            }
            return a(timeline2, i, contentPosition);
        }
        Pair<Object, Long> periodPosition = timeline.getPeriodPosition(this.window, this.j, getCurrentWindowIndex(), C.msToUs(contentPosition));
        Object obj = ((Pair) Util.castNonNull(periodPosition)).first;
        if (timeline2.getIndexOfPeriod(obj) != -1) {
            return periodPosition;
        }
        Object a = ExoPlayerImplInternal.a(this.window, this.j, this.t, this.u, obj, timeline, timeline2);
        if (a == null) {
            return a(timeline2, -1, C.TIME_UNSET);
        }
        timeline2.getPeriodByUid(a, this.j);
        return a(timeline2, this.j.windowIndex, timeline2.getWindow(this.j.windowIndex, this.window).getDefaultPositionMs());
    }

    @Nullable
    private Pair<Object, Long> a(Timeline timeline, int i, long j) {
        int i2;
        if (timeline.isEmpty()) {
            this.H = i;
            if (j == C.TIME_UNSET) {
                j = 0;
            }
            this.J = j;
            this.I = 0;
            return null;
        }
        if (i == -1 || i >= timeline.getWindowCount()) {
            int firstWindowIndex = timeline.getFirstWindowIndex(this.u);
            j = timeline.getWindow(firstWindowIndex, this.window).getDefaultPositionMs();
            i2 = firstWindowIndex;
        } else {
            i2 = i;
        }
        return timeline.getPeriodPosition(this.window, this.j, i2, C.msToUs(j));
    }

    private long a(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, long j) {
        timeline.getPeriodByUid(mediaPeriodId.periodUid, this.j);
        return j + this.j.getPositionInWindowUs();
    }

    private static boolean c(g gVar) {
        return gVar.e == 3 && gVar.l && gVar.m == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ExoPlayerImpl.java */
    /* renamed from: com.google.android.exoplayer2.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public static final class C0056a implements f {
        private final Object a;
        private Timeline b;

        public C0056a(Object obj, Timeline timeline) {
            this.a = obj;
            this.b = timeline;
        }

        @Override // com.google.android.exoplayer2.f
        public Object a() {
            return this.a;
        }

        @Override // com.google.android.exoplayer2.f
        public Timeline b() {
            return this.b;
        }
    }
}
