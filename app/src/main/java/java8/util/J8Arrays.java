package java8.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java8.lang.Integers;
import java8.lang.Longs;
import java8.util.Spliterator;
import java8.util.c;
import java8.util.concurrent.ForkJoinPool;
import java8.util.e;
import java8.util.function.BinaryOperator;
import java8.util.function.DoubleBinaryOperator;
import java8.util.function.IntBinaryOperator;
import java8.util.function.IntFunction;
import java8.util.function.IntToDoubleFunction;
import java8.util.function.IntToLongFunction;
import java8.util.function.IntUnaryOperator;
import java8.util.function.LongBinaryOperator;
import java8.util.stream.DoubleStream;
import java8.util.stream.IntStream;
import java8.util.stream.IntStreams;
import java8.util.stream.LongStream;
import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;

/* loaded from: classes5.dex */
public final class J8Arrays {
    public static <T> Spliterator<T> spliterator(T[] tArr) {
        return Spliterators.spliterator(tArr, 1040);
    }

    public static <T> Spliterator<T> spliterator(T[] tArr, int i, int i2) {
        return Spliterators.spliterator(tArr, i, i2, 1040);
    }

    public static Spliterator.OfInt spliterator(int[] iArr) {
        return Spliterators.spliterator(iArr, 1040);
    }

    public static Spliterator.OfInt spliterator(int[] iArr, int i, int i2) {
        return Spliterators.spliterator(iArr, i, i2, 1040);
    }

    public static Spliterator.OfLong spliterator(long[] jArr) {
        return Spliterators.spliterator(jArr, 1040);
    }

    public static Spliterator.OfLong spliterator(long[] jArr, int i, int i2) {
        return Spliterators.spliterator(jArr, i, i2, 1040);
    }

    public static Spliterator.OfDouble spliterator(double[] dArr) {
        return Spliterators.spliterator(dArr, 1040);
    }

    public static Spliterator.OfDouble spliterator(double[] dArr, int i, int i2) {
        return Spliterators.spliterator(dArr, i, i2, 1040);
    }

    public static <T> Stream<T> stream(T[] tArr) {
        return stream(tArr, 0, tArr.length);
    }

    public static <T> Stream<T> stream(T[] tArr, int i, int i2) {
        return StreamSupport.stream(spliterator(tArr, i, i2), false);
    }

    public static IntStream stream(int[] iArr) {
        return stream(iArr, 0, iArr.length);
    }

    public static IntStream stream(int[] iArr, int i, int i2) {
        return StreamSupport.intStream(spliterator(iArr, i, i2), false);
    }

    public static LongStream stream(long[] jArr) {
        return stream(jArr, 0, jArr.length);
    }

    public static LongStream stream(long[] jArr, int i, int i2) {
        return StreamSupport.longStream(spliterator(jArr, i, i2), false);
    }

    public static DoubleStream stream(double[] dArr) {
        return stream(dArr, 0, dArr.length);
    }

    public static DoubleStream stream(double[] dArr, int i, int i2) {
        return StreamSupport.doubleStream(spliterator(dArr, i, i2), false);
    }

    public static void parallelSort(byte[] bArr) {
        p.a(bArr, 0, bArr.length);
    }

    public static void parallelSort(byte[] bArr, int i, int i2) {
        a(bArr.length, i, i2);
        p.a(bArr, i, i2);
    }

    public static void parallelSort(char[] cArr) {
        p.a(cArr, 0, cArr.length);
    }

    public static void parallelSort(char[] cArr, int i, int i2) {
        a(cArr.length, i, i2);
        p.a(cArr, i, i2);
    }

    public static void parallelSort(short[] sArr) {
        p.a(sArr, 0, sArr.length);
    }

    public static void parallelSort(short[] sArr, int i, int i2) {
        a(sArr.length, i, i2);
        p.a(sArr, i, i2);
    }

    public static void parallelSort(int[] iArr) {
        p.a(iArr, ForkJoinPool.getCommonPoolParallelism(), 0, iArr.length);
    }

    public static void parallelSort(int[] iArr, int i, int i2) {
        a(iArr.length, i, i2);
        p.a(iArr, ForkJoinPool.getCommonPoolParallelism(), i, i2);
    }

    public static void parallelSort(long[] jArr) {
        p.a(jArr, ForkJoinPool.getCommonPoolParallelism(), 0, jArr.length);
    }

