package com.blankj.utilcode.util;

import androidx.annotation.NonNull;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class SPStaticUtils {
    private static SPUtils a;

    public static void setDefaultSPUtils(SPUtils sPUtils) {
        a = sPUtils;
    }

    public static void put(@NonNull String str, String str2) {
        if (str != null) {
            put(str, str2, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, String str2, boolean z) {
        if (str != null) {
            put(str, str2, z, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getString(@NonNull String str) {
        if (str != null) {
            return getString(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getString(@NonNull String str, String str2) {
        if (str != null) {
            return getString(str, str2, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, int i) {
        if (str != null) {
            put(str, i, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, int i, boolean z) {
        if (str != null) {
            put(str, i, z, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static int getInt(@NonNull String str) {
        if (str != null) {
            return getInt(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static int getInt(@NonNull String str, int i) {
        if (str != null) {
            return getInt(str, i, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, long j) {
        if (str != null) {
            put(str, j, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, long j, boolean z) {
        if (str != null) {
            put(str, j, z, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static long getLong(@NonNull String str) {
        if (str != null) {
            return getLong(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static long getLong(@NonNull String str, long j) {
        if (str != null) {
            return getLong(str, j, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, float f) {
        if (str != null) {
            put(str, f, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, float f, boolean z) {
        if (str != null) {
            put(str, f, z, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static float getFloat(@NonNull String str) {
        if (str != null) {
            return getFloat(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static float getFloat(@NonNull String str, float f) {
        if (str != null) {
            return getFloat(str, f, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, boolean z) {
        if (str != null) {
            put(str, z, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, boolean z, boolean z2) {
        if (str != null) {
            put(str, z, z2, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean getBoolean(@NonNull String str) {
        if (str != null) {
            return getBoolean(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean getBoolean(@NonNull String str, boolean z) {
        if (str != null) {
            return getBoolean(str, z, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, Set<String> set) {
        if (str != null) {
            put(str, set, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, Set<String> set, boolean z) {
        if (str != null) {
            put(str, set, z, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Set<String> getStringSet(@NonNull String str) {
        if (str != null) {
            return getStringSet(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Set<String> getStringSet(@NonNull String str, Set<String> set) {
        if (str != null) {
            return getStringSet(str, set, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Map<String, ?> getAll() {
        return getAll(a());
    }

    public static boolean contains(@NonNull String str) {
        if (str != null) {
            return contains(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void remove(@NonNull String str) {
        if (str != null) {
            remove(str, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void remove(@NonNull String str, boolean z) {
        if (str != null) {
            remove(str, z, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void clear() {
        clear(a());
    }

    public static void clear(boolean z) {
        clear(z, a());
    }

    public static void put(@NonNull String str, String str2, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, str2);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, String str2, boolean z, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, str2, z);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getString(@NonNull String str, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getString(str);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getString(@NonNull String str, String str2, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getString(str, str2);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, int i, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, i);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, int i, boolean z, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, i, z);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static int getInt(@NonNull String str, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getInt(str);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static int getInt(@NonNull String str, int i, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getInt(str, i);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, long j, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, j);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, long j, boolean z, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, j, z);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static long getLong(@NonNull String str, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getLong(str);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static long getLong(@NonNull String str, long j, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getLong(str, j);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, float f, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, f);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, float f, boolean z, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, f, z);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static float getFloat(@NonNull String str, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getFloat(str);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static float getFloat(@NonNull String str, float f, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getFloat(str, f);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, boolean z, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, z);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, boolean z, boolean z2, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, z, z2);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static boolean getBoolean(@NonNull String str, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getBoolean(str);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static boolean getBoolean(@NonNull String str, boolean z, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getBoolean(str, z);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Set<String> set, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, set);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Set<String> set, boolean z, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.put(str, set, z);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Set<String> getStringSet(@NonNull String str, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getStringSet(str);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Set<String> getStringSet(@NonNull String str, Set<String> set, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.getStringSet(str, set);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Map<String, ?> getAll(@NonNull SPUtils sPUtils) {
        if (sPUtils != null) {
            return sPUtils.getAll();
        }
        throw new NullPointerException("Argument 'spUtils' of type SPUtils (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean contains(@NonNull String str, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            return sPUtils.contains(str);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void remove(@NonNull String str, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.remove(str);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void remove(@NonNull String str, boolean z, @NonNull SPUtils sPUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (sPUtils != null) {
            sPUtils.remove(str, z);
        } else {
            throw new NullPointerException("Argument 'spUtils' of type SPUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void clear(@NonNull SPUtils sPUtils) {
        if (sPUtils != null) {
            sPUtils.clear();
            return;
        }
        throw new NullPointerException("Argument 'spUtils' of type SPUtils (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void clear(boolean z, @NonNull SPUtils sPUtils) {
        if (sPUtils != null) {
            sPUtils.clear(z);
            return;
        }
        throw new NullPointerException("Argument 'spUtils' of type SPUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static SPUtils a() {
        SPUtils sPUtils = a;
        return sPUtils != null ? sPUtils : SPUtils.getInstance();
    }
}
