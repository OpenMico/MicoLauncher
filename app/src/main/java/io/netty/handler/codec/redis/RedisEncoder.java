package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

/* loaded from: classes4.dex */
public class RedisEncoder extends MessageToMessageEncoder<RedisMessage> {
    private final RedisMessagePool a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, RedisMessage redisMessage, List list) throws Exception {
        encode2(channelHandlerContext, redisMessage, (List<Object>) list);
    }

    public RedisEncoder() {
        this(FixedRedisMessagePool.INSTANCE);
    }

    public RedisEncoder(RedisMessagePool redisMessagePool) {
        this.a = (RedisMessagePool) ObjectUtil.checkNotNull(redisMessagePool, "messagePool");
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, RedisMessage redisMessage, List<Object> list) throws Exception {
        try {
            a(channelHandlerContext.alloc(), redisMessage, list);
        } catch (CodecException e) {
            throw e;
        } catch (Exception e2) {
            throw new CodecException(e2);
        }
    }

    private void a(ByteBufAllocator byteBufAllocator, RedisMessage redisMessage, List<Object> list) {
        if (redisMessage instanceof SimpleStringRedisMessage) {
            a(byteBufAllocator, (SimpleStringRedisMessage) redisMessage, list);
        } else if (redisMessage instanceof ErrorRedisMessage) {
            a(byteBufAllocator, (ErrorRedisMessage) redisMessage, list);
        } else if (redisMessage instanceof IntegerRedisMessage) {
            a(byteBufAllocator, (IntegerRedisMessage) redisMessage, list);
        } else if (redisMessage instanceof FullBulkStringRedisMessage) {
            a(byteBufAllocator, (FullBulkStringRedisMessage) redisMessage, list);
        } else if (redisMessage instanceof BulkStringRedisContent) {
            a(byteBufAllocator, (BulkStringRedisContent) redisMessage, list);
        } else if (redisMessage instanceof BulkStringHeaderRedisMessage) {
            a(byteBufAllocator, (BulkStringHeaderRedisMessage) redisMessage, list);
        } else if (redisMessage instanceof ArrayHeaderRedisMessage) {
            a(byteBufAllocator, (ArrayHeaderRedisMessage) redisMessage, list);
        } else if (redisMessage instanceof ArrayRedisMessage) {
            a(byteBufAllocator, (ArrayRedisMessage) redisMessage, list);
        } else {
            throw new CodecException("unknown message type: " + redisMessage);
        }
    }

    private static void a(ByteBufAllocator byteBufAllocator, SimpleStringRedisMessage simpleStringRedisMessage, List<Object> list) {
        a(byteBufAllocator, RedisMessageType.SIMPLE_STRING.value(), simpleStringRedisMessage.content(), list);
    }

    private static void a(ByteBufAllocator byteBufAllocator, ErrorRedisMessage errorRedisMessage, List<Object> list) {
        a(byteBufAllocator, RedisMessageType.ERROR.value(), errorRedisMessage.content(), list);
    }

    private static void a(ByteBufAllocator byteBufAllocator, byte b, String str, List<Object> list) {
        ByteBuf ioBuffer = byteBufAllocator.ioBuffer(ByteBufUtil.utf8MaxBytes(str) + 1 + 2);
        ioBuffer.writeByte(b);
        ByteBufUtil.writeUtf8(ioBuffer, str);
        ioBuffer.writeShort(b.b);
        list.add(ioBuffer);
    }

    private void a(ByteBufAllocator byteBufAllocator, IntegerRedisMessage integerRedisMessage, List<Object> list) {
        ByteBuf ioBuffer = byteBufAllocator.ioBuffer(23);
        ioBuffer.writeByte(RedisMessageType.INTEGER.value());
        ioBuffer.writeBytes(a(integerRedisMessage.value()));
        ioBuffer.writeShort(b.b);
        list.add(ioBuffer);
    }

    private void a(ByteBufAllocator byteBufAllocator, BulkStringHeaderRedisMessage bulkStringHeaderRedisMessage, List<Object> list) {
        ByteBuf ioBuffer = byteBufAllocator.ioBuffer((bulkStringHeaderRedisMessage.isNull() ? 2 : 22) + 1);
        ioBuffer.writeByte(RedisMessageType.BULK_STRING.value());
        if (bulkStringHeaderRedisMessage.isNull()) {
            ioBuffer.writeShort(b.a);
        } else {
            ioBuffer.writeBytes(a(bulkStringHeaderRedisMessage.bulkStringLength()));
            ioBuffer.writeShort(b.b);
        }
        list.add(ioBuffer);
    }

    private static void a(ByteBufAllocator byteBufAllocator, BulkStringRedisContent bulkStringRedisContent, List<Object> list) {
        list.add(bulkStringRedisContent.content().retain());
        if (bulkStringRedisContent instanceof LastBulkStringRedisContent) {
            list.add(byteBufAllocator.ioBuffer(2).writeShort(b.b));
        }
    }

    private void a(ByteBufAllocator byteBufAllocator, FullBulkStringRedisMessage fullBulkStringRedisMessage, List<Object> list) {
        if (fullBulkStringRedisMessage.isNull()) {
            ByteBuf ioBuffer = byteBufAllocator.ioBuffer(5);
            ioBuffer.writeByte(RedisMessageType.BULK_STRING.value());
            ioBuffer.writeShort(b.a);
            ioBuffer.writeShort(b.b);
            list.add(ioBuffer);
            return;
        }
        ByteBuf ioBuffer2 = byteBufAllocator.ioBuffer(23);
        ioBuffer2.writeByte(RedisMessageType.BULK_STRING.value());
        ioBuffer2.writeBytes(a(fullBulkStringRedisMessage.content().readableBytes()));
        ioBuffer2.writeShort(b.b);
        list.add(ioBuffer2);
        list.add(fullBulkStringRedisMessage.content().retain());
        list.add(byteBufAllocator.ioBuffer(2).writeShort(b.b));
    }

    private void a(ByteBufAllocator byteBufAllocator, ArrayHeaderRedisMessage arrayHeaderRedisMessage, List<Object> list) {
        a(byteBufAllocator, arrayHeaderRedisMessage.isNull(), arrayHeaderRedisMessage.length(), list);
    }

    private void a(ByteBufAllocator byteBufAllocator, ArrayRedisMessage arrayRedisMessage, List<Object> list) {
        if (arrayRedisMessage.isNull()) {
            a(byteBufAllocator, arrayRedisMessage.isNull(), -1L, list);
            return;
        }
        a(byteBufAllocator, arrayRedisMessage.isNull(), arrayRedisMessage.children().size(), list);
        for (RedisMessage redisMessage : arrayRedisMessage.children()) {
            a(byteBufAllocator, redisMessage, list);
        }
    }

    private void a(ByteBufAllocator byteBufAllocator, boolean z, long j, List<Object> list) {
        if (z) {
            ByteBuf ioBuffer = byteBufAllocator.ioBuffer(5);
            ioBuffer.writeByte(RedisMessageType.ARRAY_HEADER.value());
            ioBuffer.writeShort(b.a);
            ioBuffer.writeShort(b.b);
            list.add(ioBuffer);
            return;
        }
        ByteBuf ioBuffer2 = byteBufAllocator.ioBuffer(23);
        ioBuffer2.writeByte(RedisMessageType.ARRAY_HEADER.value());
        ioBuffer2.writeBytes(a(j));
        ioBuffer2.writeShort(b.b);
        list.add(ioBuffer2);
    }

    private byte[] a(long j) {
        byte[] byteBufOfInteger = this.a.getByteBufOfInteger(j);
        return byteBufOfInteger != null ? byteBufOfInteger : a.a(j);
    }
}
