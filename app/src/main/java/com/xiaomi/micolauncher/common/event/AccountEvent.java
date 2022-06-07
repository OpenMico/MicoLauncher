package com.xiaomi.micolauncher.common.event;

import android.content.Intent;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;

/* loaded from: classes3.dex */
public class AccountEvent {
    private final ServiceTokenResult.ErrorCode a;
    private final Intent b;
    private final String c;

    public AccountEvent(String str, ServiceTokenResult.ErrorCode errorCode, Intent intent) {
        this.c = str;
        this.a = errorCode;
        this.b = intent;
    }

    public ServiceTokenResult.ErrorCode getErrorCode() {
        return this.a;
    }

    public Intent getIntent() {
        return this.b;
    }
}
