package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import okhttp3.HttpUrl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public final class ImmutableDoubleArray implements Serializable {
    private static final ImmutableDoubleArray a = new ImmutableDoubleArray(new double[0]);
    private final double[] array;
    private final transient int b;
    private final int end;

    public static ImmutableDoubleArray of() {
        return a;
    }

    public static ImmutableDoubleArray of(double d) {
        return new ImmutableDoubleArray(new double[]{d});
    }

    public static ImmutableDoubleArray of(double d, double d2) {
        return new ImmutableDoubleArray(new double[]{d, d2});
    }

    public static ImmutableDoubleArray of(double d, double d2, double d3) {
        return new ImmutableDoubleArray(new double[]{d, d2, d3});
    }

    public static ImmutableDoubleArray of(double d, double d2, double d3, double d4) {
        return new ImmutableDoubleArray(new double[]{d, d2, d3, d4});
    }

    public static ImmutableDoubleArray of(double d, double d2, double d3, double d4, double d5) {
        return new ImmutableDoubleArray(new double[]{d, d2, d3, d4, d5});
    }

    public static ImmutableDoubleArray of(double d, double d2, double d3, double d4, double d5, double d6) {
        return new ImmutableDoubleArray(new double[]{d, d2, d3, d4, d5, d6});
    }

    public static ImmutableDoubleArray of(double d, double... dArr) {
        Preconditions.checkArgument(dArr.length <= 2147483646, "the total number of elements must fit in an int");
        double[] dArr2 = new double[dArr.length + 1];
        dArr2[0] = d;
        System.arraycopy(dArr, 0, dArr2, 1, dArr.length);
        return new ImmutableDoubleArray(dArr2);
    }

    public static ImmutableDoubleArray copyOf(double[] dArr) {
        return dArr.length == 0 ? a : new ImmutableDoubleArray(Arrays.copyOf(dArr, dArr.length));
    }

    public static ImmutableDoubleArray copyOf(Collection<Double> collection) {
        return collection.isEmpty() ? a : new ImmutableDoubleArray(Doubles.toArray(collection));
    }

    public static ImmutableDoubleArray copyOf(Iterable<Double> iterable) {
        if (iterable instanceof Collection) {
            return copyOf((Collection<Double>) iterable);
        }
        return builder().addAll(iterable).build();
    }

    public static Builder builder(int i) {
        Preconditions.checkArgument(i >= 0, "Invalid initialCapacity: %s", i);
        return new Builder(i);
    }

    public static Builder builder() {
        return new Builder(10);
    }

    @CanIgnoreReturnValue
    /* loaded from: classes2.dex */
    public static final class Builder {
        private double[] a;
        private int b = 0;

        Builder(int i) {
            this.a = new double[i];
        }

        public Builder add(double d) {
            a(1);
            double[] dArr = this.a;
            int i = this.b;
            dArr[i] = d;
            this.b = i + 1;
            return this;
        }

        public Builder addAll(double[] dArr) {
            a(dArr.length);
            System.arraycopy(dArr, 0, this.a, this.b, dArr.length);
            this.b += dArr.length;
            return this;
        }

        public Builder addAll(Iterable<Double> iterable) {
            if (iterable instanceof Collection) {
                return addAll((Collection) iterable);
            }
            for (Double d : iterable) {
                add(d.doubleValue());
            }
            return this;
        }

        public Builder addAll(Collection<Double> collection) {
            a(collection.size());
            for (Double d : collection) {
                double[] dArr = this.a;
                int i = this.b;
                this.b = i + 1;
                dArr[i] = d.doubleValue();
            }
            return this;
        }

        public Builder addAll(ImmutableDoubleArray immutableDoubleArray) {
            a(immutableDoubleArray.length());
            System.arraycopy(immutableDoubleArray.array, immutableDoubleArray.b, this.a, this.b, immutableDoubleArray.length());
            this.b += immutableDoubleArray.length();
            return this;
        }

        private void a(int i) {
            int i2 = this.b + i;
            double[] dArr = this.a;
            if (i2 > dArr.length) {
                double[] dArr2 = new double[a(dArr.length, i2)];
                System.arraycopy(this.a, 0, dArr2, 0, this.b);
                this.a = dArr2;
            }
        }

        private static int a(int i, int i2) {
            if (i2 >= 0) {
                int i3 = i + (i >> 1) + 1;
                if (i3 < i2) {
                    i3 = Integer.highestOneBit(i2 - 1) << 1;
                }
                if (i3 < 0) {
                    return Integer.MAX_VALUE;
                }
                return i3;
            }
            throw new AssertionError("cannot store more than MAX_VALUE elements");
        }

        @CheckReturnValue
        public ImmutableDoubleArray build() {
            int i = this.b;
            return i == 0 ? ImmutableDoubleArray.a : new ImmutableDoubleArray(this.a, 0, i);
        }
    }

    private ImmutableDoubleArray(double[] dArr) {
        this(dArr, 0, dArr.length);
    }

    private ImmutableDoubleArray(double[] dArr, int i, int i2) {
        this.array = dArr;
        this.b = i;
        this.end = i2;
    }

    public int length() {
        return this.end - this.b;
    }

    public boolean isEmpty() {
        return this.end == this.b;
    }

    public double get(int i) {
        Preconditions.checkElementIndex(i, length());
        return this.array[this.b + i];
    }

    public int indexOf(double d) {
        for (int i = this.b; i < this.end; i++) {
            if (b(this.array[i], d)) {
                return i - this.b;
            }
        }
        return -1;
    }

    public int lastIndexOf(double d) {
        int i = this.end;
        while (true) {
            i--;
            if (i < this.b) {
                return -1;
            }
            if (b(this.array[i], d)) {
                return i - this.b;
            }
        }
    }

    public boolean contains(double d) {
        return indexOf(d) >= 0;
    }

    public double[] toArray() {
        return Arrays.copyOfRange(this.array, this.b, this.end);
    }

    public ImmutableDoubleArray subArray(int i, int i2) {
        Preconditions.checkPositionIndexes(i, i2, length());
        if (i == i2) {
            return a;
        }
        double[] dArr = this.array;
        int i3 = this.b;
        return new ImmutableDoubleArray(dArr, i + i3, i3 + i2);
    }

    public List<Double> asList() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class a extends AbstractList<Double> implements Serializable, RandomAccess {
        private final ImmutableDoubleArray parent;

        private a(ImmutableDoubleArray immutableDoubleArray) {
            this.parent = immutableDoubleArray;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.parent.length();
        }

        /* renamed from: a */
        public Double get(int i) {
            return Double.valueOf(this.parent.get(i));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            return indexOf(obj) >= 0;
        }

        @Override // java.util.AbstractList, java.util.List
        public int indexOf(Object obj) {
            if (obj instanceof Double) {
                return this.parent.indexOf(((Double) obj).doubleValue());
            }
            return -1;
        }

        @Override // java.util.AbstractList, java.util.List
        public int lastIndexOf(Object obj) {
            if (obj instanceof Double) {
                return this.parent.lastIndexOf(((Double) obj).doubleValue());
            }
            return -1;
        }

        @Override // java.util.AbstractList, java.util.List
        public List<Double> subList(int i, int i2) {
            return this.parent.subArray(i, i2).asList();
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof a) {
                return this.parent.equals(((a) obj).parent);
            }
            if (!(obj instanceof List)) {
                return false;
            }
            List list = (List) obj;
            if (size() != list.size()) {
                return false;
            }
            int i = this.parent.b;
            for (Object obj2 : list) {
                if (obj2 instanceof Double) {
                    i++;
                    if (!ImmutableDoubleArray.b(this.parent.array[i], ((Double) obj2).doubleValue())) {
                    }
                }
                return false;
            }
            return true;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public int hashCode() {
            return this.parent.hashCode();
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            return this.parent.toString();
        }
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ImmutableDoubleArray)) {
            return false;
        }
        ImmutableDoubleArray immutableDoubleArray = (ImmutableDoubleArray) obj;
        if (length() != immutableDoubleArray.length()) {
            return false;
        }
        for (int i = 0; i < length(); i++) {
            if (!b(get(i), immutableDoubleArray.get(i))) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(double d, double d2) {
        return Double.doubleToLongBits(d) == Double.doubleToLongBits(d2);
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = this.b; i2 < this.end; i2++) {
            i = (i * 31) + Doubles.hashCode(this.array[i2]);
        }
        return i;
    }

    public String toString() {
        if (isEmpty()) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder(length() * 5);
        sb.append('[');
        sb.append(this.array[this.b]);
        int i = this.b;
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

    public ImmutableDoubleArray trimmed() {
        return b() ? new ImmutableDoubleArray(toArray()) : this;
    }

    private boolean b() {
        return this.b > 0 || this.end < this.array.length;
    }

    Object writeReplace() {
        return trimmed();
    }

    Object readResolve() {
        return isEmpty() ? a : this;
    }
}
