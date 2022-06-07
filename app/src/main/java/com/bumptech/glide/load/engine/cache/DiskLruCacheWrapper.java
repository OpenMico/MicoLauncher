package com.bumptech.glide.load.engine.cache;

import android.util.Log;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.cache.DiskCache;
import java.io.File;
import java.io.IOException;

/* loaded from: classes.dex */
public class DiskLruCacheWrapper implements DiskCache {
    private static DiskLruCacheWrapper a;
    private final File c;
    private final long d;
    private DiskLruCache f;
    private final a e = new a();
    private final SafeKeyGenerator b = new SafeKeyGenerator();

    @Deprecated
    public static synchronized DiskCache get(File file, long j) {
        DiskLruCacheWrapper diskLruCacheWrapper;
        synchronized (DiskLruCacheWrapper.class) {
            if (a == null) {
                a = new DiskLruCacheWrapper(file, j);
            }
            diskLruCacheWrapper = a;
        }
        return diskLruCacheWrapper;
    }

    public static DiskCache create(File file, long j) {
        return new DiskLruCacheWrapper(file, j);
    }

    @Deprecated
    protected DiskLruCacheWrapper(File file, long j) {
        this.c = file;
        this.d = j;
    }

    private synchronized DiskLruCache a() throws IOException {
        if (this.f == null) {
            this.f = DiskLruCache.open(this.c, 1, 1, this.d);
        }
        return this.f;
    }

    @Override // com.bumptech.glide.load.engine.cache.DiskCache
    public File get(Key key) {
        String safeKey = this.b.getSafeKey(key);
        if (Log.isLoggable("DiskLruCacheWrapper", 2)) {
            Log.v("DiskLruCacheWrapper", "Get: Obtained: " + safeKey + " for for Key: " + key);
        }
        try {
            DiskLruCache.Value value = a().get(safeKey);
            if (value != null) {
                return value.getFile(0);
            }
            return null;
        } catch (IOException e) {
            if (!Log.isLoggable("DiskLruCacheWrapper", 5)) {
                return null;
            }
            Log.w("DiskLruCacheWrapper", "Unable to get from disk cache", e);
            return null;
        }
    }

    /* JADX WARN: Finally extract failed */
    @Override // com.bumptech.glide.load.engine.cache.DiskCache
    public void put(Key key, DiskCache.Writer writer) {
        DiskLruCache a2;
        String safeKey = this.b.getSafeKey(key);
        this.e.a(safeKey);
        try {
            if (Log.isLoggable("DiskLruCacheWrapper", 2)) {
                Log.v("DiskLruCacheWrapper", "Put: Obtained: " + safeKey + " for for Key: " + key);
            }
            try {
                a2 = a();
            } catch (IOException e) {
                if (Log.isLoggable("DiskLruCacheWrapper", 5)) {
                    Log.w("DiskLruCacheWrapper", "Unable to put to disk cache", e);
                }
            }
            if (a2.get(safeKey) == null) {
                DiskLruCache.Editor edit = a2.edit(safeKey);
                if (edit != null) {
                    try {
                        if (writer.write(edit.getFile(0))) {
                            edit.commit();
                        }
                        edit.abortUnlessCommitted();
                    } catch (Throwable th) {
                        edit.abortUnlessCommitted();
                        throw th;
                    }
                } else {
                    throw new IllegalStateException("Had two simultaneous puts for: " + safeKey);
                }
            }
        } finally {
            this.e.b(safeKey);
        }
    }

    @Override // com.bumptech.glide.load.engine.cache.DiskCache
    public void delete(Key key) {
        try {
            a().remove(this.b.getSafeKey(key));
        } catch (IOException e) {
            if (Log.isLoggable("DiskLruCacheWrapper", 5)) {
                Log.w("DiskLruCacheWrapper", "Unable to delete from disk cache", e);
            }
        }
    }

    @Override // com.bumptech.glide.load.engine.cache.DiskCache
    public synchronized void clear() {
        try {
            a().delete();
        } catch (IOException e) {
            if (Log.isLoggable("DiskLruCacheWrapper", 5)) {
                Log.w("DiskLruCacheWrapper", "Unable to clear disk cache or disk cache cleared externally", e);
            }
        }
        b();
    }

    private synchronized void b() {
        this.f = null;
    }
}
