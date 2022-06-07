package miuix.animation.internal;

import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.TypeEvaluator;
import java.util.List;
import miuix.animation.ViewTarget;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ColorProperty;
import miuix.animation.property.ViewPropertyExt;
import miuix.animation.styles.PropertyStyle;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.EaseManager;
import miuix.animation.utils.LogUtils;

/* compiled from: AnimRunnerTask.java */
/* loaded from: classes5.dex */
class b {
    static final ThreadLocal<AnimData> a = new ThreadLocal<>();

    private static float a(float f) {
        if (f > 1.0f) {
            return 1.0f;
        }
        if (f < 0.0f) {
            return 0.0f;
        }
        return f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(AnimTask animTask, long j, long j2, boolean z, boolean z2) {
        int i;
        UpdateInfo updateInfo;
        UpdateInfo updateInfo2;
        AnimData animData = (AnimData) CommonUtils.getLocal(a, AnimData.class);
        animData.b = LogUtils.isLogEnabled();
        long averageDelta = AnimRunner.getInst().getAverageDelta();
        for (AnimTask animTask2 = animTask; animTask2 != null; animTask2 = animTask2.remove()) {
            animTask2.animStats.d = 0;
            boolean z3 = !animTask2.animStats.a();
            List<UpdateInfo> list = animTask2.info.j;
            boolean z4 = animTask2.info.c instanceof ViewTarget;
            int i2 = animTask2.startPos;
            int animCount = i2 + animTask2.getAnimCount();
            for (int i3 = i2; i3 < animCount; i3 = i + 1) {
                UpdateInfo updateInfo3 = list.get(i3);
                if (updateInfo3 == null) {
                    animCount = animCount;
                    i = i3;
                    z4 = z4;
                } else {
                    AnimSpecialConfig specialConfig = animTask2.info.f.getSpecialConfig(updateInfo3.property.getName());
                    animData.a(updateInfo3, animTask2.info.f, specialConfig);
                    if (z3) {
                        updateInfo = updateInfo3;
                        animCount = animCount;
                        i = i3;
                        a(animTask2, animData, animTask2.info, specialConfig, j, j2);
                    } else {
                        updateInfo = updateInfo3;
                        animCount = animCount;
                        i = i3;
                    }
                    if (animData.op == 1) {
                        a(animTask2, animData, animTask2.info, j, j2);
                    }
                    if (animData.op == 2) {
                        z4 = z4;
                        a(animTask2, animData, animTask2.info, j, j2, averageDelta);
                        updateInfo2 = updateInfo;
                    } else {
                        z4 = z4;
                        updateInfo2 = updateInfo;
                    }
                    animData.a(updateInfo2);
                    if (z && z2 && !z4 && !AnimValueUtils.isInvalid(animData.value)) {
                        updateInfo2.setTargetValue(animTask2.info.c);
                    }
                }
            }
        }
    }

    static void a(AnimTask animTask, AnimData animData, TransitionInfo transitionInfo, AnimSpecialConfig animSpecialConfig, long j, long j2) {
        if (AnimValueUtils.isInvalid(animData.startValue)) {
            animData.startValue = AnimValueUtils.getValue(transitionInfo.c, animData.property, animData.startValue);
        }
        long j3 = j - j2;
        animData.initTime = j3;
        animTask.animStats.b++;
        if (animData.op != 2 || animData.delay > 0) {
            animData.setOp((byte) 1);
            float d = AnimConfigUtils.d(transitionInfo.f, animSpecialConfig);
            if (d != Float.MAX_VALUE) {
                animData.velocity = d;
                return;
            }
            return;
        }
        animData.startTime = j3;
        animData.delay = 0L;
        animTask.animStats.a--;
        a(animTask, animData);
    }

    static void a(AnimTask animTask, AnimData animData, TransitionInfo transitionInfo, long j, long j2) {
        if (animData.delay > 0) {
            if (animData.b) {
                LogUtils.debug("StartTask, tag = " + animTask.info.e + ", property = " + animData.property.getName() + ", delay = " + animData.delay + ", initTime = " + animData.initTime + ", totalT = " + j, new Object[0]);
            }
            if (j >= animData.initTime + animData.delay) {
                double value = AnimValueUtils.getValue(transitionInfo.c, animData.property, Double.MAX_VALUE);
                if (value != Double.MAX_VALUE) {
                    animData.startValue = value;
                }
            } else {
                return;
            }
        }
        c cVar = animTask.animStats;
        cVar.a--;
        if (a(animTask, animData, j, j2)) {
            a(animTask, animData);
        }
    }

    private static void a(AnimTask animTask, AnimData animData) {
        animData.progress = 0.0d;
        animData.a();
        if (animData.b) {
            LogUtils.debug("+++++ start anim, target = " + animTask.info.c + ", tag = " + animTask.info.e + ", property = " + animData.property.getName() + ", op = " + ((int) animData.op) + ", ease = " + animData.ease + ", delay = " + animData.delay + ", start value = " + animData.startValue + ", target value = " + animData.targetValue + ", value = " + animData.value + ", progress = " + animData.progress + ", velocity = " + animData.velocity, new Object[0]);
        }
    }

    private static boolean a(AnimTask animTask, AnimData animData, long j, long j2) {
        if (!a(animData)) {
            if (animData.b) {
                LogUtils.logThread(CommonUtils.TAG, "StartTask, set start value failed, break, tag = " + animTask.info.e + ", property = " + animData.property.getName() + ", start value = " + animData.startValue + ", target value = " + animData.targetValue + ", value = " + animData.value);
            }
            b(animTask, animData);
            return false;
        } else if (b(animData)) {
            if (animData.b) {
                LogUtils.logThread(CommonUtils.TAG, "StartTask, values invalid, break, tag = " + animTask.info.e + ", property = " + animData.property.getName() + ", startValue = " + animData.startValue + ", targetValue = " + animData.targetValue + ", value = " + animData.value + ", velocity = " + animData.velocity);
            }
            animData.a();
            b(animTask, animData);
            return false;
        } else {
            animData.startTime = j - j2;
            animData.frameCount = 0;
            animData.setOp((byte) 2);
            return true;
        }
    }

    private static boolean a(AnimData animData) {
        if (!AnimValueUtils.isInvalid(animData.value)) {
            if (AnimValueUtils.isInvalid(animData.startValue)) {
                animData.startValue = animData.value;
            }
            return true;
        } else if (AnimValueUtils.isInvalid(animData.startValue)) {
            return false;
        } else {
            animData.value = animData.startValue;
            return true;
        }
    }

    private static void b(AnimTask animTask, AnimData animData) {
        animData.setOp((byte) 5);
        animTask.animStats.c++;
    }

    private static boolean b(AnimData animData) {
        return animData.startValue == animData.targetValue && Math.abs(animData.velocity) < 16.66666603088379d;
    }

    private static void a(AnimTask animTask, AnimData animData, TransitionInfo transitionInfo, long j, long j2, long j3) {
        animTask.animStats.d++;
        animData.frameCount++;
        if (animData.property == ViewPropertyExt.FOREGROUND || animData.property == ViewPropertyExt.BACKGROUND || (animData.property instanceof ColorProperty)) {
            double d = animData.startValue;
            double d2 = animData.targetValue;
            animData.startValue = 0.0d;
            animData.targetValue = 1.0d;
            animData.value = animData.progress;
            PropertyStyle.doAnimationFrame(transitionInfo.c, animData, j, j2, j3);
            animData.progress = a((float) animData.value);
            animData.startValue = d;
            animData.targetValue = d2;
            animData.value = ((Integer) CommonUtils.sArgbEvaluator.evaluate((float) animData.progress, Integer.valueOf((int) animData.startValue), Integer.valueOf((int) animData.targetValue))).doubleValue();
        } else {
            PropertyStyle.doAnimationFrame(transitionInfo.c, animData, j, j2, j3);
            if (!EaseManager.isPhysicsStyle(animData.ease.style)) {
                animData.value = a(animData, (float) animData.progress);
            }
        }
        if (animData.op == 3) {
            animData.a = true;
            animTask.animStats.f++;
        }
        if (animData.b) {
            LogUtils.debug("----- update anim, target = " + animTask.info.c + ", tag = " + animTask.info.e + ", property = " + animData.property.getName() + ", op = " + ((int) animData.op) + ", init time = " + animData.initTime + ", start time = " + animData.startTime + ", start value = " + animData.startValue + ", target value = " + animData.targetValue + ", value = " + animData.value + ", progress = " + animData.progress + ", velocity = " + animData.velocity + ", delta = " + j2, new Object[0]);
        }
    }

    private static double a(AnimData animData, float f) {
        TypeEvaluator a2 = a(animData.property);
        if (a2 instanceof IntEvaluator) {
            return ((IntEvaluator) a2).evaluate(f, Integer.valueOf((int) animData.startValue), Integer.valueOf((int) animData.targetValue)).doubleValue();
        }
        return ((FloatEvaluator) a2).evaluate(f, (Number) Float.valueOf((float) animData.startValue), (Number) Float.valueOf((float) animData.targetValue)).doubleValue();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [int, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.animation.TypeEvaluator, android.animation.FloatEvaluator, com.elvishew.xlog.printer.Printer] */
    /* JADX WARN: Unknown variable types count: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.animation.TypeEvaluator a(miuix.animation.property.FloatProperty r1) {
        /*
            miuix.animation.property.ViewPropertyExt$BackgroundProperty r0 = miuix.animation.property.ViewPropertyExt.BACKGROUND
            if (r1 != r0) goto L_0x000b
            boolean r0 = r1 instanceof miuix.animation.property.ColorProperty
            if (r0 == 0) goto L_0x000b
            android.animation.ArgbEvaluator r1 = miuix.animation.utils.CommonUtils.sArgbEvaluator
            return r1
        L_0x000b:
            boolean r1 = r1 instanceof miuix.animation.property.IIntValueProperty
            if (r1 == 0) goto L_0x0015
            android.animation.IntEvaluator r1 = new android.animation.IntEvaluator
            r1.<init>()
            return r1
        L_0x0015:
            android.animation.FloatEvaluator r1 = new android.animation.FloatEvaluator
            r1.println(r0, r0, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: miuix.animation.internal.b.a(miuix.animation.property.FloatProperty):android.animation.TypeEvaluator");
    }
}