    public static void parallelSort(long[] jArr, int i, int i2) {
        a(jArr.length, i, i2);
        p.a(jArr, ForkJoinPool.getCommonPoolParallelism(), i, i2);
    }

    public static void parallelSort(float[] fArr) {
        p.a(fArr, ForkJoinPool.getCommonPoolParallelism(), 0, fArr.length);
    }

    public static void parallelSort(float[] fArr, int i, int i2) {
        a(fArr.length, i, i2);
        p.a(fArr, ForkJoinPool.getCommonPoolParallelism(), i, i2);
    }

    public static void parallelSort(double[] dArr) {
        p.a(dArr, ForkJoinPool.getCommonPoolParallelism(), 0, dArr.length);
    }

    public static void parallelSort(double[] dArr, int i, int i2) {
        a(dArr.length, i, i2);
        p.a(dArr, ForkJoinPool.getCommonPoolParallelism(), i, i2);
    }

    private static void a(int i, int i2, int i3) {
        if (i2 > i3) {
            throw new IllegalArgumentException("fromIndex(" + i2 + ") > toIndex(" + i3 + ")");
        } else if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException(i2);
        } else if (i3 > i) {
            throw new ArrayIndexOutOfBoundsException(i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a implements Comparator<Object> {
        static final a a = new a();

        a() {
        }

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    public static <T extends Comparable<? super T>> void parallelSort(T[] tArr) {
        int commonPoolParallelism;
        int length = tArr.length;
        if (length <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            ak.a(tArr, 0, length, a.a, null, 0, 0);
            return;
        }
        int i = length / (commonPoolParallelism << 2);
        new e.b.C0351b(null, tArr, (Comparable[]) Array.newInstance(tArr.getClass().getComponentType(), length), 0, length, 0, i <= 8192 ? 8192 : i, a.a).invoke();
    }

    public static <T extends Comparable<? super T>> void parallelSort(T[] tArr, int i, int i2) {
        int commonPoolParallelism;
        a(tArr.length, i, i2);
        int i3 = i2 - i;
        if (i3 <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            ak.a(tArr, i, i2, a.a, null, 0, 0);
            return;
        }
        int i4 = i3 / (commonPoolParallelism << 2);
        new e.b.C0351b(null, tArr, (Comparable[]) Array.newInstance(tArr.getClass().getComponentType(), i3), i, i3, 0, i4 <= 8192 ? 8192 : i4, a.a).invoke();
    }

    public static <T> void parallelSort(T[] tArr, Comparator<? super T> comparator) {
        int commonPoolParallelism;
        if (comparator == null) {
            comparator = a.a;
        }
        int length = tArr.length;
        if (length <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            ak.a(tArr, 0, length, comparator, null, 0, 0);
            return;
        }
        int i = length / (commonPoolParallelism << 2);
        new e.b.C0351b(null, tArr, (Object[]) Array.newInstance(tArr.getClass().getComponentType(), length), 0, length, 0, i <= 8192 ? 8192 : i, comparator).invoke();
    }

    public static <T> void parallelSort(T[] tArr, int i, int i2, Comparator<? super T> comparator) {
        int commonPoolParallelism;
        a(tArr.length, i, i2);
        if (comparator == null) {
            comparator = a.a;
        }
        int i3 = i2 - i;
        if (i3 <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            ak.a(tArr, i, i2, comparator, null, 0, 0);
            return;
        }
        int i4 = i3 / (commonPoolParallelism << 2);
        new e.b.C0351b(null, tArr, (Object[]) Array.newInstance(tArr.getClass().getComponentType(), i3), i, i3, 0, i4 <= 8192 ? 8192 : i4, comparator).invoke();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> void setAll(T[] tArr, IntFunction<? extends T> intFunction) {
        Objects.requireNonNull(intFunction);
        for (int i = 0; i < tArr.length; i++) {
            tArr[i] = intFunction.apply(i);
        }
    }

    public static <T> void parallelSetAll(T[] tArr, IntFunction<? extends T> intFunction) {
        Objects.requireNonNull(intFunction);
        IntStreams.range(0, tArr.length).parallel().forEach(s.a(tArr, intFunction));
    }

    public static /* synthetic */ void a(Object[] objArr, IntFunction intFunction, int i) {
        objArr[i] = intFunction.apply(i);
    }

    public static void setAll(int[] iArr, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = intUnaryOperator.applyAsInt(i);
        }
    }

    public static void parallelSetAll(int[] iArr, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        IntStreams.range(0, iArr.length).parallel().forEach(t.a(iArr, intUnaryOperator));
    }

    public static /* synthetic */ void a(int[] iArr, IntUnaryOperator intUnaryOperator, int i) {
        iArr[i] = intUnaryOperator.applyAsInt(i);
    }

    public static void setAll(long[] jArr, IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        for (int i = 0; i < jArr.length; i++) {
            jArr[i] = intToLongFunction.applyAsLong(i);
        }
    }

    public static void parallelSetAll(long[] jArr, IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        IntStreams.range(0, jArr.length).parallel().forEach(u.a(jArr, intToLongFunction));
    }

    public static /* synthetic */ void a(long[] jArr, IntToLongFunction intToLongFunction, int i) {
        jArr[i] = intToLongFunction.applyAsLong(i);
    }

    public static void setAll(double[] dArr, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        for (int i = 0; i < dArr.length; i++) {
            dArr[i] = intToDoubleFunction.applyAsDouble(i);
        }
    }

    public static void parallelSetAll(double[] dArr, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        IntStreams.range(0, dArr.length).parallel().forEach(v.a(dArr, intToDoubleFunction));
    }

    public static /* synthetic */ void a(double[] dArr, IntToDoubleFunction intToDoubleFunction, int i) {
        dArr[i] = intToDoubleFunction.applyAsDouble(i);
    }

    public static <T> void parallelPrefix(T[] tArr, BinaryOperator<T> binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        if (tArr.length > 0) {
            new c.a(null, binaryOperator, tArr, 0, tArr.length).invoke();
        }
    }

    public static <T> void parallelPrefix(T[] tArr, int i, int i2, BinaryOperator<T> binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        a(tArr.length, i, i2);
        if (i < i2) {
            new c.a(null, binaryOperator, tArr, i, i2).invoke();
        }
    }

    public static void parallelPrefix(long[] jArr, LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        if (jArr.length > 0) {
            new c.d(null, longBinaryOperator, jArr, 0, jArr.length).invoke();
        }
    }

    public static void parallelPrefix(long[] jArr, int i, int i2, LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        a(jArr.length, i, i2);
        if (i < i2) {
            new c.d(null, longBinaryOperator, jArr, i, i2).invoke();
        }
    }

    public static void parallelPrefix(double[] dArr, DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        if (dArr.length > 0) {
            new c.b(null, doubleBinaryOperator, dArr, 0, dArr.length).invoke();
        }
    }

    public static void parallelPrefix(double[] dArr, int i, int i2, DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        a(dArr.length, i, i2);
        if (i < i2) {
            new c.b(null, doubleBinaryOperator, dArr, i, i2).invoke();
        }
    }

    public static void parallelPrefix(int[] iArr, IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        if (iArr.length > 0) {
            new c.C0349c(null, intBinaryOperator, iArr, 0, iArr.length).invoke();
        }
    }

    public static void parallelPrefix(int[] iArr, int i, int i2, IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        a(iArr.length, i, i2);
        if (i < i2) {
            new c.C0349c(null, intBinaryOperator, iArr, i, i2).invoke();
        }
    }

    public static boolean equals(Object[] objArr, int i, int i2, Object[] objArr2, int i3, int i4) {
        a(objArr.length, i, i2);
        a(objArr2.length, i3, i4);
        int i5 = i2 - i;
        if (i5 != i4 - i3) {
            return false;
        }
        int i6 = i3;
        int i7 = i;
        for (int i8 = 0; i8 < i5; i8++) {
            i7++;
            i6++;
            if (!Objects.equals(objArr[i7], objArr2[i6])) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean equals(T[] tArr, T[] tArr2, Comparator<? super T> comparator) {
        int length;
        Objects.requireNonNull(comparator);
        if (tArr == tArr2) {
            return true;
        }
        if (tArr == null || tArr2 == null || tArr2.length != (length = tArr.length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (comparator.compare(tArr[i], tArr2[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean equals(T[] tArr, int i, int i2, T[] tArr2, int i3, int i4, Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        a(tArr.length, i, i2);
        a(tArr2.length, i3, i4);
        int i5 = i2 - i;
        if (i5 != i4 - i3) {
            return false;
        }
        int i6 = i3;
        int i7 = i;
        for (int i8 = 0; i8 < i5; i8++) {
            i7++;
            i6++;
            if (comparator.compare(tArr[i7], tArr2[i6]) != 0) {
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<? super T>> int compare(T[] tArr, T[] tArr2) {
        if (tArr == tArr2) {
            return 0;
        }
        if (tArr == null || tArr2 == null) {
            return tArr == null ? -1 : 1;
        }
        int min = Math.min(tArr.length, tArr2.length);
        for (int i = 0; i < min; i++) {
            T t = tArr[i];
            T t2 = tArr2[i];
            if (t != t2) {
                if (t == null || t2 == null) {
                    return t == null ? -1 : 1;
                }
                int compareTo = t.compareTo(t2);
                if (compareTo != 0) {
                    return compareTo;
                }
            }
        }
        return tArr.length - tArr2.length;
    }

    public static <T extends Comparable<? super T>> int compare(T[] tArr, int i, int i2, T[] tArr2, int i3, int i4) {
        a(tArr.length, i, i2);
        a(tArr2.length, i3, i4);
        int i5 = i2 - i;
        int i6 = i4 - i3;
        int min = Math.min(i5, i6);
        for (int i7 = 0; i7 < min; i7++) {
            i++;
            T t = tArr[i];
            i3++;
            T t2 = tArr2[i3];
            if (t != t2) {
                if (t == null || t2 == null) {
                    return t == null ? -1 : 1;
                }
                int compareTo = t.compareTo(t2);
                if (compareTo != 0) {
                    return compareTo;
                }
            }
        }
        return i5 - i6;
    }

    public static <T> int compare(T[] tArr, T[] tArr2, Comparator<? super T> comparator) {
        int compare;
        Objects.requireNonNull(comparator);
        if (tArr == tArr2) {
            return 0;
        }
        if (tArr == null || tArr2 == null) {
            return tArr == null ? -1 : 1;
        }
        int min = Math.min(tArr.length, tArr2.length);
        for (int i = 0; i < min; i++) {
            T t = tArr[i];
            T t2 = tArr2[i];
            if (!(t == t2 || (compare = comparator.compare(t, t2)) == 0)) {
                return compare;
            }
        }
        return tArr.length - tArr2.length;
    }

    public static <T> int compare(T[] tArr, int i, int i2, T[] tArr2, int i3, int i4, Comparator<? super T> comparator) {
        int compare;
        Objects.requireNonNull(comparator);
        a(tArr.length, i, i2);
        a(tArr2.length, i3, i4);
        int i5 = i2 - i;
        int i6 = i4 - i3;
        int min = Math.min(i5, i6);
        for (int i7 = 0; i7 < min; i7++) {
            i++;
            T t = tArr[i];
            i3++;
            T t2 = tArr2[i3];
            if (!(t == t2 || (compare = comparator.compare(t, t2)) == 0)) {
                return compare;
            }
        }
        return i5 - i6;
    }

    public static int mismatch(Object[] objArr, Object[] objArr2) {
        int min = Math.min(objArr.length, objArr2.length);
        if (objArr == objArr2) {
            return -1;
        }
        for (int i = 0; i < min; i++) {
            if (!Objects.equals(objArr[i], objArr2[i])) {
                return i;
            }
        }
        if (objArr.length != objArr2.length) {
            return min;
        }
        return -1;
    }

    public static int mismatch(Object[] objArr, int i, int i2, Object[] objArr2, int i3, int i4) {
        a(objArr.length, i, i2);
        a(objArr2.length, i3, i4);
        int i5 = i2 - i;
        int i6 = i4 - i3;
        int min = Math.min(i5, i6);
        for (int i7 = 0; i7 < min; i7++) {
            i++;
            i3++;
            if (!Objects.equals(objArr[i], objArr2[i3])) {
                return i7;
            }
        }
        if (i5 != i6) {
            return min;
        }
        return -1;
    }

    public static <T> int mismatch(T[] tArr, T[] tArr2, Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        int min = Math.min(tArr.length, tArr2.length);
        if (tArr == tArr2) {
            return -1;
        }
        for (int i = 0; i < min; i++) {
            T t = tArr[i];
            T t2 = tArr2[i];
            if (!(t == t2 || comparator.compare(t, t2) == 0)) {
                return i;
            }
        }
        if (tArr.length != tArr2.length) {
            return min;
        }
        return -1;
    }

    public static <T> int mismatch(T[] tArr, int i, int i2, T[] tArr2, int i3, int i4, Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        a(tArr.length, i, i2);
        a(tArr2.length, i3, i4);
        int i5 = i2 - i;
        int i6 = i4 - i3;
        int min = Math.min(i5, i6);
        for (int i7 = 0; i7 < min; i7++) {
            i++;
            T t = tArr[i];
            i3++;
            T t2 = tArr2[i3];
            if (!(t == t2 || comparator.compare(t, t2) == 0)) {
                return i7;
            }
        }
        if (i5 != i6) {
            return min;
        }
        return -1;
    }

    public static boolean equals(long[] jArr, int i, int i2, long[] jArr2, int i3, int i4) {
        a(jArr.length, i, i2);
        a(jArr2.length, i3, i4);
        int i5 = i2 - i;
        if (i5 != i4 - i3) {
            return false;
        }
        int i6 = i3;
        int i7 = i;
        for (int i8 = 0; i8 < i5; i8++) {
            i7++;
            i6++;
            if (jArr[i7] != jArr2[i6]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(int[] iArr, int i, int i2, int[] iArr2, int i3, int i4) {
        a(iArr.length, i, i2);
        a(iArr2.length, i3, i4);
        int i5 = i2 - i;
        if (i5 != i4 - i3) {
            return false;
        }
        int i6 = i3;
        int i7 = i;
        for (int i8 = 0; i8 < i5; i8++) {
            i7++;
            i6++;
            if (iArr[i7] != iArr2[i6]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(double[] dArr, int i, int i2, double[] dArr2, int i3, int i4) {
        a(dArr.length, i, i2);
        a(dArr2.length, i3, i4);
        int i5 = i2 - i;
        if (i5 != i4 - i3) {
            return false;
        }
        int i6 = i3;
        int i7 = i;
        for (int i8 = 0; i8 < i5; i8++) {
            i7++;
            Double valueOf = Double.valueOf(dArr[i7]);
            i6++;
            Double valueOf2 = Double.valueOf(dArr2[i6]);
            if (!(Double.doubleToRawLongBits(valueOf.doubleValue()) == Double.doubleToRawLongBits(valueOf2.doubleValue()) || (Double.isNaN(valueOf.doubleValue()) && Double.isNaN(valueOf2.doubleValue())))) {
                return false;
            }
        }
        return true;
    }

    public static int compare(int[] iArr, int[] iArr2) {
        if (iArr == iArr2) {
            return 0;
        }
        if (iArr == null || iArr2 == null) {
            return iArr == null ? -1 : 1;
        }
        int min = Math.min(iArr.length, iArr2.length);
        for (int i = 0; i < min; i++) {
            if (iArr[i] != iArr2[i]) {
                return Integers.compare(iArr[i], iArr2[i]);
            }
        }
        return iArr.length - iArr2.length;
    }

    public static int compare(int[] iArr, int i, int i2, int[] iArr2, int i3, int i4) {
        a(iArr.length, i, i2);
        a(iArr2.length, i3, i4);
        int i5 = i2 - i;
        int i6 = i4 - i3;
        int min = Math.min(i5, i6);
        for (int i7 = 0; i7 < min; i7++) {
            i++;
            int i8 = iArr[i];
            i3++;
            int i9 = iArr2[i3];
            if (i8 != i9) {
                return Integers.compare(i8, i9);
            }
        }
        return i5 - i6;
    }

    public static int compare(long[] jArr, long[] jArr2) {
        if (jArr == jArr2) {
            return 0;
        }
        if (jArr == null || jArr2 == null) {
            return jArr == null ? -1 : 1;
        }
        int min = Math.min(jArr.length, jArr2.length);
        for (int i = 0; i < min; i++) {
            if (jArr[i] != jArr2[i]) {
                return Longs.compare(jArr[i], jArr2[i]);
            }
        }
        return jArr.length - jArr2.length;
    }

    public static int compare(long[] jArr, int i, int i2, long[] jArr2, int i3, int i4) {
        a(jArr.length, i, i2);
        a(jArr2.length, i3, i4);
        int i5 = i2 - i;
        int i6 = i4 - i3;
        int min = Math.min(i5, i6);
        for (int i7 = 0; i7 < min; i7++) {
            i++;
            long j = jArr[i];
            i3++;
            long j2 = jArr2[i3];
            if (j != j2) {
                return Longs.compare(j, j2);
            }
        }
        return i5 - i6;
    }

    public static int compare(double[] dArr, double[] dArr2) {
        int compare;
        if (dArr == dArr2) {
            return 0;
        }
        if (dArr == null || dArr2 == null) {
            return dArr == null ? -1 : 1;
        }
        int min = Math.min(dArr.length, dArr2.length);
        for (int i = 0; i < min; i++) {
            double d = dArr[i];
            double d2 = dArr2[i];
            if (!(Double.doubleToRawLongBits(d) == Double.doubleToRawLongBits(d2) || (compare = Double.compare(d, d2)) == 0)) {
                return compare;
            }
        }
        return dArr.length - dArr2.length;
    }

    public static int compare(double[] dArr, int i, int i2, double[] dArr2, int i3, int i4) {
        int compare;
        int i5 = i;
        int i6 = i3;
        a(dArr.length, i5, i2);
        a(dArr2.length, i6, i4);
        int i7 = i2 - i5;
        int i8 = i4 - i6;
        int min = Math.min(i7, i8);
        for (int i9 = 0; i9 < min; i9++) {
            i5++;
            double d = dArr[i5];
            i6++;
            double d2 = dArr2[i6];
            if (!(Double.doubleToRawLongBits(d) == Double.doubleToRawLongBits(d2) || (compare = Double.compare(d, d2)) == 0)) {
                return compare;
            }
        }
        return i7 - i8;
    }

    public static int mismatch(int[] iArr, int[] iArr2) {
        int min = Math.min(iArr.length, iArr2.length);
        if (iArr == iArr2) {
            return -1;
        }
        for (int i = 0; i < min; i++) {
            if (iArr[i] != iArr2[i]) {
                return i;
            }
        }
        if (iArr.length != iArr2.length) {
            return min;
        }
        return -1;
    }

    public static int mismatch(int[] iArr, int i, int i2, int[] iArr2, int i3, int i4) {
        a(iArr.length, i, i2);
        a(iArr2.length, i3, i4);
        int i5 = i2 - i;
        int i6 = i4 - i3;
        int min = Math.min(i5, i6);
        for (int i7 = 0; i7 < min; i7++) {
            i++;
            i3++;
            if (iArr[i] != iArr2[i3]) {
                return i7;
            }
        }
        if (i5 != i6) {
            return min;
        }
        return -1;
    }

    public static int mismatch(long[] jArr, long[] jArr2) {
        int min = Math.min(jArr.length, jArr2.length);
        if (jArr == jArr2) {
            return -1;
        }
        for (int i = 0; i < min; i++) {
            if (jArr[i] != jArr2[i]) {
                return i;
            }
        }
        if (jArr.length != jArr2.length) {
            return min;
        }
        return -1;
    }

    public static int mismatch(long[] jArr, int i, int i2, long[] jArr2, int i3, int i4) {
        a(jArr.length, i, i2);
        a(jArr2.length, i3, i4);
        int i5 = i2 - i;
        int i6 = i4 - i3;
        int min = Math.min(i5, i6);
        for (int i7 = 0; i7 < min; i7++) {
            i++;
            i3++;
            if (jArr[i] != jArr2[i3]) {
                return i7;
            }
        }
        if (i5 != i6) {
            return min;
        }
        return -1;
    }

    public static int mismatch(double[] dArr, double[] dArr2) {
        int min = Math.min(dArr.length, dArr2.length);
        if (dArr == dArr2) {
            return -1;
        }
        for (int i = 0; i < min; i++) {
            double d = dArr[i];
            double d2 = dArr2[i];
            if (!(Double.doubleToRawLongBits(d) == Double.doubleToRawLongBits(d2) || (Double.isNaN(d) && Double.isNaN(d2)))) {
                return i;
            }
        }
        if (dArr.length != dArr2.length) {
            return min;
        }
        return -1;
    }

    public static int mismatch(double[] dArr, int i, int i2, double[] dArr2, int i3, int i4) {
        int i5 = i;
        int i6 = i3;
        a(dArr.length, i5, i2);
        a(dArr2.length, i6, i4);
        int i7 = i2 - i5;
        int i8 = i4 - i6;
        int min = Math.min(i7, i8);
        for (int i9 = 0; i9 < min; i9++) {
            i5++;
            double d = dArr[i5];
            i6++;
            double d2 = dArr2[i6];
            if (!(Double.doubleToRawLongBits(d) == Double.doubleToRawLongBits(d2) || (Double.isNaN(d) && Double.isNaN(d2)))) {
                return i9;
            }
        }
        if (i7 != i8) {
            return min;
        }
        return -1;
    }

    public static <T> T[] toArray(Collection<T> collection, IntFunction<T[]> intFunction) {
        return (T[]) collection.toArray(intFunction.apply(0));
    }

    private J8Arrays() {
    }
}
