package com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes;

import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.CipherParameters;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.DataLengthException;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.params.ParametersWithIV;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;

/* loaded from: classes2.dex */
public class CBCBlockCipher implements BlockCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;
    private BlockCipher e;
    private boolean f;

    public CBCBlockCipher(BlockCipher blockCipher) {
        this.e = null;
        this.e = blockCipher;
        this.d = blockCipher.getBlockSize();
        int i = this.d;
        this.a = new byte[i];
        this.b = new byte[i];
        this.c = new byte[i];
    }

    public BlockCipher getUnderlyingCipher() {
        return this.e;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        boolean z2 = this.f;
        this.f = z;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv.length == this.d) {
                System.arraycopy(iv, 0, this.a, 0, iv.length);
                reset();
                if (parametersWithIV.getParameters() != null) {
                    this.e.init(z, parametersWithIV.getParameters());
                } else if (z2 != z) {
                    throw new IllegalArgumentException("cannot change encrypting state without providing key.");
                }
            } else {
                throw new IllegalArgumentException("initialisation vector must be the same length as block size");
            }
        } else {
            reset();
            if (cipherParameters != null) {
                this.e.init(z, cipherParameters);
            } else if (z2 != z) {
                throw new IllegalArgumentException("cannot change encrypting state without providing key.");
            }
        }
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.e.getAlgorithmName() + "/CBC";
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.e.getBlockSize();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws DataLengthException, IllegalStateException {
        return this.f ? a(bArr, i, bArr2, i2) : b(bArr, i, bArr2, i2);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public void reset() {
        byte[] bArr = this.a;
        System.arraycopy(bArr, 0, this.b, 0, bArr.length);
        Arrays.fill(this.c, (byte) 0);
        this.e.reset();
    }

    private int a(byte[] bArr, int i, byte[] bArr2, int i2) throws DataLengthException, IllegalStateException {
        if (this.d + i <= bArr.length) {
            for (int i3 = 0; i3 < this.d; i3++) {
                byte[] bArr3 = this.b;
                bArr3[i3] = (byte) (bArr3[i3] ^ bArr[i + i3]);
            }
            int processBlock = this.e.processBlock(this.b, 0, bArr2, i2);
            byte[] bArr4 = this.b;
            System.arraycopy(bArr2, i2, bArr4, 0, bArr4.length);
            return processBlock;
        }
        throw new DataLengthException("input buffer too short");
    }

    private int b(byte[] bArr, int i, byte[] bArr2, int i2) throws DataLengthException, IllegalStateException {
        int i3 = this.d;
        if (i + i3 <= bArr.length) {
            System.arraycopy(bArr, i, this.c, 0, i3);
            int processBlock = this.e.processBlock(bArr, i, bArr2, i2);
            for (int i4 = 0; i4 < this.d; i4++) {
                int i5 = i2 + i4;
                bArr2[i5] = (byte) (bArr2[i5] ^ this.b[i4]);
            }
            byte[] bArr3 = this.b;
            this.b = this.c;
            this.c = bArr3;
            return processBlock;
        }
        throw new DataLengthException("input buffer too short");
    }
}
