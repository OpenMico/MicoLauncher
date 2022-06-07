package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.az;
import com.xiaomi.push.hh;
import com.xiaomi.push.hu;
import com.xiaomi.push.i;
import com.xiaomi.push.ig;
import com.xiaomi.push.l;
import com.xiaomi.push.service.aj;
import java.util.HashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class q implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        Context context;
        Context context2;
        Context context3;
        Context context4;
        Context context5;
        if (!l.d()) {
            context = MiPushClient.b;
            if (i.f(context) != null) {
                ig igVar = new ig();
                context2 = MiPushClient.b;
                igVar.b(d.m727a(context2).m728a());
                igVar.c("client_info_update");
                igVar.a(aj.a());
                igVar.a(new HashMap());
                String str = "";
                context3 = MiPushClient.b;
                String f = i.f(context3);
                if (!TextUtils.isEmpty(f)) {
                    str = str + az.a(f);
                }
                context4 = MiPushClient.b;
                String h = i.h(context4);
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(h)) {
                    str = str + Constants.ACCEPT_TIME_SEPARATOR_SP + h;
                }
                if (!TextUtils.isEmpty(str)) {
                    igVar.m1036a().put(Constants.EXTRA_KEY_IMEI_MD5, str);
                }
                int a = i.a();
                if (a >= 0) {
                    igVar.m1036a().put("space_id", Integer.toString(a));
                }
                context5 = MiPushClient.b;
                ay.a(context5).a((ay) igVar, hh.Notification, false, (hu) null);
            }
        }
    }
}
