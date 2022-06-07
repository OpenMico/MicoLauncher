package com.xiaomi.micolauncher.common.ui;

import android.view.View;
import androidx.annotation.MainThread;
import com.xiaomi.micolauncher.common.view.AnimConfigurator;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;

/* loaded from: classes3.dex */
public class MicoAnimConfigurator4ControlDevice implements AnimConfigurator {
    private static MicoAnimConfigurator4ControlDevice a;

    private MicoAnimConfigurator4ControlDevice() {
    }

    @MainThread
    public static MicoAnimConfigurator4ControlDevice get() {
        if (a == null) {
            a = new MicoAnimConfigurator4ControlDevice();
        }
        return a;
    }

    @Override // com.xiaomi.micolauncher.common.view.AnimConfigurator
    public void config(View view) {
        Folme.clean(view);
        Folme.useAt(view).touch().setScale(0.9f, ITouchStyle.TouchType.DOWN).setAlpha(0.8f, ITouchStyle.TouchType.DOWN).setScale(1.0f, ITouchStyle.TouchType.UP).setAlpha(1.0f, ITouchStyle.TouchType.UP).handleTouchOf(view, true, AnimConfigUtils.getAnimConfig());
    }
}
