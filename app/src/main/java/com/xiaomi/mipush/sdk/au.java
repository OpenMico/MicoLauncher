package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class au implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ f c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public au(String str, Context context, f fVar) {
        this.a = str;
        this.b = context;
        this.c = fVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!TextUtils.isEmpty(this.a)) {
            String str = "";
            String[] split = this.a.split(Constants.WAVE_SEPARATOR);
            int length = split.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String str2 = split[i];
                if (!TextUtils.isEmpty(str2) && str2.startsWith("token:")) {
                    str = str2.substring(str2.indexOf(Constants.COLON_SEPARATOR) + 1);
                    break;
                }
                i++;
            }
            if (!TextUtils.isEmpty(str)) {
                b.m149a("ASSEMBLE_PUSH : receive correct token");
                j.d(this.b, this.c, str);
                j.d(this.b);
                return;
            }
            b.m149a("ASSEMBLE_PUSH : receive incorrect token");
        }
    }
}
