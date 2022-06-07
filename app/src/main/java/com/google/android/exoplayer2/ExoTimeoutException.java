package com.google.android.exoplayer2;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class ExoTimeoutException extends RuntimeException {
    public static final int TIMEOUT_OPERATION_DETACH_SURFACE = 3;
    public static final int TIMEOUT_OPERATION_RELEASE = 1;
    public static final int TIMEOUT_OPERATION_SET_FOREGROUND_MODE = 2;
    public static final int TIMEOUT_OPERATION_UNDEFINED = 0;
    public final int timeoutOperation;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface TimeoutOperation {
    }

    private static String a(int i) {
        switch (i) {
            case 1:
                return "Player release timed out.";
            case 2:
                return "Setting foreground mode timed out.";
            case 3:
                return "Detaching surface timed out.";
            default:
                return "Undefined timeout.";
        }
    }

    public ExoTimeoutException(int i) {
        super(a(i));
        this.timeoutOperation = i;
    }
}
