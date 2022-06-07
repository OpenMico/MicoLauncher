package io.netty.handler.ssl;

/* loaded from: classes4.dex */
public final class SslHandshakeCompletionEvent {
    public static final SslHandshakeCompletionEvent SUCCESS = new SslHandshakeCompletionEvent();
    private final Throwable a;

    private SslHandshakeCompletionEvent() {
        this.a = null;
    }

    public SslHandshakeCompletionEvent(Throwable th) {
        if (th != null) {
            this.a = th;
            return;
        }
        throw new NullPointerException("cause");
    }

    public boolean isSuccess() {
        return this.a == null;
    }

    public Throwable cause() {
        return this.a;
    }

    public String toString() {
        Throwable cause = cause();
        if (cause == null) {
            return "SslHandshakeCompletionEvent(SUCCESS)";
        }
        return "SslHandshakeCompletionEvent(" + cause + ')';
    }
}
