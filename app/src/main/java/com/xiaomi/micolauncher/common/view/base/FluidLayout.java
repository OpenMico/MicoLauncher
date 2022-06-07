package com.xiaomi.micolauncher.common.view.base;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.xiaomi.micolauncher.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class FluidLayout extends ViewGroup {
    private int a;
    private List<List<View>> b;
    private List<Integer> c;

    public FluidLayout(Context context) {
        this(context, null);
    }

    public FluidLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FluidLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = -1;
        this.b = new ArrayList();
        this.c = new ArrayList();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FluidLayout);
        this.a = obtainStyledAttributes.getInt(0, -1);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i, i2);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                int measuredHeight = childAt.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                if (i3 + measuredHeight > (size2 - getPaddingTop()) - getPaddingBottom()) {
                    break;
                }
                int i8 = i4 + measuredWidth;
                if (i8 > (size - getPaddingLeft()) - getPaddingRight()) {
                    i5 = Math.max(i5, i4);
                    i3 += i6;
                } else {
                    measuredHeight = Math.max(i6, measuredHeight);
                    measuredWidth = i8;
                }
                if (i7 == childCount - 1) {
                    i3 += measuredHeight;
                    i5 = Math.max(measuredWidth, i5);
                    i6 = measuredHeight;
                    i4 = measuredWidth;
                } else {
                    i6 = measuredHeight;
                    i4 = measuredWidth;
                }
            }
        }
        if (mode != 1073741824) {
            size = getPaddingRight() + i5 + getPaddingLeft();
        }
        if (mode2 != 1073741824) {
            size2 = getPaddingBottom() + i3 + getPaddingTop();
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.ViewGroup, android.view.View
    @SuppressLint({"DrawAllocation"})
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        this.b.clear();
        this.c.clear();
        int width = getWidth();
        int height = getHeight();
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        ArrayList arrayList2 = arrayList;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
            if (measuredWidth + i7 + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin > (width - getPaddingLeft()) - getPaddingRight()) {
                this.c.add(Integer.valueOf(i6));
                this.b.add(arrayList2);
                i6 = marginLayoutParams.topMargin + measuredHeight + marginLayoutParams.bottomMargin;
                arrayList2 = new ArrayList();
                i7 = 0;
            }
            i7 += measuredWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            i6 = Math.max(i6, measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin);
            arrayList2.add(childAt);
        }
        this.c.add(Integer.valueOf(i6));
        this.b.add(arrayList2);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int size = this.b.size();
        int i9 = paddingLeft;
        for (int i10 = 0; i10 < size; i10++) {
            List<View> list = this.b.get(i10);
            int intValue = this.c.get(i10).intValue();
            int size2 = list.size();
            for (int i11 = 0; i11 < size2; i11++) {
                View view = list.get(i11);
                if (view.getVisibility() != 8) {
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    int i12 = this.a;
                    if (i12 == 0) {
                        i5 = ((((intValue - marginLayoutParams2.topMargin) - marginLayoutParams2.bottomMargin) - view.getMeasuredHeight()) / 2) + paddingTop;
                    } else {
                        i5 = i12 == 1 ? (((intValue - marginLayoutParams2.topMargin) - marginLayoutParams2.bottomMargin) - view.getMeasuredHeight()) + paddingTop : paddingTop;
                    }
                    int i13 = marginLayoutParams2.leftMargin + i9;
                    int i14 = i5 + marginLayoutParams2.topMargin;
                    int measuredWidth2 = view.getMeasuredWidth() + i13;
                    int measuredHeight2 = i14 + view.getMeasuredHeight();
                    if (measuredHeight2 > height) {
                        break;
                    }
                    view.layout(i13, i14, measuredWidth2, measuredHeight2);
                    i9 += view.getMeasuredWidth() + marginLayoutParams2.leftMargin + marginLayoutParams2.rightMargin;
                }
            }
            i9 = getPaddingLeft();
            paddingTop += intValue;
        }
    }

    public void startAnimationSet(final View view) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.4f);
        ofFloat.setDuration(600L);
        ofFloat.start();
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.4f, 1.0f);
        ofFloat2.setDuration(600L);
        ofFloat2.setStartDelay(1100L);
        ofFloat2.start();
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.common.view.base.-$$Lambda$FluidLayout$5UIBPbp4dmVMWm5TbpAAcaYjZck
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FluidLayout.this.b(view, valueAnimator);
            }
        });
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.common.view.base.-$$Lambda$FluidLayout$AYIItL_Ukfrlg_nmlHR9jnrW03A
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FluidLayout.this.a(view, valueAnimator);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view, ValueAnimator valueAnimator) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            if (childAt != view) {
                childAt.setAlpha(floatValue);
            }
            if (floatValue == 1.0f) {
                childAt.setClickable(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view, ValueAnimator valueAnimator) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            if (childAt != view) {
                childAt.setAlpha(floatValue);
            }
            if (floatValue == 1.0f) {
                childAt.setClickable(true);
            }
        }
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new ViewGroup.MarginLayoutParams(layoutParams);
    }
}
