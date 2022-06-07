package io.netty.util.collection;

import io.netty.util.collection.LongObjectMap;
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
public class LongObjectHashMap<V> implements LongObjectMap<V> {
    public static final int DEFAULT_CAPACITY = 8;
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final Object a = new Object();
    private int b;
    private final float c;
    private long[] d;
    private V[] e;
    private int f;
    private int g;
    private final Set<Long> h;
    private final Set<Map.Entry<Long, V>> i;
    private final Iterable<LongObjectMap.PrimitiveEntry<V>> j;

    private static int c(long j) {
        return (int) (j ^ (j >>> 32));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object put(Long l, Object obj) {
        return put2(l, (Long) obj);
    }

    public LongObjectHashMap() {
        this(8, 0.5f);
    }

    public LongObjectHashMap(int i) {
        this(i, 0.5f);
    }

    public LongObjectHashMap(int i, float f) {
        this.h = new b();
        this.i = new a();
        this.j = new Iterable<LongObjectMap.PrimitiveEntry<V>>() { // from class: io.netty.util.collection.LongObjectHashMap.1
            @Override // java.lang.Iterable
            public Iterator<LongObjectMap.PrimitiveEntry<V>> iterator() {
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
            this.d = new long[findNextPositivePowerOfTwo];
            this.e = (V[]) new Object[findNextPositivePowerOfTwo];
            this.b = c(findNextPositivePowerOfTwo);
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

    @Override // io.netty.util.collection.LongObjectMap
    public V get(long j) {
        int a2 = a(j);
        if (a2 == -1) {
            return null;
        }
        return (V) c(this.e[a2]);
    }

    @Override // io.netty.util.collection.LongObjectMap
    public V put(long j, V v) {
        int b2 = b(j);
        int i = b2;
        do {
            Object[] objArr = this.e;
            if (objArr[i] == null) {
                this.d[i] = j;
                objArr[i] = d(v);
                a();
                return null;
            } else if (this.d[i] == j) {
                Object obj = objArr[i];
                objArr[i] = d(v);
                return (V) c(obj);
            } else {
                i = a(i);
            }
        } while (i != b2);
        throw new IllegalStateException("Unable to insert");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public void putAll(Map<? extends Long, ? extends V> map) {
        if (map instanceof LongObjectHashMap) {
            LongObjectHashMap longObjectHashMap = (LongObjectHashMap) map;
            int i = 0;
            while (true) {
                V[] vArr = longObjectHashMap.e;
                if (i < vArr.length) {
                    V v = vArr[i];
                    if (v != null) {
                        put(longObjectHashMap.d[i], (long) v);
                    }
                    i++;
                } else {
                    return;
                }
            }
        } else {
            for (Map.Entry<? extends Long, ? extends V> entry : map.entrySet()) {
                put2((Long) entry.getKey(), (Long) entry.getValue());
            }
        }
    }

    @Override // io.netty.util.collection.LongObjectMap
    public V remove(long j) {
        int a2 = a(j);
        if (a2 == -1) {
            return null;
        }
        V v = this.e[a2];
        b(a2);
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
        Arrays.fill(this.d, 0L);
        Arrays.fill(this.e, (Object) null);
        this.f = 0;
    }

    @Override // io.netty.util.collection.LongObjectMap
    public boolean containsKey(long j) {
        return a(j) >= 0;
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

    @Override // io.netty.util.collection.LongObjectMap
    public Iterable<LongObjectMap.PrimitiveEntry<V>> entries() {
        return this.j;
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return new AbstractCollection<V>() { // from class: io.netty.util.collection.LongObjectHashMap.2
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<V> iterator() {
                return new Iterator<V>() { // from class: io.netty.util.collection.LongObjectHashMap.2.1
                    final LongObjectHashMap<V>.e a;

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
                return LongObjectHashMap.this.f;
            }
        };
    }

    @Override // java.util.Map
    public int hashCode() {
        int i = this.f;
        for (long j : this.d) {
            i ^= c(j);
        }
        return i;
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LongObjectMap)) {
            return false;
        }
        LongObjectMap longObjectMap = (LongObjectMap) obj;
        if (this.f != longObjectMap.size()) {
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
                Object obj2 = longObjectMap.get(this.d[i]);
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
    public V put2(Long l, V v) {
        return put(e(l), (long) v);
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        return remove(e(obj));
    }

    @Override // java.util.Map
    public Set<Long> keySet() {
        return this.h;
    }

    @Override // java.util.Map
    public Set<Map.Entry<Long, V>> entrySet() {
        return this.i;
    }

    private long e(Object obj) {
        return ((Long) obj).longValue();
    }

    private int a(long j) {
        int b2 = b(j);
        int i = b2;
        while (this.e[i] != null) {
            if (j == this.d[i]) {
                return i;
            }
            i = a(i);
            if (i == b2) {
                return -1;
            }
        }
        return -1;
    }

    private int b(long j) {
        return c(j) & this.g;
    }

    private int a(int i) {
        return (i + 1) & this.g;
    }

    private void a() {
        this.f++;
        if (this.f > this.b) {
            long[] jArr = this.d;
            if (jArr.length != Integer.MAX_VALUE) {
                d(jArr.length << 1);
                return;
            }
            throw new IllegalStateException("Max capacity reached at size=" + this.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(int i) {
        this.f--;
        this.d[i] = 0;
        this.e[i] = null;
        int a2 = a(i);
        boolean z = false;
        while (this.e[a2] != null) {
            int b2 = b(this.d[a2]);
            if ((a2 < b2 && (b2 <= i || i <= a2)) || (b2 <= i && i <= a2)) {
                long[] jArr = this.d;
                jArr[i] = jArr[a2];
                V[] vArr = this.e;
                vArr[i] = vArr[a2];
                jArr[a2] = 0;
                vArr[a2] = null;
                i = a2;
                z = true;
            }
            a2 = a(a2);
        }
        return z;
    }

    private int c(int i) {
        return Math.min(i - 1, (int) (i * this.c));
    }

    private void d(int i) {
        V[] vArr;
        long[] jArr = this.d;
        V[] vArr2 = this.e;
        this.d = new long[i];
        this.e = (V[]) new Object[i];
        this.b = c(i);
        this.g = i - 1;
        for (int i2 = 0; i2 < vArr2.length; i2++) {
            V v = vArr2[i2];
            if (v != null) {
                long j = jArr[i2];
                int b2 = b(j);
                while (true) {
                    vArr = this.e;
                    if (vArr[b2] == null) {
                        break;
                    }
                    b2 = a(b2);
                }
                this.d[b2] = j;
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

    protected String keyToString(long j) {
        return Long.toString(j);
    }

    /* loaded from: classes4.dex */
    private final class a extends AbstractSet<Map.Entry<Long, V>> {
        private a() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Long, V>> iterator() {
            return new d();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return LongObjectHashMap.this.size();
        }
    }

    /* loaded from: classes4.dex */
    private final class b extends AbstractSet<Long> {
        private b() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return LongObjectHashMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return LongObjectHashMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return LongObjectHashMap.this.remove(obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            Iterator<LongObjectMap.PrimitiveEntry<V>> it = LongObjectHashMap.this.entries().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (!collection.contains(Long.valueOf(it.next().key()))) {
                    z = true;
                    it.remove();
                }
            }
            return z;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            LongObjectHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Long> iterator() {
            return new Iterator<Long>() { // from class: io.netty.util.collection.LongObjectHashMap.b.1
                private final Iterator<Map.Entry<Long, V>> b;

                {
                    this.b = LongObjectHashMap.this.i.iterator();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.b.hasNext();
                }

                /* renamed from: a */
                public Long next() {
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
    public final class e implements LongObjectMap.PrimitiveEntry<V>, Iterator<LongObjectMap.PrimitiveEntry<V>> {
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
                if (i == LongObjectHashMap.this.e.length) {
                    return;
                }
            } while (LongObjectHashMap.this.e[this.c] == null);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.c == -1) {
                b();
            }
            return this.c < LongObjectHashMap.this.d.length;
        }

        /* renamed from: a */
        public LongObjectMap.PrimitiveEntry<V> next() {
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
                if (LongObjectHashMap.this.b(i)) {
                    this.c = this.b;
                }
                this.b = -1;
                return;
            }
            throw new IllegalStateException("next must be called before each remove.");
        }

        @Override // io.netty.util.collection.LongObjectMap.PrimitiveEntry
        public long key() {
            return LongObjectHashMap.this.d[this.d];
        }

        @Override // io.netty.util.collection.LongObjectMap.PrimitiveEntry
        public V value() {
            return (V) LongObjectHashMap.c(LongObjectHashMap.this.e[this.d]);
        }

        @Override // io.netty.util.collection.LongObjectMap.PrimitiveEntry
        public void setValue(V v) {
            LongObjectHashMap.this.e[this.d] = LongObjectHashMap.d(v);
        }
    }

    /* loaded from: classes4.dex */
    private final class d implements Iterator<Map.Entry<Long, V>> {
        private final LongObjectHashMap<V>.e b;

        private d() {
            this.b = new e();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b.hasNext();
        }

        /* renamed from: a */
        public Map.Entry<Long, V> next() {
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
    public final class c implements Map.Entry<Long, V> {
        private final int b;

        c(int i) {
            this.b = i;
        }

        /* renamed from: a */
        public Long getKey() {
            b();
            return Long.valueOf(LongObjectHashMap.this.d[this.b]);
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            b();
            return (V) LongObjectHashMap.c(LongObjectHashMap.this.e[this.b]);
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            b();
            V v2 = (V) LongObjectHashMap.c(LongObjectHashMap.this.e[this.b]);
            LongObjectHashMap.this.e[this.b] = LongObjectHashMap.d(v);
            return v2;
        }

        private void b() {
            if (LongObjectHashMap.this.e[this.b] == null) {
                throw new IllegalStateException("The map entry has been removed");
            }
        }
    }
}
