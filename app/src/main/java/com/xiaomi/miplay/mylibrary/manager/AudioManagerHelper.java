package com.xiaomi.miplay.mylibrary.manager;

import android.content.Context;
import android.media.AudioManager;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;

/* loaded from: classes4.dex */
public class AudioManagerHelper {
    private final String a = "AudioManagerHelper";
    private AudioManager b;
    private final int c;

    public AudioManagerHelper(Context context) {
        this.b = (AudioManager) context.getSystemService("audio");
        this.c = this.b.getStreamMaxVolume(3);
    }

    public void setVolume(int i) {
        Logger.i("AudioManagerHelper", "volume:" + i, new Object[0]);
        this.b.setStreamVolume(3, (int) (((float) this.c) * (((float) i) / 100.0f)), 8);
    }

    public int getVolume() {
        Logger.i("AudioManagerHelper", "getVolume.", new Object[0]);
        return (int) (((this.b.getStreamVolume(3) * 1.0f) / this.c) * 100.0f);
    }
}
