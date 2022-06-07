package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class eh {
    public static void a(Context context, String str, int i, String str2) {
        aj.a(context).a(new dj(context, str, i, str2));
    }

    private static void a(Context context, HashMap<String, String> hashMap) {
        ep a = el.a(context).m892a();
        if (a != null) {
            a.a(context, hashMap);
        }
    }

    private static void b(Context context, HashMap<String, String> hashMap) {
        ep a = el.a(context).m892a();
        if (a != null) {
            a.c(context, hashMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context, String str, int i, String str2) {
        if (context != null && !TextUtils.isEmpty(str)) {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put("awake_info", str);
                hashMap.put("event_type", String.valueOf(i));
                hashMap.put("description", str2);
                switch (el.a(context).a()) {
                    case 1:
                        a(context, hashMap);
                        break;
                    case 3:
                        a(context, hashMap);
                    case 2:
                        c(context, hashMap);
                        break;
                }
                b(context, hashMap);
            } catch (Exception e) {
                b.a(e);
            }
        }
    }

    private static void c(Context context, HashMap<String, String> hashMap) {
        ep a = el.a(context).m892a();
        if (a != null) {
            a.b(context, hashMap);
        }
    }
}
