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
public final class ImmutableLongArray implements Serializable {
    private static final ImmutableLongArray a = new ImmutableLongArray(new long[0]);
    private final long[] array;
    private final transient int b;
    private final int end;

    public static ImmutableLongArray of() {
        return a;
    }

    public static ImmutableLongArray of(long j) {
        return new ImmutableLongArray(new long[]{j});
    }

    public static ImmutableLongArray of(long j, long j2) {
        return new ImmutableLongArray(new long[]{j, j2});
    }

    public static ImmutableLongArray of(long j, long j2, long j3) {
        return new ImmutableLongArray(new long[]{j, j2, j3});
    }

    public static ImmutableLongArray of(long j, long j2, long j3, long j4) {
        return new ImmutableLongArray(new long[]{j, j2, j3, j4});
    }

    public static ImmutableLongArray of(long j, long j2, long j3, long j4, long j5) {
        return new ImmutableLongArray(new long[]{j, j2, j3, j4, j5});
    }

    public static ImmutableLongArray of(long j, long j2, long j3, long j4, long j5, long j6) {
        return new ImmutableLongArray(new long[]{j, j2, j3, j4, j5, j6});
    }

    public static ImmutableLongArray of(long j, long... jArr) {
        Preconditions.checkArgument(jArr.length <= 2147483646, "the total number of elements must fit in an int");
        long[] jArr2 = new long[jArr.length + 1];
        jArr2[0] = j;
        System.arraycopy(jArr, 0, jArr2, 1, jArr.length);
        return new ImmutableLongArray(jArr2);
    }

    public static ImmutableLongArray copyOf(long[] jArr) {
        return jArr.length == 0 ? a : new ImmutableLongArray(Arrays.copyOf(jArr, jArr.length));
    }

    public static ImmutableLongArray copyOf(Collection<Long> collection) {
        return collection.isEmpty() ? a : new ImmutableLongArray(Longs.toArray(collection));
    }

    public static ImmutableLongArray copyOf(Iterable<Long> iterable) {
        if (iterable instanceof Collection) {
            return copyOf((Collection<Long>) iterable);
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
        private long[] a;
        private int b = 0;

        Builder(int i) {
            this.a = new long[i];
        }

        public Builder add(long j) {
            a(1);
            long[] jArr = this.a;
            int i = this.b;
            jArr[i] = j;
            this.b = i + 1;
            return this;
        }

        public Builder addAll(long[] jArr) {
            a(jArr.length);
            System.arraycopy(jArr, 0, this.a, this.b, jArr.length);
            this.b += jArr.length;
            return this;
        }

        public Builder addAll(Iterable<Long> iterable) {
            if (iterable instanceof Collection) {
                return addAll((Collection) iterable);
            }
            for (Long l : iterable) {
                add(l.longValue());
            }
            return this;
        }

        public Builder addAll(Collection<Long> collection) {
            a(collection.size());
            for (Long l : collection) {
                long[] jArr = this.a;
                int i = this.b;
                this.b = i + 1;
                jArr[i] = l.longValue();
            }
            return this;
        }

        public Builder addAll(ImmutableLongArray immutableLongArray) {
            a(immutableLongArray.length());
            System.arraycopy(immutableLongArray.array, immutableLongArray.b, this.a, this.b, immutableLongArray.length());
            this.b += immutableLongArray.length();
            return this;
        }

        private void a(int i) {
            int i2 = this.b + i;
            long[] jArr = this.a;
            if (i2 > jArr.length) {
                long[] jArr2 = new long[a(jArr.length, i2)];
                System.arraycopy(this.a, 0, jArr2, 0, this.b);
                this.a = jArr2;
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
        public ImmutableLongArray build() {
            int i = this.b;
            return i == 0 ? ImmutableLongArray.a : new ImmutableLongArray(this.a, 0, i);
        }
    }

    private ImmutableLongArray(long[] jArr) {
        this(jArr, 0, jArr.length);
    }

    private ImmutableLongArray(long[] jArr, int i, int i2) {
        this.array = jArr;
        this.b = i;
        this.end = i2;
    }

    public int length() {
        return this.end - this.b;
    }

    public boolean isEmpty() {
        return this.end == this.b;
    }

    public long get(int i) {
        Preconditions.checkElementIndex(i, length());
        return this.array[this.b + i];
    }

    public int indexOf(long j) {
        for (int i = this.b; i < this.end; i++) {
            if (this.array[i] == j) {
                return i - this.b;
            }
        }
        return -1;
    }

    public int lastIndexOf(long j) {
        int i = this.end;
        while (true) {
            i--;
            int i2 = this.b;
            if (i < i2) {
                return -1;
            }
            if (this.array[i] == j) {
                return i - i2;
            }
        }
    }

    public boolean contains(long j) {
        return indexOf(j) >= 0;
    }

    public long[] toArray() {
        return Arrays.copyOfRange(this.array, this.b, this.end);
    }

    public ImmutableLongArray subArray(int i, int i2) {
        Preconditions.checkPositionIndexes(i, i2, length());
        if (i == i2) {
            return a;
        }
        long[] jArr = this.array;
        int i3 = this.b;
        return new ImmutableLongArray(jArr, i + i3, i3 + i2);
    }

    public List<Long> asList() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class a extends AbstractList<Long> implements Serializable, RandomAccess {
        private final ImmutableLongArray parent;

        private a(ImmutableLongArray immutableLongArray) {
            this.parent = immutableLongArray;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.parent.length();
        }

        /* renamed from: a */
        public Long get(int i) {
            return Long.valueOf(this.parent.get(i));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            return indexOf(obj) >= 0;
        }

        @Override // java.util.AbstractList, java.util.List
        public int indexOf(Object obj) {
            if (obj instanceof Long) {
                return this.parent.indexOf(((Long) obj).longValue());
            }
            return -1;
        }

        @Override // java.util.AbstractList, java.util.List
        public int lastIndexOf(Object obj) {
            if (obj instanceof Long) {
                return this.parent.lastIndexOf(((Long) obj).longValue());
            }
            return -1;
        }

        @Override // java.util.AbstractList, java.util.List
        public List<Long> subList(int i, int i2) {
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
                if (obj2 instanceof Long) {
                    i++;
                    if (this.parent.array[i] != ((Long) obj2).longValue()) {
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
        if (!(obj instanceof ImmutableLongArray)) {
            return false;
        }
        ImmutableLongArray immutableLongArray = (ImmutableLongArray) obj;
        if (length() != immutableLongArray.length()) {
            return false;
        }
        for (int i = 0; i < length(); i++) {
            if (get(i) != immutableLongArray.get(i)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = this.b; i2 < this.end; i2++) {
            i = (i * 31) + Longs.hashCode(this.array[i2]);
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

    public ImmutableLongArray trimmed() {
        return b() ? new ImmutableLongArray(toArray()) : this;
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
