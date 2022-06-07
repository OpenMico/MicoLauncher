package com.blankj.utilcode.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class AdaptScreenUtils {
    private static List<Field> a;

    private AdaptScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @NonNull
    public static Resources adaptWidth(@NonNull Resources resources, int i) {
        if (resources != null) {
            a(resources, (resources.getDisplayMetrics().widthPixels * 72.0f) / i);
            if (resources != null) {
                return resources;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AdaptScreenUtils.adaptWidth() marked by @androidx.annotation.NonNull");
        }
        throw new NullPointerException("Argument 'resources' of type Resources (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @NonNull
    public static Resources adaptHeight(@NonNull Resources resources, int i) {
        if (resources != null) {
            Resources adaptHeight = adaptHeight(resources, i, false);
            if (adaptHeight != null) {
                return adaptHeight;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AdaptScreenUtils.adaptHeight() marked by @androidx.annotation.NonNull");
        }
        throw new NullPointerException("Argument 'resources' of type Resources (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @NonNull
    public static Resources adaptHeight(@NonNull Resources resources, int i, boolean z) {
        if (resources != null) {
            a(resources, ((resources.getDisplayMetrics().heightPixels + (z ? a(resources) : 0)) * 72.0f) / i);
            if (resources != null) {
                return resources;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AdaptScreenUtils.adaptHeight() marked by @androidx.annotation.NonNull");
        }
        throw new NullPointerException("Argument 'resources' of type Resources (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static int a(@NonNull Resources resources) {
        if (resources != null) {
            int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (identifier != 0) {
                return resources.getDimensionPixelSize(identifier);
            }
            return 0;
        }
        throw new NullPointerException("Argument 'resources' of type Resources (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @NonNull
    public static Resources closeAdapt(@NonNull Resources resources) {
        if (resources != null) {
            a(resources, Resources.getSystem().getDisplayMetrics().density * 72.0f);
            if (resources != null) {
                return resources;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AdaptScreenUtils.closeAdapt() marked by @androidx.annotation.NonNull");
        }
        throw new NullPointerException("Argument 'resources' of type Resources (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static int pt2Px(float f) {
        return (int) (((f * Utils.getApp().getResources().getDisplayMetrics().xdpi) / 72.0f) + 0.5d);
    }

    public static int px2Pt(float f) {
        return (int) (((f * 72.0f) / Utils.getApp().getResources().getDisplayMetrics().xdpi) + 0.5d);
    }

    private static void a(@NonNull Resources resources, float f) {
        if (resources != null) {
            resources.getDisplayMetrics().xdpi = f;
            Utils.getApp().getResources().getDisplayMetrics().xdpi = f;
            b(resources, f);
            return;
        }
        throw new NullPointerException("Argument 'resources' of type Resources (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Runnable a() {
        return new Runnable() { // from class: com.blankj.utilcode.util.AdaptScreenUtils.1
            @Override // java.lang.Runnable
            public void run() {
                AdaptScreenUtils.c();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c() {
        a(Resources.getSystem(), Resources.getSystem().getDisplayMetrics().xdpi);
    }

    private static void b(Resources resources, float f) {
        if (a == null) {
            a = new ArrayList();
            Class<?> cls = resources.getClass();
            Field[] declaredFields = cls.getDeclaredFields();
            while (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.getType().isAssignableFrom(DisplayMetrics.class)) {
                        field.setAccessible(true);
                        DisplayMetrics a2 = a(resources, field);
                        if (a2 != null) {
                            a.add(field);
                            a2.xdpi = f;
                        }
                    }
                }
                cls = cls.getSuperclass();
                if (cls != null) {
                    declaredFields = cls.getDeclaredFields();
                } else {
                    return;
                }
            }
            return;
        }
        c(resources, f);
    }

    private static void c(Resources resources, float f) {
        for (Field field : a) {
            try {
                DisplayMetrics displayMetrics = (DisplayMetrics) field.get(resources);
                if (displayMetrics != null) {
                    displayMetrics.xdpi = f;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static DisplayMetrics a(Resources resources, Field field) {
        try {
            return (DisplayMetrics) field.get(resources);
        } catch (Exception unused) {
            return null;
        }
    }
}
