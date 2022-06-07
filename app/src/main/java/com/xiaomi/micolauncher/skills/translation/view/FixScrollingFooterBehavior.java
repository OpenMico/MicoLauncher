package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;

/* loaded from: classes3.dex */
public class FixScrollingFooterBehavior extends AppBarLayout.ScrollingViewBehavior {
    private AppBarLayout c;

    public FixScrollingFooterBehavior() {
    }

    public FixScrollingFooterBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
        if (this.c == null) {
            this.c = (AppBarLayout) view2;
        }
        boolean onDependentViewChanged = super.onDependentViewChanged(coordinatorLayout, view, view2);
        int a = a(this.c);
        boolean z = a != view.getPaddingBottom();
        if (z) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), a);
            view.requestLayout();
        }
        return z || onDependentViewChanged;
    }

    private int a(AppBarLayout appBarLayout) {
        return appBarLayout.getTotalScrollRange() + appBarLayout.getTop();
    }
}
