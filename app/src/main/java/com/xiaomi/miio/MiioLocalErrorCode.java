package com.xiaomi.miio;

/* loaded from: classes3.dex */
public enum MiioLocalErrorCode {
    SUCCESS(0, "ok"),
    PERMISSION_DENIED(-1, "permission denied"),
    EXCEPTION(-4, "internal exception occurred from local api"),
    DEVICE_EXCEPTION(-5, "internal exception occurred from device"),
    TIMEOUT(-3, "request time out"),
    UNKNOWN(-9, "unknown error"),
    MSG_TOO_LONG(-99, "msg too long");
    
    public static final int CODE_DEVICE_EXCEPTION = -5;
    public static final int CODE_EXCEPTION = -4;
    public static final int CODE_PERMISSION_DENIED = -1;
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_TIMEOUT = -3;
    public static final int CODE_UNKNOWN = -9;
    private int code;
    private String message;

    MiioLocalErrorCode(int i, String str) {
        this.code = i;
        this.message = str;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
