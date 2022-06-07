package io.netty.handler.codec.redis;

import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;

/* loaded from: classes4.dex */
public enum RedisMessageType {
    SIMPLE_STRING((byte) 43, true),
    ERROR((byte) 45, true),
    INTEGER((byte) 58, true),
    BULK_STRING(BinaryMemcacheOpcodes.GATKQ, false),
    ARRAY_HEADER((byte) 42, false),
    ARRAY((byte) 42, false);
    
    private final boolean inline;
    private final byte value;

    RedisMessageType(byte b, boolean z) {
        this.value = b;
        this.inline = z;
    }

    public byte value() {
        return this.value;
    }

    public boolean isInline() {
        return this.inline;
    }

    public static RedisMessageType valueOf(byte b) {
        if (b == 36) {
            return BULK_STRING;
        }
        if (b == 45) {
            return ERROR;
        }
        if (b == 58) {
            return INTEGER;
        }
        switch (b) {
            case 42:
                return ARRAY_HEADER;
            case 43:
                return SIMPLE_STRING;
            default:
                throw new RedisCodecException("Unknown RedisMessageType: " + ((int) b));
        }
    }
}
