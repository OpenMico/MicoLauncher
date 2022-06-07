package com.fasterxml.jackson.databind.util;

import java.lang.reflect.Array;
import java.util.HashSet;

/* loaded from: classes.dex */
public final class ArrayBuilders {
    private BooleanBuilder a = null;
    private ByteBuilder b = null;
    private ShortBuilder c = null;
    private IntBuilder d = null;
    private LongBuilder e = null;
    private FloatBuilder f = null;
    private DoubleBuilder g = null;

    public BooleanBuilder getBooleanBuilder() {
        if (this.a == null) {
            this.a = new BooleanBuilder();
        }
        return this.a;
    }

    public ByteBuilder getByteBuilder() {
        if (this.b == null) {
            this.b = new ByteBuilder();
        }
        return this.b;
    }

    public ShortBuilder getShortBuilder() {
        if (this.c == null) {
            this.c = new ShortBuilder();
        }
        return this.c;
    }

    public IntBuilder getIntBuilder() {
        if (this.d == null) {
            this.d = new IntBuilder();
        }
        return this.d;
    }

    public LongBuilder getLongBuilder() {
        if (this.e == null) {
            this.e = new LongBuilder();
        }
        return this.e;
    }

    public FloatBuilder getFloatBuilder() {
        if (this.f == null) {
            this.f = new FloatBuilder();
        }
        return this.f;
    }

    public DoubleBuilder getDoubleBuilder() {
        if (this.g == null) {
            this.g = new DoubleBuilder();
        }
        return this.g;
    }

    /* loaded from: classes.dex */
    public static final class BooleanBuilder extends PrimitiveArrayBuilder<boolean[]> {
        @Override // com.fasterxml.jackson.databind.util.PrimitiveArrayBuilder
        public final boolean[] _constructArray(int i) {
            return new boolean[i];
        }
    }

    /* loaded from: classes.dex */
    public static final class ByteBuilder extends PrimitiveArrayBuilder<byte[]> {
        @Override // com.fasterxml.jackson.databind.util.PrimitiveArrayBuilder
        public final byte[] _constructArray(int i) {
            return new byte[i];
        }
    }

    /* loaded from: classes.dex */
    public static final class ShortBuilder extends PrimitiveArrayBuilder<short[]> {
        @Override // com.fasterxml.jackson.databind.util.PrimitiveArrayBuilder
        public final short[] _constructArray(int i) {
            return new short[i];
        }
    }

    /* loaded from: classes.dex */
    public static final class IntBuilder extends PrimitiveArrayBuilder<int[]> {
        @Override // com.fasterxml.jackson.databind.util.PrimitiveArrayBuilder
        public final int[] _constructArray(int i) {
            return new int[i];
        }
    }

    /* loaded from: classes.dex */
    public static final class LongBuilder extends PrimitiveArrayBuilder<long[]> {
        @Override // com.fasterxml.jackson.databind.util.PrimitiveArrayBuilder
        public final long[] _constructArray(int i) {
            return new long[i];
        }
    }

    /* loaded from: classes.dex */
    public static final class FloatBuilder extends PrimitiveArrayBuilder<float[]> {
        @Override // com.fasterxml.jackson.databind.util.PrimitiveArrayBuilder
        public final float[] _constructArray(int i) {
            return new float[i];
        }
    }

    /* loaded from: classes.dex */
    public static final class DoubleBuilder extends PrimitiveArrayBuilder<double[]> {
        @Override // com.fasterxml.jackson.databind.util.PrimitiveArrayBuilder
        public final double[] _constructArray(int i) {
            return new double[i];
        }
    }

    public static Object getArrayComparator(final Object obj) {
        final int length = Array.getLength(obj);
        final Class<?> cls = obj.getClass();
        return new Object() { // from class: com.fasterxml.jackson.databind.util.ArrayBuilders.1
            public boolean equals(Object obj2) {
                if (obj2 == this) {
                    return true;
                }
                if (!(ClassUtil.hasClass(obj2, cls) && Array.getLength(obj2) == length)) {
                    return false;
                }
                for (int i = 0; i < length; i++) {
                    Object obj3 = Array.get(obj, i);
                    Object obj4 = Array.get(obj2, i);
                    if (!(obj3 == obj4 || obj3 == null || obj3.equals(obj4))) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    public static <T> HashSet<T> arrayToSet(T[] tArr) {
        if (tArr == null) {
            return new HashSet<>();
        }
        int length = tArr.length;
        HashSet<T> hashSet = new HashSet<>(length);
        for (T t : tArr) {
            hashSet.add(t);
        }
        return hashSet;
    }

    public static <T> T[] insertInListNoDup(T[] tArr, T t) {
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            if (tArr[i] == t) {
                if (i == 0) {
                    return tArr;
                } else {
                    T[] tArr2 = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), length));
                    System.arraycopy(tArr, 0, tArr2, 1, i);
                    tArr2[0] = t;
                    int i2 = i + 1;
                    int i3 = length - i2;
                    if (i3 > 0) {
                        System.arraycopy(tArr, i2, tArr2, i2, i3);
                    }
                    return tArr2;
                }
            }
        }
        T[] tArr3 = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), length + 1));
        if (length > 0) {
            System.arraycopy(tArr, 0, tArr3, 1, length);
        }
        tArr3[0] = t;
        return tArr3;
    }
}
