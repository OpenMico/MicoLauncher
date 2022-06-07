package org.apache.commons.lang3.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

/* loaded from: classes5.dex */
public class EqualsBuilder implements Builder<Boolean> {
    private static final ThreadLocal<Set<Pair<a, a>>> a = new ThreadLocal<>();
    private boolean b = true;

    static Set<Pair<a, a>> a() {
        return a.get();
    }

    static Pair<a, a> a(Object obj, Object obj2) {
        return Pair.of(new a(obj), new a(obj2));
    }

    static boolean b(Object obj, Object obj2) {
        Set<Pair<a, a>> a2 = a();
        Pair<a, a> a3 = a(obj, obj2);
        return a2 != null && (a2.contains(a3) || a2.contains(Pair.of(a3.getLeft(), a3.getRight())));
    }

    private static void c(Object obj, Object obj2) {
        Set<Pair<a, a>> a2 = a();
        if (a2 == null) {
            a2 = new HashSet<>();
            a.set(a2);
        }
        a2.add(a(obj, obj2));
    }

    private static void d(Object obj, Object obj2) {
        Set<Pair<a, a>> a2 = a();
        if (a2 != null) {
            a2.remove(a(obj, obj2));
            if (a2.isEmpty()) {
                a.remove();
            }
        }
    }

    public static boolean reflectionEquals(Object obj, Object obj2, Collection<String> collection) {
        return reflectionEquals(obj, obj2, ReflectionToStringBuilder.a(collection));
    }

    public static boolean reflectionEquals(Object obj, Object obj2, String... strArr) {
        return reflectionEquals(obj, obj2, false, null, strArr);
    }

