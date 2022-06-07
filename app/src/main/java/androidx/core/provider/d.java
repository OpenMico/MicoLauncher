package androidx.core.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.core.util.Consumer;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FontRequestWorker.java */
/* loaded from: classes.dex */
public class d {
    static final LruCache<String, Typeface> a = new LruCache<>(16);
    private static final ExecutorService d = e.a("fonts-androidx", 10, 10000);
    static final Object b = new Object();
    @GuardedBy("LOCK")
    static final SimpleArrayMap<String, ArrayList<Consumer<a>>> c = new SimpleArrayMap<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a() {
        a.evictAll();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Typeface a(@NonNull final Context context, @NonNull final FontRequest fontRequest, @NonNull a aVar, final int i, int i2) {
        final String a2 = a(fontRequest, i);
        Typeface typeface = a.get(a2);
        if (typeface != null) {
            aVar.a(new a(typeface));
            return typeface;
        } else if (i2 == -1) {
            a a3 = a(a2, context, fontRequest, i);
            aVar.a(a3);
            return a3.a;
        } else {
            try {
                a aVar2 = (a) e.a(d, new Callable<a>() { // from class: androidx.core.provider.d.1
                    /* renamed from: a */
                    public a call() {
                        return d.a(a2, context, fontRequest, i);
                    }
                }, i2);
                aVar.a(aVar2);
                return aVar2.a;
            } catch (InterruptedException unused) {
                aVar.a(new a(-3));
                return null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Typeface a(@NonNull final Context context, @NonNull final FontRequest fontRequest, final int i, @Nullable Executor executor, @NonNull final a aVar) {
        final String a2 = a(fontRequest, i);
        Typeface typeface = a.get(a2);
        if (typeface != null) {
            aVar.a(new a(typeface));
            return typeface;
        }
        Consumer<a> consumer = new Consumer<a>() { // from class: androidx.core.provider.d.2
            /* renamed from: a */
            public void accept(a aVar2) {
                a.this.a(aVar2);
            }
        };
        synchronized (b) {
            ArrayList<Consumer<a>> arrayList = c.get(a2);
            if (arrayList != null) {
                arrayList.add(consumer);
                return null;
            }
            ArrayList<Consumer<a>> arrayList2 = new ArrayList<>();
            arrayList2.add(consumer);
            c.put(a2, arrayList2);
            Callable<a> callable = new Callable<a>() { // from class: androidx.core.provider.d.3
                /* renamed from: a */
                public a call() {
                    return d.a(a2, context, fontRequest, i);
                }
            };
            if (executor == null) {
                executor = d;
            }
            e.a(executor, callable, new Consumer<a>() { // from class: androidx.core.provider.d.4
                /* renamed from: a */
                public void accept(a aVar2) {
                    synchronized (d.b) {
                        ArrayList<Consumer<a>> arrayList3 = d.c.get(a2);
                        if (arrayList3 != null) {
                            d.c.remove(a2);
                            for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                                arrayList3.get(i2).accept(aVar2);
                            }
                        }
                    }
                }
            });
            return null;
        }
    }

    private static String a(@NonNull FontRequest fontRequest, int i) {
        return fontRequest.a() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + i;
    }

    @NonNull
    static a a(@NonNull String str, @NonNull Context context, @NonNull FontRequest fontRequest, int i) {
        Typeface typeface = a.get(str);
        if (typeface != null) {
            return new a(typeface);
        }
        try {
            FontsContractCompat.FontFamilyResult a2 = c.a(context, fontRequest, (CancellationSignal) null);
            int a3 = a(a2);
            if (a3 != 0) {
                return new a(a3);
            }
            Typeface createFromFontInfo = TypefaceCompat.createFromFontInfo(context, null, a2.getFonts(), i);
            if (createFromFontInfo == null) {
                return new a(-3);
            }
            a.put(str, createFromFontInfo);
            return new a(createFromFontInfo);
        } catch (PackageManager.NameNotFoundException unused) {
            return new a(-1);
        }
    }

    @SuppressLint({"WrongConstant"})
    private static int a(@NonNull FontsContractCompat.FontFamilyResult fontFamilyResult) {
        if (fontFamilyResult.getStatusCode() != 0) {
            return fontFamilyResult.getStatusCode() != 1 ? -3 : -2;
        }
        FontsContractCompat.FontInfo[] fonts = fontFamilyResult.getFonts();
        if (fonts == null || fonts.length == 0) {
            return 1;
        }
        for (FontsContractCompat.FontInfo fontInfo : fonts) {
            int resultCode = fontInfo.getResultCode();
            if (resultCode != 0) {
                if (resultCode < 0) {
                    return -3;
                } else {
                    return resultCode;
                }
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FontRequestWorker.java */
    /* loaded from: classes.dex */
    public static final class a {
        final Typeface a;
        final int b;

        a(int i) {
            this.a = null;
            this.b = i;
        }

        @SuppressLint({"WrongConstant"})
        a(@NonNull Typeface typeface) {
            this.a = typeface;
            this.b = 0;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @SuppressLint({"WrongConstant"})
        public boolean a() {
            return this.b == 0;
        }
    }
}
