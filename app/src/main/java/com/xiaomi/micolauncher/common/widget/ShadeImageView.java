package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class ShadeImageView extends AppCompatImageView {
    private Drawable mForeground;

    public ShadeImageView(Context context) {
        this(context, null);
    }

    public ShadeImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int color = getResources().getColor(R.color.blur_foreground);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ShadeImageView);
            color = obtainStyledAttributes.getColor(0, color);
            obtainStyledAttributes.recycle();
        }
        this.mForeground = new ColorDrawable(color);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = this.mForeground;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getHeight());
            this.mForeground.draw(canvas);
        }
    }
}
