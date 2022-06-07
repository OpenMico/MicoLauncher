package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes3.dex */
public class ClickableViewPager extends ViewPager {
    private DispatchTouchEventListener dispatchTouchEventLisenler;
    private View.OnClickListener mOnClickListener;
    private GestureDetector tapGestureDetector;

    /* loaded from: classes3.dex */
    public interface DispatchTouchEventListener {
        boolean onTouchEvent(MotionEvent motionEvent);
    }

    public ClickableViewPager(@NonNull Context context) {
        super(context);
        initView();
    }

    public ClickableViewPager(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    private void initView() {
        this.tapGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.xiaomi.micolauncher.common.widget.ClickableViewPager.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                return super.onDown(motionEvent);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (ClickableViewPager.this.mOnClickListener == null) {
                    return true;
                }
                ClickableViewPager.this.mOnClickListener.onClick(ClickableViewPager.this);
                return true;
            }
        });
        this.tapGestureDetector.setIsLongpressEnabled(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        DispatchTouchEventListener dispatchTouchEventListener = this.dispatchTouchEventLisenler;
        if (dispatchTouchEventListener != null && dispatchTouchEventListener.onTouchEvent(motionEvent)) {
            return true;
        }
        this.tapGestureDetector.onTouchEvent(motionEvent);
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public void setOnClickListener(@Nullable View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setDispatchTouchEventListener(DispatchTouchEventListener dispatchTouchEventListener) {
        this.dispatchTouchEventLisenler = dispatchTouchEventListener;
    }
}
