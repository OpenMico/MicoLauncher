package com.xiaomi.accountsdk.account.exception;

import com.xiaomi.accountsdk.account.ServerErrorCode;
import com.xiaomi.accountsdk.account.data.MetaLoginData;

/* loaded from: classes2.dex */
public class InvalidCredentialException extends AccountException {
    private static final long serialVersionUID = 1;
    public String captchaUrl;
    public final boolean hasPwd;
    public MetaLoginData metaLoginData;

    public InvalidCredentialException(boolean z) {
        super(z ? ServerErrorCode.ERROR_PASSWORD : ServerErrorCode.ERROR_NO_PASSWORD, z ? "password error or passToken invalid" : "no password");
        this.hasPwd = z;
    }

    public InvalidCredentialException(int i, String str, boolean z) {
        super(i, str);
        this.hasPwd = z;
    }

    public InvalidCredentialException metaLoginData(MetaLoginData metaLoginData) {
        this.metaLoginData = metaLoginData;
        return this;
    }

    public InvalidCredentialException captchaUrl(String str) {
        this.captchaUrl = str;
        return this;
    }

    public MetaLoginData getMetaLoginData() {
        return this.metaLoginData;
    }

    public String getCaptchaUrl() {
        return this.captchaUrl;
    }

    public boolean getHasPwd() {
        return this.hasPwd;
    }
}
