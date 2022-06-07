package com.xiaomi.push;

/* loaded from: classes4.dex */
public class ac {
    public static final String a;

    /* renamed from: a  reason: collision with other field name */
    public static final boolean f6a;
    public static final boolean b;
    public static final boolean c;
    public static final boolean d;
    public static boolean e;
    public static final boolean f;
    public static final boolean g;
    private static int h;

    static {
        int i;
        a = af.f7a ? "ONEBOX" : "@SHIP.TO.2A2FE0D7@";
        f6a = a.contains("2A2FE0D7");
        boolean z = false;
        b = f6a || "DEBUG".equalsIgnoreCase(a);
        c = "LOGABLE".equalsIgnoreCase(a);
        d = a.contains("YY");
        e = a.equalsIgnoreCase("TEST");
        f = "BETA".equalsIgnoreCase(a);
        String str = a;
        if (str != null && str.startsWith("RC")) {
            z = true;
        }
        g = z;
        h = 1;
        if (a.equalsIgnoreCase("SANDBOX")) {
            i = 2;
        } else if (a.equalsIgnoreCase("ONEBOX")) {
            i = 3;
        } else {
            h = 1;
            return;
        }
        h = i;
    }

    public static int a() {
        return h;
    }

    public static void a(int i) {
        h = i;
    }

    /* renamed from: a  reason: collision with other method in class */
    public static boolean m754a() {
        return h == 2;
    }

    public static boolean b() {
        return h == 3;
    }
}
