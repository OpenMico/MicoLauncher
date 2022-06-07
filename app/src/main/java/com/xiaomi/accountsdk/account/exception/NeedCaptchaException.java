package com.xiaomi.accountsdk.account.exception;

import com.xiaomi.accountsdk.account.ServerErrorCode;

/* loaded from: classes2.dex */
public class NeedCaptchaException extends AccountException {
    private static final long serialVersionUID = 1;
    private final String mCaptchaUrl;

    public NeedCaptchaException(String str) {
        this(ServerErrorCode.ERROR_WRONG_CAPTCHA, "Need captcha code or wrong captcha code", str);
    }

    public NeedCaptchaException(int i, String str, String str2) {
        super(i, str);
        this.mCaptchaUrl = str2;
    }

    public String getCaptchaUrl() {
        return this.mCaptchaUrl;
    }
}
