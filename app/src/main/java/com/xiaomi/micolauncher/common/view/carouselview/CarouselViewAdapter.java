package com.xiaomi.micolauncher.common.view.carouselview;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

/* loaded from: classes3.dex */
public class CarouselViewAdapter extends PagerAdapter {
    private List<View> a;
    protected int realPageCount;

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public CarouselViewAdapter(List<View> list, int i) {
        this.a = list;
        this.realPageCount = i;
    }

    public int getRealPageCount() {
        return this.realPageCount;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.realPageCount == 1 ? 1 : Integer.MAX_VALUE;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        int size = i % this.a.size();
        if (size < 0) {
            size += this.a.size();
        }
        View view = this.a.get(size);
        ViewParent parent = view.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(view);
        }
        viewGroup.addView(view);
        return view;
    }
}
