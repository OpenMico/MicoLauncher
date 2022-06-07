package androidx.room;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.SQLException;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;
import androidx.room.b;
import androidx.room.util.SneakyThrow;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AutoClosingRoomOpenHelper.java */
/* loaded from: classes.dex */
public final class b implements d, SupportSQLiteOpenHelper {
    @NonNull
    private final SupportSQLiteOpenHelper a;
    @NonNull
    private final a b;
    @NonNull
    private final a c;

    public b(@NonNull SupportSQLiteOpenHelper supportSQLiteOpenHelper, @NonNull a aVar) {
        this.a = supportSQLiteOpenHelper;
        this.c = aVar;
        aVar.a(this.a);
        this.b = new a(this.c);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    @Nullable
    public String getDatabaseName() {
        return this.a.getDatabaseName();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    @RequiresApi(api = 16)
    public void setWriteAheadLoggingEnabled(boolean z) {
        this.a.setWriteAheadLoggingEnabled(z);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    @NonNull
    @RequiresApi(api = 24)
    public SupportSQLiteDatabase getWritableDatabase() {
        this.b.a();
        return this.b;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    @NonNull
    @RequiresApi(api = 24)
    public SupportSQLiteDatabase getReadableDatabase() {
        this.b.a();
        return this.b;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            this.b.close();
        } catch (IOException e) {
            SneakyThrow.reThrow(e);
        }
    }

    @NonNull
    public a a() {
        return this.c;
    }

    @Override // androidx.room.d
    @NonNull
    public SupportSQLiteOpenHelper b() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AutoClosingRoomOpenHelper.java */
    /* loaded from: classes.dex */
    public static final class a implements SupportSQLiteDatabase {
        @NonNull
        private final a a;

        public static /* synthetic */ Object b(SupportSQLiteDatabase supportSQLiteDatabase) {
            return null;
        }

        a(@NonNull a aVar) {
            this.a = aVar;
        }

        void a() {
            this.a.a($$Lambda$b$a$tZ9vxAg8UnOZI_rvpJ30sZwExgE.INSTANCE);
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public SupportSQLiteStatement compileStatement(String str) {
            return new C0027b(str, this.a);
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void beginTransaction() {
            try {
                this.a.a().beginTransaction();
            } catch (Throwable th) {
                this.a.b();
                throw th;
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void beginTransactionNonExclusive() {
            try {
                this.a.a().beginTransactionNonExclusive();
            } catch (Throwable th) {
                this.a.b();
                throw th;
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void beginTransactionWithListener(SQLiteTransactionListener sQLiteTransactionListener) {
            try {
                this.a.a().beginTransactionWithListener(sQLiteTransactionListener);
            } catch (Throwable th) {
                this.a.b();
                throw th;
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener sQLiteTransactionListener) {
            try {
                this.a.a().beginTransactionWithListenerNonExclusive(sQLiteTransactionListener);
            } catch (Throwable th) {
                this.a.b();
                throw th;
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void endTransaction() {
            if (this.a.c() != null) {
                try {
                    this.a.c().endTransaction();
                } finally {
                    this.a.b();
                }
            } else {
                throw new IllegalStateException("End transaction called but delegateDb is null");
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void setTransactionSuccessful() {
            SupportSQLiteDatabase c = this.a.c();
            if (c != null) {
                c.setTransactionSuccessful();
                return;
            }
            throw new IllegalStateException("setTransactionSuccessful called but delegateDb is null");
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public boolean inTransaction() {
            if (this.a.c() == null) {
                return false;
            }
            return ((Boolean) this.a.a($$Lambda$TMdjwAyA2HEhVVdu7fycE2v9GyM.INSTANCE)).booleanValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public boolean isDbLockedByCurrentThread() {
            if (this.a.c() == null) {
                return false;
            }
            return ((Boolean) this.a.a($$Lambda$ukE71PYFz2jhrqIgnUMNFkpCDcc.INSTANCE)).booleanValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public boolean yieldIfContendedSafely() {
            return ((Boolean) this.a.a($$Lambda$V7flxCHPcSui801PEVK0MKrvfCU.INSTANCE)).booleanValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public boolean yieldIfContendedSafely(long j) {
            return ((Boolean) this.a.a($$Lambda$V7flxCHPcSui801PEVK0MKrvfCU.INSTANCE)).booleanValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public int getVersion() {
            return ((Integer) this.a.a($$Lambda$XmxrakQ01Bf_oNrrhprBMwPcn80.INSTANCE)).intValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void setVersion(final int i) {
            this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$RHqt2O20YgNsG2gq8H2kkZabhzw
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Object version;
                    version = ((SupportSQLiteDatabase) obj).setVersion(i);
                    return version;
                }
            });
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public long getMaximumSize() {
            return ((Long) this.a.a($$Lambda$yfvpxEUNblARfw2B3344El0ksds.INSTANCE)).longValue();
        }

        public static /* synthetic */ Long b(long j, SupportSQLiteDatabase supportSQLiteDatabase) {
            return Long.valueOf(supportSQLiteDatabase.setMaximumSize(j));
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public long setMaximumSize(final long j) {
            return ((Long) this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$fbydY16XGM2rXwLEPrdZyKR5io4
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Long b;
                    b = b.a.b(j, (SupportSQLiteDatabase) obj);
                    return b;
                }
            })).longValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public long getPageSize() {
            return ((Long) this.a.a($$Lambda$O7EV1lNrU2QrC1Vknty_OHx9nWY.INSTANCE)).longValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void setPageSize(final long j) {
            this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$Hyf4kQ_HsJzdLF3zXBuoiyZYcSk
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Object pageSize;
                    pageSize = ((SupportSQLiteDatabase) obj).setPageSize(j);
                    return pageSize;
                }
            });
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public Cursor query(String str) {
            try {
                return new c(this.a.a().query(str), this.a);
            } catch (Throwable th) {
                this.a.b();
                throw th;
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public Cursor query(String str, Object[] objArr) {
            try {
                return new c(this.a.a().query(str, objArr), this.a);
            } catch (Throwable th) {
                this.a.b();
                throw th;
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public Cursor query(SupportSQLiteQuery supportSQLiteQuery) {
            try {
                return new c(this.a.a().query(supportSQLiteQuery), this.a);
            } catch (Throwable th) {
                this.a.b();
                throw th;
            }
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        @RequiresApi(api = 24)
        public Cursor query(SupportSQLiteQuery supportSQLiteQuery, CancellationSignal cancellationSignal) {
            try {
                return new c(this.a.a().query(supportSQLiteQuery, cancellationSignal), this.a);
            } catch (Throwable th) {
                this.a.b();
                throw th;
            }
        }

        public static /* synthetic */ Long a(String str, int i, ContentValues contentValues, SupportSQLiteDatabase supportSQLiteDatabase) {
            return Long.valueOf(supportSQLiteDatabase.insert(str, i, contentValues));
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public long insert(final String str, final int i, final ContentValues contentValues) throws SQLException {
            return ((Long) this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$H7bPqkDg8AQRMQKIgDrDE7y2Qfw
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Long a;
                    a = b.a.a(str, i, contentValues, (SupportSQLiteDatabase) obj);
                    return a;
                }
            })).longValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public int delete(final String str, final String str2, final Object[] objArr) {
            return ((Integer) this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$_Xps7wzTxv6-KLjwl_bJDQXIdYk
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Integer a;
                    a = b.a.a(str, str2, objArr, (SupportSQLiteDatabase) obj);
                    return a;
                }
            })).intValue();
        }

        public static /* synthetic */ Integer a(String str, String str2, Object[] objArr, SupportSQLiteDatabase supportSQLiteDatabase) {
            return Integer.valueOf(supportSQLiteDatabase.delete(str, str2, objArr));
        }

        public static /* synthetic */ Integer a(String str, int i, ContentValues contentValues, String str2, Object[] objArr, SupportSQLiteDatabase supportSQLiteDatabase) {
            return Integer.valueOf(supportSQLiteDatabase.update(str, i, contentValues, str2, objArr));
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public int update(final String str, final int i, final ContentValues contentValues, final String str2, final Object[] objArr) {
            return ((Integer) this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$IvyE3UwVUF7HYRhuANlXD-eJSgs
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Integer a;
                    a = b.a.a(str, i, contentValues, str2, objArr, (SupportSQLiteDatabase) obj);
                    return a;
                }
            })).intValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void execSQL(final String str) throws SQLException {
            this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$Vb8KVknGz7HigqG1XhhVah7emQs
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Object execSQL;
                    execSQL = ((SupportSQLiteDatabase) obj).execSQL(str);
                    return execSQL;
                }
            });
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void execSQL(final String str, final Object[] objArr) throws SQLException {
            this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$qPGY0VXelfSrXRSb7NqQJS4fJds
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Object execSQL;
                    execSQL = ((SupportSQLiteDatabase) obj).execSQL(str, objArr);
                    return execSQL;
                }
            });
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public boolean isReadOnly() {
            return ((Boolean) this.a.a($$Lambda$FnfrLN73Kb5eEMd_EThzd87ecn4.INSTANCE)).booleanValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public boolean isOpen() {
            SupportSQLiteDatabase c = this.a.c();
            if (c == null) {
                return false;
            }
            return c.isOpen();
        }

        public static /* synthetic */ Boolean b(int i, SupportSQLiteDatabase supportSQLiteDatabase) {
            return Boolean.valueOf(supportSQLiteDatabase.needUpgrade(i));
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public boolean needUpgrade(final int i) {
            return ((Boolean) this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$ZJnrSrOZp8CtlwCpmVEK5ADDltM
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Boolean b;
                    b = b.a.b(i, (SupportSQLiteDatabase) obj);
                    return b;
                }
            })).booleanValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public String getPath() {
            return (String) this.a.a($$Lambda$yKxzlL2CA_3zs224TC80CaPwg4.INSTANCE);
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void setLocale(final Locale locale) {
            this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$f6XiN4YVaMMCAcrq0JcQlAn52H0
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Object locale2;
                    locale2 = ((SupportSQLiteDatabase) obj).setLocale(locale);
                    return locale2;
                }
            });
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void setMaxSqlCacheSize(final int i) {
            this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$yAMrJtVMaWn0yjSJ4ngNQ04Rd-I
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Object maxSqlCacheSize;
                    maxSqlCacheSize = ((SupportSQLiteDatabase) obj).setMaxSqlCacheSize(i);
                    return maxSqlCacheSize;
                }
            });
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        @RequiresApi(api = 16)
        @SuppressLint({"UnsafeNewApiCall"})
        public void setForeignKeyConstraintsEnabled(final boolean z) {
            this.a.a(new Function() { // from class: androidx.room.-$$Lambda$b$a$JH8XwpgC_lUWqxykqD7xmMBMxL0
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Object a;
                    a = b.a.a(z, (SupportSQLiteDatabase) obj);
                    return a;
                }
            });
        }

        public static /* synthetic */ Object a(boolean z, SupportSQLiteDatabase supportSQLiteDatabase) {
            if (Build.VERSION.SDK_INT < 16) {
                return null;
            }
            supportSQLiteDatabase.setForeignKeyConstraintsEnabled(z);
            return null;
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public boolean enableWriteAheadLogging() {
            throw new UnsupportedOperationException("Enable/disable write ahead logging on the OpenHelper instead of on the database directly.");
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public void disableWriteAheadLogging() {
            throw new UnsupportedOperationException("Enable/disable write ahead logging on the OpenHelper instead of on the database directly.");
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        @RequiresApi(api = 16)
        @SuppressLint({"UnsafeNewApiCall"})
        public boolean isWriteAheadLoggingEnabled() {
            return ((Boolean) this.a.a($$Lambda$b$a$alQLSyDUsvGf61xEAQomGDIaqDA.INSTANCE)).booleanValue();
        }

        public static /* synthetic */ Boolean a(SupportSQLiteDatabase supportSQLiteDatabase) {
            if (Build.VERSION.SDK_INT >= 16) {
                return Boolean.valueOf(supportSQLiteDatabase.isWriteAheadLoggingEnabled());
            }
            return false;
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public List<Pair<String, String>> getAttachedDbs() {
            return (List) this.a.a($$Lambda$GfIUxMX6NwQv2yWCkyFsS9ov8.INSTANCE);
        }

        @Override // androidx.sqlite.db.SupportSQLiteDatabase
        public boolean isDatabaseIntegrityOk() {
            return ((Boolean) this.a.a($$Lambda$z_yKiINBCC3WVGnJOjudlDWWfQ.INSTANCE)).booleanValue();
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.a.d();
        }
    }

    /* compiled from: AutoClosingRoomOpenHelper.java */
    /* loaded from: classes.dex */
    private static final class c implements Cursor {
        private final Cursor a;
        private final a b;

        c(Cursor cursor, a aVar) {
            this.a = cursor;
            this.b = aVar;
        }

        @Override // android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.a.close();
            this.b.b();
        }

        @Override // android.database.Cursor
        public boolean isClosed() {
            return this.a.isClosed();
        }

        @Override // android.database.Cursor
        public int getCount() {
            return this.a.getCount();
        }

        @Override // android.database.Cursor
        public int getPosition() {
            return this.a.getPosition();
        }

        @Override // android.database.Cursor
        public boolean move(int i) {
            return this.a.move(i);
        }

        @Override // android.database.Cursor
        public boolean moveToPosition(int i) {
            return this.a.moveToPosition(i);
        }

        @Override // android.database.Cursor
        public boolean moveToFirst() {
            return this.a.moveToFirst();
        }

        @Override // android.database.Cursor
        public boolean moveToLast() {
            return this.a.moveToLast();
        }

        @Override // android.database.Cursor
        public boolean moveToNext() {
            return this.a.moveToNext();
        }

        @Override // android.database.Cursor
        public boolean moveToPrevious() {
            return this.a.moveToPrevious();
        }

        @Override // android.database.Cursor
        public boolean isFirst() {
            return this.a.isFirst();
        }

        @Override // android.database.Cursor
        public boolean isLast() {
            return this.a.isLast();
        }

        @Override // android.database.Cursor
        public boolean isBeforeFirst() {
            return this.a.isBeforeFirst();
        }

        @Override // android.database.Cursor
        public boolean isAfterLast() {
            return this.a.isAfterLast();
        }

        @Override // android.database.Cursor
        public int getColumnIndex(String str) {
            return this.a.getColumnIndex(str);
        }

        @Override // android.database.Cursor
        public int getColumnIndexOrThrow(String str) throws IllegalArgumentException {
            return this.a.getColumnIndexOrThrow(str);
        }

        @Override // android.database.Cursor
        public String getColumnName(int i) {
            return this.a.getColumnName(i);
        }

        @Override // android.database.Cursor
        public String[] getColumnNames() {
            return this.a.getColumnNames();
        }

        @Override // android.database.Cursor
        public int getColumnCount() {
            return this.a.getColumnCount();
        }

        @Override // android.database.Cursor
        public byte[] getBlob(int i) {
            return this.a.getBlob(i);
        }

        @Override // android.database.Cursor
        public String getString(int i) {
            return this.a.getString(i);
        }

        @Override // android.database.Cursor
        public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
            this.a.copyStringToBuffer(i, charArrayBuffer);
        }

        @Override // android.database.Cursor
        public short getShort(int i) {
            return this.a.getShort(i);
        }

        @Override // android.database.Cursor
        public int getInt(int i) {
            return this.a.getInt(i);
        }

        @Override // android.database.Cursor
        public long getLong(int i) {
            return this.a.getLong(i);
        }

        @Override // android.database.Cursor
        public float getFloat(int i) {
            return this.a.getFloat(i);
        }

        @Override // android.database.Cursor
        public double getDouble(int i) {
            return this.a.getDouble(i);
        }

        @Override // android.database.Cursor
        public int getType(int i) {
            return this.a.getType(i);
        }

        @Override // android.database.Cursor
        public boolean isNull(int i) {
            return this.a.isNull(i);
        }

        @Override // android.database.Cursor
        @Deprecated
        public void deactivate() {
            this.a.deactivate();
        }

        @Override // android.database.Cursor
        @Deprecated
        public boolean requery() {
            return this.a.requery();
        }

        @Override // android.database.Cursor
        public void registerContentObserver(ContentObserver contentObserver) {
            this.a.registerContentObserver(contentObserver);
        }

        @Override // android.database.Cursor
        public void unregisterContentObserver(ContentObserver contentObserver) {
            this.a.unregisterContentObserver(contentObserver);
        }

        @Override // android.database.Cursor
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            this.a.registerDataSetObserver(dataSetObserver);
        }

        @Override // android.database.Cursor
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            this.a.unregisterDataSetObserver(dataSetObserver);
        }

        @Override // android.database.Cursor
        public void setNotificationUri(ContentResolver contentResolver, Uri uri) {
            this.a.setNotificationUri(contentResolver, uri);
        }

        @Override // android.database.Cursor
        @RequiresApi(api = 19)
        @SuppressLint({"UnsafeNewApiCall"})
        public Uri getNotificationUri() {
            return this.a.getNotificationUri();
        }

        @Override // android.database.Cursor
        public boolean getWantsAllOnMoveCalls() {
            return this.a.getWantsAllOnMoveCalls();
        }

        @Override // android.database.Cursor
        @RequiresApi(api = 23)
        @SuppressLint({"UnsafeNewApiCall"})
        public void setExtras(Bundle bundle) {
            this.a.setExtras(bundle);
        }

        @Override // android.database.Cursor
        public Bundle getExtras() {
            return this.a.getExtras();
        }

        @Override // android.database.Cursor
        public Bundle respond(Bundle bundle) {
            return this.a.respond(bundle);
        }
    }

    /* compiled from: AutoClosingRoomOpenHelper.java */
    /* renamed from: androidx.room.b$b */
    /* loaded from: classes.dex */
    public static class C0027b implements SupportSQLiteStatement {
        private final String a;
        private final ArrayList<Object> b = new ArrayList<>();
        private final a c;

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
        }

        C0027b(String str, a aVar) {
            this.a = str;
            this.c = aVar;
        }

        private <T> T a(final Function<SupportSQLiteStatement, T> function) {
            return (T) this.c.a(new Function() { // from class: androidx.room.-$$Lambda$b$b$jp9jAR1Xfoi3Cyg08oQ4lrwtHzY
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Object a;
                    a = b.C0027b.this.a(function, (SupportSQLiteDatabase) obj);
                    return a;
                }
            });
        }

        public /* synthetic */ Object a(Function function, SupportSQLiteDatabase supportSQLiteDatabase) {
            SupportSQLiteStatement compileStatement = supportSQLiteDatabase.compileStatement(this.a);
            a(compileStatement);
            return function.apply(compileStatement);
        }

        private void a(SupportSQLiteStatement supportSQLiteStatement) {
            int i = 0;
            while (i < this.b.size()) {
                int i2 = i + 1;
                Object obj = this.b.get(i);
                if (obj == null) {
                    supportSQLiteStatement.bindNull(i2);
                } else if (obj instanceof Long) {
                    supportSQLiteStatement.bindLong(i2, ((Long) obj).longValue());
                } else if (obj instanceof Double) {
                    supportSQLiteStatement.bindDouble(i2, ((Double) obj).doubleValue());
                } else if (obj instanceof String) {
                    supportSQLiteStatement.bindString(i2, (String) obj);
                } else if (obj instanceof byte[]) {
                    supportSQLiteStatement.bindBlob(i2, (byte[]) obj);
                }
                i = i2;
            }
        }

        private void a(int i, Object obj) {
            int i2 = i - 1;
            if (i2 >= this.b.size()) {
                for (int size = this.b.size(); size <= i2; size++) {
                    this.b.add(null);
                }
            }
            this.b.set(i2, obj);
        }

        @Override // androidx.sqlite.db.SupportSQLiteStatement
        public void execute() {
            a($$Lambda$b$b$rs5WyrrcVXI7PEQRiz12GcoY0WM.INSTANCE);
        }

        @Override // androidx.sqlite.db.SupportSQLiteStatement
        public int executeUpdateDelete() {
            return ((Integer) a($$Lambda$1GPSSx1HgDeoXE5nNYZ3T9AckE.INSTANCE)).intValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteStatement
        public long executeInsert() {
            return ((Long) a($$Lambda$N7u9kOTpWTpzDJZkV2DOxmvLbrY.INSTANCE)).longValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteStatement
        public long simpleQueryForLong() {
            return ((Long) a($$Lambda$iYIWeB8Ygvix66FTdLDQBHFb8.INSTANCE)).longValue();
        }

        @Override // androidx.sqlite.db.SupportSQLiteStatement
        public String simpleQueryForString() {
            return (String) a($$Lambda$86MUbrc6uipmvj_wBEBf4Ja9IY.INSTANCE);
        }

        @Override // androidx.sqlite.db.SupportSQLiteProgram
        public void bindNull(int i) {
            a(i, (Object) null);
        }

        @Override // androidx.sqlite.db.SupportSQLiteProgram
        public void bindLong(int i, long j) {
            a(i, Long.valueOf(j));
        }

        @Override // androidx.sqlite.db.SupportSQLiteProgram
        public void bindDouble(int i, double d) {
            a(i, Double.valueOf(d));
        }

        @Override // androidx.sqlite.db.SupportSQLiteProgram
        public void bindString(int i, String str) {
            a(i, str);
        }

        @Override // androidx.sqlite.db.SupportSQLiteProgram
        public void bindBlob(int i, byte[] bArr) {
            a(i, bArr);
        }

        @Override // androidx.sqlite.db.SupportSQLiteProgram
        public void clearBindings() {
            this.b.clear();
        }
    }
}
