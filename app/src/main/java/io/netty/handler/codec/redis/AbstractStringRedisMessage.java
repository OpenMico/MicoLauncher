package io.netty.handler.codec.redis;

import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public abstract class AbstractStringRedisMessage implements RedisMessage {
    private final String a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AbstractStringRedisMessage(String str) {
        this.a = (String) ObjectUtil.checkNotNull(str, "content");
    }

    public final String content() {
        return this.a;
    }
}
