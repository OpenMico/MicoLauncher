package com.google.zxing;

/* loaded from: classes2.dex */
public final class NotFoundException extends ReaderException {
    private static final NotFoundException a;

    static {
        NotFoundException notFoundException = new NotFoundException();
        a = notFoundException;
        notFoundException.setStackTrace(NO_TRACE);
    }

    private NotFoundException() {
    }

    public static NotFoundException getNotFoundInstance() {
        return a;
    }
}
