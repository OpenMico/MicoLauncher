package miuix.animation.utils;

import android.animation.TimeInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import miuix.animation.physics.PhysicsOperator;
import miuix.animation.styles.PropertyStyle;
import miuix.view.animation.BounceEaseInInterpolator;
import miuix.view.animation.BounceEaseInOutInterpolator;
import miuix.view.animation.BounceEaseOutInterpolator;
import miuix.view.animation.CubicEaseInInterpolator;
import miuix.view.animation.CubicEaseInOutInterpolator;
import miuix.view.animation.CubicEaseOutInterpolator;
import miuix.view.animation.ExponentialEaseInInterpolator;
import miuix.view.animation.ExponentialEaseInOutInterpolator;
import miuix.view.animation.ExponentialEaseOutInterpolator;
import miuix.view.animation.QuadraticEaseInInterpolator;
import miuix.view.animation.QuadraticEaseInOutInterpolator;
import miuix.view.animation.QuadraticEaseOutInterpolator;
import miuix.view.animation.QuarticEaseInInterpolator;
import miuix.view.animation.QuarticEaseInOutInterpolator;
import miuix.view.animation.QuinticEaseInInterpolator;
import miuix.view.animation.QuinticEaseInOutInterpolator;
import miuix.view.animation.QuinticEaseOutInterpolator;
import miuix.view.animation.SineEaseInInterpolator;
import miuix.view.animation.SineEaseInOutInterpolator;
import miuix.view.animation.SineEaseOutInterpolator;

/* loaded from: classes5.dex */
public class EaseManager {
    public static final long DEFAULT_DURATION = 300;
    static final ConcurrentHashMap<Integer, TimeInterpolator> a = new ConcurrentHashMap<>();

    /* loaded from: classes5.dex */
    public interface EaseStyleDef {
        public static final int ACCELERATE = -3;
        public static final int ACCELERATE_DECELERATE = 21;
        public static final int ACCELERATE_INTERPOLATOR = 22;
        public static final int BOUNCE = 23;
        public static final int BOUNCE_EASE_IN = 24;
        public static final int BOUNCE_EASE_INOUT = 26;
        public static final int BOUNCE_EASE_OUT = 25;
        public static final int CUBIC_IN = 5;
        public static final int CUBIC_INOUT = 7;
        public static final int CUBIC_OUT = 6;
        public static final int DECELERATE = 20;
        public static final int DURATION = -1;
        public static final int EXPO_IN = 17;
        public static final int EXPO_INOUT = 19;
        public static final int EXPO_OUT = 18;
        public static final int FRICTION = -4;
        public static final int LINEAR = 1;
        public static final int QUAD_IN = 2;
        public static final int QUAD_INOUT = 4;
        public static final int QUAD_OUT = 3;
        public static final int QUART_IN = 8;
        public static final int QUART_INOUT = 10;
        public static final int QUART_OUT = 9;
        public static final int QUINT_IN = 11;
        public static final int QUINT_INOUT = 13;
        public static final int QUINT_OUT = 12;
        public static final int REBOUND = -6;
        public static final int SIN_IN = 14;
        public static final int SIN_INOUT = 16;
        public static final int SIN_OUT = 15;
        public static final int SPRING = 0;
        public static final int SPRING_PHY = -2;
        public static final int STOP = -5;
    }

    public static boolean isPhysicsStyle(int i) {
        return i < -1;
    }

    static TimeInterpolator a(int i, float... fArr) {
        switch (i) {
            case -1:
            case 1:
                return new LinearInterpolator();
            case 0:
                return new SpringInterpolator().setDamping(fArr[0]).setResponse(fArr[1]);
            case 2:
                return new QuadraticEaseInInterpolator();
            case 3:
                return new QuadraticEaseOutInterpolator();
            case 4:
                return new QuadraticEaseInOutInterpolator();
            case 5:
                return new CubicEaseInInterpolator();
            case 6:
                return new CubicEaseOutInterpolator();
            case 7:
                return new CubicEaseInOutInterpolator();
            case 8:
                return new QuarticEaseInInterpolator();
            case 9:
                return new QuadraticEaseOutInterpolator();
            case 10:
                return new QuarticEaseInOutInterpolator();
            case 11:
                return new QuinticEaseInInterpolator();
            case 12:
                return new QuinticEaseOutInterpolator();
            case 13:
                return new QuinticEaseInOutInterpolator();
            case 14:
                return new SineEaseInInterpolator();
            case 15:
                return new SineEaseOutInterpolator();
            case 16:
                return new SineEaseInOutInterpolator();
            case 17:
                return new ExponentialEaseInInterpolator();
            case 18:
                return new ExponentialEaseOutInterpolator();
            case 19:
                return new ExponentialEaseInOutInterpolator();
            case 20:
                return new DecelerateInterpolator();
            case 21:
                return new AccelerateDecelerateInterpolator();
            case 22:
                return new AccelerateInterpolator();
            case 23:
                return new BounceInterpolator();
            case 24:
                return new BounceEaseInInterpolator();
            case 25:
                return new BounceEaseOutInterpolator();
            case 26:
                return new BounceEaseInOutInterpolator();
            default:
                return null;
        }
    }

