package org.conscrypt;

/* loaded from: classes5.dex */
public final class ServerSessionContext extends AbstractSessionContext {
    private SSLServerSessionCache persistentCache;

    @Override // org.conscrypt.AbstractSessionContext
    void onBeforeRemoveSession(NativeSslSession nativeSslSession) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ServerSessionContext() {
        super(100);
        NativeCrypto.SSL_CTX_set_session_id_context(this.sslCtxNativePointer, this, new byte[]{32});
    }

    public void setPersistentCache(SSLServerSessionCache sSLServerSessionCache) {
        this.persistentCache = sSLServerSessionCache;
    }

    @Override // org.conscrypt.AbstractSessionContext
    NativeSslSession getSessionFromPersistentCache(byte[] bArr) {
        byte[] sessionData;
        NativeSslSession newInstance;
        SSLServerSessionCache sSLServerSessionCache = this.persistentCache;
        if (sSLServerSessionCache == null || (sessionData = sSLServerSessionCache.getSessionData(bArr)) == null || (newInstance = NativeSslSession.newInstance(this, sessionData, null, -1)) == null || !newInstance.isValid()) {
            return null;
        }
        cacheSession(newInstance);
        return newInstance;
    }

    @Override // org.conscrypt.AbstractSessionContext
    void onBeforeAddSession(NativeSslSession nativeSslSession) {
        byte[] bytes;
        if (this.persistentCache != null && (bytes = nativeSslSession.toBytes()) != null) {
            this.persistentCache.putSessionData(nativeSslSession.toSSLSession(), bytes);
        }
    }
}
