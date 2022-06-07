package com.xiaomi.miplay.mylibrary;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;

/* loaded from: classes4.dex */
public class PhoneAudioHelper {
    public static final int TYPE_MUSIC = 3;
    private static final String a = "PhoneAudioHelper";
    private static PhoneAudioHelper c;
    private AudioManager b;

    public PhoneAudioHelper(Context context) {
        this.b = (AudioManager) context.getSystemService("audio");
    }

    public static synchronized PhoneAudioHelper getInstance(Context context) {
        PhoneAudioHelper phoneAudioHelper;
        synchronized (PhoneAudioHelper.class) {
            if (c == null) {
                c = new PhoneAudioHelper(context);
            }
            phoneAudioHelper = c;
        }
        return phoneAudioHelper;
    }

    public int getSystemVolume() {
        return this.b.getStreamVolume(3);
    }

    public void setSystemVolume(int i) {
        this.b.setStreamVolume(1, i, 3);
    }

    public void setStreamMute(boolean z) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (z) {
                this.b.adjustStreamVolume(3, -100, 0);
            } else {
                this.b.adjustStreamVolume(3, 100, 0);
            }
            Logger.i(a, "adjustStreamVolume.", new Object[0]);
            return;
        }
        Logger.i(a, "setStreamMute.", new Object[0]);
        this.b.setStreamMute(3, z);
    }
}
