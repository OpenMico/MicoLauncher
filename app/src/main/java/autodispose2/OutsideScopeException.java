package autodispose2;

/* loaded from: classes.dex */
public class OutsideScopeException extends RuntimeException {
    public OutsideScopeException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public final synchronized Throwable fillInStackTrace() {
        if (!AutoDisposePlugins.a) {
            return this;
        }
        return super.fillInStackTrace();
    }
}
