package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class CacheDoubleStaticUtils {
    private static CacheDoubleUtils a;

    public static void setDefaultCacheDoubleUtils(CacheDoubleUtils cacheDoubleUtils) {
        a = cacheDoubleUtils;
    }

    public static void put(@NonNull String str, byte[] bArr) {
        if (str != null) {
            put(str, bArr, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, byte[] bArr, int i) {
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

    public static byte[] getBytes(@NonNull String str, byte[] bArr) {
        if (str != null) {
            return getBytes(str, bArr, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, String str2) {
        if (str != null) {
            put(str, str2, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, String str2, int i) {
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

    public static String getString(@NonNull String str, String str2) {
        if (str != null) {
            return getString(str, str2, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, JSONObject jSONObject) {
        if (str != null) {
            put(str, jSONObject, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, JSONObject jSONObject, int i) {
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

    public static JSONObject getJSONObject(@NonNull String str, JSONObject jSONObject) {
        if (str != null) {
            return getJSONObject(str, jSONObject, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, JSONArray jSONArray) {
        if (str != null) {
            put(str, jSONArray, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, JSONArray jSONArray, int i) {
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

    public static JSONArray getJSONArray(@NonNull String str, JSONArray jSONArray) {
        if (str != null) {
            return getJSONArray(str, jSONArray, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, Bitmap bitmap) {
        if (str != null) {
            put(str, bitmap, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, Bitmap bitmap, int i) {
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

    public static Bitmap getBitmap(@NonNull String str, Bitmap bitmap) {
        if (str != null) {
            return getBitmap(str, bitmap, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, Drawable drawable) {
        if (str != null) {
            put(str, drawable, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, Drawable drawable, int i) {
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

    public static Drawable getDrawable(@NonNull String str, Drawable drawable) {
        if (str != null) {
            return getDrawable(str, drawable, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, Parcelable parcelable) {
        if (str != null) {
            put(str, parcelable, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, Parcelable parcelable, int i) {
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

    public static <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, T t) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (creator != null) {
            return (T) getParcelable(str, creator, t, a());
        } else {
            throw new NullPointerException("Argument 'creator' of type Parcelable.Creator<T> (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Serializable serializable) {
        if (str != null) {
            put(str, serializable, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void put(@NonNull String str, Serializable serializable, int i) {
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

    public static Object getSerializable(@NonNull String str, Object obj) {
        if (str != null) {
            return getSerializable(str, obj, a());
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static long getCacheDiskSize() {
        return getCacheDiskSize(a());
    }

    public static int getCacheDiskCount() {
        return getCacheDiskCount(a());
    }

    public static int getCacheMemoryCount() {
        return getCacheMemoryCount(a());
    }

    public static void remove(@NonNull String str) {
        if (str != null) {
            remove(str, a());
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void clear() {
        clear(a());
    }

    public static void put(@NonNull String str, byte[] bArr, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, bArr);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, byte[] bArr, int i, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, bArr, i);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static byte[] getBytes(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getBytes(str);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static byte[] getBytes(@NonNull String str, byte[] bArr, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getBytes(str, bArr);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, String str2, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, str2);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, String str2, int i, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, str2, i);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getString(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getString(str);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getString(@NonNull String str, String str2, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getString(str, str2);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, JSONObject jSONObject, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, jSONObject);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, JSONObject jSONObject, int i, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, jSONObject, i);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static JSONObject getJSONObject(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getJSONObject(str);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static JSONObject getJSONObject(@NonNull String str, JSONObject jSONObject, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getJSONObject(str, jSONObject);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, JSONArray jSONArray, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, jSONArray);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, JSONArray jSONArray, int i, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, jSONArray, i);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static JSONArray getJSONArray(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getJSONArray(str);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static JSONArray getJSONArray(@NonNull String str, JSONArray jSONArray, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getJSONArray(str, jSONArray);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Bitmap bitmap, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, bitmap);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Bitmap bitmap, int i, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, bitmap, i);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Bitmap getBitmap(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getBitmap(str);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Bitmap getBitmap(@NonNull String str, Bitmap bitmap, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getBitmap(str, bitmap);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Drawable drawable, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, drawable);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Drawable drawable, int i, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, drawable, i);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Drawable getDrawable(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getDrawable(str);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Drawable getDrawable(@NonNull String str, Drawable drawable, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getDrawable(str, drawable);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Parcelable parcelable, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, parcelable);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Parcelable parcelable, int i, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, parcelable, i);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (creator == null) {
            throw new NullPointerException("Argument 'creator' of type Parcelable.Creator<T> (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return (T) cacheDoubleUtils.getParcelable(str, creator);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, T t, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (creator == null) {
            throw new NullPointerException("Argument 'creator' of type Parcelable.Creator<T> (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return (T) cacheDoubleUtils.getParcelable(str, creator, t);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Serializable serializable, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, serializable);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void put(@NonNull String str, Serializable serializable, int i, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.put(str, serializable, i);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Object getSerializable(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getSerializable(str);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Object getSerializable(@NonNull String str, Object obj, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getSerializable(str, obj);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static long getCacheDiskSize(@NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getCacheDiskSize();
        }
        throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static int getCacheDiskCount(@NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getCacheDiskCount();
        }
        throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static int getCacheMemoryCount(@NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (cacheDoubleUtils != null) {
            return cacheDoubleUtils.getCacheMemoryCount();
        }
        throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void remove(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDoubleUtils != null) {
            cacheDoubleUtils.remove(str);
        } else {
            throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void clear(@NonNull CacheDoubleUtils cacheDoubleUtils) {
        if (cacheDoubleUtils != null) {
            cacheDoubleUtils.clear();
            return;
        }
        throw new NullPointerException("Argument 'cacheDoubleUtils' of type CacheDoubleUtils (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static CacheDoubleUtils a() {
        CacheDoubleUtils cacheDoubleUtils = a;
        return cacheDoubleUtils != null ? cacheDoubleUtils : CacheDoubleUtils.getInstance();
    }
}
