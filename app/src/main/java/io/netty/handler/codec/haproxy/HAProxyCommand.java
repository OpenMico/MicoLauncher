package io.netty.handler.codec.haproxy;

/* loaded from: classes4.dex */
public enum HAProxyCommand {
    LOCAL((byte) 0),
    PROXY((byte) 1);
    
    private final byte byteValue;

    HAProxyCommand(byte b) {
        this.byteValue = b;
    }

    public static HAProxyCommand valueOf(byte b) {
        int i = b & 15;
        switch ((byte) i) {
            case 0:
                return LOCAL;
            case 1:
                return PROXY;
            default:
                throw new IllegalArgumentException("unknown command: " + i);
        }
    }

    public byte byteValue() {
        return this.byteValue;
    }
}
