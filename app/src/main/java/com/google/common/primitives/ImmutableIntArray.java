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
public final class ImmutableIntArray implements Serializable {
    private static final ImmutableIntArray a = new ImmutableIntArray(new int[0]);
    private final int[] array;
    private final transient int b;
    private final int end;

    public static ImmutableIntArray of() {
        return a;
    }

    public static ImmutableIntArray of(int i) {
        return new ImmutableIntArray(new int[]{i});
    }

    public static ImmutableIntArray of(int i, int i2) {
        return new ImmutableIntArray(new int[]{i, i2});
    }

    public static ImmutableIntArray of(int i, int i2, int i3) {
        return new ImmutableIntArray(new int[]{i, i2, i3});
    }

    public static ImmutableIntArray of(int i, int i2, int i3, int i4) {
        return new ImmutableIntArray(new int[]{i, i2, i3, i4});
    }

    public static ImmutableIntArray of(int i, int i2, int i3, int i4, int i5) {
        return new ImmutableIntArray(new int[]{i, i2, i3, i4, i5});
    }

    public static ImmutableIntArray of(int i, int i2, int i3, int i4, int i5, int i6) {
        return new ImmutableIntArray(new int[]{i, i2, i3, i4, i5, i6});
    }

    public static ImmutableIntArray of(int i, int... iArr) {
        Preconditions.checkArgument(iArr.length <= 2147483646, "the total number of elements must fit in an int");
        int[] iArr2 = new int[iArr.length + 1];
        iArr2[0] = i;
        System.arraycopy(iArr, 0, iArr2, 1, iArr.length);
        return new ImmutableIntArray(iArr2);
    }

    public static ImmutableIntArray copyOf(int[] iArr) {
        return iArr.length == 0 ? a : new ImmutableIntArray(Arrays.copyOf(iArr, iArr.length));
    }

    public static ImmutableIntArray copyOf(Collection<Integer> collection) {
        return collection.isEmpty() ? a : new ImmutableIntArray(Ints.toArray(collection));
    }

    public static ImmutableIntArray copyOf(Iterable<Integer> iterable) {
        if (iterable instanceof Collection) {
            return copyOf((Collection<Integer>) iterable);
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
        private int[] a;
        private int b = 0;

        Builder(int i) {
            this.a = new int[i];
        }

        public Builder add(int i) {
            a(1);
            int[] iArr = this.a;
            int i2 = this.b;
            iArr[i2] = i;
            this.b = i2 + 1;
            return this;
        }

        public Builder addAll(int[] iArr) {
            a(iArr.length);
            System.arraycopy(iArr, 0, this.a, this.b, iArr.length);
            this.b += iArr.length;
            return this;
        }

        public Builder addAll(Iterable<Integer> iterable) {
            if (iterable instanceof Collection) {
                return addAll((Collection) iterable);
            }
            for (Integer num : iterable) {
                add(num.intValue());
            }
            return this;
        }

        public Builder addAll(Collection<Integer> collection) {
            a(collection.size());
            for (Integer num : collection) {
                int[] iArr = this.a;
                int i = this.b;
                this.b = i + 1;
                iArr[i] = num.intValue();
            }
            return this;
        }

        public Builder addAll(ImmutableIntArray immutableIntArray) {
            a(immutableIntArray.length());
            System.arraycopy(immutableIntArray.array, immutableIntArray.b, this.a, this.b, immutableIntArray.length());
            this.b += immutableIntArray.length();
            return this;
        }

        private void a(int i) {
            int i2 = this.b + i;
            int[] iArr = this.a;
            if (i2 > iArr.length) {
                int[] iArr2 = new int[a(iArr.length, i2)];
                System.arraycopy(this.a, 0, iArr2, 0, this.b);
                this.a = iArr2;
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
        public ImmutableIntArray build() {
            int i = this.b;
            return i == 0 ? ImmutableIntArray.a : new ImmutableIntArray(this.a, 0, i);
        }
    }

    private ImmutableIntArray(int[] iArr) {
        this(iArr, 0, iArr.length);
    }

    private ImmutableIntArray(int[] iArr, int i, int i2) {
        this.array = iArr;
        this.b = i;
        this.end = i2;
    }

    public int length() {
        return this.end - this.b;
    }

    public boolean isEmpty() {
        return this.end == this.b;
    }

    public int get(int i) {
        Preconditions.checkElementIndex(i, length());
        return this.array[this.b + i];
    }

    public int indexOf(int i) {
        for (int i2 = this.b; i2 < this.end; i2++) {
            if (this.array[i2] == i) {
                return i2 - this.b;
            }
        }
        return -1;
    }

    public int lastIndexOf(int i) {
        int i2 = this.end;
        while (true) {
            i2--;
            int i3 = this.b;
            if (i2 < i3) {
                return -1;
            }
            if (this.array[i2] == i) {
                return i2 - i3;
            }
        }
    }

    public boolean contains(int i) {
        return indexOf(i) >= 0;
    }

    public int[] toArray() {
        return Arrays.copyOfRange(this.array, this.b, this.end);
    }

    public ImmutableIntArray subArray(int i, int i2) {
        Preconditions.checkPositionIndexes(i, i2, length());
        if (i == i2) {
            return a;
        }
        int[] iArr = this.array;
        int i3 = this.b;
        return new ImmutableIntArray(iArr, i + i3, i3 + i2);
    }

    public List<Integer> asList() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class a extends AbstractList<Integer> implements Serializable, RandomAccess {
        private final ImmutableIntArray parent;

        private a(ImmutableIntArray immutableIntArray) {
            this.parent = immutableIntArray;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.parent.length();
        }

        /* renamed from: a */
        public Integer get(int i) {
            return Integer.valueOf(this.parent.get(i));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            return indexOf(obj) >= 0;
        }

        @Override // java.util.AbstractList, java.util.List
        public int indexOf(Object obj) {
            if (obj instanceof Integer) {
                return this.parent.indexOf(((Integer) obj).intValue());
            }
            return -1;
        }

        @Override // java.util.AbstractList, java.util.List
        public int lastIndexOf(Object obj) {
            if (obj instanceof Integer) {
                return this.parent.lastIndexOf(((Integer) obj).intValue());
            }
            return -1;
        }

        @Override // java.util.AbstractList, java.util.List
        public List<Integer> subList(int i, int i2) {
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
                if (obj2 instanceof Integer) {
                    i++;
                    if (this.parent.array[i] != ((Integer) obj2).intValue()) {
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
        if (!(obj instanceof ImmutableIntArray)) {
            return false;
        }
        ImmutableIntArray immutableIntArray = (ImmutableIntArray) obj;
        if (length() != immutableIntArray.length()) {
            return false;
        }
        for (int i = 0; i < length(); i++) {
            if (get(i) != immutableIntArray.get(i)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = this.b; i2 < this.end; i2++) {
            i = (i * 31) + Ints.hashCode(this.array[i2]);
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

    public ImmutableIntArray trimmed() {
        return b() ? new ImmutableIntArray(toArray()) : this;
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
