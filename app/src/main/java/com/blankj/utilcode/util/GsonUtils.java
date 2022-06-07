package com.blankj.utilcode.util;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public final class GsonUtils {
    private static final Map<String, Gson> a = new ConcurrentHashMap();

    private GsonUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setGsonDelegate(Gson gson) {
        if (gson != null) {
            a.put("delegateGson", gson);
        }
    }

    public static void setGson(String str, Gson gson) {
        if (!TextUtils.isEmpty(str) && gson != null) {
            a.put(str, gson);
        }
    }

    public static Gson getGson(String str) {
        return a.get(str);
    }

    public static Gson getGson() {
        Gson gson = a.get("delegateGson");
        if (gson != null) {
            return gson;
        }
        Gson gson2 = a.get("defaultGson");
        if (gson2 != null) {
            return gson2;
        }
        Gson b = b();
        a.put("defaultGson", b);
        return b;
    }

    public static String toJson(Object obj) {
        return toJson(getGson(), obj);
    }

    public static String toJson(Object obj, @NonNull Type type) {
        if (type != null) {
            return toJson(getGson(), obj, type);
        }
        throw new NullPointerException("Argument 'typeOfSrc' of type Type (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String toJson(@NonNull Gson gson, Object obj) {
        if (gson != null) {
            return gson.toJson(obj);
        }
        throw new NullPointerException("Argument 'gson' of type Gson (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String toJson(@NonNull Gson gson, Object obj, @NonNull Type type) {
        if (gson == null) {
            throw new NullPointerException("Argument 'gson' of type Gson (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (type != null) {
            return gson.toJson(obj, type);
        } else {
            throw new NullPointerException("Argument 'typeOfSrc' of type Type (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T fromJson(String str, @NonNull Class<T> cls) {
        if (cls != null) {
            return (T) fromJson(getGson(), str, (Class<Object>) cls);
        }
        throw new NullPointerException("Argument 'type' of type Class<T> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static <T> T fromJson(String str, @NonNull Type type) {
        if (type != null) {
            return (T) fromJson(getGson(), str, type);
        }
        throw new NullPointerException("Argument 'type' of type Type (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static <T> T fromJson(@NonNull Reader reader, @NonNull Class<T> cls) {
        if (reader == null) {
            throw new NullPointerException("Argument 'reader' of type Reader (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            return (T) fromJson(getGson(), reader, (Class<Object>) cls);
        } else {
            throw new NullPointerException("Argument 'type' of type Class<T> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T fromJson(@NonNull Reader reader, @NonNull Type type) {
        if (reader == null) {
            throw new NullPointerException("Argument 'reader' of type Reader (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (type != null) {
            return (T) fromJson(getGson(), reader, type);
        } else {
            throw new NullPointerException("Argument 'type' of type Type (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T fromJson(@NonNull Gson gson, String str, @NonNull Class<T> cls) {
        if (gson == null) {
            throw new NullPointerException("Argument 'gson' of type Gson (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            return (T) gson.fromJson(str, (Class<Object>) cls);
        } else {
            throw new NullPointerException("Argument 'type' of type Class<T> (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T fromJson(@NonNull Gson gson, String str, @NonNull Type type) {
        if (gson == null) {
            throw new NullPointerException("Argument 'gson' of type Gson (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (type != null) {
            return (T) gson.fromJson(str, type);
        } else {
            throw new NullPointerException("Argument 'type' of type Type (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T fromJson(@NonNull Gson gson, Reader reader, @NonNull Class<T> cls) {
        if (gson == null) {
            throw new NullPointerException("Argument 'gson' of type Gson (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            return (T) gson.fromJson(reader, (Class<Object>) cls);
        } else {
            throw new NullPointerException("Argument 'type' of type Class<T> (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static <T> T fromJson(@NonNull Gson gson, Reader reader, @NonNull Type type) {
        if (gson == null) {
            throw new NullPointerException("Argument 'gson' of type Gson (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (type != null) {
            return (T) gson.fromJson(reader, type);
        } else {
            throw new NullPointerException("Argument 'type' of type Type (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Type getListType(@NonNull Type type) {
        if (type != null) {
            return TypeToken.getParameterized(List.class, type).getType();
        }
        throw new NullPointerException("Argument 'type' of type Type (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Type getSetType(@NonNull Type type) {
        if (type != null) {
            return TypeToken.getParameterized(Set.class, type).getType();
        }
        throw new NullPointerException("Argument 'type' of type Type (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Type getMapType(@NonNull Type type, @NonNull Type type2) {
        if (type == null) {
            throw new NullPointerException("Argument 'keyType' of type Type (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (type2 != null) {
            return TypeToken.getParameterized(Map.class, type, type2).getType();
        } else {
            throw new NullPointerException("Argument 'valueType' of type Type (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Type getArrayType(@NonNull Type type) {
        if (type != null) {
            return TypeToken.getArray(type).getType();
        }
        throw new NullPointerException("Argument 'type' of type Type (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Type getType(@NonNull Type type, @NonNull Type... typeArr) {
        if (type == null) {
            throw new NullPointerException("Argument 'rawType' of type Type (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (typeArr != null) {
            return TypeToken.getParameterized(type, typeArr).getType();
        } else {
            throw new NullPointerException("Argument 'typeArguments' of type Type[] (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Gson a() {
        Gson gson = a.get("logUtilsGson");
        if (gson != null) {
            return gson;
        }
        Gson create = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        a.put("logUtilsGson", create);
        return create;
    }

    private static Gson b() {
        return new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
    }
}
