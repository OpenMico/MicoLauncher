package com.xiaomi.micolauncher.module.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/* loaded from: classes3.dex */
public class MyHorizontalScrollView extends HorizontalScrollView {
    private OnScrollListener a;

    /* loaded from: classes3.dex */
    public interface OnScrollListener {
        void onScroll(MyHorizontalScrollView myHorizontalScrollView);
    }

    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.a != null && getHeight() + getScrollY() >= computeVerticalScrollRange()) {
            this.a.onScroll(this);
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.a = onScrollListener;
    }
}
