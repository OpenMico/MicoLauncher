package com.xiaomi.mico.base.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/* loaded from: classes3.dex */
public class AndroidBug5497Workaround {
    private int a;
    private int b;
    private View c;
    private int d;
    private FrameLayout.LayoutParams e;

    public static void assistActivity(Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private AndroidBug5497Workaround(Activity activity) {
        this.a = 0;
        this.b = 0;
        this.c = ((FrameLayout) activity.findViewById(16908290)).getChildAt(0);
        this.c.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.xiaomi.mico.base.utils.AndroidBug5497Workaround.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                AndroidBug5497Workaround.this.a();
            }
        });
        this.e = (FrameLayout.LayoutParams) this.c.getLayoutParams();
        int identifier = this.c.getContext().getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            this.a = activity.getResources().getDimensionPixelSize(identifier);
        }
        int identifier2 = this.c.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier2 > 0) {
            this.b = activity.getResources().getDimensionPixelSize(identifier2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        int b = b();
        if (b != this.d) {
            int height = this.c.getRootView().getHeight();
            int i = height - b;
            if (i > height / 4) {
                this.e.height = (height - i) + this.b;
            } else {
                this.e.height = this.b + b;
            }
            this.c.requestLayout();
            this.d = b;
        }
    }

    private int b() {
        Rect rect = new Rect();
        this.c.getWindowVisibleDisplayFrame(rect);
        return rect.bottom - rect.top;
    }
}
