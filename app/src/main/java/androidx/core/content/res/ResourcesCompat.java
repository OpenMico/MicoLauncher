package androidx.core.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import androidx.annotation.AnyRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public final class ResourcesCompat {
    @AnyRes
    public static final int ID_NULL = 0;
    private static final ThreadLocal<TypedValue> a = new ThreadLocal<>();
    private static final WeakHashMap<b, SparseArray<a>> b = new WeakHashMap<>(0);
    private static final Object c = new Object();

    @Nullable
    public static Drawable getDrawable(@NonNull Resources resources, @DrawableRes int i, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawable(i, theme);
        }
        return resources.getDrawable(i);
    }

    @Nullable
    public static Drawable getDrawableForDensity(@NonNull Resources resources, @DrawableRes int i, int i2, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawableForDensity(i, i2, theme);
        }
        if (Build.VERSION.SDK_INT >= 15) {
            return resources.getDrawableForDensity(i, i2);
        }
        return resources.getDrawable(i);
    }

    @ColorInt
    public static int getColor(@NonNull Resources resources, @ColorRes int i, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 23) {
            return resources.getColor(i, theme);
        }
        return resources.getColor(i);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Resources resources, @ColorRes int i, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 23) {
            return resources.getColorStateList(i, theme);
        }
        b bVar = new b(resources, theme);
        ColorStateList a2 = a(bVar, i);
        if (a2 != null) {
            return a2;
        }
        ColorStateList a3 = a(resources, i, theme);
        if (a3 == null) {
            return resources.getColorStateList(i);
        }
        a(bVar, i, a3);
        return a3;
    }

    @Nullable
    private static ColorStateList a(Resources resources, int i, @Nullable Resources.Theme theme) {
        if (a(resources, i)) {
            return null;
        }
        try {
            return ColorStateListInflaterCompat.createFromXml(resources, resources.getXml(i), theme);
        } catch (Exception e) {
            Log.e("ResourcesCompat", "Failed to inflate ColorStateList, leaving it to the framework", e);
            return null;
        }
    }

    @Nullable
    private static ColorStateList a(@NonNull b bVar, @ColorRes int i) {
        a aVar;
        synchronized (c) {
            SparseArray<a> sparseArray = b.get(bVar);
            if (!(sparseArray == null || sparseArray.size() <= 0 || (aVar = sparseArray.get(i)) == null)) {
                if (aVar.b.equals(bVar.a.getConfiguration())) {
                    return aVar.a;
                }
                sparseArray.remove(i);
            }
            return null;
        }
    }

    private static void a(@NonNull b bVar, @ColorRes int i, @NonNull ColorStateList colorStateList) {
        synchronized (c) {
            SparseArray<a> sparseArray = b.get(bVar);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                b.put(bVar, sparseArray);
            }
            sparseArray.append(i, new a(colorStateList, bVar.a.getConfiguration()));
        }
    }

    private static boolean a(@NonNull Resources resources, @ColorRes int i) {
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
    public static final class b {
        final Resources a;
        @Nullable
        final Resources.Theme b;

        b(@NonNull Resources resources, @Nullable Resources.Theme theme) {
            this.a = resources;
            this.b = theme;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            b bVar = (b) obj;
            return this.a.equals(bVar.a) && ObjectsCompat.equals(this.b, bVar.b);
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.a, this.b);
        }
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

    public static float getFloat(@NonNull Resources resources, @DimenRes int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            return c.a(resources, i);
        }
        TypedValue a2 = a();
        resources.getValue(i, a2, true);
        if (a2.type == 4) {
            return a2.getFloat();
        }
        throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(a2.type) + " is not valid");
    }

    @Nullable
    public static Typeface getFont(@NonNull Context context, @FontRes int i) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return a(context, i, new TypedValue(), 0, null, null, false, false);
    }

    @Nullable
    public static Typeface getCachedFont(@NonNull Context context, @FontRes int i) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return a(context, i, new TypedValue(), 0, null, null, false, true);
    }

    /* loaded from: classes.dex */
    public static abstract class FontCallback {
        public abstract void onFontRetrievalFailed(int i);

        public abstract void onFontRetrieved(@NonNull Typeface typeface);

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public final void callbackSuccessAsync(final Typeface typeface, @Nullable Handler handler) {
            getHandler(handler).post(new Runnable() { // from class: androidx.core.content.res.ResourcesCompat.FontCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    FontCallback.this.onFontRetrieved(typeface);
                }
            });
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public final void callbackFailAsync(final int i, @Nullable Handler handler) {
            getHandler(handler).post(new Runnable() { // from class: androidx.core.content.res.ResourcesCompat.FontCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    FontCallback.this.onFontRetrievalFailed(i);
                }
            });
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public static Handler getHandler(@Nullable Handler handler) {
            return handler == null ? new Handler(Looper.getMainLooper()) : handler;
        }
    }

    public static void getFont(@NonNull Context context, @FontRes int i, @NonNull FontCallback fontCallback, @Nullable Handler handler) throws Resources.NotFoundException {
        Preconditions.checkNotNull(fontCallback);
        if (context.isRestricted()) {
            fontCallback.callbackFailAsync(-4, handler);
        } else {
            a(context, i, new TypedValue(), 0, fontCallback, handler, false, false);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface getFont(@NonNull Context context, @FontRes int i, TypedValue typedValue, int i2, @Nullable FontCallback fontCallback) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return a(context, i, typedValue, i2, fontCallback, null, true, false);
    }

    private static Typeface a(@NonNull Context context, int i, TypedValue typedValue, int i2, @Nullable FontCallback fontCallback, @Nullable Handler handler, boolean z, boolean z2) {
        Resources resources = context.getResources();
        resources.getValue(i, typedValue, true);
        Typeface a2 = a(context, resources, typedValue, i, i2, fontCallback, handler, z, z2);
        if (a2 != null || fontCallback != null || z2) {
            return a2;
        }
        throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i) + " could not be retrieved.");
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.graphics.Typeface a(@androidx.annotation.NonNull android.content.Context r14, android.content.res.Resources r15, android.util.TypedValue r16, int r17, int r18, @androidx.annotation.Nullable androidx.core.content.res.ResourcesCompat.FontCallback r19, @androidx.annotation.Nullable android.os.Handler r20, boolean r21, boolean r22) {
        /*
            r0 = r15
            r1 = r16
            r4 = r17
            r5 = r18
            r9 = r19
            r10 = r20
            java.lang.CharSequence r2 = r1.string
            if (r2 == 0) goto L_0x00ae
            java.lang.CharSequence r1 = r1.string
            java.lang.String r11 = r1.toString()
            java.lang.String r1 = "res/"
            boolean r1 = r11.startsWith(r1)
            r12 = -3
            r13 = 0
            if (r1 != 0) goto L_0x0025
            if (r9 == 0) goto L_0x0024
            r9.callbackFailAsync(r12, r10)
        L_0x0024:
            return r13
        L_0x0025:
            android.graphics.Typeface r1 = androidx.core.graphics.TypefaceCompat.findFromCache(r15, r4, r5)
            if (r1 == 0) goto L_0x0031
            if (r9 == 0) goto L_0x0030
            r9.callbackSuccessAsync(r1, r10)
        L_0x0030:
            return r1
        L_0x0031:
            if (r22 == 0) goto L_0x0034
            return r13
        L_0x0034:
            java.lang.String r1 = r11.toLowerCase()     // Catch: XmlPullParserException -> 0x0091, IOException -> 0x0079
            java.lang.String r2 = ".xml"
            boolean r1 = r1.endsWith(r2)     // Catch: XmlPullParserException -> 0x0091, IOException -> 0x0079
            if (r1 == 0) goto L_0x0068
            android.content.res.XmlResourceParser r1 = r15.getXml(r4)     // Catch: XmlPullParserException -> 0x0091, IOException -> 0x0079
            androidx.core.content.res.FontResourcesParserCompat$FamilyResourceEntry r2 = androidx.core.content.res.FontResourcesParserCompat.parse(r1, r15)     // Catch: XmlPullParserException -> 0x0091, IOException -> 0x0079
            if (r2 != 0) goto L_0x0057
            java.lang.String r0 = "ResourcesCompat"
            java.lang.String r1 = "Failed to find font-family tag"
            android.util.Log.e(r0, r1)     // Catch: XmlPullParserException -> 0x0091, IOException -> 0x0079
            if (r9 == 0) goto L_0x0056
            r9.callbackFailAsync(r12, r10)     // Catch: XmlPullParserException -> 0x0091, IOException -> 0x0079
        L_0x0056:
            return r13
        L_0x0057:
            r1 = r14
            r3 = r15
            r4 = r17
            r5 = r18
            r6 = r19
            r7 = r20
            r8 = r21
            android.graphics.Typeface r0 = androidx.core.graphics.TypefaceCompat.createFromResourcesFamilyXml(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch: XmlPullParserException -> 0x0091, IOException -> 0x0079
            return r0
        L_0x0068:
            r1 = r14
            android.graphics.Typeface r0 = androidx.core.graphics.TypefaceCompat.createFromResourcesFontFile(r14, r15, r4, r11, r5)     // Catch: XmlPullParserException -> 0x0091, IOException -> 0x0079
            if (r9 == 0) goto L_0x0078
            if (r0 == 0) goto L_0x0075
            r9.callbackSuccessAsync(r0, r10)     // Catch: XmlPullParserException -> 0x0091, IOException -> 0x0079
            goto L_0x0078
        L_0x0075:
            r9.callbackFailAsync(r12, r10)     // Catch: XmlPullParserException -> 0x0091, IOException -> 0x0079
        L_0x0078:
            return r0
        L_0x0079:
            r0 = move-exception
            java.lang.String r1 = "ResourcesCompat"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed to read xml resource "
            r2.append(r3)
            r2.append(r11)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r1, r2, r0)
            goto L_0x00a8
        L_0x0091:
            r0 = move-exception
            java.lang.String r1 = "ResourcesCompat"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed to parse xml resource "
            r2.append(r3)
            r2.append(r11)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r1, r2, r0)
        L_0x00a8:
            if (r9 == 0) goto L_0x00ad
            r9.callbackFailAsync(r12, r10)
        L_0x00ad:
            return r13
        L_0x00ae:
            android.content.res.Resources$NotFoundException r2 = new android.content.res.Resources$NotFoundException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "Resource \""
            r3.append(r5)
            java.lang.String r0 = r15.getResourceName(r4)
            r3.append(r0)
            java.lang.String r0 = "\" ("
            r3.append(r0)
            java.lang.String r0 = java.lang.Integer.toHexString(r17)
            r3.append(r0)
            java.lang.String r0 = ") is not a Font: "
            r3.append(r0)
            r3.append(r1)
            java.lang.String r0 = r3.toString()
            r2.<init>(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ResourcesCompat.a(android.content.Context, android.content.res.Resources, android.util.TypedValue, int, int, androidx.core.content.res.ResourcesCompat$FontCallback, android.os.Handler, boolean, boolean):android.graphics.Typeface");
    }

    @RequiresApi(29)
    /* loaded from: classes.dex */
    static class c {
        static float a(@NonNull Resources resources, @DimenRes int i) {
            return resources.getFloat(i);
        }
    }

    private ResourcesCompat() {
    }

    /* loaded from: classes.dex */
    public static final class ThemeCompat {
        private ThemeCompat() {
        }

        public static void rebase(@NonNull Resources.Theme theme) {
            if (Build.VERSION.SDK_INT >= 29) {
                b.a(theme);
            } else if (Build.VERSION.SDK_INT >= 23) {
                a.a(theme);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @RequiresApi(29)
        /* loaded from: classes.dex */
        public static class b {
            static void a(@NonNull Resources.Theme theme) {
                theme.rebase();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @RequiresApi(23)
        /* loaded from: classes.dex */
        public static class a {
            private static final Object a = new Object();
            private static Method b;
            private static boolean c;

            static void a(@NonNull Resources.Theme theme) {
                synchronized (a) {
                    if (!c) {
                        try {
                            b = Resources.Theme.class.getDeclaredMethod("rebase", new Class[0]);
                            b.setAccessible(true);
                        } catch (NoSuchMethodException e) {
                            Log.i("ResourcesCompat", "Failed to retrieve rebase() method", e);
                        }
                        c = true;
                    }
                    if (b != null) {
                        try {
                            b.invoke(theme, new Object[0]);
                        } catch (IllegalAccessException | InvocationTargetException e2) {
                            Log.i("ResourcesCompat", "Failed to invoke rebase() method via reflection", e2);
                            b = null;
                        }
                    }
                }
            }
        }
    }
}
