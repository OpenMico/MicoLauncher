package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.aj;
import com.xiaomi.push.at;
import com.xiaomi.push.s;
import com.xiaomi.push.service.ag;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class j {
    private static HashMap<String, String> a = new HashMap<>();

    public static MiPushMessage a(String str) {
        MiPushMessage miPushMessage = new MiPushMessage();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("messageId")) {
                    miPushMessage.setMessageId(jSONObject.getString("messageId"));
                }
                if (jSONObject.has("description")) {
                    miPushMessage.setDescription(jSONObject.getString("description"));
                }
                if (jSONObject.has("title")) {
                    miPushMessage.setTitle(jSONObject.getString("title"));
                }
                if (jSONObject.has("content")) {
                    miPushMessage.setContent(jSONObject.getString("content"));
                }
                if (jSONObject.has("passThrough")) {
                    miPushMessage.setPassThrough(jSONObject.getInt("passThrough"));
                }
                if (jSONObject.has("notifyType")) {
                    miPushMessage.setNotifyType(jSONObject.getInt("notifyType"));
                }
                if (jSONObject.has("messageType")) {
                    miPushMessage.setMessageType(jSONObject.getInt("messageType"));
                }
                if (jSONObject.has("alias")) {
                    miPushMessage.setAlias(jSONObject.getString("alias"));
                }
                if (jSONObject.has("topic")) {
                    miPushMessage.setTopic(jSONObject.getString("topic"));
                }
                if (jSONObject.has("user_account")) {
                    miPushMessage.setUserAccount(jSONObject.getString("user_account"));
                }
                if (jSONObject.has("notifyId")) {
                    miPushMessage.setNotifyId(jSONObject.getInt("notifyId"));
                }
                if (jSONObject.has("category")) {
                    miPushMessage.setCategory(jSONObject.getString("category"));
                }
                if (jSONObject.has("isNotified")) {
                    miPushMessage.setNotified(jSONObject.getBoolean("isNotified"));
                }
                if (jSONObject.has("extra")) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("extra");
                    Iterator keys = jSONObject2.keys();
                    HashMap hashMap = new HashMap();
                    while (keys != null && keys.hasNext()) {
                        String str2 = (String) keys.next();
                        hashMap.put(str2, jSONObject2.getString(str2));
                    }
                    if (hashMap.size() > 0) {
                        miPushMessage.setExtra(hashMap);
                    }
                }
            } catch (Exception e) {
                b.d(e.toString());
            }
        }
        return miPushMessage;
    }

    public static PushMessageReceiver a(Context context) {
        ResolveInfo resolveInfo;
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.setPackage(context.getPackageName());
        try {
            List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 32);
            if (queryBroadcastReceivers != null) {
                Iterator<ResolveInfo> it = queryBroadcastReceivers.iterator();
                while (it.hasNext()) {
                    resolveInfo = it.next();
                    if (resolveInfo.activityInfo != null && resolveInfo.activityInfo.packageName.equals(context.getPackageName())) {
                        break;
                    }
                }
            }
            resolveInfo = null;
            if (resolveInfo != null) {
                return (PushMessageReceiver) Class.forName(resolveInfo.activityInfo.name).newInstance();
            }
            return null;
        } catch (Exception e) {
            b.d(e.toString());
            return null;
        }
    }

    public static synchronized String a(Context context, String str) {
        String str2;
        synchronized (j.class) {
            str2 = a.get(str);
            if (TextUtils.isEmpty(str2)) {
                str2 = "";
            }
        }
        return str2;
    }

    public static String a(f fVar) {
        switch (aw.a[fVar.ordinal()]) {
            case 1:
                return "hms_push_token";
            case 2:
                return "fcm_push_token";
            case 3:
                return "cos_push_token";
            case 4:
                return "ftos_push_token";
            default:
                return null;
        }
    }

    public static HashMap<String, String> a(Context context, f fVar) {
        StringBuilder sb;
        ap apVar;
        HashMap<String, String> hashMap = new HashMap<>();
        String a2 = a(fVar);
        if (TextUtils.isEmpty(a2)) {
            return hashMap;
        }
        String str = null;
        ApplicationInfo applicationInfo = null;
        switch (aw.a[fVar.ordinal()]) {
            case 1:
                try {
                    applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                } catch (Exception e) {
                    b.d(e.toString());
                }
                int i = -1;
                if (applicationInfo != null) {
                    i = applicationInfo.metaData.getInt(Constants.HUAWEI_HMS_CLIENT_APPID);
                }
                str = "brand:" + o.a(context).name() + Constants.WAVE_SEPARATOR + "token" + Constants.COLON_SEPARATOR + a(context, a2) + Constants.WAVE_SEPARATOR + "package_name" + Constants.COLON_SEPARATOR + context.getPackageName() + Constants.WAVE_SEPARATOR + "app_id" + Constants.COLON_SEPARATOR + i;
                break;
            case 2:
                sb = new StringBuilder();
                sb.append("brand:");
                apVar = ap.FCM;
                sb.append(apVar.name());
                sb.append(Constants.WAVE_SEPARATOR);
                sb.append("token");
                sb.append(Constants.COLON_SEPARATOR);
                sb.append(a(context, a2));
                sb.append(Constants.WAVE_SEPARATOR);
                sb.append("package_name");
                sb.append(Constants.COLON_SEPARATOR);
                sb.append(context.getPackageName());
                str = sb.toString();
                break;
            case 3:
                sb = new StringBuilder();
                sb.append("brand:");
                apVar = ap.OPPO;
                sb.append(apVar.name());
                sb.append(Constants.WAVE_SEPARATOR);
                sb.append("token");
                sb.append(Constants.COLON_SEPARATOR);
                sb.append(a(context, a2));
                sb.append(Constants.WAVE_SEPARATOR);
                sb.append("package_name");
                sb.append(Constants.COLON_SEPARATOR);
                sb.append(context.getPackageName());
                str = sb.toString();
                break;
            case 4:
                sb = new StringBuilder();
                sb.append("brand:");
                apVar = ap.VIVO;
                sb.append(apVar.name());
                sb.append(Constants.WAVE_SEPARATOR);
                sb.append("token");
                sb.append(Constants.COLON_SEPARATOR);
                sb.append(a(context, a2));
                sb.append(Constants.WAVE_SEPARATOR);
                sb.append("package_name");
                sb.append(Constants.COLON_SEPARATOR);
                sb.append(context.getPackageName());
                str = sb.toString();
                break;
        }
        hashMap.put(Constants.ASSEMBLE_PUSH_REG_INFO, str);
        return hashMap;
    }

    /* renamed from: a */
    public static void m742a(Context context, f fVar) {
        String a2 = a(fVar);
        if (!TextUtils.isEmpty(a2)) {
            s.a(context.getSharedPreferences("mipush_extra", 0).edit().putString(a2, ""));
        }
    }

    public static void a(Context context, f fVar, String str) {
        if (!TextUtils.isEmpty(str)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
            String a2 = a(fVar);
            if (TextUtils.isEmpty(a2)) {
                b.m149a("ASSEMBLE_PUSH : can not find the key of token used in sp file");
                return;
            }
            String string = sharedPreferences.getString(a2, "");
            if (TextUtils.isEmpty(string) || !str.equals(string)) {
                b.m149a("ASSEMBLE_PUSH : send token upload");
                a(fVar, str);
                bd a3 = m.a(fVar);
                if (a3 != null) {
                    ay.a(context).a((String) null, a3, fVar);
                    return;
                }
                return;
            }
            b.m149a("ASSEMBLE_PUSH : do not need to send token");
        }
    }

    public static void a(Intent intent) {
        Bundle extras;
        if (intent != null && (extras = intent.getExtras()) != null && extras.containsKey("pushMsg")) {
            intent.putExtra(PushMessageHelper.KEY_MESSAGE, a(extras.getString("pushMsg")));
        }
    }

    private static synchronized void a(f fVar, String str) {
        synchronized (j.class) {
            String a2 = a(fVar);
            if (TextUtils.isEmpty(a2)) {
                b.m149a("ASSEMBLE_PUSH : can not find the key of token used in sp file");
            } else if (TextUtils.isEmpty(str)) {
                b.m149a("ASSEMBLE_PUSH : token is null");
            } else {
                a.put(a2, str);
            }
        }
    }

    public static void a(String str, int i) {
        MiTinyDataClient.upload("hms_push_error", str, 1L, "error code = " + i);
    }

    /* renamed from: a */
    public static boolean m743a(Context context) {
        if (context == null) {
            return false;
        }
        return at.b(context);
    }

    /* renamed from: a */
    public static boolean m744a(Context context, f fVar) {
        if (m.m746a(fVar) != null) {
            return ag.a(context).a(m.m746a(fVar).a(), true);
        }
        return false;
    }

    public static String b(f fVar) {
        switch (aw.a[fVar.ordinal()]) {
            case 1:
                return "hms_push_error";
            case 2:
                return "fcm_push_error";
            case 3:
                return "cos_push_error";
            case 4:
                return "ftos_push_error";
            default:
                return null;
        }
    }

    public static void b(Context context) {
        g.a(context).register();
    }

    public static void b(Context context, f fVar, String str) {
        aj.a(context).a(new au(str, context, fVar));
    }

    public static void c(Context context) {
        g.a(context).unregister();
    }

    public static void d(Context context) {
        boolean z = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        String a2 = a(f.ASSEMBLE_PUSH_HUAWEI);
        String a3 = a(f.ASSEMBLE_PUSH_FCM);
        if (!TextUtils.isEmpty(sharedPreferences.getString(a2, "")) && TextUtils.isEmpty(sharedPreferences.getString(a3, ""))) {
            z = true;
        }
        if (z) {
            ay.a(context).a(2, a2);
        }
    }

    public static synchronized void d(Context context, f fVar, String str) {
        synchronized (j.class) {
            String a2 = a(fVar);
            if (TextUtils.isEmpty(a2)) {
                b.m149a("ASSEMBLE_PUSH : can not find the key of token used in sp file");
                return;
            }
            s.a(context.getSharedPreferences("mipush_extra", 0).edit().putString(a2, str));
            b.m149a("ASSEMBLE_PUSH : update sp file success!  " + str);
        }
    }
}
