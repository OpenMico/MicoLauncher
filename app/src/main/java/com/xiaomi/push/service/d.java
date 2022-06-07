package com.xiaomi.push.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.push.fg;
import com.xiaomi.push.gc;
import com.xiaomi.push.gd;
import com.xiaomi.push.ge;
import com.xiaomi.push.gg;
import com.xiaomi.push.l;
import com.xiaomi.push.service.al;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class d {
    private p a = new p();

    public static String a(al.b bVar) {
        StringBuilder sb;
        String str;
        if (!Commands.ResolutionValues.BITSTREAM_4K.equals(bVar.g)) {
            sb = new StringBuilder();
            sb.append(bVar.f188a);
            str = ".permission.MIPUSH_RECEIVE";
        } else {
            sb = new StringBuilder();
            sb.append(bVar.f188a);
            str = ".permission.MIMC_RECEIVE";
        }
        sb.append(str);
        return sb.toString();
    }

    private static void a(Context context, Intent intent, al.b bVar) {
        if ("com.xiaomi.xmsf".equals(context.getPackageName())) {
            context.sendBroadcast(intent);
        } else {
            context.sendBroadcast(intent, a(bVar));
        }
    }

    al.b a(fg fgVar) {
        Collection<al.b> a = al.a().a(Integer.toString(fgVar.a()));
        if (a.isEmpty()) {
            return null;
        }
        Iterator<al.b> it = a.iterator();
        if (a.size() == 1) {
            return it.next();
        }
        String g = fgVar.g();
        while (it.hasNext()) {
            al.b next = it.next();
            if (TextUtils.equals(g, next.b)) {
                return next;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    com.xiaomi.push.service.al.b a(com.xiaomi.push.ge r6) {
        /*
            r5 = this;
            com.xiaomi.push.service.al r0 = com.xiaomi.push.service.al.a()
            java.lang.String r1 = r6.k()
            java.util.Collection r0 = r0.a(r1)
            boolean r1 = r0.isEmpty()
            r2 = 0
            if (r1 == 0) goto L_0x0014
            return r2
        L_0x0014:
            java.util.Iterator r1 = r0.iterator()
            int r0 = r0.size()
            r3 = 1
            if (r0 != r3) goto L_0x0026
            java.lang.Object r6 = r1.next()
            com.xiaomi.push.service.al$b r6 = (com.xiaomi.push.service.al.b) r6
            return r6
        L_0x0026:
            java.lang.String r0 = r6.m()
            java.lang.String r6 = r6.l()
        L_0x002e:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x004b
            java.lang.Object r3 = r1.next()
            com.xiaomi.push.service.al$b r3 = (com.xiaomi.push.service.al.b) r3
            java.lang.String r4 = r3.b
            boolean r4 = android.text.TextUtils.equals(r0, r4)
            if (r4 != 0) goto L_0x004a
            java.lang.String r4 = r3.b
            boolean r4 = android.text.TextUtils.equals(r6, r4)
            if (r4 == 0) goto L_0x002e
        L_0x004a:
            return r3
        L_0x004b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.d.a(com.xiaomi.push.ge):com.xiaomi.push.service.al$b");
    }

    @SuppressLint({"WrongConstant"})
    public void a(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.service_started");
        if (l.c()) {
            intent.addFlags(16777216);
        }
        context.sendBroadcast(intent);
    }

    public void a(Context context, al.b bVar, int i) {
        if (!Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND.equalsIgnoreCase(bVar.g)) {
            Intent intent = new Intent();
            intent.setAction("com.xiaomi.push.channel_closed");
            intent.setPackage(bVar.f188a);
            intent.putExtra(ap.r, bVar.g);
            intent.putExtra("ext_reason", i);
            intent.putExtra(ap.p, bVar.b);
            intent.putExtra(ap.C, bVar.i);
            if (bVar.l == null || !Commands.ResolutionValues.BITSTREAM_4K.equals(bVar.g)) {
                a(context, intent, bVar);
                return;
            }
            try {
                bVar.l.send(Message.obtain(null, 17, intent));
            } catch (RemoteException unused) {
                bVar.l = null;
                b.m149a("peer may died: " + bVar.b.substring(bVar.b.lastIndexOf(64)));
            }
        }
    }

    public void a(Context context, al.b bVar, String str, String str2) {
        if (Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND.equalsIgnoreCase(bVar.g)) {
            b.d("mipush kicked by server");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.kicked");
        intent.setPackage(bVar.f188a);
        intent.putExtra("ext_kick_type", str);
        intent.putExtra("ext_kick_reason", str2);
        intent.putExtra("ext_chid", bVar.g);
        intent.putExtra(ap.p, bVar.b);
        intent.putExtra(ap.C, bVar.i);
        a(context, intent, bVar);
    }

    public void a(Context context, al.b bVar, boolean z, int i, String str) {
        if (Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND.equalsIgnoreCase(bVar.g)) {
            this.a.a(context, bVar, z, i, str);
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.channel_opened");
        intent.setPackage(bVar.f188a);
        intent.putExtra("ext_succeeded", z);
        if (!z) {
            intent.putExtra("ext_reason", i);
        }
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("ext_reason_msg", str);
        }
        intent.putExtra("ext_chid", bVar.g);
        intent.putExtra(ap.p, bVar.b);
        intent.putExtra(ap.C, bVar.i);
        a(context, intent, bVar);
    }

    public void a(XMPushService xMPushService, String str, fg fgVar) {
        al.b a = a(fgVar);
        if (a == null) {
            b.d("error while notify channel closed! channel " + str + " not registered");
        } else if (Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND.equalsIgnoreCase(str)) {
            this.a.a(xMPushService, fgVar, a);
        } else {
            String str2 = a.f188a;
            Intent intent = new Intent();
            intent.setAction("com.xiaomi.push.new_msg");
            intent.setPackage(str2);
            intent.putExtra("ext_chid", str);
            intent.putExtra("ext_raw_packet", fgVar.m911a(a.h));
            intent.putExtra(ap.C, a.i);
            intent.putExtra(ap.v, a.h);
            if (a.l != null) {
                try {
                    a.l.send(Message.obtain(null, 17, intent));
                    return;
                } catch (RemoteException unused) {
                    a.l = null;
                    b.m149a("peer may died: " + a.b.substring(a.b.lastIndexOf(64)));
                }
            }
            if (!"com.xiaomi.xmsf".equals(str2)) {
                a(xMPushService, intent, a);
            }
        }
    }

    public void a(XMPushService xMPushService, String str, ge geVar) {
        String str2;
        String str3;
        al.b a = a(geVar);
        if (a == null) {
            str3 = "error while notify channel closed! channel " + str + " not registered";
        } else if (Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND.equalsIgnoreCase(str)) {
            this.a.a(xMPushService, geVar, a);
            return;
        } else {
            String str4 = a.f188a;
            if (geVar instanceof gd) {
                str2 = "com.xiaomi.push.new_msg";
            } else if (geVar instanceof gc) {
                str2 = "com.xiaomi.push.new_iq";
            } else if (geVar instanceof gg) {
                str2 = "com.xiaomi.push.new_pres";
            } else {
                str3 = "unknown packet type, drop it";
            }
            Intent intent = new Intent();
            intent.setAction(str2);
            intent.setPackage(str4);
            intent.putExtra("ext_chid", str);
            intent.putExtra("ext_packet", geVar.mo941a());
            intent.putExtra(ap.C, a.i);
            intent.putExtra(ap.v, a.h);
            a(xMPushService, intent, a);
            return;
        }
        b.d(str3);
    }
}
