package com.google.zxing.datamatrix.encoder;

import com.xiaomi.mi_connect_sdk.BuildConfig;

/* compiled from: DataMatrixSymbolInfo144.java */
/* loaded from: classes2.dex */
final class d extends SymbolInfo {
    @Override // com.google.zxing.datamatrix.encoder.SymbolInfo
    public int getDataLengthForInterleavedBlock(int i) {
        if (i <= 8) {
            return BuildConfig.MiConnectVersionBuild;
        }
        return 155;
    }

    @Override // com.google.zxing.datamatrix.encoder.SymbolInfo
    public int getInterleavedBlockCount() {
        return 10;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public d() {
        super(false, 1558, 620, 22, 22, 36, -1, 62);
    }
}
