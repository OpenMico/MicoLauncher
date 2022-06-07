package com.xiaomi.micolauncher.common.utils.HeatShrink;

/* loaded from: classes3.dex */
public class Result {
    int a;
    int b;
    int c;
    byte[] d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Result a(byte[] bArr, int i, int i2) {
        this.d = bArr;
        this.a = i;
        this.b = i2;
        this.c = i + i2;
        return this;
    }
}
