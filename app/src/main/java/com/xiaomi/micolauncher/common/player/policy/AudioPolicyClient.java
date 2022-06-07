package com.xiaomi.micolauncher.common.player.policy;

import android.os.Handler;
import android.os.Message;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.IAudioPolicy;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AudioPolicyClient {
    private static final long a = TimeUnit.MINUTES.toMillis(5);
    private AudioSource b;
    private boolean f;
    private volatile boolean i;
    private boolean h = false;
    private AudioPolicyService c = AudioPolicyService.getInstance();
    private Handler e = new Handler(ThreadUtil.getLightWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.player.policy.-$$Lambda$AudioPolicyClient$tBbs4PC01ft72wdJ9-xW7cdNUHo
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean a2;
            a2 = AudioPolicyClient.this.a(message);
            return a2;
        }
    });
    private PlayControl d = PlayControl.IDLE;
    private CopyOnWriteArrayList<AudioSource> g = new CopyOnWriteArrayList<>();

    /* loaded from: classes3.dex */
    public enum PlayControl {
        IDLE,
        ALLOW,
        FOREGROUND,
        BACKGROUND,
        SUSPEND,
        RESUME,
        NOT_ALLOW,
        FORCE_STOP,
        COMMAND_MAX
    }

    public void onNotify(PlayControl playControl, AudioSource audioSource) {
    }

    public AudioPolicyClient(AudioSource audioSource) {
        this.b = audioSource;
        L.policy.i("AudioPolicyClient.this=%s", Integer.valueOf(System.identityHashCode(this)));
    }

    public /* synthetic */ boolean a(Message message) {
        L.policy.d("AudioPolicyClient handleMessage.what=%s", Integer.valueOf(message.what));
        switch (message.what) {
            case 1:
                if (!(message.obj instanceof a)) {
                    if (message.obj instanceof PlayControl) {
                        onNotify((PlayControl) message.obj, AudioSource.AUDIO_SOURCE_NULL);
                        break;
                    }
                } else {
                    a aVar = (a) message.obj;
                    onNotify(aVar.b, aVar.c);
                    break;
                }
                break;
            case 2:
                requestFinish();
                break;
        }
        return false;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.d != PlayControl.IDLE) {
            this.d = PlayControl.IDLE;
            this.e.sendEmptyMessage(2);
        }
        L.policy.i("AudioPolicyClient.finalize.this=%s", Integer.valueOf(System.identityHashCode(this)));
    }

    public int a() {
        return this.b.a();
    }

    public AudioSource b() {
        return this.b;
    }

    public PlayControl c() {
        return this.d;
    }

    private void a(PlayControl playControl) {
        this.d = playControl;
    }

    public boolean d() {
        return this.b == AudioSource.AUDIO_SOURCE_MUSIC_BT;
    }

    public PlayControl requestPlay() {
        this.f = true;
        PlayControl a2 = this.c.a(this);
        this.h = false;
        this.f = false;
        return a2;
    }

    public PlayControl requestPause() {
        this.f = true;
        this.h = true;
        PlayControl requestPause = this.c.requestPause(this);
        this.f = false;
        return requestPause;
    }

    public void requestPlayAsync() {
        if (isReleased()) {
            L.policy.i("client is released %s", this);
        } else if (AudioSource.AUDIO_SOURCE_NULL == this.b) {
            this.e.removeMessages(1);
            this.e.obtainMessage(1, PlayControl.ALLOW).sendToTarget();
        } else {
            this.c.b(this);
            this.h = false;
        }
    }

    public boolean getPauseStatus() {
        return this.h;
    }

    public void requestFinish() {
        if (AudioSource.AUDIO_SOURCE_NULL != this.b) {
            this.c.a(this, 0L);
        }
    }

    public void requestFinish(boolean z) {
        if (AudioSource.AUDIO_SOURCE_NULL != this.b) {
            this.c.a(this, 0L);
        }
    }

    public void release() {
        this.d = PlayControl.IDLE;
        this.i = true;
        Handler handler = this.e;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        requestFinish();
    }

    public boolean isReleased() {
        return this.i;
    }

    public void requestFinish(long j) {
        if (AudioSource.AUDIO_SOURCE_NULL != this.b) {
            this.c.a(this, j);
        }
    }

    public PlayControl a(IAudioPolicy.RequestRet requestRet, AudioSource audioSource) {
        Handler handler;
        PlayControl a2 = a(requestRet);
        if (a2 != PlayControl.IDLE && !this.f && !isReleased() && (handler = this.e) != null) {
            handler.removeMessages(1);
            this.e.obtainMessage(1, new a(a2, audioSource)).sendToTarget();
        }
        return a2;
    }

    public boolean a(AudioSource audioSource) {
        return this.g.contains(audioSource);
    }

    public void setIgnoreForceStopSource(AudioSource audioSource) {
        this.g.addIfAbsent(audioSource);
    }

    public void a(PlaybackControllerInfo playbackControllerInfo) {
        onPlaybackControl(playbackControllerInfo);
    }

    public void onPlaybackControl(PlaybackControllerInfo playbackControllerInfo) {
        L.policy.i("playbackController getSource=%s,getSourceType=%s,foregroundClient=%s", b(), Integer.valueOf(a()), this);
    }

    public boolean supportPlaybackControl() {
        return this.b == AudioSource.AUDIO_SOURCE_MUSIC || this.b == AudioSource.AUDIO_SOURCE_MUSIC_BT || this.b == AudioSource.AUDIO_SOURCE_MUSIC_MI_PLAY || this.b == AudioSource.AUDIO_SOURCE_UI || this.b == AudioSource.AUDIO_SOURCE_FAKE || this.b == AudioSource.AUDIO_SOURCE_SHORT_VIDEO || this.b == AudioSource.AUDIO_SOURCE_LONG_VIDEO;
    }

    private PlayControl a(IAudioPolicy.RequestRet requestRet) {
        PlayControl c = c();
        PlayControl playControl = this.d;
        if (isReleased()) {
            L.policy.i("processPlayCtrl client is released %s", this);
            return playControl;
        }
        this.e.removeMessages(2);
        switch (requestRet) {
            case REQUEST_RET_ALLOW:
                if (c() == PlayControl.BACKGROUND) {
                    playControl = PlayControl.FOREGROUND;
                } else {
                    playControl = PlayControl.ALLOW;
                }
                a(PlayControl.FOREGROUND);
                break;
            case REQUEST_RET_RESUME:
            case REQUEST_RET_RESUME_AI:
                playControl = PlayControl.RESUME;
                a(PlayControl.FOREGROUND);
                break;
            case REQUEST_RET_FOREGROUND:
                playControl = PlayControl.FOREGROUND;
                a(PlayControl.FOREGROUND);
                break;
            case REQUEST_RET_SUSPEND:
            case REQUEST_RET_SUSPEND_AI:
                playControl = PlayControl.SUSPEND;
                a(PlayControl.SUSPEND);
                this.e.removeMessages(2);
                this.e.sendEmptyMessageDelayed(2, a);
                break;
            case REQUEST_RET_NOT_ALLOW:
                if (b() == AudioSource.AUDIO_SOURCE_MUSIC || b() == AudioSource.AUDIO_SOURCE_MUSIC_BT || b() == AudioSource.AUDIO_SOURCE_MUSIC_MI_PLAY) {
                    a(PlayControl.SUSPEND);
                } else {
                    a(PlayControl.IDLE);
                }
                playControl = PlayControl.NOT_ALLOW;
                break;
            case REQUEST_RET_FORCE_STOP:
                playControl = PlayControl.FORCE_STOP;
                a(PlayControl.IDLE);
                break;
            case REQUEST_RET_BACKGROUND:
                playControl = PlayControl.BACKGROUND;
                a(PlayControl.BACKGROUND);
                this.e.sendEmptyMessageDelayed(2, a);
                break;
            case REQUEST_RET_FINISH:
                a(PlayControl.IDLE);
                playControl = PlayControl.IDLE;
                break;
        }
        L.policy.d("processPlayCtrl %s(%s): %s --> %s", b(), Integer.valueOf(hashCode()), c, c());
        return playControl;
    }

    /* loaded from: classes3.dex */
    public final class a {
        private PlayControl b;
        private AudioSource c;

        private a(PlayControl playControl, AudioSource audioSource) {
            AudioPolicyClient.this = r1;
            this.b = playControl;
            this.c = audioSource;
        }
    }
}
