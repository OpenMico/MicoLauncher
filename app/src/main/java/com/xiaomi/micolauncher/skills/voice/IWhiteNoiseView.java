package com.xiaomi.micolauncher.skills.voice;

import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;

/* loaded from: classes3.dex */
public interface IWhiteNoiseView {
    void finish(String str);

    void onPlayStateChange(MicoMultiAudioPlayer.AudioState audioState);

    void updateImage(String str);

    void updateImageLocal(String str);

    void updateTime(String str);

    void updateTitle(String str);
}
