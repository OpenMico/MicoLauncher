package com.xiaomi.micolauncher.common.view.carouselview;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import androidx.viewpager.widget.ViewPager;
import java.lang.reflect.Field;

/* loaded from: classes3.dex */
public class CarouselViewScroller extends Scroller {
    private int a = 2000;

    public void setScrollDuration(int i) {
        this.a = i;
    }

    public int getmDuration() {
        return this.a;
    }

    public CarouselViewScroller(Context context) {
        super(context);
    }

    public CarouselViewScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public CarouselViewScroller(Context context, Interpolator interpolator, boolean z) {
        super(context, interpolator, z);
    }

    @Override // android.widget.Scroller
    public void startScroll(int i, int i2, int i3, int i4) {
        super.startScroll(i, i2, i3, i4, this.a);
    }

    @Override // android.widget.Scroller
    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        super.startScroll(i, i2, i3, i4, this.a);
    }

    public void initViewPagerScroll(ViewPager viewPager) {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            declaredField.set(viewPager, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
