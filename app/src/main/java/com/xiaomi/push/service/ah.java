package com.xiaomi.push.service;

import android.util.Pair;
import com.xiaomi.push.Cif;
import com.xiaomi.push.ae;
import com.xiaomi.push.hn;
import com.xiaomi.push.ho;
import com.xiaomi.push.hq;
import com.xiaomi.push.hs;
import com.xiaomi.push.ie;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ah {
    public static int a(ag agVar, hn hnVar) {
        String a = a(hnVar);
        int i = 0;
        switch (j.a[hnVar.ordinal()]) {
            case 1:
                i = 1;
                break;
        }
        return agVar.a.getInt(a, i);
    }

    private static String a(hn hnVar) {
        return "oc_version_" + hnVar.a();
    }

    private static List<Pair<Integer, Object>> a(List<hs> list, boolean z) {
        Pair pair;
        if (ae.a(list)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (hs hsVar : list) {
            int a = hsVar.a();
            ho a2 = ho.a(hsVar.b());
            if (a2 != null) {
                if (!z || !hsVar.f71a) {
                    switch (j.b[a2.ordinal()]) {
                        case 1:
                            pair = new Pair(Integer.valueOf(a), Integer.valueOf(hsVar.c()));
                            break;
                        case 2:
                            pair = new Pair(Integer.valueOf(a), Long.valueOf(hsVar.m973a()));
                            break;
                        case 3:
                            pair = new Pair(Integer.valueOf(a), hsVar.m974a());
                            break;
                        case 4:
                            pair = new Pair(Integer.valueOf(a), Boolean.valueOf(hsVar.g()));
                            break;
                        default:
                            pair = null;
                            break;
                    }
                    arrayList.add(pair);
                } else {
                    arrayList.add(new Pair(Integer.valueOf(a), null));
                }
            }
        }
        return arrayList;
    }

    public static void a(ag agVar, hn hnVar, int i) {
        agVar.a.edit().putInt(a(hnVar), i).commit();
    }

    public static void a(ag agVar, ie ieVar) {
        agVar.b(a(ieVar.a(), true));
        agVar.b();
    }

    public static void a(ag agVar, Cif ifVar) {
        for (hq hqVar : ifVar.a()) {
            if (hqVar.a() > a(agVar, hqVar.m969a())) {
                a(agVar, hqVar.m969a(), hqVar.a());
                agVar.a(a(hqVar.f66a, false));
            }
        }
        agVar.b();
    }
}
