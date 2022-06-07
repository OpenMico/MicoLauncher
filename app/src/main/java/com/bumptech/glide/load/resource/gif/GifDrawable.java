package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.gif.GifFrameLoader;
import com.bumptech.glide.util.Preconditions;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class GifDrawable extends Drawable implements Animatable, Animatable2Compat, GifFrameLoader.FrameCallback {
    public static final int LOOP_FOREVER = -1;
    public static final int LOOP_INTRINSIC = 0;
    private final a a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    private int f;
    private int g;
    private boolean h;
    private Paint i;
    private Rect j;
    private List<Animatable2Compat.AnimationCallback> k;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Deprecated
    public GifDrawable(Context context, GifDecoder gifDecoder, BitmapPool bitmapPool, Transformation<Bitmap> transformation, int i, int i2, Bitmap bitmap) {
        this(context, gifDecoder, transformation, i, i2, bitmap);
    }

    public GifDrawable(Context context, GifDecoder gifDecoder, Transformation<Bitmap> transformation, int i, int i2, Bitmap bitmap) {
        this(new a(new GifFrameLoader(Glide.get(context), gifDecoder, i, i2, transformation, bitmap)));
    }

    GifDrawable(a aVar) {
        this.e = true;
        this.g = -1;
        this.a = (a) Preconditions.checkNotNull(aVar);
    }

    public int getSize() {
        return this.a.a.e();
    }

    public Bitmap getFirstFrame() {
        return this.a.a.b();
    }

    public void setFrameTransformation(Transformation<Bitmap> transformation, Bitmap bitmap) {
        this.a.a.a(transformation, bitmap);
    }

    public Transformation<Bitmap> getFrameTransformation() {
        return this.a.a.a();
    }

    public ByteBuffer getBuffer() {
        return this.a.a.g();
    }

    public int getFrameCount() {
        return this.a.a.h();
    }

    public int getFrameIndex() {
        return this.a.a.f();
    }

    private void a() {
        this.f = 0;
    }

    public void startFromFirstFrame() {
        Preconditions.checkArgument(!this.b, "You cannot restart a currently running animation.");
        this.a.a.l();
        start();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.c = true;
        a();
        if (this.e) {
            b();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.c = false;
        c();
    }

    private void b() {
        Preconditions.checkArgument(!this.d, "You cannot start a recycled Drawable. Ensure thatyou clear any references to the Drawable when clearing the corresponding request.");
        if (this.a.a.h() == 1) {
            invalidateSelf();
        } else if (!this.b) {
            this.b = true;
            this.a.a.a(this);
            invalidateSelf();
        }
    }

    private void c() {
        this.b = false;
        this.a.a.b(this);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        Preconditions.checkArgument(!this.d, "Cannot change the visibility of a recycled resource. Ensure that you unset the Drawable from your View before changing the View's visibility.");
        this.e = z;
        if (!z) {
            c();
        } else if (this.c) {
            b();
        }
        return super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.a.a.c();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.a.a.d();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.b;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.h = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        if (!this.d) {
            if (this.h) {
                Gravity.apply(119, getIntrinsicWidth(), getIntrinsicHeight(), getBounds(), d());
                this.h = false;
            }
            canvas.drawBitmap(this.a.a.k(), (Rect) null, d(), e());
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        e().setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        e().setColorFilter(colorFilter);
    }

    private Rect d() {
        if (this.j == null) {
            this.j = new Rect();
        }
        return this.j;
    }

    private Paint e() {
        if (this.i == null) {
            this.i = new Paint(2);
        }
        return this.i;
    }

    private Drawable.Callback f() {
        Drawable.Callback callback = getCallback();
        while (callback instanceof Drawable) {
            callback = ((Drawable) callback).getCallback();
        }
        return callback;
    }

    @Override // com.bumptech.glide.load.resource.gif.GifFrameLoader.FrameCallback
    public void onFrameReady() {
        if (f() == null) {
            stop();
            invalidateSelf();
            return;
        }
        invalidateSelf();
        if (getFrameIndex() == getFrameCount() - 1) {
            this.f++;
        }
        int i = this.g;
        if (i != -1 && this.f >= i) {
            g();
            stop();
        }
    }

    private void g() {
        List<Animatable2Compat.AnimationCallback> list = this.k;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                this.k.get(i).onAnimationEnd(this);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.a;
    }

    public void recycle() {
        this.d = true;
        this.a.a.j();
    }

    public void setLoopCount(int i) {
        if (i <= 0 && i != -1 && i != 0) {
            throw new IllegalArgumentException("Loop count must be greater than 0, or equal to GlideDrawable.LOOP_FOREVER, or equal to GlideDrawable.LOOP_INTRINSIC");
        } else if (i == 0) {
            int i2 = this.a.a.i();
            if (i2 == 0) {
                i2 = -1;
            }
            this.g = i2;
        } else {
            this.g = i;
        }
    }

    @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat
    public void registerAnimationCallback(@NonNull Animatable2Compat.AnimationCallback animationCallback) {
        if (animationCallback != null) {
            if (this.k == null) {
                this.k = new ArrayList();
            }
            this.k.add(animationCallback);
        }
    }

    @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat
    public boolean unregisterAnimationCallback(@NonNull Animatable2Compat.AnimationCallback animationCallback) {
        List<Animatable2Compat.AnimationCallback> list = this.k;
        if (list == null || animationCallback == null) {
            return false;
        }
        return list.remove(animationCallback);
    }

    @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat
    public void clearAnimationCallbacks() {
        List<Animatable2Compat.AnimationCallback> list = this.k;
        if (list != null) {
            list.clear();
        }
    }

    /* loaded from: classes.dex */
    public static final class a extends Drawable.ConstantState {
        @VisibleForTesting
        final GifFrameLoader a;

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        a(GifFrameLoader gifFrameLoader) {
            this.a = gifFrameLoader;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable(Resources resources) {
            return newDrawable();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return new GifDrawable(this);
        }
    }
}
