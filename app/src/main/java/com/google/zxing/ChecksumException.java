package com.google.zxing;

/* loaded from: classes2.dex */
public final class ChecksumException extends ReaderException {
    private static final ChecksumException a;

    static {
        ChecksumException checksumException = new ChecksumException();
        a = checksumException;
        checksumException.setStackTrace(NO_TRACE);
    }

    private ChecksumException() {
    }

    private ChecksumException(Throwable th) {
        super(th);
    }

    public static ChecksumException getChecksumInstance() {
        return isStackTrace ? new ChecksumException() : a;
    }

    public static ChecksumException getChecksumInstance(Throwable th) {
        return isStackTrace ? new ChecksumException(th) : a;
    }
}
