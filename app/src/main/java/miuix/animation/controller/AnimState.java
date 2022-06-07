package miuix.animation.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.internal.AnimValueUtils;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.ISpecificProperty;
import miuix.animation.property.IntValueProperty;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.ObjectPool;

/* loaded from: classes5.dex */
public class AnimState {
    public static final long FLAG_IN_TOUCH = 4;
    public static final long FLAG_PARALLEL = 2;
    public static final long FLAG_QUEUE = 1;
    public static final int VIEW_POS = 1000100;
    public static final int VIEW_SIZE = 1000000;
    private static final AtomicInteger c = new AtomicInteger();
    ValueProperty a;
    IntValueProperty b;
    private volatile Object d;
    private final AnimConfig e;
    private final Map<Object, Double> f;
    public long flags;
    public final boolean isTemporary;

    public static void alignState(AnimState animState, Collection<UpdateInfo> collection) {
        UpdateInfo updateInfo;
        for (UpdateInfo updateInfo2 : collection) {
            if (!animState.contains(updateInfo2.property)) {
                if (updateInfo2.useInt) {
                    animState.add(updateInfo2.property, (int) updateInfo2.animInfo.startValue);
                } else {
                    animState.add(updateInfo2.property, (float) updateInfo2.animInfo.startValue);
                }
            }
        }
        List<Object> list = (List) ObjectPool.acquire(ArrayList.class, new Object[0]);
        for (Object obj : animState.keySet()) {
            if (obj instanceof FloatProperty) {
                updateInfo = UpdateInfo.findBy(collection, (FloatProperty) obj);
            } else {
                updateInfo = UpdateInfo.findByName(collection, (String) obj);
            }
            if (updateInfo == null) {
                list.add(obj);
            }
        }
        for (Object obj2 : list) {
            animState.remove(obj2);
        }
        ObjectPool.release(list);
    }

    public AnimState() {
        this(null, false);
    }

    public AnimState(Object obj) {
        this(obj, false);
    }

    public AnimState(Object obj, boolean z) {
        this.a = new ValueProperty("");
        this.b = new IntValueProperty("");
        this.e = new AnimConfig();
        this.f = new ConcurrentHashMap();
        setTag(obj);
        this.isTemporary = z;
    }

    public final void setTag(Object obj) {
        if (obj == null) {
            obj = "TAG_" + c.incrementAndGet();
        }
        this.d = obj;
    }

    public void clear() {
        this.e.clear();
        this.f.clear();
    }

    public void set(AnimState animState) {
        if (animState != null) {
            setTag(animState.d);
            a(animState);
        }
    }

    private void a(AnimState animState) {
        this.e.copy(animState.e);
        this.f.clear();
        this.f.putAll(animState.f);
    }

    public Object getTag() {
        return this.d;
    }

    public AnimState add(String str, float f, long... jArr) {
        if (jArr.length > 0) {
            setConfigFlag(str, jArr[0]);
        }
        return add(str, f);
    }

    public AnimState add(String str, int i, long... jArr) {
        if (jArr.length > 0) {
            setConfigFlag(str, jArr[0] | 4);
        } else {
            setConfigFlag(str, getConfigFlags(str) | 4);
        }
        return add(str, i);
    }

    public AnimState add(ViewProperty viewProperty, float f, long... jArr) {
        return add((FloatProperty) viewProperty, f, jArr);
    }

    public AnimState add(ViewProperty viewProperty, int i, long... jArr) {
        return add((FloatProperty) viewProperty, i, jArr);
    }

    public AnimState add(FloatProperty floatProperty, float f, long... jArr) {
        if (jArr.length > 0) {
            setConfigFlag(floatProperty, jArr[0]);
        }
        return add(floatProperty, f);
    }

    public AnimState add(FloatProperty floatProperty, int i, long... jArr) {
        if (jArr.length > 0) {
            setConfigFlag(floatProperty, jArr[0] | 4);
        } else {
            setConfigFlag(floatProperty, getConfigFlags(floatProperty) | 4);
        }
        return add(floatProperty, i);
    }

    public AnimState add(Object obj, double d) {
        a(obj, d);
        return this;
    }

