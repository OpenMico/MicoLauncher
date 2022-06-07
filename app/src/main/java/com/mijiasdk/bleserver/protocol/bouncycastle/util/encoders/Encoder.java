package com.mijiasdk.bleserver.protocol.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public interface Encoder {
    int decode(String str, OutputStream outputStream) throws IOException;

    int decode(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException;

    int encode(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException;
}
