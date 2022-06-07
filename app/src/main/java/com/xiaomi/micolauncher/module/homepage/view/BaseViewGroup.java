package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class BaseViewGroup extends FrameLayout {
    private float a;
    private float b;
    private float c;
    private float d;
    private Paint e;
    private Paint f;
    private Path g;
    protected float radius;

    protected boolean needClipRound() {
        return true;
    }

    public BaseViewGroup(Context context) {
        this(context, null);
    }

    public BaseViewGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BaseViewGroup);
        this.radius = obtainStyledAttributes.getDimension(2, context.getResources().getDimensionPixelSize(R.dimen.corner_radius));
        this.a = obtainStyledAttributes.getDimension(3, this.radius);
        this.b = obtainStyledAttributes.getDimension(4, this.radius);
        this.c = obtainStyledAttributes.getDimension(0, this.radius);
        this.d = obtainStyledAttributes.getDimension(1, this.radius);
        obtainStyledAttributes.recycle();
        this.e = new Paint();
        this.e.setColor(-1);
        this.e.setAntiAlias(true);
        this.e.setStyle(Paint.Style.FILL);
        this.e.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        this.f = new Paint();
        this.f.setXfermode(null);
        this.g = new Path();
    }
}
