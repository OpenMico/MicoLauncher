package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;

/* loaded from: classes3.dex */
public class ElevatedConstraintLayout extends ConstraintLayout {
    public ElevatedConstraintLayout(Context context) {
        super(context);
        init();
    }

    public ElevatedConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ElevatedConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setDefaultAlphaOutlineProvider(10, 20, 24.0f);
    }

    public void setDefaultAlphaOutlineProvider(int i, int i2, float f) {
        setOutlineProvider(new ScalingLayoutOutlineProvider(i, i2, f));
    }
}
