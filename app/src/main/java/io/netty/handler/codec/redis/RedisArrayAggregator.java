package io.netty.handler.codec.redis;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/* loaded from: classes4.dex */
public final class RedisArrayAggregator extends MessageToMessageDecoder<RedisMessage> {
    private final Deque<a> a = new ArrayDeque(4);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageDecoder
    public /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, RedisMessage redisMessage, List list) throws Exception {
        decode2(channelHandlerContext, redisMessage, (List<Object>) list);
    }

    /* renamed from: decode  reason: avoid collision after fix types in other method */
    protected void decode2(ChannelHandlerContext channelHandlerContext, RedisMessage redisMessage, List<Object> list) throws Exception {
        if (redisMessage instanceof ArrayHeaderRedisMessage) {
            redisMessage = a((ArrayHeaderRedisMessage) redisMessage);
            if (redisMessage == null) {
                return;
            }
        } else {
            ReferenceCountUtil.retain(redisMessage);
        }
        while (!this.a.isEmpty()) {
            a peek = this.a.peek();
            peek.b.add(redisMessage);
            if (peek.b.size() == peek.a) {
                redisMessage = new ArrayRedisMessage(peek.b);
                this.a.pop();
            } else {
                return;
            }
        }
        list.add(redisMessage);
    }

    private RedisMessage a(ArrayHeaderRedisMessage arrayHeaderRedisMessage) {
        if (arrayHeaderRedisMessage.isNull()) {
            return ArrayRedisMessage.NULL_INSTANCE;
        }
        if (arrayHeaderRedisMessage.length() == 0) {
            return ArrayRedisMessage.EMPTY_INSTANCE;
        }
        if (arrayHeaderRedisMessage.length() <= 0) {
            throw new CodecException("bad length: " + arrayHeaderRedisMessage.length());
        } else if (arrayHeaderRedisMessage.length() <= 2147483647L) {
            this.a.push(new a((int) arrayHeaderRedisMessage.length()));
            return null;
        } else {
            throw new CodecException("this codec doesn't support longer length than 2147483647");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a {
        private final int a;
        private final List<RedisMessage> b;

        a(int i) {
            this.a = i;
            this.b = new ArrayList(i);
        }
    }
}
