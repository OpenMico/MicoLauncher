package com.xiaomi.micolauncher.common.view.carouselview;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes3.dex */
public class FadePageTransformer implements ViewPager.PageTransformer {
    @Override // androidx.viewpager.widget.ViewPager.PageTransformer
    public void transformPage(View view, float f) {
        if (f <= -1.0f || f >= 1.0f) {
            view.setTranslationX(view.getWidth() * f);
            view.setAlpha(0.0f);
        } else if (f == 0.0f) {
            view.setTranslationX(view.getWidth() * f);
            view.setAlpha(1.0f);
        } else {
            view.setTranslationX(view.getWidth() * (-f));
            view.setAlpha(1.0f - Math.abs(f));
        }
    }
}
