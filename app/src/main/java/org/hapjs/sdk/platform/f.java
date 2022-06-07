package org.hapjs.sdk.platform;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes5.dex */
class f {
    public static void a(Context context, String str) {
        a(context, "platform_config", str);
    }

    public static String a(Context context) {
        return b(context, "platform_config");
    }

    private static void a(Context context, String str, String str2) {
        b(context).edit().putString(str, str2).apply();
    }

    private static String b(Context context, String str) {
        return b(context).getString(str, "");
    }

    private static SharedPreferences b(Context context) {
        return context.getSharedPreferences("hap_platforms", 0);
    }
}
