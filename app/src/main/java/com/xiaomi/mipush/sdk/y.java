package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.umeng.analytics.pro.ai;
import com.xiaomi.accountsdk.account.data.DevInfoKeys;
import com.xiaomi.push.az;
import com.xiaomi.push.i;
import com.xiaomi.push.l;
import java.util.HashMap;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes4.dex */
class y {
    public static HashMap<String, String> a(Context context, String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            hashMap.put("appToken", d.m727a(context).b());
            hashMap.put("regId", MiPushClient.getRegId(context));
            hashMap.put("appId", d.m727a(context).m728a());
            hashMap.put("regResource", d.m727a(context).e());
            if (!l.d()) {
                String g = i.g(context);
                if (!TextUtils.isEmpty(g)) {
                    hashMap.put("imeiMd5", az.a(g));
                }
            }
            hashMap.put("isMIUI", String.valueOf(l.m1113a()));
            hashMap.put("miuiVersion", l.m1111a());
            hashMap.put(DevInfoKeys.DEVICEID, i.a(context, true));
            hashMap.put("model", Build.MODEL);
            hashMap.put(IChannel.EXTRA_OPEN_PKG_NAME, context.getPackageName());
            hashMap.put("sdkVersion", "3_6_19");
            hashMap.put("androidVersion", String.valueOf(Build.VERSION.SDK_INT));
            hashMap.put(ai.x, Build.VERSION.RELEASE + Constants.ACCEPT_TIME_SEPARATOR_SERVER + Build.VERSION.INCREMENTAL);
            hashMap.put("andId", i.e(context));
            if (!TextUtils.isEmpty(str)) {
                hashMap.put("clientInterfaceId", str);
            }
        } catch (Throwable unused) {
        }
        return hashMap;
    }
}
