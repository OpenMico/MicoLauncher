package com.xiaomi.miot.typedef.exception;

import com.xiaomi.miot.typedef.error.MiotError;

/* loaded from: classes3.dex */
public class MiotException extends Exception {
    private int errorCode;

    public MiotException(int i, String str) {
        super(str);
        this.errorCode = i;
    }

    public MiotException(MiotError miotError, String str) {
        super(miotError.getMessage() + " -> " + str);
        this.errorCode = miotError.getCode();
    }

    public MiotException(MiotError miotError) {
        super(miotError.getMessage());
        this.errorCode = miotError.getCode();
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public MiotError toMiotError() {
        return new MiotError(this.errorCode, getMessage());
    }
}
