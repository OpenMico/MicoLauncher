package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.aw;
import com.xiaomi.push.h;
import com.xiaomi.push.hh;
import com.xiaomi.push.hw;
import com.xiaomi.push.hx;
import com.xiaomi.push.hy;
import com.xiaomi.push.ic;
import com.xiaomi.push.id;
import com.xiaomi.push.ig;
import com.xiaomi.push.ii;
import com.xiaomi.push.ij;
import com.xiaomi.push.ik;
import com.xiaomi.push.im;
import com.xiaomi.push.io;
import com.xiaomi.push.iq;
import com.xiaomi.push.ir;
import com.xiaomi.push.is;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class ar {
    /* JADX INFO: Access modifiers changed from: protected */
    public static <T extends is<T, ?>> id a(Context context, T t, hh hhVar) {
        return a(context, t, hhVar, !hhVar.equals(hh.Registration), context.getPackageName(), d.m727a(context).m728a());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static <T extends is<T, ?>> id a(Context context, T t, hh hhVar, boolean z, String str, String str2) {
        String str3;
        byte[] a = ir.a(t);
        if (a == null) {
            str3 = "invoke convertThriftObjectToBytes method, return null.";
        } else {
            id idVar = new id();
            if (z) {
                String d = d.m727a(context).d();
                if (TextUtils.isEmpty(d)) {
                    str3 = "regSecret is empty, return null";
                } else {
                    try {
                        a = h.b(aw.a(d), a);
                    } catch (Exception unused) {
                        b.d("encryption error. ");
                    }
                }
            }
            hw hwVar = new hw();
            hwVar.a = 5L;
            hwVar.f90a = "fakeid";
            idVar.a(hwVar);
            idVar.a(ByteBuffer.wrap(a));
            idVar.a(hhVar);
            idVar.b(true);
            idVar.b(str);
            idVar.a(z);
            idVar.a(str2);
            return idVar;
        }
        b.m149a(str3);
        return null;
    }

    public static is a(Context context, id idVar) {
        byte[] bArr;
        if (idVar.m1029b()) {
            try {
                bArr = h.a(aw.a(d.m727a(context).d()), idVar.m1027a());
            } catch (Exception e) {
                throw new v("the aes decrypt failed.", e);
            }
        } else {
            bArr = idVar.m1027a();
        }
        is a = a(idVar.a(), idVar.f124b);
        if (a != null) {
            ir.a(a, bArr);
        }
        return a;
    }

    private static is a(hh hhVar, boolean z) {
        switch (ad.a[hhVar.ordinal()]) {
            case 1:
                return new ii();
            case 2:
                return new io();
            case 3:
                return new im();
            case 4:
                return new iq();
            case 5:
                return new ik();
            case 6:
                return new hx();
            case 7:
                return new ic();
            case 8:
                return new ij();
            case 9:
                if (z) {
                    return new ig();
                }
                hy hyVar = new hy();
                hyVar.a(true);
                return hyVar;
            case 10:
                return new ic();
            default:
                return null;
        }
    }
}
