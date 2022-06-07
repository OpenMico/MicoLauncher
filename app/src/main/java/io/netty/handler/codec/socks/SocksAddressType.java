package io.netty.handler.codec.socks;

/* loaded from: classes4.dex */
public enum SocksAddressType {
    IPv4((byte) 1),
    DOMAIN((byte) 3),
    IPv6((byte) 4),
    UNKNOWN((byte) -1);
    
    private final byte b;

    SocksAddressType(byte b) {
        this.b = b;
    }

    @Deprecated
    public static SocksAddressType fromByte(byte b) {
        return valueOf(b);
    }

    public static SocksAddressType valueOf(byte b) {
        SocksAddressType[] values = values();
        for (SocksAddressType socksAddressType : values) {
            if (socksAddressType.b == b) {
                return socksAddressType;
            }
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.b;
    }
}
