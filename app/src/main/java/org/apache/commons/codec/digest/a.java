package org.apache.commons.codec.digest;

import java.util.Random;

/* compiled from: B64.java */
/* loaded from: classes5.dex */
class a {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(byte b, byte b2, byte b3, int i, StringBuilder sb) {
        int i2 = ((b << 16) & 16777215) | ((b2 << 8) & 65535) | (b3 & 255);
        while (true) {
            i--;
            if (i > 0) {
                sb.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(i2 & 63));
                i2 >>= 6;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 1; i2 <= i; i2++) {
            sb.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(new Random().nextInt(64)));
        }
        return sb.toString();
    }
}
