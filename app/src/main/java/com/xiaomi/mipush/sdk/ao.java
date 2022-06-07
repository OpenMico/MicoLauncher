package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ao {
    private static volatile ao a;
    private Context b;
    private List<h> c = new ArrayList();

    private ao(Context context) {
        this.b = context.getApplicationContext();
        if (this.b == null) {
            this.b = context;
        }
    }

    public static ao a(Context context) {
        if (a == null) {
            synchronized (ao.class) {
                if (a == null) {
                    a = new ao(context);
                }
            }
        }
        return a;
    }

    public int a(String str) {
        synchronized (this.c) {
            h hVar = new h();
            hVar.b = str;
            if (this.c.contains(hVar)) {
                for (h hVar2 : this.c) {
                    if (hVar2.equals(hVar)) {
                        return hVar2.a;
                    }
                }
            }
            return 0;
        }
    }

    public synchronized String a(bd bdVar) {
        return this.b.getSharedPreferences("mipush_extra", 0).getString(bdVar.name(), "");
    }

    public synchronized void a(bd bdVar, String str) {
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("mipush_extra", 0);
        sharedPreferences.edit().putString(bdVar.name(), str).commit();
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m722a(String str) {
        synchronized (this.c) {
            h hVar = new h();
            hVar.a = 0;
            hVar.b = str;
            if (this.c.contains(hVar)) {
                this.c.remove(hVar);
            }
            this.c.add(hVar);
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m723a(String str) {
        synchronized (this.c) {
            h hVar = new h();
            hVar.b = str;
            return this.c.contains(hVar);
        }
    }

    public void b(String str) {
        synchronized (this.c) {
            h hVar = new h();
            hVar.b = str;
            if (this.c.contains(hVar)) {
                Iterator<h> it = this.c.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    h next = it.next();
                    if (hVar.equals(next)) {
                        hVar = next;
                        break;
                    }
                }
            }
            hVar.a++;
            this.c.remove(hVar);
            this.c.add(hVar);
        }
    }

    public void c(String str) {
        synchronized (this.c) {
            h hVar = new h();
            hVar.b = str;
            if (this.c.contains(hVar)) {
                this.c.remove(hVar);
            }
        }
    }
}
