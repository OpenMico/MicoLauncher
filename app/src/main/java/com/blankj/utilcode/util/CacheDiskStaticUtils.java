package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class CacheDiskStaticUtils {
    private static CacheDiskUtils a;

    public static void setDefaultCacheDiskUtils(@Nullable CacheDiskUtils cacheDiskUtils) {
        a = cacheDiskUtils;
    }

    public static void put(@NonNull String str, @Nullable byte[] bArr) {
        if (str != null) {
            put(str, bArr, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable byte[] bArr, int i) {
        if (str != null) {
            put(str, bArr, i, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static byte[] getBytes(@NonNull String str) {
        if (str != null) {
            return getBytes(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static byte[] getBytes(@NonNull String str, @Nullable byte[] bArr) {
        if (str != null) {
            return getBytes(str, bArr, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable String str2) {
        if (str != null) {
            put(str, str2, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable String str2, int i) {
        if (str != null) {
            put(str, str2, i, a());
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

    public static String getString(@NonNull String str, @Nullable String str2) {
        if (str != null) {
            return getString(str, str2, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable JSONObject jSONObject) {
        if (str != null) {
            put(str, jSONObject, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable JSONObject jSONObject, int i) {
        if (str != null) {
            put(str, jSONObject, i, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static JSONObject getJSONObject(@NonNull String str) {
        if (str != null) {
            return getJSONObject(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static JSONObject getJSONObject(@NonNull String str, @Nullable JSONObject jSONObject) {
        if (str != null) {
            return getJSONObject(str, jSONObject, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable JSONArray jSONArray) {
        if (str != null) {
            put(str, jSONArray, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable JSONArray jSONArray, int i) {
        if (str != null) {
            put(str, jSONArray, i, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static JSONArray getJSONArray(@NonNull String str) {
        if (str != null) {
            return getJSONArray(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static JSONArray getJSONArray(@NonNull String str, @Nullable JSONArray jSONArray) {
        if (str != null) {
            return getJSONArray(str, jSONArray, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable Bitmap bitmap) {
        if (str != null) {
            put(str, bitmap, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable Bitmap bitmap, int i) {
        if (str != null) {
            put(str, bitmap, i, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Bitmap getBitmap(@NonNull String str) {
        if (str != null) {
            return getBitmap(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Bitmap getBitmap(@NonNull String str, @Nullable Bitmap bitmap) {
        if (str != null) {
            return getBitmap(str, bitmap, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable Drawable drawable) {
        if (str != null) {
            put(str, drawable, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable Drawable drawable, int i) {
        if (str != null) {
            put(str, drawable, i, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Drawable getDrawable(@NonNull String str) {
        if (str != null) {
            return getDrawable(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Drawable getDrawable(@NonNull String str, @Nullable Drawable drawable) {
        if (str != null) {
            return getDrawable(str, drawable, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable Parcelable parcelable) {
        if (str != null) {
            put(str, parcelable, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable Parcelable parcelable, int i) {
        if (str != null) {
            put(str, parcelable, i, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (creator != null) {
            return (T) getParcelable(str, (Parcelable.Creator<Object>) creator, a());
        } else {
            throw new NullPointerException("Argument 'creator' of type Parcelable.Creator<T> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, @Nullable T t) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (creator != null) {
            return (T) getParcelable(str, creator, t, a());
        } else {
            throw new NullPointerException("Argument 'creator' of type Parcelable.Creator<T> (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable Serializable serializable) {
        if (str != null) {
            put(str, serializable, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, @Nullable Serializable serializable, int i) {
        if (str != null) {
            put(str, serializable, i, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Object getSerializable(@NonNull String str) {
        if (str != null) {
            return getSerializable(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Object getSerializable(@NonNull String str, @Nullable Object obj) {
        if (str != null) {
            return getSerializable(str, obj, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static long getCacheSize() {
        return getCacheSize(a());
    }

    public static int getCacheCount() {
        return getCacheCount(a());
    }

    public static boolean remove(@NonNull String str) {
        if (str != null) {
            return remove(str, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean clear() {
        return clear(a());
    }

    public static void put(@NonNull String str, @Nullable byte[] bArr, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, bArr);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable byte[] bArr, int i, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, bArr, i);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static byte[] getBytes(@NonNull String str, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getBytes(str);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static byte[] getBytes(@NonNull String str, @Nullable byte[] bArr, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getBytes(str, bArr);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable String str2, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, str2);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable String str2, int i, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, str2, i);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getString(@NonNull String str, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getString(str);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getString(@NonNull String str, @Nullable String str2, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getString(str, str2);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable JSONObject jSONObject, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, jSONObject);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable JSONObject jSONObject, int i, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, jSONObject, i);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static JSONObject getJSONObject(@NonNull String str, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getJSONObject(str);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static JSONObject getJSONObject(@NonNull String str, @Nullable JSONObject jSONObject, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getJSONObject(str, jSONObject);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable JSONArray jSONArray, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, jSONArray);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable JSONArray jSONArray, int i, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, jSONArray, i);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static JSONArray getJSONArray(@NonNull String str, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getJSONArray(str);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static JSONArray getJSONArray(@NonNull String str, @Nullable JSONArray jSONArray, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getJSONArray(str, jSONArray);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable Bitmap bitmap, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, bitmap);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable Bitmap bitmap, int i, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, bitmap, i);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Bitmap getBitmap(@NonNull String str, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getBitmap(str);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Bitmap getBitmap(@NonNull String str, @Nullable Bitmap bitmap, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getBitmap(str, bitmap);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable Drawable drawable, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, drawable);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable Drawable drawable, int i, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, drawable, i);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Drawable getDrawable(@NonNull String str, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getDrawable(str);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Drawable getDrawable(@NonNull String str, @Nullable Drawable drawable, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getDrawable(str, drawable);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable Parcelable parcelable, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, parcelable);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable Parcelable parcelable, int i, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, parcelable, i);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (creator == null) {
            throw new NullPointerException("Argument 'creator' of type Parcelable.Creator<T> (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return (T) cacheDiskUtils.getParcelable(str, creator);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, @Nullable T t, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (creator == null) {
            throw new NullPointerException("Argument 'creator' of type Parcelable.Creator<T> (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return (T) cacheDiskUtils.getParcelable(str, creator, t);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable Serializable serializable, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, serializable);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, @Nullable Serializable serializable, int i, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            cacheDiskUtils.put(str, serializable, i);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Object getSerializable(@NonNull String str, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getSerializable(str);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Object getSerializable(@NonNull String str, @Nullable Object obj, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.getSerializable(str, obj);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static long getCacheSize(@NonNull CacheDiskUtils cacheDiskUtils) {
        if (cacheDiskUtils != null) {
            return cacheDiskUtils.getCacheSize();
        }
        throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static int getCacheCount(@NonNull CacheDiskUtils cacheDiskUtils) {
        if (cacheDiskUtils != null) {
            return cacheDiskUtils.getCacheCount();
        }
        throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean remove(@NonNull String str, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            return cacheDiskUtils.remove(str);
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static boolean clear(@NonNull CacheDiskUtils cacheDiskUtils) {
        if (cacheDiskUtils != null) {
            return cacheDiskUtils.clear();
        }
        throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @NonNull
    private static CacheDiskUtils a() {
        CacheDiskUtils cacheDiskUtils = a;
        if (cacheDiskUtils == null) {
            cacheDiskUtils = CacheDiskUtils.getInstance();
        }
        if (cacheDiskUtils != null) {
            return cacheDiskUtils;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.CacheDiskStaticUtils.getDefaultCacheDiskUtils() marked by @androidx.annotation.NonNull");
    }
}
