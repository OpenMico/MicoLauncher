package com.xiaomi.mico.base.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.xiaomi.mico.base.R;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class MagicImageView extends AppCompatImageView {
    private static final String m = "MagicImageView";
    int a;
    int b;
    int c;
    int d;
    int e;
    int f;
    int g;
    int h;
    int i;
    int j;
    int k;
    int l;

    public MagicImageView(Context context) {
        this(context, null, 0);
    }

    public MagicImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet, i);
    }

    private void a(Context context, AttributeSet attributeSet, int i) {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(1, null);
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MagicImageView);
        this.a = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.MagicImageView_m_radius, 0);
        this.b = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.MagicImageView_m_left_top_radius, 0);
        this.c = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.MagicImageView_m_right_top_radius, 0);
        this.d = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.MagicImageView_m_right_bottom_radius, 0);
        this.e = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.MagicImageView_m_left_bottom_radius, 0);
        this.f = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.MagicImageView_m_border, 0);
        this.g = obtainStyledAttributes.getColor(R.styleable.MagicImageView_m_border_color, 0);
        this.h = obtainStyledAttributes.getColor(R.styleable.MagicImageView_m_bg_color, 0);
        this.i = obtainStyledAttributes.getColor(R.styleable.MagicImageView_m_gradientBgStartColor, this.h);
        this.j = obtainStyledAttributes.getColor(R.styleable.MagicImageView_m_gradientBgCenterColor, this.h);
        this.k = obtainStyledAttributes.getColor(R.styleable.MagicImageView_m_gradientBgEndColor, this.h);
        a();
        obtainStyledAttributes.recycle();
    }

    private void a() {
        if (this.b == 0) {
            this.b = this.a;
        }
        if (this.c == 0) {
            this.c = this.a;
        }
        if (this.d == 0) {
            this.d = this.a;
        }
        if (this.e == 0) {
            this.e = this.a;
        }
        int i = this.b;
        int i2 = this.c;
        int i3 = this.d;
        int i4 = this.e;
        float[] fArr = {i, i, i2, i2, i3, i3, i4, i4};
        GradientDrawable gradientDrawable = new GradientDrawable();
        int i5 = this.j;
        int i6 = this.h;
        if (!(i5 == i6 && this.k == i6 && this.i == i6) && this.h == 0) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(this.i));
            arrayList.add(Integer.valueOf(this.j));
            arrayList.add(Integer.valueOf(this.k));
            int[] iArr = new int[arrayList.size()];
            for (int i7 = 0; i7 < arrayList.size(); i7++) {
                iArr[i7] = ((Integer) arrayList.get(i7)).intValue();
            }
            gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gradientDrawable.setColors(iArr);
        } else {
            gradientDrawable.setColor(this.h);
        }
        gradientDrawable.setCornerRadii(fArr);
        gradientDrawable.setStroke(this.f, this.g);
        setBackgroundDrawable(gradientDrawable);
    }

    public MagicImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public int getRadius() {
        return this.a;
    }

    public void setRadius(int i) {
        this.a = i;
        a();
    }

    public int getLeftTopRadius() {
        return this.b;
    }

    public void setLeftTopRadius(int i) {
        this.b = i;
        a();
    }

    public int getRightTopRadius() {
        return this.c;
    }

    public void setRightTopRadius(int i) {
        this.c = i;
        a();
    }

    public int getRightBottomRadius() {
        return this.d;
    }

    public void setRightBottomRadius(int i) {
        this.d = i;
        a();
    }

    public int getLeftBottomRadius() {
        return this.e;
    }

    public void setLeftBottomRadius(int i) {
        this.e = i;
        a();
    }

    public int getBorder() {
        return this.f;
    }

    public void setBorder(int i) {
        this.f = i;
        a();
    }

    public int getBorderColor() {
        return this.g;
    }

    public void setBorderColor(int i) {
        this.g = i;
        a();
    }

    public void setBgColor(int i) {
        this.h = i;
        a();
    }

    public int getBgStartColor() {
        return this.i;
    }

    public void setBgStartColor(int i) {
        this.i = i;
        a();
    }

    public int getBgCenterColor() {
        return this.j;
    }

    public void setBgCenterColor(int i) {
        this.j = i;
        a();
    }

    public int getBgEndColor() {
        return this.k;
    }

    public void setBgEndColor(int i) {
        this.k = i;
        a();
    }

    public int getBgGradientOrientation() {
        return this.l;
    }

    public void setBgGradientOrientation(int i) {
        this.l = i;
        a();
    }
}
