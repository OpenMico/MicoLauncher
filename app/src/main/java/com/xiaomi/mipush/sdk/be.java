package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ae;
import com.xiaomi.push.aj;
import com.xiaomi.push.az;
import com.xiaomi.push.hm;
import com.xiaomi.push.ig;
import com.xiaomi.push.service.ag;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class be {
    public static void a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        long j = sharedPreferences.getLong("last_sync_info", -1L);
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        long a = ag.a(context).a(hm.SyncInfoFrequency.a(), 1209600);
        if (j != -1) {
            if (Math.abs(currentTimeMillis - j) > a) {
                a(context, true);
            } else {
                return;
            }
        }
        sharedPreferences.edit().putLong("last_sync_info", currentTimeMillis).commit();
    }

    public static void a(Context context, ig igVar) {
        b.m149a("need to update local info with: " + igVar.m1036a());
        String str = igVar.m1036a().get(Constants.EXTRA_KEY_ACCEPT_TIME);
        if (str != null) {
            MiPushClient.d(context);
            String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            if (split.length == 2) {
                MiPushClient.a(context, split[0], split[1]);
                if (!"00:00".equals(split[0]) || !"00:00".equals(split[1])) {
                    d.m727a(context).a(false);
                } else {
                    d.m727a(context).a(true);
                }
            }
        }
        String str2 = igVar.m1036a().get(Constants.EXTRA_KEY_ALIASES);
        if (str2 != null) {
            MiPushClient.a(context);
            if (!"".equals(str2)) {
                for (String str3 : str2.split(Constants.ACCEPT_TIME_SEPARATOR_SP)) {
                    MiPushClient.a(context, str3);
                }
            }
        }
        String str4 = igVar.m1036a().get(Constants.EXTRA_KEY_TOPICS);
        if (str4 != null) {
            MiPushClient.c(context);
            if (!"".equals(str4)) {
                for (String str5 : str4.split(Constants.ACCEPT_TIME_SEPARATOR_SP)) {
                    MiPushClient.e(context, str5);
                }
            }
        }
        String str6 = igVar.m1036a().get(Constants.EXTRA_KEY_ACCOUNTS);
        if (str6 != null) {
            MiPushClient.b(context);
            if (!"".equals(str6)) {
                for (String str7 : str6.split(Constants.ACCEPT_TIME_SEPARATOR_SP)) {
                    MiPushClient.c(context, str7);
                }
            }
        }
    }

    public static void a(Context context, boolean z) {
        aj.a(context).a(new al(context, z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(List<String> list) {
        String a = az.a(d(list));
        return (TextUtils.isEmpty(a) || a.length() <= 4) ? "" : a.substring(0, 4).toLowerCase();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String d(List<String> list) {
        if (ae.a(list)) {
            return "";
        }
        ArrayList<String> arrayList = new ArrayList(list);
        Collections.sort(arrayList, Collator.getInstance(Locale.CHINA));
        String str = "";
        for (String str2 : arrayList) {
            if (!TextUtils.isEmpty(str)) {
                str = str + Constants.ACCEPT_TIME_SEPARATOR_SP;
            }
            str = str + str2;
        }
        return str;
    }
}
