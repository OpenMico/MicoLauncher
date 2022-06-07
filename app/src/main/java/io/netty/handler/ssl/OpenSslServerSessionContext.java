package io.netty.handler.ssl;

import org.apache.tomcat.jni.SSLContext;

/* loaded from: classes4.dex */
public final class OpenSslServerSessionContext extends OpenSslSessionContext {
    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenSslServerSessionContext(long j) {
        super(j);
    }

    @Override // javax.net.ssl.SSLSessionContext
    public void setSessionTimeout(int i) {
        if (i >= 0) {
            SSLContext.setSessionCacheTimeout(this.a, i);
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override // javax.net.ssl.SSLSessionContext
    public int getSessionTimeout() {
        return (int) SSLContext.getSessionCacheTimeout(this.a);
    }

    @Override // javax.net.ssl.SSLSessionContext
    public void setSessionCacheSize(int i) {
        if (i >= 0) {
            SSLContext.setSessionCacheSize(this.a, i);
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override // javax.net.ssl.SSLSessionContext
    public int getSessionCacheSize() {
        return (int) SSLContext.getSessionCacheSize(this.a);
    }

    @Override // io.netty.handler.ssl.OpenSslSessionContext
    public void setSessionCacheEnabled(boolean z) {
        SSLContext.setSessionCacheMode(this.a, z ? 2L : 0L);
    }

    @Override // io.netty.handler.ssl.OpenSslSessionContext
    public boolean isSessionCacheEnabled() {
        return SSLContext.getSessionCacheMode(this.a) == 2;
    }

    public boolean setSessionIdContext(byte[] bArr) {
        return SSLContext.setSessionIdContext(this.a, bArr);
    }
}
