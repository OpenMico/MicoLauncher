package io.netty.util.concurrent;

/* loaded from: classes4.dex */
public class BlockingOperationException extends IllegalStateException {
    private static final long serialVersionUID = 2462223247762460301L;

    public BlockingOperationException() {
    }

    public BlockingOperationException(String str) {
        super(str);
    }

    public BlockingOperationException(Throwable th) {
        super(th);
    }

    public BlockingOperationException(String str, Throwable th) {
        super(str, th);
    }
}
