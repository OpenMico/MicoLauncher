package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class AspectRatioFrameLayout extends FrameLayout {
    public static final int RESIZE_MODE_FILL = 3;
    public static final int RESIZE_MODE_FIT = 0;
    public static final int RESIZE_MODE_FIXED_HEIGHT = 2;
    public static final int RESIZE_MODE_FIXED_WIDTH = 1;
    public static final int RESIZE_MODE_ZOOM = 4;
    private final a a;
    @Nullable
    private AspectRatioListener b;
    private float c;
    private int d;

    /* loaded from: classes2.dex */
    public interface AspectRatioListener {
        void onAspectRatioUpdated(float f, float f2, boolean z);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface ResizeMode {
    }

    public AspectRatioFrameLayout(Context context) {
        this(context, null);
    }

    public AspectRatioFrameLayout(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.d = 0;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.AspectRatioFrameLayout, 0, 0);
            try {
                this.d = obtainStyledAttributes.getInt(R.styleable.AspectRatioFrameLayout_resize_mode, 0);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        this.a = new a();
    }

    public void setAspectRatio(float f) {
        if (this.c != f) {
            this.c = f;
            requestLayout();
        }
    }

    public void setAspectRatioListener(@Nullable AspectRatioListener aspectRatioListener) {
        this.b = aspectRatioListener;
    }

    public int getResizeMode() {
        return this.d;
    }

    public void setResizeMode(int i) {
        if (this.d != i) {
            this.d = i;
            requestLayout();
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.c > 0.0f) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f = measuredWidth;
            float f2 = measuredHeight;
            float f3 = f / f2;
            float f4 = (this.c / f3) - 1.0f;
            if (Math.abs(f4) <= 0.01f) {
                this.a.a(this.c, f3, false);
                return;
            }
            int i3 = this.d;
            if (i3 != 4) {
                switch (i3) {
                    case 0:
                        if (f4 <= 0.0f) {
                            measuredWidth = (int) (f2 * this.c);
                            break;
                        } else {
                            measuredHeight = (int) (f / this.c);
                            break;
                        }
                    case 1:
                        measuredHeight = (int) (f / this.c);
                        break;
                    case 2:
                        measuredWidth = (int) (f2 * this.c);
                        break;
                }
            } else if (f4 > 0.0f) {
                measuredWidth = (int) (f2 * this.c);
            } else {
                measuredHeight = (int) (f / this.c);
            }
            this.a.a(this.c, f3, true);
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
        }
    }

    /* loaded from: classes2.dex */
    private final class a implements Runnable {
        private float b;
        private float c;
        private boolean d;
        private boolean e;

        private a() {
        }

        public void a(float f, float f2, boolean z) {
            this.b = f;
            this.c = f2;
            this.d = z;
            if (!this.e) {
                this.e = true;
                AspectRatioFrameLayout.this.post(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.e = false;
            if (AspectRatioFrameLayout.this.b != null) {
                AspectRatioFrameLayout.this.b.onAspectRatioUpdated(this.b, this.c, this.d);
            }
        }
    }
}
