package com.milink.runtime.provider;

/* loaded from: classes2.dex */
public class BaseCode {
    public static final int NOT_FOUND_PROVIDER;
    public static final int TIMEOUT_ERROR;
    public static final int UNKNOWN_CALLING_PACKAGE_ERROR;
    public static final int UNKNOWN_ERROR;
    private static int a = -1;

    static {
        int i = a;
        NOT_FOUND_PROVIDER = i;
        UNKNOWN_ERROR = i - 1;
        UNKNOWN_CALLING_PACKAGE_ERROR = i - 2;
        TIMEOUT_ERROR = i - 3;
    }

    private BaseCode() {
    }
}
