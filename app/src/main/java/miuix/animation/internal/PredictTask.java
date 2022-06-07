package miuix.animation.internal;

import android.animation.TimeInterpolator;
import java.util.ArrayList;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.controller.AnimState;
import miuix.animation.internal.TransitionInfo;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.physics.PhysicsOperator;
import miuix.animation.property.FloatProperty;
import miuix.animation.styles.PropertyStyle;
import miuix.animation.utils.EaseManager;

/* loaded from: classes5.dex */
public class PredictTask {
    private static final TransitionInfo.IUpdateInfoCreator a = new TransitionInfo.IUpdateInfoCreator() { // from class: miuix.animation.internal.PredictTask.1
        @Override // miuix.animation.internal.TransitionInfo.IUpdateInfoCreator
        public UpdateInfo getUpdateInfo(FloatProperty floatProperty) {
            return new UpdateInfo(floatProperty);
        }
    };

    public static long predictDuration(IAnimTarget iAnimTarget, AnimState animState, AnimState animState2, AnimConfigLink animConfigLink) {
        TransitionInfo transitionInfo = new TransitionInfo(iAnimTarget, animState, animState2, animConfigLink);
        transitionInfo.a(a);
        transitionInfo.a(true);
        long averageDelta = AnimRunner.getInst().getAverageDelta();
        long j = averageDelta;
        while (true) {
            for (AnimTask animTask : transitionInfo.k) {
                b.a(animTask, j, averageDelta, false, true);
            }
            if (!transitionInfo.b().b()) {
                return j;
            }
            j += averageDelta;
        }
    }

    public static double predictNextValue(IAnimTarget iAnimTarget, AnimState animState, FloatProperty floatProperty, AnimConfig animConfig) {
        EaseManager.EaseStyle a2 = AnimConfigUtils.a(animState.getConfig(), animConfig.getSpecialConfig(floatProperty));
        float value = iAnimTarget.getValue(floatProperty);
        double velocity = iAnimTarget.getVelocity(floatProperty);
        ArrayList<TransitionInfo> arrayList = new ArrayList();
        iAnimTarget.animManager.a(arrayList);
        UpdateInfo updateInfo = null;
        for (TransitionInfo transitionInfo : arrayList) {
            if (transitionInfo.a(floatProperty)) {
                updateInfo = UpdateInfo.findBy(transitionInfo.j, floatProperty);
            }
        }
        if (updateInfo == null) {
            return Double.MAX_VALUE;
        }
        double averageDelta = AnimRunner.getInst().getAverageDelta() / 1000.0d;
        if (EaseManager.isPhysicsStyle(a2.style)) {
            PhysicsOperator phyOperator = PropertyStyle.getPhyOperator(a2.style);
            if (AnimValueUtils.isInvalid(updateInfo.animInfo.targetValue)) {
                return Double.MAX_VALUE;
            }
            double d = value;
            return d + ((velocity + phyOperator.updateVelocity(velocity, a2.parameters[0], a2.parameters[1], averageDelta, updateInfo.animInfo.targetValue, d)) * 0.5d * averageDelta);
        }
        EaseManager.InterpolateEaseStyle interpolateEaseStyle = (EaseManager.InterpolateEaseStyle) a2;
        TimeInterpolator interpolator = EaseManager.getInterpolator(interpolateEaseStyle);
        long currentTimeMillis = (System.currentTimeMillis() - updateInfo.animInfo.startTime) + ((long) averageDelta);
        if (currentTimeMillis < interpolateEaseStyle.duration) {
            return interpolator.getInterpolation(((float) currentTimeMillis) / ((float) interpolateEaseStyle.duration));
        }
        return updateInfo.animInfo.targetValue;
    }

    public static double predictNextVelocity(IAnimTarget iAnimTarget, AnimState animState, FloatProperty floatProperty, AnimConfig animConfig) {
        EaseManager.EaseStyle a2 = AnimConfigUtils.a(animState.getConfig(), animConfig.getSpecialConfig(floatProperty));
        float value = iAnimTarget.getValue(floatProperty);
        double velocity = iAnimTarget.getVelocity(floatProperty);
        ArrayList<TransitionInfo> arrayList = new ArrayList();
        iAnimTarget.animManager.a(arrayList);
        UpdateInfo updateInfo = null;
        for (TransitionInfo transitionInfo : arrayList) {
            if (transitionInfo.a(floatProperty)) {
                updateInfo = UpdateInfo.findBy(transitionInfo.j, floatProperty);
            }
        }
        if (updateInfo == null || !EaseManager.isPhysicsStyle(a2.style)) {
            return Double.MAX_VALUE;
        }
        double averageDelta = AnimRunner.getInst().getAverageDelta() / 1000.0d;
        PhysicsOperator phyOperator = PropertyStyle.getPhyOperator(a2.style);
        if (AnimValueUtils.isInvalid(updateInfo.animInfo.targetValue)) {
            return Double.MAX_VALUE;
        }
        return phyOperator.updateVelocity(velocity, a2.parameters[0], a2.parameters[1], averageDelta, updateInfo.animInfo.targetValue, value);
    }
}
