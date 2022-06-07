package com.xiaomi.onetrack.a;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.onetrack.util.p;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class f {
    private static final String a = "ConfigDbManager";
    private static final int e = 100;
    private e b;
    private ConcurrentHashMap<String, j> c;
    private AtomicBoolean d;

    /* synthetic */ f(g gVar) {
        this();
    }

    private f() {
        this.c = new ConcurrentHashMap<>();
        this.d = new AtomicBoolean(false);
        this.b = new e(com.xiaomi.onetrack.e.a.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class a {
        private static final f a = new f(null);

        private a() {
        }
    }

    public static f a() {
        return a.a;
    }

    public void a(ArrayList<j> arrayList) {
        com.xiaomi.onetrack.b.a.a(new g(this, arrayList));
    }

    public void b(ArrayList<j> arrayList) {
        Throwable th;
        Exception e2;
        StringBuilder sb;
        String str;
        Exception e3;
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                sQLiteDatabase = this.b.getWritableDatabase();
            } catch (Exception e4) {
                e3 = e4;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            sQLiteDatabase.beginTransaction();
            Iterator<j> it = arrayList.iterator();
            while (it.hasNext()) {
                j next = it.next();
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_id", next.a);
                contentValues.put("timestamp", Long.valueOf(next.c));
                if (next.e != null) {
                    contentValues.put(e.e, next.e.toString());
                }
                contentValues.put(e.f, next.d);
                if (DatabaseUtils.queryNumEntries(sQLiteDatabase, e.b, "app_id=?", new String[]{next.a}) > 0) {
                    sQLiteDatabase.update(e.b, contentValues, "app_id=?", new String[]{next.a});
                } else {
                    sQLiteDatabase.insert(e.b, null, contentValues);
                }
                this.d.set(true);
            }
            sQLiteDatabase.setTransactionSuccessful();
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.endTransaction();
                } catch (Exception e5) {
                    e2 = e5;
                    str = a;
                    sb = new StringBuilder();
                    sb.append("Exception while endTransaction:");
                    sb.append(e2);
                    p.b(str, sb.toString());
                }
            }
        } catch (Exception e6) {
            e3 = e6;
            sQLiteDatabase = sQLiteDatabase;
            p.b(a, "updateToDb error: ", e3);
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.endTransaction();
                } catch (Exception e7) {
                    e2 = e7;
                    str = a;
                    sb = new StringBuilder();
                    sb.append("Exception while endTransaction:");
                    sb.append(e2);
                    p.b(str, sb.toString());
                }
            }
        } catch (Throwable th3) {
            th = th3;
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.endTransaction();
                } catch (Exception e8) {
                    p.b(a, "Exception while endTransaction:" + e8);
                }
            }
            throw th;
        }
    }

    public void a(String str) {
        FutureTask futureTask = new FutureTask(new h(this, str), null);
        com.xiaomi.onetrack.b.a.a(futureTask);
        try {
            futureTask.get();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void b(String str) {
        FutureTask futureTask = new FutureTask(new i(this, str));
        com.xiaomi.onetrack.b.a.a(futureTask);
        try {
            j jVar = (j) futureTask.get(5L, TimeUnit.SECONDS);
            if (jVar != null) {
                this.c.put(str, jVar);
                this.d.set(false);
                if (p.a) {
                    p.a(a, "getConfig   appId :" + str + " config: " + jVar.toString());
                }
            }
        } catch (Exception e2) {
            p.b(a, "getConfig error: " + e2.toString());
        }
    }

    public boolean a(String str, String str2, String str3, boolean z) {
        try {
            JSONObject c = c(str, str2);
            if (c != null) {
                return c.getBoolean(str3);
            }
            p.a(a, "config not available, use default value");
            return z;
        } catch (Exception e2) {
            p.b(a, "getBoolean: " + e2.toString());
            return z;
        }
    }

    public String a(String str, String str2, String str3, String str4) {
        try {
            JSONObject c = c(str, str2);
            if (c != null) {
                return c.getString(str3);
            }
            p.a(a, "config not available, use default value");
            return str4;
        } catch (Exception e2) {
            p.b(a, "getString: " + e2.toString());
            return str4;
        }
    }

    public int a(String str, String str2, String str3, int i) {
        try {
            JSONObject c = c(str, str2);
            if (c != null) {
                return c.getInt(str3);
            }
            p.a(a, "config not available, use default value");
            return i;
        } catch (Exception e2) {
            p.b(a, "getInt: " + e2.toString());
            return i;
        }
    }

    public long a(String str, String str2, String str3, long j) {
        try {
            JSONObject c = c(str, str2);
            if (c != null) {
                return c.getLong(str3);
            }
            p.a(a, "config not available, use default value");
            return j;
        } catch (Exception e2) {
            p.b(a, "getLong: " + e2.toString());
            return j;
        }
    }

    public double a(String str, String str2, String str3, double d) {
        try {
            JSONObject c = c(str, str2);
            if (c != null) {
                return c.getDouble(str3);
            }
            p.a(a, "config not available, use default value");
            return d;
        } catch (Exception e2) {
            p.b(a, "getDouble: " + e2.toString());
            return d;
        }
    }

    private JSONObject c(String str, String str2) {
        JSONArray optJSONArray;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            if (this.c.get(str) == null || this.d.get()) {
                b(str);
            }
            j jVar = this.c.get(str);
            if (!(jVar == null || jVar.e == null || (optJSONArray = jVar.e.optJSONArray("events")) == null)) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject jSONObject = optJSONArray.getJSONObject(i);
                    if (TextUtils.equals(str2, jSONObject.optString("event"))) {
                        if (p.a) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("getEventConfig:");
                            sb.append(jSONObject != null ? jSONObject.toString() : "");
                            p.a(a, sb.toString());
                        }
                        return jSONObject;
                    }
                }
            }
        } catch (Exception e2) {
            Log.e(a, "getEventConfig error: " + e2.toString());
        }
        return null;
    }

    public String c(String str) {
        j d = d(str);
        return d != null ? d.d : "";
    }

    public boolean a(String str, String str2) {
        try {
            j d = d(str);
            if (d == null || d.e == null || !d.e.has(str2)) {
                return false;
            }
            return d.e.optBoolean(str2);
        } catch (Exception e2) {
            p.b(a, "getAppLevelBoolean" + e2.toString());
            return false;
        }
    }

    public static int b(JSONObject jSONObject) {
        try {
            int optInt = jSONObject.optInt(a.e, 100);
            if (optInt < 0 || optInt > 100) {
                return 100;
            }
            return optInt;
        } catch (Exception e2) {
            p.a(a, "getCommonSample Exception:" + e2.getMessage());
            return 100;
        }
    }

    public long b(String str, String str2) {
        j jVar;
        if (TextUtils.isEmpty(str)) {
            return 100L;
        }
        try {
            if (this.c.get(str) == null) {
                b(str);
            }
            if (this.c.get(str) != null) {
                int a2 = a(str, str2, a.e, -1);
                if (a2 != -1 || (jVar = this.c.get(str)) == null) {
                    p.a(a, "will return event sample " + a2);
                    return a2;
                }
                p.a(a, "will return common sample " + jVar.b);
                return jVar.b;
            }
        } catch (Exception e2) {
            p.b(a, "getAppEventSample" + e2.toString());
        }
        p.a(a, "will return def sample");
        return 100L;
    }

    public j d(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (this.c.get(str) == null || this.d.get()) {
            b(str);
        }
        return this.c.get(str);
    }
}
