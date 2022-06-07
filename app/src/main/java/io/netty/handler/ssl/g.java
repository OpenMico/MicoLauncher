package io.netty.handler.ssl;

import java.nio.ByteBuffer;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;

/* compiled from: JdkSslEngine.java */
/* loaded from: classes4.dex */
class g extends SSLEngine {
    private final SSLEngine a;
    private final h b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(SSLEngine sSLEngine) {
        this.a = sSLEngine;
        this.b = new h(sSLEngine);
    }

    /* renamed from: b */
    public h getSession() {
        return this.b;
    }

    public SSLEngine c() {
        return this.a;
    }

    @Override // javax.net.ssl.SSLEngine
    public void closeInbound() throws SSLException {
        this.a.closeInbound();
    }

    @Override // javax.net.ssl.SSLEngine
    public void closeOutbound() {
        this.a.closeOutbound();
    }

    @Override // javax.net.ssl.SSLEngine
    public String getPeerHost() {
        return this.a.getPeerHost();
    }

    @Override // javax.net.ssl.SSLEngine
    public int getPeerPort() {
        return this.a.getPeerPort();
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.a.wrap(byteBuffer, byteBuffer2);
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLEngineResult wrap(ByteBuffer[] byteBufferArr, ByteBuffer byteBuffer) throws SSLException {
        return this.a.wrap(byteBufferArr, byteBuffer);
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLEngineResult wrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer byteBuffer) throws SSLException {
        return this.a.wrap(byteBufferArr, i, i2, byteBuffer);
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.a.unwrap(byteBuffer, byteBuffer2);
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr) throws SSLException {
        return this.a.unwrap(byteBuffer, byteBufferArr);
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, int i, int i2) throws SSLException {
        return this.a.unwrap(byteBuffer, byteBufferArr, i, i2);
    }

    @Override // javax.net.ssl.SSLEngine
    public Runnable getDelegatedTask() {
        return this.a.getDelegatedTask();
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean isInboundDone() {
        return this.a.isInboundDone();
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean isOutboundDone() {
        return this.a.isOutboundDone();
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getSupportedCipherSuites() {
        return this.a.getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getEnabledCipherSuites() {
        return this.a.getEnabledCipherSuites();
    }

    @Override // javax.net.ssl.SSLEngine
    public void setEnabledCipherSuites(String[] strArr) {
        this.a.setEnabledCipherSuites(strArr);
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getSupportedProtocols() {
        return this.a.getSupportedProtocols();
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getEnabledProtocols() {
        return this.a.getEnabledProtocols();
    }

    @Override // javax.net.ssl.SSLEngine
    public void setEnabledProtocols(String[] strArr) {
        this.a.setEnabledProtocols(strArr);
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLSession getHandshakeSession() {
        return this.a.getHandshakeSession();
    }

    @Override // javax.net.ssl.SSLEngine
    public void beginHandshake() throws SSLException {
        this.a.beginHandshake();
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return this.a.getHandshakeStatus();
    }

    @Override // javax.net.ssl.SSLEngine
    public void setUseClientMode(boolean z) {
        this.a.setUseClientMode(z);
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getUseClientMode() {
        return this.a.getUseClientMode();
    }

    @Override // javax.net.ssl.SSLEngine
    public void setNeedClientAuth(boolean z) {
        this.a.setNeedClientAuth(z);
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getNeedClientAuth() {
        return this.a.getNeedClientAuth();
    }

    @Override // javax.net.ssl.SSLEngine
    public void setWantClientAuth(boolean z) {
        this.a.setWantClientAuth(z);
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getWantClientAuth() {
        return this.a.getWantClientAuth();
    }

    @Override // javax.net.ssl.SSLEngine
    public void setEnableSessionCreation(boolean z) {
        this.a.setEnableSessionCreation(z);
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getEnableSessionCreation() {
        return this.a.getEnableSessionCreation();
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLParameters getSSLParameters() {
        return this.a.getSSLParameters();
    }

    @Override // javax.net.ssl.SSLEngine
    public void setSSLParameters(SSLParameters sSLParameters) {
        this.a.setSSLParameters(sSLParameters);
    }
}
