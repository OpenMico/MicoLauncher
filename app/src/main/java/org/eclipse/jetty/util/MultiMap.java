package org.eclipse.jetty.util;

import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes5.dex */
public class MultiMap<K> implements Serializable, ConcurrentMap<K, Object> {
    private static final long serialVersionUID = -6878723138353851005L;
    ConcurrentMap<K, Object> _cmap;
    Map<K, Object> _map;

    public MultiMap() {
        this._map = new HashMap();
    }

    public MultiMap(Map<K, Object> map) {
        if (map instanceof ConcurrentMap) {
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(map);
            this._cmap = concurrentHashMap;
            this._map = concurrentHashMap;
            return;
        }
        this._map = new HashMap(map);
    }

    public MultiMap(MultiMap<K> multiMap) {
        ConcurrentMap<K, Object> concurrentMap = multiMap._cmap;
        if (concurrentMap != null) {
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(concurrentMap);
            this._cmap = concurrentHashMap;
            this._map = concurrentHashMap;
            return;
        }
        this._map = new HashMap(multiMap._map);
    }

    public MultiMap(int i) {
        this._map = new HashMap(i);
    }

    public MultiMap(boolean z) {
        if (z) {
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
            this._cmap = concurrentHashMap;
            this._map = concurrentHashMap;
            return;
        }
        this._map = new HashMap();
    }

    public List getValues(Object obj) {
        return LazyList.getList(this._map.get(obj), true);
    }

    public Object getValue(Object obj, int i) {
        Object obj2 = this._map.get(obj);
        if (i == 0 && LazyList.size(obj2) == 0) {
            return null;
        }
        return LazyList.get(obj2, i);
    }

    public String getString(Object obj) {
        Object obj2 = this._map.get(obj);
        switch (LazyList.size(obj2)) {
            case 0:
                return null;
            case 1:
                Object obj3 = LazyList.get(obj2, 0);
                if (obj3 == null) {
                    return null;
                }
                return obj3.toString();
            default:
                StringBuilder sb = new StringBuilder(128);
                for (int i = 0; i < LazyList.size(obj2); i++) {
                    Object obj4 = LazyList.get(obj2, i);
                    if (obj4 != null) {
                        if (sb.length() > 0) {
                            sb.append(StringUtil.COMMA);
                        }
                        sb.append(obj4.toString());
                    }
                }
                return sb.toString();
        }
    }

    @Override // java.util.Map
    public Object get(Object obj) {
        Object obj2 = this._map.get(obj);
        switch (LazyList.size(obj2)) {
            case 0:
                return null;
            case 1:
                return LazyList.get(obj2, 0);
            default:
                return LazyList.getList(obj2, true);
        }
    }

    @Override // java.util.Map
    public Object put(K k, Object obj) {
        return this._map.put(k, LazyList.add(null, obj));
    }

    public Object putValues(K k, List<? extends Object> list) {
        return this._map.put(k, list);
    }

    public Object putValues(K k, String... strArr) {
        Object obj = null;
        for (String str : strArr) {
            obj = LazyList.add(obj, str);
        }
        return this._map.put(k, obj);
    }

    public void add(K k, Object obj) {
        Object obj2 = this._map.get(k);
        Object add = LazyList.add(obj2, obj);
        if (obj2 != add) {
            this._map.put(k, add);
        }
    }

    public void addValues(K k, List<? extends Object> list) {
        Object obj = this._map.get(k);
        Object addCollection = LazyList.addCollection(obj, list);
        if (obj != addCollection) {
            this._map.put(k, addCollection);
        }
    }

    public void addValues(K k, String[] strArr) {
        Object obj = this._map.get(k);
        Object addCollection = LazyList.addCollection(obj, Arrays.asList(strArr));
        if (obj != addCollection) {
            this._map.put(k, addCollection);
        }
    }

    public boolean removeValue(K k, Object obj) {
        Object obj2 = this._map.get(k);
        int size = LazyList.size(obj2);
        if (size > 0) {
            obj2 = LazyList.remove(obj2, obj);
            if (obj2 == null) {
                this._map.remove(k);
            } else {
                this._map.put(k, obj2);
            }
        }
        return LazyList.size(obj2) != size;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends Object> map) {
        if (map instanceof MultiMap) {
            for (Map.Entry<? extends K, ? extends Object> entry : map.entrySet()) {
                this._map.put(entry.getKey(), LazyList.clone(entry.getValue()));
            }
            return;
        }
        this._map.putAll(map);
    }

    public Map<K, String[]> toStringArrayMap() {
        HashMap<K, String[]> hashMap = new HashMap<K, String[]>((this._map.size() * 3) / 2) { // from class: org.eclipse.jetty.util.MultiMap.1
            @Override // java.util.AbstractMap
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append('{');
                for (K k : keySet()) {
                    if (sb.length() > 1) {
                        sb.append(StringUtil.COMMA);
                    }
                    sb.append(k);
                    sb.append('=');
                    sb.append(Arrays.asList(get(k)));
                }
                sb.append('}');
                return sb.toString();
            }
        };
        for (Map.Entry<K, Object> entry : this._map.entrySet()) {
            hashMap.put(entry.getKey(), LazyList.toStringArray(entry.getValue()));
        }
        return hashMap;
    }

    public String toString() {
        Object obj = this._cmap;
        if (obj == null) {
            obj = this._map;
        }
        return obj.toString();
    }

    @Override // java.util.Map
    public void clear() {
        this._map.clear();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this._map.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this._map.containsValue(obj);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, Object>> entrySet() {
        return this._map.entrySet();
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        return this._map.equals(obj);
    }

    @Override // java.util.Map
    public int hashCode() {
        return this._map.hashCode();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this._map.isEmpty();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return this._map.keySet();
    }

    @Override // java.util.Map
    public Object remove(Object obj) {
        return this._map.remove(obj);
    }

    @Override // java.util.Map
    public int size() {
        return this._map.size();
    }

    @Override // java.util.Map
    public Collection<Object> values() {
        return this._map.values();
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public Object putIfAbsent(K k, Object obj) {
        ConcurrentMap<K, Object> concurrentMap = this._cmap;
        if (concurrentMap != null) {
            return concurrentMap.putIfAbsent(k, obj);
        }
        throw new UnsupportedOperationException();
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean remove(Object obj, Object obj2) {
        ConcurrentMap<K, Object> concurrentMap = this._cmap;
        if (concurrentMap != null) {
            return concurrentMap.remove(obj, obj2);
        }
        throw new UnsupportedOperationException();
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean replace(K k, Object obj, Object obj2) {
        ConcurrentMap<K, Object> concurrentMap = this._cmap;
        if (concurrentMap != null) {
            return concurrentMap.replace(k, obj, obj2);
        }
        throw new UnsupportedOperationException();
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public Object replace(K k, Object obj) {
        ConcurrentMap<K, Object> concurrentMap = this._cmap;
        if (concurrentMap != null) {
            return concurrentMap.replace(k, obj);
        }
        throw new UnsupportedOperationException();
    }
}
