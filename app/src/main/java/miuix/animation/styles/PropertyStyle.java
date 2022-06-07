package miuix.animation.styles;

import android.animation.TimeInterpolator;
import miuix.animation.IAnimTarget;
import miuix.animation.internal.AnimData;
import miuix.animation.internal.AnimValueUtils;
import miuix.animation.physics.AccelerateOperator;
import miuix.animation.physics.EquilibriumChecker;
import miuix.animation.physics.FrictionOperator;
import miuix.animation.physics.PhysicsOperator;
import miuix.animation.physics.SpringOperator;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.EaseManager;
import miuix.animation.utils.LogUtils;

/* loaded from: classes5.dex */
public class PropertyStyle {
    static EquilibriumChecker d;
    static final SpringOperator a = new SpringOperator();
    static final AccelerateOperator b = new AccelerateOperator();
    static final FrictionOperator c = new FrictionOperator();
    static final ThreadLocal<EquilibriumChecker> e = new ThreadLocal<>();

    public static void doAnimationFrame(IAnimTarget iAnimTarget, AnimData animData, long j, long j2, long j3) {
        long j4 = j - animData.startTime;
        if (EaseManager.isPhysicsStyle(animData.ease.style)) {
            a(iAnimTarget, animData, j4, j2, j3);
        } else {
            a(animData, j4);
        }
    }

    private static void a(AnimData animData, long j) {
        EaseManager.InterpolateEaseStyle interpolateEaseStyle = (EaseManager.InterpolateEaseStyle) animData.ease;
        TimeInterpolator interpolator = EaseManager.getInterpolator(interpolateEaseStyle);
        if (j < interpolateEaseStyle.duration) {
            animData.progress = interpolator.getInterpolation(((float) j) / ((float) interpolateEaseStyle.duration));
            animData.value = animData.progress;
            return;
        }
        animData.setOp((byte) 3);
        animData.progress = 1.0d;
        animData.value = animData.progress;
    }

    private static void a(IAnimTarget iAnimTarget, AnimData animData, long j, long j2, long j3) {
        int round = j2 > j3 ? Math.round(((float) j2) / ((float) j3)) : 1;
        double d2 = j3 / 1000.0d;
        d = (EquilibriumChecker) CommonUtils.getLocal(e, EquilibriumChecker.class);
        d.init(iAnimTarget, animData.property, animData.targetValue);
        for (int i = 0; i < round; i++) {
            a(animData, d2);
            if (!a(d, animData.property, animData.ease.style, animData.value, animData.velocity, j)) {
                animData.setOp((byte) 3);
                a(animData);
                return;
            }
        }
    }

    private static void a(AnimData animData) {
        if (b(animData)) {
            animData.value = animData.targetValue;
        }
    }

    private static void a(AnimData animData, double d2) {
        double d3 = animData.velocity;
        PhysicsOperator phyOperator = getPhyOperator(animData.ease.style);
        if (phyOperator == null || ((phyOperator instanceof SpringOperator) && AnimValueUtils.isInvalid(animData.targetValue))) {
            animData.value = animData.targetValue;
            animData.velocity = 0.0d;
            return;
        }
        double updateVelocity = phyOperator.updateVelocity(d3, animData.ease.parameters[0], animData.ease.parameters[1], d2, animData.targetValue, animData.value);
        animData.value += (animData.velocity + updateVelocity) * 0.5d * d2;
        animData.velocity = updateVelocity;
    }

    static boolean a(EquilibriumChecker equilibriumChecker, FloatProperty floatProperty, int i, double d2, double d3, long j) {
        boolean z = !equilibriumChecker.isAtEquilibrium(i, d2, d3);
        if (!z || j <= 10000) {
            return z;
        }
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("animation for " + floatProperty.getName() + " stopped for running time too long, totalTime = " + j, new Object[0]);
        }
        return false;
    }

    public static PhysicsOperator getPhyOperator(int i) {
        switch (i) {
            case -4:
                return c;
            case -3:
                return b;
            case -2:
                return a;
            default:
                return null;
        }
    }

    public static float getVelocityThreshold() {
        EquilibriumChecker equilibriumChecker = d;
        if (equilibriumChecker != null) {
            return equilibriumChecker.getVelocityThreshold();
        }
        return 0.0f;
    }

    private static boolean b(AnimData animData) {
        return animData.ease.style == -2;
    }
}