    public void setConfigFlag(Object obj, long j) {
        this.e.queryAndCreateSpecial(obj instanceof FloatProperty ? ((FloatProperty) obj).getName() : (String) obj).flags = j;
    }

    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.f.containsKey(obj)) {
            return true;
        }
        if (obj instanceof FloatProperty) {
            return this.f.containsKey(((FloatProperty) obj).getName());
        }
        return false;
    }

    public boolean isEmpty() {
        return this.f.isEmpty();
    }

    public Set<Object> keySet() {
        return this.f.keySet();
    }

    public int getInt(IIntValueProperty iIntValueProperty) {
        Double a = a(iIntValueProperty);
        if (a != null) {
            return a.intValue();
        }
        return Integer.MAX_VALUE;
    }

    public int getInt(String str) {
        return getInt(new IntValueProperty(str));
    }

    public float getFloat(FloatProperty floatProperty) {
        Double a = a(floatProperty);
        if (a != null) {
            return a.floatValue();
        }
        return Float.MAX_VALUE;
    }

    public float getFloat(String str) {
        Double a = a(str);
        if (a != null) {
            return a.floatValue();
        }
        return Float.MAX_VALUE;
    }

    public double get(IAnimTarget iAnimTarget, FloatProperty floatProperty) {
        Double a = a(floatProperty);
        if (a != null) {
            return a(iAnimTarget, floatProperty, a.doubleValue());
        }
        return Double.MAX_VALUE;
    }

    private Double a(Object obj) {
        Double d = this.f.get(obj);
        return (d != null || !(obj instanceof FloatProperty)) ? d : this.f.get(((FloatProperty) obj).getName());
    }

    private void a(Object obj, double d) {
        if (obj instanceof FloatProperty) {
            FloatProperty floatProperty = (FloatProperty) obj;
            if (this.f.containsKey(floatProperty.getName())) {
                this.f.put(floatProperty.getName(), Double.valueOf(d));
                return;
            }
        }
        this.f.put(obj, Double.valueOf(d));
    }

    private double a(IAnimTarget iAnimTarget, FloatProperty floatProperty, double d) {
        long configFlags = getConfigFlags(floatProperty);
        boolean hasFlags = CommonUtils.hasFlags(configFlags, 1L);
        if (!hasFlags && d != 1000000.0d && d != 1000100.0d && !(floatProperty instanceof ISpecificProperty)) {
            return d;
        }
        double value = AnimValueUtils.getValue(iAnimTarget, floatProperty, d);
        if (!hasFlags || AnimValueUtils.isInvalid(d)) {
            return value;
        }
        setConfigFlag(floatProperty, configFlags & (-2));
        double d2 = value + d;
        a(floatProperty, d2);
        return d2;
    }

    public long getConfigFlags(Object obj) {
        AnimSpecialConfig specialConfig = this.e.getSpecialConfig(obj instanceof FloatProperty ? ((FloatProperty) obj).getName() : (String) obj);
        if (specialConfig != null) {
            return specialConfig.flags;
        }
        return 0L;
    }

    public AnimConfig getConfig() {
        return this.e;
    }

    public AnimState remove(Object obj) {
        this.f.remove(obj);
        if (obj instanceof FloatProperty) {
            this.f.remove(((FloatProperty) obj).getName());
        }
        return this;
    }

    public FloatProperty getProperty(Object obj) {
        if (obj instanceof FloatProperty) {
            return (FloatProperty) obj;
        }
        String str = (String) obj;
        if (CommonUtils.hasFlags(getConfigFlags(str), 4L)) {
            return new IntValueProperty(str);
        }
        return new ValueProperty(str);
    }

    public FloatProperty getTempProperty(Object obj) {
        if (obj instanceof FloatProperty) {
            return (FloatProperty) obj;
        }
        String str = (String) obj;
        ValueProperty valueProperty = CommonUtils.hasFlags(getConfigFlags(str), 4L) ? this.b : this.a;
        valueProperty.setName(str);
        return valueProperty;
    }

    public String toString() {
        return "\nAnimState{mTag='" + this.d + "', flags:" + this.flags + ", mConfig:" + this.e + ", mMaps=" + ((Object) CommonUtils.mapToString(this.f, "    ")) + '}';
    }
}
