package androidx.work.impl.model;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class PreferenceDao_Impl implements PreferenceDao {
    private final RoomDatabase a;
    private final EntityInsertionAdapter<Preference> b;

    public PreferenceDao_Impl(RoomDatabase roomDatabase) {
        this.a = roomDatabase;
        this.b = new EntityInsertionAdapter<Preference>(roomDatabase) { // from class: androidx.work.impl.model.PreferenceDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `Preference` (`key`,`long_value`) VALUES (?,?)";
            }

            /* renamed from: a */
            public void bind(SupportSQLiteStatement supportSQLiteStatement, Preference preference) {
                if (preference.mKey == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, preference.mKey);
                }
                if (preference.mValue == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindLong(2, preference.mValue.longValue());
                }
            }
        };
    }

    @Override // androidx.work.impl.model.PreferenceDao
    public void insertPreference(Preference preference) {
        this.a.assertNotSuspendingTransaction();
        this.a.beginTransaction();
        try {
            this.b.insert((EntityInsertionAdapter<Preference>) preference);
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
        }
    }

    @Override // androidx.work.impl.model.PreferenceDao
    public Long getLongValue(String str) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT long_value FROM Preference where `key`=?", 1);
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        this.a.assertNotSuspendingTransaction();
        Long l = null;
        Cursor query = DBUtil.query(this.a, acquire, false, null);
        try {
            if (query.moveToFirst() && !query.isNull(0)) {
                l = Long.valueOf(query.getLong(0));
            }
            return l;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // androidx.work.impl.model.PreferenceDao
    public LiveData<Long> getObservableLongValue(String str) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT long_value FROM Preference where `key`=?", 1);
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        return this.a.getInvalidationTracker().createLiveData(new String[]{"Preference"}, false, new Callable<Long>() { // from class: androidx.work.impl.model.PreferenceDao_Impl.2
            /* renamed from: a */
            public Long call() throws Exception {
                Long l = null;
                Cursor query = DBUtil.query(PreferenceDao_Impl.this.a, acquire, false, null);
                try {
                    if (query.moveToFirst() && !query.isNull(0)) {
                        l = Long.valueOf(query.getLong(0));
                    }
                    return l;
                } finally {
                    query.close();
                }
            }

            protected void finalize() {
                acquire.release();
            }
        });
    }
}
