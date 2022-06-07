package miuix.animation.internal;

import miuix.animation.IAnimTarget;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.ISpecificProperty;
import miuix.animation.utils.CommonUtils;

/* loaded from: classes5.dex */
public class AnimValueUtils {
    public static boolean isInvalid(double d) {
        return d == Double.MAX_VALUE || d == 3.4028234663852886E38d || d == 2.147483647E9d;
    }

    private AnimValueUtils() {
    }

    public static double getValueOfTarget(IAnimTarget iAnimTarget, FloatProperty floatProperty, double d) {
        if (d == 2.147483647E9d) {
            return iAnimTarget.getIntValue((IIntValueProperty) floatProperty);
        }
        if (d == 3.4028234663852886E38d) {
            return iAnimTarget.getValue(floatProperty);
        }
        return getValue(iAnimTarget, floatProperty, d);
    }

    public static double getValue(IAnimTarget iAnimTarget, FloatProperty floatProperty, double d) {
        if (floatProperty instanceof ISpecificProperty) {
            return ((ISpecificProperty) floatProperty).getSpecificValue((float) d);
        }
        return a(iAnimTarget, floatProperty, d);
    }

    private static double a(IAnimTarget iAnimTarget, FloatProperty floatProperty, double d) {
        double signum = Math.signum(d);
        double abs = Math.abs(d);
        if (abs == 1000000.0d) {
            return signum * CommonUtils.getSize(iAnimTarget, floatProperty);
        }
        double intValue = floatProperty instanceof IIntValueProperty ? iAnimTarget.getIntValue((IIntValueProperty) floatProperty) : iAnimTarget.getValue(floatProperty);
        return abs == 1000100.0d ? intValue * signum : intValue;
    }

    public static boolean handleSetToValue(UpdateInfo updateInfo) {
        if (isInvalid(updateInfo.animInfo.setToValue)) {
            return false;
        }
        updateInfo.animInfo.value = updateInfo.animInfo.setToValue;
        updateInfo.animInfo.setToValue = Double.MAX_VALUE;
        return true;
    }
}
