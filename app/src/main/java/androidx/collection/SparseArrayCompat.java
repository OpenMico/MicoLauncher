package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class SparseArrayCompat<E> implements Cloneable {
    private static final Object a = new Object();
    private boolean b;
    private int[] c;
    private Object[] d;
    private int e;

    public SparseArrayCompat() {
        this(10);
    }

    public SparseArrayCompat(int i) {
        this.b = false;
        if (i == 0) {
            this.c = a.a;
            this.d = a.c;
            return;
        }
        int a2 = a.a(i);
        this.c = new int[a2];
        this.d = new Object[a2];
    }

    public SparseArrayCompat<E> clone() {
        try {
            SparseArrayCompat<E> sparseArrayCompat = (SparseArrayCompat) super.clone();
            sparseArrayCompat.c = (int[]) this.c.clone();
            sparseArrayCompat.d = (Object[]) this.d.clone();
            return sparseArrayCompat;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Nullable
    public E get(int i) {
        return get(i, null);
    }

    public E get(int i, E e) {
        int a2 = a.a(this.c, this.e, i);
        if (a2 >= 0) {
            Object[] objArr = this.d;
            if (objArr[a2] != a) {
                return (E) objArr[a2];
            }
        }
        return e;
    }

    @Deprecated
    public void delete(int i) {
        remove(i);
    }

    public void remove(int i) {
        Object[] objArr;
        Object obj;
        int a2 = a.a(this.c, this.e, i);
        if (a2 >= 0 && (objArr = this.d)[a2] != (obj = a)) {
            objArr[a2] = obj;
            this.b = true;
        }
    }

    public boolean remove(int i, Object obj) {
        int indexOfKey = indexOfKey(i);
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

    public void removeAtRange(int i, int i2) {
        int min = Math.min(this.e, i2 + i);
        while (i < min) {
            removeAt(i);
            i++;
        }
    }

    @Nullable
    public E replace(int i, E e) {
        int indexOfKey = indexOfKey(i);
        if (indexOfKey < 0) {
            return null;
        }
        Object[] objArr = this.d;
        E e2 = (E) objArr[indexOfKey];
        objArr[indexOfKey] = e;
        return e2;
    }

    public boolean replace(int i, E e, E e2) {
        int indexOfKey = indexOfKey(i);
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
        int[] iArr = this.c;
        Object[] objArr = this.d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != a) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.b = false;
        this.e = i2;
    }

    public void put(int i, E e) {
        int a2 = a.a(this.c, this.e, i);
        if (a2 >= 0) {
            this.d[a2] = e;
            return;
        }
        int i2 = ~a2;
        if (i2 < this.e) {
            Object[] objArr = this.d;
            if (objArr[i2] == a) {
                this.c[i2] = i;
                objArr[i2] = e;
                return;
            }
        }
        if (this.b && this.e >= this.c.length) {
            a();
            i2 = ~a.a(this.c, this.e, i);
        }
        int i3 = this.e;
        if (i3 >= this.c.length) {
            int a3 = a.a(i3 + 1);
            int[] iArr = new int[a3];
            Object[] objArr2 = new Object[a3];
            int[] iArr2 = this.c;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.d;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.c = iArr;
            this.d = objArr2;
        }
        int i4 = this.e;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.c;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            Object[] objArr4 = this.d;
            System.arraycopy(objArr4, i2, objArr4, i5, this.e - i2);
        }
        this.c[i2] = i;
        this.d[i2] = e;
        this.e++;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void putAll(@NonNull SparseArrayCompat<? extends E> sparseArrayCompat) {
        int size = sparseArrayCompat.size();
        for (int i = 0; i < size; i++) {
            put(sparseArrayCompat.keyAt(i), sparseArrayCompat.valueAt(i));
        }
    }

    @Nullable
    public E putIfAbsent(int i, E e) {
        E e2 = get(i);
        if (e2 == null) {
            put(i, e);
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

    public int keyAt(int i) {
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

    public int indexOfKey(int i) {
        if (this.b) {
            a();
        }
        return a.a(this.c, this.e, i);
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

    public boolean containsKey(int i) {
        return indexOfKey(i) >= 0;
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

    public void append(int i, E e) {
        int i2 = this.e;
        if (i2 == 0 || i > this.c[i2 - 1]) {
            if (this.b && this.e >= this.c.length) {
                a();
            }
            int i3 = this.e;
            if (i3 >= this.c.length) {
                int a2 = a.a(i3 + 1);
                int[] iArr = new int[a2];
                Object[] objArr = new Object[a2];
                int[] iArr2 = this.c;
                System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
                Object[] objArr2 = this.d;
                System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
                this.c = iArr;
                this.d = objArr;
            }
            this.c[i3] = i;
            this.d[i3] = e;
            this.e = i3 + 1;
            return;
        }
        put(i, e);
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
