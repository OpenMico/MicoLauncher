package miuix.animation.property;

import java.util.Objects;

/* loaded from: classes5.dex */
public class ColorProperty<T> extends FloatProperty<T> implements IIntValueProperty<T> {
    private int a;

    @Override // miuix.animation.property.FloatProperty
    public float getValue(T t) {
        return 0.0f;
    }

    @Override // miuix.animation.property.FloatProperty
    public void setValue(T t, float f) {
    }

    public ColorProperty(String str) {
        super(str);
    }

    @Override // miuix.animation.property.IIntValueProperty
    public void setIntValue(T t, int i) {
        this.a = i;
        if (t instanceof ValueTargetObject) {
            ((ValueTargetObject) t).setPropertyValue(getName(), Integer.TYPE, Integer.valueOf(i));
        }
    }

    @Override // miuix.animation.property.IIntValueProperty
    public int getIntValue(T t) {
        if (t instanceof ValueTargetObject) {
            this.a = ((Integer) ((ValueTargetObject) t).getPropertyValue(getName(), Integer.TYPE)).intValue();
        }
        return this.a;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.c.equals(((ColorProperty) obj).c);
    }

    public int hashCode() {
        return Objects.hash(this.c);
    }
}
