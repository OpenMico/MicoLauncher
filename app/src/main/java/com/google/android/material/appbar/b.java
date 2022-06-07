package com.google.android.material.appbar;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.badge.BadgeDrawable;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: HeaderScrollingViewBehavior.java */
/* loaded from: classes2.dex */
public abstract class b extends c<View> {
    final Rect a = new Rect();
    final Rect b = new Rect();
    private int c = 0;
    private int d;

    private static int a(int i) {
        return i == 0 ? BadgeDrawable.TOP_START : i;
    }

    float a(View view) {
        return 1.0f;
    }

    @Nullable
    abstract View b(List<View> list);

    protected boolean shouldHeaderOverlapScrollingChild() {
        return false;
    }

    public b() {
    }

    public b(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i, int i2, int i3, int i4) {
        View b;
        WindowInsetsCompat lastWindowInsets;
        int i5 = view.getLayoutParams().height;
        if ((i5 != -1 && i5 != -2) || (b = b(coordinatorLayout.getDependencies(view))) == null) {
            return false;
        }
        int size = View.MeasureSpec.getSize(i3);
        if (size <= 0) {
            size = coordinatorLayout.getHeight();
        } else if (ViewCompat.getFitsSystemWindows(b) && (lastWindowInsets = coordinatorLayout.getLastWindowInsets()) != null) {
            size += lastWindowInsets.getSystemWindowInsetTop() + lastWindowInsets.getSystemWindowInsetBottom();
        }
        int b2 = size + b(b);
        int measuredHeight = b.getMeasuredHeight();
        if (shouldHeaderOverlapScrollingChild()) {
            view.setTranslationY(-measuredHeight);
        } else {
            b2 -= measuredHeight;
        }
        coordinatorLayout.onMeasureChild(view, i, i2, View.MeasureSpec.makeMeasureSpec(b2, i5 == -1 ? 1073741824 : Integer.MIN_VALUE), i4);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.material.appbar.c
    public void layoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i) {
        View b = b(coordinatorLayout.getDependencies(view));
        if (b != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
            Rect rect = this.a;
            rect.set(coordinatorLayout.getPaddingLeft() + layoutParams.leftMargin, b.getBottom() + layoutParams.topMargin, (coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight()) - layoutParams.rightMargin, ((coordinatorLayout.getHeight() + b.getBottom()) - coordinatorLayout.getPaddingBottom()) - layoutParams.bottomMargin);
            WindowInsetsCompat lastWindowInsets = coordinatorLayout.getLastWindowInsets();
            if (lastWindowInsets != null && ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(view)) {
                rect.left += lastWindowInsets.getSystemWindowInsetLeft();
                rect.right -= lastWindowInsets.getSystemWindowInsetRight();
            }
            Rect rect2 = this.b;
            GravityCompat.apply(a(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, i);
            int c = c(b);
            view.layout(rect2.left, rect2.top - c, rect2.right, rect2.bottom - c);
            this.c = rect2.top - b.getBottom();
            return;
        }
        super.layoutChild(coordinatorLayout, view, i);
        this.c = 0;
    }

    final int c(View view) {
        if (this.d == 0) {
            return 0;
        }
        float a = a(view);
        int i = this.d;
        return MathUtils.clamp((int) (a * i), 0, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b(@NonNull View view) {
        return view.getMeasuredHeight();
    }

    final int a() {
        return this.c;
    }

    public final void setOverlayTop(int i) {
        this.d = i;
    }

    public final int getOverlayTop() {
        return this.d;
    }
}
