package com.xiaomi.micolauncher.common.player.view;

import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.event.TtsPlayEndEvent;
import com.xiaomi.micolauncher.common.event.TtsPlayErrorEvent;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.model.PlayerModel;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.skills.baike.model.BaikeModel;
import com.xiaomi.micolauncher.skills.baike.view.BaikeTextView;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class GeneralTextActivity extends BaseEventActivity implements BaikeTextView.CompleteListener {
    private static final int f = (int) TimeUnit.SECONDS.toMillis(1);
    private String a;
    private String b;
    private Runnable c;
    private BaikeTextView d;
    private boolean e;
    private FakePlayer g;
    protected String toDisplay;

    void a() {
    }

    void b() {
    }

    void c() {
    }

    public void onBackgroundPlayer() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_baike_text);
        this.a = getIntent().getStringExtra("KEY_TO_SPEAK");
        this.b = getIntent().getStringExtra(BaikeModel.KEY_ID);
        this.c = new Runnable() { // from class: com.xiaomi.micolauncher.common.player.view.-$$Lambda$oV67LRKCrTIuKbT_3SKirSO7TvQ
            @Override // java.lang.Runnable
            public final void run() {
                GeneralTextActivity.this.finish();
            }
        };
        this.toDisplay = getIntent().getStringExtra("KEY_TEXT_CONTENT");
        AudioSource audioSource = (AudioSource) getIntent().getSerializableExtra(PlayerModel.KEY_AUDIO_SOURCE);
        if (audioSource == null) {
            audioSource = AudioSource.AUDIO_SOURCE_UI;
        }
        a(audioSource);
        d();
        String str = this.toDisplay;
        if (str != null && str.length() > 0) {
            this.d.setText(this.toDisplay);
            this.d.setCompleteListener(this);
        }
        setPlayFinish(false);
        this.g.start();
    }

    @Override // com.xiaomi.micolauncher.skills.baike.view.BaikeTextView.CompleteListener
    public void onComplete() {
        if (TextUtils.isEmpty(this.a)) {
            L.base.d("BaikeTextActivity.onComplete");
            finishDelayed(7000);
        }
    }

    private void d() {
        this.d = (BaikeTextView) findViewById(R.id.baikeTextView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ThreadUtil.removeCallbacksInMainThread(this.c);
        this.d.stopUpdate();
        FakePlayer fakePlayer = this.g;
        if (fakePlayer != null) {
            fakePlayer.stop();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTtsPlayEndEvent(TtsPlayEndEvent ttsPlayEndEvent) {
        L.base.d("BaikeTextActivity.onTtsPlayEndEvent.toSpeak=%s, query=%s", this.b, ttsPlayEndEvent.dialogId);
        String str = this.b;
        if (str != null && str.equals(ttsPlayEndEvent.dialogId)) {
            finishDelayed(7000);
            setPlayFinish(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTtsPlayErrorEvent(TtsPlayErrorEvent ttsPlayErrorEvent) {
        L.base.d("BaikeTextActivity.onTtsPlayErrorEvent");
        String str = this.b;
        if (str != null && str.equals(ttsPlayErrorEvent.dialogId)) {
            finishDelayed(7000);
            setPlayFinish(true);
        }
    }

    public void finishDelayed(int i) {
        ThreadUtil.removeCallbacksInMainThread(this.c);
        ThreadUtil.postDelayedInMainThread(this.c, i);
    }

    public void stopPlayerAndFinishActivity() {
        this.a = null;
        finishDelayed(f);
        onMediaFocusRelease();
    }

    private void a(AudioSource audioSource) {
        this.g = new FakePlayer(audioSource) { // from class: com.xiaomi.micolauncher.common.player.view.GeneralTextActivity.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void forceStop(AudioSource audioSource2) {
                L.base.i("general text activity, force stop %s", audioSource2);
                GeneralTextActivity.this.stopPlayerAndFinishActivity();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
            public void postStop(AudioSource audioSource2) {
                L.base.i("general text activity, post stop %s", audioSource2);
                if (audioSource2 == AudioSource.AUDIO_SOURCE_MUSIC_MI_PLAY) {
                    GeneralTextActivity.this.g.start();
                } else {
                    GeneralTextActivity.this.stopPlayerAndFinishActivity();
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void suspend(AudioSource audioSource2) {
                L.base.i("general text activity, suspend %s", audioSource2);
                GeneralTextActivity.this.a();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void background(AudioSource audioSource2) {
                L.base.i("general text activity, background %s", audioSource2);
                GeneralTextActivity.this.onBackgroundPlayer();
            }

            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            protected void foreground(AudioSource audioSource2) {
                L.base.i("general text activity, foreground");
                GeneralTextActivity.this.c();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void resume(AudioSource audioSource2) {
                L.base.i("general text activity, resume");
                GeneralTextActivity.this.b();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
            public void postStart() {
                super.postStart();
            }
        };
    }

    public boolean isPlayFinish() {
        return this.e;
    }

    public void setPlayFinish(boolean z) {
        this.e = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMediaFocusRelease() {
        L.base.i("general text activity onMediaFocusRelease");
    }
}
