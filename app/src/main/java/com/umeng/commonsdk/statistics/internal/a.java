package com.umeng.commonsdk.statistics.internal;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.umeng.analytics.pro.ai;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.utils.UMUtils;
import org.apache.commons.lang3.StringUtils;

/* compiled from: HeaderHelper.java */
/* loaded from: classes2.dex */
public class a {
    private static Context a;
    private String b;
    private String c;

    private a() {
        this.b = null;
        this.c = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: HeaderHelper.java */
    /* renamed from: com.umeng.commonsdk.statistics.internal.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static class C0144a {
        private static final a a = new a();

        private C0144a() {
        }
    }

    public static a a(Context context) {
        if (a == null && context != null) {
            a = context.getApplicationContext();
        }
        return C0144a.a;
    }

    public boolean a(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.startsWith(ai.at);
        }
        return false;
    }

    public boolean b(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.startsWith(ai.aF);
        }
        return false;
    }

    public boolean c(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.startsWith(ai.aB);
        }
        return false;
    }

    public void d(String str) {
        String substring = str.substring(0, str.indexOf(95));
        f(substring);
        e(substring);
    }

    private void e(String str) {
        try {
            String replaceAll = str.replaceAll("&=", StringUtils.SPACE).replaceAll("&&", StringUtils.SPACE).replaceAll("==", "/");
            this.b = replaceAll + "/Android/" + Build.DISPLAY + "/" + Build.MODEL + "/" + Build.VERSION.RELEASE + StringUtils.SPACE + HelperUtils.getUmengMD5(UMUtils.getAppkey(a));
        } catch (Throwable th) {
            UMCrashManager.reportCrash(a, th);
        }
    }

    private void f(String str) {
        try {
            String str2 = str.split("&&")[0];
            if (!TextUtils.isEmpty(str2)) {
                String[] split = str2.split("&=");
                StringBuilder sb = new StringBuilder();
                sb.append(ai.aN);
                for (String str3 : split) {
                    if (!TextUtils.isEmpty(str3)) {
                        String substring = str3.substring(0, 2);
                        if (substring.endsWith("=")) {
                            substring = substring.replace("=", "");
                        }
                        sb.append(substring);
                    }
                }
                this.c = sb.toString();
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(a, th);
        }
    }

    public String a() {
        return this.c;
    }

    public String b() {
        return this.b;
    }
}
