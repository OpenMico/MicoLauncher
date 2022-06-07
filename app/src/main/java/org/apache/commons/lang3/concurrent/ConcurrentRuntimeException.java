package org.apache.commons.lang3.concurrent;

/* loaded from: classes5.dex */
public class ConcurrentRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -6582182735562919670L;

    protected ConcurrentRuntimeException() {
    }

    public ConcurrentRuntimeException(Throwable th) {
        super(ConcurrentUtils.a(th));
    }

    public ConcurrentRuntimeException(String str, Throwable th) {
        super(str, ConcurrentUtils.a(th));
    }
}
