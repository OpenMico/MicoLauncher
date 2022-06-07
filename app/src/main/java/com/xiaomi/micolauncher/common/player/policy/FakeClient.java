package com.xiaomi.micolauncher.common.player.policy;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.video.controller.VideoSessionController;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* loaded from: classes3.dex */
public final class FakeClient extends AudioPolicyClient implements AudioManager.OnAudioFocusChangeListener {
    private static volatile FakeClient a;
    private AudioManager b;
    private a c;
    private boolean d;
    private AudioPolicyClient e;
    private Context f;
    private FakePlayer g;

    @Override // com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient
    public boolean supportPlaybackControl() {
        return true;
    }

    public static FakeClient getInstance() {
        if (a != null) {
            return a;
        }
        throw new IllegalStateException("need call init FakeClient.init()");
    }

    public static void init(Context context) {
        synchronized (FakeClient.class) {
            if (a == null) {
                a = new FakeClient(context);
            }
        }
    }

    public FakeClient(Context context) {
        super(AudioSource.AUDIO_SOURCE_FAKE);
        this.b = (AudioManager) context.getSystemService("audio");
        this.c = new a(context);
        this.f = context;
        e();
    }

    public void a(AudioPolicyClient audioPolicyClient) {
        AudioPolicyClient audioPolicyClient2 = this.e;
        if (audioPolicyClient2 != null && !audioPolicyClient2.equals(audioPolicyClient)) {
            this.e.onNotify(AudioPolicyClient.PlayControl.NOT_ALLOW, audioPolicyClient.b());
        }
        this.e = audioPolicyClient;
        requestPlayAsync();
    }

