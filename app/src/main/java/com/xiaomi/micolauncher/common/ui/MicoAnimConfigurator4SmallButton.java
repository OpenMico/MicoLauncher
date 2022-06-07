package com.xiaomi.micolauncher.common.ui;

import android.view.View;
import androidx.annotation.MainThread;
import com.xiaomi.micolauncher.common.view.AnimConfigurator;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;

/* loaded from: classes3.dex */
public class MicoAnimConfigurator4SmallButton implements AnimConfigurator {
    private static MicoAnimConfigurator4SmallButton a;

    private MicoAnimConfigurator4SmallButton() {
    }

    @MainThread
    public static MicoAnimConfigurator4SmallButton get() {
        if (a == null) {
            a = new MicoAnimConfigurator4SmallButton();
        }
        return a;
    }

    @Override // com.xiaomi.micolauncher.common.view.AnimConfigurator
    public void config(View view) {
        Folme.clean(view);
        Folme.useAt(view).touch().setAlpha(0.7f, ITouchStyle.TouchType.DOWN).setAlpha(1.0f, ITouchStyle.TouchType.UP).handleTouchOf(view, true, AnimConfigUtils.getAnimConfig());
    }
}
