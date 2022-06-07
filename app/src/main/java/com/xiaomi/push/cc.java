package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import java.text.SimpleDateFormat;

/* loaded from: classes4.dex */
public class cc {
    private static SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
    private static String b = a.format(Long.valueOf(System.currentTimeMillis()));

    public static hl a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        hl hlVar = new hl();
        hlVar.d("category_push_stat");
        hlVar.a("push_sdk_stat_channel");
        hlVar.a(1L);
        hlVar.b(str);
        hlVar.a(true);
        hlVar.b(System.currentTimeMillis());
        hlVar.g(bl.a(context).a());
        hlVar.e("com.xiaomi.xmsf");
        hlVar.f("");
        hlVar.c("push_stat");
        return hlVar;
    }
}
