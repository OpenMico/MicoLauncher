package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class af implements Runnable {
    final /* synthetic */ String[] a;
    final /* synthetic */ Context b;
    final /* synthetic */ av c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public af(av avVar, String[] strArr, Context context) {
        this.c = avVar;
        this.a = strArr;
        this.b = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        for (int i = 0; i < this.a.length; i++) {
            try {
                if (!TextUtils.isEmpty(this.a[i])) {
                    if (i > 0) {
                        Thread.sleep(((long) ((Math.random() * 2.0d) + 1.0d)) * 1000);
                    }
                    PackageInfo packageInfo = this.b.getPackageManager().getPackageInfo(this.a[i], 4);
                    if (packageInfo != null) {
                        this.c.a(this.b, packageInfo);
                    }
                }
            } catch (Throwable th) {
                b.a(th);
                return;
            }
        }
    }
}
