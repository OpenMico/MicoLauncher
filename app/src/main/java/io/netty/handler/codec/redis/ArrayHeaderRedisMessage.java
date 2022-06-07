package io.netty.handler.codec.redis;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class ArrayHeaderRedisMessage implements RedisMessage {
    private final long a;

    public ArrayHeaderRedisMessage(long j) {
        if (j >= -1) {
            this.a = j;
            return;
        }
        throw new RedisCodecException("length: " + j + " (expected: >= -1)");
    }

    public final long length() {
        return this.a;
    }

    public boolean isNull() {
        return this.a == -1;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[length=" + this.a + ']';
    }
}
