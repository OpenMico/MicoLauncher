package androidx.room.paging;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.paging.PositionalDataSource;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public abstract class LimitOffsetDataSource<T> extends PositionalDataSource<T> {
    private final RoomSQLiteQuery a;
    private final String b;
    private final String c;
    private final RoomDatabase d;
    private final InvalidationTracker.Observer e;
    private final boolean f;
    private final AtomicBoolean g;

    @NonNull
    protected abstract List<T> convertRows(@NonNull Cursor cursor);

    protected LimitOffsetDataSource(@NonNull RoomDatabase roomDatabase, @NonNull SupportSQLiteQuery supportSQLiteQuery, boolean z, @NonNull String... strArr) {
        this(roomDatabase, RoomSQLiteQuery.copyFrom(supportSQLiteQuery), z, strArr);
    }

    protected LimitOffsetDataSource(@NonNull RoomDatabase roomDatabase, @NonNull SupportSQLiteQuery supportSQLiteQuery, boolean z, boolean z2, @NonNull String... strArr) {
        this(roomDatabase, RoomSQLiteQuery.copyFrom(supportSQLiteQuery), z, z2, strArr);
    }

    protected LimitOffsetDataSource(@NonNull RoomDatabase roomDatabase, @NonNull RoomSQLiteQuery roomSQLiteQuery, boolean z, @NonNull String... strArr) {
        this(roomDatabase, roomSQLiteQuery, z, true, strArr);
    }

    protected LimitOffsetDataSource(@NonNull RoomDatabase roomDatabase, @NonNull RoomSQLiteQuery roomSQLiteQuery, boolean z, boolean z2, @NonNull String... strArr) {
        this.g = new AtomicBoolean(false);
        this.d = roomDatabase;
        this.a = roomSQLiteQuery;
        this.f = z;
        this.b = "SELECT COUNT(*) FROM ( " + this.a.getSql() + " )";
        this.c = "SELECT * FROM ( " + this.a.getSql() + " ) LIMIT ? OFFSET ?";
        this.e = new InvalidationTracker.Observer(strArr) { // from class: androidx.room.paging.LimitOffsetDataSource.1
            @Override // androidx.room.InvalidationTracker.Observer
            public void onInvalidated(@NonNull Set<String> set) {
                LimitOffsetDataSource.this.invalidate();
            }
        };
        if (z2) {
            a();
        }
    }

    private void a() {
        if (this.g.compareAndSet(false, true)) {
            this.d.getInvalidationTracker().addWeakObserver(this.e);
        }
    }

    public int countItems() {
        a();
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(this.b, this.a.getArgCount());
        acquire.copyArgumentsFrom(this.a);
        Cursor query = this.d.query(acquire);
        try {
            if (query.moveToFirst()) {
                return query.getInt(0);
            }
            return 0;
        } finally {
            query.close();
            acquire.release();
        }
    }

    public boolean isInvalid() {
        a();
        this.d.getInvalidationTracker().refreshVersionsSync();
        return LimitOffsetDataSource.super.isInvalid();
    }

    public void loadInitial(@NonNull PositionalDataSource.LoadInitialParams loadInitialParams, @NonNull PositionalDataSource.LoadInitialCallback<T> loadInitialCallback) {
        Throwable th;
        RoomSQLiteQuery roomSQLiteQuery;
        List<T> list;
        int i;
        a();
        List<T> emptyList = Collections.emptyList();
        this.d.beginTransaction();
        Cursor cursor = null;
        try {
            int countItems = countItems();
            if (countItems != 0) {
                i = computeInitialLoadPosition(loadInitialParams, countItems);
                roomSQLiteQuery = a(i, computeInitialLoadSize(loadInitialParams, i, countItems));
                try {
                    cursor = this.d.query(roomSQLiteQuery);
                    list = convertRows(cursor);
                    this.d.setTransactionSuccessful();
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    this.d.endTransaction();
                    if (roomSQLiteQuery != null) {
                        roomSQLiteQuery.release();
                    }
                    throw th;
                }
            } else {
                i = 0;
                list = emptyList;
                roomSQLiteQuery = null;
            }
            if (cursor != null) {
                cursor.close();
            }
            this.d.endTransaction();
            if (roomSQLiteQuery != null) {
                roomSQLiteQuery.release();
            }
            loadInitialCallback.onResult(list, i, countItems);
        } catch (Throwable th3) {
            th = th3;
            roomSQLiteQuery = null;
        }
    }

    public void loadRange(@NonNull PositionalDataSource.LoadRangeParams loadRangeParams, @NonNull PositionalDataSource.LoadRangeCallback<T> loadRangeCallback) {
        loadRangeCallback.onResult(loadRange(loadRangeParams.startPosition, loadRangeParams.loadSize));
    }

    @NonNull
    public List<T> loadRange(int i, int i2) {
        RoomSQLiteQuery a = a(i, i2);
        if (this.f) {
            this.d.beginTransaction();
            Cursor cursor = null;
            try {
                cursor = this.d.query(a);
                List<T> convertRows = convertRows(cursor);
                this.d.setTransactionSuccessful();
                return convertRows;
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                this.d.endTransaction();
                a.release();
            }
        } else {
            Cursor query = this.d.query(a);
            try {
                return convertRows(query);
            } finally {
                query.close();
                a.release();
            }
        }
    }

    private RoomSQLiteQuery a(int i, int i2) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(this.c, this.a.getArgCount() + 2);
        acquire.copyArgumentsFrom(this.a);
        acquire.bindLong(acquire.getArgCount() - 1, i2);
        acquire.bindLong(acquire.getArgCount(), i);
        return acquire;
    }
}
