package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.hh;
import com.xiaomi.push.hr;
import com.xiaomi.push.hu;
import com.xiaomi.push.ig;
import com.xiaomi.push.service.aj;

/* loaded from: classes4.dex */
public class MiPushClient4VR {
    public static void uploadData(Context context, String str) {
        ig igVar = new ig();
        igVar.c(hr.VRUpload.f67a);
        igVar.b(d.m727a(context).m728a());
        igVar.d(context.getPackageName());
        igVar.a("data", str);
        igVar.a(aj.a());
        ay.a(context).a((ay) igVar, hh.Notification, (hu) null);
    }
}
