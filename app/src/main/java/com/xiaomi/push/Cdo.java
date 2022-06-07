package com.xiaomi.push;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.push.service.f;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.xiaomi.push.do  reason: invalid class name */
/* loaded from: classes4.dex */
public class Cdo implements eo {
    private void a(Activity activity, Intent intent) {
        String stringExtra = intent.getStringExtra("awake_info");
        if (!TextUtils.isEmpty(stringExtra)) {
            String b = eg.b(stringExtra);
            if (!TextUtils.isEmpty(b)) {
                eh.a(activity.getApplicationContext(), b, 1007, "play with activity successfully");
                return;
            }
        }
        eh.a(activity.getApplicationContext(), IDMServer.PERSIST_TYPE_ACTIVITY, 1008, "B get incorrect message");
    }

    private void a(Context context, String str, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            if (TextUtils.isEmpty(str3)) {
                eh.a(context, IDMServer.PERSIST_TYPE_ACTIVITY, 1008, "argument error");
            } else {
                eh.a(context, str3, 1008, "argument error");
            }
        } else if (!f.b(context, str, str2)) {
            eh.a(context, str3, 1003, "B is not ready");
        } else {
            eh.a(context, str3, 1002, "B is ready");
            eh.a(context, str3, 1004, "A is ready");
            Intent intent = new Intent(str2);
            intent.setPackage(str);
            intent.putExtra("awake_info", eg.a(str3));
            intent.addFlags(276824064);
            intent.setAction(str2);
            try {
                context.startActivity(intent);
                eh.a(context, str3, 1005, "A is successful");
                eh.a(context, str3, 1006, "The job is finished");
            } catch (Exception e) {
                b.a(e);
                eh.a(context, str3, 1008, "A meet a exception when help B's activity");
            }
        }
    }

    @Override // com.xiaomi.push.eo
    public void a(Context context, Intent intent, String str) {
        if (context == null || !(context instanceof Activity) || intent == null) {
            eh.a(context, IDMServer.PERSIST_TYPE_ACTIVITY, 1008, "B receive incorrect message");
        } else {
            a((Activity) context, intent);
        }
    }

    @Override // com.xiaomi.push.eo
    public void a(Context context, ek ekVar) {
        if (ekVar != null) {
            a(context, ekVar.a(), ekVar.b(), ekVar.d());
        } else {
            eh.a(context, IDMServer.PERSIST_TYPE_ACTIVITY, 1008, "A receive incorrect message");
        }
    }
}
