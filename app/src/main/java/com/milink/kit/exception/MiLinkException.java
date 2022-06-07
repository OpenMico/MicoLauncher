package com.milink.kit.exception;

/* loaded from: classes2.dex */
public class MiLinkException extends Exception {
    private final int code;

    public MiLinkException(String str) {
        super(str);
        this.code = -1;
    }

    public MiLinkException(String str, Throwable th) {
        super(str, th);
        this.code = -1;
    }

    public MiLinkException(Throwable th) {
        super(th);
        this.code = -1;
    }

    public MiLinkException(int i, String str, Throwable th) {
        super(str, th);
        this.code = i;
    }

    public int getCode() {
        return this.code;
    }
}
