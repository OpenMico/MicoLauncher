package com.xiaomi.accountsdk.request;

import com.xiaomi.accountsdk.account.exception.HttpException;

/* loaded from: classes2.dex */
public class AuthenticationFailureException extends HttpException {
    private static final long serialVersionUID = 1933476556350874440L;
    private String wwwAuthenticateHeader = null;
    private String caDisableSecondsHeader = null;

    public AuthenticationFailureException(String str) {
        super(0, str);
    }

    public AuthenticationFailureException(int i, String str) {
        super(i, str);
    }

    public String getWwwAuthenticateHeader() {
        return this.wwwAuthenticateHeader;
    }

    public void setWwwAuthenticateHeader(String str) {
        this.wwwAuthenticateHeader = str;
    }

    public String getCaDisableSecondsHeader() {
        return this.caDisableSecondsHeader;
    }

    public void setCaDisableSecondsHeader(String str) {
        this.caDisableSecondsHeader = str;
    }
}
