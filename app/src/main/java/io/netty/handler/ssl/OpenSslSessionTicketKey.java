package io.netty.handler.ssl;

import org.apache.tomcat.jni.SessionTicketKey;

/* loaded from: classes4.dex */
public final class OpenSslSessionTicketKey {
    public static final int AES_KEY_SIZE = 16;
    public static final int HMAC_KEY_SIZE = 16;
    public static final int NAME_SIZE = 16;
    public static final int TICKET_KEY_SIZE = 48;
    final SessionTicketKey a;

    public OpenSslSessionTicketKey(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this.a = new SessionTicketKey((byte[]) bArr.clone(), (byte[]) bArr2.clone(), (byte[]) bArr3.clone());
    }

    public byte[] name() {
        return (byte[]) this.a.getName().clone();
    }

    public byte[] hmacKey() {
        return (byte[]) this.a.getHmacKey().clone();
    }

    public byte[] aesKey() {
        return (byte[]) this.a.getAesKey().clone();
    }
}
