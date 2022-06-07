package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.text.TextUtils;
import com.blankj.utilcode.constant.CacheConstants;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.aj;
import com.xiaomi.push.at;
import com.xiaomi.push.az;
import com.xiaomi.push.hm;
import com.xiaomi.push.s;
import com.xiaomi.push.service.ag;
import java.lang.Thread;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class bc implements Thread.UncaughtExceptionHandler {
    private static final Object a = new Object();
    private static final String[] b = {"com.xiaomi.channel.commonutils", "com.xiaomi.common.logger", "com.xiaomi.measite.smack", "com.xiaomi.metoknlp", "com.xiaomi.mipush.sdk", "com.xiaomi.network", "com.xiaomi.push", "com.xiaomi.slim", "com.xiaomi.smack", "com.xiaomi.stats", "com.xiaomi.tinyData", "com.xiaomi.xmpush.thrift", "com.xiaomi.clientreport"};
    private Context c;
    private SharedPreferences d;
    private Thread.UncaughtExceptionHandler e;

    public bc(Context context) {
        this(context, Thread.getDefaultUncaughtExceptionHandler());
    }

    public bc(Context context, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.c = context;
        this.e = uncaughtExceptionHandler;
    }

    private void a(Throwable th) {
        String c = c(th);
        if (!TextUtils.isEmpty(c)) {
            String b2 = b(th);
            if (!TextUtils.isEmpty(b2)) {
                u.a(this.c).a(c, b2);
                if (b()) {
                    c();
                }
            }
        }
    }

    private boolean a(boolean z, String str) {
        for (String str2 : b) {
            if (str.contains(str2)) {
                return true;
            }
        }
        return z;
    }

    private String b(Throwable th) {
        StackTraceElement[] stackTrace = th.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(3, stackTrace.length); i++) {
            sb.append(stackTrace[i].toString() + "\r\n");
        }
        String sb2 = sb.toString();
        return TextUtils.isEmpty(sb2) ? "" : az.a(sb2);
    }

    private boolean b() {
        this.d = this.c.getSharedPreferences("mipush_extra", 4);
        if (at.e(this.c)) {
            if (!ag.a(this.c).a(hm.Crash4GUploadSwitch.a(), true)) {
                return false;
            }
            return ((float) Math.abs((System.currentTimeMillis() / 1000) - this.d.getLong("last_crash_upload_time_stamp", 0L))) >= ((float) Math.max((int) CacheConstants.HOUR, ag.a(this.c).a(hm.Crash4GUploadFrequency.a(), CacheConstants.HOUR))) * 0.9f;
        } else if (!at.d(this.c)) {
            return true;
        } else {
            return Math.abs((System.currentTimeMillis() / 1000) - this.d.getLong("last_crash_upload_time_stamp", 0L)) >= ((long) Math.max(60, ag.a(this.c).a(hm.CrashWIFIUploadFrequency.a(), 1800)));
        }
    }

    private String c(Throwable th) {
        StackTraceElement[] stackTrace = th.getStackTrace();
        StringBuilder sb = new StringBuilder(th.toString());
        sb.append("\r\n");
        boolean z = false;
        for (StackTraceElement stackTraceElement : stackTrace) {
            String stackTraceElement2 = stackTraceElement.toString();
            z = a(z, stackTraceElement2);
            sb.append(stackTraceElement2);
            sb.append("\r\n");
        }
        return z ? sb.toString() : "";
    }

    private void c() {
        aj.a(this.c).a(new bf(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        this.d = this.c.getSharedPreferences("mipush_extra", 4);
        SharedPreferences.Editor edit = this.d.edit();
        edit.putLong("last_crash_upload_time_stamp", System.currentTimeMillis() / 1000);
        s.a(edit);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        a(th);
        synchronized (a) {
            try {
                a.wait(3000L);
            } catch (InterruptedException e) {
                b.a(e);
            }
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.e;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
            return;
        }
        Process.killProcess(Process.myPid());
        System.exit(1);
    }
}
