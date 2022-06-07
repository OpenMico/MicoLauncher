package com.xiaomi.push.service;

import android.content.Context;
import android.os.Messenger;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.db;
import com.xiaomi.push.fg;
import com.xiaomi.push.fn;
import com.xiaomi.push.fy;
import com.xiaomi.push.ge;
import com.xiaomi.push.hh;
import com.xiaomi.push.hw;
import com.xiaomi.push.id;
import com.xiaomi.push.ig;
import com.xiaomi.push.ir;
import com.xiaomi.push.is;
import com.xiaomi.push.ix;
import com.xiaomi.push.service.al;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public final class bq {
    static fg a(XMPushService xMPushService, byte[] bArr) {
        id idVar = new id();
        try {
            ir.a(idVar, bArr);
            return a(l.a((Context) xMPushService), xMPushService, idVar);
        } catch (ix e) {
            b.a(e);
            return null;
        }
    }

    static fg a(k kVar, Context context, id idVar) {
        try {
            fg fgVar = new fg();
            fgVar.a(5);
            fgVar.c(kVar.f190a);
            fgVar.b(a(idVar));
            fgVar.a("SECMSG", "message");
            String str = kVar.f190a;
            idVar.f119a.f90a = str.substring(0, str.indexOf("@"));
            idVar.f119a.c = str.substring(str.indexOf("/") + 1);
            fgVar.a(ir.a(idVar), kVar.c);
            fgVar.a((short) 1);
            b.m149a("try send mi push message. packagename:" + idVar.b + " action:" + idVar.a);
            return fgVar;
        } catch (NullPointerException e) {
            b.a(e);
            return null;
        }
    }

    public static id a(String str, String str2) {
        ig igVar = new ig();
        igVar.b(str2);
        igVar.c("package uninstalled");
        igVar.a(ge.i());
        igVar.a(false);
        return a(str, str2, igVar, hh.Notification);
    }

    public static <T extends is<T, ?>> id a(String str, String str2, T t, hh hhVar) {
        byte[] a = ir.a(t);
        id idVar = new id();
        hw hwVar = new hw();
        hwVar.a = 5L;
        hwVar.f90a = "fakeid";
        idVar.a(hwVar);
        idVar.a(ByteBuffer.wrap(a));
        idVar.a(hhVar);
        idVar.b(true);
        idVar.b(str);
        idVar.a(false);
        idVar.a(str2);
        return idVar;
    }

    private static String a(id idVar) {
        if (!(idVar.f118a == null || idVar.f118a.f86b == null)) {
            String str = idVar.f118a.f86b.get("ext_traffic_source_pkg");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return idVar.b;
    }

    public static String a(String str) {
        return str + ".permission.MIPUSH_RECEIVE";
    }

    public static void a(XMPushService xMPushService) {
        k a = l.a(xMPushService.getApplicationContext());
        if (a != null) {
            al.b a2 = l.a(xMPushService.getApplicationContext()).a(xMPushService);
            a(xMPushService, a2);
            al.a().a(a2);
            bc.a(xMPushService).a(new br("GAID", 172800L, xMPushService, a));
        }
    }

    public static void a(XMPushService xMPushService, id idVar) {
        db.a(idVar.b(), xMPushService.getApplicationContext(), idVar, -1);
        fn a = xMPushService.a();
        if (a == null) {
            throw new fy("try send msg while connection is null.");
        } else if (a.m917a()) {
            fg a2 = a(l.a((Context) xMPushService), xMPushService, idVar);
            if (a2 != null) {
                a.b(a2);
            }
        } else {
            throw new fy("Don't support XMPP connection.");
        }
    }

    public static void a(XMPushService xMPushService, al.b bVar) {
        bVar.a((Messenger) null);
        bVar.a(new bs(xMPushService));
    }

    public static void a(XMPushService xMPushService, String str, byte[] bArr) {
        db.a(str, xMPushService.getApplicationContext(), bArr);
        fn a = xMPushService.a();
        if (a == null) {
            throw new fy("try send msg while connection is null.");
        } else if (a.m917a()) {
            fg a2 = a(xMPushService, bArr);
            if (a2 != null) {
                a.b(a2);
            } else {
                o.a(xMPushService, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, "not a valid message");
            }
        } else {
            throw new fy("Don't support XMPP connection.");
        }
    }
}
