package org.eclipse.jetty.http;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;

/* loaded from: classes5.dex */
public class HttpException extends IOException {
    String _reason;
    int _status;

    public HttpException(int i) {
        this._status = i;
        this._reason = null;
    }

    public HttpException(int i, String str) {
        this._status = i;
        this._reason = str;
    }

    public HttpException(int i, String str, Throwable th) {
        this._status = i;
        this._reason = str;
        initCause(th);
    }

    public String getReason() {
        return this._reason;
    }

    public void setReason(String str) {
        this._reason = str;
    }

    public int getStatus() {
        return this._status;
    }

    public void setStatus(int i) {
        this._status = i;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "HttpException(" + this._status + Constants.ACCEPT_TIME_SEPARATOR_SP + this._reason + Constants.ACCEPT_TIME_SEPARATOR_SP + super.getCause() + ")";
    }
}
