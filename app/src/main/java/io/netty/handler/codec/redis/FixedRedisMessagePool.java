package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.collection.LongObjectHashMap;
import io.netty.util.collection.LongObjectMap;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class FixedRedisMessagePool implements RedisMessagePool {
    private Map<ByteBuf, SimpleStringRedisMessage> c = new HashMap(a.length, 1.0f);
    private Map<String, SimpleStringRedisMessage> d = new HashMap(a.length, 1.0f);
    private Map<ByteBuf, ErrorRedisMessage> e;
    private Map<String, ErrorRedisMessage> f;
    private Map<ByteBuf, IntegerRedisMessage> g;
    private LongObjectMap<IntegerRedisMessage> h;
    private LongObjectMap<byte[]> i;
    private static final String[] a = {"OK", "PONG", "QUEUED"};
    private static final String[] b = {"ERR", "ERR index out of range", "ERR no such key", "ERR source and destination objects are the same", "ERR syntax error", "BUSY Redis is busy running a script. You can only call SCRIPT KILL or SHUTDOWN NOSAVE.", "BUSYKEY Target key name already exists.", "EXECABORT Transaction discarded because of previous errors.", "LOADING Redis is loading the dataset in memory", "MASTERDOWN Link with MASTER is down and slave-serve-stale-data is set to 'no'.", "MISCONF Redis is configured to save RDB snapshots, but is currently not able to persist on disk. Commands that may modify the data set are disabled. Please check Redis logs for details about the error.", "NOAUTH Authentication required.", "NOREPLICAS Not enough good slaves to write.", "NOSCRIPT No matching script. Please use EVAL.", "OOM command not allowed when used memory > 'maxmemory'.", "READONLY You can't write against a read only slave.", "WRONGTYPE Operation against a key holding the wrong kind of value"};
    public static final FixedRedisMessagePool INSTANCE = new FixedRedisMessagePool();

    private FixedRedisMessagePool() {
        String[] strArr = a;
        for (String str : strArr) {
            ByteBuf unmodifiableBuffer = Unpooled.unmodifiableBuffer(Unpooled.unreleasableBuffer(Unpooled.wrappedBuffer(str.getBytes(CharsetUtil.UTF_8))));
            SimpleStringRedisMessage simpleStringRedisMessage = new SimpleStringRedisMessage(str);
            this.c.put(unmodifiableBuffer, simpleStringRedisMessage);
            this.d.put(str, simpleStringRedisMessage);
        }
        this.e = new HashMap(b.length, 1.0f);
        this.f = new HashMap(b.length, 1.0f);
        String[] strArr2 = b;
        for (String str2 : strArr2) {
            ByteBuf unmodifiableBuffer2 = Unpooled.unmodifiableBuffer(Unpooled.unreleasableBuffer(Unpooled.wrappedBuffer(str2.getBytes(CharsetUtil.UTF_8))));
            ErrorRedisMessage errorRedisMessage = new ErrorRedisMessage(str2);
            this.e.put(unmodifiableBuffer2, errorRedisMessage);
            this.f.put(str2, errorRedisMessage);
        }
        this.g = new HashMap(129, 1.0f);
        this.h = new LongObjectHashMap(129, 1.0f);
        this.i = new LongObjectHashMap(129, 1.0f);
        for (long j = -1; j < 128; j++) {
            byte[] a2 = a.a(j);
            ByteBuf unmodifiableBuffer3 = Unpooled.unmodifiableBuffer(Unpooled.unreleasableBuffer(Unpooled.wrappedBuffer(a2)));
            IntegerRedisMessage integerRedisMessage = new IntegerRedisMessage(j);
            this.g.put(unmodifiableBuffer3, integerRedisMessage);
            this.h.put(j, (long) integerRedisMessage);
            this.i.put(j, (long) a2);
        }
    }

    @Override // io.netty.handler.codec.redis.RedisMessagePool
    public SimpleStringRedisMessage getSimpleString(String str) {
        return this.d.get(str);
    }

    @Override // io.netty.handler.codec.redis.RedisMessagePool
    public SimpleStringRedisMessage getSimpleString(ByteBuf byteBuf) {
        return this.c.get(byteBuf);
    }

    @Override // io.netty.handler.codec.redis.RedisMessagePool
    public ErrorRedisMessage getError(String str) {
        return this.f.get(str);
    }

    @Override // io.netty.handler.codec.redis.RedisMessagePool
    public ErrorRedisMessage getError(ByteBuf byteBuf) {
        return this.e.get(byteBuf);
    }

    @Override // io.netty.handler.codec.redis.RedisMessagePool
    public IntegerRedisMessage getInteger(long j) {
        return this.h.get(j);
    }

    @Override // io.netty.handler.codec.redis.RedisMessagePool
    public IntegerRedisMessage getInteger(ByteBuf byteBuf) {
        return this.g.get(byteBuf);
    }

    @Override // io.netty.handler.codec.redis.RedisMessagePool
    public byte[] getByteBufOfInteger(long j) {
        return this.i.get(j);
    }
}
