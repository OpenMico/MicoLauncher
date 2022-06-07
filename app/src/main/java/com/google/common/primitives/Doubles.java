package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class Doubles {
    public static final int BYTES = 8;
    @GwtIncompatible
    static final Pattern a = a();

    public static boolean isFinite(double d) {
        return Double.NEGATIVE_INFINITY < d && d < Double.POSITIVE_INFINITY;
    }

    private Doubles() {
    }

    public static int hashCode(double d) {
        return Double.valueOf(d).hashCode();
    }

    public static int compare(double d, double d2) {
        return Double.compare(d, d2);
    }

    public static boolean contains(double[] dArr, double d) {
        for (double d2 : dArr) {
            if (d2 == d) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(double[] dArr, double d) {
        return c(dArr, d, 0, dArr.length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int c(double[] dArr, double d, int i, int i2) {
        while (i < i2) {
            if (dArr[i] == d) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0025, code lost:
        r0 = r0 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int indexOf(double[] r7, double[] r8) {
        /*
            java.lang.String r0 = "array"
            com.google.common.base.Preconditions.checkNotNull(r7, r0)
            java.lang.String r0 = "target"
            com.google.common.base.Preconditions.checkNotNull(r8, r0)
            int r0 = r8.length
            r1 = 0
            if (r0 != 0) goto L_0x000f
            return r1
        L_0x000f:
            r0 = r1
        L_0x0010:
            int r2 = r7.length
            int r3 = r8.length
            int r2 = r2 - r3
            int r2 = r2 + 1
            if (r0 >= r2) goto L_0x002c
            r2 = r1
        L_0x0018:
            int r3 = r8.length
            if (r2 >= r3) goto L_0x002b
            int r3 = r0 + r2
            r3 = r7[r3]
            r5 = r8[r2]
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x0028
            int r0 = r0 + 1
            goto L_0x0010
        L_0x0028:
            int r2 = r2 + 1
            goto L_0x0018
        L_0x002b:
            return r0
        L_0x002c:
            r7 = -1
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.primitives.Doubles.indexOf(double[], double[]):int");
    }

    public static int lastIndexOf(double[] dArr, double d) {
        return d(dArr, d, 0, dArr.length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int d(double[] dArr, double d, int i, int i2) {
        for (int i3 = i2 - 1; i3 >= i; i3--) {
            if (dArr[i3] == d) {
                return i3;
            }
        }
        return -1;
    }

    public static double min(double... dArr) {
        Preconditions.checkArgument(dArr.length > 0);
        double d = dArr[0];
        for (int i = 1; i < dArr.length; i++) {
            d = Math.min(d, dArr[i]);
        }
        return d;
    }

    public static double max(double... dArr) {
        Preconditions.checkArgument(dArr.length > 0);
        double d = dArr[0];
        for (int i = 1; i < dArr.length; i++) {
            d = Math.max(d, dArr[i]);
        }
        return d;
    }

    @Beta
    public static double constrainToRange(double d, double d2, double d3) {
        Preconditions.checkArgument(d2 <= d3, "min (%s) must be less than or equal to max (%s)", Double.valueOf(d2), Double.valueOf(d3));
        return Math.min(Math.max(d, d2), d3);
    }

    public static double[] concat(double[]... dArr) {
        int i = 0;
        for (double[] dArr2 : dArr) {
            i += dArr2.length;
        }
        double[] dArr3 = new double[i];
        int i2 = 0;
        for (double[] dArr4 : dArr) {
            System.arraycopy(dArr4, 0, dArr3, i2, dArr4.length);
            i2 += dArr4.length;
        }
        return dArr3;
    }

    /* loaded from: classes2.dex */
    private static final class b extends Converter<String, Double> implements Serializable {
        static final b a = new b();
        private static final long serialVersionUID = 1;

        public String toString() {
            return "Doubles.stringConverter()";
        }

        private b() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public Double doForward(String str) {
            return Double.valueOf(str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public String doBackward(Double d) {
            return d.toString();
        }

        private Object readResolve() {
            return a;
        }
    }

    @Beta
    public static Converter<String, Double> stringConverter() {
        return b.a;
    }

    public static double[] ensureCapacity(double[] dArr, int i, int i2) {
        boolean z = true;
        Preconditions.checkArgument(i >= 0, "Invalid minLength: %s", i);
        if (i2 < 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "Invalid padding: %s", i2);
        return dArr.length < i ? Arrays.copyOf(dArr, i + i2) : dArr;
    }

    public static String join(String str, double... dArr) {
        Preconditions.checkNotNull(str);
        if (dArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(dArr.length * 12);
        sb.append(dArr[0]);
        for (int i = 1; i < dArr.length; i++) {
            sb.append(str);
            sb.append(dArr[i]);
        }
        return sb.toString();
    }

    public static Comparator<double[]> lexicographicalComparator() {
        return c.INSTANCE;
    }

    /* loaded from: classes2.dex */
    private enum c implements Comparator<double[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Doubles.lexicographicalComparator()";
        }

        /* renamed from: a */
        public int compare(double[] dArr, double[] dArr2) {
            int min = Math.min(dArr.length, dArr2.length);
            for (int i = 0; i < min; i++) {
                int compare = Double.compare(dArr[i], dArr2[i]);
                if (compare != 0) {
                    return compare;
                }
            }
            return dArr.length - dArr2.length;
        }
    }

    public static void sortDescending(double[] dArr) {
        Preconditions.checkNotNull(dArr);
        sortDescending(dArr, 0, dArr.length);
    }

    public static void sortDescending(double[] dArr, int i, int i2) {
        Preconditions.checkNotNull(dArr);
        Preconditions.checkPositionIndexes(i, i2, dArr.length);
        Arrays.sort(dArr, i, i2);
        reverse(dArr, i, i2);
    }

    public static void reverse(double[] dArr) {
        Preconditions.checkNotNull(dArr);
        reverse(dArr, 0, dArr.length);
    }

    public static void reverse(double[] dArr, int i, int i2) {
        Preconditions.checkNotNull(dArr);
        Preconditions.checkPositionIndexes(i, i2, dArr.length);
        for (int i3 = i2 - 1; i < i3; i3--) {
            double d = dArr[i];
            dArr[i] = dArr[i3];
            dArr[i3] = d;
            i++;
        }
    }

    public static double[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof a) {
            return ((a) collection).a();
        }
        Object[] array = collection.toArray();
        int length = array.length;
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            dArr[i] = ((Number) Preconditions.checkNotNull(array[i])).doubleValue();
        }
        return dArr;
    }

    public static List<Double> asList(double... dArr) {
        if (dArr.length == 0) {
            return Collections.emptyList();
        }
        return new a(dArr);
    }

    @GwtCompatible
    /* loaded from: classes2.dex */
    private static class a extends AbstractList<Double> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        final double[] array;
        final int end;
        final int start;

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return false;
        }

        a(double[] dArr) {
            this(dArr, 0, dArr.length);
        }

        a(double[] dArr, int i, int i2) {
            this.array = dArr;
            this.start = i;
            this.end = i2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.end - this.start;
        }

        /* renamed from: a */
        public Double get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Double.valueOf(this.array[this.start + i]);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            return (obj instanceof Double) && Doubles.c(this.array, ((Double) obj).doubleValue(), this.start, this.end) != -1;
        }

        @Override // java.util.AbstractList, java.util.List
        public int indexOf(Object obj) {
            int c;
            if (!(obj instanceof Double) || (c = Doubles.c(this.array, ((Double) obj).doubleValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return c - this.start;
        }

        @Override // java.util.AbstractList, java.util.List
        public int lastIndexOf(Object obj) {
            int d;
            if (!(obj instanceof Double) || (d = Doubles.d(this.array, ((Double) obj).doubleValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return d - this.start;
        }

        /* renamed from: a */
        public Double set(int i, Double d) {
            Preconditions.checkElementIndex(i, size());
            double[] dArr = this.array;
            int i2 = this.start;
            double d2 = dArr[i2 + i];
            dArr[i2 + i] = ((Double) Preconditions.checkNotNull(d)).doubleValue();
            return Double.valueOf(d2);
        }

        @Override // java.util.AbstractList, java.util.List
        public List<Double> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            if (i == i2) {
                return Collections.emptyList();
            }
            double[] dArr = this.array;
            int i3 = this.start;
            return new a(dArr, i + i3, i3 + i2);
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public boolean equals(@NullableDecl Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a)) {
                return super.equals(obj);
            }
            a aVar = (a) obj;
            int size = size();
            if (aVar.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (this.array[this.start + i] != aVar.array[aVar.start + i]) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public int hashCode() {
            int i = 1;
            for (int i2 = this.start; i2 < this.end; i2++) {
                i = (i * 31) + Doubles.hashCode(this.array[i2]);
            }
            return i;
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            StringBuilder sb = new StringBuilder(size() * 12);
            sb.append('[');
            sb.append(this.array[this.start]);
            int i = this.start;
            while (true) {
                i++;
                if (i < this.end) {
                    sb.append(", ");
                    sb.append(this.array[i]);
                } else {
                    sb.append(']');
                    return sb.toString();
                }
            }
        }

        double[] a() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }
    }

    @GwtIncompatible
    private static Pattern a() {
        return Pattern.compile("[+-]?(?:NaN|Infinity|" + ("(?:\\d++(?:\\.\\d*+)?|\\.\\d++)(?:[eE][+-]?\\d++)?[fFdD]?") + "|" + ("0[xX](?:\\p{XDigit}++(?:\\.\\p{XDigit}*+)?|\\.\\p{XDigit}++)[pP][+-]?\\d++[fFdD]?") + ")");
    }

    @Beta
    @NullableDecl
    @GwtIncompatible
    public static Double tryParse(String str) {
        if (!a.matcher(str).matches()) {
            return null;
        }
        try {
            return Double.valueOf(Double.parseDouble(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }
}
