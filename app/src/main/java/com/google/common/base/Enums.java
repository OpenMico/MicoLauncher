package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class Enums {
    @GwtIncompatible
    private static final Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> a = new WeakHashMap();

    private Enums() {
    }

    @GwtIncompatible
    public static Field getField(Enum<?> r1) {
        try {
            return r1.getDeclaringClass().getDeclaredField(r1.name());
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> cls, String str) {
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(str);
        return j.a(cls, str);
    }

    @GwtIncompatible
    private static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> b(Class<T> cls) {
        HashMap hashMap = new HashMap();
        Iterator it = EnumSet.allOf(cls).iterator();
        while (it.hasNext()) {
            Enum r2 = (Enum) it.next();
            hashMap.put(r2.name(), new WeakReference(r2));
        }
        a.put(cls, hashMap);
        return hashMap;
    }

    @GwtIncompatible
    public static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> a(Class<T> cls) {
        Map<String, WeakReference<? extends Enum<?>>> map;
        synchronized (a) {
            map = a.get(cls);
            if (map == null) {
                map = b(cls);
            }
        }
        return map;
    }

    public static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> cls) {
        return new a(cls);
    }

    /* loaded from: classes2.dex */
    private static final class a<T extends Enum<T>> extends Converter<String, T> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Class<T> enumClass;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.base.Converter
        protected /* synthetic */ String doBackward(Object obj) {
            return a((a<T>) ((Enum) obj));
        }

        a(Class<T> cls) {
            this.enumClass = (Class) Preconditions.checkNotNull(cls);
        }

        /* renamed from: a */
        public T doForward(String str) {
            return (T) Enum.valueOf(this.enumClass, str);
        }

        protected String a(T t) {
            return t.name();
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof a) {
                return this.enumClass.equals(((a) obj).enumClass);
            }
            return false;
        }

        public int hashCode() {
            return this.enumClass.hashCode();
        }

        public String toString() {
            return "Enums.stringConverter(" + this.enumClass.getName() + ".class)";
        }
    }
}
