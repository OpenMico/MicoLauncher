package androidx.dynamicanimation.animation;

import android.os.Looper;
import android.util.AndroidRuntimeException;
import android.view.View;
import androidx.annotation.FloatRange;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.a;
import com.umeng.analytics.pro.ai;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class DynamicAnimation<T extends DynamicAnimation<T>> implements a.b {
    public static final float MIN_VISIBLE_CHANGE_ALPHA = 0.00390625f;
    public static final float MIN_VISIBLE_CHANGE_PIXELS = 1.0f;
    public static final float MIN_VISIBLE_CHANGE_ROTATION_DEGREES = 0.1f;
    public static final float MIN_VISIBLE_CHANGE_SCALE = 0.002f;
    float a;
    float b;
    boolean c;
    final Object d;
    final FloatPropertyCompat e;
    boolean f;
    float g;
    float h;
    private long i;
    private float j;
    private final ArrayList<OnAnimationEndListener> k;
    private final ArrayList<OnAnimationUpdateListener> l;
    public static final ViewProperty TRANSLATION_X = new ViewProperty("translationX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.1
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setTranslationX(f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getTranslationX();
        }
    };
    public static final ViewProperty TRANSLATION_Y = new ViewProperty("translationY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.8
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setTranslationY(f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getTranslationY();
        }
    };
    public static final ViewProperty TRANSLATION_Z = new ViewProperty("translationZ") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.9
        /* renamed from: a */
        public void setValue(View view, float f) {
            ViewCompat.setTranslationZ(view, f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return ViewCompat.getTranslationZ(view);
        }
    };
    public static final ViewProperty SCALE_X = new ViewProperty("scaleX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.10
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setScaleX(f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getScaleX();
        }
    };
    public static final ViewProperty SCALE_Y = new ViewProperty("scaleY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.11
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setScaleY(f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getScaleY();
        }
    };
    public static final ViewProperty ROTATION = new ViewProperty("rotation") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.12
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setRotation(f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getRotation();
        }
    };
    public static final ViewProperty ROTATION_X = new ViewProperty("rotationX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.13
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setRotationX(f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getRotationX();
        }
    };
    public static final ViewProperty ROTATION_Y = new ViewProperty("rotationY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.14
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setRotationY(f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getRotationY();
        }
    };
    public static final ViewProperty X = new ViewProperty("x") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.15
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setX(f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getX();
        }
    };
    public static final ViewProperty Y = new ViewProperty("y") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.2
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setY(f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getY();
        }
    };
    public static final ViewProperty Z = new ViewProperty(ai.aB) { // from class: androidx.dynamicanimation.animation.DynamicAnimation.3
        /* renamed from: a */
        public void setValue(View view, float f) {
            ViewCompat.setZ(view, f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return ViewCompat.getZ(view);
        }
    };
    public static final ViewProperty ALPHA = new ViewProperty("alpha") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.4
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setAlpha(f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getAlpha();
        }
    };
    public static final ViewProperty SCROLL_X = new ViewProperty("scrollX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.5
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setScrollX((int) f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getScrollX();
        }
    };
    public static final ViewProperty SCROLL_Y = new ViewProperty("scrollY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.6
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setScrollY((int) f);
        }

        /* renamed from: a */
        public float getValue(View view) {
            return view.getScrollY();
        }
    };

    /* loaded from: classes.dex */
    public interface OnAnimationEndListener {
        void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2);
    }

    /* loaded from: classes.dex */
    public interface OnAnimationUpdateListener {
        void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a {
        float a;
        float b;
    }

    abstract boolean a(float f, float f2);

    abstract boolean a(long j);

    abstract void b(float f);

    /* loaded from: classes.dex */
    public static abstract class ViewProperty extends FloatPropertyCompat<View> {
        private ViewProperty(String str) {
            super(str);
        }
    }

    public DynamicAnimation(final FloatValueHolder floatValueHolder) {
        this.a = 0.0f;
        this.b = Float.MAX_VALUE;
        this.c = false;
        this.f = false;
        this.g = Float.MAX_VALUE;
        this.h = -this.g;
        this.i = 0L;
        this.k = new ArrayList<>();
        this.l = new ArrayList<>();
        this.d = null;
        this.e = new FloatPropertyCompat("FloatValueHolder") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.7
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public float getValue(Object obj) {
                return floatValueHolder.getValue();
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public void setValue(Object obj, float f) {
                floatValueHolder.setValue(f);
            }
        };
        this.j = 1.0f;
    }

    public <K> DynamicAnimation(K k, FloatPropertyCompat<K> floatPropertyCompat) {
        this.a = 0.0f;
        this.b = Float.MAX_VALUE;
        this.c = false;
        this.f = false;
        this.g = Float.MAX_VALUE;
        this.h = -this.g;
        this.i = 0L;
        this.k = new ArrayList<>();
        this.l = new ArrayList<>();
        this.d = k;
        this.e = floatPropertyCompat;
        FloatPropertyCompat floatPropertyCompat2 = this.e;
        if (floatPropertyCompat2 == ROTATION || floatPropertyCompat2 == ROTATION_X || floatPropertyCompat2 == ROTATION_Y) {
            this.j = 0.1f;
        } else if (floatPropertyCompat2 == ALPHA) {
            this.j = 0.00390625f;
        } else if (floatPropertyCompat2 == SCALE_X || floatPropertyCompat2 == SCALE_Y) {
            this.j = 0.00390625f;
        } else {
            this.j = 1.0f;
        }
    }

    public T setStartValue(float f) {
        this.b = f;
        this.c = true;
        return this;
    }

    public T setStartVelocity(float f) {
        this.a = f;
        return this;
    }

    public T setMaxValue(float f) {
        this.g = f;
        return this;
    }

    public T setMinValue(float f) {
        this.h = f;
        return this;
    }

    public T addEndListener(OnAnimationEndListener onAnimationEndListener) {
        if (!this.k.contains(onAnimationEndListener)) {
            this.k.add(onAnimationEndListener);
        }
        return this;
    }

    public void removeEndListener(OnAnimationEndListener onAnimationEndListener) {
        a(this.k, onAnimationEndListener);
    }

    public T addUpdateListener(OnAnimationUpdateListener onAnimationUpdateListener) {
        if (!isRunning()) {
            if (!this.l.contains(onAnimationUpdateListener)) {
                this.l.add(onAnimationUpdateListener);
            }
            return this;
        }
        throw new UnsupportedOperationException("Error: Update listeners must be added beforethe animation.");
    }

    public void removeUpdateListener(OnAnimationUpdateListener onAnimationUpdateListener) {
        a(this.l, onAnimationUpdateListener);
    }

    public T setMinimumVisibleChange(@FloatRange(from = 0.0d, fromInclusive = false) float f) {
        if (f > 0.0f) {
            this.j = f;
            b(f * 0.75f);
            return this;
        }
        throw new IllegalArgumentException("Minimum visible change must be positive.");
    }

    public float getMinimumVisibleChange() {
        return this.j;
    }

    private static <T> void a(ArrayList<T> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size) == null) {
                arrayList.remove(size);
            }
        }
    }

    private static <T> void a(ArrayList<T> arrayList, T t) {
        int indexOf = arrayList.indexOf(t);
        if (indexOf >= 0) {
            arrayList.set(indexOf, null);
        }
    }

    public void start() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new AndroidRuntimeException("Animations may only be started on the main thread");
        } else if (!this.f) {
            b();
        }
    }

    public void cancel() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new AndroidRuntimeException("Animations may only be canceled on the main thread");
        } else if (this.f) {
            a(true);
        }
    }

    public boolean isRunning() {
        return this.f;
    }

    private void b() {
        if (!this.f) {
            this.f = true;
            if (!this.c) {
                this.b = c();
            }
            float f = this.b;
            if (f > this.g || f < this.h) {
                throw new IllegalArgumentException("Starting value need to be in between min value and max value");
            }
            a.a().a(this, 0L);
        }
    }

    @Override // androidx.dynamicanimation.animation.a.b
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean doAnimationFrame(long j) {
        long j2 = this.i;
        if (j2 == 0) {
            this.i = j;
            a(this.b);
            return false;
        }
        this.i = j;
        boolean a2 = a(j - j2);
        this.b = Math.min(this.b, this.g);
        this.b = Math.max(this.b, this.h);
        a(this.b);
        if (a2) {
            a(false);
        }
        return a2;
    }

    private void a(boolean z) {
        this.f = false;
        a.a().a(this);
        this.i = 0L;
        this.c = false;
        for (int i = 0; i < this.k.size(); i++) {
            if (this.k.get(i) != null) {
                this.k.get(i).onAnimationEnd(this, z, this.b, this.a);
            }
        }
        a(this.k);
    }

    void a(float f) {
        this.e.setValue(this.d, f);
        for (int i = 0; i < this.l.size(); i++) {
            if (this.l.get(i) != null) {
                this.l.get(i).onAnimationUpdate(this, this.b, this.a);
            }
        }
        a(this.l);
    }

    float a() {
        return this.j * 0.75f;
    }

    private float c() {
        return this.e.getValue(this.d);
    }
}
