package com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes;

import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.CipherParameters;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.DataLengthException;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.SkippingStreamCipher;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.StreamBlockCipher;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.params.ParametersWithIV;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Pack;

/* loaded from: classes2.dex */
public class SICBlockCipher extends StreamBlockCipher implements SkippingStreamCipher {
    private final BlockCipher a;
    private final int b;
    private byte[] c;
    private byte[] d;
    private byte[] e;
    private int f = 0;

    public SICBlockCipher(BlockCipher blockCipher) {
        super(blockCipher);
        this.a = blockCipher;
        this.b = this.a.getBlockSize();
        int i = this.b;
        this.c = new byte[i];
        this.d = new byte[i];
        this.e = new byte[i];
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.c = Arrays.clone(parametersWithIV.getIV());
            int i = this.b;
            if (i >= this.c.length) {
                int i2 = 8;
                if (8 > i / 2) {
                    i2 = i / 2;
                }
                if (this.b - this.c.length <= i2) {
                    if (parametersWithIV.getParameters() != null) {
                        this.a.init(true, parametersWithIV.getParameters());
                    }
                    reset();
                    return;
                }
                throw new IllegalArgumentException("CTR/SIC mode requires IV of at least: " + (this.b - i2) + " bytes.");
            }
            throw new IllegalArgumentException("CTR/SIC mode requires IV no greater than: " + this.b + " bytes.");
        }
        throw new IllegalArgumentException("CTR/SIC mode requires ParametersWithIV");
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.a.getAlgorithmName() + "/SIC";
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.a.getBlockSize();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws DataLengthException, IllegalStateException {
        processBytes(bArr, i, this.b, bArr2, i2);
        return this.b;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.StreamBlockCipher
    protected byte calculateByte(byte b) throws DataLengthException, IllegalStateException {
        int i = this.f;
        if (i == 0) {
            this.a.processBlock(this.d, 0, this.e, 0);
            byte[] bArr = this.e;
            int i2 = this.f;
            this.f = i2 + 1;
            return (byte) (b ^ bArr[i2]);
        }
        byte[] bArr2 = this.e;
        this.f = i + 1;
        byte b2 = (byte) (b ^ bArr2[i]);
        if (this.f == this.d.length) {
            this.f = 0;
            a(0);
            a();
        }
        return b2;
    }

    private void a() {
        if (this.c.length < this.b) {
            int i = 0;
            while (true) {
                byte[] bArr = this.c;
                if (i == bArr.length) {
                    return;
                }
                if (this.d[i] == bArr[i]) {
                    i++;
                } else {
                    throw new IllegalStateException("Counter in CTR/SIC mode out of range.");
                }
            }
        }
    }

    private void a(int i) {
        byte b;
        int length = this.d.length - i;
        do {
            length--;
            if (length >= 0) {
                byte[] bArr = this.d;
                b = (byte) (bArr[length] + 1);
                bArr[length] = b;
            } else {
                return;
            }
        } while (b == 0);
    }

    private void b(int i) {
        byte[] bArr = this.d;
        byte b = bArr[bArr.length - 1];
        int length = bArr.length - 1;
        bArr[length] = (byte) (bArr[length] + i);
        if (b != 0 && bArr[bArr.length - 1] < b) {
            a(1);
        }
    }

    private void c(int i) {
        byte b;
        int length = this.d.length - i;
        do {
            length--;
            if (length >= 0) {
                byte[] bArr = this.d;
                b = (byte) (bArr[length] - 1);
                bArr[length] = b;
            } else {
                return;
            }
        } while (b == -1);
    }

    private void a(long j) {
        long j2;
        long j3;
        int i = 5;
        if (j >= 0) {
            long j4 = (this.f + j) / this.b;
            if (j4 > 255) {
                j3 = j4;
                while (i >= 1) {
                    long j5 = 1 << (i * 8);
                    while (j3 >= j5) {
                        a(i);
                        j3 -= j5;
                    }
                    i--;
                }
            } else {
                j3 = j4;
            }
            b((int) j3);
            this.f = (int) ((j + this.f) - (this.b * j4));
            return;
        }
        long j6 = ((-j) - this.f) / this.b;
        if (j6 > 255) {
            j2 = j6;
            while (i >= 1) {
                long j7 = 1 << (i * 8);
                while (j2 > j7) {
                    c(i);
                    j2 -= j7;
                }
                i--;
            }
        } else {
            j2 = j6;
        }
        for (long j8 = 0; j8 != j2; j8++) {
            c(0);
        }
        int i2 = (int) (this.f + j + (this.b * j6));
        if (i2 >= 0) {
            this.f = 0;
            return;
        }
        c(0);
        this.f = this.b + i2;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public void reset() {
        Arrays.fill(this.d, (byte) 0);
        byte[] bArr = this.c;
        System.arraycopy(bArr, 0, this.d, 0, bArr.length);
        this.a.reset();
        this.f = 0;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.SkippingCipher
    public long skip(long j) {
        a(j);
        a();
        this.a.processBlock(this.d, 0, this.e, 0);
        return j;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.SkippingCipher
    public long seekTo(long j) {
        reset();
        return skip(j);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.SkippingCipher
    public long getPosition() {
        int i;
        byte[] bArr = this.d;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        for (int length = bArr2.length - 1; length >= 1; length--) {
            byte[] bArr3 = this.c;
            if (length < bArr3.length) {
                i = (bArr2[length] & 255) - (bArr3[length] & 255);
            } else {
                i = bArr2[length] & 255;
            }
            if (i < 0) {
                int i2 = length - 1;
                bArr2[i2] = (byte) (bArr2[i2] - 1);
                i += 256;
            }
            bArr2[length] = (byte) i;
        }
        return (Pack.bigEndianToLong(bArr2, bArr2.length - 8) * this.b) + this.f;
    }
}
