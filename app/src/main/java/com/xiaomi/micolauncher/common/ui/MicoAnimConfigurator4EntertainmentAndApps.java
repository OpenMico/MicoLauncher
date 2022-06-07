package com.xiaomi.micolauncher.common.ui;

import android.view.View;
import androidx.annotation.MainThread;
import com.xiaomi.micolauncher.common.view.AnimConfigurator;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;

/* loaded from: classes3.dex */
public class MicoAnimConfigurator4EntertainmentAndApps implements AnimConfigurator {
    private static MicoAnimConfigurator4EntertainmentAndApps a;

    private MicoAnimConfigurator4EntertainmentAndApps() {
    }

    @MainThread
    public static MicoAnimConfigurator4EntertainmentAndApps get() {
        if (a == null) {
            a = new MicoAnimConfigurator4EntertainmentAndApps();
        }
        return a;
    }

    @Override // com.xiaomi.micolauncher.common.view.AnimConfigurator
    public void config(View view) {
        Folme.clean(view);
        Folme.useAt(view).touch().setScale(0.95f, ITouchStyle.TouchType.DOWN).setAlpha(0.8f, ITouchStyle.TouchType.DOWN).setScale(1.0f, ITouchStyle.TouchType.UP).setAlpha(1.0f, ITouchStyle.TouchType.UP).handleTouchOf(view, true, AnimConfigUtils.getAnimConfig());
    }
}
