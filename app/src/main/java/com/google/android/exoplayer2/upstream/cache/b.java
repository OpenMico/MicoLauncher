package com.google.android.exoplayer2.upstream.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.util.Assertions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/* compiled from: CacheFileMetadataIndex.java */
/* loaded from: classes2.dex */
final class b {
    private static final String[] a = {"name", "length", "last_touch_timestamp"};
    private final DatabaseProvider b;
    private String c;

    @WorkerThread
    public static void a(DatabaseProvider databaseProvider, long j) throws DatabaseIOException {
        String hexString = Long.toHexString(j);
        try {
            String b = b(hexString);
            SQLiteDatabase writableDatabase = databaseProvider.getWritableDatabase();
            writableDatabase.beginTransactionNonExclusive();
            VersionTable.removeVersion(writableDatabase, 2, hexString);
            a(writableDatabase, b);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    public b(DatabaseProvider databaseProvider) {
        this.b = databaseProvider;
    }

    @WorkerThread
    public void a(long j) throws DatabaseIOException {
        try {
            String hexString = Long.toHexString(j);
            this.c = b(hexString);
            if (VersionTable.getVersion(this.b.getReadableDatabase(), 2, hexString) != 1) {
                SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                VersionTable.setVersion(writableDatabase, 2, hexString, 1);
                a(writableDatabase, this.c);
                String str = this.c;
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 108);
                sb.append("CREATE TABLE ");
                sb.append(str);
                sb.append(StringUtils.SPACE);
                sb.append("(name TEXT PRIMARY KEY NOT NULL,length INTEGER NOT NULL,last_touch_timestamp INTEGER NOT NULL)");
                writableDatabase.execSQL(sb.toString());
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            }
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    @WorkerThread
    public Map<String, a> a() throws DatabaseIOException {
        try {
            Cursor b = b();
            HashMap hashMap = new HashMap(b.getCount());
            while (b.moveToNext()) {
                hashMap.put((String) Assertions.checkNotNull(b.getString(0)), new a(b.getLong(1), b.getLong(2)));
            }
            if (b != null) {
                b.close();
            }
            return hashMap;
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    @WorkerThread
    public void a(String str, long j, long j2) throws DatabaseIOException {
        Assertions.checkNotNull(this.c);
        try {
            SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str);
            contentValues.put("length", Long.valueOf(j));
            contentValues.put("last_touch_timestamp", Long.valueOf(j2));
            writableDatabase.replaceOrThrow(this.c, null, contentValues);
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    @WorkerThread
    public void a(String str) throws DatabaseIOException {
        Assertions.checkNotNull(this.c);
        try {
            this.b.getWritableDatabase().delete(this.c, "name = ?", new String[]{str});
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    @WorkerThread
    public void a(Set<String> set) throws DatabaseIOException {
        Assertions.checkNotNull(this.c);
        try {
            SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
            writableDatabase.beginTransactionNonExclusive();
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                writableDatabase.delete(this.c, "name = ?", new String[]{it.next()});
            }
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    private Cursor b() {
        Assertions.checkNotNull(this.c);
        return this.b.getReadableDatabase().query(this.c, a, null, null, null, null, null);
    }

    private static void a(SQLiteDatabase sQLiteDatabase, String str) {
        String valueOf = String.valueOf(str);
        sQLiteDatabase.execSQL(valueOf.length() != 0 ? "DROP TABLE IF EXISTS ".concat(valueOf) : new String("DROP TABLE IF EXISTS "));
    }

    private static String b(String str) {
        String valueOf = String.valueOf("ExoPlayerCacheFileMetadata");
        String valueOf2 = String.valueOf(str);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }
}
