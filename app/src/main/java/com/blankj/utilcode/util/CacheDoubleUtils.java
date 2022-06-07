package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.blankj.utilcode.constant.CacheConstants;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class CacheDoubleUtils implements CacheConstants {
    private static final Map<String, CacheDoubleUtils> a = new HashMap();
    private final CacheMemoryUtils b;
    private final CacheDiskUtils c;

    public static CacheDoubleUtils getInstance() {
        return getInstance(CacheMemoryUtils.getInstance(), CacheDiskUtils.getInstance());
    }

    public static CacheDoubleUtils getInstance(@NonNull CacheMemoryUtils cacheMemoryUtils, @NonNull CacheDiskUtils cacheDiskUtils) {
        if (cacheMemoryUtils == null) {
            throw new NullPointerException("Argument 'cacheMemoryUtils' of type CacheMemoryUtils (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cacheDiskUtils != null) {
            String str = cacheDiskUtils.toString() + "_" + cacheMemoryUtils.toString();
            CacheDoubleUtils cacheDoubleUtils = a.get(str);
            if (cacheDoubleUtils == null) {
                synchronized (CacheDoubleUtils.class) {
                    cacheDoubleUtils = a.get(str);
                    if (cacheDoubleUtils == null) {
                        cacheDoubleUtils = new CacheDoubleUtils(cacheMemoryUtils, cacheDiskUtils);
                        a.put(str, cacheDoubleUtils);
                    }
                }
            }
            return cacheDoubleUtils;
        } else {
            throw new NullPointerException("Argument 'cacheDiskUtils' of type CacheDiskUtils (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    private CacheDoubleUtils(CacheMemoryUtils cacheMemoryUtils, CacheDiskUtils cacheDiskUtils) {
        this.b = cacheMemoryUtils;
        this.c = cacheDiskUtils;
    }

    public void put(@NonNull String str, byte[] bArr) {
        if (str != null) {
            put(str, bArr, -1);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, byte[] bArr, int i) {
        if (str != null) {
            this.b.put(str, bArr, i);
            this.c.put(str, bArr, i);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public byte[] getBytes(@NonNull String str) {
        if (str != null) {
            return getBytes(str, null);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public byte[] getBytes(@NonNull String str, byte[] bArr) {
        if (str != null) {
            byte[] bArr2 = (byte[]) this.b.get(str);
            if (bArr2 != null) {
                return bArr2;
            }
            byte[] bytes = this.c.getBytes(str);
            if (bytes == null) {
                return bArr;
            }
            this.b.put(str, bytes);
            return bytes;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, String str2) {
        if (str != null) {
            put(str, str2, -1);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, String str2, int i) {
        if (str != null) {
            this.b.put(str, str2, i);
            this.c.put(str, str2, i);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public String getString(@NonNull String str) {
        if (str != null) {
            return getString(str, null);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public String getString(@NonNull String str, String str2) {
        if (str != null) {
            String str3 = (String) this.b.get(str);
            if (str3 != null) {
                return str3;
            }
            String string = this.c.getString(str);
            if (string == null) {
                return str2;
            }
            this.b.put(str, string);
            return string;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, JSONObject jSONObject) {
        if (str != null) {
            put(str, jSONObject, -1);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, JSONObject jSONObject, int i) {
        if (str != null) {
            this.b.put(str, jSONObject, i);
            this.c.put(str, jSONObject, i);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public JSONObject getJSONObject(@NonNull String str) {
        if (str != null) {
            return getJSONObject(str, null);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public JSONObject getJSONObject(@NonNull String str, JSONObject jSONObject) {
        if (str != null) {
            JSONObject jSONObject2 = (JSONObject) this.b.get(str);
            if (jSONObject2 != null) {
                return jSONObject2;
            }
            JSONObject jSONObject3 = this.c.getJSONObject(str);
            if (jSONObject3 == null) {
                return jSONObject;
            }
            this.b.put(str, jSONObject3);
            return jSONObject3;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, JSONArray jSONArray) {
        if (str != null) {
            put(str, jSONArray, -1);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, JSONArray jSONArray, int i) {
        if (str != null) {
            this.b.put(str, jSONArray, i);
            this.c.put(str, jSONArray, i);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public JSONArray getJSONArray(@NonNull String str) {
        if (str != null) {
            return getJSONArray(str, null);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public JSONArray getJSONArray(@NonNull String str, JSONArray jSONArray) {
        if (str != null) {
            JSONArray jSONArray2 = (JSONArray) this.b.get(str);
            if (jSONArray2 != null) {
                return jSONArray2;
            }
            JSONArray jSONArray3 = this.c.getJSONArray(str);
            if (jSONArray3 == null) {
                return jSONArray;
            }
            this.b.put(str, jSONArray3);
            return jSONArray3;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, Bitmap bitmap) {
        if (str != null) {
            put(str, bitmap, -1);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, Bitmap bitmap, int i) {
        if (str != null) {
            this.b.put(str, bitmap, i);
            this.c.put(str, bitmap, i);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public Bitmap getBitmap(@NonNull String str) {
        if (str != null) {
            return getBitmap(str, null);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public Bitmap getBitmap(@NonNull String str, Bitmap bitmap) {
        if (str != null) {
            Bitmap bitmap2 = (Bitmap) this.b.get(str);
            if (bitmap2 != null) {
                return bitmap2;
            }
            Bitmap bitmap3 = this.c.getBitmap(str);
            if (bitmap3 == null) {
                return bitmap;
            }
            this.b.put(str, bitmap3);
            return bitmap3;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, Drawable drawable) {
        if (str != null) {
            put(str, drawable, -1);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, Drawable drawable, int i) {
        if (str != null) {
            this.b.put(str, drawable, i);
            this.c.put(str, drawable, i);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public Drawable getDrawable(@NonNull String str) {
        if (str != null) {
            return getDrawable(str, null);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public Drawable getDrawable(@NonNull String str, Drawable drawable) {
        if (str != null) {
            Drawable drawable2 = (Drawable) this.b.get(str);
            if (drawable2 != null) {
                return drawable2;
            }
            Drawable drawable3 = this.c.getDrawable(str);
            if (drawable3 == null) {
                return drawable;
            }
            this.b.put(str, drawable3);
            return drawable3;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, Parcelable parcelable) {
        if (str != null) {
            put(str, parcelable, -1);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, Parcelable parcelable, int i) {
        if (str != null) {
            this.b.put(str, parcelable, i);
            this.c.put(str, parcelable, i);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (creator != null) {
            return (T) getParcelable(str, creator, null);
        } else {
            throw new NullPointerException("Argument 'creator' of type Parcelable.Creator<T> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, T t) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (creator != null) {
            T t2 = (T) this.b.get(str);
            if (t2 != null) {
                return t2;
            }
            T t3 = (T) this.c.getParcelable(str, creator);
            if (t3 == null) {
                return t;
            }
            this.b.put(str, t3);
            return t3;
        } else {
            throw new NullPointerException("Argument 'creator' of type Parcelable.Creator<T> (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public void put(@NonNull String str, Serializable serializable) {
        if (str != null) {
            put(str, serializable, -1);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void put(@NonNull String str, Serializable serializable, int i) {
        if (str != null) {
            this.b.put(str, serializable, i);
            this.c.put(str, serializable, i);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public Object getSerializable(@NonNull String str) {
        if (str != null) {
            return getSerializable(str, null);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public Object getSerializable(@NonNull String str, Object obj) {
        if (str != null) {
            Object obj2 = this.b.get(str);
            if (obj2 != null) {
                return obj2;
            }
            Object serializable = this.c.getSerializable(str);
            if (serializable == null) {
                return obj;
            }
            this.b.put(str, serializable);
            return serializable;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public long getCacheDiskSize() {
        return this.c.getCacheSize();
    }

    public int getCacheDiskCount() {
        return this.c.getCacheCount();
    }

    public int getCacheMemoryCount() {
        return this.b.getCacheCount();
    }

    public void remove(@NonNull String str) {
        if (str != null) {
            this.b.remove(str);
            this.c.remove(str);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void clear() {
        this.b.clear();
        this.c.clear();
    }
}
