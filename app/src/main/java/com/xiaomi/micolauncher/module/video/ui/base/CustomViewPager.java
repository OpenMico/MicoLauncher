package com.xiaomi.micolauncher.module.video.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes3.dex */
public class CustomViewPager extends ViewPager {
    private boolean a = true;

    public CustomViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean isScrollable() {
        return this.a;
    }

    public void setScrollable(boolean z) {
        this.a = z;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.a) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.a) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }
}
