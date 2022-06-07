package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class LruCache<T, Y> {
    private final Map<T, a<Y>> a = new LinkedHashMap(100, 0.75f, true);
    private final long b;
    private long c;
    private long d;

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSize(@Nullable Y y) {
        return 1;
    }

    protected void onItemEvicted(@NonNull T t, @Nullable Y y) {
    }

    public LruCache(long j) {
        this.b = j;
        this.c = j;
    }

    public synchronized void setSizeMultiplier(float f) {
        if (f >= 0.0f) {
            this.c = Math.round(((float) this.b) * f);
            a();
        } else {
            throw new IllegalArgumentException("Multiplier must be >= 0");
        }
    }

    protected synchronized int getCount() {
        return this.a.size();
    }

    public synchronized long getMaxSize() {
        return this.c;
    }

    public synchronized long getCurrentSize() {
        return this.d;
    }

    public synchronized boolean contains(@NonNull T t) {
        return this.a.containsKey(t);
    }

    @Nullable
    public synchronized Y get(@NonNull T t) {
        a<Y> aVar;
        aVar = this.a.get(t);
        return aVar != null ? aVar.a : null;
    }

    @Nullable
    public synchronized Y put(@NonNull T t, @Nullable Y y) {
        int size = getSize(y);
        long j = size;
        Y y2 = null;
        if (j >= this.c) {
            onItemEvicted(t, y);
            return null;
        }
        if (y != null) {
            this.d += j;
        }
        a<Y> put = this.a.put(t, y == null ? null : new a<>(y, size));
        if (put != null) {
            this.d -= put.b;
            if (!put.a.equals(y)) {
                onItemEvicted(t, put.a);
            }
        }
        a();
        if (put != null) {
            y2 = put.a;
        }
        return y2;
    }

    @Nullable
    public synchronized Y remove(@NonNull T t) {
        a<Y> remove = this.a.remove(t);
        if (remove == null) {
            return null;
        }
        this.d -= remove.b;
        return remove.a;
    }

    public void clearMemory() {
        trimToSize(0L);
    }

    protected synchronized void trimToSize(long j) {
        while (this.d > j) {
            Iterator<Map.Entry<T, a<Y>>> it = this.a.entrySet().iterator();
            Map.Entry<T, a<Y>> next = it.next();
            a<Y> value = next.getValue();
            this.d -= value.b;
            T key = next.getKey();
            it.remove();
            onItemEvicted(key, value.a);
        }
    }

    private void a() {
        trimToSize(this.c);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class a<Y> {
        final Y a;
        final int b;

        a(Y y, int i) {
            this.a = y;
            this.b = i;
        }
    }
}
