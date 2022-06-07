package com.google.zxing.qrcode.decoder;

import com.google.zxing.common.BitMatrix;

/* compiled from: DataMask.java */
/* loaded from: classes2.dex */
enum c {
    DATA_MASK_000 {
        @Override // com.google.zxing.qrcode.decoder.c
        boolean a(int i, int i2) {
            return ((i + i2) & 1) == 0;
        }
    },
    DATA_MASK_001 {
        @Override // com.google.zxing.qrcode.decoder.c
        boolean a(int i, int i2) {
            return (i & 1) == 0;
        }
    },
    DATA_MASK_010 {
        @Override // com.google.zxing.qrcode.decoder.c
        boolean a(int i, int i2) {
            return i2 % 3 == 0;
        }
    },
    DATA_MASK_011 {
        @Override // com.google.zxing.qrcode.decoder.c
        boolean a(int i, int i2) {
            return (i + i2) % 3 == 0;
        }
    },
    DATA_MASK_100 {
        @Override // com.google.zxing.qrcode.decoder.c
        boolean a(int i, int i2) {
            return (((i / 2) + (i2 / 3)) & 1) == 0;
        }
    },
    DATA_MASK_101 {
        @Override // com.google.zxing.qrcode.decoder.c
        boolean a(int i, int i2) {
            return (i * i2) % 6 == 0;
        }
    },
    DATA_MASK_110 {
        @Override // com.google.zxing.qrcode.decoder.c
        boolean a(int i, int i2) {
            return (i * i2) % 6 < 3;
        }
    },
    DATA_MASK_111 {
        @Override // com.google.zxing.qrcode.decoder.c
        boolean a(int i, int i2) {
            return (((i + i2) + ((i * i2) % 3)) & 1) == 0;
        }
    };

    abstract boolean a(int i2, int i3);

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(BitMatrix bitMatrix, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = 0; i4 < i2; i4++) {
                if (a(i3, i4)) {
                    bitMatrix.flip(i4, i3);
                }
            }
        }
    }
}
