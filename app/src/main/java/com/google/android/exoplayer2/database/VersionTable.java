package com.google.android.exoplayer2.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.google.android.exoplayer2.util.Util;
import com.xiaomi.onetrack.api.b;

/* loaded from: classes.dex */
public final class VersionTable {
    public static final int FEATURE_CACHE_CONTENT_METADATA = 1;
    public static final int FEATURE_CACHE_FILE_METADATA = 2;
    public static final int FEATURE_EXTERNAL = 1000;
    public static final int FEATURE_OFFLINE = 0;
    public static final int VERSION_UNSET = -1;

    private VersionTable() {
    }

    public static void setVersion(SQLiteDatabase sQLiteDatabase, int i, String str, int i2) throws DatabaseIOException {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ExoPlayerVersions (feature INTEGER NOT NULL,instance_uid TEXT NOT NULL,version INTEGER NOT NULL,PRIMARY KEY (feature, instance_uid))");
            ContentValues contentValues = new ContentValues();
            contentValues.put(b.n, Integer.valueOf(i));
            contentValues.put("instance_uid", str);
            contentValues.put("version", Integer.valueOf(i2));
            sQLiteDatabase.replaceOrThrow("ExoPlayerVersions", null, contentValues);
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    public static void removeVersion(SQLiteDatabase sQLiteDatabase, int i, String str) throws DatabaseIOException {
        try {
            if (Util.tableExists(sQLiteDatabase, "ExoPlayerVersions")) {
                sQLiteDatabase.delete("ExoPlayerVersions", "feature = ? AND instance_uid = ?", a(i, str));
            }
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    public static int getVersion(SQLiteDatabase sQLiteDatabase, int i, String str) throws DatabaseIOException {
        try {
            if (!Util.tableExists(sQLiteDatabase, "ExoPlayerVersions")) {
                return -1;
            }
            Cursor query = sQLiteDatabase.query("ExoPlayerVersions", new String[]{"version"}, "feature = ? AND instance_uid = ?", a(i, str), null, null, null);
            if (query.getCount() == 0) {
                if (query != null) {
                    query.close();
                }
                return -1;
            }
            query.moveToNext();
            int i2 = query.getInt(0);
            if (query != null) {
                query.close();
            }
            return i2;
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    private static String[] a(int i, String str) {
        return new String[]{Integer.toString(i), str};
    }
}
