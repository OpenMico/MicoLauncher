package miuix.animation.physics;

import miuix.animation.physics.DynamicAnimation;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.FloatValueHolder;

/* loaded from: classes5.dex */
public final class FlingAnimation extends DynamicAnimation<FlingAnimation> {
    private final a i = new a();

    public FlingAnimation(FloatValueHolder floatValueHolder) {
        super(floatValueHolder);
        this.i.b(a());
    }

    public <K> FlingAnimation(K k, FloatProperty<K> floatProperty) {
        super(k, floatProperty);
        this.i.b(a());
    }

    public FlingAnimation setFriction(float f) {
        if (f > 0.0f) {
            this.i.a(f);
            return this;
        }
        throw new IllegalArgumentException("Friction must be positive");
    }

    public float getFriction() {
        return this.i.a();
    }

    @Override // miuix.animation.physics.DynamicAnimation
    public FlingAnimation setMinValue(float f) {
        super.setMinValue(f);
        return this;
    }

    @Override // miuix.animation.physics.DynamicAnimation
    public FlingAnimation setMaxValue(float f) {
        super.setMaxValue(f);
        return this;
    }

    @Override // miuix.animation.physics.DynamicAnimation
    public FlingAnimation setStartVelocity(float f) {
        super.setStartVelocity(f);
        return this;
    }

    @Override // miuix.animation.physics.DynamicAnimation
    boolean a(long j) {
        DynamicAnimation.a a2 = this.i.a(this.b, this.a, j);
        this.b = a2.a;
        this.a = a2.b;
        if (this.b < this.h) {
            this.b = this.h;
            return true;
        } else if (this.b <= this.g) {
            return a(this.b, this.a);
        } else {
            this.b = this.g;
            return true;
        }
    }

    @Override // miuix.animation.physics.DynamicAnimation
    boolean a(float f, float f2) {
        return f >= this.g || f <= this.h || this.i.a(f, f2);
    }

    @Override // miuix.animation.physics.DynamicAnimation
    void b(float f) {
        this.i.b(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class a {
        private float a;
        private float b;
        private final DynamicAnimation.a c;

        private a() {
            this.a = -4.2f;
            this.c = new DynamicAnimation.a();
        }

        void a(float f) {
            this.a = f * (-4.2f);
        }

        float a() {
            return this.a / (-4.2f);
        }

        DynamicAnimation.a a(float f, float f2, long j) {
            float f3 = (float) j;
            this.c.b = (float) (f2 * Math.exp((f3 / 1000.0f) * this.a));
            DynamicAnimation.a aVar = this.c;
            float f4 = this.a;
            aVar.a = (float) ((f - (f2 / f4)) + ((f2 / f4) * Math.exp((f4 * f3) / 1000.0f)));
            if (a(this.c.a, this.c.b)) {
                this.c.b = 0.0f;
            }
            return this.c;
        }

        public boolean a(float f, float f2) {
            return Math.abs(f2) < this.b;
        }

        void b(float f) {
            this.b = f * 62.5f;
        }
    }
}
