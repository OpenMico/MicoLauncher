package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.Dimension;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.tabs.TabLayout;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TabIndicatorInterpolator.java */
/* loaded from: classes2.dex */
public class b {
    static RectF a(@NonNull TabLayout.TabView tabView, @Dimension(unit = 0) int i) {
        int contentWidth = tabView.getContentWidth();
        int contentHeight = tabView.getContentHeight();
        int dpToPx = (int) ViewUtils.dpToPx(tabView.getContext(), i);
        if (contentWidth >= dpToPx) {
            dpToPx = contentWidth;
        }
        int left = (tabView.getLeft() + tabView.getRight()) / 2;
        int top = (tabView.getTop() + tabView.getBottom()) / 2;
        int i2 = dpToPx / 2;
        return new RectF(left - i2, top - (contentHeight / 2), i2 + left, top + (left / 2));
    }

    static RectF a(TabLayout tabLayout, @Nullable View view) {
        if (view == null) {
            return new RectF();
        }
        if (tabLayout.isTabIndicatorFullWidth() || !(view instanceof TabLayout.TabView)) {
            return new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
        return a((TabLayout.TabView) view, 24);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(TabLayout tabLayout, View view, @NonNull Drawable drawable) {
        RectF a = a(tabLayout, view);
        drawable.setBounds((int) a.left, drawable.getBounds().top, (int) a.right, drawable.getBounds().bottom);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(TabLayout tabLayout, View view, View view2, @FloatRange(from = 0.0d, to = 1.0d) float f, @NonNull Drawable drawable) {
        RectF a = a(tabLayout, view);
        RectF a2 = a(tabLayout, view2);
        drawable.setBounds(AnimationUtils.lerp((int) a.left, (int) a2.left, f), drawable.getBounds().top, AnimationUtils.lerp((int) a.right, (int) a2.right, f), drawable.getBounds().bottom);
    }
}
