package org.apache.commons.lang3.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

/* loaded from: classes5.dex */
public class HashCodeBuilder implements Builder<Integer> {
    private static final ThreadLocal<Set<a>> a = new ThreadLocal<>();
    private final int b;
    private int c;

    static Set<a> a() {
        return a.get();
    }

    static boolean a(Object obj) {
        Set<a> a2 = a();
        return a2 != null && a2.contains(new a(obj));
    }

    private static void a(Object obj, Class<?> cls, HashCodeBuilder hashCodeBuilder, boolean z, String[] strArr) {
        if (!a(obj)) {
            try {
                b(obj);
                Field[] declaredFields = cls.getDeclaredFields();
                AccessibleObject.setAccessible(declaredFields, true);
                for (Field field : declaredFields) {
                    if (!ArrayUtils.contains(strArr, field.getName()) && !field.getName().contains("$") && ((z || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(HashCodeExclude.class))) {
                        try {
                            hashCodeBuilder.append(field.get(obj));
                        } catch (IllegalAccessException unused) {
                            throw new InternalError("Unexpected IllegalAccessException");
                        }
                    }
                }
            } finally {
                c(obj);
            }
        }
    }

    public static int reflectionHashCode(int i, int i2, Object obj) {
        return reflectionHashCode(i, i2, obj, false, null, new String[0]);
    }

    public static int reflectionHashCode(int i, int i2, Object obj, boolean z) {
        return reflectionHashCode(i, i2, obj, z, null, new String[0]);
    }

    public static <T> int reflectionHashCode(int i, int i2, T t, boolean z, Class<? super T> cls, String... strArr) {
        if (t != null) {
            HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(i, i2);
            Class<?> cls2 = t.getClass();
            a(t, cls2, hashCodeBuilder, z, strArr);
            while (cls2.getSuperclass() != null && cls2 != cls) {
                cls2 = cls2.getSuperclass();
                a(t, cls2, hashCodeBuilder, z, strArr);
            }
            return hashCodeBuilder.toHashCode();
        }
        throw new IllegalArgumentException("The object to build a hash code for must not be null");
    }

    public static int reflectionHashCode(Object obj, boolean z) {
        return reflectionHashCode(17, 37, obj, z, null, new String[0]);
    }

    public static int reflectionHashCode(Object obj, Collection<String> collection) {
        return reflectionHashCode(obj, ReflectionToStringBuilder.a(collection));
    }

    public static int reflectionHashCode(Object obj, String... strArr) {
        return reflectionHashCode(17, 37, obj, false, null, strArr);
    }

    private static void b(Object obj) {
        Set<a> a2 = a();
        if (a2 == null) {
            a2 = new HashSet<>();
            a.set(a2);
        }
        a2.add(new a(obj));
    }

    private static void c(Object obj) {
        Set<a> a2 = a();
        if (a2 != null) {
            a2.remove(new a(obj));
            if (a2.isEmpty()) {
                a.remove();
            }
        }
    }

    public HashCodeBuilder() {
        this.c = 0;
        this.b = 37;
        this.c = 17;
    }

    public HashCodeBuilder(int i, int i2) {
        this.c = 0;
        boolean z = true;
        Validate.isTrue(i % 2 != 0, "HashCodeBuilder requires an odd initial value", new Object[0]);
        Validate.isTrue(i2 % 2 == 0 ? false : z, "HashCodeBuilder requires an odd multiplier", new Object[0]);
        this.b = i2;
        this.c = i;
    }

    public HashCodeBuilder append(boolean z) {
        this.c = (this.c * this.b) + (!z ? 1 : 0);
        return this;
    }

    public HashCodeBuilder append(boolean[] zArr) {
        if (zArr == null) {
            this.c *= this.b;
        } else {
            for (boolean z : zArr) {
                append(z);
            }
        }
        return this;
    }

    public HashCodeBuilder append(byte b) {
        this.c = (this.c * this.b) + b;
        return this;
    }

    public HashCodeBuilder append(byte[] bArr) {
        if (bArr == null) {
            this.c *= this.b;
        } else {
            for (byte b : bArr) {
                append(b);
            }
        }
        return this;
    }

    public HashCodeBuilder append(char c) {
        this.c = (this.c * this.b) + c;
        return this;
    }

    public HashCodeBuilder append(char[] cArr) {
        if (cArr == null) {
            this.c *= this.b;
        } else {
            for (char c : cArr) {
                append(c);
            }
        }
        return this;
    }

    public HashCodeBuilder append(double d) {
        return append(Double.doubleToLongBits(d));
    }

    public HashCodeBuilder append(double[] dArr) {
        if (dArr == null) {
            this.c *= this.b;
        } else {
            for (double d : dArr) {
                append(d);
            }
        }
        return this;
    }

    public HashCodeBuilder append(float f) {
        this.c = (this.c * this.b) + Float.floatToIntBits(f);
        return this;
    }

    public HashCodeBuilder append(float[] fArr) {
        if (fArr == null) {
            this.c *= this.b;
        } else {
            for (float f : fArr) {
                append(f);
            }
        }
        return this;
    }

    public HashCodeBuilder append(int i) {
        this.c = (this.c * this.b) + i;
        return this;
    }

    public HashCodeBuilder append(int[] iArr) {
        if (iArr == null) {
            this.c *= this.b;
        } else {
            for (int i : iArr) {
                append(i);
            }
        }
        return this;
    }

    public HashCodeBuilder append(long j) {
        this.c = (this.c * this.b) + ((int) (j ^ (j >> 32)));
        return this;
    }

    public HashCodeBuilder append(long[] jArr) {
        if (jArr == null) {
            this.c *= this.b;
        } else {
            for (long j : jArr) {
                append(j);
            }
        }
        return this;
    }

    public HashCodeBuilder append(Object obj) {
        if (obj == null) {
            this.c *= this.b;
        } else if (obj.getClass().isArray()) {
            d(obj);
        } else {
            this.c = (this.c * this.b) + obj.hashCode();
        }
        return this;
    }

    private void d(Object obj) {
        if (obj instanceof long[]) {
            append((long[]) obj);
        } else if (obj instanceof int[]) {
            append((int[]) obj);
        } else if (obj instanceof short[]) {
            append((short[]) obj);
        } else if (obj instanceof char[]) {
            append((char[]) obj);
        } else if (obj instanceof byte[]) {
            append((byte[]) obj);
        } else if (obj instanceof double[]) {
            append((double[]) obj);
        } else if (obj instanceof float[]) {
            append((float[]) obj);
        } else if (obj instanceof boolean[]) {
            append((boolean[]) obj);
        } else {
            append((Object[]) obj);
        }
    }

    public HashCodeBuilder append(Object[] objArr) {
        if (objArr == null) {
            this.c *= this.b;
        } else {
            for (Object obj : objArr) {
                append(obj);
            }
        }
        return this;
    }

    public HashCodeBuilder append(short s) {
        this.c = (this.c * this.b) + s;
        return this;
    }

    public HashCodeBuilder append(short[] sArr) {
        if (sArr == null) {
            this.c *= this.b;
        } else {
            for (short s : sArr) {
                append(s);
            }
        }
        return this;
    }

    public HashCodeBuilder appendSuper(int i) {
        this.c = (this.c * this.b) + i;
        return this;
    }

    public int toHashCode() {
        return this.c;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.lang3.builder.Builder
    public Integer build() {
        return Integer.valueOf(toHashCode());
    }

    public int hashCode() {
        return toHashCode();
    }
}
