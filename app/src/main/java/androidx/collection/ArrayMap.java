package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes.dex */
public class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {
    @Nullable
    ArrayMap<K, V>.a a;
    @Nullable
    ArrayMap<K, V>.c b;
    @Nullable
    ArrayMap<K, V>.e c;

    public ArrayMap() {
    }

    public ArrayMap(int i) {
        super(i);
    }

    public ArrayMap(SimpleArrayMap simpleArrayMap) {
        super(simpleArrayMap);
    }

    public boolean containsAll(@NonNull Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public void putAll(@NonNull Map<? extends K, ? extends V> map) {
        ensureCapacity(this.j + map.size());
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(@NonNull Collection<?> collection) {
        int i = this.j;
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
        return i != this.j;
    }

    public boolean retainAll(@NonNull Collection<?> collection) {
        int i = this.j;
        for (int i2 = this.j - 1; i2 >= 0; i2--) {
            if (!collection.contains(keyAt(i2))) {
                removeAt(i2);
            }
        }
        return i != this.j;
    }

    @Override // java.util.Map
    @NonNull
    public Set<Map.Entry<K, V>> entrySet() {
        ArrayMap<K, V>.a aVar = this.a;
        if (aVar != null) {
            return aVar;
        }
        ArrayMap<K, V>.a aVar2 = new a();
        this.a = aVar2;
        return aVar2;
    }

    @Override // java.util.Map
    @NonNull
    public Set<K> keySet() {
        ArrayMap<K, V>.c cVar = this.b;
        if (cVar != null) {
            return cVar;
        }
        ArrayMap<K, V>.c cVar2 = new c();
        this.b = cVar2;
        return cVar2;
    }

    @Override // java.util.Map
    @NonNull
    public Collection<V> values() {
        ArrayMap<K, V>.e eVar = this.c;
        if (eVar != null) {
            return eVar;
        }
        ArrayMap<K, V>.e eVar2 = new e();
        this.c = eVar2;
        return eVar2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class a implements Set<Map.Entry<K, V>> {
        a() {
            ArrayMap.this = r1;
        }

        /* renamed from: a */
        public boolean add(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
            int i = ArrayMap.this.j;
            Iterator<? extends Map.Entry<K, V>> it = collection.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                ArrayMap.this.put(entry.getKey(), entry.getValue());
            }
            return i != ArrayMap.this.j;
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            ArrayMap.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int indexOfKey = ArrayMap.this.indexOfKey(entry.getKey());
            if (indexOfKey < 0) {
                return false;
            }
            return a.a(ArrayMap.this.valueAt(indexOfKey), entry.getValue());
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return new d();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return ArrayMap.this.j;
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return ArrayMap.a(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int i = 0;
            for (int i2 = ArrayMap.this.j - 1; i2 >= 0; i2--) {
                K keyAt = ArrayMap.this.keyAt(i2);
                V valueAt = ArrayMap.this.valueAt(i2);
                i += (keyAt == null ? 0 : keyAt.hashCode()) ^ (valueAt == null ? 0 : valueAt.hashCode());
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class c implements Set<K> {
        c() {
            ArrayMap.this = r1;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            ArrayMap.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            return ArrayMap.this.containsKey(obj);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return ArrayMap.this.containsAll(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return new b();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            int indexOfKey = ArrayMap.this.indexOfKey(obj);
            if (indexOfKey < 0) {
                return false;
            }
            ArrayMap.this.removeAt(indexOfKey);
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            return ArrayMap.this.removeAll(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            return ArrayMap.this.retainAll(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return ArrayMap.this.j;
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            int i = ArrayMap.this.j;
            Object[] objArr = new Object[i];
            for (int i2 = 0; i2 < i; i2++) {
                objArr[i2] = ArrayMap.this.keyAt(i2);
            }
            return objArr;
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) ArrayMap.this.a((Object[]) tArr, 0);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return ArrayMap.a(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int i = 0;
            for (int i2 = ArrayMap.this.j - 1; i2 >= 0; i2--) {
                K keyAt = ArrayMap.this.keyAt(i2);
                i += keyAt == null ? 0 : keyAt.hashCode();
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class e implements Collection<V> {
        e() {
            ArrayMap.this = r1;
        }

        @Override // java.util.Collection
        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public void clear() {
            ArrayMap.this.clear();
        }

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            return ArrayMap.this.a(obj) >= 0;
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new f();
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            int a = ArrayMap.this.a(obj);
            if (a < 0) {
                return false;
            }
            ArrayMap.this.removeAt(a);
            return true;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            int i = ArrayMap.this.j;
            int i2 = 0;
            boolean z = false;
            while (i2 < i) {
                if (collection.contains(ArrayMap.this.valueAt(i2))) {
                    ArrayMap.this.removeAt(i2);
                    i2--;
                    i--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            int i = ArrayMap.this.j;
            int i2 = 0;
            boolean z = false;
            while (i2 < i) {
                if (!collection.contains(ArrayMap.this.valueAt(i2))) {
                    ArrayMap.this.removeAt(i2);
                    i2--;
                    i--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        @Override // java.util.Collection
        public int size() {
            return ArrayMap.this.j;
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            int i = ArrayMap.this.j;
            Object[] objArr = new Object[i];
            for (int i2 = 0; i2 < i; i2++) {
                objArr[i2] = ArrayMap.this.valueAt(i2);
            }
            return objArr;
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) ArrayMap.this.a((Object[]) tArr, 1);
        }
    }

    /* loaded from: classes.dex */
    final class b extends b<K> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b() {
            super(r1.j);
            ArrayMap.this = r1;
        }

        @Override // androidx.collection.b
        protected K a(int i) {
            return ArrayMap.this.keyAt(i);
        }

        @Override // androidx.collection.b
        protected void b(int i) {
            ArrayMap.this.removeAt(i);
        }
    }

    /* loaded from: classes.dex */
    final class f extends b<V> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        f() {
            super(r1.j);
            ArrayMap.this = r1;
        }

        @Override // androidx.collection.b
        protected V a(int i) {
            return ArrayMap.this.valueAt(i);
        }

        @Override // androidx.collection.b
        protected void b(int i) {
            ArrayMap.this.removeAt(i);
        }
    }

    /* loaded from: classes.dex */
    final class d implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
        int a;
        int b = -1;
        boolean c;

        d() {
            ArrayMap.this = r1;
            this.a = r1.j - 1;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b < this.a;
        }

        /* renamed from: a */
        public Map.Entry<K, V> next() {
            if (hasNext()) {
                this.b++;
                this.c = true;
                return this;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.c) {
                ArrayMap.this.removeAt(this.b);
                this.b--;
                this.a--;
                this.c = false;
                return;
            }
            throw new IllegalStateException();
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            if (this.c) {
                return ArrayMap.this.keyAt(this.b);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            if (this.c) {
                return ArrayMap.this.valueAt(this.b);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (this.c) {
                return ArrayMap.this.setValueAt(this.b, v);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!this.c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            } else if (!(obj instanceof Map.Entry)) {
                return false;
            } else {
                Map.Entry entry = (Map.Entry) obj;
                return a.a(entry.getKey(), ArrayMap.this.keyAt(this.b)) && a.a(entry.getValue(), ArrayMap.this.valueAt(this.b));
            }
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            if (this.c) {
                K keyAt = ArrayMap.this.keyAt(this.b);
                V valueAt = ArrayMap.this.valueAt(this.b);
                int i = 0;
                int hashCode = keyAt == null ? 0 : keyAt.hashCode();
                if (valueAt != null) {
                    i = valueAt.hashCode();
                }
                return hashCode ^ i;
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    <T> T[] a(T[] tArr, int i) {
        int i2 = this.j;
        if (tArr.length < i2) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i2));
        }
        for (int i3 = 0; i3 < i2; i3++) {
            tArr[i3] = this.i[(i3 << 1) + i];
        }
        if (tArr.length > i2) {
            tArr[i2] = null;
        }
        return tArr;
    }

    static <T> boolean a(Set<T> set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set2 = (Set) obj;
            try {
                if (set.size() == set2.size()) {
                    if (set.containsAll(set2)) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }
}
