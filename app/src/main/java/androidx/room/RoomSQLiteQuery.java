package androidx.room;

import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.sqlite.db.SupportSQLiteProgram;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class RoomSQLiteQuery implements SupportSQLiteProgram, SupportSQLiteQuery {
    @VisibleForTesting
    static final TreeMap<Integer, RoomSQLiteQuery> g = new TreeMap<>();
    @VisibleForTesting
    final long[] a;
    @VisibleForTesting
    final double[] b;
    @VisibleForTesting
    final String[] c;
    @VisibleForTesting
    final byte[][] d;
    @VisibleForTesting
    final int e;
    @VisibleForTesting
    int f;
    private volatile String h;
    private final int[] i;

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public static RoomSQLiteQuery copyFrom(SupportSQLiteQuery supportSQLiteQuery) {
        RoomSQLiteQuery acquire = acquire(supportSQLiteQuery.getSql(), supportSQLiteQuery.getArgCount());
        supportSQLiteQuery.bindTo(new SupportSQLiteProgram() { // from class: androidx.room.RoomSQLiteQuery.1
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // androidx.sqlite.db.SupportSQLiteProgram
            public void bindNull(int i) {
                RoomSQLiteQuery.this.bindNull(i);
            }

            @Override // androidx.sqlite.db.SupportSQLiteProgram
            public void bindLong(int i, long j) {
                RoomSQLiteQuery.this.bindLong(i, j);
            }

            @Override // androidx.sqlite.db.SupportSQLiteProgram
            public void bindDouble(int i, double d) {
                RoomSQLiteQuery.this.bindDouble(i, d);
            }

            @Override // androidx.sqlite.db.SupportSQLiteProgram
            public void bindString(int i, String str) {
                RoomSQLiteQuery.this.bindString(i, str);
            }

            @Override // androidx.sqlite.db.SupportSQLiteProgram
            public void bindBlob(int i, byte[] bArr) {
                RoomSQLiteQuery.this.bindBlob(i, bArr);
            }

            @Override // androidx.sqlite.db.SupportSQLiteProgram
            public void clearBindings() {
                RoomSQLiteQuery.this.clearBindings();
            }
        });
        return acquire;
    }

    public static RoomSQLiteQuery acquire(String str, int i) {
        synchronized (g) {
            Map.Entry<Integer, RoomSQLiteQuery> ceilingEntry = g.ceilingEntry(Integer.valueOf(i));
            if (ceilingEntry != null) {
                g.remove(ceilingEntry.getKey());
                RoomSQLiteQuery value = ceilingEntry.getValue();
                value.a(str, i);
                return value;
            }
            RoomSQLiteQuery roomSQLiteQuery = new RoomSQLiteQuery(i);
            roomSQLiteQuery.a(str, i);
            return roomSQLiteQuery;
        }
    }

    private RoomSQLiteQuery(int i) {
        this.e = i;
        int i2 = i + 1;
        this.i = new int[i2];
        this.a = new long[i2];
        this.b = new double[i2];
        this.c = new String[i2];
        this.d = new byte[i2];
    }

    void a(String str, int i) {
        this.h = str;
        this.f = i;
    }

    public void release() {
        synchronized (g) {
            g.put(Integer.valueOf(this.e), this);
            a();
        }
    }

    private static void a() {
        if (g.size() > 15) {
            int size = g.size() - 10;
            Iterator<Integer> it = g.descendingKeySet().iterator();
            while (true) {
                size--;
                if (size > 0) {
                    it.next();
                    it.remove();
                } else {
                    return;
                }
            }
        }
    }

    @Override // androidx.sqlite.db.SupportSQLiteQuery
    public String getSql() {
        return this.h;
    }

    @Override // androidx.sqlite.db.SupportSQLiteQuery
    public int getArgCount() {
        return this.f;
    }

    @Override // androidx.sqlite.db.SupportSQLiteQuery
    public void bindTo(SupportSQLiteProgram supportSQLiteProgram) {
        for (int i = 1; i <= this.f; i++) {
            switch (this.i[i]) {
                case 1:
                    supportSQLiteProgram.bindNull(i);
                    break;
                case 2:
                    supportSQLiteProgram.bindLong(i, this.a[i]);
                    break;
                case 3:
                    supportSQLiteProgram.bindDouble(i, this.b[i]);
                    break;
                case 4:
                    supportSQLiteProgram.bindString(i, this.c[i]);
                    break;
                case 5:
                    supportSQLiteProgram.bindBlob(i, this.d[i]);
                    break;
            }
        }
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindNull(int i) {
        this.i[i] = 1;
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindLong(int i, long j) {
        this.i[i] = 2;
        this.a[i] = j;
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindDouble(int i, double d) {
        this.i[i] = 3;
        this.b[i] = d;
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindString(int i, String str) {
        this.i[i] = 4;
        this.c[i] = str;
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindBlob(int i, byte[] bArr) {
        this.i[i] = 5;
        this.d[i] = bArr;
    }

    public void copyArgumentsFrom(RoomSQLiteQuery roomSQLiteQuery) {
        int argCount = roomSQLiteQuery.getArgCount() + 1;
        System.arraycopy(roomSQLiteQuery.i, 0, this.i, 0, argCount);
        System.arraycopy(roomSQLiteQuery.a, 0, this.a, 0, argCount);
        System.arraycopy(roomSQLiteQuery.c, 0, this.c, 0, argCount);
        System.arraycopy(roomSQLiteQuery.d, 0, this.d, 0, argCount);
        System.arraycopy(roomSQLiteQuery.b, 0, this.b, 0, argCount);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void clearBindings() {
        Arrays.fill(this.i, 1);
        Arrays.fill(this.c, (Object) null);
        Arrays.fill(this.d, (Object) null);
        this.h = null;
    }
}
