package org.conscrypt;

import java.io.IOException;
import java.net.Socket;

/* loaded from: classes5.dex */
public class PreKitKatPlatformOpenSSLSocketAdapterFactory extends BaseOpenSSLSocketAdapterFactory {
    public PreKitKatPlatformOpenSSLSocketAdapterFactory(OpenSSLSocketFactoryImpl openSSLSocketFactoryImpl) {
        super(openSSLSocketFactoryImpl);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.net.Socket, org.conscrypt.PreKitKatPlatformOpenSSLSocketImplAdapter] */
    @Override // org.conscrypt.BaseOpenSSLSocketAdapterFactory
    protected Socket wrap(OpenSSLSocketImpl openSSLSocketImpl) throws IOException {
        return new PreKitKatPlatformOpenSSLSocketImplAdapter(openSSLSocketImpl);
    }
}
