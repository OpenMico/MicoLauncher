package io.netty.handler.timeout;

/* loaded from: classes4.dex */
public final class ReadTimeoutException extends TimeoutException {
    public static final ReadTimeoutException INSTANCE = new ReadTimeoutException();
    private static final long serialVersionUID = 169287984113283421L;

    private ReadTimeoutException() {
    }
}
