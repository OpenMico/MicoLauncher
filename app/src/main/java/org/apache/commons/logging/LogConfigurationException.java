package org.apache.commons.logging;

/* loaded from: classes5.dex */
public class LogConfigurationException extends RuntimeException {
    private static final long serialVersionUID = 8486587136871052495L;
    protected Throwable cause;

    public LogConfigurationException() {
        this.cause = null;
    }

    public LogConfigurationException(String str) {
        super(str);
        this.cause = null;
    }

    public LogConfigurationException(Throwable th) {
        this(th == null ? null : th.toString(), th);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public LogConfigurationException(java.lang.String r2, java.lang.Throwable r3) {
        /*
            r1 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r0.append(r2)
            java.lang.String r2 = " (Caused by "
            r0.append(r2)
            r0.append(r3)
            java.lang.String r2 = ")"
            r0.append(r2)
            java.lang.String r2 = r0.toString()
            r1.<init>(r2)
            r2 = 0
            r1.cause = r2
            r1.cause = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.LogConfigurationException.<init>(java.lang.String, java.lang.Throwable):void");
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
