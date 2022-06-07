package com.xiaomi.micolauncher.module.battery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class BatteryView extends View {
    private float a;
    private Paint b = new Paint();
    private Paint c = new Paint();
    private Paint d = new Paint();
    private Paint e = new Paint();
    private Paint f = new Paint();
    private RectF g = new RectF();
    private int h;
    private a i;
    private float j;
    private float k;
    private Bitmap l;
    private Bitmap m;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public interface b {
        void draw(Canvas canvas, float f, float f2, float f3, float f4);
    }

    public BatteryView(Context context) {
        super(context);
        a(context);
    }

    public BatteryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public BatteryView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    public BatteryView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        a(context);
    }

    private void a(Context context) {
        this.j = context.getResources().getDimension(R.dimen.battery_border_corner_radius);
        this.k = context.getResources().getDimension(R.dimen.battery_head_corner_radius);
        this.a = context.getResources().getDimension(R.dimen.battery_font_size);
        this.i = new a(context);
        this.b.setStrokeWidth(context.getResources().getDimension(R.dimen.battery_border_thickness));
        this.b.setStyle(Paint.Style.STROKE);
        this.b.setAntiAlias(true);
        this.c.setStyle(Paint.Style.FILL);
        this.c.setAntiAlias(true);
        this.d.setStyle(Paint.Style.FILL);
        this.d.setAntiAlias(true);
        this.f.setTextSize(this.a);
        this.f.setAntiAlias(true);
        this.e.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.e.setStyle(Paint.Style.FILL);
    }

    public void setPaintColorOfGraphics(int i, int i2) {
        this.d.setColor(i);
        this.b.setColor(i2);
        this.c.setColor(i2);
        this.e.setColor(i);
    }

    public void setPaintColorOfText(int i) {
        this.f.setColor(i);
    }

    public int getProgress() {
        return this.h;
    }

    public void setProgress(int i) {
        this.h = i;
    }

    private void a(Canvas canvas) {
        this.i.f();
        Bitmap a2 = a(Math.max(this.h, 12) / 100.0f, this.d);
        Bitmap a3 = a();
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null);
        canvas.drawBitmap(a3, 0.0f, 0.0f, this.d);
        canvas.drawBitmap(a2, 0.0f, 0.0f, this.e);
        canvas.restoreToCount(saveLayer);
        float f = this.i.b;
        float f2 = this.i.b;
        float b2 = this.i.b();
        float c = this.i.c();
        float f3 = this.j;
        canvas.drawRoundRect(f, f2, b2, c, f3, f3, this.b);
        float e = this.i.e();
        float width = (getWidth() - e) - (this.i.a() * 2.0f);
        float dimension = getResources().getDimension(R.dimen.battery_head_height);
        float height = (getHeight() - dimension) / 2.0f;
        float f4 = this.k;
        canvas.drawRoundRect(width, height, width + e, height + dimension, f4, f4, this.c);
        String valueOf = String.valueOf(this.h);
        float measureText = this.f.measureText(valueOf);
        float f5 = this.a;
        canvas.drawText(valueOf, (this.i.d() - measureText) / 2.0f, (((getHeight() - f5) / 2.0f) + f5) - 2.0f, this.f);
    }

    private Bitmap a() {
        this.l = a(this.l);
        return a(new b() { // from class: com.xiaomi.micolauncher.module.battery.-$$Lambda$BatteryView$RW8dZKCJY7yEPauwTzCA8TqRA1g
            @Override // com.xiaomi.micolauncher.module.battery.BatteryView.b
            public final void draw(Canvas canvas, float f, float f2, float f3, float f4) {
                BatteryView.this.a(canvas, f, f2, f3, f4);
            }
        }, this.l);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Canvas canvas, float f, float f2, float f3, float f4) {
        this.g.set(f, f2, f3 + f, f4 + f2);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        RectF rectF = this.g;
        float f5 = this.j;
        canvas.drawRoundRect(rectF, f5, f5, this.d);
        RectF rectF2 = this.g;
        float f6 = this.j;
        canvas.drawRoundRect(rectF2, f6, f6, this.b);
    }

    private Bitmap a(final float f, final Paint paint) {
        this.m = a(this.m);
        return a(new b() { // from class: com.xiaomi.micolauncher.module.battery.-$$Lambda$BatteryView$GA0eXNJZ-CVEUR6ZdDVwIl6j_9c
            @Override // com.xiaomi.micolauncher.module.battery.BatteryView.b
            public final void draw(Canvas canvas, float f2, float f3, float f4, float f5) {
                BatteryView.this.a(f, paint, canvas, f2, f3, f4, f5);
            }
        }, this.m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(float f, Paint paint, Canvas canvas, float f2, float f3, float f4, float f5) {
        this.g.set(f2, f3, (f4 * f) + f2, f5 + f3);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawRect(this.g, paint);
    }

    private Bitmap a(Bitmap bitmap) {
        int width = getWidth();
        int height = getHeight();
        return (bitmap != null && bitmap.getWidth() == width && bitmap.getHeight() == height) ? bitmap : Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    private Bitmap a(b bVar, Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        this.i.f();
        float a2 = this.i.a();
        bVar.draw(canvas, a2, a2, this.i.b() - a2, this.i.c() - a2);
        return bitmap;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        a(canvas);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a {
        private float b;
        private float c;
        private float d;
        private float e;
        private float f;
        private float g;
        private final Context h;
        private final float i;

        a(Context context) {
            this.h = context;
            this.i = context.getResources().getDimensionPixelSize(R.dimen.battery_border_thickness);
        }

        float a() {
            return this.b;
        }

        float b() {
            return this.c;
        }

        float c() {
            return this.d;
        }

        float d() {
            return this.f;
        }

        float e() {
            return this.e;
        }

        a f() {
            this.e = this.h.getResources().getDimension(R.dimen.battery_head_width);
            this.b = this.i / 2.0f;
            this.f = (BatteryView.this.getWidth() - (this.e * 2.0f)) - this.b;
            this.g = BatteryView.this.getHeight();
            float f = this.f;
            float f2 = this.b;
            this.c = f - f2;
            this.d = this.g - f2;
            return this;
        }
    }
}
