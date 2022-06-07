package com.xiaomi.micolauncher.skills.voice.model;

import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;

/* loaded from: classes3.dex */
public class VoiceModel {
    private static volatile VoiceModel a;
    private String b;
    private BasePlayer c = new BasePlayer(AudioSource.AUDIO_SOURCE_URL_PLAYER);

    private VoiceModel() {
    }

    public static VoiceModel getInstance() {
        if (a == null) {
            synchronized (VoiceModel.class) {
                a = new VoiceModel();
            }
        }
        return a;
    }

    public void setSoundItem(String str) {
        this.b = str;
    }

    public void notifyUpdateSound(BasePlayer.PlayerListener playerListener) {
        BasePlayer basePlayer;
        if (this.b != null && (basePlayer = this.c) != null) {
            basePlayer.stop();
            this.c.setDataSource(this.b);
            this.c.setListener(playerListener);
            this.c.prepare();
            this.c.start();
        }
    }
}
