package com.fasterxml.jackson.databind.introspect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class MemberKey {
    static final Class<?>[] a = new Class[0];
    final String b;
    final Class<?>[] c;

    public MemberKey(Method method) {
        this(method.getName(), method.getParameterTypes());
    }

    public MemberKey(Constructor<?> constructor) {
        this("", constructor.getParameterTypes());
    }

    public MemberKey(String str, Class<?>[] clsArr) {
        this.b = str;
        this.c = clsArr == null ? a : clsArr;
    }

    public String getName() {
        return this.b;
    }

    public int argCount() {
        return this.c.length;
    }

    public String toString() {
        return this.b + "(" + this.c.length + "-args)";
    }

    public int hashCode() {
        return this.b.hashCode() + this.c.length;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        MemberKey memberKey = (MemberKey) obj;
        if (!this.b.equals(memberKey.b)) {
            return false;
        }
        Class<?>[] clsArr = memberKey.c;
        int length = this.c.length;
        if (clsArr.length != length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (clsArr[i] != this.c[i]) {
                return false;
            }
        }
        return true;
    }
}
