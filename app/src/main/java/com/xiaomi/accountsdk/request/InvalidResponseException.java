package com.xiaomi.accountsdk.request;

import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.exception.AccountException;

/* loaded from: classes2.dex */
public class InvalidResponseException extends AccountException {
    private static final long serialVersionUID = 5544530065307643635L;
    public boolean isHtmlOr302;
    private ServerError serverError;

    public InvalidResponseException(String str) {
        this(str, (Throwable) null);
    }

    public InvalidResponseException(int i, String str) {
        super(i, str);
        this.isHtmlOr302 = false;
        this.serverError = null;
    }

    public InvalidResponseException(String str, Throwable th) {
        this(str, th, false);
    }

    public InvalidResponseException(String str, Throwable th, boolean z) {
        super(-1, str, th);
        this.isHtmlOr302 = false;
        this.serverError = null;
        this.isHtmlOr302 = z;
    }

    public InvalidResponseException(ServerError serverError) {
        super(-1, null);
        this.isHtmlOr302 = false;
        this.serverError = null;
        this.serverError = serverError;
    }

    public ServerError getServerError() {
        return this.serverError;
    }
}
