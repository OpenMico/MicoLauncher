package com.google.android.exoplayer2.upstream.cache;

import android.os.ConditionVariable;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes2.dex */
public final class SimpleCache implements Cache {
    private static final HashSet<File> a = new HashSet<>();
    private final File b;
    private final CacheEvictor c;
    private final d d;
    @Nullable
    private final b e;
    private final HashMap<String, ArrayList<Cache.Listener>> f;
    private final Random g;
    private final boolean h;
    private long i;
    private long j;
    private boolean k;
    private Cache.CacheException l;

    public static synchronized boolean isCacheFolderLocked(File file) {
        boolean contains;
        synchronized (SimpleCache.class) {
            contains = a.contains(file.getAbsoluteFile());
        }
        return contains;
    }

    @WorkerThread
    public static void delete(File file, @Nullable DatabaseProvider databaseProvider) {
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                file.delete();
                return;
            }
            if (databaseProvider != null) {
                long a2 = a(listFiles);
                if (a2 != -1) {
                    try {
                        b.a(databaseProvider, a2);
                    } catch (DatabaseIOException unused) {
                        StringBuilder sb = new StringBuilder(52);
                        sb.append("Failed to delete file metadata: ");
                        sb.append(a2);
                        Log.w("SimpleCache", sb.toString());
                    }
                    try {
                        d.a(databaseProvider, a2);
                    } catch (DatabaseIOException unused2) {
                        StringBuilder sb2 = new StringBuilder(52);
                        sb2.append("Failed to delete file metadata: ");
                        sb2.append(a2);
                        Log.w("SimpleCache", sb2.toString());
                    }
                }
            }
            Util.recursiveDelete(file);
        }
    }

    @Deprecated
    public SimpleCache(File file, CacheEvictor cacheEvictor) {
        this(file, cacheEvictor, (byte[]) null, false);
    }

    @Deprecated
    public SimpleCache(File file, CacheEvictor cacheEvictor, @Nullable byte[] bArr) {
        this(file, cacheEvictor, bArr, bArr != null);
    }

    @Deprecated
    public SimpleCache(File file, CacheEvictor cacheEvictor, @Nullable byte[] bArr, boolean z) {
        this(file, cacheEvictor, null, bArr, z, true);
    }

    public SimpleCache(File file, CacheEvictor cacheEvictor, DatabaseProvider databaseProvider) {
        this(file, cacheEvictor, databaseProvider, null, false, false);
    }

    public SimpleCache(File file, CacheEvictor cacheEvictor, @Nullable DatabaseProvider databaseProvider, @Nullable byte[] bArr, boolean z, boolean z2) {
        this(file, cacheEvictor, new d(databaseProvider, file, bArr, z, z2), (databaseProvider == null || z2) ? null : new b(databaseProvider));
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.google.android.exoplayer2.upstream.cache.SimpleCache$1] */
    SimpleCache(File file, CacheEvictor cacheEvictor, d dVar, @Nullable b bVar) {
        if (c(file)) {
            this.b = file;
            this.c = cacheEvictor;
            this.d = dVar;
            this.e = bVar;
            this.f = new HashMap<>();
            this.g = new Random();
            this.h = cacheEvictor.requiresCacheSpanTouches();
            this.i = -1L;
            final ConditionVariable conditionVariable = new ConditionVariable();
            new Thread("ExoPlayer:SimpleCacheInit") { // from class: com.google.android.exoplayer2.upstream.cache.SimpleCache.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    synchronized (SimpleCache.this) {
                        conditionVariable.open();
                        SimpleCache.this.a();
                        SimpleCache.this.c.onCacheInitialized();
                    }
                }
            }.start();
            conditionVariable.block();
            return;
        }
        String valueOf = String.valueOf(file);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 46);
        sb.append("Another SimpleCache instance uses the folder: ");
        sb.append(valueOf);
        throw new IllegalStateException(sb.toString());
    }

    public synchronized void checkInitialization() throws Cache.CacheException {
        if (this.l != null) {
            throw this.l;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized long getUid() {
        return this.i;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void release() {
        if (!this.k) {
            this.f.clear();
            b();
            try {
                this.d.a();
                d(this.b);
            } catch (IOException e) {
                Log.e("SimpleCache", "Storing index file failed", e);
                d(this.b);
            }
            this.k = true;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized NavigableSet<CacheSpan> addListener(String str, Cache.Listener listener) {
        Assertions.checkState(!this.k);
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(listener);
        ArrayList<Cache.Listener> arrayList = this.f.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.f.put(str, arrayList);
        }
        arrayList.add(listener);
        return getCachedSpans(str);
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void removeListener(String str, Cache.Listener listener) {
        if (!this.k) {
            ArrayList<Cache.Listener> arrayList = this.f.get(str);
            if (arrayList != null) {
                arrayList.remove(listener);
                if (arrayList.isEmpty()) {
                    this.f.remove(str);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized NavigableSet<CacheSpan> getCachedSpans(String str) {
        TreeSet treeSet;
        Assertions.checkState(!this.k);
        c c = this.d.c(str);
        if (c != null && !c.d()) {
            treeSet = new TreeSet((Collection) c.c());
        }
        treeSet = new TreeSet();
        return treeSet;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized Set<String> getKeys() {
        Assertions.checkState(!this.k);
        return new HashSet(this.d.d());
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized long getCacheSpace() {
        Assertions.checkState(!this.k);
        return this.j;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized CacheSpan startReadWrite(String str, long j, long j2) throws InterruptedException, Cache.CacheException {
        CacheSpan startReadWriteNonBlocking;
        Assertions.checkState(!this.k);
        checkInitialization();
        while (true) {
            startReadWriteNonBlocking = startReadWriteNonBlocking(str, j, j2);
            if (startReadWriteNonBlocking == null) {
                wait();
            }
        }
        return startReadWriteNonBlocking;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    @Nullable
    public synchronized CacheSpan startReadWriteNonBlocking(String str, long j, long j2) throws Cache.CacheException {
        Assertions.checkState(!this.k);
        checkInitialization();
        e a2 = a(str, j, j2);
        if (a2.isCached) {
            return a(str, a2);
        } else if (this.d.b(str).b(j, a2.length)) {
            return a2;
        } else {
            return null;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized File startFile(String str, long j, long j2) throws Cache.CacheException {
        c c;
        File file;
        Assertions.checkState(!this.k);
        checkInitialization();
        c = this.d.c(str);
        Assertions.checkNotNull(c);
        Assertions.checkState(c.a(j, j2));
        if (!this.b.exists()) {
            b(this.b);
            b();
        }
        this.c.onStartFile(this, str, j, j2);
        file = new File(this.b, Integer.toString(this.g.nextInt(10)));
        if (!file.exists()) {
            b(file);
        }
        return e.a(file, c.a, j, System.currentTimeMillis());
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void commitFile(File file, long j) throws Cache.CacheException {
        boolean z = true;
        Assertions.checkState(!this.k);
        if (file.exists()) {
            if (j == 0) {
                file.delete();
                return;
            }
            e eVar = (e) Assertions.checkNotNull(e.a(file, j, this.d));
            c cVar = (c) Assertions.checkNotNull(this.d.c(eVar.key));
            Assertions.checkState(cVar.a(eVar.position, eVar.length));
            long contentLength = ContentMetadata.getContentLength(cVar.a());
            if (contentLength != -1) {
                if (eVar.position + eVar.length > contentLength) {
                    z = false;
                }
                Assertions.checkState(z);
            }
            if (this.e != null) {
                try {
                    this.e.a(file.getName(), eVar.length, eVar.lastTouchTimestamp);
                } catch (IOException e) {
                    throw new Cache.CacheException(e);
                }
            }
            a(eVar);
            try {
                this.d.a();
                notifyAll();
            } catch (IOException e2) {
                throw new Cache.CacheException(e2);
            }
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void releaseHoleSpan(CacheSpan cacheSpan) {
        Assertions.checkState(!this.k);
        c cVar = (c) Assertions.checkNotNull(this.d.c(cacheSpan.key));
        cVar.a(cacheSpan.position);
        this.d.e(cVar.b);
        notifyAll();
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void removeResource(String str) {
        Assertions.checkState(!this.k);
        for (CacheSpan cacheSpan : getCachedSpans(str)) {
            a(cacheSpan);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void removeSpan(CacheSpan cacheSpan) {
        Assertions.checkState(!this.k);
        a(cacheSpan);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001b, code lost:
        if (r4.d(r5, r7) >= r7) goto L_0x001f;
     */
    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean isCached(java.lang.String r4, long r5, long r7) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.k     // Catch: all -> 0x0021
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0009
            r0 = r1
            goto L_0x000a
        L_0x0009:
            r0 = r2
        L_0x000a:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)     // Catch: all -> 0x0021
            com.google.android.exoplayer2.upstream.cache.d r0 = r3.d     // Catch: all -> 0x0021
            com.google.android.exoplayer2.upstream.cache.c r4 = r0.c(r4)     // Catch: all -> 0x0021
            if (r4 == 0) goto L_0x001e
            long r4 = r4.d(r5, r7)     // Catch: all -> 0x0021
            int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r4 < 0) goto L_0x001e
            goto L_0x001f
        L_0x001e:
            r1 = r2
        L_0x001f:
            monitor-exit(r3)
            return r1
        L_0x0021:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.SimpleCache.isCached(java.lang.String, long, long):boolean");
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized long getCachedLength(String str, long j, long j2) {
        c c;
        Assertions.checkState(!this.k);
        if (j2 == -1) {
            j2 = Long.MAX_VALUE;
        }
        c = this.d.c(str);
        return c != null ? c.d(j, j2) : -j2;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized long getCachedBytes(String str, long j, long j2) {
        long j3;
        long j4 = j2 == -1 ? Long.MAX_VALUE : j + j2;
        long j5 = j4 < 0 ? Long.MAX_VALUE : j4;
        long j6 = j;
        j3 = 0;
        while (j6 < j5) {
            long cachedLength = getCachedLength(str, j6, j5 - j6);
            if (cachedLength > 0) {
                j3 += cachedLength;
            } else {
                cachedLength = -cachedLength;
            }
            j6 += cachedLength;
        }
        return j3;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void applyContentMetadataMutations(String str, ContentMetadataMutations contentMetadataMutations) throws Cache.CacheException {
        Assertions.checkState(!this.k);
        checkInitialization();
        this.d.a(str, contentMetadataMutations);
        try {
            this.d.a();
        } catch (IOException e) {
            throw new Cache.CacheException(e);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized ContentMetadata getContentMetadata(String str) {
        Assertions.checkState(!this.k);
        return this.d.f(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        if (!this.b.exists()) {
            try {
                b(this.b);
            } catch (Cache.CacheException e) {
                this.l = e;
                return;
            }
        }
        File[] listFiles = this.b.listFiles();
        if (listFiles == null) {
            String valueOf = String.valueOf(this.b);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 38);
            sb.append("Failed to list cache directory files: ");
            sb.append(valueOf);
            String sb2 = sb.toString();
            Log.e("SimpleCache", sb2);
            this.l = new Cache.CacheException(sb2);
            return;
        }
        this.i = a(listFiles);
        if (this.i == -1) {
            try {
                this.i = a(this.b);
            } catch (IOException e2) {
                String valueOf2 = String.valueOf(this.b);
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 28);
                sb3.append("Failed to create cache UID: ");
                sb3.append(valueOf2);
                String sb4 = sb3.toString();
                Log.e("SimpleCache", sb4, e2);
                this.l = new Cache.CacheException(sb4, e2);
                return;
            }
        }
        try {
            this.d.a(this.i);
            if (this.e != null) {
                this.e.a(this.i);
                Map<String, a> a2 = this.e.a();
                a(this.b, true, listFiles, a2);
                this.e.a(a2.keySet());
            } else {
                a(this.b, true, listFiles, null);
            }
            this.d.c();
            try {
                this.d.a();
            } catch (IOException e3) {
                Log.e("SimpleCache", "Storing index file failed", e3);
            }
        } catch (IOException e4) {
            String valueOf3 = String.valueOf(this.b);
            StringBuilder sb5 = new StringBuilder(String.valueOf(valueOf3).length() + 36);
            sb5.append("Failed to initialize cache indices: ");
            sb5.append(valueOf3);
            String sb6 = sb5.toString();
            Log.e("SimpleCache", sb6, e4);
            this.l = new Cache.CacheException(sb6, e4);
        }
    }

    private void a(File file, boolean z, @Nullable File[] fileArr, @Nullable Map<String, a> map) {
        if (fileArr != null && fileArr.length != 0) {
            for (File file2 : fileArr) {
                String name = file2.getName();
                if (z && name.indexOf(46) == -1) {
                    a(file2, false, file2.listFiles(), map);
                } else if (!z || (!d.a(name) && !name.endsWith(".uid"))) {
                    long j = -1;
                    long j2 = C.TIME_UNSET;
                    a remove = map != null ? map.remove(name) : null;
                    if (remove != null) {
                        j = remove.a;
                        j2 = remove.b;
                    }
                    e a2 = e.a(file2, j, j2, this.d);
                    if (a2 != null) {
                        a(a2);
                    } else {
                        file2.delete();
                    }
                }
            }
        } else if (!z) {
            file.delete();
        }
    }

    private e a(String str, e eVar) {
        if (!this.h) {
            return eVar;
        }
        String name = ((File) Assertions.checkNotNull(eVar.file)).getName();
        long j = eVar.length;
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = false;
        b bVar = this.e;
        if (bVar != null) {
            try {
                bVar.a(name, j, currentTimeMillis);
            } catch (IOException unused) {
                Log.w("SimpleCache", "Failed to update index with new touch timestamp.");
            }
        } else {
            z = true;
        }
        e a2 = this.d.c(str).a(eVar, currentTimeMillis, z);
        a(eVar, a2);
        return a2;
    }

    private e a(String str, long j, long j2) {
        e c;
        c c2 = this.d.c(str);
        if (c2 == null) {
            return e.a(str, j, j2);
        }
        while (true) {
            c = c2.c(j, j2);
            if (!c.isCached || c.file.length() == c.length) {
                break;
            }
            b();
        }
        return c;
    }

    private void a(e eVar) {
        this.d.b(eVar.key).a(eVar);
        this.j += eVar.length;
        b(eVar);
    }

    private void a(CacheSpan cacheSpan) {
        c c = this.d.c(cacheSpan.key);
        if (c != null && c.a(cacheSpan)) {
            this.j -= cacheSpan.length;
            if (this.e != null) {
                String name = cacheSpan.file.getName();
                try {
                    this.e.a(name);
                } catch (IOException unused) {
                    String valueOf = String.valueOf(name);
                    Log.w("SimpleCache", valueOf.length() != 0 ? "Failed to remove file index entry for: ".concat(valueOf) : new String("Failed to remove file index entry for: "));
                }
            }
            this.d.e(c.b);
            b(cacheSpan);
        }
    }

    private void b() {
        ArrayList arrayList = new ArrayList();
        for (c cVar : this.d.b()) {
            Iterator<e> it = cVar.c().iterator();
            while (it.hasNext()) {
                e next = it.next();
                if (next.file.length() != next.length) {
                    arrayList.add(next);
                }
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            a((CacheSpan) arrayList.get(i));
        }
    }

    private void b(CacheSpan cacheSpan) {
        ArrayList<Cache.Listener> arrayList = this.f.get(cacheSpan.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).onSpanRemoved(this, cacheSpan);
            }
        }
        this.c.onSpanRemoved(this, cacheSpan);
    }

    private void b(e eVar) {
        ArrayList<Cache.Listener> arrayList = this.f.get(eVar.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).onSpanAdded(this, eVar);
            }
        }
        this.c.onSpanAdded(this, eVar);
    }

    private void a(e eVar, CacheSpan cacheSpan) {
        ArrayList<Cache.Listener> arrayList = this.f.get(eVar.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).onSpanTouched(this, eVar, cacheSpan);
            }
        }
        this.c.onSpanTouched(this, eVar, cacheSpan);
    }

    private static long a(File[] fileArr) {
        int length = fileArr.length;
        for (int i = 0; i < length; i++) {
            File file = fileArr[i];
            String name = file.getName();
            if (name.endsWith(".uid")) {
                try {
                    return a(name);
                } catch (NumberFormatException unused) {
                    String valueOf = String.valueOf(file);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 20);
                    sb.append("Malformed UID file: ");
                    sb.append(valueOf);
                    Log.e("SimpleCache", sb.toString());
                    file.delete();
                }
            }
        }
        return -1L;
    }

    private static long a(File file) throws IOException {
        long nextLong = new SecureRandom().nextLong();
        long abs = nextLong == Long.MIN_VALUE ? 0L : Math.abs(nextLong);
        String valueOf = String.valueOf(Long.toString(abs, 16));
        String valueOf2 = String.valueOf(".uid");
        File file2 = new File(file, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (file2.createNewFile()) {
            return abs;
        }
        String valueOf3 = String.valueOf(file2);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf3).length() + 27);
        sb.append("Failed to create UID file: ");
        sb.append(valueOf3);
        throw new IOException(sb.toString());
    }

    private static long a(String str) {
        return Long.parseLong(str.substring(0, str.indexOf(46)), 16);
    }

    private static void b(File file) throws Cache.CacheException {
        if (!file.mkdirs() && !file.isDirectory()) {
            String valueOf = String.valueOf(file);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34);
            sb.append("Failed to create cache directory: ");
            sb.append(valueOf);
            String sb2 = sb.toString();
            Log.e("SimpleCache", sb2);
            throw new Cache.CacheException(sb2);
        }
    }

    private static synchronized boolean c(File file) {
        boolean add;
        synchronized (SimpleCache.class) {
            add = a.add(file.getAbsoluteFile());
        }
        return add;
    }

    private static synchronized void d(File file) {
        synchronized (SimpleCache.class) {
            a.remove(file.getAbsoluteFile());
        }
    }
}
