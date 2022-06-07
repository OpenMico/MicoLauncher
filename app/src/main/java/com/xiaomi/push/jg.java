package com.xiaomi.push;

/* loaded from: classes4.dex */
public class jg {
    private static int a = Integer.MAX_VALUE;

    public static void a(jd jdVar, byte b) {
        a(jdVar, b, a);
    }

    public static void a(jd jdVar, byte b, int i) {
        if (i > 0) {
            int i2 = 0;
            switch (b) {
                case 2:
                    jdVar.m1104a();
                    return;
                case 3:
                    jdVar.mo1106a();
                    return;
                case 4:
                    jdVar.a();
                    return;
                case 5:
                case 7:
                case 9:
                default:
                    return;
                case 6:
                    jdVar.m1102a();
                    return;
                case 8:
                    jdVar.m1093a();
                    return;
                case 10:
                    jdVar.m1094a();
                    return;
                case 11:
                    jdVar.m1101a();
                    return;
                case 12:
                    jdVar.m1099a();
                    while (true) {
                        ja a2 = jdVar.m1095a();
                        if (a2.a == 0) {
                            jdVar.f();
                            return;
                        } else {
                            a(jdVar, a2.a, i - 1);
                            jdVar.g();
                        }
                    }
                case 13:
                    jc a3 = jdVar.m1097a();
                    while (i2 < a3.f182a) {
                        int i3 = i - 1;
                        a(jdVar, a3.a, i3);
                        a(jdVar, a3.b, i3);
                        i2++;
                    }
                    jdVar.h();
                    return;
                case 14:
                    jh a4 = jdVar.m1098a();
                    while (i2 < a4.f183a) {
                        a(jdVar, a4.a, i - 1);
                        i2++;
                    }
                    jdVar.j();
                    return;
                case 15:
                    jb a5 = jdVar.m1096a();
                    while (i2 < a5.f181a) {
                        a(jdVar, a5.a, i - 1);
                        i2++;
                    }
                    jdVar.i();
                    return;
            }
        } else {
            throw new ix("Maximum skip depth exceeded");
        }
    }
}
