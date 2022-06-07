package miuix.animation.styles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import miuix.animation.Folme;
import miuix.animation.property.ViewPropertyExt;
import miuix.animation.utils.CommonUtils;

/* loaded from: classes5.dex */
public class TintDrawable extends Drawable {
    private static final View.OnAttachStateChangeListener a = new View.OnAttachStateChangeListener() { // from class: miuix.animation.styles.TintDrawable.1
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            TintDrawable tintDrawable = TintDrawable.get(view);
            if (tintDrawable != null && Build.VERSION.SDK_INT >= 23) {
                Drawable drawable = tintDrawable.g;
                if (drawable != null) {
                    view.setForeground(drawable);
                }
                tintDrawable.b();
                view.removeOnAttachStateChangeListener(this);
            }
        }
    };
    private View b;
    private Bitmap c;
    private Drawable g;
    private Paint d = new Paint();
    private RectF e = new RectF();
    private Rect f = new Rect();
    private RectF h = new RectF();
    private boolean i = false;
    private float j = 0.0f;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public static TintDrawable get(View view) {
        if (Build.VERSION.SDK_INT < 23) {
            return null;
        }
        Drawable foreground = view.getForeground();
        if (foreground instanceof TintDrawable) {
            return (TintDrawable) foreground;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static TintDrawable a(final View view) {
        TintDrawable tintDrawable = get(view);
        if (tintDrawable != null || Build.VERSION.SDK_INT < 23) {
            return tintDrawable;
        }
        final TintDrawable tintDrawable2 = new TintDrawable();
        tintDrawable2.b = view;
        tintDrawable2.a(view.getForeground());
        view.addOnAttachStateChangeListener(a);
        Folme.post(view, new Runnable() { // from class: miuix.animation.styles.TintDrawable.2
            @Override // java.lang.Runnable
            public void run() {
                view.setForeground(tintDrawable2);
            }
        });
        return tintDrawable2;
    }

    private void a(Drawable drawable) {
        this.g = drawable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(float f) {
        this.i = f != 0.0f;
        this.j = f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        View view = this.b;
        if (view != null) {
            int width = view.getWidth();
            int height = this.b.getHeight();
            if (width == 0 || height == 0) {
                c();
                return;
            }
            a(width, height);
            b(i);
        }
    }

    private void a(int i, int i2) {
        if (Build.VERSION.SDK_INT >= 23) {
            Bitmap bitmap = this.c;
            if (bitmap == null || bitmap.getWidth() != i || this.c.getHeight() != this.b.getHeight()) {
                c();
                this.d.setAntiAlias(true);
                try {
                    this.c = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                } catch (OutOfMemoryError unused) {
                    Log.w(CommonUtils.TAG, "TintDrawable.createBitmap failed, out of memory");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        c();
    }

    private void c() {
        Bitmap bitmap = this.c;
        if (bitmap != null) {
            bitmap.recycle();
            this.c = null;
        }
    }

    private void b(int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            Bitmap bitmap = this.c;
            if (bitmap == null || bitmap.isRecycled()) {
                this.b.setForeground(this.g);
                return;
            }
            try {
                this.c.eraseColor(0);
                Canvas canvas = new Canvas(this.c);
                canvas.translate(-this.b.getScrollX(), -this.b.getScrollY());
                this.b.setForeground(this.g);
                this.b.draw(canvas);
                this.b.setForeground(this);
                if (i == 0) {
                    try {
                        this.d.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Float.MAX_VALUE, 0.0f})));
                        canvas.drawBitmap(this.c, 0.0f, 0.0f, this.d);
                    } catch (Exception unused) {
                        Log.w(CommonUtils.TAG, "the Bitmap empty or Recycled");
                    }
                }
            } catch (Exception e) {
                Log.w(CommonUtils.TAG, "TintDrawable.initBitmap failed, " + e);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        try {
            int scrollX = this.b.getScrollX();
            int scrollY = this.b.getScrollY();
            int width = this.b.getWidth();
            int height = this.b.getHeight();
            this.e.set(scrollX, scrollY, scrollX + width, scrollY + height);
            this.f.set(0, 0, width, height);
            canvas.save();
            int intValue = ViewPropertyExt.FOREGROUND.getIntValue(this.b);
            try {
                canvas.clipRect(this.e);
                canvas.drawColor(0);
                if (this.g != null) {
                    this.g.draw(canvas);
                }
            } catch (RuntimeException e) {
                a(e, canvas);
            }
            if (this.c != null && !this.c.isRecycled()) {
                if (this.i) {
                    this.h.set(this.f);
                    this.d.setColorFilter(new PorterDuffColorFilter(intValue, PorterDuff.Mode.SRC_IN));
                    canvas.drawRoundRect(this.h, this.j, this.j, this.d);
                } else {
                    this.d.setColorFilter(new PorterDuffColorFilter(intValue, PorterDuff.Mode.SRC_IN));
                    canvas.drawBitmap(this.c, this.f, this.e, this.d);
                }
                return;
            }
            if (Build.VERSION.SDK_INT >= 23) {
                this.b.setForeground(this.g);
            }
        } finally {
            canvas.restore();
        }
    }

    private void a(RuntimeException runtimeException, Canvas canvas) {
        if (runtimeException.getMessage() != null && runtimeException.getMessage().length() > 0 && runtimeException.getMessage().contains("Canvas: trying to draw too large")) {
            try {
                this.c = a(this.c);
                canvas.drawBitmap(this.c, this.f, this.e, this.d);
            } catch (Exception e) {
                Log.w(CommonUtils.TAG, "TintDrawable.dealOOM failed, " + e);
            }
        }
    }

    private Bitmap a(Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            return BitmapFactory.decodeStream(a(byteArrayOutputStream), null, options);
        } catch (Exception e) {
            Log.w(CommonUtils.TAG, "TintDrawable.compressImage failed, " + e);
            return null;
        }
    }

    private ByteArrayInputStream a(ByteArrayOutputStream byteArrayOutputStream) throws Exception {
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        b();
        invalidateSelf();
    }
}
