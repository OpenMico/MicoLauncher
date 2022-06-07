package io.netty.handler.codec.redis;

import io.netty.handler.codec.CodecException;

/* loaded from: classes4.dex */
public final class RedisCodecException extends CodecException {
    private static final long serialVersionUID = 5570454251549268063L;

    public RedisCodecException(String str) {
        super(str);
    }

    public RedisCodecException(Throwable th) {
        super(th);
    }
}
