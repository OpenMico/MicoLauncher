package com.mijiasdk.bleserver.protocol.bouncycastle.crypto.paddings;

import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.InvalidCipherTextException;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public interface BlockCipherPadding {
    int addPadding(byte[] bArr, int i);

    String getPaddingName();

    void init(SecureRandom secureRandom) throws IllegalArgumentException;

    int padCount(byte[] bArr) throws InvalidCipherTextException;
}
