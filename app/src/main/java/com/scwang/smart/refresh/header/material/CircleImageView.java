package com.scwang.smart.refresh.header.material;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.widget.ImageView;
import androidx.annotation.ColorInt;

@SuppressLint({"ViewConstructor", "AppCompatCustomView"})
/* loaded from: classes2.dex */
public class CircleImageView extends ImageView {
    protected static final int FILL_SHADOW_COLOR = 1023410176;
    protected static final int KEY_SHADOW_COLOR = 503316480;
    protected static final int SHADOW_ELEVATION = 4;
    protected static final float SHADOW_RADIUS = 3.5f;
    protected static final float X_OFFSET = 0.0f;
    protected static final float Y_OFFSET = 1.75f;
    int a;

    public CircleImageView(Context context, int i) {
        super(context);
        ShapeDrawable shapeDrawable;
        float f = getResources().getDisplayMetrics().density;
        int i2 = (int) (Y_OFFSET * f);
        int i3 = (int) (X_OFFSET * f);
        this.a = (int) (SHADOW_RADIUS * f);
        if (Build.VERSION.SDK_INT >= 21) {
            shapeDrawable = new ShapeDrawable(new OvalShape());
            setElevation(f * 4.0f);
        } else {
            ShapeDrawable shapeDrawable2 = new ShapeDrawable(new a(this.a));
            setLayerType(1, shapeDrawable2.getPaint());
            shapeDrawable2.getPaint().setShadowLayer(this.a, i3, i2, KEY_SHADOW_COLOR);
            int i4 = this.a;
            setPadding(i4, i4, i4, i4);
            shapeDrawable = shapeDrawable2;
        }
        shapeDrawable.getPaint().setColor(i);
        setBackground(shapeDrawable);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (Build.VERSION.SDK_INT < 21) {
            super.setMeasuredDimension(getMeasuredWidth() + (this.a * 2), getMeasuredHeight() + (this.a * 2));
        }
    }

    @Override // android.view.View
    public void setBackgroundColor(@ColorInt int i) {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(i);
        }
    }

    /* loaded from: classes2.dex */
    private class a extends OvalShape {
        private RadialGradient b;
        private Paint c = new Paint();

        a(int i) {
            CircleImageView.this.a = i;
            a((int) super.rect().width());
        }

        @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        protected void onResize(float f, float f2) {
            super.onResize(f, f2);
            a((int) f);
        }

        @Override // android.graphics.drawable.shapes.OvalShape, android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        public void draw(Canvas canvas, Paint paint) {
            CircleImageView circleImageView = CircleImageView.this;
            float width = circleImageView.getWidth() / 2.0f;
            float height = circleImageView.getHeight() / 2.0f;
            canvas.drawCircle(width, height, width, this.c);
            canvas.drawCircle(width, height, width - CircleImageView.this.a, paint);
        }

        private void a(int i) {
            float f = i / 2.0f;
            this.b = new RadialGradient(f, f, CircleImageView.this.a, new int[]{CircleImageView.FILL_SHADOW_COLOR, 0}, (float[]) null, Shader.TileMode.CLAMP);
            this.c.setShader(this.b);
        }
    }
}
