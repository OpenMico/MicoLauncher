package org.conscrypt;

/* loaded from: classes5.dex */
class NativeCryptoJni {
    public static void init() {
        if ("com.google.android.gms.org.conscrypt".equals(NativeCrypto.class.getPackage().getName())) {
            System.loadLibrary("conscrypt_gmscore_jni");
        } else {
            System.loadLibrary("conscrypt_jni");
        }
    }

    private NativeCryptoJni() {
    }
}
