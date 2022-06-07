package com.xiaomi.push.service;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.miplay.mylibrary.DataModel;
import com.xiaomi.push.hh;
import com.xiaomi.push.hl;
import com.xiaomi.push.hu;
import com.xiaomi.push.id;
import com.xiaomi.push.ig;
import com.xiaomi.push.ir;
import com.xiaomi.push.service.XMPushService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
class bj extends XMPushService.i {
    final /* synthetic */ String b;
    final /* synthetic */ List c;
    final /* synthetic */ String d;
    final /* synthetic */ i e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bj(i iVar, int i, String str, List list, String str2) {
        super(i);
        this.e = iVar;
        this.b = str;
        this.c = list;
        this.d = str2;
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a */
    public String mo1167a() {
        return "Send tiny data.";
    }

    @Override // com.xiaomi.push.service.XMPushService.i
    /* renamed from: a  reason: collision with other method in class */
    public void mo1167a() {
        String a;
        XMPushService xMPushService;
        a = this.e.a(this.b);
        ArrayList<ig> a2 = be.a(this.c, this.b, a, 32768);
        b.m149a("TinyData LongConnUploader.upload pack notifications " + a2.toString() + "  ts:" + System.currentTimeMillis());
        if (a2 != null) {
            Iterator<ig> it = a2.iterator();
            while (it.hasNext()) {
                ig next = it.next();
                next.a("uploadWay", "longXMPushService");
                id a3 = bq.a(this.b, a, next, hh.Notification);
                if (!TextUtils.isEmpty(this.d) && !TextUtils.equals(this.b, this.d)) {
                    if (a3.m1021a() == null) {
                        hu huVar = new hu();
                        huVar.a(DataModel.CIRCULATEFAIL_NO_SUPPORT);
                        a3.a(huVar);
                    }
                    a3.m1021a().b("ext_traffic_source_pkg", this.d);
                }
                byte[] a4 = ir.a(a3);
                xMPushService = this.e.a;
                xMPushService.a(this.b, a4, true);
            }
            Iterator it2 = this.c.iterator();
            while (it2.hasNext()) {
                b.m149a("TinyData LongConnUploader.upload uploaded by com.xiaomi.push.service.TinyDataUploader.  item" + ((hl) it2.next()).d() + "  ts:" + System.currentTimeMillis());
            }
            return;
        }
        b.d("TinyData LongConnUploader.upload Get a null XmPushActionNotification list when TinyDataHelper.pack() in XMPushService.");
    }
}
