package com.google.android.material.circularreveal;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.math.MathUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class CircularRevealHelper {
    public static final int BITMAP_SHADER = 0;
    public static final int CLIP_PATH = 1;
    public static final int REVEAL_ANIMATOR = 2;
    public static final int STRATEGY;
    private final Delegate a;
    @NonNull
    private final View b;
    @NonNull
    private final Path c = new Path();
    @NonNull
    private final Paint d = new Paint(7);
    @NonNull
    private final Paint e = new Paint(1);
    @Nullable
    private CircularRevealWidget.RevealInfo f;
    @Nullable
    private Drawable g;
    private boolean h;
    private boolean i;

    /* loaded from: classes2.dex */
    public interface Delegate {
        void actualDraw(Canvas canvas);

        boolean actualIsOpaque();
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Strategy {
    }

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            STRATEGY = 2;
        } else if (Build.VERSION.SDK_INT >= 18) {
            STRATEGY = 1;
        } else {
            STRATEGY = 0;
        }
    }

    public CircularRevealHelper(Delegate delegate) {
        this.a = delegate;
        this.b = (View) delegate;
        this.b.setWillNotDraw(false);
        this.e.setColor(0);
    }

    public void buildCircularRevealCache() {
        if (STRATEGY == 0) {
            this.h = true;
            this.i = false;
            this.b.buildDrawingCache();
            Bitmap drawingCache = this.b.getDrawingCache();
            if (!(drawingCache != null || this.b.getWidth() == 0 || this.b.getHeight() == 0)) {
                drawingCache = Bitmap.createBitmap(this.b.getWidth(), this.b.getHeight(), Bitmap.Config.ARGB_8888);
                this.b.draw(new Canvas(drawingCache));
            }
            if (drawingCache != null) {
                this.d.setShader(new BitmapShader(drawingCache, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            }
            this.h = false;
            this.i = true;
        }
    }

    public void destroyCircularRevealCache() {
        if (STRATEGY == 0) {
            this.i = false;
            this.b.destroyDrawingCache();
            this.d.setShader(null);
            this.b.invalidate();
        }
    }

    public void setRevealInfo(@Nullable CircularRevealWidget.RevealInfo revealInfo) {
        if (revealInfo == null) {
            this.f = null;
        } else {
            CircularRevealWidget.RevealInfo revealInfo2 = this.f;
            if (revealInfo2 == null) {
                this.f = new CircularRevealWidget.RevealInfo(revealInfo);
            } else {
                revealInfo2.set(revealInfo);
            }
            if (MathUtils.geq(revealInfo.radius, a(revealInfo), 1.0E-4f)) {
                this.f.radius = Float.MAX_VALUE;
            }
        }
        a();
    }

    @Nullable
    public CircularRevealWidget.RevealInfo getRevealInfo() {
        CircularRevealWidget.RevealInfo revealInfo = this.f;
        if (revealInfo == null) {
            return null;
        }
        CircularRevealWidget.RevealInfo revealInfo2 = new CircularRevealWidget.RevealInfo(revealInfo);
        if (revealInfo2.isInvalid()) {
            revealInfo2.radius = a(revealInfo2);
        }
        return revealInfo2;
    }

    public void setCircularRevealScrimColor(@ColorInt int i) {
        this.e.setColor(i);
        this.b.invalidate();
    }

    @ColorInt
    public int getCircularRevealScrimColor() {
        return this.e.getColor();
    }

    @Nullable
    public Drawable getCircularRevealOverlayDrawable() {
        return this.g;
    }

    public void setCircularRevealOverlayDrawable(@Nullable Drawable drawable) {
        this.g = drawable;
        this.b.invalidate();
    }

    private void a() {
        if (STRATEGY == 1) {
            this.c.rewind();
            CircularRevealWidget.RevealInfo revealInfo = this.f;
            if (revealInfo != null) {
                this.c.addCircle(revealInfo.centerX, this.f.centerY, this.f.radius, Path.Direction.CW);
            }
        }
        this.b.invalidate();
    }

    private float a(@NonNull CircularRevealWidget.RevealInfo revealInfo) {
        return MathUtils.distanceToFurthestCorner(revealInfo.centerX, revealInfo.centerY, 0.0f, 0.0f, this.b.getWidth(), this.b.getHeight());
    }

    public void draw(@NonNull Canvas canvas) {
        if (b()) {
            switch (STRATEGY) {
                case 0:
                    canvas.drawCircle(this.f.centerX, this.f.centerY, this.f.radius, this.d);
                    if (c()) {
                        canvas.drawCircle(this.f.centerX, this.f.centerY, this.f.radius, this.e);
                        break;
                    }
                    break;
                case 1:
                    int save = canvas.save();
                    canvas.clipPath(this.c);
                    this.a.actualDraw(canvas);
                    if (c()) {
                        canvas.drawRect(0.0f, 0.0f, this.b.getWidth(), this.b.getHeight(), this.e);
                    }
                    canvas.restoreToCount(save);
                    break;
                case 2:
                    this.a.actualDraw(canvas);
                    if (c()) {
                        canvas.drawRect(0.0f, 0.0f, this.b.getWidth(), this.b.getHeight(), this.e);
                        break;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unsupported strategy " + STRATEGY);
            }
        } else {
            this.a.actualDraw(canvas);
            if (c()) {
                canvas.drawRect(0.0f, 0.0f, this.b.getWidth(), this.b.getHeight(), this.e);
            }
        }
        a(canvas);
    }

    private void a(@NonNull Canvas canvas) {
        if (d()) {
            Rect bounds = this.g.getBounds();
            float width = this.f.centerX - (bounds.width() / 2.0f);
            float height = this.f.centerY - (bounds.height() / 2.0f);
            canvas.translate(width, height);
            this.g.draw(canvas);
            canvas.translate(-width, -height);
        }
    }

    public boolean isOpaque() {
        return this.a.actualIsOpaque() && !b();
    }

    private boolean b() {
        CircularRevealWidget.RevealInfo revealInfo = this.f;
        boolean z = revealInfo == null || revealInfo.isInvalid();
        return STRATEGY == 0 ? !z && this.i : !z;
    }

    private boolean c() {
        return !this.h && Color.alpha(this.e.getColor()) != 0;
    }

    private boolean d() {
        return (this.h || this.g == null || this.f == null) ? false : true;
    }
}
