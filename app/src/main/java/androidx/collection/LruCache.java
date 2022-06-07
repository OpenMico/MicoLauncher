package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public class LruCache<K, V> {
    private final LinkedHashMap<K, V> a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;

    @Nullable
    protected V create(@NonNull K k) {
        return null;
    }

    protected void entryRemoved(boolean z, @NonNull K k, @NonNull V v, @Nullable V v2) {
    }

    protected int sizeOf(@NonNull K k, @NonNull V v) {
        return 1;
    }

    public LruCache(int i) {
        if (i > 0) {
            this.c = i;
            this.a = new LinkedHashMap<>(0, 0.75f, true);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    public void resize(int i) {
        if (i > 0) {
            synchronized (this) {
                this.c = i;
            }
            trimToSize(i);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    @Nullable
    public final V get(@NonNull K k) {
        V put;
        if (k != null) {
            synchronized (this) {
                V v = this.a.get(k);
                if (v != null) {
                    this.g++;
                    return v;
                }
                this.h++;
                V create = create(k);
                if (create == null) {
                    return null;
                }
                synchronized (this) {
                    this.e++;
                    put = this.a.put(k, create);
                    if (put != null) {
                        this.a.put(k, put);
                    } else {
                        this.b += a(k, create);
                    }
                }
                if (put != null) {
                    entryRemoved(false, k, create, put);
                    return put;
                }
                trimToSize(this.c);
                return create;
            }
        }
        throw new NullPointerException("key == null");
    }

    @Nullable
    public final V put(@NonNull K k, @NonNull V v) {
        V put;
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.d++;
            this.b += a(k, v);
            put = this.a.put(k, v);
            if (put != null) {
                this.b -= a(k, put);
            }
        }
        if (put != null) {
            entryRemoved(false, k, put, v);
        }
        trimToSize(this.c);
        return put;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0070, code lost:
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void trimToSize(int r5) {
        /*
            r4 = this;
        L_0x0000:
            monitor-enter(r4)
            int r0 = r4.b     // Catch: all -> 0x0071
            if (r0 < 0) goto L_0x0052
            java.util.LinkedHashMap<K, V> r0 = r4.a     // Catch: all -> 0x0071
            boolean r0 = r0.isEmpty()     // Catch: all -> 0x0071
            if (r0 == 0) goto L_0x0011
            int r0 = r4.b     // Catch: all -> 0x0071
            if (r0 != 0) goto L_0x0052
        L_0x0011:
            int r0 = r4.b     // Catch: all -> 0x0071
            if (r0 <= r5) goto L_0x0050
            java.util.LinkedHashMap<K, V> r0 = r4.a     // Catch: all -> 0x0071
            boolean r0 = r0.isEmpty()     // Catch: all -> 0x0071
            if (r0 == 0) goto L_0x001e
            goto L_0x0050
        L_0x001e:
            java.util.LinkedHashMap<K, V> r0 = r4.a     // Catch: all -> 0x0071
            java.util.Set r0 = r0.entrySet()     // Catch: all -> 0x0071
            java.util.Iterator r0 = r0.iterator()     // Catch: all -> 0x0071
            java.lang.Object r0 = r0.next()     // Catch: all -> 0x0071
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch: all -> 0x0071
            java.lang.Object r1 = r0.getKey()     // Catch: all -> 0x0071
            java.lang.Object r0 = r0.getValue()     // Catch: all -> 0x0071
            java.util.LinkedHashMap<K, V> r2 = r4.a     // Catch: all -> 0x0071
            r2.remove(r1)     // Catch: all -> 0x0071
            int r2 = r4.b     // Catch: all -> 0x0071
            int r3 = r4.a(r1, r0)     // Catch: all -> 0x0071
            int r2 = r2 - r3
            r4.b = r2     // Catch: all -> 0x0071
            int r2 = r4.f     // Catch: all -> 0x0071
            r3 = 1
            int r2 = r2 + r3
            r4.f = r2     // Catch: all -> 0x0071
            monitor-exit(r4)     // Catch: all -> 0x0071
            r2 = 0
            r4.entryRemoved(r3, r1, r0, r2)
            goto L_0x0000
        L_0x0050:
            monitor-exit(r4)     // Catch: all -> 0x0071
            return
        L_0x0052:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: all -> 0x0071
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: all -> 0x0071
            r0.<init>()     // Catch: all -> 0x0071
            java.lang.Class r1 = r4.getClass()     // Catch: all -> 0x0071
            java.lang.String r1 = r1.getName()     // Catch: all -> 0x0071
            r0.append(r1)     // Catch: all -> 0x0071
            java.lang.String r1 = ".sizeOf() is reporting inconsistent results!"
            r0.append(r1)     // Catch: all -> 0x0071
            java.lang.String r0 = r0.toString()     // Catch: all -> 0x0071
            r5.<init>(r0)     // Catch: all -> 0x0071
            throw r5     // Catch: all -> 0x0071
        L_0x0071:
            r5 = move-exception
            monitor-exit(r4)     // Catch: all -> 0x0071
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.LruCache.trimToSize(int):void");
    }

    @Nullable
    public final V remove(@NonNull K k) {
        V remove;
        if (k != null) {
            synchronized (this) {
                remove = this.a.remove(k);
                if (remove != null) {
                    this.b -= a(k, remove);
                }
            }
            if (remove != null) {
                entryRemoved(false, k, remove, null);
            }
            return remove;
        }
        throw new NullPointerException("key == null");
    }

    private int a(K k, V v) {
        int sizeOf = sizeOf(k, v);
        if (sizeOf >= 0) {
            return sizeOf;
        }
        throw new IllegalStateException("Negative size: " + k + "=" + v);
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        return this.b;
    }

    public final synchronized int maxSize() {
        return this.c;
    }

    public final synchronized int hitCount() {
        return this.g;
    }

    public final synchronized int missCount() {
        return this.h;
    }

    public final synchronized int createCount() {
        return this.e;
    }

    public final synchronized int putCount() {
        return this.d;
    }

    public final synchronized int evictionCount() {
        return this.f;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.a);
    }

    public final synchronized String toString() {
        int i;
        i = this.g + this.h;
        return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.c), Integer.valueOf(this.g), Integer.valueOf(this.h), Integer.valueOf(i != 0 ? (this.g * 100) / i : 0));
    }
}
