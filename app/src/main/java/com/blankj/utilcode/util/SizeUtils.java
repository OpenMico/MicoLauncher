package com.blankj.utilcode.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public final class SizeUtils {

    /* loaded from: classes.dex */
    public interface OnGetSizeListener {
        void onGetSize(View view);
    }

    private SizeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int dp2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dp(float f) {
        return (int) ((f / Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int px2sp(float f) {
        return (int) ((f / Resources.getSystem().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static float applyDimension(float f, int i) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        switch (i) {
            case 0:
                return f;
            case 1:
                return f * displayMetrics.density;
            case 2:
                return f * displayMetrics.scaledDensity;
            case 3:
                return f * displayMetrics.xdpi * 0.013888889f;
            case 4:
                return f * displayMetrics.xdpi;
            case 5:
                return f * displayMetrics.xdpi * 0.03937008f;
            default:
                return 0.0f;
        }
    }

    public static void forceGetViewSize(final View view, final OnGetSizeListener onGetSizeListener) {
        view.post(new Runnable() { // from class: com.blankj.utilcode.util.SizeUtils.1
            @Override // java.lang.Runnable
            public void run() {
                OnGetSizeListener onGetSizeListener2 = OnGetSizeListener.this;
                if (onGetSizeListener2 != null) {
                    onGetSizeListener2.onGetSize(view);
                }
            }
        });
    }

    public static int getMeasuredWidth(View view) {
        return measureView(view)[0];
    }

    public static int getMeasuredHeight(View view) {
        return measureView(view)[1];
    }

    public static int[] measureView(View view) {
        int i;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
        int i2 = layoutParams.height;
        if (i2 > 0) {
            i = View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
        } else {
            i = View.MeasureSpec.makeMeasureSpec(0, 0);
        }
        view.measure(childMeasureSpec, i);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }
}
