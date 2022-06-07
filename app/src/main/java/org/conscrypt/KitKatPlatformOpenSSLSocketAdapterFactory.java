package org.conscrypt;

import java.io.IOException;
import java.net.Socket;

/* loaded from: classes5.dex */
public class KitKatPlatformOpenSSLSocketAdapterFactory extends BaseOpenSSLSocketAdapterFactory {
    public KitKatPlatformOpenSSLSocketAdapterFactory(OpenSSLSocketFactoryImpl openSSLSocketFactoryImpl) {
        super(openSSLSocketFactoryImpl);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.net.Socket, org.conscrypt.KitKatPlatformOpenSSLSocketImplAdapter] */
    @Override // org.conscrypt.BaseOpenSSLSocketAdapterFactory
    protected Socket wrap(OpenSSLSocketImpl openSSLSocketImpl) throws IOException {
        return new KitKatPlatformOpenSSLSocketImplAdapter(openSSLSocketImpl);
    }
}
