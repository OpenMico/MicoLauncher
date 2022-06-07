package io.netty.util.collection;

import io.netty.util.collection.IntObjectMap;
import io.netty.util.internal.MathUtil;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes4.dex */
public class IntObjectHashMap<V> implements IntObjectMap<V> {
    public static final int DEFAULT_CAPACITY = 8;
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final Object a = new Object();
    private int b;
    private final float c;
    private int[] d;
    private V[] e;
    private int f;
    private int g;
    private final Set<Integer> h;
    private final Set<Map.Entry<Integer, V>> i;
    private final Iterable<IntObjectMap.PrimitiveEntry<V>> j;

    private static int c(int i) {
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object put(Integer num, Object obj) {
        return put2(num, (Integer) obj);
    }

    public IntObjectHashMap() {
        this(8, 0.5f);
    }

    public IntObjectHashMap(int i) {
        this(i, 0.5f);
    }

    public IntObjectHashMap(int i, float f) {
        this.h = new b();
        this.i = new a();
        this.j = new Iterable<IntObjectMap.PrimitiveEntry<V>>() { // from class: io.netty.util.collection.IntObjectHashMap.1
            @Override // java.lang.Iterable
            public Iterator<IntObjectMap.PrimitiveEntry<V>> iterator() {
                return new e();
            }
        };
        if (i < 1) {
            throw new IllegalArgumentException("initialCapacity must be >= 1");
        } else if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
        } else {
            this.c = f;
            int findNextPositivePowerOfTwo = MathUtil.findNextPositivePowerOfTwo(i);
            this.g = findNextPositivePowerOfTwo - 1;
            this.d = new int[findNextPositivePowerOfTwo];
            this.e = (V[]) new Object[findNextPositivePowerOfTwo];
            this.b = f(findNextPositivePowerOfTwo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T c(T t) {
        if (t == a) {
            return null;
        }
        return t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T d(T t) {
        return t == null ? (T) a : t;
    }

    @Override // io.netty.util.collection.IntObjectMap
    public V get(int i) {
        int a2 = a(i);
        if (a2 == -1) {
            return null;
        }
        return (V) c(this.e[a2]);
    }

    @Override // io.netty.util.collection.IntObjectMap
    public V put(int i, V v) {
        int b2 = b(i);
        int i2 = b2;
        do {
            Object[] objArr = this.e;
            if (objArr[i2] == null) {
                this.d[i2] = i;
                objArr[i2] = d(v);
                a();
                return null;
            } else if (this.d[i2] == i) {
                Object obj = objArr[i2];
                objArr[i2] = d(v);
                return (V) c(obj);
            } else {
                i2 = d(i2);
            }
        } while (i2 != b2);
        throw new IllegalStateException("Unable to insert");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public void putAll(Map<? extends Integer, ? extends V> map) {
        if (map instanceof IntObjectHashMap) {
            IntObjectHashMap intObjectHashMap = (IntObjectHashMap) map;
            int i = 0;
            while (true) {
                V[] vArr = intObjectHashMap.e;
                if (i < vArr.length) {
                    V v = vArr[i];
                    if (v != null) {
                        put(intObjectHashMap.d[i], (int) v);
                    }
                    i++;
                } else {
                    return;
                }
            }
        } else {
            for (Map.Entry<? extends Integer, ? extends V> entry : map.entrySet()) {
                put2((Integer) entry.getKey(), (Integer) entry.getValue());
            }
        }
    }

    @Override // io.netty.util.collection.IntObjectMap
    public V remove(int i) {
        int a2 = a(i);
        if (a2 == -1) {
            return null;
        }
        V v = this.e[a2];
        e(a2);
        return (V) c(v);
    }

    @Override // java.util.Map
    public int size() {
        return this.f;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.f == 0;
    }

    @Override // java.util.Map
    public void clear() {
        Arrays.fill(this.d, 0);
        Arrays.fill(this.e, (Object) null);
        this.f = 0;
    }

    @Override // io.netty.util.collection.IntObjectMap
    public boolean containsKey(int i) {
        return a(i) >= 0;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        Object d2 = d(obj);
        V[] vArr = this.e;
        for (V v : vArr) {
            if (v != null && v.equals(d2)) {
                return true;
            }
        }
        return false;
    }

    @Override // io.netty.util.collection.IntObjectMap
    public Iterable<IntObjectMap.PrimitiveEntry<V>> entries() {
        return this.j;
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return new AbstractCollection<V>() { // from class: io.netty.util.collection.IntObjectHashMap.2
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<V> iterator() {
                return new Iterator<V>() { // from class: io.netty.util.collection.IntObjectHashMap.2.1
                    final IntObjectHashMap<V>.e a;

                    {
                        this.a = new e();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.a.hasNext();
                    }

                    @Override // java.util.Iterator
                    public V next() {
                        return this.a.next().value();
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return IntObjectHashMap.this.f;
            }
        };
    }

    @Override // java.util.Map
    public int hashCode() {
        int i = this.f;
        for (int i2 : this.d) {
            i ^= c(i2);
        }
        return i;
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IntObjectMap)) {
            return false;
        }
        IntObjectMap intObjectMap = (IntObjectMap) obj;
        if (this.f != intObjectMap.size()) {
            return false;
        }
        int i = 0;
        while (true) {
            V[] vArr = this.e;
            if (i >= vArr.length) {
                return true;
            }
            V v = vArr[i];
            if (v != null) {
                Object obj2 = intObjectMap.get(this.d[i]);
                if (v == a) {
                    if (obj2 != null) {
                        return false;
                    }
                } else if (!v.equals(obj2)) {
                    return false;
                }
            }
            i++;
        }
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return containsKey(e(obj));
    }

    @Override // java.util.Map
    public V get(Object obj) {
        return get(e(obj));
    }

    /* renamed from: put  reason: avoid collision after fix types in other method */
    public V put2(Integer num, V v) {
        return put(e(num), (int) v);
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        return remove(e(obj));
    }

    @Override // java.util.Map
    public Set<Integer> keySet() {
        return this.h;
    }

    @Override // java.util.Map
    public Set<Map.Entry<Integer, V>> entrySet() {
        return this.i;
    }

    private int e(Object obj) {
        return ((Integer) obj).intValue();
    }

    private int a(int i) {
        int b2 = b(i);
        int i2 = b2;
        while (this.e[i2] != null) {
            if (i == this.d[i2]) {
                return i2;
            }
            i2 = d(i2);
            if (i2 == b2) {
                return -1;
            }
        }
        return -1;
    }

    private int b(int i) {
        return c(i) & this.g;
    }

    private int d(int i) {
        return (i + 1) & this.g;
    }

    private void a() {
        this.f++;
        if (this.f > this.b) {
            int[] iArr = this.d;
            if (iArr.length != Integer.MAX_VALUE) {
                g(iArr.length << 1);
                return;
            }
            throw new IllegalStateException("Max capacity reached at size=" + this.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e(int i) {
        this.f--;
        this.d[i] = 0;
        this.e[i] = null;
        int d2 = d(i);
        int i2 = i;
        boolean z = false;
        while (this.e[d2] != null) {
            int b2 = b(this.d[d2]);
            if ((d2 < b2 && (b2 <= i2 || i2 <= d2)) || (b2 <= i2 && i2 <= d2)) {
                int[] iArr = this.d;
                iArr[i2] = iArr[d2];
                V[] vArr = this.e;
                vArr[i2] = vArr[d2];
                iArr[d2] = 0;
                vArr[d2] = null;
                i2 = d2;
                z = true;
            }
            d2 = d(d2);
        }
        return z;
    }

    private int f(int i) {
        return Math.min(i - 1, (int) (i * this.c));
    }

    private void g(int i) {
        V[] vArr;
        int[] iArr = this.d;
        V[] vArr2 = this.e;
        this.d = new int[i];
        this.e = (V[]) new Object[i];
        this.b = f(i);
        this.g = i - 1;
        for (int i2 = 0; i2 < vArr2.length; i2++) {
            V v = vArr2[i2];
            if (v != null) {
                int i3 = iArr[i2];
                int b2 = b(i3);
                while (true) {
                    vArr = this.e;
                    if (vArr[b2] == null) {
                        break;
                    }
                    b2 = d(b2);
                }
                this.d[b2] = i3;
                vArr[b2] = v;
            }
        }
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f * 4);
        sb.append('{');
        boolean z = true;
        int i = 0;
        while (true) {
            V[] vArr = this.e;
            if (i < vArr.length) {
                V v = vArr[i];
                if (v != null) {
                    if (!z) {
                        sb.append(", ");
                    }
                    sb.append(keyToString(this.d[i]));
                    sb.append('=');
                    sb.append(v == this ? "(this Map)" : c(v));
                    z = false;
                }
                i++;
            } else {
                sb.append('}');
                return sb.toString();
            }
        }
    }

    protected String keyToString(int i) {
        return Integer.toString(i);
    }

    /* loaded from: classes4.dex */
    private final class a extends AbstractSet<Map.Entry<Integer, V>> {
        private a() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Integer, V>> iterator() {
            return new d();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return IntObjectHashMap.this.size();
        }
    }

    /* loaded from: classes4.dex */
    private final class b extends AbstractSet<Integer> {
        private b() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return IntObjectHashMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return IntObjectHashMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return IntObjectHashMap.this.remove(obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            Iterator<IntObjectMap.PrimitiveEntry<V>> it = IntObjectHashMap.this.entries().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (!collection.contains(Integer.valueOf(it.next().key()))) {
                    z = true;
                    it.remove();
                }
            }
            return z;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            IntObjectHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() { // from class: io.netty.util.collection.IntObjectHashMap.b.1
                private final Iterator<Map.Entry<Integer, V>> b;

                {
                    this.b = IntObjectHashMap.this.i.iterator();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.b.hasNext();
                }

                /* renamed from: a */
                public Integer next() {
                    return this.b.next().getKey();
                }

                @Override // java.util.Iterator
                public void remove() {
                    this.b.remove();
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class e implements IntObjectMap.PrimitiveEntry<V>, Iterator<IntObjectMap.PrimitiveEntry<V>> {
        private int b;
        private int c;
        private int d;

        private e() {
            this.b = -1;
            this.c = -1;
            this.d = -1;
        }

        private void b() {
            do {
                int i = this.c + 1;
                this.c = i;
                if (i == IntObjectHashMap.this.e.length) {
                    return;
                }
            } while (IntObjectHashMap.this.e[this.c] == null);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.c == -1) {
                b();
            }
            return this.c < IntObjectHashMap.this.d.length;
        }

        /* renamed from: a */
        public IntObjectMap.PrimitiveEntry<V> next() {
            if (hasNext()) {
                this.b = this.c;
                b();
                this.d = this.b;
                return this;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            int i = this.b;
            if (i >= 0) {
                if (IntObjectHashMap.this.e(i)) {
                    this.c = this.b;
                }
                this.b = -1;
                return;
            }
            throw new IllegalStateException("next must be called before each remove.");
        }

        @Override // io.netty.util.collection.IntObjectMap.PrimitiveEntry
        public int key() {
            return IntObjectHashMap.this.d[this.d];
        }

        @Override // io.netty.util.collection.IntObjectMap.PrimitiveEntry
        public V value() {
            return (V) IntObjectHashMap.c(IntObjectHashMap.this.e[this.d]);
        }

        @Override // io.netty.util.collection.IntObjectMap.PrimitiveEntry
        public void setValue(V v) {
            IntObjectHashMap.this.e[this.d] = IntObjectHashMap.d(v);
        }
    }

    /* loaded from: classes4.dex */
    private final class d implements Iterator<Map.Entry<Integer, V>> {
        private final IntObjectHashMap<V>.e b;

        private d() {
            this.b = new e();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b.hasNext();
        }

        /* renamed from: a */
        public Map.Entry<Integer, V> next() {
            if (hasNext()) {
                this.b.next();
                return new c(((e) this.b).d);
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.b.remove();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public final class c implements Map.Entry<Integer, V> {
        private final int b;

        c(int i) {
            this.b = i;
        }

        /* renamed from: a */
        public Integer getKey() {
            b();
            return Integer.valueOf(IntObjectHashMap.this.d[this.b]);
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            b();
            return (V) IntObjectHashMap.c(IntObjectHashMap.this.e[this.b]);
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            b();
            V v2 = (V) IntObjectHashMap.c(IntObjectHashMap.this.e[this.b]);
            IntObjectHashMap.this.e[this.b] = IntObjectHashMap.d(v);
            return v2;
        }

        private void b() {
            if (IntObjectHashMap.this.e[this.b] == null) {
                throw new IllegalStateException("The map entry has been removed");
            }
        }
    }
}
