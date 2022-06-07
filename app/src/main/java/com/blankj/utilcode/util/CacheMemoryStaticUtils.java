package com.blankj.utilcode.util;

import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public final class CacheMemoryStaticUtils {
    private static CacheMemoryUtils a;

    public static void setDefaultCacheMemoryUtils(CacheMemoryUtils cacheMemoryUtils) {
        a = cacheMemoryUtils;
    }

    public static void put(@NonNull String str, Object obj) {
        if (str != null) {
            put(str, obj, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, Object obj, int i) {
        if (str != null) {
            put(str, obj, i, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static <T> T get(@NonNull String str) {
        if (str != null) {
            return (T) get(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static <T> T get(@NonNull String str, T t) {
        if (str != null) {
            return (T) get(str, t, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static int getCacheCount() {
        return getCacheCount(a());
    }

    public static Object remove(@NonNull String str) {
        if (str != null) {
            return remove(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void clear() {
        clear(a());
    }

    public static void put(@NonNull String str, Object obj, @NonNull CacheMemoryUtils cacheMemoryUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheMemoryUtils != null) {
            cacheMemoryUtils.put(str, obj);
        } else {
            throw new NullPointerException("Argument 'cacheMemoryUtils' of type CacheMemoryUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Object obj, int i, @NonNull CacheMemoryUtils cacheMemoryUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheMemoryUtils != null) {
            cacheMemoryUtils.put(str, obj, i);
        } else {
            throw new NullPointerException("Argument 'cacheMemoryUtils' of type CacheMemoryUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T get(@NonNull String str, @NonNull CacheMemoryUtils cacheMemoryUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheMemoryUtils != null) {
            return (T) cacheMemoryUtils.get(str);
        } else {
            throw new NullPointerException("Argument 'cacheMemoryUtils' of type CacheMemoryUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T get(@NonNull String str, T t, @NonNull CacheMemoryUtils cacheMemoryUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheMemoryUtils != null) {
            return (T) cacheMemoryUtils.get(str, t);
        } else {
            throw new NullPointerException("Argument 'cacheMemoryUtils' of type CacheMemoryUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static int getCacheCount(@NonNull CacheMemoryUtils cacheMemoryUtils) {
        if (cacheMemoryUtils != null) {
            return cacheMemoryUtils.getCacheCount();
        }
        throw new NullPointerException("Argument 'cacheMemoryUtils' of type CacheMemoryUtils (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Object remove(@NonNull String str, @NonNull CacheMemoryUtils cacheMemoryUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheMemoryUtils != null) {
            return cacheMemoryUtils.remove(str);
        } else {
            throw new NullPointerException("Argument 'cacheMemoryUtils' of type CacheMemoryUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void clear(@NonNull CacheMemoryUtils cacheMemoryUtils) {
        if (cacheMemoryUtils != null) {
            cacheMemoryUtils.clear();
            return;
        }
        throw new NullPointerException("Argument 'cacheMemoryUtils' of type CacheMemoryUtils (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static CacheMemoryUtils a() {
        CacheMemoryUtils cacheMemoryUtils = a;
        return cacheMemoryUtils != null ? cacheMemoryUtils : CacheMemoryUtils.getInstance();
    }
}
