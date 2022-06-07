package miuix.animation;

import android.os.SystemClock;
import android.util.ArrayMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.controller.AnimState;
import miuix.animation.internal.AnimManager;
import miuix.animation.internal.NotifyManager;
import miuix.animation.internal.TargetHandler;
import miuix.animation.internal.TargetVelocityTracker;
import miuix.animation.listener.ListenerNotifier;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.property.ViewPropertyExt;
import miuix.animation.styles.PropertyStyle;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.LogUtils;

/* loaded from: classes5.dex */
public abstract class IAnimTarget<T> {
    public static final long FLAT_ONESHOT = 1;
    static final AtomicInteger b = new AtomicInteger(Integer.MAX_VALUE);
    long e;
    long f;
    public final TargetHandler handler = new TargetHandler(this);
    public final AnimManager animManager = new AnimManager();
    NotifyManager a = new NotifyManager(this);
    float c = Float.MAX_VALUE;
    Map<Object, Float> d = new ArrayMap();
    public final int id = b.decrementAndGet();
    final TargetVelocityTracker g = new TargetVelocityTracker();

    public boolean allowAnimRun() {
        return true;
    }

    public abstract void clean();

    public float getDefaultMinVisible() {
        return 1.0f;
    }

    public abstract T getTargetObject();

    public boolean isValid() {
        return true;
    }

    public void onFrameEnd(boolean z) {
    }

    public IAnimTarget() {
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("IAnimTarget create ! ", new Object[0]);
        }
        this.animManager.setTarget(this);
        setMinVisibleChange(0.1f, ViewProperty.ROTATION, ViewProperty.ROTATION_X, ViewProperty.ROTATION_Y);
        setMinVisibleChange(0.00390625f, ViewProperty.ALPHA, ViewProperty.AUTO_ALPHA, ViewPropertyExt.FOREGROUND, ViewPropertyExt.BACKGROUND);
        setMinVisibleChange(0.002f, ViewProperty.SCALE_X, ViewProperty.SCALE_Y);
    }

    public ListenerNotifier getNotifier() {
        return this.a.getNotifier();
    }

    public void setToNotify(AnimState animState, AnimConfigLink animConfigLink) {
        this.a.setToNotify(animState, animConfigLink);
    }

    public boolean isAnimRunning(FloatProperty... floatPropertyArr) {
        return this.animManager.isAnimRunning(floatPropertyArr);
    }

    public int getId() {
        return this.id;
    }

    public void setFlags(long j) {
        this.e = j;
        this.f = SystemClock.elapsedRealtime();
    }

    public boolean isValidFlag() {
        return SystemClock.elapsedRealtime() - this.f > 3;
    }

    public boolean hasFlags(long j) {
        return CommonUtils.hasFlags(this.e, j);
    }

    public float getMinVisibleChange(Object obj) {
        Float f = this.d.get(obj);
        if (f != null) {
            return f.floatValue();
        }
        float f2 = this.c;
        return f2 != Float.MAX_VALUE ? f2 : getDefaultMinVisible();
    }

    public IAnimTarget setDefaultMinVisibleChange(float f) {
        this.c = f;
        return this;
    }

    public IAnimTarget setMinVisibleChange(float f, FloatProperty... floatPropertyArr) {
        for (FloatProperty floatProperty : floatPropertyArr) {
            this.d.put(floatProperty, Float.valueOf(f));
        }
        return this;
    }

    public IAnimTarget setMinVisibleChange(Object obj, float f) {
        this.d.put(obj, Float.valueOf(f));
        return this;
    }

    public IAnimTarget setMinVisibleChange(float f, String... strArr) {
        for (String str : strArr) {
            setMinVisibleChange(new ValueProperty(str), f);
        }
        return this;
    }

    public void executeOnInitialized(Runnable runnable) {
        post(runnable);
    }

    public void getLocationOnScreen(int[] iArr) {
        iArr[1] = 0;
        iArr[0] = 0;
    }

    public float getValue(FloatProperty floatProperty) {
        T targetObject = getTargetObject();
        if (targetObject != null) {
            return floatProperty.getValue(targetObject);
        }
        return Float.MAX_VALUE;
    }

    public void setValue(FloatProperty floatProperty, float f) {
        T targetObject = getTargetObject();
        if (targetObject != null && Math.abs(f) != Float.MAX_VALUE) {
            floatProperty.setValue(targetObject, f);
        }
    }

    public int getIntValue(IIntValueProperty iIntValueProperty) {
        T targetObject = getTargetObject();
        if (targetObject != null) {
            return iIntValueProperty.getIntValue(targetObject);
        }
        return Integer.MAX_VALUE;
    }

    public void setIntValue(IIntValueProperty iIntValueProperty, int i) {
        T targetObject = getTargetObject();
        if (targetObject != null && Math.abs(i) != Integer.MAX_VALUE) {
            iIntValueProperty.setIntValue(targetObject, i);
        }
    }

    public double getVelocity(FloatProperty floatProperty) {
        return this.animManager.getVelocity(floatProperty);
    }

    public double getThresholdVelocity(FloatProperty floatProperty) {
        return PropertyStyle.getVelocityThreshold();
    }

    public void setVelocity(FloatProperty floatProperty, double d) {
        if (d != 3.4028234663852886E38d) {
            this.animManager.setVelocity(floatProperty, (float) d);
        }
    }

    public void post(Runnable runnable) {
        if (this.handler.threadId == Thread.currentThread().getId()) {
            runnable.run();
        } else {
            this.handler.post(runnable);
        }
    }

    public boolean shouldUseIntValue(FloatProperty floatProperty) {
        return floatProperty instanceof IIntValueProperty;
    }

    public void trackVelocity(FloatProperty floatProperty, double d) {
        this.g.trackVelocity(this, floatProperty, d);
    }

    public String toString() {
        return "IAnimTarget{" + getTargetObject() + "}";
    }

    protected void finalize() throws Throwable {
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("IAnimTarget was destroyed ！", new Object[0]);
        }
        super.finalize();
    }
}
