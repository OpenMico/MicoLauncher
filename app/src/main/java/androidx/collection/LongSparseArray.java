package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class LongSparseArray<E> implements Cloneable {
    private static final Object a = new Object();
    private boolean b;
    private long[] c;
    private Object[] d;
    private int e;

    public LongSparseArray() {
        this(10);
    }

    public LongSparseArray(int i) {
        this.b = false;
        if (i == 0) {
            this.c = a.b;
            this.d = a.c;
            return;
        }
        int b = a.b(i);
        this.c = new long[b];
        this.d = new Object[b];
    }

    public LongSparseArray<E> clone() {
        try {
            LongSparseArray<E> longSparseArray = (LongSparseArray) super.clone();
            longSparseArray.c = (long[]) this.c.clone();
            longSparseArray.d = (Object[]) this.d.clone();
            return longSparseArray;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Nullable
    public E get(long j) {
        return get(j, null);
    }

    public E get(long j, E e) {
        int a2 = a.a(this.c, this.e, j);
        if (a2 >= 0) {
            Object[] objArr = this.d;
            if (objArr[a2] != a) {
                return (E) objArr[a2];
            }
        }
        return e;
    }

    @Deprecated
    public void delete(long j) {
        remove(j);
    }

    public void remove(long j) {
        Object[] objArr;
        Object obj;
        int a2 = a.a(this.c, this.e, j);
        if (a2 >= 0 && (objArr = this.d)[a2] != (obj = a)) {
            objArr[a2] = obj;
            this.b = true;
        }
    }

    public boolean remove(long j, Object obj) {
        int indexOfKey = indexOfKey(j);
        if (indexOfKey < 0) {
            return false;
        }
        E valueAt = valueAt(indexOfKey);
        if (obj != valueAt && (obj == null || !obj.equals(valueAt))) {
            return false;
        }
        removeAt(indexOfKey);
        return true;
    }

    public void removeAt(int i) {
        Object[] objArr = this.d;
        Object obj = objArr[i];
        Object obj2 = a;
        if (obj != obj2) {
            objArr[i] = obj2;
            this.b = true;
        }
    }

    @Nullable
    public E replace(long j, E e) {
        int indexOfKey = indexOfKey(j);
        if (indexOfKey < 0) {
            return null;
        }
        Object[] objArr = this.d;
        E e2 = (E) objArr[indexOfKey];
        objArr[indexOfKey] = e;
        return e2;
    }

    public boolean replace(long j, E e, E e2) {
        int indexOfKey = indexOfKey(j);
        if (indexOfKey < 0) {
            return false;
        }
        Object obj = this.d[indexOfKey];
        if (obj != e && (e == null || !e.equals(obj))) {
            return false;
        }
        this.d[indexOfKey] = e2;
        return true;
    }

    private void a() {
        int i = this.e;
        long[] jArr = this.c;
        Object[] objArr = this.d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != a) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.b = false;
        this.e = i2;
    }

    public void put(long j, E e) {
        int a2 = a.a(this.c, this.e, j);
        if (a2 >= 0) {
            this.d[a2] = e;
            return;
        }
        int i = ~a2;
        if (i < this.e) {
            Object[] objArr = this.d;
            if (objArr[i] == a) {
                this.c[i] = j;
                objArr[i] = e;
                return;
            }
        }
        if (this.b && this.e >= this.c.length) {
            a();
            i = ~a.a(this.c, this.e, j);
        }
        int i2 = this.e;
        if (i2 >= this.c.length) {
            int b = a.b(i2 + 1);
            long[] jArr = new long[b];
            Object[] objArr2 = new Object[b];
            long[] jArr2 = this.c;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr3 = this.d;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.c = jArr;
            this.d = objArr2;
        }
        int i3 = this.e;
        if (i3 - i != 0) {
            long[] jArr3 = this.c;
            int i4 = i + 1;
            System.arraycopy(jArr3, i, jArr3, i4, i3 - i);
            Object[] objArr4 = this.d;
            System.arraycopy(objArr4, i, objArr4, i4, this.e - i);
        }
        this.c[i] = j;
        this.d[i] = e;
        this.e++;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void putAll(@NonNull LongSparseArray<? extends E> longSparseArray) {
        int size = longSparseArray.size();
        for (int i = 0; i < size; i++) {
            put(longSparseArray.keyAt(i), longSparseArray.valueAt(i));
        }
    }

    @Nullable
    public E putIfAbsent(long j, E e) {
        E e2 = get(j);
        if (e2 == null) {
            put(j, e);
        }
        return e2;
    }

    public int size() {
        if (this.b) {
            a();
        }
        return this.e;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public long keyAt(int i) {
        if (this.b) {
            a();
        }
        return this.c[i];
    }

    public E valueAt(int i) {
        if (this.b) {
            a();
        }
        return (E) this.d[i];
    }

    public void setValueAt(int i, E e) {
        if (this.b) {
            a();
        }
        this.d[i] = e;
    }

    public int indexOfKey(long j) {
        if (this.b) {
            a();
        }
        return a.a(this.c, this.e, j);
    }

    public int indexOfValue(E e) {
        if (this.b) {
            a();
        }
        for (int i = 0; i < this.e; i++) {
            if (this.d[i] == e) {
                return i;
            }
        }
        return -1;
    }

    public boolean containsKey(long j) {
        return indexOfKey(j) >= 0;
    }

    public boolean containsValue(E e) {
        return indexOfValue(e) >= 0;
    }

    public void clear() {
        int i = this.e;
        Object[] objArr = this.d;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.e = 0;
        this.b = false;
    }

    public void append(long j, E e) {
        int i = this.e;
        if (i == 0 || j > this.c[i - 1]) {
            if (this.b && this.e >= this.c.length) {
                a();
            }
            int i2 = this.e;
            if (i2 >= this.c.length) {
                int b = a.b(i2 + 1);
                long[] jArr = new long[b];
                Object[] objArr = new Object[b];
                long[] jArr2 = this.c;
                System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
                Object[] objArr2 = this.d;
                System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
                this.c = jArr;
                this.d = objArr;
            }
            this.c[i2] = j;
            this.d[i2] = e;
            this.e = i2 + 1;
            return;
        }
        put(j, e);
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.e * 28);
        sb.append('{');
        for (int i = 0; i < this.e; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            E valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
