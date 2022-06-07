package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public final class ArraySet<E> implements Collection<E>, Set<E> {
    @Nullable
    private static Object[] c;
    private static int d;
    @Nullable
    private static Object[] e;
    private static int f;
    private static final Object g = new Object();
    private static final Object h = new Object();
    Object[] a;
    int b;
    private int[] i;

    private int a(int i) {
        try {
            return a.a(this.i, this.b, i);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    private int a(Object obj, int i) {
        int i2 = this.b;
        if (i2 == 0) {
            return -1;
        }
        int a2 = a(i);
        if (a2 < 0 || obj.equals(this.a[a2])) {
            return a2;
        }
        int i3 = a2 + 1;
        while (i3 < i2 && this.i[i3] == i) {
            if (obj.equals(this.a[i3])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = a2 - 1; i4 >= 0 && this.i[i4] == i; i4--) {
            if (obj.equals(this.a[i4])) {
                return i4;
            }
        }
        return ~i3;
    }

    private int a() {
        int i = this.b;
        if (i == 0) {
            return -1;
        }
        int a2 = a(0);
        if (a2 < 0 || this.a[a2] == null) {
            return a2;
        }
        int i2 = a2 + 1;
        while (i2 < i && this.i[i2] == 0) {
            if (this.a[i2] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = a2 - 1; i3 >= 0 && this.i[i3] == 0; i3--) {
            if (this.a[i3] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    private void b(int i) {
        if (i == 8) {
            synchronized (h) {
                if (e != null) {
                    Object[] objArr = e;
                    try {
                        this.a = objArr;
                        e = (Object[]) objArr[0];
                        this.i = (int[]) objArr[1];
                        if (this.i != null) {
                            objArr[1] = null;
                            objArr[0] = null;
                            f--;
                            return;
                        }
                    } catch (ClassCastException unused) {
                    }
                    System.out.println("ArraySet Found corrupt ArraySet cache: [0]=" + objArr[0] + " [1]=" + objArr[1]);
                    e = null;
                    f = 0;
                }
            }
        } else if (i == 4) {
            synchronized (g) {
                if (c != null) {
                    Object[] objArr2 = c;
                    try {
                        this.a = objArr2;
                        c = (Object[]) objArr2[0];
                        this.i = (int[]) objArr2[1];
                        if (this.i != null) {
                            objArr2[1] = null;
                            objArr2[0] = null;
                            d--;
                            return;
                        }
                    } catch (ClassCastException unused2) {
                    }
                    System.out.println("ArraySet Found corrupt ArraySet cache: [0]=" + objArr2[0] + " [1]=" + objArr2[1]);
                    c = null;
                    d = 0;
                }
            }
        }
        this.i = new int[i];
        this.a = new Object[i];
    }

    private static void a(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (h) {
                if (f < 10) {
                    objArr[0] = e;
                    objArr[1] = iArr;
                    for (int i2 = i - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    e = objArr;
                    f++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (g) {
                if (d < 10) {
                    objArr[0] = c;
                    objArr[1] = iArr;
                    for (int i3 = i - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    c = objArr;
                    d++;
                }
            }
        }
    }

    public ArraySet() {
        this(0);
    }

    public ArraySet(int i) {
        if (i == 0) {
            this.i = a.a;
            this.a = a.c;
        } else {
            b(i);
        }
        this.b = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArraySet(@Nullable ArraySet<E> arraySet) {
        this();
        if (arraySet != 0) {
            addAll((ArraySet) arraySet);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArraySet(@Nullable Collection<E> collection) {
        this();
        if (collection != 0) {
            addAll(collection);
        }
    }

    public ArraySet(@Nullable E[] eArr) {
        this();
        if (eArr != null) {
            for (E e2 : eArr) {
                add(e2);
            }
        }
    }

    @Override // java.util.Collection, java.util.Set
    public void clear() {
        int i = this.b;
        if (i != 0) {
            int[] iArr = this.i;
            Object[] objArr = this.a;
            this.i = a.a;
            this.a = a.c;
            this.b = 0;
            a(iArr, objArr, i);
        }
        if (this.b != 0) {
            throw new ConcurrentModificationException();
        }
    }

    public void ensureCapacity(int i) {
        int i2 = this.b;
        int[] iArr = this.i;
        if (iArr.length < i) {
            Object[] objArr = this.a;
            b(i);
            int i3 = this.b;
            if (i3 > 0) {
                System.arraycopy(iArr, 0, this.i, 0, i3);
                System.arraycopy(objArr, 0, this.a, 0, this.b);
            }
            a(iArr, objArr, this.b);
        }
        if (this.b != i2) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean contains(@Nullable Object obj) {
        return indexOf(obj) >= 0;
    }

    public int indexOf(@Nullable Object obj) {
        return obj == null ? a() : a(obj, obj.hashCode());
    }

    public E valueAt(int i) {
        return (E) this.a[i];
    }

    @Override // java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.b <= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean add(@Nullable E e2) {
        int i;
        int i2;
        int i3 = this.b;
        if (e2 == null) {
            i2 = a();
            i = 0;
        } else {
            int hashCode = e2.hashCode();
            i = hashCode;
            i2 = a(e2, hashCode);
        }
        if (i2 >= 0) {
            return false;
        }
        int i4 = ~i2;
        if (i3 >= this.i.length) {
            int i5 = 4;
            if (i3 >= 8) {
                i5 = (i3 >> 1) + i3;
            } else if (i3 >= 4) {
                i5 = 8;
            }
            int[] iArr = this.i;
            Object[] objArr = this.a;
            b(i5);
            if (i3 == this.b) {
                int[] iArr2 = this.i;
                if (iArr2.length > 0) {
                    System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                    System.arraycopy(objArr, 0, this.a, 0, objArr.length);
                }
                a(iArr, objArr, i3);
            } else {
                throw new ConcurrentModificationException();
            }
        }
        if (i4 < i3) {
            int[] iArr3 = this.i;
            int i6 = i4 + 1;
            int i7 = i3 - i4;
            System.arraycopy(iArr3, i4, iArr3, i6, i7);
            Object[] objArr2 = this.a;
            System.arraycopy(objArr2, i4, objArr2, i6, i7);
        }
        int i8 = this.b;
        if (i3 == i8) {
            int[] iArr4 = this.i;
            if (i4 < iArr4.length) {
                iArr4[i4] = i;
                this.a[i4] = e2;
                this.b = i8 + 1;
                return true;
            }
        }
        throw new ConcurrentModificationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addAll(@NonNull ArraySet<? extends E> arraySet) {
        int i = arraySet.b;
        ensureCapacity(this.b + i);
        if (this.b != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                add(arraySet.valueAt(i2));
            }
        } else if (i > 0) {
            System.arraycopy(arraySet.i, 0, this.i, 0, i);
            System.arraycopy(arraySet.a, 0, this.a, 0, i);
            if (this.b == 0) {
                this.b = i;
                return;
            }
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean remove(@Nullable Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf < 0) {
            return false;
        }
        removeAt(indexOf);
        return true;
    }

    public E removeAt(int i) {
        int i2 = this.b;
        E e2 = (E) this.a[i];
        if (i2 <= 1) {
            clear();
        } else {
            int i3 = i2 - 1;
            int[] iArr = this.i;
            int i4 = 8;
            if (iArr.length <= 8 || i2 >= iArr.length / 3) {
                if (i < i3) {
                    int[] iArr2 = this.i;
                    int i5 = i + 1;
                    int i6 = i3 - i;
                    System.arraycopy(iArr2, i5, iArr2, i, i6);
                    Object[] objArr = this.a;
                    System.arraycopy(objArr, i5, objArr, i, i6);
                }
                this.a[i3] = null;
            } else {
                if (i2 > 8) {
                    i4 = i2 + (i2 >> 1);
                }
                int[] iArr3 = this.i;
                Object[] objArr2 = this.a;
                b(i4);
                if (i > 0) {
                    System.arraycopy(iArr3, 0, this.i, 0, i);
                    System.arraycopy(objArr2, 0, this.a, 0, i);
                }
                if (i < i3) {
                    int i7 = i + 1;
                    int i8 = i3 - i;
                    System.arraycopy(iArr3, i7, this.i, i, i8);
                    System.arraycopy(objArr2, i7, this.a, i, i8);
                }
            }
            if (i2 == this.b) {
                this.b = i3;
            } else {
                throw new ConcurrentModificationException();
            }
        }
        return e2;
    }

    public boolean removeAll(@NonNull ArraySet<? extends E> arraySet) {
        int i = arraySet.b;
        int i2 = this.b;
        for (int i3 = 0; i3 < i; i3++) {
            remove(arraySet.valueAt(i3));
        }
        return i2 != this.b;
    }

    @Override // java.util.Collection, java.util.Set
    public int size() {
        return this.b;
    }

    @Override // java.util.Collection, java.util.Set
    @NonNull
    public Object[] toArray() {
        int i = this.b;
        Object[] objArr = new Object[i];
        System.arraycopy(this.a, 0, objArr, 0, i);
        return objArr;
    }

    @Override // java.util.Collection, java.util.Set
    @NonNull
    public <T> T[] toArray(@NonNull T[] tArr) {
        if (tArr.length < this.b) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.b));
        }
        System.arraycopy(this.a, 0, tArr, 0, this.b);
        int length = tArr.length;
        int i = this.b;
        if (length > i) {
            tArr[i] = null;
        }
        return tArr;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (size() != set.size()) {
            return false;
        }
        for (int i = 0; i < this.b; i++) {
            try {
                if (!set.contains(valueAt(i))) {
                    return false;
                }
            } catch (ClassCastException unused) {
                return false;
            } catch (NullPointerException unused2) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int[] iArr = this.i;
        int i = this.b;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += iArr[i3];
        }
        return i2;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.b * 14);
        sb.append('{');
        for (int i = 0; i < this.b; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            E valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    @NonNull
    public Iterator<E> iterator() {
        return new a();
    }

    /* loaded from: classes.dex */
    private class a extends b<E> {
        a() {
            super(ArraySet.this.b);
        }

        @Override // androidx.collection.b
        protected E a(int i) {
            return (E) ArraySet.this.valueAt(i);
        }

        @Override // androidx.collection.b
        protected void b(int i) {
            ArraySet.this.removeAt(i);
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean containsAll(@NonNull Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Collection, java.util.Set
    public boolean addAll(@NonNull Collection<? extends E> collection) {
        ensureCapacity(this.b + collection.size());
        Iterator<? extends E> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= add(it.next());
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean removeAll(@NonNull Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= remove(it.next());
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean retainAll(@NonNull Collection<?> collection) {
        boolean z = false;
        for (int i = this.b - 1; i >= 0; i--) {
            if (!collection.contains(this.a[i])) {
                removeAt(i);
                z = true;
            }
        }
        return z;
    }
}
