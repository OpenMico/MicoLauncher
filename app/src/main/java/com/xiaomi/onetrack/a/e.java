package com.xiaomi.onetrack.a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* loaded from: classes4.dex */
public class e extends SQLiteOpenHelper {
    public static final String a = "one_track_cloud";
    public static final String b = "events_cloud";
    public static final String c = "_id";
    public static final String d = "app_id";
    public static final String e = "cloud_data";
    public static final String f = "data_hash";
    public static final String g = "timestamp";
    static final String h = "CREATE TABLE events_cloud (_id  INTEGER PRIMARY KEY AUTOINCREMENT,app_id TEXT,cloud_data TEXT,timestamp INTEGER,data_hash TEXT)";
    private static final String i = "ConfigDatabaseHelper";
    private static final int j = 1;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }

    public e(Context context) {
        super(context, a, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(h);
        Log.d(i, "onCreate: ");
    }
}
