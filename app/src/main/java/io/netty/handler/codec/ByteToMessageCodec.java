package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class ByteToMessageCodec<I> extends ChannelDuplexHandler {
    private final TypeParameterMatcher a;
    private final MessageToByteEncoder<I> b;
    private final ByteToMessageDecoder c;

    protected abstract void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception;

    protected abstract void encode(ChannelHandlerContext channelHandlerContext, I i, ByteBuf byteBuf) throws Exception;

    protected ByteToMessageCodec() {
        this(true);
    }

    protected ByteToMessageCodec(Class<? extends I> cls) {
        this(cls, true);
    }

    protected ByteToMessageCodec(boolean z) {
        this.c = new ByteToMessageDecoder() { // from class: io.netty.handler.codec.ByteToMessageCodec.1
            @Override // io.netty.handler.codec.ByteToMessageDecoder
            public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
                ByteToMessageCodec.this.decode(channelHandlerContext, byteBuf, list);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // io.netty.handler.codec.ByteToMessageDecoder
            public void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
                ByteToMessageCodec.this.decodeLast(channelHandlerContext, byteBuf, list);
            }
        };
        b.a(this);
        this.a = TypeParameterMatcher.find(this, ByteToMessageCodec.class, "I");
        this.b = new a(z);
    }

    protected ByteToMessageCodec(Class<? extends I> cls, boolean z) {
        this.c = new ByteToMessageDecoder() { // from class: io.netty.handler.codec.ByteToMessageCodec.1
            @Override // io.netty.handler.codec.ByteToMessageDecoder
            public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
                ByteToMessageCodec.this.decode(channelHandlerContext, byteBuf, list);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // io.netty.handler.codec.ByteToMessageDecoder
            public void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
                ByteToMessageCodec.this.decodeLast(channelHandlerContext, byteBuf, list);
            }
        };
        b.a(this);
        this.a = TypeParameterMatcher.get(cls);
        this.b = new a(z);
    }

    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return this.a.match(obj);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        this.c.channelRead(channelHandlerContext, obj);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        this.b.write(channelHandlerContext, obj, channelPromise);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.c.channelReadComplete(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.c.channelInactive(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            this.c.handlerAdded(channelHandlerContext);
        } finally {
            this.b.handlerAdded(channelHandlerContext);
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            this.c.handlerRemoved(channelHandlerContext);
        } finally {
            this.b.handlerRemoved(channelHandlerContext);
        }
    }

    protected void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.isReadable()) {
            decode(channelHandlerContext, byteBuf, list);
        }
    }

    /* loaded from: classes4.dex */
    private final class a extends MessageToByteEncoder<I> {
        a(boolean z) {
            super(z);
        }

        @Override // io.netty.handler.codec.MessageToByteEncoder
        public boolean acceptOutboundMessage(Object obj) throws Exception {
            return ByteToMessageCodec.this.acceptOutboundMessage(obj);
        }

        @Override // io.netty.handler.codec.MessageToByteEncoder
        protected void encode(ChannelHandlerContext channelHandlerContext, I i, ByteBuf byteBuf) throws Exception {
            ByteToMessageCodec.this.encode(channelHandlerContext, i, byteBuf);
        }
    }
}
