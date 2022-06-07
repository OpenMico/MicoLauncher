package io.netty.handler.ssl;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import org.apache.tomcat.jni.SSLContext;
import org.apache.tomcat.jni.SessionTicketKey;

/* loaded from: classes4.dex */
public abstract class OpenSslSessionContext implements SSLSessionContext {
    private static final Enumeration<byte[]> b = new a();
    final long a;
    private final OpenSslSessionStats c;

    public abstract boolean isSessionCacheEnabled();

    public abstract void setSessionCacheEnabled(boolean z);

    public OpenSslSessionContext(long j) {
        this.a = j;
        this.c = new OpenSslSessionStats(j);
    }

    @Override // javax.net.ssl.SSLSessionContext
    public SSLSession getSession(byte[] bArr) {
        if (bArr != null) {
            return null;
        }
        throw new NullPointerException("bytes");
    }

    @Override // javax.net.ssl.SSLSessionContext
    public Enumeration<byte[]> getIds() {
        return b;
    }

    @Deprecated
    public void setTicketKeys(byte[] bArr) {
        if (bArr != null) {
            SSLContext.setSessionTicketKeys(this.a, bArr);
            return;
        }
        throw new NullPointerException("keys");
    }

    public void setTicketKeys(OpenSslSessionTicketKey... openSslSessionTicketKeyArr) {
        if (openSslSessionTicketKeyArr != null) {
            SessionTicketKey[] sessionTicketKeyArr = new SessionTicketKey[openSslSessionTicketKeyArr.length];
            for (int i = 0; i < sessionTicketKeyArr.length; i++) {
                sessionTicketKeyArr[i] = openSslSessionTicketKeyArr[i].a;
            }
            SSLContext.setSessionTicketKeys(this.a, sessionTicketKeyArr);
            return;
        }
        throw new NullPointerException("keys");
    }

    public OpenSslSessionStats stats() {
        return this.c;
    }

    /* loaded from: classes4.dex */
    private static final class a implements Enumeration<byte[]> {
        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return false;
        }

        private a() {
        }

        /* renamed from: a */
        public byte[] nextElement() {
            throw new NoSuchElementException();
        }
    }
}