    public void b(AudioPolicyClient audioPolicyClient) {
        AudioPolicyClient audioPolicyClient2 = this.e;
        if (audioPolicyClient2 != null && audioPolicyClient2.equals(audioPolicyClient)) {
            this.e = null;
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient
    public boolean a(AudioSource audioSource) {
        AudioPolicyClient audioPolicyClient = this.e;
        if (audioPolicyClient != null) {
            return audioPolicyClient.a(audioSource);
        }
        return super.a(audioSource);
    }

    public void e() {
        if (this.b != null) {
            this.d = this.b.requestAudioFocus(new AudioFocusRequest.Builder(1).setOnAudioFocusChangeListener(this).setWillPauseWhenDucked(false).setAcceptsDelayedFocusGain(true).setAudioAttributes(new AudioAttributes.Builder().setContentType(2).setUsage(1).build()).build()) == 1;
            L.policy.d("policyFocusRequest.policyFocus=%b", Boolean.valueOf(this.d));
        }
    }

    public void f() {
        requestPlayAsync();
        this.d = false;
    }

    @Override // android.media.AudioManager.OnAudioFocusChangeListener
    public void onAudioFocusChange(int i) {
        L.policy.d("onAudioFocusChange: -----change=%s", Integer.valueOf(i));
        if (i != 1) {
            switch (i) {
                case -3:
                default:
                    return;
                case -2:
                    this.g = new FakePlayer(AudioSource.AUDIO_SOURCE_URL_PLAYER) { // from class: com.xiaomi.micolauncher.common.player.policy.FakeClient.1
                        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
                        public void postStop(AudioSource audioSource) {
                            FakeClient.this.e();
                        }
                    };
                    this.g.start();
                    return;
                case -1:
                    f();
                    return;
            }
        } else {
            FakePlayer fakePlayer = this.g;
            if (fakePlayer != null) {
                fakePlayer.stop();
                this.g = null;
                return;
            }
            requestFinish();
            this.d = true;
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient
    public void onNotify(AudioPolicyClient.PlayControl playControl, AudioSource audioSource) {
        super.onNotify(playControl, audioSource);
        L.policy.d("onNotify.status=%s, source=%s", playControl, audioSource);
        AudioPolicyClient audioPolicyClient = this.e;
        if (audioPolicyClient != null) {
            audioPolicyClient.onNotify(playControl, audioSource);
        }
        switch (playControl) {
            case SUSPEND:
                this.c.a();
                return;
            case FORCE_STOP:
            case NOT_ALLOW:
                this.c.b();
                e();
                this.e = null;
                return;
            case BACKGROUND:
                this.c.a();
                return;
            case ALLOW:
            case RESUME:
            case FOREGROUND:
                this.c.c();
                return;
            default:
                return;
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient
    public void onPlaybackControl(PlaybackControllerInfo playbackControllerInfo) {
        if (playbackControllerInfo != null) {
            L.policy.i("FakeClient onPlaybackControl=%s", playbackControllerInfo);
            if (this.e != null) {
                Remote.Response.PlayerStatus playerStatus = PlayerApi.getPlayerStatus();
                L.policy.i("policyClient media_type=%s,status=%s", Integer.valueOf(playerStatus.media_type), Integer.valueOf(playerStatus.status));
                if (MusicHelper.isPlayingBluetoothOrDlna(playerStatus.media_type)) {
                    if (playbackControllerInfo.playbackControl == PlaybackControl.PLAY || playbackControllerInfo.playbackControl == PlaybackControl.CONTINUE_PLAYING) {
                        this.e.onPlaybackControl(playbackControllerInfo);
                        return;
                    } else if (playbackControllerInfo.playbackControl == PlaybackControl.PAUSE) {
                        this.e.onPlaybackControl(playbackControllerInfo);
                    } else if (playbackControllerInfo.playbackControl == PlaybackControl.NEXT || playbackControllerInfo.playbackControl == PlaybackControl.PREV) {
                        boolean isThirdPartyAppInForeground = ThirdPartyAppProxy.getInstance().isThirdPartyAppInForeground(this.f);
                        L.policy.i("policyClient thirdPartyAppForeground=%s", Boolean.valueOf(isThirdPartyAppInForeground));
                        if (!isThirdPartyAppInForeground) {
                            this.e.onPlaybackControl(playbackControllerInfo);
                            return;
                        }
                    }
                }
            }
            switch (playbackControllerInfo.playbackControl) {
                case CONTINUE_PLAYING:
                case PLAY:
                    ThirdPartyAppProxy.getInstance().play(h());
                    return;
                case NEXT:
                    ThirdPartyAppProxy.getInstance().next(h());
                    return;
                case PREV:
                    ThirdPartyAppProxy.getInstance().previous(h());
                    return;
                case PAUSE:
                    ThirdPartyAppProxy.getInstance().pause(h());
                    return;
                case STOP:
                    ThirdPartyAppProxy.getInstance().stop(h());
                    return;
                case FAST_FORWARD:
                case REWIND:
                default:
                    return;
                case SEEK:
                    if (playbackControllerInfo.referenceDef != PlaybackControllerInfo.ReferenceDef.CURRENT) {
                        return;
                    }
                    if (playbackControllerInfo.deltaInMs > 0) {
                        ThirdPartyAppProxy.getInstance().fastForward(this.f, Math.abs(playbackControllerInfo.deltaInMs));
                        return;
                    } else if (playbackControllerInfo.deltaInMs < 0) {
                        ThirdPartyAppProxy.getInstance().fastBackward(this.f, Math.abs(playbackControllerInfo.deltaInMs));
                        return;
                    } else {
                        return;
                    }
                case SET_PROPERTY:
                    if (playbackControllerInfo.setPropertyName == PlaybackControllerInfo.SetProperty.RESOLUTION) {
                        ThirdPartyAppProxy instance = ThirdPartyAppProxy.getInstance();
                        if (instance.getCurrentProcessor() != null && instance.isThirdPartyAppInForeground(h())) {
                            instance.setResolution(h(), playbackControllerInfo.propertyValue);
                            return;
                        }
                        return;
                    }
                    return;
                case SELECTOR:
                    ThirdPartyAppProxy.getInstance().select(this.f, playbackControllerInfo.index);
                    return;
                case START_OVER:
                    ThirdPartyAppProxy.getInstance().repeat(this.f);
                    return;
                case STOP_AFTER:
                    if (playbackControllerInfo.timeoutInMs > 0) {
                        a(playbackControllerInfo.timeoutInMs);
                        return;
                    } else {
                        int i = playbackControllerInfo.countOfEnd;
                        return;
                    }
                case CANCEL_STOP_AFTER:
                    g();
                    return;
            }
        }
    }

    private void g() {
        VideoSessionController.getInstance().clearStopTime(h());
    }

    private void a(long j) {
        VideoSessionController.getInstance().setPlayerShutdownTimer(h(), j);
    }

    private Context h() {
        return this.f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public final class a implements AudioManager.OnAudioFocusChangeListener {
        private AudioManager b;
        private boolean c;

        private a(Context context) {
            FakeClient.this = r1;
            this.b = (AudioManager) context.getSystemService("audio");
            this.c = false;
        }

        private void a(int i) {
            if (this.b != null && !FakeClient.this.d) {
                boolean z = true;
                if (this.b.requestAudioFocus(new AudioFocusRequest.Builder(i).setOnAudioFocusChangeListener(this).setWillPauseWhenDucked(false).setAcceptsDelayedFocusGain(true).setAudioAttributes(new AudioAttributes.Builder().setContentType(2).setUsage(1).build()).build()) != 1) {
                    z = false;
                }
                this.c = z;
                L.policy.d("InternalAudioFocus.requestAudioFocus");
            }
        }

        public void a() {
            a(2);
        }

        public void b() {
            a(1);
        }

        public void c() {
            L.policy.d("InternalAudioFocus.foreground.focus=%s", Boolean.valueOf(this.c));
            AudioManager audioManager = this.b;
            if (audioManager != null && this.c) {
                audioManager.abandonAudioFocus(this);
            }
            this.c = false;
        }

        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i) {
            L.policy.d("InternalAudioFocus.onAudioFocusChange=%d", Integer.valueOf(i));
            if (i != 1) {
                switch (i) {
                    case -3:
                    case -2:
                    case -1:
                        if (FakeClient.this.c() != AudioPolicyClient.PlayControl.IDLE) {
                            FakeClient.this.f();
                            break;
                        }
                        break;
                }
            } else {
                FakeClient.this.e();
            }
            this.c = false;
        }
    }
}
