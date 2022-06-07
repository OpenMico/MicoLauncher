package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class MultiClassKey {
    private Class<?> a;
    private Class<?> b;
    private Class<?> c;

    public MultiClassKey() {
    }

    public MultiClassKey(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
        set(cls, cls2);
    }

    public MultiClassKey(@NonNull Class<?> cls, @NonNull Class<?> cls2, @Nullable Class<?> cls3) {
        set(cls, cls2, cls3);
    }

    public void set(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
        set(cls, cls2, null);
    }

    public void set(@NonNull Class<?> cls, @NonNull Class<?> cls2, @Nullable Class<?> cls3) {
        this.a = cls;
        this.b = cls2;
        this.c = cls3;
    }

    public String toString() {
        return "MultiClassKey{first=" + this.a + ", second=" + this.b + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MultiClassKey multiClassKey = (MultiClassKey) obj;
        return this.a.equals(multiClassKey.a) && this.b.equals(multiClassKey.b) && Util.bothNullOrEqual(this.c, multiClassKey.c);
    }

    public int hashCode() {
        int hashCode = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
        Class<?> cls = this.c;
        return hashCode + (cls != null ? cls.hashCode() : 0);
    }
}
