package miuix.animation.property;

import android.util.Property;

/* loaded from: classes5.dex */
public abstract class FloatProperty<T> extends Property<T, Float> {
    final String c;

    public abstract float getValue(T t);

    public abstract void setValue(T t, float f);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.util.Property
    public /* bridge */ /* synthetic */ void set(Object obj, Float f) {
        set2((FloatProperty<T>) obj, f);
    }

    public FloatProperty(String str) {
        super(Float.class, str);
        this.c = str;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.util.Property
    public Float get(T t) {
        if (t == null) {
            return Float.valueOf(0.0f);
        }
        return Float.valueOf(getValue(t));
    }

    /* renamed from: set  reason: avoid collision after fix types in other method */
    public final void set2(T t, Float f) {
        if (t != null) {
            setValue(t, f.floatValue());
        }
    }

    public String toString() {
        return getClass().getSimpleName() + "{mPropertyName='" + this.c + "'}";
    }
}
