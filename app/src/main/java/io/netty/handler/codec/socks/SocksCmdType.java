package io.netty.handler.codec.socks;

/* loaded from: classes4.dex */
public enum SocksCmdType {
    CONNECT((byte) 1),
    BIND((byte) 2),
    UDP((byte) 3),
    UNKNOWN((byte) -1);
    
    private final byte b;

    SocksCmdType(byte b) {
        this.b = b;
    }

    @Deprecated
    public static SocksCmdType fromByte(byte b) {
        return valueOf(b);
    }

    public static SocksCmdType valueOf(byte b) {
        SocksCmdType[] values = values();
        for (SocksCmdType socksCmdType : values) {
            if (socksCmdType.b == b) {
                return socksCmdType;
            }
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.b;
    }
}
