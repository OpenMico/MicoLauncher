package com.xiaomi.micolauncher.skills.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.OverScroller;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.reflect.Field;

/* loaded from: classes3.dex */
public class AutoScrollRecyclerView extends RecyclerView {
    private LinearSmoothScroller a;

    public AutoScrollRecyclerView(Context context) {
        super(context);
        a(context);
    }

    public AutoScrollRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public AutoScrollRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        this.a = new LinearSmoothScroller(context);
    }

    private void a(int i, Interpolator interpolator) {
        try {
            Field declaredField = RecyclerView.class.getDeclaredField("mViewFlinger");
            declaredField.setAccessible(true);
            Class<?> cls = Class.forName("android.support.v7.widget.RecyclerView$ViewFlinger");
            Field declaredField2 = cls.getDeclaredField("mScroller");
            declaredField2.setAccessible(true);
            Field declaredField3 = cls.getDeclaredField("mInterpolator");
            declaredField3.setAccessible(true);
            Field declaredField4 = LinearSmoothScroller.class.getDeclaredField("mDecelerateInterpolator");
            declaredField4.setAccessible(true);
            declaredField3.set(declaredField.get(this), declaredField4.get(this.a));
            if (i >= 0) {
                AutoOverScroller autoOverScroller = new AutoOverScroller(getContext(), interpolator);
                autoOverScroller.setScrollDuration(i);
                declaredField2.set(declaredField.get(this), autoOverScroller);
            } else {
                declaredField2.set(declaredField.get(this), new OverScroller(getContext(), interpolator));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void smoothScrollToPosition(int i, int i2, Interpolator interpolator) {
        if (getLayoutManager() != null) {
            this.a.setTargetPosition(i);
            a(i2, interpolator);
            getLayoutManager().startSmoothScroll(this.a);
        }
    }

    public void smoothScrollToPosition(int i, int i2) {
        smoothScrollToPosition(i, i2, new LinearInterpolator());
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z = getScrollState() == 2;
        boolean onInterceptTouchEvent = super.onInterceptTouchEvent(motionEvent);
        if (motionEvent.getActionMasked() != 0 || !z) {
            return onInterceptTouchEvent;
        }
        getParent().requestDisallowInterceptTouchEvent(false);
        stopScroll();
        return false;
    }
}
