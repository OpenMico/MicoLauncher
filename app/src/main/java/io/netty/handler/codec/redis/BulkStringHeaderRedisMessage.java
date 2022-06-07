package io.netty.handler.codec.redis;

/* loaded from: classes4.dex */
public class BulkStringHeaderRedisMessage implements RedisMessage {
    private final int a;

    public BulkStringHeaderRedisMessage(int i) {
        if (i > 0) {
            this.a = i;
            return;
        }
        throw new RedisCodecException("bulkStringLength: " + i + " (expected: > 0)");
    }

    public final int bulkStringLength() {
        return this.a;
    }

    public boolean isNull() {
        return this.a == -1;
    }
}
