package miuix.animation.physics;

import android.os.Looper;
import android.util.AndroidRuntimeException;
import java.util.ArrayList;
import miuix.animation.physics.AnimationHandler;
import miuix.animation.physics.DynamicAnimation;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.FloatValueHolder;
import miuix.animation.property.ViewProperty;

/* loaded from: classes5.dex */
public abstract class DynamicAnimation<T extends DynamicAnimation<T>> implements AnimationHandler.AnimationFrameCallback {
    public static final float MIN_VISIBLE_CHANGE_ALPHA = 0.00390625f;
    public static final float MIN_VISIBLE_CHANGE_PIXELS = 1.0f;
    public static final float MIN_VISIBLE_CHANGE_ROTATION_DEGREES = 0.1f;
    public static final float MIN_VISIBLE_CHANGE_SCALE = 0.002f;
    float a;
    float b;
    boolean c;
    final Object d;
    final FloatProperty e;
    boolean f;
    float g;
    float h;
    private long i;
    private float j;
    private long k;
    private final ArrayList<OnAnimationEndListener> l;
    private final ArrayList<OnAnimationUpdateListener> m;

    /* loaded from: classes5.dex */
    public interface OnAnimationEndListener {
        void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2);
    }

    /* loaded from: classes5.dex */
    public interface OnAnimationUpdateListener {
        void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class a {
        float a;
        float b;
    }

    abstract boolean a(float f, float f2);

    abstract boolean a(long j);

    abstract void b(float f);

    public DynamicAnimation(final FloatValueHolder floatValueHolder) {
        this.a = 0.0f;
        this.b = Float.MAX_VALUE;
        this.c = false;
        this.f = false;
        this.g = Float.MAX_VALUE;
        this.h = -this.g;
        this.i = 0L;
        this.k = 0L;
        this.l = new ArrayList<>();
        this.m = new ArrayList<>();
        this.d = null;
        this.e = new FloatProperty("FloatValueHolder") { // from class: miuix.animation.physics.DynamicAnimation.1
            @Override // miuix.animation.property.FloatProperty
            public float getValue(Object obj) {
                return floatValueHolder.getValue();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(Object obj, float f) {
                floatValueHolder.setValue(f);
            }
        };
        this.j = 1.0f;
    }

    public <K> DynamicAnimation(K k, FloatProperty<K> floatProperty) {
        this.a = 0.0f;
        this.b = Float.MAX_VALUE;
        this.c = false;
        this.f = false;
        this.g = Float.MAX_VALUE;
        this.h = -this.g;
        this.i = 0L;
        this.k = 0L;
        this.l = new ArrayList<>();
        this.m = new ArrayList<>();
        this.d = k;
        this.e = floatProperty;
        if (this.e == ViewProperty.ROTATION || this.e == ViewProperty.ROTATION_X || this.e == ViewProperty.ROTATION_Y) {
            this.j = 0.1f;
        } else if (this.e == ViewProperty.ALPHA) {
            this.j = 0.00390625f;
        } else if (this.e == ViewProperty.SCALE_X || this.e == ViewProperty.SCALE_Y) {
            this.j = 0.002f;
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

    public void setStartDelay(long j) {
        if (j < 0) {
            j = 0;
        }
        this.k = j;
    }

    public T addEndListener(OnAnimationEndListener onAnimationEndListener) {
        if (!this.l.contains(onAnimationEndListener)) {
            this.l.add(onAnimationEndListener);
        }
        return this;
    }

    public void removeEndListener(OnAnimationEndListener onAnimationEndListener) {
        a(this.l, onAnimationEndListener);
    }

    public T addUpdateListener(OnAnimationUpdateListener onAnimationUpdateListener) {
        if (!isRunning()) {
            if (!this.m.contains(onAnimationUpdateListener)) {
                this.m.add(onAnimationUpdateListener);
            }
            return this;
        }
        throw new UnsupportedOperationException("Error: Update listeners must be added beforethe miuix.animation.");
    }

    public void removeUpdateListener(OnAnimationUpdateListener onAnimationUpdateListener) {
        a(this.m, onAnimationUpdateListener);
    }

    public T setMinimumVisibleChange(float f) {
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
            AnimationHandler.getInstance().addAnimationFrameCallback(this, this.k);
        }
    }

    @Override // miuix.animation.physics.AnimationHandler.AnimationFrameCallback
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
        AnimationHandler.getInstance().removeCallback(this);
        this.i = 0L;
        this.c = false;
        for (int i = 0; i < this.l.size(); i++) {
            if (this.l.get(i) != null) {
                this.l.get(i).onAnimationEnd(this, z, this.b, this.a);
            }
        }
        a(this.l);
    }

    void a(float f) {
        this.e.setValue(this.d, f);
        for (int i = 0; i < this.m.size(); i++) {
            if (this.m.get(i) != null) {
                this.m.get(i).onAnimationUpdate(this, this.b, this.a);
            }
        }
        a(this.m);
    }

    float a() {
        return this.j * 0.75f;
    }

    private float c() {
        return this.e.getValue(this.d);
    }
}
