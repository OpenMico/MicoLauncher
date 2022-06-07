package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;

/* loaded from: classes4.dex */
public class db {
    public static int a(Context context, int i) {
        int a = gs.a(context);
        if (-1 == a) {
            return -1;
        }
        return (i * (a == 0 ? 13 : 11)) / 10;
    }

    public static int a(hh hhVar) {
        return ev.a(hhVar.a());
    }

    public static int a(is isVar, hh hhVar) {
        int a;
        switch (cs.a[hhVar.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                return ev.a(hhVar.a());
            case 11:
                a = ev.a(hhVar.a());
                if (isVar != null) {
                    try {
                        if (isVar instanceof hy) {
                            String str = ((hy) isVar).d;
                            if (!TextUtils.isEmpty(str) && ev.a(ev.m895a(str)) != -1) {
                                a = ev.a(ev.m895a(str));
                                break;
                            }
                        } else if (isVar instanceof ig) {
                            String str2 = ((ig) isVar).d;
                            if (!TextUtils.isEmpty(str2)) {
                                if (ev.a(ev.m895a(str2)) != -1) {
                                    a = ev.a(ev.m895a(str2));
                                }
                                if (hr.UploadTinyData.equals(ev.m895a(str2))) {
                                    return -1;
                                }
                            }
                        }
                    } catch (Exception unused) {
                        b.d("PERF_ERROR : parse Notification type error");
                        return a;
                    }
                }
                break;
            case 12:
                a = ev.a(hhVar.a());
                if (isVar != null) {
                    try {
                        if (isVar instanceof ic) {
                            String a2 = ((ic) isVar).a();
                            if (!TextUtils.isEmpty(a2) && fb.a(a2) != -1) {
                                a = fb.a(a2);
                                break;
                            }
                        } else if (isVar instanceof ib) {
                            String a3 = ((ib) isVar).a();
                            if (!TextUtils.isEmpty(a3) && fb.a(a3) != -1) {
                                return fb.a(a3);
                            }
                        }
                    } catch (Exception unused2) {
                        b.d("PERF_ERROR : parse Command type error");
                        break;
                    }
                }
                break;
            default:
                return -1;
        }
        return a;
    }

    public static void a(String str, Context context, int i, int i2) {
        if (i > 0 && i2 > 0) {
            int a = a(context, i2);
            if (i != ev.a(hr.UploadTinyData)) {
                ew.a(context.getApplicationContext()).a(str, i, 1L, a);
            }
        }
    }

    public static void a(String str, Context context, id idVar, int i) {
        hh a;
        byte[] a2;
        if (context != null && idVar != null && (a = idVar.a()) != null) {
            int a3 = a(a);
            i = 0;
            if (i <= 0 && (a2 = ir.a(idVar)) != null) {
                i = a2.length;
            }
            a(str, context, a3, i);
        }
    }

    public static void a(String str, Context context, is isVar, hh hhVar, int i) {
        a(str, context, a(isVar, hhVar), i);
    }

    public static void a(String str, Context context, byte[] bArr) {
        if (context != null && bArr != null && bArr.length > 0) {
            id idVar = new id();
            try {
                ir.a(idVar, bArr);
                a(str, context, idVar, bArr.length);
            } catch (ix unused) {
                b.m149a("fail to convert bytes to container");
            }
        }
    }
}
