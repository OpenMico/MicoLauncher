package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/* loaded from: classes3.dex */
public class HorizontalRecyclerContainScrollView extends RecyclerView {
    private int mInterval;
    private float mLastPosX;
    private float mLastPosY;
    private float mOffsetX;
    private float mOffsetY;

    public HorizontalRecyclerContainScrollView(Context context) {
        this(context, null);
    }

    public HorizontalRecyclerContainScrollView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HorizontalRecyclerContainScrollView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInterval = 50;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, 0, false) { // from class: com.xiaomi.micolauncher.common.widget.HorizontalRecyclerContainScrollView.1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.recyclerview.widget.LinearLayoutManager
            public int getExtraLayoutSpace(RecyclerView.State state) {
                return 1280;
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    Log.e("onLayoutChildren exception : %s", e.toString());
                }
            }
        };
        linearLayoutManager.setAutoMeasureEnabled(false);
        linearLayoutManager.setInitialPrefetchItemCount(2);
        setLayoutManager(linearLayoutManager);
        ((SimpleItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);
        setOverScrollMode(2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.mOffsetX += Math.abs(x - this.mLastPosX);
            this.mOffsetY += Math.abs(y - this.mLastPosY);
            this.mLastPosX = x;
            this.mLastPosY = y;
            float f = this.mOffsetX;
            int i = this.mInterval;
            return (f >= ((float) i) || this.mOffsetY >= ((float) i)) && this.mOffsetX >= this.mOffsetY;
        }
        this.mOffsetX = 0.0f;
        this.mOffsetY = 0.0f;
        this.mLastPosX = motionEvent.getX();
        this.mLastPosY = motionEvent.getY();
        return super.onInterceptTouchEvent(motionEvent);
    }
}
