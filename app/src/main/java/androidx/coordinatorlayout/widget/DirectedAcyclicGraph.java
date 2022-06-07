package androidx.coordinatorlayout.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.collection.SimpleArrayMap;
import androidx.core.util.Pools;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class DirectedAcyclicGraph<T> {
    private final Pools.Pool<ArrayList<T>> a = new Pools.SimplePool(10);
    private final SimpleArrayMap<T, ArrayList<T>> b = new SimpleArrayMap<>();
    private final ArrayList<T> c = new ArrayList<>();
    private final HashSet<T> d = new HashSet<>();

    public void addNode(@NonNull T t) {
        if (!this.b.containsKey(t)) {
            this.b.put(t, null);
        }
    }

    public boolean contains(@NonNull T t) {
        return this.b.containsKey(t);
    }

    public void addEdge(@NonNull T t, @NonNull T t2) {
        if (!this.b.containsKey(t) || !this.b.containsKey(t2)) {
            throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
        }
        ArrayList<T> arrayList = this.b.get(t);
        if (arrayList == null) {
            arrayList = a();
            this.b.put(t, arrayList);
        }
        arrayList.add(t2);
    }

    @Nullable
    public List getIncomingEdges(@NonNull T t) {
        return this.b.get(t);
    }

    @Nullable
    public List<T> getOutgoingEdges(@NonNull T t) {
        int size = this.b.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            ArrayList<T> valueAt = this.b.valueAt(i);
            if (valueAt != null && valueAt.contains(t)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(this.b.keyAt(i));
            }
        }
        return arrayList;
    }

    public boolean hasOutgoingEdges(@NonNull T t) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            ArrayList<T> valueAt = this.b.valueAt(i);
            if (valueAt != null && valueAt.contains(t)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            ArrayList<T> valueAt = this.b.valueAt(i);
            if (valueAt != null) {
                a(valueAt);
            }
        }
        this.b.clear();
    }

    @NonNull
    public ArrayList<T> getSortedList() {
        this.c.clear();
        this.d.clear();
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            a(this.b.keyAt(i), this.c, this.d);
        }
        return this.c;
    }

    private void a(T t, ArrayList<T> arrayList, HashSet<T> hashSet) {
        if (!arrayList.contains(t)) {
            if (!hashSet.contains(t)) {
                hashSet.add(t);
                ArrayList<T> arrayList2 = this.b.get(t);
                if (arrayList2 != null) {
                    int size = arrayList2.size();
                    for (int i = 0; i < size; i++) {
                        a(arrayList2.get(i), arrayList, hashSet);
                    }
                }
                hashSet.remove(t);
                arrayList.add(t);
                return;
            }
            throw new RuntimeException("This graph contains cyclic dependencies");
        }
    }

    @NonNull
    private ArrayList<T> a() {
        ArrayList<T> acquire = this.a.acquire();
        return acquire == null ? new ArrayList<>() : acquire;
    }

    private void a(@NonNull ArrayList<T> arrayList) {
        arrayList.clear();
        this.a.release(arrayList);
    }
}
