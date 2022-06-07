package io.netty.handler.codec.socks;

/* loaded from: classes4.dex */
public enum SocksAuthStatus {
    SUCCESS((byte) 0),
    FAILURE((byte) -1);
    
    private final byte b;

    SocksAuthStatus(byte b) {
        this.b = b;
    }

    @Deprecated
    public static SocksAuthStatus fromByte(byte b) {
        return valueOf(b);
    }

    public static SocksAuthStatus valueOf(byte b) {
        SocksAuthStatus[] values = values();
        for (SocksAuthStatus socksAuthStatus : values) {
            if (socksAuthStatus.b == b) {
                return socksAuthStatus;
            }
        }
        return FAILURE;
    }

    public byte byteValue() {
        return this.b;
    }
}
