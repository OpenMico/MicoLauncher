package org.fourthline.cling.support.shared;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public abstract class AbstractMap<K, V> implements Map<K, V> {
    Set<K> keySet;
    Collection<V> valuesCollection;

    @Override // java.util.Map
    public abstract Set<Map.Entry<K, V>> entrySet();

    /* loaded from: classes5.dex */
    public static class SimpleImmutableEntry<K, V> implements Serializable, Map.Entry<K, V> {
        private static final long serialVersionUID = 7138329143949025153L;
        private final K key;
        private final V value;

        public SimpleImmutableEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public SimpleImmutableEntry(Map.Entry<? extends K, ? extends V> entry) {
            this.key = (K) entry.getKey();
            this.value = (V) entry.getValue();
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            K k = this.key;
            if (k != null ? k.equals(entry.getKey()) : entry.getKey() == null) {
                V v = this.value;
                if (v == null) {
                    if (entry.getValue() == null) {
                        return true;
                    }
                } else if (v.equals(entry.getValue())) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            K k = this.key;
            int i = 0;
            int hashCode = k == null ? 0 : k.hashCode();
            V v = this.value;
            if (v != null) {
                i = v.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }
    }

    /* loaded from: classes5.dex */
    public static class SimpleEntry<K, V> implements Serializable, Map.Entry<K, V> {
        private static final long serialVersionUID = -8499721149061103585L;
        private final K key;
        private V value;

        public SimpleEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public SimpleEntry(Map.Entry<? extends K, ? extends V> entry) {
            this.key = (K) entry.getKey();
            this.value = (V) entry.getValue();
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            K k = this.key;
            if (k != null ? k.equals(entry.getKey()) : entry.getKey() == null) {
                V v = this.value;
                if (v == null) {
                    if (entry.getValue() == null) {
                        return true;
                    }
                } else if (v.equals(entry.getValue())) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            K k = this.key;
            int i = 0;
            int hashCode = k == null ? 0 : k.hashCode();
            V v = this.value;
            if (v != null) {
                i = v.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }
    }

    protected AbstractMap() {
    }

    @Override // java.util.Map
    public void clear() {
        entrySet().clear();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        Iterator<Map.Entry<K, V>> it = entrySet().iterator();
        if (obj != null) {
            while (it.hasNext()) {
                if (obj.equals(it.next().getKey())) {
                    return true;
                }
            }
            return false;
        }
        while (it.hasNext()) {
            if (it.next().getKey() == null) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        Iterator<Map.Entry<K, V>> it = entrySet().iterator();
        if (obj != null) {
            while (it.hasNext()) {
                if (obj.equals(it.next().getValue())) {
                    return true;
                }
            }
            return false;
        }
        while (it.hasNext()) {
            if (it.next().getValue() == null) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (size() != map.size()) {
            return false;
        }
        try {
            for (Map.Entry<K, V> entry : entrySet()) {
                K key = entry.getKey();
                V value = entry.getValue();
                Object obj2 = map.get(key);
                if (value == null) {
                    if (obj2 != null || !map.containsKey(key)) {
                        return false;
                    }
                } else if (!value.equals(obj2)) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused2) {
            return false;
        }
    }

    @Override // java.util.Map
    public V get(Object obj) {
        Iterator<Map.Entry<K, V>> it = entrySet().iterator();
        if (obj != null) {
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (obj.equals(next.getKey())) {
                    return next.getValue();
                }
            }
            return null;
        }
        while (it.hasNext()) {
            Map.Entry<K, V> next2 = it.next();
            if (next2.getKey() == null) {
                return next2.getValue();
            }
        }
        return null;
    }

    @Override // java.util.Map
    public int hashCode() {
        int i = 0;
        for (Map.Entry<K, V> entry : entrySet()) {
            i += entry.hashCode();
        }
        return i;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        if (this.keySet == null) {
            this.keySet = new AbstractSet<K>() { // from class: org.fourthline.cling.support.shared.AbstractMap.1
                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean contains(Object obj) {
                    return AbstractMap.this.containsKey(obj);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return AbstractMap.this.size();
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<K> iterator() {
                    return new Iterator<K>() { // from class: org.fourthline.cling.support.shared.AbstractMap.1.1
                        Iterator<Map.Entry<K, V>> setIterator;

                        {
                            this.setIterator = AbstractMap.this.entrySet().iterator();
                        }

                        @Override // java.util.Iterator
                        public boolean hasNext() {
                            return this.setIterator.hasNext();
                        }

                        @Override // java.util.Iterator
                        public K next() {
                            return this.setIterator.next().getKey();
                        }

                        @Override // java.util.Iterator
                        public void remove() {
                            this.setIterator.remove();
                        }
                    };
                }
            };
        }
        return this.keySet;
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        Iterator<Map.Entry<K, V>> it = entrySet().iterator();
        if (obj != null) {
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (obj.equals(next.getKey())) {
                    it.remove();
                    return next.getValue();
                }
            }
            return null;
        }
        while (it.hasNext()) {
            Map.Entry<K, V> next2 = it.next();
            if (next2.getKey() == null) {
                it.remove();
                return next2.getValue();
            }
        }
        return null;
    }

    @Override // java.util.Map
    public int size() {
        return entrySet().size();
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(size() * 28);
        sb.append('{');
        Iterator<Map.Entry<K, V>> it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            K key = next.getKey();
            if (key != this) {
                sb.append(key);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            V value = next.getValue();
            if (value != this) {
                sb.append(value);
            } else {
                sb.append("(this Map)");
            }
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        if (this.valuesCollection == null) {
            this.valuesCollection = new AbstractCollection<V>() { // from class: org.fourthline.cling.support.shared.AbstractMap.2
                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return AbstractMap.this.size();
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public boolean contains(Object obj) {
                    return AbstractMap.this.containsValue(obj);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
                public Iterator<V> iterator() {
                    return new Iterator<V>() { // from class: org.fourthline.cling.support.shared.AbstractMap.2.1
                        Iterator<Map.Entry<K, V>> setIterator;

                        {
                            this.setIterator = AbstractMap.this.entrySet().iterator();
                        }

                        @Override // java.util.Iterator
                        public boolean hasNext() {
                            return this.setIterator.hasNext();
                        }

                        @Override // java.util.Iterator
                        public V next() {
                            return this.setIterator.next().getValue();
                        }

                        @Override // java.util.Iterator
                        public void remove() {
                            this.setIterator.remove();
                        }
                    };
                }
            };
        }
        return this.valuesCollection;
    }

    protected Object clone() throws CloneNotSupportedException {
        AbstractMap abstractMap = (AbstractMap) super.clone();
        abstractMap.keySet = null;
        abstractMap.valuesCollection = null;
        return abstractMap;
    }
}
