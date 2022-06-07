package com.blankj.utilcode.util;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import com.blankj.utilcode.constant.CacheConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class CacheMemoryUtils implements CacheConstants {
    private static final Map<String, CacheMemoryUtils> a = new HashMap();
    private final String b;
    private final LruCache<String, a> c;

    public static CacheMemoryUtils getInstance() {
        return getInstance(256);
    }

    public static CacheMemoryUtils getInstance(int i) {
        return getInstance(String.valueOf(i), i);
    }

    public static CacheMemoryUtils getInstance(String str, int i) {
        CacheMemoryUtils cacheMemoryUtils = a.get(str);
        if (cacheMemoryUtils == null) {
            synchronized (CacheMemoryUtils.class) {
                cacheMemoryUtils = a.get(str);
                if (cacheMemoryUtils == null) {
                    cacheMemoryUtils = new CacheMemoryUtils(str, new LruCache(i));
                    a.put(str, cacheMemoryUtils);
                }
            }
        }
        return cacheMemoryUtils;
    }

    private CacheMemoryUtils(String str, LruCache<String, a> lruCache) {
        this.b = str;
        this.c = lruCache;
    }

    public String toString() {
        return this.b + "@" + Integer.toHexString(hashCode());
    }

    public void put(@NonNull String str, Object obj) {
        if (str != null) {
            put(str, obj, -1);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, Object obj, int i) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (obj != null) {
            this.c.put(str, new a(i < 0 ? -1L : System.currentTimeMillis() + (i * 1000), obj));
        }
    }

    public <T> T get(@NonNull String str) {
        if (str != null) {
            return (T) get(str, null);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public <T> T get(@NonNull String str, T t) {
        if (str != null) {
            a aVar = this.c.get(str);
            if (aVar == null) {
                return t;
            }
            if (aVar.a == -1 || aVar.a >= System.currentTimeMillis()) {
                return (T) aVar.b;
            }
            this.c.remove(str);
            return t;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public int getCacheCount() {
        return this.c.size();
    }

    public Object remove(@NonNull String str) {
        if (str != null) {
            a remove = this.c.remove(str);
            if (remove == null) {
                return null;
            }
            return remove.b;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void clear() {
        this.c.evictAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        long a;
        Object b;

        a(long j, Object obj) {
            this.a = j;
            this.b = obj;
        }
    }
}
