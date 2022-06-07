package io.netty.handler.ssl;

import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;

/* compiled from: JdkSslSession.java */
/* loaded from: classes4.dex */
final class h implements a, SSLSession {
    private final SSLEngine a;
    private volatile String b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(SSLEngine sSLEngine) {
        this.a = sSLEngine;
    }

    private SSLSession b() {
        return this.a.getSession();
    }

    @Override // javax.net.ssl.SSLSession
    public String getProtocol() {
        return b().getProtocol();
    }

    @Override // io.netty.handler.ssl.a
    public String a() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(String str) {
        this.b = str;
    }

    @Override // javax.net.ssl.SSLSession
    public byte[] getId() {
        return b().getId();
    }

    @Override // javax.net.ssl.SSLSession
    public SSLSessionContext getSessionContext() {
        return b().getSessionContext();
    }

    @Override // javax.net.ssl.SSLSession
    public long getCreationTime() {
        return b().getCreationTime();
    }

    @Override // javax.net.ssl.SSLSession
    public long getLastAccessedTime() {
        return b().getLastAccessedTime();
    }

    @Override // javax.net.ssl.SSLSession
    public void invalidate() {
        b().invalidate();
    }

    @Override // javax.net.ssl.SSLSession
    public boolean isValid() {
        return b().isValid();
    }

    @Override // javax.net.ssl.SSLSession
    public void putValue(String str, Object obj) {
        b().putValue(str, obj);
    }

    @Override // javax.net.ssl.SSLSession
    public Object getValue(String str) {
        return b().getValue(str);
    }

    @Override // javax.net.ssl.SSLSession
    public void removeValue(String str) {
        b().removeValue(str);
    }

    @Override // javax.net.ssl.SSLSession
    public String[] getValueNames() {
        return b().getValueNames();
    }

    @Override // javax.net.ssl.SSLSession
    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        return b().getPeerCertificates();
    }

    @Override // javax.net.ssl.SSLSession
    public Certificate[] getLocalCertificates() {
        return b().getLocalCertificates();
    }

    @Override // javax.net.ssl.SSLSession
    public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        return b().getPeerCertificateChain();
    }

    @Override // javax.net.ssl.SSLSession
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return b().getPeerPrincipal();
    }

    @Override // javax.net.ssl.SSLSession
    public Principal getLocalPrincipal() {
        return b().getLocalPrincipal();
    }

    @Override // javax.net.ssl.SSLSession
    public String getCipherSuite() {
        return b().getCipherSuite();
    }

    @Override // javax.net.ssl.SSLSession
    public String getPeerHost() {
        return b().getPeerHost();
    }

    @Override // javax.net.ssl.SSLSession
    public int getPeerPort() {
        return b().getPeerPort();
    }

    @Override // javax.net.ssl.SSLSession
    public int getPacketBufferSize() {
        return b().getPacketBufferSize();
    }

    @Override // javax.net.ssl.SSLSession
    public int getApplicationBufferSize() {
        return b().getApplicationBufferSize();
    }
}
