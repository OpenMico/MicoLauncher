package com.xiaomi.micolauncher.common.view.carouselview;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.xiaomi.mico.common.ContainerUtil;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class CustomCarouselViewAdapter extends CarouselViewAdapter {
    private final LinkedList<View> a = new LinkedList<>();
    protected List dataList;

    public abstract void bindPageView(View view, Object obj);

    public abstract View getPageView();

    public CustomCarouselViewAdapter(List list) {
        super(null, 0);
        this.dataList = list;
        this.realPageCount = getPageCount();
    }

    public void setDataList(List list) {
        this.dataList = list;
        this.realPageCount = getPageCount();
        notifyDataSetChanged();
    }

    public int getPageCount() {
        return ContainerUtil.getSize(this.dataList);
    }

    @Override // com.xiaomi.micolauncher.common.view.carouselview.CarouselViewAdapter, androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        View view = (View) obj;
        viewGroup.removeView(view);
        this.a.add(view);
    }

    @Override // com.xiaomi.micolauncher.common.view.carouselview.CarouselViewAdapter, androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View view;
        if (this.a.isEmpty()) {
            view = getPageView();
        } else {
            view = this.a.removeFirst();
        }
        List list = this.dataList;
        if (list == null || list.isEmpty()) {
            return null;
        }
        int size = i % this.dataList.size();
        if (size < 0) {
            size += this.dataList.size();
        }
        bindPageView(view, this.dataList.get(size));
        ViewParent parent = view.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(view);
        }
        viewGroup.addView(view);
        return view;
    }
}
