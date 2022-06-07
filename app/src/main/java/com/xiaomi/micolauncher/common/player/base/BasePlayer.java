package com.xiaomi.micolauncher.common.player.base;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.base.Player;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;

/* loaded from: classes3.dex */
public class BasePlayer extends Player {
    private PlayerListener a;
    private MediaPlayer b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    private long g;
    private Handler h;
    private float i;
    private float j;
    private float k;
    private StreamType l;
    private final boolean m;

    /* loaded from: classes3.dex */
    public interface PlayerListener {
        void onComplete(BasePlayer basePlayer);

        void onError(BasePlayer basePlayer, int i, int i2);

        void onPrepared(BasePlayer basePlayer);
    }

    /* loaded from: classes3.dex */
    public enum StreamType {
        STREAM_TYPE_MUSIC,
        STREAM_TYPE_COMMUNICATION,
        STREAM_TYPE_ALARM,
        STREAM_TYPE_RINGTONE
    }

    protected void onBackground() {
    }

    protected void onForeground() {
    }

    protected void onPaused() {
    }

    protected void onResume() {
    }

    protected void onStarted() {
    }

    protected void onStopped() {
    }

    protected void onSuspend() {
    }

    public BasePlayer(AudioSource audioSource, StreamType streamType, boolean z) {
        super(audioSource);
        a aVar = new a();
        this.b = new MediaPlayer();
        this.b.setOnCompletionListener(aVar);
        this.b.setOnErrorListener(aVar);
        this.b.setOnPreparedListener(aVar);
        this.b.setLooping(false);
        this.b.setAudioStreamType(3);
        setStreamType(streamType);
        this.d = false;
        this.e = false;
        this.f = false;
        this.i = 1.0f;
        this.j = 1.0f;
        this.m = z;
    }

    public BasePlayer(AudioSource audioSource, StreamType streamType) {
        this(audioSource, streamType, false);
    }

    public BasePlayer(StreamType streamType) {
        this(AudioSource.AUDIO_SOURCE_NULL, streamType, false);
    }

    public BasePlayer(AudioSource audioSource) {
        this(audioSource, StreamType.STREAM_TYPE_MUSIC, false);
    }

    public void setStreamType(StreamType streamType) {
        int i = 2;
        int i2 = 4;
        if (StreamType.STREAM_TYPE_COMMUNICATION == streamType) {
            i2 = 1;
        } else if (StreamType.STREAM_TYPE_ALARM == streamType) {
            i = 4;
        } else if (StreamType.STREAM_TYPE_RINGTONE == streamType) {
            i = 6;
        } else {
            i2 = 2;
            i = 1;
        }
        AudioAttributes build = new AudioAttributes.Builder().setContentType(i2).setUsage(i).build();
        this.l = streamType;
        this.b.setAudioAttributes(build);
    }

    public StreamType getStreamType() {
        return this.l;
    }

    public void setDataSource(String str) {
        if (TextUtils.isEmpty(str)) {
            L.player.d("%s setDataSource.url=null", "[BasePlayer]:");
            a(1);
            return;
        }
        e();
        try {
            this.b.stop();
            this.b.reset();
            this.b.setDataSource(str);
        } catch (Exception e) {
            L.player.e("%s setDataSource exception=%s", "[BasePlayer]:", e);
            a(1);
        }
    }

    public boolean setDataSource(int i, Context context) {
        if (i < 0 || context == null) {
            a(1);
            return false;
        }
        try {
            this.b.reset();
            AssetFileDescriptor openRawResourceFd = context.getResources().openRawResourceFd(i);
            this.b.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
            openRawResourceFd.close();
            return true;
        } catch (Exception e) {
            a(1);
            L.player.e("%s setDataSource(RawRes) exception: %s", "[BasePlayer]:", e);
            return false;
        }
    }

    public void releaseFocus(long j) {
        super.a(j);
    }

