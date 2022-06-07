package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ConcurrentModificationException;
import java.util.Map;

/* loaded from: classes.dex */
public class SimpleArrayMap<K, V> {
    @Nullable
    static Object[] d;
    static int e;
    @Nullable
    static Object[] f;
    static int g;
    int[] h;
    Object[] i;
    int j;

    private static int a(int[] iArr, int i, int i2) {
        try {
            return a.a(iArr, i, i2);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    int a(Object obj, int i) {
        int i2 = this.j;
        if (i2 == 0) {
            return -1;
        }
        int a = a(this.h, i2, i);
        if (a < 0 || obj.equals(this.i[a << 1])) {
            return a;
        }
        int i3 = a + 1;
        while (i3 < i2 && this.h[i3] == i) {
            if (obj.equals(this.i[i3 << 1])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = a - 1; i4 >= 0 && this.h[i4] == i; i4--) {
            if (obj.equals(this.i[i4 << 1])) {
                return i4;
            }
        }
        return ~i3;
    }

    int a() {
        int i = this.j;
        if (i == 0) {
            return -1;
        }
        int a = a(this.h, i, 0);
        if (a < 0 || this.i[a << 1] == null) {
            return a;
        }
        int i2 = a + 1;
        while (i2 < i && this.h[i2] == 0) {
            if (this.i[i2 << 1] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = a - 1; i3 >= 0 && this.h[i3] == 0; i3--) {
            if (this.i[i3 << 1] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    private void a(int i) {
        if (i == 8) {
            synchronized (SimpleArrayMap.class) {
                if (f != null) {
                    Object[] objArr = f;
                    this.i = objArr;
                    f = (Object[]) objArr[0];
                    this.h = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    g--;
                    return;
                }
            }
        } else if (i == 4) {
            synchronized (SimpleArrayMap.class) {
                if (d != null) {
                    Object[] objArr2 = d;
                    this.i = objArr2;
                    d = (Object[]) objArr2[0];
                    this.h = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    e--;
                    return;
                }
            }
        }
        this.h = new int[i];
        this.i = new Object[i << 1];
    }

    private static void a(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (SimpleArrayMap.class) {
                if (g < 10) {
                    objArr[0] = f;
                    objArr[1] = iArr;
                    for (int i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    f = objArr;
                    g++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (SimpleArrayMap.class) {
                if (e < 10) {
                    objArr[0] = d;
                    objArr[1] = iArr;
                    for (int i3 = (i << 1) - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    d = objArr;
                    e++;
                }
            }
        }
    }

    public SimpleArrayMap() {
        this.h = a.a;
        this.i = a.c;
        this.j = 0;
    }

    public SimpleArrayMap(int i) {
        if (i == 0) {
            this.h = a.a;
            this.i = a.c;
        } else {
            a(i);
        }
        this.j = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleArrayMap(SimpleArrayMap<K, V> simpleArrayMap) {
        this();
        if (simpleArrayMap != 0) {
            putAll(simpleArrayMap);
        }
    }

    public void clear() {
        int i = this.j;
        if (i > 0) {
            int[] iArr = this.h;
            Object[] objArr = this.i;
            this.h = a.a;
            this.i = a.c;
            this.j = 0;
            a(iArr, objArr, i);
        }
        if (this.j > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public void ensureCapacity(int i) {
        int i2 = this.j;
        int[] iArr = this.h;
        if (iArr.length < i) {
            Object[] objArr = this.i;
            a(i);
            if (this.j > 0) {
                System.arraycopy(iArr, 0, this.h, 0, i2);
                System.arraycopy(objArr, 0, this.i, 0, i2 << 1);
            }
            a(iArr, objArr, i2);
        }
        if (this.j != i2) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(@Nullable Object obj) {
        return indexOfKey(obj) >= 0;
    }

    public int indexOfKey(@Nullable Object obj) {
        return obj == null ? a() : a(obj, obj.hashCode());
    }

    int a(Object obj) {
        int i = this.j * 2;
        Object[] objArr = this.i;
        if (obj == null) {
            for (int i2 = 1; i2 < i; i2 += 2) {
                if (objArr[i2] == null) {
                    return i2 >> 1;
                }
            }
            return -1;
        }
        for (int i3 = 1; i3 < i; i3 += 2) {
            if (obj.equals(objArr[i3])) {
                return i3 >> 1;
            }
        }
        return -1;
    }

    public boolean containsValue(Object obj) {
        return a(obj) >= 0;
    }

    @Nullable
    public V get(Object obj) {
        return getOrDefault(obj, null);
    }

    public V getOrDefault(Object obj, V v) {
        int indexOfKey = indexOfKey(obj);
        return indexOfKey >= 0 ? (V) this.i[(indexOfKey << 1) + 1] : v;
    }

    public K keyAt(int i) {
        return (K) this.i[i << 1];
    }

    public V valueAt(int i) {
        return (V) this.i[(i << 1) + 1];
    }

    public V setValueAt(int i, V v) {
        int i2 = (i << 1) + 1;
        Object[] objArr = this.i;
        V v2 = (V) objArr[i2];
        objArr[i2] = v;
        return v2;
    }

    public boolean isEmpty() {
        return this.j <= 0;
    }

    @Nullable
    public V put(K k, V v) {
        int i;
        int i2;
        int i3 = this.j;
        if (k == null) {
            i2 = a();
            i = 0;
        } else {
            int hashCode = k.hashCode();
            i = hashCode;
            i2 = a(k, hashCode);
        }
        if (i2 >= 0) {
            int i4 = (i2 << 1) + 1;
            Object[] objArr = this.i;
            V v2 = (V) objArr[i4];
            objArr[i4] = v;
            return v2;
        }
        int i5 = ~i2;
        if (i3 >= this.h.length) {
            int i6 = 4;
            if (i3 >= 8) {
                i6 = (i3 >> 1) + i3;
            } else if (i3 >= 4) {
                i6 = 8;
            }
            int[] iArr = this.h;
            Object[] objArr2 = this.i;
            a(i6);
            if (i3 == this.j) {
                int[] iArr2 = this.h;
                if (iArr2.length > 0) {
                    System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                    System.arraycopy(objArr2, 0, this.i, 0, objArr2.length);
                }
                a(iArr, objArr2, i3);
            } else {
                throw new ConcurrentModificationException();
            }
        }
        if (i5 < i3) {
            int[] iArr3 = this.h;
            int i7 = i5 + 1;
            System.arraycopy(iArr3, i5, iArr3, i7, i3 - i5);
            Object[] objArr3 = this.i;
            System.arraycopy(objArr3, i5 << 1, objArr3, i7 << 1, (this.j - i5) << 1);
        }
        int i8 = this.j;
        if (i3 == i8) {
            int[] iArr4 = this.h;
            if (i5 < iArr4.length) {
                iArr4[i5] = i;
                Object[] objArr4 = this.i;
                int i9 = i5 << 1;
                objArr4[i9] = k;
                objArr4[i9 + 1] = v;
                this.j = i8 + 1;
                return null;
            }
        }
        throw new ConcurrentModificationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void putAll(@NonNull SimpleArrayMap<? extends K, ? extends V> simpleArrayMap) {
        int i = simpleArrayMap.j;
        ensureCapacity(this.j + i);
        if (this.j != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                put(simpleArrayMap.keyAt(i2), simpleArrayMap.valueAt(i2));
            }
        } else if (i > 0) {
            System.arraycopy(simpleArrayMap.h, 0, this.h, 0, i);
            System.arraycopy(simpleArrayMap.i, 0, this.i, 0, i << 1);
            this.j = i;
        }
    }

    @Nullable
    public V putIfAbsent(K k, V v) {
        V v2 = get(k);
        return v2 == null ? put(k, v) : v2;
    }

    @Nullable
    public V remove(Object obj) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey >= 0) {
            return removeAt(indexOfKey);
        }
        return null;
    }

    public boolean remove(Object obj, Object obj2) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey < 0) {
            return false;
        }
        V valueAt = valueAt(indexOfKey);
        if (obj2 != valueAt && (obj2 == null || !obj2.equals(valueAt))) {
            return false;
        }
        removeAt(indexOfKey);
        return true;
    }

    public V removeAt(int i) {
        int i2 = i << 1;
        V v = (V) this.i[i2 + 1];
        int i3 = this.j;
        if (i3 <= 1) {
            clear();
        } else {
            int i4 = i3 - 1;
            int[] iArr = this.h;
            int i5 = 8;
            if (iArr.length <= 8 || i3 >= iArr.length / 3) {
                if (i < i4) {
                    int[] iArr2 = this.h;
                    int i6 = i + 1;
                    int i7 = i4 - i;
                    System.arraycopy(iArr2, i6, iArr2, i, i7);
                    Object[] objArr = this.i;
                    System.arraycopy(objArr, i6 << 1, objArr, i2, i7 << 1);
                }
                Object[] objArr2 = this.i;
                int i8 = i4 << 1;
                objArr2[i8] = null;
                objArr2[i8 + 1] = null;
            } else {
                if (i3 > 8) {
                    i5 = i3 + (i3 >> 1);
                }
                int[] iArr3 = this.h;
                Object[] objArr3 = this.i;
                a(i5);
                if (i3 == this.j) {
                    if (i > 0) {
                        System.arraycopy(iArr3, 0, this.h, 0, i);
                        System.arraycopy(objArr3, 0, this.i, 0, i2);
                    }
                    if (i < i4) {
                        int i9 = i + 1;
                        int i10 = i4 - i;
                        System.arraycopy(iArr3, i9, this.h, i, i10);
                        System.arraycopy(objArr3, i9 << 1, this.i, i2, i10 << 1);
                    }
                } else {
                    throw new ConcurrentModificationException();
                }
            }
            if (i3 == this.j) {
                this.j = i4;
            } else {
                throw new ConcurrentModificationException();
            }
        }
        return v;
    }

    @Nullable
    public V replace(K k, V v) {
        int indexOfKey = indexOfKey(k);
        if (indexOfKey >= 0) {
            return setValueAt(indexOfKey, v);
        }
        return null;
    }

    public boolean replace(K k, V v, V v2) {
        int indexOfKey = indexOfKey(k);
        if (indexOfKey < 0) {
            return false;
        }
        V valueAt = valueAt(indexOfKey);
        if (valueAt != v && (v == null || !v.equals(valueAt))) {
            return false;
        }
        setValueAt(indexOfKey, v2);
        return true;
    }

    public int size() {
        return this.j;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SimpleArrayMap) {
            SimpleArrayMap simpleArrayMap = (SimpleArrayMap) obj;
            if (size() != simpleArrayMap.size()) {
                return false;
            }
            for (int i = 0; i < this.j; i++) {
                K keyAt = keyAt(i);
                V valueAt = valueAt(i);
                Object obj2 = simpleArrayMap.get(keyAt);
                if (valueAt == null) {
                    if (obj2 != null || !simpleArrayMap.containsKey(keyAt)) {
                        return false;
                    }
                } else if (!valueAt.equals(obj2)) {
                    return false;
                }
            }
            return true;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (size() != map.size()) {
                return false;
            }
            for (int i2 = 0; i2 < this.j; i2++) {
                K keyAt2 = keyAt(i2);
                V valueAt2 = valueAt(i2);
                Object obj3 = map.get(keyAt2);
                if (valueAt2 == null) {
                    if (obj3 != null || !map.containsKey(keyAt2)) {
                        return false;
                    }
                } else if (!valueAt2.equals(obj3)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        int[] iArr = this.h;
        Object[] objArr = this.i;
        int i = this.j;
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            Object obj = objArr[i2];
            i4 += (obj == null ? 0 : obj.hashCode()) ^ iArr[i3];
            i3++;
            i2 += 2;
        }
        return i4;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.j * 28);
        sb.append('{');
        for (int i = 0; i < this.j; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            K keyAt = keyAt(i);
            if (keyAt != this) {
                sb.append(keyAt);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            V valueAt = valueAt(i);
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
