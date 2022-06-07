package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.google.android.material.animation.AnimationUtils;

/* compiled from: ElasticTabIndicatorInterpolator.java */
/* loaded from: classes2.dex */
class a extends b {
    private static float a(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        return (float) Math.sin((f * 3.141592653589793d) / 2.0d);
    }

    private static float b(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        return (float) (1.0d - Math.cos((f * 3.141592653589793d) / 2.0d));
    }

    @Override // com.google.android.material.tabs.b
    void a(TabLayout tabLayout, View view, View view2, float f, @NonNull Drawable drawable) {
        float f2;
        float f3;
        RectF a = a(tabLayout, view);
        RectF a2 = a(tabLayout, view2);
        if (a.left < a2.left) {
            f3 = b(f);
            f2 = a(f);
        } else {
            f3 = a(f);
            f2 = b(f);
        }
        drawable.setBounds(AnimationUtils.lerp((int) a.left, (int) a2.left, f3), drawable.getBounds().top, AnimationUtils.lerp((int) a.right, (int) a2.right, f2), drawable.getBounds().bottom);
    }
}
