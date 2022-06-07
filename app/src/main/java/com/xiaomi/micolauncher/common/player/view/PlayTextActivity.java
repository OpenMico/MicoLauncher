package com.xiaomi.micolauncher.common.player.view;

import android.os.Bundle;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.event.AlarmTtsEvent;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.model.PlayerModel;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;

/* loaded from: classes3.dex */
public final class PlayTextActivity extends GeneralTextActivity {
    AudioSource a;
    private MultiAudioPlayer b;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.player.view.GeneralTextActivity, com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = (AudioSource) getIntent().getSerializableExtra(PlayerModel.KEY_AUDIO_SOURCE);
        if (this.a != null) {
            EventBusRegistry.getEventBus().post(new AlarmTtsEvent(this.a, true));
        }
        this.b = new MultiAudioPlayer(BasePlayer.StreamType.STREAM_TYPE_ALARM);
        this.b.setListener(new MultiAudioPlayer.PlayerListener() { // from class: com.xiaomi.micolauncher.common.player.view.PlayTextActivity.1
            @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
            public void onComplete() {
                PlayTextActivity.this.stopPlayerAndFinishActivity();
            }

            @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
            public void onError(int i, Exception exc) {
                PlayTextActivity.this.stopPlayerAndFinishActivity();
            }
        });
        this.b.playTts(this.toDisplay);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.player.view.GeneralTextActivity, com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        if (this.a != null) {
            EventBusRegistry.getEventBus().post(new AlarmTtsEvent(this.a, false));
        }
        super.onDestroy();
        this.b.stop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.player.view.GeneralTextActivity
    public void onMediaFocusRelease() {
        super.onMediaFocusRelease();
        MultiAudioPlayer multiAudioPlayer = this.b;
        if (multiAudioPlayer != null) {
            multiAudioPlayer.stop();
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.view.GeneralTextActivity
    public void onBackgroundPlayer() {
        MultiAudioPlayer multiAudioPlayer = this.b;
        if (multiAudioPlayer != null) {
            multiAudioPlayer.setVolume(0.1f);
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.view.GeneralTextActivity
    void c() {
        MultiAudioPlayer multiAudioPlayer = this.b;
        if (multiAudioPlayer != null) {
            multiAudioPlayer.setVolume(1.0f);
        }
    }
}
