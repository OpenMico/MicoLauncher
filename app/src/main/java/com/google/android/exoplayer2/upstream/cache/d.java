package com.google.android.exoplayer2.upstream.cache;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.ReusableBufferedOutputStream;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CachedContentIndex.java */
/* loaded from: classes2.dex */
public class d {
    private final HashMap<String, c> a;
    private final SparseArray<String> b;
    private final SparseBooleanArray c;
    private final SparseBooleanArray d;
    private c e;
    @Nullable
    private c f;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: CachedContentIndex.java */
    /* loaded from: classes2.dex */
    public interface c {
        void a(long j);

        void a(c cVar);

        void a(c cVar, boolean z);

        void a(HashMap<String, c> hashMap) throws IOException;

        void a(HashMap<String, c> hashMap, SparseArray<String> sparseArray) throws IOException;

        boolean a() throws IOException;

        void b() throws IOException;

        void b(HashMap<String, c> hashMap) throws IOException;
    }

    public static boolean a(String str) {
        return str.startsWith("cached_content_index.exi");
    }

    @WorkerThread
    public static void a(DatabaseProvider databaseProvider, long j) throws DatabaseIOException {
        a.a(databaseProvider, j);
    }

    public d(@Nullable DatabaseProvider databaseProvider, @Nullable File file, @Nullable byte[] bArr, boolean z, boolean z2) {
        Assertions.checkState((databaseProvider == null && file == null) ? false : true);
        this.a = new HashMap<>();
        this.b = new SparseArray<>();
        this.c = new SparseBooleanArray();
        this.d = new SparseBooleanArray();
        b bVar = null;
        a aVar = databaseProvider != null ? new a(databaseProvider) : null;
        bVar = file != null ? new b(new File(file, "cached_content_index.exi"), bArr, z) : bVar;
        if (aVar == null || (bVar != null && z2)) {
            this.e = (c) Util.castNonNull(bVar);
            this.f = aVar;
            return;
        }
        this.e = aVar;
        this.f = bVar;
    }

    @WorkerThread
    public void a(long j) throws IOException {
        c cVar;
        this.e.a(j);
        c cVar2 = this.f;
        if (cVar2 != null) {
            cVar2.a(j);
        }
        if (this.e.a() || (cVar = this.f) == null || !cVar.a()) {
            this.e.a(this.a, this.b);
        } else {
            this.f.a(this.a, this.b);
            this.e.a(this.a);
        }
        c cVar3 = this.f;
        if (cVar3 != null) {
            cVar3.b();
            this.f = null;
        }
    }

