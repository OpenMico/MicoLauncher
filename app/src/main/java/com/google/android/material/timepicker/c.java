package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RelativeCornerSize;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RadialViewGroup.java */
/* loaded from: classes2.dex */
public class c extends ConstraintLayout {
    private final Runnable a;
    private int b;
    private MaterialShapeDrawable c;

    public c(@NonNull Context context) {
        this(context, null);
    }

    public c(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public c(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.material_radial_view_group, this);
        ViewCompat.setBackground(this, c());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RadialViewGroup, i, 0);
        this.b = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RadialViewGroup_materialCircleRadius, 0);
        this.a = new Runnable() { // from class: com.google.android.material.timepicker.c.1
            @Override // java.lang.Runnable
            public void run() {
                c.this.a();
            }
        };
        obtainStyledAttributes.recycle();
    }

    private Drawable c() {
        this.c = new MaterialShapeDrawable();
        this.c.setCornerSize(new RelativeCornerSize(0.5f));
        this.c.setFillColor(ColorStateList.valueOf(-1));
        return this.c;
    }

    @Override // android.view.View
    public void setBackgroundColor(@ColorInt int i) {
        this.c.setFillColor(ColorStateList.valueOf(i));
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        if (view.getId() == -1) {
            view.setId(ViewCompat.generateViewId());
        }
        d();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        d();
    }

    private void d() {
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.a);
            handler.post(this.a);
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        a();
    }

    protected void a() {
        int childCount = getChildCount();
        int i = 1;
        for (int i2 = 0; i2 < childCount; i2++) {
            if (a(getChildAt(i2))) {
                i++;
            }
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        float f = 0.0f;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getId() != R.id.circle_center && !a(childAt)) {
                constraintSet.constrainCircle(childAt.getId(), R.id.circle_center, this.b, f);
                f += 360.0f / (childCount - i);
            }
        }
        constraintSet.applyTo(this);
    }

    public void a(@Dimension int i) {
        this.b = i;
        a();
    }

    @Dimension
    public int b() {
        return this.b;
    }

    private static boolean a(View view) {
        return "skip".equals(view.getTag());
    }
}
