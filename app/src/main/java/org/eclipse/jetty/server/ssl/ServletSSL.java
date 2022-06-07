package org.eclipse.jetty.server.ssl;

/* loaded from: classes5.dex */
public class ServletSSL {
    public static int deduceKeyLength(String str) {
        if (str == null) {
            return 0;
        }
        if (str.indexOf("WITH_AES_256_") >= 0) {
            return 256;
        }
        if (str.indexOf("WITH_RC4_128_") >= 0 || str.indexOf("WITH_AES_128_") >= 0) {
            return 128;
        }
        if (str.indexOf("WITH_RC4_40_") >= 0) {
            return 40;
        }
        if (str.indexOf("WITH_3DES_EDE_CBC_") >= 0) {
            return 168;
        }
        if (str.indexOf("WITH_IDEA_CBC_") >= 0) {
            return 128;
        }
        if (str.indexOf("WITH_RC2_CBC_40_") < 0 && str.indexOf("WITH_DES40_CBC_") < 0) {
            return str.indexOf("WITH_DES_CBC_") >= 0 ? 56 : 0;
        }
        return 40;
    }
}
