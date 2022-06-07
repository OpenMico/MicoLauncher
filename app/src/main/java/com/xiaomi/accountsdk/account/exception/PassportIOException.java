package com.xiaomi.accountsdk.account.exception;

import java.io.IOException;

/* loaded from: classes2.dex */
public class PassportIOException extends IOException {
    public boolean isStsUrlRequestError;
    public final int responseCode;
    public String serviceId;

    public PassportIOException(IOException iOException) {
        super(iOException);
        this.isStsUrlRequestError = false;
        this.responseCode = -1;
    }

    public PassportIOException(int i, String str) {
        super(str);
        this.isStsUrlRequestError = false;
        this.responseCode = i;
    }

    public void stsUrlRequestError(String str) {
        this.serviceId = str;
        this.isStsUrlRequestError = true;
    }

    @Override // java.lang.Throwable
    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("response code: ");
        sb.append(this.responseCode);
        sb.append("\n");
        if (this.isStsUrlRequestError) {
            str = this.serviceId + " sts url request error \n";
        } else {
            str = "";
        }
        sb.append(str);
        return sb.toString() + super.toString();
    }
}
