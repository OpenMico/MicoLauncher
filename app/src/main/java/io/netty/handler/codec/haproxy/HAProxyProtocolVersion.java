package io.netty.handler.codec.haproxy;

/* loaded from: classes4.dex */
public enum HAProxyProtocolVersion {
    V1((byte) 16),
    V2((byte) 32);
    
    private final byte byteValue;

    HAProxyProtocolVersion(byte b) {
        this.byteValue = b;
    }

    public static HAProxyProtocolVersion valueOf(byte b) {
        int i = b & (-16);
        byte b2 = (byte) i;
        if (b2 == 16) {
            return V1;
        }
        if (b2 == 32) {
            return V2;
        }
        throw new IllegalArgumentException("unknown version: " + i);
    }

    public byte byteValue() {
        return this.byteValue;
    }
}
