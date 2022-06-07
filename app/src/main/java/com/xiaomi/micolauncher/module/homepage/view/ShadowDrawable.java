package com.xiaomi.micolauncher.module.homepage.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import androidx.core.view.ViewCompat;
import com.xiaomi.micolauncher.R;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class ShadowDrawable extends Drawable {
    public static final int SHAPE_CIRCLE = 2;
    public static final int SHAPE_ROUND = 1;
    private static HashMap<String, Drawable> j = null;
    private static int k = -1;
    private Paint a;
    private Paint b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int[] h;
    private RectF i;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    private ShadowDrawable(int i, int[] iArr, int i2, int i3, int i4, int i5, int i6) {
        this.d = i;
        this.h = iArr;
        this.e = i2;
        this.c = i4;
        this.f = i5;
        this.g = i6;
        this.a = new Paint();
        this.a.setColor(0);
        this.a.setAntiAlias(true);
        this.a.setShadowLayer(i4, i5, i6, i3);
        this.a.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        this.b = new Paint();
        this.b.setAntiAlias(true);
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        int i5 = this.c;
        int i6 = this.f;
        int i7 = this.g;
        this.i = new RectF((i + i5) - i6, (i2 + i5) - i7, (i3 - i5) - i6, (i4 - i5) - i7);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int[] iArr = this.h;
        if (iArr != null) {
            if (iArr.length == 1) {
                this.b.setColor(iArr[0]);
            } else {
                this.b.setShader(new LinearGradient(this.i.left, this.i.height() / 2.0f, this.i.right, this.i.height() / 2.0f, this.h, (float[]) null, Shader.TileMode.CLAMP));
            }
        }
        if (this.d == 1) {
            RectF rectF = this.i;
            int i = this.e;
            canvas.drawRoundRect(rectF, i, i, this.a);
            return;
        }
        canvas.drawCircle(this.i.centerX(), this.i.centerY(), Math.min(this.i.width(), this.i.height()) / 2.0f, this.a);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.a.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.a.setColorFilter(colorFilter);
    }

    private static void a(View view) {
        if (-1 == k) {
            k = view.getResources().getDimensionPixelOffset(R.dimen.shadow_drawable_padding);
        }
    }

    public static void setShadowDrawable(View view, Drawable drawable) {
        view.setLayerType(1, null);
        ViewCompat.setBackground(view, drawable);
    }

    public static void setShadowDrawable(View view, int i, int i2, int i3, int i4, int i5) {
        ShadowDrawable builder = new Builder().setShapeRadius(i).setShadowColor(i2).setShadowRadius(i3).setOffsetX(i4).setOffsetY(i5).builder();
        view.setLayerType(1, null);
        ViewCompat.setBackground(view, builder);
    }

    public static void setShadowDrawable(View view, String str, int i, int i2, int i3, int i4, int i5, int i6) {
        ShadowDrawable builder = new Builder().setBgColor(i).setShapeRadius(i2).setShadowColor(i3).setShadowRadius(i4).setOffsetX(i5).setOffsetY(i6).builder(str);
        view.setLayerType(1, null);
        ViewCompat.setBackground(view, builder);
    }

    public static void setVideoViewBg(View view, String str, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        if (j == null) {
            j = new HashMap<>();
        }
        Drawable drawable = j.get(str);
        if (drawable == null) {
            ShadowDrawable builder = new Builder().setBgColor(i).setShapeRadius(i2).setShadowColor(i3).setShadowRadius(i4).setOffsetX(i5).setOffsetY(i6).builder(str);
            Bitmap createBitmap = Bitmap.createBitmap(i9, i10, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            builder.setBounds(0, 0, i9, i10);
            builder.draw(canvas);
            Drawable drawable2 = view.getResources().getDrawable(i8);
            a(view);
            int i11 = k;
            drawable2.setBounds(i11, i11, drawable2.getIntrinsicWidth() + k, drawable2.getIntrinsicHeight() + k);
            drawable2.draw(canvas);
            drawable = new BitmapDrawable(view.getResources(), createBitmap);
            j.put(str, drawable);
        }
        view.setLayerType(2, null);
        ViewCompat.setBackground(view, drawable);
    }

    public static void setShadowDrawable(View view, String str, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        LayerDrawable layerDrawable;
        if (j == null) {
            j = new HashMap<>();
        }
        Drawable drawable = j.get(str);
        LayerDrawable layerDrawable2 = drawable;
        layerDrawable2 = drawable;
        layerDrawable2 = drawable;
        if (drawable == null && i8 > 0 && i9 > 0) {
            ShadowDrawable builder = (i3 == 0 || i == 0) ? null : new Builder().setBgColor(i).setShapeRadius(i2).setShadowColor(i3).setShadowRadius(i4).setOffsetX(i5).setOffsetY(i6).builder(str);
            Drawable drawable2 = view.getResources().getDrawable(i7);
            a(view);
            if (builder != null) {
                LayerDrawable layerDrawable3 = new LayerDrawable(new Drawable[]{builder, drawable2});
                int i10 = k;
                layerDrawable3.setLayerInset(1, i10, i10, i10, i10);
                layerDrawable = layerDrawable3;
            } else {
                LayerDrawable layerDrawable4 = new LayerDrawable(new Drawable[]{drawable2});
                int i11 = k;
                layerDrawable4.setLayerInset(0, i11, i11, i11, i11);
                layerDrawable = layerDrawable4;
            }
            layerDrawable.setBounds(0, 0, i8, i9);
            j.put(str, layerDrawable);
            layerDrawable2 = layerDrawable;
        }
        view.setLayerType(2, null);
        ViewCompat.setBackground(view, layerDrawable2);
    }

    public static void setShadowDrawable(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        ShadowDrawable builder = new Builder().setShape(i).setBgColor(i2).setShapeRadius(i3).setShadowColor(i4).setShadowRadius(i5).setOffsetX(i6).setOffsetY(i7).builder();
        view.setLayerType(1, null);
        ViewCompat.setBackground(view, builder);
    }

    public static void setShadowDrawable(View view, int[] iArr, int i, int i2, int i3, int i4, int i5) {
        ShadowDrawable builder = new Builder().setBgColor(iArr).setShapeRadius(i).setShadowColor(i2).setShadowRadius(i3).setOffsetX(i4).setOffsetY(i5).builder();
        view.setLayerType(1, null);
        ViewCompat.setBackground(view, builder);
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private HashMap<String, ShadowDrawable> h = new HashMap<>();
        private int a = 1;
        private int b = 12;
        private int c = Color.parseColor("#4d000000");
        private int d = 18;
        private int e = 0;
        private int f = 0;
        private int[] g = new int[1];

        public Builder() {
            this.g[0] = 0;
        }

        public Builder setShape(int i) {
            this.a = i;
            return this;
        }

        public Builder setShapeRadius(int i) {
            this.b = i;
            return this;
        }

        public Builder setShadowColor(int i) {
            this.c = i;
            return this;
        }

        public Builder setShadowRadius(int i) {
            this.d = i;
            return this;
        }

        public Builder setOffsetX(int i) {
            this.e = i;
            return this;
        }

        public Builder setOffsetY(int i) {
            this.f = i;
            return this;
        }

        public Builder setBgColor(int i) {
            this.g[0] = i;
            return this;
        }

        public Builder setBgColor(int[] iArr) {
            this.g = iArr;
            return this;
        }

        public ShadowDrawable builder() {
            return new ShadowDrawable(this.a, this.g, this.b, this.c, this.d, this.e, this.f);
        }

        public ShadowDrawable builder(String str) {
            ShadowDrawable shadowDrawable = this.h.get(str);
            return shadowDrawable != null ? shadowDrawable : builder();
        }
    }
}
