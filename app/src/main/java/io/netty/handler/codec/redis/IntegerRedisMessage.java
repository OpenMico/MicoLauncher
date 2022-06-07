package io.netty.handler.codec.redis;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public final class IntegerRedisMessage implements RedisMessage {
    private final long a;

    public IntegerRedisMessage(long j) {
        this.a = j;
    }

    public long value() {
        return this.a;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[value=" + this.a + ']';
    }
}
