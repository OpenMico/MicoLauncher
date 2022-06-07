package androidx.core.graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.collection.LruCache;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.provider.FontsContractCompat;
import com.xiaomi.mipush.sdk.Constants;

@SuppressLint({"NewApi"})
/* loaded from: classes.dex */
public class TypefaceCompat {
    private static final d a;
    private static final LruCache<String, Typeface> b;

    static {
        if (Build.VERSION.SDK_INT >= 29) {
            a = new TypefaceCompatApi29Impl();
        } else if (Build.VERSION.SDK_INT >= 28) {
            a = new TypefaceCompatApi28Impl();
        } else if (Build.VERSION.SDK_INT >= 26) {
            a = new TypefaceCompatApi26Impl();
        } else if (Build.VERSION.SDK_INT >= 24 && c.a()) {
            a = new c();
        } else if (Build.VERSION.SDK_INT >= 21) {
            a = new b();
        } else {
            a = new d();
        }
        b = new LruCache<>(16);
    }

    private TypefaceCompat() {
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface findFromCache(@NonNull Resources resources, int i, int i2) {
        return b.get(a(resources, i, i2));
    }

    private static String a(Resources resources, int i, int i2) {
        return resources.getResourcePackageName(i) + Constants.ACCEPT_TIME_SEPARATOR_SERVER + i + Constants.ACCEPT_TIME_SEPARATOR_SERVER + i2;
    }

    private static Typeface a(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        Typeface create = Typeface.create(str, 0);
        Typeface create2 = Typeface.create(Typeface.DEFAULT, 0);
        if (create == null || create.equals(create2)) {
            return null;
        }
        return create;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface createFromResourcesFamilyXml(@NonNull Context context, @NonNull FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry, @NonNull Resources resources, int i, int i2, @Nullable ResourcesCompat.FontCallback fontCallback, @Nullable Handler handler, boolean z) {
        Typeface typeface;
        boolean z2;
        if (familyResourceEntry instanceof FontResourcesParserCompat.ProviderResourceEntry) {
            FontResourcesParserCompat.ProviderResourceEntry providerResourceEntry = (FontResourcesParserCompat.ProviderResourceEntry) familyResourceEntry;
            Typeface a2 = a(providerResourceEntry.getSystemFontFamilyName());
            if (a2 != null) {
                if (fontCallback != null) {
                    fontCallback.callbackSuccessAsync(a2, handler);
                }
                return a2;
            }
            if (z) {
                z2 = providerResourceEntry.getFetchStrategy() == 0;
            } else {
                z2 = fontCallback == null;
            }
            typeface = FontsContractCompat.requestFont(context, providerResourceEntry.getRequest(), i2, z2, z ? providerResourceEntry.getTimeout() : -1, ResourcesCompat.FontCallback.getHandler(handler), new ResourcesCallbackAdapter(fontCallback));
        } else {
            typeface = a.createFromFontFamilyFilesResourceEntry(context, (FontResourcesParserCompat.FontFamilyFilesResourceEntry) familyResourceEntry, resources, i2);
            if (fontCallback != null) {
                if (typeface != null) {
                    fontCallback.callbackSuccessAsync(typeface, handler);
                } else {
                    fontCallback.callbackFailAsync(-3, handler);
                }
            }
        }
        if (typeface != null) {
            b.put(a(resources, i, i2), typeface);
        }
        return typeface;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface createFromResourcesFontFile(@NonNull Context context, @NonNull Resources resources, int i, String str, int i2) {
        Typeface createFromResourcesFontFile = a.createFromResourcesFontFile(context, resources, i, str, i2);
        if (createFromResourcesFontFile != null) {
            b.put(a(resources, i, i2), createFromResourcesFontFile);
        }
        return createFromResourcesFontFile;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface createFromFontInfo(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        return a.createFromFontInfo(context, cancellationSignal, fontInfoArr, i);
    }

    @Nullable
    private static Typeface a(Context context, Typeface typeface, int i) {
        FontResourcesParserCompat.FontFamilyFilesResourceEntry a2 = a.a(typeface);
        if (a2 == null) {
            return null;
        }
        return a.createFromFontFamilyFilesResourceEntry(context, a2, context.getResources(), i);
    }

    @NonNull
    public static Typeface create(@NonNull Context context, @Nullable Typeface typeface, int i) {
        Typeface a2;
        if (context != null) {
            return (Build.VERSION.SDK_INT >= 21 || (a2 = a(context, typeface, i)) == null) ? Typeface.create(typeface, i) : a2;
        }
        throw new IllegalArgumentException("Context cannot be null");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @VisibleForTesting
    public static void clearCache() {
        b.evictAll();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* loaded from: classes.dex */
    public static class ResourcesCallbackAdapter extends FontsContractCompat.FontRequestCallback {
        @Nullable
        private ResourcesCompat.FontCallback a;

        public ResourcesCallbackAdapter(@Nullable ResourcesCompat.FontCallback fontCallback) {
            this.a = fontCallback;
        }

        @Override // androidx.core.provider.FontsContractCompat.FontRequestCallback
        public void onTypefaceRetrieved(@NonNull Typeface typeface) {
            ResourcesCompat.FontCallback fontCallback = this.a;
            if (fontCallback != null) {
                fontCallback.onFontRetrieved(typeface);
            }
        }

        @Override // androidx.core.provider.FontsContractCompat.FontRequestCallback
        public void onTypefaceRequestFailed(int i) {
            ResourcesCompat.FontCallback fontCallback = this.a;
            if (fontCallback != null) {
                fontCallback.onFontRetrievalFailed(i);
            }
        }
    }
}