    @WorkerThread
    public void a() throws IOException {
        this.e.b(this.a);
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            this.b.remove(this.c.keyAt(i));
        }
        this.c.clear();
        this.d.clear();
    }

    public c b(String str) {
        c cVar = this.a.get(str);
        return cVar == null ? g(str) : cVar;
    }

    @Nullable
    public c c(String str) {
        return this.a.get(str);
    }

    public Collection<c> b() {
        return Collections.unmodifiableCollection(this.a.values());
    }

    public int d(String str) {
        return b(str).a;
    }

    @Nullable
    public String a(int i) {
        return this.b.get(i);
    }

    public void e(String str) {
        c cVar = this.a.get(str);
        if (cVar != null && cVar.d() && cVar.b()) {
            this.a.remove(str);
            int i = cVar.a;
            boolean z = this.d.get(i);
            this.e.a(cVar, z);
            if (z) {
                this.b.remove(i);
                this.d.delete(i);
                return;
            }
            this.b.put(i, null);
            this.c.put(i, true);
        }
    }

    public void c() {
        UnmodifiableIterator it = ImmutableSet.copyOf((Collection) this.a.keySet()).iterator();
        while (it.hasNext()) {
            e((String) it.next());
        }
    }

    public Set<String> d() {
        return this.a.keySet();
    }

    public void a(String str, ContentMetadataMutations contentMetadataMutations) {
        c b2 = b(str);
        if (b2.a(contentMetadataMutations)) {
            this.e.a(b2);
        }
    }

    public ContentMetadata f(String str) {
        c c2 = c(str);
        return c2 != null ? c2.a() : DefaultContentMetadata.EMPTY;
    }

    private c g(String str) {
        int a2 = a(this.b);
        c cVar = new c(a2, str);
        this.a.put(str, cVar);
        this.b.put(a2, str);
        this.d.put(a2, true);
        this.e.a(cVar);
        return cVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"GetInstance"})
    public static Cipher f() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if (Util.SDK_INT == 18) {
            try {
                return Cipher.getInstance("AES/CBC/PKCS5PADDING", "BC");
            } catch (Throwable unused) {
            }
        }
        return Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }

    @VisibleForTesting
    static int a(SparseArray<String> sparseArray) {
        int size = sparseArray.size();
        int keyAt = size == 0 ? 0 : sparseArray.keyAt(size - 1) + 1;
        if (keyAt < 0) {
            keyAt = 0;
            while (keyAt < size && keyAt == sparseArray.keyAt(keyAt)) {
                keyAt++;
            }
        }
        return keyAt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DefaultContentMetadata b(DataInputStream dataInputStream) throws IOException {
        int readInt = dataInputStream.readInt();
        HashMap hashMap = new HashMap();
        for (int i = 0; i < readInt; i++) {
            String readUTF = dataInputStream.readUTF();
            int readInt2 = dataInputStream.readInt();
            if (readInt2 >= 0) {
                int min = Math.min(readInt2, 10485760);
                byte[] bArr = Util.EMPTY_BYTE_ARRAY;
                int i2 = min;
                int i3 = 0;
                while (i3 != readInt2) {
                    int i4 = i3 + i2;
                    bArr = Arrays.copyOf(bArr, i4);
                    dataInputStream.readFully(bArr, i3, i2);
                    i2 = Math.min(readInt2 - i4, 10485760);
                    i3 = i4;
                }
                hashMap.put(readUTF, bArr);
            } else {
                StringBuilder sb = new StringBuilder(31);
                sb.append("Invalid value size: ");
                sb.append(readInt2);
                throw new IOException(sb.toString());
            }
        }
        return new DefaultContentMetadata(hashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(DefaultContentMetadata defaultContentMetadata, DataOutputStream dataOutputStream) throws IOException {
        Set<Map.Entry<String, byte[]>> entrySet = defaultContentMetadata.entrySet();
        dataOutputStream.writeInt(entrySet.size());
        for (Map.Entry<String, byte[]> entry : entrySet) {
            dataOutputStream.writeUTF(entry.getKey());
            byte[] value = entry.getValue();
            dataOutputStream.writeInt(value.length);
            dataOutputStream.write(value);
        }
    }

    /* compiled from: CachedContentIndex.java */
    /* loaded from: classes2.dex */
    private static class b implements c {
        private final boolean a;
        @Nullable
        private final Cipher b;
        @Nullable
        private final SecretKeySpec c;
        @Nullable
        private final SecureRandom d;
        private final AtomicFile e;
        private boolean f;
        @Nullable
        private ReusableBufferedOutputStream g;

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void a(long j) {
        }

        public b(File file, @Nullable byte[] bArr, boolean z) {
            SecretKeySpec secretKeySpec;
            Cipher cipher;
            boolean z2 = false;
            Assertions.checkState(bArr != null || !z);
            SecureRandom secureRandom = null;
            if (bArr != null) {
                Assertions.checkArgument(bArr.length == 16 ? true : z2);
                try {
                    cipher = d.f();
                    secretKeySpec = new SecretKeySpec(bArr, "AES");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                    throw new IllegalStateException(e);
                }
            } else {
                Assertions.checkArgument(!z);
                cipher = null;
                secretKeySpec = null;
            }
            this.a = z;
            this.b = cipher;
            this.c = secretKeySpec;
            this.d = z ? new SecureRandom() : secureRandom;
            this.e = new AtomicFile(file);
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public boolean a() {
            return this.e.exists();
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void b() {
            this.e.delete();
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void a(HashMap<String, c> hashMap, SparseArray<String> sparseArray) {
            Assertions.checkState(!this.f);
            if (!b(hashMap, sparseArray)) {
                hashMap.clear();
                sparseArray.clear();
                this.e.delete();
            }
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void a(HashMap<String, c> hashMap) throws IOException {
            c(hashMap);
            this.f = false;
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void b(HashMap<String, c> hashMap) throws IOException {
            if (this.f) {
                a(hashMap);
            }
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void a(c cVar) {
            this.f = true;
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void a(c cVar, boolean z) {
            this.f = true;
        }

        private boolean b(HashMap<String, c> hashMap, SparseArray<String> sparseArray) {
            DataInputStream dataInputStream;
            Throwable th;
            BufferedInputStream bufferedInputStream;
            Throwable e;
            if (!this.e.exists()) {
                return true;
            }
            try {
                bufferedInputStream = new BufferedInputStream(this.e.openRead());
                dataInputStream = new DataInputStream(bufferedInputStream);
            } catch (IOException unused) {
                dataInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                dataInputStream = null;
            }
            try {
                int readInt = dataInputStream.readInt();
                if (readInt >= 0 && readInt <= 2) {
                    if ((dataInputStream.readInt() & 1) != 0) {
                        if (this.b == null) {
                            Util.closeQuietly(dataInputStream);
                            return false;
                        }
                        byte[] bArr = new byte[16];
                        dataInputStream.readFully(bArr);
                        try {
                            this.b.init(2, (Key) Util.castNonNull(this.c), new IvParameterSpec(bArr));
                            dataInputStream = new DataInputStream(new CipherInputStream(bufferedInputStream, this.b));
                        } catch (InvalidAlgorithmParameterException e2) {
                            e = e2;
                            throw new IllegalStateException(e);
                        } catch (InvalidKeyException e3) {
                            e = e3;
                            throw new IllegalStateException(e);
                        }
                    } else if (this.a) {
                        this.f = true;
                    }
                    int readInt2 = dataInputStream.readInt();
                    int i = 0;
                    for (int i2 = 0; i2 < readInt2; i2++) {
                        c a = a(readInt, dataInputStream);
                        hashMap.put(a.b, a);
                        sparseArray.put(a.a, a.b);
                        i += a(a, readInt);
                    }
                    int readInt3 = dataInputStream.readInt();
                    boolean z = dataInputStream.read() == -1;
                    if (readInt3 != i || !z) {
                        Util.closeQuietly(dataInputStream);
                        return false;
                    }
                    Util.closeQuietly(dataInputStream);
                    return true;
                }
                Util.closeQuietly(dataInputStream);
                return false;
            } catch (IOException unused2) {
                if (dataInputStream != null) {
                    Util.closeQuietly(dataInputStream);
                }
                return false;
            } catch (Throwable th3) {
                th = th3;
                if (dataInputStream != null) {
                    Util.closeQuietly(dataInputStream);
                }
                throw th;
            }
        }

        private void c(HashMap<String, c> hashMap) throws IOException {
            Throwable th;
            DataOutputStream dataOutputStream;
            Throwable e;
            try {
                OutputStream startWrite = this.e.startWrite();
                if (this.g == null) {
                    this.g = new ReusableBufferedOutputStream(startWrite);
                } else {
                    this.g.reset(startWrite);
                }
                ReusableBufferedOutputStream reusableBufferedOutputStream = this.g;
                dataOutputStream = new DataOutputStream(reusableBufferedOutputStream);
                try {
                    dataOutputStream.writeInt(2);
                    int i = 0;
                    dataOutputStream.writeInt(this.a ? 1 : 0);
                    if (this.a) {
                        byte[] bArr = new byte[16];
                        ((SecureRandom) Util.castNonNull(this.d)).nextBytes(bArr);
                        dataOutputStream.write(bArr);
                        try {
                            ((Cipher) Util.castNonNull(this.b)).init(1, (Key) Util.castNonNull(this.c), new IvParameterSpec(bArr));
                            dataOutputStream.flush();
                            dataOutputStream = new DataOutputStream(new CipherOutputStream(reusableBufferedOutputStream, this.b));
                        } catch (InvalidAlgorithmParameterException e2) {
                            e = e2;
                            throw new IllegalStateException(e);
                        } catch (InvalidKeyException e3) {
                            e = e3;
                            throw new IllegalStateException(e);
                        }
                    }
                    dataOutputStream.writeInt(hashMap.size());
                    for (c cVar : hashMap.values()) {
                        a(cVar, dataOutputStream);
                        i += a(cVar, 2);
                    }
                    dataOutputStream.writeInt(i);
                    this.e.endWrite(dataOutputStream);
                    Util.closeQuietly((Closeable) null);
                } catch (Throwable th2) {
                    th = th2;
                    Util.closeQuietly(dataOutputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                dataOutputStream = null;
            }
        }

        private int a(c cVar, int i) {
            int hashCode = (cVar.a * 31) + cVar.b.hashCode();
            if (i >= 2) {
                return (hashCode * 31) + cVar.a().hashCode();
            }
            long contentLength = ContentMetadata.getContentLength(cVar.a());
            return (hashCode * 31) + ((int) (contentLength ^ (contentLength >>> 32)));
        }

        private c a(int i, DataInputStream dataInputStream) throws IOException {
            DefaultContentMetadata defaultContentMetadata;
            int readInt = dataInputStream.readInt();
            String readUTF = dataInputStream.readUTF();
            if (i < 2) {
                long readLong = dataInputStream.readLong();
                ContentMetadataMutations contentMetadataMutations = new ContentMetadataMutations();
                ContentMetadataMutations.setContentLength(contentMetadataMutations, readLong);
                defaultContentMetadata = DefaultContentMetadata.EMPTY.copyWithMutationsApplied(contentMetadataMutations);
            } else {
                defaultContentMetadata = d.b(dataInputStream);
            }
            return new c(readInt, readUTF, defaultContentMetadata);
        }

        private void a(c cVar, DataOutputStream dataOutputStream) throws IOException {
            dataOutputStream.writeInt(cVar.a);
            dataOutputStream.writeUTF(cVar.b);
            d.b(cVar.a(), dataOutputStream);
        }
    }

    /* compiled from: CachedContentIndex.java */
    /* loaded from: classes2.dex */
    private static final class a implements c {
        private static final String[] a = {"id", "key", PlayerEvent.METADATA};
        private final DatabaseProvider b;
        private final SparseArray<c> c = new SparseArray<>();
        private String d;
        private String e;

        public static void a(DatabaseProvider databaseProvider, long j) throws DatabaseIOException {
            a(databaseProvider, Long.toHexString(j));
        }

        public a(DatabaseProvider databaseProvider) {
            this.b = databaseProvider;
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void a(long j) {
            this.d = Long.toHexString(j);
            this.e = a(this.d);
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public boolean a() throws DatabaseIOException {
            return VersionTable.getVersion(this.b.getReadableDatabase(), 1, (String) Assertions.checkNotNull(this.d)) != -1;
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void b() throws DatabaseIOException {
            a(this.b, (String) Assertions.checkNotNull(this.d));
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void a(HashMap<String, c> hashMap, SparseArray<String> sparseArray) throws IOException {
            Assertions.checkState(this.c.size() == 0);
            try {
                if (VersionTable.getVersion(this.b.getReadableDatabase(), 1, (String) Assertions.checkNotNull(this.d)) != 1) {
                    SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
                    writableDatabase.beginTransactionNonExclusive();
                    a(writableDatabase);
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                }
                Cursor c = c();
                while (c.moveToNext()) {
                    c cVar = new c(c.getInt(0), (String) Assertions.checkNotNull(c.getString(1)), d.b(new DataInputStream(new ByteArrayInputStream(c.getBlob(2)))));
                    hashMap.put(cVar.b, cVar);
                    sparseArray.put(cVar.a, cVar.b);
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLiteException e) {
                hashMap.clear();
                sparseArray.clear();
                throw new DatabaseIOException(e);
            }
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void a(HashMap<String, c> hashMap) throws IOException {
            try {
                SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                a(writableDatabase);
                for (c cVar : hashMap.values()) {
                    a(writableDatabase, cVar);
                }
                writableDatabase.setTransactionSuccessful();
                this.c.clear();
                writableDatabase.endTransaction();
            } catch (SQLException e) {
                throw new DatabaseIOException(e);
            }
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void b(HashMap<String, c> hashMap) throws IOException {
            if (this.c.size() != 0) {
                try {
                    SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
                    writableDatabase.beginTransactionNonExclusive();
                    for (int i = 0; i < this.c.size(); i++) {
                        c valueAt = this.c.valueAt(i);
                        if (valueAt == null) {
                            a(writableDatabase, this.c.keyAt(i));
                        } else {
                            a(writableDatabase, valueAt);
                        }
                    }
                    writableDatabase.setTransactionSuccessful();
                    this.c.clear();
                    writableDatabase.endTransaction();
                } catch (SQLException e) {
                    throw new DatabaseIOException(e);
                }
            }
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void a(c cVar) {
            this.c.put(cVar.a, cVar);
        }

        @Override // com.google.android.exoplayer2.upstream.cache.d.c
        public void a(c cVar, boolean z) {
            if (z) {
                this.c.delete(cVar.a);
            } else {
                this.c.put(cVar.a, null);
            }
        }

        private Cursor c() {
            return this.b.getReadableDatabase().query((String) Assertions.checkNotNull(this.e), a, null, null, null, null, null);
        }

        private void a(SQLiteDatabase sQLiteDatabase) throws DatabaseIOException {
            VersionTable.setVersion(sQLiteDatabase, 1, (String) Assertions.checkNotNull(this.d), 1);
            a(sQLiteDatabase, (String) Assertions.checkNotNull(this.e));
            String str = this.e;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 88);
            sb.append("CREATE TABLE ");
            sb.append(str);
            sb.append(StringUtils.SPACE);
            sb.append("(id INTEGER PRIMARY KEY NOT NULL,key TEXT NOT NULL,metadata BLOB NOT NULL)");
            sQLiteDatabase.execSQL(sb.toString());
        }

        private void a(SQLiteDatabase sQLiteDatabase, int i) {
            sQLiteDatabase.delete((String) Assertions.checkNotNull(this.e), "id = ?", new String[]{Integer.toString(i)});
        }

        private void a(SQLiteDatabase sQLiteDatabase, c cVar) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            d.b(cVar.a(), new DataOutputStream(byteArrayOutputStream));
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Integer.valueOf(cVar.a));
            contentValues.put("key", cVar.b);
            contentValues.put(PlayerEvent.METADATA, byteArray);
            sQLiteDatabase.replaceOrThrow((String) Assertions.checkNotNull(this.e), null, contentValues);
        }

        private static void a(DatabaseProvider databaseProvider, String str) throws DatabaseIOException {
            try {
                String a2 = a(str);
                SQLiteDatabase writableDatabase = databaseProvider.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                VersionTable.removeVersion(writableDatabase, 1, str);
                a(writableDatabase, a2);
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            } catch (SQLException e) {
                throw new DatabaseIOException(e);
            }
        }

        private static void a(SQLiteDatabase sQLiteDatabase, String str) {
            String valueOf = String.valueOf(str);
            sQLiteDatabase.execSQL(valueOf.length() != 0 ? "DROP TABLE IF EXISTS ".concat(valueOf) : new String("DROP TABLE IF EXISTS "));
        }

        private static String a(String str) {
            String valueOf = String.valueOf("ExoPlayerCacheIndex");
            String valueOf2 = String.valueOf(str);
            return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        }
    }
}
