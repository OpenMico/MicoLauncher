package com.xiaomi.micolauncher.common.ui;

import android.view.View;
import androidx.annotation.MainThread;
import com.xiaomi.micolauncher.common.view.AnimConfigurator;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;

/* loaded from: classes3.dex */
public class MicoAnimConfigurator4TabAndSwitch implements AnimConfigurator {
    private static MicoAnimConfigurator4TabAndSwitch a;

    private MicoAnimConfigurator4TabAndSwitch() {
    }

    @MainThread
    public static MicoAnimConfigurator4TabAndSwitch get() {
        if (a == null) {
            a = new MicoAnimConfigurator4TabAndSwitch();
        }
        return a;
    }

    @Override // com.xiaomi.micolauncher.common.view.AnimConfigurator
    public void config(View view) {
        Folme.clean(view);
        Folme.useAt(view).touch().setAlpha(0.5f, ITouchStyle.TouchType.DOWN).setAlpha(1.0f, ITouchStyle.TouchType.UP).handleTouchOf(view, true, AnimConfigUtils.getAnimConfig());
    }
}
