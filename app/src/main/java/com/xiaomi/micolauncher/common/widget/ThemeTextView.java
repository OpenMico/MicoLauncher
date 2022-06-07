package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class ThemeTextView extends TextView {
    public ThemeTextView(Context context) {
        super(context);
        initTheme();
    }

    public ThemeTextView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initTheme();
    }

    public ThemeTextView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initTheme();
    }

    public ThemeTextView(Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initTheme();
    }

    private void initTheme() {
        if (ChildModeManager.getManager().isChildMode()) {
            setTextAppearance(R.style.TextAppearanceKid);
        }
    }
}
