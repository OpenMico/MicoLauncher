package io.netty.handler.timeout;

/* loaded from: classes4.dex */
public final class WriteTimeoutException extends TimeoutException {
    public static final WriteTimeoutException INSTANCE = new WriteTimeoutException();
    private static final long serialVersionUID = -144786655770296065L;

    private WriteTimeoutException() {
    }
}
