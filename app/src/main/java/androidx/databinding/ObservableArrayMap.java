package androidx.databinding;

import androidx.collection.ArrayMap;
import androidx.databinding.ObservableMap;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ObservableArrayMap<K, V> extends ArrayMap<K, V> implements ObservableMap<K, V> {
    private transient MapChangeRegistry k;

    @Override // androidx.databinding.ObservableMap
    public void addOnMapChangedCallback(ObservableMap.OnMapChangedCallback<? extends ObservableMap<K, V>, K, V> onMapChangedCallback) {
        if (this.k == null) {
            this.k = new MapChangeRegistry();
        }
        this.k.add(onMapChangedCallback);
    }

    @Override // androidx.databinding.ObservableMap
    public void removeOnMapChangedCallback(ObservableMap.OnMapChangedCallback<? extends ObservableMap<K, V>, K, V> onMapChangedCallback) {
        MapChangeRegistry mapChangeRegistry = this.k;
        if (mapChangeRegistry != null) {
            mapChangeRegistry.remove(onMapChangedCallback);
        }
    }

    @Override // androidx.collection.SimpleArrayMap, java.util.Map
    public void clear() {
        if (!isEmpty()) {
            super.clear();
            b(null);
        }
    }

    @Override // androidx.collection.SimpleArrayMap, java.util.Map
    public V put(K k, V v) {
        super.put(k, v);
        b(k);
        return v;
    }

    @Override // androidx.collection.ArrayMap
    public boolean removeAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            int indexOfKey = indexOfKey(it.next());
            if (indexOfKey >= 0) {
                z = true;
                removeAt(indexOfKey);
            }
        }
        return z;
    }

    @Override // androidx.collection.ArrayMap
    public boolean retainAll(Collection<?> collection) {
        boolean z = false;
        for (int size = size() - 1; size >= 0; size--) {
            if (!collection.contains(keyAt(size))) {
                removeAt(size);
                z = true;
            }
        }
        return z;
    }

    @Override // androidx.collection.SimpleArrayMap
    public V removeAt(int i) {
        K keyAt = keyAt(i);
        V v = (V) super.removeAt(i);
        if (v != null) {
            b(keyAt);
        }
        return v;
    }

    @Override // androidx.collection.SimpleArrayMap
    public V setValueAt(int i, V v) {
        K keyAt = keyAt(i);
        V v2 = (V) super.setValueAt(i, v);
        b(keyAt);
        return v2;
    }

    private void b(Object obj) {
        MapChangeRegistry mapChangeRegistry = this.k;
        if (mapChangeRegistry != null) {
            mapChangeRegistry.notifyCallbacks(this, 0, obj);
        }
    }
}
