package com.google.android.exoplayer2.decoder;

import android.media.MediaCodec;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class CryptoInfo {
    private final MediaCodec.CryptoInfo a = new MediaCodec.CryptoInfo();
    @Nullable
    private final a b;
    public int clearBlocks;
    public int encryptedBlocks;
    @Nullable
    public byte[] iv;
    @Nullable
    public byte[] key;
    public int mode;
    @Nullable
    public int[] numBytesOfClearData;
    @Nullable
    public int[] numBytesOfEncryptedData;
    public int numSubSamples;

    public CryptoInfo() {
        this.b = Util.SDK_INT >= 24 ? new a(this.a) : null;
    }

    public void set(int i, int[] iArr, int[] iArr2, byte[] bArr, byte[] bArr2, int i2, int i3, int i4) {
        this.numSubSamples = i;
        this.numBytesOfClearData = iArr;
        this.numBytesOfEncryptedData = iArr2;
        this.key = bArr;
        this.iv = bArr2;
        this.mode = i2;
        this.encryptedBlocks = i3;
        this.clearBlocks = i4;
        MediaCodec.CryptoInfo cryptoInfo = this.a;
        cryptoInfo.numSubSamples = i;
        cryptoInfo.numBytesOfClearData = iArr;
        cryptoInfo.numBytesOfEncryptedData = iArr2;
        cryptoInfo.key = bArr;
        cryptoInfo.iv = bArr2;
        cryptoInfo.mode = i2;
        if (Util.SDK_INT >= 24) {
            ((a) Assertions.checkNotNull(this.b)).a(i3, i4);
        }
    }

    public MediaCodec.CryptoInfo getFrameworkCryptoInfo() {
        return this.a;
    }

    public void increaseClearDataFirstSubSampleBy(int i) {
        if (i != 0) {
            if (this.numBytesOfClearData == null) {
                this.numBytesOfClearData = new int[1];
                this.a.numBytesOfClearData = this.numBytesOfClearData;
            }
            int[] iArr = this.numBytesOfClearData;
            iArr[0] = iArr[0] + i;
        }
    }

    @RequiresApi(24)
    /* loaded from: classes.dex */
    private static final class a {
        private final MediaCodec.CryptoInfo a;
        private final MediaCodec.CryptoInfo.Pattern b;

        private a(MediaCodec.CryptoInfo cryptoInfo) {
            this.a = cryptoInfo;
            this.b = new MediaCodec.CryptoInfo.Pattern(0, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, int i2) {
            this.b.set(i, i2);
            this.a.setPattern(this.b);
        }
    }
}
