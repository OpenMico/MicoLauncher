package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;

/* loaded from: classes4.dex */
final class r implements Runnable {
    final /* synthetic */ String[] a;
    final /* synthetic */ Context b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public r(String[] strArr, Context context) {
        this.a = strArr;
        this.b = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        PackageInfo packageInfo;
        try {
            String[] strArr = this.a;
            for (String str : strArr) {
                if (!TextUtils.isEmpty(str) && (packageInfo = this.b.getPackageManager().getPackageInfo(str, 4)) != null) {
                    MiPushClient.b(this.b, packageInfo);
                }
            }
        } catch (Throwable th) {
            b.a(th);
        }
    }
}
