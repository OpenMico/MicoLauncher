package io.netty.handler.codec.redis;

import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ArrayRedisMessage extends AbstractReferenceCounted implements RedisMessage {
    private final List<RedisMessage> a;
    public static final ArrayRedisMessage NULL_INSTANCE = new ArrayRedisMessage() { // from class: io.netty.handler.codec.redis.ArrayRedisMessage.1
        /* renamed from: a */
        public ArrayRedisMessage retain() {
            return this;
        }

        /* renamed from: a */
        public ArrayRedisMessage retain(int i) {
            return this;
        }

        /* renamed from: b */
        public ArrayRedisMessage touch() {
            return this;
        }

        @Override // io.netty.handler.codec.redis.ArrayRedisMessage
        public boolean isNull() {
            return true;
        }

        @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
        public boolean release() {
            return false;
        }

        @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
        public boolean release(int i) {
            return false;
        }

        @Override // io.netty.handler.codec.redis.ArrayRedisMessage
        public String toString() {
            return "NullArrayRedisMessage";
        }

        @Override // io.netty.handler.codec.redis.ArrayRedisMessage, io.netty.util.ReferenceCounted
        public ArrayRedisMessage touch(Object obj) {
            return this;
        }
    };
    public static final ArrayRedisMessage EMPTY_INSTANCE = new ArrayRedisMessage() { // from class: io.netty.handler.codec.redis.ArrayRedisMessage.2
        /* renamed from: a */
        public ArrayRedisMessage retain() {
            return this;
        }

        /* renamed from: a */
        public ArrayRedisMessage retain(int i) {
            return this;
        }

        /* renamed from: b */
        public ArrayRedisMessage touch() {
            return this;
        }

        @Override // io.netty.handler.codec.redis.ArrayRedisMessage
        public boolean isNull() {
            return false;
        }

        @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
        public boolean release() {
            return false;
        }

        @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
        public boolean release(int i) {
            return false;
        }

        @Override // io.netty.handler.codec.redis.ArrayRedisMessage
        public String toString() {
            return "EmptyArrayRedisMessage";
        }

        @Override // io.netty.handler.codec.redis.ArrayRedisMessage, io.netty.util.ReferenceCounted
        public ArrayRedisMessage touch(Object obj) {
            return this;
        }
    };

    public boolean isNull() {
        return false;
    }

    private ArrayRedisMessage() {
        this.a = Collections.emptyList();
    }

    public ArrayRedisMessage(List<RedisMessage> list) {
        this.a = (List) ObjectUtil.checkNotNull(list, "children");
    }

    public final List<RedisMessage> children() {
        return this.a;
    }

    @Override // io.netty.util.AbstractReferenceCounted
    protected void deallocate() {
        for (RedisMessage redisMessage : this.a) {
            ReferenceCountUtil.release(redisMessage);
        }
    }

    @Override // io.netty.util.ReferenceCounted
    public ArrayRedisMessage touch(Object obj) {
        for (RedisMessage redisMessage : this.a) {
            ReferenceCountUtil.touch(redisMessage);
        }
        return this;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[children=" + this.a.size() + ']';
    }
}
