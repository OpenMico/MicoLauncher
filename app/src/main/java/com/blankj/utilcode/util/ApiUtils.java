package com.blankj.utilcode.util;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public final class ApiUtils {
    private Map<Class, BaseApi> a;
    private Map<Class, Class> b;

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.CLASS)
    /* loaded from: classes.dex */
    public @interface Api {
        boolean isMock() default false;
    }

    /* loaded from: classes.dex */
    public static class BaseApi {
    }

    private void a() {
    }

    private ApiUtils() {
        this.a = new ConcurrentHashMap();
        this.b = new HashMap();
        a();
    }

    private void a(Class cls) {
        this.b.put(cls.getSuperclass(), cls);
    }

    @Nullable
    public static <T extends BaseApi> T getApi(@NonNull Class<T> cls) {
        if (cls != null) {
            return (T) ((BaseApi) b().b(cls));
        }
        throw new NullPointerException("Argument 'apiClass' of type Class<T> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void register(@Nullable Class<? extends BaseApi> cls) {
        if (cls != null) {
            b().a(cls);
        }
    }

    @NonNull
    public static String toString_() {
        String apiUtils = b().toString();
        if (apiUtils != null) {
            return apiUtils;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ApiUtils.toString_() marked by @androidx.annotation.NonNull");
    }

    public String toString() {
        return "ApiUtils: " + this.b;
    }

    private static ApiUtils b() {
        return a.a;
    }

    private <Result> Result b(Class cls) {
        Result result = (Result) this.a.get(cls);
        if (result != null) {
            return result;
        }
        synchronized (cls) {
            Result result2 = (Result) this.a.get(cls);
            if (result2 != null) {
                return result2;
            }
            Class cls2 = this.b.get(cls);
            if (cls2 != null) {
                try {
                    Result result3 = (Result) ((BaseApi) cls2.newInstance());
                    this.a.put(cls, result3);
                    return result3;
                } catch (Exception unused) {
                    Log.e("ApiUtils", "The <" + cls2 + "> has no parameterless constructor.");
                    return null;
                }
            } else {
                Log.e("ApiUtils", "The <" + cls + "> doesn't implement.");
                return null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        private static final ApiUtils a = new ApiUtils();
    }
}
