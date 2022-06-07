package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.NonNull;
import com.blankj.utilcode.constant.CacheConstants;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class CacheDiskUtils implements CacheConstants {
    private static final Map<String, CacheDiskUtils> a = new HashMap();
    private final String b;
    private final File c;
    private final long d;
    private final int e;
    private b f;

    public static CacheDiskUtils getInstance() {
        return getInstance("", Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    public static CacheDiskUtils getInstance(String str) {
        return getInstance(str, Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    public static CacheDiskUtils getInstance(long j, int i) {
        return getInstance("", j, i);
    }

    public static CacheDiskUtils getInstance(String str, long j, int i) {
        if (b.o(str)) {
            str = "cacheUtils";
        }
        return getInstance(new File(Utils.getApp().getCacheDir(), str), j, i);
    }

    public static CacheDiskUtils getInstance(@NonNull File file) {
        if (file != null) {
            return getInstance(file, Long.MAX_VALUE, Integer.MAX_VALUE);
        }
        throw new NullPointerException("Argument 'cacheDir' of type File (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static CacheDiskUtils getInstance(@NonNull File file, long j, int i) {
        if (file != null) {
            String str = file.getAbsoluteFile() + "_" + j + "_" + i;
            CacheDiskUtils cacheDiskUtils = a.get(str);
            if (cacheDiskUtils == null) {
                synchronized (CacheDiskUtils.class) {
                    cacheDiskUtils = a.get(str);
                    if (cacheDiskUtils == null) {
                        cacheDiskUtils = new CacheDiskUtils(str, file, j, i);
                        a.put(str, cacheDiskUtils);
                    }
                }
            }
            return cacheDiskUtils;
        }
        throw new NullPointerException("Argument 'cacheDir' of type File (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private CacheDiskUtils(String str, File file, long j, int i) {
        this.b = str;
        this.c = file;
        this.d = j;
        this.e = i;
    }

    private b a() {
        if (this.c.exists()) {
            if (this.f == null) {
                this.f = new b(this.c, this.d, this.e);
            }
        } else if (this.c.mkdirs()) {
            this.f = new b(this.c, this.d, this.e);
        } else {
            Log.e("CacheDiskUtils", "can't make dirs in " + this.c.getAbsolutePath());
        }
        return this.f;
    }

    public String toString() {
        return this.b + "@" + Integer.toHexString(hashCode());
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
            a("by_" + str, bArr, i);
            return;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private void a(String str, byte[] bArr, int i) {
        b a2;
        if (bArr != null && (a2 = a()) != null) {
            if (i >= 0) {
                bArr = a.b(i, bArr);
            }
            File a3 = a2.a(str);
            b.a(a3, bArr);
            a2.b(a3);
            a2.a(a3);
        }
    }

    public byte[] getBytes(@NonNull String str) {
        if (str != null) {
            return getBytes(str, null);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public byte[] getBytes(@NonNull String str, byte[] bArr) {
        if (str != null) {
            return a("by_" + str, bArr);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private byte[] a(@NonNull String str) {
        if (str != null) {
            return a(str, null);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private byte[] a(@NonNull String str, byte[] bArr) {
        File b2;
        if (str != null) {
            b a2 = a();
            if (a2 == null || (b2 = a2.b(str)) == null) {
                return bArr;
            }
            byte[] a3 = b.a(b2);
            if (a.c(a3)) {
                a2.d(str);
                return bArr;
            }
            a2.b(b2);
            return a.e(a3);
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
            a("st_" + str, b.e(str2), i);
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
            byte[] a2 = a("st_" + str);
            return a2 == null ? str2 : b.b(a2);
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
            a("jo_" + str, b.a(jSONObject), i);
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
            byte[] a2 = a("jo_" + str);
            return a2 == null ? jSONObject : b.c(a2);
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
            a("ja_" + str, b.a(jSONArray), i);
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
            byte[] a2 = a("ja_" + str);
            return a2 == null ? jSONArray : b.d(a2);
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
            a("bi_" + str, b.a(bitmap), i);
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
            byte[] a2 = a("bi_" + str);
            return a2 == null ? bitmap : b.h(a2);
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
            a("dr_" + str, b.a(drawable), i);
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
            byte[] a2 = a("dr_" + str);
            return a2 == null ? drawable : b.i(a2);
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
            a("pa_" + str, b.a(parcelable), i);
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
            byte[] a2 = a("pa_" + str);
            return a2 == null ? t : (T) b.a(a2, creator);
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
            a("se_" + str, b.a(serializable), i);
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
            byte[] a2 = a("se_" + str);
            return a2 == null ? obj : b.e(a2);
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public long getCacheSize() {
        b a2 = a();
        if (a2 == null) {
            return 0L;
        }
        return a2.a();
    }

    public int getCacheCount() {
        b a2 = a();
        if (a2 == null) {
            return 0;
        }
        return a2.b();
    }

    public boolean remove(@NonNull String str) {
        if (str != null) {
            b a2 = a();
            if (a2 == null) {
                return true;
            }
            if (a2.d("by_" + str)) {
                if (a2.d("st_" + str)) {
                    if (a2.d("jo_" + str)) {
                        if (a2.d("ja_" + str)) {
                            if (a2.d("bi_" + str)) {
                                if (a2.d("dr_" + str)) {
                                    if (a2.d("pa_" + str)) {
                                        if (a2.d("se_" + str)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public boolean clear() {
        b a2 = a();
        if (a2 == null) {
            return true;
        }
        return a2.d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b {
        private final AtomicLong a;
        private final AtomicInteger b;
        private final long c;
        private final int d;
        private final Map<File, Long> e;
        private final File f;
        private final Thread g;

        private b(final File file, long j, int i) {
            this.e = Collections.synchronizedMap(new HashMap());
            this.f = file;
            this.c = j;
            this.d = i;
            this.a = new AtomicLong();
            this.b = new AtomicInteger();
            this.g = new Thread(new Runnable() { // from class: com.blankj.utilcode.util.CacheDiskUtils.b.1
                @Override // java.lang.Runnable
                public void run() {
                    File[] listFiles = file.listFiles(new FilenameFilter() { // from class: com.blankj.utilcode.util.CacheDiskUtils.b.1.1
                        @Override // java.io.FilenameFilter
                        public boolean accept(File file2, String str) {
                            return str.startsWith("cdu_");
                        }
                    });
                    if (listFiles != null) {
                        int i2 = 0;
                        int i3 = 0;
                        for (File file2 : listFiles) {
                            i2 = (int) (i2 + file2.length());
                            i3++;
                            b.this.e.put(file2, Long.valueOf(file2.lastModified()));
                        }
                        b.this.a.getAndAdd(i2);
                        b.this.b.getAndAdd(i3);
                    }
                }
            });
            this.g.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long a() {
            c();
            return this.a.get();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int b() {
            c();
            return this.b.get();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File a(String str) {
            c();
            File file = new File(this.f, c(str));
            if (file.exists()) {
                this.b.addAndGet(-1);
                this.a.addAndGet(-file.length());
            }
            return file;
        }

        private void c() {
            try {
                this.g.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File b(String str) {
            File file = new File(this.f, c(str));
            if (!file.exists()) {
                return null;
            }
            return file;
        }

        private String c(String str) {
            return "cdu_" + str.substring(0, 3) + str.substring(3).hashCode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(File file) {
            this.b.addAndGet(1);
            this.a.addAndGet(file.length());
            while (true) {
                if (this.b.get() > this.d || this.a.get() > this.c) {
                    this.a.addAndGet(-e());
                    this.b.addAndGet(-1);
                } else {
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(File file) {
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(valueOf.longValue());
            this.e.put(file, valueOf);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean d(String str) {
            File b = b(str);
            if (b == null) {
                return true;
            }
            if (!b.delete()) {
                return false;
            }
            this.a.addAndGet(-b.length());
            this.b.addAndGet(-1);
            this.e.remove(b);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean d() {
            File[] listFiles = this.f.listFiles(new FilenameFilter() { // from class: com.blankj.utilcode.util.CacheDiskUtils.b.2
                @Override // java.io.FilenameFilter
                public boolean accept(File file, String str) {
                    return str.startsWith("cdu_");
                }
            });
            if (listFiles == null || listFiles.length <= 0) {
                return true;
            }
            boolean z = true;
            for (File file : listFiles) {
                if (!file.delete()) {
                    z = false;
                } else {
                    this.a.addAndGet(-file.length());
                    this.b.addAndGet(-1);
                    this.e.remove(file);
                }
            }
            if (z) {
                this.e.clear();
                this.a.set(0L);
                this.b.set(0);
            }
            return z;
        }

        private long e() {
            if (this.e.isEmpty()) {
                return 0L;
            }
            Long l = Long.MAX_VALUE;
            File file = null;
            Set<Map.Entry<File, Long>> entrySet = this.e.entrySet();
            synchronized (this.e) {
                for (Map.Entry<File, Long> entry : entrySet) {
                    Long value = entry.getValue();
                    if (value.longValue() < l.longValue()) {
                        file = entry.getKey();
                        l = value;
                    }
                }
            }
            if (file == null) {
                return 0L;
            }
            long length = file.length();
            if (!file.delete()) {
                return 0L;
            }
            this.e.remove(file);
            return length;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] b(int i, byte[] bArr) {
            byte[] bytes = a(i).getBytes();
            byte[] bArr2 = new byte[bytes.length + bArr.length];
            System.arraycopy(bytes, 0, bArr2, 0, bytes.length);
            System.arraycopy(bArr, 0, bArr2, bytes.length, bArr.length);
            return bArr2;
        }

        private static String a(int i) {
            return String.format(Locale.getDefault(), "_$%010d$_", Long.valueOf((System.currentTimeMillis() / 1000) + i));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean c(byte[] bArr) {
            long d = d(bArr);
            return d != -1 && System.currentTimeMillis() > d;
        }

        private static long d(byte[] bArr) {
            if (!f(bArr)) {
                return -1L;
            }
            try {
                return Long.parseLong(new String(a(bArr, 2, 12))) * 1000;
            } catch (NumberFormatException unused) {
                return -1L;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] e(byte[] bArr) {
            return f(bArr) ? a(bArr, 14, bArr.length) : bArr;
        }

        private static byte[] a(byte[] bArr, int i, int i2) {
            int i3 = i2 - i;
            if (i3 >= 0) {
                byte[] bArr2 = new byte[i3];
                System.arraycopy(bArr, i, bArr2, 0, Math.min(bArr.length - i, i3));
                return bArr2;
            }
            throw new IllegalArgumentException(i + " > " + i2);
        }

        private static boolean f(byte[] bArr) {
            return bArr != null && bArr.length >= 14 && bArr[0] == 95 && bArr[1] == 36 && bArr[12] == 36 && bArr[13] == 95;
        }
    }
}
