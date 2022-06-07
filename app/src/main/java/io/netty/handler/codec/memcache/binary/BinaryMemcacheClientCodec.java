package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.PrematureChannelClosureException;
import io.netty.handler.codec.memcache.LastMemcacheContent;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public final class BinaryMemcacheClientCodec extends CombinedChannelDuplexHandler<BinaryMemcacheResponseDecoder, BinaryMemcacheRequestEncoder> {
    private final boolean b;
    private final AtomicLong c;

    public BinaryMemcacheClientCodec() {
        this(8192);
    }

    public BinaryMemcacheClientCodec(int i) {
        this(i, false);
    }

    public BinaryMemcacheClientCodec(int i, boolean z) {
        this.c = new AtomicLong();
        this.b = z;
        init(new a(i), new b());
    }

    /* loaded from: classes4.dex */
    private final class b extends BinaryMemcacheRequestEncoder {
        private b() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.handler.codec.memcache.AbstractMemcacheObjectEncoder, io.netty.handler.codec.MessageToMessageEncoder
        public void encode(ChannelHandlerContext channelHandlerContext, Object obj, List<Object> list) throws Exception {
            super.encode(channelHandlerContext, obj, list);
            if (BinaryMemcacheClientCodec.this.b && (obj instanceof LastMemcacheContent)) {
                BinaryMemcacheClientCodec.this.c.incrementAndGet();
            }
        }
    }

    /* loaded from: classes4.dex */
    private final class a extends BinaryMemcacheResponseDecoder {
        a(int i) {
            super(i);
        }

        @Override // io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder, io.netty.handler.codec.ByteToMessageDecoder
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            super.decode(channelHandlerContext, byteBuf, list);
            if (BinaryMemcacheClientCodec.this.b) {
                int size = list.size();
                for (int size2 = list.size(); size2 < size; size2++) {
                    if (list.get(size2) instanceof LastMemcacheContent) {
                        BinaryMemcacheClientCodec.this.c.decrementAndGet();
                    }
                }
            }
        }

        @Override // io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder, io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
            super.channelInactive(channelHandlerContext);
            if (BinaryMemcacheClientCodec.this.b) {
                long j = BinaryMemcacheClientCodec.this.c.get();
                if (j > 0) {
                    channelHandlerContext.fireExceptionCaught((Throwable) new PrematureChannelClosureException("channel gone inactive with " + j + " missing response(s)"));
                }
            }
        }
    }
}
