package com.xiaomi.micolauncher.api;

/* loaded from: classes3.dex */
public class ApiError extends RuntimeException {
    private int mCode;

    public ApiError(int i, String str) {
        super(str);
        this.mCode = i;
    }

    public ApiError(ErrorCode errorCode) {
        this.mCode = errorCode.getCode();
    }

    public int getCode() {
        return this.mCode;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return String.format("%d %s", Integer.valueOf(getCode()), getMessage());
    }
}
