package miuix.animation.listener;

import java.util.Collection;
import miuix.animation.IAnimTarget;
import miuix.animation.internal.AnimInfo;
import miuix.animation.internal.AnimTask;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;

/* loaded from: classes5.dex */
public class UpdateInfo {
    public final AnimInfo animInfo = new AnimInfo();
    public volatile int frameCount;
    public volatile boolean isCompleted;
    public final FloatProperty property;
    public final boolean useInt;
    public volatile double velocity;

    public static UpdateInfo findByName(Collection<UpdateInfo> collection, String str) {
        for (UpdateInfo updateInfo : collection) {
            if (updateInfo.property.getName().equals(str)) {
                return updateInfo;
            }
        }
        return null;
    }

    public static UpdateInfo findBy(Collection<UpdateInfo> collection, FloatProperty floatProperty) {
        for (UpdateInfo updateInfo : collection) {
            if (updateInfo.property.equals(floatProperty)) {
                return updateInfo;
            }
        }
        return null;
    }

    public UpdateInfo(FloatProperty floatProperty) {
        this.property = floatProperty;
        this.useInt = floatProperty instanceof IIntValueProperty;
    }

    public Class<?> getType() {
        return this.property instanceof IIntValueProperty ? Integer.TYPE : Float.TYPE;
    }

    public <T> T getValue(Class<T> cls) {
        if (cls == Float.class || cls == Float.TYPE) {
            return (T) Float.valueOf(getFloatValue());
        }
        if (cls == Double.class || cls == Double.TYPE) {
            return (T) Double.valueOf(this.animInfo.value);
        }
        return (T) Integer.valueOf(getIntValue());
    }

    public float getFloatValue() {
        double d = this.animInfo.setToValue;
        if (d != Double.MAX_VALUE) {
            return (float) d;
        }
        if (this.animInfo.value == Double.MAX_VALUE) {
            return Float.MAX_VALUE;
        }
        return (float) this.animInfo.value;
    }

    public int getIntValue() {
        double d = this.animInfo.setToValue;
        if (d != Double.MAX_VALUE) {
            return (int) d;
        }
        if (this.animInfo.value == Double.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) this.animInfo.value;
    }

    public String toString() {
        return "UpdateInfo{, property=" + this.property + ", velocity=" + this.velocity + ", value = " + this.animInfo.value + ", useInt=" + this.useInt + ", frameCount=" + this.frameCount + ", isCompleted=" + this.isCompleted + '}';
    }

    public void reset() {
        this.isCompleted = false;
        this.frameCount = 0;
    }

    public void setOp(byte b) {
        this.isCompleted = b == 0 || b > 2;
        if (this.isCompleted && AnimTask.isRunning(this.animInfo.op)) {
            this.animInfo.justEnd = true;
        }
        this.animInfo.op = b;
    }

    public boolean isValid() {
        return this.property != null;
    }

    public void setTargetValue(IAnimTarget iAnimTarget) {
        if (this.useInt) {
            iAnimTarget.setIntValue((IIntValueProperty) this.property, getIntValue());
        } else {
            iAnimTarget.setValue(this.property, getFloatValue());
        }
    }
}
