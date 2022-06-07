package com.xiaomi.push;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.clientreport.manager.a;
import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class bf {
    public static String a() {
        return Build.VERSION.RELEASE + Constants.ACCEPT_TIME_SEPARATOR_SERVER + Build.VERSION.INCREMENTAL;
    }

    public static String a(Context context) {
        String a = bi.a(context).a("sp_client_report_status", "sp_client_report_key", "");
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        String a2 = az.a(20);
        bi.a(context).m781a("sp_client_report_status", "sp_client_report_key", a2);
        return a2;
    }

    public static void a(Context context, String str) {
        Intent intent = new Intent("com.xiaomi.xmsf.push.XMSF_UPLOAD_ACTIVE");
        intent.putExtra("pkgname", context.getPackageName());
        intent.putExtra("category", "category_client_report_data");
        intent.putExtra("name", "quality_support");
        intent.putExtra("data", str);
        context.sendBroadcast(intent, "com.xiaomi.xmsf.permission.USE_XMSF_UPLOAD");
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00e6, code lost:
        if (r7 == null) goto L_0x00eb;
     */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0104  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(android.content.Context r11, java.lang.String r12, java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.bf.a(android.content.Context, java.lang.String, java.lang.String):void");
    }

    public static void a(Context context, List<String> list) {
        if (list != null && list.size() > 0 && m777a(context)) {
            for (String str : list) {
                if (!TextUtils.isEmpty(str)) {
                    a(context, str);
                }
            }
        }
    }

    /* renamed from: a */
    public static boolean m777a(Context context) {
        try {
            return context.getApplicationContext().getPackageManager().getPackageInfo("com.xiaomi.xmsf", 0).versionCode >= 108;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m778a(Context context, String str) {
        File file = new File(str);
        long maxFileLength = a.a(context).a().getMaxFileLength();
        if (file.exists()) {
            try {
                if (file.length() > maxFileLength) {
                    return false;
                }
            } catch (Exception e) {
                b.a(e);
                return false;
            }
        } else {
            z.m1176a(file);
        }
        return true;
    }

    @TargetApi(9)
    public static byte[] a(String str) {
        byte[] copyOf = Arrays.copyOf(aw.a(str), 16);
        copyOf[0] = 68;
        copyOf[15] = 84;
        return copyOf;
    }

    /* renamed from: a */
    public static File[] m779a(Context context, String str) {
        File externalFilesDir = context.getExternalFilesDir(str);
        if (externalFilesDir != null) {
            return externalFilesDir.listFiles(new al());
        }
        return null;
    }
}
