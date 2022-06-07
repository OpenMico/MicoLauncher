package com.xiaomi.micolauncher.common.ui;

import android.view.View;
import androidx.annotation.MainThread;
import com.xiaomi.micolauncher.common.view.AnimConfigurator;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;

/* loaded from: classes3.dex */
public class MicoAnimConfigurator4BigButton implements AnimConfigurator {
    private static MicoAnimConfigurator4BigButton a;

    private MicoAnimConfigurator4BigButton() {
    }

    @MainThread
    public static MicoAnimConfigurator4BigButton get() {
        if (a == null) {
            a = new MicoAnimConfigurator4BigButton();
        }
        return a;
    }

    @Override // com.xiaomi.micolauncher.common.view.AnimConfigurator
    public void config(View view) {
        Folme.clean(view);
        boolean isEditMode = MicoAnimationManager.getManager().isEditMode();
        float f = 0.95f;
        ITouchStyle alpha = Folme.useAt(view).touch().setScale(isEditMode ? 0.9f : 0.95f, ITouchStyle.TouchType.DOWN).setAlpha(0.7f, ITouchStyle.TouchType.DOWN);
        if (!isEditMode) {
            f = 1.0f;
        }
        alpha.setScale(f, ITouchStyle.TouchType.UP).setAlpha(1.0f, ITouchStyle.TouchType.UP).handleTouchOf(view, true, AnimConfigUtils.getAnimConfig());
    }
}
