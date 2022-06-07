package com.xiaomi.micolauncher.common.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class SoundEventActivity extends BaseEventActivity implements BasePlayer.PlayerListener {
    public static final int DELAY_MILLIS = 1000;
    private AudioPolicyClient a;
    private BasePlayer b;

    protected boolean getLoop() {
        return false;
    }

    protected void onNext() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.a = new AudioPolicyClient(getAudioSource()) { // from class: com.xiaomi.micolauncher.common.base.SoundEventActivity.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient
            public void onNotify(AudioPolicyClient.PlayControl playControl, AudioSource audioSource) {
                super.onNotify(playControl, audioSource);
                switch (AnonymousClass3.a[playControl.ordinal()]) {
                    case 1:
                    case 2:
                        if (SoundEventActivity.this.b != null) {
                            SoundEventActivity.this.b.stop();
                        }
                        SoundEventActivity.this.onStopped();
                        return;
                    case 3:
                        if (SoundEventActivity.this.b != null) {
                            SoundEventActivity.this.b.stop();
                        }
                        SoundEventActivity.this.onStopped();
                        return;
                    default:
                        return;
                }
            }
        };
        this.b = null;
    }

    /* renamed from: com.xiaomi.micolauncher.common.base.SoundEventActivity$3  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a = new int[AudioPolicyClient.PlayControl.values().length];

        static {
            try {
                a[AudioPolicyClient.PlayControl.FORCE_STOP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[AudioPolicyClient.PlayControl.NOT_ALLOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[AudioPolicyClient.PlayControl.BACKGROUND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @NotNull
    protected AudioSource getAudioSource() {
        return AudioSource.AUDIO_SOURCE_UI;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.a.requestPlayAsync();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        BasePlayer basePlayer = this.b;
        if (basePlayer != null) {
            basePlayer.stop();
        }
        this.a.requestFinish();
        super.onDestroy();
    }

    protected void play(String str) {
        if (this.b == null) {
            this.b = new BasePlayer(getStreamType());
            this.b.setListener(this);
        }
        this.b.setDataSource(str);
        this.b.prepareAsync();
    }

    protected void playTts(String str) {
        SpeechEventUtil.getInstance().ttsRequest(str, new SpeechEventUtil.TTSListener() { // from class: com.xiaomi.micolauncher.common.base.SoundEventActivity.2
            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.TTSListener
            public void onTtsUrl(String str2, String str3) {
                SoundEventActivity.this.play(str2);
            }

            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.TTSListener
            public void onTtsFail(String str2, boolean z) {
                SoundEventActivity.this.onNext();
            }
        });
    }

    protected void play(@RawRes int i) {
        if (this.b == null) {
            this.b = new BasePlayer(getStreamType());
            this.b.setListener(this);
        }
        this.b.setDataSource(i, this);
        this.b.setLooping(getLoop());
        this.b.prepareAsync();
    }

    protected BasePlayer.StreamType getStreamType() {
        return BasePlayer.StreamType.STREAM_TYPE_MUSIC;
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onPrepared(BasePlayer basePlayer) {
        basePlayer.start();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onComplete(BasePlayer basePlayer) {
        onNext();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onError(BasePlayer basePlayer, int i, int i2) {
        onNext();
    }

    protected void onStopped() {
        ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.base.-$$Lambda$mrGtcJ4jZxTq43l8mfNxyThGPe0
            @Override // java.lang.Runnable
            public final void run() {
                SoundEventActivity.this.finish();
            }
        }, 1000L);
    }
}
