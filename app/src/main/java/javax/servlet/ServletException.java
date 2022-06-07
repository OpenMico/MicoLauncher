package javax.servlet;

/* loaded from: classes5.dex */
public class ServletException extends Exception {
    private Throwable rootCause;

    public ServletException() {
    }

    public ServletException(String str) {
        super(str);
    }

    public ServletException(String str, Throwable th) {
        super(str, th);
        this.rootCause = th;
    }

    public ServletException(Throwable th) {
        super(th);
        this.rootCause = th;
    }

    public Throwable getRootCause() {
        return this.rootCause;
    }
}
