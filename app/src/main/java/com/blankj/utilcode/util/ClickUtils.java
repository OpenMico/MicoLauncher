package com.blankj.utilcode.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import com.blankj.utilcode.util.ShadowUtils;
import com.google.android.exoplayer2.SimpleExoPlayer;

/* loaded from: classes.dex */
public class ClickUtils {
    private static long a;
    private static int b;

    /* loaded from: classes.dex */
    public interface Back2HomeFriendlyListener {
        public static final Back2HomeFriendlyListener DEFAULT = new Back2HomeFriendlyListener() { // from class: com.blankj.utilcode.util.ClickUtils.Back2HomeFriendlyListener.1
            @Override // com.blankj.utilcode.util.ClickUtils.Back2HomeFriendlyListener
            public void show(CharSequence charSequence, long j) {
                b.a(charSequence);
            }

            @Override // com.blankj.utilcode.util.ClickUtils.Back2HomeFriendlyListener
            public void dismiss() {
                b.u();
            }
        };

        void dismiss();

        void show(CharSequence charSequence, long j);
    }

    private ClickUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void applyPressedViewScale(View... viewArr) {
        applyPressedViewScale(viewArr, (float[]) null);
    }

    public static void applyPressedViewScale(View[] viewArr, float[] fArr) {
        if (!(viewArr == null || viewArr.length == 0)) {
            for (int i = 0; i < viewArr.length; i++) {
                if (fArr == null || i >= fArr.length) {
                    applyPressedViewScale(viewArr[i], -0.06f);
                } else {
                    applyPressedViewScale(viewArr[i], fArr[i]);
                }
            }
        }
    }

    public static void applyPressedViewScale(View view, float f) {
        if (view != null) {
            view.setTag(-1, Float.valueOf(f));
            view.setClickable(true);
            view.setOnTouchListener(b.a());
        }
    }

    public static void applyPressedViewAlpha(View... viewArr) {
        applyPressedViewAlpha(viewArr, (float[]) null);
    }

    public static void applyPressedViewAlpha(View[] viewArr, float[] fArr) {
        if (!(viewArr == null || viewArr.length == 0)) {
            for (int i = 0; i < viewArr.length; i++) {
                if (fArr == null || i >= fArr.length) {
                    applyPressedViewAlpha(viewArr[i], 0.8f);
                } else {
                    applyPressedViewAlpha(viewArr[i], fArr[i]);
                }
            }
        }
    }

    public static void applyPressedViewAlpha(View view, float f) {
        if (view != null) {
            view.setTag(-2, Float.valueOf(f));
            view.setTag(-3, Float.valueOf(view.getAlpha()));
            view.setClickable(true);
            view.setOnTouchListener(b.a());
        }
    }

    public static void applyPressedBgAlpha(View view) {
        applyPressedBgAlpha(view, 0.9f);
    }

    public static void applyPressedBgAlpha(View view, float f) {
        a(view, 4, f);
    }

    public static void applyPressedBgDark(View view) {
        applyPressedBgDark(view, 0.9f);
    }

    public static void applyPressedBgDark(View view, float f) {
        a(view, 5, f);
    }

    private static void a(View view, int i, float f) {
        if (view != null) {
            Drawable background = view.getBackground();
            int i2 = -i;
            Object tag = view.getTag(i2);
            if (tag instanceof Drawable) {
                ViewCompat.setBackground(view, (Drawable) tag);
                return;
            }
            Drawable a2 = a(background, i, f);
            ViewCompat.setBackground(view, a2);
            view.setTag(i2, a2);
        }
    }

