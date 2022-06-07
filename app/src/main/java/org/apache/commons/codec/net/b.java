package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;

/* compiled from: Utils.java */
/* loaded from: classes5.dex */
class b {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(byte b) throws DecoderException {
        int digit = Character.digit((char) b, 16);
        if (digit != -1) {
            return digit;
        }
        throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + ((int) b));
    }
}
