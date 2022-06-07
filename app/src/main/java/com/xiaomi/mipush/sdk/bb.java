package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.at;
import com.xiaomi.push.di;
import com.xiaomi.push.z;
import java.io.File;
import java.util.HashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class bb implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ boolean b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public bb(Context context, boolean z) {
        this.a = context;
        this.b = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        File file;
        Throwable th;
        HashMap<String, String> a;
        String str;
        File logFile;
        try {
            a = y.a(this.a, "");
            if (this.b) {
                str = this.a.getFilesDir().getAbsolutePath();
            } else {
                str = this.a.getExternalFilesDir(null).getAbsolutePath() + di.a;
            }
            logFile = Logger.getLogFile(str);
        } catch (Throwable th2) {
            th = th2;
            file = null;
        }
        if (logFile == null) {
            b.m149a("log file null");
            return;
        }
        file = new File(str, this.a.getPackageName() + ".zip");
        try {
            z.a(file, logFile);
            if (file.exists()) {
                at.a((this.b ? "https://api.xmpush.xiaomi.com/upload/xmsf_log?file=" : "https://api.xmpush.xiaomi.com/upload/app_log?file=") + file.getName(), a, file, "file");
            } else {
                b.m149a("zip log file failed");
            }
        } catch (Throwable th3) {
            th = th3;
            b.a(th);
            if (file != null) {
                return;
            }
        }
        if (file != null && file.exists()) {
            file.delete();
        }
    }
}
