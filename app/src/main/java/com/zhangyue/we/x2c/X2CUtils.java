package com.zhangyue.we.x2c;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.TypedValue;

/* loaded from: classes4.dex */
public final class X2CUtils {
    public static int getResourceIdFromAttr(Context context, int i) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(i, typedValue, true);
        TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(typedValue.resourceId, new int[]{i});
        try {
            return obtainStyledAttributes.getResourceId(0, 0);
        } catch (Exception unused) {
            obtainStyledAttributes.recycle();
            return 0;
        }
    }
}
