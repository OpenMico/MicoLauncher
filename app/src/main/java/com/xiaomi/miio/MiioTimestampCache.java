package com.xiaomi.miio;

import java.util.HashMap;

/* loaded from: classes3.dex */
public class MiioTimestampCache {
    private static HashMap<Long, a> a = new HashMap<>();
    private static int b = 6;

    /* loaded from: classes3.dex */
    static class a {
        public long a;
        public int b;
        public long c;

        a() {
        }
    }

    public static int getTs(long j) {
        if (a.get(Long.valueOf(j)) == null) {
            return -1;
        }
        a aVar = a.get(Long.valueOf(j));
        int currentTimeMillis = (int) ((System.currentTimeMillis() - aVar.c) / 1000);
        if (currentTimeMillis > b) {
            return -1;
        }
        return aVar.b + currentTimeMillis;
    }

    public static void updateTs(long j, int i) {
        a aVar;
        if (a.get(Long.valueOf(j)) != null) {
            aVar = a.get(Long.valueOf(j));
        } else {
            aVar = new a();
        }
        aVar.a = j;
        aVar.b = i;
        aVar.c = System.currentTimeMillis();
        a.put(Long.valueOf(j), aVar);
    }
}
