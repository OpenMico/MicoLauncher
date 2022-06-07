package androidx.appcompat.content.res;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ColorStateListInflaterCompat;
import java.util.WeakHashMap;

@SuppressLint({"RestrictedAPI"})
/* loaded from: classes.dex */
public final class AppCompatResources {
    private static final ThreadLocal<TypedValue> a = new ThreadLocal<>();
    private static final WeakHashMap<Context, SparseArray<a>> b = new WeakHashMap<>(0);
    private static final Object c = new Object();

    private AppCompatResources() {
    }

    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColorStateList(i);
        }
        ColorStateList b2 = b(context, i);
        if (b2 != null) {
            return b2;
        }
        ColorStateList a2 = a(context, i);
        if (a2 == null) {
            return ContextCompat.getColorStateList(context, i);
        }
        a(context, i, a2);
        return a2;
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int i) {
        return ResourceManagerInternal.get().getDrawable(context, i);
    }

    @Nullable
    private static ColorStateList a(Context context, int i) {
        if (c(context, i)) {
            return null;
        }
        Resources resources = context.getResources();
        try {
            return ColorStateListInflaterCompat.createFromXml(resources, resources.getXml(i), context.getTheme());
        } catch (Exception e) {
            Log.e("AppCompatResources", "Failed to inflate ColorStateList, leaving it to the framework", e);
            return null;
        }
    }

    @Nullable
    private static ColorStateList b(@NonNull Context context, @ColorRes int i) {
        a aVar;
        synchronized (c) {
            SparseArray<a> sparseArray = b.get(context);
            if (!(sparseArray == null || sparseArray.size() <= 0 || (aVar = sparseArray.get(i)) == null)) {
                if (aVar.b.equals(context.getResources().getConfiguration())) {
                    return aVar.a;
                }
                sparseArray.remove(i);
            }
            return null;
        }
    }

    private static void a(@NonNull Context context, @ColorRes int i, @NonNull ColorStateList colorStateList) {
        synchronized (c) {
            SparseArray<a> sparseArray = b.get(context);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                b.put(context, sparseArray);
            }
            sparseArray.append(i, new a(colorStateList, context.getResources().getConfiguration()));
        }
    }

    private static boolean c(@NonNull Context context, @ColorRes int i) {
        Resources resources = context.getResources();
        TypedValue a2 = a();
        resources.getValue(i, a2, true);
        return a2.type >= 28 && a2.type <= 31;
    }

    @NonNull
    private static TypedValue a() {
        TypedValue typedValue = a.get();
        if (typedValue != null) {
            return typedValue;
        }
        TypedValue typedValue2 = new TypedValue();
        a.set(typedValue2);
        return typedValue2;
    }

    /* loaded from: classes.dex */
    public static class a {
        final ColorStateList a;
        final Configuration b;

        a(@NonNull ColorStateList colorStateList, @NonNull Configuration configuration) {
            this.a = colorStateList;
            this.b = configuration;
        }
    }
}