    private static Drawable a(Drawable drawable, int i, float f) {
        if (drawable == null) {
            drawable = new ColorDrawable(0);
        }
        if (drawable.getConstantState() == null) {
            return drawable;
        }
        Drawable mutate = drawable.getConstantState().newDrawable().mutate();
        if (i == 4) {
            mutate = a(mutate, f);
        } else if (i == 5) {
            mutate = b(mutate, f);
        }
        Drawable a2 = a(drawable.getConstantState().newDrawable().mutate(), 0.5f);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, mutate);
        stateListDrawable.addState(new int[]{-16842910}, a2);
        stateListDrawable.addState(StateSet.WILD_CARD, drawable);
        return stateListDrawable;
    }

    private static Drawable a(Drawable drawable, float f) {
        a aVar = new a(drawable);
        aVar.setAlpha((int) (f * 255.0f));
        return aVar;
    }

    private static Drawable b(Drawable drawable, float f) {
        a aVar = new a(drawable);
        aVar.setColorFilter(a(f));
        return aVar;
    }

    private static ColorMatrixColorFilter a(float f) {
        return new ColorMatrixColorFilter(new ColorMatrix(new float[]{f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 0.0f}));
    }

    public static void applySingleDebouncing(View view, View.OnClickListener onClickListener) {
        applySingleDebouncing(new View[]{view}, onClickListener);
    }

    public static void applySingleDebouncing(View view, @IntRange(from = 0) long j, View.OnClickListener onClickListener) {
        applySingleDebouncing(new View[]{view}, j, onClickListener);
    }

    public static void applySingleDebouncing(View[] viewArr, View.OnClickListener onClickListener) {
        applySingleDebouncing(viewArr, 1000L, onClickListener);
    }

    public static void applySingleDebouncing(View[] viewArr, @IntRange(from = 0) long j, View.OnClickListener onClickListener) {
        a(viewArr, false, j, onClickListener);
    }

    public static void applyGlobalDebouncing(View view, View.OnClickListener onClickListener) {
        applyGlobalDebouncing(new View[]{view}, onClickListener);
    }

    public static void applyGlobalDebouncing(View view, @IntRange(from = 0) long j, View.OnClickListener onClickListener) {
        applyGlobalDebouncing(new View[]{view}, j, onClickListener);
    }

    public static void applyGlobalDebouncing(View[] viewArr, View.OnClickListener onClickListener) {
        applyGlobalDebouncing(viewArr, 1000L, onClickListener);
    }

    public static void applyGlobalDebouncing(View[] viewArr, @IntRange(from = 0) long j, View.OnClickListener onClickListener) {
        a(viewArr, true, j, onClickListener);
    }

    private static void a(View[] viewArr, boolean z, @IntRange(from = 0) long j, final View.OnClickListener onClickListener) {
        if (!(viewArr == null || viewArr.length == 0 || onClickListener == null)) {
            for (View view : viewArr) {
                if (view != null) {
                    view.setOnClickListener(new OnDebouncingClickListener(z, j) { // from class: com.blankj.utilcode.util.ClickUtils.1
                        @Override // com.blankj.utilcode.util.ClickUtils.OnDebouncingClickListener
                        public void onDebouncingClick(View view2) {
                            onClickListener.onClick(view2);
                        }
                    });
                }
            }
        }
    }

    public static void expandClickArea(@NonNull View view, int i) {
        if (view != null) {
            expandClickArea(view, i, i, i, i);
            return;
        }
        throw new NullPointerException("Argument 'view' of type View (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void expandClickArea(@NonNull final View view, final int i, final int i2, final int i3, final int i4) {
        if (view != null) {
            final View view2 = (View) view.getParent();
            if (view2 == null) {
                Log.e("ClickUtils", "expandClickArea must have parent view.");
            } else {
                view2.post(new Runnable() { // from class: com.blankj.utilcode.util.ClickUtils.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Rect rect = new Rect();
                        view.getHitRect(rect);
                        rect.top -= i;
                        rect.bottom += i4;
                        rect.left -= i2;
                        rect.right += i3;
                        view2.setTouchDelegate(new TouchDelegate(rect, view));
                    }
                });
            }
        } else {
            throw new NullPointerException("Argument 'view' of type View (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void back2HomeFriendly(CharSequence charSequence) {
        back2HomeFriendly(charSequence, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, Back2HomeFriendlyListener.DEFAULT);
    }

    public static void back2HomeFriendly(@NonNull CharSequence charSequence, long j, @NonNull Back2HomeFriendlyListener back2HomeFriendlyListener) {
        if (charSequence == null) {
            throw new NullPointerException("Argument 'tip' of type CharSequence (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (back2HomeFriendlyListener != null) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (Math.abs(elapsedRealtime - a) < j) {
                b++;
                if (b == 2) {
                    b.f();
                    back2HomeFriendlyListener.dismiss();
                    a = 0L;
                    return;
                }
                return;
            }
            b = 1;
            back2HomeFriendlyListener.show(charSequence, j);
            a = elapsedRealtime;
        } else {
            throw new NullPointerException("Argument 'listener' of type Back2HomeFriendlyListener (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    /* loaded from: classes.dex */
    public static abstract class OnDebouncingClickListener implements View.OnClickListener {
        private static boolean a = true;
        private static final Runnable b = new Runnable() { // from class: com.blankj.utilcode.util.ClickUtils.OnDebouncingClickListener.1
            @Override // java.lang.Runnable
            public void run() {
                boolean unused = OnDebouncingClickListener.a = true;
            }
        };
        private long c;
        private boolean d;

        public abstract void onDebouncingClick(View view);

        private static boolean a(@NonNull View view, long j) {
            if (view != null) {
                return b.a(view, j);
            }
            throw new NullPointerException("Argument 'view' of type View (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }

        public OnDebouncingClickListener() {
            this(true, 1000L);
        }

        public OnDebouncingClickListener(boolean z) {
            this(z, 1000L);
        }

        public OnDebouncingClickListener(long j) {
            this(true, j);
        }

        public OnDebouncingClickListener(boolean z, long j) {
            this.d = z;
            this.c = j;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (this.d) {
                if (a) {
                    a = false;
                    view.postDelayed(b, this.c);
                    onDebouncingClick(view);
                }
            } else if (a(view, this.c)) {
                onDebouncingClick(view);
            }
        }
    }

    /* loaded from: classes.dex */
    public static abstract class OnMultiClickListener implements View.OnClickListener {
        private final int a;
        private final long b;
        private long c;
        private int d;

        public abstract void onBeforeTriggerClick(View view, int i);

        public abstract void onTriggerClick(View view);

        public OnMultiClickListener(int i) {
            this(i, 666L);
        }

        public OnMultiClickListener(int i, long j) {
            this.a = i;
            this.b = j;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (this.a <= 1) {
                onTriggerClick(view);
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.c < this.b) {
                this.d++;
                int i = this.d;
                int i2 = this.a;
                if (i == i2) {
                    onTriggerClick(view);
                } else if (i < i2) {
                    onBeforeTriggerClick(view, i);
                } else {
                    this.d = 1;
                    onBeforeTriggerClick(view, this.d);
                }
            } else {
                this.d = 1;
                onBeforeTriggerClick(view, this.d);
            }
            this.c = currentTimeMillis;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class b implements View.OnTouchListener {
        public static b a() {
            return a.a;
        }

        private b() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == 0) {
                a(view, true);
                b(view, true);
            } else if (action == 1 || action == 3) {
                a(view, false);
                b(view, false);
            }
            return false;
        }

        private void a(View view, boolean z) {
            Object tag = view.getTag(-1);
            if (tag instanceof Float) {
                float f = 1.0f;
                if (z) {
                    f = 1.0f + ((Float) tag).floatValue();
                }
                view.animate().scaleX(f).scaleY(f).setDuration(200L).start();
            }
        }

        private void b(View view, boolean z) {
            Object tag = view.getTag(z ? -2 : -3);
            if (tag instanceof Float) {
                view.setAlpha(((Float) tag).floatValue());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class a {
            private static final b a = new b();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a extends ShadowUtils.a {
        private BitmapDrawable a = null;
        private Paint b;

        public a(Drawable drawable) {
            super(drawable);
            this.b = null;
            if (drawable instanceof ColorDrawable) {
                this.b = new Paint(5);
                this.b.setColor(((ColorDrawable) drawable).getColor());
            }
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            Paint paint;
            super.setColorFilter(colorFilter);
            if (Build.VERSION.SDK_INT < 21 && (paint = this.b) != null) {
                paint.setColorFilter(colorFilter);
            }
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            Paint paint;
            super.setAlpha(i);
            if (Build.VERSION.SDK_INT < 21 && (paint = this.b) != null) {
                paint.setColor(((ColorDrawable) getWrappedDrawable()).getColor());
            }
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            if (this.a == null) {
                Bitmap createBitmap = Bitmap.createBitmap(getBounds().width(), getBounds().height(), Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(createBitmap);
                if (this.b != null) {
                    canvas2.drawRect(getBounds(), this.b);
                } else {
                    super.draw(canvas2);
                }
                this.a = new BitmapDrawable(Resources.getSystem(), createBitmap);
                this.a.setBounds(getBounds());
            }
            this.a.draw(canvas);
        }
    }
}
