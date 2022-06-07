package io.netty.handler.codec.socks;

/* loaded from: classes4.dex */
public enum SocksProtocolVersion {
    SOCKS4a((byte) 4),
    SOCKS5((byte) 5),
    UNKNOWN((byte) -1);
    
    private final byte b;

    SocksProtocolVersion(byte b) {
        this.b = b;
    }

    @Deprecated
    public static SocksProtocolVersion fromByte(byte b) {
        return valueOf(b);
    }

    public static SocksProtocolVersion valueOf(byte b) {
        SocksProtocolVersion[] values = values();
        for (SocksProtocolVersion socksProtocolVersion : values) {
            if (socksProtocolVersion.b == b) {
                return socksProtocolVersion;
            }
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.b;
    }
}
