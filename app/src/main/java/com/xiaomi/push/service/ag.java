package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ae;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ag {
    private static volatile ag b;
    protected SharedPreferences a;
    private HashSet<a> c = new HashSet<>();

    /* loaded from: classes4.dex */
    public static abstract class a implements Runnable {
        private int a;
        private String b;

        public a(int i, String str) {
            this.a = i;
            this.b = str;
        }

        protected abstract void a();

        public boolean equals(Object obj) {
            return (obj instanceof a) && this.a == ((a) obj).a;
        }

        public int hashCode() {
            return this.a;
        }

        @Override // java.lang.Runnable
        public final void run() {
            a();
        }
    }

    private ag(Context context) {
        this.a = context.getSharedPreferences("mipush_oc", 0);
    }

    public static ag a(Context context) {
        if (b == null) {
            synchronized (ag.class) {
                if (b == null) {
                    b = new ag(context);
                }
            }
        }
        return b;
    }

    private String a(int i) {
        return "normal_oc_" + i;
    }

    private void a(SharedPreferences.Editor editor, Pair<Integer, Object> pair, String str) {
        if (pair.second instanceof Integer) {
            editor.putInt(str, ((Integer) pair.second).intValue());
        } else if (pair.second instanceof Long) {
            editor.putLong(str, ((Long) pair.second).longValue());
        } else if (pair.second instanceof String) {
            editor.putString(str, (String) pair.second);
        } else if (pair.second instanceof Boolean) {
            editor.putBoolean(str, ((Boolean) pair.second).booleanValue());
        }
    }

    private String b(int i) {
        return "custom_oc_" + i;
    }

    public int a(int i, int i2) {
        String b2 = b(i);
        if (this.a.contains(b2)) {
            return this.a.getInt(b2, 0);
        }
        String a2 = a(i);
        return this.a.contains(a2) ? this.a.getInt(a2, 0) : i2;
    }

    public synchronized void a() {
        this.c.clear();
    }

    public synchronized void a(a aVar) {
        if (!this.c.contains(aVar)) {
            this.c.add(aVar);
        }
    }

    public void a(List<Pair<Integer, Object>> list) {
        if (!ae.a(list)) {
            SharedPreferences.Editor edit = this.a.edit();
            for (Pair<Integer, Object> pair : list) {
                if (!(pair.first == null || pair.second == null)) {
                    a(edit, pair, a(((Integer) pair.first).intValue()));
                }
            }
            edit.commit();
        }
    }

    public boolean a(int i, boolean z) {
        String b2 = b(i);
        if (this.a.contains(b2)) {
            return this.a.getBoolean(b2, false);
        }
        String a2 = a(i);
        return this.a.contains(a2) ? this.a.getBoolean(a2, false) : z;
    }

    public void b() {
        b.c("OC_Callback : receive new oc data");
        HashSet hashSet = new HashSet();
        synchronized (this) {
            hashSet.addAll(this.c);
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            a aVar = (a) it.next();
            if (aVar != null) {
                aVar.run();
            }
        }
        hashSet.clear();
    }

    public void b(List<Pair<Integer, Object>> list) {
        if (!ae.a(list)) {
            SharedPreferences.Editor edit = this.a.edit();
            for (Pair<Integer, Object> pair : list) {
                if (pair.first != null) {
                    String b2 = b(((Integer) pair.first).intValue());
                    if (pair.second == null) {
                        edit.remove(b2);
                    } else {
                        a(edit, pair, b2);
                    }
                }
            }
            edit.commit();
        }
    }
}
