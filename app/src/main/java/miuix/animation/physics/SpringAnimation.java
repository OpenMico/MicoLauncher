package miuix.animation.physics;

import android.os.Looper;
import android.util.AndroidRuntimeException;
import miuix.animation.physics.DynamicAnimation;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.FloatValueHolder;

/* loaded from: classes5.dex */
public final class SpringAnimation extends DynamicAnimation<SpringAnimation> {
    private SpringForce i;
    private float j;
    private boolean k;

    @Override // miuix.animation.physics.DynamicAnimation
    void b(float f) {
    }

    public SpringAnimation(FloatValueHolder floatValueHolder) {
        super(floatValueHolder);
        this.i = null;
        this.j = Float.MAX_VALUE;
        this.k = false;
    }

    public <K> SpringAnimation(K k, FloatProperty<K> floatProperty) {
        super(k, floatProperty);
        this.i = null;
        this.j = Float.MAX_VALUE;
        this.k = false;
    }

    public <K> SpringAnimation(K k, FloatProperty<K> floatProperty, float f) {
        super(k, floatProperty);
        this.i = null;
        this.j = Float.MAX_VALUE;
        this.k = false;
        this.i = new SpringForce(f);
    }

    public SpringForce getSpring() {
        return this.i;
    }

    public SpringAnimation setSpring(SpringForce springForce) {
        this.i = springForce;
        return this;
    }

    @Override // miuix.animation.physics.DynamicAnimation
    public void start() {
        b();
        this.i.a(a());
        super.start();
    }

    public void animateToFinalPosition(float f) {
        if (isRunning()) {
            this.j = f;
            return;
        }
        if (this.i == null) {
            this.i = new SpringForce(f);
        }
        this.i.setFinalPosition(f);
        start();
    }

    public void skipToEnd() {
        if (!canSkipToEnd()) {
            throw new UnsupportedOperationException("Spring animations can only come to an end when there is damping");
        } else if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new AndroidRuntimeException("Animations may only be started on the main thread");
        } else if (this.f) {
            this.k = true;
        }
    }

    public boolean canSkipToEnd() {
        return this.i.b > 0.0d;
    }

    private void b() {
        SpringForce springForce = this.i;
        if (springForce != null) {
            double finalPosition = springForce.getFinalPosition();
            if (finalPosition > this.g) {
                throw new UnsupportedOperationException("Final position of the spring cannot be greater than the max value.");
            } else if (finalPosition < this.h) {
                throw new UnsupportedOperationException("Final position of the spring cannot be less than the min value.");
            }
        } else {
            throw new UnsupportedOperationException("Incomplete SpringAnimation: Either final position or a spring force needs to be set.");
        }
    }

    @Override // miuix.animation.physics.DynamicAnimation
    boolean a(long j) {
        if (this.k) {
            float f = this.j;
            if (f != Float.MAX_VALUE) {
                this.i.setFinalPosition(f);
                this.j = Float.MAX_VALUE;
            }
            this.b = this.i.getFinalPosition();
            this.a = 0.0f;
            this.k = false;
            return true;
        }
        if (this.j != Float.MAX_VALUE) {
            this.i.getFinalPosition();
            long j2 = j / 2;
            DynamicAnimation.a a = this.i.a(this.b, this.a, j2);
            this.i.setFinalPosition(this.j);
            this.j = Float.MAX_VALUE;
            DynamicAnimation.a a2 = this.i.a(a.a, a.b, j2);
            this.b = a2.a;
            this.a = a2.b;
        } else {
            DynamicAnimation.a a3 = this.i.a(this.b, this.a, j);
            this.b = a3.a;
            this.a = a3.b;
        }
        this.b = Math.max(this.b, this.h);
        this.b = Math.min(this.b, this.g);
        if (!a(this.b, this.a)) {
            return false;
        }
        this.b = this.i.getFinalPosition();
        this.a = 0.0f;
        return true;
    }

    @Override // miuix.animation.physics.DynamicAnimation
    boolean a(float f, float f2) {
        return this.i.isAtEquilibrium(f, f2);
    }
}
