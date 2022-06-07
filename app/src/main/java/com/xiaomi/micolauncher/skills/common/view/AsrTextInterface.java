package com.xiaomi.micolauncher.skills.common.view;

import android.view.ViewPropertyAnimator;

/* loaded from: classes3.dex */
public interface AsrTextInterface {
    ViewPropertyAnimator animate();

    float getTextSize();

    void setAlpha(float f);

    void setText(String str);

    void setText(String str, boolean z);

    void setText(String str, boolean z, boolean z2);

    void setVisibility(int i);
}
