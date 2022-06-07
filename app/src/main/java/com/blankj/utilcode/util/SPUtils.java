package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressLint({"ApplySharedPref"})
/* loaded from: classes.dex */
public final class SPUtils {
    private static final Map<String, SPUtils> a = new HashMap();
    private SharedPreferences b;

    public static SPUtils getInstance() {
        return getInstance("", 0);
    }

    public static SPUtils getInstance(int i) {
        return getInstance("", i);
    }

    public static SPUtils getInstance(String str) {
        return getInstance(str, 0);
    }

    public static SPUtils getInstance(String str, int i) {
        if (a(str)) {
            str = "spUtils";
        }
        SPUtils sPUtils = a.get(str);
        if (sPUtils == null) {
            synchronized (SPUtils.class) {
                sPUtils = a.get(str);
                if (sPUtils == null) {
                    sPUtils = new SPUtils(str, i);
                    a.put(str, sPUtils);
                }
            }
        }
        return sPUtils;
    }

    private SPUtils(String str, int i) {
        this.b = Utils.getApp().getSharedPreferences(str, i);
    }

    public void put(@NonNull String str, String str2) {
        if (str != null) {
            put(str, str2, false);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, String str2, boolean z) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (z) {
            this.b.edit().putString(str, str2).commit();
        } else {
            this.b.edit().putString(str, str2).apply();
        }
    }

    public String getString(@NonNull String str) {
        if (str != null) {
            return getString(str, "");
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public String getString(@NonNull String str, String str2) {
        if (str != null) {
            return this.b.getString(str, str2);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, int i) {
        if (str != null) {
            put(str, i, false);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, int i, boolean z) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (z) {
            this.b.edit().putInt(str, i).commit();
        } else {
            this.b.edit().putInt(str, i).apply();
        }
    }

    public int getInt(@NonNull String str) {
        if (str != null) {
            return getInt(str, -1);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public int getInt(@NonNull String str, int i) {
        if (str != null) {
            return this.b.getInt(str, i);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, long j) {
        if (str != null) {
            put(str, j, false);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, long j, boolean z) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (z) {
            this.b.edit().putLong(str, j).commit();
        } else {
            this.b.edit().putLong(str, j).apply();
        }
    }

    public long getLong(@NonNull String str) {
        if (str != null) {
            return getLong(str, -1L);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public long getLong(@NonNull String str, long j) {
        if (str != null) {
            return this.b.getLong(str, j);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, float f) {
        if (str != null) {
            put(str, f, false);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, float f, boolean z) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (z) {
            this.b.edit().putFloat(str, f).commit();
        } else {
            this.b.edit().putFloat(str, f).apply();
        }
    }

    public float getFloat(@NonNull String str) {
        if (str != null) {
            return getFloat(str, -1.0f);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public float getFloat(@NonNull String str, float f) {
        if (str != null) {
            return this.b.getFloat(str, f);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, boolean z) {
        if (str != null) {
            put(str, z, false);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, boolean z, boolean z2) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (z2) {
            this.b.edit().putBoolean(str, z).commit();
        } else {
            this.b.edit().putBoolean(str, z).apply();
        }
    }

    public boolean getBoolean(@NonNull String str) {
        if (str != null) {
            return getBoolean(str, false);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public boolean getBoolean(@NonNull String str, boolean z) {
        if (str != null) {
            return this.b.getBoolean(str, z);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, Set<String> set) {
        if (str != null) {
            put(str, set, false);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, Set<String> set, boolean z) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (z) {
            this.b.edit().putStringSet(str, set).commit();
        } else {
            this.b.edit().putStringSet(str, set).apply();
        }
    }

    public Set<String> getStringSet(@NonNull String str) {
        if (str != null) {
            return getStringSet(str, Collections.emptySet());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public Set<String> getStringSet(@NonNull String str, Set<String> set) {
        if (str != null) {
            return this.b.getStringSet(str, set);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public Map<String, ?> getAll() {
        return this.b.getAll();
    }

    public boolean contains(@NonNull String str) {
        if (str != null) {
            return this.b.contains(str);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void remove(@NonNull String str) {
        if (str != null) {
            remove(str, false);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void remove(@NonNull String str, boolean z) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (z) {
            this.b.edit().remove(str).commit();
        } else {
            this.b.edit().remove(str).apply();
        }
    }

    public void clear() {
        clear(false);
    }

    public void clear(boolean z) {
        if (z) {
            this.b.edit().clear().commit();
        } else {
            this.b.edit().clear().apply();
        }
    }

    private static boolean a(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
