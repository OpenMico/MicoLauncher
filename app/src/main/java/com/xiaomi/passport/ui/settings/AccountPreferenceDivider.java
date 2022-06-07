package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.xiaomi.passport.ui.R;

/* loaded from: classes4.dex */
public class AccountPreferenceDivider extends View {
    public AccountPreferenceDivider(Context context) {
        super(context);
        initLayout();
    }

    public AccountPreferenceDivider(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initLayout();
    }

    public AccountPreferenceDivider(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initLayout();
    }

    private void initLayout() {
        setBackground(new ColorDrawable(getResources().getColor(R.color.preference_divider_color)));
    }
}
