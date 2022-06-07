package com.mijiasdk.bleserver.protocol.bouncycastle.crypto;

/* loaded from: classes2.dex */
public abstract class StreamBlockCipher implements BlockCipher, StreamCipher {
    private final BlockCipher a;

    protected abstract byte calculateByte(byte b);

    /* JADX INFO: Access modifiers changed from: protected */
    public StreamBlockCipher(BlockCipher blockCipher) {
        this.a = blockCipher;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.a;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.StreamCipher
    public final byte returnByte(byte b) {
        return calculateByte(b);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.StreamCipher
    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException {
        if (i3 + i2 <= bArr2.length) {
            int i4 = i + i2;
            if (i4 <= bArr.length) {
                while (i < i4) {
                    i3++;
                    i++;
                    bArr2[i3] = calculateByte(bArr[i]);
                }
                return i2;
            }
            throw new DataLengthException("input buffer too small");
        }
        throw new DataLengthException("output buffer too short");
    }
}
