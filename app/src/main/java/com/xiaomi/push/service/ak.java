package com.xiaomi.push.service;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.micolauncher.common.webview.CommonWebActivity;
import com.xiaomi.push.cr;
import com.xiaomi.push.cv;
import com.xiaomi.push.de;
import com.xiaomi.push.ef;
import com.xiaomi.push.fc;
import com.xiaomi.push.fg;
import com.xiaomi.push.fo;
import com.xiaomi.push.gb;
import com.xiaomi.push.gc;
import com.xiaomi.push.gd;
import com.xiaomi.push.ge;
import com.xiaomi.push.gs;
import com.xiaomi.push.hb;
import com.xiaomi.push.service.al;
import java.util.Date;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes4.dex */
public class ak {
    private XMPushService a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ak(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    private void a(gb gbVar) {
        String c = gbVar.c();
        if (!TextUtils.isEmpty(c)) {
            String[] split = c.split(";");
            cr a = cv.a().a(fo.a(), false);
            if (a != null && split.length > 0) {
                a.a(split);
                this.a.a(20, (Exception) null);
                this.a.a(true);
            }
        }
    }

    private void b(ge geVar) {
        al.b a;
        String l = geVar.l();
        String k = geVar.k();
        if (!TextUtils.isEmpty(l) && !TextUtils.isEmpty(k) && (a = al.a().a(k, l)) != null) {
            gs.a(this.a, a.f188a, gs.a(geVar.m939a()), true, true, System.currentTimeMillis());
        }
    }

    private void c(fg fgVar) {
        al.b a;
        String g = fgVar.g();
        String num = Integer.toString(fgVar.a());
        if (!TextUtils.isEmpty(g) && !TextUtils.isEmpty(num) && (a = al.a().a(num, g)) != null) {
            gs.a(this.a, a.f188a, fgVar.c(), true, true, System.currentTimeMillis());
        }
    }

    public void a(fg fgVar) {
        if (5 != fgVar.a()) {
            c(fgVar);
        }
        try {
            b(fgVar);
        } catch (Exception e) {
            b.a("handle Blob chid = " + fgVar.a() + " cmd = " + fgVar.m907a() + " packetid = " + fgVar.e() + " failure ", e);
        }
    }

    public void a(ge geVar) {
        if (!Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND.equals(geVar.k())) {
            b(geVar);
        }
        String k = geVar.k();
        if (TextUtils.isEmpty(k)) {
            k = "1";
            geVar.l(k);
        }
        if (k.equals("0")) {
            b.m149a("Received wrong packet with chid = 0 : " + geVar.m939a());
        }
        if (geVar instanceof gc) {
            gb a = geVar.a("kick");
            if (a != null) {
                String l = geVar.l();
                String a2 = a.a("type");
                String a3 = a.a(IChannel.EXTRA_CLOSE_REASON);
                b.m149a("kicked by server, chid=" + k + " res=" + al.b.a(l) + " type=" + a2 + " reason=" + a3);
                if ("wait".equals(a2)) {
                    al.b a4 = al.a().a(k, l);
                    if (a4 != null) {
                        this.a.a(a4);
                        a4.a(al.c.unbind, 3, 0, a3, a2);
                        return;
                    }
                    return;
                }
                this.a.a(k, l, 3, a3, a2);
                al.a().m1140a(k, l);
                return;
            }
        } else if (geVar instanceof gd) {
            gd gdVar = (gd) geVar;
            if ("redir".equals(gdVar.b())) {
                gb a5 = gdVar.a("hosts");
                if (a5 != null) {
                    a(a5);
                    return;
                }
                return;
            }
        }
        this.a.b().a(this.a, k, geVar);
    }

    public void b(fg fgVar) {
        StringBuilder sb;
        String a;
        String str;
        int i;
        int i2;
        al.c cVar;
        String a2 = fgVar.m907a();
        if (fgVar.a() != 0) {
            String num = Integer.toString(fgVar.a());
            if ("SECMSG".equals(fgVar.m907a())) {
                if (!fgVar.m909a()) {
                    this.a.b().a(this.a, num, fgVar);
                    return;
                }
                sb = new StringBuilder();
                sb.append("Recv SECMSG errCode = ");
                sb.append(fgVar.b());
                sb.append(" errStr = ");
                a = fgVar.m913c();
            } else if ("BIND".equals(a2)) {
                ef.d a3 = ef.d.a(fgVar.m910a());
                String g = fgVar.g();
                al.b a4 = al.a().a(num, g);
                if (a4 != null) {
                    if (a3.mo888a()) {
                        b.m149a("SMACK: channel bind succeeded, chid=" + fgVar.a());
                        a4.a(al.c.binded, 1, 0, (String) null, (String) null);
                        return;
                    }
                    String a5 = a3.mo888a();
                    if (CommonWebActivity.COMMON_AUTH.equals(a5)) {
                        if ("invalid-sig".equals(a3.mo890b())) {
                            b.m149a("SMACK: bind error invalid-sig token = " + a4.c + " sec = " + a4.h);
                            hb.a(0, fc.BIND_INVALID_SIG.a(), 1, null, 0);
                        }
                        cVar = al.c.unbind;
                        i2 = 1;
                        i = 5;
                    } else if ("cancel".equals(a5)) {
                        cVar = al.c.unbind;
                        i2 = 1;
                        i = 7;
                    } else {
                        if ("wait".equals(a5)) {
                            this.a.a(a4);
                            a4.a(al.c.unbind, 1, 7, a3.mo890b(), a5);
                        }
                        str = "SMACK: channel bind failed, chid=" + num + " reason=" + a3.mo890b();
                        b.m149a(str);
                    }
                    a4.a(cVar, i2, i, a3.mo890b(), a5);
                    al.a().m1140a(num, g);
                    str = "SMACK: channel bind failed, chid=" + num + " reason=" + a3.mo890b();
                    b.m149a(str);
                }
                return;
            } else if ("KICK".equals(a2)) {
                ef.g a6 = ef.g.a(fgVar.m910a());
                String g2 = fgVar.g();
                String a7 = a6.mo888a();
                String b = a6.mo890b();
                b.m149a("kicked by server, chid=" + num + " res= " + al.b.a(g2) + " type=" + a7 + " reason=" + b);
                if ("wait".equals(a7)) {
                    al.b a8 = al.a().a(num, g2);
                    if (a8 != null) {
                        this.a.a(a8);
                        a8.a(al.c.unbind, 3, 0, b, a7);
                        return;
                    }
                    return;
                }
                this.a.a(num, g2, 3, b, a7);
                al.a().m1140a(num, g2);
                return;
            } else {
                return;
            }
        } else if ("PING".equals(a2)) {
            byte[] a9 = fgVar.m910a();
            if (a9 != null && a9.length > 0) {
                ef.j a10 = ef.j.a(a9);
                if (a10.mo890b()) {
                    ba.a().a(a10.mo888a());
                }
            }
            if (!"com.xiaomi.xmsf".equals(this.a.getPackageName())) {
                this.a.e();
            }
            if ("1".equals(fgVar.e())) {
                b.m149a("received a server ping");
            } else {
                hb.b();
            }
            this.a.f();
            return;
        } else if ("SYNC".equals(a2)) {
            if ("CONF".equals(fgVar.m912b())) {
                ba.a().a(ef.b.a(fgVar.m910a()));
                return;
            } else if (TextUtils.equals("U", fgVar.m912b())) {
                ef.k a11 = ef.k.a(fgVar.m910a());
                de.a(this.a).a(a11.mo888a(), a11.mo890b(), new Date(a11.mo888a()), new Date(a11.mo890b()), a11.c() * 1024, a11.e());
                fg fgVar2 = new fg();
                fgVar2.a(0);
                fgVar2.a(fgVar.m907a(), "UCA");
                fgVar2.a(fgVar.e());
                XMPushService xMPushService = this.a;
                xMPushService.a(new u(xMPushService, fgVar2));
                return;
            } else if (TextUtils.equals("P", fgVar.m912b())) {
                ef.i a12 = ef.i.a(fgVar.m910a());
                fg fgVar3 = new fg();
                fgVar3.a(0);
                fgVar3.a(fgVar.m907a(), "PCA");
                fgVar3.a(fgVar.e());
                ef.i iVar = new ef.i();
                if (a12.mo888a()) {
                    iVar.a(a12.mo888a());
                }
                fgVar3.a(iVar.mo888a(), (String) null);
                XMPushService xMPushService2 = this.a;
                xMPushService2.a(new u(xMPushService2, fgVar3));
                sb = new StringBuilder();
                sb.append("ACK msgP: id = ");
                a = fgVar.e();
            } else {
                return;
            }
        } else if ("NOTIFY".equals(fgVar.m907a())) {
            ef.h a13 = ef.h.a(fgVar.m910a());
            sb = new StringBuilder();
            sb.append("notify by server err = ");
            sb.append(a13.c());
            sb.append(" desc = ");
            a = a13.mo888a();
        } else {
            return;
        }
        sb.append(a);
        str = sb.toString();
        b.m149a(str);
    }
}
