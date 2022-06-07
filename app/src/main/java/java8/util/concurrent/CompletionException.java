package java8.util.concurrent;

/* loaded from: classes5.dex */
public class CompletionException extends RuntimeException {
    private static final long serialVersionUID = 7830266012832686185L;

    protected CompletionException() {
    }

    protected CompletionException(String str) {
        super(str);
    }

    public CompletionException(String str, Throwable th) {
        super(str, th);
    }

    public CompletionException(Throwable th) {
        super(th);
    }
}
