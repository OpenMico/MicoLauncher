package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.xiaomi.passport.ui.R;

/* loaded from: classes4.dex */
public class AccountPreferenceCategoryDivider extends View {
    public AccountPreferenceCategoryDivider(Context context) {
        super(context);
        initLayout();
    }

    public AccountPreferenceCategoryDivider(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initLayout();
    }

    public AccountPreferenceCategoryDivider(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initLayout();
    }

    private void initLayout() {
        setBackground(getResources().getDrawable(R.drawable.account_preference_category_divider));
    }
}
