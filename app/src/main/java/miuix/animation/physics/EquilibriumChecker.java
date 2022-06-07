package miuix.animation.physics;

import miuix.animation.IAnimTarget;
import miuix.animation.property.FloatProperty;

/* loaded from: classes5.dex */
public class EquilibriumChecker {
    public static final float MIN_VISIBLE_CHANGE_ALPHA = 0.00390625f;
    public static final float MIN_VISIBLE_CHANGE_PIXELS = 1.0f;
    public static final float MIN_VISIBLE_CHANGE_ROTATION_DEGREES = 0.1f;
    public static final float MIN_VISIBLE_CHANGE_SCALE = 0.002f;
    public static final float VELOCITY_THRESHOLD_MULTIPLIER = 16.666666f;
    private double a = Double.MAX_VALUE;
    private float b;
    private float c;

    public void init(IAnimTarget iAnimTarget, FloatProperty floatProperty, double d) {
        this.b = iAnimTarget.getMinVisibleChange(floatProperty) * 0.75f;
        this.c = this.b * 16.666666f;
        this.a = d;
    }

    public boolean isAtEquilibrium(int i, double d, double d2) {
        return (i != -2 || a(d, this.a)) && i != -3 && Math.abs(d2) < ((double) this.c);
    }

    public float getVelocityThreshold() {
        return this.c;
    }

    private boolean a(double d, double d2) {
        return Math.abs(this.a) == 3.4028234663852886E38d || Math.abs(d - d2) < ((double) this.b);
    }
}
