package com.efs.sdk.base.a.f;

/* loaded from: classes.dex */
public final class a {
    public String a;
    public byte b;
    public int c = 0;
    public String d = "none";
    public int e = 1;
    long f = 0;
    int g = 1;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(String str, byte b) {
        this.b = (byte) 2;
        this.a = str;
        if (b <= 0 || 3 < b) {
            throw new IllegalArgumentException("log protocol flag invalid : ".concat(String.valueOf((int) b)));
        }
        this.b = b;
    }
}
