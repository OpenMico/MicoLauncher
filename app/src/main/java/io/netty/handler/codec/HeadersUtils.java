package io.netty.handler.codec;

import io.netty.util.internal.ObjectUtil;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public final class HeadersUtils {
    private HeadersUtils() {
    }

    public static <K, V> List<String> getAllAsString(Headers<K, V, ?> headers, K k) {
        final List<V> all = headers.getAll(k);
        return new AbstractList<String>() { // from class: io.netty.handler.codec.HeadersUtils.1
            /* renamed from: a */
            public String get(int i) {
                Object obj = all.get(i);
                if (obj != null) {
                    return obj.toString();
                }
                return null;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return all.size();
            }
        };
    }

    public static <K, V> String getAsString(Headers<K, V, ?> headers, K k) {
        V v = headers.get(k);
        if (v != null) {
            return v.toString();
        }
        return null;
    }

    public static Iterator<Map.Entry<String, String>> iteratorAsString(Iterable<Map.Entry<CharSequence, CharSequence>> iterable) {
        return new d(iterable.iterator());
    }

    public static Set<String> namesAsString(Headers<CharSequence, CharSequence, ?> headers) {
        return new a(headers.names());
    }

    /* loaded from: classes4.dex */
    private static final class d implements Iterator<Map.Entry<String, String>> {
        private final Iterator<Map.Entry<CharSequence, CharSequence>> a;

        public d(Iterator<Map.Entry<CharSequence, CharSequence>> it) {
            this.a = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.a.hasNext();
        }

        /* renamed from: a */
        public Map.Entry<String, String> next() {
            return new c(this.a.next());
        }

        @Override // java.util.Iterator
        public void remove() {
            this.a.remove();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class c implements Map.Entry<String, String> {
        private final Map.Entry<CharSequence, CharSequence> a;
        private String b;
        private String c;

        c(Map.Entry<CharSequence, CharSequence> entry) {
            this.a = entry;
        }

        /* renamed from: a */
        public String getKey() {
            if (this.b == null) {
                this.b = this.a.getKey().toString();
            }
            return this.b;
        }

        /* renamed from: b */
        public String getValue() {
            if (this.c == null && this.a.getValue() != null) {
                this.c = this.a.getValue().toString();
            }
            return this.c;
        }

        /* renamed from: a */
        public String setValue(String str) {
            String b = getValue();
            this.a.setValue(str);
            return b;
        }

        public String toString() {
            return this.a.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class e<T> implements Iterator<String> {
        private final Iterator<T> a;

        public e(Iterator<T> it) {
            this.a = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.a.hasNext();
        }

        /* renamed from: a */
        public String next() {
            T next = this.a.next();
            if (next != null) {
                return next.toString();
            }
            return null;
        }

        @Override // java.util.Iterator
        public void remove() {
            this.a.remove();
        }
    }

    /* loaded from: classes4.dex */
    private static final class a extends b<CharSequence> {
        public a(Set<CharSequence> set) {
            super(set);
        }

        /* renamed from: a */
        public boolean add(String str) {
            return this.a.add(str);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends String> collection) {
            return this.a.addAll(collection);
        }
    }

    /* loaded from: classes4.dex */
    private static abstract class b<T> implements Set<String> {
        protected final Set<T> a;

        public b(Set<T> set) {
            this.a = (Set) ObjectUtil.checkNotNull(set, "allNames");
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return this.a.size();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            return this.a.contains(obj.toString());
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<String> iterator() {
            return new e(this.a.iterator());
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            String[] strArr = new String[size()];
            a(strArr);
            return strArr;
        }

        @Override // java.util.Set, java.util.Collection
        public <X> X[] toArray(X[] xArr) {
            if (xArr == null || xArr.length < size()) {
                X[] xArr2 = (X[]) new Object[size()];
                a(xArr2);
                return xArr2;
            }
            a(xArr);
            return xArr;
        }

        private void a(Object[] objArr) {
            Iterator<T> it = this.a.iterator();
            for (int i = 0; i < size(); i++) {
                objArr[i] = it.next();
            }
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            return this.a.remove(obj);
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
        public boolean removeAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (remove(it.next())) {
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            Iterator<String> it = iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (!collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            this.a.clear();
        }
    }
}
