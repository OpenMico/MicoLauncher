package com.google.android.material.theme.overlay;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.ContextThemeWrapper;
import com.google.android.material.R;

/* loaded from: classes2.dex */
public class MaterialThemeOverlay {
    private static final int[] a = {16842752, R.attr.theme};
    private static final int[] b = {R.attr.materialThemeOverlay};

    private MaterialThemeOverlay() {
    }

    @NonNull
    public static Context wrap(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        int a2 = a(context, attributeSet, i, i2);
        boolean z = (context instanceof ContextThemeWrapper) && ((ContextThemeWrapper) context).getThemeResId() == a2;
        if (a2 == 0 || z) {
            return context;
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, a2);
        int a3 = a(context, attributeSet);
        if (a3 != 0) {
            contextThemeWrapper.getTheme().applyStyle(a3, true);
        }
        return contextThemeWrapper;
    }

    @StyleRes
    private static int a(@NonNull Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
        obtainStyledAttributes.recycle();
        return resourceId != 0 ? resourceId : resourceId2;
    }

    @StyleRes
    private static int a(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, b, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        return resourceId;
    }
}
