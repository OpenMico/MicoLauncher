package com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes;

import com.google.common.primitives.SignedBytes;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.CipherParameters;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.DataLengthException;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.InvalidCipherTextException;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.OutputLengthException;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.macs.CBCBlockCipherMac;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.params.AEADParameters;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.params.ParametersWithIV;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import java.io.ByteArrayOutputStream;

/* loaded from: classes2.dex */
public class CCMBlockCipher implements AEADBlockCipher {
    private BlockCipher a;
    private int b;
    private boolean c;
    private byte[] d;
    private byte[] e;
    private int f;
    private CipherParameters g;
    private byte[] h;
    private a i = new a();
    private a j = new a();

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public int getUpdateOutputSize(int i) {
        return 0;
    }

    public CCMBlockCipher(BlockCipher blockCipher) {
        this.a = blockCipher;
        this.b = blockCipher.getBlockSize();
        int i = this.b;
        this.h = new byte[i];
        if (i != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public BlockCipher getUnderlyingCipher() {
        return this.a;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        CipherParameters cipherParameters2;
        this.c = z;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            this.d = aEADParameters.getNonce();
            this.e = aEADParameters.getAssociatedText();
            this.f = aEADParameters.getMacSize() / 8;
            cipherParameters2 = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.d = parametersWithIV.getIV();
            this.e = null;
            this.f = this.h.length / 2;
            cipherParameters2 = parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to CCM: " + cipherParameters.getClass().getName());
        }
        if (cipherParameters2 != null) {
            this.g = cipherParameters2;
        }
        byte[] bArr = this.d;
        if (bArr == null || bArr.length < 7 || bArr.length > 13) {
            throw new IllegalArgumentException("nonce must have length from 7 to 13 octets");
        }
        reset();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public String getAlgorithmName() {
        return this.a.getAlgorithmName() + "/CCM";
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public void processAADByte(byte b) {
        this.i.write(b);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public void processAADBytes(byte[] bArr, int i, int i2) {
        this.i.write(bArr, i, i2);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public int processByte(byte b, byte[] bArr, int i) throws DataLengthException, IllegalStateException {
        this.j.write(b);
        return 0;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException, IllegalStateException {
        if (bArr.length >= i + i2) {
            this.j.write(bArr, i, i2);
            return 0;
        }
        throw new DataLengthException("Input buffer too short");
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public int doFinal(byte[] bArr, int i) throws IllegalStateException, InvalidCipherTextException {
        int processPacket = processPacket(this.j.a(), 0, this.j.size(), bArr, i);
        reset();
        return processPacket;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public void reset() {
        this.a.reset();
        this.i.reset();
        this.j.reset();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public byte[] getMac() {
        byte[] bArr = new byte[this.f];
        System.arraycopy(this.h, 0, bArr, 0, bArr.length);
        return bArr;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.AEADBlockCipher
    public int getOutputSize(int i) {
        int size = i + this.j.size();
        if (this.c) {
            return size + this.f;
        }
        int i2 = this.f;
        if (size < i2) {
            return 0;
        }
        return size - i2;
    }

    public byte[] processPacket(byte[] bArr, int i, int i2) throws IllegalStateException, InvalidCipherTextException {
        byte[] bArr2;
        if (this.c) {
            bArr2 = new byte[this.f + i2];
        } else {
            int i3 = this.f;
            if (i2 >= i3) {
                bArr2 = new byte[i2 - i3];
            } else {
                throw new InvalidCipherTextException("data too short");
            }
        }
        processPacket(bArr, i, i2, bArr2, 0);
        return bArr2;
    }

    public int processPacket(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalStateException, InvalidCipherTextException, DataLengthException {
        int i4;
        if (this.g != null) {
            int length = 15 - this.d.length;
            if (length >= 4 || i2 < (1 << (length * 8))) {
                byte[] bArr3 = new byte[this.b];
                bArr3[0] = (byte) ((length - 1) & 7);
                byte[] bArr4 = this.d;
                System.arraycopy(bArr4, 0, bArr3, 1, bArr4.length);
                SICBlockCipher sICBlockCipher = new SICBlockCipher(this.a);
                sICBlockCipher.init(this.c, new ParametersWithIV(this.g, bArr3));
                if (this.c) {
                    int i5 = this.f + i2;
                    if (bArr2.length >= i5 + i3) {
                        a(bArr, i, i2, this.h);
                        byte[] bArr5 = new byte[this.b];
                        sICBlockCipher.processBlock(this.h, 0, bArr5, 0);
                        int i6 = i;
                        int i7 = i3;
                        while (true) {
                            int i8 = i + i2;
                            int i9 = this.b;
                            if (i6 < i8 - i9) {
                                sICBlockCipher.processBlock(bArr, i6, bArr2, i7);
                                int i10 = this.b;
                                i7 += i10;
                                i6 += i10;
                            } else {
                                byte[] bArr6 = new byte[i9];
                                int i11 = i8 - i6;
                                System.arraycopy(bArr, i6, bArr6, 0, i11);
                                sICBlockCipher.processBlock(bArr6, 0, bArr6, 0);
                                System.arraycopy(bArr6, 0, bArr2, i7, i11);
                                System.arraycopy(bArr5, 0, bArr2, i3 + i2, this.f);
                                return i5;
                            }
                        }
                    } else {
                        throw new OutputLengthException("Output buffer too short.");
                    }
                } else {
                    int i12 = this.f;
                    if (i2 >= i12) {
                        int i13 = i2 - i12;
                        if (bArr2.length >= i13 + i3) {
                            int i14 = i + i13;
                            System.arraycopy(bArr, i14, this.h, 0, i12);
                            byte[] bArr7 = this.h;
                            sICBlockCipher.processBlock(bArr7, 0, bArr7, 0);
                            int i15 = this.f;
                            while (true) {
                                byte[] bArr8 = this.h;
                                if (i15 == bArr8.length) {
                                    break;
                                }
                                bArr8[i15] = 0;
                                i15++;
                            }
                            int i16 = i;
                            int i17 = i3;
                            while (true) {
                                i4 = this.b;
                                if (i16 >= i14 - i4) {
                                    break;
                                }
                                sICBlockCipher.processBlock(bArr, i16, bArr2, i17);
                                int i18 = this.b;
                                i17 += i18;
                                i16 += i18;
                            }
                            byte[] bArr9 = new byte[i4];
                            int i19 = i13 - (i16 - i);
                            System.arraycopy(bArr, i16, bArr9, 0, i19);
                            sICBlockCipher.processBlock(bArr9, 0, bArr9, 0);
                            System.arraycopy(bArr9, 0, bArr2, i17, i19);
                            byte[] bArr10 = new byte[this.b];
                            a(bArr2, i3, i13, bArr10);
                            if (Arrays.constantTimeAreEqual(this.h, bArr10)) {
                                return i13;
                            }
                            throw new InvalidCipherTextException("mac check in CCM failed");
                        }
                        throw new OutputLengthException("Output buffer too short.");
                    }
                    throw new InvalidCipherTextException("data too short");
                }
            } else {
                throw new IllegalStateException("CCM packet too large for choice of q.");
            }
        } else {
            throw new IllegalStateException("CCM cipher unitialized.");
        }
    }

    private int a(byte[] bArr, int i, int i2, byte[] bArr2) {
        CBCBlockCipherMac cBCBlockCipherMac = new CBCBlockCipherMac(this.a, this.f * 8);
        cBCBlockCipherMac.init(this.g);
        byte[] bArr3 = new byte[16];
        if (b()) {
            bArr3[0] = (byte) (bArr3[0] | SignedBytes.MAX_POWER_OF_TWO);
        }
        int i3 = 2;
        bArr3[0] = (byte) (bArr3[0] | ((((cBCBlockCipherMac.getMacSize() - 2) / 2) & 7) << 3));
        byte b = bArr3[0];
        byte[] bArr4 = this.d;
        bArr3[0] = (byte) (b | (((15 - bArr4.length) - 1) & 7));
        System.arraycopy(bArr4, 0, bArr3, 1, bArr4.length);
        int i4 = i2;
        int i5 = 1;
        while (i4 > 0) {
            bArr3[bArr3.length - i5] = (byte) (i4 & 255);
            i4 >>>= 8;
            i5++;
        }
        cBCBlockCipherMac.update(bArr3, 0, bArr3.length);
        if (b()) {
            int a2 = a();
            if (a2 < 65280) {
                cBCBlockCipherMac.update((byte) (a2 >> 8));
                cBCBlockCipherMac.update((byte) a2);
            } else {
                cBCBlockCipherMac.update((byte) -1);
                cBCBlockCipherMac.update((byte) -2);
                cBCBlockCipherMac.update((byte) (a2 >> 24));
                cBCBlockCipherMac.update((byte) (a2 >> 16));
                cBCBlockCipherMac.update((byte) (a2 >> 8));
                cBCBlockCipherMac.update((byte) a2);
                i3 = 6;
            }
            byte[] bArr5 = this.e;
            if (bArr5 != null) {
                cBCBlockCipherMac.update(bArr5, 0, bArr5.length);
            }
            if (this.i.size() > 0) {
                cBCBlockCipherMac.update(this.i.a(), 0, this.i.size());
            }
            int i6 = (i3 + a2) % 16;
            if (i6 != 0) {
                while (i6 != 16) {
                    cBCBlockCipherMac.update((byte) 0);
                    i6++;
                }
            }
        }
        cBCBlockCipherMac.update(bArr, i, i2);
        return cBCBlockCipherMac.doFinal(bArr2, 0);
    }

    private int a() {
        int size = this.i.size();
        byte[] bArr = this.e;
        return size + (bArr == null ? 0 : bArr.length);
    }

    private boolean b() {
        return a() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class a extends ByteArrayOutputStream {
        public a() {
        }

        public byte[] a() {
            return this.buf;
        }
    }
}
