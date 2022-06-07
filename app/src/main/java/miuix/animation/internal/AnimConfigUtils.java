package miuix.animation.internal;

import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.utils.EaseManager;

/* loaded from: classes5.dex */
public class AnimConfigUtils {
    private AnimConfigUtils() {
    }

    public static EaseManager.EaseStyle a(AnimConfig animConfig, AnimSpecialConfig animSpecialConfig) {
        EaseManager.EaseStyle easeStyle;
        if (animSpecialConfig == null || animSpecialConfig.ease == null || animSpecialConfig.ease == AnimConfig.sDefEase) {
            easeStyle = animConfig.ease;
        } else {
            easeStyle = animSpecialConfig.ease;
        }
        return easeStyle == null ? AnimConfig.sDefEase : easeStyle;
    }

    public static long b(AnimConfig animConfig, AnimSpecialConfig animSpecialConfig) {
        return Math.max(animConfig.delay, animSpecialConfig != null ? animSpecialConfig.delay : 0L);
    }

    public static int c(AnimConfig animConfig, AnimSpecialConfig animSpecialConfig) {
        return Math.max(animConfig.tintMode, animSpecialConfig != null ? animSpecialConfig.tintMode : -1);
    }

    public static float d(AnimConfig animConfig, AnimSpecialConfig animSpecialConfig) {
        if (animSpecialConfig == null || AnimValueUtils.isInvalid(animSpecialConfig.fromSpeed)) {
            return animConfig.fromSpeed;
        }
        return animSpecialConfig.fromSpeed;
    }

    public static float chooseSpeed(float f, float f2) {
        return AnimValueUtils.isInvalid((double) f) ? f2 : AnimValueUtils.isInvalid((double) f2) ? f : Math.max(f, f2);
    }
}
