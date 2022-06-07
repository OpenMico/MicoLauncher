package com.xiaomi.micolauncher.common.player.base;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.Util;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.base.exo.extractor.MicoExtractorsFactory;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import java.io.File;

/* loaded from: classes3.dex */
public class BaseExoPlayer extends Player implements Player.Listener {
    public static final float FADE_VOLUME_MAX = 1.0f;
    private volatile PlayerListener a;
    private SimpleExoPlayer b;
    private MediaSourceFactory c;
    private DataSource.Factory d;
    private DefaultHlsExtractorFactory e;
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private long j;
    private volatile Handler k;
    private float l;
    private float m;
    private float n;
    private BasePlayer.StreamType o;
    private final boolean p;
    private volatile boolean q;

    /* loaded from: classes3.dex */
    public interface PlayerListener {
        void onComplete(Player player);

        void onError(Player player, int i, Exception exc);

        void onPrepared(Player player);
    }

    public void onBackground() {
    }

    public void onForeground() {
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onIsLoadingChanged(boolean z) {
    }

    protected void onPaused() {
    }

    public void onPlaybackControl(PlaybackControllerInfo playbackControllerInfo) {
    }

    protected void onResume() {
    }

    protected void onStarted() {
    }

    protected void onStopped() {
    }

    protected void onSuspend() {
    }

    public BaseExoPlayer(AudioSource audioSource) {
        this(MicoApplication.getGlobalContext(), audioSource, BasePlayer.StreamType.STREAM_TYPE_MUSIC, true);
    }

    public BaseExoPlayer(Context context, AudioSource audioSource, BasePlayer.StreamType streamType, boolean z) {
        super(audioSource);
        if (this.b == null) {
            ExoPlayerUtil.getInstance();
            this.d = ExoPlayerUtil.createDataSourceFactory(context, Util.getUserAgent(context, "MicoApplication"), null);
            MicoExtractorsFactory mp3ExtractorFlags = new MicoExtractorsFactory().setConstantBitrateSeekingEnabled(true).setMp3ExtractorFlags(2);
            this.c = ExoPlayerUtil.getInstance().getMediaSourceFactory(this.d, mp3ExtractorFlags);
            this.b = new SimpleExoPlayer.Builder(context, mp3ExtractorFlags).setMediaSourceFactory(this.c).setTrackSelector(new DefaultTrackSelector(context)).setLooper(Looper.getMainLooper()).build();
            this.b.setThrowsWhenUsingWrongThread(true);
            this.b.addListener((Player.Listener) this);
            setStreamType(streamType);
        }
        this.g = false;
        this.h = false;
        this.i = false;
        this.l = 1.0f;
        this.m = 1.0f;
        this.p = z;
        L.player.i("BaseExoPlayer source=%s,streamType=%s,create=%s", audioSource, streamType, this);
    }

    public void setStreamType(BasePlayer.StreamType streamType) {
        this.o = streamType;
        final AudioAttributes.Builder builder = new AudioAttributes.Builder();
        if (BasePlayer.StreamType.STREAM_TYPE_COMMUNICATION == streamType) {
            builder.setContentType(1);
            builder.setUsage(2);
        } else if (BasePlayer.StreamType.STREAM_TYPE_ALARM == streamType) {
            builder.setContentType(4);
            builder.setUsage(4);
        } else if (BasePlayer.StreamType.STREAM_TYPE_RINGTONE == streamType) {
            builder.setContentType(4);
            builder.setUsage(6);
        } else {
            builder.setContentType(2);
            builder.setUsage(1);
        }
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$4Z21S4hYH4Y9VNFECOGZ6PsimU8
            @Override // java.lang.Runnable
            public final void run() {
                BaseExoPlayer.this.a(builder);
            }
        });
    }

    public /* synthetic */ void a(AudioAttributes.Builder builder) {
        this.b.setAudioAttributes(builder.build(), false);
    }

    public void setDataSource(String str) {
        if (TextUtils.isEmpty(str)) {
            L.player.d("%s setDataSource.url=null", "[BaseExoPlayer]:");
            a(-1004, ExoPlaybackException.createForRemote("Url is empty"));
            return;
        }
        e();
        try {
            this.b.stop();
            this.b.setMediaSource(a(str));
            prepareAsync();
        } catch (Exception e) {
            L.player.e("%s setDataSource exception=%s", "[BaseExoPlayer]:", e);
            a(1, e);
        }
    }

    private MediaSource a(String str) {
        int inferContentType = Util.inferContentType(Uri.parse(str));
        if (2 == inferContentType) {
            if (this.e == null) {
                this.e = new DefaultHlsExtractorFactory(4, true);
            }
            return new HlsMediaSource.Factory(this.d).setAllowChunklessPreparation(true).setExtractorFactory(this.e).createMediaSource(MediaItem.fromUri(str));
        } else if (4 == inferContentType) {
            return new ProgressiveMediaSource.Factory(this.d).createMediaSource(MediaItem.fromUri(str));
        } else {
            return this.c.createMediaSource(MediaItem.fromUri(str));
        }
    }

    public void setDataSource(File file) {
        if (!file.exists()) {
            L.player.d("%s setDataSource.file not exists", "[BaseExoPlayer]:");
            a(-1004, ExoPlaybackException.createForRemote("file not exists"));
            return;
        }
        e();
        try {
            this.b.stop();
            this.b.setMediaSource(this.c.createMediaSource(MediaItem.fromUri(Uri.fromFile(file))));
            prepareAsync();
        } catch (Exception e) {
            L.player.e("%s setDataSource exception=%s", "[BaseExoPlayer]:", e);
            a(1, e);
        }
    }

    public boolean setDataSource(int i, Context context) {
        if (i < 0 || context == null) {
            a(-1004, ExoPlaybackException.createForRemote("play RawRes, but rawId < 0"));
            return false;
        }
        try {
            this.b.stop();
            this.b.setMediaSource(this.c.createMediaSource(MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(i))));
            prepareAsync();
            return true;
        } catch (Exception e) {
            a(1, e);
            L.player.e("%s setDataSource(RawRes) exception: %s", "[BaseExoPlayer]:", e);
            return false;
        }
    }

    public void releaseFocus() {
        super.a(0L);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public void release() {
        super.release();
        L.player.i("BaseExoPlayer release=%s", this);
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$NkdVx1k1WRTKf8H95Y3A3GRSJ7Y
            @Override // java.lang.Runnable
            public final void run() {
                BaseExoPlayer.this.p();
            }
        });
    }

    public /* synthetic */ void p() {
        try {
            this.b.stop();
            this.b.release();
            L.player.i("exoPlayer release=%s", this.b);
        } catch (Exception e) {
            L.player.e("%s release exception: %s", "[BaseExoPlayer]:", e);
        }
        e();
        this.m = 1.0f;
        this.l = 1.0f;
        if (this.k != null) {
            this.k.post(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.1
                @Override // java.lang.Runnable
                public void run() {
                    BaseExoPlayer.this.k.removeCallbacksAndMessages(null);
                }
            });
        }
    }

    public void prepareAsync() {
        L.player.d("%s prepareAsync ", "[BaseExoPlayer]:");
        try {
            this.h = false;
            this.f = false;
            this.g = false;
            try {
                this.b.prepare();
            } catch (ArrayIndexOutOfBoundsException e) {
                L.player.e("exoPlayer prepare exception", e);
                a(1, e);
            }
            d();
        } catch (Exception e2) {
            L.player.e("%s prepareAsync exception: %s", "[BaseExoPlayer]:", e2);
        }
        a();
    }

    public void setListener(PlayerListener playerListener) {
        this.a = playerListener;
    }

    public void setVolume(float f) {
        L.player.d("this=%s, setVolume.volume=%f", Integer.valueOf(hashCode()), Float.valueOf(f));
        this.m = f;
        this.l = f;
        a(f);
    }

    private void a(final float f) {
        try {
            L.player.d("this=%s, doSetVolume.volume=%f", Integer.valueOf(hashCode()), Float.valueOf(f));
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$LqlRrvhrKpW23JfZyxCOvT7hxhI
                @Override // java.lang.Runnable
                public final void run() {
                    BaseExoPlayer.this.b(f);
                }
            });
        } catch (Exception e) {
            L.player.e("%s setVolume exception: %s", "[BaseExoPlayer]:", e);
        }
    }

    public /* synthetic */ void b(float f) {
        this.b.setVolume(f);
    }

    public void setLooping(final boolean z) {
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$J1mHe4NZdtMQEjjH1AsQlM7PKKo
            @Override // java.lang.Runnable
            public final void run() {
                BaseExoPlayer.this.a(z);
            }
        });
    }

    public /* synthetic */ void a(boolean z) {
        try {
            if (z) {
                this.b.setRepeatMode(2);
            } else {
                this.b.setRepeatMode(0);
            }
        } catch (Exception e) {
            L.player.e("%s setLooping exception: %s", "[BaseExoPlayer]:", e);
        }
        this.i = z;
    }

    public int getDuration() {
        try {
            return (int) this.b.getDuration();
        } catch (Exception e) {
            L.player.e("%s getDuration exception: %s", "[BaseExoPlayer]:", e);
            return -1;
        }
    }

    public boolean isPrepared() {
        return this.f;
    }

    public int getCurrentPosition() {
        try {
            return (int) this.b.getCurrentPosition();
        } catch (Exception e) {
            L.player.e("%s getCurrentPosition exception: %s", "[BaseExoPlayer]:", e);
            return -1;
        }
    }

    public boolean isPlaying() {
        if (this.b == null) {
            return false;
        }
        return this.q;
    }

    public boolean isManualPause() {
        return this.g;
    }

    public boolean isAutoPause() {
        return this.h;
    }

    public void setPrepareTimeOut(long j) {
        this.j = j;
        c();
    }

    public void seekTo(final int i) {
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$7CpweGKx7r1MTGOZ61Zbg-d6eTs
            @Override // java.lang.Runnable
            public final void run() {
                BaseExoPlayer.this.a(i);
            }
        });
    }

    public /* synthetic */ void a(int i) {
        try {
            L.player.d("%s seekTo:%d", "[BaseExoPlayer]:", Integer.valueOf(i));
            this.b.seekTo(i);
            start();
        } catch (Exception e) {
            L.player.e("%s seekTo exception: %s", "[BaseExoPlayer]:", e);
        }
    }

    public void reset() {
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$Op4e1fE5KiXKr09wY9GrjMTvhjg
            @Override // java.lang.Runnable
            public final void run() {
                BaseExoPlayer.this.o();
            }
        });
    }

    public /* synthetic */ void o() {
        L.player.w("%s reset manualPause=%b prepared=%b", "[BaseExoPlayer]:", Boolean.valueOf(this.g), Boolean.valueOf(this.f));
        this.f = false;
        this.g = false;
        this.h = false;
        a(0L);
        try {
            this.b.stop();
        } catch (Exception e) {
            L.player.e("%s reset.exception=%s", "[BaseExoPlayer]:", e);
        }
        e();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public final void start() {
        L.player.d("%s start manualPause=%b prepared=%b", "[BaseExoPlayer]:", Boolean.valueOf(this.g), Boolean.valueOf(this.f));
        this.g = false;
        if (this.f) {
            super.a();
            return;
        }
        L.player.d("%s start error, no prepared!!", "[BaseExoPlayer]:");
        a(-1004, ExoPlaybackException.createForRemote("start error, no prepared!!"));
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public void restart() {
        try {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$2i0pnRXI6vqqaYBimE5eRgeR0Ko
                @Override // java.lang.Runnable
                public final void run() {
                    BaseExoPlayer.this.n();
                }
            });
        } catch (Exception e) {
            L.player.e("%s restart seekTo 0 exception: %s", "[BaseExoPlayer]:", e);
        }
    }

    public /* synthetic */ void n() {
        L.player.d("%s restart", "[BaseExoPlayer]:");
        this.b.seekTo(0L);
        this.f = true;
        start();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public final void stop() {
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$886cb1o6ScktDIm1G8tpjCgkK48
            @Override // java.lang.Runnable
            public final void run() {
                BaseExoPlayer.this.m();
            }
        });
    }

    public /* synthetic */ void m() {
        L.player.i("%s stop, isPlaying: %b, exoPlayer: %s", "[BaseExoPlayer]:", Boolean.valueOf(isPlaying()), this.b);
        a(0L);
        try {
            if (isPlaying() && this.b != null) {
                this.b.stop();
                this.g = true;
            }
        } catch (Exception e) {
            L.player.e("%s stop exception: %s", "[BaseExoPlayer]:", e);
        }
        L.player.d("%s stop manualPause=%b prepared=%b", "[BaseExoPlayer]:", Boolean.valueOf(this.g), Boolean.valueOf(this.f));
        e();
        this.l = this.m;
    }

    public final void pause() {
        this.g = true;
        super.b();
        try {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$i7kazPXAR9xZ3ExFlMacBhuqKSw
                @Override // java.lang.Runnable
                public final void run() {
                    BaseExoPlayer.this.l();
                }
            });
        } catch (Exception e) {
            L.player.e("%s pause exception: %s", "[BaseExoPlayer]:", e);
        }
        L.player.d("%s pause manualPause=%b prepared=%b", "[BaseExoPlayer]:", Boolean.valueOf(this.g), Boolean.valueOf(this.f));
        e();
        this.l = this.m;
    }

    public /* synthetic */ void l() {
        if (isPlaying()) {
            this.b.setPlayWhenReady(false);
        }
    }

    public final void fadeIn(float f, float f2, long j) {
        L.player.d("fadeIn:from=%f, target=%f,duration=%d", Float.valueOf(f), Float.valueOf(f2), Long.valueOf(j));
        if (f < f2) {
            if (f < 0.1f) {
                f = 0.1f;
            }
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            if (j < 200) {
                j = 200;
            }
            this.l = f;
            this.m = f2;
            a(this.l);
            this.n = (f2 - f) / ((float) (j / 200));
            c();
            if (this.k != null) {
                this.k.sendEmptyMessageDelayed(2, 200L);
            }
        }
    }

    public final void fadeOut(float f, long j) {
        float f2 = this.l;
        if (f2 <= f) {
            this.m = f2;
            return;
        }
        if (f > 1.0f) {
            f = 1.0f;
        } else if (f < 0.1f) {
            f = 0.1f;
        }
        if (j < 200) {
            j = 200;
        }
        this.m = f;
        this.n = (this.l - f) / ((float) (j / 200));
        c();
        if (this.k != null) {
            this.k.sendEmptyMessageDelayed(2, 200L);
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public final void setIgnoreForceStop(AudioSource audioSource) {
        L.player.i("%s setIgnoreForceStop.source=%s", "[BaseExoPlayer]:", audioSource);
        super.setIgnoreForceStop(audioSource);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    final void postStart() {
        L.player.d("%s postStart manualPause=%b, isPrepared=%b", "[BaseExoPlayer]:", Boolean.valueOf(this.g), Boolean.valueOf(isPrepared()));
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$C-NKLD6bUKg7q7hDbHG41991u4E
            @Override // java.lang.Runnable
            public final void run() {
                BaseExoPlayer.this.k();
            }
        });
    }

    public /* synthetic */ void k() {
        if (this.g || !isPrepared()) {
            L.player.w("%s postStart not prepared", "[BaseExoPlayer]:");
            return;
        }
        try {
            this.b.setPlayWhenReady(true);
            onStarted();
        } catch (Exception e) {
            L.player.e("%s postStart exception: %s", "[BaseExoPlayer]:", e);
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    protected void postStop(AudioSource audioSource) {
        L.player.d("%s postStop ", "[BaseExoPlayer]:");
        try {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$5VQqMV5uCQv9lEqKFI8WpOI26Wk
                @Override // java.lang.Runnable
                public final void run() {
                    BaseExoPlayer.this.j();
                }
            });
        } catch (Exception e) {
            L.player.e("%s postStop exception: %s", "[BaseExoPlayer]:", e);
        }
        L.player.d("%s postStop manualPause=%b", "[BaseExoPlayer]:", Boolean.valueOf(this.g));
        e();
        this.l = this.m;
        setVolume(this.l);
    }

    public /* synthetic */ void j() {
        if (isPlaying()) {
            this.b.setPlayWhenReady(false);
            if (!isPlaying()) {
                L.player.d("%s onPaused ", "[BaseExoPlayer]:");
                onPaused();
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    final void suspend(AudioSource audioSource) {
        L.player.d("%s suspend ", "[BaseExoPlayer]:");
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$hyG119DpNmXcMhk4qQGB-DVD9gs
            @Override // java.lang.Runnable
            public final void run() {
                BaseExoPlayer.this.i();
            }
        });
    }

    public /* synthetic */ void i() {
        try {
            this.h = true;
            if (isPlaying()) {
                this.b.setPlayWhenReady(false);
                if (!isPlaying()) {
                    onPaused();
                }
            }
            onSuspend();
        } catch (Exception e) {
            L.player.e("%s suspend exception: %s", "[BaseExoPlayer]:", e);
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    final void resume(AudioSource audioSource) {
        L.player.d("%s resume by %s", "[BaseExoPlayer]:", audioSource);
        if (this.h) {
            try {
                postStart();
                this.h = false;
            } catch (Exception e) {
                L.player.e("%s resume exception: %s", "[BaseExoPlayer]:", e);
            }
        }
        a(this.l);
        onResume();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    final void background(AudioSource audioSource) {
        L.player.d("%s background ", "[BaseExoPlayer]:");
        try {
            if (!isPlaying()) {
                postStart();
            }
        } catch (Exception e) {
            L.player.e("%s background exception: %s", "[BaseExoPlayer]:", e);
        }
        this.h = false;
        a(0.1f);
        onBackground();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    final void foreground(AudioSource audioSource) {
        L.player.d("%s foreground by %s", "[BaseExoPlayer]:", audioSource);
        a(this.l);
        onForeground();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    void forceStop(AudioSource audioSource) {
        L.player.d("%s forceStop.source=%s", "[BaseExoPlayer]:", audioSource);
        onStopped();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    void playbackControl(PlaybackControllerInfo playbackControllerInfo) {
        L.player.d("%s playbackControl=%s", "[BaseExoPlayer]:", playbackControllerInfo.toString());
        onPlaybackControl(playbackControllerInfo);
    }

    public float getCurrentVolume() {
        return this.l;
    }

    private void c() {
        if (this.k == null) {
            this.k = new Handler(ThreadUtil.getLightWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$fb4tKy4CSucyZPwPcaqiGGJ9eF4
                @Override // android.os.Handler.Callback
                public final boolean handleMessage(Message message) {
                    boolean a;
                    a = BaseExoPlayer.this.a(message);
                    return a;
                }
            });
        }
    }

    public /* synthetic */ boolean a(Message message) {
        switch (message.what) {
            case 1:
                L.player.d("%s handler receive msg_time_out", "[BaseExoPlayer]:");
                stop();
                a(-110, ExoPlaybackException.createForRemote("time out, check url or network"));
                break;
            case 2:
                if (isPlaying()) {
                    float abs = Math.abs(this.m - this.l);
                    float f = this.n;
                    if (abs <= f) {
                        this.l = this.m;
                        onVolumeFadeComplete();
                    } else {
                        float f2 = this.m;
                        float f3 = this.l;
                        if (f2 > f3) {
                            this.l = f3 + f;
                        } else if (f2 < f3) {
                            this.l = f3 - f;
                        }
                    }
                    L.player.d("MSG_ADJUST_VOLUME:current=%s, target=%s", Float.valueOf(this.l), Float.valueOf(this.m));
                    a(this.l);
                }
                if (this.k != null && Math.abs(this.m - this.l) >= this.n) {
                    this.k.removeMessages(2);
                    this.k.sendEmptyMessageDelayed(2, 200L);
                    break;
                }
                break;
        }
        return false;
    }

    private void d() {
        if (this.j > 0 && this.k != null) {
            L.player.d("%s signTimeOut MSG", "[BaseExoPlayer]:");
            this.k.sendEmptyMessageDelayed(1, this.j);
        }
    }

    private void e() {
        if (this.k != null && this.k.hasMessages(1)) {
            L.player.d("%s remove TimeOut MSG ", "[BaseExoPlayer]:");
            this.k.removeMessages(1);
        }
    }

    public void onVolumeFadeComplete() {
        L.player.d("onVolumeFadeComplete");
    }

    private void a(final int i, final Exception exc) {
        final PlayerListener playerListener = this.a;
        if (playerListener != null) {
            if (this.p) {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$yzHTk339dfY1tco9_4UacvdE3qA
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseExoPlayer.this.a(playerListener, i, exc);
                    }
                });
            } else {
                playerListener.onError(this, i, exc);
            }
        }
    }

    public /* synthetic */ void a(PlayerListener playerListener, int i, Exception exc) {
        playerListener.onError(this, i, exc);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onIsPlayingChanged(boolean z) {
        this.q = z;
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onPlaybackStateChanged(int i) {
        L.player.i("%s onPlaybackStateChanged playbackState=%s", "[BaseExoPlayer]:", Integer.valueOf(i));
        switch (i) {
            case 1:
            case 2:
            default:
                return;
            case 3:
                if (!this.f) {
                    f();
                    return;
                } else {
                    start();
                    return;
                }
            case 4:
                g();
                return;
        }
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onPlayerError(PlaybackException playbackException) {
        L.player.w("%s onPlayerError=%s", "[BaseExoPlayer]:", playbackException.getCause());
        this.f = false;
        try {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$REBkWvFXMPGoKH1gBUkUmegiVyY
                @Override // java.lang.Runnable
                public final void run() {
                    BaseExoPlayer.this.h();
                }
            });
        } catch (Exception e) {
            L.player.e("%s, reset.exception=%s", "[BaseExoPlayer]:", e);
        }
        if (this.a != null) {
            L.player.w("%s onError autoPause=%b manualPause=%b", "[BaseExoPlayer]:", Boolean.valueOf(this.h), Boolean.valueOf(this.g));
            a(1, playbackException);
        } else {
            a(0L);
        }
        e();
    }

    public /* synthetic */ void h() {
        this.b.stop();
    }

    private void f() {
        final PlayerListener playerListener = this.a;
        L.player.d("%s onPrepared, manual paused=%s, playerListener=%s", "[BaseExoPlayer]:", Boolean.valueOf(this.g), playerListener);
        this.f = true;
        if (this.g) {
            L.player.i("%s onPrepared manual paused", "[BaseExoPlayer]:");
        } else if (playerListener == null) {
            start();
        } else if (this.p) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$y0CN7FOsnIckuvM6Lz9Pstji1yI
                @Override // java.lang.Runnable
                public final void run() {
                    BaseExoPlayer.this.b(playerListener);
                }
            });
        } else {
            playerListener.onPrepared(this);
        }
        e();
    }

    public /* synthetic */ void b(PlayerListener playerListener) {
        playerListener.onPrepared(this);
    }

    private void g() {
        final PlayerListener playerListener = this.a;
        L.player.d("%s onCompletion playerListener=%s", "[BaseExoPlayer]:", playerListener);
        this.f = false;
        if (playerListener == null) {
            a(0L);
        } else if (this.p) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BaseExoPlayer$mjyd6JtdJQ_u3bNzOJ56k-a0ow8
                @Override // java.lang.Runnable
                public final void run() {
                    BaseExoPlayer.this.a(playerListener);
                }
            });
        } else {
            playerListener.onComplete(this);
        }
    }

    public /* synthetic */ void a(PlayerListener playerListener) {
        playerListener.onComplete(this);
    }
}
