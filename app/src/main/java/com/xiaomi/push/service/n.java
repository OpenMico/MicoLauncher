package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.fy;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.al;
import java.io.IOException;
import java.util.Collection;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class n extends XMPushService.i {
    private XMPushService b;
    private byte[] c;
    private String d;
    private String e;
    private String f;

    public n(XMPushService xMPushService, String str, String str2, String str3, byte[] bArr) {
        super(9);
        this.b = xMPushService;
        this.d = str;
        this.c = bArr;
        this.e = str2;
        this.f = str3;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "register app";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        al.b bVar;
        k a = l.a((Context) this.b);
        if (a == null) {
            try {
                a = l.a(this.b, this.d, this.e, this.f);
            } catch (IOException | JSONException e) {
                b.a(e);
            }
        }
        if (a == null) {
            b.d("no account for mipush");
            o.a(this.b, ErrorCode.ERROR_AUTHERICATION_ERROR, "no account.");
            return;
        }
        Collection<al.b> a2 = al.a().a(Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND);
        if (a2.isEmpty()) {
            bVar = a.a(this.b);
            bq.a(this.b, bVar);
            al.a().a(bVar);
        } else {
            bVar = a2.iterator().next();
        }
        if (this.b.c()) {
            try {
                if (bVar.j == al.c.binded) {
                    bq.a(this.b, this.d, this.c);
                } else if (bVar.j == al.c.unbind) {
                    XMPushService xMPushService = this.b;
                    XMPushService xMPushService2 = this.b;
                    xMPushService2.getClass();
                    xMPushService.a(new XMPushService.a(bVar));
                }
            } catch (fy e2) {
                b.a(e2);
                this.b.a(10, e2);
            }
        } else {
            this.b.a(true);
        }
    }
}
