package com.mijiasdk.bleserver.protocol.bouncycastle.crypto.macs;

import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.CipherParameters;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.Mac;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.CBCBlockCipher;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.paddings.BlockCipherPadding;

/* loaded from: classes2.dex */
public class CBCBlockCipherMac implements Mac {
    private byte[] a;
    private byte[] b;
    private int c;
    private BlockCipher d;
    private BlockCipherPadding e;
    private int f;

    public CBCBlockCipherMac(BlockCipher blockCipher) {
        this(blockCipher, (blockCipher.getBlockSize() * 8) / 2, null);
    }

    public CBCBlockCipherMac(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
        this(blockCipher, (blockCipher.getBlockSize() * 8) / 2, blockCipherPadding);
    }

    public CBCBlockCipherMac(BlockCipher blockCipher, int i) {
        this(blockCipher, i, null);
    }

    public CBCBlockCipherMac(BlockCipher blockCipher, int i, BlockCipherPadding blockCipherPadding) {
        if (i % 8 == 0) {
            this.d = new CBCBlockCipher(blockCipher);
            this.e = blockCipherPadding;
            this.f = i / 8;
            this.a = new byte[blockCipher.getBlockSize()];
            this.b = new byte[blockCipher.getBlockSize()];
            this.c = 0;
            return;
        }
        throw new IllegalArgumentException("MAC size must be multiple of 8");
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.Mac
    public String getAlgorithmName() {
        return this.d.getAlgorithmName();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.Mac
    public void init(CipherParameters cipherParameters) {
        reset();
        this.d.init(true, cipherParameters);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.Mac
    public int getMacSize() {
        return this.f;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.Mac
    public void update(byte b) {
        int i = this.c;
        byte[] bArr = this.b;
        if (i == bArr.length) {
            this.d.processBlock(bArr, 0, this.a, 0);
            this.c = 0;
        }
        byte[] bArr2 = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        bArr2[i2] = b;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.Mac
    public void update(byte[] bArr, int i, int i2) {
        if (i2 >= 0) {
            int blockSize = this.d.getBlockSize();
            int i3 = this.c;
            int i4 = blockSize - i3;
            if (i2 > i4) {
                System.arraycopy(bArr, i, this.b, i3, i4);
                this.d.processBlock(this.b, 0, this.a, 0);
                this.c = 0;
                i2 -= i4;
                i += i4;
                while (i2 > blockSize) {
                    this.d.processBlock(bArr, i, this.a, 0);
                    i2 -= blockSize;
                    i += blockSize;
                }
            }
            System.arraycopy(bArr, i, this.b, this.c, i2);
            this.c += i2;
            return;
        }
        throw new IllegalArgumentException("Can't have a negative input length!");
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i) {
        int blockSize = this.d.getBlockSize();
        if (this.e == null) {
            while (true) {
                int i2 = this.c;
                if (i2 >= blockSize) {
                    break;
                }
                this.b[i2] = 0;
                this.c = i2 + 1;
            }
        } else {
            if (this.c == blockSize) {
                this.d.processBlock(this.b, 0, this.a, 0);
                this.c = 0;
            }
            this.e.addPadding(this.b, this.c);
        }
        this.d.processBlock(this.b, 0, this.a, 0);
        System.arraycopy(this.a, 0, bArr, i, this.f);
        reset();
        return this.f;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.Mac
    public void reset() {
        int i = 0;
        while (true) {
            byte[] bArr = this.b;
            if (i < bArr.length) {
                bArr[i] = 0;
                i++;
            } else {
                this.c = 0;
                this.d.reset();
                return;
            }
        }
    }
}
