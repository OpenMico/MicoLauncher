package io.netty.handler.codec.redis;

import com.xiaomi.mipush.sdk.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import java.util.List;

/* loaded from: classes4.dex */
public final class RedisDecoder extends ByteToMessageDecoder {
    private final b a;
    private final int c;
    private final RedisMessagePool d;
    private a e;
    private RedisMessageType f;
    private int g;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public enum a {
        DECODE_TYPE,
        DECODE_INLINE,
        DECODE_LENGTH,
        DECODE_BULK_STRING_EOL,
        DECODE_BULK_STRING_CONTENT
    }

    public RedisDecoder() {
        this(65536, FixedRedisMessagePool.INSTANCE);
    }

    public RedisDecoder(int i, RedisMessagePool redisMessagePool) {
        this.a = new b();
        this.e = a.DECODE_TYPE;
        if (i <= 0 || i > 536870912) {
            throw new RedisCodecException("maxInlineMessageLength: " + i + " (expected: <= 536870912)");
        }
        this.c = i;
        this.d = redisMessagePool;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (true) {
            try {
                switch (this.e) {
                    case DECODE_TYPE:
                        if (a(byteBuf)) {
                            break;
                        } else {
                            return;
                        }
                    case DECODE_INLINE:
                        if (a(byteBuf, list)) {
                            break;
                        } else {
                            return;
                        }
                    case DECODE_LENGTH:
                        if (b(byteBuf, list)) {
                            break;
                        } else {
                            return;
                        }
                    case DECODE_BULK_STRING_EOL:
                        if (d(byteBuf, list)) {
                            break;
                        } else {
                            return;
                        }
                    case DECODE_BULK_STRING_CONTENT:
                        if (e(byteBuf, list)) {
                            break;
                        } else {
                            return;
                        }
                    default:
                        throw new RedisCodecException("Unknown state: " + this.e);
                }
            } catch (RedisCodecException e) {
                a();
                throw e;
            } catch (Exception e2) {
                a();
                throw new RedisCodecException(e2);
            }
        }
    }

    private void a() {
        this.e = a.DECODE_TYPE;
        this.g = 0;
    }

    private boolean a(ByteBuf byteBuf) throws Exception {
        if (!byteBuf.isReadable()) {
            return false;
        }
        this.f = RedisMessageType.valueOf(byteBuf.readByte());
        this.e = this.f.isInline() ? a.DECODE_INLINE : a.DECODE_LENGTH;
        return true;
    }

    private boolean a(ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteBuf c = c(byteBuf);
        if (c != null) {
            list.add(a(this.f, c));
            a();
            return true;
        } else if (byteBuf.readableBytes() <= this.c) {
            return false;
        } else {
            throw new RedisCodecException("length: " + byteBuf.readableBytes() + " (expected: <= " + this.c + ")");
        }
    }

    private boolean b(ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteBuf c = c(byteBuf);
        if (c == null) {
            return false;
        }
        long d = d(c);
        if (d >= -1) {
            switch (this.f) {
                case ARRAY_HEADER:
                    list.add(new ArrayHeaderRedisMessage(d));
                    a();
                    return true;
                case BULK_STRING:
                    if (d <= 536870912) {
                        this.g = (int) d;
                        return c(byteBuf, list);
                    }
                    throw new RedisCodecException("length: " + d + " (expected: <= 536870912)");
                default:
                    throw new RedisCodecException("bad type: " + this.f);
            }
        } else {
            throw new RedisCodecException("length: " + d + " (expected: >= -1)");
        }
    }

    private boolean c(ByteBuf byteBuf, List<Object> list) throws Exception {
        int i = this.g;
        switch (i) {
            case -1:
                list.add(FullBulkStringRedisMessage.NULL_INSTANCE);
                a();
                return true;
            case 0:
                this.e = a.DECODE_BULK_STRING_EOL;
                return d(byteBuf, list);
            default:
                list.add(new BulkStringHeaderRedisMessage(i));
                this.e = a.DECODE_BULK_STRING_CONTENT;
                return e(byteBuf, list);
        }
    }

    private boolean d(ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 2) {
            return false;
        }
        b(byteBuf);
        list.add(FullBulkStringRedisMessage.EMPTY_INSTANCE);
        a();
        return true;
    }

    private boolean e(ByteBuf byteBuf, List<Object> list) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            return false;
        }
        int i = this.g;
        if (readableBytes >= i + 2) {
            ByteBuf readSlice = byteBuf.readSlice(i);
            b(byteBuf);
            list.add(new DefaultLastBulkStringRedisContent(readSlice.retain()));
            a();
            return true;
        }
        int min = Math.min(i, readableBytes);
        this.g -= min;
        list.add(new DefaultBulkStringRedisContent(byteBuf.readSlice(min).retain()));
        return true;
    }

    private static void b(ByteBuf byteBuf) {
        short readShort = byteBuf.readShort();
        if (b.b != readShort) {
            byte[] a2 = a.a(readShort);
            throw new RedisCodecException("delimiter: [" + ((int) a2[0]) + Constants.ACCEPT_TIME_SEPARATOR_SP + ((int) a2[1]) + "] (expected: \\r\\n)");
        }
    }

    private RedisMessage a(RedisMessageType redisMessageType, ByteBuf byteBuf) {
        switch (redisMessageType) {
            case SIMPLE_STRING:
                SimpleStringRedisMessage simpleString = this.d.getSimpleString(byteBuf);
                return simpleString != null ? simpleString : new SimpleStringRedisMessage(byteBuf.toString(CharsetUtil.UTF_8));
            case ERROR:
                ErrorRedisMessage error = this.d.getError(byteBuf);
                return error != null ? error : new ErrorRedisMessage(byteBuf.toString(CharsetUtil.UTF_8));
            case INTEGER:
                IntegerRedisMessage integer = this.d.getInteger(byteBuf);
                return integer != null ? integer : new IntegerRedisMessage(d(byteBuf));
            default:
                throw new RedisCodecException("bad type: " + redisMessageType);
        }
    }

    private static ByteBuf c(ByteBuf byteBuf) {
        int forEachByte;
        if (!byteBuf.isReadable(2) || (forEachByte = byteBuf.forEachByte(ByteProcessor.FIND_LF)) < 0) {
            return null;
        }
        ByteBuf readSlice = byteBuf.readSlice((forEachByte - byteBuf.readerIndex()) - 1);
        b(byteBuf);
        return readSlice;
    }

    private long d(ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        int i = (readableBytes <= 0 || byteBuf.getByte(byteBuf.readerIndex()) != 45) ? 0 : 1;
        if (readableBytes <= i) {
            throw new RedisCodecException("no number to parse: " + byteBuf.toString(CharsetUtil.US_ASCII));
        } else if (readableBytes > i + 19) {
            throw new RedisCodecException("too many characters to be a valid RESP Integer: " + byteBuf.toString(CharsetUtil.US_ASCII));
        } else if (i != 0) {
            return -e(byteBuf.skipBytes(i));
        } else {
            return e(byteBuf);
        }
    }

    private long e(ByteBuf byteBuf) {
        this.a.b();
        byteBuf.forEachByte(this.a);
        return this.a.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b implements ByteProcessor {
        private long a;

        private b() {
        }

        @Override // io.netty.util.ByteProcessor
        public boolean process(byte b) throws Exception {
            if (b < 48 || b > 57) {
                throw new RedisCodecException("bad byte in number: " + ((int) b));
            }
            this.a = (this.a * 10) + (b - 48);
            return true;
        }

        public long a() {
            return this.a;
        }

        public void b() {
            this.a = 0L;
        }
    }
}