    /* loaded from: classes5.dex */
    public static class EaseStyle {
        public volatile float[] factors;
        public final double[] parameters = {0.0d, 0.0d};
        public boolean stopAtTarget;
        public final int style;

        public EaseStyle(int i, float... fArr) {
            this.style = i;
            this.factors = fArr;
            a(this, this.parameters);
        }

        public void setFactors(float... fArr) {
            this.factors = fArr;
            a(this, this.parameters);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EaseStyle)) {
                return false;
            }
            EaseStyle easeStyle = (EaseStyle) obj;
            return this.style == easeStyle.style && Arrays.equals(this.factors, easeStyle.factors);
        }

        public int hashCode() {
            return (Objects.hash(Integer.valueOf(this.style)) * 31) + Arrays.hashCode(this.factors);
        }

        public String toString() {
            return "EaseStyle{style=" + this.style + ", factors=" + Arrays.toString(this.factors) + ", parameters = " + Arrays.toString(this.parameters) + '}';
        }

        private static void a(EaseStyle easeStyle, double[] dArr) {
            PhysicsOperator phyOperator = easeStyle == null ? null : PropertyStyle.getPhyOperator(easeStyle.style);
            if (phyOperator != null) {
                phyOperator.getParameters(easeStyle.factors, dArr);
            } else {
                Arrays.fill(dArr, 0.0d);
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class InterpolateEaseStyle extends EaseStyle {
        public long duration = 300;

        public InterpolateEaseStyle(int i, float... fArr) {
            super(i, fArr);
        }

        public InterpolateEaseStyle setDuration(long j) {
            this.duration = j;
            return this;
        }

        @Override // miuix.animation.utils.EaseManager.EaseStyle
        public String toString() {
            return "InterpolateEaseStyle{style=" + this.style + ", duration=" + this.duration + ", factors=" + Arrays.toString(this.factors) + '}';
        }
    }

    public static EaseStyle getStyle(int i, float... fArr) {
        if (i < -1) {
            return new EaseStyle(i, fArr);
        }
        InterpolateEaseStyle b = b(i, fArr.length > 1 ? Arrays.copyOfRange(fArr, 1, fArr.length) : new float[0]);
        if (fArr.length > 0) {
            b.setDuration((int) fArr[0]);
        }
        return b;
    }

    public static TimeInterpolator getInterpolator(int i, float... fArr) {
        return getInterpolator(b(i, fArr));
    }

    private static InterpolateEaseStyle b(int i, float... fArr) {
        return new InterpolateEaseStyle(i, fArr);
    }

    public static TimeInterpolator getInterpolator(InterpolateEaseStyle interpolateEaseStyle) {
        if (interpolateEaseStyle == null) {
            return null;
        }
        TimeInterpolator timeInterpolator = a.get(Integer.valueOf(interpolateEaseStyle.style));
        if (timeInterpolator == null && (timeInterpolator = a(interpolateEaseStyle.style, interpolateEaseStyle.factors)) != null) {
            a.put(Integer.valueOf(interpolateEaseStyle.style), timeInterpolator);
        }
        return timeInterpolator;
    }

    /* loaded from: classes5.dex */
    public static class SpringInterpolator implements TimeInterpolator {
        private float a = 0.95f;
        private float b = 0.6f;
        private float c = -1.0f;
        private float d = this.c;
        private float e = 1.0f;
        private float f;
        private float g;
        private float h;
        private float i;
        private float j;

        public SpringInterpolator() {
            a();
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            return (float) ((Math.pow(2.718281828459045d, this.i * f) * ((this.d * Math.cos(this.h * f)) + (this.j * Math.sin(this.h * f)))) + 1.0d);
        }

        public float getDamping() {
            return this.a;
        }

        public float getResponse() {
            return this.b;
        }

        public SpringInterpolator setDamping(float f) {
            this.a = f;
            a();
            return this;
        }

        public SpringInterpolator setResponse(float f) {
            this.b = f;
            a();
            return this;
        }

        private void a() {
            double pow = Math.pow(6.283185307179586d / this.b, 2.0d);
            float f = this.e;
            this.f = (float) (pow * f);
            this.g = (float) (((this.a * 12.566370614359172d) * f) / this.b);
            float f2 = f * 4.0f * this.f;
            float f3 = this.g;
            float f4 = this.e;
            this.h = ((float) Math.sqrt(f2 - (f3 * f3))) / (f4 * 2.0f);
            this.i = -((this.g / 2.0f) * f4);
            this.j = (0.0f - (this.i * this.c)) / this.h;
        }
    }
}
