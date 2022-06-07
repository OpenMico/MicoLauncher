package com.xiaomi.push;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.f;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ej implements eo {
    private void a(Service service, Intent intent) {
        if ("com.xiaomi.mipush.sdk.WAKEUP".equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra("waker_pkgname");
            String stringExtra2 = intent.getStringExtra("awake_info");
            if (TextUtils.isEmpty(stringExtra)) {
                eh.a(service.getApplicationContext(), "service", 1007, "old version message");
            } else if (!TextUtils.isEmpty(stringExtra2)) {
                String b = eg.b(stringExtra2);
                if (!TextUtils.isEmpty(b)) {
                    eh.a(service.getApplicationContext(), b, 1007, "old version message ");
                } else {
                    eh.a(service.getApplicationContext(), "service", 1008, "B get a incorrect message");
                }
            } else {
                eh.a(service.getApplicationContext(), stringExtra, 1007, "play with service ");
            }
        }
    }

    private void a(Context context, String str, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (TextUtils.isEmpty(str3)) {
                eh.a(context, "service", 1008, "argument error");
            } else {
                eh.a(context, str3, 1008, "argument error");
            }
        } else if (!f.a(context, str)) {
            eh.a(context, str3, 1003, "B is not ready");
        } else {
            eh.a(context, str3, 1002, "B is ready");
            eh.a(context, str3, 1004, "A is ready");
            try {
                Intent intent = new Intent();
                intent.setClassName(str, str2);
                intent.setAction("com.xiaomi.mipush.sdk.WAKEUP");
                intent.putExtra("waker_pkgname", context.getPackageName());
                intent.putExtra("awake_info", eg.a(str3));
                if (context.startService(intent) != null) {
                    eh.a(context, str3, 1005, "A is successful");
                    eh.a(context, str3, 1006, "The job is finished");
                    return;
                }
                eh.a(context, str3, 1008, "A is fail to help B's service");
            } catch (Exception e) {
                b.a(e);
                eh.a(context, str3, 1008, "A meet a exception when help B's service");
            }
        }
    }

    @Override // com.xiaomi.push.eo
    public void a(Context context, Intent intent, String str) {
        if (context != null && (context instanceof Service)) {
            a((Service) context, intent);
        }
    }

    @Override // com.xiaomi.push.eo
    public void a(Context context, ek ekVar) {
        if (ekVar != null) {
            a(context, ekVar.a(), ekVar.c(), ekVar.d());
        }
    }
}
