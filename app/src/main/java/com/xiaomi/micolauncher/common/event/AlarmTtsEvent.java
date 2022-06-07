package com.xiaomi.micolauncher.common.event;

import com.xiaomi.micolauncher.common.player.policy.AudioSource;

/* loaded from: classes3.dex */
public class AlarmTtsEvent {
    AudioSource a;
    boolean b;

    public AlarmTtsEvent(AudioSource audioSource, boolean z) {
        this.a = audioSource;
        this.b = z;
    }

    public AudioSource getSource() {
        return this.a;
    }

    public boolean isStart() {
        return this.b;
    }
}
