package io.netty.util;

import io.netty.util.Constant;
import io.netty.util.internal.ObjectUtil;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class ConstantPool<T extends Constant<T>> {
    private final Map<String, T> a = new HashMap();
    private int b = 1;

    protected abstract T newConstant(int i, String str);

    public T valueOf(Class<?> cls, String str) {
        if (cls == null) {
            throw new NullPointerException("firstNameComponent");
        } else if (str != null) {
            return valueOf(cls.getName() + '#' + str);
        } else {
            throw new NullPointerException("secondNameComponent");
        }
    }

    public T valueOf(String str) {
        T t;
        synchronized (this.a) {
            if (exists(str)) {
                t = this.a.get(str);
            } else {
                t = a(str);
            }
        }
        return t;
    }

    public boolean exists(String str) {
        boolean containsKey;
        b(str);
        synchronized (this.a) {
            containsKey = this.a.containsKey(str);
        }
        return containsKey;
    }

    public T newInstance(String str) {
        if (!exists(str)) {
            return a(str);
        }
        throw new IllegalArgumentException(String.format("'%s' is already in use", str));
    }

    private T a(String str) {
        T newConstant;
        synchronized (this.a) {
            newConstant = newConstant(this.b, str);
            this.a.put(str, newConstant);
            this.b++;
        }
        return newConstant;
    }

    private String b(String str) {
        ObjectUtil.checkNotNull(str, "name");
        if (!str.isEmpty()) {
            return str;
        }
        throw new IllegalArgumentException("empty name");
    }

    @Deprecated
    public final int nextId() {
        int i;
        synchronized (this.a) {
            i = this.b;
            this.b++;
        }
        return i;
    }
}