    public static boolean reflectionEquals(Object obj, Object obj2, boolean z) {
        return reflectionEquals(obj, obj2, z, null, new String[0]);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001d, code lost:
        if (r2.isInstance(r11) == false) goto L_0x002d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002a, code lost:
        if (r1.isInstance(r12) == false) goto L_0x002e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002d, code lost:
        r1 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002e, code lost:
        r10 = new org.apache.commons.lang3.builder.EqualsBuilder();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0037, code lost:
        if (r1.isArray() == false) goto L_0x003d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0039, code lost:
        r10.append(r11, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003d, code lost:
        a(r11, r12, r1, r10, r13, r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004a, code lost:
        if (r1.getSuperclass() == null) goto L_0x005c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004c, code lost:
        if (r1 == r14) goto L_0x005c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004e, code lost:
        r1 = r1.getSuperclass();
        a(r11, r12, r1, r10, r13, r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0060, code lost:
        return r10.isEquals();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0061, code lost:
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean reflectionEquals(java.lang.Object r11, java.lang.Object r12, boolean r13, java.lang.Class<?> r14, java.lang.String... r15) {
        /*
            if (r11 != r12) goto L_0x0004
            r11 = 1
            return r11
        L_0x0004:
            r0 = 0
            if (r11 == 0) goto L_0x0063
            if (r12 != 0) goto L_0x000b
            goto L_0x0063
        L_0x000b:
            java.lang.Class r1 = r11.getClass()
            java.lang.Class r2 = r12.getClass()
            boolean r3 = r1.isInstance(r12)
            if (r3 == 0) goto L_0x0020
            boolean r3 = r2.isInstance(r11)
            if (r3 != 0) goto L_0x002e
            goto L_0x002d
        L_0x0020:
            boolean r3 = r2.isInstance(r11)
            if (r3 == 0) goto L_0x0062
            boolean r3 = r1.isInstance(r12)
            if (r3 != 0) goto L_0x002d
            goto L_0x002e
        L_0x002d:
            r1 = r2
        L_0x002e:
            org.apache.commons.lang3.builder.EqualsBuilder r10 = new org.apache.commons.lang3.builder.EqualsBuilder
            r10.<init>()
            boolean r2 = r1.isArray()     // Catch: IllegalArgumentException -> 0x0061
            if (r2 == 0) goto L_0x003d
            r10.append(r11, r12)     // Catch: IllegalArgumentException -> 0x0061
            goto L_0x005c
        L_0x003d:
            r4 = r11
            r5 = r12
            r6 = r1
            r7 = r10
            r8 = r13
            r9 = r15
            a(r4, r5, r6, r7, r8, r9)     // Catch: IllegalArgumentException -> 0x0061
        L_0x0046:
            java.lang.Class r2 = r1.getSuperclass()     // Catch: IllegalArgumentException -> 0x0061
            if (r2 == 0) goto L_0x005c
            if (r1 == r14) goto L_0x005c
            java.lang.Class r1 = r1.getSuperclass()     // Catch: IllegalArgumentException -> 0x0061
            r2 = r11
            r3 = r12
            r4 = r1
            r5 = r10
            r6 = r13
            r7 = r15
            a(r2, r3, r4, r5, r6, r7)     // Catch: IllegalArgumentException -> 0x0061
            goto L_0x0046
        L_0x005c:
            boolean r11 = r10.isEquals()
            return r11
        L_0x0061:
            return r0
        L_0x0062:
            return r0
        L_0x0063:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals(java.lang.Object, java.lang.Object, boolean, java.lang.Class, java.lang.String[]):boolean");
    }

    private static void a(Object obj, Object obj2, Class<?> cls, EqualsBuilder equalsBuilder, boolean z, String[] strArr) {
        if (!b(obj, obj2)) {
            try {
                c(obj, obj2);
                Field[] declaredFields = cls.getDeclaredFields();
                AccessibleObject.setAccessible(declaredFields, true);
                for (int i = 0; i < declaredFields.length && equalsBuilder.b; i++) {
                    Field field = declaredFields[i];
                    if (!ArrayUtils.contains(strArr, field.getName()) && !field.getName().contains("$") && ((z || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(EqualsExclude.class))) {
                        try {
                            equalsBuilder.append(field.get(obj), field.get(obj2));
                        } catch (IllegalAccessException unused) {
                            throw new InternalError("Unexpected IllegalAccessException");
                        }
                    }
                }
            } finally {
                d(obj, obj2);
            }
        }
    }

    public EqualsBuilder appendSuper(boolean z) {
        if (!this.b) {
            return this;
        }
        this.b = z;
        return this;
    }

    public EqualsBuilder append(Object obj, Object obj2) {
        if (!this.b || obj == obj2) {
            return this;
        }
        if (obj == null || obj2 == null) {
            setEquals(false);
            return this;
        }
        if (!obj.getClass().isArray()) {
            this.b = obj.equals(obj2);
        } else {
            e(obj, obj2);
        }
        return this;
    }

    private void e(Object obj, Object obj2) {
        if (obj.getClass() != obj2.getClass()) {
            setEquals(false);
        } else if (obj instanceof long[]) {
            append((long[]) obj, (long[]) obj2);
        } else if (obj instanceof int[]) {
            append((int[]) obj, (int[]) obj2);
        } else if (obj instanceof short[]) {
            append((short[]) obj, (short[]) obj2);
        } else if (obj instanceof char[]) {
            append((char[]) obj, (char[]) obj2);
        } else if (obj instanceof byte[]) {
            append((byte[]) obj, (byte[]) obj2);
        } else if (obj instanceof double[]) {
            append((double[]) obj, (double[]) obj2);
        } else if (obj instanceof float[]) {
            append((float[]) obj, (float[]) obj2);
        } else if (obj instanceof boolean[]) {
            append((boolean[]) obj, (boolean[]) obj2);
        } else {
            append((Object[]) obj, (Object[]) obj2);
        }
    }

    public EqualsBuilder append(long j, long j2) {
        if (!this.b) {
            return this;
        }
        this.b = j == j2;
        return this;
    }

    public EqualsBuilder append(int i, int i2) {
        if (!this.b) {
            return this;
        }
        this.b = i == i2;
        return this;
    }

    public EqualsBuilder append(short s, short s2) {
        if (!this.b) {
            return this;
        }
        this.b = s == s2;
        return this;
    }

    public EqualsBuilder append(char c, char c2) {
        if (!this.b) {
            return this;
        }
        this.b = c == c2;
        return this;
    }

    public EqualsBuilder append(byte b, byte b2) {
        if (!this.b) {
            return this;
        }
        this.b = b == b2;
        return this;
    }

    public EqualsBuilder append(double d, double d2) {
        return !this.b ? this : append(Double.doubleToLongBits(d), Double.doubleToLongBits(d2));
    }

    public EqualsBuilder append(float f, float f2) {
        return !this.b ? this : append(Float.floatToIntBits(f), Float.floatToIntBits(f2));
    }

    public EqualsBuilder append(boolean z, boolean z2) {
        if (!this.b) {
            return this;
        }
        this.b = z == z2;
        return this;
    }

    public EqualsBuilder append(Object[] objArr, Object[] objArr2) {
        if (!this.b || objArr == objArr2) {
            return this;
        }
        if (objArr == null || objArr2 == null) {
            setEquals(false);
            return this;
        } else if (objArr.length != objArr2.length) {
            setEquals(false);
            return this;
        } else {
            for (int i = 0; i < objArr.length && this.b; i++) {
                append(objArr[i], objArr2[i]);
            }
            return this;
        }
    }

    public EqualsBuilder append(long[] jArr, long[] jArr2) {
        if (!this.b || jArr == jArr2) {
            return this;
        }
        if (jArr == null || jArr2 == null) {
            setEquals(false);
            return this;
        } else if (jArr.length != jArr2.length) {
            setEquals(false);
            return this;
        } else {
            for (int i = 0; i < jArr.length && this.b; i++) {
                append(jArr[i], jArr2[i]);
            }
            return this;
        }
    }

    public EqualsBuilder append(int[] iArr, int[] iArr2) {
        if (!this.b || iArr == iArr2) {
            return this;
        }
        if (iArr == null || iArr2 == null) {
            setEquals(false);
            return this;
        } else if (iArr.length != iArr2.length) {
            setEquals(false);
            return this;
        } else {
            for (int i = 0; i < iArr.length && this.b; i++) {
                append(iArr[i], iArr2[i]);
            }
            return this;
        }
    }

    public EqualsBuilder append(short[] sArr, short[] sArr2) {
        if (!this.b || sArr == sArr2) {
            return this;
        }
        if (sArr == null || sArr2 == null) {
            setEquals(false);
            return this;
        } else if (sArr.length != sArr2.length) {
            setEquals(false);
            return this;
        } else {
            for (int i = 0; i < sArr.length && this.b; i++) {
                append(sArr[i], sArr2[i]);
            }
            return this;
        }
    }

    public EqualsBuilder append(char[] cArr, char[] cArr2) {
        if (!this.b || cArr == cArr2) {
            return this;
        }
        if (cArr == null || cArr2 == null) {
            setEquals(false);
            return this;
        } else if (cArr.length != cArr2.length) {
            setEquals(false);
            return this;
        } else {
            for (int i = 0; i < cArr.length && this.b; i++) {
                append(cArr[i], cArr2[i]);
            }
            return this;
        }
    }

    public EqualsBuilder append(byte[] bArr, byte[] bArr2) {
        if (!this.b || bArr == bArr2) {
            return this;
        }
        if (bArr == null || bArr2 == null) {
            setEquals(false);
            return this;
        } else if (bArr.length != bArr2.length) {
            setEquals(false);
            return this;
        } else {
            for (int i = 0; i < bArr.length && this.b; i++) {
                append(bArr[i], bArr2[i]);
            }
            return this;
        }
    }

    public EqualsBuilder append(double[] dArr, double[] dArr2) {
        if (!this.b || dArr == dArr2) {
            return this;
        }
        if (dArr == null || dArr2 == null) {
            setEquals(false);
            return this;
        } else if (dArr.length != dArr2.length) {
            setEquals(false);
            return this;
        } else {
            for (int i = 0; i < dArr.length && this.b; i++) {
                append(dArr[i], dArr2[i]);
            }
            return this;
        }
    }

    public EqualsBuilder append(float[] fArr, float[] fArr2) {
        if (!this.b || fArr == fArr2) {
            return this;
        }
        if (fArr == null || fArr2 == null) {
            setEquals(false);
            return this;
        } else if (fArr.length != fArr2.length) {
            setEquals(false);
            return this;
        } else {
            for (int i = 0; i < fArr.length && this.b; i++) {
                append(fArr[i], fArr2[i]);
            }
            return this;
        }
    }

    public EqualsBuilder append(boolean[] zArr, boolean[] zArr2) {
        if (!this.b || zArr == zArr2) {
            return this;
        }
        if (zArr == null || zArr2 == null) {
            setEquals(false);
            return this;
        } else if (zArr.length != zArr2.length) {
            setEquals(false);
            return this;
        } else {
            for (int i = 0; i < zArr.length && this.b; i++) {
                append(zArr[i], zArr2[i]);
            }
            return this;
        }
    }

    public boolean isEquals() {
        return this.b;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.lang3.builder.Builder
    public Boolean build() {
        return Boolean.valueOf(isEquals());
    }

    protected void setEquals(boolean z) {
        this.b = z;
    }

    public void reset() {
        this.b = true;
    }
}