    public void releaseFocus() {
        super.a(0L);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public void release() {
        super.a(0L);
        try {
            this.b.stop();
            this.b.release();
        } catch (Exception e) {
            L.player.e("%s release exception: %s", "[BasePlayer]:", e);
        }
        e();
        this.j = 1.0f;
        this.i = 1.0f;
        Handler handler = this.h;
        if (handler != null) {
            handler.removeMessages(2);
        }
    }

    public boolean prepare() {
        this.c = false;
        this.e = false;
        this.d = false;
        super.a();
        try {
            this.b.prepare();
            this.c = true;
            return true;
        } catch (Exception e) {
            L.player.e("%s prepare exception: %s", "[BasePlayer]:", e);
            a(-1004);
            return false;
        }
    }

    public void prepareAsync() {
        L.player.d("%s prepareAsync ", "[BasePlayer]:");
        try {
            this.e = false;
            this.c = false;
            this.d = false;
            this.b.prepareAsync();
            d();
        } catch (Exception e) {
            L.player.e("%s prepareAsync exception: %s", "[BasePlayer]:", e);
        }
        super.a();
    }

    public void setListener(PlayerListener playerListener) {
        this.a = playerListener;
    }

    public void setPolicyListener(Player.PlayControlListener playControlListener) {
        super.setListener(playControlListener);
    }

    public void setVolume(float f) {
        L.player.d("this=%s, setVolume.volume=%f", Integer.valueOf(hashCode()), Float.valueOf(f));
        this.j = f;
        this.i = f;
        a(f);
    }

    private void a(float f) {
        try {
            L.player.d("this=%s, doSetVolume.volume=%f", Integer.valueOf(hashCode()), Float.valueOf(f));
            this.b.setVolume(f, f);
        } catch (Exception e) {
            L.player.e("%s setVolume exception: %s", "[BasePlayer]:", e);
        }
    }

    public void setLooping(boolean z) {
        try {
            this.b.setLooping(z);
        } catch (Exception e) {
            L.player.e("%s setLooping exception: %s", "[BasePlayer]:", e);
        }
        this.f = z;
    }

    public int getDuration() {
        try {
            return this.b.getDuration();
        } catch (Exception e) {
            L.player.e("%s getDuration exception: %s", "[BasePlayer]:", e);
            return -1;
        }
    }

    public boolean isPrepared() {
        return this.c;
    }

    public int getCurrentPosition() {
        try {
            return this.b.getCurrentPosition();
        } catch (Exception e) {
            L.player.e("%s getCurrentPosition exception: %s", "[BasePlayer]:", e);
            return -1;
        }
    }

    public boolean isPlaying() {
        try {
            if (this.c) {
                return this.b.isPlaying();
            }
        } catch (Exception e) {
            L.player.e("%s isPlaying exception: %s", "[BasePlayer]:", e);
        }
        return false;
    }

    public boolean isManualPause() {
        return this.d;
    }

    public boolean isAutoPause() {
        return this.e;
    }

    public void setPrepareTimeOut(long j) {
        this.g = j;
        c();
    }

    public void seekTo(int i) {
        try {
            L.player.d("%s seekTo:%d", "[BasePlayer]:", Integer.valueOf(i));
            this.b.seekTo(i);
        } catch (Exception e) {
            L.player.e("%s seekTo exception: %s", "[BasePlayer]:", e);
        }
    }

    public void reset() {
        L.player.w("%s reset manualPause=%b prepared=%b", "[BasePlayer]:", Boolean.valueOf(this.d), Boolean.valueOf(this.c));
        this.c = false;
        this.d = false;
        this.e = false;
        super.a(0L);
        try {
            this.b.stop();
            this.b.reset();
        } catch (Exception e) {
            L.player.e("%s reset.exception=%s", "[BasePlayer]:", e);
        }
        e();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public final void start() {
        L.player.d("%s start manualPause=%b prepared=%b", "[BasePlayer]:", Boolean.valueOf(this.d), Boolean.valueOf(this.c));
        this.d = false;
        if (this.c) {
            super.a();
            return;
        }
        L.player.d("%s start error, no prepared!!", "[BasePlayer]:");
        a(-1004);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public void restart() {
        try {
            L.player.d("%s restart", "[BasePlayer]:");
            this.b.seekTo(0);
            this.c = true;
            start();
        } catch (Exception e) {
            L.player.e("%s restart seekTo 0 exception: %s", "[BasePlayer]:", e);
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public final void stop() {
        this.d = true;
        super.a(0L);
        try {
            if (this.b.isPlaying()) {
                this.b.stop();
            }
        } catch (Exception e) {
            L.player.e("%s stop exception: %s", "[BasePlayer]:", e);
        }
        L.player.d("%s stop manualPause=%b prepared=%b", "[BasePlayer]:", Boolean.valueOf(this.d), Boolean.valueOf(this.c));
        e();
        this.i = this.j;
    }

    public final void pause() {
        this.d = true;
        super.b();
        try {
            if (this.b.isPlaying()) {
                this.b.pause();
            }
        } catch (Exception e) {
            L.player.e("%s pause exception: %s", "[BasePlayer]:", e);
        }
        L.player.d("%s pause manualPause=%b prepared=%b", "[BasePlayer]:", Boolean.valueOf(this.d), Boolean.valueOf(this.c));
        e();
        this.i = this.j;
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
            this.i = f;
            this.j = f2;
            a(this.i);
            this.k = (f2 - f) / ((float) (j / 200));
            c();
            Handler handler = this.h;
            if (handler != null) {
                handler.sendEmptyMessageDelayed(2, 200L);
            }
        }
    }

    public final void fadeOut(float f, long j) {
        float f2 = this.i;
        if (f2 <= f) {
            this.j = f2;
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
        this.j = f;
        this.k = (this.i - f) / ((float) (j / 200));
        c();
        Handler handler = this.h;
        if (handler != null) {
            handler.sendEmptyMessageDelayed(2, 200L);
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public final void setIgnoreForceStop(AudioSource audioSource) {
        L.player.i("%s setIgnoreForceStop.source=%s", "[BasePlayer]:", audioSource);
        super.setIgnoreForceStop(audioSource);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    final void postStart() {
        L.player.d("%s postStart manualPause=%b", "[BasePlayer]:", Boolean.valueOf(this.d));
        if (this.d || !isPrepared()) {
            L.player.w("%s postStart not prepared", "[BasePlayer]:");
            return;
        }
        try {
            this.b.start();
            onStarted();
        } catch (Exception e) {
            L.player.e("%s postStart exception: %s", "[BasePlayer]:", e);
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    protected void postStop(AudioSource audioSource) {
        L.player.d("%s postStop ", "[BasePlayer]:");
        this.d = true;
        this.e = false;
        try {
            if (this.b.isPlaying()) {
                this.b.pause();
            }
            if (!this.b.isPlaying()) {
                L.player.d("%s onPaused ", "[BasePlayer]:");
                onPaused();
            }
        } catch (Exception e) {
            L.player.e("%s postStop exception: %s", "[BasePlayer]:", e);
        }
        L.player.d("%s postStop manualPause=%b", "[BasePlayer]:", Boolean.valueOf(this.d));
        e();
        this.i = this.j;
        setVolume(this.i);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    final void suspend(AudioSource audioSource) {
        L.player.d("%s suspend ", "[BasePlayer]:");
        this.e = true;
        try {
            if (this.b.isPlaying()) {
                this.b.pause();
                if (!this.b.isPlaying()) {
                    onPaused();
                }
            }
        } catch (Exception e) {
            L.player.e("%s suspend exception: %s", "[BasePlayer]:", e);
        }
        onSuspend();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    final void resume(AudioSource audioSource) {
        L.player.d("%s resume by %s", "[BasePlayer]:", audioSource);
        if (this.e) {
            try {
                postStart();
                this.e = false;
                if (this.b.isPlaying()) {
                    onStarted();
                }
            } catch (Exception e) {
                L.player.e("%s resume exception: %s", "[BasePlayer]:", e);
            }
        }
        a(this.i);
        onResume();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    final void background(AudioSource audioSource) {
        L.player.d("%s background ", "[BasePlayer]:");
        try {
            if (!this.b.isPlaying()) {
                postStart();
            }
        } catch (Exception e) {
            L.player.e("%s background exception: %s", "[BasePlayer]:", e);
        }
        this.e = false;
        a(0.1f);
        onBackground();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    final void foreground(AudioSource audioSource) {
        L.player.d("%s foreground by %s", "[BasePlayer]:", audioSource);
        a(this.i);
        onForeground();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    void forceStop(AudioSource audioSource) {
        L.player.d("%s forceStop.source=%s", "[BasePlayer]:", audioSource);
        onStopped();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    void playbackControl(PlaybackControllerInfo playbackControllerInfo) {
        L.player.d("%s playbackControl=%s", "[BasePlayer]:", playbackControllerInfo.toString());
        onPlaybackControl(playbackControllerInfo);
    }

    private void c() {
        if (this.h == null) {
            this.h = new Handler(ThreadUtil.getLightWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BasePlayer$2yHKYWnygq0og-g3WCBMHfJPtmw
                @Override // android.os.Handler.Callback
                public final boolean handleMessage(Message message) {
                    boolean a2;
                    a2 = BasePlayer.this.a(message);
                    return a2;
                }
            });
        }
    }

    public /* synthetic */ boolean a(Message message) {
        switch (message.what) {
            case 1:
                L.player.d("%s handler receive msg_time_out", "[BasePlayer]:");
                MediaPlayer mediaPlayer = this.b;
                if (mediaPlayer != null) {
                    mediaPlayer.reset();
                }
                f();
                break;
            case 2:
                if (isPlaying()) {
                    float abs = Math.abs(this.j - this.i);
                    float f = this.k;
                    if (abs <= f) {
                        this.i = this.j;
                        onVolumeFadeComplete();
                    } else {
                        float f2 = this.j;
                        float f3 = this.i;
                        if (f2 > f3) {
                            this.i = f3 + f;
                        } else if (f2 < f3) {
                            this.i = f3 - f;
                        }
                    }
                    L.player.d("MSG_ADJUST_VOLUME:current=%s, target=%s", Float.valueOf(this.i), Float.valueOf(this.j));
                    a(this.i);
                }
                if (Math.abs(this.j - this.i) >= this.k) {
                    this.h.removeMessages(2);
                    this.h.sendEmptyMessageDelayed(2, 200L);
                    break;
                }
                break;
        }
        return false;
    }

    private void d() {
        if (this.g > 0 && this.h != null) {
            L.player.d("%s signTimeOut MSG", "[BasePlayer]:");
            this.h.sendEmptyMessageDelayed(1, this.g);
        }
    }

    public void e() {
        Handler handler = this.h;
        if (handler != null && handler.hasMessages(1)) {
            L.player.d("%s remove TimeOut MSG ", "[BasePlayer]:");
            this.h.removeMessages(1);
        }
    }

    public void onPlaybackControl(PlaybackControllerInfo playbackControllerInfo) {
        switch (playbackControllerInfo.playbackControl) {
            case PLAY:
            case CONTINUE_PLAYING:
                start();
                return;
            case PAUSE:
                pause();
                return;
            case STOP:
                stop();
                return;
            default:
                return;
        }
    }

    protected void onVolumeFadeComplete() {
        L.player.d("onVolumeFadeComplete");
    }

    private void f() {
        a(-110, 1);
    }

    private void a(int i) {
        a(i, 1);
    }

    public void a(final int i, final int i2) {
        final PlayerListener playerListener = this.a;
        if (playerListener != null) {
            if (this.m) {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BasePlayer$Q73GkQrjIUHE-dQ-Vgx0ODsEeBc
                    @Override // java.lang.Runnable
                    public final void run() {
                        BasePlayer.this.a(playerListener, i, i2);
                    }
                });
            } else {
                playerListener.onError(this, i, i2);
            }
        }
    }

    public /* synthetic */ void a(PlayerListener playerListener, int i, int i2) {
        playerListener.onError(this, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public final class a implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
        private a() {
            BasePlayer.this = r1;
        }

        @Override // android.media.MediaPlayer.OnErrorListener
        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            L.player.w("%s onError,%d %d", "[BasePlayer]:", Integer.valueOf(i), Integer.valueOf(i2));
            BasePlayer.this.c = false;
            try {
                mediaPlayer.reset();
            } catch (Exception e) {
                L.player.e("%s, reset.exception=%s", "[BasePlayer]:", e);
            }
            if (BasePlayer.this.a != null) {
                L.player.w("%s onError autoPause=%b manualPause=%b", "[BasePlayer]:", Boolean.valueOf(BasePlayer.this.e), Boolean.valueOf(BasePlayer.this.d));
                BasePlayer.this.a(i, i2);
            } else {
                BasePlayer.super.a(0L);
            }
            BasePlayer.this.e();
            return true;
        }

        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mediaPlayer) {
            final PlayerListener playerListener = BasePlayer.this.a;
            L.player.d("%s onCompletion playerListener=%s", "[BasePlayer]:", playerListener);
            BasePlayer.this.c = false;
            if (playerListener == null) {
                BasePlayer.super.a(0L);
            } else if (BasePlayer.this.m) {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BasePlayer$a$bLUE8NQ_JAegndbXLlGO-lN8t4M
                    @Override // java.lang.Runnable
                    public final void run() {
                        BasePlayer.a.this.b(playerListener);
                    }
                });
            } else {
                playerListener.onComplete(BasePlayer.this);
            }
        }

        public /* synthetic */ void b(PlayerListener playerListener) {
            playerListener.onComplete(BasePlayer.this);
        }

        @Override // android.media.MediaPlayer.OnPreparedListener
        public void onPrepared(MediaPlayer mediaPlayer) {
            final PlayerListener playerListener = BasePlayer.this.a;
            L.player.d("%s onPrepared, manual paused=%s, playerListener=%s", "[BasePlayer]:", Boolean.valueOf(BasePlayer.this.d), playerListener);
            BasePlayer.this.c = true;
            try {
                mediaPlayer.setLooping(BasePlayer.this.f);
            } catch (Exception e) {
                L.player.e("%s, onPrepared.exception=%s", "[BasePlayer]:", e);
            }
            if (BasePlayer.this.d) {
                L.player.i("manual paused");
            } else if (playerListener == null) {
                BasePlayer.this.start();
            } else if (BasePlayer.this.m) {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.base.-$$Lambda$BasePlayer$a$WxGLuQUt0ISv0fUQuIPTF_3gOSY
                    @Override // java.lang.Runnable
                    public final void run() {
                        BasePlayer.a.this.a(playerListener);
                    }
                });
            } else {
                playerListener.onPrepared(BasePlayer.this);
            }
            BasePlayer.this.e();
        }

        public /* synthetic */ void a(PlayerListener playerListener) {
            playerListener.onPrepared(BasePlayer.this);
        }
    }
}
