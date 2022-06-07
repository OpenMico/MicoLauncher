package com.bumptech.glide.util;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.model.Model;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

/* loaded from: classes.dex */
public final class Util {
    private static final char[] a = "0123456789abcdef".toCharArray();
    private static final char[] b = new char[64];
    @Nullable
    private static volatile Handler c;

    private static boolean a(int i) {
        return i > 0 || i == Integer.MIN_VALUE;
    }

    public static int hashCode(int i, int i2) {
        return (i2 * 31) + i;
    }

    private Util() {
    }

    @NonNull
    public static String sha256BytesToHex(@NonNull byte[] bArr) {
        String a2;
        synchronized (b) {
            a2 = a(bArr, b);
        }
        return a2;
    }

    @NonNull
    private static String a(@NonNull byte[] bArr, @NonNull char[] cArr) {
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            int i3 = i * 2;
            char[] cArr2 = a;
            cArr[i3] = cArr2[i2 >>> 4];
            cArr[i3 + 1] = cArr2[i2 & 15];
        }
        return new String(cArr);
    }

    @Deprecated
    public static int getSize(@NonNull Bitmap bitmap) {
        return getBitmapByteSize(bitmap);
    }

    @TargetApi(19)
    public static int getBitmapByteSize(@NonNull Bitmap bitmap) {
        if (!bitmap.isRecycled()) {
            if (Build.VERSION.SDK_INT >= 19) {
                try {
                    return bitmap.getAllocationByteCount();
                } catch (NullPointerException unused) {
                }
            }
            return bitmap.getHeight() * bitmap.getRowBytes();
        }
        throw new IllegalStateException("Cannot obtain size for recycled Bitmap: " + bitmap + "[" + bitmap.getWidth() + "x" + bitmap.getHeight() + "] " + bitmap.getConfig());
    }

    public static int getBitmapByteSize(int i, int i2, @Nullable Bitmap.Config config) {
        return i * i2 * getBytesPerPixel(config);
    }

    public static int getBytesPerPixel(@Nullable Bitmap.Config config) {
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        switch (AnonymousClass1.a[config.ordinal()]) {
            case 1:
                return 1;
            case 2:
            case 3:
                return 2;
            case 4:
                return 8;
            default:
                return 4;
        }
    }

    /* renamed from: com.bumptech.glide.util.Util$1 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[Bitmap.Config.values().length];

        static {
            try {
                a[Bitmap.Config.ALPHA_8.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[Bitmap.Config.RGB_565.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[Bitmap.Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[Bitmap.Config.RGBA_F16.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[Bitmap.Config.ARGB_8888.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static boolean isValidDimensions(int i, int i2) {
        return a(i) && a(i2);
    }

    public static void postOnUiThread(Runnable runnable) {
        a().post(runnable);
    }

    public static void removeCallbacksOnUiThread(Runnable runnable) {
        a().removeCallbacks(runnable);
    }

    private static Handler a() {
        if (c == null) {
            synchronized (Util.class) {
                if (c == null) {
                    c = new Handler(Looper.getMainLooper());
                }
            }
        }
        return c;
    }

    public static void assertMainThread() {
        if (!isOnMainThread()) {
            throw new IllegalArgumentException("You must call this method on the main thread");
        }
    }

    public static void assertBackgroundThread() {
        if (!isOnBackgroundThread()) {
            throw new IllegalArgumentException("You must call this method on a background thread");
        }
    }

    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean isOnBackgroundThread() {
        return !isOnMainThread();
    }

    @NonNull
    public static <T> Queue<T> createQueue(int i) {
        return new ArrayDeque(i);
    }

    @NonNull
    public static <T> List<T> getSnapshot(@NonNull Collection<T> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (T t : collection) {
            if (t != null) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static boolean bothNullOrEqual(@Nullable Object obj, @Nullable Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public static boolean bothModelsNullEquivalentOrEquals(@Nullable Object obj, @Nullable Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        if (obj instanceof Model) {
            return ((Model) obj).isEquivalentTo(obj2);
        }
        return obj.equals(obj2);
    }

    public static int hashCode(int i) {
        return hashCode(i, 17);
    }

    public static int hashCode(float f) {
        return hashCode(f, 17);
    }

    public static int hashCode(float f, int i) {
        return hashCode(Float.floatToIntBits(f), i);
    }

    public static int hashCode(@Nullable Object obj, int i) {
        return hashCode(obj == null ? 0 : obj.hashCode(), i);
    }

    public static int hashCode(boolean z, int i) {
        return hashCode(z ? 1 : 0, i);
    }

    public static int hashCode(boolean z) {
        return hashCode(z, 17);
    }
}
