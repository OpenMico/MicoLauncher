package com.blankj.utilcode.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class ArrayUtils {
    public static final int INDEX_NOT_FOUND = -1;

    /* loaded from: classes.dex */
    public interface Closure<E> {
        void execute(int i, E e);
    }

    private ArrayUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @NonNull
    public static <T> T[] newArray(T... tArr) {
        if (tArr != null) {
            return tArr;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.newArray() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static long[] newLongArray(long... jArr) {
        if (jArr != null) {
            return jArr;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.newLongArray() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static int[] newIntArray(int... iArr) {
        if (iArr != null) {
            return iArr;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.newIntArray() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static short[] newShortArray(short... sArr) {
        if (sArr != null) {
            return sArr;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.newShortArray() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static char[] newCharArray(char... cArr) {
        if (cArr != null) {
            return cArr;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.newCharArray() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static byte[] newByteArray(byte... bArr) {
        if (bArr != null) {
            return bArr;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.newByteArray() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static double[] newDoubleArray(double... dArr) {
        if (dArr != null) {
            return dArr;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.newDoubleArray() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static float[] newFloatArray(float... fArr) {
        if (fArr != null) {
            return fArr;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.newFloatArray() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static boolean[] newBooleanArray(boolean... zArr) {
        if (zArr != null) {
            return zArr;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.newBooleanArray() marked by @androidx.annotation.NonNull");
    }

    public static boolean isEmpty(@Nullable Object obj) {
        return getLength(obj) == 0;
    }

    public static int getLength(@Nullable Object obj) {
        if (obj == null) {
            return 0;
        }
        return Array.getLength(obj);
    }

    public static boolean isSameLength(@Nullable Object obj, @Nullable Object obj2) {
        return getLength(obj) == getLength(obj2);
    }

    @Nullable
    public static Object get(@Nullable Object obj, int i) {
        return get(obj, i, null);
    }

    @Nullable
    public static Object get(@Nullable Object obj, int i, @Nullable Object obj2) {
        if (obj == null) {
            return obj2;
        }
        try {
            return Array.get(obj, i);
        } catch (Exception unused) {
            return obj2;
        }
    }

    public static void set(@Nullable Object obj, int i, @Nullable Object obj2) {
        if (obj != null) {
            Array.set(obj, i, obj2);
        }
    }

    public static boolean equals(@Nullable Object[] objArr, @Nullable Object[] objArr2) {
        return Arrays.deepEquals(objArr, objArr2);
    }

    public static boolean equals(boolean[] zArr, boolean[] zArr2) {
        return Arrays.equals(zArr, zArr2);
    }

    public static boolean equals(byte[] bArr, byte[] bArr2) {
        return Arrays.equals(bArr, bArr2);
    }

    public static boolean equals(char[] cArr, char[] cArr2) {
        return Arrays.equals(cArr, cArr2);
    }

    public static boolean equals(double[] dArr, double[] dArr2) {
        return Arrays.equals(dArr, dArr2);
    }

    public static boolean equals(float[] fArr, float[] fArr2) {
        return Arrays.equals(fArr, fArr2);
    }

    public static boolean equals(int[] iArr, int[] iArr2) {
        return Arrays.equals(iArr, iArr2);
    }

    public static boolean equals(short[] sArr, short[] sArr2) {
        return Arrays.equals(sArr, sArr2);
    }

    public static <T> void reverse(T[] tArr) {
        if (tArr != null) {
            int length = tArr.length - 1;
            for (int i = 0; length > i; i++) {
                T t = tArr[length];
                tArr[length] = tArr[i];
                tArr[i] = t;
                length--;
            }
        }
    }

    public static void reverse(long[] jArr) {
        if (jArr != null) {
            int length = jArr.length - 1;
            for (int i = 0; length > i; i++) {
                long j = jArr[length];
                jArr[length] = jArr[i];
                jArr[i] = j;
                length--;
            }
        }
    }

    public static void reverse(int[] iArr) {
        if (iArr != null) {
            int length = iArr.length - 1;
            for (int i = 0; length > i; i++) {
                int i2 = iArr[length];
                iArr[length] = iArr[i];
                iArr[i] = i2;
                length--;
            }
        }
    }

    public static void reverse(short[] sArr) {
        if (sArr != null) {
            int length = sArr.length - 1;
            for (int i = 0; length > i; i++) {
                short s = sArr[length];
                sArr[length] = sArr[i];
                sArr[i] = s;
                length--;
            }
        }
    }

    public static void reverse(char[] cArr) {
        if (cArr != null) {
            int length = cArr.length - 1;
            for (int i = 0; length > i; i++) {
                char c = cArr[length];
                cArr[length] = cArr[i];
                cArr[i] = c;
                length--;
            }
        }
    }

    public static void reverse(byte[] bArr) {
        if (bArr != null) {
            int length = bArr.length - 1;
            for (int i = 0; length > i; i++) {
                byte b = bArr[length];
                bArr[length] = bArr[i];
                bArr[i] = b;
                length--;
            }
        }
    }

    public static void reverse(double[] dArr) {
        if (dArr != null) {
            int length = dArr.length - 1;
            for (int i = 0; length > i; i++) {
                double d = dArr[length];
                dArr[length] = dArr[i];
                dArr[i] = d;
                length--;
            }
        }
    }

    public static void reverse(float[] fArr) {
        if (fArr != null) {
            int length = fArr.length - 1;
            for (int i = 0; length > i; i++) {
                float f = fArr[length];
                fArr[length] = fArr[i];
                fArr[i] = f;
                length--;
            }
        }
    }

    public static void reverse(boolean[] zArr) {
        if (zArr != null) {
            int length = zArr.length - 1;
            for (int i = 0; length > i; i++) {
                boolean z = zArr[length];
                zArr[length] = zArr[i];
                zArr[i] = z;
                length--;
            }
        }
    }

    @Nullable
    public static <T> T[] copy(@Nullable T[] tArr) {
        if (tArr == null) {
            return null;
        }
        return (T[]) subArray(tArr, 0, tArr.length);
    }

    @Nullable
    public static long[] copy(@Nullable long[] jArr) {
        if (jArr == null) {
            return null;
        }
        return subArray(jArr, 0, jArr.length);
    }

    @Nullable
    public static int[] copy(@Nullable int[] iArr) {
        if (iArr == null) {
            return null;
        }
        return subArray(iArr, 0, iArr.length);
    }

    @Nullable
    public static short[] copy(@Nullable short[] sArr) {
        if (sArr == null) {
            return null;
        }
        return subArray(sArr, 0, sArr.length);
    }

    @Nullable
    public static char[] copy(@Nullable char[] cArr) {
        if (cArr == null) {
            return null;
        }
        return subArray(cArr, 0, cArr.length);
    }

    @Nullable
    public static byte[] copy(@Nullable byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return subArray(bArr, 0, bArr.length);
    }

    @Nullable
    public static double[] copy(@Nullable double[] dArr) {
        if (dArr == null) {
            return null;
        }
        return subArray(dArr, 0, dArr.length);
    }

    @Nullable
    public static float[] copy(@Nullable float[] fArr) {
        if (fArr == null) {
            return null;
        }
        return subArray(fArr, 0, fArr.length);
    }

    @Nullable
    public static boolean[] copy(@Nullable boolean[] zArr) {
        if (zArr == null) {
            return null;
        }
        return subArray(zArr, 0, zArr.length);
    }

    @Nullable
    private static Object a(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        return a(obj, 0, getLength(obj));
    }

    @Nullable
    public static <T> T[] subArray(@Nullable T[] tArr, int i, int i2) {
        return (T[]) ((Object[]) a(tArr, i, i2));
    }

    @Nullable
    public static long[] subArray(@Nullable long[] jArr, int i, int i2) {
        return (long[]) a(jArr, i, i2);
    }

    @Nullable
    public static int[] subArray(@Nullable int[] iArr, int i, int i2) {
        return (int[]) a(iArr, i, i2);
    }

    @Nullable
    public static short[] subArray(@Nullable short[] sArr, int i, int i2) {
        return (short[]) a(sArr, i, i2);
    }

    @Nullable
    public static char[] subArray(@Nullable char[] cArr, int i, int i2) {
        return (char[]) a(cArr, i, i2);
    }

    @Nullable
    public static byte[] subArray(@Nullable byte[] bArr, int i, int i2) {
        return (byte[]) a(bArr, i, i2);
    }

    @Nullable
    public static double[] subArray(@Nullable double[] dArr, int i, int i2) {
        return (double[]) a(dArr, i, i2);
    }

    @Nullable
    public static float[] subArray(@Nullable float[] fArr, int i, int i2) {
        return (float[]) a(fArr, i, i2);
    }

    @Nullable
    public static boolean[] subArray(@Nullable boolean[] zArr, int i, int i2) {
        return (boolean[]) a(zArr, i, i2);
    }

    @Nullable
    private static Object a(@Nullable Object obj, int i, int i2) {
        if (obj == null) {
            return null;
        }
        if (i < 0) {
            i = 0;
        }
        int length = getLength(obj);
        if (i2 > length) {
            i2 = length;
        }
        int i3 = i2 - i;
        Class<?> componentType = obj.getClass().getComponentType();
        if (i3 <= 0) {
            return Array.newInstance(componentType, 0);
        }
        Object newInstance = Array.newInstance(componentType, i3);
        System.arraycopy(obj, i, newInstance, 0, i3);
        return newInstance;
    }

    @NonNull
    public static <T> T[] add(@Nullable T[] tArr, @Nullable T t) {
        Class cls;
        if (tArr != null) {
            cls = tArr.getClass();
        } else {
            cls = t != null ? t.getClass() : Object.class;
        }
        T[] tArr2 = (T[]) ((Object[]) a(tArr, t, cls));
        if (tArr2 != null) {
            return tArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static boolean[] add(@Nullable boolean[] zArr, boolean z) {
        boolean[] zArr2 = (boolean[]) a(zArr, Boolean.valueOf(z), Boolean.TYPE);
        if (zArr2 != null) {
            return zArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static byte[] add(@Nullable byte[] bArr, byte b) {
        byte[] bArr2 = (byte[]) a(bArr, Byte.valueOf(b), Byte.TYPE);
        if (bArr2 != null) {
            return bArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static char[] add(@Nullable char[] cArr, char c) {
        char[] cArr2 = (char[]) a(cArr, Character.valueOf(c), Character.TYPE);
        if (cArr2 != null) {
            return cArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static double[] add(@Nullable double[] dArr, double d) {
        double[] dArr2 = (double[]) a(dArr, Double.valueOf(d), Double.TYPE);
        if (dArr2 != null) {
            return dArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static float[] add(@Nullable float[] fArr, float f) {
        float[] fArr2 = (float[]) a(fArr, Float.valueOf(f), Float.TYPE);
        if (fArr2 != null) {
            return fArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static int[] add(@Nullable int[] iArr, int i) {
        int[] iArr2 = (int[]) a(iArr, Integer.valueOf(i), Integer.TYPE);
        if (iArr2 != null) {
            return iArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static long[] add(@Nullable long[] jArr, long j) {
        long[] jArr2 = (long[]) a(jArr, Long.valueOf(j), Long.TYPE);
        if (jArr2 != null) {
            return jArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static short[] add(@Nullable short[] sArr, short s) {
        short[] sArr2 = (short[]) a(sArr, Short.valueOf(s), Short.TYPE);
        if (sArr2 != null) {
            return sArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    private static Object a(@Nullable Object obj, @Nullable Object obj2, Class cls) {
        int i;
        Object obj3;
        if (obj != null) {
            i = getLength(obj);
            obj3 = Array.newInstance(obj.getClass().getComponentType(), i + 1);
            System.arraycopy(obj, 0, obj3, 0, i);
        } else {
            obj3 = Array.newInstance(cls, 1);
            i = 0;
        }
        Array.set(obj3, i, obj2);
        if (obj3 != null) {
            return obj3;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.realAddOne() marked by @androidx.annotation.NonNull");
    }

    @Nullable
    public static <T> T[] add(@Nullable T[] tArr, @Nullable T[] tArr2) {
        return (T[]) ((Object[]) a(tArr, tArr2));
    }

    @Nullable
    public static boolean[] add(@Nullable boolean[] zArr, @Nullable boolean[] zArr2) {
        return (boolean[]) a(zArr, zArr2);
    }

    @Nullable
    public static char[] add(@Nullable char[] cArr, @Nullable char[] cArr2) {
        return (char[]) a(cArr, cArr2);
    }

    @Nullable
    public static byte[] add(@Nullable byte[] bArr, @Nullable byte[] bArr2) {
        return (byte[]) a(bArr, bArr2);
    }

    @Nullable
    public static short[] add(@Nullable short[] sArr, @Nullable short[] sArr2) {
        return (short[]) a(sArr, sArr2);
    }

    @Nullable
    public static int[] add(@Nullable int[] iArr, @Nullable int[] iArr2) {
        return (int[]) a(iArr, iArr2);
    }

    @Nullable
    public static long[] add(@Nullable long[] jArr, @Nullable long[] jArr2) {
        return (long[]) a(jArr, jArr2);
    }

    @Nullable
    public static float[] add(@Nullable float[] fArr, @Nullable float[] fArr2) {
        return (float[]) a(fArr, fArr2);
    }

    @Nullable
    public static double[] add(@Nullable double[] dArr, @Nullable double[] dArr2) {
        return (double[]) a(dArr, dArr2);
    }

    private static Object a(@Nullable Object obj, @Nullable Object obj2) {
        if (obj == null && obj2 == null) {
            return null;
        }
        if (obj == null) {
            return a(obj2);
        }
        if (obj2 == null) {
            return a(obj);
        }
        int length = getLength(obj);
        int length2 = getLength(obj2);
        Object newInstance = Array.newInstance(obj.getClass().getComponentType(), length + length2);
        System.arraycopy(obj, 0, newInstance, 0, length);
        System.arraycopy(obj2, 0, newInstance, length, length2);
        return newInstance;
    }

    @Nullable
    public static <T> T[] add(@Nullable T[] tArr, int i, @Nullable T[] tArr2) {
        Class<?> cls;
        if (tArr != null) {
            cls = tArr.getClass().getComponentType();
        } else if (tArr2 == null) {
            return null;
        } else {
            cls = tArr2.getClass().getComponentType();
        }
        return (T[]) ((Object[]) a(tArr, i, tArr2, cls));
    }

    @Nullable
    public static boolean[] add(@Nullable boolean[] zArr, int i, @Nullable boolean[] zArr2) {
        Object a = a(zArr, i, zArr2, Boolean.TYPE);
        if (a == null) {
            return null;
        }
        return (boolean[]) a;
    }

    public static char[] add(@Nullable char[] cArr, int i, @Nullable char[] cArr2) {
        Object a = a(cArr, i, cArr2, Character.TYPE);
        if (a == null) {
            return null;
        }
        return (char[]) a;
    }

    @Nullable
    public static byte[] add(@Nullable byte[] bArr, int i, @Nullable byte[] bArr2) {
        Object a = a(bArr, i, bArr2, Byte.TYPE);
        if (a == null) {
            return null;
        }
        return (byte[]) a;
    }

    @Nullable
    public static short[] add(@Nullable short[] sArr, int i, @Nullable short[] sArr2) {
        Object a = a(sArr, i, sArr2, Short.TYPE);
        if (a == null) {
            return null;
        }
        return (short[]) a;
    }

    @Nullable
    public static int[] add(@Nullable int[] iArr, int i, @Nullable int[] iArr2) {
        Object a = a(iArr, i, iArr2, Integer.TYPE);
        if (a == null) {
            return null;
        }
        return (int[]) a;
    }

    @Nullable
    public static long[] add(@Nullable long[] jArr, int i, @Nullable long[] jArr2) {
        Object a = a(jArr, i, jArr2, Long.TYPE);
        if (a == null) {
            return null;
        }
        return (long[]) a;
    }

    @Nullable
    public static float[] add(@Nullable float[] fArr, int i, @Nullable float[] fArr2) {
        Object a = a(fArr, i, fArr2, Float.TYPE);
        if (a == null) {
            return null;
        }
        return (float[]) a;
    }

    @Nullable
    public static double[] add(@Nullable double[] dArr, int i, @Nullable double[] dArr2) {
        Object a = a(dArr, i, dArr2, Double.TYPE);
        if (a == null) {
            return null;
        }
        return (double[]) a;
    }

    @Nullable
    private static Object a(@Nullable Object obj, int i, @Nullable Object obj2, Class cls) {
        if (obj == null && obj2 == null) {
            return null;
        }
        int length = getLength(obj);
        int length2 = getLength(obj2);
        if (length == 0) {
            if (i == 0) {
                return a(obj2);
            }
            throw new IndexOutOfBoundsException("Index: " + i + ", array1 Length: 0");
        } else if (length2 == 0) {
            return a(obj);
        } else {
            if (i > length || i < 0) {
                throw new IndexOutOfBoundsException("Index: " + i + ", array1 Length: " + length);
            }
            Object newInstance = Array.newInstance(obj.getClass().getComponentType(), length + length2);
            if (i == length) {
                System.arraycopy(obj, 0, newInstance, 0, length);
                System.arraycopy(obj2, 0, newInstance, length, length2);
            } else if (i == 0) {
                System.arraycopy(obj2, 0, newInstance, 0, length2);
                System.arraycopy(obj, 0, newInstance, length2, length);
            } else {
                System.arraycopy(obj, 0, newInstance, 0, i);
                System.arraycopy(obj2, 0, newInstance, i, length2);
                System.arraycopy(obj, i, newInstance, length2 + i, length - i);
            }
            return newInstance;
        }
    }

    @NonNull
    public static <T> T[] add(@Nullable T[] tArr, int i, @Nullable T t) {
        Class<?> cls;
        if (tArr != null) {
            cls = tArr.getClass().getComponentType();
        } else if (t == null) {
            return (T[]) new Object[]{null};
        } else {
            cls = t.getClass();
        }
        T[] tArr2 = (T[]) ((Object[]) b(tArr, i, t, cls));
        if (tArr2 != null) {
            return tArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static boolean[] add(@Nullable boolean[] zArr, int i, boolean z) {
        boolean[] zArr2 = (boolean[]) b(zArr, i, Boolean.valueOf(z), Boolean.TYPE);
        if (zArr2 != null) {
            return zArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static char[] add(@Nullable char[] cArr, int i, char c) {
        char[] cArr2 = (char[]) b(cArr, i, Character.valueOf(c), Character.TYPE);
        if (cArr2 != null) {
            return cArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static byte[] add(@Nullable byte[] bArr, int i, byte b) {
        byte[] bArr2 = (byte[]) b(bArr, i, Byte.valueOf(b), Byte.TYPE);
        if (bArr2 != null) {
            return bArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static short[] add(@Nullable short[] sArr, int i, short s) {
        short[] sArr2 = (short[]) b(sArr, i, Short.valueOf(s), Short.TYPE);
        if (sArr2 != null) {
            return sArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static int[] add(@Nullable int[] iArr, int i, int i2) {
        int[] iArr2 = (int[]) b(iArr, i, Integer.valueOf(i2), Integer.TYPE);
        if (iArr2 != null) {
            return iArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static long[] add(@Nullable long[] jArr, int i, long j) {
        long[] jArr2 = (long[]) b(jArr, i, Long.valueOf(j), Long.TYPE);
        if (jArr2 != null) {
            return jArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static float[] add(@Nullable float[] fArr, int i, float f) {
        float[] fArr2 = (float[]) b(fArr, i, Float.valueOf(f), Float.TYPE);
        if (fArr2 != null) {
            return fArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static double[] add(@Nullable double[] dArr, int i, double d) {
        double[] dArr2 = (double[]) b(dArr, i, Double.valueOf(d), Double.TYPE);
        if (dArr2 != null) {
            return dArr2;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.add() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    private static Object b(@Nullable Object obj, int i, @Nullable Object obj2, Class cls) {
        if (obj != null) {
            int length = Array.getLength(obj);
            if (i > length || i < 0) {
                throw new IndexOutOfBoundsException("Index: " + i + ", Length: " + length);
            }
            Object newInstance = Array.newInstance(cls, length + 1);
            System.arraycopy(obj, 0, newInstance, 0, i);
            Array.set(newInstance, i, obj2);
            if (i < length) {
                System.arraycopy(obj, i, newInstance, i + 1, length - i);
            }
            if (newInstance != null) {
                return newInstance;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.realAdd() marked by @androidx.annotation.NonNull");
        } else if (i == 0) {
            Object newInstance2 = Array.newInstance(cls, 1);
            Array.set(newInstance2, 0, obj2);
            if (newInstance2 != null) {
                return newInstance2;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.realAdd() marked by @androidx.annotation.NonNull");
        } else {
            throw new IndexOutOfBoundsException("Index: " + i + ", Length: 0");
        }
    }

    @Nullable
    public static Object[] remove(@Nullable Object[] objArr, int i) {
        if (objArr == null) {
            return null;
        }
        return (Object[]) a(objArr, i);
    }

    @Nullable
    public static Object[] removeElement(@Nullable Object[] objArr, @Nullable Object obj) {
        int indexOf = indexOf(objArr, obj);
        if (indexOf == -1) {
            return copy(objArr);
        }
        return remove(objArr, indexOf);
    }

    @Nullable
    public static boolean[] remove(@Nullable boolean[] zArr, int i) {
        if (zArr == null) {
            return null;
        }
        return (boolean[]) a(zArr, i);
    }

    @Nullable
    public static boolean[] removeElement(@Nullable boolean[] zArr, boolean z) {
        int indexOf = indexOf(zArr, z);
        if (indexOf == -1) {
            return copy(zArr);
        }
        return remove(zArr, indexOf);
    }

    @Nullable
    public static byte[] remove(@Nullable byte[] bArr, int i) {
        if (bArr == null) {
            return null;
        }
        return (byte[]) a(bArr, i);
    }

    @Nullable
    public static byte[] removeElement(@Nullable byte[] bArr, byte b) {
        int indexOf = indexOf(bArr, b);
        if (indexOf == -1) {
            return copy(bArr);
        }
        return remove(bArr, indexOf);
    }

    @Nullable
    public static char[] remove(@Nullable char[] cArr, int i) {
        if (cArr == null) {
            return null;
        }
        return (char[]) a(cArr, i);
    }

    @Nullable
    public static char[] removeElement(@Nullable char[] cArr, char c) {
        int indexOf = indexOf(cArr, c);
        if (indexOf == -1) {
            return copy(cArr);
        }
        return remove(cArr, indexOf);
    }

    @Nullable
    public static double[] remove(@Nullable double[] dArr, int i) {
        if (dArr == null) {
            return null;
        }
        return (double[]) a(dArr, i);
    }

    @Nullable
    public static double[] removeElement(@Nullable double[] dArr, double d) {
        int indexOf = indexOf(dArr, d);
        if (indexOf == -1) {
            return copy(dArr);
        }
        return remove(dArr, indexOf);
    }

    @Nullable
    public static float[] remove(@Nullable float[] fArr, int i) {
        if (fArr == null) {
            return null;
        }
        return (float[]) a(fArr, i);
    }

    @Nullable
    public static float[] removeElement(@Nullable float[] fArr, float f) {
        int indexOf = indexOf(fArr, f);
        if (indexOf == -1) {
            return copy(fArr);
        }
        return remove(fArr, indexOf);
    }

    @Nullable
    public static int[] remove(@Nullable int[] iArr, int i) {
        if (iArr == null) {
            return null;
        }
        return (int[]) a(iArr, i);
    }

    @Nullable
    public static int[] removeElement(@Nullable int[] iArr, int i) {
        int indexOf = indexOf(iArr, i);
        if (indexOf == -1) {
            return copy(iArr);
        }
        return remove(iArr, indexOf);
    }

    @Nullable
    public static long[] remove(@Nullable long[] jArr, int i) {
        if (jArr == null) {
            return null;
        }
        return (long[]) a(jArr, i);
    }

    @Nullable
    public static long[] removeElement(@Nullable long[] jArr, long j) {
        int indexOf = indexOf(jArr, j);
        if (indexOf == -1) {
            return copy(jArr);
        }
        return remove(jArr, indexOf);
    }

    @Nullable
    public static short[] remove(@Nullable short[] sArr, int i) {
        if (sArr == null) {
            return null;
        }
        return (short[]) a(sArr, i);
    }

    @Nullable
    public static short[] removeElement(@Nullable short[] sArr, short s) {
        int indexOf = indexOf(sArr, s);
        if (indexOf == -1) {
            return copy(sArr);
        }
        return remove(sArr, indexOf);
    }

    @NonNull
    private static Object a(@NonNull Object obj, int i) {
        if (obj != null) {
            int length = getLength(obj);
            if (i < 0 || i >= length) {
                throw new IndexOutOfBoundsException("Index: " + i + ", Length: " + length);
            }
            int i2 = length - 1;
            Object newInstance = Array.newInstance(obj.getClass().getComponentType(), i2);
            System.arraycopy(obj, 0, newInstance, 0, i);
            if (i < i2) {
                System.arraycopy(obj, i + 1, newInstance, i, (length - i) - 1);
            }
            if (newInstance != null) {
                return newInstance;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.remove() marked by @androidx.annotation.NonNull");
        }
        throw new NullPointerException("Argument 'array' of type Object (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static int indexOf(@Nullable Object[] objArr, @Nullable Object obj) {
        return indexOf(objArr, obj, 0);
    }

    public static int indexOf(@Nullable Object[] objArr, @Nullable Object obj, int i) {
        if (objArr == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        if (obj == null) {
            while (i < objArr.length) {
                if (objArr[i] == null) {
                    return i;
                }
                i++;
            }
        } else {
            while (i < objArr.length) {
                if (obj.equals(objArr[i])) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable Object[] objArr, @Nullable Object obj) {
        return lastIndexOf(objArr, obj, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable Object[] objArr, @Nullable Object obj, int i) {
        if (objArr == null || i < 0) {
            return -1;
        }
        if (i >= objArr.length) {
            i = objArr.length - 1;
        }
        if (obj == null) {
            while (i >= 0) {
                if (objArr[i] == null) {
                    return i;
                }
                i--;
            }
        } else {
            while (i >= 0) {
                if (obj.equals(objArr[i])) {
                    return i;
                }
                i--;
            }
        }
        return -1;
    }

    public static boolean contains(@Nullable Object[] objArr, @Nullable Object obj) {
        return indexOf(objArr, obj) != -1;
    }

    public static int indexOf(@Nullable long[] jArr, long j) {
        return indexOf(jArr, j, 0);
    }

    public static int indexOf(@Nullable long[] jArr, long j, int i) {
        if (jArr == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        while (i < jArr.length) {
            if (j == jArr[i]) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable long[] jArr, long j) {
        return lastIndexOf(jArr, j, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable long[] jArr, long j, int i) {
        if (jArr == null || i < 0) {
            return -1;
        }
        if (i >= jArr.length) {
            i = jArr.length - 1;
        }
        while (i >= 0) {
            if (j == jArr[i]) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public static boolean contains(@Nullable long[] jArr, long j) {
        return indexOf(jArr, j) != -1;
    }

    public static int indexOf(@Nullable int[] iArr, int i) {
        return indexOf(iArr, i, 0);
    }

    public static int indexOf(@Nullable int[] iArr, int i, int i2) {
        if (iArr == null) {
            return -1;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        while (i2 < iArr.length) {
            if (i == iArr[i2]) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable int[] iArr, int i) {
        return lastIndexOf(iArr, i, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable int[] iArr, int i, int i2) {
        if (iArr == null || i2 < 0) {
            return -1;
        }
        if (i2 >= iArr.length) {
            i2 = iArr.length - 1;
        }
        while (i2 >= 0) {
            if (i == iArr[i2]) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    public static boolean contains(@Nullable int[] iArr, int i) {
        return indexOf(iArr, i) != -1;
    }

    public static int indexOf(@Nullable short[] sArr, short s) {
        return indexOf(sArr, s, 0);
    }

    public static int indexOf(@Nullable short[] sArr, short s, int i) {
        if (sArr == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        while (i < sArr.length) {
            if (s == sArr[i]) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable short[] sArr, short s) {
        return lastIndexOf(sArr, s, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable short[] sArr, short s, int i) {
        if (sArr == null || i < 0) {
            return -1;
        }
        if (i >= sArr.length) {
            i = sArr.length - 1;
        }
        while (i >= 0) {
            if (s == sArr[i]) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public static boolean contains(@Nullable short[] sArr, short s) {
        return indexOf(sArr, s) != -1;
    }

    public static int indexOf(@Nullable char[] cArr, char c) {
        return indexOf(cArr, c, 0);
    }

    public static int indexOf(@Nullable char[] cArr, char c, int i) {
        if (cArr == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        while (i < cArr.length) {
            if (c == cArr[i]) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable char[] cArr, char c) {
        return lastIndexOf(cArr, c, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable char[] cArr, char c, int i) {
        if (cArr == null || i < 0) {
            return -1;
        }
        if (i >= cArr.length) {
            i = cArr.length - 1;
        }
        while (i >= 0) {
            if (c == cArr[i]) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public static boolean contains(@Nullable char[] cArr, char c) {
        return indexOf(cArr, c) != -1;
    }

    public static int indexOf(@Nullable byte[] bArr, byte b) {
        return indexOf(bArr, b, 0);
    }

    public static int indexOf(@Nullable byte[] bArr, byte b, int i) {
        if (bArr == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        while (i < bArr.length) {
            if (b == bArr[i]) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable byte[] bArr, byte b) {
        return lastIndexOf(bArr, b, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable byte[] bArr, byte b, int i) {
        if (bArr == null || i < 0) {
            return -1;
        }
        if (i >= bArr.length) {
            i = bArr.length - 1;
        }
        while (i >= 0) {
            if (b == bArr[i]) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public static boolean contains(@Nullable byte[] bArr, byte b) {
        return indexOf(bArr, b) != -1;
    }

    public static int indexOf(@Nullable double[] dArr, double d) {
        return indexOf(dArr, d, 0);
    }

    public static int indexOf(@Nullable double[] dArr, double d, double d2) {
        return indexOf(dArr, d, 0, d2);
    }

    public static int indexOf(@Nullable double[] dArr, double d, int i) {
        if (isEmpty(dArr)) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        while (i < dArr.length) {
            if (d == dArr[i]) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int indexOf(@Nullable double[] dArr, double d, int i, double d2) {
        if (isEmpty(dArr)) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        double d3 = d - d2;
        double d4 = d + d2;
        while (i < dArr.length) {
            if (dArr[i] >= d3 && dArr[i] <= d4) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable double[] dArr, double d) {
        return lastIndexOf(dArr, d, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable double[] dArr, double d, double d2) {
        return lastIndexOf(dArr, d, Integer.MAX_VALUE, d2);
    }

    public static int lastIndexOf(@Nullable double[] dArr, double d, int i) {
        if (isEmpty(dArr) || i < 0) {
            return -1;
        }
        if (i >= dArr.length) {
            i = dArr.length - 1;
        }
        while (i >= 0) {
            if (d == dArr[i]) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable double[] dArr, double d, int i, double d2) {
        if (isEmpty(dArr) || i < 0) {
            return -1;
        }
        if (i >= dArr.length) {
            i = dArr.length - 1;
        }
        double d3 = d - d2;
        double d4 = d + d2;
        while (i >= 0) {
            if (dArr[i] >= d3 && dArr[i] <= d4) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public static boolean contains(@Nullable double[] dArr, double d) {
        return indexOf(dArr, d) != -1;
    }

    public static boolean contains(@Nullable double[] dArr, double d, double d2) {
        return indexOf(dArr, d, 0, d2) != -1;
    }

    public static int indexOf(@Nullable float[] fArr, float f) {
        return indexOf(fArr, f, 0);
    }

    public static int indexOf(@Nullable float[] fArr, float f, int i) {
        if (isEmpty(fArr)) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        while (i < fArr.length) {
            if (f == fArr[i]) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable float[] fArr, float f) {
        return lastIndexOf(fArr, f, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable float[] fArr, float f, int i) {
        if (isEmpty(fArr) || i < 0) {
            return -1;
        }
        if (i >= fArr.length) {
            i = fArr.length - 1;
        }
        while (i >= 0) {
            if (f == fArr[i]) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public static boolean contains(@Nullable float[] fArr, float f) {
        return indexOf(fArr, f) != -1;
    }

    public static int indexOf(@Nullable boolean[] zArr, boolean z) {
        return indexOf(zArr, z, 0);
    }

    public static int indexOf(@Nullable boolean[] zArr, boolean z, int i) {
        if (isEmpty(zArr)) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        while (i < zArr.length) {
            if (z == zArr[i]) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable boolean[] zArr, boolean z) {
        return lastIndexOf(zArr, z, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(@Nullable boolean[] zArr, boolean z, int i) {
        if (isEmpty(zArr) || i < 0) {
            return -1;
        }
        if (i >= zArr.length) {
            i = zArr.length - 1;
        }
        while (i >= 0) {
            if (z == zArr[i]) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public static boolean contains(@Nullable boolean[] zArr, boolean z) {
        return indexOf(zArr, z) != -1;
    }

    @Nullable
    public static char[] toPrimitive(@Nullable Character[] chArr) {
        if (chArr == null) {
            return null;
        }
        if (chArr.length == 0) {
            return new char[0];
        }
        char[] cArr = new char[chArr.length];
        for (int i = 0; i < chArr.length; i++) {
            cArr[i] = chArr[i].charValue();
        }
        return cArr;
    }

    @Nullable
    public static char[] toPrimitive(@Nullable Character[] chArr, char c) {
        if (chArr == null) {
            return null;
        }
        if (chArr.length == 0) {
            return new char[0];
        }
        char[] cArr = new char[chArr.length];
        for (int i = 0; i < chArr.length; i++) {
            Character ch = chArr[i];
            cArr[i] = ch == null ? c : ch.charValue();
        }
        return cArr;
    }

    @Nullable
    public static Character[] toObject(@Nullable char[] cArr) {
        if (cArr == null) {
            return null;
        }
        if (cArr.length == 0) {
            return new Character[0];
        }
        Character[] chArr = new Character[cArr.length];
        for (int i = 0; i < cArr.length; i++) {
            chArr[i] = new Character(cArr[i]);
        }
        return chArr;
    }

    @Nullable
    public static long[] toPrimitive(@Nullable Long[] lArr) {
        if (lArr == null) {
            return null;
        }
        if (lArr.length == 0) {
            return new long[0];
        }
        long[] jArr = new long[lArr.length];
        for (int i = 0; i < lArr.length; i++) {
            jArr[i] = lArr[i].longValue();
        }
        return jArr;
    }

    @Nullable
    public static long[] toPrimitive(@Nullable Long[] lArr, long j) {
        if (lArr == null) {
            return null;
        }
        if (lArr.length == 0) {
            return new long[0];
        }
        long[] jArr = new long[lArr.length];
        for (int i = 0; i < lArr.length; i++) {
            Long l = lArr[i];
            jArr[i] = l == null ? j : l.longValue();
        }
        return jArr;
    }

    @Nullable
    public static Long[] toObject(@Nullable long[] jArr) {
        if (jArr == null) {
            return null;
        }
        if (jArr.length == 0) {
            return new Long[0];
        }
        Long[] lArr = new Long[jArr.length];
        for (int i = 0; i < jArr.length; i++) {
            lArr[i] = new Long(jArr[i]);
        }
        return lArr;
    }

    @Nullable
    public static int[] toPrimitive(@Nullable Integer[] numArr) {
        if (numArr == null) {
            return null;
        }
        if (numArr.length == 0) {
            return new int[0];
        }
        int[] iArr = new int[numArr.length];
        for (int i = 0; i < numArr.length; i++) {
            iArr[i] = numArr[i].intValue();
        }
        return iArr;
    }

    @Nullable
    public static int[] toPrimitive(@Nullable Integer[] numArr, int i) {
        if (numArr == null) {
            return null;
        }
        if (numArr.length == 0) {
            return new int[0];
        }
        int[] iArr = new int[numArr.length];
        for (int i2 = 0; i2 < numArr.length; i2++) {
            Integer num = numArr[i2];
            iArr[i2] = num == null ? i : num.intValue();
        }
        return iArr;
    }

    @Nullable
    public static Integer[] toObject(@Nullable int[] iArr) {
        if (iArr == null) {
            return null;
        }
        if (iArr.length == 0) {
            return new Integer[0];
        }
        Integer[] numArr = new Integer[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            numArr[i] = new Integer(iArr[i]);
        }
        return numArr;
    }

    @Nullable
    public static short[] toPrimitive(@Nullable Short[] shArr) {
        if (shArr == null) {
            return null;
        }
        if (shArr.length == 0) {
            return new short[0];
        }
        short[] sArr = new short[shArr.length];
        for (int i = 0; i < shArr.length; i++) {
            sArr[i] = shArr[i].shortValue();
        }
        return sArr;
    }

    @Nullable
    public static short[] toPrimitive(@Nullable Short[] shArr, short s) {
        if (shArr == null) {
            return null;
        }
        if (shArr.length == 0) {
            return new short[0];
        }
        short[] sArr = new short[shArr.length];
        for (int i = 0; i < shArr.length; i++) {
            Short sh = shArr[i];
            sArr[i] = sh == null ? s : sh.shortValue();
        }
        return sArr;
    }

    @Nullable
    public static Short[] toObject(@Nullable short[] sArr) {
        if (sArr == null) {
            return null;
        }
        if (sArr.length == 0) {
            return new Short[0];
        }
        Short[] shArr = new Short[sArr.length];
        for (int i = 0; i < sArr.length; i++) {
            shArr[i] = new Short(sArr[i]);
        }
        return shArr;
    }

    @Nullable
    public static byte[] toPrimitive(@Nullable Byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return new byte[0];
        }
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = bArr[i].byteValue();
        }
        return bArr2;
    }

    @Nullable
    public static byte[] toPrimitive(@Nullable Byte[] bArr, byte b) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return new byte[0];
        }
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            Byte b2 = bArr[i];
            bArr2[i] = b2 == null ? b : b2.byteValue();
        }
        return bArr2;
    }

    @Nullable
    public static Byte[] toObject(@Nullable byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return new Byte[0];
        }
        Byte[] bArr2 = new Byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = new Byte(bArr[i]);
        }
        return bArr2;
    }

    @Nullable
    public static double[] toPrimitive(@Nullable Double[] dArr) {
        if (dArr == null) {
            return null;
        }
        if (dArr.length == 0) {
            return new double[0];
        }
        double[] dArr2 = new double[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            dArr2[i] = dArr[i].doubleValue();
        }
        return dArr2;
    }

    @Nullable
    public static double[] toPrimitive(@Nullable Double[] dArr, double d) {
        if (dArr == null) {
            return null;
        }
        if (dArr.length == 0) {
            return new double[0];
        }
        double[] dArr2 = new double[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            Double d2 = dArr[i];
            dArr2[i] = d2 == null ? d : d2.doubleValue();
        }
        return dArr2;
    }

    @Nullable
    public static Double[] toObject(@Nullable double[] dArr) {
        if (dArr == null) {
            return null;
        }
        if (dArr.length == 0) {
            return new Double[0];
        }
        Double[] dArr2 = new Double[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            dArr2[i] = new Double(dArr[i]);
        }
        return dArr2;
    }

    @Nullable
    public static float[] toPrimitive(@Nullable Float[] fArr) {
        if (fArr == null) {
            return null;
        }
        if (fArr.length == 0) {
            return new float[0];
        }
        float[] fArr2 = new float[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            fArr2[i] = fArr[i].floatValue();
        }
        return fArr2;
    }

    @Nullable
    public static float[] toPrimitive(@Nullable Float[] fArr, float f) {
        if (fArr == null) {
            return null;
        }
        if (fArr.length == 0) {
            return new float[0];
        }
        float[] fArr2 = new float[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            Float f2 = fArr[i];
            fArr2[i] = f2 == null ? f : f2.floatValue();
        }
        return fArr2;
    }

    @Nullable
    public static Float[] toObject(@Nullable float[] fArr) {
        if (fArr == null) {
            return null;
        }
        if (fArr.length == 0) {
            return new Float[0];
        }
        Float[] fArr2 = new Float[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            fArr2[i] = new Float(fArr[i]);
        }
        return fArr2;
    }

    @Nullable
    public static boolean[] toPrimitive(@Nullable Boolean[] boolArr) {
        if (boolArr == null) {
            return null;
        }
        if (boolArr.length == 0) {
            return new boolean[0];
        }
        boolean[] zArr = new boolean[boolArr.length];
        for (int i = 0; i < boolArr.length; i++) {
            zArr[i] = boolArr[i].booleanValue();
        }
        return zArr;
    }

    @Nullable
    public static boolean[] toPrimitive(@Nullable Boolean[] boolArr, boolean z) {
        if (boolArr == null) {
            return null;
        }
        if (boolArr.length == 0) {
            return new boolean[0];
        }
        boolean[] zArr = new boolean[boolArr.length];
        for (int i = 0; i < boolArr.length; i++) {
            Boolean bool = boolArr[i];
            zArr[i] = bool == null ? z : bool.booleanValue();
        }
        return zArr;
    }

    @Nullable
    public static Boolean[] toObject(@Nullable boolean[] zArr) {
        if (zArr == null) {
            return null;
        }
        if (zArr.length == 0) {
            return new Boolean[0];
        }
        Boolean[] boolArr = new Boolean[zArr.length];
        for (int i = 0; i < zArr.length; i++) {
            boolArr[i] = zArr[i] ? Boolean.TRUE : Boolean.FALSE;
        }
        return boolArr;
    }

    @NonNull
    public static <T> List<T> asList(@Nullable T... tArr) {
        if (tArr == null || tArr.length == 0) {
            List<T> emptyList = Collections.emptyList();
            if (emptyList != null) {
                return emptyList;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.asList() marked by @androidx.annotation.NonNull");
        }
        List<T> asList = Arrays.asList(tArr);
        if (asList != null) {
            return asList;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.asList() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static <T> List<T> asUnmodifiableList(@Nullable T... tArr) {
        List<T> unmodifiableList = Collections.unmodifiableList(asList(tArr));
        if (unmodifiableList != null) {
            return unmodifiableList;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.asUnmodifiableList() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public static <T> List<T> asArrayList(@Nullable T... tArr) {
        ArrayList arrayList = new ArrayList();
        if (tArr == null || tArr.length == 0) {
            return arrayList;
        }
        arrayList.addAll(Arrays.asList(tArr));
        return arrayList;
    }

    @NonNull
    public static <T> List<T> asLinkedList(@Nullable T... tArr) {
        LinkedList linkedList = new LinkedList();
        if (tArr == null || tArr.length == 0) {
            return linkedList;
        }
        linkedList.addAll(Arrays.asList(tArr));
        return linkedList;
    }

    public static <T> void sort(@Nullable T[] tArr, Comparator<? super T> comparator) {
        if (tArr != null && tArr.length >= 2) {
            Arrays.sort(tArr, comparator);
        }
    }

    public static void sort(@Nullable byte[] bArr) {
        if (bArr != null && bArr.length >= 2) {
            Arrays.sort(bArr);
        }
    }

    public static void sort(@Nullable char[] cArr) {
        if (cArr != null && cArr.length >= 2) {
            Arrays.sort(cArr);
        }
    }

    public static void sort(@Nullable double[] dArr) {
        if (dArr != null && dArr.length >= 2) {
            Arrays.sort(dArr);
        }
    }

    public static void sort(@Nullable float[] fArr) {
        if (fArr != null && fArr.length >= 2) {
            Arrays.sort(fArr);
        }
    }

    public static void sort(@Nullable int[] iArr) {
        if (iArr != null && iArr.length >= 2) {
            Arrays.sort(iArr);
        }
    }

    public static void sort(@Nullable long[] jArr) {
        if (jArr != null && jArr.length >= 2) {
            Arrays.sort(jArr);
        }
    }

    public static void sort(@Nullable short[] sArr) {
        if (sArr != null && sArr.length >= 2) {
            Arrays.sort(sArr);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> void forAllDo(@Nullable Object obj, @Nullable Closure<E> closure) {
        if (obj != null && closure != 0) {
            int i = 0;
            if (obj instanceof Object[]) {
                Object[] objArr = (Object[]) obj;
                int length = objArr.length;
                while (i < length) {
                    closure.execute(i, objArr[i]);
                    i++;
                }
            } else if (obj instanceof boolean[]) {
                boolean[] zArr = (boolean[]) obj;
                int length2 = zArr.length;
                while (i < length2) {
                    closure.execute(i, zArr[i] ? Boolean.TRUE : Boolean.FALSE);
                    i++;
                }
            } else if (obj instanceof byte[]) {
                byte[] bArr = (byte[]) obj;
                int length3 = bArr.length;
                while (i < length3) {
                    closure.execute(i, Byte.valueOf(bArr[i]));
                    i++;
                }
            } else if (obj instanceof char[]) {
                char[] cArr = (char[]) obj;
                int length4 = cArr.length;
                while (i < length4) {
                    closure.execute(i, Character.valueOf(cArr[i]));
                    i++;
                }
            } else if (obj instanceof short[]) {
                short[] sArr = (short[]) obj;
                int length5 = sArr.length;
                while (i < length5) {
                    closure.execute(i, Short.valueOf(sArr[i]));
                    i++;
                }
            } else if (obj instanceof int[]) {
                int[] iArr = (int[]) obj;
                int length6 = iArr.length;
                while (i < length6) {
                    closure.execute(i, Integer.valueOf(iArr[i]));
                    i++;
                }
            } else if (obj instanceof long[]) {
                long[] jArr = (long[]) obj;
                int length7 = jArr.length;
                while (i < length7) {
                    closure.execute(i, Long.valueOf(jArr[i]));
                    i++;
                }
            } else if (obj instanceof float[]) {
                float[] fArr = (float[]) obj;
                int length8 = fArr.length;
                while (i < length8) {
                    closure.execute(i, Float.valueOf(fArr[i]));
                    i++;
                }
            } else if (obj instanceof double[]) {
                double[] dArr = (double[]) obj;
                int length9 = dArr.length;
                while (i < length9) {
                    closure.execute(i, Double.valueOf(dArr[i]));
                    i++;
                }
            } else {
                throw new IllegalArgumentException("Not an array: " + obj.getClass());
            }
        }
    }

    @NonNull
    public static String toString(@Nullable Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof Object[]) {
            String deepToString = Arrays.deepToString((Object[]) obj);
            if (deepToString != null) {
                return deepToString;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.toString() marked by @androidx.annotation.NonNull");
        } else if (obj instanceof boolean[]) {
            String arrays = Arrays.toString((boolean[]) obj);
            if (arrays != null) {
                return arrays;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.toString() marked by @androidx.annotation.NonNull");
        } else if (obj instanceof byte[]) {
            String arrays2 = Arrays.toString((byte[]) obj);
            if (arrays2 != null) {
                return arrays2;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.toString() marked by @androidx.annotation.NonNull");
        } else if (obj instanceof char[]) {
            String arrays3 = Arrays.toString((char[]) obj);
            if (arrays3 != null) {
                return arrays3;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.toString() marked by @androidx.annotation.NonNull");
        } else if (obj instanceof double[]) {
            String arrays4 = Arrays.toString((double[]) obj);
            if (arrays4 != null) {
                return arrays4;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.toString() marked by @androidx.annotation.NonNull");
        } else if (obj instanceof float[]) {
            String arrays5 = Arrays.toString((float[]) obj);
            if (arrays5 != null) {
                return arrays5;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.toString() marked by @androidx.annotation.NonNull");
        } else if (obj instanceof int[]) {
            String arrays6 = Arrays.toString((int[]) obj);
            if (arrays6 != null) {
                return arrays6;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.toString() marked by @androidx.annotation.NonNull");
        } else if (obj instanceof long[]) {
            String arrays7 = Arrays.toString((long[]) obj);
            if (arrays7 != null) {
                return arrays7;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.toString() marked by @androidx.annotation.NonNull");
        } else if (obj instanceof short[]) {
            String arrays8 = Arrays.toString((short[]) obj);
            if (arrays8 != null) {
                return arrays8;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ArrayUtils.toString() marked by @androidx.annotation.NonNull");
        } else {
            throw new IllegalArgumentException("Array has incompatible type: " + obj.getClass());
        }
    }
}
