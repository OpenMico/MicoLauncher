package miuix.animation.property;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.FieldManager;

/* loaded from: classes5.dex */
public class ValueTargetObject {
    private Object a;
    private WeakReference<Object> b;
    private FieldManager c = new FieldManager();
    private Map<String, Object> d = new ConcurrentHashMap();

    public ValueTargetObject(Object obj) {
        if (CommonUtils.isBuiltInClass(obj.getClass())) {
            this.a = obj;
        } else {
            this.b = new WeakReference<>(obj);
        }
    }

    public boolean isValid() {
        return getRealObject() != null;
    }

    public Object getRealObject() {
        WeakReference<Object> weakReference = this.b;
        return weakReference != null ? weakReference.get() : this.a;
    }

    public <T> T getPropertyValue(String str, Class<T> cls) {
        Object realObject = getRealObject();
        if (realObject == null || this.a == realObject) {
            return (T) this.d.get(str);
        }
        T t = (T) this.d.get(str);
        return t != null ? t : (T) this.c.getField(realObject, str, cls);
    }

    public <T> void setPropertyValue(String str, Class<T> cls, T t) {
        Object realObject = getRealObject();
        if (realObject == null || this.a == realObject) {
            this.d.put(str, t);
        } else if (this.d.containsKey(str) || !this.c.setField(realObject, str, cls, t)) {
            this.d.put(str, t);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            Object obj2 = this.a;
            if (obj2 != null) {
                return Objects.equals(obj2, obj);
            }
            Object realObject = getRealObject();
            if (realObject != null) {
                return Objects.equals(realObject, obj);
            }
            return false;
        }
        ValueTargetObject valueTargetObject = (ValueTargetObject) obj;
        Object obj3 = this.a;
        if (obj3 != null) {
            return Objects.equals(obj3, valueTargetObject.a);
        }
        return Objects.equals(getRealObject(), valueTargetObject.getRealObject());
    }

    public int hashCode() {
        Object obj = this.a;
        if (obj != null) {
            return obj.hashCode();
        }
        Object realObject = getRealObject();
        if (realObject != null) {
            return realObject.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "ValueTargetObject{" + getRealObject() + "}";
    }
}
