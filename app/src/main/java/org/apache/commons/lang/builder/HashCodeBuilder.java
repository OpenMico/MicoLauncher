package org.apache.commons.lang.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;

/* loaded from: classes5.dex */
public class HashCodeBuilder {
    static Class a;
    private static final ThreadLocal b = new ThreadLocal();
    private final int c;
    private int d;

    static Set a() {
        return (Set) b.get();
    }

    static boolean a(Object obj) {
        Set a2 = a();
        return a2 != null && a2.contains(new a(obj));
    }

    private static void a(Object obj, Class cls, HashCodeBuilder hashCodeBuilder, boolean z, String[] strArr) {
        if (!a(obj)) {
            try {
                b(obj);
                Field[] declaredFields = cls.getDeclaredFields();
                AccessibleObject.setAccessible(declaredFields, true);
                for (Field field : declaredFields) {
                    if (!ArrayUtils.contains(strArr, field.getName()) && field.getName().indexOf(36) == -1 && ((z || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()))) {
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
        return reflectionHashCode(i, i2, obj, false, null, null);
    }

    public static int reflectionHashCode(int i, int i2, Object obj, boolean z) {
        return reflectionHashCode(i, i2, obj, z, null, null);
    }

    public static int reflectionHashCode(int i, int i2, Object obj, boolean z, Class cls) {
        return reflectionHashCode(i, i2, obj, z, cls, null);
    }

    public static int reflectionHashCode(int i, int i2, Object obj, boolean z, Class cls, String[] strArr) {
        if (obj != null) {
            HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(i, i2);
            Class<?> cls2 = obj.getClass();
            a(obj, cls2, hashCodeBuilder, z, strArr);
            while (cls2.getSuperclass() != null && cls2 != cls) {
                cls2 = cls2.getSuperclass();
                a(obj, cls2, hashCodeBuilder, z, strArr);
            }
            return hashCodeBuilder.toHashCode();
        }
        throw new IllegalArgumentException("The object to build a hash code for must not be null");
    }

    public static int reflectionHashCode(Object obj) {
        return reflectionHashCode(17, 37, obj, false, null, null);
    }

    public static int reflectionHashCode(Object obj, boolean z) {
        return reflectionHashCode(17, 37, obj, z, null, null);
    }

    public static int reflectionHashCode(Object obj, Collection collection) {
        return reflectionHashCode(obj, ReflectionToStringBuilder.a(collection));
    }

    public static int reflectionHashCode(Object obj, String[] strArr) {
        return reflectionHashCode(17, 37, obj, false, null, strArr);
    }

    static Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    static void b(Object obj) {
        Class cls = a;
        if (cls == null) {
            cls = a("org.apache.commons.lang.builder.HashCodeBuilder");
            a = cls;
        }
        synchronized (cls) {
            if (a() == null) {
                b.set(new HashSet());
            }
        }
        a().add(new a(obj));
    }

    static void c(Object obj) {
        Set a2 = a();
        if (a2 != null) {
            a2.remove(new a(obj));
            Class cls = a;
            if (cls == null) {
                cls = a("org.apache.commons.lang.builder.HashCodeBuilder");
                a = cls;
            }
            synchronized (cls) {
                Set a3 = a();
                if (a3 != null && a3.isEmpty()) {
                    b.set(null);
                }
            }
        }
    }

    public HashCodeBuilder() {
        this.d = 0;
        this.c = 37;
        this.d = 17;
    }

    public HashCodeBuilder(int i, int i2) {
        this.d = 0;
        if (i == 0) {
            throw new IllegalArgumentException("HashCodeBuilder requires a non zero initial value");
        } else if (i % 2 == 0) {
            throw new IllegalArgumentException("HashCodeBuilder requires an odd initial value");
        } else if (i2 == 0) {
            throw new IllegalArgumentException("HashCodeBuilder requires a non zero multiplier");
        } else if (i2 % 2 != 0) {
            this.c = i2;
            this.d = i;
        } else {
            throw new IllegalArgumentException("HashCodeBuilder requires an odd multiplier");
        }
    }

    public HashCodeBuilder append(boolean z) {
        this.d = (this.d * this.c) + (!z ? 1 : 0);
        return this;
    }

    public HashCodeBuilder append(boolean[] zArr) {
        if (zArr == null) {
            this.d *= this.c;
        } else {
            for (boolean z : zArr) {
                append(z);
            }
        }
        return this;
    }

    public HashCodeBuilder append(byte b2) {
        this.d = (this.d * this.c) + b2;
        return this;
    }

    public HashCodeBuilder append(byte[] bArr) {
        if (bArr == null) {
            this.d *= this.c;
        } else {
            for (byte b2 : bArr) {
                append(b2);
            }
        }
        return this;
    }

    public HashCodeBuilder append(char c) {
        this.d = (this.d * this.c) + c;
        return this;
    }

    public HashCodeBuilder append(char[] cArr) {
        if (cArr == null) {
            this.d *= this.c;
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
            this.d *= this.c;
        } else {
            for (double d : dArr) {
                append(d);
            }
        }
        return this;
    }

    public HashCodeBuilder append(float f) {
        this.d = (this.d * this.c) + Float.floatToIntBits(f);
        return this;
    }

    public HashCodeBuilder append(float[] fArr) {
        if (fArr == null) {
            this.d *= this.c;
        } else {
            for (float f : fArr) {
                append(f);
            }
        }
        return this;
    }

    public HashCodeBuilder append(int i) {
        this.d = (this.d * this.c) + i;
        return this;
    }

    public HashCodeBuilder append(int[] iArr) {
        if (iArr == null) {
            this.d *= this.c;
        } else {
            for (int i : iArr) {
                append(i);
            }
        }
        return this;
    }

    public HashCodeBuilder append(long j) {
        this.d = (this.d * this.c) + ((int) (j ^ (j >> 32)));
        return this;
    }

    public HashCodeBuilder append(long[] jArr) {
        if (jArr == null) {
            this.d *= this.c;
        } else {
            for (long j : jArr) {
                append(j);
            }
        }
        return this;
    }

    public HashCodeBuilder append(Object obj) {
        if (obj == null) {
            this.d *= this.c;
        } else if (!obj.getClass().isArray()) {
            this.d = (this.d * this.c) + obj.hashCode();
        } else if (obj instanceof long[]) {
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
        return this;
    }

    public HashCodeBuilder append(Object[] objArr) {
        if (objArr == null) {
            this.d *= this.c;
        } else {
            for (Object obj : objArr) {
                append(obj);
            }
        }
        return this;
    }

    public HashCodeBuilder append(short s) {
        this.d = (this.d * this.c) + s;
        return this;
    }

    public HashCodeBuilder append(short[] sArr) {
        if (sArr == null) {
            this.d *= this.c;
        } else {
            for (short s : sArr) {
                append(s);
            }
        }
        return this;
    }

    public HashCodeBuilder appendSuper(int i) {
        this.d = (this.d * this.c) + i;
        return this;
    }

    public int toHashCode() {
        return this.d;
    }

    public int hashCode() {
        return toHashCode();
    }
}
