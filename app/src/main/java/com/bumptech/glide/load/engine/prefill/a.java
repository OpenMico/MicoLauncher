package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.util.Util;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BitmapPreFillRunner.java */
/* loaded from: classes.dex */
public final class a implements Runnable {
    private final BitmapPool c;
    private final MemoryCache d;
    private final b e;
    private final C0042a f;
    private final Set<PreFillType> g;
    private final Handler h;
    private long i;
    private boolean j;
    private static final C0042a b = new C0042a();
    static final long a = TimeUnit.SECONDS.toMillis(1);

    public a(BitmapPool bitmapPool, MemoryCache memoryCache, b bVar) {
        this(bitmapPool, memoryCache, bVar, b, new Handler(Looper.getMainLooper()));
    }

    @VisibleForTesting
    a(BitmapPool bitmapPool, MemoryCache memoryCache, b bVar, C0042a aVar, Handler handler) {
        this.g = new HashSet();
        this.i = 40L;
        this.c = bitmapPool;
        this.d = memoryCache;
        this.e = bVar;
        this.f = aVar;
        this.h = handler;
    }

    public void a() {
        this.j = true;
    }

    @VisibleForTesting
    boolean b() {
        Bitmap bitmap;
        long a2 = this.f.a();
        while (!this.e.b() && !a(a2)) {
            PreFillType a3 = this.e.a();
            if (!this.g.contains(a3)) {
                this.g.add(a3);
                bitmap = this.c.getDirty(a3.a(), a3.b(), a3.c());
            } else {
                bitmap = Bitmap.createBitmap(a3.a(), a3.b(), a3.c());
            }
            int bitmapByteSize = Util.getBitmapByteSize(bitmap);
            if (c() >= bitmapByteSize) {
                this.d.put(new b(), BitmapResource.obtain(bitmap, this.c));
            } else {
                this.c.put(bitmap);
            }
            if (Log.isLoggable("PreFillRunner", 3)) {
                Log.d("PreFillRunner", "allocated [" + a3.a() + "x" + a3.b() + "] " + a3.c() + " size: " + bitmapByteSize);
            }
        }
        return !this.j && !this.e.b();
    }

    private boolean a(long j) {
        return this.f.a() - j >= 32;
    }

    private long c() {
        return this.d.getMaxSize() - this.d.getCurrentSize();
    }

    @Override // java.lang.Runnable
    public void run() {
        if (b()) {
            this.h.postDelayed(this, d());
        }
    }

    private long d() {
        long j = this.i;
        this.i = Math.min(4 * j, a);
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: BitmapPreFillRunner.java */
    /* loaded from: classes.dex */
    public static final class b implements Key {
        b() {
        }

        @Override // com.bumptech.glide.load.Key
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: BitmapPreFillRunner.java */
    @VisibleForTesting
    /* renamed from: com.bumptech.glide.load.engine.prefill.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public static class C0042a {
        C0042a() {
        }

        long a() {
            return SystemClock.currentThreadTimeMillis();
        }
    }
}
