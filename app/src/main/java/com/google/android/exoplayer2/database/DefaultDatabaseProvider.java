package com.google.android.exoplayer2.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* loaded from: classes.dex */
public final class DefaultDatabaseProvider implements DatabaseProvider {
    private final SQLiteOpenHelper a;

    public DefaultDatabaseProvider(SQLiteOpenHelper sQLiteOpenHelper) {
        this.a = sQLiteOpenHelper;
    }

    @Override // com.google.android.exoplayer2.database.DatabaseProvider
    public SQLiteDatabase getWritableDatabase() {
        return this.a.getWritableDatabase();
    }

    @Override // com.google.android.exoplayer2.database.DatabaseProvider
    public SQLiteDatabase getReadableDatabase() {
        return this.a.getReadableDatabase();
    }
}
