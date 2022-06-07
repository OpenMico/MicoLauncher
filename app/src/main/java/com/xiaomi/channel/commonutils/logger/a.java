package com.xiaomi.channel.commonutils.logger;

import android.util.Log;

/* loaded from: classes3.dex */
public class a implements LoggerInterface {
    private String a = "xiaomi";

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void log(String str) {
        Log.v(this.a, str);
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void log(String str, Throwable th) {
        Log.v(this.a, str, th);
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void setTag(String str) {
        this.a = str;
    }
}
