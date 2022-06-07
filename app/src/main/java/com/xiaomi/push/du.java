package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.umeng.analytics.pro.c;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.passport.StatConstants;
import com.xiaomi.push.service.f;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class du implements eo {
    private void a(Context context, String str) {
        String str2;
        String str3;
        try {
            if (TextUtils.isEmpty(str) || context == null) {
                str2 = c.M;
                str3 = "B get a incorrect message";
            } else {
                String[] split = str.split("/");
                if (split.length <= 0 || TextUtils.isEmpty(split[split.length - 1])) {
                    str2 = c.M;
                    str3 = "B get a incorrect message";
                } else {
                    String str4 = split[split.length - 1];
                    if (TextUtils.isEmpty(str4)) {
                        eh.a(context, c.M, 1008, "B get a incorrect message");
                        return;
                    }
                    String decode = Uri.decode(str4);
                    if (TextUtils.isEmpty(decode)) {
                        eh.a(context, c.M, 1008, "B get a incorrect message");
                        return;
                    }
                    String b = eg.b(decode);
                    if (!TextUtils.isEmpty(b)) {
                        eh.a(context, b, 1007, "play with provider successfully");
                        return;
                    } else {
                        str2 = c.M;
                        str3 = "B get a incorrect message";
                    }
                }
            }
            eh.a(context, str2, 1008, str3);
        } catch (Exception e) {
            eh.a(context, c.M, 1008, "B meet a exception" + e.getMessage());
        }
    }

    private void a(Context context, String str, String str2) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (TextUtils.isEmpty(str2)) {
                eh.a(context, c.M, 1008, "argument error");
            } else {
                eh.a(context, str2, 1008, "argument error");
            }
        } else if (!f.b(context, str)) {
            eh.a(context, str2, 1003, "B is not ready");
        } else {
            eh.a(context, str2, 1002, "B is ready");
            eh.a(context, str2, 1004, "A is ready");
            String a = eg.a(str2);
            try {
                if (!TextUtils.isEmpty(a)) {
                    String type = context.getContentResolver().getType(eg.a(str, a));
                    if (TextUtils.isEmpty(type) || !StatConstants.BIND_SUCCESS.equals(type)) {
                        eh.a(context, str2, 1008, "A is fail to help B's provider");
                        return;
                    }
                    eh.a(context, str2, 1005, "A is successful");
                    eh.a(context, str2, 1006, "The job is finished");
                    return;
                }
                eh.a(context, str2, 1008, "info is empty");
            } catch (Exception e) {
                b.a(e);
                eh.a(context, str2, 1008, "A meet a exception when help B's provider");
            }
        }
    }

    @Override // com.xiaomi.push.eo
    public void a(Context context, Intent intent, String str) {
        a(context, str);
    }

    @Override // com.xiaomi.push.eo
    public void a(Context context, ek ekVar) {
        if (ekVar != null) {
            a(context, ekVar.b(), ekVar.d());
        } else {
            eh.a(context, c.M, 1008, "A receive incorrect message");
        }
    }
}
