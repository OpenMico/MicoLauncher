package miuix.animation;

import java.util.concurrent.atomic.AtomicInteger;
import miuix.animation.property.ColorProperty;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.IntValueProperty;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ValueTargetObject;
import miuix.animation.property.ViewProperty;

/* loaded from: classes5.dex */
public class ValueTarget extends IAnimTarget {
    static ITargetCreator h = new ITargetCreator() { // from class: miuix.animation.ValueTarget.1
        @Override // miuix.animation.ITargetCreator
        public IAnimTarget createTarget(Object obj) {
            return new ValueTarget(obj);
        }
    };
    private ValueTargetObject i;
    private AtomicInteger j;

    @Override // miuix.animation.IAnimTarget
    public void clean() {
    }

    @Override // miuix.animation.IAnimTarget
    public float getDefaultMinVisible() {
        return 0.002f;
    }

    public ValueTarget() {
        this(null);
    }

    private ValueTarget(Object obj) {
        this.j = new AtomicInteger(1000);
        this.i = new ValueTargetObject(obj == null ? Integer.valueOf(getId()) : obj);
    }

    @Override // miuix.animation.IAnimTarget
    public void setValue(FloatProperty floatProperty, float f) {
        if (a(floatProperty)) {
            this.i.setPropertyValue(floatProperty.getName(), Float.TYPE, Float.valueOf(f));
        } else {
            floatProperty.setValue(this.i.getRealObject(), f);
        }
    }

    public float getValue(String str) {
        return getValue(getFloatProperty(str));
    }

    public void setValue(String str, float f) {
        setValue(getFloatProperty(str), f);
    }

    public int getIntValue(String str) {
        return getIntValue(getIntValueProperty(str));
    }

    public void setIntValue(String str, int i) {
        setIntValue(getIntValueProperty(str), i);
    }

    @Override // miuix.animation.IAnimTarget
    public float getValue(FloatProperty floatProperty) {
        if (!a(floatProperty)) {
            return floatProperty.getValue(this.i.getRealObject());
        }
        Float f = (Float) this.i.getPropertyValue(floatProperty.getName(), Float.TYPE);
        if (f == null) {
            return Float.MAX_VALUE;
        }
        return f.floatValue();
    }

    @Override // miuix.animation.IAnimTarget
    public void setIntValue(IIntValueProperty iIntValueProperty, int i) {
        if (a(iIntValueProperty)) {
            this.i.setPropertyValue(iIntValueProperty.getName(), Integer.TYPE, Integer.valueOf(i));
        } else {
            iIntValueProperty.setIntValue(this.i.getRealObject(), i);
        }
    }

    @Override // miuix.animation.IAnimTarget
    public int getIntValue(IIntValueProperty iIntValueProperty) {
        if (!a(iIntValueProperty)) {
            return iIntValueProperty.getIntValue(this.i.getRealObject());
        }
        Integer num = (Integer) this.i.getPropertyValue(iIntValueProperty.getName(), Integer.TYPE);
        if (num == null) {
            return Integer.MAX_VALUE;
        }
        return num.intValue();
    }

    public double getVelocity(String str) {
        return getVelocity(getFloatProperty(str));
    }

    public void setVelocity(String str, double d) {
        setVelocity(getFloatProperty(str), d);
    }

    @Override // miuix.animation.IAnimTarget
    public float getMinVisibleChange(Object obj) {
        if (!(obj instanceof IIntValueProperty) || (obj instanceof ColorProperty)) {
            return super.getMinVisibleChange(obj);
        }
        return 1.0f;
    }

    @Override // miuix.animation.IAnimTarget
    public boolean isValid() {
        return this.i.isValid();
    }

    @Override // miuix.animation.IAnimTarget
    public Object getTargetObject() {
        return this.i;
    }

    public FloatProperty createProperty(String str, Class<?> cls) {
        if (cls == Integer.TYPE || cls == Integer.class) {
            return new IntValueProperty(str);
        }
        return new ValueProperty(str);
    }

    private boolean a(Object obj) {
        return (obj instanceof ValueProperty) || (obj instanceof ViewProperty) || (obj instanceof ColorProperty);
    }

    public FloatProperty getFloatProperty(String str) {
        return createProperty(str, Float.TYPE);
    }

    public IIntValueProperty getIntValueProperty(String str) {
        return (IIntValueProperty) createProperty(str, Integer.TYPE);
    }
}
