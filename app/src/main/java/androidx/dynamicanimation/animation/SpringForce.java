package androidx.dynamicanimation.animation;

import androidx.annotation.FloatRange;
import androidx.annotation.RestrictTo;
import androidx.dynamicanimation.animation.DynamicAnimation;

/* loaded from: classes.dex */
public final class SpringForce {
    public static final float DAMPING_RATIO_HIGH_BOUNCY = 0.2f;
    public static final float DAMPING_RATIO_LOW_BOUNCY = 0.75f;
    public static final float DAMPING_RATIO_MEDIUM_BOUNCY = 0.5f;
    public static final float DAMPING_RATIO_NO_BOUNCY = 1.0f;
    public static final float STIFFNESS_HIGH = 10000.0f;
    public static final float STIFFNESS_LOW = 200.0f;
    public static final float STIFFNESS_MEDIUM = 1500.0f;
    public static final float STIFFNESS_VERY_LOW = 50.0f;
    double a;
    double b;
    private boolean c;
    private double d;
    private double e;
    private double f;
    private double g;
    private double h;
    private double i;
    private final DynamicAnimation.a j;

    public SpringForce() {
        this.a = Math.sqrt(1500.0d);
        this.b = 0.5d;
        this.c = false;
        this.i = Double.MAX_VALUE;
        this.j = new DynamicAnimation.a();
    }

    public SpringForce(float f) {
        this.a = Math.sqrt(1500.0d);
        this.b = 0.5d;
        this.c = false;
        this.i = Double.MAX_VALUE;
        this.j = new DynamicAnimation.a();
        this.i = f;
    }

    public SpringForce setStiffness(@FloatRange(from = 0.0d, fromInclusive = false) float f) {
        if (f > 0.0f) {
            this.a = Math.sqrt(f);
            this.c = false;
            return this;
        }
        throw new IllegalArgumentException("Spring stiffness constant must be positive.");
    }

    public float getStiffness() {
        double d = this.a;
        return (float) (d * d);
    }

    public SpringForce setDampingRatio(@FloatRange(from = 0.0d) float f) {
        if (f >= 0.0f) {
            this.b = f;
            this.c = false;
            return this;
        }
        throw new IllegalArgumentException("Damping ratio must be non-negative");
    }

    public float getDampingRatio() {
        return (float) this.b;
    }

    public SpringForce setFinalPosition(float f) {
        this.i = f;
        return this;
    }

    public float getFinalPosition() {
        return (float) this.i;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public float getAcceleration(float f, float f2) {
        float finalPosition = f - getFinalPosition();
        double d = this.a;
        return (float) (((-(d * d)) * finalPosition) - (((d * 2.0d) * this.b) * f2));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean isAtEquilibrium(float f, float f2) {
        return ((double) Math.abs(f2)) < this.e && ((double) Math.abs(f - getFinalPosition())) < this.d;
    }

    private void a() {
        if (!this.c) {
            if (this.i != Double.MAX_VALUE) {
                double d = this.b;
                if (d > 1.0d) {
                    double d2 = this.a;
                    this.f = ((-d) * d2) + (d2 * Math.sqrt((d * d) - 1.0d));
                    double d3 = this.b;
                    double d4 = this.a;
                    this.g = ((-d3) * d4) - (d4 * Math.sqrt((d3 * d3) - 1.0d));
                } else if (d >= 0.0d && d < 1.0d) {
                    this.h = this.a * Math.sqrt(1.0d - (d * d));
                }
                this.c = true;
                return;
            }
            throw new IllegalStateException("Error: Final position of the spring must be set before the animation starts");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DynamicAnimation.a a(double d, double d2, long j) {
        double d3;
        double d4;
        a();
        double d5 = j / 1000.0d;
        double d6 = d - this.i;
        double d7 = this.b;
        if (d7 > 1.0d) {
            double d8 = this.g;
            double d9 = this.f;
            double d10 = d6 - (((d8 * d6) - d2) / (d8 - d9));
            double d11 = ((d6 * d8) - d2) / (d8 - d9);
            d4 = (Math.pow(2.718281828459045d, d8 * d5) * d10) + (Math.pow(2.718281828459045d, this.f * d5) * d11);
            double d12 = this.g;
            double pow = d10 * d12 * Math.pow(2.718281828459045d, d12 * d5);
            double d13 = this.f;
            d3 = pow + (d11 * d13 * Math.pow(2.718281828459045d, d13 * d5));
        } else if (d7 == 1.0d) {
            double d14 = this.a;
            double d15 = d2 + (d14 * d6);
            double d16 = d6 + (d15 * d5);
            d4 = Math.pow(2.718281828459045d, (-d14) * d5) * d16;
            double pow2 = d16 * Math.pow(2.718281828459045d, (-this.a) * d5);
            double d17 = this.a;
            d3 = (d15 * Math.pow(2.718281828459045d, (-d17) * d5)) + (pow2 * (-d17));
        } else {
            double d18 = 1.0d / this.h;
            double d19 = this.a;
            double d20 = d18 * ((d7 * d19 * d6) + d2);
            double pow3 = Math.pow(2.718281828459045d, (-d7) * d19 * d5) * ((Math.cos(this.h * d5) * d6) + (Math.sin(this.h * d5) * d20));
            double d21 = this.a;
            double d22 = this.b;
            double pow4 = Math.pow(2.718281828459045d, (-d22) * d21 * d5);
            double d23 = this.h;
            double sin = (-d23) * d6 * Math.sin(d23 * d5);
            double d24 = this.h;
            d3 = ((-d21) * pow3 * d22) + (pow4 * (sin + (d20 * d24 * Math.cos(d24 * d5))));
            d4 = pow3;
        }
        DynamicAnimation.a aVar = this.j;
        aVar.a = (float) (d4 + this.i);
        aVar.b = (float) d3;
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(double d) {
        this.d = Math.abs(d);
        this.e = this.d * 62.5d;
    }
}
