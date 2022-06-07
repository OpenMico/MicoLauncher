package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class LruBitmapPool implements BitmapPool {
    private static final Bitmap.Config a = Bitmap.Config.ARGB_8888;
    private final e b;
    private final Set<Bitmap.Config> c;
    private final long d;
    private final a e;
    private long f;
    private long g;
    private int h;
    private int i;
    private int j;
    private int k;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface a {
        void a(Bitmap bitmap);

        void b(Bitmap bitmap);
    }

    LruBitmapPool(long j, e eVar, Set<Bitmap.Config> set) {
        this.d = j;
        this.f = j;
        this.b = eVar;
        this.c = set;
        this.e = new b();
    }

    public LruBitmapPool(long j) {
        this(j, d(), e());
    }

    public LruBitmapPool(long j, Set<Bitmap.Config> set) {
        this(j, d(), set);
    }

    public long hitCount() {
        return this.h;
    }

    public long missCount() {
        return this.i;
    }

    public long evictionCount() {
        return this.k;
    }

    public long getCurrentSize() {
        return this.g;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public long getMaxSize() {
        return this.f;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public synchronized void setSizeMultiplier(float f) {
        this.f = Math.round(((float) this.d) * f);
        a();
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public synchronized void put(Bitmap bitmap) {
        try {
            if (bitmap == null) {
                throw new NullPointerException("Bitmap must not be null");
            } else if (!bitmap.isRecycled()) {
                if (bitmap.isMutable() && this.b.getSize(bitmap) <= this.f && this.c.contains(bitmap.getConfig())) {
                    int size = this.b.getSize(bitmap);
                    this.b.put(bitmap);
                    this.e.a(bitmap);
                    this.j++;
                    this.g += size;
                    if (Log.isLoggable("LruBitmapPool", 2)) {
                        Log.v("LruBitmapPool", "Put bitmap in pool=" + this.b.logBitmap(bitmap));
                    }
                    b();
                    a();
                    return;
                }
                if (Log.isLoggable("LruBitmapPool", 2)) {
                    Log.v("LruBitmapPool", "Reject bitmap from pool, bitmap: " + this.b.logBitmap(bitmap) + ", is mutable: " + bitmap.isMutable() + ", is allowed config: " + this.c.contains(bitmap.getConfig()));
                }
                bitmap.recycle();
            } else {
                throw new IllegalStateException("Cannot pool recycled bitmap");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private void a() {
        a(this.f);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    @NonNull
    public Bitmap get(int i, int i2, Bitmap.Config config) {
        Bitmap b2 = b(i, i2, config);
        if (b2 == null) {
            return a(i, i2, config);
        }
        b2.eraseColor(0);
        return b2;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    @NonNull
    public Bitmap getDirty(int i, int i2, Bitmap.Config config) {
        Bitmap b2 = b(i, i2, config);
        return b2 == null ? a(i, i2, config) : b2;
    }

    @NonNull
    private static Bitmap a(int i, int i2, @Nullable Bitmap.Config config) {
        if (config == null) {
            config = a;
        }
        return Bitmap.createBitmap(i, i2, config);
    }

    @TargetApi(26)
    private static void a(Bitmap.Config config) {
        if (Build.VERSION.SDK_INT >= 26 && config == Bitmap.Config.HARDWARE) {
            throw new IllegalArgumentException("Cannot create a mutable Bitmap with config: " + config + ". Consider setting Downsampler#ALLOW_HARDWARE_CONFIG to false in your RequestOptions and/or in GlideBuilder.setDefaultRequestOptions");
        }
    }

    @Nullable
    private synchronized Bitmap b(int i, int i2, @Nullable Bitmap.Config config) {
        Bitmap bitmap;
        a(config);
        bitmap = this.b.get(i, i2, config != null ? config : a);
        if (bitmap == null) {
            if (Log.isLoggable("LruBitmapPool", 3)) {
                Log.d("LruBitmapPool", "Missing bitmap=" + this.b.logBitmap(i, i2, config));
            }
            this.i++;
        } else {
            this.h++;
            this.g -= this.b.getSize(bitmap);
            this.e.b(bitmap);
            a(bitmap);
        }
        if (Log.isLoggable("LruBitmapPool", 2)) {
            Log.v("LruBitmapPool", "Get bitmap=" + this.b.logBitmap(i, i2, config));
        }
        b();
        return bitmap;
    }

    private static void a(Bitmap bitmap) {
        bitmap.setHasAlpha(true);
        b(bitmap);
    }

    @TargetApi(19)
    private static void b(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            bitmap.setPremultiplied(true);
        }
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public void clearMemory() {
        if (Log.isLoggable("LruBitmapPool", 3)) {
            Log.d("LruBitmapPool", "clearMemory");
        }
        a(0L);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    @SuppressLint({"InlinedApi"})
    public void trimMemory(int i) {
        if (Log.isLoggable("LruBitmapPool", 3)) {
            Log.d("LruBitmapPool", "trimMemory, level=" + i);
        }
        if (i >= 40 || (Build.VERSION.SDK_INT >= 23 && i >= 20)) {
            clearMemory();
        } else if (i >= 20 || i == 15) {
            a(getMaxSize() / 2);
        }
    }

    private synchronized void a(long j) {
        while (this.g > j) {
            Bitmap removeLast = this.b.removeLast();
            if (removeLast == null) {
                if (Log.isLoggable("LruBitmapPool", 5)) {
                    Log.w("LruBitmapPool", "Size mismatch, resetting");
                    c();
                }
                this.g = 0L;
                return;
            }
            this.e.b(removeLast);
            this.g -= this.b.getSize(removeLast);
            this.k++;
            if (Log.isLoggable("LruBitmapPool", 3)) {
                Log.d("LruBitmapPool", "Evicting bitmap=" + this.b.logBitmap(removeLast));
            }
            b();
            removeLast.recycle();
        }
    }

    private void b() {
        if (Log.isLoggable("LruBitmapPool", 2)) {
            c();
        }
    }

    private void c() {
        Log.v("LruBitmapPool", "Hits=" + this.h + ", misses=" + this.i + ", puts=" + this.j + ", evictions=" + this.k + ", currentSize=" + this.g + ", maxSize=" + this.f + "\nStrategy=" + this.b);
    }

    private static e d() {
        if (Build.VERSION.SDK_INT >= 19) {
            return new SizeConfigStrategy();
        }
        return new b();
    }

    @TargetApi(26)
    private static Set<Bitmap.Config> e() {
        HashSet hashSet = new HashSet(Arrays.asList(Bitmap.Config.values()));
        if (Build.VERSION.SDK_INT >= 19) {
            hashSet.add(null);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            hashSet.remove(Bitmap.Config.HARDWARE);
        }
        return Collections.unmodifiableSet(hashSet);
    }

    /* loaded from: classes.dex */
    private static final class b implements a {
        @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool.a
        public void a(Bitmap bitmap) {
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool.a
        public void b(Bitmap bitmap) {
        }

        b() {
        }
    }
}
