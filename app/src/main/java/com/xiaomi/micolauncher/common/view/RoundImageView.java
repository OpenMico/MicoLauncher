package com.xiaomi.micolauncher.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class RoundImageView extends AppCompatImageView {
    private float a;
    private float b;
    private int c;
    private boolean d;
    private boolean e;
    private int f;
    private int g;
    private int h;
    private int i;
    private Paint j;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RoundImageView, i, 0);
        if (obtainStyledAttributes != null) {
            this.a = obtainStyledAttributes.getDimension(1, 0.0f);
            this.b = obtainStyledAttributes.getDimension(3, 0.0f);
            this.d = obtainStyledAttributes.getBoolean(0, false);
            this.e = obtainStyledAttributes.getBoolean(2, false);
            this.c = obtainStyledAttributes.getInteger(4, -1776412);
            obtainStyledAttributes.recycle();
            return;
        }
        this.a = 0.0f;
        this.b = 0.0f;
        this.d = false;
        this.e = false;
        this.c = -1776412;
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        this.f = (canvas.getWidth() - getPaddingLeft()) - getPaddingRight();
        this.g = (canvas.getHeight() - getPaddingTop()) - getPaddingBottom();
        if (!this.e) {
            this.b = 0.0f;
        }
        int i = this.f;
        float f = this.b;
        this.h = i - (((int) f) * 2);
        this.i = this.g - (((int) f) * 2);
        if (this.h > 0 && this.i > 0) {
            Bitmap a = a(getDrawable());
            if (a == null) {
                L.base.e("RoundImageView#onDraw  drawableToBitmap bitmap is null or recycled!");
                return;
            }
            Bitmap a2 = a(a, this.h, this.i);
            a();
            if (this.d) {
                canvas.drawBitmap(b(a2), getPaddingLeft(), getPaddingTop(), (Paint) null);
            } else {
                canvas.drawBitmap(a(a2), getPaddingLeft(), getPaddingTop(), (Paint) null);
            }
        }
    }

    private void a() {
        this.j = new Paint();
        this.j.setAntiAlias(true);
    }

    public void setRadius(float f) {
        this.a = f;
    }

    private Bitmap a(Bitmap bitmap) {
        if (bitmap != null) {
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Bitmap createBitmap = Bitmap.createBitmap(this.h, this.i, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            this.j.setShader(bitmapShader);
            RectF rectF = new RectF(0.0f, 0.0f, this.h, this.i);
            float f = this.a;
            canvas.drawRoundRect(rectF, f, f, this.j);
            if (!this.e) {
                return createBitmap;
            }
            this.j.setShader(null);
            this.j.setColor(this.c);
            this.j.setShadowLayer(this.b, 1.0f, 1.0f, this.c);
            Bitmap createBitmap2 = Bitmap.createBitmap(this.f, this.g, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap2);
            float f2 = this.b;
            RectF rectF2 = new RectF(f2, f2, this.f - f2, this.g - f2);
            float f3 = this.a;
            canvas2.drawRoundRect(rectF2, f3, f3, this.j);
            this.j.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            this.j.setShadowLayer(0.0f, 0.0f, 0.0f, 16777215);
            float f4 = this.b;
            canvas2.drawBitmap(createBitmap, f4, f4, this.j);
            return createBitmap2;
        }
        throw new NullPointerException("Bitmap can't be null");
    }

    private Bitmap b(Bitmap bitmap) {
        if (bitmap != null) {
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Bitmap createBitmap = Bitmap.createBitmap(this.h, this.i, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            this.j.setShader(bitmapShader);
            int i = this.h;
            canvas.drawCircle(i / 2, i / 2, Math.min(i, this.i) / 2, this.j);
            if (!this.e) {
                return createBitmap;
            }
            this.j.setShader(null);
            this.j.setColor(this.c);
            this.j.setShadowLayer(this.b, 1.0f, 1.0f, this.c);
            Bitmap createBitmap2 = Bitmap.createBitmap(this.f, this.g, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap2);
            canvas2.drawCircle(this.f / 2, this.g / 2, Math.min(this.h, this.i) / 2, this.j);
            this.j.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            this.j.setShadowLayer(0.0f, 0.0f, 0.0f, 16777215);
            float f = this.b;
            canvas2.drawBitmap(createBitmap, f, f, this.j);
            return createBitmap2;
        }
        throw new NullPointerException("Bitmap can't be null");
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
    }

    private Bitmap a(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if (bitmap == null || bitmap.isRecycled()) {
                return null;
            }
            return bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicHeight(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    private Bitmap a(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(i / width, i2 / height);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}
