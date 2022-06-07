package com.google.android.exoplayer2.upstream.crypto;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public final class AesFlushingCipher {
    private final Cipher a;
    private final int b;
    private final byte[] c;
    private final byte[] d;
    private int e;

    public AesFlushingCipher(int i, byte[] bArr, long j, long j2) {
        try {
            this.a = Cipher.getInstance("AES/CTR/NoPadding");
            this.b = this.a.getBlockSize();
            this.c = new byte[this.b];
            this.d = new byte[this.b];
            long j3 = j2 / this.b;
            int i2 = (int) (j2 % this.b);
            this.a.init(i, new SecretKeySpec(bArr, Util.splitAtFirst(this.a.getAlgorithm(), "/")[0]), new IvParameterSpec(a(j, j3)));
            if (i2 != 0) {
                updateInPlace(new byte[i2], 0, i2);
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateInPlace(byte[] bArr, int i, int i2) {
        update(bArr, i, i2, bArr, i);
    }

    public void update(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        int i4 = i;
        do {
            int i5 = this.e;
            if (i5 > 0) {
                bArr2[i3] = (byte) (bArr[i4] ^ this.d[this.b - i5]);
                i3++;
                i4++;
                this.e = i5 - 1;
                i2--;
            } else {
                int a = a(bArr, i4, i2, bArr2, i3);
                if (i2 != a) {
                    int i6 = i2 - a;
                    boolean z = true;
                    Assertions.checkState(i6 < this.b);
                    int i7 = i3 + a;
                    this.e = this.b - i6;
                    if (a(this.c, 0, this.e, this.d, 0) != this.b) {
                        z = false;
                    }
                    Assertions.checkState(z);
                    for (int i8 = 0; i8 < i6; i8++) {
                        i7++;
                        bArr2[i7] = this.d[i8];
                    }
                    return;
                }
                return;
            }
        } while (i2 != 0);
    }

    private int a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        try {
            return this.a.update(bArr, i, i2, bArr2, i3);
        } catch (ShortBufferException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] a(long j, long j2) {
        return ByteBuffer.allocate(16).putLong(j).putLong(j2).array();
    }
}
