package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: classes3.dex */
public class CustomImageView extends AppCompatImageView {
    private int a;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setImageHeight(int i) {
        this.a = i;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.setBounds(12, 12, getWidth() - 12, this.a + 12);
            drawable.draw(canvas);
        }
    }
}
