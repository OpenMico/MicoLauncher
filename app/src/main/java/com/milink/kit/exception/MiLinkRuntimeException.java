package com.milink.kit.exception;

/* loaded from: classes2.dex */
public class MiLinkRuntimeException extends RuntimeException {
    private final int code;

    public MiLinkRuntimeException(int i, String str) {
        this(i, str, null);
    }

    public MiLinkRuntimeException(int i, String str, Throwable th) {
        super(str, th);
        this.code = i;
    }

    public MiLinkRuntimeException(int i, Throwable th) {
        this(i, null, th);
    }

    public int getCode() {
        return this.code;
    }
}
