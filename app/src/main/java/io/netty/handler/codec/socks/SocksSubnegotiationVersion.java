package io.netty.handler.codec.socks;

/* loaded from: classes4.dex */
public enum SocksSubnegotiationVersion {
    AUTH_PASSWORD((byte) 1),
    UNKNOWN((byte) -1);
    
    private final byte b;

    SocksSubnegotiationVersion(byte b) {
        this.b = b;
    }

    @Deprecated
    public static SocksSubnegotiationVersion fromByte(byte b) {
        return valueOf(b);
    }

    public static SocksSubnegotiationVersion valueOf(byte b) {
        SocksSubnegotiationVersion[] values = values();
        for (SocksSubnegotiationVersion socksSubnegotiationVersion : values) {
            if (socksSubnegotiationVersion.b == b) {
                return socksSubnegotiationVersion;
            }
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.b;
    }
}
