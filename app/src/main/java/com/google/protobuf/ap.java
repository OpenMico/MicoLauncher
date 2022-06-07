package com.google.protobuf;

import com.google.protobuf.FieldSet;
import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SmallSortedMap.java */
/* loaded from: classes2.dex */
public class ap<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private final int a;
    private List<ap<K, V>.d> b;
    private Map<K, V> c;
    private boolean d;
    private volatile ap<K, V>.f e;
    private Map<K, V> f;
    private volatile ap<K, V>.b g;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public /* synthetic */ Object put(Object obj, Object obj2) {
        return a((ap<K, V>) ((Comparable) obj), (Comparable) obj2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <FieldDescriptorType extends FieldSet.FieldDescriptorLite<FieldDescriptorType>> ap<FieldDescriptorType, Object> a(int i) {
        return (ap<FieldDescriptorType, Object>) new ap<FieldDescriptorType, Object>(i) { // from class: com.google.protobuf.ap.1
            @Override // com.google.protobuf.ap, java.util.AbstractMap, java.util.Map
            public /* synthetic */ Object put(Object obj, Object obj2) {
                return ap.super.a((AnonymousClass1) ((FieldSet.FieldDescriptorLite) obj), (FieldSet.FieldDescriptorLite) obj2);
            }

            @Override // com.google.protobuf.ap
            public void a() {
                if (!b()) {
                    for (int i2 = 0; i2 < c(); i2++) {
                        Map.Entry<FieldDescriptorType, Object> b2 = b(i2);
                        if (((FieldSet.FieldDescriptorLite) b2.getKey()).isRepeated()) {
                            b2.setValue(Collections.unmodifiableList((List) b2.getValue()));
                        }
                    }
                    Iterator it = e().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        if (((FieldSet.FieldDescriptorLite) entry.getKey()).isRepeated()) {
                            entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                        }
                    }
                }
                ap.super.a();
            }
        };
    }

    private ap(int i) {
        this.a = i;
        this.b = Collections.emptyList();
        this.c = Collections.emptyMap();
        this.f = Collections.emptyMap();
    }

    public void a() {
        Map<K, V> map;
        Map<K, V> map2;
        if (!this.d) {
            if (this.c.isEmpty()) {
                map = Collections.emptyMap();
            } else {
                map = Collections.unmodifiableMap(this.c);
            }
            this.c = map;
            if (this.f.isEmpty()) {
                map2 = Collections.emptyMap();
            } else {
                map2 = Collections.unmodifiableMap(this.f);
            }
            this.f = map2;
            this.d = true;
        }
    }

    public boolean b() {
        return this.d;
    }

    public int c() {
        return this.b.size();
    }

    public Map.Entry<K, V> b(int i) {
        return this.b.get(i);
    }

    public int d() {
        return this.c.size();
    }

    public Iterable<Map.Entry<K, V>> e() {
        if (this.c.isEmpty()) {
            return c.a();
        }
        return this.c.entrySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.b.size() + this.c.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return a((ap<K, V>) comparable) >= 0 || this.c.containsKey(comparable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int a2 = a((ap<K, V>) comparable);
        if (a2 >= 0) {
            return this.b.get(a2).getValue();
        }
        return this.c.get(comparable);
    }

    public V a(K k, V v) {
        g();
        int a2 = a((ap<K, V>) k);
        if (a2 >= 0) {
            return this.b.get(a2).setValue(v);
        }
        i();
        int i = -(a2 + 1);
        if (i >= this.a) {
            return h().put(k, v);
        }
        int size = this.b.size();
        int i2 = this.a;
        if (size == i2) {
            ap<K, V>.d remove = this.b.remove(i2 - 1);
            h().put((K) remove.getKey(), remove.getValue());
        }
        this.b.add(i, new d(k, v));
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        g();
        if (!this.b.isEmpty()) {
            this.b.clear();
        }
        if (!this.c.isEmpty()) {
            this.c.clear();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        g();
        Comparable comparable = (Comparable) obj;
        int a2 = a((ap<K, V>) comparable);
        if (a2 >= 0) {
            return (V) c(a2);
        }
        if (this.c.isEmpty()) {
            return null;
        }
        return this.c.remove(comparable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public V c(int i) {
        g();
        V value = this.b.remove(i).getValue();
        if (!this.c.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = h().entrySet().iterator();
            this.b.add(new d(this, it.next()));
            it.remove();
        }
        return value;
    }

    private int a(K k) {
        int size = this.b.size() - 1;
        if (size >= 0) {
            int compareTo = k.compareTo(this.b.get(size).getKey());
            if (compareTo > 0) {
                return -(size + 2);
            }
            if (compareTo == 0) {
                return size;
            }
        }
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) / 2;
            int compareTo2 = k.compareTo(this.b.get(i2).getKey());
            if (compareTo2 < 0) {
                size = i2 - 1;
            } else if (compareTo2 <= 0) {
                return i2;
            } else {
                i = i2 + 1;
            }
        }
        return -(i + 1);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.e == null) {
            this.e = new f();
        }
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Set<Map.Entry<K, V>> f() {
        if (this.g == null) {
            this.g = new b();
        }
        return this.g;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        if (this.d) {
            throw new UnsupportedOperationException();
        }
    }

    private SortedMap<K, V> h() {
        g();
        if (this.c.isEmpty() && !(this.c instanceof TreeMap)) {
            this.c = new TreeMap();
            this.f = ((TreeMap) this.c).descendingMap();
        }
        return (SortedMap) this.c;
    }

    private void i() {
        g();
        if (this.b.isEmpty() && !(this.b instanceof ArrayList)) {
            this.b = new ArrayList(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Incorrect field signature: TK; */
    /* compiled from: SmallSortedMap.java */
    /* loaded from: classes2.dex */
    public class d implements Comparable<ap<K, V>.d>, Map.Entry<K, V> {
        private final Comparable b;
        private V c;

        d(ap apVar, Map.Entry<K, V> entry) {
            this(entry.getKey(), entry.getValue());
        }

        d(K k, V v) {
            this.b = k;
            this.c = v;
        }

        /* renamed from: a */
        public K getKey() {
            return (K) this.b;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.c;
        }

        /* renamed from: a */
        public int compareTo(ap<K, V>.d dVar) {
            return getKey().compareTo(dVar.getKey());
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            ap.this.g();
            V v2 = this.c;
            this.c = v;
            return v2;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return a(this.b, entry.getKey()) && a(this.c, entry.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            Comparable comparable = this.b;
            int i = 0;
            int hashCode = comparable == null ? 0 : comparable.hashCode();
            V v = this.c;
            if (v != null) {
                i = v.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            return this.b + "=" + this.c;
        }

        private boolean a(Object obj, Object obj2) {
            if (obj == null) {
                return obj2 == null;
            }
            return obj.equals(obj2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SmallSortedMap.java */
    /* loaded from: classes2.dex */
    public class f extends AbstractSet<Map.Entry<K, V>> {
        private f() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new e();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return ap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            Map.Entry entry = (Map.Entry) obj;
            Object obj2 = ap.this.get(entry.getKey());
            Object value = entry.getValue();
            return obj2 == value || (obj2 != null && obj2.equals(value));
        }

        /* renamed from: a */
        public boolean add(Map.Entry<K, V> entry) {
            if (contains(entry)) {
                return false;
            }
            ap.this.a((ap) entry.getKey(), (K) entry.getValue());
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            Map.Entry entry = (Map.Entry) obj;
            if (!contains(entry)) {
                return false;
            }
            ap.this.remove(entry.getKey());
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            ap.this.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SmallSortedMap.java */
    /* loaded from: classes2.dex */
    public class b extends ap<K, V>.f {
        private b() {
            super();
        }

        @Override // com.google.protobuf.ap.f, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new a();
        }
    }

    /* compiled from: SmallSortedMap.java */
    /* loaded from: classes2.dex */
    private class e implements Iterator<Map.Entry<K, V>> {
        private int b;
        private boolean c;
        private Iterator<Map.Entry<K, V>> d;

        private e() {
            this.b = -1;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.b + 1 >= ap.this.b.size()) {
                return !ap.this.c.isEmpty() && b().hasNext();
            }
            return true;
        }

        /* renamed from: a */
        public Map.Entry<K, V> next() {
            this.c = true;
            int i = this.b + 1;
            this.b = i;
            if (i < ap.this.b.size()) {
                return (Map.Entry) ap.this.b.get(this.b);
            }
            return b().next();
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.c) {
                this.c = false;
                ap.this.g();
                if (this.b < ap.this.b.size()) {
                    ap apVar = ap.this;
                    int i = this.b;
                    this.b = i - 1;
                    apVar.c(i);
                    return;
                }
                b().remove();
                return;
            }
            throw new IllegalStateException("remove() was called before next()");
        }

        private Iterator<Map.Entry<K, V>> b() {
            if (this.d == null) {
                this.d = ap.this.c.entrySet().iterator();
            }
            return (Iterator<Map.Entry<K, V>>) this.d;
        }
    }

    /* compiled from: SmallSortedMap.java */
    /* loaded from: classes2.dex */
    private class a implements Iterator<Map.Entry<K, V>> {
        private int b;
        private Iterator<Map.Entry<K, V>> c;

        private a() {
            this.b = ap.this.b.size();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            int i = this.b;
            return (i > 0 && i <= ap.this.b.size()) || b().hasNext();
        }

        /* renamed from: a */
        public Map.Entry<K, V> next() {
            if (b().hasNext()) {
                return b().next();
            }
            List list = ap.this.b;
            int i = this.b - 1;
            this.b = i;
            return (Map.Entry) list.get(i);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private Iterator<Map.Entry<K, V>> b() {
            if (this.c == null) {
                this.c = ap.this.f.entrySet().iterator();
            }
            return (Iterator<Map.Entry<K, V>>) this.c;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SmallSortedMap.java */
    /* loaded from: classes2.dex */
    public static class c {
        private static final Iterator<Object> a = new Iterator<Object>() { // from class: com.google.protobuf.ap.c.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return false;
            }

            @Override // java.util.Iterator
            public Object next() {
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        private static final Iterable<Object> b = new Iterable<Object>() { // from class: com.google.protobuf.ap.c.2
            @Override // java.lang.Iterable
            public Iterator<Object> iterator() {
                return c.a;
            }
        };

        static <T> Iterable<T> a() {
            return (Iterable<T>) b;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ap)) {
            return super.equals(obj);
        }
        ap apVar = (ap) obj;
        int size = size();
        if (size != apVar.size()) {
            return false;
        }
        int c2 = c();
        if (c2 != apVar.c()) {
            return entrySet().equals(apVar.entrySet());
        }
        for (int i = 0; i < c2; i++) {
            if (!b(i).equals(apVar.b(i))) {
                return false;
            }
        }
        if (c2 != size) {
            return this.c.equals(apVar.c);
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int c2 = c();
        int i = 0;
        for (int i2 = 0; i2 < c2; i2++) {
            i += this.b.get(i2).hashCode();
        }
        return d() > 0 ? i + this.c.hashCode() : i;
    }
}
