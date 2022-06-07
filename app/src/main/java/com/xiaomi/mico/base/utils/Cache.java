package com.xiaomi.mico.base.utils;

import android.util.Log;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.jakewharton.disklrucache.DiskLruCache;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class Cache {
    private static a b;
    private static Gson a = new Gson();
    public static MemoryCache<String, Object> memory = new MemoryCache<>();

    public static synchronized void init(File file, int i, long j) throws IOException {
        synchronized (Cache.class) {
            if (b == null) {
                b = new a(file, i, j);
            }
        }
    }

    public static boolean contains(String str) {
        try {
            return b.a(str);
        } catch (Exception e) {
            Log.e("Cache", Log.getStackTraceString(e));
            return false;
        }
    }

    public static void put(String str, Object obj) {
        put(str, obj, -1L);
    }

    public static void put(String str, Object obj, long j) {
        try {
            b.a(str, obj, j);
        } catch (Exception e) {
            Log.e("Cache", Log.getStackTraceString(e));
        }
    }

    public static Object getSerializableObject(String str) {
        try {
            return b.b(str);
        } catch (Exception e) {
            Log.e("Cache", Log.getStackTraceString(e));
            return null;
        }
    }

    public static <T> T getJsonObject(String str, Class<T> cls) {
        return (T) a(str, cls);
    }

    private static <T> T a(String str, Class<T> cls) {
        try {
            return (T) b.a(str, cls);
        } catch (Exception e) {
            Log.e("Cache", Log.getStackTraceString(e));
            return null;
        }
    }

    public static void delete(String str) {
        try {
            b.c(str);
        } catch (Exception e) {
            Log.e("Cache", Log.getStackTraceString(e));
        }
    }

    public static void deleteAll() {
        b.a();
        b = null;
    }

    /* loaded from: classes3.dex */
    public static class a {
        private DiskLruCache a;

        public a(File file, int i, long j) throws IOException {
            this.a = DiskLruCache.open(file, i, 2, j);
        }

        public void a(String str, Object obj, long j) throws IOException {
            b bVar;
            if (j > 0) {
                bVar = new b(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(j));
            } else {
                bVar = new b(-1L);
            }
            a(str, obj, bVar);
        }

        boolean a(String str) throws IOException {
            DiskLruCache.Snapshot snapshot = this.a.get(d(str));
            if (snapshot == null) {
                return false;
            }
            snapshot.close();
            return true;
        }

        public Object b(String str) {
            return a(str, Object.class);
        }

        /* JADX WARN: Code restructure failed: missing block: B:24:0x0066, code lost:
            if (r2 != null) goto L_0x0068;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0068, code lost:
            r2.close();
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0092, code lost:
            if (r2 != null) goto L_0x0068;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0095, code lost:
            return (T) r0;
         */
        /* JADX WARN: Type inference failed for: r0v0, types: [com.jakewharton.disklrucache.DiskLruCache$Snapshot] */
        /* JADX WARN: Type inference failed for: r0v1, types: [T] */
        /* JADX WARN: Type inference failed for: r0v10 */
        /* JADX WARN: Type inference failed for: r0v11 */
        /* JADX WARN: Type inference failed for: r0v12 */
        /* JADX WARN: Type inference failed for: r0v13 */
        /* JADX WARN: Type inference failed for: r0v2 */
        /* JADX WARN: Type inference failed for: r0v3 */
        /* JADX WARN: Unknown variable types count: 1 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public <T> T a(java.lang.String r10, java.lang.Class<T> r11) {
            /*
                r9 = this;
                r0 = 0
                java.lang.String r1 = r9.d(r10)     // Catch: Exception -> 0x0073, all -> 0x0070
                com.jakewharton.disklrucache.DiskLruCache r2 = r9.a     // Catch: Exception -> 0x006e, all -> 0x0070
                com.jakewharton.disklrucache.DiskLruCache$Snapshot r2 = r2.get(r1)     // Catch: Exception -> 0x006e, all -> 0x0070
                if (r2 != 0) goto L_0x0013
                if (r2 == 0) goto L_0x0012
                r2.close()
            L_0x0012:
                return r0
            L_0x0013:
                java.io.ObjectInputStream r3 = new java.io.ObjectInputStream     // Catch: Exception -> 0x006c, all -> 0x0096
                r4 = 0
                java.io.InputStream r4 = r2.getInputStream(r4)     // Catch: Exception -> 0x006c, all -> 0x0096
                r3.<init>(r4)     // Catch: Exception -> 0x006c, all -> 0x0096
                java.io.ObjectInputStream r4 = new java.io.ObjectInputStream     // Catch: Exception -> 0x006c, all -> 0x0096
                r5 = 1
                java.io.InputStream r5 = r2.getInputStream(r5)     // Catch: Exception -> 0x006c, all -> 0x0096
                r4.<init>(r5)     // Catch: Exception -> 0x006c, all -> 0x0096
                java.lang.Object r3 = r3.readObject()     // Catch: Exception -> 0x006c, all -> 0x0096
                com.xiaomi.mico.base.utils.Cache$b r3 = (com.xiaomi.mico.base.utils.Cache.b) r3     // Catch: Exception -> 0x006c, all -> 0x0096
                long r5 = r3.expire     // Catch: Exception -> 0x006c, all -> 0x0096
                r7 = 0
                int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r5 <= 0) goto L_0x004d
                long r5 = r3.expire     // Catch: Exception -> 0x006c, all -> 0x0096
                long r7 = java.lang.System.currentTimeMillis()     // Catch: Exception -> 0x006c, all -> 0x0096
                int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r5 >= 0) goto L_0x004d
                r2.close()     // Catch: Exception -> 0x006c, all -> 0x0096
                com.jakewharton.disklrucache.DiskLruCache r11 = r9.a     // Catch: Exception -> 0x006c, all -> 0x0096
                r11.remove(r1)     // Catch: Exception -> 0x006c, all -> 0x0096
                if (r2 == 0) goto L_0x004c
                r2.close()
            L_0x004c:
                return r0
            L_0x004d:
                int r3 = r3.serializeType     // Catch: Exception -> 0x006c, all -> 0x0096
                switch(r3) {
                    case 0: goto L_0x0062;
                    case 1: goto L_0x0053;
                    default: goto L_0x0052;
                }     // Catch: Exception -> 0x006c, all -> 0x0096
            L_0x0052:
                goto L_0x0066
            L_0x0053:
                java.lang.String r3 = r4.readUTF()     // Catch: Exception -> 0x006c, all -> 0x0096
                if (r11 == 0) goto L_0x0066
                com.google.gson.Gson r4 = com.xiaomi.mico.base.utils.Cache.a()     // Catch: Exception -> 0x006c, all -> 0x0096
                java.lang.Object r0 = r4.fromJson(r3, r11)     // Catch: Exception -> 0x006c, all -> 0x0096
                goto L_0x0066
            L_0x0062:
                java.lang.Object r0 = r4.readObject()     // Catch: Exception -> 0x006c, all -> 0x0096
            L_0x0066:
                if (r2 == 0) goto L_0x0095
            L_0x0068:
                r2.close()
                goto L_0x0095
            L_0x006c:
                r11 = move-exception
                goto L_0x0076
            L_0x006e:
                r11 = move-exception
                goto L_0x0075
            L_0x0070:
                r10 = move-exception
                r2 = r0
                goto L_0x0097
            L_0x0073:
                r11 = move-exception
                r1 = r10
            L_0x0075:
                r2 = r0
            L_0x0076:
                java.lang.String r3 = "Cache"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: all -> 0x0096
                r4.<init>()     // Catch: all -> 0x0096
                r4.append(r10)     // Catch: all -> 0x0096
                java.lang.String r5 = "  "
                r4.append(r5)     // Catch: all -> 0x0096
                r4.append(r1)     // Catch: all -> 0x0096
                java.lang.String r1 = r4.toString()     // Catch: all -> 0x0096
                android.util.Log.e(r3, r1, r11)     // Catch: all -> 0x0096
                r9.c(r10)     // Catch: all -> 0x0096
                if (r2 == 0) goto L_0x0095
                goto L_0x0068
            L_0x0095:
                return r0
            L_0x0096:
                r10 = move-exception
            L_0x0097:
                if (r2 == 0) goto L_0x009c
                r2.close()
            L_0x009c:
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mico.base.utils.Cache.a.a(java.lang.String, java.lang.Class):java.lang.Object");
        }

        public void a(String str, Object obj, b bVar) throws IOException {
            DiskLruCache.Editor editor = null;
            try {
                editor = this.a.edit(d(str));
                if (editor != null) {
                    a(obj, editor, bVar);
                    a(bVar, editor);
                    editor.commit();
                }
            } catch (IOException e) {
                Log.e("Cache", Log.getStackTraceString(e));
                if (editor != null) {
                    editor.abort();
                }
            }
        }

        public void c(String str) {
            try {
                this.a.remove(d(str));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void a() {
            try {
                this.a.delete();
            } catch (IOException e) {
                Log.e("Cache", Log.getStackTraceString(e));
            }
        }

        private void a(b bVar, DiskLruCache.Editor editor) throws IOException {
            Throwable th;
            ObjectOutputStream objectOutputStream = null;
            try {
                ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(editor.newOutputStream(0));
                try {
                    objectOutputStream2.writeObject(bVar);
                    IOUtils.closeQuietly(objectOutputStream2);
                } catch (Throwable th2) {
                    th = th2;
                    objectOutputStream = objectOutputStream2;
                    IOUtils.closeQuietly(objectOutputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }

        private void a(Object obj, DiskLruCache.Editor editor, b bVar) throws IOException {
            Throwable th;
            ObjectOutputStream objectOutputStream;
            try {
                objectOutputStream = new ObjectOutputStream(editor.newOutputStream(1));
                try {
                    if (obj instanceof Serializable) {
                        objectOutputStream.writeObject(obj);
                        bVar.serializeType = 0;
                    } else {
                        objectOutputStream.writeUTF(Cache.a.toJson(obj));
                        bVar.serializeType = 1;
                    }
                    IOUtils.closeQuietly(objectOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    IOUtils.closeQuietly(objectOutputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                objectOutputStream = null;
            }
        }

        private String d(String str) {
            return MD5.MD5_16(str);
        }
    }

    /* loaded from: classes3.dex */
    public static class b implements Serializable {
        private static final long serialVersionUID = 862397375458034278L;
        public final long expire;
        public int serializeType;

        public b(long j) {
            this.expire = j;
        }
    }

    /* loaded from: classes3.dex */
    public static class MemoryCache<K, V> {
        private com.google.common.cache.Cache<K, V> a = (com.google.common.cache.Cache<K, V>) CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(10, TimeUnit.MINUTES).build();

        public V get(K k) {
            return get(k, null);
        }

        public V get(K k, V v) {
            V ifPresent = this.a.getIfPresent(k);
            return ifPresent == null ? v : ifPresent;
        }

        public void put(K k, V v) {
            this.a.put(k, v);
        }
    }
}
