package io.netty.handler.codec.spdy;

/* loaded from: classes4.dex */
public enum SpdyVersion {
    SPDY_3_1(3, 1);
    
    private final int minorVersion;
    private final int version;

    SpdyVersion(int i, int i2) {
        this.version = i;
        this.minorVersion = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        return this.version;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return this.minorVersion;
    }
}
