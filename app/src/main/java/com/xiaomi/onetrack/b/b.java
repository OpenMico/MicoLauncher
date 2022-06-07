package com.xiaomi.onetrack.b;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.a.l;
import com.xiaomi.onetrack.c.c;
import com.xiaomi.onetrack.c.d;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.p;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class b {
    private static final String a = "EventManager";
    private static final boolean b = false;
    private static final int c = 204800;
    private static final int d = 307200;
    private static final int e = 300;
    private static final String f = "priority ASC, _id ASC";
    private static final int g = 7;
    private static b h;
    private static BroadcastReceiver j = new c();
    private a i = new a(com.xiaomi.onetrack.e.a.a());

    public static b a() {
        if (h == null) {
            a(com.xiaomi.onetrack.e.a.a());
        }
        return h;
    }

    public static void a(Context context) {
        if (h == null) {
            synchronized (b.class) {
                if (h == null) {
                    h = new b();
                }
            }
        }
        b(context);
    }

    private b() {
        b();
    }

    private static void b(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(j, intentFilter);
    }

    public synchronized void a(com.xiaomi.onetrack.e.b bVar) {
        a.a(new d(this, bVar));
    }

    public void b(com.xiaomi.onetrack.e.b bVar) {
        synchronized (this.i) {
            if (!bVar.h()) {
                p.c(a, "addEventToDatabase event is inValid, event:" + bVar.d());
                return;
            }
            SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("appid", bVar.b());
            contentValues.put("package", bVar.c());
            contentValues.put(a.f, bVar.d());
            contentValues.put("priority", Integer.valueOf(bVar.e()));
            contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
            byte[] a2 = a(bVar.f().toString());
            if (a2.length > c) {
                p.b(a, "Too large data, discard ***");
                return;
            }
            contentValues.put("data", a2);
            long insert = writableDatabase.insert("events", null, contentValues);
            p.a(a, "DB-Thread: EventManager.addEventToDatabase , row=" + insert);
            if (insert != -1) {
                if (p.a) {
                    p.a(a, "添加后，DB 中事件个数为 " + c());
                }
                long currentTimeMillis = System.currentTimeMillis();
                if (com.xiaomi.onetrack.util.a.c.equals(bVar.d())) {
                    aa.a(currentTimeMillis);
                }
                l.a(false);
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 10, insn: 0x0139: MOVE  (r2 I:??[OBJECT, ARRAY]) = (r10 I:??[OBJECT, ARRAY]), block:B:63:0x0139
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    public com.xiaomi.onetrack.b.f a(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 10, insn: 0x0139: MOVE  (r2 I:??[OBJECT, ARRAY]) = (r10 I:??[OBJECT, ARRAY]), block:B:63:0x0139
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r21v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    public int a(ArrayList<Long> arrayList) {
        synchronized (this.i) {
            if (arrayList != null) {
                if (arrayList.size() != 0) {
                    try {
                        SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
                        boolean z = true;
                        StringBuilder sb = new StringBuilder(((Long.toString(arrayList.get(0).longValue()).length() + 1) * arrayList.size()) + 16);
                        sb.append("_id");
                        sb.append(" in (");
                        sb.append(arrayList.get(0));
                        int size = arrayList.size();
                        for (int i = 1; i < size; i++) {
                            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                            sb.append(arrayList.get(i));
                        }
                        sb.append(")");
                        int delete = writableDatabase.delete("events", sb.toString(), null);
                        p.a(a, "deleted events count " + delete);
                        long c2 = a().c();
                        if (c2 != 0) {
                            z = false;
                        }
                        l.a(z);
                        p.a(a, "after delete DB record remains=" + c2);
                        return delete;
                    } catch (Exception e2) {
                        p.b(a, "e=" + e2);
                        return 0;
                    }
                }
            }
            return 0;
        }
    }

    public void b() {
        a.a(new e(this));
    }

    public long c() {
        return DatabaseUtils.queryNumEntries(this.i.getReadableDatabase(), "events");
    }

    public static byte[] a(String str) {
        return com.xiaomi.onetrack.c.a.a(str.getBytes(), d.a(c.a(), true).getBytes());
    }

    public static String a(byte[] bArr) {
        return new String(com.xiaomi.onetrack.c.a.b(bArr, d.a(c.a(), true).getBytes()));
    }

    private void d() {
        try {
            this.i.getWritableDatabase().delete("events", null, null);
            p.a(a, "delete table events");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* loaded from: classes4.dex */
    public static class a extends SQLiteOpenHelper {
        public static final String a = "onetrack";
        public static final String b = "events";
        public static final String c = "_id";
        public static final String d = "appid";
        public static final String e = "package";
        public static final String f = "event_name";
        public static final String g = "priority";
        public static final String h = "data";
        public static final String i = "timestamp";
        private static final int j = 1;
        private static final String k = "CREATE TABLE events (_id INTEGER PRIMARY KEY AUTOINCREMENT,appid TEXT,package TEXT,event_name TEXT,priority INTEGER,data BLOB,timestamp INTEGER)";

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        }

        public a(Context context) {
            super(context, a, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(k);
        }
    }
}
