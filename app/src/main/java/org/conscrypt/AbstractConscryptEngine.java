package org.conscrypt;

import java.nio.ByteBuffer;
import java.security.PrivateKey;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public abstract class AbstractConscryptEngine extends SSLEngine {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract byte[] exportKeyingMaterial(String str, byte[] bArr, int i) throws SSLException;

    @Override // javax.net.ssl.SSLEngine
    public abstract String getApplicationProtocol();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String[] getApplicationProtocols();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract byte[] getChannelId() throws SSLException;

    @Override // javax.net.ssl.SSLEngine
    public abstract String getHandshakeApplicationProtocol();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String getHostname();

    @Override // javax.net.ssl.SSLEngine
    public abstract String getPeerHost();

    @Override // javax.net.ssl.SSLEngine
    public abstract int getPeerPort();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract byte[] getTlsUnique();

    abstract SSLSession handshakeSession();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int maxSealOverhead();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void setApplicationProtocolSelector(ApplicationProtocolSelector applicationProtocolSelector);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void setApplicationProtocols(String[] strArr);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void setBufferAllocator(BufferAllocator bufferAllocator);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void setChannelIdEnabled(boolean z);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void setChannelIdPrivateKey(PrivateKey privateKey);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void setHandshakeListener(HandshakeListener handshakeListener);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void setHostname(String str);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void setUseSessionTickets(boolean z);

    @Override // javax.net.ssl.SSLEngine
    public abstract SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException;

    @Override // javax.net.ssl.SSLEngine
    public abstract SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr) throws SSLException;

    @Override // javax.net.ssl.SSLEngine
    public abstract SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, int i, int i2) throws SSLException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer[] byteBufferArr2, int i3, int i4) throws SSLException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, ByteBuffer[] byteBufferArr2) throws SSLException;

    @Override // javax.net.ssl.SSLEngine
    public abstract SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException;

    @Override // javax.net.ssl.SSLEngine
    public abstract SSLEngineResult wrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer byteBuffer) throws SSLException;

    @Override // javax.net.ssl.SSLEngine
    public final SSLSession getHandshakeSession() {
        return handshakeSession();
    }
}
