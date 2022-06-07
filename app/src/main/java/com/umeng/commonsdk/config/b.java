package com.umeng.commonsdk.config;

import java.util.HashMap;
import java.util.Map;

/* compiled from: CollectController.java */
/* loaded from: classes2.dex */
public class b implements f {
    private static Map<String, Boolean> a = new HashMap();
    private static Object b = new Object();

    private b() {
    }

    public void a() {
        synchronized (b) {
            a.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: CollectController.java */
    /* loaded from: classes2.dex */
    public static class a {
        private static final b a = new b();

        private a() {
        }
    }

    public static b b() {
        return a.a;
    }

    public static boolean a(String str) {
        if (!d.a(str)) {
            return false;
        }
        synchronized (b) {
            if (!a.containsKey(str)) {
                return true;
            }
            return a.get(str).booleanValue();
        }
    }

    @Override // com.umeng.commonsdk.config.f
    public void a(String str, Boolean bool) {
        if (d.a(str)) {
            synchronized (b) {
                if (a != null) {
                    a.put(str, bool);
                }
            }
        }
    }
}
